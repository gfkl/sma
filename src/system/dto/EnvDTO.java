package system.dto;

import system.model.objects.Grid;

public class EnvDTO {
	
	private int idAgentFollow;
	private Grid grid;
	private int energyInit;
	private int energyWrongColorBoxReward;
	private int energyGoodColorBoxReward;
	private int energyConcumedToCreate;
	private int energyRequiredToCreate;
	private int energyConsumedAction;
	
	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public int getEnergyInit() {
		return energyInit;
	}

	public void setEnergyInit(int energyInit) {
		this.energyInit = energyInit;
	}

	public int getEnergyWrongColorBoxReward() {
		return energyWrongColorBoxReward;
	}

	public void setEnergyWrongColorBoxReward(int energyWrongColorBoxReward) {
		this.energyWrongColorBoxReward = energyWrongColorBoxReward;
	}

	public int getEnergyGoodColorBoxReward() {
		return energyGoodColorBoxReward;
	}

	public void setEnergyGoodColorBoxReward(int energyGoodColorBoxReward) {
		this.energyGoodColorBoxReward = energyGoodColorBoxReward;
	}

	public int getEnergyConcumedToCreate() {
		return energyConcumedToCreate;
	}

	public void setEnergyConcumedToCreate(int energyConcumedToCreate) {
		this.energyConcumedToCreate = energyConcumedToCreate;
	}

	public int getEnergyRequiredToCreate() {
		return energyRequiredToCreate;
	}

	public void setEnergyRequiredToCreate(int energyRequiredToCreate) {
		this.energyRequiredToCreate = energyRequiredToCreate;
	}

	public int getEnergyConsumedAction() {
		return energyConsumedAction;
	}

	public void setEnergyConsumedAction(int energyConsumedAction) {
		this.energyConsumedAction = energyConsumedAction;
	}
	
	public int getIdAgentFollow() {
		return idAgentFollow;
	}

	public void setIdAgentFollow(int idAgentFollow) {
		this.idAgentFollow = idAgentFollow;
	}

	
	
}
