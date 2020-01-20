package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pal06
 */
public class ArenaLeague2006 extends ExpansionSet {

    private static final ArenaLeague2006 instance = new ArenaLeague2006();

    public static ArenaLeague2006 getInstance() {
        return instance;
    }

    private ArenaLeague2006() {
        super("Arena League 2006", "PAL06", ExpansionSet.buildDate(2006, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Castigate", 6, Rarity.RARE, mage.cards.c.Castigate.class));
        cards.add(new SetCardInfo("Coiling Oracle", 8, Rarity.RARE, mage.cards.c.CoilingOracle.class));
        cards.add(new SetCardInfo("Forest", 5, Rarity.LAND, mage.cards.basiclands.Forest.class));
        cards.add(new SetCardInfo("Island", 2, Rarity.LAND, mage.cards.basiclands.Island.class));
        cards.add(new SetCardInfo("Mountain", 4, Rarity.LAND, mage.cards.basiclands.Mountain.class));
        cards.add(new SetCardInfo("Plains", 1, Rarity.LAND, mage.cards.basiclands.Plains.class));
        cards.add(new SetCardInfo("Surging Flame", 9, Rarity.RARE, mage.cards.s.SurgingFlame.class));
        cards.add(new SetCardInfo("Swamp", 3, Rarity.LAND, mage.cards.basiclands.Swamp.class));
        cards.add(new SetCardInfo("Wee Dragonauts", 7, Rarity.RARE, mage.cards.w.WeeDragonauts.class));
     }
}
