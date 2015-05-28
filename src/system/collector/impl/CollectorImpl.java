package system.collector.impl;

import system.collector.CollectorComponent;
import system.collector.interfaces.ICollector;
import system.dto.EnvironmentDTO;
import system.dto.RobotDTO;

public class CollectorImpl extends CollectorComponent {

	@Override
	protected ICollector make_translator() {
		return new ICollector() {
			
			@Override
			public RobotDTO translate(EnvironmentDTO env) {
				System.out.println("Start of: CollectorImpl#translate");
				RobotDTO perception =  new RobotDTO();
				// TODO
				System.out.println("End of: CollectorImpl#translate");
				return perception;
			}
		};
	}

}
