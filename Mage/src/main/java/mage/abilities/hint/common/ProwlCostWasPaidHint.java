package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.condition.common.ProwlCostWasPaidCondition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;

/**
 * @author JayDi85
 */
public enum ProwlCostWasPaidHint implements Hint {

    instance;
    private static final ConditionHint hint = new ConditionHint(ProwlCostWasPaidCondition.instance, "Prowl cost was paid");

    @Override
    public String getText(Game game, Ability ability) {
        return hint.getText(game, ability);
    }

    @Override
    public Hint copy() {
        return instance;
    }
}
