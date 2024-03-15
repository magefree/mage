package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class OutlawsOfThunderJunction extends ExpansionSet {

    private static final OutlawsOfThunderJunction instance = new OutlawsOfThunderJunction();

    public static OutlawsOfThunderJunction getInstance() {
        return instance;
    }

    private OutlawsOfThunderJunction() {
        super("Outlaws of Thunder Junction", "OTJ", ExpansionSet.buildDate(2024, 4, 19), SetType.EXPANSION);
        this.blockName = "Outlaws of Thunder Junction"; // for sorting in GUI
        this.hasBasicLands = true;
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Forest", 276, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hell to Pay", 126, Rarity.RARE, mage.cards.h.HellToPay.class));
        cards.add(new SetCardInfo("Island", 273, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 275, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 272, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 274, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
    }
}
