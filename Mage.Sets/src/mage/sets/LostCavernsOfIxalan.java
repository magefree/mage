
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author Susucr
 */
public final class LostCavernsOfIxalan extends ExpansionSet {

    private static final LostCavernsOfIxalan instance = new LostCavernsOfIxalan();

    public static LostCavernsOfIxalan getInstance() {
        return instance;
    }

    private LostCavernsOfIxalan() {
        super("Lost Caverns of Ixalan", "LCI", ExpansionSet.buildDate(2023, 11, 17), SetType.EXPANSION);
        this.hasBoosters = false; // TODO: enable boosters
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Forest", 291, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 288, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 290, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 287, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 289, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
    }
}
