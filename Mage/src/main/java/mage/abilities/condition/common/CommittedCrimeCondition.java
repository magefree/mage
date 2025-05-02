package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;
import mage.watchers.common.CommittedCrimeWatcher;

/**
 * requires CommittedCrimeWatcher
 *
 * @author TheElk801
 */
public enum CommittedCrimeCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(instance, "You committed a crime this turn");

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return CommittedCrimeWatcher.checkCriminality(game, source);
    }

    @Override
    public String toString() {
        return "you've committed a crime this turn";
    }
}
