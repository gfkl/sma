package system.model.objects;

public class Grid {
	public static final int GRID_SIZE = 10;
	
	private Object[][] grid;
	
	public Grid(Object[][] grid) {
		this.grid = grid;
	}

	public Object[][] getGrid() {
		return grid;
	}

	public void setGrid(Object[][] grid) {
		this.grid = grid;
	}

}
