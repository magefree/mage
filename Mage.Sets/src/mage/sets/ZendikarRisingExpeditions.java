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
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Ancient Tomb", 21, Rarity.MYTHIC, mage.cards.a.AncientTomb.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Arid Mesa", 9, Rarity.MYTHIC, mage.cards.a.AridMesa.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Blackcleave Cliffs", 13, Rarity.MYTHIC, mage.cards.b.BlackcleaveCliffs.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Bloodstained Mire", 3, Rarity.MYTHIC, mage.cards.b.BloodstainedMire.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Bountiful Promenade", 20, Rarity.MYTHIC, mage.cards.b.BountifulPromenade.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Cavern of Souls", 22, Rarity.MYTHIC, mage.cards.c.CavernOfSouls.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Celestial Colonnade", 23, Rarity.MYTHIC, mage.cards.c.CelestialColonnade.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Copperline Gorge", 14, Rarity.MYTHIC, mage.cards.c.CopperlineGorge.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Creeping Tar Pit", 24, Rarity.MYTHIC, mage.cards.c.CreepingTarPit.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Darkslick Shores", 12, Rarity.MYTHIC, mage.cards.d.DarkslickShores.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Flooded Strand", 1, Rarity.MYTHIC, mage.cards.f.FloodedStrand.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Grove of the Burnwillows", 25, Rarity.MYTHIC, mage.cards.g.GroveOfTheBurnwillows.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Horizon Canopy", 26, Rarity.MYTHIC, mage.cards.h.HorizonCanopy.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Luxury Suite", 18, Rarity.MYTHIC, mage.cards.l.LuxurySuite.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Marsh Flats", 6, Rarity.MYTHIC, mage.cards.m.MarshFlats.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Misty Rainforest", 10, Rarity.MYTHIC, mage.cards.m.MistyRainforest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Morphic Pool", 17, Rarity.MYTHIC, mage.cards.m.MorphicPool.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Polluted Delta", 2, Rarity.MYTHIC, mage.cards.p.PollutedDelta.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Prismatic Vista", 27, Rarity.MYTHIC, mage.cards.p.PrismaticVista.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Razorverge Thicket", 15, Rarity.MYTHIC, mage.cards.r.RazorvergeThicket.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Scalding Tarn", 7, Rarity.MYTHIC, mage.cards.s.ScaldingTarn.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Sea of Clouds", 16, Rarity.MYTHIC, mage.cards.s.SeaOfClouds.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Seachrome Coast", 11, Rarity.MYTHIC, mage.cards.s.SeachromeCoast.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Spire Garden", 19, Rarity.MYTHIC, mage.cards.s.SpireGarden.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Strip Mine", 28, Rarity.MYTHIC, mage.cards.s.StripMine.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Valakut, the Molten Pinnacle", 29, Rarity.MYTHIC, mage.cards.v.ValakutTheMoltenPinnacle.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Verdant Catacombs", 8, Rarity.MYTHIC, mage.cards.v.VerdantCatacombs.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Wasteland", 30, Rarity.MYTHIC, mage.cards.w.Wasteland.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Windswept Heath", 5, Rarity.MYTHIC, mage.cards.w.WindsweptHeath.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Wooded Foothills", 4, Rarity.MYTHIC, mage.cards.w.WoodedFoothills.class, FULL_ART_BFZ_VARIOUS));
    }
}
