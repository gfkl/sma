package system.agentmanager.interfaces;

import system.dto.EnvDTO;

public interface IAgentManager {
	EnvDTO init(EnvDTO env, int nbAgents);
	EnvDTO executeAgents(EnvDTO env);
}
