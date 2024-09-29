package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author xenohedron
 */
public class ExileHandCost extends CostImpl {

    public ExileHandCost() {
    }

    private ExileHandCost(final ExileHandCost cost) {
        super(cost);
    }

    @Override
    public ExileHandCost copy() {
        return new ExileHandCost(this);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        if (player == null) {
            return paid;
        }
        player.moveCards(player.getHand(), Zone.EXILED, source, game);
        paid = true;
        return paid;
    }

    @Override
    public String getText() {
        return "exile your hand";
    }
}
