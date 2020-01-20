package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pal04
 */
public class ArenaLeague2004 extends ExpansionSet {

    private static final ArenaLeague2004 instance = new ArenaLeague2004();

    public static ArenaLeague2004 getInstance() {
        return instance;
    }

    private ArenaLeague2004() {
        super("Arena League 2004", "PAL04", ExpansionSet.buildDate(2004, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        // Not implemented
        //cards.add(new SetCardInfo("Ashnod's Coupon", 14, Rarity.RARE, mage.cards.a.AshnodsCoupon.class));
        cards.add(new SetCardInfo("Booster Tutor", 11, Rarity.RARE, mage.cards.b.BoosterTutor.class));
        // Not implemented
        //cards.add(new SetCardInfo("Circle of Protection: Art", 9, Rarity.RARE, mage.cards.c.CircleOfProtectionArt.class));
        cards.add(new SetCardInfo("Darksteel Ingot", 6, Rarity.RARE, mage.cards.d.DarksteelIngot.class));
        cards.add(new SetCardInfo("Forest", 5, Rarity.LAND, mage.cards.basiclands.Forest.class));
        cards.add(new SetCardInfo("Glacial Ray", 8, Rarity.RARE, mage.cards.g.GlacialRay.class));
        // Not implemented
        // cards.add(new SetCardInfo("Goblin Mime", 12, Rarity.RARE, mage.cards.g.GoblinMime.class));
        // cards.add(new SetCardInfo("Granny's Payback", 13, Rarity.RARE, mage.cards.g.GrannysPayback.class));
        cards.add(new SetCardInfo("Island", 2, Rarity.LAND, mage.cards.basiclands.Island.class));
        cards.add(new SetCardInfo("Mise", 10, Rarity.RARE, mage.cards.m.Mise.class));
        cards.add(new SetCardInfo("Mountain", 4, Rarity.LAND, mage.cards.basiclands.Mountain.class));
        cards.add(new SetCardInfo("Plains", 1, Rarity.LAND, mage.cards.basiclands.Plains.class));
        cards.add(new SetCardInfo("Serum Visions", 7, Rarity.RARE, mage.cards.s.SerumVisions.class));
        cards.add(new SetCardInfo("Swamp", 3, Rarity.LAND, mage.cards.basiclands.Swamp.class));
     }
}
