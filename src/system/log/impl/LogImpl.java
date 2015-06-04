package system.log.impl;

import system.dto.ActionDTO;
import system.log.LogComponent;
import system.log.interfaces.ILog;

public class LogImpl extends LogComponent{

	@Override
	protected ILog make_log() {
		return new ILog() {
			
			@Override
			public void log(ActionDTO action) {
				System.out.println("Start of: LogImpl#log");
				// TODO Créer le systeme de log
				System.out.println("End of: LogImpl#log");
				
			}
		};
	}

}
