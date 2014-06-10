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

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ddm.configuration.ManagerConfiguration;
import ddm.data.DatasetManager;
import ddm.data.PartitionList;
import ddm.ontology.ClassifierSettings;

public class TestDatasetManager {

	private ManagerConfiguration conf = null;
	private HashMap<String, ClassifierSettings> ClassifierSettings = new HashMap<String, ClassifierSettings>();

	@Before
	public void setUp() throws Exception {
		ClassifierSettings cs1 = new ClassifierSettings();
		cs1.setName("Classifier1");
		cs1.setClassifierModule("J48");
		cs1.setPercentageTrainingData(78);

		ClassifierSettings.put("Classifier1", cs1);

		ClassifierSettings cs2 = new ClassifierSettings();
		cs2.setName("Classifier2");
		cs2.setClassifierModule("J48");
		cs2.setPercentageTrainingData(56);

		ClassifierSettings.put("Classifier2", cs2);

		ClassifierSettings cs3 = new ClassifierSettings();
		cs3.setName("Classifier3");
		cs3.setClassifierModule("J48");
		cs3.setPercentageTrainingData(65);

		ClassifierSettings.put("Classifier3", cs3);
		conf = ManagerConfiguration.getInstance();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetDatasets() {
		DatasetManager datasetManager = new DatasetManager(conf,
				ClassifierSettings, 3);

		PartitionList partitionList = datasetManager.getPartitionList();

		System.out.println("Number of partitions "
				+ partitionList.NumberOfPartitions());
		System.out.println("Training Size " + partitionList.getTrainingSize());

		System.out.println("Partition 0 (class1) "
				+ partitionList.getPartitions().get(0));
		System.out.println("Partition 1 (class2)"
				+ partitionList.getPartitions().get(1));
		System.out.println("Partition 2 (class3)"
				+ partitionList.getPartitions().get(2));
		System.out.println("Partition 3 (data) "
				+ partitionList.getPartitions().get(3));

		System.out.println("Data index start "
				+ partitionList.getPartitionMap().get(3).getStartIndex());
		System.out.println("Data index end "
				+ partitionList.getPartitionMap().get(3).getEndIndex());

		assertTrue(partitionList.NumberOfPartitions() == 4);
		assertTrue(partitionList.getTrainingSize() == 70);

		// We are expecting 3 chunks of training data with 78, 56 and 65% of the
		// total of training data.
		// Training size should be:

		assertTrue(partitionList.getPartitions().get(0) == 55); // 70 * 0.78 =
																// 54.6
		assertTrue(partitionList.getPartitions().get(1) == 39); // 70 * 0.56 =
																// 39.2
		assertTrue(partitionList.getPartitions().get(2) == 46); // 70 * 0.65 =
																// 45.5

		// This partition is only for DATA
		assertTrue(partitionList.getPartitionMap().get(3).getStartIndex() == 70);
		assertTrue(partitionList.getPartitionMap().get(3).getEndIndex() == 99);

		System.out.println("Dataset data size "
				+ datasetManager.getDatasetsData().size());
		System.out.println("Partition 3 "
				+ partitionList.getPartitions().get(3));

		assertTrue(datasetManager.getDatasetsData().size() == partitionList
				.getPartitions().get(3) - 1);
		assertTrue(datasetManager.getDatasetsTraining().size() == 3);

		System.out.println("Header size "
				+ datasetManager.getArffFile().HeaderSize());
		System.out.println("Header size (Class1) "
				+ datasetManager.getDatasetsTraining().get(0).getHeader()
						.size());
		System.out.println("Header size (Class2) "
				+ datasetManager.getDatasetsTraining().get(1).getHeader()
						.size());
		System.out.println("Header size (Class3) "
				+ datasetManager.getDatasetsTraining().get(2).getHeader()
						.size());

		System.out.println("Data size (Class1) "
				+ datasetManager.getDatasetsTraining().get(0).getData().size());
		System.out.println("Data size (Class2) "
				+ datasetManager.getDatasetsTraining().get(1).getData().size());
		System.out.println("Data size (Class3) "
				+ datasetManager.getDatasetsTraining().get(2).getData().size());

		assertTrue(datasetManager.getDatasetsTraining().get(0).getHeader()
				.size() == datasetManager.getArffFile().HeaderSize());
		assertTrue(datasetManager.getDatasetsTraining().get(0).getData().size() == partitionList
				.getPartitions().get(0) - 1);

		assertTrue(datasetManager.getDatasetsTraining().get(1).getHeader()
				.size() == datasetManager.getArffFile().HeaderSize());
		assertTrue(datasetManager.getDatasetsTraining().get(1).getData().size() == partitionList
				.getPartitions().get(1) - 1);

		assertTrue(datasetManager.getDatasetsTraining().get(2).getHeader()
				.size() == datasetManager.getArffFile().HeaderSize());
		assertTrue(datasetManager.getDatasetsTraining().get(2).getData().size() == partitionList
				.getPartitions().get(2) - 1);
	}

}
