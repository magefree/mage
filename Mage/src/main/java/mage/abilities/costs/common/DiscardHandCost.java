

package mage.abilities.costs.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.cards.Card;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */

public class DiscardHandCost extends CostImpl {

    public DiscardHandCost() {
    
    }

    public DiscardHandCost(final DiscardHandCost cost) {
        super(cost);
    }

    @Override
    public DiscardHandCost copy() {
        return new DiscardHandCost(this);
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        if (player != null) {
            for (Card card : player.getHand().getCards(game)) {
                player.discard(card, ability, game);
            }
            paid = true;
        }
        return paid;
    }

    @Override
    public String getText() {
        return "Discard your hand";
    }
}