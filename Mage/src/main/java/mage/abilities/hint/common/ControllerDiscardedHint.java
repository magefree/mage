package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.condition.common.ControllerDiscardedThisTurnCondition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;

/**
 * @author TheElk801
 */
public enum ControllerDiscardedHint implements Hint {
    instance;

    private static final Hint hint = new ConditionHint(
            ControllerDiscardedThisTurnCondition.instance, "You discarded a card this turn"
    );

    @Override
    public String getText(Game game, Ability ability) {
        return hint.getText(game, ability);
    }

    @Override
    public ControllerDiscardedHint copy() {
        return instance;
    }
}
