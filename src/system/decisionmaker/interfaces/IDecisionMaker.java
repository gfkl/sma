package system.decisionmaker.interfaces;

import system.dto.EnvDTO;
import system.dto.AgentDTO;

public interface IDecisionMaker {
	AgentDTO makeDecision(EnvDTO env);
}
