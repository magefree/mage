
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author Styxo
 */
public enum GreatestPowerAmongControlledCreaturesValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int amount = 0;
        for (Permanent p : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE, sourceAbility.getControllerId(), game
        )) {
            amount = Math.max(p.getPower().getValue(), amount);
        }
        return amount;
    }

    @Override
    public GreatestPowerAmongControlledCreaturesValue copy() {
        return GreatestPowerAmongControlledCreaturesValue.instance;
    }

    @Override
    public String getMessage() {
        return "the greatest power among creatures you control";
    }

    @Override
    public String toString() {
        return "X";
    }

}
