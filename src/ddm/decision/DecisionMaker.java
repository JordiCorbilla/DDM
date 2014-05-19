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

package ddm.decision;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import ddm.configuration.ManagerConfiguration;
import ddm.ontology.ClassificationResult;
import ddm.ontology.DataInstance;

public class DecisionMaker {

	private File file = null;
	private BufferedWriter bw = null;
	private FileWriter fw = null;

	public DecisionMaker(ManagerConfiguration conf) {
		// Create the file where all the data will be stored
		file = new File(conf.getOutputFileLocation());

		try {
			file.createNewFile();
			fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);		
			
			bw.write("***************************************************************************" + "\r\n");
			bw.write("This file has been auto generated by DDM Distributed Decision Making System" + "\r\n");
			bw.write("***************************************************************************" + "\r\n");
			bw.write("" + "\r\n");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void Make(DataInstance dataToPredict, HashMap<String, ClassificationResult> decisionResult, int trainingSize) {
		
		DecisionRange decisionRange = new DecisionRange();
		double accumulated = 0.0;
		double percentageAccumulated = 0.0;
		String description = "";
		for (int i= 1; i<=decisionResult.size();i++)
		{
			ClassificationResult cr = decisionResult.get("Classifier"+i);
			double percentage = cr.getTrainingSize() / (double) trainingSize;
			double prediction = cr.getInstancePredictedValue() + 1.0; //Transform it into a range 1..N
			decisionRange.AddItem(cr.getPredictedInstanceValue(), prediction);
			
			accumulated = accumulated + (percentage * prediction);
			percentageAccumulated = percentageAccumulated + percentage;
			
			java.text.DecimalFormat percentageFormatter = new java.text.DecimalFormat("#0.00");	
			String text = percentageFormatter.format(percentage);
			
			description = description + "Classifier"+i + " decision:" +cr.getPredictedInstanceValue() + "("+text+"%) |"; 
		}
		
		double WeightedMean = accumulated / percentageAccumulated;
		String value = decisionRange.testValue(WeightedMean);
		
		try {
			java.text.DecimalFormat percentageFormatter = new java.text.DecimalFormat("#0.00");	
			String text = percentageFormatter.format(WeightedMean);
			bw.write(dataToPredict.getValue().toString() + "\t" + description + "\t -> Final Decision (" + value + ") value:" + text + "\r\n");
		} catch (Exception e) {
			e.printStackTrace();
		};
	}
	
	public void CloseFile()
	{
		try {
			bw.close();
		} catch (Exception e) {
		}
	}
}
