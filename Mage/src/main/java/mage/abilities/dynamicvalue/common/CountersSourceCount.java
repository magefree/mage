package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

public class CountersSourceCount implements DynamicValue {

    private final CounterType counterType;

    public CountersSourceCount(CounterType counterType) {
        this.counterType = counterType;
    }

    protected CountersSourceCount(final CountersSourceCount countersCount) {
        this.counterType = countersCount.counterType;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent permanent = sourceAbility.getSourcePermanentOrLKI(game);
        if (permanent == null) {
            return 0;
        }
        return counterType != null
                ? permanent
                .getCounters(game)
                .getCount(counterType)
                : permanent
                .getCounters(game)
                .values()
                .stream()
                .mapToInt(Counter::getCount).sum();
    }

    @Override
    public CountersSourceCount copy() {
        return new CountersSourceCount(this);
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return (counterType != null ? counterType.toString() + ' ' : "") + "counter on {this}";
    }
}
