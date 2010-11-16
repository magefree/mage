package mage.interfaces.plugin;

import java.util.Map;

import javax.swing.JComponent;

import net.xeoh.plugins.base.Plugin;

/**
 * Interface for theme plugins
 * 
 * @version 0.1 31.10.2010
 * @author nantuko
 */
public interface ThemePlugin extends Plugin {
	void applyInGame(Map<String, JComponent> ui);
	JComponent updateTable(Map<String, JComponent> ui);
}
