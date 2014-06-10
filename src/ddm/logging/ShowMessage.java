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

package ddm.logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;
import ddm.utils.NetworkUtils;

/**
 * 
 * @author jordi Corbilla
 * Class helper to display the message in the correct format
 *         in the command line
 */

public class ShowMessage {

	private String localName;
	private String description;
	private boolean verbosity;
	private boolean logging;
	static Logger log;

	/**
	 * ShowMessage constructor adding the following parameters
	 * @param description
	 * @param localName
	 * @param verbosity
	 * @param logging
	 */
	public ShowMessage(String description, String localName, boolean verbosity,
			boolean logging) {
		this.localName = localName;
		this.description = description;
		this.verbosity = verbosity;
		this.logging = logging;
		if (this.logging)
			log = Logger.getLogger("DDM");
	}

	/**
	 * Log method that displays the values on the screen according to verbosity and logging parameters
	 * @param message
	 */
	public void Log(String message) {
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("MMMM dd, yyyy h:mm:ss a");
		if (isVerbosity())
			System.out.println(ft.format(date) + " " + getDescription() + " "
					+ NetworkUtils.getHostName() + " " + getLocalName() + ": "
					+ message);
		if (isLogging())
			log.debug(ft.format(date) + " " + getDescription() + " "
					+ NetworkUtils.getHostName() + " " + getLocalName() + ": "
					+ message);

	}

	/**
	 * Local name of the agent
	 * @return string
	 */
	public String getLocalName() {
		return localName;
	}

	/**
	 * Local description of the text to display
	 * @return string
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Is logging enabled?
	 * @return boolean
	 */
	public boolean isLogging() {
		return logging;
	}

	/**
	 * Is verbosity enabled?
	 * @return
	 */
	public boolean isVerbosity() {
		return verbosity;
	}
}
