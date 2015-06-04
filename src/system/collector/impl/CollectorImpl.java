package system.collector.impl;

import system.collector.CollectorComponent;
import system.collector.interfaces.ICollector;
import system.dto.AgentDTO;
import system.dto.EnvDTO;
import system.model.Position;
import system.model.objects.Agent;
import system.model.objects.Box;
import system.model.objects.Grid;
import system.model.objects.Nest;

public class CollectorImpl extends CollectorComponent {

	@Override
	protected ICollector make_translator() {
		return new ICollector() {
			
			@Override
			// Give to the agent what it is allowed to see 
			public AgentDTO translate(EnvDTO env, int id) {
				AgentDTO agentPerception =  new AgentDTO();
				Object[][] grid = env.getGrid().getGrid();
				
				for (int x = 0; x < Grid.GRID_SIZE; x++) {
					for (int y = 0; y < Grid.GRID_SIZE; y++) {
						if (grid[x][y] instanceof Agent) {
							Agent agent = ((Agent) grid[x][y]);
							
							if (agent.getId() == id) {
								agentPerception.setAgent(agent);
								agentPerception.setPosition(new Position(x, y));
							} else {
								agentPerception.getOtherAgents().put(agent, new Position(x, y));
							}
						}
						if (grid[x][y] instanceof Nest) {
							Nest nest = ((Nest) grid[x][y]);
							agentPerception.getNests().put(nest, new Position(x, y));
						}
						if (grid[x][y] instanceof Box) {
							Box box = ((Box) grid[x][y]);
							agentPerception.getBoxes().put(box, new Position(x, y));
						}						
					}
				}

				return agentPerception;
			}
		};
	}

}
