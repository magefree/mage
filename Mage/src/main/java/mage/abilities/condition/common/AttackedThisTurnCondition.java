package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author LevelX2
 */
public enum AttackedThisTurnCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        AttackedThisTurnWatcher watcher = game.getState().getWatcher(AttackedThisTurnWatcher.class);
        return watcher != null && !watcher.getAttackedThisTurnCreatures().isEmpty();
    }
}
