package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/p30t
 *
 * @author DarioBarrabinoDev
 */
public final class The30thAnniversaryCelebrationTokyo extends ExpansionSet {

    private static final The30thAnniversaryCelebrationTokyo instance = new The30thAnniversaryCelebrationTokyo();

    public static The30thAnniversaryCelebrationTokyo getInstance() {
        return instance;
    }

    private The30thAnniversaryCelebrationTokyo() {
        super("30th Anniversary Celebration Tokyo", "P30T", ExpansionSet.buildDate(2023, 9, 1), SetType.PROMOTIONAL);
        hasBasicLands = false;

        cards.add(new SetCardInfo("Destroy Evil", "1", Rarity.RARE, mage.cards.d.DestroyEvil.class));
        cards.add(new SetCardInfo("Shivan Dragon", "2", Rarity.RARE, mage.cards.s.ShivanDragon.class));
    }
}
