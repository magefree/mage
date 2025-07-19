package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.VariableCostType;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;
import java.util.stream.IntStream;

/**
 * @author LevelX
 */
public class RemoveVariableCountersTargetCost extends VariableCostImpl {

    protected final FilterPermanent filter;
    protected final CounterType counterTypeToRemove;
    protected final int minValue;
    protected final boolean onlyOne;

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
        this(filter, counterTypeToRemove, xText, minValue, false, text);
    }

    public RemoveVariableCountersTargetCost(FilterPermanent filter, CounterType counterTypeToRemove, String xText, int minValue, boolean onlyOne, String text) {
        super(VariableCostType.NORMAL, xText, new StringBuilder(counterTypeToRemove != null ? counterTypeToRemove.getName() + ' ' : "").append("counters to remove").toString());
        this.filter = filter;
        this.counterTypeToRemove = counterTypeToRemove;
        this.minValue = minValue;
        this.onlyOne = onlyOne;
        if (text != null && !text.isEmpty()) {
            this.text = text;
        } else {
            this.text = setText();
        }
    }

    protected RemoveVariableCountersTargetCost(final RemoveVariableCountersTargetCost cost) {
        super(cost);
        this.filter = cost.filter;
        this.counterTypeToRemove = cost.counterTypeToRemove;
        this.minValue = cost.minValue;
        this.onlyOne = cost.onlyOne;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        return paid;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("Remove ");
        sb.append(xText);
        if (counterTypeToRemove != null) {
            sb.append(' ');
            sb.append(counterTypeToRemove.getName());
        }
        sb.append(" counters from ");
        if (onlyOne) {
            sb.append(CardUtil.addArticle(filter.getMessage()));
        } else {
            sb.append("among ");
            sb.append(filter.getMessage());
        }
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
        IntStream stream = game
                .getBattlefield()
                .getAllActivePermanents(filter, source.getControllerId(), game)
                .stream()
                .map(permanent -> permanent.getCounters(game))
                .mapToInt(counters -> counterTypeToRemove == null
                        ? counters.values().stream().mapToInt(Counter::getCount).sum()
                        : counters.getCount(counterTypeToRemove));
        if (onlyOne) {
            return stream.max().orElse(0);
        }
        return stream.sum();
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        return new RemoveCounterCost(
                new TargetPermanent(minValue, onlyOne ? 1 : Integer.MAX_VALUE, filter, true), counterTypeToRemove, xValue
        );
    }
}
