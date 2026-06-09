package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.condition.common.TeamworkCondition;
import mage.abilities.hint.ConditionTrueHint;
import mage.abilities.hint.Hint;
import mage.game.Game;

/**
 *
 * @author muz
 */
public enum TeamworkCostWasPaidHint implements Hint {
    instance;

    private static final ConditionTrueHint hint = new ConditionTrueHint(TeamworkCondition.instance, "Teamwork cost was paid");

    @Override
    public String getText(Game game, Ability ability) {
        return hint.getText(game, ability);
    }

    @Override
    public Hint copy() {
        return instance;
    }
}
