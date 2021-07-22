
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.game.Game;

/**
 * Calculates the converted mana costs of a card that was exiled from hand as
 * cost. If no card was exiled the getManaCostsToPay().getX() will be used as
 * value.
 *
 * @author LevelX2
 */
public enum ExileFromHandCostCardConvertedMana implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        for (Cost cost : sourceAbility.getCosts()) {
            if (cost.isPaid() && cost instanceof ExileFromHandCost) {
                int xValue = 0;
                for (Card card : ((ExileFromHandCost) cost).getCards()) {
                    xValue += card.getManaValue();
                }
                return xValue;
            }
        }
        return sourceAbility.getManaCostsToPay().getX();
    }

    @Override
    public ExileFromHandCostCardConvertedMana copy() {
        return ExileFromHandCostCardConvertedMana.instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "";
    }
}
