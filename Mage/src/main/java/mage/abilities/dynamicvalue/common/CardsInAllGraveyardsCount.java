
package mage.abilities.dynamicvalue.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public class CardsInAllGraveyardsCount implements DynamicValue {

    private FilterCard filter;

    public CardsInAllGraveyardsCount() {
        this(new FilterCard());
    }

    public CardsInAllGraveyardsCount(FilterCard filter) {
        this.filter = filter;
    }

    public CardsInAllGraveyardsCount(CardsInAllGraveyardsCount dynamicValue) {
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
