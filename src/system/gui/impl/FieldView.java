package system.gui.impl;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import system.model.objects.Agent;
import system.model.objects.Box;
import system.model.objects.Grid;
import system.model.objects.Nest;

public class FieldView extends AbstractTableModel  {

	private Object[][] data;
	private int sizeGrid;
	private List<Integer> listAgentFollow;
	private boolean follow;

	public FieldView( Object[][] data, int size, List<Integer> listAgentFollow, boolean follow) {
		super();
		this.data = data;
		this.sizeGrid = size;
		this.listAgentFollow = listAgentFollow;
		this.follow = follow;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object comp = data[rowIndex][columnIndex];

		if (comp instanceof Agent) {
			Agent agent = (Agent)comp;

			String boxColor = "N";

			if(agent.getBoxTransported() != null){
				switch(agent.getBoxTransported().getColor()) {
				case BLUE:
					boxColor = "B";
					break;
				case GREEN :
					boxColor = "G";
					break;
				case RED :
					boxColor = "R";
					break;
				default:
					boxColor = "N";
					break;
				}
			}

			for(int id : listAgentFollow)
				if(agent.getId() == id)
					return "XX"+boxColor;
			if(!follow){
				switch(agent.getColor()) {
				case  BLUE:
					return "AB"+boxColor;
				case GREEN :
					return "AG"+boxColor;
				case RED :
					return "AR"+boxColor;
				default:
					return "AR"+boxColor;
				}
			}else
				return "";

		} else if (comp instanceof Box) {
			Box box = (Box)comp;
			switch(box.getColor()) {
			case  BLUE:
				return "BB";
			case GREEN :
				return "BG";
			case RED :
				return "BR";
			default:
				return "BR";
			}
		} else if (comp instanceof Grid) {
			return "";
		} else if (comp instanceof Nest) {
			Nest nest = (Nest)comp;
			switch(nest.getColor()) {
			case  BLUE:
				return "NB";
			case GREEN :
				return "NG";
			case RED :
				return "NR";
			default:
				return "NR";
			}
		} else {
			return "";
		}
	}

	@Override
	public int getColumnCount() {
		return this.sizeGrid;
	}

	@Override
	public int getRowCount() {
		return this.sizeGrid;
	}

}
