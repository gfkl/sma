package system.persistence.interfaces;

import system.dto.EnvParamDTO;
import system.dto.EnvironmentDTO;

public interface IPersistence {
	EnvironmentDTO getLap(EnvParamDTO params);
}
