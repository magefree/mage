package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.condition.common.BargainedCondition;
import mage.abilities.hint.ConditionTrueHint;
import mage.abilities.hint.Hint;
import mage.game.Game;

/**
 * @author Susucr
 */
public enum BargainCostWasPaidHint implements Hint {

    instance;
    private static final ConditionTrueHint hint = new ConditionTrueHint(BargainedCondition.instance, "bargained");

    @Override
    public String getText(Game game, Ability ability) {
        return hint.getText(game, ability);
    }

    @Override
    public Hint copy() {
        return instance;
    }

}
