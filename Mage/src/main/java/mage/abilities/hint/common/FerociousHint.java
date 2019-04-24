package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;

/**
 * @author JayDi85
 */
public enum FerociousHint implements Hint {

    instance;
    private static final ConditionHint hint = new ConditionHint(FerociousCondition.instance, "You control a creature with power 4+");

    @Override
    public String getText(Game game, Ability ability) {
        return hint.getText(game, ability);
    }

    @Override
    public Hint copy() {
        return instance;
    }
}
