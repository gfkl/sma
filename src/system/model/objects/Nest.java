package system.model.objects;

import system.model.ColorEnum;

public class Nest {
	private ColorEnum color;
	
	public Nest(ColorEnum color) {
		this.color = color;
	}

	public ColorEnum getColor() {
		return color;
	}

	public void setColor(ColorEnum color) {
		this.color = color;
	}

}
