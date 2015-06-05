package system.dto;

import system.model.ActionEnum;
import system.model.Position;


public class ActionDTO  {
	private ActionEnum action;
	private Position actionPosition;
	private Position agentPosition;
	
	public ActionDTO(ActionEnum action, Position agentPosition, Position actionPosition) {
		this.action = action;
		this.actionPosition = actionPosition;
		this.agentPosition = agentPosition;
	}

	public ActionDTO(ActionEnum action, Position agentPosition) {
		this.action = action;
		this.agentPosition = agentPosition;
	}

	public ActionEnum getAction() {
		return action;
	}

	public void setAction(ActionEnum action) {
		this.action = action;
	}

	public Position getActionPosition() {
		return actionPosition;
	}

	public void setActionPosition(Position actionPosition) {
		this.actionPosition = actionPosition;
	}

	public Position getAgentPosition() {
		return agentPosition;
	}

	public void setAgentPosition(Position agentPosition) {
		this.agentPosition = agentPosition;
	}
		
}
