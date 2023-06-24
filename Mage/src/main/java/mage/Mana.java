package mage;

import mage.abilities.condition.Condition;
import mage.constants.ColoredManaSymbol;
import mage.constants.ManaType;
import mage.filter.FilterMana;
import mage.util.CardUtil;
import mage.util.Copyable;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.*;
import java.util.function.BiFunction;

/**
 * WARNING, all mana operations must use overflow check, see usage of CardUtil.addWithOverflowCheck and same methods
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Mana implements Comparable<Mana>, Serializable, Copyable<Mana> {

    private static final transient Logger logger = Logger.getLogger(Mana.class);

    protected int white;
    protected int blue;
    protected int black;
    protected int red;
    protected int green;
    protected int generic;
    protected int colorless;
    protected int any;
    protected boolean flag;

    /**
     * Default constructor. Creates a {@link Mana} object with 0 values.
     */
    public Mana() {
        white = 0;
        blue = 0;
        black = 0;
        red = 0;
        green = 0;
        generic = 0;
        colorless = 0;
        any = 0;
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
        this.white = notNegative(white, "White");
        this.blue = notNegative(blue, "Blue");
        this.black = notNegative(black, "Black");
        this.red = notNegative(red, "Red");
        this.green = notNegative(green, "Green");
        this.generic = notNegative(generic, "Generic");
        this.colorless = notNegative(colorless, "Colorless");
        this.any = notNegative(any, "Any");
        this.flag = false;
    }

    /**
     * Copy constructor. Creates a {@link Mana} object from existing
     * {@link Mana}
     *
     * @param mana object to create copy from.
     */
    public Mana(final Mana mana) {
        Objects.requireNonNull(mana, "The passed in mana can not be null");
        this.white = mana.white;
        this.blue = mana.blue;
        this.black = mana.black;
        this.red = mana.red;
        this.green = mana.green;
        this.generic = mana.generic;
        this.colorless = mana.colorless;
        this.any = mana.any;
        this.flag = mana.flag;
    }


    public Mana(final ColoredManaSymbol color) {
        this(color, 1);
    }

    /**
     * Creates {@link Mana} object from {@link ColoredManaSymbol}. Created
     * {@link Mana} will have a single mana of the passed in
     * {@link ColoredManaSymbol} color.
     *
     * @param color  the color to create the {@link Mana} object with.
     * @param amount the number of mana to add of the given color
     */

    public Mana(final ColoredManaSymbol color, int amount) {
        this();
        Objects.requireNonNull(color, "The passed in ColoredManaSymbol can not be null");
        switch (color) {
            case W:
                white = CardUtil.overflowInc(white, amount);
                break;
            case U:
                blue = CardUtil.overflowInc(blue, amount);
                break;
            case B:
                black = CardUtil.overflowInc(black, amount);
                break;
            case R:
                red = CardUtil.overflowInc(red, amount);
                break;
            case G:
                green = CardUtil.overflowInc(green, amount);
                break;
            default:
                throw new IllegalArgumentException("Unknown mana color: " + color);
        }
    }

    /**
     * Creates a {@link Mana} object of one mana of the passed {@link ManaType}.
     *
     * @param manaType The type of mana to set to one.
     */
    public Mana(final ManaType manaType) {
        this();
        Objects.requireNonNull(manaType, "The passed in ManaType can not be null");
        switch (manaType) {
            case WHITE:
                white = CardUtil.overflowInc(white, 1);
                break;
            case BLUE:
                blue = CardUtil.overflowInc(blue, 1);
                break;
            case BLACK:
                black = CardUtil.overflowInc(black, 1);
                break;
            case RED:
                red = CardUtil.overflowInc(red, 1);
                break;
            case GREEN:
                green = CardUtil.overflowInc(green, 1);
                break;
            case COLORLESS:
                colorless = CardUtil.overflowInc(colorless, 1);
                break;
            case GENERIC:
                generic = CardUtil.overflowInc(generic, 1);
                break;
            default:
                throw new IllegalArgumentException("Unknown manaType: " + manaType);
        }
    }

    /**
     * Creates a {@link Mana} object of #num mana of the passed {@link ManaType}.
     *
     * @param manaType  The type of mana to set.
     * @param num       The number of mana available of the passed ManaType.
     **/
    public Mana(final ManaType manaType, int num) {
        this();
        Objects.requireNonNull(manaType, "The passed in ManaType can not be null");
        switch (manaType) {
            case WHITE:
                white = CardUtil.overflowInc(white, num);
                break;
            case BLUE:
                blue = CardUtil.overflowInc(blue, num);
                break;
            case BLACK:
                black = CardUtil.overflowInc(black, num);
                break;
            case RED:
                red = CardUtil.overflowInc(red, num);
                break;
            case GREEN:
                green = CardUtil.overflowInc(green, num);
                break;
            case COLORLESS:
                colorless = CardUtil.overflowInc(colorless, num);
                break;
            case GENERIC:
                generic = CardUtil.overflowInc(generic, num);
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
        white = CardUtil.overflowInc(white, mana.white);
        blue = CardUtil.overflowInc(blue, mana.blue);
        black = CardUtil.overflowInc(black, mana.black);
        red = CardUtil.overflowInc(red, mana.red);
        green = CardUtil.overflowInc(green, mana.green);
        generic = CardUtil.overflowInc(generic, mana.generic);
        colorless = CardUtil.overflowInc(colorless, mana.colorless);
        any = CardUtil.overflowInc(any, mana.any);
    }

    /**
     * Increases this mana by one of the passed in ManaType.
     *
     * @param manaType the type of mana to increase by one.
     */
    public void increase(ManaType manaType) {
        increaseOrDecrease(manaType, true);
    }

    /**
     * Decreases this mana by onw of the passed in ManaType.
     *
     * @param manaType the type of mana to increase by one.
     */
    public void decrease(ManaType manaType) {
        increaseOrDecrease(manaType, false);
    }

    /**
     * Helper function for increase and decrease to not have the code duplicated.
     * @param manaType
     * @param increase
     */
    private void increaseOrDecrease(ManaType manaType, boolean increase) {
        BiFunction<Integer, Integer, Integer> overflowIncOrDec = increase ? CardUtil::overflowInc : CardUtil::overflowDec;
        switch (manaType) {
            case WHITE:
                white = overflowIncOrDec.apply(white, 1);
                break;
            case BLUE:
                blue = overflowIncOrDec.apply(blue, 1);
                break;
            case BLACK:
                black = overflowIncOrDec.apply(black, 1);
                break;
            case RED:
                red = overflowIncOrDec.apply(red, 1);
                break;
            case GREEN:
                green = overflowIncOrDec.apply(green, 1);
                break;
            case COLORLESS:
                colorless = overflowIncOrDec.apply(colorless, 1);
                break;
            case GENERIC:
                generic = overflowIncOrDec.apply(generic, 1);
                break;
        }
    }

    /**
     * Increases the White mana by one.
     */
    public void increaseWhite() {
        white = CardUtil.overflowInc(white, 1);
    }

    /**
     * Increases the Blue mana by one.
     */
    public void increaseBlue() {
        blue = CardUtil.overflowInc(blue, 1);
    }

    /**
     * Increases the Black mana by one.
     */
    public void increaseBlack() {
        black = CardUtil.overflowInc(black, 1);
    }

    /**
     * Increases the Red mana by one.
     */
    public void increaseRed() {
        red = CardUtil.overflowInc(red, 1);
    }

    /**
     * Increases the Green mana by one.
     */
    public void increaseGreen() {
        green = CardUtil.overflowInc(green, 1);
    }

    /**
     * Increases the Generic mana by one.
     */
    public void increaseGeneric() {
        generic = CardUtil.overflowInc(generic, 1);
    }

    /**
     * Increases the Colorless mana by one.
     */
    public void increaseColorless() {
        colorless = CardUtil.overflowInc(colorless, 1);
    }

    public void increaseAny() {
        any = CardUtil.overflowInc(any, 1);
    }
    public void decreaseAny() {
        any = CardUtil.overflowDec(any, 1);
    }

    /**
     * Subtracts the passed in mana values from this instance.
     *
     * @param mana mana values to subtract
     */
    public void subtract(final Mana mana) {
        white = CardUtil.overflowDec(white, mana.white);
        blue = CardUtil.overflowDec(blue, mana.blue);
        black = CardUtil.overflowDec(black, mana.black);
        red = CardUtil.overflowDec(red, mana.red);
        green = CardUtil.overflowDec(green, mana.green);
        generic = CardUtil.overflowDec(generic, mana.generic);
        colorless = CardUtil.overflowDec(colorless, mana.colorless);
        any = CardUtil.overflowDec(any, mana.any);
    }

    /**
     * Subtracts the passed in mana values from this instance. The difference
     * between this and {@code subtract()} is that if we do not have the
     * available generic mana to pay, we take mana from our colored mana pools.
     *
     * @param mana mana values to subtract
     * @throws ArithmeticException thrown if there is not enough available
     *                             mana to pay the generic cost
     */
    public void subtractCost(final Mana mana) throws ArithmeticException {
        this.subtract(mana);

        // Handle any unpaid generic mana costs
        while (generic < 0) {
            int oldColorless = generic;
            if (white > 0) {
                white = CardUtil.overflowDec(white, 1);
                generic = CardUtil.overflowInc(generic, 1);
                continue;
            }
            if (blue > 0) {
                blue = CardUtil.overflowDec(blue, 1);
                generic = CardUtil.overflowInc(generic, 1);
                continue;
            }
            if (black > 0) {
                black = CardUtil.overflowDec(black, 1);
                generic = CardUtil.overflowInc(generic, 1);
                continue;
            }
            if (red > 0) {
                red = CardUtil.overflowDec(red, 1);
                generic = CardUtil.overflowInc(generic, 1);
                continue;
            }
            if (green > 0) {
                green = CardUtil.overflowDec(green, 1);
                generic = CardUtil.overflowInc(generic, 1);
                continue;
            }
            if (colorless > 0) {
                colorless = CardUtil.overflowDec(colorless, 1);
                generic = CardUtil.overflowInc(generic, 1);
                continue;
            }
            if (any > 0) {
                any = CardUtil.overflowDec(any, 1);
                generic = CardUtil.overflowInc(generic, 1);
                continue;
            }
            if (oldColorless == generic) {
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
        int sum = countColored();
        sum = CardUtil.overflowInc(sum, generic);
        sum = CardUtil.overflowInc(sum, colorless);

        return sum;
    }

    /**
     * Returns the total count of all colored mana.
     *
     * @return the total count of all colored mana.
     */
    public int countColored() {
        int sum = CardUtil.overflowInc(white, blue);
        sum = CardUtil.overflowInc(sum, black);
        sum = CardUtil.overflowInc(sum, red);
        sum = CardUtil.overflowInc(sum, green);
        sum = CardUtil.overflowInc(sum, any);

        return sum;
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
            count = CardUtil.overflowInc(count, white);
        }
        if (filter.isBlue()) {
            count = CardUtil.overflowInc(count, blue);
        }
        if (filter.isBlack()) {
            count = CardUtil.overflowInc(count, black);
        }
        if (filter.isRed()) {
            count = CardUtil.overflowInc(count, red);
        }
        if (filter.isGreen()) {
            count = CardUtil.overflowInc(count, green);
        }
        if (filter.isGeneric()) {
            count = CardUtil.overflowInc(count, generic);
        }
        if (filter.isColorless()) {
            count = CardUtil.overflowInc(count, colorless);
        }
        return count;
    }

    /**
     * Sets all mana to 0.
     */
    public void clear() {
        white = 0;
        blue = 0;
        black = 0;
        red = 0;
        green = 0;
        generic = 0;
        colorless = 0;
        any = 0;
    }

    /**
     * Used in order to reorder mana combinations so they're returned in the order found on cards.
     */
    private static final Map<String, String> colorLetterMap = new HashMap<>();

    static {
        colorLetterMap.put("WR", "RW");
        colorLetterMap.put("WG", "GW");
        colorLetterMap.put("UG", "GU");
        colorLetterMap.put("WRG", "RGW");
        colorLetterMap.put("WUG", "GWU");
        colorLetterMap.put("WUR", "URW");
        colorLetterMap.put("URG", "GUR");
        colorLetterMap.put("UBG", "BGU");
        colorLetterMap.put("WBG", "RWB");
        colorLetterMap.put("WBRG", "BRGW");
        colorLetterMap.put("WURG", "RGWU");
        colorLetterMap.put("WUBG", "GWUB");
    }

    /**
     * The use of a StringBuilder was
     *
     * @return
     */
    private String getColorsInOrder() {
        StringBuilder sb = new StringBuilder();
        if (white > 0) {
            sb.append("W");
        }
        if (blue > 0) {
            sb.append("U");
        }
        if (black > 0) {
            sb.append("B");
        }
        if (red > 0) {
            sb.append("R");
        }
        if (green > 0) {
            sb.append("G");
        }
        String manaString = sb.toString();
        return colorLetterMap.getOrDefault(manaString, manaString);
    }

    private int colorCharToAmount(char color) {
        switch (color) {
            case 'W':
                return white;
            case 'U':
                return blue;
            case 'B':
                return black;
            case 'R':
                return red;
            case 'G':
                return green;
        }
        return 0;
    }

    /**
     * Returns this objects values as a {@link String}.
     *
     * @return this objects values as a {@link String}.
     */
    @Override
    public String toString() {
        StringBuilder sbMana = new StringBuilder();

        if (generic > 0) {
            sbMana.append('{').append(generic).append('}');
        }

        // normal mana
        if (colorless < 20) {
            for (int i = 0; i < colorless; i++) {
                sbMana.append("{C}");
            }
        } else {
            sbMana.append(colorless).append("{C}");
        }

        String colorsInOrder = getColorsInOrder();
        for (char c : colorsInOrder.toCharArray()) {
            int amount = colorCharToAmount(c);
            if (amount < 20) {
                for (int i = 0; i < amount; i++) {
                    sbMana.append('{').append(c).append('}');
                }
            } else {
                sbMana.append(amount).append('{').append(c).append('}');
            }
        }

        if (any < 20) {
            for (int i = 0; i < any; i++) {
                sbMana.append("{Any}");
            }
        } else {
            sbMana.append(any).append("{Any}");
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
     * Returns if the cost (this) can be paid by the mana provided by the passed in {@link Mana} object.
     *
     * @param avail The mana to compare too.
     * @return      boolean indicating if there is enough available mana to pay.
     */
    public boolean enough(final Mana avail) {
        Mana compare = avail.copy();

        // Subtract the mana cost (this) from the mana available (compare).
        // This will only subtract like mana types from one another (e.g. green from green, coloreless from colorless).
        compare.subtract(this);

        // A negative value for compare.X means that mana of type X from the cost could not be paid by mana
        // of the same kind from the available mana.
        // Check each of the types, and see if there is enough mana of any color left to pay for the colors.

        if (compare.colorless < 0) { // Put first to shortcut the calculations
            // Colorless mana can only be paid by colorless mana.
            // If there's a negative value, then there's nothing else that can be used to pay for it.
            return false;
        }
        if (compare.white < 0) {
            compare.any = CardUtil.overflowInc(compare.any, compare.white);
            // A negatice value means that there was more mana of the given type required than there was mana of any
            // color to pay for it.
            // So, there is not enough mana to pay the avail.
            if (compare.any < 0) {
                return false;
            }
            compare.white = 0;
        }
        if (compare.blue < 0) {
            compare.any = CardUtil.overflowInc(compare.any, compare.blue);
            if (compare.any < 0) {
                return false;
            }
            compare.blue = 0;
        }
        if (compare.black < 0) {
            compare.any = CardUtil.overflowInc(compare.any, compare.black);
            if (compare.any < 0) {
                return false;
            }
            compare.black = 0;
        }
        if (compare.red < 0) {
            compare.any = CardUtil.overflowInc(compare.any, compare.red);
            if (compare.any < 0) {
                return false;
            }
            compare.red = 0;
        }
        if (compare.green < 0) {
            compare.any = CardUtil.overflowInc(compare.any, compare.green);
            if (compare.any < 0) {
                return false;
            }
            compare.green = 0;
        }
        if (compare.generic < 0) {
            compare.generic = CardUtil.overflowInc(compare.generic, compare.white);
            compare.generic = CardUtil.overflowInc(compare.generic, compare.blue);
            compare.generic = CardUtil.overflowInc(compare.generic, compare.black);
            compare.generic = CardUtil.overflowInc(compare.generic, compare.red);
            compare.generic = CardUtil.overflowInc(compare.generic, compare.green);
            compare.generic = CardUtil.overflowInc(compare.generic, compare.colorless);
            compare.generic = CardUtil.overflowInc(compare.generic, compare.any);
            return compare.generic >= 0;
        }
        return true;
    }

    /**
     * Returns the total mana needed to meet the cost of this given the available mana passed in
     * as a {@link Mana} object.
     *
     * Used by the AI to calculate what mana it needs to obtain for a spell to become playable.
     *
     * @param avail the mana available to pay the cost
     * @return      the total mana needed to pay this given the available mana passed in as a {@link Mana} object.
     */
    public Mana needed(final Mana avail) {
        Mana compare = avail.copy();

        // Subtract the mana cost (this) from the mana available (compare).
        // This will only subtract like mana types from one another (e.g. green from green, coloreless from colorless).
        compare.subtract(this);

        // A negative value for compare.X means that mana of type X from the cost could not be paid by mana
        // of the same kind from the available mana (this).
        if (compare.white < 0 && compare.any > 0) {
            // Calculate how much of the unpaid colored mana can be covered by mana of any color
            int diff = Math.min(compare.any, Math.abs(compare.white));
            // Make the payment
            compare.any = CardUtil.overflowDec(compare.any, diff);
            compare.white = CardUtil.overflowInc(compare.white, diff);
        }
        if (compare.blue < 0 && compare.any > 0) {
            int diff = Math.min(compare.any, Math.abs(compare.blue));
            compare.any = CardUtil.overflowDec(compare.any, diff);
            compare.blue = CardUtil.overflowInc(compare.blue, diff);
        }
        if (compare.black < 0 && compare.any > 0) {
            int diff = Math.min(compare.any, Math.abs(compare.black));
            compare.any = CardUtil.overflowDec(compare.any, diff);
            compare.black = CardUtil.overflowInc(compare.black, diff);
        }
        if (compare.red < 0 && compare.any > 0) {
            int diff = Math.min(compare.any, Math.abs(compare.red));
            compare.any = CardUtil.overflowDec(compare.any, diff);
            compare.red = CardUtil.overflowInc(compare.red, diff);
        }
        if (compare.green < 0 && compare.any > 0) {
            int diff = Math.min(compare.any, Math.abs(compare.green));
            compare.any = CardUtil.overflowDec(compare.any, diff);
            compare.green = CardUtil.overflowInc(compare.green, diff);
        }

        // Colorless mana can only be paid by colorless sources, so a check for it is not performed.

        if (compare.generic < 0) {
            // Calculate total leftover mana available to pay for generic costs
            int remaining = 0;
            remaining = CardUtil.overflowInc(remaining, Math.max(0, compare.white));
            remaining = CardUtil.overflowInc(remaining, Math.max(0, compare.blue));
            remaining = CardUtil.overflowInc(remaining, Math.max(0, compare.black));
            remaining = CardUtil.overflowInc(remaining, Math.max(0, compare.red));
            remaining = CardUtil.overflowInc(remaining, Math.max(0, compare.green));
            remaining = CardUtil.overflowInc(remaining, Math.max(0, compare.colorless));
            remaining = CardUtil.overflowInc(remaining, Math.max(0, compare.any));

            if (remaining > 0) {
                // Calculate how much of the unpaid generic cost can be paid by the leftover mana
                int diff = Math.min(remaining, Math.abs(compare.generic));
                // Make the payment
                compare.generic = CardUtil.overflowInc(compare.generic, diff);
            }
        }

        // Create the mana object holding the mana needed to pay the cost
        // If the value in compare is positive it means that there's excess mana of that type, and no more is needed.
        Mana needed = new Mana();
        if (compare.white < 0) {
            needed.white = CardUtil.overflowDec(needed.white, compare.white);
        }
        if (compare.blue < 0) {
            needed.blue = CardUtil.overflowDec(needed.blue, compare.blue);
        }
        if (compare.black < 0) {
            needed.black = CardUtil.overflowDec(needed.black, compare.black);
        }
        if (compare.red < 0) {
            needed.red = CardUtil.overflowDec(needed.red, compare.red);
        }
        if (compare.green < 0) {
            needed.green = CardUtil.overflowDec(needed.green, compare.green);
        }
        if (compare.colorless < 0) {
            needed.colorless = CardUtil.overflowDec(needed.colorless, compare.colorless);
        }
        if (compare.generic < 0) {
            needed.generic = CardUtil.overflowDec(needed.generic, compare.generic);
        }
        return needed;
    }

    /**
     * Returns total White mana.
     *
     * @return total White mana.
     */
    public int getWhite() {
        return white;
    }

    /**
     * Sets the total White mana. Can not be negative. Negative values will be
     * logged and set to 0.
     *
     * @param white total White mana.
     */
    public void setWhite(int white) {
        this.white = notNegative(white, "White");
    }

    /**
     * Returns total Blue mana.
     *
     * @return total Blue mana.
     */
    public int getBlue() {
        return blue;
    }

    /**
     * Sets the total Blue mana. Can not be negative. Negative values will be
     * logged and set to 0.
     *
     * @param blue total Blue mana.
     */
    public void setBlue(int blue) {
        this.blue = notNegative(blue, "Blue");
    }

    /**
     * Returns total Black mana.
     *
     * @return total Black mana.
     */
    public int getBlack() {
        return black;
    }

    /**
     * Sets the total Black mana. Can not be negative. Negative values will be
     * logged and set to 0.
     *
     * @param black total Black mana.
     */
    public void setBlack(int black) {
        this.black = notNegative(black, "Black");
    }

    /**
     * Returns total Red mana.
     *
     * @return total Red mana.
     */
    public int getRed() {
        return red;
    }

    /**
     * Sets the total Red mana. Can not be negative. Negative values will be
     * logged and set to 0.
     *
     * @param red total Red mana.
     */
    public void setRed(int red) {
        this.red = notNegative(red, "Red");
    }

    /**
     * Returns total Green mana.
     *
     * @return total Green mana.
     */
    public int getGreen() {
        return green;
    }

    /**
     * Sets the total Green mana. Can not be negative. Negative values will be
     * logged and set to 0.
     *
     * @param green total Green mana.
     */
    public void setGreen(int green) {
        this.green = notNegative(green, "Green");
    }

    /**
     * Returns total Generic mana.
     *
     * @return total Generic mana.
     */
    public int getGeneric() {
        return generic;
    }

    /**
     * Sets the total Generic mana. Can not be negative. Negative values will be
     * logged and set to 0.
     *
     * @param generic total Generic mana.
     */
    public void setGeneric(int generic) {
        this.generic = notNegative(generic, "Generic");
    }

    /**
     * Returns total Colorless mana.
     *
     * @return total Colorless mana.
     */
    public int getColorless() {
        return colorless;
    }

    /**
     * Sets the total Colorless mana. Can not be negative. Negative values will
     * be logged and set to 0.
     *
     * @param colorless total Colorless mana.
     */
    public void setColorless(int colorless) {
        this.colorless = notNegative(colorless, "Colorless");
    }

    /**
     * Returns total Any mana.
     *
     * @return total Any mana.
     */
    public int getAny() {
        return any;
    }

    /**
     * Sets the total Any mana. Can not be negative. Negative values will be
     * logged and set to 0.
     *
     * @param any total Any mana.
     */
    public void setAny(int any) {
        this.any = notNegative(any, "Any");
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
        if (mana.white > 0 && this.white > 0) {
            return true;
        }
        if (mana.blue > 0 && this.blue > 0) {
            return true;
        }
        if (mana.black > 0 && this.black > 0) {
            return true;
        }
        if (mana.red > 0 && this.red > 0) {
            return true;
        }
        if (mana.green > 0 && this.green > 0) {
            return true;
        }
        if (mana.colorless > 0 && this.colorless > 0) {
            return true;
        }
        return mana.generic > 0 && this.count() > 0;
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
        if (mana.white > 0 && this.white > 0) {
            return true;
        } else if (mana.blue > 0 && this.blue > 0) {
            return true;
        } else if (mana.black > 0 && this.black > 0) {
            return true;
        } else if (mana.red > 0 && this.red > 0) {
            return true;
        } else if (mana.green > 0 && this.green > 0) {
            return true;
        } else if (mana.colorless > 0 && this.colorless > 0 && includeColorless) {
            return true;
        } else return mana.any > 0 && this.count() > 0;
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
        switch (color) {
            case W:
                return white;
            case U:
                return blue;
            case B:
                return black;
            case R:
                return red;
            case G:
                return green;
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
                return white;
            case BLUE:
                return blue;
            case BLACK:
                return black;
            case RED:
                return red;
            case GREEN:
                return green;
            case COLORLESS:
                return CardUtil.overflowInc(generic, colorless); // TODO: This seems like a mistake
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
        this.any = mana.any;
        this.white = mana.white;
        this.blue = mana.blue;
        this.black = mana.black;
        this.red = mana.red;
        this.green = mana.green;
        this.colorless = mana.colorless;
        this.generic = mana.generic;
        //this.flag = mana.flag;
    }

    /**
     * Returns if the passed in {@link Mana} values are equal to this objects.
     *
     * @param mana the {@link Mana} to compare to.
     * @return if the passed in {@link Mana} values are equal to this object.
     */
    public boolean equalManaValue(final Mana mana) {
        return this.any == mana.any
                && this.white == mana.white
                && this.blue == mana.blue
                && this.black == mana.black
                && this.red == mana.red
                && this.green == mana.green
                && this.colorless == mana.colorless
                && this.generic == mana.generic;
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
        return this.white >= mana.white
                && this.blue >= mana.blue
                && this.black >= mana.black
                && this.red >= mana.red
                && this.green >= mana.green
                && this.colorless >= mana.colorless
                && (this.generic >= mana.generic
                || CardUtil.overflowInc(this.countColored(), this.colorless) >= mana.count());

    }

    public boolean isMoreValuableThan(Mana that) {
        // Use of == is intentional since getMoreValuableMana returns one of its inputs.
        return this == Mana.getMoreValuableMana(this, that);
    }

    /**
     * Returns the mana that is more colored or has a greater amount but does
     * not contain one less mana in any type but generic.
     * <p>
     * See tests ManaTest.moreValuableManaTest for several examples
     *
     * Examples:
     *      {1}       and {R}       -> {R}
     *      {2}       and {1}{W}    -> {1}{W}
     *      {3}       and {1}{W}    -> {1}{W}
     *      {1}{W}{R} and {G}{W}{R} -> {G}{W}{R}
     *      {G}{W}{R} and {G}{W}{R} -> null
     *      {G}{W}{B} and {G}{W}{R} -> null
     *      {C}       and {ANY}     -> null
     *
     * @param mana1     The 1st mana to compare.
     * @param mana2     The 2nd mana to compare.
     * @return          The greater of the two manas, or null if they're the same OR they cannot be compared
     */
    public static Mana getMoreValuableMana(final Mana mana1, final Mana mana2) {
        if (mana1.equals(mana2)) {
            return null;
        }

        boolean mana1IsConditional = mana1 instanceof ConditionalMana;
        boolean mana2IsConditional = mana2 instanceof ConditionalMana;
        if (mana1IsConditional != mana2IsConditional) {
            return null;
        }

        if (mana1IsConditional) {
            List<Condition> conditions1 = ((ConditionalMana) mana1).getConditions();
            List<Condition> conditions2 = ((ConditionalMana) mana2).getConditions();
            if (!Objects.equals(conditions1, conditions2)) {
                return null;
            }
        }

        // Set one mana as moreMana and one as lessMana.
        Mana moreMana;
        Mana lessMana;
        if (mana2.any > mana1.any
                || mana2.colorless > mana1.colorless
                || mana2.countColored() > mana1.countColored()
                || (mana2.countColored() == mana1.countColored()
                        && mana2.colorless == mana1.colorless
                        && mana2.count() > mana1.count())) {
            moreMana = mana2;
            lessMana = mana1;
        } else {
            moreMana = mana1;
            lessMana = mana2;
        }

        if (lessMana.any > moreMana.any) {
            return null;
        }
        if (lessMana.colorless > moreMana.colorless) {
            return null; // Any (color) can't produce colorless mana
        }

        int anyDiff = CardUtil.overflowDec(moreMana.any, lessMana.any);

        int whiteDiff = CardUtil.overflowDec(lessMana.white, moreMana.white);
        if (whiteDiff > 0) {
            anyDiff = CardUtil.overflowDec(anyDiff, whiteDiff);
            if (anyDiff < 0) {
                return null;
            }
        }

        int redDiff = CardUtil.overflowDec(lessMana.red, moreMana.red);
        if (redDiff > 0) {
            anyDiff = CardUtil.overflowDec(anyDiff, redDiff);
            if (anyDiff < 0) {
                return null;
            }
        }

        int greenDiff = CardUtil.overflowDec(lessMana.green, moreMana.green);
        if (greenDiff > 0) {
            anyDiff = CardUtil.overflowDec(anyDiff, greenDiff);
            if (anyDiff < 0) {
                return null;
            }
        }

        int blueDiff = CardUtil.overflowDec(lessMana.blue, moreMana.blue);
        if (blueDiff > 0) {
            anyDiff = CardUtil.overflowDec(anyDiff, blueDiff);
            if (anyDiff < 0) {
                return null;
            }
        }

        int blackDiff = CardUtil.overflowDec(lessMana.black, moreMana.black);
        if (blackDiff > 0) {
            anyDiff = CardUtil.overflowDec(anyDiff, blackDiff);
            if (anyDiff < 0) {
                return null;
            }
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
        if (white > 0) {
            count = CardUtil.overflowInc(count, 1);
        }
        if (blue > 0) {
            count = CardUtil.overflowInc(count, 1);
        }
        if (black > 0) {
            count = CardUtil.overflowInc(count, 1);
        }
        if (red > 0) {
            count = CardUtil.overflowInc(count, 1);
        }
        if (green > 0) {
            count = CardUtil.overflowInc(count, 1);
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
                && white == mana.white
                && blue == mana.blue
                && black == mana.black
                && red == mana.red
                && green == mana.green
                && generic == mana.generic
                && colorless == mana.colorless
                && any == mana.any;
    }

    /**
     * Hardcoding here versus using Objects.hash in order to increase performance since this is
     * called thousands of times by {@link mage.abilities.mana.ManaOptions#addManaWithCost(List, Game)}
     *
     * @return
     */
    @Override
    public int hashCode() {
        long result = 1;

        result = 31 * result + white;
        result = 31 * result + blue;
        result = 31 * result + black;
        result = 31 * result + red;
        result = 31 * result + green;
        result = 31 * result + generic;
        result = 31 * result + colorless;
        result = 31 * result + any;
        result = 31 * result + (flag ? 1 : 0);

        return Long.hashCode(result);
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
