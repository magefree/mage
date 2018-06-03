
package mage.watchers.common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.MageObject;
import mage.MageObjectReference;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DamagedByWatcher extends Watcher {

    public final Set<MageObjectReference> damagedBySource = new HashSet<>();

    private final boolean watchPlaneswalkers;

    public DamagedByWatcher() {
        this(false);
    }

    public DamagedByWatcher(boolean watchPlaneswalkers) {
        super(DamagedByWatcher.class.getSimpleName(), WatcherScope.CARD);
        this.watchPlaneswalkers = watchPlaneswalkers;
    }

    public DamagedByWatcher(final DamagedByWatcher watcher) {
        super(watcher);
        this.damagedBySource.addAll(watcher.damagedBySource);
        this.watchPlaneswalkers = watcher.watchPlaneswalkers;
    }

    @Override
    public DamagedByWatcher copy() {
        return new DamagedByWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        boolean eventHasAppropriateType = (event.getType() == EventType.DAMAGED_CREATURE) ||
                (watchPlaneswalkers && event.getType() == EventType.DAMAGED_PLANESWALKER);
        if (eventHasAppropriateType && sourceId.equals(event.getSourceId())) {
            MageObjectReference mor = new MageObjectReference(event.getTargetId(), game);
            damagedBySource.add(mor);

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
