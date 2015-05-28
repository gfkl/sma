package system.collector.interfaces;

import system.dto.EnvironmentDTO;
import system.dto.RobotDTO;

public interface ICollector {
	RobotDTO translate(EnvironmentDTO env);
}
