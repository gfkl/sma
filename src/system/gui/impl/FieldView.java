package system.gui.impl;

import javax.swing.table.AbstractTableModel;

import system.model.objects.Agent;
import system.model.objects.Box;
import system.model.objects.Grid;
import system.model.objects.Nest;

public class FieldView extends AbstractTableModel  {
	
	private Object[][] data;
	private int sizeGrid;
	
	public FieldView( Object[][] data, int size) {
		super();
		this.data = data;
		this.sizeGrid = size;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object comp = data[rowIndex][columnIndex];

		if (comp instanceof Agent) {
			Agent agent = (Agent)comp;
			switch(agent.getColor()) {
			case  BLUE:
				return "AB";
			case GREEN :
				return "AG";
			case RED :
				return "AR";
			default:
				return "AR";
			}
			
			
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
