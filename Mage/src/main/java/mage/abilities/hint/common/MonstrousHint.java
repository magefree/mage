package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.condition.common.MonstrousCondition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;

public enum MonstrousHint implements Hint {
    instance;
    private static final ConditionHint hint = new ConditionHint(MonstrousCondition.instance,
            "{this} is monstrous", null,
            "{this} isn't monstrous", null, true);

    @Override
    public String getText(Game game, Ability ability) {
        return hint.getText(game, ability);
    }

    @Override
    public Hint copy() {
        return instance;
    }
}
