package system.gui.impl;

import system.dto.EnvConfigDTO;
import system.dto.EnvDTO;
import system.dto.EnvObsDTO;
import system.gui.GuiComponent;
import system.gui.interfaces.IGui;

public class GuiImpl extends GuiComponent{

	@Override
	protected IGui make_printer() {
		return new IGui() {
			
			@Override
			public EnvConfigDTO printEnv(EnvObsDTO env) {
				// afficher a chaque appel de la méthode
				// TODO Peut influer sur l'env via les params de config
				return new EnvConfigDTO();
			}

			@Override
			public void createGUI() {
				// TODO Creer la GUI				
			}
		};
	}

}
