package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pal99
 */
public class ArenaLeague1999 extends ExpansionSet {

    private static final ArenaLeague1999 instance = new ArenaLeague1999();

    public static ArenaLeague1999 getInstance() {
        return instance;
    }

    private ArenaLeague1999() {
        super("Arena League 1999", "PAL99", ExpansionSet.buildDate(1999, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Forest", 1, Rarity.LAND, mage.cards.basiclands.Forest.class));
        cards.add(new SetCardInfo("Island", 3, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "3+", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Karn, Silver Golem", 8, Rarity.RARE, mage.cards.k.KarnSilverGolem.class));
        cards.add(new SetCardInfo("Mountain", 5, Rarity.LAND, mage.cards.basiclands.Mountain.class));
        cards.add(new SetCardInfo("Plains", 7, Rarity.LAND, mage.cards.basiclands.Plains.class));
        cards.add(new SetCardInfo("Pouncing Jaguar", 2, Rarity.RARE, mage.cards.p.PouncingJaguar.class));
        cards.add(new SetCardInfo("Rewind", 6, Rarity.RARE, mage.cards.r.Rewind.class));
        cards.add(new SetCardInfo("Skittering Skirge", 4, Rarity.RARE, mage.cards.s.SkitteringSkirge.class));
        cards.add(new SetCardInfo("Swamp", 9, Rarity.LAND, mage.cards.basiclands.Swamp.class));
     }
}
