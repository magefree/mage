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
        super("Reality Fracture", "FRA", ExpansionSet.buildDate(2026, 10, 2), SetType.CUSTOM_SET);
        // super("Reality Fracture", "FRA", ExpansionSet.buildDate(2026, 10, 2), SetType.EXPANSION); TODO: Once mtgjson/Scryfall have the set, change to EXPANSION
        this.blockName = "Reality Fracture"; // for sorting in GUI
        this.hasBasicLands = false; // TODO: Enable once basic collector numbers are known

        // this.enablePlayBooster(305); TODO: Enable later

        cards.add(new SetCardInfo("Stingcaster Mage", 329, Rarity.MYTHIC, mage.cards.s.StingcasterMage.class));
    }
}
