package system.effector.impl;

import system.dto.EnvDTO;
import system.dto.AgentDTO;
import system.effector.EffectorComponent;
import system.effector.interfaces.IEffector;

public class EffectorImpl extends EffectorComponent {

	@Override
	protected IEffector make_applier() {
		return new IEffector() {
			
			@Override
			public EnvDTO applyToEnvironment(EnvDTO env, int id) {
				System.out.println("Start of: EffectorImpl#applyToEnvironment");
				AgentDTO action = requires().decision().makeDecision(env);
				// TODO l'agent traduit l'action qu'il fait en l'appliquant a l'env du systeme
				EnvDTO resultEnv = new EnvDTO();
				System.out.println("End of: EffectorImpl#applyToEnvironment");
				return resultEnv;
			}
		};
	}

}
