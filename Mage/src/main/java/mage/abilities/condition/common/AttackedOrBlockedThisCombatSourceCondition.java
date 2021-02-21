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
            AttackedOrBlockedThisCombatWatcher watcher = game.getState().getWatcher(AttackedOrBlockedThisCombatWatcher.class);
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
