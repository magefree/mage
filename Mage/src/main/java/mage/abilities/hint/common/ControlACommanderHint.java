package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.condition.common.ControlACommanderCondition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;

/**
 * @author JayDi85
 */
public enum ControlACommanderHint implements Hint {

    instance;
    private static final ConditionHint hint = new ConditionHint(ControlACommanderCondition.instance, "You control a commander");

    @Override
    public String getText(Game game, Ability ability) {
        return hint.getText(game, ability);
    }

    @Override
    public Hint copy() {
        return instance;
    }
}
