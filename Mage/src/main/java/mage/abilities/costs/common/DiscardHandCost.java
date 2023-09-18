package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */

public class DiscardHandCost extends CostImpl {

    public DiscardHandCost() {
    }

    private DiscardHandCost(final DiscardHandCost cost) {
        super(cost);
    }

    @Override
    public DiscardHandCost copy() {
        return new DiscardHandCost(this);
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
        player.discard(player.getHand(), true, source, game);
        paid = true;
        return paid;
    }

    @Override
    public String getText() {
        return "discard your hand";
    }
}