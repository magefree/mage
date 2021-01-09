package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.condition.common.BoastCondition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;

/**
 *
 * @author weirddan455
 */
public enum BoastHint implements Hint {
    instance;
    private static final ConditionHint hint = new ConditionHint(BoastCondition.instance,"Can activate Boast ability");

    @Override
    public String getText(Game game, Ability ability) {
        return hint.getText(game, ability);
    }

    @Override
    public Hint copy() {
        return instance;
    }
}
