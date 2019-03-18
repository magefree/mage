package mage.constants;

/**
 * @author North
 */
public enum Rarity {

    LAND("Land", "common", "C", 1, 1),
    COMMON("Common", "common", "C", 1, 2),
    UNCOMMON("Uncommon", "uncommon", "U", 2, 3),
    RARE("Rare", "rare", "R", 3, 4),
    MYTHIC("Mythic", "mythic", "M", 3, 5),
    SPECIAL("Special", "special", "Special", 3, 6),
    BONUS("Bonus", "bonus", "Bonus", 3, 7);

    private final String text;
    private final String symbolCode;
    private final String code;
    private final int rating;
    private final int sorting;

    Rarity(String text, String symbolCode, String code, int rating, int sorting) {
        this.text = text;
        this.symbolCode = symbolCode;
        this.code = code;
        this.rating = rating;
        this.sorting = sorting;
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

    public int getSorting() {
        return sorting;
    }
}
