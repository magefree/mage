package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;

import java.util.Objects;

/**
 * @author JayDi85
 */
public enum OpponentsCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getOpponents(sourceAbility.getControllerId())
                .stream()
                .map(game::getPlayer)
                .map(Objects::nonNull)
                .mapToInt(x -> x ? 1 : 0)
                .sum();
    }

    @Override
    public OpponentsCount copy() {
        return OpponentsCount.instance;
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
