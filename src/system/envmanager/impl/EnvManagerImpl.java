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

			public static final int GRID_SIZE = 50;
			public static final int NB_BOXES_INIT = GRID_SIZE / 2;
			public static final int LAP_SPEED_INIT = 100; // ms
			public static final int NB_AGENTS_INIT = GRID_SIZE / 2;
			public static final int AGENT_ENERGY_INIT = GRID_SIZE * 2;
			public static final int NB_LAPS_CREATE_BOX = GRID_SIZE / 2;
			public static final int NB_BOXES_TO_CREATE = GRID_SIZE / 2;
			public static final int GOOD_COLOR_BOX_ENERGY_REWARD = GRID_SIZE * 5;
			public static final int WRONG_COLOR_BOX_ENERGY_REWARD = GRID_SIZE * 2;
			public static final int ENERGY_CONSUMED_TO_CREATE = GRID_SIZE * 5;
			public static final int ENERGY_REQUIRED_TO_CREATE = GRID_SIZE * 10;
			public static final int ENERGY_CONSUMED_ACTION = 1;
			

			
			EnvConfigDTO config = new EnvConfigDTO(LAP_SPEED_INIT);
			EnvDTO env = new EnvDTO();
			int idBox = 0;
			int idNest = 0;

			
			private void initEnv() {
				Object[][] grid = new Object[GRID_SIZE][GRID_SIZE];
				
				// Creation of Nests
				grid[0][0] = new Nest(ColorEnum.RED, ++idNest);
				grid[GRID_SIZE - 1][0] = new Nest(ColorEnum.BLUE, ++idNest);
				grid[GRID_SIZE - 1][GRID_SIZE - 1] = new Nest(ColorEnum.GREEN, ++idNest);

				this.env.setGrid(new Grid(grid, GRID_SIZE));
				this.env.setEnergyInit(AGENT_ENERGY_INIT);
				this.env.setEnergyGoodColorBoxReward(GOOD_COLOR_BOX_ENERGY_REWARD);
				this.env.setEnergyWrongColorBoxReward(WRONG_COLOR_BOX_ENERGY_REWARD);
				this.env.setEnergyConcumedToCreate(ENERGY_CONSUMED_TO_CREATE);
				this.env.setEnergyRequiredToCreate(ENERGY_REQUIRED_TO_CREATE);
				this.env.setEnergyConsumedAction(ENERGY_CONSUMED_ACTION);
				
				// Creation of Boxes
				this.generateBoxes(NB_BOXES_INIT);
				
				// Creation of Agents
				this.env = requires().agentmanager().init(this.env, NB_AGENTS_INIT);

				// Creation of GUI
				requires().gui().createGUI();
			}
			
			private void generateBoxes(int nbBox) {
				Object[][] grid = this.env.getGrid().getGrid();
				
				for(int i = 0; i < nbBox; i++) {
					int posX = new Random().nextInt(GRID_SIZE); 
					int posY = new Random().nextInt(GRID_SIZE);
					int color = new Random().nextInt(ColorEnum.values().length);
					int posXSave = posX;
					
					while (grid[posX][posY] != null) {
						posX = (posX + 1) % GRID_SIZE;
						if (posX == posXSave) {
							posY = (posY + 1) % GRID_SIZE;
						}
					}
					grid[posX][posY] = new Box(ColorEnum.values()[color], ++idBox);						
				}
				
				this.env.getGrid().setGrid(grid);
			}
		
			@Override
			public void runEnv() {
				int lap = 0;
				
				initEnv();
				while (!this.config.isExit()) {
					lap++;
					if (lap % NB_LAPS_CREATE_BOX == 0)
						generateBoxes(NB_BOXES_TO_CREATE);
					
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
				for (int y = 0; y < GRID_SIZE; y++) {
					String line = "";
					for (int x = 0; x < GRID_SIZE; x++) {
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
