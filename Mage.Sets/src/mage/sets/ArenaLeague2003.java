package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pal03
 */
public class ArenaLeague2003 extends ExpansionSet {

    private static final ArenaLeague2003 instance = new ArenaLeague2003();

    public static ArenaLeague2003 getInstance() {
        return instance;
    }

    private ArenaLeague2003() {
        super("Arena League 2003", "PAL03", ExpansionSet.buildDate(2003, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Bonesplitter", 8, Rarity.RARE, mage.cards.b.Bonesplitter.class));
        cards.add(new SetCardInfo("Elvish Aberration", 7, Rarity.RARE, mage.cards.e.ElvishAberration.class));
        cards.add(new SetCardInfo("Forest", 5, Rarity.LAND, mage.cards.basiclands.Forest.class));
        cards.add(new SetCardInfo("Island", 2, Rarity.LAND, mage.cards.basiclands.Island.class));
        cards.add(new SetCardInfo("Mountain", 4, Rarity.LAND, mage.cards.basiclands.Mountain.class));
        cards.add(new SetCardInfo("Plains", 1, Rarity.LAND, mage.cards.basiclands.Plains.class));
        cards.add(new SetCardInfo("Skirk Marauder", 6, Rarity.RARE, mage.cards.s.SkirkMarauder.class));
        cards.add(new SetCardInfo("Swamp", 3, Rarity.LAND, mage.cards.basiclands.Swamp.class));
     }
}
