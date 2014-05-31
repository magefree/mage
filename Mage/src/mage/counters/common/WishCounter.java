package mage.counters.common;

import mage.counters.Counter;

/**
 * Wish counter.
 *
 * @author nantuko
 */
public class WishCounter extends Counter {

    public WishCounter() {
        super("Wish");
        this.count = 1;
    }

    public WishCounter(int amount) {
        super("Wish");
        this.count = amount;
    }
}