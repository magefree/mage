package mage.abilities.costs.common;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.cards.Card;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public class CyclingDiscardCost extends CostImpl {

    private MageObjectReference cycledCard = null;

    public CyclingDiscardCost() {
    }

    private CyclingDiscardCost(CyclingDiscardCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return game.getPlayer(controllerId).getHand().contains(source.getSourceId());
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        if (player != null) {
            Card card = player.getHand().get(source.getSourceId(), game);
            if (card != null) {
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.CYCLE_CARD, card.getId(), source, card.getOwnerId()));
                paid = player.discard(card, true, source, game);
                if (paid) {
                    cycledCard = new MageObjectReference(card, game);
                    game.fireEvent(GameEvent.getEvent(GameEvent.EventType.CYCLED_CARD, card.getId(), source, card.getOwnerId()));
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

    public MageObjectReference getCycledCard() {
        return cycledCard;
    }
}
