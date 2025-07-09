package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.Arrays;
import java.util.List;

/**
 * @author TheElk801
 */
public final class EdgeOfEternities extends ExpansionSet {

    private static final List<String> unfinished = Arrays.asList("Anticausal Vestige", "Astelli Reclaimer", "Bygone Colossus", "Eusocial Engineering", "Exalted Sunborn", "Haliya, Guided by Light", "Mechanozoa", "Nova Hellkite", "Quantum Riddler", "Red Tiger Mechan", "Starbreach Whale", "Starfield Shepherd", "Starfield Vocalist", "Timeline Culler", "Weftstalker Ardent");
    private static final EdgeOfEternities instance = new EdgeOfEternities();

    public static EdgeOfEternities getInstance() {
        return instance;
    }

    private EdgeOfEternities() {
        super("Edge of Eternities", "EOE", ExpansionSet.buildDate(2025, 8, 1), SetType.EXPANSION);
        this.blockName = "Edge of Eternities"; // for sorting in GUI
        this.rotationSet = true;

        cards.add(new SetCardInfo("Alpharael, Dreaming Acolyte", 212, Rarity.UNCOMMON, mage.cards.a.AlpharaelDreamingAcolyte.class));
        cards.add(new SetCardInfo("Anticausal Vestige", 1, Rarity.RARE, mage.cards.a.AnticausalVestige.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Anticausal Vestige", 317, Rarity.RARE, mage.cards.a.AnticausalVestige.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Anticausal Vestige", 357, Rarity.MYTHIC, mage.cards.a.AnticausalVestige.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Anticausal Vestige", 383, Rarity.RARE, mage.cards.a.AnticausalVestige.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Archenemy's Charm", 307, Rarity.RARE, mage.cards.a.ArchenemysCharm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Archenemy's Charm", 88, Rarity.RARE, mage.cards.a.ArchenemysCharm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Banishing Light", 6, Rarity.COMMON, mage.cards.b.BanishingLight.class));
        cards.add(new SetCardInfo("Biotech Specialist", 214, Rarity.RARE, mage.cards.b.BiotechSpecialist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Biotech Specialist", 347, Rarity.RARE, mage.cards.b.BiotechSpecialist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Breeding Pool", 251, Rarity.RARE, mage.cards.b.BreedingPool.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Breeding Pool", 278, Rarity.RARE, mage.cards.b.BreedingPool.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Breeding Pool", 373, Rarity.RARE, mage.cards.b.BreedingPool.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bygone Colossus", 235, Rarity.UNCOMMON, mage.cards.b.BygoneColossus.class));
        cards.add(new SetCardInfo("Chrome Companion", 236, Rarity.COMMON, mage.cards.c.ChromeCompanion.class));
        cards.add(new SetCardInfo("Command Bridge", 252, Rarity.COMMON, mage.cards.c.CommandBridge.class));
        cards.add(new SetCardInfo("Consult the Star Charts", 325, Rarity.RARE, mage.cards.c.ConsultTheStarCharts.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Consult the Star Charts", 51, Rarity.RARE, mage.cards.c.ConsultTheStarCharts.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cosmogoyf", 215, Rarity.RARE, mage.cards.c.Cosmogoyf.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cosmogoyf", 313, Rarity.RARE, mage.cards.c.Cosmogoyf.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Decode Transmissions", 94, Rarity.COMMON, mage.cards.d.DecodeTransmissions.class));
        cards.add(new SetCardInfo("Embrace Oblivion", 98, Rarity.COMMON, mage.cards.e.EmbraceOblivion.class));
        cards.add(new SetCardInfo("Emergency Eject", 14, Rarity.UNCOMMON, mage.cards.e.EmergencyEject.class));
        cards.add(new SetCardInfo("Emissary Escort", 326, Rarity.RARE, mage.cards.e.EmissaryEscort.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Emissary Escort", 399, Rarity.RARE, mage.cards.e.EmissaryEscort.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Emissary Escort", 56, Rarity.RARE, mage.cards.e.EmissaryEscort.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eusocial Engineering", 181, Rarity.UNCOMMON, mage.cards.e.EusocialEngineering.class));
        cards.add(new SetCardInfo("Exalted Sunborn", 15, Rarity.MYTHIC, mage.cards.e.ExaltedSunborn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Exalted Sunborn", 318, Rarity.MYTHIC, mage.cards.e.ExaltedSunborn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Exalted Sunborn", 358, Rarity.MYTHIC, mage.cards.e.ExaltedSunborn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Exalted Sunborn", 384, Rarity.MYTHIC, mage.cards.e.ExaltedSunborn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Extinguisher Battleship", 242, Rarity.RARE, mage.cards.e.ExtinguisherBattleship.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Extinguisher Battleship", 355, Rarity.RARE, mage.cards.e.ExtinguisherBattleship.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 266, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forest", 275, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 276, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 371, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Frenzied Baloth", 183, Rarity.RARE, mage.cards.f.FrenziedBaloth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frenzied Baloth", 342, Rarity.RARE, mage.cards.f.FrenziedBaloth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Galactic Wayfarer", 185, Rarity.COMMON, mage.cards.g.GalacticWayfarer.class));
        cards.add(new SetCardInfo("Genemorph Imago", 217, Rarity.RARE, mage.cards.g.GenemorphImago.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Genemorph Imago", 299, Rarity.RARE, mage.cards.g.GenemorphImago.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Godless Shrine", 254, Rarity.RARE, mage.cards.g.GodlessShrine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Godless Shrine", 280, Rarity.RARE, mage.cards.g.GodlessShrine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Godless Shrine", 375, Rarity.RARE, mage.cards.g.GodlessShrine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Haliya, Guided by Light", 19, Rarity.RARE, mage.cards.h.HaliyaGuidedByLight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Haliya, Guided by Light", 289, Rarity.RARE, mage.cards.h.HaliyaGuidedByLight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Harmonious Grovestrider", 189, Rarity.UNCOMMON, mage.cards.h.HarmoniousGrovestrider.class));
        cards.add(new SetCardInfo("Infinite Guideline Station", 219, Rarity.RARE, mage.cards.i.InfiniteGuidelineStation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Infinite Guideline Station", 348, Rarity.RARE, mage.cards.i.InfiniteGuidelineStation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Insatiable Skittermaw", 108, Rarity.COMMON, mage.cards.i.InsatiableSkittermaw.class));
        cards.add(new SetCardInfo("Island", 263, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 269, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 270, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 368, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mechanozoa", 66, Rarity.COMMON, mage.cards.m.Mechanozoa.class));
        cards.add(new SetCardInfo("Mountain", 265, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 273, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 274, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 370, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Nova Hellkite", 148, Rarity.RARE, mage.cards.n.NovaHellkite.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nova Hellkite", 309, Rarity.RARE, mage.cards.n.NovaHellkite.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ouroboroid", 201, Rarity.MYTHIC, mage.cards.o.Ouroboroid.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ouroboroid", 345, Rarity.MYTHIC, mage.cards.o.Ouroboroid.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 262, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 267, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 268, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 367, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Red Tiger Mechan", 154, Rarity.COMMON, mage.cards.r.RedTigerMechan.class));
        cards.add(new SetCardInfo("Sacred Foundry", 256, Rarity.RARE, mage.cards.s.SacredFoundry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sacred Foundry", 282, Rarity.RARE, mage.cards.s.SacredFoundry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sacred Foundry", 377, Rarity.RARE, mage.cards.s.SacredFoundry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sami, Ship's Engineer", 225, Rarity.UNCOMMON, mage.cards.s.SamiShipsEngineer.class));
        cards.add(new SetCardInfo("Shattered Wings", 206, Rarity.COMMON, mage.cards.s.ShatteredWings.class));
        cards.add(new SetCardInfo("Singularity Rupture", 228, Rarity.RARE, mage.cards.s.SingularityRupture.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Singularity Rupture", 350, Rarity.RARE, mage.cards.s.SingularityRupture.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Singularity Rupture", 398, Rarity.RARE, mage.cards.s.SingularityRupture.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sothera, the Supervoid", 115, Rarity.MYTHIC, mage.cards.s.SotheraTheSupervoid.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sothera, the Supervoid", 360, Rarity.MYTHIC, mage.cards.s.SotheraTheSupervoid.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sothera, the Supervoid", 382, Rarity.MYTHIC, mage.cards.s.SotheraTheSupervoid.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sothera, the Supervoid", 386, Rarity.MYTHIC, mage.cards.s.SotheraTheSupervoid.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Starbreach Whale", 77, Rarity.COMMON, mage.cards.s.StarbreachWhale.class));
        cards.add(new SetCardInfo("Starfighter Pilot", 38, Rarity.COMMON, mage.cards.s.StarfighterPilot.class));
        cards.add(new SetCardInfo("Stomping Ground", 258, Rarity.RARE, mage.cards.s.StompingGround.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stomping Ground", 283, Rarity.RARE, mage.cards.s.StompingGround.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stomping Ground", 378, Rarity.RARE, mage.cards.s.StompingGround.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sunset Saboteur", 116, Rarity.RARE, mage.cards.s.SunsetSaboteur.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sunset Saboteur", 334, Rarity.RARE, mage.cards.s.SunsetSaboteur.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 264, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 271, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 272, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 369, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Syr Vondam, the Lucent", 232, Rarity.UNCOMMON, mage.cards.s.SyrVondamTheLucent.class));
        cards.add(new SetCardInfo("Tannuk, Memorial Ensign", 233, Rarity.UNCOMMON, mage.cards.t.TannukMemorialEnsign.class));
        cards.add(new SetCardInfo("Temporal Intervention", 120, Rarity.COMMON, mage.cards.t.TemporalIntervention.class));
        cards.add(new SetCardInfo("Tezzeret, Cruel Captain", 2, Rarity.MYTHIC, mage.cards.t.TezzeretCruelCaptain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tezzeret, Cruel Captain", 287, Rarity.MYTHIC, mage.cards.t.TezzeretCruelCaptain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Seriema", 323, Rarity.RARE, mage.cards.t.TheSeriema.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Seriema", 35, Rarity.RARE, mage.cards.t.TheSeriema.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thrumming Hivepool", 247, Rarity.RARE, mage.cards.t.ThrummingHivepool.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thrumming Hivepool", 356, Rarity.RARE, mage.cards.t.ThrummingHivepool.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tragic Trajectory", 122, Rarity.UNCOMMON, mage.cards.t.TragicTrajectory.class));
        cards.add(new SetCardInfo("Unravel", 83, Rarity.UNCOMMON, mage.cards.u.Unravel.class));
        cards.add(new SetCardInfo("Uthros, Titanic Godcore", 260, Rarity.MYTHIC, mage.cards.u.UthrosTitanicGodcore.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Uthros, Titanic Godcore", 285, Rarity.MYTHIC, mage.cards.u.UthrosTitanicGodcore.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Uthros, Titanic Godcore", 380, Rarity.MYTHIC, mage.cards.u.UthrosTitanicGodcore.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Virulent Silencer", 248, Rarity.UNCOMMON, mage.cards.v.VirulentSilencer.class));
        cards.add(new SetCardInfo("Voidforged Titan", 125, Rarity.UNCOMMON, mage.cards.v.VoidforgedTitan.class));
        cards.add(new SetCardInfo("Watery Grave", 261, Rarity.RARE, mage.cards.w.WateryGrave.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Watery Grave", 286, Rarity.RARE, mage.cards.w.WateryGrave.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Watery Grave", 381, Rarity.RARE, mage.cards.w.WateryGrave.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wurmwall Sweeper", 249, Rarity.COMMON, mage.cards.w.WurmwallSweeper.class));

        cards.removeIf(setCardInfo -> unfinished.contains(setCardInfo.getName()));
    }
}
