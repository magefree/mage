package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.collation.BoosterCollator;
import mage.collation.BoosterStructure;
import mage.collation.CardRun;
import mage.collation.RarityConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Backfir3
 */
public final class UrzasSaga extends ExpansionSet {

    private static final UrzasSaga instance = new UrzasSaga();

    public static UrzasSaga getInstance() {
        return instance;
    }

    private UrzasSaga() {
        super("Urza's Saga", "USG", ExpansionSet.buildDate(1998, 10, 12), SetType.EXPANSION);
        this.blockName = "Urza";
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;

        cards.add(new SetCardInfo("Absolute Grace", 1, Rarity.UNCOMMON, mage.cards.a.AbsoluteGrace.class, RETRO_ART));
        cards.add(new SetCardInfo("Absolute Law", 2, Rarity.UNCOMMON, mage.cards.a.AbsoluteLaw.class, RETRO_ART));
        cards.add(new SetCardInfo("Abundance", 229, Rarity.RARE, mage.cards.a.Abundance.class, RETRO_ART));
        cards.add(new SetCardInfo("Abyssal Horror", 115, Rarity.RARE, mage.cards.a.AbyssalHorror.class, RETRO_ART));
        cards.add(new SetCardInfo("Academy Researchers", 58, Rarity.UNCOMMON, mage.cards.a.AcademyResearchers.class, RETRO_ART));
        cards.add(new SetCardInfo("Acidic Soil", 172, Rarity.UNCOMMON, mage.cards.a.AcidicSoil.class, RETRO_ART));
        cards.add(new SetCardInfo("Acridian", 230, Rarity.COMMON, mage.cards.a.Acridian.class, RETRO_ART));
        cards.add(new SetCardInfo("Albino Troll", 231, Rarity.UNCOMMON, mage.cards.a.AlbinoTroll.class, RETRO_ART));
        cards.add(new SetCardInfo("Anaconda", 232, Rarity.UNCOMMON, mage.cards.a.Anaconda.class, RETRO_ART));
        cards.add(new SetCardInfo("Angelic Chorus", 3, Rarity.RARE, mage.cards.a.AngelicChorus.class, RETRO_ART));
        cards.add(new SetCardInfo("Angelic Page", 4, Rarity.COMMON, mage.cards.a.AngelicPage.class, RETRO_ART));
        cards.add(new SetCardInfo("Annul", 59, Rarity.COMMON, mage.cards.a.Annul.class, RETRO_ART));
        cards.add(new SetCardInfo("Antagonism", 173, Rarity.RARE, mage.cards.a.Antagonism.class, RETRO_ART));
        cards.add(new SetCardInfo("Arc Lightning", 174, Rarity.COMMON, mage.cards.a.ArcLightning.class, RETRO_ART));
        cards.add(new SetCardInfo("Arcane Laboratory", 60, Rarity.UNCOMMON, mage.cards.a.ArcaneLaboratory.class, RETRO_ART));
        cards.add(new SetCardInfo("Argothian Elder", 233, Rarity.UNCOMMON, mage.cards.a.ArgothianElder.class, RETRO_ART));
        cards.add(new SetCardInfo("Argothian Enchantress", 234, Rarity.RARE, mage.cards.a.ArgothianEnchantress.class, RETRO_ART));
        cards.add(new SetCardInfo("Argothian Swine", 235, Rarity.COMMON, mage.cards.a.ArgothianSwine.class, RETRO_ART));
        cards.add(new SetCardInfo("Argothian Wurm", 236, Rarity.RARE, mage.cards.a.ArgothianWurm.class, RETRO_ART));
        cards.add(new SetCardInfo("Attunement", 61, Rarity.RARE, mage.cards.a.Attunement.class, RETRO_ART));
        cards.add(new SetCardInfo("Back to Basics", 62, Rarity.RARE, mage.cards.b.BackToBasics.class, RETRO_ART));
        cards.add(new SetCardInfo("Barrin's Codex", 286, Rarity.RARE, mage.cards.b.BarrinsCodex.class, RETRO_ART));
        cards.add(new SetCardInfo("Barrin, Master Wizard", 63, Rarity.RARE, mage.cards.b.BarrinMasterWizard.class, RETRO_ART));
        cards.add(new SetCardInfo("Bedlam", 175, Rarity.RARE, mage.cards.b.Bedlam.class, RETRO_ART));
        cards.add(new SetCardInfo("Befoul", 116, Rarity.COMMON, mage.cards.b.Befoul.class, RETRO_ART));
        cards.add(new SetCardInfo("Bereavement", 117, Rarity.UNCOMMON, mage.cards.b.Bereavement.class, RETRO_ART));
        cards.add(new SetCardInfo("Blanchwood Armor", 237, Rarity.UNCOMMON, mage.cards.b.BlanchwoodArmor.class, RETRO_ART));
        cards.add(new SetCardInfo("Blanchwood Treefolk", 238, Rarity.COMMON, mage.cards.b.BlanchwoodTreefolk.class, RETRO_ART));
        cards.add(new SetCardInfo("Blasted Landscape", 319, Rarity.UNCOMMON, mage.cards.b.BlastedLandscape.class, RETRO_ART));
        cards.add(new SetCardInfo("Blood Vassal", 118, Rarity.COMMON, mage.cards.b.BloodVassal.class, RETRO_ART));
        cards.add(new SetCardInfo("Bog Raiders", 119, Rarity.COMMON, mage.cards.b.BogRaiders.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Bog Raiders", "119s", Rarity.COMMON, mage.cards.b.BogRaiders.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Brand", 176, Rarity.RARE, mage.cards.b.Brand.class, RETRO_ART));
        cards.add(new SetCardInfo("Bravado", 177, Rarity.COMMON, mage.cards.b.Bravado.class, RETRO_ART));
        cards.add(new SetCardInfo("Breach", 120, Rarity.COMMON, mage.cards.b.Breach.class, RETRO_ART));
        cards.add(new SetCardInfo("Brilliant Halo", 5, Rarity.COMMON, mage.cards.b.BrilliantHalo.class, RETRO_ART));
        cards.add(new SetCardInfo("Bull Hippo", 239, Rarity.UNCOMMON, mage.cards.b.BullHippo.class, RETRO_ART));
        cards.add(new SetCardInfo("Bulwark", 178, Rarity.RARE, mage.cards.b.Bulwark.class, RETRO_ART));
        cards.add(new SetCardInfo("Cackling Fiend", 121, Rarity.COMMON, mage.cards.c.CacklingFiend.class, RETRO_ART));
        cards.add(new SetCardInfo("Carpet of Flowers", 240, Rarity.UNCOMMON, mage.cards.c.CarpetOfFlowers.class, RETRO_ART));
        cards.add(new SetCardInfo("Carrion Beetles", 122, Rarity.COMMON, mage.cards.c.CarrionBeetles.class, RETRO_ART));
        cards.add(new SetCardInfo("Catalog", 64, Rarity.COMMON, mage.cards.c.Catalog.class, RETRO_ART));
        cards.add(new SetCardInfo("Catastrophe", 6, Rarity.RARE, mage.cards.c.Catastrophe.class, RETRO_ART));
        cards.add(new SetCardInfo("Cathodion", 287, Rarity.UNCOMMON, mage.cards.c.Cathodion.class, RETRO_ART));
        cards.add(new SetCardInfo("Cave Tiger", 241, Rarity.COMMON, mage.cards.c.CaveTiger.class, RETRO_ART));
        cards.add(new SetCardInfo("Child of Gaea", 242, Rarity.RARE, mage.cards.c.ChildOfGaea.class, RETRO_ART));
        cards.add(new SetCardInfo("Chimeric Staff", 288, Rarity.RARE, mage.cards.c.ChimericStaff.class, RETRO_ART));
        cards.add(new SetCardInfo("Citanul Centaurs", 243, Rarity.RARE, mage.cards.c.CitanulCentaurs.class, RETRO_ART));
        cards.add(new SetCardInfo("Citanul Flute", 289, Rarity.RARE, mage.cards.c.CitanulFlute.class, RETRO_ART));
        cards.add(new SetCardInfo("Citanul Hierophants", 244, Rarity.RARE, mage.cards.c.CitanulHierophants.class, RETRO_ART));
        cards.add(new SetCardInfo("Claws of Gix", 290, Rarity.UNCOMMON, mage.cards.c.ClawsOfGix.class, RETRO_ART));
        cards.add(new SetCardInfo("Clear", 7, Rarity.UNCOMMON, mage.cards.c.Clear.class, RETRO_ART));
        cards.add(new SetCardInfo("Cloak of Mists", 65, Rarity.COMMON, mage.cards.c.CloakOfMists.class, RETRO_ART));
        cards.add(new SetCardInfo("Confiscate", 66, Rarity.UNCOMMON, mage.cards.c.Confiscate.class, RETRO_ART));
        cards.add(new SetCardInfo("Congregate", 8, Rarity.COMMON, mage.cards.c.Congregate.class, RETRO_ART));
        cards.add(new SetCardInfo("Contamination", 123, Rarity.RARE, mage.cards.c.Contamination.class, RETRO_ART));
        cards.add(new SetCardInfo("Copper Gnomes", 291, Rarity.RARE, mage.cards.c.CopperGnomes.class, RETRO_ART));
        cards.add(new SetCardInfo("Coral Merfolk", 67, Rarity.COMMON, mage.cards.c.CoralMerfolk.class, RETRO_ART));
        cards.add(new SetCardInfo("Corrupt", 124, Rarity.COMMON, mage.cards.c.Corrupt.class, RETRO_ART));
        cards.add(new SetCardInfo("Cradle Guard", 245, Rarity.UNCOMMON, mage.cards.c.CradleGuard.class, RETRO_ART));
        cards.add(new SetCardInfo("Crater Hellion", 179, Rarity.RARE, mage.cards.c.CraterHellion.class, RETRO_ART));
        cards.add(new SetCardInfo("Crazed Skirge", 125, Rarity.UNCOMMON, mage.cards.c.CrazedSkirge.class, RETRO_ART));
        cards.add(new SetCardInfo("Crosswinds", 246, Rarity.UNCOMMON, mage.cards.c.Crosswinds.class, RETRO_ART));
        cards.add(new SetCardInfo("Crystal Chimes", 292, Rarity.UNCOMMON, mage.cards.c.CrystalChimes.class, RETRO_ART));
        cards.add(new SetCardInfo("Curfew", 68, Rarity.COMMON, mage.cards.c.Curfew.class, RETRO_ART));
        cards.add(new SetCardInfo("Dark Hatchling", 126, Rarity.RARE, mage.cards.d.DarkHatchling.class, RETRO_ART));
        cards.add(new SetCardInfo("Dark Ritual", 127, Rarity.COMMON, mage.cards.d.DarkRitual.class, RETRO_ART));
        cards.add(new SetCardInfo("Darkest Hour", 128, Rarity.RARE, mage.cards.d.DarkestHour.class, RETRO_ART));
        cards.add(new SetCardInfo("Defensive Formation", 9, Rarity.UNCOMMON, mage.cards.d.DefensiveFormation.class, RETRO_ART));
        cards.add(new SetCardInfo("Despondency", 129, Rarity.COMMON, mage.cards.d.Despondency.class, RETRO_ART));
        cards.add(new SetCardInfo("Destructive Urge", 180, Rarity.UNCOMMON, mage.cards.d.DestructiveUrge.class, RETRO_ART));
        cards.add(new SetCardInfo("Diabolic Servitude", 130, Rarity.UNCOMMON, mage.cards.d.DiabolicServitude.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Diabolic Servitude", "130s", Rarity.UNCOMMON, mage.cards.d.DiabolicServitude.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Disciple of Grace", 10, Rarity.COMMON, mage.cards.d.DiscipleOfGrace.class, RETRO_ART));
        cards.add(new SetCardInfo("Disciple of Law", 11, Rarity.COMMON, mage.cards.d.DiscipleOfLaw.class, RETRO_ART));
        cards.add(new SetCardInfo("Discordant Dirge", 131, Rarity.RARE, mage.cards.d.DiscordantDirge.class, RETRO_ART));
        cards.add(new SetCardInfo("Disenchant", 12, Rarity.COMMON, mage.cards.d.Disenchant.class, RETRO_ART));
        cards.add(new SetCardInfo("Disorder", 181, Rarity.UNCOMMON, mage.cards.d.Disorder.class, RETRO_ART));
        cards.add(new SetCardInfo("Disruptive Student", 69, Rarity.COMMON, mage.cards.d.DisruptiveStudent.class, RETRO_ART));
        cards.add(new SetCardInfo("Douse", 70, Rarity.UNCOMMON, mage.cards.d.Douse.class, RETRO_ART));
        cards.add(new SetCardInfo("Dragon Blood", 293, Rarity.UNCOMMON, mage.cards.d.DragonBlood.class, RETRO_ART));
        cards.add(new SetCardInfo("Drifting Djinn", 71, Rarity.RARE, mage.cards.d.DriftingDjinn.class, RETRO_ART));
        cards.add(new SetCardInfo("Drifting Meadow", 320, Rarity.COMMON, mage.cards.d.DriftingMeadow.class, RETRO_ART));
        cards.add(new SetCardInfo("Dromosaur", 182, Rarity.COMMON, mage.cards.d.Dromosaur.class, RETRO_ART));
        cards.add(new SetCardInfo("Duress", 132, Rarity.COMMON, mage.cards.d.Duress.class, RETRO_ART));
        cards.add(new SetCardInfo("Eastern Paladin", 133, Rarity.RARE, mage.cards.e.EasternPaladin.class, RETRO_ART));
        cards.add(new SetCardInfo("Electryte", 183, Rarity.RARE, mage.cards.e.Electryte.class, RETRO_ART));
        cards.add(new SetCardInfo("Elite Archers", 13, Rarity.RARE, mage.cards.e.EliteArchers.class, RETRO_ART));
        cards.add(new SetCardInfo("Elvish Herder", 247, Rarity.COMMON, mage.cards.e.ElvishHerder.class, RETRO_ART));
        cards.add(new SetCardInfo("Elvish Lyrist", 248, Rarity.COMMON, mage.cards.e.ElvishLyrist.class, RETRO_ART));
        cards.add(new SetCardInfo("Enchantment Alteration", 72, Rarity.UNCOMMON, mage.cards.e.EnchantmentAlteration.class, RETRO_ART));
        cards.add(new SetCardInfo("Endless Wurm", 249, Rarity.RARE, mage.cards.e.EndlessWurm.class, RETRO_ART));
        cards.add(new SetCardInfo("Endoskeleton", 294, Rarity.UNCOMMON, mage.cards.e.Endoskeleton.class, RETRO_ART));
        cards.add(new SetCardInfo("Energy Field", 73, Rarity.RARE, mage.cards.e.EnergyField.class, RETRO_ART));
        cards.add(new SetCardInfo("Exhaustion", 74, Rarity.UNCOMMON, mage.cards.e.Exhaustion.class, RETRO_ART));
        cards.add(new SetCardInfo("Exhume", 134, Rarity.COMMON, mage.cards.e.Exhume.class, RETRO_ART));
        cards.add(new SetCardInfo("Exploration", 250, Rarity.RARE, mage.cards.e.Exploration.class, RETRO_ART));
        cards.add(new SetCardInfo("Expunge", 135, Rarity.COMMON, mage.cards.e.Expunge.class, RETRO_ART));
        cards.add(new SetCardInfo("Faith Healer", 14, Rarity.RARE, mage.cards.f.FaithHealer.class, RETRO_ART));
        cards.add(new SetCardInfo("Falter", 184, Rarity.COMMON, mage.cards.f.Falter.class, RETRO_ART));
        cards.add(new SetCardInfo("Fault Line", 185, Rarity.RARE, mage.cards.f.FaultLine.class, RETRO_ART));
        cards.add(new SetCardInfo("Fecundity", 251, Rarity.UNCOMMON, mage.cards.f.Fecundity.class, RETRO_ART));
        cards.add(new SetCardInfo("Fertile Ground", 252, Rarity.COMMON, mage.cards.f.FertileGround.class, RETRO_ART));
        cards.add(new SetCardInfo("Fiery Mantle", 186, Rarity.COMMON, mage.cards.f.FieryMantle.class, RETRO_ART));
        cards.add(new SetCardInfo("Fire Ants", 187, Rarity.UNCOMMON, mage.cards.f.FireAnts.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Fire Ants", "187s", Rarity.UNCOMMON, mage.cards.f.FireAnts.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Flesh Reaver", 136, Rarity.UNCOMMON, mage.cards.f.FleshReaver.class, RETRO_ART));
        cards.add(new SetCardInfo("Fluctuator", 295, Rarity.RARE, mage.cards.f.Fluctuator.class, RETRO_ART));
        cards.add(new SetCardInfo("Fog Bank", 75, Rarity.UNCOMMON, mage.cards.f.FogBank.class, RETRO_ART));
        cards.add(new SetCardInfo("Forest", 347, Rarity.LAND, mage.cards.basiclands.Forest.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 348, Rarity.LAND, mage.cards.basiclands.Forest.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 349, Rarity.LAND, mage.cards.basiclands.Forest.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 350, Rarity.LAND, mage.cards.basiclands.Forest.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Fortitude", 253, Rarity.COMMON, mage.cards.f.Fortitude.class, RETRO_ART));
        cards.add(new SetCardInfo("Gaea's Bounty", 254, Rarity.COMMON, mage.cards.g.GaeasBounty.class, RETRO_ART));
        cards.add(new SetCardInfo("Gaea's Cradle", 321, Rarity.RARE, mage.cards.g.GaeasCradle.class, RETRO_ART));
        cards.add(new SetCardInfo("Gaea's Embrace", 255, Rarity.UNCOMMON, mage.cards.g.GaeasEmbrace.class, RETRO_ART));
        cards.add(new SetCardInfo("Gamble", 188, Rarity.RARE, mage.cards.g.Gamble.class, RETRO_ART));
        cards.add(new SetCardInfo("Gilded Drake", 76, Rarity.RARE, mage.cards.g.GildedDrake.class, RETRO_ART));
        cards.add(new SetCardInfo("Glorious Anthem", 15, Rarity.RARE, mage.cards.g.GloriousAnthem.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Cadets", 189, Rarity.UNCOMMON, mage.cards.g.GoblinCadets.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Lackey", 190, Rarity.UNCOMMON, mage.cards.g.GoblinLackey.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Matron", 191, Rarity.COMMON, mage.cards.g.GoblinMatron.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Offensive", 192, Rarity.UNCOMMON, mage.cards.g.GoblinOffensive.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Patrol", 193, Rarity.COMMON, mage.cards.g.GoblinPatrol.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Raider", 194, Rarity.COMMON, mage.cards.g.GoblinRaider.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Spelunkers", 195, Rarity.COMMON, mage.cards.g.GoblinSpelunkers.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin War Buggy", 196, Rarity.COMMON, mage.cards.g.GoblinWarBuggy.class, RETRO_ART));
        cards.add(new SetCardInfo("Gorilla Warrior", 256, Rarity.COMMON, mage.cards.g.GorillaWarrior.class, RETRO_ART));
        cards.add(new SetCardInfo("Grafted Skullcap", 296, Rarity.RARE, mage.cards.g.GraftedSkullcap.class, RETRO_ART));
        cards.add(new SetCardInfo("Great Whale", 77, Rarity.RARE, mage.cards.g.GreatWhale.class, RETRO_ART));
        cards.add(new SetCardInfo("Greater Good", 257, Rarity.RARE, mage.cards.g.GreaterGood.class, RETRO_ART));
        cards.add(new SetCardInfo("Greener Pastures", 258, Rarity.RARE, mage.cards.g.GreenerPastures.class, RETRO_ART));
        cards.add(new SetCardInfo("Guma", 197, Rarity.UNCOMMON, mage.cards.g.Guma.class, RETRO_ART));
        cards.add(new SetCardInfo("Hawkeater Moth", 259, Rarity.UNCOMMON, mage.cards.h.HawkeaterMoth.class, RETRO_ART));
        cards.add(new SetCardInfo("Headlong Rush", 198, Rarity.COMMON, mage.cards.h.HeadlongRush.class, RETRO_ART));
        cards.add(new SetCardInfo("Healing Salve", 16, Rarity.COMMON, mage.cards.h.HealingSalve.class, RETRO_ART));
        cards.add(new SetCardInfo("Heat Ray", 199, Rarity.COMMON, mage.cards.h.HeatRay.class, RETRO_ART));
        cards.add(new SetCardInfo("Herald of Serra", 17, Rarity.RARE, mage.cards.h.HeraldOfSerra.class, RETRO_ART));
        cards.add(new SetCardInfo("Hermetic Study", 78, Rarity.COMMON, mage.cards.h.HermeticStudy.class, RETRO_ART));
        cards.add(new SetCardInfo("Hibernation", 79, Rarity.UNCOMMON, mage.cards.h.Hibernation.class, RETRO_ART));
        cards.add(new SetCardInfo("Hidden Ancients", 260, Rarity.UNCOMMON, mage.cards.h.HiddenAncients.class, RETRO_ART));
        cards.add(new SetCardInfo("Hidden Guerrillas", 261, Rarity.UNCOMMON, mage.cards.h.HiddenGuerrillas.class, RETRO_ART));
        cards.add(new SetCardInfo("Hidden Herd", 262, Rarity.RARE, mage.cards.h.HiddenHerd.class, RETRO_ART));
        cards.add(new SetCardInfo("Hidden Predators", 263, Rarity.RARE, mage.cards.h.HiddenPredators.class, RETRO_ART));
        cards.add(new SetCardInfo("Hidden Spider", 264, Rarity.COMMON, mage.cards.h.HiddenSpider.class, RETRO_ART));
        cards.add(new SetCardInfo("Hidden Stag", 265, Rarity.RARE, mage.cards.h.HiddenStag.class, RETRO_ART));
        cards.add(new SetCardInfo("Hollow Dogs", 137, Rarity.COMMON, mage.cards.h.HollowDogs.class, RETRO_ART));
        cards.add(new SetCardInfo("Hopping Automaton", 297, Rarity.UNCOMMON, mage.cards.h.HoppingAutomaton.class, RETRO_ART));
        cards.add(new SetCardInfo("Horseshoe Crab", 80, Rarity.COMMON, mage.cards.h.HorseshoeCrab.class, RETRO_ART));
        cards.add(new SetCardInfo("Humble", 18, Rarity.UNCOMMON, mage.cards.h.Humble.class, RETRO_ART));
        cards.add(new SetCardInfo("Hush", 266, Rarity.COMMON, mage.cards.h.Hush.class, RETRO_ART));
        cards.add(new SetCardInfo("Ill-Gotten Gains", 138, Rarity.RARE, mage.cards.i.IllGottenGains.class, RETRO_ART));
        cards.add(new SetCardInfo("Imaginary Pet", 81, Rarity.RARE, mage.cards.i.ImaginaryPet.class, RETRO_ART));
        cards.add(new SetCardInfo("Intrepid Hero", 19, Rarity.RARE, mage.cards.i.IntrepidHero.class, RETRO_ART));
        cards.add(new SetCardInfo("Island", 335, Rarity.LAND, mage.cards.basiclands.Island.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 336, Rarity.LAND, mage.cards.basiclands.Island.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 337, Rarity.LAND, mage.cards.basiclands.Island.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 338, Rarity.LAND, mage.cards.basiclands.Island.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Jagged Lightning", 200, Rarity.UNCOMMON, mage.cards.j.JaggedLightning.class, RETRO_ART));
        cards.add(new SetCardInfo("Karn, Silver Golem", 298, Rarity.RARE, mage.cards.k.KarnSilverGolem.class, RETRO_ART));
        cards.add(new SetCardInfo("Launch", 82, Rarity.COMMON, mage.cards.l.Launch.class, RETRO_ART));
        cards.add(new SetCardInfo("Lay Waste", 201, Rarity.COMMON, mage.cards.l.LayWaste.class, RETRO_ART));
        cards.add(new SetCardInfo("Lifeline", 299, Rarity.RARE, mage.cards.l.Lifeline.class, RETRO_ART));
        cards.add(new SetCardInfo("Lightning Dragon", 202, Rarity.RARE, mage.cards.l.LightningDragon.class, RETRO_ART));
        cards.add(new SetCardInfo("Lilting Refrain", 83, Rarity.UNCOMMON, mage.cards.l.LiltingRefrain.class, RETRO_ART));
        cards.add(new SetCardInfo("Lingering Mirage", 84, Rarity.UNCOMMON, mage.cards.l.LingeringMirage.class, RETRO_ART));
        cards.add(new SetCardInfo("Looming Shade", 139, Rarity.COMMON, mage.cards.l.LoomingShade.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Looming Shade", "139s", Rarity.COMMON, mage.cards.l.LoomingShade.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Lotus Blossom", 300, Rarity.RARE, mage.cards.l.LotusBlossom.class, RETRO_ART));
        cards.add(new SetCardInfo("Lull", 267, Rarity.COMMON, mage.cards.l.Lull.class, RETRO_ART));
        cards.add(new SetCardInfo("Lurking Evil", 140, Rarity.RARE, mage.cards.l.LurkingEvil.class, RETRO_ART));
        cards.add(new SetCardInfo("Mana Leech", 141, Rarity.UNCOMMON, mage.cards.m.ManaLeech.class, RETRO_ART));
        cards.add(new SetCardInfo("Meltdown", 203, Rarity.UNCOMMON, mage.cards.m.Meltdown.class, RETRO_ART));
        cards.add(new SetCardInfo("Metrognome", 301, Rarity.RARE, mage.cards.m.Metrognome.class, RETRO_ART));
        cards.add(new SetCardInfo("Midsummer Revel", 268, Rarity.RARE, mage.cards.m.MidsummerRevel.class, RETRO_ART));
        cards.add(new SetCardInfo("Mishra's Helix", 302, Rarity.RARE, mage.cards.m.MishrasHelix.class, RETRO_ART));
        cards.add(new SetCardInfo("Mobile Fort", 303, Rarity.UNCOMMON, mage.cards.m.MobileFort.class, RETRO_ART));
        cards.add(new SetCardInfo("Monk Idealist", 20, Rarity.UNCOMMON, mage.cards.m.MonkIdealist.class, RETRO_ART));
        cards.add(new SetCardInfo("Monk Realist", 21, Rarity.COMMON, mage.cards.m.MonkRealist.class, RETRO_ART));
        cards.add(new SetCardInfo("Morphling", 85, Rarity.RARE, mage.cards.m.Morphling.class, RETRO_ART));
        cards.add(new SetCardInfo("Mountain", 343, Rarity.LAND, mage.cards.basiclands.Mountain.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 344, Rarity.LAND, mage.cards.basiclands.Mountain.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 345, Rarity.LAND, mage.cards.basiclands.Mountain.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 346, Rarity.LAND, mage.cards.basiclands.Mountain.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("No Rest for the Wicked", 142, Rarity.UNCOMMON, mage.cards.n.NoRestForTheWicked.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("No Rest for the Wicked", "142s", Rarity.UNCOMMON, mage.cards.n.NoRestForTheWicked.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Noetic Scales", 304, Rarity.RARE, mage.cards.n.NoeticScales.class, RETRO_ART));
        cards.add(new SetCardInfo("Okk", 204, Rarity.RARE, mage.cards.o.Okk.class, RETRO_ART));
        cards.add(new SetCardInfo("Opal Acrolith", 22, Rarity.UNCOMMON, mage.cards.o.OpalAcrolith.class, RETRO_ART));
        cards.add(new SetCardInfo("Opal Archangel", 23, Rarity.RARE, mage.cards.o.OpalArchangel.class, RETRO_ART));
        cards.add(new SetCardInfo("Opal Caryatid", 24, Rarity.COMMON, mage.cards.o.OpalCaryatid.class, RETRO_ART));
        cards.add(new SetCardInfo("Opal Gargoyle", 25, Rarity.COMMON, mage.cards.o.OpalGargoyle.class, RETRO_ART));
        cards.add(new SetCardInfo("Opal Titan", 26, Rarity.RARE, mage.cards.o.OpalTitan.class, RETRO_ART));
        cards.add(new SetCardInfo("Oppression", 143, Rarity.RARE, mage.cards.o.Oppression.class, RETRO_ART));
        cards.add(new SetCardInfo("Order of Yawgmoth", 144, Rarity.UNCOMMON, mage.cards.o.OrderOfYawgmoth.class, RETRO_ART));
        cards.add(new SetCardInfo("Outmaneuver", 205, Rarity.UNCOMMON, mage.cards.o.Outmaneuver.class, RETRO_ART));
        cards.add(new SetCardInfo("Pacifism", 27, Rarity.COMMON, mage.cards.p.Pacifism.class, RETRO_ART));
        cards.add(new SetCardInfo("Parasitic Bond", 145, Rarity.UNCOMMON, mage.cards.p.ParasiticBond.class, RETRO_ART));
        cards.add(new SetCardInfo("Pariah", 28, Rarity.RARE, mage.cards.p.Pariah.class, RETRO_ART));
        cards.add(new SetCardInfo("Path of Peace", 29, Rarity.COMMON, mage.cards.p.PathOfPeace.class, RETRO_ART));
        cards.add(new SetCardInfo("Pegasus Charger", 30, Rarity.COMMON, mage.cards.p.PegasusCharger.class, RETRO_ART));
        cards.add(new SetCardInfo("Pendrell Drake", 86, Rarity.COMMON, mage.cards.p.PendrellDrake.class, RETRO_ART));
        cards.add(new SetCardInfo("Pendrell Flux", 87, Rarity.COMMON, mage.cards.p.PendrellFlux.class, RETRO_ART));
        cards.add(new SetCardInfo("Peregrine Drake", 88, Rarity.UNCOMMON, mage.cards.p.PeregrineDrake.class, RETRO_ART));
        cards.add(new SetCardInfo("Persecute", 146, Rarity.RARE, mage.cards.p.Persecute.class, RETRO_ART));
        cards.add(new SetCardInfo("Pestilence", 147, Rarity.COMMON, mage.cards.p.Pestilence.class, RETRO_ART));
        cards.add(new SetCardInfo("Phyrexian Colossus", 305, Rarity.RARE, mage.cards.p.PhyrexianColossus.class, RETRO_ART));
        cards.add(new SetCardInfo("Phyrexian Ghoul", 148, Rarity.COMMON, mage.cards.p.PhyrexianGhoul.class, RETRO_ART));
        cards.add(new SetCardInfo("Phyrexian Processor", 306, Rarity.RARE, mage.cards.p.PhyrexianProcessor.class, RETRO_ART));
        cards.add(new SetCardInfo("Phyrexian Tower", 322, Rarity.RARE, mage.cards.p.PhyrexianTower.class, RETRO_ART));
        cards.add(new SetCardInfo("Pit Trap", 307, Rarity.UNCOMMON, mage.cards.p.PitTrap.class, RETRO_ART));
        cards.add(new SetCardInfo("Plains", 331, Rarity.LAND, mage.cards.basiclands.Plains.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 332, Rarity.LAND, mage.cards.basiclands.Plains.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 333, Rarity.LAND, mage.cards.basiclands.Plains.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 334, Rarity.LAND, mage.cards.basiclands.Plains.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Planar Birth", 31, Rarity.RARE, mage.cards.p.PlanarBirth.class, RETRO_ART));
        cards.add(new SetCardInfo("Planar Void", 149, Rarity.UNCOMMON, mage.cards.p.PlanarVoid.class, RETRO_ART));
        cards.add(new SetCardInfo("Polluted Mire", 323, Rarity.COMMON, mage.cards.p.PollutedMire.class, RETRO_ART));
        cards.add(new SetCardInfo("Pouncing Jaguar", 269, Rarity.COMMON, mage.cards.p.PouncingJaguar.class, RETRO_ART));
        cards.add(new SetCardInfo("Power Sink", 89, Rarity.COMMON, mage.cards.p.PowerSink.class, RETRO_ART));
        cards.add(new SetCardInfo("Power Taint", 90, Rarity.COMMON, mage.cards.p.PowerTaint.class, RETRO_ART));
        cards.add(new SetCardInfo("Presence of the Master", 32, Rarity.UNCOMMON, mage.cards.p.PresenceOfTheMaster.class, RETRO_ART));
        cards.add(new SetCardInfo("Priest of Gix", 150, Rarity.UNCOMMON, mage.cards.p.PriestOfGix.class, RETRO_ART));
        cards.add(new SetCardInfo("Priest of Titania", 270, Rarity.COMMON, mage.cards.p.PriestOfTitania.class, RETRO_ART));
        cards.add(new SetCardInfo("Purging Scythe", 308, Rarity.RARE, mage.cards.p.PurgingScythe.class, RETRO_ART));
        cards.add(new SetCardInfo("Rain of Filth", 151, Rarity.UNCOMMON, mage.cards.r.RainOfFilth.class, RETRO_ART));
        cards.add(new SetCardInfo("Rain of Salt", 206, Rarity.UNCOMMON, mage.cards.r.RainOfSalt.class, RETRO_ART));
        cards.add(new SetCardInfo("Ravenous Skirge", 152, Rarity.COMMON, mage.cards.r.RavenousSkirge.class, RETRO_ART));
        cards.add(new SetCardInfo("Raze", 207, Rarity.COMMON, mage.cards.r.Raze.class, RETRO_ART));
        cards.add(new SetCardInfo("Recantation", 91, Rarity.RARE, mage.cards.r.Recantation.class, RETRO_ART));
        cards.add(new SetCardInfo("Reclusive Wight", 153, Rarity.UNCOMMON, mage.cards.r.ReclusiveWight.class, RETRO_ART));
        cards.add(new SetCardInfo("Redeem", 33, Rarity.UNCOMMON, mage.cards.r.Redeem.class, RETRO_ART));
        cards.add(new SetCardInfo("Reflexes", 208, Rarity.COMMON, mage.cards.r.Reflexes.class, RETRO_ART));
        cards.add(new SetCardInfo("Rejuvenate", 271, Rarity.COMMON, mage.cards.r.Rejuvenate.class, RETRO_ART));
        cards.add(new SetCardInfo("Remembrance", 34, Rarity.RARE, mage.cards.r.Remembrance.class, RETRO_ART));
        cards.add(new SetCardInfo("Remote Isle", 324, Rarity.COMMON, mage.cards.r.RemoteIsle.class, RETRO_ART));
        cards.add(new SetCardInfo("Reprocess", 154, Rarity.RARE, mage.cards.r.Reprocess.class, RETRO_ART));
        cards.add(new SetCardInfo("Rescind", 92, Rarity.COMMON, mage.cards.r.Rescind.class, RETRO_ART));
        cards.add(new SetCardInfo("Retaliation", 272, Rarity.UNCOMMON, mage.cards.r.Retaliation.class, RETRO_ART));
        cards.add(new SetCardInfo("Retromancer", 209, Rarity.COMMON, mage.cards.r.Retromancer.class, RETRO_ART));
        cards.add(new SetCardInfo("Rewind", 93, Rarity.COMMON, mage.cards.r.Rewind.class, RETRO_ART));
        cards.add(new SetCardInfo("Rumbling Crescendo", 210, Rarity.RARE, mage.cards.r.RumblingCrescendo.class, RETRO_ART));
        cards.add(new SetCardInfo("Rune of Protection: Artifacts", 35, Rarity.UNCOMMON, mage.cards.r.RuneOfProtectionArtifacts.class, RETRO_ART));
        cards.add(new SetCardInfo("Rune of Protection: Black", 36, Rarity.COMMON, mage.cards.r.RuneOfProtectionBlack.class, RETRO_ART));
        cards.add(new SetCardInfo("Rune of Protection: Blue", 37, Rarity.COMMON, mage.cards.r.RuneOfProtectionBlue.class, RETRO_ART));
        cards.add(new SetCardInfo("Rune of Protection: Green", 38, Rarity.COMMON, mage.cards.r.RuneOfProtectionGreen.class, RETRO_ART));
        cards.add(new SetCardInfo("Rune of Protection: Lands", 39, Rarity.RARE, mage.cards.r.RuneOfProtectionLands.class, RETRO_ART));
        cards.add(new SetCardInfo("Rune of Protection: Red", 40, Rarity.COMMON, mage.cards.r.RuneOfProtectionRed.class, RETRO_ART));
        cards.add(new SetCardInfo("Rune of Protection: White", 41, Rarity.COMMON, mage.cards.r.RuneOfProtectionWhite.class, RETRO_ART));
        cards.add(new SetCardInfo("Sanctum Custodian", 42, Rarity.COMMON, mage.cards.s.SanctumCustodian.class, RETRO_ART));
        cards.add(new SetCardInfo("Sanctum Guardian", 43, Rarity.UNCOMMON, mage.cards.s.SanctumGuardian.class, RETRO_ART));
        cards.add(new SetCardInfo("Sandbar Merfolk", 94, Rarity.COMMON, mage.cards.s.SandbarMerfolk.class, RETRO_ART));
        cards.add(new SetCardInfo("Sandbar Serpent", 95, Rarity.UNCOMMON, mage.cards.s.SandbarSerpent.class, RETRO_ART));
        cards.add(new SetCardInfo("Sanguine Guard", 155, Rarity.UNCOMMON, mage.cards.s.SanguineGuard.class, RETRO_ART));
        cards.add(new SetCardInfo("Scald", 211, Rarity.UNCOMMON, mage.cards.s.Scald.class, RETRO_ART));
        cards.add(new SetCardInfo("Scoria Wurm", 212, Rarity.RARE, mage.cards.s.ScoriaWurm.class, RETRO_ART));
        cards.add(new SetCardInfo("Scrap", 213, Rarity.COMMON, mage.cards.s.Scrap.class, RETRO_ART));
        cards.add(new SetCardInfo("Seasoned Marshal", 44, Rarity.UNCOMMON, mage.cards.s.SeasonedMarshal.class, RETRO_ART));
        cards.add(new SetCardInfo("Serra Avatar", 45, Rarity.RARE, mage.cards.s.SerraAvatar.class, RETRO_ART));
        cards.add(new SetCardInfo("Serra Zealot", 46, Rarity.COMMON, mage.cards.s.SerraZealot.class, RETRO_ART));
        cards.add(new SetCardInfo("Serra's Embrace", 47, Rarity.UNCOMMON, mage.cards.s.SerrasEmbrace.class, RETRO_ART));
        cards.add(new SetCardInfo("Serra's Hymn", 48, Rarity.UNCOMMON, mage.cards.s.SerrasHymn.class, RETRO_ART));
        cards.add(new SetCardInfo("Serra's Liturgy", 49, Rarity.RARE, mage.cards.s.SerrasLiturgy.class, RETRO_ART));
        cards.add(new SetCardInfo("Serra's Sanctum", 325, Rarity.RARE, mage.cards.s.SerrasSanctum.class, RETRO_ART));
        cards.add(new SetCardInfo("Shimmering Barrier", 50, Rarity.UNCOMMON, mage.cards.s.ShimmeringBarrier.class, RETRO_ART));
        cards.add(new SetCardInfo("Shiv's Embrace", 216, Rarity.UNCOMMON, mage.cards.s.ShivsEmbrace.class, RETRO_ART));
        cards.add(new SetCardInfo("Shivan Gorge", 326, Rarity.RARE, mage.cards.s.ShivanGorge.class, RETRO_ART));
        cards.add(new SetCardInfo("Shivan Hellkite", 214, Rarity.RARE, mage.cards.s.ShivanHellkite.class, RETRO_ART));
        cards.add(new SetCardInfo("Shivan Raptor", 215, Rarity.UNCOMMON, mage.cards.s.ShivanRaptor.class, RETRO_ART));
        cards.add(new SetCardInfo("Show and Tell", 96, Rarity.RARE, mage.cards.s.ShowAndTell.class, RETRO_ART));
        cards.add(new SetCardInfo("Shower of Sparks", 217, Rarity.COMMON, mage.cards.s.ShowerOfSparks.class, RETRO_ART));
        cards.add(new SetCardInfo("Sicken", 156, Rarity.COMMON, mage.cards.s.Sicken.class, RETRO_ART));
        cards.add(new SetCardInfo("Silent Attendant", 51, Rarity.COMMON, mage.cards.s.SilentAttendant.class, RETRO_ART));
        cards.add(new SetCardInfo("Skirge Familiar", 157, Rarity.UNCOMMON, mage.cards.s.SkirgeFamiliar.class, RETRO_ART));
        cards.add(new SetCardInfo("Skittering Skirge", 158, Rarity.COMMON, mage.cards.s.SkitteringSkirge.class, RETRO_ART));
        cards.add(new SetCardInfo("Sleeper Agent", 159, Rarity.RARE, mage.cards.s.SleeperAgent.class, RETRO_ART));
        cards.add(new SetCardInfo("Slippery Karst", 327, Rarity.COMMON, mage.cards.s.SlipperyKarst.class, RETRO_ART));
        cards.add(new SetCardInfo("Smokestack", 309, Rarity.RARE, mage.cards.s.Smokestack.class, RETRO_ART));
        cards.add(new SetCardInfo("Smoldering Crater", 328, Rarity.COMMON, mage.cards.s.SmolderingCrater.class, RETRO_ART));
        cards.add(new SetCardInfo("Sneak Attack", 218, Rarity.RARE, mage.cards.s.SneakAttack.class, RETRO_ART));
        cards.add(new SetCardInfo("Somnophore", 97, Rarity.RARE, mage.cards.s.Somnophore.class, RETRO_ART));
        cards.add(new SetCardInfo("Songstitcher", 52, Rarity.UNCOMMON, mage.cards.s.Songstitcher.class, RETRO_ART));
        cards.add(new SetCardInfo("Soul Sculptor", 53, Rarity.RARE, mage.cards.s.SoulSculptor.class, RETRO_ART));
        cards.add(new SetCardInfo("Spined Fluke", 160, Rarity.UNCOMMON, mage.cards.s.SpinedFluke.class, RETRO_ART));
        cards.add(new SetCardInfo("Spire Owl", 98, Rarity.COMMON, mage.cards.s.SpireOwl.class, RETRO_ART));
        cards.add(new SetCardInfo("Sporogenesis", 273, Rarity.RARE, mage.cards.s.Sporogenesis.class, RETRO_ART));
        cards.add(new SetCardInfo("Spreading Algae", 274, Rarity.UNCOMMON, mage.cards.s.SpreadingAlgae.class, RETRO_ART));
        cards.add(new SetCardInfo("Steam Blast", 219, Rarity.UNCOMMON, mage.cards.s.SteamBlast.class, RETRO_ART));
        cards.add(new SetCardInfo("Stern Proctor", 99, Rarity.UNCOMMON, mage.cards.s.SternProctor.class, RETRO_ART));
        cards.add(new SetCardInfo("Stroke of Genius", 100, Rarity.RARE, mage.cards.s.StrokeOfGenius.class, RETRO_ART));
        cards.add(new SetCardInfo("Sulfuric Vapors", 220, Rarity.RARE, mage.cards.s.SulfuricVapors.class, RETRO_ART));
        cards.add(new SetCardInfo("Sunder", 101, Rarity.RARE, mage.cards.s.Sunder.class, RETRO_ART));
        cards.add(new SetCardInfo("Swamp", 339, Rarity.LAND, mage.cards.basiclands.Swamp.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 340, Rarity.LAND, mage.cards.basiclands.Swamp.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 341, Rarity.LAND, mage.cards.basiclands.Swamp.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 342, Rarity.LAND, mage.cards.basiclands.Swamp.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Symbiosis", 275, Rarity.COMMON, mage.cards.s.Symbiosis.class, RETRO_ART));
        cards.add(new SetCardInfo("Tainted Aether", 161, Rarity.RARE, mage.cards.t.TaintedAether.class, RETRO_ART));
        cards.add(new SetCardInfo("Telepathy", 102, Rarity.UNCOMMON, mage.cards.t.Telepathy.class, RETRO_ART));
        cards.add(new SetCardInfo("Temporal Aperture", 310, Rarity.RARE, mage.cards.t.TemporalAperture.class, RETRO_ART));
        cards.add(new SetCardInfo("Thran Quarry", 329, Rarity.RARE, mage.cards.t.ThranQuarry.class, RETRO_ART));
        cards.add(new SetCardInfo("Thran Turbine", 311, Rarity.UNCOMMON, mage.cards.t.ThranTurbine.class, RETRO_ART));
        cards.add(new SetCardInfo("Thundering Giant", 221, Rarity.UNCOMMON, mage.cards.t.ThunderingGiant.class, RETRO_ART));
        cards.add(new SetCardInfo("Time Spiral", 103, Rarity.RARE, mage.cards.t.TimeSpiral.class, RETRO_ART));
        cards.add(new SetCardInfo("Titania's Boon", 276, Rarity.UNCOMMON, mage.cards.t.TitaniasBoon.class, RETRO_ART));
        cards.add(new SetCardInfo("Titania's Chosen", 277, Rarity.UNCOMMON, mage.cards.t.TitaniasChosen.class, RETRO_ART));
        cards.add(new SetCardInfo("Tolarian Academy", 330, Rarity.RARE, mage.cards.t.TolarianAcademy.class, RETRO_ART));
        cards.add(new SetCardInfo("Tolarian Winds", 104, Rarity.COMMON, mage.cards.t.TolarianWinds.class, RETRO_ART));
        cards.add(new SetCardInfo("Torch Song", 222, Rarity.UNCOMMON, mage.cards.t.TorchSong.class, RETRO_ART));
        cards.add(new SetCardInfo("Treefolk Seedlings", 278, Rarity.UNCOMMON, mage.cards.t.TreefolkSeedlings.class, RETRO_ART));
        cards.add(new SetCardInfo("Treetop Rangers", 279, Rarity.COMMON, mage.cards.t.TreetopRangers.class, RETRO_ART));
        cards.add(new SetCardInfo("Turnabout", 105, Rarity.UNCOMMON, mage.cards.t.Turnabout.class, RETRO_ART));
        cards.add(new SetCardInfo("Umbilicus", 312, Rarity.RARE, mage.cards.u.Umbilicus.class, RETRO_ART));
        cards.add(new SetCardInfo("Unnerve", 162, Rarity.COMMON, mage.cards.u.Unnerve.class, RETRO_ART));
        cards.add(new SetCardInfo("Unworthy Dead", 163, Rarity.COMMON, mage.cards.u.UnworthyDead.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Unworthy Dead", "163s", Rarity.COMMON, mage.cards.u.UnworthyDead.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Armor", 313, Rarity.UNCOMMON, mage.cards.u.UrzasArmor.class, RETRO_ART));
        cards.add(new SetCardInfo("Vampiric Embrace", 164, Rarity.UNCOMMON, mage.cards.v.VampiricEmbrace.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Vampiric Embrace", "164s", Rarity.UNCOMMON, mage.cards.v.VampiricEmbrace.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Vebulid", 165, Rarity.RARE, mage.cards.v.Vebulid.class, RETRO_ART));
        cards.add(new SetCardInfo("Veil of Birds", 106, Rarity.COMMON, mage.cards.v.VeilOfBirds.class, RETRO_ART));
        cards.add(new SetCardInfo("Veiled Apparition", 107, Rarity.UNCOMMON, mage.cards.v.VeiledApparition.class, RETRO_ART));
        cards.add(new SetCardInfo("Veiled Crocodile", 108, Rarity.RARE, mage.cards.v.VeiledCrocodile.class, RETRO_ART));
        cards.add(new SetCardInfo("Veiled Sentry", 109, Rarity.UNCOMMON, mage.cards.v.VeiledSentry.class, RETRO_ART));
        cards.add(new SetCardInfo("Veiled Serpent", 110, Rarity.COMMON, mage.cards.v.VeiledSerpent.class, RETRO_ART));
        cards.add(new SetCardInfo("Venomous Fangs", 280, Rarity.COMMON, mage.cards.v.VenomousFangs.class, RETRO_ART));
        cards.add(new SetCardInfo("Vernal Bloom", 281, Rarity.RARE, mage.cards.v.VernalBloom.class, RETRO_ART));
        cards.add(new SetCardInfo("Viashino Outrider", 223, Rarity.COMMON, mage.cards.v.ViashinoOutrider.class, RETRO_ART));
        cards.add(new SetCardInfo("Viashino Runner", 224, Rarity.COMMON, mage.cards.v.ViashinoRunner.class, RETRO_ART));
        cards.add(new SetCardInfo("Viashino Sandswimmer", 225, Rarity.RARE, mage.cards.v.ViashinoSandswimmer.class, RETRO_ART));
        cards.add(new SetCardInfo("Viashino Weaponsmith", 226, Rarity.COMMON, mage.cards.v.ViashinoWeaponsmith.class, RETRO_ART));
        cards.add(new SetCardInfo("Victimize", 166, Rarity.UNCOMMON, mage.cards.v.Victimize.class, RETRO_ART));
        cards.add(new SetCardInfo("Vile Requiem", 167, Rarity.UNCOMMON, mage.cards.v.VileRequiem.class, RETRO_ART));
        cards.add(new SetCardInfo("Voice of Grace", 54, Rarity.UNCOMMON, mage.cards.v.VoiceOfGrace.class, RETRO_ART));
        cards.add(new SetCardInfo("Voice of Law", 55, Rarity.UNCOMMON, mage.cards.v.VoiceOfLaw.class, RETRO_ART));
        cards.add(new SetCardInfo("Voltaic Key", 314, Rarity.UNCOMMON, mage.cards.v.VoltaicKey.class, RETRO_ART));
        cards.add(new SetCardInfo("Vug Lizard", 227, Rarity.UNCOMMON, mage.cards.v.VugLizard.class, RETRO_ART));
        cards.add(new SetCardInfo("Wall of Junk", 315, Rarity.UNCOMMON, mage.cards.w.WallOfJunk.class, RETRO_ART));
        cards.add(new SetCardInfo("War Dance", 282, Rarity.UNCOMMON, mage.cards.w.WarDance.class, RETRO_ART));
        cards.add(new SetCardInfo("Waylay", 56, Rarity.UNCOMMON, mage.cards.w.Waylay.class, RETRO_ART));
        cards.add(new SetCardInfo("Western Paladin", 168, Rarity.RARE, mage.cards.w.WesternPaladin.class, RETRO_ART));
        cards.add(new SetCardInfo("Whetstone", 316, Rarity.RARE, mage.cards.w.Whetstone.class, RETRO_ART));
        cards.add(new SetCardInfo("Whirlwind", 283, Rarity.RARE, mage.cards.w.Whirlwind.class, RETRO_ART));
        cards.add(new SetCardInfo("Wild Dogs", 284, Rarity.COMMON, mage.cards.w.WildDogs.class, RETRO_ART));
        cards.add(new SetCardInfo("Wildfire", 228, Rarity.RARE, mage.cards.w.Wildfire.class, RETRO_ART));
        cards.add(new SetCardInfo("Windfall", 111, Rarity.UNCOMMON, mage.cards.w.Windfall.class, RETRO_ART));
        cards.add(new SetCardInfo("Winding Wurm", 285, Rarity.COMMON, mage.cards.w.WindingWurm.class, RETRO_ART));
        cards.add(new SetCardInfo("Wirecat", 317, Rarity.UNCOMMON, mage.cards.w.Wirecat.class, RETRO_ART));
        cards.add(new SetCardInfo("Witch Engine", 169, Rarity.RARE, mage.cards.w.WitchEngine.class, RETRO_ART));
        cards.add(new SetCardInfo("Wizard Mentor", 112, Rarity.COMMON, mage.cards.w.WizardMentor.class, RETRO_ART));
        cards.add(new SetCardInfo("Worn Powerstone", 318, Rarity.UNCOMMON, mage.cards.w.WornPowerstone.class, RETRO_ART));
        cards.add(new SetCardInfo("Worship", 57, Rarity.RARE, mage.cards.w.Worship.class, RETRO_ART));
        cards.add(new SetCardInfo("Yawgmoth's Edict", 170, Rarity.UNCOMMON, mage.cards.y.YawgmothsEdict.class, RETRO_ART));
        cards.add(new SetCardInfo("Yawgmoth's Will", 171, Rarity.RARE, mage.cards.y.YawgmothsWill.class, RETRO_ART));
        cards.add(new SetCardInfo("Zephid", 113, Rarity.RARE, mage.cards.z.Zephid.class, RETRO_ART));
        cards.add(new SetCardInfo("Zephid's Embrace", 114, Rarity.UNCOMMON, mage.cards.z.ZephidsEmbrace.class, RETRO_ART));
    }

    @Override
    public BoosterCollator createCollator() {
        return new UrzasSagaCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/usg.html
// Using common collation
class UrzasSagaCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "139", "247", "94", "213", "5", "163", "256", "92", "226", "8", "139", "241", "82", "213", "46", "158", "267", "68", "191", "51", "129", "284", "64", "186", "10", "118", "256", "68", "201", "51", "124", "264", "67", "186", "46", "118", "267", "94", "226", "16", "124", "284", "92", "194", "8", "163", "241", "67", "201", "10", "129", "247", "82", "194", "5", "158", "264", "64", "191", "16");
    private final CardRun commonB = new CardRun(true, "323", "238", "86", "174", "12", "148", "279", "324", "195", "29", "147", "327", "80", "199", "4", "152", "285", "112", "328", "27", "116", "238", "324", "224", "29", "148", "275", "86", "195", "320", "147", "269", "78", "174", "30", "323", "285", "89", "224", "320", "135", "269", "112", "199", "12", "152", "327", "78", "223", "4", "135", "275", "80", "328", "30", "116", "279", "89", "223", "27");
    private final CardRun commonC = new CardRun(true, "156", "230", "110", "182", "42", "120", "235", "87", "193", "24", "119", "266", "106", "196", "21", "120", "253", "98", "182", "25", "137", "230", "59", "196", "11", "156", "235", "106", "217", "42", "121", "266", "98", "209", "11", "137", "248", "87", "217", "25", "121", "253", "110", "193", "21", "119", "248", "59", "209", "24");
    private final CardRun commonD = new CardRun(true, "132", "280", "93", "184", "41", "127", "252", "69", "177", "40", "134", "254", "93", "207", "36", "162", "271", "104", "198", "38", "122", "280", "65", "184", "37", "132", "252", "90", "208", "36", "122", "270", "65", "198", "37", "127", "271", "90", "177", "41", "134", "270", "104", "208", "40", "162", "254", "69", "207", "38");
    private final CardRun uncommon = new CardRun(false, "130", "272", "189", "107", "2", "293", "274", "227", "79", "50", "297", "114", "1", "166", "260", "215", "111", "292", "164", "245", "32", "150", "287", "180", "83", "44", "145", "239", "203", "88", "277", "200", "60", "18", "153", "255", "303", "70", "48", "318", "314", "33", "144", "259", "205", "95", "7", "155", "261", "219", "54", "319", "211", "75", "311", "47", "141", "222", "74", "278", "142", "99", "246", "43", "192", "170", "58", "313", "52", "216", "276", "55", "307", "149", "105", "237", "22", "181", "136", "109", "197", "157", "102", "232", "9", "187", "151", "294", "251", "56", "66", "240", "317", "221", "117", "315", "231", "20", "172", "160", "290", "190", "167", "84", "233", "35", "206", "125", "72", "282");
    private final CardRun rare = new CardRun(true, "321", "169", "281", "228", "305", "73", "13", "146", "273", "291", "183", "101", "14", "138", "316", "234", "176", "113", "6", "299", "133", "263", "178", "103", "301", "330", "57", "140", "262", "289", "175", "76", "28", "161", "288", "236", "188", "97", "53", "302", "154", "244", "173", "61", "298", "49", "131", "243", "212", "309", "329", "77", "3", "126", "283", "218", "85", "31", "159", "229", "286", "204", "100", "39", "165", "308", "250", "214", "108", "34", "300", "168", "257", "210", "63", "312", "26", "322", "171", "268", "296", "220", "71", "19", "143", "310", "242", "225", "96", "15", "306", "123", "258", "202", "62", "304", "45", "128", "265", "185", "325", "81", "17", "295", "115", "326", "249", "179", "91", "23");

    private final BoosterStructure AAABBB = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB, commonB
    );
    private final BoosterStructure BBBAAA = new BoosterStructure(
            commonB, commonB, commonB,
            commonA, commonA, commonA
    );
    private final BoosterStructure AAAABB = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB
    );
    private final BoosterStructure BBBBAA = new BoosterStructure(
            commonB, commonB, commonB, commonB,
            commonA, commonA
    );
    private final BoosterStructure CCCDD = new BoosterStructure(
            commonC, commonC, commonC,
            commonD, commonD
    );
    private final BoosterStructure DDDCC = new BoosterStructure(
            commonD, commonD, commonD,
            commonC, commonC
    );
    private final BoosterStructure CCDDD = new BoosterStructure(
            commonC, commonC,
            commonD, commonD, commonD
    );
    private final BoosterStructure DDCCC = new BoosterStructure(
            commonD, commonD,
            commonC, commonC, commonC
    );
    private final BoosterStructure U3 = new BoosterStructure(uncommon, uncommon, uncommon);
    private final BoosterStructure R1 = new BoosterStructure(rare);

    private final RarityConfiguration commonRunsAB = new RarityConfiguration(
        AAAABB,
        AAABBB, AAABBB, AAABBB, AAABBB, AAABBB, AAABBB, AAABBB, AAABBB, AAABBB,
        BBBAAA, BBBAAA, BBBAAA, BBBAAA, BBBAAA, BBBAAA, BBBAAA, BBBAAA, BBBAAA,
        BBBBAA
    );
    private final RarityConfiguration commonRunsCD = new RarityConfiguration(
        CCCDD, DDDCC, CCDDD, DDCCC
    );
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(U3);
    private final RarityConfiguration rareRuns = new RarityConfiguration(R1);

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        booster.addAll(commonRunsAB.getNext().makeRun());
        booster.addAll(commonRunsCD.getNext().makeRun());
        booster.addAll(uncommonRuns.getNext().makeRun());
        booster.addAll(rareRuns.getNext().makeRun());
        return booster;
    }
}
