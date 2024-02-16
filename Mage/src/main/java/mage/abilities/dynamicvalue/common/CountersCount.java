
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author Styxo
 */
public class CountersCount implements DynamicValue {

    private CounterType counter;
    private FilterPermanent filter;

    public CountersCount(CounterType counterType) {
        this(counterType, new FilterPermanent());
    }

    public CountersCount(CounterType counter, FilterPermanent filter) {
        this.counter = counter;
        this.filter = filter;
    }

    protected CountersCount(final CountersCount countersCount) {
        this.counter = countersCount.counter;
        this.filter = countersCount.filter;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int count = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, game)) {
            count += permanent.getCounters(game).getCount(counter);
        }
        return count;
    }

    @Override
    public CountersCount copy() {
        return new CountersCount(this);
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return counter.getName() + " counter on " + filter.getMessage();
    }
}
