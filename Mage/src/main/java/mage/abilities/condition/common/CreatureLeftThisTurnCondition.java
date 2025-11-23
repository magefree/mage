package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;
import mage.watchers.common.CreatureLeftBattlefieldWatcher;

/**
 * @author TheElk801
 */
public enum CreatureLeftThisTurnCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return CreatureLeftBattlefieldWatcher.getNumberCreatureLeft(source.getControllerId(), game) > 0;
    }

    @Override
    public String toString() {
        return "a creature left the battlefield under your control this turn";
    }
}
