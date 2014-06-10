// Distributed Decision making system framework 
// Copyright (c) 2014, Jordi Coll Corbilla
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
// - Redistributions of source code must retain the above copyright notice,
// this list of conditions and the following disclaimer.
// - Redistributions in binary form must reproduce the above copyright notice,
// this list of conditions and the following disclaimer in the documentation
// and/or other materials provided with the distribution.
// - Neither the name of this library nor the names of its contributors may be
// used to endorse or promote products derived from this software without
// specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
// POSSIBILITY OF SUCH DAMAGE.

package ddm.behaviours;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import jade.content.Concept;
import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import ddm.agents.ManagerAgent;
import ddm.data.DatasetManager;
import ddm.decision.DecisionMaker;
import ddm.decision.DecisionType;
import ddm.jade.JadeAgents;
import ddm.logging.ShowMessage;
import ddm.ontology.ClassificationResult;
import ddm.ontology.ClassifierOntology;
import ddm.ontology.DataInstance;

/**
 * 
 * @author jordi Corbilla
 * Behaviour that manages the communication between manager and agents. This class sends the information
 * to each individual classifier and it waits until the response is back from the server.
 * It's using a bespoke behaviour with three different steps.
 */
@SuppressWarnings("serial")
public class SendDataBehaviour extends Behaviour {

	private ManagerAgent myAgent;
	private ShowMessage sm;
	private Codec codec = new SLCodec();
	private Ontology ontology = ClassifierOntology.getInstance();
	private int step = 0;
	private int repliesCnt = 0;
	private int DataSize = 0;
	private int index = 0;
	private ArrayList<DataInstance> data;
	private MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
	private jade.util.leap.List Classifiers;
	private HashMap<String, ClassificationResult> decisionResult = new HashMap<String, ClassificationResult>();
	private DataInstance dataToPredict;
	private DecisionMaker decisionMaker;
	private int TrainingSize;
	
	// Get the user command
	public SendDataBehaviour(ManagerAgent a) {
		super(a);
		myAgent = a;
		sm = a.getSM();
	}

	public void action() {
		sm.Log("SendDataBehaviour - start");
		switch (step) {
		case 0:
			//Prepare the data
			DatasetManager datasetManager = myAgent.getDatasetManager();
			this.data = datasetManager.getDatasetsData();
			this.DataSize = data.size();
			this.TrainingSize = datasetManager.getPartitionList().getTrainingSize();
			decisionMaker = new DecisionMaker(myAgent.getConfiguration());
			Classifiers = JadeAgents.SearchAgents(myAgent, "Classifier Agent", null);
			sm.Log("Agents found: " + Classifiers.size());
			System.out.println("Data size" + this.DataSize);
			System.out.println("GOING TO STEP 1");
			step = 1;
			break;
		case 1:
			//Send the data to classify to every agent
			repliesCnt = 0;
			this.dataToPredict = null;
			decisionResult.clear();
			sm.Log("Looking for agents online");
			
			Iterator<?> classifiers = Classifiers.iterator();
			
			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
			while (classifiers.hasNext()) {
				AID server = (AID) classifiers.next();
				System.out.println("Sending to: " + server.getName());
				msg.setLanguage(codec.getName());
				msg.setOntology(ontology.getName());
				try {
					DataInstance dataInstance = data.get(index);
					this.dataToPredict = dataInstance;
					System.out.println("Data to send " + dataInstance.toString());
					myAgent.getContentManager().fillContent(msg, new Action(server, dataInstance));
					msg.addReceiver(server);
					myAgent.send(msg);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//System.out.println("Contacting client... Please wait!");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			
			if (index < DataSize)
			{
				index++;
				System.out.println("GOING TO STEP 2 - Decision");
				step = 2;
			}
			break;
		case 2:
			//This property is set to the number of agents + 1 as the framework is sending duplicated messages.
			//http://jade.17737.x6.nabble.com/Receiving-duplicate-messages-td5002566.html
			int number = myAgent.getNumberOfAgents() + 1;
			
			//Receive all the proposals from every agent
			ACLMessage reply = myAgent.receive(mt);
			if (reply != null) {
				if (reply.getPerformative() == ACLMessage.PROPOSE) {
					//System.out.println("Propose!!!");
					ContentElement content = null;
					try {
						content = myAgent.getContentManager().extractContent(reply);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					Concept action = ((Action) content).getAction();
					if (action instanceof ClassificationResult) {
						ClassificationResult cr = (ClassificationResult) action;
						System.out.println("*****" + cr.getName() + " " + cr.getType() + " " + cr.getDuration() + "ms NumCorrect: " 
						+ cr.getNumCorrect() + " Value:" + cr.getInstanceValue() + " TrainingSize:" + cr.getTrainingSize() 
						+ " Total:" + this.TrainingSize + " Val:" + cr.getInstanceClassification() 
						+ " Pred:" + cr.getInstancePredictedValue() + " Value:" + cr.getPredictedInstanceValue());
						repliesCnt++;
						decisionResult.put(cr.getName(), cr);
						if (repliesCnt >= (number*myAgent.getNumberOfAgents()) && (index < DataSize))
						{
							decisionMaker.Make(DecisionType.OrderedWeightedAggregation, dataToPredict, decisionResult, TrainingSize);
							step = 1;
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							System.out.println("GOING TO STEP 1 - Communications");
						}
						else if (repliesCnt >= (number*myAgent.getNumberOfAgents()) && (index == DataSize))
						{
							System.out.println("GOING TO STEP 3 - evaluation");
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}							
							step = 3;
						}
					}			
				}
			}
			else
			{
				block();
			}
			break;
		case 3:
			decisionMaker.CloseFile();
			myAgent.addBehaviour(new WaitUserCommandBehaviour(myAgent));
			step = 4;
			break;
		}
		sm.Log("SendDataBehaviour - end");
	}

	@Override
	public boolean done() {
		return step == 4;
	}	

}
