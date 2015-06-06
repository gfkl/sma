package system.log.impl;

import system.dto.AgentActionDTO;
import system.log.LogComponent;
import system.log.interfaces.ILog;
import system.model.objects.Agent;

public class LogImpl extends LogComponent{

	@Override
	protected ILog make_log() {
		return new ILog() {

			@Override
			public void log(AgentActionDTO action) {
				System.out.println("Agent id :"+action.getAgent().getId());
				System.out.println("Agent color :"+action.getAgent().getColor().name());
				System.out.println("Agent Action : "+action.getAction().toString());

				Object[][] grid = action.getGrid().getGrid();
				for (int x = 0; x < action.getGrid().getGridSize(); x++) {
					for (int y = 0; y < action.getGrid().getGridSize(); y++) {
						if (grid[x][y] != null && grid[x][y] instanceof Agent) {					
							if (action.getAgent().equals(((Agent) grid[x][y])))
									System.out.println("Agent position [X:Y] ["+ x+":"+y+"]");
							
						}
					}
				}
			}
		};
	}

}
