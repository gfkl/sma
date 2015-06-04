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
import system.model.objects.Box;
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
					steps += Math.abs(x) - Math.abs(y);
					
					if (bestSteps == -1 || bestSteps > steps) {
						bestStop = stop;
						bestSteps = steps;
					}
				}
				
				return bestStop;
			}
			
			private Position getNextPosition(Position start, Position stop, Object[][] grid) {
				
				// TODO
				return start;
			}
			
			@Override
			public ActionDTO makeDecision(EnvDTO env, int id) {
				AgentDTO perception = requires().translation().translate(env, id);				
				
				// Consume energy for thinking
				perception.getAgent().setEnergy(perception.getAgent().getEnergy() - 1);
				
				if (perception.getAgent().getEnergy() > 100) {
					return new ActionDTO(ActionEnum.CREATE);
				}
				
				if (perception.getAgent().getEnergy() < 1) {
					return new ActionDTO(ActionEnum.DIE);
				}
				
				// Go to the nest
				if (perception.getAgent().isTransportingBox()) {
					List<Position> potentielNestPositions = new ArrayList<Position>();

					for (Nest nest : perception.getNests().keySet()) {
						if (nest.getColor().equals(perception.getAgent().getBoxTransported().getColor()))
							potentielNestPositions.add(perception.getNests().get(nest));
					}
					
					Position stop = getNearestPosition(perception.getPosition(), potentielNestPositions);
					Position nextPosition = getNextPosition(perception.getPosition(), stop, env.getGrid().getGrid());

					if (nextPosition == null) {
						return new ActionDTO(ActionEnum.NOTHING);
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
							potentielBoxPositions.add(perception.getNests().get(box));
					}

					// else find box
					if (potentielBoxPositions.isEmpty()) {
						for (Box box : perception.getBoxes().keySet()) {
							potentielBoxPositions.add(perception.getNests().get(box));
						}						
					}
					
					Position stop = getNearestPosition(perception.getPosition(), potentielBoxPositions);
					Position nextPosition = getNextPosition(perception.getPosition(), stop, env.getGrid().getGrid());
					
					if (nextPosition == null) {
						return new ActionDTO(ActionEnum.NOTHING);
					}
					
					if (nextPosition.equals(stop)) {
						return new ActionDTO(ActionEnum.GET, perception.getPosition(), nextPosition);
					} else {
						return new ActionDTO(ActionEnum.MOVE, perception.getPosition(), nextPosition);						
					}
										
				}
				
				return new ActionDTO(ActionEnum.NOTHING);
			}
		};		

	}

}
