package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;
import mage.watchers.common.CreaturesDiedWatcher;

/**
 * @author TheElk801
 */
public enum CreatureDiedControlledCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(
            instance, "A creature died under your control this turn"
    );

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(CreaturesDiedWatcher.class)
                .getAmountOfCreaturesDiedThisTurnByController(source.getControllerId()) > 0;
    }

    @Override
    public String toString() {
        return "a creature died under your control this turn";
    }
}
