package mage.counters.common;

import mage.counters.Counter;

/**
 * Ki counter.
 *
 * @author Loki
 */
public class KiCounter extends Counter<LevelCounter> {

    public KiCounter() {
        super("Ki");
        this.count = 1;
    }

    public KiCounter(int amount) {
        super("Ki");
        this.count = amount;
    }
}
