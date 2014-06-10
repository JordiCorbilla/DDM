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

import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.basic.Action;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import ddm.agents.ClassifierAgent;
import ddm.factory.classifiers.ClassifierInstanceFactory;
import ddm.factory.classifiers.ClassifierType;
import ddm.logging.ShowMessage;
import ddm.ontology.ClassifierOntology;
import ddm.ontology.ClassifierSettings;
import ddm.ontology.ManagerSettings;

@SuppressWarnings("serial")
public class HandleManagerSettingsBehaviour extends OneShotBehaviour {

	private ACLMessage request;
	private ClassifierAgent myAgent;
	private ShowMessage sm;
	private Codec codec = new SLCodec();
	private Ontology ontology = ClassifierOntology.getInstance();

	public HandleManagerSettingsBehaviour(ClassifierAgent a, ACLMessage request) {

		super(a);
		myAgent = a;
		this.request = request;
		this.sm = a.getSM();
	}

	@Override
	public void action() {
		try {
			ContentElement content = myAgent.getContentManager()
					.extractContent(request);
			ManagerSettings ms = (ManagerSettings) ((Action) content)
					.getAction();
			sm.Log("Classifier requested by Manager : "
					+ ms.getClassifierModule());
			String newClassifierRequested = ms.getClassifierModule();
			if (!newClassifierRequested.equals("random")) {
				sm.Log("Classifier Changed");
				ClassifierType ct = ClassifierType
						.GetType(newClassifierRequested);
				myAgent.setClassifier(ClassifierInstanceFactory
						.buildClassifier(ct));
				sm.Log("Classifier Changed ->" + myAgent.getClassifier().type());
			}
			sm.Log("Sending data to : " + request.getSender().toString());
			ACLMessage reply = request.createReply();
			reply.setPerformative(ACLMessage.INFORM);
			reply.setLanguage(codec.getName());
			reply.setOntology(ontology.getName());
			try {
				ClassifierSettings cs = new ClassifierSettings();
				cs.setName(myAgent.getLocalName());
				cs.setPercentageTrainingData(myAgent
						.getPercentageTrainingData());
				cs.setClassifierModule(ms.getClassifierModule());
				myAgent.getContentManager().fillContent(reply,
						new Action(request.getSender(), cs));
				reply.addReceiver(request.getSender());
				myAgent.send(reply);
				System.out.println("Contacting server... Please wait!");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
