package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.condition.common.RenownedSourceCondition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;

public enum RenownedHint implements Hint {
    instance;
    private static final ConditionHint hint = new ConditionHint(RenownedSourceCondition.instance,
        "{this} is renowned", null,
        "{this} isn't renowned", null, true);

    @Override
    public String getText(Game game, Ability ability) {
        return hint.getText(game, ability);
    }

    @Override
    public Hint copy() {
        return instance;
    }
}
