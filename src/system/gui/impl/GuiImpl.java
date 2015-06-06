package system.gui.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import system.dto.EnvConfigDTO;
import system.dto.EnvObsDTO;
import system.gui.GuiComponent;
import system.gui.interfaces.IGui;
import system.model.objects.Agent;

public class GuiImpl extends GuiComponent {

	private JFrame frame;
	private final int incSpeed = 25;
	private boolean isPause = false;
	private boolean nextStep = false;
	private boolean readByStep = false;
	private int speed;
	
	private List<Integer> listAgentFollow = new ArrayList<Integer>();

	@Override
	protected IGui make_printer() {
		return new IGui() {
			@Override
			public EnvConfigDTO printEnv(EnvObsDTO env) {
				Object[][] objs = (Object[][]) env.getGrid().getGrid();
				FieldView field = new FieldView(objs, env.getGrid().getGridSize(), listAgentFollow);
				JTable table = new JTable(field);
				table.setDefaultRenderer(Object.class, new MonCellRenderer());
				
			    table.setColumnSelectionAllowed(true);
			    table.setRowSelectionAllowed(true);
				
			    table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			        public void valueChanged(ListSelectionEvent event) {
			            int column = table.getSelectedColumn();
			            int row = table.getSelectedRow();
			            
			            Object comp = objs[row][column];
			            if (comp instanceof Agent) {
			            	listAgentFollow.add(((Agent) comp).getId());
			            	//env.setIdAgentSelected(idAgentFollow);
			            }

			        }
			    });
			    
			    
				frame.getContentPane().removeAll();
				frame.getContentPane().add(table);
				frame.setVisible(true);
				
				managePause();
				if(readByStep)
					manageStepByStep();

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

			public List<Integer> getFocusRobot() {
				return listAgentFollow;
			}

			
			private void manageStepByStep() {
				nextStep = true;
			    while(nextStep){
			    	if(!nextStep || !readByStep){
			    		break;
			    	}
			        try {
			        	//Optimise the proc
			            Thread.sleep(500);
			        }
			        catch (InterruptedException e) {}
			    }
			}

			private void managePause() {
			    while(isPause){
			    	if(!isPause){
			    		break;
			    	}
			        try {
			        	//Optimise the proc
			            Thread.sleep(500);
			        }
			        catch (InterruptedException e) {}
			    }
			}

			private void createMenuBar() {

				JMenuBar menubar = new JMenuBar();

				JMenu file = new JMenu("Action");
				file.setMnemonic(KeyEvent.VK_F);

				JMenuItem eMenuItemByStep= new JMenuItem("Step By Step");
				eMenuItemByStep.setAccelerator(KeyStroke.getKeyStroke(
						java.awt.event.KeyEvent.VK_RIGHT, 
						java.awt.Event.CTRL_MASK));
				eMenuItemByStep.setToolTipText("Read Step by Step application");
				eMenuItemByStep.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent event) {
						readByStep = true;
						nextStep = !nextStep;
					}
				});
				
				JMenuItem eMenuItemSpeedUp = new JMenuItem("Speed Up");
				eMenuItemSpeedUp.setAccelerator(KeyStroke.getKeyStroke(
						java.awt.event.KeyEvent.VK_UP, 
						java.awt.Event.CTRL_MASK));
				eMenuItemSpeedUp.setToolTipText("Speed up application");
				eMenuItemSpeedUp.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent event) {
						if(speed > incSpeed)
							speed -= incSpeed;
						else
							speed = 0;
						
						readByStep = false;
					}
				});

				JMenuItem eMenuItemSpeedDown = new JMenuItem("Speed Down");
				eMenuItemSpeedDown.setAccelerator(KeyStroke.getKeyStroke(
						java.awt.event.KeyEvent.VK_DOWN, 
						java.awt.Event.CTRL_MASK));
				eMenuItemSpeedDown.setToolTipText("Speed down application");
				eMenuItemSpeedDown.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent event) {
						speed += incSpeed;
						readByStep = false;

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
						isPause = !isPause;
						readByStep = false;

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

				file.add(eMenuItemByStep);
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
