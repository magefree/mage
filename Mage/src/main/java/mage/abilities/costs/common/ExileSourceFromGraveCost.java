
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
 * @author magenoxx_at_gmail.com
 */
public class ExileSourceFromGraveCost extends CostImpl {

    public ExileSourceFromGraveCost() {
        this.text = "Exile this card from your graveyard";
    }

    public ExileSourceFromGraveCost(ExileSourceFromGraveCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            Card card = game.getCard(sourceId);
            if (card != null && game.getState().getZone(sourceId) == Zone.GRAVEYARD) {
                controller.moveCardToExileWithInfo(card, null, "", sourceId, game, Zone.GRAVEYARD, true);
                // 117.11. The actions performed when paying a cost may be modified by effects.
                // Even if they are, meaning the actions that are performed don't match the actions
                // that are called for, the cost has still been paid.
                // so return state here is not important because the user indended to exile the target anyway
                paid = true;
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        Card card = game.getCard(sourceId);
        return card != null && game.getState().getZone(sourceId) == Zone.GRAVEYARD;
    }

    @Override
    public ExileSourceFromGraveCost copy() {
        return new ExileSourceFromGraveCost(this);
    }

}
