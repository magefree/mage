package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/parl
 */
public class ArenaLeague1996 extends ExpansionSet {

    private static final ArenaLeague1996 instance = new ArenaLeague1996();

    public static ArenaLeague1996 getInstance() {
        return instance;
    }

    private ArenaLeague1996() {
        super("Arena League 1996", "PARL", ExpansionSet.buildDate(1996, 8, 2), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Disenchant", 6, Rarity.RARE, mage.cards.d.Disenchant.class));
        cards.add(new SetCardInfo("Fireball", 7, Rarity.RARE, mage.cards.f.Fireball.class));
        cards.add(new SetCardInfo("Forest", 5, Rarity.LAND, mage.cards.basiclands.Forest.class));
        cards.add(new SetCardInfo("Island", 2, Rarity.LAND, mage.cards.basiclands.Island.class));
        cards.add(new SetCardInfo("Mountain", 4, Rarity.LAND, mage.cards.basiclands.Mountain.class));
        cards.add(new SetCardInfo("Plains", 1, Rarity.LAND, mage.cards.basiclands.Plains.class));
        cards.add(new SetCardInfo("Swamp", 3, Rarity.LAND, mage.cards.basiclands.Swamp.class));
     }
}
