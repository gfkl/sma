package system.persistence.impl;

import system.dto.EnvDTO;
import system.persistence.PersistenceComponent;
import system.persistence.interfaces.IPersistence;

public class PersistenceImpl extends PersistenceComponent {

	@Override
	protected IPersistence make_persistence() {
		return new IPersistence() {
			
			@Override
			public void saveContext(EnvDTO env) {
				System.out.println("Start of: PersistenceImpl#getLap");
				// TODO Persistence de l'env
				System.out.println("End of: PersistenceImpl#getLap");
				
			}
		};
	}

}
