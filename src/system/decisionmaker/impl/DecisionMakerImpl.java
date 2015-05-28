package system.decisionmaker.impl;

import system.decisionmaker.DecisionMakerComponent;
import system.decisionmaker.interfaces.IDecisionMaker;
import system.dto.EnvironmentDTO;
import system.dto.RobotDTO;

public class DecisionMakerImpl extends DecisionMakerComponent {

	@Override
	protected IDecisionMaker make_decision() {
		return new IDecisionMaker() {

			@Override
			public RobotDTO makeDecision(EnvironmentDTO env) {
				System.out.println("Start of: DecisionMakerImpl#makeDecision");
				RobotDTO perception = requires().translation().translate(env);
				// TODO le robot prend une décision en fonction de ce qu'il voit et de son etat
				RobotDTO action = new RobotDTO();
				System.out.println("End of: DecisionMakerImpl#makeDecision");
				return action;
			}
		};		

	}

}
