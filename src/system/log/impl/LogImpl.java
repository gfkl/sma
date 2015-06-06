package system.log.impl;

import system.dto.AgentActionDTO;
import system.log.LogComponent;
import system.log.interfaces.ILog;

public class LogImpl extends LogComponent{

	@Override
	protected ILog make_log() {
		return new ILog() {
			
			@Override
			public void log(AgentActionDTO action) {
				System.out.println("Agent Action : "+action.getAction().toString());
			}
		};
	}

}
