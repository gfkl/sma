package system.dto;

import system.model.ActionEnum;
import system.model.objects.Agent;
import system.model.objects.Grid;

public class AgentActionDTO {
	private Grid grid;
	private ActionEnum action;
	private Agent agent;

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

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

}
