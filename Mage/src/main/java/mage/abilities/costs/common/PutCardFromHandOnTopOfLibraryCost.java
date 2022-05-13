package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */

public class PutCardFromHandOnTopOfLibraryCost extends CostImpl {

    public PutCardFromHandOnTopOfLibraryCost() {
        this.text = "Put a card from your hand on top of your library";
    }

    public PutCardFromHandOnTopOfLibraryCost(PutCardFromHandOnTopOfLibraryCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        TargetCardInHand targetCardInHand = new TargetCardInHand();
        targetCardInHand.setRequired(false);
        Card card;
        if (targetCardInHand.canChoose(controllerId, source, game)
                && controller.choose(Outcome.PreventDamage, targetCardInHand, source, game)) {
            card = game.getCard(targetCardInHand.getFirstTarget());
            paid = card != null && controller.moveCardToLibraryWithInfo(card, source, game, Zone.HAND, true, true);
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        return (controller != null
                && !controller.getHand().isEmpty());
    }

    @Override
    public PutCardFromHandOnTopOfLibraryCost copy() {
        return new PutCardFromHandOnTopOfLibraryCost(this);
    }
}

