package system.model.objects;

public class Grid {
	
	private Object[][] grid;
	private int gridSize;
	
	public Grid(Object[][] grid, int gridSize) {
		this.grid = grid;
		this.setGridSize(gridSize);
	}

	public Object[][] getGrid() {
		return grid;
	}

	public void setGrid(Object[][] grid) {
		this.grid = grid;
	}

	public int getGridSize() {
		return gridSize;
	}

	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}

}
