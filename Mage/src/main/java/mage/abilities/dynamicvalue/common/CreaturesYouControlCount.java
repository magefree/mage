package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.Objects;

/**
 * @author JayDi85
 */
public class CreaturesYouControlCount extends DynamicValue {

    @Override
    public int calculateBase(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield().count(StaticFilters.FILTER_CONTROLLED_CREATURES, sourceAbility.getControllerId(), sourceAbility, game);
    }

    protected CreaturesYouControlCount(final CreaturesYouControlCount other) {
        super(other);
    }

    @Override
    public CreaturesYouControlCount copy() {
        return new CreaturesYouControlCount(this);
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage(Phrasing phrasing, boolean useMultiplier) {
        if (Objects.requireNonNull(phrasing) == Phrasing.NUMBER_OF) {
            return (useMultiplier && multiplier > 1 ? getMultiplierAsWord() + " times " : "") + "the number of creatures you control";
        }
        return "for each creature you control";
    }
}
