
package mage.abilities.condition.common;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 * @author MTGfan
 */
public enum TargetAttackedThisTurnCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanentOrLKIBattlefield(source.getTargets().getFirstTarget());
        AttackedThisTurnWatcher watcher = game.getState().getWatcher(AttackedThisTurnWatcher.class);
        return creature != null && watcher.getAttackedThisTurnCreatures().contains(new MageObjectReference(creature, game));
    }
}
