package mage;

import mage.util.CardUtil;
import mage.util.Copyable;

import java.io.Serializable;
import java.util.Objects;

/**
 * Wrapper class for the Integer used to represent power and toughness.
 */
public class MageInt implements Serializable, Copyable<MageInt> {

    public static final MageInt EmptyMageInt = new MageInt(Integer.MIN_VALUE, "") {

        private static final String exceptionMessage = "MageInt.EmptyMageInt can't be modified.";

        @Override
        public void increaseBoostedValue(int amount) {
            throw new RuntimeException(exceptionMessage);
        }

        @Override
        public void setBoostedValue(int value) {
            throw new RuntimeException(exceptionMessage);
        }
    };

    // The original P/T value, can never change
    protected final int baseValue;
    // The current base value. Can be changed by effects such as Biomass Mutation
    protected int baseValueModified;
    // The curent final value: current base + any modifications (e.g. +1/+1 counters or "creature gets +1/+1")
    protected int boostedValue;
    // String representation of the current base value, update automatically
    protected String cardValue;

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

    /**
     * TODO:
     * Special cases:
     * - Morph
     *      setPermanentToFaceDownCreature
     *      turnFaceUp
     * - Transform
     *      transformPermanent
     * @param value
     */
    public void modifyBaseValue(int value) {
        this.baseValueModified = value;
        this.boostedValue = value;
        this.cardValue = Integer.toString(value);
    }

    public void setBoostedValue(int value) {
        this.boostedValue = value;
    }

    public void increaseBoostedValue(int amount) {
        this.boostedValue = CardUtil.overflowInc(this.boostedValue, amount);
    }

    public void resetToModifiedBaseValue() {
        this.boostedValue = this.baseValueModified;
    }

    // TODO
    public void resetToBaseValue() {
        modifyBaseValue(this.baseValue);
    }

    @Override
    public String toString() {
        return cardValue;
    }
}
