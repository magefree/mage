package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;
import mage.watchers.common.VoidWatcher;

/**
 * Requires {@link mage.watchers.common.VoidWatcher}
 *
 * @author TheElk801
 */
public enum VoidCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return VoidWatcher.checkPlayer(source.getControllerId(), game);
    }

    @Override
    public String toString() {
        return "a nonland permanent left the battlefield this turn or a spell was warped this turn";
    }
}
