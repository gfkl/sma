package system.model.objects;

import system.model.ColorEnum;

public class Nest {
	private ColorEnum color;
	private int id;
	
	public Nest(ColorEnum color, int id) {
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
		Nest other = (Nest) obj;
		if (color != other.color)
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
