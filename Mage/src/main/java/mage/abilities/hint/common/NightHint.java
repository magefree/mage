package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.condition.common.NightCondition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;

/**
 * @author TheElk801
 */
public enum NightHint implements Hint {
    instance;
    private static final Hint hint = new ConditionHint(
            NightCondition.instance, "It's currently night"
    );

    @Override
    public String getText(Game game, Ability ability) {
        return hint.getText(game, ability);
    }

    @Override
    public Hint copy() {
        return this;
    }
}
