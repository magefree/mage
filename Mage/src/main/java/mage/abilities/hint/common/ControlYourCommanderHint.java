package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.condition.common.ControlYourCommanderCondition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;

/**
 * @author anishtilekar
 */
public enum ControlYourCommanderHint implements Hint {

    instance;
    private static final ConditionHint hint = new ConditionHint(ControlYourCommanderCondition.instance, "You control your commander");

    @Override
    public String getText(Game game, Ability ability) {
        return hint.getText(game, ability);
    }

    @Override
    public Hint copy() {
        return instance;
    }
}
