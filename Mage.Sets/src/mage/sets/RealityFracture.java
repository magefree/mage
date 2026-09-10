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
        super("Reality Fracture", "FRA", ExpansionSet.buildDate(2026, 10, 2), SetType.EXPANSION);
        this.blockName = "Reality Fracture"; // for sorting in GUI
        this.hasBasicLands = true;

        // this.enablePlayBooster(305); TODO: Enable later

        cards.add(new SetCardInfo("Academic Ascent", 2, Rarity.COMMON, mage.cards.a.AcademicAscent.class));
        cards.add(new SetCardInfo("Ajani Resolute", 195, Rarity.MYTHIC, mage.cards.a.AjaniResolute.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ajani Resolute", 291, Rarity.MYTHIC, mage.cards.a.AjaniResolute.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ajani Unrelenting", 242, Rarity.MYTHIC, mage.cards.a.AjaniUnrelenting.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ajani Unrelenting", 308, Rarity.MYTHIC, mage.cards.a.AjaniUnrelenting.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arcane Amphisbaena", 97, Rarity.COMMON, mage.cards.a.ArcaneAmphisbaena.class));
        cards.add(new SetCardInfo("Blazing Crescendo", 75, Rarity.COMMON, mage.cards.b.BlazingCrescendo.class));
        cards.add(new SetCardInfo("Bloodline Recollector", 402, Rarity.MYTHIC, mage.cards.b.BloodlineRecollector.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodline Recollector", 427, Rarity.MYTHIC, mage.cards.b.BloodlineRecollector.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodline Recollector", 49, Rarity.MYTHIC, mage.cards.b.BloodlineRecollector.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Campus Crier", 4, Rarity.COMMON, mage.cards.c.CampusCrier.class));
        cards.add(new SetCardInfo("Cast Away Doubt", 51, Rarity.COMMON, mage.cards.c.CastAwayDoubt.class));
        cards.add(new SetCardInfo("Chandra, Chill of Compliance", 212, Rarity.MYTHIC, mage.cards.c.ChandraChillOfCompliance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chandra, Chill of Compliance", 297, Rarity.MYTHIC, mage.cards.c.ChandraChillOfCompliance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chandra, Torch of Defiance", 244, Rarity.MYTHIC, mage.cards.c.ChandraTorchOfDefiance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chandra, Torch of Defiance", 309, Rarity.MYTHIC, mage.cards.c.ChandraTorchOfDefiance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Charge the Sanctum", 125, Rarity.COMMON, mage.cards.c.ChargeTheSanctum.class));
        cards.add(new SetCardInfo("Compel Brutality", 101, Rarity.COMMON, mage.cards.c.CompelBrutality.class));
        cards.add(new SetCardInfo("Countersculpt", 25, Rarity.UNCOMMON, mage.cards.c.Countersculpt.class));
        cards.add(new SetCardInfo("Craterclaw Colossus", 327, Rarity.MYTHIC, mage.cards.c.CraterclawColossus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Craterclaw Colossus", 446, Rarity.MYTHIC, mage.cards.c.CraterclawColossus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Craterclaw Colossus", 455, Rarity.MYTHIC, mage.cards.c.CraterclawColossus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Craterclaw Colossus", 78, Rarity.MYTHIC, mage.cards.c.CraterclawColossus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Danitha, Spear of Agony", 227, Rarity.UNCOMMON, mage.cards.d.DanithaSpearOfAgony.class));
        cards.add(new SetCardInfo("Denzilore Fatehold", 128, Rarity.MYTHIC, mage.cards.d.DenziloreFatehold.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Denzilore Fatehold", 349, Rarity.MYTHIC, mage.cards.d.DenziloreFatehold.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Denzilore Fatehold", 408, Rarity.MYTHIC, mage.cards.d.DenziloreFatehold.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Denzilore Fatehold", 418, Rarity.MYTHIC, mage.cards.d.DenziloreFatehold.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deserted Beach", 176, Rarity.RARE, mage.cards.d.DesertedBeach.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deserted Beach", 397, Rarity.RARE, mage.cards.d.DesertedBeach.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Diviner of Victory", 28, Rarity.RARE, mage.cards.d.DivinerOfVictory.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Diviner of Victory", 425, Rarity.RARE, mage.cards.d.DivinerOfVictory.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eardrum Rattler", 81, Rarity.COMMON, mage.cards.e.EardrumRattler.class));
        cards.add(new SetCardInfo("Fatehold Chronologist", 133, Rarity.COMMON, mage.cards.f.FateholdChronologist.class));
        cards.add(new SetCardInfo("Forest", 394, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forest", 395, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forest", 396, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Garruk, Curse Breaker", 259, Rarity.MYTHIC, mage.cards.g.GarrukCurseBreaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Garruk, Curse Breaker", 314, Rarity.MYTHIC, mage.cards.g.GarrukCurseBreaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Garruk, Veiled Butcher", 229, Rarity.MYTHIC, mage.cards.g.GarrukVeiledButcher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Garruk, Veiled Butcher", 303, Rarity.MYTHIC, mage.cards.g.GarrukVeiledButcher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Generous Revival", 8, Rarity.UNCOMMON, mage.cards.g.GenerousRevival.class));
        cards.add(new SetCardInfo("Ghalta the Unstoppable", 260, Rarity.UNCOMMON, mage.cards.g.GhaltaTheUnstoppable.class));
        cards.add(new SetCardInfo("Greenhouse Propagator", 104, Rarity.COMMON, mage.cards.g.GreenhousePropagator.class));
        cards.add(new SetCardInfo("Haunted Ridge", 180, Rarity.RARE, mage.cards.h.HauntedRidge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Haunted Ridge", 398, Rarity.RARE, mage.cards.h.HauntedRidge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Icy Reception", 30, Rarity.COMMON, mage.cards.i.IcyReception.class));
        cards.add(new SetCardInfo("Inspired Tethermage", 109, Rarity.COMMON, mage.cards.i.InspiredTethermage.class));
        cards.add(new SetCardInfo("Island", 385, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 386, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 387, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Jiang Yanggu, Never Alone", 261, Rarity.UNCOMMON, mage.cards.j.JiangYangguNeverAlone.class));
        cards.add(new SetCardInfo("Karn, Argent Defender", 279, Rarity.RARE, mage.cards.k.KarnArgentDefender.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Karn, Argent Defender", 320, Rarity.RARE, mage.cards.k.KarnArgentDefender.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Keeper of the Quiet Hour", 171, Rarity.COMMON, mage.cards.k.KeeperOfTheQuietHour.class));
        cards.add(new SetCardInfo("Kwia Vigorbloom", 140, Rarity.MYTHIC, mage.cards.k.KwiaVigorbloom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kwia Vigorbloom", 352, Rarity.MYTHIC, mage.cards.k.KwiaVigorbloom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kwia Vigorbloom", 410, Rarity.MYTHIC, mage.cards.k.KwiaVigorbloom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kwia Vigorbloom", 420, Rarity.MYTHIC, mage.cards.k.KwiaVigorbloom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Last Gasp", 56, Rarity.COMMON, mage.cards.l.LastGasp.class));
        cards.add(new SetCardInfo("Liliana the Faultless", 200, Rarity.RARE, mage.cards.l.LilianaTheFaultless.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Liliana the Faultless", 293, Rarity.RARE, mage.cards.l.LilianaTheFaultless.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Liliana the Repentant", 231, Rarity.RARE, mage.cards.l.LilianaTheRepentant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Liliana the Repentant", 305, Rarity.RARE, mage.cards.l.LilianaTheRepentant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Loyal Tutor", 14, Rarity.RARE, mage.cards.l.LoyalTutor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Loyal Tutor", 404, Rarity.MYTHIC, mage.cards.l.LoyalTutor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Loyal Tutor", 414, Rarity.MYTHIC, mage.cards.l.LoyalTutor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Loyal Tutor", 424, Rarity.RARE, mage.cards.l.LoyalTutor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Memory Trap", 15, Rarity.COMMON, mage.cards.m.MemoryTrap.class));
        cards.add(new SetCardInfo("Mountain", 391, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 392, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 393, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("No Admittance", 89, Rarity.COMMON, mage.cards.n.NoAdmittance.class));
        cards.add(new SetCardInfo("Overgrown Farmland", 185, Rarity.RARE, mage.cards.o.OvergrownFarmland.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Overgrown Farmland", 399, Rarity.RARE, mage.cards.o.OvergrownFarmland.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Overwrite the Multiverse", 341, Rarity.MYTHIC, mage.cards.o.OverwriteTheMultiverse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Overwrite the Multiverse", 445, Rarity.MYTHIC, mage.cards.o.OverwriteTheMultiverse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Overwrite the Multiverse", 59, Rarity.MYTHIC, mage.cards.o.OverwriteTheMultiverse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Paradox Shaper", 143, Rarity.UNCOMMON, mage.cards.p.ParadoxShaper.class));
        cards.add(new SetCardInfo("Perfected Theory", 34, Rarity.UNCOMMON, mage.cards.p.PerfectedTheory.class));
        cards.add(new SetCardInfo("Plains", 382, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 383, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 384, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Pompous Battlemage", 428, Rarity.RARE, mage.cards.p.PompousBattlemage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pompous Battlemage", 90, Rarity.RARE, mage.cards.p.PompousBattlemage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Proft, Consulting Detective", 218, Rarity.UNCOMMON, mage.cards.p.ProftConsultingDetective.class));
        cards.add(new SetCardInfo("Protege's Awakening", 37, Rarity.COMMON, mage.cards.p.ProtegesAwakening.class));
        cards.add(new SetCardInfo("Prudent Fateseer", 146, Rarity.UNCOMMON, mage.cards.p.PrudentFateseer.class));
        cards.add(new SetCardInfo("Restore with Empathy", 112, Rarity.UNCOMMON, mage.cards.r.RestoreWithEmpathy.class));
        cards.add(new SetCardInfo("Return to the Light Realms", 20, Rarity.MYTHIC, mage.cards.r.ReturnToTheLightRealms.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Return to the Light Realms", 323, Rarity.MYTHIC, mage.cards.r.ReturnToTheLightRealms.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Return to the Light Realms", 440, Rarity.MYTHIC, mage.cards.r.ReturnToTheLightRealms.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Return to the Light Realms", 451, Rarity.MYTHIC, mage.cards.r.ReturnToTheLightRealms.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rewrite Regrets", 62, Rarity.UNCOMMON, mage.cards.r.RewriteRegrets.class));
        cards.add(new SetCardInfo("Rockfall Vale", 186, Rarity.RARE, mage.cards.r.RockfallVale.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rockfall Vale", 400, Rarity.RARE, mage.cards.r.RockfallVale.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shipwreck Marsh", 189, Rarity.RARE, mage.cards.s.ShipwreckMarsh.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shipwreck Marsh", 401, Rarity.RARE, mage.cards.s.ShipwreckMarsh.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Solitary Cell", 149, Rarity.RARE, mage.cards.s.SolitaryCell.class));
        cards.add(new SetCardInfo("Solve for Disappointment", 67, Rarity.COMMON, mage.cards.s.SolveForDisappointment.class));
        cards.add(new SetCardInfo("Something Worth Saving", 114, Rarity.COMMON, mage.cards.s.SomethingWorthSaving.class));
        cards.add(new SetCardInfo("Stingcaster Mage", 329, Rarity.MYTHIC, mage.cards.s.StingcasterMage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stingcaster Mage", 447, Rarity.MYTHIC, mage.cards.s.StingcasterMage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stingcaster Mage", 457, Rarity.MYTHIC, mage.cards.s.StingcasterMage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stingcaster Mage", 93, Rarity.MYTHIC, mage.cards.s.StingcasterMage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stingerquill Voxmancer", 151, Rarity.UNCOMMON, mage.cards.s.StingerquillVoxmancer.class));
        cards.add(new SetCardInfo("Swamp", 388, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 389, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 390, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Tam's Resistance", 153, Rarity.COMMON, mage.cards.t.TamsResistance.class));
        cards.add(new SetCardInfo("Tarmogoyf", 116, Rarity.MYTHIC, mage.cards.t.Tarmogoyf.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tarmogoyf", 374, Rarity.MYTHIC, mage.cards.t.Tarmogoyf.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tethermage's Advantage", 117, Rarity.COMMON, mage.cards.t.TethermagesAdvantage.class));
        cards.add(new SetCardInfo("The Theorist, Jace Beleren", 363, Rarity.MYTHIC, mage.cards.t.TheTheoristJaceBeleren.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Theorist, Jace Beleren", 405, Rarity.MYTHIC, mage.cards.t.TheTheoristJaceBeleren.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Theorist, Jace Beleren", 415, Rarity.MYTHIC, mage.cards.t.TheTheoristJaceBeleren.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Theorist, Jace Beleren", 43, Rarity.MYTHIC, mage.cards.t.TheTheoristJaceBeleren.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Theorist, Jace Beleren", 443, Rarity.MYTHIC, mage.cards.t.TheTheoristJaceBeleren.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Theorist's Proxy", 339, Rarity.RARE, mage.cards.t.TheoristsProxy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Theorist's Proxy", 44, Rarity.RARE, mage.cards.t.TheoristsProxy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Theorist's Sanctum", 191, Rarity.RARE, mage.cards.t.TheoristsSanctum.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Theorist's Sanctum", 381, Rarity.RARE, mage.cards.t.TheoristsSanctum.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tinybones, Pocket Nuisance", 237, Rarity.UNCOMMON, mage.cards.t.TinybonesPocketNuisance.class));
        cards.add(new SetCardInfo("Titanbones, Towering Heart", 266, Rarity.UNCOMMON, mage.cards.t.TitanbonesToweringHeart.class));
        cards.add(new SetCardInfo("Vigorbloom Vanguard", 161, Rarity.UNCOMMON, mage.cards.v.VigorbloomVanguard.class));
        cards.add(new SetCardInfo("Warrior's Blades", 163, Rarity.UNCOMMON, mage.cards.w.WarriorsBlades.class));
        cards.add(new SetCardInfo("Way of the Cryomancer", 223, Rarity.UNCOMMON, mage.cards.w.WayOfTheCryomancer.class));
        cards.add(new SetCardInfo("Way of the Pyromancer", 254, Rarity.UNCOMMON, mage.cards.w.WayOfThePyromancer.class));
        cards.add(new SetCardInfo("Winter, Team Player", 312, Rarity.UNCOMMON, mage.cards.w.WinterTeamPlayer.class));
        cards.add(new SetCardInfo("Woodwork Prodigy", 165, Rarity.UNCOMMON, mage.cards.w.WoodworkProdigy.class));
        cards.add(new SetCardInfo("Yargle, Glutton of Urborg", 241, Rarity.UNCOMMON, mage.cards.y.YargleGluttonOfUrborg.class));
        cards.add(new SetCardInfo("Yargle, Goliath of Otaria", 225, Rarity.UNCOMMON, mage.cards.y.YargleGoliathOfOtaria.class));

    }
}
