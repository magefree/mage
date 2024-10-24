package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.ValuePhrasing;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

public class CountersSourceCount implements DynamicValue {

    private final CounterType counterType;

    /**
     * Number of counters of any type on the source permanent
     */
    public static final CountersSourceCount ANY = new CountersSourceCount((CounterType) null);

    /**
     * Number of counters of the specified type on the source permanent
     */
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

    @Override
    public String getMessage(ValuePhrasing textPhrasing) {
        String optCounterName = counterType != null ? counterType.toString() + ' ' : "";
        // TODO: add option to use "it" instead of {this}
        switch (textPhrasing) {
            case FOR_EACH:
                return optCounterName + "counter on {this}";
            case X_HIDDEN:
                return "";
            default:
                return "the number of " + optCounterName + "counters on {this}";
        }
    }
}
