
package mage.watchers.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import mage.MageObjectReference;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public class BlockedAttackerWatcher extends Watcher {

    private final Map<MageObjectReference, Set<MageObjectReference>> blockData = new HashMap<>();

    public BlockedAttackerWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BLOCKER_DECLARED) {
            MageObjectReference blocker = new MageObjectReference(event.getSourceId(), game);
            Set<MageObjectReference> blockedAttackers = blockData.get(blocker);
            if (blockedAttackers != null) {
                blockedAttackers.add(new MageObjectReference(event.getTargetId(), game));
            } else {
                blockedAttackers = new HashSet<>();
                blockedAttackers.add(new MageObjectReference(event.getTargetId(), game));
                blockData.put(blocker, blockedAttackers);
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        blockData.clear();
    }

    public boolean creatureHasBlockedAttacker(Permanent attacker, Permanent blocker, Game game) {
        Set<MageObjectReference> blockedAttackers = blockData.get(new MageObjectReference(blocker, game));
        return blockedAttackers != null && blockedAttackers.contains(new MageObjectReference(attacker, game));
    }

    public boolean creatureHasBlockedAttacker(MageObjectReference attacker, MageObjectReference blocker, Game game) {
        Set<MageObjectReference> blockedAttackers = blockData.get(blocker);
        return blockedAttackers != null && blockedAttackers.contains(attacker);
    }
}
