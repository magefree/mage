
package mage.counters;

import mage.util.Copyable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class Counters extends HashMap<String, Counter> implements Serializable, Copyable<Counters> {

    public Counters() {
    }

    protected Counters(final Counters counters) {
        for (Map.Entry<String, Counter> entry : counters.entrySet()) {
            this.put(entry.getKey(), entry.getValue().copy());
        }
    }

    public Counters copy() {
        return new Counters(this);
    }

    public Counters addCounter(Counter counter) {
        if (!containsKey(counter.name)) {
            put(counter.name, counter);
        } else {
            get(counter.name).add(counter.getCount());
        }
        return this;
    }

    public boolean removeCounter(CounterType counterType, int amount) {
        return removeCounter(counterType.getName(), amount);
    }

    public boolean removeCounter(String name, int amount) {
        if (this.containsKey(name)) {
            this.get(name).remove(amount);
            if (this.get(name).getCount() == 0) {
                this.remove(name);
            }
            return true;
        }
        return false;
    }

    public int getCount(String name) {
        if (this.containsKey(name)) {
            return this.get(name).getCount();
        }
        return 0;
    }

    public boolean containsKey(CounterType counterType) {
        return getCount(counterType) > 0;
    }

    public int getTotalCount() {
        return this.values().stream().mapToInt(Counter::getCount).sum();
    }

    public int getCount(CounterType type) {
        if (this.containsKey(type.getName())) {
            return this.get(type.getName()).getCount();
        }
        return 0;
    }

    public List<BoostCounter> getBoostCounters() {
        return values()
                .stream()
                .filter(BoostCounter.class::isInstance)
                .map(BoostCounter.class::cast)
                .collect(Collectors.toList());
    }

    public List<AbilityCounter> getAbilityCounters() {
        return values()
                .stream()
                .filter(AbilityCounter.class::isInstance)
                .map(AbilityCounter.class::cast)
                .collect(Collectors.toList());
    }
}
