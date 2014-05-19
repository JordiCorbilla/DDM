// Distributed Decision making system framework 
//Copyright (c) 2014, Jordi Coll Corbilla
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

package ddm.agents;

import java.util.HashMap;

import ddm.behaviours.RegisterInDFBehaviour;
import ddm.behaviours.WaitUserCommandBehaviour;
import ddm.configuration.ManagerConfiguration;
import ddm.data.DatasetManager;
import ddm.logging.ShowMessage;
import ddm.ontology.ClassifierOntology;
import ddm.ontology.ClassifierSettings;
import ddm.ontology.TrainingResult;
import jade.content.Concept;
import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.basic.Action;
import jade.core.*;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.ContainerController;

@SuppressWarnings("serial")
public class ManagerAgent extends Agent {
	private Codec codec = new SLCodec();
	private Ontology ontology = ClassifierOntology.getInstance();
	private ManagerConfiguration conf = null;
	private int numberOfAgents;
	private boolean createDefinedAgents;
	private ShowMessage sm;
	private ContainerController mainContainerController;
	private DatasetManager datasetManager = null;
	private HashMap<String, ClassifierSettings> ClassifierSettings = new HashMap<String, ClassifierSettings>();

	protected void setup() {
		mainContainerController = null;
		conf = ManagerConfiguration.getInstance();
		sm = new ShowMessage("Manager Agent", getLocalName(), conf.getVerbosity(), conf.getLogging());
		sm.Log("Starting Agent");
		// Read Properties file
		sm.Log("Reading configuration file");
		setNumberOfAgents(0);
		setCreateDefinedAgents(false);
		// Register language and ontology
		sm.Log("Registering language and ontology");
		getContentManager().registerLanguage(codec);
		getContentManager().registerOntology(ontology);

		// Set this agent main behaviour
		sm.Log("Setting up behaviours");
		SequentialBehaviour sb = new SequentialBehaviour();
		sb.addSubBehaviour(new RegisterInDFBehaviour(this, sm, "Manager Agent", "Man0001"));
		addBehaviour(sb);
		addBehaviour(new WaitUserCommandBehaviour(this));

		addBehaviour(new ReceiveResponse(this));
	}

	public void ConfigureDataSet() {
		datasetManager = new DatasetManager(conf, ClassifierSettings, getNumberOfAgents());	
	}
	
	class ReceiveResponse extends CyclicBehaviour {
		// ----------------------------------------------- // Receive and handle
		// server responses

		// private boolean finished = false;

		private ManagerAgent myAgent;
		ReceiveResponse(ManagerAgent a) {
			super(a);
			myAgent = a;
			count = 0;
			count2 = 0;
		}

		private int count = 0;
		private int count2 = 0;
		
		private MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);

		private int step = 0;

		public void action() {

			switch (step) {
			case 0:
				ACLMessage msg = myAgent.receive(mt);
				if (msg == null) {
					block();
				} else {
					if (msg.getPerformative() == ACLMessage.NOT_UNDERSTOOD) {
						sm.Log("\n\n\tResponse from server: NOT UNDERSTOOD!");
					} else if (msg.getPerformative() == ACLMessage.INFORM) {
						ContentElement content = null;
						try {
							content = getContentManager().extractContent(msg);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						Concept action = ((Action) content).getAction();
						if (action instanceof ClassifierSettings) {
							ClassifierSettings.put(msg.getSender().getLocalName(), (ClassifierSettings) action);
							sm.Log("Classifier Settings received " + ((ClassifierSettings) action).toString());
							System.out.println("Classifier Settings received " + ((ClassifierSettings) action).toString());
							count++;
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							if (count == (2*numberOfAgents))
								step = 1;
						}
						else if (action instanceof TrainingResult) {
							TrainingResult tr = (TrainingResult) action;
							System.out.println(tr.getName() + " " + tr.getType() + " " + tr.getDuration());
							count2++;
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							if (count2 == (2*numberOfAgents))
								step = 1;
						}						
					}
				}
				break;
			case 1:
				addBehaviour(new WaitUserCommandBehaviour(myAgent));
				count = 0;
				count2 = 0;
				step = 0;
				break;
			}
		}
	}

	public int getNumberOfAgents() {
		return numberOfAgents;
	}

	public void setNumberOfAgents(int numberOfAgents) {
		this.numberOfAgents = numberOfAgents;
	}

	public boolean isCreateDefinedAgents() {
		return createDefinedAgents;
	}

	public void setCreateDefinedAgents(boolean createDefinedAgents) {
		this.createDefinedAgents = createDefinedAgents;
	}

	public DatasetManager getDatasetManager() {
		return datasetManager;
	}

	public void setDatasetManager(DatasetManager datasetManager) {
		this.datasetManager = datasetManager;
	}
	
	public ShowMessage getSM(){
		return sm;
	}
	
	public ManagerConfiguration getConfiguration()
	{
		return conf;
	}
	
	public HashMap<String, ClassifierSettings> getClassifierSettings(){
		return ClassifierSettings;
	}
	
	public ContainerController getMainContainerController()
	{
		return mainContainerController;
	}
	
	public void setMainContainerController(ContainerController mainContainerController){
		this.mainContainerController = mainContainerController;
	}
}
