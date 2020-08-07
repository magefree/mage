
package mage.abilities.condition.common;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author LevelX2
 */
public enum AttackedThisTurnSourceCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        AttackedThisTurnWatcher watcher = game.getState().getWatcher(AttackedThisTurnWatcher.class);
        return sourcePermanent != null && watcher.getAttackedThisTurnCreatures().contains(new MageObjectReference(sourcePermanent, game));
    }
}
