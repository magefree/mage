package mage.counters.common;

import mage.counters.Counter;

/**
 * Divinity counter.
 *
 * @author Loki
 */
public class DivinityCounter extends Counter<DivinityCounter> {

    public DivinityCounter() {
        super("Divinity");
        this.count = 1;
    }

    public DivinityCounter(int amount) {
        super("Divinity");
        this.count = amount;
    }
}