/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.condition.common;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.AttackedOrBlockedThisCombatWatcher;

/**
 *
 * @author LevelX2
 */
public enum AttackedOrBlockedThisCombatSourceCondition implements Condition {

   instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourceObject = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourceObject != null) {
            AttackedOrBlockedThisCombatWatcher watcher = (AttackedOrBlockedThisCombatWatcher) game.getState().getWatchers().get(AttackedOrBlockedThisCombatWatcher.class.getSimpleName());
            if (watcher != null) {
                for (MageObjectReference mor : watcher.getAttackedThisTurnCreatures()) {
                    if (mor.refersTo(sourceObject, game)) {
                        return true;
                    }
                }
                for (MageObjectReference mor : watcher.getBlockedThisTurnCreatures()) {
                    if (mor.refersTo(sourceObject, game)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "{this} attacked or blocked this combat";
    }
}
