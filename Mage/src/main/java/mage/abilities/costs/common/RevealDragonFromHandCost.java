package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class RevealDragonFromHandCost extends RevealTargetFromHandCost {

    private static final FilterCard filter = new FilterCard("a Dragon card from your hand");
    private static final FilterPermanent filter2 = new FilterControlledPermanent(SubType.DRAGON);

    static {
        filter.add(SubType.DRAGON.getPredicate());
    }

    private boolean revealedOrControlled = false;

    public RevealDragonFromHandCost() {
        super(new TargetCardInHand(0, 1, filter));
        this.text = "you may reveal a Dragon card from your hand";
    }

    private RevealDragonFromHandCost(final RevealDragonFromHandCost cost) {
        super(cost);
        this.revealedOrControlled = cost.revealedOrControlled;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        super.pay(ability, game, source, controllerId, noMana, costToPay);
        revealedOrControlled = numberCardsRevealed > 0
                || game.getBattlefield().count(filter2, controllerId, source, game) > 0;
        return paid = true;
    }

    @Override
    public RevealDragonFromHandCost copy() {
        return new RevealDragonFromHandCost(this);
    }

    public boolean isRevealedOrControlled() {
        return revealedOrControlled;
    }
}
