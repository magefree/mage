package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.cards.Card;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author fireshoes
 */
public class ExileTopCardOfGraveyardCost extends CostImpl {

    public ExileTopCardOfGraveyardCost() {
        this.text = "Exile the top card of your graveyard";
    }

    private ExileTopCardOfGraveyardCost(final ExileTopCardOfGraveyardCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return false;
        }
        return !controller.getGraveyard().isEmpty();
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return paid;
        }
        Card topCard = controller.getGraveyard().getTopCard(game);
        if (topCard != null) {
            controller.moveCards(topCard, Zone.EXILED, source, game);
            paid = true;
        }
        return paid;
    }

    @Override
    public ExileTopCardOfGraveyardCost copy() {
        return new ExileTopCardOfGraveyardCost(this);
    }
}
