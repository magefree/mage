package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pss4
 */
public class MKMStandardShowdown extends ExpansionSet {

    private static final MKMStandardShowdown instance = new MKMStandardShowdown();

    public static MKMStandardShowdown getInstance() {
        return instance;
    }

    private MKMStandardShowdown() {
        super("MKM Standard Showdown", "PSS4", ExpansionSet.buildDate(2024, 2, 10), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Forest", 5, Rarity.LAND, mage.cards.basiclands.Forest.class));
        cards.add(new SetCardInfo("Island", 2, Rarity.LAND, mage.cards.basiclands.Island.class));
        cards.add(new SetCardInfo("Mountain", 4, Rarity.LAND, mage.cards.basiclands.Mountain.class));
        cards.add(new SetCardInfo("Plains", 1, Rarity.LAND, mage.cards.basiclands.Plains.class));
        cards.add(new SetCardInfo("Swamp", 3, Rarity.LAND, mage.cards.basiclands.Swamp.class));
    }
}
