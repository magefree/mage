package mage.sets;

import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.repository.CardInfo;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.constants.SubType;
import mage.util.RandomUtil;

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
        cards.add(new SetCardInfo("Academic Probation", 7, Rarity.RARE, mage.cards.a.AcademicProbation.class));
        cards.add(new SetCardInfo("Access Tunnel", 262, Rarity.UNCOMMON, mage.cards.a.AccessTunnel.class));
        cards.add(new SetCardInfo("Accomplished Alchemist", 119, Rarity.RARE, mage.cards.a.AccomplishedAlchemist.class));
        cards.add(new SetCardInfo("Aether Helix", 162, Rarity.UNCOMMON, mage.cards.a.AetherHelix.class));
        cards.add(new SetCardInfo("Ageless Guardian", 8, Rarity.COMMON, mage.cards.a.AgelessGuardian.class));
        cards.add(new SetCardInfo("Arcane Subtraction", 36, Rarity.COMMON, mage.cards.a.ArcaneSubtraction.class));
        cards.add(new SetCardInfo("Archmage Emeritus", 37, Rarity.RARE, mage.cards.a.ArchmageEmeritus.class));
        cards.add(new SetCardInfo("Archway Commons", 263, Rarity.COMMON, mage.cards.a.ArchwayCommons.class));
        cards.add(new SetCardInfo("Ardent Dustspeaker", 92, Rarity.UNCOMMON, mage.cards.a.ArdentDustspeaker.class));
        cards.add(new SetCardInfo("Arrogant Poet", 63, Rarity.COMMON, mage.cards.a.ArrogantPoet.class));
        cards.add(new SetCardInfo("Augmenter Pugilist", 147, Rarity.RARE, mage.cards.a.AugmenterPugilist.class));
        cards.add(new SetCardInfo("Baleful Mastery", 64, Rarity.RARE, mage.cards.b.BalefulMastery.class));
        cards.add(new SetCardInfo("Basic Conjuration", 120, Rarity.RARE, mage.cards.b.BasicConjuration.class));
        cards.add(new SetCardInfo("Bayou Groff", 121, Rarity.COMMON, mage.cards.b.BayouGroff.class));
        cards.add(new SetCardInfo("Beaming Defiance", 9, Rarity.COMMON, mage.cards.b.BeamingDefiance.class));
        cards.add(new SetCardInfo("Beledros Witherbloom", 163, Rarity.MYTHIC, mage.cards.b.BeledrosWitherbloom.class));
        cards.add(new SetCardInfo("Biblioplex Assistant", 251, Rarity.COMMON, mage.cards.b.BiblioplexAssistant.class));
        cards.add(new SetCardInfo("Big Play", 122, Rarity.COMMON, mage.cards.b.BigPlay.class));
        cards.add(new SetCardInfo("Biomathematician", 164, Rarity.COMMON, mage.cards.b.Biomathematician.class));
        cards.add(new SetCardInfo("Blade Historian", 165, Rarity.RARE, mage.cards.b.BladeHistorian.class));
        cards.add(new SetCardInfo("Blex, Vexing Pest", 148, Rarity.MYTHIC, mage.cards.b.BlexVexingPest.class));
        cards.add(new SetCardInfo("Blood Age General", 93, Rarity.COMMON, mage.cards.b.BloodAgeGeneral.class));
        cards.add(new SetCardInfo("Blood Researcher", 166, Rarity.COMMON, mage.cards.b.BloodResearcher.class));
        cards.add(new SetCardInfo("Blot Out the Sky", 167, Rarity.MYTHIC, mage.cards.b.BlotOutTheSky.class));
        cards.add(new SetCardInfo("Body of Research", 168, Rarity.MYTHIC, mage.cards.b.BodyOfResearch.class));
        cards.add(new SetCardInfo("Bookwurm", 123, Rarity.UNCOMMON, mage.cards.b.Bookwurm.class));
        cards.add(new SetCardInfo("Brackish Trudge", 65, Rarity.UNCOMMON, mage.cards.b.BrackishTrudge.class));
        cards.add(new SetCardInfo("Burrog Befuddler", 38, Rarity.COMMON, mage.cards.b.BurrogBefuddler.class));
        cards.add(new SetCardInfo("Bury in Books", 39, Rarity.COMMON, mage.cards.b.BuryInBooks.class));
        cards.add(new SetCardInfo("Callous Bloodmage", 66, Rarity.RARE, mage.cards.c.CallousBloodmage.class));
        cards.add(new SetCardInfo("Campus Guide", 252, Rarity.COMMON, mage.cards.c.CampusGuide.class));
        cards.add(new SetCardInfo("Charge Through", 124, Rarity.COMMON, mage.cards.c.ChargeThrough.class));
        cards.add(new SetCardInfo("Clever Lumimancer", 10, Rarity.UNCOMMON, mage.cards.c.CleverLumimancer.class));
        cards.add(new SetCardInfo("Closing Statement", 169, Rarity.UNCOMMON, mage.cards.c.ClosingStatement.class));
        cards.add(new SetCardInfo("Codie, Vociferous Codex", 253, Rarity.RARE, mage.cards.c.CodieVociferousCodex.class));
        cards.add(new SetCardInfo("Cogwork Archivist", 254, Rarity.COMMON, mage.cards.c.CogworkArchivist.class));
        cards.add(new SetCardInfo("Combat Professor", 11, Rarity.COMMON, mage.cards.c.CombatProfessor.class));
        cards.add(new SetCardInfo("Confront the Past", 67, Rarity.RARE, mage.cards.c.ConfrontThePast.class));
        cards.add(new SetCardInfo("Conspiracy Theorist", 94, Rarity.RARE, mage.cards.c.ConspiracyTheorist.class));
        cards.add(new SetCardInfo("Containment Breach", 125, Rarity.UNCOMMON, mage.cards.c.ContainmentBreach.class));
        cards.add(new SetCardInfo("Crackle with Power", 95, Rarity.MYTHIC, mage.cards.c.CrackleWithPower.class));
        cards.add(new SetCardInfo("Cram Session", 170, Rarity.COMMON, mage.cards.c.CramSession.class));
        cards.add(new SetCardInfo("Creative Outburst", 171, Rarity.UNCOMMON, mage.cards.c.CreativeOutburst.class));
        cards.add(new SetCardInfo("Crushing Disappointment", 68, Rarity.COMMON, mage.cards.c.CrushingDisappointment.class));
        cards.add(new SetCardInfo("Culling Ritual", 172, Rarity.RARE, mage.cards.c.CullingRitual.class));
        cards.add(new SetCardInfo("Culmination of Studies", 173, Rarity.RARE, mage.cards.c.CulminationOfStudies.class));
        cards.add(new SetCardInfo("Curate", 40, Rarity.COMMON, mage.cards.c.Curate.class));
        cards.add(new SetCardInfo("Daemogoth Titan", 174, Rarity.RARE, mage.cards.d.DaemogothTitan.class));
        cards.add(new SetCardInfo("Daemogoth Woe-Eater", 175, Rarity.UNCOMMON, mage.cards.d.DaemogothWoeEater.class));
        cards.add(new SetCardInfo("Deadly Brew", 176, Rarity.UNCOMMON, mage.cards.d.DeadlyBrew.class));
        cards.add(new SetCardInfo("Decisive Denial", 177, Rarity.UNCOMMON, mage.cards.d.DecisiveDenial.class));
        cards.add(new SetCardInfo("Defend the Campus", 12, Rarity.COMMON, mage.cards.d.DefendTheCampus.class));
        cards.add(new SetCardInfo("Detention Vortex", 13, Rarity.UNCOMMON, mage.cards.d.DetentionVortex.class));
        cards.add(new SetCardInfo("Devastating Mastery", 14, Rarity.RARE, mage.cards.d.DevastatingMastery.class));
        cards.add(new SetCardInfo("Devouring Tendrils", 126, Rarity.UNCOMMON, mage.cards.d.DevouringTendrils.class));
        cards.add(new SetCardInfo("Dina, Soul Steeper", 178, Rarity.UNCOMMON, mage.cards.d.DinaSoulSteeper.class));
        cards.add(new SetCardInfo("Divide by Zero", 41, Rarity.UNCOMMON, mage.cards.d.DivideByZero.class));
        cards.add(new SetCardInfo("Double Major", 179, Rarity.RARE, mage.cards.d.DoubleMajor.class));
        cards.add(new SetCardInfo("Draconic Intervention", 96, Rarity.RARE, mage.cards.d.DraconicIntervention.class));
        cards.add(new SetCardInfo("Dragon's Approach", 97, Rarity.COMMON, mage.cards.d.DragonsApproach.class));
        cards.add(new SetCardInfo("Dragonsguard Elite", 127, Rarity.RARE, mage.cards.d.DragonsguardElite.class));
        cards.add(new SetCardInfo("Dramatic Finale", 180, Rarity.RARE, mage.cards.d.DramaticFinale.class));
        cards.add(new SetCardInfo("Dream Strix", 42, Rarity.RARE, mage.cards.d.DreamStrix.class));
        cards.add(new SetCardInfo("Dueling Coach", 15, Rarity.UNCOMMON, mage.cards.d.DuelingCoach.class));
        cards.add(new SetCardInfo("Eager First-Year", 16, Rarity.COMMON, mage.cards.e.EagerFirstYear.class));
        cards.add(new SetCardInfo("Ecological Appreciation", 128, Rarity.MYTHIC, mage.cards.e.EcologicalAppreciation.class));
        cards.add(new SetCardInfo("Efreet Flamepainter", 98, Rarity.RARE, mage.cards.e.EfreetFlamepainter.class));
        cards.add(new SetCardInfo("Elemental Expressionist", 181, Rarity.RARE, mage.cards.e.ElementalExpressionist.class));
        cards.add(new SetCardInfo("Elemental Masterpiece", 182, Rarity.COMMON, mage.cards.e.ElementalMasterpiece.class));
        cards.add(new SetCardInfo("Elemental Summoning", 183, Rarity.COMMON, mage.cards.e.ElementalSummoning.class));
        cards.add(new SetCardInfo("Elite Spellbinder", 17, Rarity.RARE, mage.cards.e.EliteSpellbinder.class));
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
        cards.add(new SetCardInfo("Exponential Growth", 130, Rarity.RARE, mage.cards.e.ExponentialGrowth.class));
        cards.add(new SetCardInfo("Expressive Iteration", 186, Rarity.UNCOMMON, mage.cards.e.ExpressiveIteration.class));
        cards.add(new SetCardInfo("Extus, Oriq Overlord", 149, Rarity.MYTHIC, mage.cards.e.ExtusOriqOverlord.class));
        cards.add(new SetCardInfo("Eyetwitch", 70, Rarity.UNCOMMON, mage.cards.e.Eyetwitch.class));
        cards.add(new SetCardInfo("Fervent Mastery", 101, Rarity.RARE, mage.cards.f.FerventMastery.class));
        cards.add(new SetCardInfo("Field Trip", 131, Rarity.COMMON, mage.cards.f.FieldTrip.class));
        cards.add(new SetCardInfo("First Day of Class", 102, Rarity.COMMON, mage.cards.f.FirstDayOfClass.class));
        cards.add(new SetCardInfo("Flamescroll Celebrant", 150, Rarity.RARE, mage.cards.f.FlamescrollCelebrant.class));
        cards.add(new SetCardInfo("Flunk", 71, Rarity.UNCOMMON, mage.cards.f.Flunk.class));
        cards.add(new SetCardInfo("Forest", 374, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fortifying Draught", 132, Rarity.UNCOMMON, mage.cards.f.FortifyingDraught.class));
        cards.add(new SetCardInfo("Fractal Summoning", 187, Rarity.COMMON, mage.cards.f.FractalSummoning.class));
        cards.add(new SetCardInfo("Fracture", 188, Rarity.UNCOMMON, mage.cards.f.Fracture.class));
        cards.add(new SetCardInfo("Frost Trickster", 43, Rarity.COMMON, mage.cards.f.FrostTrickster.class));
        cards.add(new SetCardInfo("Frostboil Snarl", 265, Rarity.RARE, mage.cards.f.FrostboilSnarl.class));
        cards.add(new SetCardInfo("Fuming Effigy", 103, Rarity.COMMON, mage.cards.f.FumingEffigy.class));
        cards.add(new SetCardInfo("Furycalm Snarl", 266, Rarity.RARE, mage.cards.f.FurycalmSnarl.class));
        cards.add(new SetCardInfo("Galazeth Prismari", 189, Rarity.MYTHIC, mage.cards.g.GalazethPrismari.class));
        cards.add(new SetCardInfo("Gnarled Professor", 133, Rarity.RARE, mage.cards.g.GnarledProfessor.class));
        cards.add(new SetCardInfo("Go Blank", 72, Rarity.UNCOMMON, mage.cards.g.GoBlank.class));
        cards.add(new SetCardInfo("Golden Ratio", 190, Rarity.UNCOMMON, mage.cards.g.GoldenRatio.class));
        cards.add(new SetCardInfo("Grinning Ignus", 104, Rarity.UNCOMMON, mage.cards.g.GrinningIgnus.class));
        cards.add(new SetCardInfo("Guiding Voice", 19, Rarity.COMMON, mage.cards.g.GuidingVoice.class));
        cards.add(new SetCardInfo("Hall Monitor", 105, Rarity.UNCOMMON, mage.cards.h.HallMonitor.class));
        cards.add(new SetCardInfo("Hall of Oracles", 267, Rarity.RARE, mage.cards.h.HallOfOracles.class));
        cards.add(new SetCardInfo("Harness Infinity", 191, Rarity.MYTHIC, mage.cards.h.HarnessInfinity.class));
        cards.add(new SetCardInfo("Heated Debate", 106, Rarity.COMMON, mage.cards.h.HeatedDebate.class));
        cards.add(new SetCardInfo("Hofri Ghostforge", 192, Rarity.MYTHIC, mage.cards.h.HofriGhostforge.class));
        cards.add(new SetCardInfo("Honor Troll", 134, Rarity.UNCOMMON, mage.cards.h.HonorTroll.class));
        cards.add(new SetCardInfo("Humiliate", 193, Rarity.UNCOMMON, mage.cards.h.Humiliate.class));
        cards.add(new SetCardInfo("Hunt for Specimens", 73, Rarity.COMMON, mage.cards.h.HuntForSpecimens.class));
        cards.add(new SetCardInfo("Igneous Inspiration", 107, Rarity.UNCOMMON, mage.cards.i.IgneousInspiration.class));
        cards.add(new SetCardInfo("Illuminate History", 108, Rarity.RARE, mage.cards.i.IlluminateHistory.class));
        cards.add(new SetCardInfo("Illustrious Historian", 109, Rarity.COMMON, mage.cards.i.IllustriousHistorian.class));
        cards.add(new SetCardInfo("Infuse with Vitality", 194, Rarity.COMMON, mage.cards.i.InfuseWithVitality.class));
        cards.add(new SetCardInfo("Ingenious Mastery", 44, Rarity.RARE, mage.cards.i.IngeniousMastery.class));
        cards.add(new SetCardInfo("Inkling Summoning", 195, Rarity.COMMON, mage.cards.i.InklingSummoning.class));
        cards.add(new SetCardInfo("Introduction to Annihilation", 3, Rarity.COMMON, mage.cards.i.IntroductionToAnnihilation.class));
        cards.add(new SetCardInfo("Introduction to Prophecy", 4, Rarity.COMMON, mage.cards.i.IntroductionToProphecy.class));
        cards.add(new SetCardInfo("Island", 368, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jadzi, Oracle of Arcavios", 151, Rarity.MYTHIC, mage.cards.j.JadziOracleOfArcavios.class));
        cards.add(new SetCardInfo("Karok Wrangler", 135, Rarity.UNCOMMON, mage.cards.k.KarokWrangler.class));
        cards.add(new SetCardInfo("Kasmina, Enigma Sage", 196, Rarity.MYTHIC, mage.cards.k.KasminaEnigmaSage.class));
        cards.add(new SetCardInfo("Kelpie Guide", 45, Rarity.UNCOMMON, mage.cards.k.KelpieGuide.class));
        cards.add(new SetCardInfo("Kianne, Dean of Substance", 152, Rarity.RARE, mage.cards.k.KianneDeanOfSubstance.class));
        cards.add(new SetCardInfo("Killian, Ink Duelist", 197, Rarity.UNCOMMON, mage.cards.k.KillianInkDuelist.class));
        cards.add(new SetCardInfo("Lash of Malice", 74, Rarity.COMMON, mage.cards.l.LashOfMalice.class));
        cards.add(new SetCardInfo("Leech Fanatic", 75, Rarity.COMMON, mage.cards.l.LeechFanatic.class));
        cards.add(new SetCardInfo("Leonin Lightscribe", 20, Rarity.RARE, mage.cards.l.LeoninLightscribe.class));
        cards.add(new SetCardInfo("Letter of Acceptance", 256, Rarity.COMMON, mage.cards.l.LetterOfAcceptance.class));
        cards.add(new SetCardInfo("Leyline Invocation", 136, Rarity.COMMON, mage.cards.l.LeylineInvocation.class));
        cards.add(new SetCardInfo("Lorehold Apprentice", 198, Rarity.UNCOMMON, mage.cards.l.LoreholdApprentice.class));
        cards.add(new SetCardInfo("Lorehold Campus", 268, Rarity.COMMON, mage.cards.l.LoreholdCampus.class));
        cards.add(new SetCardInfo("Lorehold Command", 199, Rarity.RARE, mage.cards.l.LoreholdCommand.class));
        cards.add(new SetCardInfo("Lorehold Excavation", 200, Rarity.UNCOMMON, mage.cards.l.LoreholdExcavation.class));
        cards.add(new SetCardInfo("Lorehold Pledgemage", 201, Rarity.COMMON, mage.cards.l.LoreholdPledgemage.class));
        cards.add(new SetCardInfo("Maelstrom Muse", 202, Rarity.UNCOMMON, mage.cards.m.MaelstromMuse.class));
        cards.add(new SetCardInfo("Mage Duel", 137, Rarity.COMMON, mage.cards.m.MageDuel.class));
        cards.add(new SetCardInfo("Mage Hunter", 76, Rarity.UNCOMMON, mage.cards.m.MageHunter.class));
        cards.add(new SetCardInfo("Mage Hunters' Onslaught", 77, Rarity.COMMON, mage.cards.m.MageHuntersOnslaught.class));
        cards.add(new SetCardInfo("Magma Opus", 203, Rarity.MYTHIC, mage.cards.m.MagmaOpus.class));
        cards.add(new SetCardInfo("Make Your Mark", 204, Rarity.COMMON, mage.cards.m.MakeYourMark.class));
        cards.add(new SetCardInfo("Manifestation Sage", 205, Rarity.RARE, mage.cards.m.ManifestationSage.class));
        cards.add(new SetCardInfo("Mascot Exhibition", 5, Rarity.MYTHIC, mage.cards.m.MascotExhibition.class));
        cards.add(new SetCardInfo("Mascot Interception", 110, Rarity.UNCOMMON, mage.cards.m.MascotInterception.class));
        cards.add(new SetCardInfo("Master Symmetrist", 138, Rarity.UNCOMMON, mage.cards.m.MasterSymmetrist.class));
        cards.add(new SetCardInfo("Mavinda, Students' Advocate", 21, Rarity.MYTHIC, mage.cards.m.MavindaStudentsAdvocate.class));
        cards.add(new SetCardInfo("Mentor's Guidance", 46, Rarity.UNCOMMON, mage.cards.m.MentorsGuidance.class));
        cards.add(new SetCardInfo("Mercurial Transformation", 47, Rarity.UNCOMMON, mage.cards.m.MercurialTransformation.class));
        cards.add(new SetCardInfo("Mila, Crafty Companion", 153, Rarity.MYTHIC, mage.cards.m.MilaCraftyCompanion.class));
        cards.add(new SetCardInfo("Moldering Karok", 206, Rarity.COMMON, mage.cards.m.MolderingKarok.class));
        cards.add(new SetCardInfo("Mortality Spear", 207, Rarity.UNCOMMON, mage.cards.m.MortalitySpear.class));
        cards.add(new SetCardInfo("Mountain", 372, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Multiple Choice", 48, Rarity.RARE, mage.cards.m.MultipleChoice.class));
        cards.add(new SetCardInfo("Necroblossom Snarl", 269, Rarity.RARE, mage.cards.n.NecroblossomSnarl.class));
        cards.add(new SetCardInfo("Necrotic Fumes", 78, Rarity.UNCOMMON, mage.cards.n.NecroticFumes.class));
        cards.add(new SetCardInfo("Needlethorn Drake", 208, Rarity.COMMON, mage.cards.n.NeedlethornDrake.class));
        cards.add(new SetCardInfo("Novice Dissector", 79, Rarity.COMMON, mage.cards.n.NoviceDissector.class));
        cards.add(new SetCardInfo("Oggyar Battle-Seer", 209, Rarity.COMMON, mage.cards.o.OggyarBattleSeer.class));
        cards.add(new SetCardInfo("Oriq Loremage", 80, Rarity.RARE, mage.cards.o.OriqLoremage.class));
        cards.add(new SetCardInfo("Overgrown Arch", 139, Rarity.UNCOMMON, mage.cards.o.OvergrownArch.class));
        cards.add(new SetCardInfo("Owlin Shieldmage", 210, Rarity.COMMON, mage.cards.o.OwlinShieldmage.class));
        cards.add(new SetCardInfo("Pest Summoning", 211, Rarity.COMMON, mage.cards.p.PestSummoning.class));
        cards.add(new SetCardInfo("Pestilent Cauldron", 154, Rarity.RARE, mage.cards.p.PestilentCauldron.class));
        cards.add(new SetCardInfo("Pigment Storm", 111, Rarity.COMMON, mage.cards.p.PigmentStorm.class));
        cards.add(new SetCardInfo("Pilgrim of the Ages", 22, Rarity.COMMON, mage.cards.p.PilgrimOfTheAges.class));
        cards.add(new SetCardInfo("Pillardrop Rescuer", 23, Rarity.COMMON, mage.cards.p.PillardropRescuer.class));
        cards.add(new SetCardInfo("Pillardrop Warden", 112, Rarity.COMMON, mage.cards.p.PillardropWarden.class));
        cards.add(new SetCardInfo("Plains", 366, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plargg, Dean of Chaos", 155, Rarity.RARE, mage.cards.p.PlarggDeanOfChaos.class));
        cards.add(new SetCardInfo("Plumb the Forbidden", 81, Rarity.UNCOMMON, mage.cards.p.PlumbTheForbidden.class));
        cards.add(new SetCardInfo("Poet's Quill", 82, Rarity.RARE, mage.cards.p.PoetsQuill.class));
        cards.add(new SetCardInfo("Pop Quiz", 49, Rarity.COMMON, mage.cards.p.PopQuiz.class));
        cards.add(new SetCardInfo("Practical Research", 212, Rarity.UNCOMMON, mage.cards.p.PracticalResearch.class));
        cards.add(new SetCardInfo("Prismari Apprentice", 213, Rarity.UNCOMMON, mage.cards.p.PrismariApprentice.class));
        cards.add(new SetCardInfo("Prismari Campus", 270, Rarity.COMMON, mage.cards.p.PrismariCampus.class));
        cards.add(new SetCardInfo("Prismari Command", 214, Rarity.RARE, mage.cards.p.PrismariCommand.class));
        cards.add(new SetCardInfo("Prismari Pledgemage", 215, Rarity.COMMON, mage.cards.p.PrismariPledgemage.class));
        cards.add(new SetCardInfo("Professor Onyx", 83, Rarity.MYTHIC, mage.cards.p.ProfessorOnyx.class));
        cards.add(new SetCardInfo("Professor of Symbology", 24, Rarity.UNCOMMON, mage.cards.p.ProfessorOfSymbology.class));
        cards.add(new SetCardInfo("Professor of Zoomancy", 140, Rarity.COMMON, mage.cards.p.ProfessorOfZoomancy.class));
        cards.add(new SetCardInfo("Professor's Warning", 84, Rarity.COMMON, mage.cards.p.ProfessorsWarning.class));
        cards.add(new SetCardInfo("Promising Duskmage", 85, Rarity.COMMON, mage.cards.p.PromisingDuskmage.class));
        cards.add(new SetCardInfo("Quandrix Apprentice", 216, Rarity.UNCOMMON, mage.cards.q.QuandrixApprentice.class));
        cards.add(new SetCardInfo("Quandrix Campus", 271, Rarity.COMMON, mage.cards.q.QuandrixCampus.class));
        cards.add(new SetCardInfo("Quandrix Command", 217, Rarity.RARE, mage.cards.q.QuandrixCommand.class));
        cards.add(new SetCardInfo("Quandrix Cultivator", 218, Rarity.UNCOMMON, mage.cards.q.QuandrixCultivator.class));
        cards.add(new SetCardInfo("Quandrix Pledgemage", 219, Rarity.COMMON, mage.cards.q.QuandrixPledgemage.class));
        cards.add(new SetCardInfo("Quintorius, Field Historian", 220, Rarity.UNCOMMON, mage.cards.q.QuintoriusFieldHistorian.class));
        cards.add(new SetCardInfo("Radiant Scrollwielder", 221, Rarity.RARE, mage.cards.r.RadiantScrollwielder.class));
        cards.add(new SetCardInfo("Reckless Amplimancer", 141, Rarity.COMMON, mage.cards.r.RecklessAmplimancer.class));
        cards.add(new SetCardInfo("Reconstruct History", 222, Rarity.UNCOMMON, mage.cards.r.ReconstructHistory.class));
        cards.add(new SetCardInfo("Reduce to Memory", 25, Rarity.UNCOMMON, mage.cards.r.ReduceToMemory.class));
        cards.add(new SetCardInfo("Reflective Golem", 257, Rarity.UNCOMMON, mage.cards.r.ReflectiveGolem.class));
        cards.add(new SetCardInfo("Reject", 50, Rarity.COMMON, mage.cards.r.Reject.class));
        cards.add(new SetCardInfo("Relic Sloth", 223, Rarity.COMMON, mage.cards.r.RelicSloth.class));
        cards.add(new SetCardInfo("Resculpt", 51, Rarity.COMMON, mage.cards.r.Resculpt.class));
        cards.add(new SetCardInfo("Retriever Phoenix", 113, Rarity.RARE, mage.cards.r.RetrieverPhoenix.class));
        cards.add(new SetCardInfo("Returned Pastcaller", 224, Rarity.UNCOMMON, mage.cards.r.ReturnedPastcaller.class));
        cards.add(new SetCardInfo("Rip Apart", 225, Rarity.UNCOMMON, mage.cards.r.RipApart.class));
        cards.add(new SetCardInfo("Rise of Extus", 226, Rarity.COMMON, mage.cards.r.RiseOfExtus.class));
        cards.add(new SetCardInfo("Rootha, Mercurial Artist", 227, Rarity.UNCOMMON, mage.cards.r.RoothaMercurialArtist.class));
        cards.add(new SetCardInfo("Rowan, Scholar of Sparks", 156, Rarity.MYTHIC, mage.cards.r.RowanScholarOfSparks.class));
        cards.add(new SetCardInfo("Rushed Rebirth", 228, Rarity.RARE, mage.cards.r.RushedRebirth.class));
        cards.add(new SetCardInfo("Scurrid Colony", 142, Rarity.COMMON, mage.cards.s.ScurridColony.class));
        cards.add(new SetCardInfo("Secret Rendezvous", 26, Rarity.UNCOMMON, mage.cards.s.SecretRendezvous.class));
        cards.add(new SetCardInfo("Sedgemoor Witch", 86, Rarity.RARE, mage.cards.s.SedgemoorWitch.class));
        cards.add(new SetCardInfo("Semester's End", 27, Rarity.RARE, mage.cards.s.SemestersEnd.class));
        cards.add(new SetCardInfo("Serpentine Curve", 52, Rarity.COMMON, mage.cards.s.SerpentineCurve.class));
        cards.add(new SetCardInfo("Shadewing Laureate", 229, Rarity.UNCOMMON, mage.cards.s.ShadewingLaureate.class));
        cards.add(new SetCardInfo("Shadrix Silverquill", 230, Rarity.MYTHIC, mage.cards.s.ShadrixSilverquill.class));
        cards.add(new SetCardInfo("Shaile, Dean of Radiance", 158, Rarity.RARE, mage.cards.s.ShaileDeanOfRadiance.class));
        cards.add(new SetCardInfo("Shineshadow Snarl", 272, Rarity.RARE, mage.cards.s.ShineshadowSnarl.class));
        cards.add(new SetCardInfo("Show of Confidence", 28, Rarity.UNCOMMON, mage.cards.s.ShowOfConfidence.class));
        cards.add(new SetCardInfo("Silverquill Apprentice", 231, Rarity.UNCOMMON, mage.cards.s.SilverquillApprentice.class));
        cards.add(new SetCardInfo("Silverquill Campus", 273, Rarity.COMMON, mage.cards.s.SilverquillCampus.class));
        cards.add(new SetCardInfo("Silverquill Command", 232, Rarity.RARE, mage.cards.s.SilverquillCommand.class));
        cards.add(new SetCardInfo("Silverquill Pledgemage", 233, Rarity.COMMON, mage.cards.s.SilverquillPledgemage.class));
        cards.add(new SetCardInfo("Silverquill Silencer", 234, Rarity.RARE, mage.cards.s.SilverquillSilencer.class));
        cards.add(new SetCardInfo("Snow Day", 53, Rarity.UNCOMMON, mage.cards.s.SnowDay.class));
        cards.add(new SetCardInfo("Solve the Equation", 54, Rarity.UNCOMMON, mage.cards.s.SolveTheEquation.class));
        cards.add(new SetCardInfo("Soothsayer Adept", 55, Rarity.COMMON, mage.cards.s.SoothsayerAdept.class));
        cards.add(new SetCardInfo("Sparring Regimen", 29, Rarity.RARE, mage.cards.s.SparringRegimen.class));
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
        cards.add(new SetCardInfo("Strict Proctor", 33, Rarity.RARE, mage.cards.s.StrictProctor.class));
        cards.add(new SetCardInfo("Strixhaven Stadium", 259, Rarity.RARE, mage.cards.s.StrixhavenStadium.class));
        cards.add(new SetCardInfo("Study Break", 34, Rarity.COMMON, mage.cards.s.StudyBreak.class));
        cards.add(new SetCardInfo("Sudden Breakthrough", 116, Rarity.COMMON, mage.cards.s.SuddenBreakthrough.class));
        cards.add(new SetCardInfo("Swamp", 370, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Symmetry Sage", 56, Rarity.UNCOMMON, mage.cards.s.SymmetrySage.class));
        cards.add(new SetCardInfo("Tanazir Quandrix", 240, Rarity.MYTHIC, mage.cards.t.TanazirQuandrix.class));
        cards.add(new SetCardInfo("Tangletrap", 145, Rarity.COMMON, mage.cards.t.Tangletrap.class));
        cards.add(new SetCardInfo("Teach by Example", 241, Rarity.COMMON, mage.cards.t.TeachByExample.class));
        cards.add(new SetCardInfo("Teachings of the Archaics", 57, Rarity.RARE, mage.cards.t.TeachingsOfTheArchaics.class));
        cards.add(new SetCardInfo("Team Pennant", 260, Rarity.UNCOMMON, mage.cards.t.TeamPennant.class));
        cards.add(new SetCardInfo("Tempted by the Oriq", 58, Rarity.RARE, mage.cards.t.TemptedByTheOriq.class));
        cards.add(new SetCardInfo("Tend the Pests", 242, Rarity.UNCOMMON, mage.cards.t.TendThePests.class));
        cards.add(new SetCardInfo("Tenured Inkcaster", 88, Rarity.UNCOMMON, mage.cards.t.TenuredInkcaster.class));
        cards.add(new SetCardInfo("Test of Talents", 59, Rarity.UNCOMMON, mage.cards.t.TestOfTalents.class));
        cards.add(new SetCardInfo("The Biblioplex", 264, Rarity.RARE, mage.cards.t.TheBiblioplex.class));
        cards.add(new SetCardInfo("Thrilling Discovery", 243, Rarity.COMMON, mage.cards.t.ThrillingDiscovery.class));
        cards.add(new SetCardInfo("Thunderous Orator", 35, Rarity.UNCOMMON, mage.cards.t.ThunderousOrator.class));
        cards.add(new SetCardInfo("Tome Shredder", 117, Rarity.COMMON, mage.cards.t.TomeShredder.class));
        cards.add(new SetCardInfo("Torrent Sculptor", 159, Rarity.RARE, mage.cards.t.TorrentSculptor.class));
        cards.add(new SetCardInfo("Twinscroll Shaman", 118, Rarity.COMMON, mage.cards.t.TwinscrollShaman.class));
        cards.add(new SetCardInfo("Umbral Juke", 89, Rarity.UNCOMMON, mage.cards.u.UmbralJuke.class));
        cards.add(new SetCardInfo("Unwilling Ingredient", 90, Rarity.COMMON, mage.cards.u.UnwillingIngredient.class));
        cards.add(new SetCardInfo("Valentin, Dean of the Vein", 161, Rarity.RARE, mage.cards.v.ValentinDeanOfTheVein.class));
        cards.add(new SetCardInfo("Vanishing Verse", 244, Rarity.RARE, mage.cards.v.VanishingVerse.class));
        cards.add(new SetCardInfo("Velomachus Lorehold", 245, Rarity.MYTHIC, mage.cards.v.VelomachusLorehold.class));
        cards.add(new SetCardInfo("Venerable Warsinger", 246, Rarity.RARE, mage.cards.v.VenerableWarsinger.class));
        cards.add(new SetCardInfo("Verdant Mastery", 146, Rarity.RARE, mage.cards.v.VerdantMastery.class));
        cards.add(new SetCardInfo("Vineglimmer Snarl", 274, Rarity.RARE, mage.cards.v.VineglimmerSnarl.class));
        cards.add(new SetCardInfo("Vortex Runner", 60, Rarity.COMMON, mage.cards.v.VortexRunner.class));
        cards.add(new SetCardInfo("Wandering Archaic", 6, Rarity.RARE, mage.cards.w.WanderingArchaic.class));
        cards.add(new SetCardInfo("Waterfall Aerialist", 61, Rarity.COMMON, mage.cards.w.WaterfallAerialist.class));
        cards.add(new SetCardInfo("Witherbloom Apprentice", 247, Rarity.UNCOMMON, mage.cards.w.WitherbloomApprentice.class));
        cards.add(new SetCardInfo("Witherbloom Campus", 275, Rarity.COMMON, mage.cards.w.WitherbloomCampus.class));
        cards.add(new SetCardInfo("Witherbloom Command", 248, Rarity.RARE, mage.cards.w.WitherbloomCommand.class));
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
        int i = RandomUtil.nextInt(15);
        if (i == 14) { // TODO: change this when correct ratio is known
            rarity = Rarity.MYTHIC;
        } else if (i >= 10) {
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
}
