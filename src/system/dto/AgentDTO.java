package system.dto;

import java.util.HashMap;
import java.util.Map;

import system.model.Position;
import system.model.objects.Agent;
import system.model.objects.Box;
import system.model.objects.Nest;

public class AgentDTO {
	
	private Agent agent;
	private Position position;
	private Map<Agent, Position> otherAgents = new HashMap<Agent, Position>();
	private Map<Nest, Position> nests = new HashMap<Nest, Position>();
	private Map<Box, Position> boxes = new HashMap<Box, Position>();
	
	public Agent getAgent() {
		return agent;
	}
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	public Map<Agent, Position> getOtherAgents() {
		return otherAgents;
	}
	public void setOtherAgents(Map<Agent, Position> otherAgents) {
		this.otherAgents = otherAgents;
	}
	public Map<Nest, Position> getNests() {
		return nests;
	}
	public void setNests(Map<Nest, Position> nests) {
		this.nests = nests;
	}
	public Map<Box, Position> getBoxes() {
		return boxes;
	}
	public void setBoxes(Map<Box, Position> boxes) {
		this.boxes = boxes;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	
}
