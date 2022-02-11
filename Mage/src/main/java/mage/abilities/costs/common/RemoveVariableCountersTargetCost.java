package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.VariableCostType;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX
 */
public class RemoveVariableCountersTargetCost extends VariableCostImpl {

    protected FilterPermanent filter;
    protected CounterType counterTypeToRemove;
    protected int minValue;

    public RemoveVariableCountersTargetCost(FilterPermanent filter) {
        this(filter, null);
    }

    public RemoveVariableCountersTargetCost(FilterPermanent filter, CounterType counterTypeToRemove) {
        this(filter, counterTypeToRemove, "X", 0);
    }

    public RemoveVariableCountersTargetCost(FilterPermanent filter, CounterType counterTypeToRemove, String xText, int minValue) {
        this(filter, counterTypeToRemove, xText, minValue, null);
    }

    public RemoveVariableCountersTargetCost(FilterPermanent filter, CounterType counterTypeToRemove, String xText, int minValue, String text) {
        super(VariableCostType.NORMAL, xText, new StringBuilder(counterTypeToRemove != null ? counterTypeToRemove.getName() + ' ' : "").append("counters to remove").toString());
        this.filter = filter;
        this.counterTypeToRemove = counterTypeToRemove;
        if (text != null && !text.isEmpty()) {
            this.text = text;
        } else {
            this.text = setText();
        }
        this.minValue = minValue;
    }

    public RemoveVariableCountersTargetCost(final RemoveVariableCountersTargetCost cost) {
        super(cost);
        this.filter = cost.filter;
        this.counterTypeToRemove = cost.counterTypeToRemove;
        this.minValue = cost.minValue;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        return paid;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("Remove ").append(xText);
        if (counterTypeToRemove != null) {
            sb.append(' ').append(counterTypeToRemove.getName());
        }
        sb.append(" counters from among ").append(filter.getMessage());
        return sb.toString();
    }

    @Override
    public RemoveVariableCountersTargetCost copy() {
        return new RemoveVariableCountersTargetCost(this);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return getMaxValue(source, game) >= minValue;
    }

    @Override
    public int getMinValue(Ability source, Game game) {
        return minValue;
    }

    @Override
    public int getMaxValue(Ability source, Game game) {
        int maxValue = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
            if (counterTypeToRemove != null) {
                maxValue += permanent.getCounters(game).getCount(counterTypeToRemove);
            } else {
                for (Counter counter : permanent.getCounters(game).values()) {
                    maxValue += counter.getCount();
                }
            }
        }
        return maxValue;
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        return new RemoveCounterCost(new TargetPermanent(minValue, Integer.MAX_VALUE, filter, true), counterTypeToRemove, xValue);
    }

}
