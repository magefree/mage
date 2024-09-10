
package mage.abilities.dynamicvalue.common;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.filter.StaticFilters;
import mage.game.Game;

/**
 * @author TheElk801
 */
public enum GreatestToughnessAmongControlledCreaturesValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_CREATURE,
                        sourceAbility.getControllerId(), game
                )
                .stream()
                .map(MageObject::getToughness)
                .mapToInt(MageInt::getValue)
                .max()
                .orElse(0);
    }

    @Override
    public GreatestToughnessAmongControlledCreaturesValue copy() {
        return GreatestToughnessAmongControlledCreaturesValue.instance;
    }

    @Override
    public String getMessage() {
        return "the greatest toughness among creatures you control";
    }

    @Override
    public String toString() {
        return "X";
    }

}
