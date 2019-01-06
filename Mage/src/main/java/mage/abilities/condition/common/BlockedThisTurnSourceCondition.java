
package mage.abilities.condition.common;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.BlockedThisTurnWatcher;

/**
 *
 * @author LevelX2 & L_J
 */
public enum BlockedThisTurnSourceCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        BlockedThisTurnWatcher watcher = game.getState().getWatcher(BlockedThisTurnWatcher.class);
        return sourcePermanent != null && watcher.getBlockedThisTurnCreatures().contains(new MageObjectReference(sourcePermanent, game));
    }
}
