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

import ddm.logging.ShowMessage;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

/***
 * 
 * @author jordi Corbilla
 * Register in the DF (Directory Facilitator) for the client agent to be able to retrieve its AID.
 *
 */

@SuppressWarnings("serial")
public class RegisterInDFBehaviour extends OneShotBehaviour {

	private ShowMessage sm;
	private String type;
	private String owner;
	
	public RegisterInDFBehaviour(Agent a, ShowMessage sm, String type, String owner) {
		super(a);
		setLog(sm);
		setType(type);
		setOwner(owner);
	}

	public void action() {
		sm.Log("RegisterInDFBehaviour - start");
		ServiceDescription sd = new ServiceDescription();
		sd.setType(getType());
		sd.setName(this.myAgent.getName());
		sd.setOwnership(getOwner());
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(this.myAgent.getAID());
		dfd.addServices(sd);
		try {
			DFAgentDescription[] dfds = DFService.search(this.myAgent, dfd);
			if (dfds.length > 0) {
				DFService.deregister(this.myAgent, dfd);
			}
			DFService.register(this.myAgent, dfd);
			sm.Log(this.myAgent.getLocalName() + " is ready.");
		} catch (Exception ex) {
			sm.Log("Failed registering with DF! Shutting down...");
			ex.printStackTrace();
			this.myAgent.doDelete();
		}
		sm.Log("RegisterInDFBehaviour - end");
	}

	public ShowMessage getLog() {
		return sm;
	}

	public void setLog(ShowMessage sm) {
		this.sm = sm;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
}
