package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pl24
 */
public class YearOfTheDragon2024 extends ExpansionSet {

    private static final YearOfTheDragon2024 instance = new YearOfTheDragon2024();

    public static YearOfTheDragon2024 getInstance() {
        return instance;
    }

    private YearOfTheDragon2024() {
        super("Year of the Dragon 2024", "PL24", ExpansionSet.buildDate(2024, 2, 8), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Dragon Tempest", 7, Rarity.RARE, mage.cards.d.DragonTempest.class));
        cards.add(new SetCardInfo("Dragonlord's Servant", 1, Rarity.RARE, mage.cards.d.DragonlordsServant.class));
        cards.add(new SetCardInfo("Korvold, Fae-Cursed King", 6, Rarity.MYTHIC, mage.cards.k.KorvoldFaeCursedKing.class));
        cards.add(new SetCardInfo("Mountain", 5, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Sarkhan Unbroken", 2, Rarity.MYTHIC, mage.cards.s.SarkhanUnbroken.class));
        cards.add(new SetCardInfo("Steel Hellkite", 4, Rarity.RARE, mage.cards.s.SteelHellkite.class));
    }
}
