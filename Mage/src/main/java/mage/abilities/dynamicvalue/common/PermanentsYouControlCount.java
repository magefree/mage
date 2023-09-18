package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.filter.StaticFilters;
import mage.game.Game;

/**
 * @author JayDi85
 */
public enum PermanentsYouControlCount implements DynamicValue {

    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield().count(StaticFilters.FILTER_CONTROLLED_PERMANENT, sourceAbility.getControllerId(), sourceAbility, game);
    }

    @Override
    public PermanentsYouControlCount copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "permanents you control";
    }
}
