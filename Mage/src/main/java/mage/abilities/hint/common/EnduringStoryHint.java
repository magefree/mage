package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.condition.common.EnduringStoryCondition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;

/**
 * @author muz
 */
public enum EnduringStoryHint implements Hint {

    instance;
    private static final ConditionHint hint = new ConditionHint(EnduringStoryCondition.instance, "You have an enduring story");

    @Override
    public String getText(Game game, Ability ability) {
        return hint.getText(game, ability);
    }

    @Override
    public Hint copy() {
        return instance;
    }
}
