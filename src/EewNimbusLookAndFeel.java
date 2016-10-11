import java.awt.Font;
import java.util.Set;

import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class EewNimbusLookAndFeel extends NimbusLookAndFeel {
	@Override
	public String getID() {
		return "EewNimbusLookAndFeel";
	}
	
	@Override
    public UIDefaults getDefaults() {
		final UIDefaults defaults = super.getDefaults();
		Set<Object> keySet = defaults.keySet();
		Object[] keys = keySet.toArray(new Object[keySet.size()]);
		for( Object key : keys ) {
			if( key != null && key.toString().toLowerCase().contains("font") ) {
				Font font = UIManager.getFont(key);
				if (font != null) {
					System.out.print("Font Key: " + key + " " + font.getSize2D() + " ----> ");
					defaults.put(key, font);
					System.out.println( defaults.getFont(key).getSize2D() );
				}
			}
		} //end for(keys)
		return defaults;
    } //end getDefaults Override
} //end class EewNimbusLookAndFeel