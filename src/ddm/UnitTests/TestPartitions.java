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

package ddm.UnitTests;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ddm.data.PartitionList;
import ddm.ontology.ClassifierSettings;

/**
 * 
 * @author jordi Corbilla
 * Unit test to test the correct handling of partitions from a repository file
 */
public class TestPartitions {

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
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetPartitionsNotDistributedEvenly() {
		PartitionList partitionList = new PartitionList(73, 70,
				ClassifierSettings, 3);
		System.out.println(partitionList.NumberOfPartitions());
		assertTrue(partitionList.NumberOfPartitions() == 4);
		System.out.println(partitionList.getTrainingSize());
		assertTrue(partitionList.getTrainingSize() == 51);

		// We are expecting 3 chunks of training data with 78, 56 and 65% of the
		// total of training data.
		// Training size should be:
		System.out.println(partitionList.getPartitions().get(0));
		System.out.println(partitionList.getPartitions().get(1));
		System.out.println(partitionList.getPartitions().get(2));
		assertTrue(partitionList.getPartitions().get(0) == 40); // 51 * 0.78 =
																// 39.78
		assertTrue(partitionList.getPartitions().get(1) == 29); // 51 * 0.56 =
																// 28.56
		assertTrue(partitionList.getPartitions().get(2) == 33); // 51 * 0.65 =
																// 33.15

		// This partition is only for DATA
		assertTrue(partitionList.getPartitionMap().get(3).getStartIndex() == 51);
		assertTrue(partitionList.getPartitionMap().get(3).getEndIndex() == 72);
	}

	@Test
	public void testGetPartitionsDistributedEvenly() {
		PartitionList partitionList = new PartitionList(100, 70,
				ClassifierSettings, 3);
		assertTrue(partitionList.NumberOfPartitions() == 4);
		assertTrue(partitionList.getTrainingSize() == 70);

		// We are expecting 3 chunks of training data with 78, 56 and 65% of the
		// total of training data.
		// Training size should be:
		System.out.println(partitionList.getPartitions().get(0));
		System.out.println(partitionList.getPartitions().get(1));
		System.out.println(partitionList.getPartitions().get(2));
		assertTrue(partitionList.getPartitions().get(0) == 55); // 70 * 0.78 =
																// 54.6
		assertTrue(partitionList.getPartitions().get(1) == 39); // 70 * 0.56 =
																// 39.2
		assertTrue(partitionList.getPartitions().get(2) == 46); // 70 * 0.65 =
																// 45.5

		// This partition is only for DATA
		assertTrue(partitionList.getPartitionMap().get(3).getStartIndex() == 70);
		assertTrue(partitionList.getPartitionMap().get(3).getEndIndex() == 99);
	}

}
