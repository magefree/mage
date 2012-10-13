package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class SacrificeCostCreaturesToughness implements DynamicValue {
    @Override
    public int calculate(Game game, Ability sourceAbility) {
        for (Cost cost: sourceAbility.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                SacrificeTargetCost sacrificeCost = (SacrificeTargetCost) cost;
                int toughnessSum = 0;
                for (Permanent permanent :sacrificeCost.getPermanents()) {
                    toughnessSum += permanent.getToughness().getValue();
                }
                return toughnessSum;
            }
        }
        return 0;
    }

    @Override
    public DynamicValue clone() {
        return new SacrificeCostCreaturesToughness();
    }

    @Override
    public String toString() {
        return "X";
    }


    @Override
    public String getMessage() {
        return "the sacrificed creature's toughness";
    }
}
