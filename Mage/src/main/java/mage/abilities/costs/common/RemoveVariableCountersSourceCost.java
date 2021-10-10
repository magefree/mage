package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.VariableCostType;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class RemoveVariableCountersSourceCost extends VariableCostImpl {

    protected int minimalCountersToPay = 0;
    private final String counterName;

    public RemoveVariableCountersSourceCost(Counter counter) {
        this(counter, 0);
    }

    public RemoveVariableCountersSourceCost(Counter counter, String text) {
        this(counter, 0, text);
    }

    public RemoveVariableCountersSourceCost(Counter counter, int minimalCountersToPay) {
        this(counter, minimalCountersToPay, "");
    }

    public RemoveVariableCountersSourceCost(Counter counter, int minimalCountersToPay, String text) {
        super(VariableCostType.NORMAL, counter.getName() + " counters to remove");
        this.minimalCountersToPay = minimalCountersToPay;
        this.counterName = counter.getName();
        if (text == null || text.isEmpty()) {
            this.text = "Remove X " + counterName + " counters from {this}";
        } else {
            this.text = text;
        }
    }

    public RemoveVariableCountersSourceCost(final RemoveVariableCountersSourceCost cost) {
        super(cost);
        this.minimalCountersToPay = cost.minimalCountersToPay;
        this.counterName = cost.counterName;
    }

    @Override
    public RemoveVariableCountersSourceCost copy() {
        return new RemoveVariableCountersSourceCost(this);
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        return new RemoveCountersSourceCost(new Counter(counterName, xValue));
    }

    @Override
    public int getMinValue(Ability source, Game game) {
        return minimalCountersToPay;
    }

    @Override
    public int getMaxValue(Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        return permanent != null ? permanent.getCounters(game).getCount(counterName) : 0;
    }
}
