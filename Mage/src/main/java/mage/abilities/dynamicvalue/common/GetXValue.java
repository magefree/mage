
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.costs.VariableCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;

/**
 * @author BetaSteward_at_googlemail.com
 */
public enum GetXValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int amount = 0;
        for (VariableCost cost : sourceAbility.getCosts().getVariableCosts()) {
            amount += cost.getAmount();
        }
        return amount;
    }

    @Override
    public GetXValue copy() {
        return GetXValue.instance;
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
