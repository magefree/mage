

package mage.cards.repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores a list of classloaders for plugins that define custom sets.
 *
 * @author Lymia
 */
public final class PluginClassloaderRegistery {
    static List<ClassLoader> pluginClassloaders = new ArrayList<>();

    public static void registerPluginClassloader(ClassLoader cl) {
        pluginClassloaders.add(cl);
    }

    public static Class<?> forName(String className) throws ClassNotFoundException {
        for(ClassLoader cl : pluginClassloaders) try {
            return Class.forName(className, true, cl);
        } catch (ClassNotFoundException c) {
            // ignored
        }
        throw new ClassNotFoundException();
    }
}
