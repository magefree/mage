package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.DescendedWatcher;

/**
 * @author TheElk801
 */
public enum DescendedThisTurnCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return DescendedWatcher.getDescendedCount(source.getControllerId(), game) > 0;
    }

    @Override
    public String toString() {
        return "you descended this turn";
    }
}
