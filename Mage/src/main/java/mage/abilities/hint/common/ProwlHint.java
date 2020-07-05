package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.condition.common.ProwlCondition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;

/**
 * @author JayDi85
 */
public enum ProwlHint implements Hint {

    instance;
    private static final ConditionHint hint = new ConditionHint(ProwlCondition.instance, "Prowl cost can be activated");

    @Override
    public String getText(Game game, Ability ability) {
        return hint.getText(game, ability);
    }

    @Override
    public Hint copy() {
        return instance;
    }
}
