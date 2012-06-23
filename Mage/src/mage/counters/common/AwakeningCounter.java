package mage.counters.common;

import mage.counters.Counter;

public class AwakeningCounter extends Counter<AwakeningCounter> {
    public AwakeningCounter() {
        this(1);
    }

    public AwakeningCounter(int amount) {
        super("Awakening");
        this.count = amount;
    }
}
