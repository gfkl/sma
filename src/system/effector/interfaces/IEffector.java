package system.effector.interfaces;

import system.dto.EnvironmentDTO;
import system.dto.RobotDTO;

public interface IEffector {
	EnvironmentDTO applyToEnvironment(EnvironmentDTO env);
}
