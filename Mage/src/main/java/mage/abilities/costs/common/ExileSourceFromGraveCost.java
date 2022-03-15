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
 * @author magenoxx_at_gmail.com
 */
public class ExileSourceFromGraveCost extends CostImpl {

    public ExileSourceFromGraveCost() {
        this.text = "exile {this} from your graveyard";
    }

    private ExileSourceFromGraveCost(final ExileSourceFromGraveCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return paid;
        }
        Card card = game.getCard(source.getSourceId());
        if (card != null && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
            controller.moveCards(card, Zone.EXILED, source, game);
            // 117.11. The actions performed when paying a cost may be modified by effects.
            // Even if they are, meaning the actions that are performed don't match the actions
            // that are called for, the cost has still been paid.
            // so return state here is not important because the user indended to exile the target anyway
            paid = true;
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return game.getCard(source.getSourceId()) != null
                && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD;
    }

    @Override
    public ExileSourceFromGraveCost copy() {
        return new ExileSourceFromGraveCost(this);
    }
}
