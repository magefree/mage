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
        if (controller == null) {
            return false;
        }
        return controller.getGraveyard().size() >= amount;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return paid;
        }
        Card topCard = controller
                .getGraveyard()
                .getCards(game)
                .stream()
                .filter(card -> card.isCreature(game))
                .findFirst()
                .orElse(null);
        if (topCard != null) {
            controller.moveCards(topCard, Zone.EXILED, source, game);
            paid = true;
        }
        return paid;
    }

    @Override
    public ExileTopCreatureCardOfGraveyardCost copy() {
        return new ExileTopCreatureCardOfGraveyardCost(this);
    }
}
