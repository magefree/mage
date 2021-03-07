package mage;

import mage.constants.ColoredManaSymbol;
import mage.constants.ManaType;
import mage.filter.FilterMana;
import mage.util.CardUtil;
import mage.util.Copyable;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Objects;

/**
 * WARNING, all mana operations must use overflow check, see usage of CardUtil.addWithOverflowCheck and same methods
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Mana implements Comparable<Mana>, Serializable, Copyable<Mana> {

    protected static final class ManaColor implements Serializable {
        private int amount = 0;
        private int snowAmount = 0;

        private ManaColor() {
        }

        protected ManaColor(final ManaColor manaColor) {
            this.copyFrom(manaColor);
        }

        private ManaColor(int amount) {
            this.amount = amount;
        }

        protected int getAmount() {
            return amount;
        }

        protected int getSnowAmount() {
            return snowAmount;
        }

        protected void incrementAmount() {
            incrementAmount(1, false);
        }

        protected void incrementAmount(ManaColor manaColor) {
            this.amount = CardUtil.overflowInc(this.amount, manaColor.amount);
            this.snowAmount = CardUtil.overflowInc(this.snowAmount, manaColor.snowAmount);
        }

        protected void incrementAmount(int amount, boolean snow) {
            this.amount = CardUtil.overflowInc(this.amount, amount);
            if (snow) {
                this.snowAmount = CardUtil.overflowInc(this.snowAmount, amount);
            }
        }

        protected void removeAmount(ManaColor manaColor) {
            this.amount = CardUtil.overflowDec(this.amount, manaColor.amount);
            this.snowAmount = CardUtil.overflowDec(this.snowAmount, manaColor.snowAmount);
        }

        protected void clear() {
            amount = 0;
            snowAmount = 0;
        }

        protected boolean removeOne(ManaColor manaColor) {
            if (manaColor.getAmount() < 1) {
                return false;
            }
            if (manaColor.getSnowAmount() > 0) {
                manaColor.snowAmount = CardUtil.overflowDec(manaColor.snowAmount, 1);
                this.snowAmount = CardUtil.overflowInc(this.snowAmount, 1);
            }
            manaColor.amount = CardUtil.overflowDec(manaColor.amount, 1);
            this.amount = CardUtil.overflowInc(this.amount, 1);
            return true;
        }

        protected ManaColor copy() {
            return new ManaColor(this);
        }

        protected void copyFrom(final ManaColor manaColor) {
            this.amount = manaColor.amount;
            this.snowAmount = manaColor.snowAmount;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ManaColor manaColor = (ManaColor) o;
            return amount == manaColor.amount && snowAmount == manaColor.snowAmount;
        }

        @Override
        public int hashCode() {
            return Objects.hash(amount, snowAmount);
        }

        @Override
        public String toString() {
            if (amount != 0 || snowAmount != 0) {
                return amount + "/" + snowAmount;
            } else {
                return "";
            }
        }
    }

    private static final transient Logger logger = Logger.getLogger(Mana.class);

    protected ManaColor white;
    protected ManaColor blue;
    protected ManaColor black;
    protected ManaColor red;
    protected ManaColor green;
    protected ManaColor generic;
    protected ManaColor colorless;
    protected ManaColor any;
    protected boolean flag;

    /**
     * Default constructor. Creates a {@link Mana} object with 0 values.
     */
    public Mana() {
        white = new ManaColor();
        blue = new ManaColor();
        black = new ManaColor();
        red = new ManaColor();
        green = new ManaColor();
        generic = new ManaColor();
        colorless = new ManaColor();
        any = new ManaColor();
        flag = false;
    }

    /**
     * Creates a {@link Mana} object with the passed in values. Values can not
     * be less than 0. Any values less than 0 will be logged and set to 0.
     *
     * @param white     total White mana to have.
     * @param blue      total Blue mana to have.
     * @param black     total Black mana to have.
     * @param red       total Red mana to have.
     * @param green     total Green mana to have.
     * @param generic   total Generic mana to have.
     * @param any       total Any mana to have.
     * @param colorless total Colorless mana to have.
     */
    public Mana(final int white, final int blue, final int black, final int red, final int green, final int generic, final int any, final int colorless) {
        this.white = new ManaColor(notNegative(white, "White"));
        this.blue = new ManaColor(notNegative(blue, "Blue"));
        this.black = new ManaColor(notNegative(black, "Black"));
        this.red = new ManaColor(notNegative(red, "Red"));
        this.green = new ManaColor(notNegative(green, "Green"));
        this.generic = new ManaColor(notNegative(generic, "Generic"));
        this.colorless = new ManaColor(notNegative(colorless, "Colorless"));
        this.any = new ManaColor(notNegative(any, "Any"));
        this.flag = false;
    }

    /**
     * Copy constructor. Creates a {@link Mana} object from existing
     * {@link Mana}
     *
     * @param mana object to create copy from
     */
    public Mana(final Mana mana) {
        Objects.requireNonNull(mana, "The passed in mana can not be null");
        this.white = mana.white.copy();
        this.blue = mana.blue.copy();
        this.black = mana.black.copy();
        this.red = mana.red.copy();
        this.green = mana.green.copy();
        this.generic = mana.generic.copy();
        this.colorless = mana.colorless.copy();
        this.any = mana.any.copy();
        this.flag = mana.flag;
    }

    /**
     * Creates {@link Mana} object from {@link ColoredManaSymbol}. Created
     * {@link Mana} will have a single mana of the passed in
     * {@link ColoredManaSymbol} color.
     *
     * @param color The color to create the {@link Mana} object with.
     */
    public Mana(final ColoredManaSymbol color) {
        this();
        Objects.requireNonNull(color, "The passed in ColoredManaSymbol can not be null");
        switch (color) {
            case W:
                white.incrementAmount();
                break;
            case U:
                blue.incrementAmount();
                break;
            case B:
                black.incrementAmount();
                break;
            case R:
                red.incrementAmount();
                break;
            case G:
                green.incrementAmount();
                break;
            default:
                throw new IllegalArgumentException("Unknown mana color: " + color);
        }
    }

    public Mana(final ManaType manaType) {
        this();
        Objects.requireNonNull(manaType, "The passed in ManaType can not be null");
        switch (manaType) {
            case WHITE:
                white.incrementAmount();
                break;
            case BLUE:
                blue.incrementAmount();
                break;
            case BLACK:
                black.incrementAmount();
                break;
            case RED:
                red.incrementAmount();
                break;
            case GREEN:
                green.incrementAmount();
                break;
            case COLORLESS:
                colorless.incrementAmount();
                break;
            case GENERIC:
                generic.incrementAmount();
                break;
            default:
                throw new IllegalArgumentException("Unknown manaType: " + manaType);
        }
    }

    public Mana(final ManaType manaType, int num) {
        this();
        Objects.requireNonNull(manaType, "The passed in ManaType can not be null");
        switch (manaType) {
            case WHITE:
                white.incrementAmount(num, false);
                break;
            case BLUE:
                blue.incrementAmount(num, false);
                break;
            case BLACK:
                black.incrementAmount(num, false);
                break;
            case RED:
                red.incrementAmount(num, false);
                break;
            case GREEN:
                green.incrementAmount(num, false);
                break;
            case COLORLESS:
                colorless.incrementAmount(num, false);
                break;
            case GENERIC:
                generic.incrementAmount(num, false);
                break;
            default:
                throw new IllegalArgumentException("Unknown manaType: " + manaType);
        }
    }

    /**
     * Creates a {@link Mana} object with the passed in {@code num} of White
     * mana. {@code num} can not be a negative value. Negative values will be
     * logged and set to 0.
     *
     * @param num value of White mana to create.
     * @return a {@link Mana} object with the passed in {@code num} of White
     * mana.
     */
    public static Mana WhiteMana(int num) {
        return new Mana(notNegative(num, "White"), 0, 0, 0, 0, 0, 0, 0);
    }

    /**
     * Creates a {@link Mana} object with the passed in {@code num} of Blue
     * mana. {@code num} can not be a negative value. Negative values will be
     * logged and set to 0.
     *
     * @param num value of Blue mana to create.
     * @return a {@link Mana} object with the passed in {@code num} of Blue
     * mana.
     */
    public static Mana BlueMana(int num) {
        return new Mana(0, notNegative(num, "Blue"), 0, 0, 0, 0, 0, 0);
    }

    /**
     * Creates a {@link Mana} object with the passed in {@code num} of Black
     * mana. {@code num} can not be a negative value. Negative values will be
     * logged and set to 0.
     *
     * @param num value of Black mana to create.
     * @return a {@link Mana} object with the passed in {@code num} of Black
     * mana.
     */
    public static Mana BlackMana(int num) {
        return new Mana(0, 0, notNegative(num, "Black"), 0, 0, 0, 0, 0);
    }

    /**
     * Creates a {@link Mana} object with the passed in {@code num} of Red mana.
     * {@code num} can not be a negative value. Negative values will be logged
     * and set to 0.
     *
     * @param num value of Red mana to create.
     * @return a {@link Mana} object with the passed in {@code num} of Red mana.
     */
    public static Mana RedMana(int num) {
        return new Mana(0, 0, 0, notNegative(num, "Red"), 0, 0, 0, 0);
    }

    /**
     * Creates a {@link Mana} object with the passed in {@code num} of Green
     * mana. {@code num} can not be a negative value. Negative values will be
     * logged and set to 0.
     *
     * @param num value of Green mana to create.
     * @return a {@link Mana} object with the passed in {@code num} of Green
     * mana.
     */
    public static Mana GreenMana(int num) {
        return new Mana(0, 0, 0, 0, notNegative(num, "Green"), 0, 0, 0);
    }

    /**
     * Creates a {@link Mana} object with the passed in {@code num} of Generic
     * mana. {@code num} can not be a negative value. Negative values will be
     * logged and set to 0.
     *
     * @param num value of Generic mana to create.
     * @return a {@link Mana} object with the passed in {@code num} of Generic
     * mana.
     */
    public static Mana GenericMana(int num) {
        return new Mana(0, 0, 0, 0, 0, notNegative(num, "Generic"), 0, 0);
    }

    /**
     * Creates a {@link Mana} object with the passed in {@code num} of Colorless
     * mana. {@code num} can not be a negative value. Negative values will be
     * logged and set to 0.
     *
     * @param num value of Colorless mana to create.
     * @return a {@link Mana} object with the passed in {@code num} of Colorless
     * mana.
     */
    public static Mana ColorlessMana(int num) {
        return new Mana(0, 0, 0, 0, 0, 0, 0, notNegative(num, "Colorless"));
    }

    /**
     * Creates a {@link Mana} object with the passed in {@code num} of Any mana.
     * {@code num} can not be a negative value. Negative values will be logged
     * and set to 0.
     *
     * @param num value of Any mana to create.
     * @return a {@link Mana} object with the passed in {@code num} of Any mana.
     */
    public static Mana AnyMana(int num) {
        return new Mana(0, 0, 0, 0, 0, 0, notNegative(num, "Any"), 0);
    }

    /**
     * Adds mana from the passed in {@link Mana} object to this object.
     * Ignores conditions from conditional mana
     *
     * @param mana mana to add to this object.
     */
    public void add(final Mana mana) {
        white.incrementAmount(mana.white);
        blue.incrementAmount(mana.blue);
        black.incrementAmount(mana.black);
        red.incrementAmount(mana.red);
        green.incrementAmount(mana.green);
        generic.incrementAmount(mana.generic);
        colorless.incrementAmount(mana.colorless);
        any.incrementAmount(mana.any);
    }

    /**
     * Increases the given mana by one.
     *
     * @param manaType
     */
    public void increase(ManaType manaType) {
        increase(manaType, false);
    }

    public void increase(ManaType manaType, boolean isSnow) {
        switch (manaType) {
            case WHITE:
                white.incrementAmount(1, isSnow);
                break;
            case BLUE:
                blue.incrementAmount(1, isSnow);
                break;
            case BLACK:
                black.incrementAmount(1, isSnow);
                break;
            case RED:
                red.incrementAmount(1, isSnow);
                break;
            case GREEN:
                green.incrementAmount(1, isSnow);
                break;
            case COLORLESS:
                colorless.incrementAmount(1, isSnow);
                break;
            case GENERIC:
                generic.incrementAmount(1, isSnow);
                break;
        }
    }

    /**
     * Increases the White mana by one.
     */
    public void increaseWhite() {
        increaseWhite(1, false);
    }

    public void increaseWhite(int amount, boolean snow) {
        white.incrementAmount(amount, snow);
    }

    /**
     * Increases the Blue mana by one.
     */
    public void increaseBlue() {
        increaseBlue(1, false);
    }

    public void increaseBlue(int amount, boolean snow) {
        blue.incrementAmount(amount, snow);
    }

    /**
     * Increases the Black mana by one.
     */
    public void increaseBlack() {
        increaseBlack(1, false);
    }

    public void increaseBlack(int amount, boolean snow) {
        black.incrementAmount(amount, snow);
    }

    /**
     * Increases the Red mana by one.
     */
    public void increaseRed() {
        increaseRed(1, false);
    }

    public void increaseRed(int amount, boolean snow) {
        red.incrementAmount(amount, snow);
    }

    /**
     * Increases the Green mana by one.
     */
    public void increaseGreen() {
        increaseGreen(1, false);
    }

    public void increaseGreen(int amount, boolean snow) {
        green.incrementAmount(amount, snow);
    }

    /**
     * Increases the Generic mana by one.
     */
    public void increaseGeneric() {
        increaseGeneric(1, false);
    }

    public void increaseGeneric(int amount, boolean snow) {
        generic.incrementAmount(amount, snow);
    }

    /**
     * Increases the Colorless mana by one.
     */
    public void increaseColorless() {
        increaseColorless(1, false);
    }

    public void increaseColorless(int amount, boolean snow) {
        colorless.incrementAmount(amount, snow);
    }

    /**
     * Subtracts the passed in mana values from this instance.
     *
     * @param mana mana values to subtract
     */
    public void subtract(final Mana mana) {
        white.removeAmount(mana.white);
        blue.removeAmount(mana.blue);
        black.removeAmount(mana.black);
        red.removeAmount(mana.red);
        green.removeAmount(mana.green);
        generic.removeAmount(mana.generic);
        colorless.removeAmount(mana.colorless);
        any.removeAmount(mana.any);
    }

    /**
     * Subtracts the passed in mana values from this instance. The difference
     * between this and {@code subtract()} is that if we do not have the
     * available generic mana to pay, we take mana from our colored mana pools.
     *
     * @param mana mana values to subtract
     * @throws ArithmeticException thrown if there is not enough available
     *                             colored mana to pay the generic cost
     */
    public void subtractCost(final Mana mana) throws ArithmeticException {
        white.removeAmount(mana.white);
        blue.removeAmount(mana.blue);
        black.removeAmount(mana.black);
        red.removeAmount(mana.red);
        green.removeAmount(mana.green);
        generic.removeAmount(mana.generic);
        colorless.removeAmount(mana.colorless);
        any.removeAmount(mana.any);

        while (generic.getAmount() < 0) {
            ManaColor oldColorless = generic.copy();
            if (generic.removeOne(white)) {
                continue;
            }
            if (generic.removeOne(blue)) {
                continue;
            }
            if (generic.removeOne(black)) {
                continue;
            }
            if (generic.removeOne(red)) {
                continue;
            }
            if (generic.removeOne(green)) {
                continue;
            }
            if (generic.removeOne(colorless)) {
                continue;
            }
            if (generic.removeOne(any)) {
                continue;
            }
            if (oldColorless.getAmount() == generic.getAmount()) {
                throw new ArithmeticException("Not enough mana to pay colorless");
            }
        }
    }

    /**
     * Returns the total count of all combined mana.
     *
     * @return the total count of all combined mana.
     */
    public int count() {
        return white.getAmount()
                + blue.getAmount()
                + black.getAmount()
                + red.getAmount()
                + green.getAmount()
                + generic.getAmount()
                + colorless.getAmount()
                + any.getAmount();
    }

    /**
     * Returns the total count of all colored mana.
     *
     * @return the total count of all colored mana.
     */
    public int countColored() {
        return white.getAmount()
                + blue.getAmount()
                + black.getAmount()
                + red.getAmount()
                + green.getAmount()
                + any.getAmount();
    }

    /**
     * Returns the count of filtered mana provided by the passed in
     * {@link FilterMana}. If {@link FilterMana} is null, the total mana count
     * is returned via {@link #count() count}.
     *
     * @param filter the colors of mana to return the count for.
     * @return the count of filtered mana provided by the passed in
     * {@link FilterMana}.
     */
    public int count(final FilterMana filter) {
        if (filter == null) {
            return count();
        }
        int count = 0;
        if (filter.isWhite()) {
            count = CardUtil.overflowInc(count, white.getAmount());
        }
        if (filter.isBlue()) {
            count = CardUtil.overflowInc(count, blue.getAmount());
        }
        if (filter.isBlack()) {
            count = CardUtil.overflowInc(count, black.getAmount());
        }
        if (filter.isRed()) {
            count = CardUtil.overflowInc(count, red.getAmount());
        }
        if (filter.isGreen()) {
            count = CardUtil.overflowInc(count, green.getAmount());
        }
        if (filter.isGeneric()) {
            count = CardUtil.overflowInc(count, generic.getAmount());
        }
        if (filter.isColorless()) {
            count = CardUtil.overflowInc(count, colorless.getAmount());
        }
        return count;
    }

    /**
     * Sets all mana to 0.
     */
    public void clear() {
        white.clear();
        blue.clear();
        black.clear();
        red.clear();
        green.clear();
        generic.clear();
        colorless.clear();
        any.clear();
    }

    /**
     * Returns this objects values as a {@link String}.
     *
     * @return this objects values as a {@link String}.
     */
    @Override
    public String toString() {
        StringBuilder sbMana = new StringBuilder();
        if (generic.getAmount() > 0) {
            sbMana.append('{').append(generic.getAmount()).append('}');
        }

        // too many mana - replace by single icon
        if (colorless.getAmount() >= 20) {
            sbMana.append(colorless.getAmount()).append("{C}");
        }
        if (white.getAmount() >= 20) {
            sbMana.append(white.getAmount()).append("{W}");
        }
        if (blue.getAmount() >= 20) {
            sbMana.append(blue.getAmount()).append("{U}");
        }
        if (black.getAmount() >= 20) {
            sbMana.append(black.getAmount()).append("{B}");
        }
        if (red.getAmount() >= 20) {
            sbMana.append(red.getAmount()).append("{R}");
        }
        if (green.getAmount() >= 20) {
            sbMana.append(green.getAmount()).append("{G}");
        }
        if (any.getAmount() >= 20) {
            sbMana.append(any.getAmount()).append("{Any}");
        }

        // normal mana
        for (int i = 0; i < colorless.getAmount() && colorless.getAmount() < 20; i++) {
            sbMana.append("{C}");
        }
        for (int i = 0; i < white.getAmount() && white.getAmount() < 20; i++) {
            sbMana.append("{W}");
        }
        for (int i = 0; i < blue.getAmount() && blue.getAmount() < 20; i++) {
            sbMana.append("{U}");
        }
        for (int i = 0; i < black.getAmount() && black.getAmount() < 20; i++) {
            sbMana.append("{B}");
        }
        for (int i = 0; i < red.getAmount() && red.getAmount() < 20; i++) {
            sbMana.append("{R}");
        }
        for (int i = 0; i < green.getAmount() && green.getAmount() < 20; i++) {
            sbMana.append("{G}");
        }
        for (int i = 0; i < any.getAmount() && any.getAmount() < 20; i++) {
            sbMana.append("{Any}");
        }

        return sbMana.toString();
    }

    /**
     * Returns a deep copy of this object.
     *
     * @return a deep copy of this object.
     */
    @Override
    public Mana copy() {
        return new Mana(this);
    }

    /**
     * Returns if there is enough available mana to pay the mana provided by the
     * passed in {@link Mana} object.
     *
     * @param cost the cost to compare too.
     * @return if there is enough available mana to pay.
     */
    public boolean enough(final Mana cost) {
        Mana compare = cost.copy();
        compare.subtract(this);
        if (compare.white.getAmount() < 0) {
            compare.any.incrementAmount(compare.white);
            if (compare.any.getAmount() < 0) {
                return false;
            }
            compare.white.clear();
        }
        if (compare.blue.getAmount() < 0) {
            compare.any.incrementAmount(compare.blue);
            if (compare.any.getAmount() < 0) {
                return false;
            }
            compare.blue.clear();
        }
        if (compare.black.getAmount() < 0) {
            compare.any.incrementAmount(compare.black);
            if (compare.any.getAmount() < 0) {
                return false;
            }
            compare.black.clear();
        }
        if (compare.red.getAmount() < 0) {
            compare.any.incrementAmount(compare.red);
            if (compare.any.getAmount() < 0) {
                return false;
            }
            compare.red.clear();
        }
        if (compare.green.getAmount() < 0) {
            compare.any.incrementAmount(compare.green);
            if (compare.any.getAmount() < 0) {
                return false;
            }
            compare.green.clear();
        }
        if (compare.colorless.getAmount() < 0) {
            compare.any.incrementAmount(compare.colorless);
            if (compare.any.getAmount() < 0) {
                return false;
            }
            compare.colorless.clear();
        }
        if (compare.generic.getAmount() < 0) {
            compare.generic.incrementAmount(compare.white);
            compare.generic.incrementAmount(compare.blue);
            compare.generic.incrementAmount(compare.black);
            compare.generic.incrementAmount(compare.red);
            compare.generic.incrementAmount(compare.green);
            compare.generic.incrementAmount(compare.colorless);
            compare.generic.incrementAmount(compare.any);
            return compare.generic.getAmount() >= 0;
        }
        return true;
    }

    /**
     * Returns the total mana needed to meet the passed in {@link Mana} object.
     *
     * @param cost the mana cost
     * @return the total mana needed to meet the passes in {@link Mana} object.
     */
    public Mana needed(final Mana cost) {
        Mana compare = cost.copy();
        compare.subtract(this);
        if (compare.white.getAmount() < 0 && compare.any.getAmount() > 0) {
            int diff = Math.min(compare.any.getAmount(), Math.abs(compare.white.getAmount()));
            compare.any.incrementAmount(-diff, false);
            compare.white.incrementAmount(diff, false);
        }
        if (compare.blue.getAmount() < 0 && compare.any.getAmount() > 0) {
            int diff = Math.min(compare.any.getAmount(), Math.abs(compare.blue.getAmount()));
            compare.any.incrementAmount(-diff, false);
            compare.blue.incrementAmount(diff, false);
        }
        if (compare.black.getAmount() < 0 && compare.any.getAmount() > 0) {
            int diff = Math.min(compare.any.getAmount(), Math.abs(compare.black.getAmount()));
            compare.any.incrementAmount(-diff, false);
            compare.black.incrementAmount(diff, false);
        }
        if (compare.red.getAmount() < 0 && compare.any.getAmount() > 0) {
            int diff = Math.min(compare.any.getAmount(), Math.abs(compare.red.getAmount()));
            compare.any.incrementAmount(-diff, false);
            compare.red.incrementAmount(diff, false);
        }
        if (compare.green.getAmount() < 0 && compare.any.getAmount() > 0) {
            int diff = Math.min(compare.any.getAmount(), Math.abs(compare.green.getAmount()));
            compare.any.incrementAmount(-diff, false);
            compare.green.incrementAmount(diff, false);
        }
        if (compare.colorless.getAmount() < 0 && compare.any.getAmount() > 0) {
            int diff = Math.min(compare.any.getAmount(), Math.abs(compare.colorless.getAmount()));
            compare.any.incrementAmount(-diff, false);
            compare.colorless.incrementAmount(diff, false);
        }
        if (compare.generic.getAmount() < 0) {
            int remaining = 0;
            remaining = CardUtil.overflowInc(remaining, Math.min(0, compare.white.getAmount()));
            remaining = CardUtil.overflowInc(remaining, Math.min(0, compare.blue.getAmount()));
            remaining = CardUtil.overflowInc(remaining, Math.min(0, compare.black.getAmount()));
            remaining = CardUtil.overflowInc(remaining, Math.min(0, compare.red.getAmount()));
            remaining = CardUtil.overflowInc(remaining, Math.min(0, compare.green.getAmount()));
            remaining = CardUtil.overflowInc(remaining, Math.min(0, compare.colorless.getAmount()));
            remaining = CardUtil.overflowInc(remaining, Math.min(0, compare.any.getAmount()));
            if (remaining > 0) {
                int diff = Math.min(remaining, Math.abs(compare.generic.getAmount()));
                compare.generic.incrementAmount(diff, false);
            }
        }
        Mana needed = new Mana();
        if (compare.white.getAmount() < 0) {
            needed.white.removeAmount(compare.white);
        }
        if (compare.blue.getAmount() < 0) {
            needed.blue.removeAmount(compare.blue);
        }
        if (compare.black.getAmount() < 0) {
            needed.black.removeAmount(compare.black);
        }
        if (compare.red.getAmount() < 0) {
            needed.red.removeAmount(compare.red);
        }
        if (compare.green.getAmount() < 0) {
            needed.green.removeAmount(compare.green);
        }
        if (compare.colorless.getAmount() < 0) {
            needed.colorless.removeAmount(compare.colorless);
        }
        if (compare.generic.getAmount() < 0) {
            needed.generic.removeAmount(compare.generic);
        }
        return needed;
    }

    /**
     * Returns total White mana.
     *
     * @return total White mana.
     */
    public int getWhite() {
        return white.getAmount();
    }

    /**
     * Sets the total White mana. Can not be negative. Negative values will be
     * logged and set to 0.
     *
     * @param white total White mana.
     */
    public void setWhite(int white) {
        this.white.clear();
        this.white.incrementAmount(notNegative(white, "White"), false);
    }

    /**
     * Returns total Blue mana.
     *
     * @return total Blue mana.
     */
    public int getBlue() {
        return blue.getAmount();
    }

    /**
     * Sets the total Blue mana. Can not be negative. Negative values will be
     * logged and set to 0.
     *
     * @param blue total Blue mana.
     */
    public void setBlue(int blue) {
        this.blue.clear();
        this.blue.incrementAmount(notNegative(blue, "Blue"), false);
    }

    /**
     * Returns total Black mana.
     *
     * @return total Black mana.
     */
    public int getBlack() {
        return black.getAmount();
    }

    /**
     * Sets the total Black mana. Can not be negative. Negative values will be
     * logged and set to 0.
     *
     * @param black total Black mana.
     */
    public void setBlack(int black) {
        this.black.clear();
        this.black.incrementAmount(notNegative(black, "Black"), false);
    }

    /**
     * Returns total Red mana.
     *
     * @return total Red mana.
     */
    public int getRed() {
        return red.getAmount();
    }

    /**
     * Sets the total Red mana. Can not be negative. Negative values will be
     * logged and set to 0.
     *
     * @param red total Red mana.
     */
    public void setRed(int red) {
        this.red.clear();
        this.red.incrementAmount(notNegative(red, "Red"), false);
    }

    /**
     * Returns total Green mana.
     *
     * @return total Green mana.
     */
    public int getGreen() {
        return green.getAmount();
    }

    /**
     * Sets the total Green mana. Can not be negative. Negative values will be
     * logged and set to 0.
     *
     * @param green total Green mana.
     */
    public void setGreen(int green) {
        this.green.clear();
        this.green.incrementAmount(notNegative(green, "Green"), false);
    }

    /**
     * Returns total Generic mana.
     *
     * @return total Generic mana.
     */
    public int getGeneric() {
        return generic.getAmount();
    }

    /**
     * Sets the total Generic mana. Can not be negative. Negative values will be
     * logged and set to 0.
     *
     * @param generic total Generic mana.
     */
    public void setGeneric(int generic) {
        this.generic.clear();
        this.generic.incrementAmount(notNegative(generic, "Generic"), false);
    }

    /**
     * Returns total Colorless mana.
     *
     * @return total Colorless mana.
     */
    public int getColorless() {
        return colorless.getAmount();
    }

    /**
     * Sets the total Colorless mana. Can not be negative. Negative values will
     * be logged and set to 0.
     *
     * @param colorless total Colorless mana.
     */
    public void setColorless(int colorless) {
        this.colorless.clear();
        this.colorless.incrementAmount(notNegative(colorless, "Colorless"), false);
    }

    /**
     * Returns total Any mana.
     *
     * @return total Any mana.
     */
    public int getAny() {
        return any.getAmount();
    }

    /**
     * Sets the total Any mana. Can not be negative. Negative values will be
     * logged and set to 0.
     *
     * @param any total Any mana.
     */
    public void setAny(int any) {
        this.any.clear();
        this.any.incrementAmount(notNegative(any, "Any"), false);
    }

    public int getSnow() {
        return white.getSnowAmount()
                + blue.getSnowAmount()
                + black.getSnowAmount()
                + red.getSnowAmount()
                + green.getSnowAmount()
                + colorless.getSnowAmount()
                + generic.getSnowAmount()
                + any.getSnowAmount();
    }

    public boolean checkSnow(ObjectColor color) {
        if (color.isWhite() && white.getSnowAmount() > 0) {
            return true;
        } else if (color.isBlue() && blue.getSnowAmount() > 0) {
            return true;
        } else if (color.isBlack() && black.getSnowAmount() > 0) {
            return true;
        } else if (color.isRed() && red.getSnowAmount() > 0) {
            return true;
        } else return color.isGreen() && green.getSnowAmount() > 0;
    }

    /**
     * Returns this objects total mana minus the passed in {@link Mana}'s mana.
     *
     * @param o the object to compare to.
     * @return this objects total mana minus the passed in {@link Mana}'s mana.
     */
    @Override
    public int compareTo(final Mana o) {
        return Integer.compare(this.count(), o.count());
    }

    /**
     * Returns if this objects mana contains any number of the passed in
     * {@link Mana}'s mana.
     *
     * @param mana the mana to check for
     * @return true if this contains any values that mana has
     */
    public boolean contains(final Mana mana) {
        if (mana.white.getAmount() > 0 && this.white.getAmount() > 0) {
            return true;
        }
        if (mana.blue.getAmount() > 0 && this.blue.getAmount() > 0) {
            return true;
        }
        if (mana.black.getAmount() > 0 && this.black.getAmount() > 0) {
            return true;
        }
        if (mana.red.getAmount() > 0 && this.red.getAmount() > 0) {
            return true;
        }
        if (mana.green.getAmount() > 0 && this.green.getAmount() > 0) {
            return true;
        }
        if (mana.colorless.getAmount() > 0 && this.colorless.getAmount() > 0) {
            return true;
        }
        return mana.generic.getAmount() > 0 && this.count() > 0;
    }

    public boolean containsAny(final Mana mana) {
        return containsAny(mana, false);
    }

    /**
     * Returns if this objects mana contains any coloured mana the same as the
     * passed in {@link Mana}'s mana.
     *
     * @param mana             the mana to check for
     * @param includeColorless also check for colorless
     * @return true if this contains any of the same type of coloured mana that
     * this has
     */
    public boolean containsAny(final Mana mana, boolean includeColorless) {
        if (mana.white.getAmount() > 0 && this.white.getAmount() > 0) {
            return true;
        } else if (mana.blue.getAmount() > 0 && this.blue.getAmount() > 0) {
            return true;
        } else if (mana.black.getAmount() > 0 && this.black.getAmount() > 0) {
            return true;
        } else if (mana.red.getAmount() > 0 && this.red.getAmount() > 0) {
            return true;
        } else if (mana.green.getAmount() > 0 && this.green.getAmount() > 0) {
            return true;
        } else if (mana.colorless.getAmount() > 0 && this.colorless.getAmount() > 0 && includeColorless) {
            return true;
        } else return mana.any.getAmount() > 0 && this.count() > 0;
    }

    /**
     * Returns the total count of mana in this object as specified by the passed
     * in {@link ColoredManaSymbol}.
     *
     * @param color the color to return the count for.
     * @return the total count of mana in this object as specified by the passed
     * in {@link ColoredManaSymbol}.
     */
    public int getColor(final ColoredManaSymbol color) {
        if (color == ColoredManaSymbol.W) {
            return white.getAmount();
        }
        if (color == ColoredManaSymbol.U) {
            return blue.getAmount();
        }
        if (color == ColoredManaSymbol.B) {
            return black.getAmount();
        }
        if (color == ColoredManaSymbol.R) {
            return red.getAmount();
        }
        if (color == ColoredManaSymbol.G) {
            return green.getAmount();
        }
        return 0;
    }

    /**
     * Returns the total count of mana in this object as specified by the passed
     * in {@link ManaType}.
     *
     * @param manaType the type to return the count for.
     * @return the total count of mana in this object as specified by the passed
     * in {@link ManaType}.
     */
    public int get(final ManaType manaType) {
        switch (manaType) {
            case WHITE:
                return white.getAmount();
            case BLUE:
                return blue.getAmount();
            case BLACK:
                return black.getAmount();
            case RED:
                return red.getAmount();
            case GREEN:
                return green.getAmount();
            case COLORLESS:
                return CardUtil.overflowInc(generic.getAmount(), colorless.getAmount());
        }
        return 0;
    }

    /**
     * Set the color of mana specified by the passed in {@link ManaType} to
     * {@code amount} .
     * <p>
     * WARNING, you must check amount for overflow values, see CardUtil.multiplyWithOverflowCheck
     * and other CardUtil.xxxWithOverflowCheck math methods
     *
     * @param manaType the color of the mana to set
     * @param amount   the value to set the mana too
     */
    public void set(final ManaType manaType, final int amount) {
        switch (manaType) {
            case WHITE:
                setWhite(notNegative(amount, "white"));
                break;
            case BLUE:
                setBlue(notNegative(amount, "blue"));
                break;
            case BLACK:
                setBlack(notNegative(amount, "black"));
                break;
            case RED:
                setRed(notNegative(amount, "red"));
                break;
            case GREEN:
                setGreen(notNegative(amount, "green"));
                break;
            case COLORLESS:
                setColorless(notNegative(amount, "colorless"));
                break;
            default:
                throw new IllegalArgumentException("Unknown color: " + manaType);
        }
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean getFlag() {
        return flag;
    }

    /**
     * Sets this objects mana to that of the passed in {@link Mana}
     *
     * @param mana the mana to set this object to.
     */
    public void setToMana(final Mana mana) {
        this.any.copyFrom(mana.any);
        this.white.copyFrom(mana.white);
        this.blue.copyFrom(mana.blue.copy());
        this.black.copyFrom(mana.black.copy());
        this.red.copyFrom(mana.red.copy());
        this.green.copyFrom(mana.green.copy());
        this.colorless.copyFrom(mana.colorless.copy());
        this.generic.copyFrom(mana.generic.copy());
        //this.flag = mana.flag;
    }

    /**
     * Returns if the passed in {@link Mana} values are equal to this objects.
     *
     * @param mana the {@link Mana} to compare to.
     * @return if the passed in {@link Mana} values are equal to this object.
     */
    public boolean equalManaValue(final Mana mana) {
        return this.any.equals(mana.any)
                && this.white.equals(mana.white)
                && this.blue.equals(mana.blue)
                && this.black.equals(mana.black)
                && this.red.equals(mana.red)
                && this.green.equals(mana.green)
                && this.colorless.equals(mana.colorless)
                && this.generic.equals(mana.generic);
    }

    /**
     * Returns if this {@link Mana} object has more than or equal values of mana
     * as the passed in {@link Mana} object. Ignores {Any} mana to prevent
     * endless iterations.
     *
     * @param mana the mana to compare with
     * @return if this object has more than or equal mana to the passed in
     * {@link Mana}.
     */
    public boolean includesMana(Mana mana) {
        return this.white.getAmount() >= mana.white.getAmount()
                && this.blue.getAmount() >= mana.blue.getAmount()
                && this.black.getAmount() >= mana.black.getAmount()
                && this.red.getAmount() >= mana.red.getAmount()
                && this.green.getAmount() >= mana.green.getAmount()
                && this.colorless.getAmount() >= mana.colorless.getAmount()
                && (this.generic.getAmount() >= mana.generic.getAmount()
                || CardUtil.overflowInc(this.countColored(), this.colorless.getAmount()) >= mana.count());

    }

    /**
     * Returns the mana that is more colored or has a greater amount but does
     * not contain one less mana in any color but generic if you call with
     * {1}{W}{R} and {G}{W}{R} you get back {G}{W}{R} if you call with {G}{W}{R}
     * and {G}{W}{R} you get back {G}{W}{R} if you call with {G}{W}{B} and
     * {G}{W}{R} you get back null
     *
     * @param mana1
     * @param mana2
     * @return
     */
    public static Mana getMoreValuableMana(final Mana mana1, final Mana mana2) {
        String conditionString1 = "";
        String conditionString2 = "";
        if (mana1 instanceof ConditionalMana) {
            conditionString1 = ((ConditionalMana) mana1).getConditionString();
        }
        if (mana2 instanceof ConditionalMana) {
            conditionString2 = ((ConditionalMana) mana2).getConditionString();
        }
        if (!conditionString1.equals(conditionString2)) {
            return null;
        }
        Mana moreMana;
        Mana lessMana;
        if (mana2.countColored() > mana1.countColored() || mana2.getAny() > mana1.getAny() || mana2.count() > mana1.count()) {
            moreMana = mana2;
            lessMana = mana1;
        } else {
            moreMana = mana1;
            lessMana = mana2;
        }
        int anyDiff = CardUtil.overflowDec(mana2.getAny(), mana1.getAny());
        if (lessMana.getWhite() > moreMana.getWhite()) {
            anyDiff = CardUtil.overflowDec(anyDiff, CardUtil.overflowDec(lessMana.getWhite(), moreMana.getWhite()));
            if (anyDiff < 0) {
                return null;
            }
        }
        if (lessMana.getRed() > moreMana.getRed()) {
            anyDiff = CardUtil.overflowDec(anyDiff, CardUtil.overflowDec(lessMana.getRed(), moreMana.getRed()));
            if (anyDiff < 0) {
                return null;
            }
        }
        if (lessMana.getGreen() > moreMana.getGreen()) {
            anyDiff = CardUtil.overflowDec(anyDiff, CardUtil.overflowDec(lessMana.getGreen(), moreMana.getGreen()));
            if (anyDiff < 0) {
                return null;
            }
        }
        if (lessMana.getBlue() > moreMana.getBlue()) {
            anyDiff = CardUtil.overflowDec(anyDiff, CardUtil.overflowDec(lessMana.getBlue(), moreMana.getBlue()));
            if (anyDiff < 0) {
                return null;
            }
        }
        if (lessMana.getBlack() > moreMana.getBlack()) {
            anyDiff = CardUtil.overflowDec(anyDiff, CardUtil.overflowDec(lessMana.getBlack(), moreMana.getBlack()));
            if (anyDiff < 0) {
                return null;
            }
        }
        if (lessMana.getColorless() > moreMana.getColorless()) {
            return null; // Any (color) can't produce colorless mana
        }
        if (lessMana.getAny() > moreMana.getAny()) {
            return null;
        }
        return moreMana;
    }

    /**
     * Returns the total count of mana colors that have at least one.
     *
     * @return the total count of mana colors that have at least one.
     */
    public int getDifferentColors() {
        int count = 0;
        if (white.getAmount() > 0) {
            count++;
        }
        if (blue.getAmount() > 0) {
            count++;
        }
        if (black.getAmount() > 0) {
            count++;
        }
        if (red.getAmount() > 0) {
            count++;
        }
        if (green.getAmount() > 0) {
            count++;
        }
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Mana mana = (Mana) o;
        return flag == mana.flag
                && Objects.equals(white, mana.white)
                && Objects.equals(blue, mana.blue)
                && Objects.equals(black, mana.black)
                && Objects.equals(red, mana.red)
                && Objects.equals(green, mana.green)
                && Objects.equals(generic, mana.generic)
                && Objects.equals(colorless, mana.colorless)
                && Objects.equals(any, mana.any);
    }

    @Override
    public int hashCode() {
        return Objects.hash(white, blue, black, red, green, generic, colorless, any, flag);
    }

    /**
     * Checks that the {@code value} passed in is not less than 0. If the value
     * is negative, it is logged and 0 is returned.
     *
     * @param value the value to check.
     * @param name  the name of the value to check. Used to make logging of the
     *              {@code value} easier
     * @return the {@code value} passed in, unless it is minus, in which case 0
     * is returned.
     */
    private static int notNegative(int value, final String name) {
        if (value < 0) {
            logger.info(name + " can not be less than 0. Passed in: " + value + " Defaulting to 0.");
            value = 0;
        }
        return value;
    }
}
