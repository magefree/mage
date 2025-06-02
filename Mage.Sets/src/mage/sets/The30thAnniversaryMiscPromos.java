package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/p30a
 *
 * @author Jmlundeen
 */
public class The30thAnniversaryMiscPromos extends ExpansionSet {

    private static final The30thAnniversaryMiscPromos instance = new The30thAnniversaryMiscPromos();

    public static The30thAnniversaryMiscPromos getInstance() {
        return instance;
    }

    private The30thAnniversaryMiscPromos() {
        super("30th Anniversary Misc Promos", "P30M", ExpansionSet.buildDate(2022, 9, 2), SetType.PROMOTIONAL);
        hasBasicLands = false;


        cards.add(new SetCardInfo("Arcane Signet", "1F", Rarity.RARE, mage.cards.a.ArcaneSignet.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arcane Signet", "1F*", Rarity.RARE, mage.cards.a.ArcaneSignet.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arcane Signet", "1P", Rarity.RARE, mage.cards.a.ArcaneSignet.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Lotus Petal", 2, Rarity.MYTHIC, mage.cards.l.LotusPetal.class));
    }
}
