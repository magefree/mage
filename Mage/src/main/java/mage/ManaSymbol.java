package mage;



/**
 * Enum representing the mana symbols.
 * <p>
 * 107.4. The mana symbols are {W}, {U}, {B}, {R}, {G}, and {X}; the numerals
 * {0}, {1}, {2}, {3}, {4}, and so on; the hybrid symbols {W/U}, {W/B}, {U/B},
 * {U/R}, {B/R}, {B/G}, {R/G}, {R/W}, {G/W}, and {G/U}; the monocolored hybrid
 * symbols {2/W}, {2/U}, {2/B}, {2/R}, and {2/G}; the Phyrexian mana symbols
 * {W/P}, {U/P}, {B/P}, {R/P}, and {G/P}; and the snow symbol {S}.
 * <p>
 * 107.4a. There are five primary colored mana symbols: {W} is white, {U} blue,
 * {B} black, {R} red, and {G} green. These symbols are used to represent colored
 * mana, and also to represent colored mana in costs. Colored mana in costs can
 * be paid only with the appropriate color of mana. See rule 202, "Mana Cost and
 * Color."
 * <p>
 * 107.4b. Numeral symbols (such as {1}) and variable symbols (such as {X})
 * represent generic mana in costs. Generic mana in costs can be paid with any
 * type of mana. For more information about {X}, see rule 107.3.
 * <p>
 * 107.4c. Numeral symbols (such as {1}) and variable symbols (such as {X}) can
 * also represent colorless mana if they appear in the effect of a spell or
 * ability that reads "add [mana symbol]" or something similar.
 * (See rule 107.3e.)
 * <p>
 * 107.4d. The symbol {0} represents zero mana and is used as a placeholder for a
 * cost that can be paid with no resources. (See rule 117.5.)
 * <p>
 * 107.4e. Hybrid mana symbols are also colored mana symbols. Each one represents
 * a cost that can be paid in one of two ways, as represented by the two halves
 * of the symbol. A hybrid symbol such as {W/U} can be paid with either white or
 * blue mana, and a monocolored hybrid symbol such as {2/B} can be paid with
 * either one black mana or two mana of any type. A hybrid mana symbol is all of
 * its component colors. Example: {G/W}{G/W} can be paid by spending {G}{G},
 * {G}{W}, or {W}{W}.
 * <p>
 * 107.4f. Phyrexian mana symbols are colored mana symbols: {W/P} is white, {U/P}
 * is blue, {B/P} is black, {R/P} is red, and {G/P} is green. A Phyrexian mana
 * symbol represents a cost that can be paid either with one mana of its color or
 * by paying 2 life. Example: {W/P}{W/P} can be paid by spending {W}{W}, by
 * spending {W} and paying 2 life, or by paying 4 life.
 * <p>
 * 107.4g. In rules text, the Phyrexian symbol {P} with no colored background
 * means any of the five Phyrexian mana symbols.
 * <p>
 * 107.4h. The snow mana symbol {S} represents one generic mana in a cost. This
 * generic mana can be paid with one mana of any type produced by a snow
 * permanent (see rule 205.4f). Effects that reduce the amount of generic mana
 * you pay don't affect {S} costs. (There is no such thing as "snow mana"; "snow"
 * is not a type of mana.)
 *
 * @author noxx
 */
public enum ManaSymbol {

    W("{W}", Type.PRIMARY, Type.COLORED, Type.MONOCOLORED),
    U("{U}", Type.PRIMARY, Type.COLORED, Type.MONOCOLORED),
    B("{B}", Type.PRIMARY, Type.COLORED, Type.MONOCOLORED),
    R("{R}", Type.PRIMARY, Type.COLORED, Type.MONOCOLORED),
    G("{G}", Type.PRIMARY, Type.COLORED, Type.MONOCOLORED),
    X("{X}", Type.GENERIC, Type.COLORLESS),
    NUMERIC("{N/A}", Type.GENERIC, Type.COLORLESS),
    HYBRID_WU("{W/U}", W, U, Type.HYBRID, Type.COLORED),
    HYBRID_WB("{W/B}", W, B, Type.HYBRID, Type.COLORED),
    HYBRID_UB("{U/B}", U, B, Type.HYBRID, Type.COLORED),
    HYBRID_UR("{U/R}", U, R, Type.HYBRID, Type.COLORED),
    HYBRID_BR("{B/R}", B, R, Type.HYBRID, Type.COLORED),
    HYBRID_BG("{B/G}", B, G, Type.HYBRID, Type.COLORED),
    HYBRID_RG("{R/G}", R, G, Type.HYBRID, Type.COLORED),
    HYBRID_RW("{R/W}", R, W, Type.HYBRID, Type.COLORED),
    HYBRID_GW("{G/W}", G, W, Type.HYBRID, Type.COLORED),
    HYBRID_GU("{G/U}", G, U, Type.HYBRID, Type.COLORED),
    MONOCOLORED_HYBRID_W("{2/W}", W, Type.HYBRID, Type.MONOCOLORED, Type.COLORED),
    MONOCOLORED_HYBRID_U("{2/U}", U, Type.HYBRID, Type.MONOCOLORED, Type.COLORED),
    MONOCOLORED_HYBRID_B("{2/B}", B, Type.HYBRID, Type.MONOCOLORED, Type.COLORED),
    MONOCOLORED_HYBRID_R("{2/R}", R, Type.HYBRID, Type.MONOCOLORED, Type.COLORED),
    MONOCOLORED_HYBRID_G("{2/G}", G, Type.HYBRID, Type.MONOCOLORED, Type.COLORED),
    PHYREXIAN_W("{W/P}", W, Type.PHYREXIAN, Type.COLORED, Type.MONOCOLORED),
    PHYREXIAN_G("{G/P}", G, Type.PHYREXIAN, Type.COLORED, Type.MONOCOLORED),
    PHYREXIAN_R("{R/P}", R, Type.PHYREXIAN, Type.COLORED, Type.MONOCOLORED),
    PHYREXIAN_B("{B/P}", B, Type.PHYREXIAN, Type.COLORED, Type.MONOCOLORED),
    PHYREXIAN_U("{U/P}", U, Type.PHYREXIAN, Type.COLORED, Type.MONOCOLORED),
    PHYREXIAN_HYBRID_WU("{W/U/P}", W, U, Type.PHYREXIAN, Type.HYBRID, Type.COLORED),
    PHYREXIAN_HYBRID_WB("{W/B/P}", W, B, Type.PHYREXIAN, Type.HYBRID, Type.COLORED),
    PHYREXIAN_HYBRID_UB("{U/B/P}", U, B, Type.PHYREXIAN, Type.HYBRID, Type.COLORED),
    PHYREXIAN_HYBRID_UR("{U/R/P}", U, R, Type.PHYREXIAN, Type.HYBRID, Type.COLORED),
    PHYREXIAN_HYBRID_BR("{B/R/P}", B, R, Type.PHYREXIAN, Type.HYBRID, Type.COLORED),
    PHYREXIAN_HYBRID_BG("{B/G/P}", B, G, Type.PHYREXIAN, Type.HYBRID, Type.COLORED),
    PHYREXIAN_HYBRID_RG("{R/G/P}", R, G, Type.PHYREXIAN, Type.HYBRID, Type.COLORED),
    PHYREXIAN_HYBRID_RW("{R/W/P}", R, W, Type.PHYREXIAN, Type.HYBRID, Type.COLORED),
    PHYREXIAN_HYBRID_GW("{G/W/P}", G, W, Type.PHYREXIAN, Type.HYBRID, Type.COLORED),
    PHYREXIAN_HYBRID_GU("{G/U/P}", G, U, Type.PHYREXIAN, Type.HYBRID, Type.COLORED),
    SNOW("{S}", Type.SNOW);

    private enum Type {
        PRIMARY,
        COLORED,
        GENERIC,
        COLORLESS,
        MONOCOLORED,
        HYBRID,
        PHYREXIAN,
        SNOW
    }

    private final String symbol;
    private final boolean primary;
    private final boolean colored;
    private final boolean generic;
    private final boolean colorless;
    private final boolean monocolored;
    private final boolean hybrid;
    private final boolean phyrexian;
    private final boolean snow;

    private final boolean white;
    private final boolean blue;
    private final boolean black;
    private final boolean red;
    private final boolean green;

    private final ManaSymbol manaSymbol1;
    private final ManaSymbol manaSymbol2;

    /**
     * @param symbol
     * @param manaSymbol1 First associated mana symbol. For hybrid mana symbol.
     * @param manaSymbol2 Second associated mana symbol. For hybrid mana symbol.
     * @param types
     */
    ManaSymbol(String symbol, ManaSymbol manaSymbol1, ManaSymbol manaSymbol2, Type... types) {
        this.symbol = symbol;
        boolean lPrimary = false, lColored = false, lGeneric = false, lColorless = false;
        boolean lMonocolored = false, lHybrid = false, lPhyrexian = false, lSnow = false;
        for (Type type : types) {
            switch (type) {
                case PRIMARY:
                    lPrimary = true;
                    break;
                case COLORED:
                    lColored = true;
                    break;
                case GENERIC:
                    lGeneric = true;
                    break;
                case COLORLESS:
                    lColorless = true;
                    break;
                case MONOCOLORED:
                    lMonocolored = true;
                    break;
                case HYBRID:
                    lHybrid = true;
                    break;
                case PHYREXIAN:
                    lPhyrexian = true;
                    break;
                case SNOW:
                    lSnow = true;
                    break;
            }
        }
        primary = lPrimary;
        colored = lColored;
        generic = lGeneric;
        colorless = lColorless;
        monocolored = lMonocolored;
        hybrid = lHybrid;
        phyrexian = lPhyrexian;
        snow = lSnow;
        white = symbol.contains("W");
        blue = symbol.contains("U");
        black = symbol.contains("B");
        red = symbol.contains("R");
        green = symbol.contains("G");
        this.manaSymbol1 = manaSymbol1;
        this.manaSymbol2 = manaSymbol2;
    }

    /**
     * @param symbol
     * @param manaSymbol Associated mana symbol. For monocolored hybrid and phyrexian mana.
     * @param types
     */
    ManaSymbol(String symbol, ManaSymbol manaSymbol, Type... types) {
        this(symbol, manaSymbol, null, types);
    }

    ManaSymbol(String symbol, Type... types) {
        this(symbol, null, null, types);
    }

    public boolean isPrimary() {
        return primary;
    }

    public boolean isColored() {
        return colored;
    }

    public boolean isGeneric() {
        return generic;
    }

    public boolean isColorless() {
        return colorless;
    }

    public boolean isMonocolored() {
        return monocolored;
    }

    public boolean isHybrid() {
        return hybrid;
    }

    public boolean isPhyrexian() {
        return phyrexian;
    }

    public boolean isSnow() {
        return snow;
    }

    public boolean isWhite() {
        return white;
    }

    public boolean isBlue() {
        return blue;
    }

    public boolean isBlack() {
        return black;
    }

    public boolean isRed() {
        return red;
    }

    public boolean isGreen() {
        return green;
    }

    public ManaSymbol getManaSymbol1() {
        return manaSymbol1;
    }

    public ManaSymbol getManaSymbol2() {
        return manaSymbol2;
    }

    @Override
    public String toString() {
        return symbol;
    }

}
