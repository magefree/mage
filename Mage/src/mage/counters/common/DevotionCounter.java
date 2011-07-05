package mage.counters.common;

import mage.counters.Counter;

/**
 * Devotion counter.
 *
 * @author Loki
 */
public class DevotionCounter extends Counter<DevotionCounter> {

    public DevotionCounter() {
        super("Devotion");
        this.count = 1;
    }

    public DevotionCounter(int amount) {
        super("Devotion");
        this.count = amount;
    }
}