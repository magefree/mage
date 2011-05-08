package mage.abilities.keyword;


import mage.Constants;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

public class ReinforceAbility extends SimpleActivatedAbility {
    private int count;
    private Cost cost;

    public ReinforceAbility(int count, Cost cost) {
        super(Constants.Zone.HAND, new AddCountersTargetEffect(CounterType.P1P1.createInstance(count)), cost);
        this.addCost(new DiscardSourceCost());
        this.addTarget(new TargetCreaturePermanent());
        this.cost = cost.copy();
        this.count = count;
    }

    public ReinforceAbility(final ReinforceAbility ability) {
        super(ability);
        this.cost = ability.cost.copy();
        this.count = ability.count;
    }

    @Override
    public SimpleActivatedAbility copy() {
        return new ReinforceAbility(this);
    }

    @Override
    public String getRule() {
        return "Reinforce " + count + " - " + cost.getText();
    }
}
