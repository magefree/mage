

package mage.abilities.costs.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCostImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PayVariableLoyaltyCost extends VariableCostImpl  {

    private int costModification = 0;

    public PayVariableLoyaltyCost() {
        super("loyality counters to remove");
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
        return planeswalker!= null && planeswalker.canLoyaltyBeUsed(game);
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        return new PayLoyaltyCost(-xValue);
    }

    @Override
    public int getMaxValue(Ability source, Game game) {
        int maxValue = 0;
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            if (source instanceof LoyaltyAbility) {
                LoyaltyAbility copiedAbility = ((LoyaltyAbility) source).copy();
                permanent.adjustCosts(copiedAbility, game);
                game.getContinuousEffects().costModification(copiedAbility, game);
                for (Cost cost : copiedAbility.getCosts()) {
                    if (cost instanceof PayVariableLoyaltyCost) {
                        maxValue += ((PayVariableLoyaltyCost) cost).getCostModification();
                    }
                }
            }
            maxValue += permanent.getCounters(game).getCount(CounterType.LOYALTY.getName());
        }
        return maxValue;
    }

    public int getCostModification() {
        return costModification;
    }

    public void setCostModification(int costModification) {
        this.costModification = costModification;
    }
}
