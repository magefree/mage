package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class DoctorWho extends ExpansionSet {

    private static final DoctorWho instance = new DoctorWho();

    public static DoctorWho getInstance() {
        return instance;
    }

    private DoctorWho() {
        super("Doctor Who", "WHO", ExpansionSet.buildDate(2023, 10, 13), SetType.SUPPLEMENTAL);

        cards.add(new SetCardInfo("Exterminate!", 68, Rarity.UNCOMMON, mage.cards.e.Exterminate.class));
        cards.add(new SetCardInfo("Forest", 205, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 199, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 203, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 197, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 201, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
    }
}
