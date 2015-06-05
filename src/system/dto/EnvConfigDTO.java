package system.dto;

public class EnvConfigDTO {

	// int ms
	private int	speed;
	
	private boolean exit;
	
	public EnvConfigDTO(int speed) {
		this.exit = false;
		this.speed = speed;
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
