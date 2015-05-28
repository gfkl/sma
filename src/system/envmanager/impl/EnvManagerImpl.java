package system.envmanager.impl;

import system.dto.EnvParamDTO;
import system.dto.EnvironmentDTO;
import system.envmanager.EnvManagerComponent;
import system.envmanager.interfaces.IEnvManager;

public class EnvManagerImpl extends EnvManagerComponent {

	@Override
	protected IEnvManager make_lap() {
		return new IEnvManager() {
			
			@Override
			public EnvironmentDTO runLap(EnvParamDTO params) {
				System.out.println("Start of: EnvManagerImpl#runLap");
				// TODO gerer l'env en fonction des params d'entree
				// TODO boucler sur tous les robots pour creer l'environnement T+1
				EnvironmentDTO env = new EnvironmentDTO();
				env = requires().robotaction().applyToEnvironment(env);
				System.out.println("End of: EnvManagerImpl#runLap");
				return env;
			}
		};
	}
	
}
