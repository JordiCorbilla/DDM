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

package ddm.factory.classifiers;

/**
 * 
 * @author jordi Corbilla
 * Class that executes a KNearest Neighbour algorithm
 */
public class KNearestNeighbourClassifier extends ClassifierInstance {

	private weka.classifiers.lazy.IBk KNearestNeighbours;

	/**
	 * Method that trains the classifier
	 */	
	@Override
	public void TrainClassifier() {
		// Load Training data
		weka.core.Instances trainingInstances = null;
		try {
			trainingInstances = new weka.core.Instances(new java.io.FileReader(
					getTrainingDataFile()));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		if (trainingInstances.classIndex() == -1)
			trainingInstances
					.setClassIndex(trainingInstances.numAttributes() - 1);

		setTrainingSetSize(trainingInstances.numInstances());
		long startTimeMillis = System.currentTimeMillis();

		setKNearestNeighbours(new weka.classifiers.lazy.IBk());
		try {
			getKNearestNeighbours().buildClassifier(trainingInstances);
		} catch (Exception e) {
			e.printStackTrace();
		}

		long finishTimeMillis = System.currentTimeMillis();
		setDurationTrainingTimeMs(finishTimeMillis - startTimeMillis);
	}

	/**
	 * Method that classifies an individual instance
	 */	
	@Override
	public void ClassifyInstances() {
		// Load data to classify
		weka.core.Instances dataInstances = null;
		try {
			dataInstances = new weka.core.Instances(new java.io.FileReader(
					getDataFile()));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		if (dataInstances.classIndex() == -1)
			dataInstances.setClassIndex(dataInstances.numAttributes() - 1);

		setInstanceValue(dataInstances.firstInstance().stringValue(
				dataInstances.numAttributes() - 1));

		setDataSetSize(dataInstances.numInstances());
		long startTimeMillis = System.currentTimeMillis();

		int numCorrect = 0;

		for (int i = 0; i < getDataSetSize(); i++) {
			weka.core.Instance currentInst = dataInstances.instance(i);

			double predictedClass = 0;
			try {
				predictedClass = getKNearestNeighbours().classifyInstance(
						currentInst);
			} catch (Exception e) {
				e.printStackTrace();
			}

			setInstanceClassification(dataInstances.instance(i).classValue());
			setInstancePredictedClass(predictedClass);
			setPredictedInstanceValue(dataInstances.classAttribute().value(
					(int) predictedClass));
			if (predictedClass == dataInstances.instance(i).classValue()) {
				numCorrect++;
			}
		}

		long finishTimeMillis = System.currentTimeMillis();
		setDurationTimeMs(finishTimeMillis - startTimeMillis);
		setPercentage((double) numCorrect / (double) getDataSetSize() * 100.0);
		setCorrectIntances(numCorrect);
	}

	/**
	 * Returns the classifier
	 * @return IBk
	 */	
	public weka.classifiers.lazy.IBk getKNearestNeighbours() {
		return KNearestNeighbours;
	}

	/**
	 * Sets the classifier
	 * @param KNearestNeighbours
	 */	
	public void setKNearestNeighbours(
			weka.classifiers.lazy.IBk KNearestNeighbours) {
		this.KNearestNeighbours = KNearestNeighbours;
	}

	/**
	 * Returns the description of the weka algorithm
	 */	
	@Override
	public String type() {
		return "weka.classifiers.lazy.IBk";
	}
}
