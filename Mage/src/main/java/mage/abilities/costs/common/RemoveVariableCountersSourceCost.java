package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.VariableCostType;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class RemoveVariableCountersSourceCost extends VariableCostImpl {

    protected int minimalCountersToPay = 0;
    private final CounterType counterType;

    public RemoveVariableCountersSourceCost(CounterType counterType) {
        this(counterType, 0);
    }

    public RemoveVariableCountersSourceCost(CounterType counterType, String text) {
        this(counterType, 0, text);
    }

    public RemoveVariableCountersSourceCost(CounterType counterType, int minimalCountersToPay) {
        this(counterType, minimalCountersToPay, "");
    }

    public RemoveVariableCountersSourceCost(CounterType counterType, int minimalCountersToPay, String text) {
        super(VariableCostType.NORMAL, counterType.getName() + " counters to remove");
        this.minimalCountersToPay = minimalCountersToPay;
        this.counterType = counterType;
        if (text == null || text.isEmpty()) {
            this.text = "Remove X " + counterType.getName() + " counters from {this}";
        } else {
            this.text = text;
        }
    }

    public RemoveVariableCountersSourceCost(final RemoveVariableCountersSourceCost cost) {
        super(cost);
        this.minimalCountersToPay = cost.minimalCountersToPay;
        this.counterType = cost.counterType;
    }

    @Override
    public RemoveVariableCountersSourceCost copy() {
        return new RemoveVariableCountersSourceCost(this);
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        return new RemoveCountersSourceCost(new Counter(this.counterType.getName(), xValue));
    }

    @Override
    public int getMinValue(Ability source, Game game) {
        return minimalCountersToPay;
    }

    @Override
    public int getMaxValue(Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        return permanent != null ? permanent.getCounters(game).getCount(this.counterType) : 0;
    }
}
