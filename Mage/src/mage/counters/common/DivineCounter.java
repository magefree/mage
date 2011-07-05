package mage.counters.common;

import mage.counters.Counter;

/**
 * Divine counter.
 *
 * @author Loki
 */
public class DivineCounter extends Counter<DivineCounter> {

    public DivineCounter() {
        super("Divine");
        this.count = 1;
    }

    public DivineCounter(int amount) {
        super("Divine");
        this.count = amount;
    }
}