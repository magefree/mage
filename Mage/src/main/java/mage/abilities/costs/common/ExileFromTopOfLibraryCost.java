
package mage.abilities.costs.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LoneFox
 */
public class ExileFromTopOfLibraryCost extends CostImpl {

    private final int amount;

    public ExileFromTopOfLibraryCost(int amount) {
        this.amount = amount;
        this.text = "Exile the top " + (amount == 1 ? "card" : CardUtil.numberToText(amount) + " cards")
                + " of your library";
    }

    public ExileFromTopOfLibraryCost(ExileFromTopOfLibraryCost cost) {
        super(cost);
        this.amount = cost.amount;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return false;
        }
        return controller.getLibrary().size() >= amount;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            controller.moveCards(controller.getLibrary().getTopCards(game, amount), Zone.EXILED, ability, game);
            paid = true;
        }
        return paid;
    }

    @Override
    public ExileFromTopOfLibraryCost copy() {
        return new ExileFromTopOfLibraryCost(this);
    }
}
