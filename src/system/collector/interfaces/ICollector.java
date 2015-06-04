package system.collector.interfaces;

import system.dto.EnvDTO;
import system.dto.AgentDTO;

public interface ICollector {
	AgentDTO translate(EnvDTO env, int id);
}
