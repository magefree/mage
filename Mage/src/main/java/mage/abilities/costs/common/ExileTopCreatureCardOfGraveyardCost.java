package mage.abilities.costs.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.cards.Card;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class ExileTopCreatureCardOfGraveyardCost extends CostImpl {

    private final int amount;

    public ExileTopCreatureCardOfGraveyardCost(int amount) {
        this.amount = amount;
        this.text = "Exile the top creature card of your graveyard";
    }

    public ExileTopCreatureCardOfGraveyardCost(ExileTopCreatureCardOfGraveyardCost cost) {
        super(cost);
        this.amount = cost.amount;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        if(controller == null) {
            return false;
        }
        return controller.getGraveyard().size() >= amount;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if(controller != null) {
            Card topCard = null;
            for (Card card :controller.getGraveyard().getCards(game)) {
                if (card.isCreature(game)) {
                    topCard = card;
                }
            }
            if (topCard != null) {
                controller.moveCardToExileWithInfo(topCard, null, "", source, game, Zone.GRAVEYARD, true);
                paid = true;
            }
        }
        return paid;
    }

    @Override
    public ExileTopCreatureCardOfGraveyardCost copy() {
        return new ExileTopCreatureCardOfGraveyardCost(this);
    }
}
