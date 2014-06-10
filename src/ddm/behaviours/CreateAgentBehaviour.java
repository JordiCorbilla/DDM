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

import ddm.agents.ManagerAgent;
import ddm.logging.ShowMessage;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

/**
 * @author jordi Corbilla
 * Behaviour to create additional agents.
 * This class automatically generates new instances of Jade Agents using the ClassifierAgent class
 *
 */
@SuppressWarnings("serial")
public class CreateAgentBehaviour extends OneShotBehaviour {

	private ShowMessage sm;
	private ManagerAgent myAgent;
	
	public CreateAgentBehaviour(ManagerAgent a) {
		super(a);
		myAgent = a;
		this.sm = a.getSM();
	}	

	@Override
	public void action() {
		sm.Log("Create Agent - start");
		Runtime rt = Runtime.instance();
		Profile p = new ProfileImpl();
		p.setParameter(Profile.MAIN_HOST, "localhost");
		p.setParameter(Profile.MAIN_PORT, "1099");
		p.setParameter(Profile.CONTAINER_NAME, "Main-Container");

		ContainerController cc = null;
		if (myAgent.getConfiguration().getClassifiersUseSameContainer()) {
			if (myAgent.getContainerController() == null)
				myAgent.setMainContainerController(rt.createAgentContainer(p));
			cc = myAgent.getContainerController();

		} else
			cc = rt.createAgentContainer(p);
		if (cc != null) {
			try {
				int agentId = myAgent.getNumberOfAgents() + 1;
				AgentController ac = cc.createNewAgent("Classifier" + agentId, "ddm.agents.ClassifierAgent", null);
				ac.start();
				myAgent.setNumberOfAgents(myAgent.getNumberOfAgents()+1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		sm.Log("Create Agent - end");
	}
	
	@Override
	public int onEnd(){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return super.onEnd();
	}
}