package system.model.objects;

import system.agentmanager.AgentManagerComponent.AgentSpecies;
import system.model.ColorEnum;

public class Agent {
	private AgentSpecies.Component component;
	private int id;
	private Box	boxTransported;
	private ColorEnum color;
	private int energy;
	private boolean transportingBox;
	
	public Agent(AgentSpecies.Component comp, int id, ColorEnum color, int energy) {
		this.component = comp;
		this.id = id;
		this.color = color;
		this.boxTransported = null;
		this.energy = energy;
		this.transportingBox = false;
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
		if (boxTransported == null)
			this.transportingBox = false;
		else
			this.transportingBox = true;
	}
	
	public ColorEnum getColor() {
		return color;
	}
	
	public void setColor(ColorEnum color) {
		this.color = color;
	}
	
	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public boolean isTransportingBox() {
		return transportingBox;
	}

	public void setTransportingBox(boolean transportingBox) {
		this.transportingBox = transportingBox;
	}

}
