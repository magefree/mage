package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class TherosBeyondDeath extends ExpansionSet {

    private static final TherosBeyondDeath instance = new TherosBeyondDeath();

    public static TherosBeyondDeath getInstance() {
        return instance;
    }

    private TherosBeyondDeath() {
        super("Theros: Beyond Death", "THB", ExpansionSet.buildDate(2020, 1, 24), SetType.EXPANSION);
        this.blockName = "Theros: Beyond Death";
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.maxCardNumberInBooster = 254;

        cards.add(new SetCardInfo("Elspeth, Sun's Nemesis", 14, Rarity.MYTHIC, mage.cards.e.ElspethSunsNemesis.class));
        cards.add(new SetCardInfo("Forest", 254, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 251, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Klothys's Design", 176, Rarity.UNCOMMON, mage.cards.k.KlothyssDesign.class));
        cards.add(new SetCardInfo("Mountain", 253, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 250, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Setessan Champion", 198, Rarity.RARE, mage.cards.s.SetessanChampion.class));
        cards.add(new SetCardInfo("Swamp", 252, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
    }
}
