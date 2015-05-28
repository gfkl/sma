package system.effector.impl;

import system.dto.EnvironmentDTO;
import system.dto.RobotDTO;
import system.effector.EffectorComponent;
import system.effector.interfaces.IEffector;

public class EffectorImpl extends EffectorComponent {

	@Override
	protected IEffector make_applier() {
		return new IEffector() {
			
			@Override
			public EnvironmentDTO applyToEnvironment(EnvironmentDTO env) {
				System.out.println("Start of: EffectorImpl#applyToEnvironment");
				RobotDTO action = requires().decision().makeDecision(env);
				// TODO le robot traduit l'action qu'il fait en l'appliquant a l'env du systeme
				EnvironmentDTO resultEnv = new EnvironmentDTO();
				System.out.println("End of: EffectorImpl#applyToEnvironment");
				return resultEnv;
			}
		};
	}

}
