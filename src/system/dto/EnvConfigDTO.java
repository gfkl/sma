package system.dto;

public class EnvConfigDTO {

	// int ms
	private int	speed;
	
	private boolean exit;
	
	public EnvConfigDTO() {
		speed = 1000;
		this.exit = false;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public boolean isExit() {
		return exit;
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}
}
