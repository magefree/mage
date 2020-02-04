

package mage.abilities.costs.common;

import java.util.UUID;
import mage.abilities.Ability;
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

    public PayVariableLoyaltyCost() {
        super("loyality counters to remove");
        this.text = "-X";
    }

    public PayVariableLoyaltyCost(final PayVariableLoyaltyCost cost) {
        super(cost);
    }

    @Override
    public PayVariableLoyaltyCost copy() {
        return new PayVariableLoyaltyCost(this);
    }
    
    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        Permanent planeswalker = game.getPermanent(sourceId);
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
            maxValue = permanent.getCounters(game).getCount(CounterType.LOYALTY.getName());
        }
        return maxValue;
    }

}
