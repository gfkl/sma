package system.log.impl;

import system.dto.EnvParamDTO;
import system.dto.EnvironmentDTO;
import system.log.LogComponent;
import system.log.interfaces.ILog;

public class LogImpl extends LogComponent{

	@Override
	protected ILog make_log() {
		return new ILog() {
			
			@Override
			public EnvironmentDTO log(EnvParamDTO params) {
				System.out.println("Start of: LogImpl#log");
				EnvironmentDTO env = requires().lap().getLap(params);
				// TODO Créer le systeme de log
				System.out.println("End of: LogImpl#log");
				return env;
			}
		};
	}

}
