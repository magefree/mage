package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author JayDi85
 */
public final class SegaDreamcastCards extends ExpansionSet {

    private static final SegaDreamcastCards instance = new SegaDreamcastCards();

    public static SegaDreamcastCards getInstance() {
        return instance;
    }

    private SegaDreamcastCards() {
        super("Sega Dreamcast Cards", "PSDG", ExpansionSet.buildDate(2001,6,28), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Arden Angel", 1, Rarity.RARE, mage.cards.a.ArdenAngel.class, RETRO_ART));
        cards.add(new SetCardInfo("Velukan Dragon", 10, Rarity.RARE, mage.cards.v.VelukanDragon.class, RETRO_ART));
    }
}
