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

	public abstract void TrainClassifier();

	public abstract void ClassifyInstances();

	public abstract String type();

	public int getCorrectIntances() {
		return correctIntances;
	}

	public void setCorrectIntances(int correctIntances) {
		this.correctIntances = correctIntances;
	}

	public int getDataSetSize() {
		return dataSetSize;
	}

	public void setDataSetSize(int testSetSize) {
		this.dataSetSize = testSetSize;
	}

	public int getTrainingSetSize() {
		return trainingSetSize;
	}

	public void setTrainingSetSize(int trainingSetSize) {
		this.trainingSetSize = trainingSetSize;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public long getDurationTimeMs() {
		return durationTimeMs;
	}

	public void setDurationTimeMs(long durationTimeMs) {
		this.durationTimeMs = durationTimeMs;
	}

	public String getTrainingDataFile() {
		return TrainingDataFile;
	}

	public void setTrainingDataFile(String trainingDataFile) {
		TrainingDataFile = trainingDataFile;
	}

	public String getDataFile() {
		return DataFile;
	}

	public void setDataFile(String dataFile) {
		DataFile = dataFile;
	}

	public long getDurationTrainingTimeMs() {
		return durationTrainingTimeMs;
	}

	public void setDurationTrainingTimeMs(long durationTrainingTimeMs) {
		this.durationTrainingTimeMs = durationTrainingTimeMs;
	}

	public String percentageFormatted() {
		java.text.DecimalFormat percentageFormatter = new java.text.DecimalFormat(
				"#0.00");
		return percentageFormatter.format(this.percentage);
	}

	public String getInstanceValue() {
		return instanceValue;
	}

	public void setInstanceValue(String instanceValue) {
		this.instanceValue = instanceValue;
	}

	public double getInstanceClassification() {
		return InstanceClassification;
	}

	public void setInstanceClassification(double instanceClassification) {
		InstanceClassification = instanceClassification;
	}

	public double getInstancePredictedClass() {
		return InstancePredictedClass;
	}

	public void setInstancePredictedClass(double instancePredictedClass) {
		InstancePredictedClass = instancePredictedClass;
	}

	public String getPredictedInstanceValue() {
		return predictedInstanceValue;
	}

	public void setPredictedInstanceValue(String predictedInstanceValue) {
		this.predictedInstanceValue = predictedInstanceValue;
	}
}
