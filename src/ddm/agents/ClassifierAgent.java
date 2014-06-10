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

package ddm.agents;

import ddm.behaviours.HandleDataToClassifyBehaviour;
import ddm.behaviours.HandleManagerSettingsBehaviour;
import ddm.behaviours.HandleResponseInClassifierBehaviour;
import ddm.behaviours.RegisterInDFBehaviour;
import ddm.configuration.ClassifierConfiguration;
import ddm.factory.classifiers.ClassifierInstance;
import ddm.factory.classifiers.ClassifierInstanceFactory;
import ddm.logging.ShowMessage;
import ddm.ontology.Arff_Training_Repository;
import ddm.ontology.ClassifierOntology;
import ddm.ontology.DataInstance;
import ddm.ontology.ManagerSettings;
import ddm.utils.DataUtils;
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
import jade.util.leap.List;

/**
 * 
 * @author jordi Corbilla
 * ClassifierAgent class. This class generates the agent
 *         wrapper that deals with the requests from the manager and classifies
 *         the data according to the configuration section.
 */
@SuppressWarnings("serial")
public class ClassifierAgent extends Agent {
	private AID server = null;
	private Codec codec = new SLCodec();
	private Ontology ontology = ClassifierOntology.getInstance();
	private int percentageTrainingData;
	private ClassifierInstance classifier;
	private ClassifierConfiguration conf = null;
	private ShowMessage sm;
	private List header;
	private int TrainingSize;

	protected void setup() {
		// Read Properties file
		conf = ClassifierConfiguration.getInstance();
		sm = new ShowMessage("Classifier Agent", getLocalName(),
				conf.getVerbosity(), conf.getVerbosity());
		// Configure Agent with random parameters.
		this.percentageTrainingData = DataUtils.RandomNumber(
				conf.getMinimumPercentageTraining(),
				conf.getMaximumPercentageTraining());
		this.classifier = ClassifierInstanceFactory.giveMeAClassifier();
		sm.Log("Default Classifier choosen by agent: " + classifier.type());
		sm.Log("Percentage of training data that the agent will use: "
				+ this.percentageTrainingData);
		// ------------------------
		// Register language and ontology
		getContentManager().registerLanguage(codec);
		getContentManager().registerOntology(ontology);

		SequentialBehaviour sb = new SequentialBehaviour();
		sm.Log("Register with Directory Facilitator");
		sb.addSubBehaviour(new RegisterInDFBehaviour(this, sm,
				"Classifier Agent", "Man0001"));
		addBehaviour(sb);
		sm.Log("Messaging service installed with agent " + getLocalName());
		addBehaviour(new ReceiveMessages(this));
	}

	/**
	 * @author jordi coll
	 * Class that is used when the messaging service is installed
	 * to process all the messages from the manager
	 *
	 */
	class ReceiveMessages extends CyclicBehaviour {
		private ClassifierAgent myAgent;

		public ReceiveMessages(ClassifierAgent a) {
			super(a);
			myAgent = a;
		}

		private MessageTemplate mt = MessageTemplate
				.MatchPerformative(ACLMessage.REQUEST);

		public void action() {

			ACLMessage msg = myAgent.receive(mt);
			if (msg == null) {
				block();
			} else {
				try {
					ContentElement content = getContentManager()
							.extractContent(msg);
					Concept action = ((Action) content).getAction();

					switch (msg.getPerformative()) {

					case (ACLMessage.REQUEST):
						sm.Log("Request from " + msg.getSender().getLocalName());

						if (action instanceof Arff_Training_Repository)
							addBehaviour(new HandleResponseInClassifierBehaviour(
									myAgent, msg));
						else if (action instanceof ManagerSettings)
							addBehaviour(new HandleManagerSettingsBehaviour(
									myAgent, msg));
						else if (action instanceof DataInstance)
							addBehaviour(new HandleDataToClassifyBehaviour(
									myAgent, msg));
						break;
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	/**
	 * Get percentage of data destined to training
	 * @return percentageTrainingData
	 */
	public int getPercentageTrainingData() {
		return percentageTrainingData;
	}

	/**
	 * Get Show message module for logging purposes
	 * @return ShowMessage
	 */
	public ShowMessage getSM() {
		return sm;
	}

	/**
	 * Get configuration variable for the agent
	 * @return Configuration
	 */
	public ClassifierConfiguration getConfiguration() {
		return conf;
	}

	/**
	 * Set the amount of training data
	 * @param percentageTrainingData
	 */
	public void setPercentageTrainingData(int percentageTrainingData) {
		this.percentageTrainingData = percentageTrainingData;
	}

	/**
	 * Get the classifier instance that the factory will instantiate
	 * @return ClassifierInstance
	 */
	public ClassifierInstance getClassifier() {
		return classifier;
	}

	/**
	 * Set the type of classifier
	 * @param classifier
	 */
	public void setClassifier(ClassifierInstance classifier) {
		this.classifier = classifier;
	}

	/**
	 * Get ARFF file header
	 * @return List
	 */
	public List getHeader() {
		return header;
	}

	/**
	 * Set the ARFF header
	 * @param header
	 */
	public void setHeader(List header) {
		this.header = header;
	}

	/**
	 * Get training size
	 * @return training
	 */
	public int getTrainingSize() {
		return TrainingSize;
	}

	/**
	 * Set training size
	 * @param trainingSize
	 */
	public void setTrainingSize(int trainingSize) {
		TrainingSize = trainingSize;
	}

	/**
	 * Get AID server value used by Jade
	 * @return AID
	 */
	public AID getServer() {
		return server;
	}

	/**
	 * Set AID value used by Jade
	 * @param server
	 */
	public void setServer(AID server) {
		this.server = server;
	}

}
