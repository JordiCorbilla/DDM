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

package ddm.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

@SuppressWarnings("serial")
public class ManagerConfiguration implements java.io.Serializable{
    
	private static ManagerConfiguration _properties = null;

	public ManagerConfiguration() {
		_classifierModule = new HashMap<String, String>();
		readProperties();
	}
	
	private synchronized void readProperties(){
		Properties prop = new Properties();
		InputStream input = null;
	 
		try {
			File properties = new File("ManagerAgent.properties");
			
			if (properties.isFile() && properties.canRead())
			{
				input = new FileInputStream(properties);
		 
				// load a properties file
				prop.load(input);
		 
				// get the property value and print it out
				setArffDataSetLocation(prop.getProperty("ArffDataSetLocation"));
				setPercentageTrainingData(Integer.parseInt(prop.getProperty("PercentageTrainingData")));
				setNumberOfClassifiers(Integer.parseInt(prop.getProperty("NumberOfClassifiers")));
				
				for (int i=1;i<=this.getNumberOfClassifiers();i++)
				{
					String value = prop.getProperty("ClassifierModule" + i);
					getClassifierModule().put("Classifier" + i, value);
				}
				
				setClassifiersUseSameContainer(Boolean.valueOf(prop.getProperty("ClassifiersUseSameContainer")));
				setOutputFileLocation(prop.getProperty("OutputFileLocation"));
				setVerbosity(Boolean.valueOf(prop.getProperty("Verbosity")));
				setLogging(Boolean.valueOf(prop.getProperty("Logging")));
			}
			else
				System.out.println("Unable to locate the file ManagerAgent.properties");
	 
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
	}
	
    public static ManagerConfiguration getInstance()
    {
        if( _properties == null)
        {
        	_properties = new ManagerConfiguration(); 
        }
        return _properties; 
    }
    
    //Arff path location
    private String _arffDataSetLocation;
    public void setArffDataSetLocation(String arffDataSetLocation)
    {
    	_arffDataSetLocation = arffDataSetLocation;
    }
    public String getArffDataSetLocation()
    {
        return _arffDataSetLocation;
    }
    
    //Percentage of data dedicated for training
    private int _percentageTrainingData;
    public void setPercentageTrainingData(int percentageTrainingData)
    {
    	_percentageTrainingData = percentageTrainingData;
    }
    public int getPercentageTrainingData()
    {
        return _percentageTrainingData;
    }
    
    //Arff path location
    private HashMap<String, String> _classifierModule;
    public void setClassifierModule(HashMap<String, String> classifierModule)
    {
    	_classifierModule = classifierModule;
    }
    public HashMap<String, String> getClassifierModule()
    {
        return _classifierModule;
    }    
    
    //Number of classifiers
    private int _numberOfClassifiers;
    public void setNumberOfClassifiers(int numberOfClassifiers)
    {
    	_numberOfClassifiers = numberOfClassifiers;
    }
    public int getNumberOfClassifiers()
    {
        return _numberOfClassifiers;
    }     
    
    //Number of classifiers
    private boolean _classifiersUseSameContainer;
    public void setClassifiersUseSameContainer(boolean classifiersUseSameContainer)
    {
    	_classifiersUseSameContainer = classifiersUseSameContainer;
    }
    public boolean getClassifiersUseSameContainer()
    {
        return _classifiersUseSameContainer;
    }      
    
    //Output file location
    private String _OutputFileLocation;
    public void setOutputFileLocation(String OutputFileLocation)
    {
    	_OutputFileLocation = OutputFileLocation;
    }
    public String getOutputFileLocation()
    {
    	return _OutputFileLocation;
    }
    
    //Verbosity
    private boolean _verbosity;
    public void setVerbosity(boolean verbosity)
    {
    	_verbosity = verbosity;
    }
    public boolean getVerbosity()
    {
    	return _verbosity;
    }    
    
    //Logging
    private boolean _logging;
    public void setLogging(boolean logging)
    {
    	_logging = logging;
    }
    public boolean getLogging()
    {
    	return _logging;
    }      
    
    public String ToString()
    {
    	return "";
    }
 
}
