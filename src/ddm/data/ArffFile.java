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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/***
 * 
 * @author jordi Corbilla
 * This class contains an ARFF file
 * 
 */

@SuppressWarnings("serial")
public class ArffFile implements java.io.Serializable {
	private ArrayList<String> data;
	private ArrayList<String> header;

	public ArffFile(String ArffFile) {
		this.data = new ArrayList<String>();
		this.header = new ArrayList<String>();
		LoadFile(ArffFile);
	}

	private void LoadFile(String ArrFile) {
		try (BufferedReader br = new BufferedReader(new FileReader(ArrFile))) {
			String line = br.readLine();
			int i = 0;
			while (line != null) {
				if (i == 0)
					header.add(line);
				else
					data.add(line);

				if (!line.contentEquals("@DATA") && i == 0)
					i = 0;
				else
					i = 1;

				line = br.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> Data() {
		return this.data;
	}

	public ArrayList<String> Header() {
		return this.header;
	}

	public int DataSize() {
		return this.data.size();
	}

	public int HeaderSize() {
		return this.header.size();
	}
}
