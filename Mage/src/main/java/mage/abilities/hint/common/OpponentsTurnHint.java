package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.condition.common.OpponentsTurnCondition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;

/**
 * @author TheElk801
 */
public enum OpponentsTurnHint implements Hint {
    instance;
    private static final ConditionHint hint = new ConditionHint(OpponentsTurnCondition.instance, "It's an opponent's turn");

    @Override
    public String getText(Game game, Ability ability) {
        return hint.getText(game, ability);
    }

    @Override
    public Hint copy() {
        return instance;
    }
}
