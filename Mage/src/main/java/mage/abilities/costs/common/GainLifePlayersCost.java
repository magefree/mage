
package mage.abilities.costs.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class GainLifePlayersCost extends CostImpl {

    private final int amount;

    public GainLifePlayersCost(int amount) {
        this.amount = amount;
        this.text = new StringBuilder("you may have each other player gain ").append(amount).append(" life").toString();
    }

    public GainLifePlayersCost(GainLifePlayersCost cost) {
        super(cost);
        this.amount = cost.amount;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (!playerId.equals(controllerId)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null && !player.isCanGainLife()) {
                        // if only one other player can't gain life, the cost can't be paid
                        return false;
                    }
                }

            }
        }
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (!playerId.equals(controllerId)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.gainLife(amount, game, source);
                    }
                }
            }
            paid = true;
        }
        return paid;
    }

    @Override
    public GainLifePlayersCost copy() {
        return new GainLifePlayersCost(this);
    }

}
