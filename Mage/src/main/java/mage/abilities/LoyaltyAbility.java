package mage.abilities;

import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLoyaltyCost;
import mage.abilities.costs.common.PayVariableLoyaltyCost;
import mage.abilities.effects.Effect;
import mage.constants.TimingRule;
import mage.constants.Zone;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class LoyaltyAbility extends ActivatedAbilityImpl {

    public LoyaltyAbility(Effect effect, int loyalty) {
        super(Zone.BATTLEFIELD, effect, new PayLoyaltyCost(loyalty));
        this.timing = TimingRule.SORCERY;
    }

    public LoyaltyAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, new PayVariableLoyaltyCost());
        this.timing = TimingRule.SORCERY;
    }

    protected LoyaltyAbility(final LoyaltyAbility ability) {
        super(ability);
    }

    @Override
    public LoyaltyAbility copy() {
        return new LoyaltyAbility(this);
    }

    /**
     * Change loyalty cost by amount value
     *
     * @param amount
     */
    public void increaseLoyaltyCost(int amount) {

        // loyalty cost modification rules from Carth the Lion

        // If a planeswalker’s loyalty ability normally has a cost of [+1], Carth’s ability makes it cost [+2] instead.
        // A cost of [0] would become [+1], and a cost of [-6] would become [-5].
        // (2021-06-18)
        //
        // If you somehow manage to control two Carths (perhaps because of Spark Double), the cost-changing effect is
        // cumulative. In total, loyalty abilities will cost an additional [+2] to activate.
        // (2021-06-18)
        //
        // The total cost of a planeswalker’s loyalty ability is calculated before any counters are added or removed.
        // If a loyalty ability normally costs [-3] to activate, you do not remove three counters from it and then
        // put one counter on it. You remove two counters at one time when you pay the cost.
        // (2021-06-18)
        //
        // If an effect replaces the number of counters that would be placed on a planeswalker, such as that of
        // Vorinclex, Monstrous Raider, that replacement happens only once, at the time payment is made.
        // (2021-06-18)

        // cost modification support only 1 cost item
        int staticCount = 0;
        for (Cost cost : getCosts()) {
            if (cost instanceof PayLoyaltyCost) {
                // static cost
                PayLoyaltyCost staticCost = (PayLoyaltyCost) cost;
                staticCost.setAmount(staticCost.getAmount() + amount);
                staticCount++;
            } else if (cost instanceof PayVariableLoyaltyCost) {
                // x cost (after x announce: x cost + static cost)
                PayVariableLoyaltyCost xCost = (PayVariableLoyaltyCost) cost;
                xCost.setCostModification(xCost.getCostModification() + amount);
            }
        }
        if (staticCount > 1) {
            throw new IllegalArgumentException(String.format("Loyalty ability must have only 1 static cost, but has %d: %s",
                    staticCount, this.getRule()));
        }
    }
}
