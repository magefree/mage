
package mage.watchers.common;

import java.util.HashSet;
import java.util.Set;

import mage.MageObjectReference;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

/**
 *
 * @author Quercitron
 */
public class BlockedThisTurnWatcher extends Watcher {

    private final Set<MageObjectReference> blockedThisTurnCreatures;

    public BlockedThisTurnWatcher() {
        super(BlockedThisTurnWatcher.class.getSimpleName(), WatcherScope.GAME);
        blockedThisTurnCreatures = new HashSet<>();
    }

    public BlockedThisTurnWatcher(final BlockedThisTurnWatcher watcher) {
        super(watcher);
        blockedThisTurnCreatures = new HashSet<>(watcher.blockedThisTurnCreatures);
    }

    @Override
    public Watcher copy() {
        return new BlockedThisTurnWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BLOCKER_DECLARED) {
            this.blockedThisTurnCreatures.add(new MageObjectReference(event.getSourceId(), game));
        }
    }

    public Set<MageObjectReference> getBlockedThisTurnCreatures() {
        return this.blockedThisTurnCreatures;
    }

    @Override
    public void reset() {
        super.reset();
        blockedThisTurnCreatures.clear();
    }

}
