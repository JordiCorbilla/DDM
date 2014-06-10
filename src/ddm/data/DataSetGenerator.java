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

package ddm.data;

import jade.util.leap.List;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import ddm.ontology.DataInstance;

/**
 * 
 * @author jordi Corbilla
 * This class generates a file from a particular DataSet.
 * 
 */
public class DataSetGenerator {
	
	/**
	 * Generates a bespoke file for a classifier
	 * @param localName
	 * @param header
	 * @param rows
	 */
	public static void GenerateFileForClassifier(String localName, List header, List rows) {
		BufferedWriter bw = null;
		try {
            File file = new File(localName + "_TrainingRepository.arff");

            if (!file.exists()) {
				file.createNewFile();
			}

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			for (int i=0;i<header.size(); i++)
				bw.write(header.get(i).toString() + "\r\n");			
			for (int i=0;i<rows.size(); i++)
				bw.write(rows.get(i).toString() + "\r\n");
			
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
            	bw.close();
            } catch (Exception e) {
            }
        }	
	}
	
	/**
	 * Generates a bespoke file for a classifier from a DataSet
	 * @param localName
	 * @param header
	 * @param dataInstance
	 */
	public static void GenerateDataFileForClassifier(String localName, List header, DataInstance dataInstance) {
		BufferedWriter bw = null;
		try {
            File file = new File(localName + "_DataRepository.arff");

            if (!file.exists()) {
				file.createNewFile();
			}

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			for (int i=0;i<header.size(); i++)
				bw.write(header.get(i).toString() + "\r\n");			
			bw.write(dataInstance.getValue() + "\r\n");
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
            	bw.close();
            } catch (Exception e) {
            }
        }	
	}
}
