/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public class RemovedCountersForCostValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        for (Cost cost: sourceAbility.getCosts()) {
            if (cost instanceof RemoveVariableCountersSourceCost) {
                return ((RemoveVariableCountersSourceCost) cost).getAmount();
            }
        }
        return 0;
    }

    @Override
    public String getMessage() {
          return "number of removed counters";
    }

    @Override
    public DynamicValue copy() {
        return new RemovedCountersForCostValue();
    }

    @Override
    public String toString() {
        return "X";
    }

}
