package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;

/**
 * @author Sidorovich77
 */
public enum DesertsYouControlCount implements DynamicValue {

    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield().count(new FilterControlledPermanent(SubType.DESERT), sourceAbility.getControllerId(), sourceAbility, game);
    }

    @Override
    public DesertsYouControlCount copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "deserts you control";
    }
}
