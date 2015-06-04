package system.effector.interfaces;

import system.dto.AgentActionDTO;
import system.dto.EnvDTO;

public interface IEffector {
	AgentActionDTO applyToEnvironment(EnvDTO env, int id);
}
