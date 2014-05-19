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

package ddm.jade;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.Iterator;

public class JadeAgents {

	public static jade.util.leap.List SearchAgents(Agent a, String type, String name) {
		jade.util.leap.List results = new jade.util.leap.ArrayList();
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		if (type != null)
			sd.setType(type);
		if (name != null)
			sd.setName(name);
		dfd.addServices(sd);
		try {
			SearchConstraints c = new SearchConstraints();
			c.setMaxResults(new Long(-1));
			DFAgentDescription[] DFAgents = DFService.search(a, dfd, c);
			int i = 0;
			while ((DFAgents != null) && (i < DFAgents.length)) {
				DFAgentDescription agent = DFAgents[i];
				i++;
				@SuppressWarnings("rawtypes")
				Iterator services = agent.getAllServices();
				boolean found = false;
				ServiceDescription service = null;
				while (services.hasNext() && !found) {
					service = (ServiceDescription) services.next();
					found = (service.getType().equals(type) || service
							.getName().equals(name));
				}
				if (found) {
					results.add((AID) agent.getName());
				}
			}
		} catch (FIPAException e) {
			System.out.println("ERROR: " + e.toString());
		}
		return results;
	}	
	
}
