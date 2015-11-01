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
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.other.OwnerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInExile;

/**
 *
 * @author LevelX2
 */
public class ExileOpponentsCardFromExileToGraveyardCost extends CostImpl {

    public ExileOpponentsCardFromExileToGraveyardCost(boolean thatPlayersText) {
        if (false) {
            this.text = "put a card an opponent owns from exile into its owner's graveyard";
        } else {
            this.text = "put a card an opponent owns from exile into that player's graveyard";
        }
    }

    public ExileOpponentsCardFromExileToGraveyardCost(ExileOpponentsCardFromExileToGraveyardCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            FilterCard filter = new FilterCard();
            filter.add(new OwnerPredicate(TargetController.OPPONENT));
            Target target = new TargetCardInExile(filter);
            if (controller.chooseTarget(Outcome.Damage, target, ability, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    paid = true;
                    controller.moveCards(card, null, Zone.GRAVEYARD, ability, game);
                }
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            for (Card card : game.getExile().getAllCards(game)) {
                if (controller.hasOpponent(card.getOwnerId(), game)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public ExileOpponentsCardFromExileToGraveyardCost copy() {
        return new ExileOpponentsCardFromExileToGraveyardCost(this);
    }

}
