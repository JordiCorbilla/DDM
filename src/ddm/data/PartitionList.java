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

import ddm.ontology.ClassifierSettings;

/***
 * 
 * @author jordi coll 
 * Given a data set, percentage of training and number of classifiers
 * this class will generate the partition list for the Data set manager
 * 
 */

@SuppressWarnings("serial")
public class PartitionList implements java.io.Serializable {
	private int trainingSize;
	private int dataSize;
	private ArrayList<Integer> partitions;
	private ArrayList<PartitionMap> partitionMap;
	
	public PartitionList(int DataSize, int PercentageData, HashMap<String, ClassifierSettings> ClassifierSettings, int NumberClassifiers)
	{
		partitions = new ArrayList<Integer>();
		partitionMap = new ArrayList<PartitionMap>();
		CalculatePartitions(DataSize, PercentageData, ClassifierSettings, NumberClassifiers);
		CalculatePartitionMap();
	}

	private void CalculatePartitions(int DataSize, int PercentageData, HashMap<String, ClassifierSettings> ClassifierSettings, int NumberClassifiers)
	{
		//Get the basic chunk of data destined for training
		float percentage = PercentageData / (float)100;
		this.trainingSize = (int)((float)DataSize * percentage);

		this.dataSize = DataSize;
		
		int newTrainingSize = getTrainingSize();
		
		for (int i=1; i<= NumberClassifiers; i++)
		{
			int percentageClassifier = ClassifierSettings.get("Classifier" + i).getPercentageTrainingData();
			float percentageClass = percentageClassifier / (float)100;
			int sizeTrainingClassifier = (int)((float)newTrainingSize * percentageClass);
			partitions.add(sizeTrainingClassifier);
		}
		partitions.add(getDataSize()-getTrainingSize());
	}
	
	private void CalculatePartitionMap()
	{
		for (int i=0;i<NumberOfPartitions()-1;i++)
		{
			partitionMap.add(new PartitionMap(0, partitions.get(i)-1));
		}
		
		int startIndex = getTrainingSize();
		int endIndex = getDataSize()-1;
		partitionMap.add(new PartitionMap(startIndex, endIndex));
	}
	
	public ArrayList<Integer> getPartitions() {
		return partitions;
	}

	public int getTrainingSize() {
		return trainingSize;
	}
	
	public int getDataSize() {
		return dataSize;
	}	
	
	public int NumberOfPartitions(){
		return partitions.size();
	}

	public ArrayList<PartitionMap> getPartitionMap() {
		return partitionMap;
	}
	
	public int GetStartIndex(int index)
	{
		return getPartitionMap().get(index).getStartIndex();
	}
	
	public int GetEndIndex(int index)
	{
		return getPartitionMap().get(index).getEndIndex();
	}	
}
