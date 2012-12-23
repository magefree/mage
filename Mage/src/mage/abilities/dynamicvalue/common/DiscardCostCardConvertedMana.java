package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.cards.Card;
import mage.game.Game;

/**
 * @author LevelX2
 */
public class DiscardCostCardConvertedMana implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        for (Cost cost: sourceAbility.getCosts()) {
            if (cost instanceof DiscardCardCost) {
                DiscardCardCost discardCost = (DiscardCardCost) cost;
                int cmc = 0;
                for (Card card :discardCost.getCards()) {
                    cmc += card.getManaCost().convertedManaCost();
                }
                return cmc;
            }
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return new DiscardCostCardConvertedMana();
    }

    @Override
    public String toString() {
        return "";
    }


    @Override
    public String getMessage() {
        return "the discarded card's converted mana cost";
    }
}
