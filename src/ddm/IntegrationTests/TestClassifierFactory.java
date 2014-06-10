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

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ddm.factory.classifiers.ClassifierInstance;
import ddm.factory.classifiers.ClassifierInstanceFactory;
import ddm.factory.classifiers.ClassifierType;

public class TestClassifierFactory {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testJ48Classifier() {
		System.out.println("*****************Testing J48Classifier\n");
		ClassifierInstance instance = ClassifierInstanceFactory
				.buildClassifier(ClassifierType.J48DecisionTree);
		instance.setTrainingDataFile("C:/Users/jordi coll/workspace/TFG-DistributedDecisionJade/src/ddm/IntegrationTests/ClassifierTraining_repository.arff");
		instance.TrainClassifier();
		System.out.println("Training time duration: "
				+ instance.getDurationTrainingTimeMs() + "ms");
		System.out.println("Training size: " + instance.getTrainingSetSize());
		assertTrue(instance.getTrainingSetSize() == 20);

		instance.setDataFile("C:/Users/jordi coll/workspace/TFG-DistributedDecisionJade/src/ddm/IntegrationTests/ClassifierData_repository1.arff");
		instance.ClassifyInstances();
		System.out.println("\nTest 1");
		System.out.println("Classification duration: "
				+ instance.getDurationTimeMs() + "ms");
		System.out.println("Data size: " + instance.getDataSetSize());
		System.out.println("Correct : " + instance.getCorrectIntances());
		System.out.println("Correct Percentage: "
				+ instance.percentageFormatted());
		System.out.println("Intance value: " + instance.getInstanceValue());
		assertTrue(instance.getDataSetSize() == 1);
		assertTrue(instance.getCorrectIntances() == 1);
		assertTrue(instance.getInstanceValue().equals("N"));
		assertTrue(instance.getPercentage() == 100.00);

		instance.setDataFile("C:/Users/jordi coll/workspace/TFG-DistributedDecisionJade/src/ddm/IntegrationTests/ClassifierData_repository2.arff");
		instance.ClassifyInstances();
		System.out.println("\nTest 2");
		System.out.println("Classification duration: "
				+ instance.getDurationTimeMs() + "ms");
		System.out.println("Data size: " + instance.getDataSetSize());
		System.out.println("Correct : " + instance.getCorrectIntances());
		System.out.println("Correct Percentage: "
				+ instance.percentageFormatted());
		System.out.println("Intance value: " + instance.getInstanceValue());
		assertTrue(instance.getDataSetSize() == 1);
		assertTrue(instance.getCorrectIntances() == 1);
		assertTrue(instance.getInstanceValue().equals("N"));
		assertTrue(instance.getPercentage() == 100.00);

		instance.setDataFile("C:/Users/jordi coll/workspace/TFG-DistributedDecisionJade/src/ddm/IntegrationTests/ClassifierData_repository3.arff");
		instance.ClassifyInstances();
		System.out.println("\nTest 3");
		System.out.println("Classification duration: "
				+ instance.getDurationTimeMs() + "ms");
		System.out.println("Data size: " + instance.getDataSetSize());
		System.out.println("Correct : " + instance.getCorrectIntances());
		System.out.println("Correct Percentage: "
				+ instance.percentageFormatted());
		System.out.println("Intance value: " + instance.getInstanceValue());
		assertTrue(instance.getDataSetSize() == 1);
		assertTrue(instance.getCorrectIntances() == 1);
		assertTrue(instance.getInstanceValue().equals("N"));
		assertTrue(instance.getPercentage() == 100.00);

		instance.setDataFile("C:/Users/jordi coll/workspace/TFG-DistributedDecisionJade/src/ddm/IntegrationTests/ClassifierData_repository4.arff");
		instance.ClassifyInstances();
		System.out.println("\nTest 4");
		System.out.println("Classification duration: "
				+ instance.getDurationTimeMs() + "ms");
		System.out.println("Data size: " + instance.getDataSetSize());
		System.out.println("Correct : " + instance.getCorrectIntances());
		System.out.println("Correct Percentage: "
				+ instance.percentageFormatted());
		System.out.println("Intance value: " + instance.getInstanceValue());
		assertTrue(instance.getDataSetSize() == 1);
		assertTrue(instance.getCorrectIntances() == 0);
		assertTrue(instance.getInstanceValue().equals("O"));
		assertTrue(instance.getPercentage() == 0.00);

		instance.setDataFile("C:/Users/jordi coll/workspace/TFG-DistributedDecisionJade/src/ddm/IntegrationTests/ClassifierData_repository5.arff");
		instance.ClassifyInstances();
		System.out.println("\nTest 5");
		System.out.println("Classification duration: "
				+ instance.getDurationTimeMs() + "ms");
		System.out.println("Data size: " + instance.getDataSetSize());
		System.out.println("Correct : " + instance.getCorrectIntances());
		System.out.println("Correct Percentage: "
				+ instance.percentageFormatted());
		System.out.println("Intance value: " + instance.getInstanceValue());
		assertTrue(instance.getDataSetSize() == 1);
		assertTrue(instance.getCorrectIntances() == 1);
		assertTrue(instance.getInstanceValue().equals("N"));
		assertTrue(instance.getPercentage() == 100.00);
	}

	@Test
	public void testMLPClassifier() {
		System.out.println("*****************Testing MLPClassifier\n");
		ClassifierInstance instance = ClassifierInstanceFactory
				.buildClassifier(ClassifierType.NeuronalNetwork);
		instance.setTrainingDataFile("C:/Users/jordi coll/workspace/TFG-DistributedDecisionJade/src/ddm/IntegrationTests/ClassifierTraining_repository.arff");
		instance.TrainClassifier();
		System.out.println("Training time duration: "
				+ instance.getDurationTrainingTimeMs() + "ms");
		System.out.println("Training size: " + instance.getTrainingSetSize());
		assertTrue(instance.getTrainingSetSize() == 20);

		instance.setDataFile("C:/Users/jordi coll/workspace/TFG-DistributedDecisionJade/src/ddm/IntegrationTests/ClassifierData_repository1.arff");
		instance.ClassifyInstances();
		System.out.println("\nTest 1");
		System.out.println("Classification duration: "
				+ instance.getDurationTimeMs() + "ms");
		System.out.println("Data size: " + instance.getDataSetSize());
		System.out.println("Correct : " + instance.getCorrectIntances());
		System.out.println("Correct Percentage: "
				+ instance.percentageFormatted());
		System.out.println("Intance value: " + instance.getInstanceValue());
		assertTrue(instance.getDataSetSize() == 1);
		assertTrue(instance.getCorrectIntances() == 1);
		assertTrue(instance.getInstanceValue().equals("N"));
		assertTrue(instance.getPercentage() == 100.00);

		instance.setDataFile("C:/Users/jordi coll/workspace/TFG-DistributedDecisionJade/src/ddm/IntegrationTests/ClassifierData_repository2.arff");
		instance.ClassifyInstances();
		System.out.println("\nTest 2");
		System.out.println("Classification duration: "
				+ instance.getDurationTimeMs() + "ms");
		System.out.println("Data size: " + instance.getDataSetSize());
		System.out.println("Correct : " + instance.getCorrectIntances());
		System.out.println("Correct Percentage: "
				+ instance.percentageFormatted());
		System.out.println("Intance value: " + instance.getInstanceValue());
		assertTrue(instance.getDataSetSize() == 1);
		assertTrue(instance.getCorrectIntances() == 1);
		assertTrue(instance.getInstanceValue().equals("N"));
		assertTrue(instance.getPercentage() == 100.00);

		instance.setDataFile("C:/Users/jordi coll/workspace/TFG-DistributedDecisionJade/src/ddm/IntegrationTests/ClassifierData_repository3.arff");
		instance.ClassifyInstances();
		System.out.println("\nTest 3");
		System.out.println("Classification duration: "
				+ instance.getDurationTimeMs() + "ms");
		System.out.println("Data size: " + instance.getDataSetSize());
		System.out.println("Correct : " + instance.getCorrectIntances());
		System.out.println("Correct Percentage: "
				+ instance.percentageFormatted());
		System.out.println("Intance value: " + instance.getInstanceValue());
		assertTrue(instance.getDataSetSize() == 1);
		assertTrue(instance.getCorrectIntances() == 1);
		assertTrue(instance.getInstanceValue().equals("N"));
		assertTrue(instance.getPercentage() == 100.00);

		instance.setDataFile("C:/Users/jordi coll/workspace/TFG-DistributedDecisionJade/src/ddm/IntegrationTests/ClassifierData_repository4.arff");
		instance.ClassifyInstances();
		System.out.println("\nTest 4");
		System.out.println("Classification duration: "
				+ instance.getDurationTimeMs() + "ms");
		System.out.println("Data size: " + instance.getDataSetSize());
		System.out.println("Correct : " + instance.getCorrectIntances());
		System.out.println("Correct Percentage: "
				+ instance.percentageFormatted());
		System.out.println("Intance value: " + instance.getInstanceValue());
		assertTrue(instance.getDataSetSize() == 1);
		assertTrue(instance.getCorrectIntances() == 0);
		assertTrue(instance.getInstanceValue().equals("O"));
		assertTrue(instance.getPercentage() == 0.00);

		instance.setDataFile("C:/Users/jordi coll/workspace/TFG-DistributedDecisionJade/src/ddm/IntegrationTests/ClassifierData_repository5.arff");
		instance.ClassifyInstances();
		System.out.println("\nTest 5");
		System.out.println("Classification duration: "
				+ instance.getDurationTimeMs() + "ms");
		System.out.println("Data size: " + instance.getDataSetSize());
		System.out.println("Correct : " + instance.getCorrectIntances());
		System.out.println("Correct Percentage: "
				+ instance.percentageFormatted());
		System.out.println("Intance value: " + instance.getInstanceValue());
		assertTrue(instance.getDataSetSize() == 1);
		assertTrue(instance.getCorrectIntances() == 1);
		assertTrue(instance.getInstanceValue().equals("N"));
		assertTrue(instance.getPercentage() == 100.00);
	}

	@Test
	public void testIBKClassifier() {
		System.out.println("*****************Testing MLPClassifier\n");
		ClassifierInstance instance = ClassifierInstanceFactory
				.buildClassifier(ClassifierType.KNearestNeighbour);
		instance.setTrainingDataFile("C:/Users/jordi coll/workspace/TFG-DistributedDecisionJade/src/ddm/IntegrationTests/ClassifierTraining_repository.arff");
		instance.TrainClassifier();
		System.out.println("Training time duration: "
				+ instance.getDurationTrainingTimeMs() + "ms");
		System.out.println("Training size: " + instance.getTrainingSetSize());
		assertTrue(instance.getTrainingSetSize() == 20);

		instance.setDataFile("C:/Users/jordi coll/workspace/TFG-DistributedDecisionJade/src/ddm/IntegrationTests/ClassifierData_repository1.arff");
		instance.ClassifyInstances();
		System.out.println("\nTest 1");
		System.out.println("Classification duration: "
				+ instance.getDurationTimeMs() + "ms");
		System.out.println("Data size: " + instance.getDataSetSize());
		System.out.println("Correct : " + instance.getCorrectIntances());
		System.out.println("Correct Percentage: "
				+ instance.percentageFormatted());
		System.out.println("Intance value: " + instance.getInstanceValue());
		assertTrue(instance.getDataSetSize() == 1);
		assertTrue(instance.getCorrectIntances() == 1);
		assertTrue(instance.getInstanceValue().equals("N"));
		assertTrue(instance.getPercentage() == 100.00);

		instance.setDataFile("C:/Users/jordi coll/workspace/TFG-DistributedDecisionJade/src/ddm/IntegrationTests/ClassifierData_repository2.arff");
		instance.ClassifyInstances();
		System.out.println("\nTest 2");
		System.out.println("Classification duration: "
				+ instance.getDurationTimeMs() + "ms");
		System.out.println("Data size: " + instance.getDataSetSize());
		System.out.println("Correct : " + instance.getCorrectIntances());
		System.out.println("Correct Percentage: "
				+ instance.percentageFormatted());
		System.out.println("Intance value: " + instance.getInstanceValue());
		assertTrue(instance.getDataSetSize() == 1);
		assertTrue(instance.getCorrectIntances() == 1);
		assertTrue(instance.getInstanceValue().equals("N"));
		assertTrue(instance.getPercentage() == 100.00);

		instance.setDataFile("C:/Users/jordi coll/workspace/TFG-DistributedDecisionJade/src/ddm/IntegrationTests/ClassifierData_repository3.arff");
		instance.ClassifyInstances();
		System.out.println("\nTest 3");
		System.out.println("Classification duration: "
				+ instance.getDurationTimeMs() + "ms");
		System.out.println("Data size: " + instance.getDataSetSize());
		System.out.println("Correct : " + instance.getCorrectIntances());
		System.out.println("Correct Percentage: "
				+ instance.percentageFormatted());
		System.out.println("Intance value: " + instance.getInstanceValue());
		assertTrue(instance.getDataSetSize() == 1);
		assertTrue(instance.getCorrectIntances() == 1);
		assertTrue(instance.getInstanceValue().equals("N"));
		assertTrue(instance.getPercentage() == 100.00);

		instance.setDataFile("C:/Users/jordi coll/workspace/TFG-DistributedDecisionJade/src/ddm/IntegrationTests/ClassifierData_repository4.arff");
		instance.ClassifyInstances();
		System.out.println("\nTest 4");
		System.out.println("Classification duration: "
				+ instance.getDurationTimeMs() + "ms");
		System.out.println("Data size: " + instance.getDataSetSize());
		System.out.println("Correct : " + instance.getCorrectIntances());
		System.out.println("Correct Percentage: "
				+ instance.percentageFormatted());
		System.out.println("Intance value: " + instance.getInstanceValue());
		assertTrue(instance.getDataSetSize() == 1);
		assertTrue(instance.getCorrectIntances() == 0);
		assertTrue(instance.getInstanceValue().equals("O"));
		assertTrue(instance.getPercentage() == 0.00);

		instance.setDataFile("C:/Users/jordi coll/workspace/TFG-DistributedDecisionJade/src/ddm/IntegrationTests/ClassifierData_repository5.arff");
		instance.ClassifyInstances();
		System.out.println("\nTest 5");
		System.out.println("Classification duration: "
				+ instance.getDurationTimeMs() + "ms");
		System.out.println("Data size: " + instance.getDataSetSize());
		System.out.println("Correct : " + instance.getCorrectIntances());
		System.out.println("Correct Percentage: "
				+ instance.percentageFormatted());
		System.out.println("Intance value: " + instance.getInstanceValue());
		assertTrue(instance.getDataSetSize() == 1);
		assertTrue(instance.getCorrectIntances() == 1);
		assertTrue(instance.getInstanceValue().equals("N"));
		assertTrue(instance.getPercentage() == 100.00);
	}
}
