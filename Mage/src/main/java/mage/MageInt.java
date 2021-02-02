package mage;

import mage.util.CardUtil;
import mage.util.Copyable;

import java.io.Serializable;
import java.util.Objects;

public class MageInt implements Serializable, Copyable<MageInt> {

    public static final MageInt EmptyMageInt = new MageInt(Integer.MIN_VALUE, "") {

        private static final String exceptionMessage = "MageInt.EmptyMageInt can't be modified.";

        @Override
        public void boostValue(int amount) {
            throw new RuntimeException(exceptionMessage);
        }

        @Override
        public void setValue(int value) {
            throw new RuntimeException(exceptionMessage);
        }
    };

    protected int baseValue;
    protected int baseValueModified;
    protected int boostedValue;
    protected String cardValue = "";

    public MageInt(int value) {
        this.baseValue = value;
        this.baseValueModified = baseValue;
        this.boostedValue = baseValue;
        this.cardValue = Integer.toString(value);
    }

    public MageInt(int baseValue, String cardValue) {
        this.baseValue = baseValue;
        this.baseValueModified = baseValue;
        this.boostedValue = baseValue;
        this.cardValue = cardValue;
    }

    public MageInt(int baseValue, int baseValueModified, int boostedValue, String cardValue) {
        this.baseValue = baseValue;
        this.baseValueModified = baseValueModified;
        this.boostedValue = boostedValue;
        this.cardValue = cardValue;
    }

    @Override
    public MageInt copy() {
        if (Objects.equals(this, EmptyMageInt)) {
            return this;
        }
        return new MageInt(baseValue, baseValueModified, boostedValue, cardValue);
    }

    public int getBaseValue() {
        return baseValue;
    }

    public int getBaseValueModified() {
        return baseValueModified;
    }

    public int getValue() {
        return boostedValue;
    }

    public void modifyBaseValue(int value) {
        this.baseValueModified = value;
        this.boostedValue = value;
        this.cardValue = Integer.toString(value);
    }

    public void setValue(int value) {
        this.boostedValue = value;
    }

    public void boostValue(int amount) {
        this.boostedValue = CardUtil.overflowInc(this.boostedValue, amount);
    }

    public void resetToBaseValue() {
        this.boostedValue = this.baseValueModified;
    }

    @Override
    public String toString() {
        return cardValue;
    }

}
