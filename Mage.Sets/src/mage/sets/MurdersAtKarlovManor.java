package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class MurdersAtKarlovManor extends ExpansionSet {

    private static final MurdersAtKarlovManor instance = new MurdersAtKarlovManor();

    public static MurdersAtKarlovManor getInstance() {
        return instance;
    }

    private MurdersAtKarlovManor() {
        super("Murders at Karlov Manor", "MKM", ExpansionSet.buildDate(2024, 2, 9), SetType.EXPANSION);
        this.blockName = "Murders at Karlov Manor"; // for sorting in GUI
        this.hasBasicLands = true;
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Benthic Criminologists", 40, Rarity.COMMON, mage.cards.b.BenthicCriminologists.class));
        cards.add(new SetCardInfo("Deduce", 52, Rarity.COMMON, mage.cards.d.Deduce.class));
        cards.add(new SetCardInfo("Fanatical Strength", 159, Rarity.COMMON, mage.cards.f.FanaticalStrength.class));
        cards.add(new SetCardInfo("Forest", 276, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Gleaming Geardrake", 205, Rarity.UNCOMMON, mage.cards.g.GleamingGeardrake.class));
        cards.add(new SetCardInfo("Island", 273, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Lightning Helix", 218, Rarity.UNCOMMON, mage.cards.l.LightningHelix.class));
        cards.add(new SetCardInfo("Magnifying Glass", 255, Rarity.COMMON, mage.cards.m.MagnifyingGlass.class));
        cards.add(new SetCardInfo("Mountain", 275, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Not on My Watch", 28, Rarity.COMMON, mage.cards.n.NotOnMyWatch.class));
        cards.add(new SetCardInfo("Novice Inspector", 29, Rarity.COMMON, mage.cards.n.NoviceInspector.class));
        cards.add(new SetCardInfo("Plains", 272, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Shock", 144, Rarity.COMMON, mage.cards.s.Shock.class));
        cards.add(new SetCardInfo("Swamp", 274, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Topiary Panther", 179, Rarity.COMMON, mage.cards.t.TopiaryPanther.class));
        cards.add(new SetCardInfo("Wojek Investigator", 36, Rarity.RARE, mage.cards.w.WojekInvestigator.class));
    }
}
