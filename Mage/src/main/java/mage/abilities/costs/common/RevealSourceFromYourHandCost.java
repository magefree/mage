

package mage.abilities.costs.common;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 *
 *
 * @author LevelX2
 *
 */

public class RevealSourceFromYourHandCost extends CostImpl {
    public RevealSourceFromYourHandCost() {
        this.text = "reveal {this} from your hand";
    }
    public RevealSourceFromYourHandCost(RevealSourceFromYourHandCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        paid = false;
        Player player = game.getPlayer(controllerId);
        if (player != null) {
            Card card = player.getHand().get(ability.getSourceId(), game);
            if (card != null) {
                Cards cards = new CardsImpl(card);
                paid = true;
                player.revealCards("Reveal card cost", cards, game);
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return game.getState().getZone(source.getSourceId()) == Zone.HAND;
    }

    @Override
    public RevealSourceFromYourHandCost copy() {
        return new RevealSourceFromYourHandCost(this);
    }

}
