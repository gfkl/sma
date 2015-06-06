package system.log.interfaces;

import system.dto.AgentActionDTO;

public interface ILog {
	void log(AgentActionDTO action);
}
