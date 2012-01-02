package mage.abilities.dynamicvalue.common;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

public class CountersCount implements DynamicValue {
    private CounterType counter;
    private boolean negative = false;
    
    public CountersCount(CounterType counter) {
        this.counter = counter;
    }

    public CountersCount(CounterType counter, boolean negative) {
        this.counter = counter;
        this.negative = negative;
    }

    public CountersCount(final CountersCount countersCount) {
        this.counter = countersCount.counter;
        this.negative = countersCount.negative;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        Permanent p = game.getPermanent(sourceAbility.getSourceId());
        // if permanent already leaves the battlefield, try to find counters count via last known information
        if (p == null) {
            p = (Permanent) game.getLastKnownInformation(sourceAbility.getSourceId(), Constants.Zone.BATTLEFIELD);
        }
        if (p != null) {
            return (negative ? p.getCounters().getCount(counter)*-1:  p.getCounters().getCount(counter)) ;
        }
        return 0;
    }

    @Override
    public DynamicValue clone() {
        return new CountersCount(this);
    }

    @Override
    public String toString() {
        return (negative ?"-1":"1");
    }

    @Override
    public String getMessage() {
        return counter.getName() + " counter on {this}";
    }
}
