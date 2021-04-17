package mage.watchers.common;

import mage.MageObject;
import mage.MageObjectReference;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;

/**
 * Watcher stores which sources did damage to anything.
 *
 * @author jeffwadsworth
 */
public class SourceDidDamageWatcher extends Watcher {

    private final Set<MageObjectReference> damageSources = new HashSet<>();

    public SourceDidDamageWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            damageSources.add(new MageObjectReference(event.getSourceId(), game));
        }
    }

    public boolean checkSource(MageObject mageObject, Game game) {
        return damageSources.stream().anyMatch(mor -> mor.refersTo(mageObject, game));
    }

    @Override
    public void reset() {
        super.reset();
        damageSources.clear();
    }
}
