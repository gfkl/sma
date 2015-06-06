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
              if (label.getText().contains("AB"))
              {
            	  label.setText("A");
            	  setForeground(Color.blue);
              }
              else if (label.getText().contains("AR"))
              {
            	  label.setText("A");
            	  setForeground(Color.red);
              }
              else if (label.getText().contains("AG"))
              {
            	  label.setText("A");
            	  setForeground(Color.green);
              }
              else if (label.getText().contains("BB"))
              {
            	  label.setText("B");
            	  setForeground(Color.white);
                  component.setBackground(Color.blue);
              }
              else if (label.getText().contains("BR"))
              {
            	  label.setText("B");
            	  setForeground(Color.white);
                  component.setBackground(Color.red);
              }
              else if (label.getText().contains("BG"))
              {
            	  label.setText("B");
            	  setForeground(Color.white);
                  component.setBackground(Color.green);
              }
              else if (label.getText().contains("NB"))
              {
            	  label.setText("N");
            	  setForeground(Color.white);
                  component.setBackground(Color.blue);
              }
              else if (label.getText().contains("NR"))
              {
            	  label.setText("N");
            	  setForeground(Color.white);
                  component.setBackground(Color.red);
              }
              else if (label.getText().contains("NG"))
              {
            	  label.setText("N");
            	  setForeground(Color.white);
                  component.setBackground(Color.green);
              }
              else if (label.getText().contains("")) {
            	  component.setBackground(Color.white);
              }

          }
          return component;
      }
}

