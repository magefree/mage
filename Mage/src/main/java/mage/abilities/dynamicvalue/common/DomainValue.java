package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Loki
 */
public enum DomainValue implements DynamicValue {
    REGULAR,
    TARGET,
    ACTIVE;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        UUID targetPlayer;
        switch (this) {
            case ACTIVE:
                targetPlayer = game.getActivePlayerId();
                break;
            case TARGET:
                targetPlayer = effect.getTargetPointer().getFirst(game, sourceAbility);
                break;
            case REGULAR:
                targetPlayer = sourceAbility.getControllerId();
                break;
            default:
                targetPlayer = null;
        }
        return game.getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS,
                        targetPlayer, sourceAbility, game
                ).stream()
                .map(permanent -> SubType
                        .getBasicLands()
                        .stream()
                        .filter(subType -> permanent.hasSubtype(subType, game))
                        .collect(Collectors.toSet()))
                .flatMap(Collection::stream)
                .distinct()
                .mapToInt(x -> 1)
                .sum();
    }

    @Override
    public DomainValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "1";
    }

    public int getAmount() {
        return 1;
    }

    @Override
    public String getMessage() {
        return "basic land type among lands " + (this == TARGET ? "they control" : "you control");
    }
}
