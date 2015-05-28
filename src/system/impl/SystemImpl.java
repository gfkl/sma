package system.impl;

import system.SystemComponent;
import system.envmanager.EnvManagerComponent;
import system.envmanager.impl.EnvManagerImpl;
import system.gui.GuiComponent;
import system.gui.impl.GuiImpl;
import system.log.LogComponent;
import system.log.impl.LogImpl;
import system.persistence.PersistenceComponent;
import system.persistence.impl.PersistenceImpl;
import system.robot.RobotComponent;
import system.robot.impl.RobotImpl;

public class SystemImpl extends SystemComponent{

	@Override
	protected GuiComponent make_gui() {
		return new GuiImpl();
	}

	@Override
	protected LogComponent make_log() {
		return new LogImpl();
	}

	@Override
	protected PersistenceComponent make_persistence() {
		return new PersistenceImpl();
	}

	@Override
	protected EnvManagerComponent make_envmanager() {
		return new EnvManagerImpl();
	}

	@Override
	protected RobotComponent make_robot() {
		return new RobotImpl();
	}

}
