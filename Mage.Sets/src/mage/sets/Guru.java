package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

public final class Guru extends ExpansionSet {

    private static final Guru instance = new Guru();

    public static Guru getInstance() {
        return instance;
    }

    private Guru() {
        super("Guru", "GUR", ExpansionSet.buildDate(1990, 1, 2), SetType.PROMOTIONAL);
        cards.add(new SetCardInfo("Forest", 1, Rarity.LAND, mage.cards.basiclands.Forest.class));
        cards.add(new SetCardInfo("Island", 2, Rarity.LAND, mage.cards.basiclands.Island.class));
        cards.add(new SetCardInfo("Mountain", 3, Rarity.LAND, mage.cards.basiclands.Mountain.class));
        cards.add(new SetCardInfo("Plains", 4, Rarity.LAND, mage.cards.basiclands.Plains.class));
        cards.add(new SetCardInfo("Swamp", 5, Rarity.LAND, mage.cards.basiclands.Swamp.class));
    }
}
