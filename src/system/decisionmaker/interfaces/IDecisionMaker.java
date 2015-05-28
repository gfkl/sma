package system.decisionmaker.interfaces;

import system.dto.EnvironmentDTO;
import system.dto.RobotDTO;

public interface IDecisionMaker {
	RobotDTO makeDecision(EnvironmentDTO env);
}
