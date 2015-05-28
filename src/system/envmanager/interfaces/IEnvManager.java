package system.envmanager.interfaces;

import system.dto.EnvParamDTO;
import system.dto.EnvironmentDTO;

public interface IEnvManager {
	EnvironmentDTO runLap(EnvParamDTO params);
}
