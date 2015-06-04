package system.effector.interfaces;

import system.dto.EnvDTO;
import system.dto.AgentDTO;

public interface IEffector {
	EnvDTO applyToEnvironment(EnvDTO env);
}
