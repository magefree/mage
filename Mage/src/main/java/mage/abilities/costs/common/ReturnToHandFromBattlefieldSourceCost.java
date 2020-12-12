
package mage.abilities.costs.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author Loki
 */
public class ReturnToHandFromBattlefieldSourceCost extends CostImpl {

    public ReturnToHandFromBattlefieldSourceCost() {
        this.text = "return {this} to its owner's hand";
    }

    public ReturnToHandFromBattlefieldSourceCost(ReturnToHandFromBattlefieldSourceCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(controllerId);
        if (permanent == null || controller == null) {
            return false;
        }
        controller.moveCards(permanent, Zone.HAND, ability, game);
        paid = true;
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return game.getBattlefield().containsPermanent(source.getSourceId());
    }

    @Override
    public ReturnToHandFromBattlefieldSourceCost copy() {
        return new ReturnToHandFromBattlefieldSourceCost(this);
    }
}
