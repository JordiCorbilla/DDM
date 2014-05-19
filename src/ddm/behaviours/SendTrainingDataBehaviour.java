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

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import java.util.Iterator;
import ddm.agents.ManagerAgent;
import ddm.data.DatasetManager;
import ddm.jade.JadeAgents;
import ddm.logging.ShowMessage;
import ddm.ontology.Arff_Training_Repository;
import ddm.ontology.ClassifierOntology;

@SuppressWarnings("serial")
public class SendTrainingDataBehaviour extends OneShotBehaviour {

	private ManagerAgent myAgent;
	private ShowMessage sm;
	private Codec codec = new SLCodec();
	private Ontology ontology = ClassifierOntology.getInstance();
	
	// Get the user command
	public SendTrainingDataBehaviour(ManagerAgent a) {
		super(a);
		myAgent = a;
		sm = a.getSM();
	}	
	
	@Override
	public void action() {
		sm.Log("SendConfigurationToClassifiers - start");
		sm.Log("Looking for agents online");
		jade.util.leap.List Classifiers = JadeAgents.SearchAgents(myAgent, "Classifier Agent", null);
		sm.Log("Agents found: " + Classifiers.size());
		Iterator<?> classifiers = Classifiers.iterator();

		myAgent.ConfigureDataSet();
		DatasetManager datasetManager = myAgent.getDatasetManager();
		ArrayList<Arff_Training_Repository> datasetsTraining = datasetManager.getDatasetsTraining();
		
		int i = 0;
		while (classifiers.hasNext()) {
			AID server = (AID) classifiers.next();

			sm.Log("Sending data to : " + server.toString());

			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
			msg.setLanguage(codec.getName());
			msg.setOntology(ontology.getName());
			try {
				Arff_Training_Repository arff = datasetsTraining.get(i);
				myAgent.getContentManager().fillContent(msg, new Action(server, arff));
				msg.addReceiver(server);
				myAgent.send(msg);
				i++;
				System.out.println("Contacting client... Please wait!");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		sm.Log("SendConfigurationToClassifiers - end");
	}
}
