/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author LevelX2
 */

public class AttackedThisCombatWatcher extends Watcher {

    public Set<MageObjectReference> attackedThisTurnCreatures = new HashSet<>();

    public AttackedThisCombatWatcher() {
        super("AttackedThisCombat", WatcherScope.GAME);
    }

    public AttackedThisCombatWatcher(final AttackedThisCombatWatcher watcher) {
        super(watcher);
        this.attackedThisTurnCreatures.addAll(watcher.attackedThisTurnCreatures);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BEGIN_COMBAT_STEP_PRE) {
            this.attackedThisTurnCreatures.clear();
        }        
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED) {
            this.attackedThisTurnCreatures.add(new MageObjectReference(event.getSourceId(),game));
        }
    }
    
    public Set<MageObjectReference> getAttackedThisTurnCreatures() {        
        return this.attackedThisTurnCreatures;
    }

    @Override
    public AttackedThisCombatWatcher copy() {
        return new AttackedThisCombatWatcher(this);
    }

}
