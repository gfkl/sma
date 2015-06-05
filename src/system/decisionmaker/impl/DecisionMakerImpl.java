package system.decisionmaker.impl;

import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.xalan.internal.xsltc.dom.AbsoluteIterator;

import system.decisionmaker.DecisionMakerComponent;
import system.decisionmaker.interfaces.IDecisionMaker;
import system.dto.ActionDTO;
import system.dto.AgentDTO;
import system.dto.EnvDTO;
import system.model.ActionEnum;
import system.model.Position;
import system.model.objects.Agent;
import system.model.objects.Box;
import system.model.objects.Grid;
import system.model.objects.Nest;

public class DecisionMakerImpl extends DecisionMakerComponent {

	@Override
	protected IDecisionMaker make_decision() {
		return new IDecisionMaker() {

			private Position getNearestPosition(Position start, List<Position> stops) {

				Position bestStop = null;
				int bestSteps = -1;
				
				for (Position stop : stops) {
					int x = stop.getX() - start.getX();
					int y = stop.getY() - start.getY();
					int steps = 0;
					
					steps = (Math.abs(x) > Math.abs(y)) ? Math.abs(y) : Math.abs(x);
					steps += Math.abs(Math.abs(x) - Math.abs(y));
					
					if (bestSteps == -1 || bestSteps > steps) {
						bestStop = stop;
						bestSteps = steps;
					}
				}
				
				return bestStop;
			}
			
			private boolean isAvailable(Object[][] grid, int x, int y, int gridSize) {
				
				if (x < 0 || y < 0)
					return false;
				
				if (x >= gridSize || y >= gridSize)
					return false;
				
				if (grid[x][y] != null) {
					return false;
				}
				
				return true;
			}
			
			private Position getNextPosition(Position start, Position stop, Object[][] grid, int gridSize) {
				
				Position newPos = null;
				
				if (stop == null)
					return null;

				// Go to SW
				if (start.getX() > stop.getX() && start.getY() > stop.getY()) {
					newPos = new Position(start.getX() - 1, start.getY() - 1);
					if (newPos.equals(stop)) {
						return newPos;
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos.setX(newPos.getX() + 1);
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos.setX(newPos.getX() - 1);
						newPos.setY(newPos.getY() + 1);
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos.setY(newPos.getY() + 1);							
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos.setX(newPos.getX() + 2);
						newPos.setY(newPos.getY() - 2);
					} 
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos = null;
					}
				}

				// Go to S
				if (start.getX() == stop.getX() && start.getY() > stop.getY()) {
					newPos = new Position(start.getX(), start.getY() - 1);
					if (newPos.equals(stop)) {
						return newPos;
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos.setX(newPos.getX() - 1);
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos.setX(newPos.getX() + 2);
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos.setY(newPos.getY() + 1);
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos.setX(newPos.getX() - 2);
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos = null;
					}
				}	
				
				// GO to SE
				if (start.getX() < stop.getX() && start.getY() > stop.getY()) {
					newPos = new Position(start.getX() + 1, start.getY() - 1);
					if (newPos.equals(stop)) {
						return newPos;
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos.setX(newPos.getX() - 1);
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos.setX(newPos.getX() + 1);
						newPos.setY(newPos.getY() + 1);
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos.setY(newPos.getY() + 1);
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos.setX(newPos.getX() - 2);
						newPos.setY(newPos.getY() - 2);
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos = null;
					}
				}

				// Go to E
				if (start.getX() < stop.getX() && start.getY() == stop.getY()) {
					newPos = new Position(start.getX() + 1, start.getY());
					if (newPos.equals(stop)) {
						return newPos;
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos.setY(newPos.getY() - 1);						
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos.setY(newPos.getY() + 2);						
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos.setX(newPos.getX() - 1);	
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos.setY(newPos.getY() - 2);
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos = null;
					}
				}
				
				// Go to NE
				if (start.getX() < stop.getX() && start.getY() < stop.getY()) {
					newPos = new Position(start.getX() + 1, start.getY() + 1);
					if (newPos.equals(stop)) {
						return newPos;
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos.setY(newPos.getY() - 1);
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos.setX(newPos.getX() - 1);
						newPos.setY(newPos.getY() + 1);
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos.setX(newPos.getX() - 1);
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos.setX(newPos.getX() + 2);
						newPos.setY(newPos.getY() - 2);
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos = null;
					}
				}
				
				// Go to N
				if (start.getX() == stop.getX() && start.getY() < stop.getY()) {
					newPos = new Position(start.getX(), start.getY() + 1);
					if (newPos.equals(stop)) {
						return newPos;
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos.setX(newPos.getX() - 1);
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos.setX(newPos.getX() + 2);
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos.setY(newPos.getY() - 1);
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos.setX(newPos.getX() - 2);
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos = null;
					}
				}
				
				// Go to NW
				if (start.getX() > stop.getX() && start.getY() < stop.getY()) {
					newPos = new Position(start.getX() - 1, start.getY() + 1);
					if (newPos.equals(stop)) {
						return newPos;
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos.setX(newPos.getX() + 1);
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos.setX(newPos.getX() - 1);
						newPos.setY(newPos.getY() - 1);
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos.setY(newPos.getY() - 1);
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos.setX(newPos.getX() + 2);
						newPos.setY(newPos.getY() + 2);
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos = null;
					}
				}
				
				// Go to W
				if (start.getX() > stop.getX() && start.getY() == stop.getY()) {
					newPos = new Position(start.getX() - 1, start.getY());
					if (newPos.equals(stop)) {
						return newPos;
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos.setY(newPos.getY() + 1);
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos.setY(newPos.getY() - 2);
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos.setX(newPos.getX() + 1);
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos.setY(newPos.getY() + 2);
					}
					if (!isAvailable(grid, newPos.getX(), newPos.getY(), gridSize)) {
						newPos = null;
					}
				}
				
				return newPos;
			}
			
			@Override
			public ActionDTO makeDecision(EnvDTO env, int id) {
				AgentDTO perception = requires().translation().translate(env, id);				
				
				// Consume energy for thinking
				perception.getAgent().setEnergy(perception.getAgent().getEnergy() - env.getEnergyConsumedAction());
				
				if (perception.getAgent().getEnergy() > env.getEnergyRequiredToCreate()) {
					return new ActionDTO(ActionEnum.CREATE, perception.getPosition());
				}
				
				if (perception.getAgent().getEnergy() < 1) {
					return new ActionDTO(ActionEnum.DIE, perception.getPosition());
				}
				
				// Go to the nest
				if (perception.getAgent().isTransportingBox()) {
					List<Position> potentielNestPositions = new ArrayList<Position>();

					for (Nest nest : perception.getNests().keySet()) {
						if (nest.getColor().equals(perception.getAgent().getBoxTransported().getColor()))
							potentielNestPositions.add(perception.getNests().get(nest));
					}
					
					Position stop = getNearestPosition(perception.getPosition(), potentielNestPositions);
					Position nextPosition = getNextPosition(perception.getPosition(), stop, env.getGrid().getGrid(), env.getGrid().getGridSize());

					if (nextPosition == null) {
						return new ActionDTO(ActionEnum.NOTHING, perception.getPosition());
					}

					
					if (nextPosition.equals(stop)) {
						return new ActionDTO(ActionEnum.PUT, perception.getPosition(), nextPosition);
					} else {
						return new ActionDTO(ActionEnum.MOVE, perception.getPosition(), nextPosition);						
					}
					
				}

				// Go to get a box
				if (!perception.getAgent().isTransportingBox()) {
					List<Position> potentielBoxPositions = new ArrayList<Position>();

					// find box with good color
					for (Box box : perception.getBoxes().keySet()) {
						if (box.getColor().equals(perception.getAgent().getColor()))
							potentielBoxPositions.add(perception.getBoxes().get(box));
					}

					// else find box
					if (potentielBoxPositions.isEmpty()) {
						for (Box box : perception.getBoxes().keySet()) {
							potentielBoxPositions.add(perception.getBoxes().get(box));
						}						
					}
					
					Position stop = getNearestPosition(perception.getPosition(), potentielBoxPositions);
					Position nextPosition = getNextPosition(perception.getPosition(), stop, env.getGrid().getGrid(), env.getGrid().getGridSize());
					
					if (nextPosition == null) {
						return new ActionDTO(ActionEnum.NOTHING, perception.getPosition());
					}
					
					if (nextPosition.equals(stop)) {
						return new ActionDTO(ActionEnum.GET, perception.getPosition(), nextPosition);
					} else {
						return new ActionDTO(ActionEnum.MOVE, perception.getPosition(), nextPosition);						
					}
										
				}
				
				return new ActionDTO(ActionEnum.NOTHING, perception.getPosition());
			}
		};		

	}

}
