package system.dto;

import system.model.ActionEnum;
import system.model.objects.Grid;

public class AgentActionDTO {
	private Grid grid;
	private ActionEnum action;

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public ActionEnum getAction() {
		return action;
	}

	public void setAction(ActionEnum action) {
		this.action = action;
	}

}
