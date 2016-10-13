package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

import java.util.GregorianCalendar;
import mage.constants.Rarity;
import java.util.List;

public class Guru extends ExpansionSet {
    private static final Guru fINSTANCE = new Guru();

    public static Guru getInstance() {
        return fINSTANCE;
    }

    private Guru() {
        super("Guru", "GUR", "mage.sets.guru", ExpansionSet.buildDate(1990, 1, 2), SetType.PROMOTIONAL);
        cards.add(new SetCardInfo("Forest", 1, Rarity.LAND, mage.cards.basiclands.Forest.class));
        cards.add(new SetCardInfo("Island", 2, Rarity.LAND, mage.cards.basiclands.Island.class));
        cards.add(new SetCardInfo("Mountain", 3, Rarity.LAND, mage.cards.basiclands.Mountain.class));
        cards.add(new SetCardInfo("Plains", 4, Rarity.LAND, mage.cards.basiclands.Plains.class));
        cards.add(new SetCardInfo("Swamp", 5, Rarity.LAND, mage.cards.basiclands.Swamp.class));
    }
}
