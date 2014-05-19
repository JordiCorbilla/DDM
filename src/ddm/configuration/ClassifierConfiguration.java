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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@SuppressWarnings("serial")
public class ClassifierConfiguration implements java.io.Serializable{

	private static ClassifierConfiguration _properties = null;	
	
	public ClassifierConfiguration() {
		readProperties();
	}	
	
	private synchronized void readProperties(){
		Properties prop = new Properties();
		InputStream input = null;
	 
		try {
	 
			input = new FileInputStream("ClassifierAgent.properties");
	 
			// load a properties file
			prop.load(input);
	 
			// get the property value and print it out
			setMinimumPercentageTraining(Integer.parseInt(prop.getProperty("MinimumPercentageTraining")));
			setMaximumPercentageTraining(Integer.parseInt(prop.getProperty("MaximumPercentageTraining")));
			setApplicationPath(prop.getProperty("ApplicationPath"));
			setVerbosity(Boolean.valueOf(prop.getProperty("Verbosity")));
			setLogging(Boolean.valueOf(prop.getProperty("Logging")));			
	 
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
	
    public static ClassifierConfiguration getInstance()
    {
        if( _properties == null)
        {
        	_properties = new ClassifierConfiguration(); 
        }
        return _properties; 
    }	
    
    //Lower range for the percentage of training data for the agent
    private int _minimumPercentageTraining;
    public void setMinimumPercentageTraining(int minimumPercentageTraining)
    {
    	_minimumPercentageTraining = minimumPercentageTraining;
    }
    public int getMinimumPercentageTraining()
    {
        return _minimumPercentageTraining;
    }    
    
    //Lower range for the percentage of training data for the agent
    private int _maximumPercentageTraining;
    public void setMaximumPercentageTraining(int maximumPercentageTraining)
    {
    	_maximumPercentageTraining = maximumPercentageTraining;
    }
    public int getMaximumPercentageTraining()
    {
        return _maximumPercentageTraining;
    }     
    
    //Application path to retrieve the files that the agent stores
    private String _applicationPath;
    public void setApplicationPath(String applicationPath)
    {
    	_applicationPath = applicationPath;
    }
    public String getApplicationPath()
    {
        return _applicationPath;
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
}
