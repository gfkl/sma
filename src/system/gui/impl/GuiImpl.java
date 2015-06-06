package system.gui.impl;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import system.dto.EnvConfigDTO;
import system.dto.EnvObsDTO;
import system.gui.GuiComponent;
import system.gui.interfaces.IGui;
import system.model.objects.Agent;
import system.model.objects.Box;
import system.model.objects.Grid;
import system.model.objects.Nest;

public class GuiImpl extends GuiComponent {

	private JFrame frame;

	@Override
	protected IGui make_printer() {
		return new IGui() {

			@Override
			public EnvConfigDTO printEnv(EnvObsDTO env) {
				Object[][] objs = (Object[][]) env.getGrid().getGrid();
				System.out.println("LENGTH : " + objs.length);
				FieldView field = new FieldView(objs);
				JTable table = new JTable(field);
				table.setDefaultRenderer(Object.class, new MonCellRenderer());
				frame.getContentPane().removeAll();
				frame.getContentPane().add(table);
				frame.setVisible(true);
				// afficher a chaque appel de la méthode
				// TODO Peut influer sur l'env via les params de config
				return new EnvConfigDTO(100);
			}

			@Override
			public void createGUI() {
				// TODO Creer la GUI
				frame = new JFrame();
				frame.setTitle("SMA-AL Viewer");
				frame.setBounds(100, 100, 900, 900);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				JButton button = new JButton();
				button.setText("Pause");
				frame.getContentPane().add(button);
			}
		};
	}


//	public class FieldView extends AbstractTableModel {
//
//		private Object[][] data;
//
//
//		@Override
//		public int getRowCount() {
//			return 50;
//		}
//
//		@Override
//		public Object getValueAt(int rowIndex, int columnIndex) {
//
//			System.out.println("AGENT");
//			System.out.println("AGENT");
//			System.out.println("AGENT");
//			System.out.println("AGENT");
//			System.out.println("AGENT");
//			Object comp = data[rowIndex][columnIndex];
//
//			if (comp instanceof Agent) {
//				return "A";
//			} else if (comp instanceof Box) {
//				return "B";
//			} else if (comp instanceof Grid) {
//				return "";
//			} else if (comp instanceof Nest) {
//				return "N";
//			} else
//				return "";
//
//		}
//
//		@Override
//		public int getColumnCount() {
//			return 50;
//		}
//	}

}
