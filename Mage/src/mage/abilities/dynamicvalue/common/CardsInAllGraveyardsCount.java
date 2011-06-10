/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.dynamicvalue.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.players.PlayerList;

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

    private CardsInAllGraveyardsCount(CardsInAllGraveyardsCount dynamicValue) {
        this.filter = dynamicValue.filter;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        int amount = 0;
        PlayerList playerList = game.getPlayerList();
        for (UUID playerUUID : playerList) {
            Player player = game.getPlayer(playerUUID);
            if (player != null) {
                amount += player.getGraveyard().count(filter, game);
            }
        }
        return amount;
    }

    @Override
    public DynamicValue clone() {
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
