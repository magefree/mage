package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.filter.StaticFilters;
import mage.game.Game;

/**
 * @author TheElk801
 */
public enum LandsYouControlCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND,
                sourceAbility.getControllerId(), sourceAbility, game
        );
    }

    @Override
    public LandsYouControlCount copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "lands you control";
    }
}
