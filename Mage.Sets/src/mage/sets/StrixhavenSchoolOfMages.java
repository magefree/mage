package mage.sets;

import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.collation.BoosterCollator;
import mage.collation.BoosterStructure;
import mage.collation.CardRun;
import mage.collation.RarityConfiguration;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.constants.SubType;
import mage.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TheElk801
 */
public final class StrixhavenSchoolOfMages extends ExpansionSet {

    private static final StrixhavenSchoolOfMages instance = new StrixhavenSchoolOfMages();

    public static StrixhavenSchoolOfMages getInstance() {
        return instance;
    }

    private StrixhavenSchoolOfMages() {
        super("Strixhaven: School of Mages", "STX", ExpansionSet.buildDate(2021, 4, 23), SetType.EXPANSION);
        this.blockName = "Strixhaven: School of Mages";
        this.hasBoosters = true;
        this.hasBasicLands = true;
        this.numBoosterCommon = 9;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 7.4;
        this.maxCardNumberInBooster = 275;

        cards.add(new SetCardInfo("Academic Dispute", 91, Rarity.UNCOMMON, mage.cards.a.AcademicDispute.class));
        cards.add(new SetCardInfo("Academic Probation", 287, Rarity.RARE, mage.cards.a.AcademicProbation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Academic Probation", 7, Rarity.RARE, mage.cards.a.AcademicProbation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Access Tunnel", 262, Rarity.UNCOMMON, mage.cards.a.AccessTunnel.class));
        cards.add(new SetCardInfo("Accomplished Alchemist", 119, Rarity.RARE, mage.cards.a.AccomplishedAlchemist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Accomplished Alchemist", 314, Rarity.RARE, mage.cards.a.AccomplishedAlchemist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aether Helix", 162, Rarity.UNCOMMON, mage.cards.a.AetherHelix.class));
        cards.add(new SetCardInfo("Ageless Guardian", 8, Rarity.COMMON, mage.cards.a.AgelessGuardian.class));
        cards.add(new SetCardInfo("Arcane Subtraction", 36, Rarity.COMMON, mage.cards.a.ArcaneSubtraction.class));
        cards.add(new SetCardInfo("Archmage Emeritus", 295, Rarity.RARE, mage.cards.a.ArchmageEmeritus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Archmage Emeritus", 37, Rarity.RARE, mage.cards.a.ArchmageEmeritus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Archmage Emeritus", 377, Rarity.RARE, mage.cards.a.ArchmageEmeritus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Archway Commons", 263, Rarity.COMMON, mage.cards.a.ArchwayCommons.class));
        cards.add(new SetCardInfo("Ardent Dustspeaker", 92, Rarity.UNCOMMON, mage.cards.a.ArdentDustspeaker.class));
        cards.add(new SetCardInfo("Arrogant Poet", 63, Rarity.COMMON, mage.cards.a.ArrogantPoet.class));
        cards.add(new SetCardInfo("Augmenter Pugilist", 147, Rarity.RARE, mage.cards.a.AugmenterPugilist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Augmenter Pugilist", 321, Rarity.RARE, mage.cards.a.AugmenterPugilist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Baleful Mastery", 301, Rarity.RARE, mage.cards.b.BalefulMastery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Baleful Mastery", 64, Rarity.RARE, mage.cards.b.BalefulMastery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Basic Conjuration", 120, Rarity.RARE, mage.cards.b.BasicConjuration.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Basic Conjuration", 315, Rarity.RARE, mage.cards.b.BasicConjuration.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bayou Groff", 121, Rarity.COMMON, mage.cards.b.BayouGroff.class));
        cards.add(new SetCardInfo("Beaming Defiance", 9, Rarity.COMMON, mage.cards.b.BeamingDefiance.class));
        cards.add(new SetCardInfo("Beledros Witherbloom", 163, Rarity.MYTHIC, mage.cards.b.BeledrosWitherbloom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Beledros Witherbloom", 282, Rarity.MYTHIC, mage.cards.b.BeledrosWitherbloom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Biblioplex Assistant", 251, Rarity.COMMON, mage.cards.b.BiblioplexAssistant.class));
        cards.add(new SetCardInfo("Big Play", 122, Rarity.COMMON, mage.cards.b.BigPlay.class));
        cards.add(new SetCardInfo("Biomathematician", 164, Rarity.COMMON, mage.cards.b.Biomathematician.class));
        cards.add(new SetCardInfo("Blade Historian", 165, Rarity.RARE, mage.cards.b.BladeHistorian.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blade Historian", 334, Rarity.RARE, mage.cards.b.BladeHistorian.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blex, Vexing Pest", 148, Rarity.MYTHIC, mage.cards.b.BlexVexingPest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blex, Vexing Pest", 322, Rarity.MYTHIC, mage.cards.b.BlexVexingPest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blood Age General", 93, Rarity.COMMON, mage.cards.b.BloodAgeGeneral.class));
        cards.add(new SetCardInfo("Blood Researcher", 166, Rarity.COMMON, mage.cards.b.BloodResearcher.class));
        cards.add(new SetCardInfo("Blot Out the Sky", 167, Rarity.MYTHIC, mage.cards.b.BlotOutTheSky.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blot Out the Sky", 335, Rarity.MYTHIC, mage.cards.b.BlotOutTheSky.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Body of Research", 168, Rarity.MYTHIC, mage.cards.b.BodyOfResearch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Body of Research", 336, Rarity.MYTHIC, mage.cards.b.BodyOfResearch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bookwurm", 123, Rarity.UNCOMMON, mage.cards.b.Bookwurm.class));
        cards.add(new SetCardInfo("Brackish Trudge", 65, Rarity.UNCOMMON, mage.cards.b.BrackishTrudge.class));
        cards.add(new SetCardInfo("Burrog Befuddler", 38, Rarity.COMMON, mage.cards.b.BurrogBefuddler.class));
        cards.add(new SetCardInfo("Bury in Books", 39, Rarity.COMMON, mage.cards.b.BuryInBooks.class));
        cards.add(new SetCardInfo("Callous Bloodmage", 302, Rarity.RARE, mage.cards.c.CallousBloodmage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Callous Bloodmage", 66, Rarity.RARE, mage.cards.c.CallousBloodmage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Campus Guide", 252, Rarity.COMMON, mage.cards.c.CampusGuide.class));
        cards.add(new SetCardInfo("Charge Through", 124, Rarity.COMMON, mage.cards.c.ChargeThrough.class));
        cards.add(new SetCardInfo("Clever Lumimancer", 10, Rarity.UNCOMMON, mage.cards.c.CleverLumimancer.class));
        cards.add(new SetCardInfo("Closing Statement", 169, Rarity.UNCOMMON, mage.cards.c.ClosingStatement.class));
        cards.add(new SetCardInfo("Codie, Vociferous Codex", 253, Rarity.RARE, mage.cards.c.CodieVociferousCodex.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Codie, Vociferous Codex", 357, Rarity.RARE, mage.cards.c.CodieVociferousCodex.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cogwork Archivist", 254, Rarity.COMMON, mage.cards.c.CogworkArchivist.class));
        cards.add(new SetCardInfo("Combat Professor", 11, Rarity.COMMON, mage.cards.c.CombatProfessor.class));
        cards.add(new SetCardInfo("Confront the Past", 303, Rarity.RARE, mage.cards.c.ConfrontThePast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Confront the Past", 67, Rarity.RARE, mage.cards.c.ConfrontThePast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Conspiracy Theorist", 307, Rarity.RARE, mage.cards.c.ConspiracyTheorist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Conspiracy Theorist", 94, Rarity.RARE, mage.cards.c.ConspiracyTheorist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Containment Breach", 125, Rarity.UNCOMMON, mage.cards.c.ContainmentBreach.class));
        cards.add(new SetCardInfo("Crackle with Power", 308, Rarity.MYTHIC, mage.cards.c.CrackleWithPower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crackle with Power", 95, Rarity.MYTHIC, mage.cards.c.CrackleWithPower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cram Session", 170, Rarity.COMMON, mage.cards.c.CramSession.class));
        cards.add(new SetCardInfo("Creative Outburst", 171, Rarity.UNCOMMON, mage.cards.c.CreativeOutburst.class));
        cards.add(new SetCardInfo("Crushing Disappointment", 68, Rarity.COMMON, mage.cards.c.CrushingDisappointment.class));
        cards.add(new SetCardInfo("Culling Ritual", 172, Rarity.RARE, mage.cards.c.CullingRitual.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Culling Ritual", 337, Rarity.RARE, mage.cards.c.CullingRitual.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Culmination of Studies", 173, Rarity.RARE, mage.cards.c.CulminationOfStudies.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Culmination of Studies", 338, Rarity.RARE, mage.cards.c.CulminationOfStudies.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Curate", 40, Rarity.COMMON, mage.cards.c.Curate.class));
        cards.add(new SetCardInfo("Daemogoth Titan", 174, Rarity.RARE, mage.cards.d.DaemogothTitan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Daemogoth Titan", 339, Rarity.RARE, mage.cards.d.DaemogothTitan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Daemogoth Woe-Eater", 175, Rarity.UNCOMMON, mage.cards.d.DaemogothWoeEater.class));
        cards.add(new SetCardInfo("Deadly Brew", 176, Rarity.UNCOMMON, mage.cards.d.DeadlyBrew.class));
        cards.add(new SetCardInfo("Decisive Denial", 177, Rarity.UNCOMMON, mage.cards.d.DecisiveDenial.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Decisive Denial", 382, Rarity.UNCOMMON, mage.cards.d.DecisiveDenial.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Defend the Campus", 12, Rarity.COMMON, mage.cards.d.DefendTheCampus.class));
        cards.add(new SetCardInfo("Detention Vortex", 13, Rarity.UNCOMMON, mage.cards.d.DetentionVortex.class));
        cards.add(new SetCardInfo("Devastating Mastery", 14, Rarity.RARE, mage.cards.d.DevastatingMastery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Devastating Mastery", 288, Rarity.RARE, mage.cards.d.DevastatingMastery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Devouring Tendrils", 126, Rarity.UNCOMMON, mage.cards.d.DevouringTendrils.class));
        cards.add(new SetCardInfo("Dina, Soul Steeper", 178, Rarity.UNCOMMON, mage.cards.d.DinaSoulSteeper.class));
        cards.add(new SetCardInfo("Divide by Zero", 41, Rarity.UNCOMMON, mage.cards.d.DivideByZero.class));
        cards.add(new SetCardInfo("Double Major", 179, Rarity.RARE, mage.cards.d.DoubleMajor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Double Major", 340, Rarity.RARE, mage.cards.d.DoubleMajor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Draconic Intervention", 309, Rarity.RARE, mage.cards.d.DraconicIntervention.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Draconic Intervention", 96, Rarity.RARE, mage.cards.d.DraconicIntervention.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dragon's Approach", 97, Rarity.COMMON, mage.cards.d.DragonsApproach.class));
        cards.add(new SetCardInfo("Dragonsguard Elite", 127, Rarity.RARE, mage.cards.d.DragonsguardElite.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dragonsguard Elite", 316, Rarity.RARE, mage.cards.d.DragonsguardElite.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dragonsguard Elite", 376, Rarity.RARE, mage.cards.d.DragonsguardElite.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dramatic Finale", 180, Rarity.RARE, mage.cards.d.DramaticFinale.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dramatic Finale", 341, Rarity.RARE, mage.cards.d.DramaticFinale.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dream Strix", 296, Rarity.RARE, mage.cards.d.DreamStrix.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dream Strix", 42, Rarity.RARE, mage.cards.d.DreamStrix.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dueling Coach", 15, Rarity.UNCOMMON, mage.cards.d.DuelingCoach.class));
        cards.add(new SetCardInfo("Eager First-Year", 16, Rarity.COMMON, mage.cards.e.EagerFirstYear.class));
        cards.add(new SetCardInfo("Ecological Appreciation", 128, Rarity.MYTHIC, mage.cards.e.EcologicalAppreciation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ecological Appreciation", 317, Rarity.MYTHIC, mage.cards.e.EcologicalAppreciation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Efreet Flamepainter", 310, Rarity.RARE, mage.cards.e.EfreetFlamepainter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Efreet Flamepainter", 98, Rarity.RARE, mage.cards.e.EfreetFlamepainter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Elemental Expressionist", 181, Rarity.RARE, mage.cards.e.ElementalExpressionist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Elemental Expressionist", 342, Rarity.RARE, mage.cards.e.ElementalExpressionist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Elemental Masterpiece", 182, Rarity.COMMON, mage.cards.e.ElementalMasterpiece.class));
        cards.add(new SetCardInfo("Elemental Summoning", 183, Rarity.COMMON, mage.cards.e.ElementalSummoning.class));
        cards.add(new SetCardInfo("Elite Spellbinder", 17, Rarity.RARE, mage.cards.e.EliteSpellbinder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Elite Spellbinder", 289, Rarity.RARE, mage.cards.e.EliteSpellbinder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Emergent Sequence", 129, Rarity.UNCOMMON, mage.cards.e.EmergentSequence.class));
        cards.add(new SetCardInfo("Enthusiastic Study", 99, Rarity.COMMON, mage.cards.e.EnthusiasticStudy.class));
        cards.add(new SetCardInfo("Environmental Sciences", 1, Rarity.COMMON, mage.cards.e.EnvironmentalSciences.class));
        cards.add(new SetCardInfo("Essence Infusion", 69, Rarity.COMMON, mage.cards.e.EssenceInfusion.class));
        cards.add(new SetCardInfo("Eureka Moment", 184, Rarity.COMMON, mage.cards.e.EurekaMoment.class));
        cards.add(new SetCardInfo("Excavated Wall", 255, Rarity.COMMON, mage.cards.e.ExcavatedWall.class));
        cards.add(new SetCardInfo("Exhilarating Elocution", 185, Rarity.COMMON, mage.cards.e.ExhilaratingElocution.class));
        cards.add(new SetCardInfo("Expanded Anatomy", 2, Rarity.COMMON, mage.cards.e.ExpandedAnatomy.class));
        cards.add(new SetCardInfo("Expel", 18, Rarity.COMMON, mage.cards.e.Expel.class));
        cards.add(new SetCardInfo("Explosive Welcome", 100, Rarity.UNCOMMON, mage.cards.e.ExplosiveWelcome.class));
        cards.add(new SetCardInfo("Exponential Growth", 130, Rarity.RARE, mage.cards.e.ExponentialGrowth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Exponential Growth", 318, Rarity.RARE, mage.cards.e.ExponentialGrowth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Expressive Iteration", 186, Rarity.UNCOMMON, mage.cards.e.ExpressiveIteration.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Expressive Iteration", 379, Rarity.UNCOMMON, mage.cards.e.ExpressiveIteration.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Extus, Oriq Overlord", 149, Rarity.MYTHIC, mage.cards.e.ExtusOriqOverlord.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Extus, Oriq Overlord", 323, Rarity.MYTHIC, mage.cards.e.ExtusOriqOverlord.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eyetwitch", 70, Rarity.UNCOMMON, mage.cards.e.Eyetwitch.class));
        cards.add(new SetCardInfo("Fervent Mastery", 101, Rarity.RARE, mage.cards.f.FerventMastery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fervent Mastery", 311, Rarity.RARE, mage.cards.f.FerventMastery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Field Trip", 131, Rarity.COMMON, mage.cards.f.FieldTrip.class));
        cards.add(new SetCardInfo("First Day of Class", 102, Rarity.COMMON, mage.cards.f.FirstDayOfClass.class));
        cards.add(new SetCardInfo("Flamescroll Celebrant", 150, Rarity.RARE, mage.cards.f.FlamescrollCelebrant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Flamescroll Celebrant", 324, Rarity.RARE, mage.cards.f.FlamescrollCelebrant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Flunk", 71, Rarity.UNCOMMON, mage.cards.f.Flunk.class));
        cards.add(new SetCardInfo("Forest", 374, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 375, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fortifying Draught", 132, Rarity.UNCOMMON, mage.cards.f.FortifyingDraught.class));
        cards.add(new SetCardInfo("Fractal Summoning", 187, Rarity.COMMON, mage.cards.f.FractalSummoning.class));
        cards.add(new SetCardInfo("Fracture", 188, Rarity.UNCOMMON, mage.cards.f.Fracture.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fracture", 378, Rarity.UNCOMMON, mage.cards.f.Fracture.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frost Trickster", 43, Rarity.COMMON, mage.cards.f.FrostTrickster.class));
        cards.add(new SetCardInfo("Frostboil Snarl", 265, Rarity.RARE, mage.cards.f.FrostboilSnarl.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frostboil Snarl", 360, Rarity.RARE, mage.cards.f.FrostboilSnarl.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fuming Effigy", 103, Rarity.COMMON, mage.cards.f.FumingEffigy.class));
        cards.add(new SetCardInfo("Furycalm Snarl", 266, Rarity.RARE, mage.cards.f.FurycalmSnarl.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Furycalm Snarl", 361, Rarity.RARE, mage.cards.f.FurycalmSnarl.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Galazeth Prismari", 189, Rarity.MYTHIC, mage.cards.g.GalazethPrismari.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Galazeth Prismari", 281, Rarity.MYTHIC, mage.cards.g.GalazethPrismari.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gnarled Professor", 133, Rarity.RARE, mage.cards.g.GnarledProfessor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gnarled Professor", 319, Rarity.RARE, mage.cards.g.GnarledProfessor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Go Blank", 72, Rarity.UNCOMMON, mage.cards.g.GoBlank.class));
        cards.add(new SetCardInfo("Golden Ratio", 190, Rarity.UNCOMMON, mage.cards.g.GoldenRatio.class));
        cards.add(new SetCardInfo("Grinning Ignus", 104, Rarity.UNCOMMON, mage.cards.g.GrinningIgnus.class));
        cards.add(new SetCardInfo("Guiding Voice", 19, Rarity.COMMON, mage.cards.g.GuidingVoice.class));
        cards.add(new SetCardInfo("Hall Monitor", 105, Rarity.UNCOMMON, mage.cards.h.HallMonitor.class));
        cards.add(new SetCardInfo("Hall of Oracles", 267, Rarity.RARE, mage.cards.h.HallOfOracles.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hall of Oracles", 362, Rarity.RARE, mage.cards.h.HallOfOracles.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Harness Infinity", 191, Rarity.MYTHIC, mage.cards.h.HarnessInfinity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Harness Infinity", 343, Rarity.MYTHIC, mage.cards.h.HarnessInfinity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Heated Debate", 106, Rarity.COMMON, mage.cards.h.HeatedDebate.class));
        cards.add(new SetCardInfo("Hofri Ghostforge", 192, Rarity.MYTHIC, mage.cards.h.HofriGhostforge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hofri Ghostforge", 344, Rarity.MYTHIC, mage.cards.h.HofriGhostforge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Honor Troll", 134, Rarity.UNCOMMON, mage.cards.h.HonorTroll.class));
        cards.add(new SetCardInfo("Humiliate", 193, Rarity.UNCOMMON, mage.cards.h.Humiliate.class));
        cards.add(new SetCardInfo("Hunt for Specimens", 73, Rarity.COMMON, mage.cards.h.HuntForSpecimens.class));
        cards.add(new SetCardInfo("Igneous Inspiration", 107, Rarity.UNCOMMON, mage.cards.i.IgneousInspiration.class));
        cards.add(new SetCardInfo("Illuminate History", 108, Rarity.RARE, mage.cards.i.IlluminateHistory.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Illuminate History", 312, Rarity.RARE, mage.cards.i.IlluminateHistory.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Illustrious Historian", 109, Rarity.COMMON, mage.cards.i.IllustriousHistorian.class));
        cards.add(new SetCardInfo("Infuse with Vitality", 194, Rarity.COMMON, mage.cards.i.InfuseWithVitality.class));
        cards.add(new SetCardInfo("Ingenious Mastery", 297, Rarity.RARE, mage.cards.i.IngeniousMastery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ingenious Mastery", 44, Rarity.RARE, mage.cards.i.IngeniousMastery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Inkling Summoning", 195, Rarity.COMMON, mage.cards.i.InklingSummoning.class));
        cards.add(new SetCardInfo("Introduction to Annihilation", 3, Rarity.COMMON, mage.cards.i.IntroductionToAnnihilation.class));
        cards.add(new SetCardInfo("Introduction to Prophecy", 4, Rarity.COMMON, mage.cards.i.IntroductionToProphecy.class));
        cards.add(new SetCardInfo("Island", 368, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 369, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jadzi, Oracle of Arcavios", 151, Rarity.MYTHIC, mage.cards.j.JadziOracleOfArcavios.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jadzi, Oracle of Arcavios", 325, Rarity.MYTHIC, mage.cards.j.JadziOracleOfArcavios.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Karok Wrangler", 135, Rarity.UNCOMMON, mage.cards.k.KarokWrangler.class));
        cards.add(new SetCardInfo("Kasmina, Enigma Sage", 196, Rarity.MYTHIC, mage.cards.k.KasminaEnigmaSage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kasmina, Enigma Sage", 279, Rarity.MYTHIC, mage.cards.k.KasminaEnigmaSage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kelpie Guide", 45, Rarity.UNCOMMON, mage.cards.k.KelpieGuide.class));
        cards.add(new SetCardInfo("Kianne, Dean of Substance", 152, Rarity.RARE, mage.cards.k.KianneDeanOfSubstance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kianne, Dean of Substance", 326, Rarity.RARE, mage.cards.k.KianneDeanOfSubstance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Killian, Ink Duelist", 197, Rarity.UNCOMMON, mage.cards.k.KillianInkDuelist.class));
        cards.add(new SetCardInfo("Lash of Malice", 74, Rarity.COMMON, mage.cards.l.LashOfMalice.class));
        cards.add(new SetCardInfo("Leech Fanatic", 75, Rarity.COMMON, mage.cards.l.LeechFanatic.class));
        cards.add(new SetCardInfo("Leonin Lightscribe", 20, Rarity.RARE, mage.cards.l.LeoninLightscribe.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Leonin Lightscribe", 290, Rarity.RARE, mage.cards.l.LeoninLightscribe.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Letter of Acceptance", 256, Rarity.COMMON, mage.cards.l.LetterOfAcceptance.class));
        cards.add(new SetCardInfo("Leyline Invocation", 136, Rarity.COMMON, mage.cards.l.LeylineInvocation.class));
        cards.add(new SetCardInfo("Lorehold Apprentice", 198, Rarity.UNCOMMON, mage.cards.l.LoreholdApprentice.class));
        cards.add(new SetCardInfo("Lorehold Campus", 268, Rarity.COMMON, mage.cards.l.LoreholdCampus.class));
        cards.add(new SetCardInfo("Lorehold Command", 199, Rarity.RARE, mage.cards.l.LoreholdCommand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lorehold Command", 345, Rarity.RARE, mage.cards.l.LoreholdCommand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lorehold Excavation", 200, Rarity.UNCOMMON, mage.cards.l.LoreholdExcavation.class));
        cards.add(new SetCardInfo("Lorehold Pledgemage", 201, Rarity.COMMON, mage.cards.l.LoreholdPledgemage.class));
        cards.add(new SetCardInfo("Maelstrom Muse", 202, Rarity.UNCOMMON, mage.cards.m.MaelstromMuse.class));
        cards.add(new SetCardInfo("Mage Duel", 137, Rarity.COMMON, mage.cards.m.MageDuel.class));
        cards.add(new SetCardInfo("Mage Hunter", 76, Rarity.UNCOMMON, mage.cards.m.MageHunter.class));
        cards.add(new SetCardInfo("Mage Hunters' Onslaught", 77, Rarity.COMMON, mage.cards.m.MageHuntersOnslaught.class));
        cards.add(new SetCardInfo("Magma Opus", 203, Rarity.MYTHIC, mage.cards.m.MagmaOpus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Magma Opus", 346, Rarity.MYTHIC, mage.cards.m.MagmaOpus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Make Your Mark", 204, Rarity.COMMON, mage.cards.m.MakeYourMark.class));
        cards.add(new SetCardInfo("Manifestation Sage", 205, Rarity.RARE, mage.cards.m.ManifestationSage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Manifestation Sage", 347, Rarity.RARE, mage.cards.m.ManifestationSage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mascot Exhibition", 285, Rarity.MYTHIC, mage.cards.m.MascotExhibition.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mascot Exhibition", 5, Rarity.MYTHIC, mage.cards.m.MascotExhibition.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mascot Interception", 110, Rarity.UNCOMMON, mage.cards.m.MascotInterception.class));
        cards.add(new SetCardInfo("Master Symmetrist", 138, Rarity.UNCOMMON, mage.cards.m.MasterSymmetrist.class));
        cards.add(new SetCardInfo("Mavinda, Students' Advocate", 21, Rarity.MYTHIC, mage.cards.m.MavindaStudentsAdvocate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mavinda, Students' Advocate", 291, Rarity.MYTHIC, mage.cards.m.MavindaStudentsAdvocate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mentor's Guidance", 46, Rarity.UNCOMMON, mage.cards.m.MentorsGuidance.class));
        cards.add(new SetCardInfo("Mercurial Transformation", 47, Rarity.UNCOMMON, mage.cards.m.MercurialTransformation.class));
        cards.add(new SetCardInfo("Mila, Crafty Companion", 153, Rarity.MYTHIC, mage.cards.m.MilaCraftyCompanion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mila, Crafty Companion", 277, Rarity.MYTHIC, mage.cards.m.MilaCraftyCompanion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Moldering Karok", 206, Rarity.COMMON, mage.cards.m.MolderingKarok.class));
        cards.add(new SetCardInfo("Mortality Spear", 207, Rarity.UNCOMMON, mage.cards.m.MortalitySpear.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mortality Spear", 380, Rarity.UNCOMMON, mage.cards.m.MortalitySpear.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 372, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 373, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Multiple Choice", 298, Rarity.RARE, mage.cards.m.MultipleChoice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Multiple Choice", 48, Rarity.RARE, mage.cards.m.MultipleChoice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Necroblossom Snarl", 269, Rarity.RARE, mage.cards.n.NecroblossomSnarl.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Necroblossom Snarl", 363, Rarity.RARE, mage.cards.n.NecroblossomSnarl.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Necrotic Fumes", 78, Rarity.UNCOMMON, mage.cards.n.NecroticFumes.class));
        cards.add(new SetCardInfo("Needlethorn Drake", 208, Rarity.COMMON, mage.cards.n.NeedlethornDrake.class));
        cards.add(new SetCardInfo("Novice Dissector", 79, Rarity.COMMON, mage.cards.n.NoviceDissector.class));
        cards.add(new SetCardInfo("Oggyar Battle-Seer", 209, Rarity.COMMON, mage.cards.o.OggyarBattleSeer.class));
        cards.add(new SetCardInfo("Oriq Loremage", 304, Rarity.RARE, mage.cards.o.OriqLoremage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Oriq Loremage", 80, Rarity.RARE, mage.cards.o.OriqLoremage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Overgrown Arch", 139, Rarity.UNCOMMON, mage.cards.o.OvergrownArch.class));
        cards.add(new SetCardInfo("Owlin Shieldmage", 210, Rarity.COMMON, mage.cards.o.OwlinShieldmage.class));
        cards.add(new SetCardInfo("Pest Summoning", 211, Rarity.COMMON, mage.cards.p.PestSummoning.class));
        cards.add(new SetCardInfo("Pestilent Cauldron", 154, Rarity.RARE, mage.cards.p.PestilentCauldron.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pestilent Cauldron", 327, Rarity.RARE, mage.cards.p.PestilentCauldron.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pigment Storm", 111, Rarity.COMMON, mage.cards.p.PigmentStorm.class));
        cards.add(new SetCardInfo("Pilgrim of the Ages", 22, Rarity.COMMON, mage.cards.p.PilgrimOfTheAges.class));
        cards.add(new SetCardInfo("Pillardrop Rescuer", 23, Rarity.COMMON, mage.cards.p.PillardropRescuer.class));
        cards.add(new SetCardInfo("Pillardrop Warden", 112, Rarity.COMMON, mage.cards.p.PillardropWarden.class));
        cards.add(new SetCardInfo("Plains", 366, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 367, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plargg, Dean of Chaos", 155, Rarity.RARE, mage.cards.p.PlarggDeanOfChaos.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plargg, Dean of Chaos", 328, Rarity.RARE, mage.cards.p.PlarggDeanOfChaos.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plumb the Forbidden", 81, Rarity.UNCOMMON, mage.cards.p.PlumbTheForbidden.class));
        cards.add(new SetCardInfo("Poet's Quill", 305, Rarity.RARE, mage.cards.p.PoetsQuill.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Poet's Quill", 82, Rarity.RARE, mage.cards.p.PoetsQuill.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pop Quiz", 49, Rarity.COMMON, mage.cards.p.PopQuiz.class));
        cards.add(new SetCardInfo("Practical Research", 212, Rarity.UNCOMMON, mage.cards.p.PracticalResearch.class));
        cards.add(new SetCardInfo("Prismari Apprentice", 213, Rarity.UNCOMMON, mage.cards.p.PrismariApprentice.class));
        cards.add(new SetCardInfo("Prismari Campus", 270, Rarity.COMMON, mage.cards.p.PrismariCampus.class));
        cards.add(new SetCardInfo("Prismari Command", 214, Rarity.RARE, mage.cards.p.PrismariCommand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prismari Command", 348, Rarity.RARE, mage.cards.p.PrismariCommand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prismari Pledgemage", 215, Rarity.COMMON, mage.cards.p.PrismariPledgemage.class));
        cards.add(new SetCardInfo("Professor Onyx", 276, Rarity.MYTHIC, mage.cards.p.ProfessorOnyx.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Professor Onyx", 83, Rarity.MYTHIC, mage.cards.p.ProfessorOnyx.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Professor of Symbology", 24, Rarity.UNCOMMON, mage.cards.p.ProfessorOfSymbology.class));
        cards.add(new SetCardInfo("Professor of Zoomancy", 140, Rarity.COMMON, mage.cards.p.ProfessorOfZoomancy.class));
        cards.add(new SetCardInfo("Professor's Warning", 84, Rarity.COMMON, mage.cards.p.ProfessorsWarning.class));
        cards.add(new SetCardInfo("Promising Duskmage", 85, Rarity.COMMON, mage.cards.p.PromisingDuskmage.class));
        cards.add(new SetCardInfo("Quandrix Apprentice", 216, Rarity.UNCOMMON, mage.cards.q.QuandrixApprentice.class));
        cards.add(new SetCardInfo("Quandrix Campus", 271, Rarity.COMMON, mage.cards.q.QuandrixCampus.class));
        cards.add(new SetCardInfo("Quandrix Command", 217, Rarity.RARE, mage.cards.q.QuandrixCommand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Quandrix Command", 349, Rarity.RARE, mage.cards.q.QuandrixCommand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Quandrix Cultivator", 218, Rarity.UNCOMMON, mage.cards.q.QuandrixCultivator.class));
        cards.add(new SetCardInfo("Quandrix Pledgemage", 219, Rarity.COMMON, mage.cards.q.QuandrixPledgemage.class));
        cards.add(new SetCardInfo("Quintorius, Field Historian", 220, Rarity.UNCOMMON, mage.cards.q.QuintoriusFieldHistorian.class));
        cards.add(new SetCardInfo("Radiant Scrollwielder", 221, Rarity.RARE, mage.cards.r.RadiantScrollwielder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Radiant Scrollwielder", 350, Rarity.RARE, mage.cards.r.RadiantScrollwielder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Reckless Amplimancer", 141, Rarity.COMMON, mage.cards.r.RecklessAmplimancer.class));
        cards.add(new SetCardInfo("Reconstruct History", 222, Rarity.UNCOMMON, mage.cards.r.ReconstructHistory.class));
        cards.add(new SetCardInfo("Reduce to Memory", 25, Rarity.UNCOMMON, mage.cards.r.ReduceToMemory.class));
        cards.add(new SetCardInfo("Reflective Golem", 257, Rarity.UNCOMMON, mage.cards.r.ReflectiveGolem.class));
        cards.add(new SetCardInfo("Reject", 50, Rarity.COMMON, mage.cards.r.Reject.class));
        cards.add(new SetCardInfo("Relic Sloth", 223, Rarity.COMMON, mage.cards.r.RelicSloth.class));
        cards.add(new SetCardInfo("Resculpt", 51, Rarity.COMMON, mage.cards.r.Resculpt.class));
        cards.add(new SetCardInfo("Retriever Phoenix", 113, Rarity.RARE, mage.cards.r.RetrieverPhoenix.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Retriever Phoenix", 313, Rarity.RARE, mage.cards.r.RetrieverPhoenix.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Returned Pastcaller", 224, Rarity.UNCOMMON, mage.cards.r.ReturnedPastcaller.class));
        cards.add(new SetCardInfo("Rip Apart", 225, Rarity.UNCOMMON, mage.cards.r.RipApart.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rip Apart", 381, Rarity.UNCOMMON, mage.cards.r.RipApart.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rise of Extus", 226, Rarity.COMMON, mage.cards.r.RiseOfExtus.class));
        cards.add(new SetCardInfo("Rootha, Mercurial Artist", 227, Rarity.UNCOMMON, mage.cards.r.RoothaMercurialArtist.class));
        cards.add(new SetCardInfo("Rowan, Scholar of Sparks", 156, Rarity.MYTHIC, mage.cards.r.RowanScholarOfSparks.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rowan, Scholar of Sparks", 278, Rarity.MYTHIC, mage.cards.r.RowanScholarOfSparks.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rushed Rebirth", 228, Rarity.RARE, mage.cards.r.RushedRebirth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rushed Rebirth", 351, Rarity.RARE, mage.cards.r.RushedRebirth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scurrid Colony", 142, Rarity.COMMON, mage.cards.s.ScurridColony.class));
        cards.add(new SetCardInfo("Secret Rendezvous", 26, Rarity.UNCOMMON, mage.cards.s.SecretRendezvous.class));
        cards.add(new SetCardInfo("Sedgemoor Witch", 306, Rarity.RARE, mage.cards.s.SedgemoorWitch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sedgemoor Witch", 86, Rarity.RARE, mage.cards.s.SedgemoorWitch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Selfless Glyphweaver", 157, Rarity.RARE, mage.cards.s.SelflessGlyphweaver.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Selfless Glyphweaver", 329, Rarity.RARE, mage.cards.s.SelflessGlyphweaver.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Semester's End", 27, Rarity.RARE, mage.cards.s.SemestersEnd.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Semester's End", 292, Rarity.RARE, mage.cards.s.SemestersEnd.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Serpentine Curve", 52, Rarity.COMMON, mage.cards.s.SerpentineCurve.class));
        cards.add(new SetCardInfo("Shadewing Laureate", 229, Rarity.UNCOMMON, mage.cards.s.ShadewingLaureate.class));
        cards.add(new SetCardInfo("Shadrix Silverquill", 230, Rarity.MYTHIC, mage.cards.s.ShadrixSilverquill.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shadrix Silverquill", 280, Rarity.MYTHIC, mage.cards.s.ShadrixSilverquill.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shaile, Dean of Radiance", 158, Rarity.RARE, mage.cards.s.ShaileDeanOfRadiance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shaile, Dean of Radiance", 330, Rarity.RARE, mage.cards.s.ShaileDeanOfRadiance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shineshadow Snarl", 272, Rarity.RARE, mage.cards.s.ShineshadowSnarl.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shineshadow Snarl", 364, Rarity.RARE, mage.cards.s.ShineshadowSnarl.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Show of Confidence", 28, Rarity.UNCOMMON, mage.cards.s.ShowOfConfidence.class));
        cards.add(new SetCardInfo("Silverquill Apprentice", 231, Rarity.UNCOMMON, mage.cards.s.SilverquillApprentice.class));
        cards.add(new SetCardInfo("Silverquill Campus", 273, Rarity.COMMON, mage.cards.s.SilverquillCampus.class));
        cards.add(new SetCardInfo("Silverquill Command", 232, Rarity.RARE, mage.cards.s.SilverquillCommand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Silverquill Command", 352, Rarity.RARE, mage.cards.s.SilverquillCommand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Silverquill Pledgemage", 233, Rarity.COMMON, mage.cards.s.SilverquillPledgemage.class));
        cards.add(new SetCardInfo("Silverquill Silencer", 234, Rarity.RARE, mage.cards.s.SilverquillSilencer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Silverquill Silencer", 353, Rarity.RARE, mage.cards.s.SilverquillSilencer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Snow Day", 53, Rarity.UNCOMMON, mage.cards.s.SnowDay.class));
        cards.add(new SetCardInfo("Solve the Equation", 54, Rarity.UNCOMMON, mage.cards.s.SolveTheEquation.class));
        cards.add(new SetCardInfo("Soothsayer Adept", 55, Rarity.COMMON, mage.cards.s.SoothsayerAdept.class));
        cards.add(new SetCardInfo("Sparring Regimen", 29, Rarity.RARE, mage.cards.s.SparringRegimen.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sparring Regimen", 293, Rarity.RARE, mage.cards.s.SparringRegimen.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spectacle Mage", 235, Rarity.COMMON, mage.cards.s.SpectacleMage.class));
        cards.add(new SetCardInfo("Specter of the Fens", 87, Rarity.COMMON, mage.cards.s.SpecterOfTheFens.class));
        cards.add(new SetCardInfo("Spell Satchel", 258, Rarity.UNCOMMON, mage.cards.s.SpellSatchel.class));
        cards.add(new SetCardInfo("Spined Karok", 143, Rarity.COMMON, mage.cards.s.SpinedKarok.class));
        cards.add(new SetCardInfo("Spirit Summoning", 236, Rarity.COMMON, mage.cards.s.SpiritSummoning.class));
        cards.add(new SetCardInfo("Spiteful Squad", 237, Rarity.COMMON, mage.cards.s.SpitefulSquad.class));
        cards.add(new SetCardInfo("Springmane Cervin", 144, Rarity.COMMON, mage.cards.s.SpringmaneCervin.class));
        cards.add(new SetCardInfo("Square Up", 238, Rarity.COMMON, mage.cards.s.SquareUp.class));
        cards.add(new SetCardInfo("Star Pupil", 30, Rarity.COMMON, mage.cards.s.StarPupil.class));
        cards.add(new SetCardInfo("Start from Scratch", 114, Rarity.UNCOMMON, mage.cards.s.StartFromScratch.class));
        cards.add(new SetCardInfo("Stonebinder's Familiar", 31, Rarity.UNCOMMON, mage.cards.s.StonebindersFamiliar.class));
        cards.add(new SetCardInfo("Stonebound Mentor", 239, Rarity.COMMON, mage.cards.s.StoneboundMentor.class));
        cards.add(new SetCardInfo("Stonerise Spirit", 32, Rarity.COMMON, mage.cards.s.StoneriseSpirit.class));
        cards.add(new SetCardInfo("Storm-Kiln Artist", 115, Rarity.UNCOMMON, mage.cards.s.StormKilnArtist.class));
        cards.add(new SetCardInfo("Strict Proctor", 294, Rarity.RARE, mage.cards.s.StrictProctor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Strict Proctor", 33, Rarity.RARE, mage.cards.s.StrictProctor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Strixhaven Stadium", 259, Rarity.RARE, mage.cards.s.StrixhavenStadium.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Strixhaven Stadium", 358, Rarity.RARE, mage.cards.s.StrixhavenStadium.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Study Break", 34, Rarity.COMMON, mage.cards.s.StudyBreak.class));
        cards.add(new SetCardInfo("Sudden Breakthrough", 116, Rarity.COMMON, mage.cards.s.SuddenBreakthrough.class));
        cards.add(new SetCardInfo("Swamp", 370, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 371, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Symmetry Sage", 56, Rarity.UNCOMMON, mage.cards.s.SymmetrySage.class));
        cards.add(new SetCardInfo("Tanazir Quandrix", 240, Rarity.MYTHIC, mage.cards.t.TanazirQuandrix.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tanazir Quandrix", 284, Rarity.MYTHIC, mage.cards.t.TanazirQuandrix.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tangletrap", 145, Rarity.COMMON, mage.cards.t.Tangletrap.class));
        cards.add(new SetCardInfo("Teach by Example", 241, Rarity.COMMON, mage.cards.t.TeachByExample.class));
        cards.add(new SetCardInfo("Teachings of the Archaics", 299, Rarity.RARE, mage.cards.t.TeachingsOfTheArchaics.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teachings of the Archaics", 57, Rarity.RARE, mage.cards.t.TeachingsOfTheArchaics.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Team Pennant", 260, Rarity.UNCOMMON, mage.cards.t.TeamPennant.class));
        cards.add(new SetCardInfo("Tempted by the Oriq", 300, Rarity.RARE, mage.cards.t.TemptedByTheOriq.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tempted by the Oriq", 58, Rarity.RARE, mage.cards.t.TemptedByTheOriq.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tend the Pests", 242, Rarity.UNCOMMON, mage.cards.t.TendThePests.class));
        cards.add(new SetCardInfo("Tenured Inkcaster", 88, Rarity.UNCOMMON, mage.cards.t.TenuredInkcaster.class));
        cards.add(new SetCardInfo("Test of Talents", 59, Rarity.UNCOMMON, mage.cards.t.TestOfTalents.class));
        cards.add(new SetCardInfo("The Biblioplex", 264, Rarity.RARE, mage.cards.t.TheBiblioplex.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Biblioplex", 359, Rarity.RARE, mage.cards.t.TheBiblioplex.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thrilling Discovery", 243, Rarity.COMMON, mage.cards.t.ThrillingDiscovery.class));
        cards.add(new SetCardInfo("Thunderous Orator", 35, Rarity.UNCOMMON, mage.cards.t.ThunderousOrator.class));
        cards.add(new SetCardInfo("Tome Shredder", 117, Rarity.COMMON, mage.cards.t.TomeShredder.class));
        cards.add(new SetCardInfo("Torrent Sculptor", 159, Rarity.RARE, mage.cards.t.TorrentSculptor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Torrent Sculptor", 331, Rarity.RARE, mage.cards.t.TorrentSculptor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Twinscroll Shaman", 118, Rarity.COMMON, mage.cards.t.TwinscrollShaman.class));
        cards.add(new SetCardInfo("Umbral Juke", 89, Rarity.UNCOMMON, mage.cards.u.UmbralJuke.class));
        cards.add(new SetCardInfo("Unwilling Ingredient", 90, Rarity.COMMON, mage.cards.u.UnwillingIngredient.class));
        cards.add(new SetCardInfo("Uvilda, Dean of Perfection", 160, Rarity.RARE, mage.cards.u.UvildaDeanOfPerfection.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Uvilda, Dean of Perfection", 332, Rarity.RARE, mage.cards.u.UvildaDeanOfPerfection.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Valentin, Dean of the Vein", 161, Rarity.RARE, mage.cards.v.ValentinDeanOfTheVein.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Valentin, Dean of the Vein", 333, Rarity.RARE, mage.cards.v.ValentinDeanOfTheVein.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vanishing Verse", 244, Rarity.RARE, mage.cards.v.VanishingVerse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vanishing Verse", 354, Rarity.RARE, mage.cards.v.VanishingVerse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Velomachus Lorehold", 245, Rarity.MYTHIC, mage.cards.v.VelomachusLorehold.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Velomachus Lorehold", 283, Rarity.MYTHIC, mage.cards.v.VelomachusLorehold.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Venerable Warsinger", 246, Rarity.RARE, mage.cards.v.VenerableWarsinger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Venerable Warsinger", 355, Rarity.RARE, mage.cards.v.VenerableWarsinger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Verdant Mastery", 146, Rarity.RARE, mage.cards.v.VerdantMastery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Verdant Mastery", 320, Rarity.RARE, mage.cards.v.VerdantMastery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vineglimmer Snarl", 274, Rarity.RARE, mage.cards.v.VineglimmerSnarl.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vineglimmer Snarl", 365, Rarity.RARE, mage.cards.v.VineglimmerSnarl.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vortex Runner", 60, Rarity.COMMON, mage.cards.v.VortexRunner.class));
        cards.add(new SetCardInfo("Wandering Archaic", 286, Rarity.RARE, mage.cards.w.WanderingArchaic.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wandering Archaic", 6, Rarity.RARE, mage.cards.w.WanderingArchaic.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Waterfall Aerialist", 61, Rarity.COMMON, mage.cards.w.WaterfallAerialist.class));
        cards.add(new SetCardInfo("Witherbloom Apprentice", 247, Rarity.UNCOMMON, mage.cards.w.WitherbloomApprentice.class));
        cards.add(new SetCardInfo("Witherbloom Campus", 275, Rarity.COMMON, mage.cards.w.WitherbloomCampus.class));
        cards.add(new SetCardInfo("Witherbloom Command", 248, Rarity.RARE, mage.cards.w.WitherbloomCommand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Witherbloom Command", 356, Rarity.RARE, mage.cards.w.WitherbloomCommand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Witherbloom Pledgemage", 249, Rarity.COMMON, mage.cards.w.WitherbloomPledgemage.class));
        cards.add(new SetCardInfo("Wormhole Serpent", 62, Rarity.UNCOMMON, mage.cards.w.WormholeSerpent.class));
        cards.add(new SetCardInfo("Zephyr Boots", 261, Rarity.UNCOMMON, mage.cards.z.ZephyrBoots.class));
        cards.add(new SetCardInfo("Zimone, Quandrix Prodigy", 250, Rarity.UNCOMMON, mage.cards.z.ZimoneQuandrixProdigy.class));
    }

    @Override
    public List<Card> tryBooster() {
        List<Card> booster = super.tryBooster();
        addArchive(booster);
        addLesson(booster);
        return booster;
    }

    private void addArchive(List<Card> booster) {
        // Boosters have one card from STA, odds are 2/3 for uncommon, 4/15 for rare, 1/15 for mythic
        final Rarity rarity;
        int i = RandomUtil.nextInt(15);
        if (i == 14) {
            rarity = Rarity.MYTHIC;
        } else if (i >= 10) {
            rarity = Rarity.RARE;
        } else {
            rarity = Rarity.UNCOMMON;
        }
        addToBooster(booster, StrixhavenMysticalArchive.getInstance().getCardsByRarity(rarity));
    }

    private void addLesson(List<Card> booster) {
        // Boosters have one Lesson card
        final Rarity rarity;
        int i = RandomUtil.nextInt(148);
        if (i == 0) {
            rarity = Rarity.MYTHIC;
        } else if (i < 11) {
            rarity = Rarity.RARE;
        } else {
            rarity = Rarity.COMMON;
        }
        List<CardInfo> cards = super.getCardsByRarity(rarity);
        cards.removeIf(cardInfo -> !cardInfo.getCard().hasSubtype(SubType.LESSON, null));
        addToBooster(booster, cards);
    }

    @Override
    public List<CardInfo> getCardsByRarity(Rarity rarity) {
        List<CardInfo> cards = super.getCardsByRarity(rarity);
        if (rarity != Rarity.UNCOMMON) {
            cards.removeIf(cardInfo -> cardInfo.getCard().hasSubtype(SubType.LESSON, null));
        }
        return cards;
    }

    @Override
    protected void generateBoosterMap() {
        super.generateBoosterMap();
        CardRepository
                .instance
                .findCards(new CardCriteria().setCodes("STA"))
                .stream()
                .forEach(cardInfo -> inBoosterMap.put("STA_" + cardInfo.getCardNumber(), cardInfo));
    }

    @Override
    public BoosterCollator createCollator() {
        return new StrixhavenSchoolOfMagesCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/stx.html
// Using Belgian collation plus other info inferred from various sources
class StrixhavenSchoolOfMagesCollator implements BoosterCollator {

    private final CardRun commonA = new CardRun(true, "249", "206", "182", "226", "237", "255", "210", "209", "239", "226", "251", "185", "219", "243", "206", "164", "215", "238", "256", "184", "239", "208", "254", "237", "185", "223", "251", "206", "166", "182", "164", "238", "204", "233", "184", "210", "182", "252", "243", "254", "208", "194", "255", "243", "249", "201", "204", "194", "235", "239", "166", "251", "223", "252", "237", "208", "233", "241", "219", "255", "201", "194", "164", "252", "170", "223", "241", "215", "166", "249", "237", "238", "184", "210", "243", "209", "235", "252", "204", "210", "185", "233", "249", "256", "239", "235", "209", "170", "201", "226", "241", "215", "206", "256", "208", "254", "185", "251", "226", "170", "184", "256", "235", "233", "209", "238", "254", "219", "201", "241", "182", "164", "194", "223", "170", "204", "166", "219", "215", "255");
    private final CardRun commonB = new CardRun(true, "79", "9", "111", "136", "52", "85", "12", "118", "131", "40", "90", "34", "117", "141", "60", "69", "30", "93", "140", "36", "74", "16", "97", "143", "40", "87", "11", "99", "121", "39", "75", "30", "106", "122", "38", "79", "22", "97", "131", "51", "90", "23", "112", "145", "50", "87", "18", "103", "124", "43", "68", "34", "102", "142", "49", "77", "16", "93", "140", "52", "73", "32", "116", "144", "60", "63", "22", "109", "124", "55", "69", "9", "106", "143", "39", "73", "23", "116", "142", "61", "84", "8", "99", "122", "43", "74", "32", "117", "145", "36", "75", "19", "112", "141", "61", "77", "8", "109", "144", "50", "84", "18", "118", "137", "55", "68", "12", "111", "121", "38", "63", "19", "103", "136", "51", "85", "11", "102", "137", "49");
    private final CardRun commonC = new CardRun(false, "263", "268", "270", "271", "273", "275");
    private final CardRun uncommonA = new CardRun(true, "257", "65", "56", "132", "92", "125", "72", "62", "104", "89", "123", "54", "10", "135", "114", "53", "105", "188", "45", "115", "47", "70", "100", "129", "88", "260", "46", "76", "257", "110", "65", "13", "139", "78", "92", "26", "72", "15", "56", "89", "134", "28", "70", "91", "132", "105", "31", "123", "88", "104", "135", "54", "115", "25", "76", "13", "62", "125", "35", "188", "65", "260", "10", "114", "129", "47", "100", "15", "257", "53", "26", "110", "139", "28", "134", "56", "35", "45", "91", "72", "10", "31", "132", "54", "89", "15", "78", "46", "123", "25", "92", "135", "104", "70", "125", "105", "260", "62", "188", "129", "114", "88", "13", "53", "26", "115", "47", "76", "28", "139", "100", "35", "91", "45", "78", "134", "31", "46", "25", "110");
    private final CardRun uncommonB = new CardRun(true, "229", "218", "216", "222", "212", "198", "71", "177", "202", "138", "258", "162", "81", "175", "213", "224", "220", "176", "227", "107", "247", "186", "169", "216", "261", "197", "242", "126", "262", "198", "24", "225", "207", "229", "190", "202", "81", "178", "200", "193", "41", "162", "71", "171", "59", "212", "218", "222", "227", "138", "231", "220", "258", "175", "169", "186", "107", "193", "250", "197", "176", "171", "126", "261", "247", "227", "213", "177", "262", "207", "190", "224", "225", "24", "216", "200", "242", "71", "178", "41", "212", "59", "198", "81", "231", "220", "229", "218", "202", "169", "175", "222", "193", "197", "250", "213", "186", "126", "177", "190", "258", "138", "162", "107", "261", "224", "200", "176", "178", "24", "247", "262", "207", "171", "225", "59", "231", "242", "41", "250");
    private final CardRun rareA = new CardRun(false, "6", "14", "17", "20", "27", "29", "33", "37", "42", "44", "48", "58", "64", "66", "80", "82", "86", "94", "96", "98", "101", "113", "119", "127", "130", "133", "146", "147", "150", "152", "154", "155", "157", "158", "159", "160", "161", "165", "172", "173", "174", "179", "180", "181", "199", "205", "214", "217", "221", "228", "232", "234", "244", "246", "248", "253", "259", "264", "265", "266", "267", "269", "272", "274", "6", "14", "17", "20", "27", "29", "33", "37", "42", "44", "48", "58", "64", "66", "80", "82", "86", "94", "96", "98", "101", "113", "119", "127", "130", "133", "146", "147", "150", "152", "154", "155", "157", "158", "159", "160", "161", "165", "172", "173", "174", "179", "180", "181", "199", "205", "214", "217", "221", "228", "232", "234", "244", "246", "248", "253", "259", "264", "265", "266", "267", "269", "272", "274", "21", "83", "95", "128", "148", "149", "151", "153", "156", "163", "167", "168", "189", "191", "192", "196", "203", "230", "240", "245");
    private final CardRun commonLesson = new CardRun(true, "4", "187", "183", "195", "211", "3", "1", "236", "2", "187", "195", "4", "183", "211", "1", "2", "236", "3", "183", "187", "4", "195", "2", "211", "3", "1", "236", "187", "183", "195", "4", "1", "211", "3", "2", "236", "187", "4", "183", "195", "3", "1", "2", "211", "236", "187", "195", "4", "183", "2", "1", "211", "3", "187", "183", "4", "236", "195", "1", "3", "211", "236", "2", "187", "4", "195", "183", "3", "211", "1", "2", "236", "187", "4", "195", "183", "211", "3", "1", "2", "236", "183", "195", "4", "187", "1", "211", "3", "2", "236", "187", "4", "195", "183", "2", "211", "3", "1", "187", "236", "4", "183", "195", "211", "1", "236", "3", "2", "195", "187", "183", "4", "2", "236", "3", "211", "1");
    private final CardRun rareLesson = new CardRun(false, "5", "7", "57", "67", "108", "120", "7", "57", "67", "108", "120");
    private final CardRun uncommonArchive = new CardRun(true, "18", "29", "19", "41", "4", "57", "46", "23", "35", "3", "20", "49", "37", "30", "41", "24", "9", "44", "4", "29", "3", "46", "35", "18", "57", "41", "9", "19", "24", "44", "51", "30", "23", "37", "18", "49", "20", "29", "4", "19", "35", "46", "51", "23", "30", "18", "57", "3", "37", "49", "41", "24", "9", "44", "20", "4", "23", "37", "30", "3", "35", "46", "57", "29", "9", "29", "49", "19", "51", "44", "4", "24", "41", "18", "46", "3", "20", "57", "19", "35", "44", "23", "24", "9", "51", "30", "49", "37", "20", "51");
    private final CardRun rareArchive = new CardRun(false, "5", "6", "7", "8", "10", "13", "14", "15", "16", "21", "26", "28", "31", "32", "34", "38", "39", "42", "45", "47", "48", "52", "53", "56", "58", "59", "60", "61", "62", "63", "5", "6", "7", "8", "10", "13", "14", "15", "16", "21", "26", "28", "31", "32", "34", "38", "39", "42", "45", "47", "48", "52", "53", "56", "58", "59", "60", "61", "62", "63", "1", "2", "11", "12", "17", "22", "25", "27", "33", "36", "40", "43", "50", "54", "55");

    private final BoosterStructure AABBBBBBC = new BoosterStructure(
            commonA, commonA,
            commonB, commonB, commonB, commonB, commonB, commonB,
            commonC
    );
    private final BoosterStructure AAABBBBBC = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB, commonB, commonB, commonB,
            commonC
    );
    private final BoosterStructure AAABBBBBB = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB, commonB, commonB, commonB, commonB
    );
    private final BoosterStructure AAB = new BoosterStructure(uncommonA, uncommonA, uncommonB);
    private final BoosterStructure ABB = new BoosterStructure(uncommonA, uncommonB, uncommonB);
    private final BoosterStructure R1 = new BoosterStructure(rareA);
    private final BoosterStructure L1 = new BoosterStructure(commonLesson);
    private final BoosterStructure L2 = new BoosterStructure(rareLesson);
    private final BoosterStructure A1 = new BoosterStructure(uncommonArchive);
    private final BoosterStructure A2 = new BoosterStructure(rareArchive);

    // In order for equal numbers of each common to exist, the average booster must contain:
    // 2.81 A commons (45 / 16)
    // 5.63 B commons (90 / 16)
    // 0.56 C commons ( 9 / 16)
    private final RarityConfiguration commonRuns = new RarityConfiguration(
            AABBBBBBC,
            AABBBBBBC,
            AABBBBBBC,
            AAABBBBBC,
            AAABBBBBC,
            AAABBBBBC,
            AAABBBBBC,
            AAABBBBBC,
            AAABBBBBC,
            AAABBBBBB,
            AAABBBBBB,
            AAABBBBBB,
            AAABBBBBB,
            AAABBBBBB,
            AAABBBBBB,
            AAABBBBBB
    );
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(AAB, ABB);
    private final RarityConfiguration rareRuns = new RarityConfiguration(R1);

    // The ratio of common to rare/mythic Lesson cards hasn't been officially disclosed
    // rare/mythic Lessons were observed in 37 out of 468 packs, which is very close to 2/25
    private final RarityConfiguration lessonRuns = new RarityConfiguration(
            L1, L1, L1, L1, L1,
            L1, L1, L1, L1, L1,
            L1, L1, L1, L1, L1,
            L1, L1, L1, L1, L1,
            L1, L1, L1, L2, L2
    );
    private final RarityConfiguration archiveRuns = new RarityConfiguration(A1, A1, A2);

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        booster.addAll(commonRuns.getNext().makeRun());
        booster.addAll(uncommonRuns.getNext().makeRun());
        booster.addAll(rareRuns.getNext().makeRun());
        booster.addAll(lessonRuns.getNext().makeRun());
        archiveRuns.getNext().makeRun().stream().map(s -> "STA_" + s).forEach(booster::add);
        return booster;
    }
}
