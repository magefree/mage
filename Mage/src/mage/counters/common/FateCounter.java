package mage.counters.common;

import mage.counters.Counter;

/**
 * Fate counter.
 *
 * @author nantuko
 */
public class FateCounter extends Counter<FateCounter> {

    public FateCounter() {
        super("Fate");
        this.count = 1;
    }

    public FateCounter(int amount) {
        super("Fate");
        this.count = amount;
    }
}