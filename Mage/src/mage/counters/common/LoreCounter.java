package mage.counters.common;

import mage.counters.Counter;

/**
 * Lore counter
 */
public class LoreCounter extends Counter<LoreCounter> {
    public LoreCounter() {
        super("Lore");
        this.count = 1;
    }

    public LoreCounter(int amount) {
        super("Lore");
        this.count = amount;
    }
}
