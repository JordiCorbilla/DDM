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
 * Abstract instance of a classifier.
 * This class defines a generic class that will contain a Classifier algorithm.
 */
public abstract class ClassifierInstance {

	private int correctIntances;
	private int dataSetSize;
	private int trainingSetSize;
	private double percentage;
	private long durationTrainingTimeMs;
	private long durationTimeMs;
	private String TrainingDataFile;
	private String DataFile;
	private String instanceValue;
	private String predictedInstanceValue;
	private double InstanceClassification;
	private double InstancePredictedClass;

	public ClassifierInstance() {

	}

	/**
	 * Method to train the classifier
	 */
	public abstract void TrainClassifier();

	/**
	 * Method to classify a particular instance
	 */
	public abstract void ClassifyInstances();

	/**
	 * Method that returns the type of algorithm used.
	 * @return string
	 */
	public abstract String type();

	/**
	 * Number of correct instances that the classifier returns
	 * @return int
	 */
	public int getCorrectIntances() {
		return correctIntances;
	}

	/**
	 * Set the number of correct instances
	 * @param correctIntances
	 */
	public void setCorrectIntances(int correctIntances) {
		this.correctIntances = correctIntances;
	}

	/**
	 * DataSet size
	 * @return int
	 */
	public int getDataSetSize() {
		return dataSetSize;
	}

	/**
	 * Set the DataSet size
	 * @param testSetSize
	 */
	public void setDataSetSize(int testSetSize) {
		this.dataSetSize = testSetSize;
	}

	/**
	 * Get the training set size
	 * @return int
	 */
	public int getTrainingSetSize() {
		return trainingSetSize;
	}

	/**
	 * Set training set size
	 * @param trainingSetSize
	 */
	public void setTrainingSetSize(int trainingSetSize) {
		this.trainingSetSize = trainingSetSize;
	}

	/**
	 * Percentage of the data
	 * @return double
	 */
	public double getPercentage() {
		return percentage;
	}

	/**
	 * Set the percentage of the data
	 * @param percentage
	 */
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	/**
	 * Duration in ms of the execution of the classification
	 * @return
	 */
	public long getDurationTimeMs() {
		return durationTimeMs;
	}
	
	/**
	 * Set the time of the execution for the classification
	 * @param durationTimeMs
	 */
	public void setDurationTimeMs(long durationTimeMs) {
		this.durationTimeMs = durationTimeMs;
	}

	/**
	 * Get the training data file
	 * @return string
	 */
	public String getTrainingDataFile() {
		return TrainingDataFile;
	}

	/**
	 * Set the training data file
	 * @param trainingDataFile
	 */
	public void setTrainingDataFile(String trainingDataFile) {
		TrainingDataFile = trainingDataFile;
	}

	/**
	 * Get the data file
	 * @return string
	 */
	public String getDataFile() {
		return DataFile;
	}

	/**
	 * Set the data file
	 * @param dataFile
	 */	
	public void setDataFile(String dataFile) {
		DataFile = dataFile;
	}

	/**
	 * Get the duration of the training in ms
	 * @return
	 */
	public long getDurationTrainingTimeMs() {
		return durationTrainingTimeMs;
	}

	/**
	 * Set the duration of the training in ms
	 * @param durationTrainingTimeMs
	 */
	public void setDurationTrainingTimeMs(long durationTrainingTimeMs) {
		this.durationTrainingTimeMs = durationTrainingTimeMs;
	}

	/**
	 * Get an additional value formatted string for the percentage to be stored in the file or displayed.
	 * @return String
	 */
	public String percentageFormatted() {
		java.text.DecimalFormat percentageFormatter = new java.text.DecimalFormat(
				"#0.00");
		return percentageFormatter.format(this.percentage);
	}

	/**
	 * Get the value of the processed instance from weka
	 * @return String
	 */
	public String getInstanceValue() {
		return instanceValue;
	}

	/**
	 * Set the value of the instance returned from weka
	 * @param instanceValue
	 */
	public void setInstanceValue(String instanceValue) {
		this.instanceValue = instanceValue;
	}

	/**
	 * Get the value of the classification for the particular instance
	 * @return
	 */
	public double getInstanceClassification() {
		return InstanceClassification;
	}

	/**
	 * Set the value for the classification instance
	 * @param instanceClassification
	 */
	public void setInstanceClassification(double instanceClassification) {
		InstanceClassification = instanceClassification;
	}

	/**
	 * Get the value for the predicted class from weka
	 * @return
	 */
	public double getInstancePredictedClass() {
		return InstancePredictedClass;
	}

	/**
	 * Set the value for the predicted class from weka
	 * @param instancePredictedClass
	 */
	public void setInstancePredictedClass(double instancePredictedClass) {
		InstancePredictedClass = instancePredictedClass;
	}

	/**
	 * Get predicted instance image from weka
	 * @return
	 */
	public String getPredictedInstanceValue() {
		return predictedInstanceValue;
	}

	/**
	 * Set the predicted instance image from weka
	 * @param predictedInstanceValue
	 */
	public void setPredictedInstanceValue(String predictedInstanceValue) {
		this.predictedInstanceValue = predictedInstanceValue;
	}
}
