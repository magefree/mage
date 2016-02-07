/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.watchers.common;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageObjectReference;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public class DamageDoneWatcher extends Watcher {

    // which object did how much damage during the turn
    public final Map<MageObjectReference, Integer> damagingObjects;

    // which object received how much damage during the turn
    public final Map<MageObjectReference, Integer> damagedObjects;

    public DamageDoneWatcher() {
        super("DamageDone", WatcherScope.GAME);
        this.damagingObjects = new HashMap<>();
        this.damagedObjects = new HashMap<>();
    }

    public DamageDoneWatcher(final DamageDoneWatcher watcher) {
        super(watcher);
        this.damagingObjects = new HashMap<>(watcher.damagingObjects);
        this.damagedObjects = new HashMap<>(watcher.damagedObjects);
    }

    @Override
    public DamageDoneWatcher copy() {
        return new DamageDoneWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGED_CREATURE:
            case DAMAGED_PLANESWALKER:
            case DAMAGED_PLAYER: {
                MageObjectReference damageSourceRef = new MageObjectReference(event.getSourceId(), game);
                int damageDone = damagingObjects.containsKey(damageSourceRef) ? damagingObjects.get(damageSourceRef) : 0;
                damagingObjects.put(damageSourceRef, damageDone + event.getAmount());

                MageObjectReference damageTargetRef = new MageObjectReference(event.getTargetId(), game);
                int damageReceived = damagedObjects.containsKey(damageTargetRef) ? damagedObjects.get(damageTargetRef) : 0;
                damagedObjects.put(damageTargetRef, damageReceived + event.getAmount());
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        damagingObjects.clear();
        damagedObjects.clear();
    }

    public int damageDoneBy(UUID objectId, int zoneChangeCounter, Game game) {
        MageObjectReference mor = new MageObjectReference(objectId, zoneChangeCounter, game);
        return damagingObjects.containsKey(mor) ? damagingObjects.get(mor) : 0;
    }

    public int damageDoneTo(UUID objectId, int zoneChangeCounter, Game game) {
        MageObjectReference mor = new MageObjectReference(objectId, zoneChangeCounter, game);
        return damagedObjects.containsKey(mor) ? damagedObjects.get(mor) : 0;
    }

    public boolean isDamaged(UUID objectId, int zoneChangeCounter, Game game) {
        MageObjectReference mor = new MageObjectReference(objectId, zoneChangeCounter, game);
        return damagedObjects.containsKey(mor);
    }

}
