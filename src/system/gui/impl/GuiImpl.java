package system.gui.impl;

import system.dto.EnvParamDTO;
import system.dto.EnvironmentDTO;
import system.gui.GuiComponent;
import system.gui.interfaces.IGui;

public class GuiImpl extends GuiComponent{

	@Override
	protected IGui make_gui() {
		return new IGui() {
			
			@Override
			public void runGui() {
				System.out.println("Start of: GuiImpl#runGui");
				// TODO Creer la GUI
				// TODO Peut influer sur l'en via les params
				EnvParamDTO params = new EnvParamDTO();
				EnvironmentDTO env = requires().lap().log(params);
				System.out.println("End of: GuiImpl#runGui");
			}
		};
	}

}
