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
    private String sourceName = "{this}"; // for text generation
    private boolean pluralizeCounter = false; // for text generation

    public CountersSourceCount(CounterType counterType) {
        this.counterType = counterType;
    }

    protected CountersSourceCount(final CountersSourceCount countersCount) {
        this.counterType = countersCount.counterType;
        this.sourceName = countersCount.sourceName;
        this.pluralizeCounter = countersCount.pluralizeCounter;
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

    public CountersSourceCount setSourceName(String sourceName) {
        this.sourceName = sourceName;
        return this;
    }

    public CountersSourceCount doPluralizeCounter() {
        this.pluralizeCounter = true;
        return this;
    }

    @Override
    public String getMessage() {
        return (counterType != null ? counterType.toString() + ' ' : "")
                + "counter" + (pluralizeCounter ? "s" : "")
                + " on " + sourceName;
    }
}
