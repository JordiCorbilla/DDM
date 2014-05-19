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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import ddm.behaviours.RegisterInDFBehaviour;
import ddm.configuration.ClassifierConfiguration;
import ddm.factory.classifiers.ClassifierInstance;
import ddm.factory.classifiers.ClassifierInstanceFactory;
import ddm.factory.classifiers.ClassifierType;
import ddm.logging.ShowMessage;
import ddm.ontology.Arff_Training_Repository;
import ddm.ontology.ClassificationResult;
import ddm.ontology.ClassifierOntology;
import ddm.ontology.ClassifierSettings;
import ddm.ontology.DataInstance;
import ddm.ontology.ManagerSettings;
import ddm.ontology.TrainingResult;
import ddm.utils.DataUtils;
import jade.content.Concept;
import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.basic.Action;
import jade.core.*;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.leap.List;

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
		//Read Properties file
		conf = ClassifierConfiguration.getInstance();
		sm = new ShowMessage("Classifier Agent", getLocalName(),conf.getVerbosity(), conf.getVerbosity());		
		//Configure Agent with random parameters.
		this.percentageTrainingData = DataUtils.RandomNumber(conf.getMinimumPercentageTraining(), conf.getMaximumPercentageTraining()); 
		this.classifier = ClassifierInstanceFactory.giveMeAClassifier();
		sm.Log("Default Classifier choosen by agent: " + classifier.type());
		sm.Log("Perentage of training data the agent will use: " + this.percentageTrainingData);
		// ------------------------
		// Register language and ontology
		getContentManager().registerLanguage(codec);
		getContentManager().registerOntology(ontology);
		
		SequentialBehaviour sb = new SequentialBehaviour();
		sb.addSubBehaviour(new RegisterInDFBehaviour(this, sm, "Classifier Agent", "Man0001"));
		addBehaviour(sb);	
		addBehaviour(new ReceiveMessages(this));
	}
	
	class ReceiveMessages extends CyclicBehaviour {
		// ----------------------------------------------- Receive requests and
		// queries from client
		// agent and launch appropriate handlers
		private ClassifierAgent myAgent;
		public ReceiveMessages(ClassifierAgent a) {
			super(a);
			myAgent = a;
		}
		private MessageTemplate mt =
				MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
		public void action() {

			ACLMessage msg = myAgent.receive(mt);
			if (msg == null) {
				block(); //block the agent until a message is received.
			}
			else {
				try {
					ContentElement content = getContentManager().extractContent(msg);
					Concept action = ((Action) content).getAction();
	
					switch (msg.getPerformative()) {
	
					case (ACLMessage.REQUEST):
	
						sm.Log("Request from " + msg.getSender().getLocalName());
	
						if (action instanceof Arff_Training_Repository)
							addBehaviour(new HandleResponse(myAgent, msg));
						else if (action instanceof ManagerSettings)
							addBehaviour(new HandleManagerSettings(myAgent, msg, classifier, percentageTrainingData));		
						else if (action instanceof DataInstance)
							addBehaviour(new HandleDataToClassify(myAgent, msg));		
						break;
					default: // replyNotUnderstood(msg);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}	

	class HandleDataToClassify extends OneShotBehaviour {

		private ClassifierAgent myAgent;
		private ACLMessage request;
		
		HandleDataToClassify(ClassifierAgent a, ACLMessage request) {
			super(a);
			this.myAgent = a;
			this.request = request;
		}
		
		@Override
		public void action() {
			try {
	            ContentElement content = getContentManager().extractContent(request);
	            DataInstance di = (DataInstance)((Action)content).getAction();
        
	            //Now that the data is here, we can compose the file and start Weka.
	            GenerateDataFileForClassifier(myAgent.getHeader(), di);
	            
	            classifier.setDataFile(conf.getApplicationPath() + getLocalName() + "_DataRepository.arff");
	            
	            classifier.ClassifyInstances();
	            //System.out.println("Classified!!!");
	            
	            ACLMessage reply = request.createReply();
	            reply.setPerformative(ACLMessage.PROPOSE);
	            reply.setLanguage(codec.getName());
	            reply.setOntology(ontology.getName());
	            try {
	            	ClassificationResult cr = new ClassificationResult();  
	            	cr.setDuration(classifier.getDurationTimeMs());
	            	cr.setName(getLocalName());
	            	cr.setType(classifier.type());
	            	cr.setNumCorrect(classifier.getCorrectIntances());
	            	cr.setPercentage(classifier.getPercentage());
	            	cr.setTrainingSize(myAgent.getTrainingSize());
	            	cr.setInstanceValue(classifier.getInstanceValue());
	            	cr.setPredictedInstanceValue(classifier.getPredictedInstanceValue());
	            	cr.setInstanceClassification(classifier.getInstanceClassification());
	            	cr.setInstancePredictedValue(classifier.getInstancePredictedClass());
			        getContentManager().fillContent(reply, new Action(request.getSender(), cr));
			        reply.addReceiver(request.getSender());
			        myAgent.send(reply);
			        //System.out.println("Contacting server... Please wait!");
			       }
			      	catch (Exception ex) { ex.printStackTrace(); 
			      }
	         }
	         catch(Exception ex) { ex.printStackTrace(); }
		}
		
	}
	
	class HandleManagerSettings extends OneShotBehaviour {

	      private ACLMessage request;
	      private ClassifierInstance classifier;
	      private int percentageTrainingData;

	      HandleManagerSettings(Agent a, ACLMessage request, ClassifierInstance classifier, int percentageTrainingData) {

	         super(a);
	         this.request = request;
	         this.setClassifier(classifier);
	         this.percentageTrainingData = percentageTrainingData;
	      }		
		
		@Override
		public void action() {
			try {
	            ContentElement content = getContentManager().extractContent(request);
	            ManagerSettings ms = (ManagerSettings)((Action)content).getAction();
	            sm.Log("Classifier requested by Manager : "+ ms.getClassifierModule());
	            String newClassifierRequested = ms.getClassifierModule();
	            if (!newClassifierRequested.equals("random")) {
		            ClassifierType ct = ClassifierType.GetType(newClassifierRequested);
		            this.setClassifier(ClassifierInstanceFactory.buildClassifier(ct));
	            }
	            sm.Log("Sending data to : " + request.getSender().toString());
	            ACLMessage reply = request.createReply();
	            reply.setPerformative(ACLMessage.INFORM);
	            reply.setLanguage(codec.getName());
	            reply.setOntology(ontology.getName());
	            try {
			    	  ClassifierSettings cs = new ClassifierSettings();
			    	  cs.setName(getLocalName());
			    	  cs.setPercentageTrainingData(this.percentageTrainingData);
			    	  cs.setClassifierModule(ms.getClassifierModule());
			          getContentManager().fillContent(reply, new Action(request.getSender(), cs));
			          reply.addReceiver(request.getSender());
			          myAgent.send(reply);
			          System.out.println("Contacting server... Please wait!");
			       }
			      	catch (Exception ex) { ex.printStackTrace(); 
			      }
	            //block();
	         }
	         catch(Exception ex) { ex.printStackTrace(); }
	      }

		public ClassifierInstance getClassifier() {
			return classifier;
		}

		public void setClassifier(ClassifierInstance classifier) {
			this.classifier = classifier;
		}
		}
	//}
	
	class HandleResponse extends OneShotBehaviour {
		// ----------------------------------------------------  Handler for a CreateAccount request

		      private ACLMessage request;
		      private ClassifierAgent myAgent;
		      	
		      HandleResponse(ClassifierAgent a, ACLMessage request) {
		         super(a);
		         myAgent = a;
		         this.request = request;
		      }
		      
		      @Override
		      public void action() {

		         try {
		            ContentElement content = getContentManager().extractContent(request);
		            Arff_Training_Repository ca = (Arff_Training_Repository)((Action)content).getAction();
		            sm.Log("Folder location: "+ ca.getName());
		            myAgent.setTrainingSize(ca.getData().size());
		            myAgent.setHeader(ca.getHeader());
		            sm.Log("Number of header rows received from Manager: "+ Integer.toString(header.size()));		            
		            List rows = ca.getData();
		            sm.Log("Number of rows received from Manager: "+ Integer.toString(rows.size()));
		            
		            //Now that the data is here, we can compose the file and start Weka.
		            GenerateFileForClassifier(myAgent.getHeader(), rows);
		            
		            classifier.setTrainingDataFile(conf.getApplicationPath() + getLocalName() + "_TrainingRepository.arff");
		            
		            classifier.TrainClassifier();
		            System.out.println("trained!!!");
		            
		            ACLMessage reply = request.createReply();
		            reply.setPerformative(ACLMessage.INFORM);
		            reply.setLanguage(codec.getName());
		            reply.setOntology(ontology.getName());
		            try {
		            	TrainingResult tr = new TrainingResult();  
		            	tr.setDuration(classifier.getDurationTrainingTimeMs());
		            	tr.setName(getLocalName());
		            	tr.setType(classifier.type());
   			            getContentManager().fillContent(reply, new Action(request.getSender(), tr));
				        reply.addReceiver(request.getSender());
				        myAgent.send(reply);
				        System.out.println("Contacting server... Please wait!");
				       }
				      	catch (Exception ex) { ex.printStackTrace(); 
				      }
		         }
		         catch(Exception ex) { ex.printStackTrace(); }
		      }
		   }	
	
	private void GenerateFileForClassifier(List header, List rows) {
		BufferedWriter bw = null;
		try {
            File file = new File(getLocalName() + "_TrainingRepository.arff");

            if (!file.exists()) {
				file.createNewFile();
			}

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			for (int i=0;i<header.size(); i++)
				bw.write(header.get(i).toString() + "\r\n");			
			for (int i=0;i<rows.size(); i++)
				bw.write(rows.get(i).toString() + "\r\n");
			
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
            	bw.close();
            } catch (Exception e) {
            }
        }	
	}
	
	private void GenerateDataFileForClassifier(List header, DataInstance dataInstance) {
		BufferedWriter bw = null;
		try {
            File file = new File(getLocalName() + "_DataRepository.arff");

            if (!file.exists()) {
				file.createNewFile();
			}

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			for (int i=0;i<header.size(); i++)
				bw.write(header.get(i).toString() + "\r\n");			
			bw.write(dataInstance.getValue() + "\r\n");
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
            	bw.close();
            } catch (Exception e) {
            }
        }	
	}	
	
	
//	private void ClassifyInstances()
//	{
//		String fileName = "C:/Users/jordi coll/workspace/TFG-DistributedDecisionJade/bin/" + getLocalName() + "_repository.arff";
//	
//		weka.core.Instances test = null;
//		try {
//			test = new weka.core.Instances(new java.io.FileReader(fileName));
//			sm.Log("here!!!! " + fileName);
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} 
//
//		if (test.classIndex() == -1)
//			test.setClassIndex(test.numAttributes()-1);		
//		
//		weka.classifiers.rules.PART j48DecisionTree = new weka.classifiers.rules.PART();
//		int trainingSetSize = test.numInstances() * 66 / 100;
//		int testSetSize = test.numInstances() - trainingSetSize ;
//		long startTimeMillis = System.currentTimeMillis();
//		weka.core.Instances trainingSet = new weka.core.Instances(test, 0, trainingSetSize );
//
//		trainingSet.setClassIndex(test.numAttributes() - 1);
//		try {
//			j48DecisionTree.buildClassifier(trainingSet);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		int numCorrect = 0;			
//		
//		for (int i = trainingSetSize; i < test.numInstances(); i++)
//		{
//			weka.core.Instance currentInst = test.instance(i);
//			
//			double predictedClass = 0;
//			try {
//				predictedClass = j48DecisionTree.classifyInstance(currentInst);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			
//			if (predictedClass == test.instance(i).classValue())
//			{
//				numCorrect++;
//			}
//		}
//
//		long finishTimeMillis = System.currentTimeMillis();
//		long durationTimeMllis = finishTimeMillis - startTimeMillis;
//		
//		double percentage = (double)numCorrect / (double)testSetSize * 100.0;
//		
//		java.text.DecimalFormat percentageFormatter = new java.text.DecimalFormat("#0.00");
//		sm.Log(" \t " + 
//							"Correct Instances: " + numCorrect + "\t\t"+
//							"Test Set Size: " + testSetSize + "\t\t " +
//							"Percentage: " + percentageFormatter.format(percentage)+" \t\t" +
//				 			"Duration(ms): " + durationTimeMllis +"\t\t" //+
//				 			);	
//		
//	}
	
	
	class WaitUserCommand extends OneShotBehaviour {
		// ------------------------------------------------ Get the user command

		WaitUserCommand(Agent a) {
			super(a);
			// command = WAIT;
		}

		public void action() {
			if (server == null)
				lookupServer();
			if (server == null) {
				//System.out.println("Unable to localize the server! \nOperation aborted!");
				return;
			}
			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
			msg.setLanguage(codec.getName());
			msg.setOntology(ontology.getName());
			try {
				msg.addReceiver(server);
				send(msg);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	class RegisterInDF extends OneShotBehaviour {
		// --------------------------------------------- Register in the DF for
		// the client agent
		// be able to retrieve its AID
		RegisterInDF(Agent a) {
			super(a);
		}

		public void action() {

			ServiceDescription sd = new ServiceDescription();
			sd.setType("Classifier Agent");
			sd.setName(getName());
			sd.setOwnership("Prof6802");
			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());
			dfd.addServices(sd);
			try {
				DFAgentDescription[] dfds = DFService.search(myAgent, dfd);
				if (dfds.length > 0) {
					DFService.deregister(myAgent, dfd);
				}
				DFService.register(myAgent, dfd);
				sm.Log(getLocalName() + " is ready.");
			} catch (Exception ex) {
				sm.Log("Failed registering with DF! Shutting down...");
				ex.printStackTrace();
				doDelete();
			}
		}
	}

	void lookupServer() {
		// --------------------- Search in the DF to retrieve the server AID

		ServiceDescription sd = new ServiceDescription();
		sd.setType("Manager Agent");
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.addServices(sd);
		try {
			DFAgentDescription[] dfds = DFService.search(this, dfd);
			if (dfds.length > 0) {
				server = dfds[0].getName();
				sm.Log("Localized server");
			} else
				sm.Log("\nCouldn't localize server!");
		} catch (Exception ex) {
			ex.printStackTrace();
			sm.Log("\nFailed searching int the DF!");
		}
	}
	
//	public void showMessage(String message) {
//		System.out.println("Client " + getLocalName() + ": " + message);
//	}


	public int getPercentageTrainingData() {
		return percentageTrainingData;
	}


	public void setPercentageTrainingData(int percentageTrainingData) {
		this.percentageTrainingData = percentageTrainingData;
	}


	public ClassifierInstance getClassifier() {
		return classifier;
	}


	public void setClassifier(ClassifierInstance classifier) {
		this.classifier = classifier;
	}
	
	public List getHeader()
	{
		return header;
	}
	
	public void setHeader(List header)
	{
		this.header = header;
	}

	public int getTrainingSize() {
		return TrainingSize;
	}

	public void setTrainingSize(int trainingSize) {
		TrainingSize = trainingSize;
	}	

}
