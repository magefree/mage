package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class ModernHorizons3 extends ExpansionSet {

    private static final ModernHorizons3 instance = new ModernHorizons3();

    public static ModernHorizons3 getInstance() {
        return instance;
    }

    private ModernHorizons3() {
        super("Modern Horizons 3", "MH3", ExpansionSet.buildDate(2024, 6, 7), SetType.SUPPLEMENTAL_MODERN_LEGAL);
        this.blockName = "Modern Horizons 3";
        this.hasBasicLands = true;
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Ajani, Nacatl Avenger", 237, Rarity.MYTHIC, mage.cards.a.AjaniNacatlAvenger.class));
        cards.add(new SetCardInfo("Ajani, Nacatl Pariah", 237, Rarity.MYTHIC, mage.cards.a.AjaniNacatlPariah.class));
        cards.add(new SetCardInfo("Barbarian Ring", 299, Rarity.UNCOMMON, mage.cards.b.BarbarianRing.class));
        cards.add(new SetCardInfo("Basking Broodscale", 145, Rarity.COMMON, mage.cards.b.BaskingBroodscale.class));
        cards.add(new SetCardInfo("Bloodsoaked Insight", 252, Rarity.UNCOMMON, mage.cards.b.BloodsoakedInsight.class));
        cards.add(new SetCardInfo("Bloodstained Mire", 216, Rarity.RARE, mage.cards.b.BloodstainedMire.class));
        cards.add(new SetCardInfo("Brainsurge", 53, Rarity.UNCOMMON, mage.cards.b.Brainsurge.class));
        cards.add(new SetCardInfo("Breaker of Creation", 1, Rarity.UNCOMMON, mage.cards.b.BreakerOfCreation.class));
        cards.add(new SetCardInfo("Breya, Etherium Shaper", 289, Rarity.MYTHIC, mage.cards.b.BreyaEtheriumShaper.class));
        cards.add(new SetCardInfo("Cephalid Coliseum", 300, Rarity.UNCOMMON, mage.cards.c.CephalidColiseum.class));
        cards.add(new SetCardInfo("Chthonian Nightmare", 83, Rarity.RARE, mage.cards.c.ChthonianNightmare.class));
        cards.add(new SetCardInfo("Collective Resistance", 147, Rarity.UNCOMMON, mage.cards.c.CollectiveResistance.class));
        cards.add(new SetCardInfo("Conduit Goblin", 179, Rarity.COMMON, mage.cards.c.ConduitGoblin.class));
        cards.add(new SetCardInfo("Cursed Mirror", 279, Rarity.RARE, mage.cards.c.CursedMirror.class));
        cards.add(new SetCardInfo("Deep Analysis", 268, Rarity.UNCOMMON, mage.cards.d.DeepAnalysis.class));
        cards.add(new SetCardInfo("Deserted Temple", 301, Rarity.RARE, mage.cards.d.DesertedTemple.class));
        cards.add(new SetCardInfo("Devourer of Destiny", 2, Rarity.RARE, mage.cards.d.DevourerOfDestiny.class));
        cards.add(new SetCardInfo("Drossclaw", 89, Rarity.COMMON, mage.cards.d.Drossclaw.class));
        cards.add(new SetCardInfo("Drowner of Truth", 253, Rarity.UNCOMMON, mage.cards.d.DrownerOfTruth.class));
        cards.add(new SetCardInfo("Echoes of Eternity", 4, Rarity.RARE, mage.cards.e.EchoesOfEternity.class));
        cards.add(new SetCardInfo("Eladamri, Korvecdal", 149, Rarity.MYTHIC, mage.cards.e.EladamriKorvecdal.class));
        cards.add(new SetCardInfo("Eldrazi Ravager", 5, Rarity.UNCOMMON, mage.cards.e.EldraziRavager.class));
        cards.add(new SetCardInfo("Emerald Medallion", 291, Rarity.RARE, mage.cards.e.EmeraldMedallion.class));
        cards.add(new SetCardInfo("Emrakul, the World Anew", 6, Rarity.MYTHIC, mage.cards.e.EmrakulTheWorldAnew.class));
        cards.add(new SetCardInfo("Estrid's Invocation", 269, Rarity.RARE, mage.cards.e.EstridsInvocation.class));
        cards.add(new SetCardInfo("Evolution Witness", 151, Rarity.COMMON, mage.cards.e.EvolutionWitness.class));
        cards.add(new SetCardInfo("Expanding Ooze", 184, Rarity.COMMON, mage.cards.e.ExpandingOoze.class));
        cards.add(new SetCardInfo("Flare of Cultivation", 154, Rarity.RARE, mage.cards.f.FlareOfCultivation.class));
        cards.add(new SetCardInfo("Flare of Denial", 62, Rarity.RARE, mage.cards.f.FlareOfDenial.class));
        cards.add(new SetCardInfo("Flare of Duplication", 119, Rarity.RARE, mage.cards.f.FlareOfDuplication.class));
        cards.add(new SetCardInfo("Flare of Fortitude", 26, Rarity.RARE, mage.cards.f.FlareOfFortitude.class));
        cards.add(new SetCardInfo("Flare of Malice", 95, Rarity.RARE, mage.cards.f.FlareOfMalice.class));
        cards.add(new SetCardInfo("Flooded Strand", 220, Rarity.RARE, mage.cards.f.FloodedStrand.class));
        cards.add(new SetCardInfo("Flusterstorm", 496, Rarity.RARE, mage.cards.f.Flusterstorm.class));
        cards.add(new SetCardInfo("Forest", 308, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Frogmyr Enforcer", 120, Rarity.UNCOMMON, mage.cards.f.FrogmyrEnforcer.class));
        cards.add(new SetCardInfo("Glasswing Grace", 254, Rarity.UNCOMMON, mage.cards.g.GlasswingGrace.class));
        cards.add(new SetCardInfo("Grim Servant", 97, Rarity.UNCOMMON, mage.cards.g.GrimServant.class));
        cards.add(new SetCardInfo("Grist, Voracious Larva", 251, Rarity.MYTHIC, mage.cards.g.GristVoraciousLarva.class));
        cards.add(new SetCardInfo("Grist, the Plague Swarm", 251, Rarity.MYTHIC, mage.cards.g.GristThePlagueSwarm.class));
        cards.add(new SetCardInfo("Guide of Souls", 29, Rarity.RARE, mage.cards.g.GuideOfSouls.class));
        cards.add(new SetCardInfo("Island", 305, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("It That Heralds the End", 9, Rarity.UNCOMMON, mage.cards.i.ItThatHeraldsTheEnd.class));
        cards.add(new SetCardInfo("Jet Medallion", 292, Rarity.RARE, mage.cards.j.JetMedallion.class));
        cards.add(new SetCardInfo("K'rrik, Son of Yawgmoth", 274, Rarity.RARE, mage.cards.k.KrrikSonOfYawgmoth.class));
        cards.add(new SetCardInfo("Kaalia of the Vast", 290, Rarity.MYTHIC, mage.cards.k.KaaliaOfTheVast.class));
        cards.add(new SetCardInfo("Kappa Cannoneer", 270, Rarity.RARE, mage.cards.k.KappaCannoneer.class));
        cards.add(new SetCardInfo("Kozilek's Unsealing", 65, Rarity.UNCOMMON, mage.cards.k.KozileksUnsealing.class));
        cards.add(new SetCardInfo("Kudo, King Among Bears", 192, Rarity.RARE, mage.cards.k.KudoKingAmongBears.class));
        cards.add(new SetCardInfo("Laelia, the Blade Reforged", 281, Rarity.RARE, mage.cards.l.LaeliaTheBladeReforged.class));
        cards.add(new SetCardInfo("Legion Leadership", 255, Rarity.UNCOMMON, mage.cards.l.LegionLeadership.class));
        cards.add(new SetCardInfo("Mandibular Kite", 34, Rarity.COMMON, mage.cards.m.MandibularKite.class));
        cards.add(new SetCardInfo("Marionette Apprentice", 100, Rarity.UNCOMMON, mage.cards.m.MarionetteApprentice.class));
        cards.add(new SetCardInfo("Meltdown", 282, Rarity.UNCOMMON, mage.cards.m.Meltdown.class));
        cards.add(new SetCardInfo("Mogg Mob", 127, Rarity.UNCOMMON, mage.cards.m.MoggMob.class));
        cards.add(new SetCardInfo("Mountain", 307, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Nethergoyf", 103, Rarity.MYTHIC, mage.cards.n.Nethergoyf.class));
        cards.add(new SetCardInfo("Null Elemental Blast", 12, Rarity.UNCOMMON, mage.cards.n.NullElementalBlast.class));
        cards.add(new SetCardInfo("Nulldrifter", 13, Rarity.RARE, mage.cards.n.Nulldrifter.class));
        cards.add(new SetCardInfo("Ophiomancer", 276, Rarity.RARE, mage.cards.o.Ophiomancer.class));
        cards.add(new SetCardInfo("Orim's Chant", 265, Rarity.RARE, mage.cards.o.OrimsChant.class));
        cards.add(new SetCardInfo("Path of Annihilation", 165, Rarity.UNCOMMON, mage.cards.p.PathOfAnnihilation.class));
        cards.add(new SetCardInfo("Pearl Medallion", 294, Rarity.RARE, mage.cards.p.PearlMedallion.class));
        cards.add(new SetCardInfo("Petrifying Meddler", 66, Rarity.COMMON, mage.cards.p.PetrifyingMeddler.class));
        cards.add(new SetCardInfo("Phelia, Exuberant Shepherd", 40, Rarity.RARE, mage.cards.p.PheliaExuberantShepherd.class));
        cards.add(new SetCardInfo("Phlage, Titan of Fire's Fury", 493, Rarity.MYTHIC, mage.cards.p.PhlageTitanOfFiresFury.class));
        cards.add(new SetCardInfo("Plains", 304, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Polluted Delta", 224, Rarity.RARE, mage.cards.p.PollutedDelta.class));
        cards.add(new SetCardInfo("Priest of Titania", 286, Rarity.UNCOMMON, mage.cards.p.PriestOfTitania.class));
        cards.add(new SetCardInfo("Psychic Frog", 199, Rarity.RARE, mage.cards.p.PsychicFrog.class));
        cards.add(new SetCardInfo("Ral, Leyline Prodigy", 247, Rarity.MYTHIC, mage.cards.r.RalLeylineProdigy.class));
        cards.add(new SetCardInfo("Ral, Monsoon Mage", 247, Rarity.MYTHIC, mage.cards.r.RalMonsoonMage.class));
        cards.add(new SetCardInfo("Reef Worm", 271, Rarity.RARE, mage.cards.r.ReefWorm.class));
        cards.add(new SetCardInfo("Revitalizing Repast", 256, Rarity.UNCOMMON, mage.cards.r.RevitalizingRepast.class));
        cards.add(new SetCardInfo("Ruby Medallion", 295, Rarity.RARE, mage.cards.r.RubyMedallion.class));
        cards.add(new SetCardInfo("Rush of Inspiration", 257, Rarity.UNCOMMON, mage.cards.r.RushOfInspiration.class));
        cards.add(new SetCardInfo("Sapphire Medallion", 296, Rarity.RARE, mage.cards.s.SapphireMedallion.class));
        cards.add(new SetCardInfo("Scurrilous Sentry", 108, Rarity.COMMON, mage.cards.s.ScurrilousSentry.class));
        cards.add(new SetCardInfo("Scurry of Gremlins", 203, Rarity.UNCOMMON, mage.cards.s.ScurryOfGremlins.class));
        cards.add(new SetCardInfo("Serum Visionary", 69, Rarity.COMMON, mage.cards.s.SerumVisionary.class));
        cards.add(new SetCardInfo("Six", 169, Rarity.RARE, mage.cards.s.Six.class));
        cards.add(new SetCardInfo("Skoa, Embermage", 138, Rarity.COMMON, mage.cards.s.SkoaEmbermage.class));
        cards.add(new SetCardInfo("Snow-Covered Wastes", 229, Rarity.UNCOMMON, mage.cards.s.SnowCoveredWastes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sorin of House Markov", 245, Rarity.MYTHIC, mage.cards.s.SorinOfHouseMarkov.class));
        cards.add(new SetCardInfo("Sorin, Ravenous Neonate", 245, Rarity.MYTHIC, mage.cards.s.SorinRavenousNeonate.class));
        cards.add(new SetCardInfo("Spawn-Gang Commander", 140, Rarity.UNCOMMON, mage.cards.s.SpawnGangCommander.class));
        cards.add(new SetCardInfo("Strength of the Harvest", 258, Rarity.UNCOMMON, mage.cards.s.StrengthOfTheHarvest.class));
        cards.add(new SetCardInfo("Strix Serenade", 71, Rarity.RARE, mage.cards.s.StrixSerenade.class));
        cards.add(new SetCardInfo("Stump Stomp", 259, Rarity.UNCOMMON, mage.cards.s.StumpStomp.class));
        cards.add(new SetCardInfo("Suppression Ray", 260, Rarity.UNCOMMON, mage.cards.s.SuppressionRay.class));
        cards.add(new SetCardInfo("Swamp", 306, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Sylvan Safekeeper", 287, Rarity.RARE, mage.cards.s.SylvanSafekeeper.class));
        cards.add(new SetCardInfo("Tamiyo, Inquisitive Student", 242, Rarity.MYTHIC, mage.cards.t.TamiyoInquisitiveStudent.class));
        cards.add(new SetCardInfo("Tamiyo, Seasoned Scholar", 242, Rarity.MYTHIC, mage.cards.t.TamiyoSeasonedScholar.class));
        cards.add(new SetCardInfo("Toxic Deluge", 277, Rarity.RARE, mage.cards.t.ToxicDeluge.class));
        cards.add(new SetCardInfo("Trickster's Elk", 175, Rarity.UNCOMMON, mage.cards.t.TrickstersElk.class));
        cards.add(new SetCardInfo("Tune the Narrative", 75, Rarity.COMMON, mage.cards.t.TuneTheNarrative.class));
        cards.add(new SetCardInfo("Ugin's Labyrinth", 233, Rarity.MYTHIC, mage.cards.u.UginsLabyrinth.class));
        cards.add(new SetCardInfo("Urza's Cave", 234, Rarity.UNCOMMON, mage.cards.u.UrzasCave.class));
        cards.add(new SetCardInfo("Victimize", 278, Rarity.UNCOMMON, mage.cards.v.Victimize.class));
        cards.add(new SetCardInfo("Warren Soultrader", 110, Rarity.RARE, mage.cards.w.WarrenSoultrader.class));
        cards.add(new SetCardInfo("Waterlogged Teachings", 261, Rarity.UNCOMMON, mage.cards.w.WaterloggedTeachings.class));
        cards.add(new SetCardInfo("Wight of the Reliquary", 207, Rarity.RARE, mage.cards.w.WightOfTheReliquary.class));
        cards.add(new SetCardInfo("Windswept Heath", 235, Rarity.RARE, mage.cards.w.WindsweptHeath.class));
        cards.add(new SetCardInfo("Winter Moon", 213, Rarity.RARE, mage.cards.w.WinterMoon.class));
        cards.add(new SetCardInfo("Wirewood Symbiote", 288, Rarity.UNCOMMON, mage.cards.w.WirewoodSymbiote.class));
        cards.add(new SetCardInfo("Wooded Foothills", 236, Rarity.RARE, mage.cards.w.WoodedFoothills.class));
        cards.add(new SetCardInfo("Worn Powerstone", 298, Rarity.UNCOMMON, mage.cards.w.WornPowerstone.class));
        cards.add(new SetCardInfo("Writhing Chrysalis", 208, Rarity.COMMON, mage.cards.w.WrithingChrysalis.class));
        cards.add(new SetCardInfo("Wurmcoil Larva", 112, Rarity.UNCOMMON, mage.cards.w.WurmcoilLarva.class));
    }
}
