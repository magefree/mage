package org.mage.test.serverside.base;

import mage.game.match.MatchType;
import mage.game.tournament.TournamentType;
import mage.server.game.DeckValidatorFactory;
import mage.server.game.GameFactory;
import mage.server.game.PlayerFactory;
import mage.server.tournament.TournamentFactory;
import mage.server.util.ConfigSettings;
import mage.server.util.PluginClassLoader;
import mage.server.util.config.GamePlugin;
import mage.server.util.config.Plugin;
import mage.util.Copier;
import org.junit.BeforeClass;

import java.io.File;
import java.io.FilenameFilter;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import java.util.regex.Pattern;

/**
 * @author ayratn
 */
public class MageTestBase {
	protected static Logger logger = Logger.getLogger(MageTestBase.class);

	public static PluginClassLoader classLoader = new PluginClassLoader();

	private final static String pluginFolder = "plugins";

	protected Pattern pattern = Pattern.compile("([a-zA-Z]*):([\\w]*):([a-zA-Z ,\\-.!'\\d]*):([\\d]*)(:\\{tapped\\})?");

	@BeforeClass
	public static void init() {
		Logger.getRootLogger().setLevel(Level.DEBUG);
		logger.info("Starting MAGE tests");
		logger.info("Logging level: " + logger.getLevel());
		deleteSavedGames();
		ConfigSettings config = ConfigSettings.getInstance();
		for (GamePlugin plugin : config.getGameTypes()) {
			GameFactory.getInstance().addGameType(plugin.getName(), loadGameType(plugin), loadPlugin(plugin));
		}
		for (GamePlugin plugin : config.getTournamentTypes()) {
			TournamentFactory.getInstance().addTournamentType(plugin.getName(), loadTournamentType(plugin), loadPlugin(plugin));
		}
		for (Plugin plugin : config.getPlayerTypes()) {
			PlayerFactory.getInstance().addPlayerType(plugin.getName(), loadPlugin(plugin));
		}
		for (Plugin plugin : config.getDeckTypes()) {
			DeckValidatorFactory.getInstance().addDeckType(plugin.getName(), loadPlugin(plugin));
		}
		Copier.setLoader(classLoader);
	}

	private static Class<?> loadPlugin(Plugin plugin) {
		try {
			classLoader.addURL(new File(pluginFolder + "/" + plugin.getJar()).toURI().toURL());
			logger.info("Loading plugin: " + plugin.getClassName());
			return Class.forName(plugin.getClassName(), true, classLoader);
		} catch (ClassNotFoundException ex) {
			logger.warn("Plugin not Found:" + plugin.getJar() + " - check plugin folder");
		} catch (Exception ex) {
			logger.fatal("Error loading plugin " + plugin.getJar(), ex);
		}
		return null;
	}

	private static MatchType loadGameType(GamePlugin plugin) {
		try {
			classLoader.addURL(new File(pluginFolder + "/" + plugin.getJar()).toURI().toURL());
			logger.info("Loading game type: " + plugin.getClassName());
			return (MatchType) Class.forName(plugin.getTypeName(), true, classLoader).newInstance();
		} catch (ClassNotFoundException ex) {
			logger.warn("Game type not found:" + plugin.getJar() + " - check plugin folder");
		} catch (Exception ex) {
			logger.fatal("Error loading game type " + plugin.getJar(), ex);
		}
		return null;
	}

	private static TournamentType loadTournamentType(GamePlugin plugin) {
		try {
			classLoader.addURL(new File(pluginFolder + "/" + plugin.getJar()).toURI().toURL());
			logger.info("Loading tournament type: " + plugin.getClassName());
			return (TournamentType) Class.forName(plugin.getTypeName(), true, classLoader).newInstance();
		} catch (ClassNotFoundException ex) {
			logger.warn("Tournament type not found:" + plugin.getJar() + " - check plugin folder");
		} catch (Exception ex) {
			logger.fatal("Error loading game type " + plugin.getJar(), ex);
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
		for (File file : files) {
			file.delete();
		}
	}
}
