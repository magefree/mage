
package mage.watchers;

import java.util.HashMap;
import java.util.UUID;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Watchers extends HashMap<String, Watcher> {

    public Watchers() {
    }

    public Watchers(final Watchers watchers) {
        watchers.entrySet().forEach((entry) -> this.put(entry.getKey(), entry.getValue().copy()));
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

    public Watcher get(String key, UUID id) {
        return this.get(id + key);
    }
}
