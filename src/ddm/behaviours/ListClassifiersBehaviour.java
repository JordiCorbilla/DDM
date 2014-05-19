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

package ddm.behaviours;

import java.util.HashMap;
import java.util.Iterator;
import ddm.agents.ManagerAgent;
import ddm.jade.JadeAgents;
import ddm.ontology.ClassifierSettings;
import jade.core.behaviours.OneShotBehaviour;

@SuppressWarnings("serial")
public class ListClassifiersBehaviour extends OneShotBehaviour {

	private ManagerAgent myAgent;
	
	public ListClassifiersBehaviour(ManagerAgent a) {
		super(a);
		myAgent = a;
	}	
	
	@Override
	public void action() {
		System.out.println("\n\nListing Classifiers Online:");
		jade.util.leap.List Classifiers = JadeAgents.SearchAgents(myAgent, "Classifier Agent", null);
		@SuppressWarnings("rawtypes")
		Iterator classifiers = Classifiers.iterator();

		if (!classifiers.hasNext())
			System.out.println("List is empty");

		int i = 0;
		while (classifiers.hasNext()) {
			i++;
			System.out.println("  " + i + "- " + classifiers.next().toString());
		}
		
		System.out.println("\n\n");
		System.out.println("Settings:");
		HashMap<String, ClassifierSettings> settings = myAgent.getClassifierSettings();
		for (int j=1; j<= settings.size(); j++)
		{
			String details = settings.get("Classifier"+j).toString();
			System.out.println("\t"+details);
		}

		System.out.println("Number of Classifiers online: " + i);
		System.out.println("\n\n");
	}

}
