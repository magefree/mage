package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.Arrays;
import java.util.List;

/**
 * @author TheElk801
 */
public final class Aetherdrift extends ExpansionSet {

    private static final List<String> unfinished = Arrays.asList("Aether Syphon", "Amonkhet Raceway", "Avishkar Raceway", "Endrider Catalyzer", "Far Fortune, End Boss", "Goblin Surveyor", "Hazoret, Godseeker", "Hour of Victory", "Mendicant Core, Guidelight", "Momentum Breaker", "Muraganda Raceway", "Nesting Bot", "Perilous Snare", "Samut, the Driving Force", "Starting Column", "Streaking Oilgorger", "Vnwxt, Verbose Host", "Zahur, Glory's Past");
    private static final Aetherdrift instance = new Aetherdrift();

    public static Aetherdrift getInstance() {
        return instance;
    }

    private Aetherdrift() {
        super("Aetherdrift", "DFT", ExpansionSet.buildDate(2025, 2, 14), SetType.EXPANSION);
        this.blockName = "Aetherdrift"; // for sorting in GUI
        this.hasBasicLands = true;
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Aether Syphon", 38, Rarity.UNCOMMON, mage.cards.a.AetherSyphon.class));
        cards.add(new SetCardInfo("Air Response Unit", 1, Rarity.UNCOMMON, mage.cards.a.AirResponseUnit.class));
        cards.add(new SetCardInfo("Amonkhet Raceway", 248, Rarity.UNCOMMON, mage.cards.a.AmonkhetRaceway.class));
        cards.add(new SetCardInfo("Apocalypse Runner", 188, Rarity.UNCOMMON, mage.cards.a.ApocalypseRunner.class));
        cards.add(new SetCardInfo("Avishkar Raceway", 249, Rarity.COMMON, mage.cards.a.AvishkarRaceway.class));
        cards.add(new SetCardInfo("Bleachbone Verge", 250, Rarity.RARE, mage.cards.b.BleachboneVerge.class));
        cards.add(new SetCardInfo("Bloodfell Caves", 251, Rarity.COMMON, mage.cards.b.BloodfellCaves.class));
        cards.add(new SetCardInfo("Bloodghast", 77, Rarity.RARE, mage.cards.b.Bloodghast.class));
        cards.add(new SetCardInfo("Blossoming Sands", 252, Rarity.COMMON, mage.cards.b.BlossomingSands.class));
        cards.add(new SetCardInfo("Boom Scholar", 189, Rarity.UNCOMMON, mage.cards.b.BoomScholar.class));
        cards.add(new SetCardInfo("Boosted Sloop", 190, Rarity.UNCOMMON, mage.cards.b.BoostedSloop.class));
        cards.add(new SetCardInfo("Brightfield Glider", 4, Rarity.COMMON, mage.cards.b.BrightfieldGlider.class));
        cards.add(new SetCardInfo("Brightfield Mustang", 5, Rarity.COMMON, mage.cards.b.BrightfieldMustang.class));
        cards.add(new SetCardInfo("Brightglass Gearhulk", 191, Rarity.MYTHIC, mage.cards.b.BrightglassGearhulk.class));
        cards.add(new SetCardInfo("Count on Luck", 118, Rarity.RARE, mage.cards.c.CountOnLuck.class));
        cards.add(new SetCardInfo("Daretti, Rocketeer Engineer", 120, Rarity.RARE, mage.cards.d.DarettiRocketeerEngineer.class));
        cards.add(new SetCardInfo("Dismal Backwater", 254, Rarity.COMMON, mage.cards.d.DismalBackwater.class));
        cards.add(new SetCardInfo("Earthrumbler", 160, Rarity.UNCOMMON, mage.cards.e.Earthrumbler.class));
        cards.add(new SetCardInfo("Endrider Catalyzer", 124, Rarity.COMMON, mage.cards.e.EndriderCatalyzer.class));
        cards.add(new SetCardInfo("Forest", 289, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hulldrifter", 47, Rarity.COMMON, mage.cards.h.Hulldrifter.class));
        cards.add(new SetCardInfo("Island", 280, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jungle Hollow", 256, Rarity.COMMON, mage.cards.j.JungleHollow.class));
        cards.add(new SetCardInfo("Lagorin, Soul of Alacria", 211, Rarity.UNCOMMON, mage.cards.l.LagorinSoulOfAlacria.class));
        cards.add(new SetCardInfo("Lightshield Parry", 19, Rarity.COMMON, mage.cards.l.LightshieldParry.class));
        cards.add(new SetCardInfo("Marketback Walker", 235, Rarity.RARE, mage.cards.m.MarketbackWalker.class));
        cards.add(new SetCardInfo("Marshals' Pathcruiser", 236, Rarity.UNCOMMON, mage.cards.m.MarshalsPathcruiser.class));
        cards.add(new SetCardInfo("Midnight Mangler", 50, Rarity.COMMON, mage.cards.m.MidnightMangler.class));
        cards.add(new SetCardInfo("Mindspring Merfolk", 51, Rarity.RARE, mage.cards.m.MindspringMerfolk.class));
        cards.add(new SetCardInfo("Molt Tender", 171, Rarity.UNCOMMON, mage.cards.m.MoltTender.class));
        cards.add(new SetCardInfo("Mountain", 286, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nesting Bot", 22, Rarity.UNCOMMON, mage.cards.n.NestingBot.class));
        cards.add(new SetCardInfo("Night Market", 258, Rarity.COMMON, mage.cards.n.NightMarket.class));
        cards.add(new SetCardInfo("Plains", 272, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prowcatcher Specialist", 142, Rarity.COMMON, mage.cards.p.ProwcatcherSpecialist.class));
        cards.add(new SetCardInfo("Pyrewood Gearhulk", 216, Rarity.MYTHIC, mage.cards.p.PyrewoodGearhulk.class));
        cards.add(new SetCardInfo("Riverpyre Verge", 260, Rarity.RARE, mage.cards.r.RiverpyreVerge.class));
        cards.add(new SetCardInfo("Rocketeer Boostbuggy", 220, Rarity.UNCOMMON, mage.cards.r.RocketeerBoostbuggy.class));
        cards.add(new SetCardInfo("Rugged Highlands", 262, Rarity.COMMON, mage.cards.r.RuggedHighlands.class));
        cards.add(new SetCardInfo("Scoured Barrens", 263, Rarity.COMMON, mage.cards.s.ScouredBarrens.class));
        cards.add(new SetCardInfo("Stock Up", 67, Rarity.UNCOMMON, mage.cards.s.StockUp.class));
        cards.add(new SetCardInfo("Streaking Oilgorger", 107, Rarity.COMMON, mage.cards.s.StreakingOilgorger.class));
        cards.add(new SetCardInfo("Sunbillow Verge", 264, Rarity.RARE, mage.cards.s.SunbillowVerge.class));
        cards.add(new SetCardInfo("Swamp", 274, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swiftwater Cliffs", 265, Rarity.COMMON, mage.cards.s.SwiftwaterCliffs.class));
        cards.add(new SetCardInfo("The Last Ride", 94, Rarity.MYTHIC, mage.cards.t.TheLastRide.class));
        cards.add(new SetCardInfo("Thopter Fabricator", 68, Rarity.RARE, mage.cards.t.ThopterFabricator.class));
        cards.add(new SetCardInfo("Thornwood Falls", 266, Rarity.COMMON, mage.cards.t.ThornwoodFalls.class));
        cards.add(new SetCardInfo("Tranquil Cove", 267, Rarity.COMMON, mage.cards.t.TranquilCove.class));
        cards.add(new SetCardInfo("Venomsac Lagac", 185, Rarity.COMMON, mage.cards.v.VenomsacLagac.class));
        cards.add(new SetCardInfo("Wastewood Verge", 268, Rarity.RARE, mage.cards.w.WastewoodVerge.class));
        cards.add(new SetCardInfo("Willowrush Verge", 270, Rarity.RARE, mage.cards.w.WillowrushVerge.class));
        cards.add(new SetCardInfo("Wind-Scarred Crag", 271, Rarity.COMMON, mage.cards.w.WindScarredCrag.class));

        cards.removeIf(setCardInfo -> unfinished.contains(setCardInfo.getName()));
    }
}
