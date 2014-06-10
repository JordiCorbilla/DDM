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

package ddm.IntegrationTests;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ddm.configuration.ManagerConfiguration;
import ddm.decision.DecisionMaker;
import ddm.decision.DecisionType;
import ddm.ontology.ClassificationResult;
import ddm.ontology.DataInstance;

public class TestDecisionMaker {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void TestWritingfile() {
		// ManagerConfiguration conf = ManagerConfiguration.getInstance();
		// DecisionMaker decisionMaker = new DecisionMaker(conf);
		//
		// DataInstance di = new DataInstance();
		// di.setValue("test");
		// ClassificationResult cr1 = new ClassificationResult();
		// HashMap<String, ClassificationResult> decisionResult = new
		// HashMap<String, ClassificationResult>();
		// decisionResult.put(cr1.getName(), cr1);
		// decisionMaker.Make(di, decisionResult, 3);
		//
		// DataInstance di2 = new DataInstance();
		// di2.setValue("test2");
		// ClassificationResult cr2 = new ClassificationResult();
		// HashMap<String, ClassificationResult> decisionResult2 = new
		// HashMap<String, ClassificationResult>();
		// decisionResult.put(cr2.getName(), cr2);
		// decisionMaker.Make(di2, decisionResult2, 3);
		//
		// decisionMaker.CloseFile();
	}

	@Test
	public void TestDecisionWeightedArithmeticMean() {
		// Example decision
		// *****Classifier2 weka.classifiers.trees.J48 0ms NumCorrect: 0 Value:O
		// TrainingSize:42 Total:70 Val:1.0 Pred:0.0
		// *****Classifier1 weka.classifiers.lazy.IBk 0ms NumCorrect: 1 Value:O
		// TrainingSize:57 Total:70 Val:1.0 Pred:1.0
		// *****Classifier3 weka.classifiers.lazy.IBk 0ms NumCorrect: 1 Value:O
		// TrainingSize:39 Total:70 Val:1.0 Pred:1.0
		ManagerConfiguration conf = ManagerConfiguration.getInstance();
		DecisionMaker decisionMaker = new DecisionMaker(conf);

		ClassificationResult cr1 = new ClassificationResult();
		cr1.setName("Classifier2");
		cr1.setTrainingSize(42);
		cr1.setPredictedInstanceValue("N");
		cr1.setInstancePredictedValue(0.0);

		ClassificationResult cr2 = new ClassificationResult();
		cr2.setName("Classifier1");
		cr2.setTrainingSize(57);
		cr2.setPredictedInstanceValue("O");
		cr2.setInstancePredictedValue(1.0);

		ClassificationResult cr3 = new ClassificationResult();
		cr3.setName("Classifier3");
		cr3.setTrainingSize(39);
		cr3.setPredictedInstanceValue("O");
		cr3.setInstancePredictedValue(1.0);

		HashMap<String, ClassificationResult> decisionResult = new HashMap<String, ClassificationResult>();
		decisionResult.put(cr1.getName(), cr1);
		decisionResult.put(cr2.getName(), cr2);
		decisionResult.put(cr3.getName(), cr3);

		DataInstance di = new DataInstance();
		di.setValue("-1,0.67,0,0,1,0,0.6,0,0.5,O");

		decisionMaker.Make(DecisionType.WeightedArithmeticMean, di,
				decisionResult, 70);
		decisionMaker.CloseFile();
	}

	@Test
	public void TestDecisionOrderedWeightedAggregation() {
		// Example decision
		// *****Classifier2 weka.classifiers.trees.J48 0ms NumCorrect: 0 Value:O
		// TrainingSize:42 Total:70 Val:1.0 Pred:0.0
		// *****Classifier1 weka.classifiers.lazy.IBk 0ms NumCorrect: 1 Value:O
		// TrainingSize:57 Total:70 Val:1.0 Pred:1.0
		// *****Classifier3 weka.classifiers.lazy.IBk 0ms NumCorrect: 1 Value:O
		// TrainingSize:39 Total:70 Val:1.0 Pred:1.0
		ManagerConfiguration conf = ManagerConfiguration.getInstance();
		DecisionMaker decisionMaker = new DecisionMaker(conf);

		ClassificationResult cr1 = new ClassificationResult();
		cr1.setName("Classifier2");
		cr1.setTrainingSize(42);
		cr1.setPredictedInstanceValue("N");
		cr1.setInstancePredictedValue(0.0);

		ClassificationResult cr2 = new ClassificationResult();
		cr2.setName("Classifier1");
		cr2.setTrainingSize(57);
		cr2.setPredictedInstanceValue("O");
		cr2.setInstancePredictedValue(1.0);

		ClassificationResult cr3 = new ClassificationResult();
		cr3.setName("Classifier3");
		cr3.setTrainingSize(39);
		cr3.setPredictedInstanceValue("O");
		cr3.setInstancePredictedValue(1.0);

		HashMap<String, ClassificationResult> decisionResult = new HashMap<String, ClassificationResult>();
		decisionResult.put(cr1.getName(), cr1);
		decisionResult.put(cr2.getName(), cr2);
		decisionResult.put(cr3.getName(), cr3);

		DataInstance di = new DataInstance();
		di.setValue("-1,0.67,0,0,1,0,0.6,0,0.5,O");

		decisionMaker.Make(DecisionType.OrderedWeightedAggregation, di,
				decisionResult, 70);
		decisionMaker.CloseFile();
	}
}
