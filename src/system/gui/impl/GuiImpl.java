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
				System.out.println("Start of: GuiImpl#runGui");
				// afficher a chaque appel de la m�thode
				// TODO Peut influer sur l'env via les params de config
				System.out.println("End of: GuiImpl#runGui");
				return new EnvConfigDTO();
			}

			@Override
			public void createGUI() {
				System.out.println("Start of: GuiImpl#createGui");
				// TODO Creer la GUI
				
			}
		};
	}

}