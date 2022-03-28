
package mage.abilities.costs.common;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author markedagain
 */
public class ReturnToHandFromGraveyardCost extends CostImpl {

    public ReturnToHandFromGraveyardCost(TargetCardInYourGraveyard target) {
        this.addTarget(target);
        if (target.getMaxNumberOfTargets() > 1 && target.getMaxNumberOfTargets() == target.getNumberOfTargets()) {
            this.text = new StringBuilder("return ").append(target.getMaxNumberOfTargets()).append(' ').append(target.getTargetName()).append(" from graveyard to it's owner's hand").toString();
        } else {
            this.text = new StringBuilder("return ").append(target.getTargetName()).append(" from graveyard to it's owner's hand").toString();
        }
    }

    public ReturnToHandFromGraveyardCost(ReturnToHandFromGraveyardCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            if (targets.choose(Outcome.ReturnToHand, controllerId, source.getSourceId(), source, game)) {
                Set<Card> cardsToMove = new LinkedHashSet<>();
                for (UUID targetId : targets.get(0).getTargets()) {
                    mage.cards.Card targetCard = game.getCard(targetId);
                    if (targetCard == null) {
                        return false;
                    }
                    cardsToMove.add(targetCard);
                }
                controller.moveCards(cardsToMove, Zone.HAND, ability, game);
                paid = true;
            }

        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return targets.canChoose(controllerId, source, game);
    }

    @Override
    public ReturnToHandFromGraveyardCost copy() {
        return new ReturnToHandFromGraveyardCost(this);
    }

}
