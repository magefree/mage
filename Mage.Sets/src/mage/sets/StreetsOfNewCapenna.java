package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class StreetsOfNewCapenna extends ExpansionSet {

    private static final StreetsOfNewCapenna instance = new StreetsOfNewCapenna();

    public static StreetsOfNewCapenna getInstance() {
        return instance;
    }

    private StreetsOfNewCapenna() {
        super("Streets of New Capenna", "SNC", ExpansionSet.buildDate(2022, 4, 29), SetType.EXPANSION);
        this.blockName = "Streets of New Capenna";
        this.hasBoosters = true;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Forest", 280, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 274, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 278, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 272, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 276, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
    }
}
