package mage.abilities.costs.common;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.cards.Card;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class ExileSourceFromHandCost extends CostImpl {

    public ExileSourceFromHandCost() {
        this.text = "exile {this} from your hand";
    }

    private ExileSourceFromHandCost(ExileSourceFromHandCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        Card card = game.getCard(source.getSourceId());
        if (player != null && player.getHand().contains(source.getSourceId()) && card != null) {
            paid = player.moveCards(card, Zone.EXILED, source, game);
            source.getEffects().setValue("exiledHandCardRef", new MageObjectReference(card, game));
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player player = game.getPlayer(controllerId);
        return player != null && player.getHand().contains(source.getSourceId());
    }

    @Override
    public ExileSourceFromHandCost copy() {
        return new ExileSourceFromHandCost(this);
    }
}
