package system.robot.impl;

import system.collector.CollectorComponent;
import system.collector.impl.CollectorImpl;
import system.decisionmaker.DecisionMakerComponent;
import system.decisionmaker.impl.DecisionMakerImpl;
import system.effector.EffectorComponent;
import system.effector.impl.EffectorImpl;
import system.robot.RobotComponent;

public class RobotImpl extends RobotComponent{

	@Override
	protected DecisionMakerComponent make_decisionmaker() {
		return new DecisionMakerImpl();
	}

	@Override
	protected CollectorComponent make_collector() {
		return new CollectorImpl();
	}

	@Override
	protected EffectorComponent make_effector() {
		return new EffectorImpl();
	}

}
