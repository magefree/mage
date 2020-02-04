
package mage.watchers.common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.watchers.Watcher;

/**
 * Watcher stores which sources did damage to anything.
 *
 * @author jeffwadsworth
 */
public class SourceDidDamageWatcher extends Watcher {

    public final Set<UUID> damageSources = new HashSet<>();

    public SourceDidDamageWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.DAMAGED_CREATURE
                || event.getType() == EventType.DAMAGED_PLANESWALKER
                || event.getType() == EventType.DAMAGED_PLAYER) {
                damageSources.add(event.getSourceId());

        }
    }

    @Override
    public void reset() {
        super.reset();
        damageSources.clear();
    }
}
