package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pal05
 */
public class ArenaLeague2005 extends ExpansionSet {

    private static final ArenaLeague2005 instance = new ArenaLeague2005();

    public static ArenaLeague2005 getInstance() {
        return instance;
    }

    private ArenaLeague2005() {
        super("Arena League 2005", "PAL05", ExpansionSet.buildDate(2005, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Forest", 5, Rarity.LAND, mage.cards.basiclands.Forest.class));
        cards.add(new SetCardInfo("Genju of the Spires", 6, Rarity.RARE, mage.cards.g.GenjuOfTheSpires.class));
        cards.add(new SetCardInfo("Island", 2, Rarity.LAND, mage.cards.basiclands.Island.class));
        cards.add(new SetCardInfo("Mountain", 4, Rarity.LAND, mage.cards.basiclands.Mountain.class));
        cards.add(new SetCardInfo("Okina Nightwatch", 7, Rarity.RARE, mage.cards.o.OkinaNightwatch.class));
        cards.add(new SetCardInfo("Plains", 1, Rarity.LAND, mage.cards.basiclands.Plains.class));
        cards.add(new SetCardInfo("Skyknight Legionnaire", 8, Rarity.RARE, mage.cards.s.SkyknightLegionnaire.class));
        cards.add(new SetCardInfo("Swamp", 3, Rarity.LAND, mage.cards.basiclands.Swamp.class));
     }
}
