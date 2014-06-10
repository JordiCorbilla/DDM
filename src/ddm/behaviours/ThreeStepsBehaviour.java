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
import jade.core.behaviours.Behaviour;

/**
 * 
 * @author jordi Corbilla
 * Behaviour that generated the defined agents and it invokes other steps to ensure that
 * the communication is granted and all the parameters have been correctly passed through.
 */
@SuppressWarnings("serial")
public class ThreeStepsBehaviour extends Behaviour {

	private int step = 1;
	private ShowMessage sm;
	private ManagerAgent myAgent;

	public ThreeStepsBehaviour(ManagerAgent a) {
		super(a);
		myAgent = a;
		this.sm = a.getSM();
	}

	public void action() {
		sm.Log("ThreeStepsBehaviour - start");
		switch (step) {
		case 1:
			myAgent.addBehaviour(new CreateDefinedAgentBehaviour(myAgent));
			step = 2;
			break;
		case 2:
			// myAgent.addBehaviour(new
			// SendConfigurationToClassifierBehaviour(myAgent));
			step = 3;
			break;
		case 3:
			// myAgent.addBehaviour(new WaitUserCommandBehaviour(myAgent));
			step = 4;
			break;
		}
		sm.Log("ThreeStepsBehaviour - end");
	}

	@Override
	public boolean done() {
		return (step == 3);
	}
}
