package mage.sets;

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

import java.util.ArrayList;
import java.util.List;

/**
 * @author TheElk801
 */
public final class ZendikarRising extends ExpansionSet {

    private static final ZendikarRising instance = new ZendikarRising();

    public static ZendikarRising getInstance() {
        return instance;
    }

    private ZendikarRising() {
        super("Zendikar Rising", "ZNR", ExpansionSet.buildDate(2020, 9, 25), SetType.EXPANSION);
        this.blockName = "Zendikar Rising";
        this.hasBasicLands = true;
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.ratioBoosterSpecialRare = 5.5;
        this.ratioBoosterSpecialMythic = 5.4;   // 5 mythic MDFCs, 11 rare MDFCs
        this.maxCardNumberInBooster = 280;

        cards.add(new SetCardInfo("Acquisitions Expert", 89, Rarity.UNCOMMON, mage.cards.a.AcquisitionsExpert.class));
        cards.add(new SetCardInfo("Adventure Awaits", 177, Rarity.COMMON, mage.cards.a.AdventureAwaits.class));
        cards.add(new SetCardInfo("Agadeem's Awakening", 336, Rarity.MYTHIC, mage.cards.a.AgadeemsAwakening.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Agadeem's Awakening", 90, Rarity.MYTHIC, mage.cards.a.AgadeemsAwakening.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Akiri, Fearless Voyager", 220, Rarity.RARE, mage.cards.a.AkiriFearlessVoyager.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Akiri, Fearless Voyager", 365, Rarity.RARE, mage.cards.a.AkiriFearlessVoyager.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Akoum Hellhound", 133, Rarity.COMMON, mage.cards.a.AkoumHellhound.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Akoum Hellhound", 299, Rarity.COMMON, mage.cards.a.AkoumHellhound.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Akoum Warrior", 134, Rarity.UNCOMMON, mage.cards.a.AkoumWarrior.class));
        cards.add(new SetCardInfo("Allied Assault", 1, Rarity.UNCOMMON, mage.cards.a.AlliedAssault.class));
        cards.add(new SetCardInfo("Ancient Greenwarden", 178, Rarity.MYTHIC, mage.cards.a.AncientGreenwarden.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ancient Greenwarden", 357, Rarity.MYTHIC, mage.cards.a.AncientGreenwarden.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Angel of Destiny", 2, Rarity.MYTHIC, mage.cards.a.AngelOfDestiny.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Angel of Destiny", 314, Rarity.MYTHIC, mage.cards.a.AngelOfDestiny.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Angelheart Protector", 3, Rarity.COMMON, mage.cards.a.AngelheartProtector.class));
        cards.add(new SetCardInfo("Anticognition", 45, Rarity.COMMON, mage.cards.a.Anticognition.class));
        cards.add(new SetCardInfo("Archon of Emeria", 315, Rarity.RARE, mage.cards.a.ArchonOfEmeria.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Archon of Emeria", 4, Rarity.RARE, mage.cards.a.ArchonOfEmeria.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Archpriest of Iona", 316, Rarity.RARE, mage.cards.a.ArchpriestOfIona.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Archpriest of Iona", 5, Rarity.RARE, mage.cards.a.ArchpriestOfIona.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ardent Electromancer", 135, Rarity.COMMON, mage.cards.a.ArdentElectromancer.class));
        cards.add(new SetCardInfo("Ashaya, Soul of the Wild", 179, Rarity.MYTHIC, mage.cards.a.AshayaSoulOfTheWild.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ashaya, Soul of the Wild", 358, Rarity.MYTHIC, mage.cards.a.AshayaSoulOfTheWild.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Attended Healer", 6, Rarity.UNCOMMON, mage.cards.a.AttendedHealer.class));
        cards.add(new SetCardInfo("Bala Ged Recovery", 180, Rarity.UNCOMMON, mage.cards.b.BalaGedRecovery.class));
        cards.add(new SetCardInfo("Base Camp", 257, Rarity.UNCOMMON, mage.cards.b.BaseCamp.class));
        cards.add(new SetCardInfo("Beyeen Veil", 46, Rarity.UNCOMMON, mage.cards.b.BeyeenVeil.class));
        cards.add(new SetCardInfo("Blackbloom Rogue", 91, Rarity.UNCOMMON, mage.cards.b.BlackbloomRogue.class));
        cards.add(new SetCardInfo("Blood Beckoning", 92, Rarity.COMMON, mage.cards.b.BloodBeckoning.class));
        cards.add(new SetCardInfo("Blood Price", 93, Rarity.COMMON, mage.cards.b.BloodPrice.class));
        cards.add(new SetCardInfo("Bloodchief's Thirst", 388, Rarity.UNCOMMON, mage.cards.b.BloodchiefsThirst.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodchief's Thirst", 94, Rarity.UNCOMMON, mage.cards.b.BloodchiefsThirst.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Branchloft Pathway", 258, Rarity.RARE, mage.cards.b.BranchloftPathway.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Branchloft Pathway", 284, Rarity.RARE, mage.cards.b.BranchloftPathway.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Brightclimb Pathway", 259, Rarity.RARE, mage.cards.b.BrightclimbPathway.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Brightclimb Pathway", 285, Rarity.RARE, mage.cards.b.BrightclimbPathway.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Broken Wings", 181, Rarity.COMMON, mage.cards.b.BrokenWings.class));
        cards.add(new SetCardInfo("Brushfire Elemental", 221, Rarity.UNCOMMON, mage.cards.b.BrushfireElemental.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Brushfire Elemental", 311, Rarity.UNCOMMON, mage.cards.b.BrushfireElemental.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bubble Snare", 47, Rarity.COMMON, mage.cards.b.BubbleSnare.class));
        cards.add(new SetCardInfo("Canopy Baloth", 182, Rarity.COMMON, mage.cards.c.CanopyBaloth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Canopy Baloth", 304, Rarity.COMMON, mage.cards.c.CanopyBaloth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Canyon Jerboa", 290, Rarity.UNCOMMON, mage.cards.c.CanyonJerboa.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Canyon Jerboa", 7, Rarity.UNCOMMON, mage.cards.c.CanyonJerboa.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cascade Seer", 48, Rarity.COMMON, mage.cards.c.CascadeSeer.class));
        cards.add(new SetCardInfo("Charix, the Raging Isle", 325, Rarity.RARE, mage.cards.c.CharixTheRagingIsle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Charix, the Raging Isle", 386, Rarity.RARE, mage.cards.c.CharixTheRagingIsle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Charix, the Raging Isle", 49, Rarity.RARE, mage.cards.c.CharixTheRagingIsle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chilling Trap", 50, Rarity.COMMON, mage.cards.c.ChillingTrap.class));
        cards.add(new SetCardInfo("Cinderclasm", 136, Rarity.UNCOMMON, mage.cards.c.Cinderclasm.class));
        cards.add(new SetCardInfo("Cleansing Wildfire", 137, Rarity.COMMON, mage.cards.c.CleansingWildfire.class));
        cards.add(new SetCardInfo("Clearwater Pathway", 260, Rarity.RARE, mage.cards.c.ClearwaterPathway.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Clearwater Pathway", 286, Rarity.RARE, mage.cards.c.ClearwaterPathway.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cleric of Chill Depths", 51, Rarity.COMMON, mage.cards.c.ClericOfChillDepths.class));
        cards.add(new SetCardInfo("Cleric of Life's Bond", 222, Rarity.UNCOMMON, mage.cards.c.ClericOfLifesBond.class));
        cards.add(new SetCardInfo("Cliffhaven Kitesail", 243, Rarity.COMMON, mage.cards.c.CliffhavenKitesail.class));
        cards.add(new SetCardInfo("Cliffhaven Sell-Sword", 8, Rarity.COMMON, mage.cards.c.CliffhavenSellSword.class));
        cards.add(new SetCardInfo("Concerted Defense", 52, Rarity.UNCOMMON, mage.cards.c.ConcertedDefense.class));
        cards.add(new SetCardInfo("Confounding Conundrum", 326, Rarity.RARE, mage.cards.c.ConfoundingConundrum.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Confounding Conundrum", 53, Rarity.RARE, mage.cards.c.ConfoundingConundrum.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Coralhelm Chronicler", 327, Rarity.RARE, mage.cards.c.CoralhelmChronicler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Coralhelm Chronicler", 54, Rarity.RARE, mage.cards.c.CoralhelmChronicler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Coveted Prize", 337, Rarity.RARE, mage.cards.c.CovetedPrize.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Coveted Prize", 95, Rarity.RARE, mage.cards.c.CovetedPrize.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cragcrown Pathway", 261, Rarity.RARE, mage.cards.c.CragcrownPathway.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cragcrown Pathway", 287, Rarity.RARE, mage.cards.c.CragcrownPathway.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cragplate Baloth", 183, Rarity.RARE, mage.cards.c.CragplateBaloth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cragplate Baloth", 359, Rarity.RARE, mage.cards.c.CragplateBaloth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crawling Barrens", 262, Rarity.RARE, mage.cards.c.CrawlingBarrens.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crawling Barrens", 378, Rarity.RARE, mage.cards.c.CrawlingBarrens.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cunning Geysermage", 55, Rarity.COMMON, mage.cards.c.CunningGeysermage.class));
        cards.add(new SetCardInfo("Dauntless Survivor", 184, Rarity.COMMON, mage.cards.d.DauntlessSurvivor.class));
        cards.add(new SetCardInfo("Dauntless Unity", 9, Rarity.COMMON, mage.cards.d.DauntlessUnity.class));
        cards.add(new SetCardInfo("Deadly Alliance", 96, Rarity.COMMON, mage.cards.d.DeadlyAlliance.class));
        cards.add(new SetCardInfo("Deliberate", 56, Rarity.COMMON, mage.cards.d.Deliberate.class));
        cards.add(new SetCardInfo("Demon's Disciple", 97, Rarity.UNCOMMON, mage.cards.d.DemonsDisciple.class));
        cards.add(new SetCardInfo("Disenchant", 10, Rarity.COMMON, mage.cards.d.Disenchant.class));
        cards.add(new SetCardInfo("Drana's Silencer", 99, Rarity.COMMON, mage.cards.d.DranasSilencer.class));
        cards.add(new SetCardInfo("Drana, the Last Bloodchief", 338, Rarity.MYTHIC, mage.cards.d.DranaTheLastBloodchief.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Drana, the Last Bloodchief", 98, Rarity.MYTHIC, mage.cards.d.DranaTheLastBloodchief.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dreadwurm", 100, Rarity.COMMON, mage.cards.d.Dreadwurm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dreadwurm", 297, Rarity.COMMON, mage.cards.d.Dreadwurm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Emeria Captain", 11, Rarity.UNCOMMON, mage.cards.e.EmeriaCaptain.class));
        cards.add(new SetCardInfo("Emeria's Call", 12, Rarity.MYTHIC, mage.cards.e.EmeriasCall.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Emeria's Call", 317, Rarity.MYTHIC, mage.cards.e.EmeriasCall.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Expedition Champion", 138, Rarity.COMMON, mage.cards.e.ExpeditionChampion.class));
        cards.add(new SetCardInfo("Expedition Diviner", 57, Rarity.COMMON, mage.cards.e.ExpeditionDiviner.class));
        cards.add(new SetCardInfo("Expedition Healer", 13, Rarity.COMMON, mage.cards.e.ExpeditionHealer.class));
        cards.add(new SetCardInfo("Expedition Skulker", 101, Rarity.COMMON, mage.cards.e.ExpeditionSkulker.class));
        cards.add(new SetCardInfo("Farsight Adept", 14, Rarity.COMMON, mage.cards.f.FarsightAdept.class));
        cards.add(new SetCardInfo("Fearless Fledgling", 15, Rarity.UNCOMMON, mage.cards.f.FearlessFledgling.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fearless Fledgling", 291, Rarity.UNCOMMON, mage.cards.f.FearlessFledgling.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Feed the Swarm", 102, Rarity.COMMON, mage.cards.f.FeedTheSwarm.class));
        cards.add(new SetCardInfo("Felidar Retreat", 16, Rarity.RARE, mage.cards.f.FelidarRetreat.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Felidar Retreat", 292, Rarity.RARE, mage.cards.f.FelidarRetreat.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Field Research", 58, Rarity.COMMON, mage.cards.f.FieldResearch.class));
        cards.add(new SetCardInfo("Fireblade Charger", 139, Rarity.UNCOMMON, mage.cards.f.FirebladeCharger.class));
        cards.add(new SetCardInfo("Fissure Wizard", 140, Rarity.COMMON, mage.cards.f.FissureWizard.class));
        cards.add(new SetCardInfo("Forest", 278, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forest", 279, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forest", 280, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forest", 384, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forsaken Monument", 244, Rarity.MYTHIC, mage.cards.f.ForsakenMonument.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forsaken Monument", 374, Rarity.MYTHIC, mage.cards.f.ForsakenMonument.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ghastly Gloomhunter", 103, Rarity.COMMON, mage.cards.g.GhastlyGloomhunter.class));
        cards.add(new SetCardInfo("Glacial Grasp", 59, Rarity.COMMON, mage.cards.g.GlacialGrasp.class));
        cards.add(new SetCardInfo("Glasspool Mimic", 328, Rarity.RARE, mage.cards.g.GlasspoolMimic.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Glasspool Mimic", 60, Rarity.RARE, mage.cards.g.GlasspoolMimic.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gnarlid Colony", 185, Rarity.COMMON, mage.cards.g.GnarlidColony.class));
        cards.add(new SetCardInfo("Goma Fada Vanguard", 141, Rarity.UNCOMMON, mage.cards.g.GomaFadaVanguard.class));
        cards.add(new SetCardInfo("Grakmaw, Skyclave Ravager", 223, Rarity.RARE, mage.cards.g.GrakmawSkyclaveRavager.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Grakmaw, Skyclave Ravager", 366, Rarity.RARE, mage.cards.g.GrakmawSkyclaveRavager.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Grotag Bug-Catcher", 142, Rarity.COMMON, mage.cards.g.GrotagBugCatcher.class));
        cards.add(new SetCardInfo("Grotag Night-Runner", 143, Rarity.UNCOMMON, mage.cards.g.GrotagNightRunner.class));
        cards.add(new SetCardInfo("Guul Draz Mucklord", 104, Rarity.COMMON, mage.cards.g.GuulDrazMucklord.class));
        cards.add(new SetCardInfo("Hagra Constrictor", 105, Rarity.COMMON, mage.cards.h.HagraConstrictor.class));
        cards.add(new SetCardInfo("Hagra Mauling", 106, Rarity.RARE, mage.cards.h.HagraMauling.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hagra Mauling", 339, Rarity.RARE, mage.cards.h.HagraMauling.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Highborn Vampire", 107, Rarity.COMMON, mage.cards.h.HighbornVampire.class));
        cards.add(new SetCardInfo("Inordinate Rage", 144, Rarity.COMMON, mage.cards.i.InordinateRage.class));
        cards.add(new SetCardInfo("Inscription of Abundance", 186, Rarity.RARE, mage.cards.i.InscriptionOfAbundance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Inscription of Abundance", 360, Rarity.RARE, mage.cards.i.InscriptionOfAbundance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Inscription of Insight", 329, Rarity.RARE, mage.cards.i.InscriptionOfInsight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Inscription of Insight", 61, Rarity.RARE, mage.cards.i.InscriptionOfInsight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Inscription of Ruin", 108, Rarity.RARE, mage.cards.i.InscriptionOfRuin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Inscription of Ruin", 340, Rarity.RARE, mage.cards.i.InscriptionOfRuin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Into the Roil", 387, Rarity.COMMON, mage.cards.i.IntoTheRoil.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Into the Roil", 62, Rarity.COMMON, mage.cards.i.IntoTheRoil.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Iridescent Hornbeetle", 187, Rarity.UNCOMMON, mage.cards.i.IridescentHornbeetle.class));
        cards.add(new SetCardInfo("Island", 269, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 270, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 271, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 381, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jace, Mirror Mage", 281, Rarity.MYTHIC, mage.cards.j.JaceMirrorMage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jace, Mirror Mage", 63, Rarity.MYTHIC, mage.cards.j.JaceMirrorMage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Joraga Visionary", 188, Rarity.COMMON, mage.cards.j.JoragaVisionary.class));
        cards.add(new SetCardInfo("Journey to Oblivion", 17, Rarity.UNCOMMON, mage.cards.j.JourneyToOblivion.class));
        cards.add(new SetCardInfo("Jwari Disruption", 64, Rarity.UNCOMMON, mage.cards.j.JwariDisruption.class));
        cards.add(new SetCardInfo("Kabira Outrider", 18, Rarity.COMMON, mage.cards.k.KabiraOutrider.class));
        cards.add(new SetCardInfo("Kabira Takedown", 19, Rarity.UNCOMMON, mage.cards.k.KabiraTakedown.class));
        cards.add(new SetCardInfo("Kargan Intimidator", 145, Rarity.RARE, mage.cards.k.KarganIntimidator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kargan Intimidator", 347, Rarity.RARE, mage.cards.k.KarganIntimidator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kargan Warleader", 224, Rarity.UNCOMMON, mage.cards.k.KarganWarleader.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kargan Warleader", 391, Rarity.UNCOMMON, mage.cards.k.KarganWarleader.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kaza, Roil Chaser", 225, Rarity.RARE, mage.cards.k.KazaRoilChaser.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kaza, Roil Chaser", 367, Rarity.RARE, mage.cards.k.KazaRoilChaser.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kazandu Mammoth", 189, Rarity.RARE, mage.cards.k.KazanduMammoth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kazandu Mammoth", 305, Rarity.RARE, mage.cards.k.KazanduMammoth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kazandu Nectarpot", 190, Rarity.COMMON, mage.cards.k.KazanduNectarpot.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kazandu Nectarpot", 306, Rarity.COMMON, mage.cards.k.KazanduNectarpot.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kazandu Stomper", 191, Rarity.COMMON, mage.cards.k.KazanduStomper.class));
        cards.add(new SetCardInfo("Kazuul's Fury", 146, Rarity.UNCOMMON, mage.cards.k.KazuulsFury.class));
        cards.add(new SetCardInfo("Khalni Ambush", 192, Rarity.UNCOMMON, mage.cards.k.KhalniAmbush.class));
        cards.add(new SetCardInfo("Kitesail Cleric", 20, Rarity.UNCOMMON, mage.cards.k.KitesailCleric.class));
        cards.add(new SetCardInfo("Kor Blademaster", 21, Rarity.UNCOMMON, mage.cards.k.KorBlademaster.class));
        cards.add(new SetCardInfo("Kor Celebrant", 22, Rarity.COMMON, mage.cards.k.KorCelebrant.class));
        cards.add(new SetCardInfo("Legion Angel", 23, Rarity.RARE, mage.cards.l.LegionAngel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Legion Angel", 318, Rarity.RARE, mage.cards.l.LegionAngel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Leyline Tyrant", 147, Rarity.MYTHIC, mage.cards.l.LeylineTyrant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Leyline Tyrant", 348, Rarity.MYTHIC, mage.cards.l.LeylineTyrant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Linvala, Shield of Sea Gate", 226, Rarity.RARE, mage.cards.l.LinvalaShieldOfSeaGate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Linvala, Shield of Sea Gate", 368, Rarity.RARE, mage.cards.l.LinvalaShieldOfSeaGate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lithoform Blight", 109, Rarity.UNCOMMON, mage.cards.l.LithoformBlight.class));
        cards.add(new SetCardInfo("Lithoform Engine", 245, Rarity.MYTHIC, mage.cards.l.LithoformEngine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lithoform Engine", 375, Rarity.MYTHIC, mage.cards.l.LithoformEngine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Living Tempest", 65, Rarity.COMMON, mage.cards.l.LivingTempest.class));
        cards.add(new SetCardInfo("Lotus Cobra", 193, Rarity.RARE, mage.cards.l.LotusCobra.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lotus Cobra", 307, Rarity.RARE, mage.cards.l.LotusCobra.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lullmage's Domination", 66, Rarity.UNCOMMON, mage.cards.l.LullmagesDomination.class));
        cards.add(new SetCardInfo("Lullmage's Familiar", 227, Rarity.UNCOMMON, mage.cards.l.LullmagesFamiliar.class));
        cards.add(new SetCardInfo("Luminarch Aspirant", 24, Rarity.RARE, mage.cards.l.LuminarchAspirant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Luminarch Aspirant", 319, Rarity.RARE, mage.cards.l.LuminarchAspirant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Maddening Cacophony", 330, Rarity.RARE, mage.cards.m.MaddeningCacophony.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Maddening Cacophony", 67, Rarity.RARE, mage.cards.m.MaddeningCacophony.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Magmatic Channeler", 148, Rarity.RARE, mage.cards.m.MagmaticChanneler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Magmatic Channeler", 349, Rarity.RARE, mage.cards.m.MagmaticChanneler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Makindi Ox", 25, Rarity.COMMON, mage.cards.m.MakindiOx.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Makindi Ox", 293, Rarity.COMMON, mage.cards.m.MakindiOx.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Makindi Stampede", 26, Rarity.UNCOMMON, mage.cards.m.MakindiStampede.class));
        cards.add(new SetCardInfo("Malakir Blood-Priest", 110, Rarity.COMMON, mage.cards.m.MalakirBloodPriest.class));
        cards.add(new SetCardInfo("Malakir Rebirth", 111, Rarity.UNCOMMON, mage.cards.m.MalakirRebirth.class));
        cards.add(new SetCardInfo("Marauding Blight-Priest", 112, Rarity.COMMON, mage.cards.m.MaraudingBlightPriest.class));
        cards.add(new SetCardInfo("Master of Winds", 331, Rarity.RARE, mage.cards.m.MasterOfWinds.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Master of Winds", 68, Rarity.RARE, mage.cards.m.MasterOfWinds.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Maul of the Skyclaves", 27, Rarity.RARE, mage.cards.m.MaulOfTheSkyclaves.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Maul of the Skyclaves", 320, Rarity.RARE, mage.cards.m.MaulOfTheSkyclaves.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Merfolk Falconer", 69, Rarity.UNCOMMON, mage.cards.m.MerfolkFalconer.class));
        cards.add(new SetCardInfo("Merfolk Windrobber", 70, Rarity.UNCOMMON, mage.cards.m.MerfolkWindrobber.class));
        cards.add(new SetCardInfo("Mesa Lynx", 28, Rarity.COMMON, mage.cards.m.MesaLynx.class));
        cards.add(new SetCardInfo("Might of Murasa", 194, Rarity.COMMON, mage.cards.m.MightOfMurasa.class));
        cards.add(new SetCardInfo("Mind Carver", 113, Rarity.UNCOMMON, mage.cards.m.MindCarver.class));
        cards.add(new SetCardInfo("Mind Drain", 114, Rarity.COMMON, mage.cards.m.MindDrain.class));
        cards.add(new SetCardInfo("Molten Blast", 149, Rarity.COMMON, mage.cards.m.MoltenBlast.class));
        cards.add(new SetCardInfo("Moraug, Fury of Akoum", 150, Rarity.MYTHIC, mage.cards.m.MoraugFuryOfAkoum.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Moraug, Fury of Akoum", 300, Rarity.MYTHIC, mage.cards.m.MoraugFuryOfAkoum.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Moss-Pit Skeleton", 228, Rarity.UNCOMMON, mage.cards.m.MossPitSkeleton.class));
        cards.add(new SetCardInfo("Mountain", 275, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 276, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 277, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 383, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Murasa Brute", 195, Rarity.COMMON, mage.cards.m.MurasaBrute.class));
        cards.add(new SetCardInfo("Murasa Rootgrazer", 229, Rarity.UNCOMMON, mage.cards.m.MurasaRootgrazer.class));
        cards.add(new SetCardInfo("Murasa Sproutling", 196, Rarity.UNCOMMON, mage.cards.m.MurasaSproutling.class));
        cards.add(new SetCardInfo("Myriad Construct", 246, Rarity.RARE, mage.cards.m.MyriadConstruct.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Myriad Construct", 376, Rarity.RARE, mage.cards.m.MyriadConstruct.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nahiri's Binding", 29, Rarity.COMMON, mage.cards.n.NahirisBinding.class));
        cards.add(new SetCardInfo("Nahiri's Lithoforming", 151, Rarity.RARE, mage.cards.n.NahirisLithoforming.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nahiri's Lithoforming", 350, Rarity.RARE, mage.cards.n.NahirisLithoforming.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nahiri, Heir of the Ancients", 230, Rarity.MYTHIC, mage.cards.n.NahiriHeirOfTheAncients.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nahiri, Heir of the Ancients", 282, Rarity.MYTHIC, mage.cards.n.NahiriHeirOfTheAncients.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Needleverge Pathway", 263, Rarity.RARE, mage.cards.n.NeedlevergePathway.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Needleverge Pathway", 288, Rarity.RARE, mage.cards.n.NeedlevergePathway.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Negate", 71, Rarity.COMMON, mage.cards.n.Negate.class));
        cards.add(new SetCardInfo("Nighthawk Scavenger", 115, Rarity.RARE, mage.cards.n.NighthawkScavenger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nighthawk Scavenger", 341, Rarity.RARE, mage.cards.n.NighthawkScavenger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nimana Skitter-Sneak", 116, Rarity.COMMON, mage.cards.n.NimanaSkitterSneak.class));
        cards.add(new SetCardInfo("Nimana Skydancer", 117, Rarity.COMMON, mage.cards.n.NimanaSkydancer.class));
        cards.add(new SetCardInfo("Nimble Trapfinder", 332, Rarity.RARE, mage.cards.n.NimbleTrapfinder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nimble Trapfinder", 72, Rarity.RARE, mage.cards.n.NimbleTrapfinder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nissa of Shadowed Boughs", 231, Rarity.MYTHIC, mage.cards.n.NissaOfShadowedBoughs.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nissa of Shadowed Boughs", 283, Rarity.MYTHIC, mage.cards.n.NissaOfShadowedBoughs.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nissa's Zendikon", 197, Rarity.COMMON, mage.cards.n.NissasZendikon.class));
        cards.add(new SetCardInfo("Nullpriest of Oblivion", 118, Rarity.RARE, mage.cards.n.NullpriestOfOblivion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nullpriest of Oblivion", 342, Rarity.RARE, mage.cards.n.NullpriestOfOblivion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Oblivion's Hunger", 119, Rarity.COMMON, mage.cards.o.OblivionsHunger.class));
        cards.add(new SetCardInfo("Omnath, Locus of Creation", 232, Rarity.MYTHIC, mage.cards.o.OmnathLocusOfCreation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Omnath, Locus of Creation", 312, Rarity.MYTHIC, mage.cards.o.OmnathLocusOfCreation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ondu Inversion", 30, Rarity.RARE, mage.cards.o.OnduInversion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ondu Inversion", 321, Rarity.RARE, mage.cards.o.OnduInversion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Orah, Skyclave Hierophant", 233, Rarity.RARE, mage.cards.o.OrahSkyclaveHierophant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Orah, Skyclave Hierophant", 369, Rarity.RARE, mage.cards.o.OrahSkyclaveHierophant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Orah, Skyclave Hierophant", 385, Rarity.RARE, mage.cards.o.OrahSkyclaveHierophant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Oran-Rief Ooze", 198, Rarity.RARE, mage.cards.o.OranRiefOoze.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Oran-Rief Ooze", 361, Rarity.RARE, mage.cards.o.OranRiefOoze.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Paired Tactician", 31, Rarity.UNCOMMON, mage.cards.p.PairedTactician.class));
        cards.add(new SetCardInfo("Pelakka Predation", 120, Rarity.UNCOMMON, mage.cards.p.PelakkaPredation.class));
        cards.add(new SetCardInfo("Phylath, World Sculptor", 234, Rarity.RARE, mage.cards.p.PhylathWorldSculptor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Phylath, World Sculptor", 313, Rarity.RARE, mage.cards.p.PhylathWorldSculptor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 266, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 267, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 268, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 380, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Practiced Tactics", 32, Rarity.COMMON, mage.cards.p.PracticedTactics.class));
        cards.add(new SetCardInfo("Pressure Point", 33, Rarity.COMMON, mage.cards.p.PressurePoint.class));
        cards.add(new SetCardInfo("Prowling Felidar", 294, Rarity.COMMON, mage.cards.p.ProwlingFelidar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prowling Felidar", 34, Rarity.COMMON, mage.cards.p.ProwlingFelidar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pyroclastic Hellion", 152, Rarity.COMMON, mage.cards.p.PyroclasticHellion.class));
        cards.add(new SetCardInfo("Rabid Bite", 199, Rarity.COMMON, mage.cards.r.RabidBite.class));
        cards.add(new SetCardInfo("Ravager's Mace", 235, Rarity.UNCOMMON, mage.cards.r.RavagersMace.class));
        cards.add(new SetCardInfo("Reclaim the Wastes", 200, Rarity.COMMON, mage.cards.r.ReclaimTheWastes.class));
        cards.add(new SetCardInfo("Relic Amulet", 247, Rarity.UNCOMMON, mage.cards.r.RelicAmulet.class));
        cards.add(new SetCardInfo("Relic Axe", 248, Rarity.UNCOMMON, mage.cards.r.RelicAxe.class));
        cards.add(new SetCardInfo("Relic Golem", 249, Rarity.UNCOMMON, mage.cards.r.RelicGolem.class));
        cards.add(new SetCardInfo("Relic Robber", 153, Rarity.RARE, mage.cards.r.RelicRobber.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Relic Robber", 351, Rarity.RARE, mage.cards.r.RelicRobber.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Relic Vial", 250, Rarity.UNCOMMON, mage.cards.r.RelicVial.class));
        cards.add(new SetCardInfo("Resolute Strike", 35, Rarity.COMMON, mage.cards.r.ResoluteStrike.class));
        cards.add(new SetCardInfo("Risen Riptide", 73, Rarity.COMMON, mage.cards.r.RisenRiptide.class));
        cards.add(new SetCardInfo("Riverglide Pathway", 264, Rarity.RARE, mage.cards.r.RiverglidePathway.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Riverglide Pathway", 289, Rarity.RARE, mage.cards.r.RiverglidePathway.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rockslide Sorcerer", 154, Rarity.UNCOMMON, mage.cards.r.RockslideSorcerer.class));
        cards.add(new SetCardInfo("Roil Eruption", 155, Rarity.COMMON, mage.cards.r.RoilEruption.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Roil Eruption", 389, Rarity.COMMON, mage.cards.r.RoilEruption.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Roiling Regrowth", 201, Rarity.UNCOMMON, mage.cards.r.RoilingRegrowth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Roiling Regrowth", 390, Rarity.UNCOMMON, mage.cards.r.RoilingRegrowth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Roiling Vortex", 156, Rarity.RARE, mage.cards.r.RoilingVortex.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Roiling Vortex", 352, Rarity.RARE, mage.cards.r.RoilingVortex.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Roost of Drakes", 74, Rarity.UNCOMMON, mage.cards.r.RoostOfDrakes.class));
        cards.add(new SetCardInfo("Ruin Crab", 295, Rarity.UNCOMMON, mage.cards.r.RuinCrab.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ruin Crab", 75, Rarity.UNCOMMON, mage.cards.r.RuinCrab.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scale the Heights", 202, Rarity.COMMON, mage.cards.s.ScaleTheHeights.class));
        cards.add(new SetCardInfo("Scavenged Blade", 157, Rarity.COMMON, mage.cards.s.ScavengedBlade.class));
        cards.add(new SetCardInfo("Scion of the Swarm", 121, Rarity.UNCOMMON, mage.cards.s.ScionOfTheSwarm.class));
        cards.add(new SetCardInfo("Scorch Rider", 158, Rarity.COMMON, mage.cards.s.ScorchRider.class));
        cards.add(new SetCardInfo("Scourge of the Skyclaves", 122, Rarity.MYTHIC, mage.cards.s.ScourgeOfTheSkyclaves.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scourge of the Skyclaves", 343, Rarity.MYTHIC, mage.cards.s.ScourgeOfTheSkyclaves.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scute Swarm", 203, Rarity.RARE, mage.cards.s.ScuteSwarm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scute Swarm", 308, Rarity.RARE, mage.cards.s.ScuteSwarm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sea Gate Banneret", 36, Rarity.COMMON, mage.cards.s.SeaGateBanneret.class));
        cards.add(new SetCardInfo("Sea Gate Colossus", 251, Rarity.COMMON, mage.cards.s.SeaGateColossus.class));
        cards.add(new SetCardInfo("Sea Gate Restoration", 333, Rarity.MYTHIC, mage.cards.s.SeaGateRestoration.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sea Gate Restoration", 76, Rarity.MYTHIC, mage.cards.s.SeaGateRestoration.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sea Gate Stormcaller", 334, Rarity.MYTHIC, mage.cards.s.SeaGateStormcaller.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sea Gate Stormcaller", 77, Rarity.MYTHIC, mage.cards.s.SeaGateStormcaller.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Seafloor Stalker", 78, Rarity.COMMON, mage.cards.s.SeafloorStalker.class));
        cards.add(new SetCardInfo("Sejiri Shelter", 37, Rarity.UNCOMMON, mage.cards.s.SejiriShelter.class));
        cards.add(new SetCardInfo("Shadow Stinger", 123, Rarity.UNCOMMON, mage.cards.s.ShadowStinger.class));
        cards.add(new SetCardInfo("Shadows' Verdict", 124, Rarity.RARE, mage.cards.s.ShadowsVerdict.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shadows' Verdict", 344, Rarity.RARE, mage.cards.s.ShadowsVerdict.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shatterskull Charger", 159, Rarity.RARE, mage.cards.s.ShatterskullCharger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shatterskull Charger", 353, Rarity.RARE, mage.cards.s.ShatterskullCharger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shatterskull Minotaur", 160, Rarity.UNCOMMON, mage.cards.s.ShatterskullMinotaur.class));
        cards.add(new SetCardInfo("Shatterskull Smashing", 161, Rarity.MYTHIC, mage.cards.s.ShatterskullSmashing.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shatterskull Smashing", 354, Rarity.MYTHIC, mage.cards.s.ShatterskullSmashing.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shell Shield", 79, Rarity.COMMON, mage.cards.s.ShellShield.class));
        cards.add(new SetCardInfo("Shepherd of Heroes", 38, Rarity.COMMON, mage.cards.s.ShepherdOfHeroes.class));
        cards.add(new SetCardInfo("Silundi Vision", 80, Rarity.UNCOMMON, mage.cards.s.SilundiVision.class));
        cards.add(new SetCardInfo("Sizzling Barrage", 162, Rarity.COMMON, mage.cards.s.SizzlingBarrage.class));
        cards.add(new SetCardInfo("Skyclave Apparition", 322, Rarity.RARE, mage.cards.s.SkyclaveApparition.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Skyclave Apparition", 39, Rarity.RARE, mage.cards.s.SkyclaveApparition.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Skyclave Cleric", 40, Rarity.UNCOMMON, mage.cards.s.SkyclaveCleric.class));
        cards.add(new SetCardInfo("Skyclave Geopede", 163, Rarity.UNCOMMON, mage.cards.s.SkyclaveGeopede.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Skyclave Geopede", 301, Rarity.UNCOMMON, mage.cards.s.SkyclaveGeopede.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Skyclave Pick-Axe", 204, Rarity.UNCOMMON, mage.cards.s.SkyclavePickAxe.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Skyclave Pick-Axe", 309, Rarity.UNCOMMON, mage.cards.s.SkyclavePickAxe.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Skyclave Plunder", 81, Rarity.UNCOMMON, mage.cards.s.SkyclavePlunder.class));
        cards.add(new SetCardInfo("Skyclave Relic", 252, Rarity.RARE, mage.cards.s.SkyclaveRelic.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Skyclave Relic", 377, Rarity.RARE, mage.cards.s.SkyclaveRelic.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Skyclave Sentinel", 253, Rarity.COMMON, mage.cards.s.SkyclaveSentinel.class));
        cards.add(new SetCardInfo("Skyclave Shade", 125, Rarity.RARE, mage.cards.s.SkyclaveShade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Skyclave Shade", 298, Rarity.RARE, mage.cards.s.SkyclaveShade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Skyclave Shadowcat", 126, Rarity.UNCOMMON, mage.cards.s.SkyclaveShadowcat.class));
        cards.add(new SetCardInfo("Skyclave Squid", 296, Rarity.COMMON, mage.cards.s.SkyclaveSquid.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Skyclave Squid", 82, Rarity.COMMON, mage.cards.s.SkyclaveSquid.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Smite the Monstrous", 42, Rarity.COMMON, mage.cards.s.SmiteTheMonstrous.class));
        cards.add(new SetCardInfo("Sneaking Guide", 164, Rarity.COMMON, mage.cards.s.SneakingGuide.class));
        cards.add(new SetCardInfo("Soaring Thought-Thief", 236, Rarity.UNCOMMON, mage.cards.s.SoaringThoughtThief.class));
        cards.add(new SetCardInfo("Song-Mad Treachery", 165, Rarity.UNCOMMON, mage.cards.s.SongMadTreachery.class));
        cards.add(new SetCardInfo("Soul Shatter", 127, Rarity.RARE, mage.cards.s.SoulShatter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Soul Shatter", 345, Rarity.RARE, mage.cards.s.SoulShatter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spare Supplies", 254, Rarity.COMMON, mage.cards.s.SpareSupplies.class));
        cards.add(new SetCardInfo("Spikefield Hazard", 166, Rarity.UNCOMMON, mage.cards.s.SpikefieldHazard.class));
        cards.add(new SetCardInfo("Spitfire Lagac", 167, Rarity.COMMON, mage.cards.s.SpitfireLagac.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spitfire Lagac", 302, Rarity.COMMON, mage.cards.s.SpitfireLagac.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spoils of Adventure", 237, Rarity.UNCOMMON, mage.cards.s.SpoilsOfAdventure.class));
        cards.add(new SetCardInfo("Springmantle Cleric", 205, Rarity.UNCOMMON, mage.cards.s.SpringmantleCleric.class));
        cards.add(new SetCardInfo("Squad Commander", 323, Rarity.RARE, mage.cards.s.SquadCommander.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Squad Commander", 41, Rarity.RARE, mage.cards.s.SquadCommander.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stonework Packbeast", 255, Rarity.COMMON, mage.cards.s.StoneworkPackbeast.class));
        cards.add(new SetCardInfo("Strength of Solidarity", 206, Rarity.COMMON, mage.cards.s.StrengthOfSolidarity.class));
        cards.add(new SetCardInfo("Subtle Strike", 128, Rarity.COMMON, mage.cards.s.SubtleStrike.class));
        cards.add(new SetCardInfo("Sure-Footed Infiltrator", 83, Rarity.UNCOMMON, mage.cards.s.SureFootedInfiltrator.class));
        cards.add(new SetCardInfo("Swamp", 272, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 273, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 274, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 382, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swarm Shambler", 207, Rarity.RARE, mage.cards.s.SwarmShambler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swarm Shambler", 362, Rarity.RARE, mage.cards.s.SwarmShambler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Synchronized Spellcraft", 168, Rarity.COMMON, mage.cards.s.SynchronizedSpellcraft.class));
        cards.add(new SetCardInfo("Taborax, Hope's Demise", 129, Rarity.RARE, mage.cards.t.TaboraxHopesDemise.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Taborax, Hope's Demise", 346, Rarity.RARE, mage.cards.t.TaboraxHopesDemise.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tajuru Blightblade", 208, Rarity.COMMON, mage.cards.t.TajuruBlightblade.class));
        cards.add(new SetCardInfo("Tajuru Paragon", 209, Rarity.RARE, mage.cards.t.TajuruParagon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tajuru Paragon", 363, Rarity.RARE, mage.cards.t.TajuruParagon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tajuru Snarecaster", 210, Rarity.COMMON, mage.cards.t.TajuruSnarecaster.class));
        cards.add(new SetCardInfo("Tangled Florahedron", 211, Rarity.UNCOMMON, mage.cards.t.TangledFlorahedron.class));
        cards.add(new SetCardInfo("Taunting Arbormage", 212, Rarity.UNCOMMON, mage.cards.t.TauntingArbormage.class));
        cards.add(new SetCardInfo("Tazeem Raptor", 43, Rarity.COMMON, mage.cards.t.TazeemRaptor.class));
        cards.add(new SetCardInfo("Tazeem Roilmage", 84, Rarity.COMMON, mage.cards.t.TazeemRoilmage.class));
        cards.add(new SetCardInfo("Tazri, Beacon of Unity", 324, Rarity.MYTHIC, mage.cards.t.TazriBeaconOfUnity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tazri, Beacon of Unity", 44, Rarity.MYTHIC, mage.cards.t.TazriBeaconOfUnity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teeterpeak Ambusher", 169, Rarity.COMMON, mage.cards.t.TeeterpeakAmbusher.class));
        cards.add(new SetCardInfo("Territorial Scythecat", 213, Rarity.COMMON, mage.cards.t.TerritorialScythecat.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Territorial Scythecat", 310, Rarity.COMMON, mage.cards.t.TerritorialScythecat.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thieving Skydiver", 335, Rarity.RARE, mage.cards.t.ThievingSkydiver.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thieving Skydiver", 85, Rarity.RARE, mage.cards.t.ThievingSkydiver.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Throne of Makindi", 265, Rarity.RARE, mage.cards.t.ThroneOfMakindi.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Throne of Makindi", 379, Rarity.RARE, mage.cards.t.ThroneOfMakindi.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thundering Rebuke", 170, Rarity.UNCOMMON, mage.cards.t.ThunderingRebuke.class));
        cards.add(new SetCardInfo("Thundering Sparkmage", 171, Rarity.UNCOMMON, mage.cards.t.ThunderingSparkmage.class));
        cards.add(new SetCardInfo("Thwart the Grave", 130, Rarity.UNCOMMON, mage.cards.t.ThwartTheGrave.class));
        cards.add(new SetCardInfo("Tormenting Voice", 172, Rarity.COMMON, mage.cards.t.TormentingVoice.class));
        cards.add(new SetCardInfo("Tuktuk Rubblefort", 173, Rarity.COMMON, mage.cards.t.TuktukRubblefort.class));
        cards.add(new SetCardInfo("Turntimber Ascetic", 214, Rarity.COMMON, mage.cards.t.TurntimberAscetic.class));
        cards.add(new SetCardInfo("Turntimber Symbiosis", 215, Rarity.MYTHIC, mage.cards.t.TurntimberSymbiosis.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Turntimber Symbiosis", 364, Rarity.MYTHIC, mage.cards.t.TurntimberSymbiosis.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Umara Mystic", 238, Rarity.UNCOMMON, mage.cards.u.UmaraMystic.class));
        cards.add(new SetCardInfo("Umara Wizard", 86, Rarity.UNCOMMON, mage.cards.u.UmaraWizard.class));
        cards.add(new SetCardInfo("Utility Knife", 256, Rarity.COMMON, mage.cards.u.UtilityKnife.class));
        cards.add(new SetCardInfo("Valakut Awakening", 174, Rarity.RARE, mage.cards.v.ValakutAwakening.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Valakut Awakening", 355, Rarity.RARE, mage.cards.v.ValakutAwakening.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Valakut Exploration", 175, Rarity.RARE, mage.cards.v.ValakutExploration.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Valakut Exploration", 303, Rarity.RARE, mage.cards.v.ValakutExploration.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vanquish the Weak", 131, Rarity.COMMON, mage.cards.v.VanquishTheWeak.class));
        cards.add(new SetCardInfo("Vastwood Fortification", 216, Rarity.UNCOMMON, mage.cards.v.VastwoodFortification.class));
        cards.add(new SetCardInfo("Vastwood Surge", 217, Rarity.UNCOMMON, mage.cards.v.VastwoodSurge.class));
        cards.add(new SetCardInfo("Verazol, the Split Current", 239, Rarity.RARE, mage.cards.v.VerazolTheSplitCurrent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Verazol, the Split Current", 370, Rarity.RARE, mage.cards.v.VerazolTheSplitCurrent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Veteran Adventurer", 218, Rarity.UNCOMMON, mage.cards.v.VeteranAdventurer.class));
        cards.add(new SetCardInfo("Vine Gecko", 219, Rarity.UNCOMMON, mage.cards.v.VineGecko.class));
        cards.add(new SetCardInfo("Wayward Guide-Beast", 176, Rarity.RARE, mage.cards.w.WaywardGuideBeast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wayward Guide-Beast", 356, Rarity.RARE, mage.cards.w.WaywardGuideBeast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Windrider Wizard", 87, Rarity.UNCOMMON, mage.cards.w.WindriderWizard.class));
        cards.add(new SetCardInfo("Yasharn, Implacable Earth", 240, Rarity.RARE, mage.cards.y.YasharnImplacableEarth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Yasharn, Implacable Earth", 371, Rarity.RARE, mage.cards.y.YasharnImplacableEarth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zagras, Thief of Heartbeats", 241, Rarity.RARE, mage.cards.z.ZagrasThiefOfHeartbeats.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zagras, Thief of Heartbeats", 372, Rarity.RARE, mage.cards.z.ZagrasThiefOfHeartbeats.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zareth San, the Trickster", 242, Rarity.RARE, mage.cards.z.ZarethSanTheTrickster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zareth San, the Trickster", 373, Rarity.RARE, mage.cards.z.ZarethSanTheTrickster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zof Consumption", 132, Rarity.UNCOMMON, mage.cards.z.ZofConsumption.class));
        cards.add(new SetCardInfo("Zulaport Duelist", 88, Rarity.COMMON, mage.cards.z.ZulaportDuelist.class));
    }

    @Override
    protected List<CardInfo> findSpecialCardsByRarity(Rarity rarity) {
        List<CardInfo> cardInfos = super.findSpecialCardsByRarity(rarity);
        cardInfos.addAll(CardRepository.instance.findCards(new CardCriteria()
                .setCodes(this.code)
                .rarities(rarity)
                .modalDoubleFaced(true)
                .maxCardNumber(maxCardNumberInBooster)));
        return cardInfos;
    }

    @Override
    public BoosterCollator createCollator() {
        return new ZendikarRisingCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/znr.html
// Using Japanese collation for common/uncommon, rare collation inferred from other sets
class ZendikarRisingCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "45", "110", "99", "254", "105", "102", "255", "117", "135", "114", "243", "128", "79", "107", "116", "168", "92", "131", "71", "93", "251", "119", "78", "103", "164", "101", "112", "45", "101", "243", "116", "135", "107", "254", "103", "128", "164", "117", "79", "93", "102", "251", "131", "71", "99", "112", "255", "114", "78", "110", "168", "119", "45", "105", "92", "164", "93", "103", "135", "101", "105", "251", "117", "79", "114", "168", "99", "254", "107", "78", "131", "255", "110", "92", "45", "119", "128", "243", "112", "102", "71", "116", "135", "92", "103", "254", "99", "114", "79", "107", "168", "117", "105", "243", "131", "71", "119", "112", "251", "110", "78", "101", "128", "255", "93", "164", "116", "102");
    private final CardRun commonB = new CardRun(true, "208", "173", "36", "210", "88", "10", "195", "28", "194", "8", "104", "181", "18", "13", "206", "158", "32", "188", "42", "256", "185", "9", "157", "197", "14", "191", "84", "33", "200", "22", "184", "253", "3", "177", "36", "206", "14", "210", "18", "173", "200", "28", "9", "184", "104", "13", "208", "158", "33", "194", "88", "8", "202", "157", "22", "188", "253", "35", "177", "3", "256", "195", "43", "191", "10", "185", "84", "42", "197", "32", "181", "9", "256", "210", "35", "88", "202", "42", "181", "28", "206", "18", "195", "13", "173", "194", "43", "188", "33", "84", "208", "22", "104", "177", "8", "158", "197", "36", "185", "3", "253", "184", "32", "200", "10", "191", "14", "157", "202", "43", "35");
    private final CardRun commonC = new CardRun(true, "294", "82", "142", "100", "65", "138", "34", "296", "155", "162", "51", "96", "172", "47", "144", "100", "59", "152", "182", "62", "169", "199", "50", "138", "56", "310", "142", "48", "38", "167", "58", "214", "137", "62", "29", "149", "73", "293", "140", "57", "190", "172", "55", "25", "133", "47", "96", "302", "50", "182", "133", "56", "38", "144", "73", "213", "142", "58", "297", "138", "48", "25", "155", "62", "214", "152", "57", "34", "137", "55", "306", "140", "59", "65", "169", "29", "82", "162", "199", "51", "149", "55", "172", "73", "162", "65", "96", "299", "51", "38", "169", "48", "214", "167", "47", "152", "213", "59", "144", "190", "57", "140", "304", "56", "149", "199", "58", "137", "29", "50", "155");
    private final CardRun uncommon = new CardRun(true, "227", "66", "20", "170", "238", "204", "257", "126", "205", "171", "21", "228", "295", "160", "235", "52", "229", "163", "109", "201", "89", "237", "31", "69", "121", "218", "250", "139", "187", "74", "11", "236", "196", "143", "81", "221", "154", "222", "70", "141", "249", "97", "290", "247", "94", "87", "219", "1", "130", "136", "15", "224", "248", "6", "212", "123", "17", "113", "227", "217", "83", "238", "66", "20", "257", "170", "126", "204", "52", "160", "21", "205", "301", "109", "228", "171", "218", "139", "75", "89", "201", "69", "235", "121", "31", "143", "187", "229", "11", "74", "154", "237", "250", "81", "196", "141", "222", "249", "70", "7", "236", "97", "291", "248", "87", "94", "6", "219", "221", "123", "247", "224", "130", "1", "212", "113", "17", "136", "66", "227", "217", "83", "238", "170", "20", "126", "75", "205", "52", "160", "257", "109", "309", "228", "21", "171", "235", "201", "163", "89", "143", "218", "121", "69", "31", "139", "229", "250", "187", "11", "74", "196", "154", "7", "237", "222", "81", "249", "141", "94", "70", "247", "311", "97", "219", "6", "87", "130", "15", "248", "224", "123", "212", "1", "136", "236", "17", "83", "113", "217");
    private final CardRun uncommonDFC = new CardRun(true, "180", "120", "80", "166", "19", "46", "91", "134", "26", "192", "132", "64", "40", "216", "146", "111", "86", "37", "165", "211", "80", "120", "166", "19", "180", "46", "91", "134", "192", "26", "64", "132", "216", "86", "146", "40", "211", "111", "80", "165", "120", "180", "37", "166", "46", "192", "91", "19", "134", "132", "64", "216", "26", "86", "146", "211", "40", "165", "111", "80", "180", "37", "166", "120", "46", "216", "19", "134", "91", "192", "26", "64", "132", "211", "86", "40", "146", "111", "80", "165", "37", "120", "180", "19", "166", "46", "192", "134", "26", "91", "216", "64", "132", "40", "211", "146", "86", "111", "165", "37");
    private final CardRun rare = new CardRun(false, "4", "5", "16", "23", "24", "27", "39", "41", "49", "53", "54", "61", "67", "68", "72", "85", "95", "108", "115", "118", "124", "125", "127", "129", "145", "148", "151", "153", "156", "159", "175", "176", "183", "186", "193", "198", "203", "207", "209", "220", "223", "225", "226", "233", "234", "239", "240", "241", "242", "246", "252", "262", "265", "4", "5", "16", "23", "24", "27", "39", "41", "49", "53", "54", "61", "67", "68", "72", "85", "95", "108", "115", "118", "124", "125", "127", "129", "145", "148", "151", "153", "156", "159", "175", "176", "183", "186", "193", "198", "203", "207", "209", "220", "223", "225", "226", "233", "234", "239", "240", "241", "242", "246", "252", "262", "265", "2", "44", "63", "77", "98", "122", "147", "150", "178", "179", "230", "231", "232", "244", "245");
    private final CardRun rareDFC = new CardRun(false, "30", "60", "106", "174", "189", "258", "259", "260", "261", "263", "264", "30", "60", "106", "174", "189", "258", "259", "260", "261", "263", "264", "12", "76", "90", "161", "215");
    private final CardRun land = new CardRun(true, "266", "278", "276", "269", "279", "272", "277", "267", "275", "273", "270", "274", "268", "280", "271", "278", "269", "279", "266", "277", "272", "276", "280", "267", "278", "271", "275", "273", "270", "274", "266", "279", "272", "276", "268", "277", "269", "280", "267", "271", "275", "278", "274", "279", "266", "276", "273", "277", "270", "268", "280", "275", "269", "278", "267", "271", "279", "274", "272", "273", "269", "266", "276", "275", "270", "277", "271", "268", "273", "269", "272", "280", "267", "276", "274", "268", "270", "278", "266", "273", "279", "271", "275", "277", "272", "267", "274", "280", "268", "276", "270", "278", "266", "273", "269", "279", "272", "267", "275", "271", "274", "268", "277", "270", "280");

    private final BoosterStructure AABBBBCCCC = new BoosterStructure(
            commonA, commonA,
            commonB, commonB, commonB, commonB,
            commonC, commonC, commonC, commonC
    );

    private final BoosterStructure AAABBBCCCC = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB, commonB,
            commonC, commonC, commonC, commonC
    );

    private final BoosterStructure AAABBBBCCC = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB, commonB, commonB,
            commonC, commonC, commonC
    );

    private final BoosterStructure UD = new BoosterStructure(uncommon, uncommon, uncommonDFC, rare);
    private final BoosterStructure RD = new BoosterStructure(uncommon, uncommon, uncommon, rareDFC);
    private final BoosterStructure L1 = new BoosterStructure(land);

    // In order for equal numbers of each common to exist, the average booster must contain:
    // 2.67 A commons (270 / 101)
    // 3.66 B commons (370 / 101)
    // 3.66 C commons (370 / 101)
    private final RarityConfiguration commonRuns = new RarityConfiguration(
            AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC,
            AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC,
            AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC,
            AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC,
            AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC,
            AABBBBCCCC, AABBBBCCCC, AABBBBCCCC,
            AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC,
            AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC,
            AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC,
            AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC,
            AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC,
            AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC,
            AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC,
            AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC,
            AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC,
            AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC,
            AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC,
            AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC
    );

    private final RarityConfiguration uncommonRuns = new RarityConfiguration(UD, UD, UD, UD, UD, UD, UD, UD, UD, RD, RD);
    private final RarityConfiguration landRuns = new RarityConfiguration(L1);

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        booster.addAll(commonRuns.getNext().makeRun());
        booster.addAll(uncommonRuns.getNext().makeRun());
        booster.addAll(landRuns.getNext().makeRun());
        return booster;
    }
}
