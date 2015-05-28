package system;

import system.impl.SystemImpl;

public class Main {

	public static void main(String[] args) {
		SystemComponent.Component system = new SystemImpl().newComponent();
		system.run().runGui();
	}

}
