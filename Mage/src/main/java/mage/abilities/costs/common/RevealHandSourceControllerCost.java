
package mage.abilities.costs.common;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */

public class RevealHandSourceControllerCost extends CostImpl {

    public RevealHandSourceControllerCost() {
       this.text = "reveal your hand";
    }

    public RevealHandSourceControllerCost(RevealHandSourceControllerCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            controller.revealCards(sourceObject.getName(), controller.getHand(), game);
            paid = true;
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public RevealHandSourceControllerCost copy() {
        return new RevealHandSourceControllerCost(this);
    }
}