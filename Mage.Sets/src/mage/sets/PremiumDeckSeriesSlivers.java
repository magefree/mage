
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author fireshoes
 */
public final class PremiumDeckSeriesSlivers extends ExpansionSet {

    private static final PremiumDeckSeriesSlivers instance = new PremiumDeckSeriesSlivers();

    public static PremiumDeckSeriesSlivers getInstance() {
        return instance;
    }

    private PremiumDeckSeriesSlivers() {
        super("Premium Deck Series: Slivers", "H09", ExpansionSet.buildDate(2009, 11, 1), SetType.SUPPLEMENTAL);
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Acidic Sliver", 13, Rarity.UNCOMMON, mage.cards.a.AcidicSliver.class));
        cards.add(new SetCardInfo("Amoeboid Changeling", 3, Rarity.COMMON, mage.cards.a.AmoeboidChangeling.class));
        cards.add(new SetCardInfo("Ancient Ziggurat", 31, Rarity.UNCOMMON, mage.cards.a.AncientZiggurat.class));
        cards.add(new SetCardInfo("Aphetto Dredging", 28, Rarity.COMMON, mage.cards.a.AphettoDredging.class));
        cards.add(new SetCardInfo("Armor Sliver", 16, Rarity.UNCOMMON, mage.cards.a.ArmorSliver.class));
        cards.add(new SetCardInfo("Barbed Sliver", 18, Rarity.UNCOMMON, mage.cards.b.BarbedSliver.class));
        cards.add(new SetCardInfo("Brood Sliver", 22, Rarity.RARE, mage.cards.b.BroodSliver.class));
        cards.add(new SetCardInfo("Clot Sliver", 5, Rarity.COMMON, mage.cards.c.ClotSliver.class));
        cards.add(new SetCardInfo("Coat of Arms", 29, Rarity.RARE, mage.cards.c.CoatOfArms.class));
        cards.add(new SetCardInfo("Crystalline Sliver", 11, Rarity.UNCOMMON, mage.cards.c.CrystallineSliver.class));
        cards.add(new SetCardInfo("Distant Melody", 27, Rarity.COMMON, mage.cards.d.DistantMelody.class));
        cards.add(new SetCardInfo("Forest", 41, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frenzy Sliver", 6, Rarity.COMMON, mage.cards.f.FrenzySliver.class));
        cards.add(new SetCardInfo("Fungus Sliver", 21, Rarity.RARE, mage.cards.f.FungusSliver.class));
        cards.add(new SetCardInfo("Fury Sliver", 25, Rarity.UNCOMMON, mage.cards.f.FurySliver.class));
        cards.add(new SetCardInfo("Gemhide Sliver", 8, Rarity.COMMON, mage.cards.g.GemhideSliver.class));
        cards.add(new SetCardInfo("Heart Sliver", 7, Rarity.COMMON, mage.cards.h.HeartSliver.class));
        cards.add(new SetCardInfo("Heartstone", 26, Rarity.UNCOMMON, mage.cards.h.Heartstone.class));
        cards.add(new SetCardInfo("Hibernation Sliver", 12, Rarity.UNCOMMON, mage.cards.h.HibernationSliver.class));
        cards.add(new SetCardInfo("Homing Sliver", 19, Rarity.COMMON, mage.cards.h.HomingSliver.class));
        cards.add(new SetCardInfo("Island", 38, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Metallic Sliver", 1, Rarity.COMMON, mage.cards.m.MetallicSliver.class));
        cards.add(new SetCardInfo("Might Sliver", 23, Rarity.UNCOMMON, mage.cards.m.MightSliver.class));
        cards.add(new SetCardInfo("Mountain", 40, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Muscle Sliver", 9, Rarity.COMMON, mage.cards.m.MuscleSliver.class));
        cards.add(new SetCardInfo("Necrotic Sliver", 20, Rarity.UNCOMMON, mage.cards.n.NecroticSliver.class));
        cards.add(new SetCardInfo("Plains", 37, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Quick Sliver", 10, Rarity.COMMON, mage.cards.q.QuickSliver.class));
        cards.add(new SetCardInfo("Rootbound Crag", 32, Rarity.RARE, mage.cards.r.RootboundCrag.class));
        cards.add(new SetCardInfo("Rupture Spire", 33, Rarity.COMMON, mage.cards.r.RuptureSpire.class));
        cards.add(new SetCardInfo("Sliver Overlord", 24, Rarity.MYTHIC, mage.cards.s.SliverOverlord.class));
        cards.add(new SetCardInfo("Spectral Sliver", 17, Rarity.UNCOMMON, mage.cards.s.SpectralSliver.class));
        cards.add(new SetCardInfo("Spined Sliver", 14, Rarity.UNCOMMON, mage.cards.s.SpinedSliver.class));
        cards.add(new SetCardInfo("Swamp", 39, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Terramorphic Expanse", 34, Rarity.COMMON, mage.cards.t.TerramorphicExpanse.class));
        cards.add(new SetCardInfo("Victual Sliver", 15, Rarity.UNCOMMON, mage.cards.v.VictualSliver.class));
        cards.add(new SetCardInfo("Virulent Sliver", 2, Rarity.COMMON, mage.cards.v.VirulentSliver.class));
        cards.add(new SetCardInfo("Vivid Creek", 35, Rarity.UNCOMMON, mage.cards.v.VividCreek.class));
        cards.add(new SetCardInfo("Vivid Grove", 36, Rarity.UNCOMMON, mage.cards.v.VividGrove.class));
        cards.add(new SetCardInfo("Wild Pair", 30, Rarity.RARE, mage.cards.w.WildPair.class));
        cards.add(new SetCardInfo("Winged Sliver", 4, Rarity.COMMON, mage.cards.w.WingedSliver.class));
    }
}
