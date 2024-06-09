package mage.verify.mtgjson;

import java.util.List;

public final class MtgJsonSet {
    // v5 support
    // https://mtgjson.com/data-models/card-atomic/
    // contains only used fields, if you need more for tests then just add it here

    public List<MtgJsonCard> cards;
    public String code;
    public String name;
    public String releaseDate;
    public int totalSetSize;

    public String block;

    public String parentCode;

    @Override
    public String toString() {
        return code + " - " + name;
    }
}
