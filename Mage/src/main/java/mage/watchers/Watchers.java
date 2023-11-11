package mage.watchers;

import mage.game.Game;
import mage.game.events.GameEvent;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class Watchers extends HashMap<String, Watcher> {

    private static final Logger logger = LogManager.getLogger(Watcher.class.getSimpleName());

    public Watchers() {
    }

    private Watchers(final Watchers watchers) {
        watchers.forEach((key, value) -> this.put(key, value.copy()));
    }

    public Watchers copy() {
        return new Watchers(this);
    }

    public void add(Watcher watcher) {
        putIfAbsent(watcher.getKey(), watcher);
    }

    public void watch(GameEvent event, Game game) {
        for (Watcher watcher : this.values()) {
            watcher.watch(event, game);
        }
    }

    public void reset() {
        this.values().forEach(Watcher::reset);
    }

    @Override
    public Watcher get(Object key) {
        if (containsKey(key)) {
            return super.get(key);
        }
        // can't add game exeption here because it's an easy way to ruin any game with bugged card
        logger.error(key + " not found in watchers", new Throwable());
        return null;
    }
}
