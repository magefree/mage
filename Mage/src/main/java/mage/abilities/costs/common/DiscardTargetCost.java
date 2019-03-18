
package mage.abilities.costs.common;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DiscardTargetCost extends CostImpl {

    List<Card> cards = new ArrayList<>();
    protected boolean randomDiscard;

    public DiscardTargetCost(TargetCardInHand target) {
        this(target, false);
    }

    public DiscardTargetCost(TargetCardInHand target, boolean randomDiscard) {
        this.addTarget(target);
        this.randomDiscard = randomDiscard;
        this.text = "discard " + target.getTargetName();
    }

    public DiscardTargetCost(DiscardTargetCost cost) {
        super(cost);
        this.cards.addAll(cost.cards);
        this.randomDiscard = cost.randomDiscard;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        this.cards.clear();
        this.targets.clearChosen();
        Player player = game.getPlayer(controllerId);
        if (player == null) {
            return false;
        }
        int amount = this.getTargets().get(0).getNumberOfTargets();
        if (randomDiscard) {
            this.cards.addAll(player.discard(amount, true, ability, game).getCards(game));
        } else if (targets.choose(Outcome.Discard, controllerId, sourceId, game)) {
            for (UUID targetId : targets.get(0).getTargets()) {
                Card card = player.getHand().get(targetId, game);
                if (card == null) {
                    return false;
                }
                player.discard(card, ability, game);
                this.cards.add(card);
            }
        }
        paid = cards.size() >= amount;
        return paid;
    }

    @Override
    public void clearPaid() {
        super.clearPaid();
        this.cards.clear();
        this.targets.clearChosen();
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return targets.canChoose(sourceId, controllerId, game);
    }

    @Override
    public DiscardTargetCost copy() {
        return new DiscardTargetCost(this);
    }

    public List<Card> getCards() {
        return cards;
    }
}
