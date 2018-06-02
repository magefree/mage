

/**
 *
 * @author jeffwadsworth
 */

package mage.abilities.costs.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.cards.Card;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

public class CyclingDiscardCost extends CostImpl {

    public CyclingDiscardCost() {
    }

    public CyclingDiscardCost(CyclingDiscardCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return game.getPlayer(controllerId).getHand().contains(sourceId);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        if (player != null) {
            Card card = player.getHand().get(sourceId, game);
            if (card != null) {
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.CYCLE_CARD, card.getId(), card.getId(), card.getOwnerId()));
                paid = player.discard(card, null, game);
                if (paid) {
                    game.fireEvent(GameEvent.getEvent(GameEvent.EventType.CYCLED_CARD, card.getId(), card.getId(), card.getOwnerId()));
                }
            }
        }
        return paid;
    }

    @Override
    public String getText() {
        return "Discard this card";
    }

    @Override
    public CyclingDiscardCost copy() {
        return new CyclingDiscardCost(this);
    }
}
