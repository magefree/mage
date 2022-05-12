
package mage.abilities.dynamicvalue.common;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.filter.StaticFilters;
import mage.game.Game;

/**
 * @author Styxo
 */
public enum GreatestPowerAmongControlledCreaturesValue implements DynamicValue {
    instance;

    private static final Hint hint=new ValueHint("Greatest power among creatures you control",instance);
    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_CREATURE,
                        sourceAbility.getControllerId(), game
                ).stream()
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .sum();
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

    public static Hint getHint() {
        return hint;
    }
}
