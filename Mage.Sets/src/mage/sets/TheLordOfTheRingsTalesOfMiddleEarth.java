package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

public final class TheLordOfTheRingsTalesOfMiddleEarth extends ExpansionSet {

    private static final TheLordOfTheRingsTalesOfMiddleEarth instance = new TheLordOfTheRingsTalesOfMiddleEarth();

    public static TheLordOfTheRingsTalesOfMiddleEarth getInstance() {
        return instance;
    }

    private TheLordOfTheRingsTalesOfMiddleEarth() {
        super("The Lord of the Rings: Tales of Middle-earth", "LTR", ExpansionSet.buildDate(2023, 6, 23), SetType.SUPPLEMENTAL_MODERN_LEGAL);
        this.blockName = "The Lord of the Rings: Tales of Middle-earth";
        this.hasBasicLands = true;
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Forest", 280, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 274, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 278, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 272, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 276, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Trailblazer's Boots", 398, Rarity.RARE, mage.cards.t.TrailblazersBoots.class));
    }
}
