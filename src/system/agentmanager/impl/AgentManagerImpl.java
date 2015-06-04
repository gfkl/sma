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
import system.persistence.PersistenceComponent;
import system.persistence.impl.PersistenceImpl;

public class AgentManagerImpl extends AgentManagerComponent {

	private List<AgentSpecies.Component> agents;


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
				agents = new ArrayList<AgentSpecies.Component>();
				for (int i = 0; i < nbAgents; i++) {
					agents.add(newAgentSpecies(i));
				}
			}
			
			@Override
			public EnvDTO executeAgents(EnvDTO env) {
				for(AgentSpecies.Component agent : agents) {
					System.out.println("Call to agent");
					agent.agentaction().applyToEnvironment(null);
				}
				return null;
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
