package mage.abilities.hint.common;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.hint.Hint;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public enum CovenHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        List<String> powers = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_CREATURE,
                        ability.getControllerId(), ability, game
                )
                .stream()
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .distinct()
                .sorted()
                .mapToObj(String::valueOf)
                .collect(Collectors.toList());
        return "Different powers among creatures you control: " + powers.size()
                + (powers.size() > 0 ? " (" + String.join(", ", powers) + ')' : "");
    }

    @Override
    public Hint copy() {
        return this;
    }
}
