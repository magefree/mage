
package mage.abilities.costs.common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ReturnToHandChosenControlledPermanentCost extends CostImpl {

    public ReturnToHandChosenControlledPermanentCost(TargetControlledPermanent target) {
        target.setNotTarget(true);
        this.addTarget(target);
        if (target.getMaxNumberOfTargets() > 1 && target.getMaxNumberOfTargets() == target.getNumberOfTargets()) {
            this.text = "Return " + CardUtil.numberToText(target.getMaxNumberOfTargets()) + ' '
                    + target.getTargetName()
                    + (target.getTargetName().endsWith(" you control") ? "" : " you control")
                    + " to their owner's hand";
        } else {
            this.text = "return " + target.getTargetName()
                    + (target.getTargetName().endsWith(" you control") ? "" : " you control")
                    + " to its owner's hand";
        }
    }

    public ReturnToHandChosenControlledPermanentCost(ReturnToHandChosenControlledPermanentCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            if (targets.choose(Outcome.ReturnToHand, controllerId, sourceId, game)) {
                Set<Card> permanentsToReturn = new HashSet<>();
                for (UUID targetId : targets.get(0).getTargets()) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent == null) {
                        return false;
                    }
                    permanentsToReturn.add((Card) permanent);
                }
                controller.moveCards(permanentsToReturn, Zone.HAND, ability, game);
                paid = true;
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return targets.canChoose(controllerId, game);
    }

    @Override
    public ReturnToHandChosenControlledPermanentCost copy() {
        return new ReturnToHandChosenControlledPermanentCost(this);
    }

}
