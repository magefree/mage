
package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author NinthWorld
 */
public class TurnFaceDownSourceCost extends CostImpl {

    public TurnFaceDownSourceCost() {
        this.text = "Turn {this} face down";
    }

    public TurnFaceDownSourceCost(TurnFaceDownSourceCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent permanent = game.getPermanent(sourceId);
        if (permanent != null) {
            paid = permanent.turnFaceDown(game, permanent.getControllerId());
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        Permanent permanent = game.getPermanent(sourceId);
        if (permanent != null) {
            return !permanent.isFaceDown(game);
        }
        return false;
    }

    @Override
    public TurnFaceDownSourceCost copy() {
        return new TurnFaceDownSourceCost(this);
    }
}
