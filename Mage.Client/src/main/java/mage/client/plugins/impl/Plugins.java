package mage.client.plugins.impl;

import java.io.File;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;

import mage.cards.CardDimensions;
import mage.cards.MagePermanent;
import mage.client.cards.BigCard;
import mage.client.cards.Permanent;
import mage.client.plugins.MagePlugins;
import mage.client.util.Config;
import mage.constants.Constants;
import mage.interfaces.plugin.CardPlugin;
import mage.interfaces.plugin.ThemePlugin;
import mage.util.Logging;
import mage.view.PermanentView;
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
	
	@Override
	public MagePermanent getMagePermanent(PermanentView card, BigCard bigCard, CardDimensions dimension, UUID gameId) {
		CardPlugin cp = pm.getPlugin(CardPlugin.class);
		if (cp != null) {
			return cp.getMagePermanent(card, dimension, gameId);
		} else {
			return new Permanent(card, bigCard, Config.dimensions, gameId);
		}
	}
}
