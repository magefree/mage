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
public class DomainValue implements DynamicValue {

    private final int amount;
    private final boolean countTargetPlayer;
    private UUID playerId;

    public DomainValue() {
        this(1);
    }

    public DomainValue(boolean countTargetPlayer) {
        this(1, countTargetPlayer);
    }

    public DomainValue(int amount) {
        this(amount, false);
    }

    public DomainValue(int amount, boolean countTargetPlayer) {
        this.amount = amount;
        this.countTargetPlayer = countTargetPlayer;
    }

    public DomainValue(int amount, UUID playerId) {
        this(amount, false);
        this.playerId = playerId;
    }

    public DomainValue(final DomainValue dynamicValue) {
        this.amount = dynamicValue.amount;
        this.countTargetPlayer = dynamicValue.countTargetPlayer;
        this.playerId = dynamicValue.playerId;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        UUID targetPlayer;
        if (playerId != null) {
            targetPlayer = playerId;
        } else if (countTargetPlayer) {
            targetPlayer = effect.getTargetPointer().getFirst(game, sourceAbility);
        } else {
            targetPlayer = sourceAbility.getControllerId();
        }
        return game.getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS,
                        targetPlayer, sourceAbility.getSourceId(), game
                ).stream()
                .map(permanent -> SubType
                        .getBasicLands()
                        .stream()
                        .filter(subType -> permanent.hasSubtype(subType, game))
                        .collect(Collectors.toSet()))
                .flatMap(Collection::stream)
                .distinct()
                .mapToInt(x -> amount)
                .sum();
    }

    @Override
    public DomainValue copy() {
        return new DomainValue(this);
    }

    @Override
    public String toString() {
        return String.valueOf(amount);
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String getMessage() {
        return "basic land type among lands " + (countTargetPlayer ? "they control" : "you control");
    }
}
