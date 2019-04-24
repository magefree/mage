package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.game.Game;

/**
 * @author fireshoes
 */
public enum DeliriumCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardTypesInGraveyardCount.instance.calculate(game, source, null) >= 4;
    }

    @Override
    public String toString() {
        return "if there are four or more card types among cards in your graveyard";
    }

}
