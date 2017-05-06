package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

public class CountersSourceCount implements DynamicValue {

    private final String counterName;

    public CountersSourceCount(CounterType counter) {
        this.counterName = counter.getName();
    }

    public CountersSourceCount(String counterName) {
        this.counterName = counterName;
    }

    public CountersSourceCount(final CountersSourceCount countersCount) {
        this.counterName = countersCount.counterName;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(sourceAbility.getSourceId());
        if (permanent != null) {
            return permanent.getCounters(game).getCount(counterName);
        }
        return 0;
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
        return counterName + " counter on {this}";
    }
}
