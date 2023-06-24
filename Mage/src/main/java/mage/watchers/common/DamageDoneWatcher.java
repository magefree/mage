package mage.watchers.common;

import mage.MageObjectReference;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author LevelX2
 */
public class DamageDoneWatcher extends Watcher {

    // which object did how much damage during the turn
    private final Map<MageObjectReference, Integer> damagingObjects;

    // which object received how much damage during the turn
    private final Map<MageObjectReference, Integer> damagedObjects;

    // Which object damaged which player(s)
    private final Map<MageObjectReference, Integer> objectsToPlayersDamaged;

    public Map<MageObjectReference, Integer> getDamagingObjects() {
        return damagingObjects;
    }

    public Map<MageObjectReference, Integer> getDamagedObjects() {
        return damagedObjects;
    }



    public DamageDoneWatcher() {
        super(WatcherScope.GAME);
        this.damagingObjects = new HashMap<>();
        this.damagedObjects = new HashMap<>();
        this.objectsToPlayersDamaged = new HashMap<>();
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGED_PERMANENT:
            case DAMAGED_PLAYER: {
                MageObjectReference damageSourceRef = new MageObjectReference(event.getSourceId(), game);
                damagingObjects.putIfAbsent(damageSourceRef, 0);
                damagingObjects.compute(damageSourceRef, (k, damage) -> damage + event.getAmount());

                MageObjectReference damageTargetRef = new MageObjectReference(event.getTargetId(), game);
                damagedObjects.putIfAbsent(damageTargetRef, 0);
                damagedObjects.compute(damageTargetRef, (k, damage) -> damage + event.getAmount());

                if (game.getPlayer(event.getTargetId()) != null) {
                    objectsToPlayersDamaged.putIfAbsent(damageSourceRef, 0);
                    objectsToPlayersDamaged.compute(damageSourceRef, (k, numPlayers) -> numPlayers + 1);
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        damagingObjects.clear();
        damagedObjects.clear();
        objectsToPlayersDamaged.clear();
    }

    public int damageDoneBy(UUID objectId, int zoneChangeCounter, Game game) {
        MageObjectReference mor = new MageObjectReference(objectId, zoneChangeCounter, game);
        return damagingObjects.getOrDefault(mor, 0);
    }

    public int damageDoneTo(UUID objectId, int zoneChangeCounter, Game game) {
        MageObjectReference mor = new MageObjectReference(objectId, zoneChangeCounter, game);
        return damagedObjects.getOrDefault(mor, 0);
    }

    public boolean isDamaged(UUID objectId, int zoneChangeCounter, Game game) {
        MageObjectReference mor = new MageObjectReference(objectId, zoneChangeCounter, game);
        return damagedObjects.containsKey(mor);
    }

    public boolean damagedAPlayer(UUID objectId, int zoneChangeCounter, Game game) {
        MageObjectReference mor = new MageObjectReference(objectId, zoneChangeCounter, game);
        return objectsToPlayersDamaged.containsKey(mor);
    }

}
