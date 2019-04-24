/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.DamageDoneWatcher;

/**
 *
 * @author LevelX2
 */

public class SourceDealtDamageCondition implements Condition {
    private final int value;

    public SourceDealtDamageCondition(int value) {
        this.value = value;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DamageDoneWatcher watcher = (DamageDoneWatcher) game.getState().getWatchers().get(DamageDoneWatcher.class.getSimpleName());
        return watcher != null && watcher.damageDoneBy(source.getSourceId(), source.getSourceObjectZoneChangeCounter(), game) >= value;
    }

    @Override
    public String toString() {
        return "{this} has dealt " + value + " or more damage this turn" ;
    }
    
    
}