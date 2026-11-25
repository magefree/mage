package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.filter.StaticFilters;
import mage.game.Game;

public enum StrangeNewWorldsValue implements DynamicValue {
    instance;

    private static final DifferentlyNamedPermanentCount landCount =
        new DifferentlyNamedPermanentCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS);

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return landCount.calculate(game, sourceAbility, effect);
    }

    @Override
    public StrangeNewWorldsValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return landCount.getMessage();
    }

    @Override
    public String toString() {
        return landCount.toString();
    }
}
