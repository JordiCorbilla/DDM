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

import jade.core.behaviours.OneShotBehaviour;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import ddm.agents.ManagerAgent;
import ddm.logging.ShowMessage;

/**
 * 
 * @author jordi Corbilla
 * Behaviour that displays the command line section. This gives the user a set of options so 
 * it can perform the operation needed according to the state of the application
 */
@SuppressWarnings("serial")
public class WaitUserCommandBehaviour extends OneShotBehaviour {

	static final int WAIT = -1;
	static final int QUIT = 0;
	static final int TerminateAgent = 0;
	static final int ListClassifiersOnline = 1;
	static final int CreateDefinedAgents = 2;
	static final int SendTrainingDataToClassifiers = 3;
	static final int SendDataToClassifiers = 4;
	static final int CreateNewAgent = 5;
	static final int SendConfigurationToClassifiers = 6;
	private ShowMessage sm;

	private int command = WAIT;
	private ManagerAgent myAgent;

	// Get the user command
	public WaitUserCommandBehaviour(ManagerAgent a) {
		super(a);
		myAgent = a;
		this.sm = a.getSM();
		command = WAIT;
	}

	public void action() {

		command = getUserChoice();

		switch (command) {
		case TerminateAgent:
			sm.Log(" is shutting down...Bye!");
			myAgent.doDelete();
			System.exit(0);
			break;
		case ListClassifiersOnline:
			myAgent.addBehaviour(new ListClassifiersBehaviour(myAgent));
			myAgent.addBehaviour(new WaitUserCommandBehaviour(myAgent));
			break;
		case CreateDefinedAgents:
			myAgent.addBehaviour(new ThreeStepsBehaviour(myAgent));
			break;
		case SendTrainingDataToClassifiers:
			myAgent.addBehaviour(new SendTrainingDataBehaviour(myAgent));
			break;
		case SendDataToClassifiers:
			myAgent.addBehaviour(new SendDataBehaviour(myAgent));
			break;
		case CreateNewAgent:
			myAgent.addBehaviour(new CreateAgentBehaviour(myAgent));
			myAgent.addBehaviour(new WaitUserCommandBehaviour(myAgent));
			break;
		case SendConfigurationToClassifiers:
			myAgent.addBehaviour(new SendConfigurationToClassifierBehaviour(
					myAgent));
			break;
		default:
			System.out.println("Invalid choice!");
			myAgent.addBehaviour(new WaitUserCommandBehaviour(myAgent));
			break;
		}
	}

	int getUserChoice() {
		System.out
				.print("\n    <<****** Distributed decision-making system ******>>"
						+ "\n    <<******        with Jade and Weka          ******>>"
						+ "\n    <<************ MANAGER AGENT - MENU **************>>"
						+ "\n    ****************************************************\n"
						+ "\n    0. Terminate Agent"
						+ "\n    1. List Classifiers online"
						+ "\n    2. Create defined agents"
						+ "\n    3. Send Training Data to Classifiers"
						+ "\n    4. Send Data to Classifiers"
						+ "\n    5. Create a new agent"
						+ "\n    6. Send Configuration to Classifiers" + "\n> ");
		try {
			BufferedReader buf = new BufferedReader(new InputStreamReader(
					System.in));
			String in = buf.readLine();
			return Integer.parseInt(in);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return WAIT;
	}

}
