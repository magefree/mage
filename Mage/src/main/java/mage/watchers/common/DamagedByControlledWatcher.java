package mage.watchers.common;

import mage.MageObjectReference;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.watchers.Watcher;

import java.util.HashSet;

/**
 *
 * @author weirddan455
 */
public class DamagedByControlledWatcher extends Watcher {

    private final HashSet<MageObjectReference> damagedPermanents = new HashSet<>();

    public DamagedByControlledWatcher() {
        super(WatcherScope.PLAYER);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PERMANENT) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.isCreature(game)) {
                Permanent sourcePermanent = game.getPermanent(event.getSourceId());
                if (sourcePermanent != null && sourcePermanent.isControlledBy(controllerId)) {
                    damagedPermanents.add(new MageObjectReference(event.getTargetId(), game));
                    return;
                }
                StackObject sourceObject = game.getStack().getStackObject(event.getSourceId());
                if (sourceObject != null && sourceObject.isControlledBy(controllerId)) {
                    damagedPermanents.add(new MageObjectReference(event.getTargetId(), game));
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        damagedPermanents.clear();
    }

    public boolean wasDamaged(Permanent permanent, Game game) {
        return damagedPermanents.contains(new MageObjectReference(permanent, game));
    }
}
