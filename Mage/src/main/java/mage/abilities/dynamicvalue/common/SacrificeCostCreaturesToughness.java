package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class SacrificeCostCreaturesToughness implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        for (Cost cost : sourceAbility.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                SacrificeTargetCost sacrificeCost = (SacrificeTargetCost) cost;
                int toughnessSum = 0;
                for (Permanent permanent : sacrificeCost.getPermanents()) {
                    toughnessSum += permanent.getToughness().getValue();
                }
                return toughnessSum;
            }
        }
        return 0;
    }

    @Override
    public SacrificeCostCreaturesToughness copy() {
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
