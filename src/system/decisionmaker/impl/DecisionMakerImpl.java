package system.decisionmaker.impl;

import system.decisionmaker.DecisionMakerComponent;
import system.decisionmaker.interfaces.IDecisionMaker;
import system.dto.EnvDTO;
import system.dto.AgentDTO;

public class DecisionMakerImpl extends DecisionMakerComponent {

	@Override
	protected IDecisionMaker make_decision() {
		return new IDecisionMaker() {

			@Override
			public AgentDTO makeDecision(EnvDTO env) {
				System.out.println("Start of: DecisionMakerImpl#makeDecision");
				AgentDTO perception = requires().translation().translate(env);
				// TODO l'agent prend une décision en fonction de ce qu'il voit et de son etat
				AgentDTO action = new AgentDTO();
				System.out.println("End of: DecisionMakerImpl#makeDecision");
				return action;
			}
		};		

	}

}
