

package mage.abilities.keyword;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 * @author LevelX2
 */
public class OutlastAbility extends ActivatedAbilityImpl {

    public OutlastAbility(Cost cost) {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), cost);
        this.addCost(new TapSourceCost());
        this.timing = TimingRule.SORCERY;
    }

    protected OutlastAbility(final OutlastAbility ability) {
        super(ability);
    }

    @Override
    public OutlastAbility copy() {
        return new OutlastAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("Outlast ").append(getManaCosts().getText());
        sb.append(" <i>(").append(getManaCosts().getText()).append(", ").append(getCosts().getText()).append(":  Put a +1/+1 counter on this creature. Outlast only as a sorcery.)</i>");
        return sb.toString();
    }

}
