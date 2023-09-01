package mage.abilities.keyword;


import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

public class ReinforceAbility extends SimpleActivatedAbility {
    private DynamicValue count;
    private Cost cost;

    public ReinforceAbility(int count, Cost cost) {
        this(StaticValue.get(count), cost);
    }

    public ReinforceAbility(DynamicValue count, Cost cost) {
        super(Zone.HAND, new AddCountersTargetEffect(CounterType.P1P1.createInstance(0), count), cost);
        this.addCost(new DiscardSourceCost());
        this.addTarget(new TargetCreaturePermanent());
        this.cost = cost.copy();
        this.count = count;
    }


    protected ReinforceAbility(final ReinforceAbility ability) {
        super(ability);
        this.cost = ability.cost.copy();
        this.count = ability.count;
    }

    @Override
    public ReinforceAbility copy() {
        return new ReinforceAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("Reinforce ");
        sb.append(count.toString()).append("&mdash;");
        sb.append(cost.getText());
        sb.append(" <i>(").append(cost.getText()).append(", Discard this card: Put ");
        if (count.toString().equals("1")) {
            sb.append("a +1/+1 counter");
        } else {
            sb.append(CardUtil.numberToText(count.toString())).append(" +1/+1 counters");
        }
        sb.append(" on target creature.)</i>");
        return sb.toString();
    }
}
