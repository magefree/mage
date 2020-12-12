

package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.cards.Card;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DiscardSourceCost extends CostImpl {

    private boolean nameCard = true;

    public DiscardSourceCost() {}

    public DiscardSourceCost(boolean nameCard){
        this.nameCard = nameCard;
    }

    public DiscardSourceCost(DiscardSourceCost cost) {
        super(cost);
        nameCard = cost.nameCard;
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
            paid = player.discard(card, true, source, game);

        }
        return paid;
    }

    @Override
    public String getText() {
        if(nameCard) {
            return "Discard {this}";
        }
        else{
            return "Discard this card";
        }
    }

    @Override
    public DiscardSourceCost copy() {
        return new DiscardSourceCost(this);
    }
}
