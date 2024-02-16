package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;
import mage.watchers.common.CreatedTokenWatcher;

/**
 * @author TheElk801
 */
public enum CreatedTokenThisTurnCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(instance, "You created a token this turn");

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return CreatedTokenWatcher.checkPlayer(source.getControllerId(), game);
    }

    @Override
    public String toString() {
        return "if you created a token this turn";
    }
}
