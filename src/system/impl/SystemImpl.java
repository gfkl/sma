package system.impl;

import system.SystemComponent;
import system.agentmanager.AgentManagerComponent;
import system.agentmanager.impl.AgentManagerImpl;
import system.envmanager.EnvManagerComponent;
import system.envmanager.impl.EnvManagerImpl;
import system.gui.GuiComponent;
import system.gui.impl.GuiImpl;

public class SystemImpl extends SystemComponent{

	@Override
	protected EnvManagerComponent make_envmanager() {
		return new EnvManagerImpl();
	}

	@Override
	protected GuiComponent make_gui() {
		return new GuiImpl();
	}

	@Override
	protected AgentManagerComponent make_agentmanager() {
		return new AgentManagerImpl();
	}

}
