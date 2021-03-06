package system.effector.impl;

import system.dto.ActionDTO;
import system.dto.AgentActionDTO;
import system.dto.EnvDTO;
import system.effector.EffectorComponent;
import system.effector.interfaces.IEffector;
import system.model.objects.Agent;
import system.model.objects.Box;
import system.model.objects.Grid;

public class EffectorImpl extends EffectorComponent {

	@Override
	protected IEffector make_applier() {
		return new IEffector() {
			
			@Override
			public AgentActionDTO applyToEnvironment(EnvDTO env, int id) {
				ActionDTO action = requires().decision().makeDecision(env, id);
				AgentActionDTO agentAction = new AgentActionDTO();
				Object[][] grid = env.getGrid().getGrid();
				Agent agent = ((Agent) grid[action.getAgentPosition().getX()][action.getAgentPosition().getY()]);

				switch (action.getAction()) {
					case MOVE:
						grid[action.getAgentPosition().getX()][action.getAgentPosition().getY()] = null;
						grid[action.getActionPosition().getX()][action.getActionPosition().getY()] = agent;

						agent.setEnergy(agent.getEnergy() - env.getEnergyConsumedAction());						
						break;
						
					case PUT:
						if (agent.getColor().equals(agent.getBoxTransported().getColor())) {
							agent.setEnergy(agent.getEnergy() + env.getEnergyGoodColorBoxReward());
						} else {
							agent.setEnergy(agent.getEnergy() + env.getEnergyWrongColorBoxReward());
						}

						agent.setBoxTransported(null);
						
						agent.setEnergy(agent.getEnergy() - env.getEnergyConsumedAction());
						break;

					case GET:
						Box box = ((Box) grid[action.getActionPosition().getX()][action.getActionPosition().getY()]);
						agent.setBoxTransported(box);
						grid[action.getActionPosition().getX()][action.getActionPosition().getY()] = null;

						agent.setEnergy(agent.getEnergy() - env.getEnergyConsumedAction());
						break;

					case CREATE:
						agent.setEnergy(agent.getEnergy() - env.getEnergyConcumedToCreate());
						break;

					case DIE:
						break;

					case NOTHING:
						break;

					default: 
						System.err.println("Error: Agent " + id + ": Action not found");
						break;
				}
				agentAction.setAction(action.getAction());
				agentAction.setGrid(new Grid(grid, env.getGrid().getGridSize()));
				
				return agentAction;
			}
		};
	}

}
