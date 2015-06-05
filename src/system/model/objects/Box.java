package system.model.objects;

import system.model.ColorEnum;

public class Box {
	private ColorEnum color;
	private int id;

	public Box(ColorEnum color, int id) {
		this.color = color;
		this.id = id;
	}
	
	public ColorEnum getColor() {
		return color;
	}

	public void setColor(ColorEnum color) {
		this.color = color;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Box other = (Box) obj;
		if (color != other.color)
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	
}
