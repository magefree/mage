package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.VariableCostType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class PayVariableLoyaltyCost extends VariableCostImpl {

    // dynamic x cost modification from effects like Carth the Lion
    // GUI only (applies to -X value on X announce)
    // Example:
    // - counters: 3
    // - cost modification: +1
    // - max possible X to pay: 4
    private int costModification = 0;

    public PayVariableLoyaltyCost() {
        super(VariableCostType.NORMAL, "loyality counters to remove");
        this.text = "-X";
    }

    public PayVariableLoyaltyCost(final PayVariableLoyaltyCost cost) {
        super(cost);
        this.costModification = cost.costModification;
    }

    @Override
    public PayVariableLoyaltyCost copy() {
        return new PayVariableLoyaltyCost(this);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Permanent planeswalker = game.getPermanent(source.getSourceId());
        return planeswalker != null && planeswalker.canLoyaltyBeUsed(game);
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        return new PayLoyaltyCost(-xValue);
    }

    @Override
    public int getMaxValue(Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return 0;
        }

        int maxValue = permanent.getCounters(game).getCount(CounterType.LOYALTY);

        // apply cost modification
        if (source instanceof LoyaltyAbility) {
            LoyaltyAbility copiedAbility = ((LoyaltyAbility) source).copy();
            copiedAbility.adjustCosts(game);
            game.getContinuousEffects().costModification(copiedAbility, game);
            for (Cost cost : copiedAbility.getCosts()) {
                if (cost instanceof PayVariableLoyaltyCost) {
                    maxValue += ((PayVariableLoyaltyCost) cost).getCostModification();
                }
            }
        }

        return Math.max(0, maxValue);
    }

    public int getCostModification() {
        return costModification;
    }

    public void setCostModification(int costModification) {
        this.costModification = costModification;
    }
}
