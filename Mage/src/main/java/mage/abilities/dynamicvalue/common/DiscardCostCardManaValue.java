package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.game.Game;

/**
 * @author LevelX2
 */
public enum DiscardCostCardManaValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        for (Cost cost : sourceAbility.getCosts()) {
            if (cost instanceof DiscardTargetCost) {
                DiscardTargetCost discardCost = (DiscardTargetCost) cost;
                return discardCost.getCards().stream().mapToInt(Card::getManaValue).sum();
            }
        }
        return 0;
    }

    @Override
    public DiscardCostCardManaValue copy() {
        return DiscardCostCardManaValue.instance;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public String getMessage() {
        return "the mana value of the discarded card";
    }
}
