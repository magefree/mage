
package mage.abilities.costs.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class PutSourceOnBottomOwnerLibraryCost extends CostImpl {

    public PutSourceOnBottomOwnerLibraryCost() {
        this.text = setText();
    }

    public PutSourceOnBottomOwnerLibraryCost(PutSourceOnBottomOwnerLibraryCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (player != null && sourcePermanent != null) {
            paid = true;
            player.putCardsOnBottomOfLibrary(new CardsImpl(sourcePermanent), game, ability, false);
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return game.getPermanent(source.getSourceId()) != null;
    }

    @Override
    public PutSourceOnBottomOwnerLibraryCost copy() {
        return new PutSourceOnBottomOwnerLibraryCost(this);
    }

    private String setText() {
        return "Put {this} on the bottom of its owner's library";
    }
}
