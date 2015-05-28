package system;

import system.dto.EnvironmentDTO;
import system.robot.RobotComponent;
import system.robot.impl.RobotImpl;

public class Main {

	public static void main(String[] args) {
		RobotComponent.Component robot = new RobotImpl().newComponent();
		EnvironmentDTO env = new EnvironmentDTO();
		env = robot.action().applyToEnvironment(env);		
	}

}
