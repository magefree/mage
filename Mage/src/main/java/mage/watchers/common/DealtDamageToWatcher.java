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

public class DealtDamageToWatcher extends Watcher {

    public final Set<MageObjectReference> dealtDamageToSource = new HashSet<>();

    public DealtDamageToWatcher() {
        super(DealtDamageToWatcher.class.getSimpleName(), WatcherScope.CARD);
    }

    public DealtDamageToWatcher(final DealtDamageToWatcher watcher) {
        super(watcher);
        this.dealtDamageToSource.addAll(watcher.dealtDamageToSource);
    }

    @Override
    public DealtDamageToWatcher copy() {
        return new DealtDamageToWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        boolean eventHasAppropriateType = event.getType() == GameEvent.EventType.DAMAGED_CREATURE ||
                event.getType() == GameEvent.EventType.DAMAGED_PLANESWALKER;
        if (eventHasAppropriateType && sourceId.equals(event.getTargetId())) {
            MageObjectReference mor = new MageObjectReference(event.getSourceId(), game);
            dealtDamageToSource.add(mor);
        }
    }

    @Override
    public void reset() {
        super.reset();
        dealtDamageToSource.clear();
    }

    public boolean didDamage(UUID sourceId, Game game) {
        MageObject mageObject = game.getObject(sourceId);
        if (mageObject != null) {
            return didDamage(new MageObjectReference(mageObject, game));
        }
        return false;
    }

    private boolean didDamage(MageObjectReference objectReference) {
        return dealtDamageToSource.contains(objectReference);
    }

    public boolean didDamage(Permanent permanent, Game game) {
        return dealtDamageToSource.contains(new MageObjectReference(permanent, game));
    }
}
