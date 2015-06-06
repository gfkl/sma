package system.persistence.impl;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import system.agentmanager.AgentManagerComponent.AgentSpecies;
import system.dto.EnvDTO;
import system.model.ColorEnum;
import system.model.objects.Agent;
import system.model.objects.Box;
import system.model.objects.Grid;
import system.model.objects.Nest;
import system.persistence.PersistenceComponent;
import system.persistence.interfaces.IPersistence;
public class PersistenceImpl extends PersistenceComponent {

	@Override
	protected IPersistence make_persistence() {
		return new IPersistence() {

			@Override
			public void saveContext(EnvDTO env) {
				try {
					final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				    final DocumentBuilder builder = factory.newDocumentBuilder();
					final Document document= builder.newDocument();
					
					final Element racine = document.createElement("persistence");
					document.appendChild(racine);
					
					initPersist(env, document, racine);
					gridPersist(env.getGrid(), document, racine);

					
					
					final TransformerFactory transformerFactory = TransformerFactory.newInstance();
					final Transformer transformer = transformerFactory.newTransformer();
					final DOMSource source = new DOMSource(document);
					deleteFileIfExist();
					final StreamResult sortie = new StreamResult(new File("Persistence.xml"));
					transformer.transform(source, sortie);
				}catch (Exception e) {
				    e.printStackTrace();
				}
			}

			private void deleteFileIfExist() {
				try{
			        File fileTemp = new File("Persistence.xml");
			          if (fileTemp.exists()){
			             fileTemp.delete();
			          }   
			      }catch(Exception e){
			         e.printStackTrace();
			      }
			}

			private Element gridPersist(Grid grid, Document document, Element racine) {
				int gridSize = grid.getGridSize();
				final Element gridMain = document.createElement("Grid");
				final Element gridNodeSize = document.createElement("GridSize");
				gridNodeSize.setAttribute("value", ""+gridSize);
				gridMain.appendChild(gridNodeSize);
				
				final Element boxes = document.createElement("Boxes");
				final Element nests = document.createElement("Nests");
				final Element agents = document.createElement("Agents");

				for (int y = 0; y < gridSize; y++) {
					Element agent;
					String line = "";
					String nodeName;
					
					for (int x = 0; x < gridSize; x++) {
						if (grid.getGrid()[x][y] == null) {
							line += "  ";
						} else if (grid.getGrid()[x][y] instanceof Box) {
							Box box = (Box) grid.getGrid()[x][y];
							agent = boxNode(x, y, document, box);
							boxes.appendChild(agent);
							
						} else if (grid.getGrid()[x][y] instanceof Nest) {
							Nest nest = (Nest) grid.getGrid()[x][y];
							agent = nestNode(x, y, document, nest);
							nests.appendChild(agent);
						} else if (grid.getGrid()[x][y] instanceof Agent) {
							Agent curenAgent = (Agent) grid.getGrid()[x][y];
							agent = agentNode(x, y, document, curenAgent);
							agents.appendChild(agent);
						} else {
							line += "##";
						}
						
						gridMain.appendChild(agents);
					}
				}
				
				gridMain.appendChild(boxes);
				gridMain.appendChild(nests);
				gridMain.appendChild(agents);

				racine.appendChild(gridMain);
				return gridMain;
			}
			
			private Element boxNode(int x, int y, Document document, Box box){
				Element agent = document.createElement("Box");
				agent.setAttribute("X", ""+x);
				agent.setAttribute("Y", ""+y);
				
				final Element color = document.createElement("color");
				color.appendChild(document.createTextNode(box.getColor().name()));
				final Element id = document.createElement("id");
				id.appendChild(document.createTextNode(box.getId()+""));
				
				agent.appendChild(color);
				agent.appendChild(id);
				
				return agent;
			}
			
			private Element nestNode(int x, int y, Document document, Nest nest){
				Element agent = document.createElement("Nest");
				agent.setAttribute("X", ""+x);
				agent.setAttribute("Y", ""+y);
				
				final Element color = document.createElement("color");
				color.appendChild(document.createTextNode(nest.getColor().name()));
				final Element id = document.createElement("id");
				id.appendChild(document.createTextNode(nest.getId()+""));
				
				agent.appendChild(color);
				agent.appendChild(id);
				
				return agent;
			}

			private Element agentNode(int x, int y, Document document, Agent curenAgent){
				Element agent = document.createElement("Agent");
				agent.setAttribute("X", ""+x);
				agent.setAttribute("Y", ""+y);
				
				final Element color = document.createElement("color");
				color.appendChild(document.createTextNode(curenAgent.getColor().name()));
				final Element id = document.createElement("idRobot");
				id.appendChild(document.createTextNode(curenAgent.getId()+""));
				final Element energy = document.createElement("energy");
				energy.appendChild(document.createTextNode(curenAgent.getEnergy()+""));
				
				boolean haveBox = curenAgent.isTransportingBox();
				final Element boxTransport = document.createElement("transportingBox");
				final Element idBox = document.createElement("idBox");

				if(haveBox){
					idBox.appendChild(document.createTextNode(curenAgent.getBoxTransported().getId()+""));
					boxTransport.appendChild(document.createTextNode("true"));
				}else{
					idBox.appendChild(document.createTextNode("-1"));
					boxTransport.appendChild(document.createTextNode("false"));
				}
				
				
				
				agent.appendChild(color);
				agent.appendChild(id);
				agent.appendChild(energy);
				agent.appendChild(boxTransport);
				agent.appendChild(idBox);
				
				return agent;
			}
			
			private Element initPersist(EnvDTO env, Document document, Element racine) {
				final Element energy = document.createElement("Energy");
				racine.appendChild(energy);
				
				final Element energyInit = document.createElement("init");
				final Element energyWrongColorBoxReward = document.createElement("WrongColorBoxReward");
				final Element energyGoodColorBoxReward = document.createElement("GoodColorBoxReward");
				final Element energyConcumedToCreate = document.createElement("ConcumedToCreate");
				final Element energyRequiredToCreate = document.createElement("RequiredToCreate");
				final Element energyConsumedAction = document.createElement("ConsumedAction");

				energyInit.setAttribute("value", ""+env.getEnergyInit());
				energyWrongColorBoxReward.setAttribute("value", ""+env.getEnergyWrongColorBoxReward());
				energyGoodColorBoxReward.setAttribute("value",""+env.getEnergyGoodColorBoxReward());
				energyConcumedToCreate.setAttribute("value", ""+env.getEnergyConcumedToCreate());
				energyRequiredToCreate.setAttribute("value", ""+env.getEnergyRequiredToCreate());
				energyConsumedAction.setAttribute("value", ""+env.getEnergyConsumedAction());

				
				energy.appendChild(energyInit);
				energy.appendChild(energyWrongColorBoxReward);
				energy.appendChild(energyGoodColorBoxReward);
				energy.appendChild(energyConcumedToCreate);
				energy.appendChild(energyRequiredToCreate);
				energy.appendChild(energyConsumedAction);
				
				return energy;
			}
		};
	}

}
