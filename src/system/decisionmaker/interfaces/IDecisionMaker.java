package system.decisionmaker.interfaces;

import system.dto.ActionDTO;
import system.dto.EnvDTO;

public interface IDecisionMaker {
	ActionDTO makeDecision(EnvDTO env, int id);
}
