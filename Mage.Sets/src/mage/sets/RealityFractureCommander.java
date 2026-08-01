package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author muz
 */
public final class RealityFractureCommander extends ExpansionSet {

    private static final RealityFractureCommander instance = new RealityFractureCommander();

    public static RealityFractureCommander getInstance() {
        return instance;
    }

    private RealityFractureCommander() {
        // TODO: Pending MtgJSON updating
        super("Reality Fracture Commander", "FRC", ExpansionSet.buildDate(2026, 10, 2), SetType.CUSTOM_SET);
        // super("Reality Fracture Commander", "FRC", ExpansionSet.buildDate(2026, 10, 2), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Darksteel Angel", 98, Rarity.RARE, mage.cards.d.DarksteelAngel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Darksteel Angel", 13, Rarity.RARE, mage.cards.d.DarksteelAngel.class, NON_FULL_USE_VARIOUS));
    }
}
