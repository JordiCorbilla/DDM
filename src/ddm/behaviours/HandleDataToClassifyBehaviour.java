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
import ddm.data.DataSetGenerator;
import ddm.factory.classifiers.ClassifierInstance;
import ddm.ontology.ClassificationResult;
import ddm.ontology.ClassifierOntology;
import ddm.ontology.DataInstance;

/**
 * 
 * @author jordi Corbilla
 * Behaviour to handle the data the classifier needs to process.
 * Once the agent has processed the data, it sends the result back to the manager.
 */
@SuppressWarnings("serial")
public class HandleDataToClassifyBehaviour extends OneShotBehaviour {

	private ClassifierAgent myAgent;
	private ACLMessage request;
	private ClassifierInstance classifier;
	private Codec codec = new SLCodec();
	private Ontology ontology = ClassifierOntology.getInstance();	
	
	public HandleDataToClassifyBehaviour(ClassifierAgent a, ACLMessage request) {
		super(a);
		this.myAgent = a;
		this.request = request;
		this.classifier = a.getClassifier();
	}
	
	@Override
	public void action() {
		try {
            ContentElement content = myAgent.getContentManager().extractContent(request);
            DataInstance di = (DataInstance)((Action)content).getAction();
    
            DataSetGenerator.GenerateDataFileForClassifier(myAgent.getLocalName(), myAgent.getHeader(), di);
            
			this.classifier.setDataFile(myAgent.getConfiguration()
					.getApplicationPath() + myAgent.getLocalName() + "_DataRepository.arff");
            
            this.classifier.ClassifyInstances();
            
            ACLMessage reply = request.createReply();
            reply.setPerformative(ACLMessage.PROPOSE);
            reply.setLanguage(codec.getName());
            reply.setOntology(ontology.getName());
            try {
            	ClassificationResult cr = new ClassificationResult();  
            	cr.setDuration(classifier.getDurationTimeMs());
            	cr.setName(myAgent.getLocalName());
            	cr.setType(this.classifier.type());
            	cr.setNumCorrect(this.classifier.getCorrectIntances());
            	cr.setPercentage(this.classifier.getPercentage());
            	cr.setTrainingSize(myAgent.getTrainingSize());
            	cr.setInstanceValue(this.classifier.getInstanceValue());
            	cr.setPredictedInstanceValue(this.classifier.getPredictedInstanceValue());
            	cr.setInstanceClassification(this.classifier.getInstanceClassification());
            	cr.setInstancePredictedValue(this.classifier.getInstancePredictedClass());
		        myAgent.getContentManager().fillContent(reply, new Action(request.getSender(), cr));
		        reply.addReceiver(request.getSender());
		        myAgent.send(reply);
		       }
		      	catch (Exception ex) { ex.printStackTrace(); 
		      }
         }
         catch(Exception ex) { ex.printStackTrace(); }
	}
	
}