package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author North
 */
public class CardsInControllerGraveyardCount implements DynamicValue {

    private final FilterCard filter;
    private final Integer multiplier;

    public CardsInControllerGraveyardCount() {
        this(StaticFilters.FILTER_CARD, 1);
    }

    public CardsInControllerGraveyardCount(FilterCard filter) {
        this(filter, 1);
    }

    public CardsInControllerGraveyardCount(FilterCard filter, Integer multiplier) {
        this.filter = filter;
        this.multiplier = multiplier;
    }

    public CardsInControllerGraveyardCount(final CardsInControllerGraveyardCount dynamicValue) {
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
        int value = player.getGraveyard().count(filter, playerId, sourceAbility, game);
        if (multiplier != null) {
            value *= multiplier;
        }
        return value;
    }

    @Override
    public CardsInControllerGraveyardCount copy() {
        return new CardsInControllerGraveyardCount(this);
    }

    @Override
    public String toString() {
        return multiplier == null ? "X" : multiplier.toString();
    }

    @Override
    public String getMessage() {
        return (multiplier == null ? "the number of " : "") + filter.getMessage() + " in your graveyard";
    }

    @Override
    public int getSign() {
        return multiplier == null ? 1 : multiplier;
    }
}
