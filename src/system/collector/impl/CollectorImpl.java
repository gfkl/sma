package system.collector.impl;

import system.collector.CollectorComponent;
import system.collector.interfaces.ICollector;
import system.dto.EnvDTO;
import system.dto.AgentDTO;

public class CollectorImpl extends CollectorComponent {

	@Override
	protected ICollector make_translator() {
		return new ICollector() {
			
			@Override
			public AgentDTO translate(EnvDTO env) {
				System.out.println("Start of: CollectorImpl#translate");
				AgentDTO perception =  new AgentDTO();
				// TODO traduire l'env en ce que conprend et percoit le agent
				System.out.println(">>>DEEP POINT OF WORKFLOW<<<");
				System.out.println("End of: CollectorImpl#translate");
				return perception;
			}
		};
	}

}
