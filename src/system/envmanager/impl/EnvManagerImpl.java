package system.envmanager.impl;

import java.util.Random;

import system.dto.EnvConfigDTO;
import system.dto.EnvDTO;
import system.dto.EnvObsDTO;
import system.envmanager.EnvManagerComponent;
import system.envmanager.interfaces.IEnvManager;
import system.model.ColorEnum;
import system.model.objects.Agent;
import system.model.objects.Box;
import system.model.objects.Grid;
import system.model.objects.Nest;

public class EnvManagerImpl extends EnvManagerComponent {
		
	@Override
	protected IEnvManager make_runenv() {
		return new IEnvManager() {

			EnvConfigDTO config = new EnvConfigDTO();
			EnvDTO env = new EnvDTO();
			int idBox = 0;
			int idNest = 0;

			private void initComponents() {
				
			}
			
			private void initEnv() {
				Object[][] grid = new Object[Grid.GRID_SIZE][Grid.GRID_SIZE];
				
				// Creation of Nests
				;
				grid[0][0] = new Nest(ColorEnum.RED, ++idNest);
				grid[Grid.GRID_SIZE - 1][0] = new Nest(ColorEnum.BLUE, ++idNest);
				grid[Grid.GRID_SIZE - 1][Grid.GRID_SIZE - 1] = new Nest(ColorEnum.GREEN, ++idNest);

				this.env.setGrid(new Grid(grid));
				
				// Creation of Boxes
				this.generateBoxes(Grid.GRID_SIZE / 2);
				
				// Creation of Agents
				this.env = requires().agentmanager().init(this.env, 5);

				// Creation of GUI
				requires().gui().createGUI();
			}
			
			private void generateBoxes(int nbBox) {
				Object[][] grid = this.env.getGrid().getGrid();
				
				for(int i = 0; i < nbBox; i++) {
					int posX = new Random().nextInt(Grid.GRID_SIZE); 
					int posY = new Random().nextInt(Grid.GRID_SIZE);
					int color = new Random().nextInt(ColorEnum.values().length);
					int posXSave = posX;
					
					while (grid[posX][posY] != null) {
						posX = (posX + 1) % Grid.GRID_SIZE;
						if (posX == posXSave)
							posY++;
					}
					grid[posX][posY] = new Box(ColorEnum.values()[color], ++idBox);						
				}
				
				this.env.getGrid().setGrid(grid);
			}
		
			@Override
			public void runEnv() {
				int lap = 0;
				
				initComponents();
				initEnv();
				while (!this.config.isExit()) {
					lap++;
					if (lap % 5 == 0)
						generateBoxes(1);
					
					this.env = requires().agentmanager().executeAgents(this.env);
					this.config = requires().gui().printEnv(new EnvObsDTO());
					try {
						Thread.sleep(this.config.getSpeed());
					} catch (InterruptedException e) {
						System.err.println("Tread sleep failed");
					}
					debugPrintEnv();
				}
			}
			
			private void debugPrintEnv() {
				System.out.println("\n----------------------------------------------\n");
				for (int y = 0; y < Grid.GRID_SIZE; y++) {
					String line = "";
					for (int x = 0; x < Grid.GRID_SIZE; x++) {
						line += "[";

						if (this.env.getGrid().getGrid()[x][y] == null) {
							line += "  ";
						} else if (this.env.getGrid().getGrid()[x][y] instanceof Box) {
							line += "B" + ((Box)env.getGrid().getGrid()[x][y]).getColor().getDebug();
						} else if (this.env.getGrid().getGrid()[x][y] instanceof Nest) {
							line += "N" + ((Nest)env.getGrid().getGrid()[x][y]).getColor().getDebug();
						} else if (this.env.getGrid().getGrid()[x][y] instanceof Agent) {
							line += "A" + ((Agent)env.getGrid().getGrid()[x][y]).getColor().getDebug();
						} else {
							line += "##";
						}
						
						line += "]";
					}
					System.out.println(line);
				}
			}
			
			
		};
	}
		
}
