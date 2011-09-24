package mage.counters.common;

import mage.counters.Counter;

/**
 * Hatchling counter
 * @author Loki
 */
public class HatchlingCounter extends Counter<HatchlingCounter> {
    public HatchlingCounter() {
        super("Hatchling");
        this.count = 1;
    }

    public HatchlingCounter(int amount) {
        super("Hatchling");
        this.count = amount;
    }
}
