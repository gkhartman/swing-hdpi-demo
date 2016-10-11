/** @file EewTrayIcon.java */

//awt imports
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//swing imports
import javax.swing.*;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;

public class EewTrayIcon extends TrayIcon {
	/** The popup menu displayed on mouse clicked event. */
    private JPopupMenu popupMenu;
    
    /** Popup menu listener to show and hide the popup menu in response to mouse events. */
    private PopupMenuListener popupListener = new PopupMenuListener() {
    	@Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
    		popupMenu.setVisible(true);
    	}
    	
    	@Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            popupMenu.setVisible(false);
        }
    	
    	@Override
        public void popupMenuCanceled(PopupMenuEvent e) {
    		popupMenu.setVisible(false);
        }
    };
    
    /** Constructs a new EewTrayIcon instance without a popup menu or tooltip.
     * 	@param iconImage The image to be shown on the operating system taskbar.
     *  @param toolTipText The string to be displayed as a tooltip when the mouse hovers over the system tray icon.
     */
    public EewTrayIcon(Image image) {
    	this(image, null);
    }
    
    /** Constructs a new EewTrayIcon instance without a popup menu
     * 	@param iconImage The image to be shown on the operating system taskbar.
     *  @param toolTipText The string to be displayed as a tooltip when the mouse hovers over the system tray icon.
     */
    public EewTrayIcon(Image image, String toolTipText) {
    	this(image, toolTipText, null);
    }
    
    /** Constructs a new EewTrayIcon instance that has a displayed icon image, tooltip, and popup menu
     * 	@param iconImage The image to be shown on the operating system taskbar.
     *  @param toolTipText The string to be displayed as a tooltip when the mouse hovers over the system tray icon.
     *  @param menu The JPopupMenu object that will be shown when the system tray icon is clicked.
     */
    public EewTrayIcon(Image iconImage, String toolTipText, JPopupMenu menu) {
    	super(iconImage, toolTipText); //all parent constructor
    	if(menu == null) return;
    	popupMenu = menu;
    	
        //create and add tray icon mouse listeners
        addMouseListener(new MouseAdapter() {
        	//when mouse click is pressed on tray icon
        	@Override
            public void mousePressed(MouseEvent mousePressedEvent) {
        		if( popupMenu.isShowing() ) {
        			popupMenu.setVisible(false);
        		}
        		else {
        			//if popup menu is not currenly visible call showJPopupMenu(MouseEvent)
        			showJPopupMenu( mousePressedEvent );
        		}
            }
        	//Note: no TrayIcon support for mouseEntered/Exited methods
        }); //end addMouseListener( MouseAdapter() )
    } //end EewTrayIcon(Image, JToolTip, JPopupMenu)
    
    /** Displays the JPopupMenu defined for the TrayIcon if available. 
     *  @param iconClickedEvent The mouse event that determines the location of the popup window.
     *  @return void
     */
    protected void showJPopupMenu( MouseEvent iconClickedEvent ) {
    	//show JPopupMenu where mouse was clicked
        if (popupMenu != null) {
            popupMenu.setLocation( iconClickedEvent.getX(), iconClickedEvent.getY() );
            popupMenu.setVisible(true);
        }
    } //end showJPopupMenu(MouseEvent)
    
    /** Returns the JPopupMenu member that is desplayed when the tray icon is clicked. 
     *  @return JPopupMenu
     */
    public JPopupMenu getJPopupMenu() { return popupMenu; }
    
} //end class EewTrayIcon
//end file EewTrayIcon.java