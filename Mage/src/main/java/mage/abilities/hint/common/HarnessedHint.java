package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.condition.common.HarnessedCondition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;

/**
 * @author PurpleCrowbar
 */
public enum HarnessedHint implements Hint {
    instance;

    private static final ConditionHint hint = new ConditionHint(
            HarnessedCondition.instance,
            "{this} is harnessed", null,
            "{this} isn't harnessed", null, true
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
