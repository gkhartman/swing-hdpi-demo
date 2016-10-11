/** @file EewTrayIcon.java
	@author Garret Hartman (ghartman@gps.caltech.edu)
*/

//java imports
import java.util.Set;
import java.awt.AWTException;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;

//awt imports
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.event.ActionEvent;
import java.io.IOException;

//swing imports
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.plaf.synth.SynthLookAndFeel;  //synth xml configurable look and feel
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.BorderFactory;
import javax.swing.Box;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.BoxLayout;
import javax.swing.SpringLayout;

//Main Class containing main method
public class Main {

	//V<version>-<YYYY><MM><DD>
	private static final String versionNumber = "V1.0-20161007";
	
	private static boolean hasTitleTheme = false;

	private static SystemTray sysTray = null; //awt system tray member
	
	private static EewTrayIcon sysTrayIcon = null;

	public static void main(String[] args) {
		new Main();
	} //end main(String[])
	
	//default constructor
	public Main() {
	setHdpiFontMultiplyer(2);
	/*	
		System.out.println("Look and feel defaults: \n\n");
		Set defaults = UIManager.getLookAndFeelDefaults().entrySet();
		for (java.util.Iterator i = defaults.iterator(); i.hasNext();) {
			Map.Entry entry = (Map.Entry) i.next();
			System.out.print(entry.getKey() + " = ");
			System.out.println(entry.getValue());
		}
	*/	
		//Spin up a Swing UI thread to display example JFrame
		EventQueue.invokeLater( 
			() -> {
                //create and configure new JFrame and LayoutManager
                JFrame frame = new JFrame( "Swing HiDPI Scaling Example " + versionNumber );
                frame.getContentPane().setLayout(new GridLayout());
                try {
					frame.setIconImage( ImageIO.read( ClassLoader.getSystemResource("arc.png") ) );
				} catch (IOException ioExcept) {
					ioExcept.printStackTrace();
				}
                
                JPanel panel = new JPanel();
                //panel.setLayout( new BoxLayout(panel, BoxLayout.Y_AXIS) );
                SpringLayout panelLayout = new SpringLayout();
                panel.setLayout(panelLayout);
                panel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
                panel.setAlignmentY(JPanel.CENTER_ALIGNMENT);
                panel.setBorder( BorderFactory.createEmptyBorder(10, 10, 10, 10) );
                frame.setMinimumSize(panel.getSize());
                frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
                
                //create system tray icon
                createTrayIcon();
                
                //create and add new JLabel and add to frame
                JLabel promptLabel = new JLabel("This JLabel has a default 12pt font size");
                promptLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                promptLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
                promptLabel.setFont(promptLabel.getFont().deriveFont(12)); //set to 12pt for reference
                
                
                
                //////create buttons + tool tips//////
                JButton buttonMetalLAF = new JButton("Metal Look And Feel");
                buttonMetalLAF.setToolTipText("Best cross platform support. Scales properly on all platforms");
                //JButton buttonSynthLAF = new JButton("Synth Look And Feel");
                //buttonSynthLAF.setToolTipText("Scaling SynthStyle or synth xml object must be defined for support");
                JButton buttonSystemLAF = new JButton("System Look And Feel (Windows, GTK, or Motif)");
                buttonSystemLAF.setToolTipText("Only some components scale properly on Mac, Unix, and Linux");
                JButton buttonNimbusLAF = new JButton("Nimbus Look And Feel");
                buttonNimbusLAF.setToolTipText("Only some components scale properly");
                JButton buttonEewNimbusLAF = new JButton("EewNimbusLookAndFeel");
                buttonEewNimbusLAF.setToolTipText("EewNimbus Scales Properly On All Platforms");
                //////end create buttons + tool tips//////
                
                /*////set preferred button sizes////
                Dimension buttonSize2D = new Dimension(50, 100);
                buttonMetalLAF.setSize(buttonMetalLAF.getParent().getSize());
                //buttonSynthLAF.setSize(buttonSize2D);
                buttonSystemLAF.setSize(buttonSize2D);
                buttonNimbusLAF.setSize(buttonSize2D);
                buttonEewNimbusLAF.setSize(buttonSize2D);
                //////end set preferred button sizes////*/
                
                //Add button listener to enable Metal LAF
                buttonMetalLAF.addActionListener( (ActionEvent) -> {
					String lookAndFeelName = UIManager.getCrossPlatformLookAndFeelClassName();
						try {
							UIManager.setLookAndFeel(lookAndFeelName);
						} catch (Exception e) {
							e.printStackTrace();
						}
						SwingUtilities.updateComponentTreeUI(frame);
						frame.pack();
                }); //end addActionListener(ActionListener -> actionEvent(ActionEvent))
                
                //Add button listener to enable Synth LAF
                /*buttonSynthLAF.addActionListener( (ActionEvent) -> {
					SynthLookAndFeel laf = new SynthLookAndFeel();
					try {
						laf.load(new File("resource/synth_laf_config.xml").toURI().toURL());
						UIManager.setLookAndFeel(laf);
					} catch (Exception e) {
						e.printStackTrace();
					}
					SwingUtilities.updateComponentTreeUI(frame);
					frame.pack();
                }); //end addActionListener(ActionListener -> actionEvent(ActionEvent)) */
                
                //Add button listener to enable System LAF
                buttonSystemLAF.addActionListener( (ActionEvent) -> {
					String lookAndFeelName = UIManager.getSystemLookAndFeelClassName();
					try {
						UIManager.setLookAndFeel(lookAndFeelName);
					} catch (Exception e) {
						e.printStackTrace();
					}
					SwingUtilities.updateComponentTreeUI(frame);
					frame.pack();
                }); //end addActionListener( Lambda(ActionEvent) : ActionListener )
                
                //Add button listener to enable Nimbus LAF
                //buttonNimbusLAF.addActionListener( new ActionListener() {
                buttonNimbusLAF.addActionListener( (ActionEvent) -> {
					NimbusLookAndFeel nimbusLAF = new NimbusLookAndFeel();
					try {
						UIManager.setLookAndFeel(nimbusLAF);
					} catch (Exception e) {
						e.printStackTrace();
					}
					SwingUtilities.updateComponentTreeUI(frame);
					frame.pack();
                }); //end addActionListener( Lambda(ActionEvent) : ActionListener )
                
                //Add button listener to enable EewNimbus LAF
                buttonEewNimbusLAF.addActionListener( (ActionEvent event) -> {
					EewNimbusLookAndFeel eewNimbusLAF = new EewNimbusLookAndFeel();
					try {
						UIManager.setLookAndFeel(eewNimbusLAF);
					} catch (Exception e) {
						e.printStackTrace();
					}
					SwingUtilities.updateComponentTreeUI(frame);
					frame.pack();
                }); //end addActionListener( Lambda(ActionEvent) : ActionListener )
                
                //create boxes that will be added to panel
                Box buttons = Box.createHorizontalBox();
                buttons.setAlignmentX(Box.LEFT_ALIGNMENT);
                buttons.setAlignmentY(Box.LEFT_ALIGNMENT);
                Box leftColumn = Box.createVerticalBox();
                Box rightColumn = Box.createVerticalBox(); 
                leftColumn.setAlignmentX(Box.CENTER_ALIGNMENT);
                leftColumn.setAlignmentY(Box.CENTER_ALIGNMENT);
                rightColumn.setAlignmentX(Box.CENTER_ALIGNMENT);
                rightColumn.setAlignmentY(Box.CENTER_ALIGNMENT);
                
                //add boxes to panel
                panel.add(promptLabel);
                leftColumn.add(buttonMetalLAF);
                leftColumn.add(buttonSystemLAF);
                rightColumn.add(buttonNimbusLAF);
                rightColumn.add(buttonEewNimbusLAF);
                buttons.add(leftColumn);
                buttons.add(Box.createHorizontalStrut(10));
                buttons.add(rightColumn);
                panel.add(buttons);
                
                //Set spring layout constraints for prompt label
                panelLayout.putConstraint(SpringLayout.NORTH, promptLabel, 5, SpringLayout.NORTH, panel);
                panelLayout.putConstraint(SpringLayout.EAST, promptLabel, 5, SpringLayout.EAST, panel);
                panelLayout.putConstraint(SpringLayout.SOUTH, promptLabel, 5, SpringLayout.NORTH, buttons);
                panelLayout.putConstraint(SpringLayout.WEST, promptLabel, 5, SpringLayout.WEST, panel);
                
                //Set springs for button box
                panelLayout.putConstraint(SpringLayout.NORTH, buttons, 5, SpringLayout.NORTH, promptLabel);
                panelLayout.putConstraint(SpringLayout.EAST, buttons, 5, SpringLayout.EAST, panel);
                panelLayout.putConstraint(SpringLayout.SOUTH, buttons, 5, SpringLayout.SOUTH, panel);
                panelLayout.putConstraint(SpringLayout.WEST, buttons, 5, SpringLayout.WEST, panel);
                
                
                
                //Create formatted text field to enter float font scaler
                frame.add(panel);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                
			} //end run() lambda
        ); // end invokeLater( lambda->Runnable.run() )	
	} //end default constructor Main()
	
	private static void createTrayIcon() {
		if(sysTray != null) {return;}
		if( SystemTray.isSupported()) {
			sysTray = SystemTray.getSystemTray(); //get system tray instance
			
			//load image that will be applied to EewTrayIcon
			Image trayIconImage = null;
			try {
				trayIconImage = ImageIO.read( ClassLoader.getSystemResource("arc.png") );
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//create JPopupMenu that will be displayed when system tray icon is clicked
			JPopupMenu popupMenu = new JPopupMenu("Menu");
			
			//create menuItems with lambda defined action listeners
			JMenuItem settingsItem = new JMenuItem("Settings");
			
			//add Settings menu item clicked listener
			settingsItem.addActionListener( (ActionEvent) -> {
				System.out.println("Settings Button Clicked...");
			}); //end addActionListener( lambda (ActionEvent) : ActionListener )
			
			JMenuItem showItem = new JMenuItem("Show");
			showItem.addActionListener( (ActionEvent) -> {
				System.out.println("Show Button Clicked...");
			}); //end addActionListener( lambda (ActionEvent) : ActionListener )
			
			JMenuItem exitItem = new JMenuItem("Exit");
			exitItem.addActionListener( (ActionEvent) -> {
				System.out.println("Exit Button Clicked...");
				System.exit(0);
			}); //end addActionListener( lambda (ActionEvent) : ActionListener )
			
			//add menu items to popup menu
			popupMenu.add(settingsItem); //add item to system tray popup menu
			popupMenu.add(showItem); //add item to system tray popup menu
			popupMenu.add(exitItem); //add item to system tray popup menu
			
			String toolTipText = "System Tray Tooltip Example";
			int scaledImgWidth = sysTray.getTrayIconSize().width;
			int scaledImgHeight = sysTray.getTrayIconSize().height;
			EewTrayIcon sysTrayIcon = new EewTrayIcon( (trayIconImage.getScaledInstance(scaledImgWidth, scaledImgHeight, Image.SCALE_DEFAULT) ), toolTipText, popupMenu);
			//add EewTrayIcon to SystemTray
			try {
				sysTray.add(sysTrayIcon);
			}
			catch (AWTException awtExcept) {
				awtExcept.printStackTrace();
			}
			
			//sysTrayIcon.displayMessage("", mesgText, MessageType.NONE);
			  
		} //close if
		else {
			System.out.println("System tray is not supported");
			return;
		} //end if-else
	} //end createTrayIcon()
	
	//Iterate to find and set all fonts in default look and feel to parameter font_size
	private void setHdpiFontMultiplyer(float fontMultiplyer) {
		Set<Object> keySet = UIManager.getLookAndFeelDefaults().keySet();
		Object[] keys = keySet.toArray(new Object[keySet.size()]);
		for (Object key : keys) {
			if (key != null && (key.toString().toLowerCase().contains("font") || key.toString().toLowerCase().endsWith(".font")) ) {
				Font font = UIManager.getDefaults().getFont(key);
				if (font != null) {
					System.out.print("Font Key: " + key + " " + font.getSize2D() + " --to--> ");
					font = font.deriveFont(font.getSize2D() * fontMultiplyer);
					UIManager.put(key, font);
					System.out.println( UIManager.getFont(key).getSize2D() );
				}
			}
		} //end for(keys)
	} //setDefaultSize(float)
	
} //end class Main
//end file Main.java