
package mage.watchers;

import org.apache.log4j.Logger;

import mage.MageObject;
import mage.abilities.Ability;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public final class WatcherUtils {

    public static void logMissingWatcher(Game game, Ability source, Class watcherClass, Class usingClass) {
        MageObject sourceObject = source.getSourceObject(game);
        Logger.getLogger(usingClass).error("Needed watcher is not started " + watcherClass.getSimpleName()
                + " - " + (sourceObject == null ? " no source object" : sourceObject.getName()));
    }
}
