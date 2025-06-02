package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/p15a
 */
public class The15thAnniversaryCards extends ExpansionSet {

    private static final The15thAnniversaryCards instance = new The15thAnniversaryCards();

    public static The15thAnniversaryCards getInstance() {
        return instance;
    }

    private The15thAnniversaryCards() {
        super("15th Anniversary Cards", "P15A", ExpansionSet.buildDate(2008, 4, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Char", 1, Rarity.RARE, mage.cards.c.Char.class));
        cards.add(new SetCardInfo("Kamahl, Pit Fighter", 2, Rarity.RARE, mage.cards.k.KamahlPitFighter.class));
     }
}
