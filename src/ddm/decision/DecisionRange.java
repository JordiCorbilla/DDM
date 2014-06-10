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
 * Unit that create a set of ranges according to weka images.
 * Once the range is set, we can test if a value belongs to it or not.
 */
public class DecisionRange {

	private ArrayList<Range> listRange = null;

	public DecisionRange() {
		listRange = new ArrayList<Range>();
	}

	private boolean exists(Range range) {
		boolean found = false;
		int i = 0;
		while (!found && i < listRange.size()) {
			found = range.getImage().equals(listRange.get(i).getImage());
			i++;
		}
		return found;
	}

	public void AddItem(String image, double value) {
		double min = 0.0;
		double max = 0.0;
		min = value - 0.5;
		max = value + 0.5;
		Range r = new Range(image, value, min, max);
		if (!exists(r))
			listRange.add(r);
	}

	public String testValue(double value) {
		boolean found = false;
		int i = 0;
		String image = "";
		Range r = null;
		while (!found && i < listRange.size()) {
			r = listRange.get(i);
			found = r.isInRange(value);
			i++;
		}
		if (found)
			image = r.getImage();
		return image;
	}

	public String ToString() {
		String s = "";
		for (int i = 0; i < listRange.size(); i++) {
			s = s + "Image: " + listRange.get(i).getImage()
					+ " associated value: " + listRange.get(i).getValue()
					+ "\r\n";
		}
		return s;
	}
}
