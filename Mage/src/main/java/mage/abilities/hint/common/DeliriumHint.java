package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;

/**
 * @author JayDi85
 */
public enum DeliriumHint implements Hint {

    instance;
    private static final ConditionHint hint = new ConditionHint(DeliriumCondition.instance, "4+ card types in your graveyard");

    @Override
    public String getText(Game game, Ability ability) {
        return hint.getText(game, ability) + " (current: " + CardTypesInGraveyardCount.instance.calculate(game, ability, null) + ")";
    }

    @Override
    public Hint copy() {
        return instance;
    }
}
