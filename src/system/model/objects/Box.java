package system.model.objects;

import system.model.ColorEnum;

public class Box {
	private ColorEnum color;

	public Box(ColorEnum color) {
		this.color = color;
	}
	
	public ColorEnum getColor() {
		return color;
	}

	public void setColor(ColorEnum color) {
		this.color = color;
	}
	
}
