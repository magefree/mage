package mage.counters.common;

import mage.counters.Counter;

/**
 * @author Loki
 */
public class HoofprintCounter extends Counter<HoofprintCounter> {

    public HoofprintCounter() {
        super("Hoofprint");
        this.count = 1;
    }

    public HoofprintCounter(int amount) {
        super("Hoofprint");
        this.count = amount;
    }
}
