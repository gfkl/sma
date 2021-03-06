package system.agentmanager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import system.agentmanager.AgentManagerComponent;
import system.agentmanager.interfaces.IAgentManager;
import system.collector.CollectorComponent;
import system.collector.impl.CollectorImpl;
import system.decisionmaker.DecisionMakerComponent;
import system.decisionmaker.impl.DecisionMakerImpl;
import system.dto.AgentActionDTO;
import system.dto.EnvDTO;
import system.effector.EffectorComponent;
import system.effector.impl.EffectorImpl;
import system.log.LogComponent;
import system.log.impl.LogImpl;
import system.model.ActionEnum;
import system.model.ColorEnum;
import system.model.objects.Agent;
import system.persistence.PersistenceComponent;
import system.persistence.impl.PersistenceImpl;

public class AgentManagerImpl extends AgentManagerComponent {

	private List<Agent> agents = new ArrayList<>();
	private int lastAgentid = 0;


	@Override
	protected LogComponent make_log() {
		// TODO Auto-generated method stub
		return new LogImpl();
	}

	@Override
	protected PersistenceComponent make_persistence() {
		// TODO Auto-generated method stub
		return new PersistenceImpl();
	}
	
	@Override
	protected IAgentManager make_executeagents() {
		return new IAgentManager() {
			

			@Override
			public EnvDTO init(EnvDTO env, int nbAgents) {
				Object[][] grid = env.getGrid().getGrid();
				
				for(int i = 0; i < nbAgents; i++) {
					int posX = new Random().nextInt(env.getGrid().getGridSize()); 
					int posY = new Random().nextInt(env.getGrid().getGridSize());
					int color = new Random().nextInt(ColorEnum.values().length);
					int posXSave = posX;
					
					while (grid[posX][posY] != null) {
						posX = (posX + 1) % env.getGrid().getGridSize();
						if (posX == posXSave)
							posY++;
					}
					Agent newAgent = new Agent(newAgentSpecies(++lastAgentid),lastAgentid,ColorEnum.values()[color], env.getEnergyInit());
					grid[posX][posY] = newAgent;
					agents.add(newAgent);
				}
				
				
				env.getGrid().setGrid(grid);
				return env;
			}
			
			@Override
			public EnvDTO executeAgents(EnvDTO env) {
				List<Agent> agentsToKill = new ArrayList<Agent>();
				int agentsToCreate = 0;
				
				for(Agent agent : agents) {
					AgentActionDTO action = agent.getComponent().agentaction().applyToEnvironment(env, agent.getId());

					if (ActionEnum.DIE.equals(action.getAction())) {
						agentsToKill.add(agent);
					}
					if (ActionEnum.CREATE.equals(action.getAction())) {
						agentsToCreate++;
					}
					
					if(env.getIdAgentFollow() != -1 && agent.getId() == env.getIdAgentFollow()){
						action.setAgent(agent);
						parts().log().log().log(action);
					}
					
					env.setGrid(action.getGrid());
				}

				// Kill agents
				if (!agentsToKill.isEmpty()) {
					Object[][] grid = env.getGrid().getGrid();
					for (int x = 0; x < env.getGrid().getGridSize(); x++) {
						for (int y = 0; y < env.getGrid().getGridSize(); y++) {
							if (grid[x][y] != null && grid[x][y] instanceof Agent) {					
								for (Agent agent : agentsToKill) {
									if (agent.equals(((Agent) grid[x][y]))) {
										agents.remove(agent);
										grid[x][y] = null;
									}
								}
							}
						}
					}
					agentsToKill.clear();
				}
				
				if (agentsToCreate > 0)
					init(env, agentsToCreate);
				
				parts().persistence().persistence().saveContext(env);
				return env;
			}
		};
	}

	@Override
	protected AgentSpecies make_AgentSpecies(int id) {
		return new AgentSpecies() {

			@Override
			protected DecisionMakerComponent make_decisionmaker() {
				return new DecisionMakerImpl();
			}

			@Override
			protected CollectorComponent make_collector() {
				return new CollectorImpl();
			}

			@Override
			protected EffectorComponent make_effector() {
				return new EffectorImpl();
			}
			
		};
	}


}
