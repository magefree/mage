/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.costs.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.CostImpl;
import mage.cards.Card;
import mage.constants.CardType;
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
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        if(controller == null) {
            return false;
        }
        return controller.getGraveyard().size() >= amount;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        Player controller = game.getPlayer(controllerId);
        if(controller != null) {
            Card topCard = null;
            for (Card card :controller.getGraveyard().getCards(game)) {
                if (card.getCardType().contains(CardType.CREATURE)) {
                    topCard = card;
                }
            }
            if (topCard != null) {
                controller.moveCardToExileWithInfo(topCard, null, "", ability.getSourceId(), game, Zone.GRAVEYARD, true);
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
