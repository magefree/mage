package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

public class CountersSourceCount implements DynamicValue {

    private final CounterType counterType;

    public CountersSourceCount(CounterType counterType) {
        this.counterType = counterType;
    }

    public CountersSourceCount(final CountersSourceCount countersCount) {
        this.counterType = countersCount.counterType;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent permanent = sourceAbility.getSourcePermanentOrLKI(game);
        return permanent != null ? permanent.getCounters(game).getCount(counterType) : 0;
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
        return counterType + " counter on {this}";
    }
}
