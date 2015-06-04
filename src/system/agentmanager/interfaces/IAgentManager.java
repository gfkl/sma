package system.agentmanager.interfaces;

import system.dto.EnvDTO;

public interface IAgentManager {
	void init(int nbAgents);
	EnvDTO executeAgents(EnvDTO env);
}
