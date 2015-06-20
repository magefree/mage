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
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */

public class DamageDoneWatcher extends Watcher {
    
    // which object did how much damage during the turn
    public Map<MageObjectReference, Integer> damagingObjects = new HashMap<>();   

    public DamageDoneWatcher() {
        super("DamageDone", WatcherScope.GAME);
    }

    public DamageDoneWatcher(final DamageDoneWatcher watcher) {
        super(watcher);
        this.damagingObjects.putAll(damagingObjects);
    }

    @Override
    public DamageDoneWatcher copy() {
        return new DamageDoneWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch(event.getType()) {
            case DAMAGED_CREATURE:
            case DAMAGED_PLANESWALKER:
            case DAMAGED_PLAYER: 
            {
                MageObjectReference mor = new MageObjectReference(event.getSourceId(), game);
                int count = damagingObjects.containsKey(mor) ? damagingObjects.get(mor) : 0;
                damagingObjects.put(mor, count + event.getAmount());
            }        
        }
    }

    @Override
    public void reset() {
        super.reset();
        damagingObjects.clear();
    }

    public int damageDone(UUID objectId, int zoneChangeCounter, Game game) {
        MageObjectReference mor = new MageObjectReference(objectId, zoneChangeCounter, game);
        return damagingObjects.containsKey(mor) ? damagingObjects.get(mor) : 0;
    }

}
