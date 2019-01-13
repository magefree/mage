package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;

/**
 * @author LevelX2
 */
public enum SacrificeCostCreaturesToughness implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        for (Cost cost : sourceAbility.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                SacrificeTargetCost sacrificeCost = (SacrificeTargetCost) cost;
                return sacrificeCost.getPermanents().stream().mapToInt(p -> p.getToughness().getValue()).sum();
            }
        }
        return 0;
    }

    @Override
    public SacrificeCostCreaturesToughness copy() {
        return SacrificeCostCreaturesToughness.instance;
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
