package mage.abilities.dynamicvalue.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.UUID;

/**
 * @author grimreap124
 */
public class ManaValueInGraveyard implements DynamicValue {

    private final FilterCard filter;
    private final Integer multiplier;

    public ManaValueInGraveyard() {
        this(StaticFilters.FILTER_CARD, 1);
    }

    public ManaValueInGraveyard(FilterCard filter) {
        this(filter, 1);
    }

    public ManaValueInGraveyard(FilterCard filter, Integer multiplier) {
        this.filter = filter;
        this.multiplier = multiplier;
    }

    protected ManaValueInGraveyard(final ManaValueInGraveyard dynamicValue) {
        this.filter = dynamicValue.filter;
        this.multiplier = dynamicValue.multiplier;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        UUID playerId = sourceAbility.getControllerId();
        Player player = game.getPlayer(playerId);
        if (player == null) {
            return 0;
        }
        int value = player.getGraveyard().stream().map(game::getCard).filter(Objects::nonNull)
                .filter(card -> filter.match(card, playerId, sourceAbility, game)).mapToInt(MageObject::getManaValue).sum();
        if (multiplier != null) {
            value *= multiplier;
        }
        return value;
    }

    @Override
    public ManaValueInGraveyard copy() {
        return new ManaValueInGraveyard(this);
    }

    @Override
    public String toString() {
        return multiplier == null ? "X" : multiplier.toString();
    }

    @Override
    public String getMessage() {
        return (multiplier == null ? "the mana value of " : "") + filter.getMessage() + " in your graveyard";
    }

    @Override
    public int getSign() {
        return multiplier == null ? 1 : multiplier;
    }
}
