package mage.abilities.hint;

import mage.abilities.Ability;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.game.Game;

/**
 * @author JayDi85
 */
public enum DeliriumHint implements Hint {

    instance;
    private static final ConditionHint hint = new ConditionHint(DeliriumCondition.instance, "4+ card types in the graveyard");

    @Override
    public String getText(Game game, Ability ability) {
        return hint.getText(game, ability) + " (current: " + CardTypesInGraveyardCount.instance.calculate(game, ability, null) + ")";
    }

    @Override
    public Hint copy() {
        return instance;
    }
}
