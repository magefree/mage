package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;

/**
 * @author TheElk801
 */
public enum MorbidHint implements Hint {
    instance;
    private static final ConditionHint hint = new ConditionHint(
            MorbidCondition.instance, "A creature died this turn"
    );

    @Override
    public String getText(Game game, Ability ability) {
        return hint.getText(game, ability);
    }

    @Override
    public Hint copy() {
        return instance;
    }
}
