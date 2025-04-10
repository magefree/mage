package mage.abilities.dynamicvalue.common;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;

/**
 * @author TheElk801
 */
public enum GreatestToughnessAmongControlledCreaturesValue implements DynamicValue {
    ALL(StaticFilters.FILTER_CONTROLLED_CREATURES),
    OTHER(StaticFilters.FILTER_OTHER_CONTROLLED_CREATURES);
    private final FilterPermanent filter;
    private final Hint hint;

    GreatestToughnessAmongControlledCreaturesValue(FilterPermanent filter) {
        this.filter = filter;
        this.hint = new ValueHint("The greatest toughness among " + filter.getMessage(), this);
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getActivePermanents(filter, sourceAbility.getControllerId(), game)
                .stream()
                .map(MageObject::getToughness)
                .mapToInt(MageInt::getValue)
                .max()
                .orElse(0);
    }

    @Override
    public GreatestToughnessAmongControlledCreaturesValue copy() {
        return GreatestToughnessAmongControlledCreaturesValue.ALL;
    }

    @Override
    public String getMessage() {
        return "the greatest toughness among " + filter.getMessage();
    }

    @Override
    public String toString() {
        return "X";
    }

    public Hint getHint() {
        return hint;
    }
}
