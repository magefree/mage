package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;

/**
 * @author JayDi85
 */
public class OpponentsCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getOpponents(sourceAbility.getControllerId()).size();
    }

    @Override
    public OpponentsCount copy() {
        return new OpponentsCount();
    }

    @Override
    public String getMessage() {
        return "number of opponents you have";
    }

    @Override
    public String toString() {
        return "1";
    }
}