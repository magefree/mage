package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pf20
 */
public class MagicFest2020 extends ExpansionSet {

    private static final MagicFest2020 instance = new MagicFest2020();

    public static MagicFest2020 getInstance() {
        return instance;
    }

    private MagicFest2020() {
        super("MagicFest 2020", "PF20", ExpansionSet.buildDate(2020, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Forest", 6, Rarity.LAND, mage.cards.basiclands.Forest.class));
        cards.add(new SetCardInfo("Island", 3, Rarity.LAND, mage.cards.basiclands.Island.class));
        cards.add(new SetCardInfo("Mountain", 5, Rarity.LAND, mage.cards.basiclands.Mountain.class));
        cards.add(new SetCardInfo("Path to Exile", 1, Rarity.RARE, mage.cards.p.PathToExile.class));
        cards.add(new SetCardInfo("Plains", 2, Rarity.LAND, mage.cards.basiclands.Plains.class));
        cards.add(new SetCardInfo("Swamp", 4, Rarity.LAND, mage.cards.basiclands.Swamp.class));
     }
}
