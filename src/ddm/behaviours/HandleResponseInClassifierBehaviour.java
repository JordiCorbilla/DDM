package ddm.behaviours;

import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.basic.Action;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.util.leap.List;
import ddm.agents.ClassifierAgent;
import ddm.data.DataSetGenerator;
import ddm.factory.classifiers.ClassifierInstance;
import ddm.logging.ShowMessage;
import ddm.ontology.Arff_Training_Repository;
import ddm.ontology.ClassifierOntology;
import ddm.ontology.TrainingResult;

/**
 * 
 * @author jordi Corbilla
 * behaviour to handle the response from the classifier. Once the training details are back,
 * the classifier needs to train the system.
 */
@SuppressWarnings("serial")
public class HandleResponseInClassifierBehaviour extends OneShotBehaviour {
    private ACLMessage request;
    private ClassifierAgent myAgent;
    private ShowMessage sm;
    private ClassifierInstance classifier;
	private Codec codec = new SLCodec();
	private Ontology ontology = ClassifierOntology.getInstance();	
    	
    public HandleResponseInClassifierBehaviour(ClassifierAgent a, ACLMessage request) {
       super(a);
       myAgent = a;
       this.request = request;
       this.sm = a.getSM();
       this.classifier = a.getClassifier();
    }
    
    @Override
    public void action() {

       try {
          ContentElement content = myAgent.getContentManager().extractContent(request);
          Arff_Training_Repository ca = (Arff_Training_Repository)((Action)content).getAction();
          sm.Log("Folder location: "+ ca.getName());
          myAgent.setTrainingSize(ca.getData().size());
          myAgent.setHeader(ca.getHeader());
          sm.Log("Number of header rows received from Manager: "+ Integer.toString(myAgent.getHeader().size()));		            
          List rows = ca.getData();
          sm.Log("Number of rows received from Manager: "+ Integer.toString(rows.size()));
          
          //Now that the data is here, we can compose the file and start Weka.
          DataSetGenerator.GenerateFileForClassifier(myAgent.getLocalName(), myAgent.getHeader(), rows);
          
          this.classifier.setTrainingDataFile(myAgent.getConfiguration().getApplicationPath() + myAgent.getLocalName() + "_TrainingRepository.arff");
          
          this.classifier.TrainClassifier();
          System.out.println("trained!!!");
          
          ACLMessage reply = request.createReply();
          reply.setPerformative(ACLMessage.INFORM);
          reply.setLanguage(codec.getName());
          reply.setOntology(ontology.getName());
          try {
          	TrainingResult tr = new TrainingResult();  
          	tr.setDuration(this.classifier.getDurationTrainingTimeMs());
          	tr.setName(myAgent.getLocalName());
          	tr.setType(this.classifier.type());
		            myAgent.getContentManager().fillContent(reply, new Action(request.getSender(), tr));
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