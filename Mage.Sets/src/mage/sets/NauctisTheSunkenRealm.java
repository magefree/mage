package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author muz
 */
public final class NauctisTheSunkenRealm extends ExpansionSet {

    private static final NauctisTheSunkenRealm instance = new NauctisTheSunkenRealm();

    public static NauctisTheSunkenRealm getInstance() {
        return instance;
    }

    private NauctisTheSunkenRealm() {
        super("Nauctis: The Sunken Realm", "NAU", ExpansionSet.buildDate(2027, 2, 5), SetType.EXPANSION);
        this.blockName = "Nauctis: The Sunken Realm"; // for sorting in GUI
        this.hasBasicLands = false; // TODO: Once spoiled, add and change

        // this.enablePlayBooster(401); // TODO: Enable once booster details are available

        cards.add(new SetCardInfo("Abyssal Lotus", 234, Rarity.MYTHIC, mage.cards.a.AbyssalLotus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Abyssal Lotus", 316, Rarity.MYTHIC, mage.cards.a.AbyssalLotus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Abyssal Lotus", 361, Rarity.MYTHIC, mage.cards.a.AbyssalLotus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Calmwater Mentor", 281, Rarity.MYTHIC, mage.cards.c.CalmwaterMentor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Calmwater Mentor", 5, Rarity.MYTHIC, mage.cards.c.CalmwaterMentor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ring of Unity", 238, Rarity.MYTHIC, mage.cards.r.RingOfUnity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ring of Unity", 317, Rarity.MYTHIC, mage.cards.r.RingOfUnity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ring of Unity", 360, Rarity.MYTHIC, mage.cards.r.RingOfUnity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ring of Unity", 376, Rarity.MYTHIC, mage.cards.r.RingOfUnity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Salinae, the Ocean Mother", 321, Rarity.MYTHIC, mage.cards.s.SalinaeTheOceanMother.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Salinae, the Ocean Mother", 353, Rarity.MYTHIC, mage.cards.s.SalinaeTheOceanMother.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Salinae, the Ocean Mother", 369, Rarity.MYTHIC, mage.cards.s.SalinaeTheOceanMother.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Salinae, the Ocean Mother", 409, Rarity.MYTHIC, mage.cards.s.SalinaeTheOceanMother.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Salinae, the Ocean Mother", 70, Rarity.MYTHIC, mage.cards.s.SalinaeTheOceanMother.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shimmering Citadel", 260, Rarity.RARE, mage.cards.s.ShimmeringCitadel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shimmering Citadel", 346, Rarity.RARE, mage.cards.s.ShimmeringCitadel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tecton, Malignant Ruin", 121, Rarity.MYTHIC, mage.cards.t.TectonMalignantRuin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tecton, Malignant Ruin", 341, Rarity.MYTHIC, mage.cards.t.TectonMalignantRuin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tecton, Malignant Ruin", 354, Rarity.MYTHIC, mage.cards.t.TectonMalignantRuin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tecton, Malignant Ruin", 370, Rarity.MYTHIC, mage.cards.t.TectonMalignantRuin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tecton, Malignant Ruin", 410, Rarity.MYTHIC, mage.cards.t.TectonMalignantRuin.class, NON_FULL_USE_VARIOUS));
    }
}
