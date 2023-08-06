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

        cards.add(new SetCardInfo("City of Death", 99, Rarity.RARE, mage.cards.c.CityOfDeath.class));
        cards.add(new SetCardInfo("Exterminate!", 68, Rarity.UNCOMMON, mage.cards.e.Exterminate.class));
        cards.add(new SetCardInfo("Farewell", 207, Rarity.RARE, mage.cards.f.Farewell.class));
        cards.add(new SetCardInfo("Forest", 205, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Four Knocks", 20, Rarity.RARE, mage.cards.f.FourKnocks.class));
        cards.add(new SetCardInfo("Island", 199, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 203, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 197, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sarah Jane Smith", 6, Rarity.RARE, mage.cards.s.SarahJaneSmith.class));
        cards.add(new SetCardInfo("Swamp", 201, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Eleventh Hour", 41, Rarity.RARE, mage.cards.t.TheEleventhHour.class));
        cards.add(new SetCardInfo("The Flux", 86, Rarity.RARE, mage.cards.t.TheFlux.class));
        cards.add(new SetCardInfo("The Thirteenth Doctor", 4, Rarity.MYTHIC, mage.cards.t.TheThirteenthDoctor.class));
        cards.add(new SetCardInfo("Yasmin Khan", 7, Rarity.RARE, mage.cards.y.YasminKhan.class));
    }
}
