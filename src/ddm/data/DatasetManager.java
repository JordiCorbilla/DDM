// Distributed Decision making system framework 
//Copyright (c) 2014, Jordi Coll Corbilla
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

package ddm.data;

import java.util.ArrayList;
import java.util.HashMap;

import ddm.configuration.ManagerConfiguration;
import ddm.ontology.Arff_Training_Repository;
import ddm.ontology.ClassifierSettings;
import ddm.ontology.DataInstance;

/***
 * 
 * @author jordi Corbilla 
 * DatasetManager creates the logic partition of the data
 *         from the ARFF file
 * 
 */
@SuppressWarnings("serial")
public class DatasetManager implements java.io.Serializable {

	private ArrayList<DataInstance> datasetsData;
	private ArrayList<Arff_Training_Repository> datasetsTraining;
	private ArffFile arffFile;
	private PartitionList partitionList;
	private String arffFileName;

	public DatasetManager(ManagerConfiguration conf,
			HashMap<String, ClassifierSettings> ClassifierSettings,
			int NumberClassifiers) {
		setArffFileName(conf.getArffDataSetLocation());
		this.datasetsData = new ArrayList<DataInstance>();
		this.datasetsTraining = new ArrayList<Arff_Training_Repository>();
		this.arffFile = new ArffFile(conf.getArffDataSetLocation());
		this.partitionList = new PartitionList(arffFile.DataSize(),
				conf.getPercentageTrainingData(), ClassifierSettings,
				NumberClassifiers);
		FillDatasets();
	}

	private void FillDatasets() {
		int StartIndex = 0;
		int EndIndex = 0;
		for (int i = 0; i < partitionList.NumberOfPartitions() - 1; i++) {
			Arff_Training_Repository arffTraining = new Arff_Training_Repository();
			arffTraining.setName(getArffFileName());

			jade.util.leap.List headerTraining = new jade.util.leap.ArrayList();
			for (int j = 0; j < arffFile.HeaderSize(); j++)
				headerTraining.add(arffFile.Header().get(j));
			arffTraining.setHeader(headerTraining);

			jade.util.leap.List training = new jade.util.leap.ArrayList();
			StartIndex = partitionList.GetStartIndex(i);
			EndIndex = partitionList.GetEndIndex(i);
			for (int j = StartIndex; j < EndIndex; j++)
				training.add(arffFile.Data().get(j));
			arffTraining.setData(training);
			datasetsTraining.add(arffTraining);
		}
		int j = partitionList.NumberOfPartitions() - 1;
		StartIndex = partitionList.GetStartIndex(j);
		EndIndex = partitionList.GetEndIndex(j);
		for (int k = StartIndex; k < EndIndex; k++) {
			DataInstance dataInstance = new DataInstance();
			dataInstance.setValue(arffFile.Data().get(k));
			datasetsData.add(dataInstance);
		}
	}

	public ArrayList<DataInstance> getDatasetsData() {
		return datasetsData;
	}

	public ArrayList<Arff_Training_Repository> getDatasetsTraining() {
		return datasetsTraining;
	}

	public ArffFile getArffFile() {
		return arffFile;
	}

	public PartitionList getPartitionList() {
		return partitionList;
	}

	public String getArffFileName() {
		return arffFileName;
	}

	public void setArffFileName(String arffFileName) {
		this.arffFileName = arffFileName;
	}
}
