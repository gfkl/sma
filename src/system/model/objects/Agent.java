package system.model.objects;

import system.agentmanager.AgentManagerComponent.AgentSpecies;
import system.model.ColorEnum;

public class Agent {
	private AgentSpecies.Component component;
	private int id;
	private Box	boxTransported;
	private ColorEnum color;
	
	public Agent(AgentSpecies.Component comp, int id, ColorEnum color) {
		this.component = comp;
		this.id = id;
		this.color = color;
		this.boxTransported = null;
	}
	
	public AgentSpecies.Component getComponent() {
		return component;
	}
	
	public void setComponent(AgentSpecies.Component component) {
		this.component = component;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Box getBoxTransported() {
		return boxTransported;
	}
	
	public void setBoxTransported(Box boxTransported) {
		this.boxTransported = boxTransported;
	}
	
	public ColorEnum getColor() {
		return color;
	}
	
	public void setColor(ColorEnum color) {
		this.color = color;
	}
	
}
