package system.persistence.impl;

import system.dto.EnvParamDTO;
import system.dto.EnvironmentDTO;
import system.persistence.PersistenceComponent;
import system.persistence.interfaces.IPersistence;

public class PersistenceImpl extends PersistenceComponent {

	@Override
	protected IPersistence make_persistence() {
		return new IPersistence() {
			
			@Override
			public EnvironmentDTO getLap(EnvParamDTO params) {
				System.out.println("Start of: PersistenceImpl#getLap");
				EnvironmentDTO env = requires().lap().runLap(params);
				// TODO Persistence de l'env
				System.out.println("End of: PersistenceImpl#getLap");
				return env;
			}
		};
	}

}
