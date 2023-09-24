
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author Susucr
 */
public final class SpecialGuests extends ExpansionSet {

    private static final SpecialGuests instance = new SpecialGuests();

    public static SpecialGuests getInstance() {
        return instance;
    }

    private SpecialGuests() {
        super("Special Guests", "SPG", ExpansionSet.buildDate(2023, 11, 17), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Lord of Atlantis", 1, Rarity.RARE, mage.cards.l.LordOfAtlantis.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mana Crypt", 17, Rarity.MYTHIC, mage.cards.m.ManaCrypt.class, FULL_ART_BFZ_VARIOUS));
    }
}
