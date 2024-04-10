package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;


/**
 * Monstrosity X
 *
 * @author notgreat
 */
public enum GetMonstrosityXValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return ((BecomesMonstrousSourceTriggeredAbility) sourceAbility).getMonstrosityValue();
    }

    @Override
    public GetMonstrosityXValue copy() {
        return GetMonstrosityXValue.instance;
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
