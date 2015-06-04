package system.model;

public enum ColorEnum {
	RED("R"),
	GREEN("G"),
	BLUE("B");
	
	private String debug;
	
	private ColorEnum(String debug) {
		this.debug = debug;
	}
	
	public String getDebug() {
		return debug;
	}
}
