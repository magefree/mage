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
public class CardsInAllGraveyardsCount implements DynamicValue {

    private final FilterCard filter;

    public CardsInAllGraveyardsCount() {
        this(StaticFilters.FILTER_CARD);
    }

    public CardsInAllGraveyardsCount(FilterCard filter) {
        this.filter = filter;
    }

    public CardsInAllGraveyardsCount(final CardsInAllGraveyardsCount dynamicValue) {
        this.filter = dynamicValue.filter.copy();
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int amount = 0;
        Player controller = game.getPlayer(sourceAbility.getControllerId());
        if (controller != null) {
            for (UUID playerUUID : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerUUID);
                if (player != null) {
                    amount += player.getGraveyard().count(filter, sourceAbility.getSourceId(), sourceAbility.getControllerId(), game);
                }
            }
        }
        return amount;
    }

    @Override
    public CardsInAllGraveyardsCount copy() {
        return new CardsInAllGraveyardsCount(this);
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return filter.getMessage() + " in all graveyards";
    }
}
