package mage.server.util;

import java.io.File;

import org.apache.log4j.Logger;
import mage.game.match.MatchType;
import mage.game.tournament.TournamentType;
import mage.server.util.config.GamePlugin;
import mage.server.util.config.Plugin;
import mage.utils.MageVersion;

/**
 * Helper class to load server side plugins like game types, player types, etc
 * 
 * TODO: rework config and plugins system:
 * - [ ] remove jar-fields from config.xml
 * - [ ] remove plugin loader and use registry system for game types, player types, etc
 * - [ ] keep enable lists in config.xml to allow/disable game modes, player types, etc
 * 
 * @author JayDi85
 */
public class PluginUtil {

    // TODO: remove jar-fields from config.xml -- it's useless

    protected static Logger logger = Logger.getLogger(PluginUtil.class);
    private static final MageVersion version = new MageVersion(PluginUtil.class);

    private static final File pluginFolder = new File("plugins");
    public static final PluginClassLoader classLoader = new PluginClassLoader();

    public static Class<?> loadPlugin(Plugin plugin, String lookupClassName) {
        try {
            logger.debug("Loading plugin: " + lookupClassName + " from " + plugin.getJar());
            if (plugin.getName() == null || plugin.getName().isEmpty()
                    || plugin.getJar() == null || plugin.getJar().isEmpty()
                    || lookupClassName == null || lookupClassName.isEmpty()
            ) {
                logger.error(String.format("Can't load plugin, found miss fields in config.xml: %s, %s, %s",
                        plugin.getName(), plugin.getJar(), lookupClassName
                ));
                return null;
            }

            File jarFile = new File(pluginFolder, plugin.getJar());
            if (!jarFile.exists() && !version.isDeveloperBuild()) {
                // developer build don't use jar and load classes by IDE's classpaths
                logger.error(String.format(
                        "Can't load plugin '%s':\n"
                        + "- jar file not found: %s (expected at %s)\n"
                        + "- admin: make sure config's jar name from config.xml is same as pom's <artifactId>"
                        + "- dev: make sure Mage.Server's distribution.xml contains artifactId to put it into plugins folder",
                        plugin.getName(), plugin.getJar(), jarFile.getAbsolutePath()
                ));
                return null;
            }

            classLoader.addURL(jarFile.toURI().toURL());
            return Class.forName(lookupClassName, true, classLoader);
        } catch (Throwable e) {
            logger.error("Error loading plugin " + lookupClassName + " from " + plugin.getJar(), e);
        }
        return null;
    }

    public static MatchType loadGameType(GamePlugin plugin) {
        Class<?> clazz = loadPlugin(plugin, plugin.getTypeName());
        if (clazz == null) {
            return null;
        }
        try {
            return (MatchType) clazz.getConstructor().newInstance();
        } catch (Throwable e) {
            logger.error("Error loading game type plugin " + plugin.getTypeName() + " from " + plugin.getJar(), e);
        }
        return null;
    }

    public static TournamentType loadTournamentType(GamePlugin plugin) {
        Class<?> clazz = loadPlugin(plugin, plugin.getTypeName());
        if (clazz == null) {
            return null;
        }
        try {
            return (TournamentType) clazz.getConstructor().newInstance();
        } catch (Throwable e) {
            logger.error("Error loading tournament type plugin " + plugin.getTypeName() + " from " + plugin.getJar(), e);
        }
        return null;
    }
}
