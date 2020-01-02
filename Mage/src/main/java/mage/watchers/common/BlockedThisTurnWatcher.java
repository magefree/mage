
package mage.watchers.common;

import mage.MageObjectReference;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Quercitron
 */
public class BlockedThisTurnWatcher extends Watcher {

    private final Set<MageObjectReference> blockedThisTurnCreatures = new HashSet<>();

    public BlockedThisTurnWatcher() {
        super(WatcherScope.GAME);
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

    public boolean checkIfBlocked(Permanent permanent, Game game) {
        for (MageObjectReference mor : blockedThisTurnCreatures) {
            if (mor.refersTo(permanent, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void reset() {
        super.reset();
        blockedThisTurnCreatures.clear();
    }

}
