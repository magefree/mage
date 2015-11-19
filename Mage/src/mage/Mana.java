/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage;

import java.io.Serializable;
import java.util.Objects;
import java.util.logging.Logger;

import mage.constants.ColoredManaSymbol;
import mage.constants.ManaType;
import mage.filter.FilterMana;
import mage.util.Copyable;
import mage.util.Logging;

/**
 * Representation of a mana pool. Can contain colored and colorless mana.
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Mana implements Comparable<Mana>, Serializable, Copyable<Mana> {

    private static Logger logger = Logging.getLogger(Mana.class.getName());

    public static final String RED = "RED";
    public static final String GREEN = "GREEN";
    public static final String BLUE = "BLUE";
    public static final String WHITE = "WHITE";
    public static final String BLACK = "BLACK";
    public static final String COLORLESS = "COLORLESS";
    public static final String ANY = "ANY";

    protected int red;
    protected int green;
    protected int blue;
    protected int white;
    protected int black;
    protected int colorless;
    protected int any;
    protected boolean flag = false;

    //TODO THIS IS UNSAFE AND MUTABLE
    //TODO THIS SHOULD BE REMOVED
    public static final Mana RedMana = RedMana(1);
    public static final Mana GreenMana = GreenMana(1);
    public static final Mana BlueMana = BlueMana(1);
    public static final Mana WhiteMana = WhiteMana(1);
    public static final Mana BlackMana = BlackMana(1);
    public static final Mana ColorlessMana = ColorlessMana(1);

    public Mana() {
    }

    /**
     * Copy constructor.
     *
     * @param mana The {@link Mana} to copy from. Can not be null.
     */
    public Mana(final Mana mana) {
        Objects.requireNonNull(mana, "The passed in Mana can not be null");
        this.red = mana.red;
        this.green = mana.green;
        this.blue = mana.blue;
        this.white = mana.white;
        this.black = mana.black;
        this.colorless = mana.colorless;
        this.any = mana.any;
        this.flag = mana.flag;
    }


    /**
     * Creates a {@link Mana} object with the mana passed in.
     *
     * @param red       Total red mana to add.
     * @param green     Total green mana to add.
     * @param blue      Total blue mana to add.
     * @param white     Total white mana to add.
     * @param black     Total black mana to add.
     * @param colorless Total colorless mana to add.
     * @param any       Total any colored mana to add.
     */
    public Mana(final int red, final int green, final int blue, final int white,
                final int black, final int colorless, final int any) {
        this.red = notNegative(red, RED);
        this.green = notNegative(green, GREEN);
        this.blue = notNegative(blue, BLUE);
        this.white = notNegative(white, WHITE);
        this.black = notNegative(black, BLACK);
        this.colorless = notNegative(colorless, COLORLESS);
        this.any = notNegative(any, ANY);
    }


    /**
     * Creates a {@link Mana} object from the {@link ColoredManaSymbol} {@code color}.
     *
     * @param color The {@link ColoredManaSymbol} to create {@link Mana} from.
     *              Can not be null.
     */
    public Mana(final ColoredManaSymbol color) {
        Objects.requireNonNull(color, "The passed in ColoredManaSymbol can not be null");
        switch (color) {
            case G:
                green = 1;
                break;
            case R:
                red = 1;
                break;
            case B:
                black = 1;
                break;
            case U:
                blue = 1;
                break;
            case W:
                white = 1;
                break;
            default:
                throw new IllegalArgumentException("Unknown color " + color.getColorName());
        }
    }

    /**
     * Creates a {@link Mana} object with {@code num} Red mana.
     *
     * @param num The amount of Red mana to add. Can not be negative.
     * @return {@link Mana} object with {@code num} Red mana.
     */
    public static Mana RedMana(final int num) {
        return new Mana(notNegative(num, RED), 0, 0, 0, 0, 0, 0);
    }

    /**
     * Creates a {@link Mana} object with {@code num} Green mana.
     *
     * @param num The amount of Green mana to add. Can not be negative.
     * @return {@link Mana} object with {@code num} Green mana.
     */
    public static Mana GreenMana(final int num) {
        return new Mana(0, notNegative(num, GREEN), 0, 0, 0, 0, 0);
    }

    /**
     * Creates a {@link Mana} object with {@code num} Blue mana.
     *
     * @param num The amount of Blue mana to add. Can not be negative.
     * @return {@link Mana} object with {@code num} Blue mana.
     */
    public static Mana BlueMana(final int num) {
        return new Mana(0, 0, notNegative(num, BLUE), 0, 0, 0, 0);
    }

    /**
     * Creates a {@link Mana} object with {@code num} White mana.
     *
     * @param num The amount of White mana to add. Can not be negative.
     * @return {@link Mana} object with {@code num} White mana.
     */
    public static Mana WhiteMana(final int num) {
        return new Mana(0, 0, 0, notNegative(num, WHITE), 0, 0, 0);
    }

    /**
     * Creates a {@link Mana} object with {@code num} Black mana.
     *
     * @param num The amount of Black mana to add. Can not be negative.
     * @return {@link Mana} object with {@code num} Black mana.
     */
    public static Mana BlackMana(final int num) {
        return new Mana(0, 0, 0, 0, notNegative(num, BLACK), 0, 0);
    }

    /**
     * Creates a {@link Mana} object with {@code num} Colorless mana.
     *
     * @param num The amount of Colorless mana to add. Can not be negative.
     * @return {@link Mana} object with {@code num} Colorless mana.
     */
    public static Mana ColorlessMana(final int num) {
        return new Mana(0, 0, 0, 0, 0, notNegative(num, COLORLESS), 0);
    }


    /**
     * Increases the mana in this object by the relative mana in the passed in {@link Mana} object.
     *
     * @param mana {@link Mana} object to increase this mana by.
     */
    public void add(final Mana mana) {
        red += mana.red;
        green += mana.green;
        blue += mana.blue;
        white += mana.white;
        black += mana.black;
        colorless += mana.colorless;
        any += mana.any;
    }

    /**
     * Increases the Red mana by one.
     */
    public void increaseRed() {
        red++;
    }

    /**
     * Increases the Green mana by one.
     */
    public void increaseGreen() {
        green++;
    }

    /**
     * Increases the Blue mana by one.
     */
    public void increaseBlue() {
        blue++;
    }

    /**
     * Increases the White mana by one.
     */
    public void increaseWhite() {
        white++;
    }

    /**
     * Increases the Black mana by one.
     */
    public void increaseBlack() {
        black++;
    }

    /**
     * Increases the Colorless mana by one.
     */
    public void increaseColorless() {
        colorless++;
    }

    /**
     * Subtracts the passed in mana values from this instance. Will not
     * reduce this instances mana below 0.
     *
     * @param mana mana values to subtract
     */
    public void subtract(final Mana mana) throws ArithmeticException {
        red = validateSubtraction(red, mana.red);
        green = validateSubtraction(green, mana.green);
        blue = validateSubtraction(blue, mana.blue);
        white = validateSubtraction(white, mana.white);
        black = validateSubtraction(black, mana.black);
        colorless = validateSubtraction(colorless, mana.colorless);
        any = validateSubtraction(any, mana.any);
    }

    /**
     * Ensures subtraction will not result in a negative number.
     *
     * @param lhs left hand side operand
     * @param rhs right hand side operand
     * @return returns the non-negative subtraction result
     * @throws ArithmeticException thrown when the result of the subtraction
     *                             is less than 0.
     */
    private int validateSubtraction(final int lhs, final int rhs) throws ArithmeticException {
        int result = lhs - rhs;
        if (result < 0) {
            throw new ArithmeticException("You can not subtract below 0");
        }
        return result;
    }


    /**
     * Subtracts the passed in mana values from this instance. Will not
     * reduce this instances mana below 0. The difference between this and
     * {@code subtract()} is that if we do not have the available colorlesss
     * mana to pay, we take mana from our colored mana pools.
     *
     * @param mana mana values to subtract
     * @throws ArithmeticException thrown if there is not enough available
     *                             colored mana to make up the negative colorless cost
     */
    public void subtractCost(final Mana mana) throws ArithmeticException {
        red = validateSubtraction(red, mana.red);
        green = validateSubtraction(green, mana.green);
        blue = validateSubtraction(blue, mana.blue);
        white = validateSubtraction(white, mana.white);
        black = validateSubtraction(black, mana.black);
        any = validateSubtraction(any, mana.any);
        colorless -= mana.colorless; // can be minus, will use remaining mana to pay

        while (colorless < 0) {
            int oldColorless = colorless;
            if (red > 0) {
                red--;
                colorless++;
                continue;
            }
            if (green > 0) {
                green--;
                colorless++;
                continue;
            }
            if (blue > 0) {
                blue--;
                colorless++;
                continue;
            }
            if (white > 0) {
                white--;
                colorless++;
                continue;
            }
            if (black > 0) {
                black--;
                colorless++;
            }
            if (any > 0) {
                any--;
                colorless++;
            }
            if (oldColorless == colorless) {
                throw new ArithmeticException("Not enough mana to pay colorless");
            }
        }
    }

    /**
     * Sets this object's mana to be equal to the passed in {@code mana}
     *
     * @param mana the mana to copy from
     */
    public void setToMana(final Mana mana) {
        any = mana.any;
        red = mana.red;
        green = mana.green;
        white = mana.white;
        blue = mana.blue;
        black = mana.black;
        colorless = mana.colorless;
    }

    /**
     * Returns the total mana count.
     *
     * @return the total mana count.
     */
    public int count() {
        return red + green + blue + white + black + colorless + any;
    }

    /**
     * Returns the total colored mana count.
     *
     * @return the total colored mana count.
     */
    public int countColored() {
        return red + green + blue + white + black + any;
    }


    /**
     * Returns how many colors are currently more than 0.
     *
     * @return how many colors are currently more than 0.
     */
    public int getDifferentColors() {
        int count = 0;
        if (blue > 0) {
            count++;
        }
        if (black > 0) {
            count++;
        }
        if (green > 0) {
            count++;
        }
        if (white > 0) {
            count++;
        }
        if (red > 0) {
            count++;
        }
        return count;
    }

    /**
     * Returns the total mana with a {@link FilterMana} applied.
     *
     * @param filter the filter to apply when counting mana
     * @return the total mana after filtration.
     */
    public int count(final FilterMana filter) {
        if (filter == null) {
            return count();
        }
        int count = 0;
        if (filter.isBlack()) {
            count += black;
        }
        if (filter.isBlue()) {
            count += blue;
        }
        if (filter.isWhite()) {
            count += white;
        }
        if (filter.isGreen()) {
            count += green;
        }
        if (filter.isRed()) {
            count += red;
        }
        if (filter.isColorless()) {
            count += colorless;
        }
        return count;
    }

    /**
     * Resets all mana to 0
     */
    public void clear() {
        red = 0;
        green = 0;
        blue = 0;
        white = 0;
        black = 0;
        colorless = 0;
        any = 0;
    }

    /**
     * Returns {@link String} of internal state.
     *
     * @return text version of internal state.
     */
    @Override
    public String toString() {
        StringBuilder sbMana = new StringBuilder();
        if (colorless > 0) {
            sbMana.append("{").append(Integer.toString(colorless)).append("}");
        }
        for (int i = 0; i < red; i++) {
            sbMana.append("{R}");
        }
        for (int i = 0; i < green; i++) {
            sbMana.append("{G}");
        }
        for (int i = 0; i < blue; i++) {
            sbMana.append("{U}");
        }
        for (int i = 0; i < white; i++) {
            sbMana.append("{W}");
        }
        for (int i = 0; i < black; i++) {
            sbMana.append("{B}");
        }
        for (int i = 0; i < any; i++) {
            sbMana.append("{Any}");
        }
        return sbMana.toString();
    }


    /**
     * Returns a deep copy of this object
     *
     * @return a deep copy of this object
     */
    @Override
    public Mana copy() {
        return new Mana(this);
    }

    /**
     * Returns if there is enough mana available compared to the passed in {@link Mana}
     *
     * @param avail value to compare with
     * @return if there is enough mana in the mana pool compared to the passed in {@link Mana}
     */
    public boolean enough(final Mana avail) {
        Mana compare = avail.copy();
        compare.subtract(this);
        if (compare.getRed() < 0) {
            compare.setAny(compare.getAny() + compare.getRed());
            if (compare.getAny() < 0) {
                return false;
            }
            compare.setRed(0);
        }
        if (compare.getGreen() < 0) {
            compare.setAny(compare.getAny() + compare.getGreen());
            if (compare.getAny() < 0) {
                return false;
            }
            compare.setGreen(0);
        }
        if (compare.getBlue() < 0) {
            compare.setAny(compare.getAny() + compare.getBlue());
            if (compare.getAny() < 0) {
                return false;
            }
            compare.setBlue(0);
        }
        if (compare.getBlack() < 0) {
            compare.setAny(compare.getAny() + compare.getBlack());
            if (compare.getAny() < 0) {
                return false;
            }
            compare.setBlack(0);
        }
        if (compare.getWhite() < 0) {
            compare.setAny(compare.getAny() + compare.getWhite());
            if (compare.getAny() < 0) {
                return false;
            }
            compare.setWhite(0);
        }
        if (compare.getColorless() < 0) {
            int remaining = compare.getRed() + compare.getGreen() + compare.getBlack() + compare.getBlue() + compare.getWhite() + compare.getAny();
            if (compare.getColorless() + remaining < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns how much mana is needed to meet the passed in cost
     *
     * @param avail mana cost to meet
     * @return how much mana is needed to meet the passed in cost
     */
    public Mana needed(final Mana avail) {
        Mana compare = avail.copy();
        compare.subtract(this);
        if (compare.getRed() < 0 && compare.getAny() > 0) {
            int diff = Math.min(compare.getAny(), Math.abs(compare.getRed()));
            compare.setAny(compare.getAny() - diff);
            compare.setRed(compare.getRed() + diff);
        }
        if (compare.getGreen() < 0 && compare.getAny() > 0) {
            int diff = Math.min(compare.getAny(), Math.abs(compare.getGreen()));
            compare.setAny(compare.getAny() - diff);
            compare.setGreen(compare.getGreen() + diff);
        }
        if (compare.getBlue() < 0 && compare.getAny() > 0) {
            int diff = Math.min(compare.getAny(), Math.abs(compare.getBlue()));
            compare.setAny(compare.getAny() - diff);
            compare.setBlue(compare.getBlue() + diff);
        }
        if (compare.getBlack() < 0 && compare.getAny() > 0) {
            int diff = Math.min(compare.getAny(), Math.abs(compare.getBlack()));
            compare.setAny(compare.getAny() - diff);
            compare.setBlack(compare.getBlack() + diff);
        }
        if (compare.getWhite() < 0 && compare.getAny() > 0) {
            int diff = Math.min(compare.getAny(), Math.abs(compare.getWhite()));
            compare.setAny(compare.getAny() - diff);
            compare.setWhite(compare.getWhite() + diff);
        }
        if (compare.getColorless() < 0) {
            int remaining = 0;
            remaining += Math.min(0, compare.getRed());
            remaining += Math.min(0, compare.getWhite());
            remaining += Math.min(0, compare.getGreen());
            remaining += Math.min(0, compare.getBlack());
            remaining += Math.min(0, compare.getBlue());
            remaining += Math.min(0, compare.getAny());
            if (remaining > 0) {
                int diff = Math.min(remaining, Math.abs(compare.getColorless()));
                compare.setColorless(compare.getColorless() + diff);
            }
        }
        Mana needed = new Mana();
        if (compare.getRed() < 0) {
            needed.setRed(Math.abs(compare.getRed()));
        }
        if (compare.getWhite() < 0) {
            needed.setWhite(Math.abs(compare.getWhite()));
        }
        if (compare.getGreen() < 0) {
            needed.setGreen(Math.abs(compare.getGreen()));
        }
        if (compare.getBlack() < 0) {
            needed.setBlack(Math.abs(compare.getBlack()));
        }
        if (compare.getBlue() < 0) {
            needed.setBlue(Math.abs(compare.getBlue()));
        }
        if (compare.getColorless() < 0) {
            needed.setColorless(Math.abs(compare.getColorless()));
        }
        return needed;
    }

    public int getRed() {
        return red;
    }

    public void setRed(final int red) {
        this.red = notNegative(red, "Red");
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = notNegative(green, "Green");
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = notNegative(blue, "Blue");
    }

    public int getWhite() {
        return white;
    }

    public void setWhite(int white) {
        this.white = notNegative(white, "White");
    }

    public int getBlack() {
        return black;
    }

    public void setBlack(int black) {
        this.black = notNegative(black, "Black");
    }

    public int getColorless() {
        return colorless;
    }

    public void setColorless(int colorless) {
        this.colorless = notNegative(colorless, "Colorless");
    }

    public int getAny() {
        return any;
    }

    public void setAny(int any) {
        this.any = notNegative(any, "Any");
    }


    @Override
    public int compareTo(final Mana o) {
        return count() - o.count();
    }

    /**
     * @param mana
     * @return true if this contains any values that mana has
     */
    // todo what purpose does this serve?
    // todo what if you want to check for red, and you have black?
    public boolean contains(Mana mana) {
        if (mana.black > 0 && black > 0) {
            return true;
        }
        if (mana.blue > 0 && blue > 0) {
            return true;
        }
        if (mana.red > 0 && red > 0) {
            return true;
        }
        if (mana.white > 0 && white > 0) {
            return true;
        }
        if (mana.green > 0 && green > 0) {
            return true;
        }
        if (mana.colorless > 0 && count() > 0) {
            return true;
        }

        return false;
    }

    /**
     * Returns the total color based on the {@link ColoredManaSymbol} passed in
     *
     * @param color the {@link ColoredManaSymbol} to get mana for
     * @return the total color based on the {@link ColoredManaSymbol} passed in
     */
    public int getColor(final ColoredManaSymbol color) {
        if (color.equals(ColoredManaSymbol.G)) {
            return getGreen();
        }
        if (color.equals(ColoredManaSymbol.R)) {
            return getRed();
        }
        if (color.equals(ColoredManaSymbol.B)) {
            return getBlack();
        }
        if (color.equals(ColoredManaSymbol.U)) {
            return getBlue();
        }
        if (color.equals(ColoredManaSymbol.W)) {
            return getWhite();
        }
        return 0;
    }

    /**
     * Returns the total color based on the {@link ManaType} passed in
     *
     * @param manaType the {@link ManaType} to return the color for
     * @return the total color based on the {@link ManaType} passed in
     */
    public int get(final ManaType manaType) {
        switch (manaType) {
            case BLACK:
                return black;
            case BLUE:
                return blue;
            case GREEN:
                return green;
            case RED:
                return red;
            case WHITE:
                return white;
            case COLORLESS:
                return colorless;
        }
        return 0;
    }

    /**
     * Sets the total mana based on the passed int {@code manaType} and {@code amount}
     *
     * @param manaType the type of mana
     * @param amount   the amount to set the mana to, can not be negative
     */
    public void set(final ManaType manaType, final int amount) {
        notNegative(amount, manaType.toString());
        switch (manaType) {
            case BLACK:
                black = amount;
                break;
            case BLUE:
                blue = amount;
                break;
            case GREEN:
                green = amount;
                break;
            case RED:
                red = amount;
                break;
            case WHITE:
                white = amount;
                break;
            case COLORLESS:
                colorless = amount;
                break;
        }
    }

    //todo not sure what this does, should we add some documentation of what a flag is?
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean getFlag() {
        return flag;
    }


    /**
     * Checks if this object has the same mana values as the passed in {@link Mana}
     *
     * @param mana the {@link Mana} to compare to
     * @return if both {@link Mana} objects have the same mana values
     */
    public boolean equalManaValue(final Mana mana) {
        return any == mana.any
                && red == mana.red
                && green == mana.green
                && white == mana.white
                && blue == mana.blue
                && black == mana.black
                && colorless == mana.colorless;
    }

    /**
     * Don't takes any mana into account to be usable in calculating available
     * mana
     *
     * @param mana
     * @return
     */
    public boolean includesMana(Mana mana) {
        return green >= mana.green
                && blue >= mana.blue
                && white >= mana.white
                && black >= mana.black
                && red >= mana.red
                && (colorless >= mana.colorless
                || countColored() >= mana.countColored() + mana.colorless);
    }

    /**
     * Returns the mana that is more colored or has a greater amount but does
     * not contain one less mana in any color but colorless if you call with
     * {1}{W}{R} and {G}{W}{R} you get back {G}{W}{R} if you call with {G}{W}{R}
     * and {G}{W}{R} you get back {G}{W}{R} if you call with {G}{W}{B} and
     * {G}{W}{R} you get back null
     *
     * @param mana1
     * @param mana2
     * @return
     */
    public static Mana getMoreValuableMana(Mana mana1, Mana mana2) {
        Mana moreMana;
        Mana lessMana;
        if (mana2.countColored() > mana1.countColored() || mana2.getAny() > mana1.getAny() || mana2.count() > mana1.count()) {
            moreMana = mana2;
            lessMana = mana1;
        } else {
            moreMana = mana1;
            lessMana = mana2;
        }
        if (lessMana.getWhite() > moreMana.getWhite()
                || lessMana.getRed() > moreMana.getRed()
                || lessMana.getGreen() > moreMana.getGreen()
                || lessMana.getBlue() > moreMana.getBlue()
                || lessMana.getBlack() > moreMana.getBlack()
                || lessMana.getAny() > moreMana.getAny()) {
            return null;
        }
        return moreMana;
    }


    /**
     * Used to check if a passed in value is less than 0. Log if the value is.
     *
     * @param value     The value to check
     * @param valueName The name of the value
     * @return 0 if less than 0, or the value if more than 0
     */
    private static int notNegative(int value, final String valueName) {
        if (value < 0) {
            logger.info(valueName + " can not be set to less than 0. Setting to 0");
            value = 0;
        }
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mana mana = (Mana) o;

        if (red != mana.red) return false;
        if (green != mana.green) return false;
        if (blue != mana.blue) return false;
        if (white != mana.white) return false;
        if (black != mana.black) return false;
        if (colorless != mana.colorless) return false;
        if (any != mana.any) return false;
        return flag == mana.flag;
    }

    @Override
    public int hashCode() {
        int result = red;
        result = 31 * result + green;
        result = 31 * result + blue;
        result = 31 * result + white;
        result = 31 * result + black;
        result = 31 * result + colorless;
        result = 31 * result + any;
        result = 31 * result + (flag ? 1 : 0);
        return result;
    }
}
