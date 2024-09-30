package mage.verify.mtgjson;

import java.util.List;

/**
 * MTGJSON v5: set class
 * <p>
 * Contains set info and related cards list
 * Only used fields, if you need more for tests then just add it here
 * <p>
 * API docs <a href="https://mtgjson.com/data-models/set/">here</a>
 *
 * @author JayDi85
 */
public final class MtgJsonSet {

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
