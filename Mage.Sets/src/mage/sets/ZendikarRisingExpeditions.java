package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class ZendikarRisingExpeditions extends ExpansionSet {

    private static final ZendikarRisingExpeditions instance = new ZendikarRisingExpeditions();

    public static ZendikarRisingExpeditions getInstance() {
        return instance;
    }

    private ZendikarRisingExpeditions() {
        super("Zendikar Rising Expeditions", "ZNE", ExpansionSet.buildDate(2020, 9, 25), SetType.PROMOTIONAL);
        this.blockName = "Masterpiece Series";
        this.hasBoosters = false; // no boosters, it's a box toppers
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Ancient Tomb", 21, Rarity.MYTHIC, mage.cards.a.AncientTomb.class));
        cards.add(new SetCardInfo("Arid Mesa", 9, Rarity.MYTHIC, mage.cards.a.AridMesa.class));
        cards.add(new SetCardInfo("Blackcleave Cliffs", 13, Rarity.MYTHIC, mage.cards.b.BlackcleaveCliffs.class));
        cards.add(new SetCardInfo("Bloodstained Mire", 3, Rarity.MYTHIC, mage.cards.b.BloodstainedMire.class));
        cards.add(new SetCardInfo("Bountiful Promenade", 20, Rarity.MYTHIC, mage.cards.b.BountifulPromenade.class));
        cards.add(new SetCardInfo("Cavern of Souls", 22, Rarity.MYTHIC, mage.cards.c.CavernOfSouls.class));
        cards.add(new SetCardInfo("Celestial Colonnade", 23, Rarity.MYTHIC, mage.cards.c.CelestialColonnade.class));
        cards.add(new SetCardInfo("Copperline Gorge", 14, Rarity.MYTHIC, mage.cards.c.CopperlineGorge.class));
        cards.add(new SetCardInfo("Creeping Tar Pit", 24, Rarity.MYTHIC, mage.cards.c.CreepingTarPit.class));
        cards.add(new SetCardInfo("Darkslick Shores", 12, Rarity.MYTHIC, mage.cards.d.DarkslickShores.class));
        cards.add(new SetCardInfo("Flooded Strand", 1, Rarity.MYTHIC, mage.cards.f.FloodedStrand.class));
        cards.add(new SetCardInfo("Grove of the Burnwillows", 25, Rarity.MYTHIC, mage.cards.g.GroveOfTheBurnwillows.class));
        cards.add(new SetCardInfo("Horizon Canopy", 26, Rarity.MYTHIC, mage.cards.h.HorizonCanopy.class));
        cards.add(new SetCardInfo("Luxury Suite", 18, Rarity.MYTHIC, mage.cards.l.LuxurySuite.class));
        cards.add(new SetCardInfo("Marsh Flats", 6, Rarity.MYTHIC, mage.cards.m.MarshFlats.class));
        cards.add(new SetCardInfo("Misty Rainforest", 10, Rarity.MYTHIC, mage.cards.m.MistyRainforest.class));
        cards.add(new SetCardInfo("Morphic Pool", 17, Rarity.MYTHIC, mage.cards.m.MorphicPool.class));
        cards.add(new SetCardInfo("Polluted Delta", 2, Rarity.MYTHIC, mage.cards.p.PollutedDelta.class));
        cards.add(new SetCardInfo("Prismatic Vista", 27, Rarity.MYTHIC, mage.cards.p.PrismaticVista.class));
        cards.add(new SetCardInfo("Razorverge Thicket", 15, Rarity.MYTHIC, mage.cards.r.RazorvergeThicket.class));
        cards.add(new SetCardInfo("Scalding Tarn", 7, Rarity.MYTHIC, mage.cards.s.ScaldingTarn.class));
        cards.add(new SetCardInfo("Sea of Clouds", 16, Rarity.MYTHIC, mage.cards.s.SeaOfClouds.class));
        cards.add(new SetCardInfo("Seachrome Coast", 11, Rarity.MYTHIC, mage.cards.s.SeachromeCoast.class));
        cards.add(new SetCardInfo("Spire Garden", 19, Rarity.MYTHIC, mage.cards.s.SpireGarden.class));
        cards.add(new SetCardInfo("Strip Mine", 28, Rarity.MYTHIC, mage.cards.s.StripMine.class));
        cards.add(new SetCardInfo("Valakut, the Molten Pinnacle", 29, Rarity.MYTHIC, mage.cards.v.ValakutTheMoltenPinnacle.class));
        cards.add(new SetCardInfo("Verdant Catacombs", 8, Rarity.MYTHIC, mage.cards.v.VerdantCatacombs.class));
        cards.add(new SetCardInfo("Wasteland", 30, Rarity.MYTHIC, mage.cards.w.Wasteland.class));
        cards.add(new SetCardInfo("Windswept Heath", 5, Rarity.MYTHIC, mage.cards.w.WindsweptHeath.class));
        cards.add(new SetCardInfo("Wooded Foothills", 4, Rarity.MYTHIC, mage.cards.w.WoodedFoothills.class));
    }
}
