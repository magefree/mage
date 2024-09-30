package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;
import mage.watchers.common.PlayerGainedLifeWatcher;
import mage.watchers.common.PlayerLostLifeWatcher;

/**
 * Needs PlayerGainedLifeWatcher to work
 *
 * @author TheElk801
 */
public enum YouGainedOrLostLifeCondition implements Condition {
    instance;

    private static final Hint hint = new ConditionHint(instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(PlayerGainedLifeWatcher.class)
                .getLifeGained(source.getControllerId()) > 0
                || game
                .getState()
                .getWatcher(PlayerLostLifeWatcher.class)
                .getLifeLost(source.getControllerId()) > 0;
    }

    @Override
    public String toString() {
        return "you gained or lost life this turn";
    }
}
