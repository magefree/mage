package mage.client.plugins.impl;

import java.io.File;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;

import mage.client.plugins.MagePlugins;
import mage.client.util.Constants;
import mage.interfaces.plugin.ThemePlugin;
import mage.util.Logging;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.util.PluginManagerUtil;


public class Plugins implements MagePlugins {

	private static final MagePlugins fINSTANCE =  new Plugins();
	private static PluginManager pm;
	private final static Logger logger = Logging.getLogger(Plugins.class.getName());
	
	public static MagePlugins getInstance() {
		return fINSTANCE;
	}
	
	@Override
	public void loadPlugins() {
		logger.log(Level.INFO, "Loading plugins...");
		pm = PluginManagerFactory.createPluginManager();
		pm.addPluginsFrom(new File(Constants.PLUGINS_DIRECTORY).toURI());
		logger.log(Level.INFO, "Done.");
	}
	
	@Override
	public void shutdown() {
		if (pm != null) pm.shutdown();
	}

	@Override
	public void updateGamePanel(Map<String, JComponent> ui) {
		PluginManagerUtil pmu = new PluginManagerUtil(pm);
		
		for (ThemePlugin pl : pmu.getPlugins(ThemePlugin.class)) {
			pl.apply(ui);
		}
	}
}
