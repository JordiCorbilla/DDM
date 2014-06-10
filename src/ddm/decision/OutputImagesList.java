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

import java.util.ArrayList;

/**
 * 
 * @author jordi Corbilla
 * This class saves the list of weka images (values) that the system is using and it 
 * returns a string to be stored in the output file so the user can see what are the values.
 */
public class OutputImagesList {
	private ArrayList<String> listRange = null;

	public OutputImagesList() {
		listRange = new ArrayList<String>();
	}

	private boolean exists(String description) {
		boolean found = false;
		int i = 0;
		while (!found && i < listRange.size()) {
			found = description.equals(listRange.get(i));
			i++;
		}
		return found;
	}

	/**
	 * Add a new image and it's value
	 * @param image
	 * @param value
	 */
	public void AddItem(String image, double value) {
		String description = "Image: " + image + " associated value: " + value
				+ "\r\n";
		if (!exists(description))
			listRange.add(description);
	}

	/**
	 * Return the formatted text to be saved.
	 * @return
	 */
	public String ToString() {
		String s = "";
		for (int i = 0; i < listRange.size(); i++) {
			s = s + listRange.get(i);
		}
		return s;
	}
}
