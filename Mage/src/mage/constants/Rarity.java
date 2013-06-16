package mage.constants;

/**
 *
 * @author North
 */
public enum Rarity {

    NA ("na", "na", "N", 0),
    LAND ("Land", "common", "C", 1),
    COMMON ("Common", "common", "C", 1),
    UNCOMMON ("Uncommon", "uncommon", "U", 2),
    RARE ("Rare", "rare", "R", 3),
    MYTHIC ("Mythic", "mythic", "M", 3),
    SPECIAL ("Special", "special", "SP", 3);

    private String text;
    private String symbolCode;
    private String code;
    private int rating;

    Rarity(String text, String symbolCode, String code, int rating) {
        this.text = text;
        this.symbolCode = symbolCode;
        this.code = code;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return text;
    }

    public String getSymbolCode() {
        return symbolCode;
    }

    public String getCode() {
        return code;
    }

    public int getRating() {
        return rating;
    }
}
