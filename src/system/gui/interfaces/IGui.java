package system.gui.interfaces;

import system.dto.EnvConfigDTO;
import system.dto.EnvObsDTO;

public interface IGui {
	EnvConfigDTO	printEnv(EnvObsDTO env);
}
