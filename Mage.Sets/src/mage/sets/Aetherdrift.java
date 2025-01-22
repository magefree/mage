package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class Aetherdrift extends ExpansionSet {

    private static final Aetherdrift instance = new Aetherdrift();

    public static Aetherdrift getInstance() {
        return instance;
    }

    private Aetherdrift() {
        super("Aetherdrift", "DFT", ExpansionSet.buildDate(2025, 2, 14), SetType.EXPANSION);
        this.blockName = "Aetherdrift"; // for sorting in GUI
        this.hasBasicLands = false; // temporary
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Air Response Unit", 1, Rarity.UNCOMMON, mage.cards.a.AirResponseUnit.class));
        cards.add(new SetCardInfo("Bleachbone Verge", 250, Rarity.RARE, mage.cards.b.BleachboneVerge.class));
        cards.add(new SetCardInfo("Bloodfell Caves", 251, Rarity.COMMON, mage.cards.b.BloodfellCaves.class));
        cards.add(new SetCardInfo("Bloodghast", 77, Rarity.RARE, mage.cards.b.Bloodghast.class));
        cards.add(new SetCardInfo("Blossoming Sands", 252, Rarity.COMMON, mage.cards.b.BlossomingSands.class));
        cards.add(new SetCardInfo("Boosted Sloop", 190, Rarity.UNCOMMON, mage.cards.b.BoostedSloop.class));
        cards.add(new SetCardInfo("Brightglass Gearhulk", 191, Rarity.MYTHIC, mage.cards.b.BrightglassGearhulk.class));
        cards.add(new SetCardInfo("Daretti, Rocketeer Engineer", 120, Rarity.RARE, mage.cards.d.DarettiRocketeerEngineer.class));
        cards.add(new SetCardInfo("Dismal Backwater", 254, Rarity.COMMON, mage.cards.d.DismalBackwater.class));
        cards.add(new SetCardInfo("Earthrumbler", 160, Rarity.UNCOMMON, mage.cards.e.Earthrumbler.class));
        cards.add(new SetCardInfo("Forest", 289, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hulldrifter", 47, Rarity.COMMON, mage.cards.h.Hulldrifter.class));
        cards.add(new SetCardInfo("Island", 280, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jungle Hollow", 256, Rarity.COMMON, mage.cards.j.JungleHollow.class));
        cards.add(new SetCardInfo("Lagorin, Soul of Alacria", 211, Rarity.UNCOMMON, mage.cards.l.LagorinSoulOfAlacria.class));
        cards.add(new SetCardInfo("Lightshield Parry", 19, Rarity.COMMON, mage.cards.l.LightshieldParry.class));
        cards.add(new SetCardInfo("Mountain", 286, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 272, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Riverpyre Verge", 260, Rarity.RARE, mage.cards.r.RiverpyreVerge.class));
        cards.add(new SetCardInfo("Rugged Highlands", 262, Rarity.COMMON, mage.cards.r.RuggedHighlands.class));
        cards.add(new SetCardInfo("Scoured Barrens", 263, Rarity.COMMON, mage.cards.s.ScouredBarrens.class));
        cards.add(new SetCardInfo("Sunbillow Verge", 264, Rarity.RARE, mage.cards.s.SunbillowVerge.class));
        cards.add(new SetCardInfo("Swamp", 274, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swiftwater Cliffs", 265, Rarity.COMMON, mage.cards.s.SwiftwaterCliffs.class));
        cards.add(new SetCardInfo("Thornwood Falls", 266, Rarity.COMMON, mage.cards.t.ThornwoodFalls.class));
        cards.add(new SetCardInfo("Tranquil Cove", 267, Rarity.COMMON, mage.cards.t.TranquilCove.class));
        cards.add(new SetCardInfo("Wastewood Verge", 268, Rarity.RARE, mage.cards.w.WastewoodVerge.class));
        cards.add(new SetCardInfo("Willowrush Verge", 270, Rarity.RARE, mage.cards.w.WillowrushVerge.class));
        cards.add(new SetCardInfo("Wind-Scarred Crag", 271, Rarity.COMMON, mage.cards.w.WindScarredCrag.class));
    }
}
