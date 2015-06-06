package system.gui.impl;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MonCellRenderer  extends DefaultTableCellRenderer {

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		Object o = table.getValueAt(row, column);
		if (o != null && component instanceof JLabel)
		{
			JLabel label = (JLabel) component;
			String labelValue = label.getText();
			if(labelValue.contains("X")){
				label.setText("X");
				component = agentHaveBox(labelValue, component);
				setForeground(Color.black);
			} else if (labelValue.contains("AB"))
			{
				label.setText("A");
				setForeground(Color.blue);
				component = agentHaveBox(labelValue, component);
			}
			else if (labelValue.contains("AR"))
			{
				label.setText("A");
				setForeground(Color.red);
				component = agentHaveBox(labelValue , component);

			}
			else if (labelValue.contains("AG"))
			{
				label.setText("A");
				setForeground(Color.green);
				component = agentHaveBox(labelValue, component);

			}
			else if (labelValue.contains("BB"))
			{
				label.setText("B");
				setForeground(Color.white);
				component.setBackground(Color.blue);
			}
			else if (labelValue.contains("BR"))
			{
				label.setText("B");
				setForeground(Color.white);
				component.setBackground(Color.red);
			}
			else if (labelValue.contains("BG"))
			{
				label.setText("B");
				setForeground(Color.white);
				component.setBackground(Color.green);
			}
			else if (labelValue.contains("NB"))
			{
				label.setText("N");
				setForeground(Color.white);
				component.setBackground(Color.blue);
			}
			else if (labelValue.contains("NR"))
			{
				label.setText("N");
				setForeground(Color.white);
				component.setBackground(Color.red);
			}
			else if (labelValue.contains("NG"))
			{
				label.setText("N");
				setForeground(Color.white);
				component.setBackground(Color.green);
			}
			else if (labelValue.contains("")) {
				component.setBackground(Color.white);
			}

		}
		return component;
	}

	private Component agentHaveBox(String color, Component component) {
		char colorAgent = color.charAt(1);
		char colorBox = color.charAt(2);
		
		if(colorBox == 'R'){
			newAgentColor(colorAgent);
			component.setBackground(Color.red);
		}
		else if(colorBox  == 'B'){
			newAgentColor(colorAgent);
			component.setBackground(Color.blue);
		}
		else if(colorBox  == 'G'){
			newAgentColor(colorAgent);
			component.setBackground(Color.green);
		}else
			component.setBackground(Color.white);

		return component;
	}

	private void newAgentColor(char colorAgent) {
		if(colorAgent == 'R'){
			setForeground(Color.orange);
		}
		else if(colorAgent  == 'B'){
			setForeground(Color.cyan);
		}
		else if(colorAgent  == 'G'){
			setForeground(Color.darkGray);
		}
	}
}

