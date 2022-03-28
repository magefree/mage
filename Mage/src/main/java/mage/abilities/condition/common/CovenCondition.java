package mage.abilities.condition.common;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.Objects;

/**
 * @author TheElk801
 */
public enum CovenCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_CREATURE,
                        source.getControllerId(), source, game
                )
                .stream()
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .distinct()
                .count() >= 3;
    }

    @Override
    public String toString() {
        return "if you control three or more creatures with different powers";
    }
}
