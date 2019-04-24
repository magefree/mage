
package mage.watchers.common;

import mage.MageObjectReference;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author noahg
 */
public class WasBlockedThisTurnWatcher extends Watcher {

    private final Set<MageObjectReference> wasBlockedThisTurnCreatures;

    public WasBlockedThisTurnWatcher() {
        super(WasBlockedThisTurnWatcher.class.getSimpleName(), WatcherScope.GAME);
        wasBlockedThisTurnCreatures = new HashSet<>();
    }

    public WasBlockedThisTurnWatcher(final WasBlockedThisTurnWatcher watcher) {
        super(watcher);
        wasBlockedThisTurnCreatures = new HashSet<>(watcher.wasBlockedThisTurnCreatures);
    }

    @Override
    public Watcher copy() {
        return new WasBlockedThisTurnWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BLOCKER_DECLARED) {
            this.wasBlockedThisTurnCreatures.add(new MageObjectReference(event.getTargetId(), game));
        }
    }

    public Set<MageObjectReference> getWasBlockedThisTurnCreatures() {
        return this.wasBlockedThisTurnCreatures;
    }

    @Override
    public void reset() {
        super.reset();
        wasBlockedThisTurnCreatures.clear();
    }

}
