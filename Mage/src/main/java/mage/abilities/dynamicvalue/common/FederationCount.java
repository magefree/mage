package mage.abilities.dynamicvalue.common;

import java.util.Collection;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;

/**
 * @author muz
 */
public enum FederationCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield()
            .getActivePermanents(
                    StaticFilters.FILTER_CONTROLLED_CREATURES,
                    sourceAbility.getControllerId(), sourceAbility, game
            ).stream()
            .filter(permanent -> !permanent.hasSubtype(SubType.BORG, game))
            .map(permanent -> permanent.getSubtype(game))
            .flatMap(Collection::stream)
            .distinct()
            .mapToInt(x -> 1)
            .sum();
    }

    @Override
    public FederationCount copy() {
        return this;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the number of creature types among non-Borg creatures you control";
    }

    public Hint getHint() {
        return new ValueHint("Total creature types of non-Borg creatures you control", this);
    }
}
