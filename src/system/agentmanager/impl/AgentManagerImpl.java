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
import system.model.ColorEnum;
import system.model.objects.Agent;
import system.model.objects.Grid;
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
					int posX = new Random().nextInt(Grid.GRID_SIZE); 
					int posY = new Random().nextInt(Grid.GRID_SIZE);
					int color = new Random().nextInt(ColorEnum.values().length);
					int posXSave = posX;
					
					while (grid[posX][posY] != null) {
						posX = (posX + 1) % Grid.GRID_SIZE;
						if (posX == posXSave)
							posY++;
					}
					Agent newAgent = new Agent(newAgentSpecies(++lastAgentid),lastAgentid,ColorEnum.values()[color]);
					grid[posX][posY] = newAgent;
					agents.add(newAgent);
				}
				
				env.getGrid().setGrid(grid);
				return env;
			}
			
			@Override
			public EnvDTO executeAgents(EnvDTO env) {
				for(Agent agent : agents) {
					System.out.println("Call to agent " + agent.getId());
					
					AgentActionDTO action = agent.getComponent().agentaction().applyToEnvironment(env, agent.getId());
					env.setGrid(action.getGrid());
				}
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
