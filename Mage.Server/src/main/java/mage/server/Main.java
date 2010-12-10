/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.server;

import java.net.UnknownHostException;
import mage.server.util.PluginClassLoader;
import java.io.File;
import java.io.FilenameFilter;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import mage.game.GameType;
import mage.server.game.DeckValidatorFactory;
import mage.server.game.GameFactory;
import mage.server.game.PlayerFactory;
import mage.server.util.ConfigSettings;
import mage.server.util.config.Plugin;
import mage.server.util.config.GamePlugin;
import mage.util.Copier;
import mage.util.Logging;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Main {

	private static Logger logger = Logging.getLogger(Main.class.getName());

	private final static String testModeArg = "-testMode=";
	private final static String pluginFolder = "plugins";
	private final static String version = "0.5";

	public static PluginClassLoader classLoader = new PluginClassLoader();
	public static ServerImpl server;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

		logger.info("Starting MAGE server version " + version);
		logger.info("Logging level: " + Logging.getLevel(logger));
		deleteSavedGames();
		ConfigSettings config = ConfigSettings.getInstance();
		for (GamePlugin plugin: config.getGameTypes()) {
			GameFactory.getInstance().addGameType(plugin.getName(), loadGameType(plugin), loadPlugin(plugin));
		}
		for (Plugin plugin: config.getPlayerTypes()) {
			PlayerFactory.getInstance().addPlayerType(plugin.getName(), loadPlugin(plugin));
		}
		for (Plugin plugin: config.getDeckTypes()) {
			DeckValidatorFactory.getInstance().addDeckType(plugin.getName(), loadPlugin(plugin));
		}
		boolean testMode = false;
		for (String arg: args) {
			if (arg.startsWith(testModeArg)) {
				testMode = Boolean.valueOf(arg.replace(testModeArg, ""));
			}
		}
		Copier.setLoader(classLoader);
		String ip = config.getServerAddress();
		try {
			if (ip.equals("localhost")) {
				ip = InetAddress.getLocalHost().getHostAddress();
			}
		} catch (UnknownHostException ex) {
			logger.log(Level.WARNING, "Could not get server address: ", ex);
		}
		System.setProperty("java.rmi.server.hostname", ip);
		logger.info("MAGE server - using address " + ip);
		server = new ServerImpl(config.getPort(), config.getServerName(), testMode);

    }

	private static Class<?> loadPlugin(Plugin plugin) {
		try {
			classLoader.addURL(new File(pluginFolder + "/" + plugin.getJar()).toURI().toURL());
			logger.info("Loading plugin: " + plugin.getClassName());
			return Class.forName(plugin.getClassName(), true, classLoader);
		} catch (ClassNotFoundException ex) {
			logger.log(Level.SEVERE, "Plugin not Found:" + plugin.getJar() + " - check plugin folder");
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "Error loading plugin " + plugin.getJar(), ex);
		}
		return null;
	}

	private static GameType loadGameType(GamePlugin plugin) {
		try {
			classLoader.addURL(new File(pluginFolder + "/" + plugin.getJar()).toURI().toURL());
			logger.info("Loading game type: " + plugin.getClassName());
			return (GameType) Class.forName(plugin.getTypeName(), true, classLoader).newInstance();
		} catch (ClassNotFoundException ex) {
			logger.log(Level.SEVERE, "Game type not found:" + plugin.getJar() + " - check plugin folder");
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "Error loading game type " + plugin.getJar(), ex);
		}
		return null;
	}

	private static void deleteSavedGames() {
		File directory = new File("saved/");
		if (!directory.exists())
			directory.mkdirs();
		File[] files = directory.listFiles(
			new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".game");
				}
			}
		);
		for (File file : files)
		{
			file.delete();
		}
	}

}
