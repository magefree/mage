package mage.abilities.costs.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.cards.Card;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ExileSourceCost extends CostImpl {

    /**
     * @param toUniqueExileZone moves the card to a source object dependant
     *                          unique exile zone, so another effect of the same source object (e.g.
     *                          Deadeye Navigator) can identify the card
     */
    public ExileSourceCost() {
        this.text = "exile {this}";
    }

    public ExileSourceCost(ExileSourceCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        MageObject sourceObject = ability.getSourceObject(game);
        Player controller = game.getPlayer(controllerId);
        if (controller == null || !(sourceObject instanceof Card)) {
            return paid;
        }
        controller.moveCards((Card) sourceObject, Zone.EXILED, source, game);
        // 117.11. The actions performed when paying a cost may be modified by effects.
        // Even if they are, meaning the actions that are performed don't match the actions
        // that are called for, the cost has still been paid.
        // so return state here is not important because the user indended to exile the target anyway
        paid = true;
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return source.getSourceObjectIfItStillExists(game) instanceof Card;
    }

    @Override
    public ExileSourceCost copy() {
        return new ExileSourceCost(this);
    }
}
