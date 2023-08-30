
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LoneFox, Susucr
 */
public enum SacrificeCostManaValue implements DynamicValue {
    CREATURE("creature"),
    ENCHANTMENT("enchantment"),
    ARTIFACT("artifact"),
    PERMANENT("permanent");

    private final String type;

    private SacrificeCostManaValue(String type) {
        this.type = type;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        for (Cost cost : sourceAbility.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                SacrificeTargetCost sacrificeCost = (SacrificeTargetCost) cost;
                int totalCMC = 0;
                for (Permanent permanent : sacrificeCost.getPermanents()) {
                    totalCMC += permanent.getManaValue();
                }
                return totalCMC;
            }
        }
        return 0;
    }

    @Override
    public SacrificeCostManaValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the sacrificed " + type + "'s mana value";
    }
}
