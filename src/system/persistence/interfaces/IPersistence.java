package system.persistence.interfaces;

import system.dto.EnvDTO;

public interface IPersistence {
	void saveContext(EnvDTO env);
}
