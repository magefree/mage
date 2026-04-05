package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.Arrays;
import java.util.List;

/**
 * @author TheElk801
 */
public final class SecretsOfStrixhaven extends ExpansionSet {

    private static final List<String> unfinished = Arrays.asList("Abigale, Poet Laureate", "Campus Composer", "Emeritus of Conflict", "Emeritus of Ideation", "Grave Researcher", "Joined Researchers", "Kirol, History Buff", "Landscape Painter", "Lluwen, Exchange Student", "Maelstrom Artisan", "Sanar, Unfinished Genius", "Spiritcall Enthusiast", "Studious First-Year", "Tam, Observant Sequencer");
    private static final SecretsOfStrixhaven instance = new SecretsOfStrixhaven();

    public static SecretsOfStrixhaven getInstance() {
        return instance;
    }

    private SecretsOfStrixhaven() {
        super("Secrets of Strixhaven", "SOS", ExpansionSet.buildDate(2026, 4, 24), SetType.EXPANSION);
        this.blockName = "Secrets of Strixhaven"; // for sorting in GUI
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Abigale, Poet Laureate", 170, Rarity.UNCOMMON, mage.cards.a.AbigalePoetLaureate.class));
        cards.add(new SetCardInfo("Arcane Omens", 73, Rarity.UNCOMMON, mage.cards.a.ArcaneOmens.class));
        cards.add(new SetCardInfo("Archaic's Agony", 107, Rarity.UNCOMMON, mage.cards.a.ArchaicsAgony.class));
        cards.add(new SetCardInfo("Artistic Process", 108, Rarity.UNCOMMON, mage.cards.a.ArtisticProcess.class));
        cards.add(new SetCardInfo("Banishing Betrayal", 38, Rarity.COMMON, mage.cards.b.BanishingBetrayal.class));
        cards.add(new SetCardInfo("Berta, Wise Extrapolator", 175, Rarity.RARE, mage.cards.b.BertaWiseExtrapolator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Berta, Wise Extrapolator", 346, Rarity.RARE, mage.cards.b.BertaWiseExtrapolator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Biblioplex Tomekeeper", 247, Rarity.COMMON, mage.cards.b.BiblioplexTomekeeper.class));
        cards.add(new SetCardInfo("Bogwater Lumaret", 177, Rarity.COMMON, mage.cards.b.BogwaterLumaret.class));
        cards.add(new SetCardInfo("Campus Composer", 40, Rarity.UNCOMMON, mage.cards.c.CampusComposer.class));
        cards.add(new SetCardInfo("Colorstorm Stallion", 180, Rarity.RARE, mage.cards.c.ColorstormStallion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Colorstorm Stallion", 299, Rarity.RARE, mage.cards.c.ColorstormStallion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Conciliator's Duelist", 182, Rarity.RARE, mage.cards.c.ConciliatorsDuelist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Conciliator's Duelist", 348, Rarity.RARE, mage.cards.c.ConciliatorsDuelist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cuboid Colony", 183, Rarity.UNCOMMON, mage.cards.c.CuboidColony.class));
        cards.add(new SetCardInfo("Daydream", 9, Rarity.UNCOMMON, mage.cards.d.Daydream.class));
        cards.add(new SetCardInfo("Deathcap Glade", 253, Rarity.RARE, mage.cards.d.DeathcapGlade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deathcap Glade", 301, Rarity.RARE, mage.cards.d.DeathcapGlade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Diary of Dreams", 248, Rarity.UNCOMMON, mage.cards.d.DiaryOfDreams.class));
        cards.add(new SetCardInfo("Dreamroot Cascade", 254, Rarity.RARE, mage.cards.d.DreamrootCascade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dreamroot Cascade", 302, Rarity.RARE, mage.cards.d.DreamrootCascade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Duel Tactics", 112, Rarity.UNCOMMON, mage.cards.d.DuelTactics.class));
        cards.add(new SetCardInfo("Elemental Mascot", 185, Rarity.COMMON, mage.cards.e.ElementalMascot.class));
        cards.add(new SetCardInfo("Emeritus of Ideation", 306, Rarity.MYTHIC, mage.cards.e.EmeritusOfIdeation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Emeritus of Ideation", 315, Rarity.MYTHIC, mage.cards.e.EmeritusOfIdeation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Emeritus of Ideation", 45, Rarity.MYTHIC, mage.cards.e.EmeritusOfIdeation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Erode", 15, Rarity.RARE, mage.cards.e.Erode.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Erode", 310, Rarity.RARE, mage.cards.e.Erode.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Exhibition Tidecaller", 316, Rarity.RARE, mage.cards.e.ExhibitionTidecaller.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Exhibition Tidecaller", 48, Rarity.RARE, mage.cards.e.ExhibitionTidecaller.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fields of Strife", 255, Rarity.COMMON, mage.cards.f.FieldsOfStrife.class));
        cards.add(new SetCardInfo("Forest", 271, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forest", 280, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 281, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forum of Amity", 256, Rarity.COMMON, mage.cards.f.ForumOfAmity.class));
        cards.add(new SetCardInfo("Fractal Mascot", 189, Rarity.COMMON, mage.cards.f.FractalMascot.class));
        cards.add(new SetCardInfo("Garrison Excavator", 116, Rarity.UNCOMMON, mage.cards.g.GarrisonExcavator.class));
        cards.add(new SetCardInfo("Graduation Day", 16, Rarity.UNCOMMON, mage.cards.g.GraduationDay.class));
        cards.add(new SetCardInfo("Grapple with Death", 192, Rarity.COMMON, mage.cards.g.GrappleWithDeath.class));
        cards.add(new SetCardInfo("Group Project", 17, Rarity.UNCOMMON, mage.cards.g.GroupProject.class));
        cards.add(new SetCardInfo("Hardened Academic", 194, Rarity.RARE, mage.cards.h.HardenedAcademic.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hardened Academic", 351, Rarity.RARE, mage.cards.h.HardenedAcademic.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Heated Argument", 118, Rarity.COMMON, mage.cards.h.HeatedArgument.class));
        cards.add(new SetCardInfo("Inkling Mascot", 196, Rarity.COMMON, mage.cards.i.InklingMascot.class));
        cards.add(new SetCardInfo("Island", 268, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 274, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 275, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Joined Researchers", 23, Rarity.RARE, mage.cards.j.JoinedResearchers.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Joined Researchers", 312, Rarity.RARE, mage.cards.j.JoinedResearchers.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Landscape Painter", 56, Rarity.COMMON, mage.cards.l.LandscapePainter.class));
        cards.add(new SetCardInfo("Lluwen, Exchange Student", 199, Rarity.UNCOMMON, mage.cards.l.LluwenExchangeStudent.class));
        cards.add(new SetCardInfo("Lorehold Charm", 200, Rarity.UNCOMMON, mage.cards.l.LoreholdCharm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lorehold Charm", 363, Rarity.UNCOMMON, mage.cards.l.LoreholdCharm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lumaret's Favor", 153, Rarity.UNCOMMON, mage.cards.l.LumaretsFavor.class));
        cards.add(new SetCardInfo("Maelstrom Artisan", 122, Rarity.RARE, mage.cards.m.MaelstromArtisan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Maelstrom Artisan", 334, Rarity.RARE, mage.cards.m.MaelstromArtisan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mathemagics", 320, Rarity.MYTHIC, mage.cards.m.Mathemagics.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mathemagics", 58, Rarity.MYTHIC, mage.cards.m.Mathemagics.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Molten Note", 204, Rarity.UNCOMMON, mage.cards.m.MoltenNote.class));
        cards.add(new SetCardInfo("Molten-Core Maestro", 125, Rarity.RARE, mage.cards.m.MoltenCoreMaestro.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Molten-Core Maestro", 335, Rarity.RARE, mage.cards.m.MoltenCoreMaestro.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 270, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 278, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 279, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Muse Seeker", 60, Rarity.UNCOMMON, mage.cards.m.MuseSeeker.class));
        cards.add(new SetCardInfo("Muse's Encouragement", 61, Rarity.COMMON, mage.cards.m.MusesEncouragement.class));
        cards.add(new SetCardInfo("Old-Growth Educator", 207, Rarity.UNCOMMON, mage.cards.o.OldGrowthEducator.class));
        cards.add(new SetCardInfo("Oracle's Restoration", 156, Rarity.COMMON, mage.cards.o.OraclesRestoration.class));
        cards.add(new SetCardInfo("Orysa, Tide Choreographer", 62, Rarity.UNCOMMON, mage.cards.o.OrysaTideChoreographer.class));
        cards.add(new SetCardInfo("Paradox Gardens", 258, Rarity.COMMON, mage.cards.p.ParadoxGardens.class));
        cards.add(new SetCardInfo("Pensive Professor", 321, Rarity.RARE, mage.cards.p.PensiveProfessor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pensive Professor", 63, Rarity.RARE, mage.cards.p.PensiveProfessor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pest Mascot", 209, Rarity.COMMON, mage.cards.p.PestMascot.class));
        cards.add(new SetCardInfo("Plains", 267, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 272, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 273, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prismari Charm", 211, Rarity.UNCOMMON, mage.cards.p.PrismariCharm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prismari Charm", 364, Rarity.UNCOMMON, mage.cards.p.PrismariCharm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prismari, the Inspiration", 212, Rarity.MYTHIC, mage.cards.p.PrismariTheInspiration.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prismari, the Inspiration", 285, Rarity.MYTHIC, mage.cards.p.PrismariTheInspiration.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Procrastinate", 64, Rarity.COMMON, mage.cards.p.Procrastinate.class));
        cards.add(new SetCardInfo("Professor Dellian Fel", 214, Rarity.MYTHIC, mage.cards.p.ProfessorDellianFel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Professor Dellian Fel", 283, Rarity.MYTHIC, mage.cards.p.ProfessorDellianFel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pull from the Grave", 95, Rarity.COMMON, mage.cards.p.PullFromTheGrave.class));
        cards.add(new SetCardInfo("Rancorous Archaic", 2, Rarity.COMMON, mage.cards.r.RancorousArchaic.class));
        cards.add(new SetCardInfo("Rapturous Moment", 219, Rarity.UNCOMMON, mage.cards.r.RapturousMoment.class));
        cards.add(new SetCardInfo("Seize the Spoils", 129, Rarity.COMMON, mage.cards.s.SeizeTheSpoils.class));
        cards.add(new SetCardInfo("Shattered Acolyte", 31, Rarity.COMMON, mage.cards.s.ShatteredAcolyte.class));
        cards.add(new SetCardInfo("Shattered Sanctum", 260, Rarity.RARE, mage.cards.s.ShatteredSanctum.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shattered Sanctum", 303, Rarity.RARE, mage.cards.s.ShatteredSanctum.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Silverquill Charm", 225, Rarity.UNCOMMON, mage.cards.s.SilverquillCharm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Silverquill Charm", 366, Rarity.UNCOMMON, mage.cards.s.SilverquillCharm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Skycoach Waypoint", 261, Rarity.UNCOMMON, mage.cards.s.SkycoachWaypoint.class));
        cards.add(new SetCardInfo("Spectacle Summit", 262, Rarity.COMMON, mage.cards.s.SpectacleSummit.class));
        cards.add(new SetCardInfo("Spectacular Skywhale", 229, Rarity.UNCOMMON, mage.cards.s.SpectacularSkywhale.class));
        cards.add(new SetCardInfo("Spirit Mascot", 230, Rarity.COMMON, mage.cards.s.SpiritMascot.class));
        cards.add(new SetCardInfo("Spiritcall Enthusiast", 33, Rarity.UNCOMMON, mage.cards.s.SpiritcallEnthusiast.class));
        cards.add(new SetCardInfo("Splatter Technique", 231, Rarity.RARE, mage.cards.s.SplatterTechnique.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Splatter Technique", 356, Rarity.RARE, mage.cards.s.SplatterTechnique.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stormcarved Coast", 263, Rarity.RARE, mage.cards.s.StormcarvedCoast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stormcarved Coast", 304, Rarity.RARE, mage.cards.s.StormcarvedCoast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stress Dream", 235, Rarity.UNCOMMON, mage.cards.s.StressDream.class));
        cards.add(new SetCardInfo("Studious First-Year", 162, Rarity.COMMON, mage.cards.s.StudiousFirstYear.class));
        cards.add(new SetCardInfo("Sundown Pass", 264, Rarity.RARE, mage.cards.s.SundownPass.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sundown Pass", 305, Rarity.RARE, mage.cards.s.SundownPass.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 269, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 276, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 277, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tam, Observant Sequencer", 237, Rarity.UNCOMMON, mage.cards.t.TamObservantSequencer.class));
        cards.add(new SetCardInfo("Teacher's Pest", 238, Rarity.UNCOMMON, mage.cards.t.TeachersPest.class));
        cards.add(new SetCardInfo("Terramorphic Expanse", 265, Rarity.COMMON, mage.cards.t.TerramorphicExpanse.class));
        cards.add(new SetCardInfo("Titan's Grave", 266, Rarity.COMMON, mage.cards.t.TitansGrave.class));
        cards.add(new SetCardInfo("Together as One", 307, Rarity.RARE, mage.cards.t.TogetherAsOne.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Together as One", 4, Rarity.RARE, mage.cards.t.TogetherAsOne.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Traumatic Critique", 239, Rarity.RARE, mage.cards.t.TraumaticCritique.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Traumatic Critique", 358, Rarity.RARE, mage.cards.t.TraumaticCritique.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wisdom of Ages", 323, Rarity.RARE, mage.cards.w.WisdomOfAges.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wisdom of Ages", 368, Rarity.RARE, mage.cards.w.WisdomOfAges.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wisdom of Ages", 71, Rarity.RARE, mage.cards.w.WisdomOfAges.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Witherbloom Charm", 244, Rarity.UNCOMMON, mage.cards.w.WitherbloomCharm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Witherbloom Charm", 367, Rarity.UNCOMMON, mage.cards.w.WitherbloomCharm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Witherbloom, the Balancer", 245, Rarity.MYTHIC, mage.cards.w.WitherbloomTheBalancer.class));
        cards.add(new SetCardInfo("Withering Curse", 105, Rarity.MYTHIC, mage.cards.w.WitheringCurse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Withering Curse", 330, Rarity.MYTHIC, mage.cards.w.WitheringCurse.class, NON_FULL_USE_VARIOUS));

        cards.removeIf(setCardInfo -> unfinished.contains(setCardInfo.getName()));
    }
}
