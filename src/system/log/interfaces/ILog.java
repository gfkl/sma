package system.log.interfaces;

import system.dto.EnvParamDTO;
import system.dto.EnvironmentDTO;

public interface ILog {
	EnvironmentDTO log(EnvParamDTO params);
}
