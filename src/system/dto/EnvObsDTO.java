package system.dto;

import system.model.objects.Grid;

public class EnvObsDTO {
	private Grid grid;
	private int idAgentSelected;

	
	public int getIdAgentSelected() {
		return idAgentSelected;
	}

	public void setIdAgentSelected(int idAgentSelected) {
		this.idAgentSelected = idAgentSelected;
	}
	
	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}
	
}
