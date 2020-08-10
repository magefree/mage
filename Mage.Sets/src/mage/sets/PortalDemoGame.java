package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/ppod
 */
public class PortalDemoGame extends ExpansionSet {

    private static final PortalDemoGame instance = new PortalDemoGame();

    public static PortalDemoGame getInstance() {
        return instance;
    }

    private PortalDemoGame() {
        super("Portal Demo Game", "PPOD", ExpansionSet.buildDate(1997, 5, 1), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Armored Pegasus", 1, Rarity.COMMON, mage.cards.a.ArmoredPegasus.class));
        cards.add(new SetCardInfo("Bull Hippo", 2, Rarity.UNCOMMON, mage.cards.b.BullHippo.class));
        cards.add(new SetCardInfo("Cloud Pirates", 3, Rarity.COMMON, mage.cards.c.CloudPirates.class));
        cards.add(new SetCardInfo("Feral Shadow", 4, Rarity.COMMON, mage.cards.f.FeralShadow.class));
        cards.add(new SetCardInfo("Snapping Drake", 5, Rarity.COMMON, mage.cards.s.SnappingDrake.class));
        cards.add(new SetCardInfo("Storm Crow", 6, Rarity.COMMON, mage.cards.s.StormCrow.class));
     }
}
