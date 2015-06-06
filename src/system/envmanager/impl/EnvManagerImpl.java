package system.envmanager.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
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

			// System configuration. You can update values to experiment agent behaviors
			public int GRID_SIZE = 50;
			public int NB_BOXES_INIT = GRID_SIZE / 2;
			public int LAP_SPEED_INIT = 100; // ms
			public int NB_AGENTS_INIT = GRID_SIZE / 2;
			public int AGENT_ENERGY_INIT = GRID_SIZE * 2;
			public int NB_LAPS_CREATE_BOX = GRID_SIZE / 2;
			public int NB_BOXES_TO_CREATE = GRID_SIZE / 2;
			public int GOOD_COLOR_BOX_ENERGY_REWARD = GRID_SIZE * 5;
			public int WRONG_COLOR_BOX_ENERGY_REWARD = GRID_SIZE * 2;
			public int ENERGY_CONSUMED_TO_CREATE = GRID_SIZE * 5;
			public int ENERGY_REQUIRED_TO_CREATE = GRID_SIZE * 10;
			public int ENERGY_CONSUMED_ACTION = 1;
			public boolean DEBUG_PRINTER = false;



			EnvConfigDTO config = new EnvConfigDTO(LAP_SPEED_INIT);
			EnvDTO env = new EnvDTO();
			int idBox = 0;
			int idNest = 0;


			private void initEnv() {
				try {
					getPropValues();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Object[][] grid = new Object[GRID_SIZE][GRID_SIZE];

				// Creation of Nests
				grid[0][0] = new Nest(ColorEnum.RED, ++idNest);
				grid[GRID_SIZE - 1][0] = new Nest(ColorEnum.BLUE, ++idNest);
				grid[GRID_SIZE - 1][GRID_SIZE - 1] = new Nest(ColorEnum.GREEN, ++idNest);

				// Set env for components
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

					// Boxes generator
					lap++;
					if (lap % NB_LAPS_CREATE_BOX == 0)
						generateBoxes(NB_BOXES_TO_CREATE);

					// Execute agents
					this.env = requires().agentmanager().executeAgents(this.env);

					// Build env for GUI
					EnvObsDTO envObs = new EnvObsDTO();
					envObs.setGrid(new Grid(this.env.getGrid().getGrid(), GRID_SIZE));
					this.config = requires().gui().printEnv(envObs);

					this.env.setIdAgentFollow(envObs.getIdAgentSelected());

					// Debug trace of the grid
					if (DEBUG_PRINTER) {
						debugPrintEnv();
					}

					// timer
					try {
						Thread.sleep(this.config.getSpeed());
					} catch (InterruptedException e) {
						System.err.println("Tread sleep failed");
					}
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


			public String getPropValues() throws IOException {

				String result = "";
				Properties prop = new Properties();
				String propFileName = "config.properties";

				InputStream inputStream = new FileInputStream(propFileName);

				if (inputStream != null) {
					prop.load(inputStream);
				} else {
					throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
				}

				Date time = new Date(System.currentTimeMillis());

				// get the property value and print it out
				//i = Integer.parseInt(phrase); 
				int GRID_SIZE = Integer.parseInt(prop.getProperty("GRID_SIZE")); 
				int LAP_SPEED_INIT = Integer.parseInt(prop.getProperty("LAP_SPEED_INIT")); 
				int ENERGY_CONSUMED_ACTION = Integer.parseInt(prop.getProperty("ENERGY_CONSUMED_ACTION")); 
				int NB_BOXES_INIT = Integer.parseInt(prop.getProperty("NB_BOXES_INIT")); 
				int NB_AGENTS_INIT = Integer.parseInt(prop.getProperty("NB_AGENTS_INIT")); 
				int AGENT_ENERGY_INIT = Integer.parseInt(prop.getProperty("AGENT_ENERGY_INIT")); 
				int NB_LAPS_CREATE_BOX = Integer.parseInt(prop.getProperty("NB_LAPS_CREATE_BOX")); 
				int NB_BOXES_TO_CREATE = Integer.parseInt(prop.getProperty("NB_BOXES_TO_CREATE")); 
				int GOOD_COLOR_BOX_ENERGY_REWARD = Integer.parseInt(prop.getProperty("GOOD_COLOR_BOX_ENERGY_REWARD")); 
				int WRONG_COLOR_BOX_ENERGY_REWARD = Integer.parseInt(prop.getProperty("WRONG_COLOR_BOX_ENERGY_REWARD")); 
				int ENERGY_CONSUMED_TO_CREATE = Integer.parseInt(prop.getProperty("ENERGY_CONSUMED_TO_CREATE")); 
				int ENERGY_REQUIRED_TO_CREATE = Integer.parseInt(prop.getProperty("ENERGY_REQUIRED_TO_CREATE")); 
				String DEBUG_PRINTER = prop.getProperty("DEBUG_PRINTER"); 
				
				this.GRID_SIZE = GRID_SIZE;
				this.LAP_SPEED_INIT = LAP_SPEED_INIT;
				this.ENERGY_CONSUMED_ACTION = ENERGY_CONSUMED_ACTION;
				if(DEBUG_PRINTER.equals("false"))
					this.DEBUG_PRINTER = false;
				else
					this.DEBUG_PRINTER = true;

				this.NB_BOXES_INIT = NB_BOXES_INIT;
				this.NB_AGENTS_INIT = NB_AGENTS_INIT;
				this.AGENT_ENERGY_INIT = AGENT_ENERGY_INIT;
				this.NB_LAPS_CREATE_BOX = NB_LAPS_CREATE_BOX;
				this.NB_BOXES_TO_CREATE = NB_BOXES_TO_CREATE;
				this.GOOD_COLOR_BOX_ENERGY_REWARD = GOOD_COLOR_BOX_ENERGY_REWARD;
				this.WRONG_COLOR_BOX_ENERGY_REWARD = WRONG_COLOR_BOX_ENERGY_REWARD;
				this.ENERGY_CONSUMED_TO_CREATE = ENERGY_CONSUMED_TO_CREATE;
				this.ENERGY_REQUIRED_TO_CREATE = ENERGY_REQUIRED_TO_CREATE;

				return result;
			}


		};
	}

}
