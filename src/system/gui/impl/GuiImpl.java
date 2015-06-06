package system.gui.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.KeyStroke;

import system.dto.EnvConfigDTO;
import system.dto.EnvObsDTO;
import system.gui.GuiComponent;
import system.gui.interfaces.IGui;

public class GuiImpl extends GuiComponent {

	private JFrame frame;
	private final int incSpeed = 250;
	private int speed;
	private int currentSpeed;
	
	@Override
	protected IGui make_printer() {
		return new IGui() {
			@Override
			public EnvConfigDTO printEnv(EnvObsDTO env) {
				Object[][] objs = (Object[][]) env.getGrid().getGrid();
				FieldView field = new FieldView(objs);
				JTable table = new JTable(field);
				table.setDefaultRenderer(Object.class, new MonCellRenderer());
				frame.getContentPane().removeAll();
				frame.getContentPane().add(table);
				frame.setVisible(true);
				return new EnvConfigDTO(speed);
			}

			@Override
			public void createGUI() {
				speed = 100;
				frame = new JFrame();
				frame.setTitle("SMA-AL Viewer");
				frame.setBounds(100, 100, 900, 900);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				createMenuBar();
			}

			
		    private void createMenuBar() {

		        JMenuBar menubar = new JMenuBar();

		        JMenu file = new JMenu("Action");
		        file.setMnemonic(KeyEvent.VK_F);

		        
		        
		        JMenuItem eMenuItemSpeedUp = new JMenuItem("Speed Up");
		        eMenuItemSpeedUp.setAccelerator(KeyStroke.getKeyStroke(
		                java.awt.event.KeyEvent.VK_UP, 
		                java.awt.Event.CTRL_MASK));
		        eMenuItemSpeedUp.setToolTipText("Pause application");
		        eMenuItemSpeedUp.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent event) {
		            	if(speed > incSpeed)
		            		speed -= incSpeed;
		            	else
		            		speed = 0;

		            }
		        });
		        
		        JMenuItem eMenuItemSpeedDown = new JMenuItem("Speed Down");
		        eMenuItemSpeedDown.setAccelerator(KeyStroke.getKeyStroke(
		                java.awt.event.KeyEvent.VK_DOWN, 
		                java.awt.Event.CTRL_MASK));
		        eMenuItemSpeedDown.setToolTipText("Pause application");
		        eMenuItemSpeedDown.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent event) {
		            		speed += incSpeed;
		            }
		        });
		        
		        
		        
		        JMenuItem eMenuItemPause = new JMenuItem("Pause");
		        eMenuItemPause.setAccelerator(KeyStroke.getKeyStroke(
		                java.awt.event.KeyEvent.VK_P, 
		                java.awt.Event.CTRL_MASK));
		        eMenuItemPause.setToolTipText("Pause application");
		        eMenuItemPause.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent event) {
		            	if(speed != 0){
		            		currentSpeed = speed;
		            		speed = 10000;
		            	}else
		            		speed = currentSpeed;
		            }
		        });
		        
		        
		        JMenuItem eMenuItemExit = new JMenuItem("Exit");
		        eMenuItemExit.setAccelerator(KeyStroke.getKeyStroke(
		                java.awt.event.KeyEvent.VK_E, 
		                java.awt.Event.CTRL_MASK));
		        eMenuItemExit.setToolTipText("Exit application");
		        eMenuItemExit.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent event) {
		                System.exit(0);
		            }
		        });

		        file.add(eMenuItemSpeedUp);
		        file.add(eMenuItemSpeedDown);
		        file.add(eMenuItemPause);
		        file.add(eMenuItemExit);
		        menubar.add(file);

		        frame.setJMenuBar(menubar);
		    }
		};

	}
}
