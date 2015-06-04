package system.envmanager.impl;

import system.dto.EnvConfigDTO;
import system.dto.EnvDTO;
import system.dto.EnvObsDTO;
import system.envmanager.EnvManagerComponent;
import system.envmanager.interfaces.IEnvManager;

public class EnvManagerImpl extends EnvManagerComponent {
	
	@Override
	protected IEnvManager make_runenv() {
		return new IEnvManager() {

			EnvConfigDTO config = new EnvConfigDTO();
			EnvDTO env = new EnvDTO();

			@Override
			public void runEnv() {
				
				System.out.println("Start of: EnvManagerImpl#runEnv");
				requires().gui().createGUI();
				while (!this.config.isExit()) {
					System.out.println("--------------------------------------------");
					System.out.println("Step: RunEnv#newlap");
					requires().agentmanager().init(5);
					this.env = requires().agentmanager().executeAgents(this.env);
					this.config = requires().gui().printEnv(new EnvObsDTO());
					try {
						Thread.sleep(this.config.getSpeed());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		};
	}
		
}
