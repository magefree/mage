package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;

/**
 * @author LevelX2
 */
public enum RemovedCountersForCostValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        for (Cost cost : sourceAbility.getCosts()) {
            if (cost instanceof RemoveVariableCountersSourceCost) {
                return ((RemoveVariableCountersSourceCost) cost).getAmount();
            }
        }
        return 0;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public RemovedCountersForCostValue copy() {
        return RemovedCountersForCostValue.instance;
    }

    @Override
    public String toString() {
        return "X";
    }
}
