package system.agentmanager.impl;

import java.util.ArrayList;
import java.util.List;

import system.agentmanager.AgentManagerComponent;
import system.agentmanager.interfaces.IAgentManager;
import system.collector.CollectorComponent;
import system.collector.impl.CollectorImpl;
import system.decisionmaker.DecisionMakerComponent;
import system.decisionmaker.impl.DecisionMakerImpl;
import system.dto.EnvDTO;
import system.effector.EffectorComponent;
import system.effector.impl.EffectorImpl;
import system.log.LogComponent;
import system.log.impl.LogImpl;
import system.model.ColorEnum;
import system.model.objects.Agent;
import system.persistence.PersistenceComponent;
import system.persistence.impl.PersistenceImpl;

public class AgentManagerImpl extends AgentManagerComponent {

	private List<Agent> agents;


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
			public void init(int nbAgents) {
				agents = new ArrayList<Agent>();
				for (int i = 0; i < nbAgents; i++) {
					agents.add(new Agent(newAgentSpecies(i), i, ColorEnum.RED));
				}
			}
			
			@Override
			public EnvDTO executeAgents(EnvDTO env) {
				int id = 1;
				for(Agent agent : agents) {
					System.out.println("Call to agent " + agent.getId());
					env = agent.getComponent().agentaction().applyToEnvironment(env, id);
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
