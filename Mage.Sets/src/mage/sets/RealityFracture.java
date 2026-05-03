package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author muz
 */
public final class RealityFracture extends ExpansionSet {

    private static final RealityFracture instance = new RealityFracture();

    public static RealityFracture getInstance() {
        return instance;
    }

    private RealityFracture() {
        super("Reality Fracture", "FRA", ExpansionSet.buildDate(2026, 10, 2), SetType.EXPANSION);
        this.blockName = "Reality Fracture"; // for sorting in GUI
        this.hasBasicLands = false; // TODO: Enable once basic collector numbers are known

        // this.enablePlayBooster(305); TODO: Enable later

        cards.add(new SetCardInfo("Chandra, Torch of Defiance", 244, Rarity.MYTHIC, mage.cards.c.ChandraTorchOfDefiance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stingcaster Mage", 329, Rarity.MYTHIC, mage.cards.s.StingcasterMage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stingcaster Mage", 457, Rarity.MYTHIC, mage.cards.s.StingcasterMage.class, NON_FULL_USE_VARIOUS));
    }
}
