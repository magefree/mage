
package mage.watchers.common;

import mage.MageObject;
import mage.MageObjectReference;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DamagedByWatcher extends Watcher {

    private final Set<MageObjectReference> damagedBySource = new HashSet<>();

    private final boolean watchPlaneswalkers;

    public DamagedByWatcher(boolean watchPlaneswalkers) {
        super(WatcherScope.CARD);
        this.watchPlaneswalkers = watchPlaneswalkers;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.DAMAGED_PERMANENT) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && !watchPlaneswalkers && !permanent.isCreature(game)) {
            return;
        }
        if (sourceId.equals(event.getSourceId())) {
            damagedBySource.add(new MageObjectReference(event.getTargetId(), game));
        }
    }

    @Override
    public void reset() {
        super.reset();
        damagedBySource.clear();
    }

    public boolean wasDamaged(UUID sourceId, Game game) {
        MageObject mageObject = game.getObject(sourceId);
        if (mageObject instanceof Permanent) {
            return wasDamaged((Permanent) mageObject, game);
        }
        return false;
    }

    public boolean wasDamaged(Permanent permanent, Game game) {
        return damagedBySource.contains(new MageObjectReference(permanent, game));
    }
}
