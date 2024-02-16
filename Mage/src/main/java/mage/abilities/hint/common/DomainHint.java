package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.hint.Hint;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public enum DomainHint implements Hint {
    instance;
    private static final List<String> typesInOrder = Arrays.asList("Plains", "Island", "Swamp", "Mountain", "Forest");

    @Override
    public String getText(Game game, Ability ability) {
        List<String> landTypes = game.getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS,
                        ability.getControllerId(), ability, game
                ).stream()
                .map(permanent -> SubType
                        .getBasicLands()
                        .stream()
                        .filter(subType -> permanent.hasSubtype(subType, game))
                        .collect(Collectors.toSet()))
                .flatMap(Collection::stream)
                .distinct()
                .map(SubType::getDescription)
                .sorted(Comparator.comparing(typesInOrder::indexOf))
                .collect(Collectors.toList());
        return "Basic land types among lands you control: " + landTypes.size()
                + (landTypes.size() > 0 ? " (" + String.join(", ", landTypes) + ')' : "");
    }

    @Override
    public DomainHint copy() {
        return instance;
    }
}
