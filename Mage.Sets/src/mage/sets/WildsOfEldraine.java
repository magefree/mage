package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class WildsOfEldraine extends ExpansionSet {

    private static final WildsOfEldraine instance = new WildsOfEldraine();

    public static WildsOfEldraine getInstance() {
        return instance;
    }

    private WildsOfEldraine() {
        super("Wilds of Eldraine", "WOE", ExpansionSet.buildDate(2023, 9, 8), SetType.EXPANSION);
        this.blockName = "Wilds of Eldraine";
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Cruel Somnophage", 222, Rarity.RARE, mage.cards.c.CruelSomnophage.class));
        cards.add(new SetCardInfo("Forest", 266, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 263, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 265, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 262, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sleight of Hand", 67, Rarity.COMMON, mage.cards.s.SleightOfHand.class));
        cards.add(new SetCardInfo("Swamp", 264, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
    }

//    @Override
//    public BoosterCollator createCollator() {
//        return new WildsOfEldraineCollator();
//    }
}
