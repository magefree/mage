package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/p30h
 *
 * @author resech
 */
public class The30thAnniversaryHistoryPromos extends ExpansionSet {

    private static final The30thAnniversaryHistoryPromos instance = new The30thAnniversaryHistoryPromos();

    public static The30thAnniversaryHistoryPromos getInstance() {
        return instance;
    }

    private The30thAnniversaryHistoryPromos() {
        super("30th Anniversary History Promos", "P30H", ExpansionSet.buildDate(2022, 9, 9), SetType.PROMOTIONAL);
        hasBasicLands = false;


        cards.add(new SetCardInfo("Llanowar Elves", "5*", Rarity.RARE, mage.cards.l.LlanowarElves.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Llanowar Elves", 5, Rarity.RARE, mage.cards.l.LlanowarElves.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lord of Atlantis", "2*", Rarity.RARE, mage.cards.l.LordOfAtlantis.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Lord of Atlantis", 2, Rarity.RARE, mage.cards.l.LordOfAtlantis.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sengir Vampire", "3*", Rarity.RARE, mage.cards.s.SengirVampire.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Sengir Vampire", 3, Rarity.RARE, mage.cards.s.SengirVampire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Serra Angel", "1*", Rarity.RARE, mage.cards.s.SerraAngel.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Serra Angel", 1, Rarity.RARE, mage.cards.s.SerraAngel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shivan Dragon", "4*", Rarity.RARE, mage.cards.s.ShivanDragon.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Shivan Dragon", 4, Rarity.RARE, mage.cards.s.ShivanDragon.class, NON_FULL_USE_VARIOUS));
    }
}
