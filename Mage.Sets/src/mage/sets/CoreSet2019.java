package mage.sets;

import mage.cards.ExpansionSet;
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
public final class CoreSet2019 extends ExpansionSet {

    private static final CoreSet2019 instance = new CoreSet2019();

    public static CoreSet2019 getInstance() {
        return instance;
    }

    private CoreSet2019() {
        super("Core Set 2019", "M19", ExpansionSet.buildDate(2018, 7, 13), SetType.CORE);
        this.hasBoosters = true;
        this.hasBasicLands = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.numBoosterDoubleFaced = -1;
        this.maxCardNumberInBooster = 280;

        // Core 2019 boosters have a 5/12 chance of basic land being replaced
        // with the common taplands, which DO NOT appear in the common slot.
        this.ratioBoosterSpecialLand = 12;
        this.ratioBoosterSpecialLandNumerator = 5;

        cards.add(new SetCardInfo("Abnormal Endurance", 85, Rarity.COMMON, mage.cards.a.AbnormalEndurance.class));
        cards.add(new SetCardInfo("Act of Treason", 127, Rarity.COMMON, mage.cards.a.ActOfTreason.class));
        cards.add(new SetCardInfo("Aegis of the Heavens", 1, Rarity.UNCOMMON, mage.cards.a.AegisOfTheHeavens.class));
        cards.add(new SetCardInfo("Aerial Engineer", 211, Rarity.UNCOMMON, mage.cards.a.AerialEngineer.class));
        cards.add(new SetCardInfo("Aether Tunnel", 43, Rarity.UNCOMMON, mage.cards.a.AetherTunnel.class));
        cards.add(new SetCardInfo("Aethershield Artificer", 2, Rarity.UNCOMMON, mage.cards.a.AethershieldArtificer.class));
        cards.add(new SetCardInfo("Aggressive Mammoth", 302, Rarity.RARE, mage.cards.a.AggressiveMammoth.class));
        cards.add(new SetCardInfo("Air Elemental", 308, Rarity.UNCOMMON, mage.cards.a.AirElemental.class));
        cards.add(new SetCardInfo("Ajani's Influence", 282, Rarity.RARE, mage.cards.a.AjanisInfluence.class));
        cards.add(new SetCardInfo("Ajani's Last Stand", 4, Rarity.RARE, mage.cards.a.AjanisLastStand.class));
        cards.add(new SetCardInfo("Ajani's Pridemate", 5, Rarity.UNCOMMON, mage.cards.a.AjanisPridemate.class));
        cards.add(new SetCardInfo("Ajani's Welcome", 6, Rarity.UNCOMMON, mage.cards.a.AjanisWelcome.class));
        cards.add(new SetCardInfo("Ajani, Adversary of Tyrants", 3, Rarity.MYTHIC, mage.cards.a.AjaniAdversaryOfTyrants.class));
        cards.add(new SetCardInfo("Ajani, Wise Counselor", 281, Rarity.MYTHIC, mage.cards.a.AjaniWiseCounselor.class));
        cards.add(new SetCardInfo("Alpine Moon", 128, Rarity.RARE, mage.cards.a.AlpineMoon.class));
        cards.add(new SetCardInfo("Amulet of Safekeeping", 226, Rarity.RARE, mage.cards.a.AmuletOfSafekeeping.class));
        cards.add(new SetCardInfo("Angel of the Dawn", 7, Rarity.COMMON, mage.cards.a.AngelOfTheDawn.class));
        cards.add(new SetCardInfo("Anticipate", 44, Rarity.COMMON, mage.cards.a.Anticipate.class));
        cards.add(new SetCardInfo("Apex of Power", 129, Rarity.MYTHIC, mage.cards.a.ApexOfPower.class));
        cards.add(new SetCardInfo("Arcades, the Strategist", 212, Rarity.MYTHIC, mage.cards.a.ArcadesTheStrategist.class));
        cards.add(new SetCardInfo("Arcane Encyclopedia", 227, Rarity.UNCOMMON, mage.cards.a.ArcaneEncyclopedia.class));
        cards.add(new SetCardInfo("Arisen Gorgon", 292, Rarity.UNCOMMON, mage.cards.a.ArisenGorgon.class));
        cards.add(new SetCardInfo("Aven Wind Mage", 45, Rarity.COMMON, mage.cards.a.AvenWindMage.class));
        cards.add(new SetCardInfo("Aviation Pioneer", 46, Rarity.COMMON, mage.cards.a.AviationPioneer.class));
        cards.add(new SetCardInfo("Banefire", 130, Rarity.RARE, mage.cards.b.Banefire.class));
        cards.add(new SetCardInfo("Befuddle", 309, Rarity.COMMON, mage.cards.b.Befuddle.class));
        cards.add(new SetCardInfo("Blanchwood Armor", 169, Rarity.UNCOMMON, mage.cards.b.BlanchwoodArmor.class));
        cards.add(new SetCardInfo("Blood Divination", 86, Rarity.UNCOMMON, mage.cards.b.BloodDivination.class));
        cards.add(new SetCardInfo("Boggart Brute", 131, Rarity.COMMON, mage.cards.b.BoggartBrute.class));
        cards.add(new SetCardInfo("Bogstomper", 87, Rarity.COMMON, mage.cards.b.Bogstomper.class));
        cards.add(new SetCardInfo("Bone Dragon", 88, Rarity.MYTHIC, mage.cards.b.BoneDragon.class));
        cards.add(new SetCardInfo("Bone to Ash", 47, Rarity.UNCOMMON, mage.cards.b.BoneToAsh.class));
        cards.add(new SetCardInfo("Brawl-Bash Ogre", 213, Rarity.UNCOMMON, mage.cards.b.BrawlBashOgre.class));
        cards.add(new SetCardInfo("Bristling Boar", 170, Rarity.COMMON, mage.cards.b.BristlingBoar.class));
        cards.add(new SetCardInfo("Cancel", 48, Rarity.COMMON, mage.cards.c.Cancel.class));
        cards.add(new SetCardInfo("Catalyst Elemental", 132, Rarity.COMMON, mage.cards.c.CatalystElemental.class));
        cards.add(new SetCardInfo("Cavalry Drillmaster", 8, Rarity.COMMON, mage.cards.c.CavalryDrillmaster.class));
        cards.add(new SetCardInfo("Centaur Courser", 171, Rarity.COMMON, mage.cards.c.CentaurCourser.class));
        cards.add(new SetCardInfo("Chaos Wand", 228, Rarity.RARE, mage.cards.c.ChaosWand.class));
        cards.add(new SetCardInfo("Child of Night", 89, Rarity.COMMON, mage.cards.c.ChildOfNight.class));
        cards.add(new SetCardInfo("Chromium, the Mutable", 214, Rarity.MYTHIC, mage.cards.c.ChromiumTheMutable.class));
        cards.add(new SetCardInfo("Cinder Barrens", 248, Rarity.COMMON, mage.cards.c.CinderBarrens.class));
        cards.add(new SetCardInfo("Cleansing Nova", 9, Rarity.RARE, mage.cards.c.CleansingNova.class));
        cards.add(new SetCardInfo("Colossal Dreadmaw", 172, Rarity.COMMON, mage.cards.c.ColossalDreadmaw.class));
        cards.add(new SetCardInfo("Colossal Majesty", 173, Rarity.UNCOMMON, mage.cards.c.ColossalMajesty.class));
        cards.add(new SetCardInfo("Court Cleric", 283, Rarity.UNCOMMON, mage.cards.c.CourtCleric.class));
        cards.add(new SetCardInfo("Crash Through", 133, Rarity.COMMON, mage.cards.c.CrashThrough.class));
        cards.add(new SetCardInfo("Crucible of Worlds", 229, Rarity.MYTHIC, mage.cards.c.CrucibleOfWorlds.class));
        cards.add(new SetCardInfo("Daggerback Basilisk", 174, Rarity.COMMON, mage.cards.d.DaggerbackBasilisk.class));
        cards.add(new SetCardInfo("Dark-Dweller Oracle", 134, Rarity.RARE, mage.cards.d.DarkDwellerOracle.class));
        cards.add(new SetCardInfo("Daybreak Chaplain", 10, Rarity.COMMON, mage.cards.d.DaybreakChaplain.class));
        cards.add(new SetCardInfo("Death Baron", 90, Rarity.RARE, mage.cards.d.DeathBaron.class));
        cards.add(new SetCardInfo("Declare Dominance", 175, Rarity.UNCOMMON, mage.cards.d.DeclareDominance.class));
        cards.add(new SetCardInfo("Demanding Dragon", 135, Rarity.RARE, mage.cards.d.DemandingDragon.class));
        cards.add(new SetCardInfo("Demon of Catastrophes", 91, Rarity.RARE, mage.cards.d.DemonOfCatastrophes.class));
        cards.add(new SetCardInfo("Departed Deckhand", 49, Rarity.UNCOMMON, mage.cards.d.DepartedDeckhand.class));
        cards.add(new SetCardInfo("Desecrated Tomb", 230, Rarity.RARE, mage.cards.d.DesecratedTomb.class));
        cards.add(new SetCardInfo("Detection Tower", 249, Rarity.RARE, mage.cards.d.DetectionTower.class));
        cards.add(new SetCardInfo("Diamond Mare", 231, Rarity.UNCOMMON, mage.cards.d.DiamondMare.class));
        cards.add(new SetCardInfo("Diregraf Ghoul", 92, Rarity.UNCOMMON, mage.cards.d.DiregrafGhoul.class));
        cards.add(new SetCardInfo("Dismissive Pyromancer", 136, Rarity.RARE, mage.cards.d.DismissivePyromancer.class));
        cards.add(new SetCardInfo("Disperse", 50, Rarity.COMMON, mage.cards.d.Disperse.class));
        cards.add(new SetCardInfo("Divination", 51, Rarity.COMMON, mage.cards.d.Divination.class));
        cards.add(new SetCardInfo("Djinn of Wishes", 52, Rarity.RARE, mage.cards.d.DjinnOfWishes.class));
        cards.add(new SetCardInfo("Doomed Dissenter", 93, Rarity.COMMON, mage.cards.d.DoomedDissenter.class));
        cards.add(new SetCardInfo("Doublecast", 137, Rarity.UNCOMMON, mage.cards.d.Doublecast.class));
        cards.add(new SetCardInfo("Draconic Disciple", 215, Rarity.UNCOMMON, mage.cards.d.DraconicDisciple.class));
        cards.add(new SetCardInfo("Dragon Egg", 138, Rarity.UNCOMMON, mage.cards.d.DragonEgg.class));
        cards.add(new SetCardInfo("Dragon's Hoard", 232, Rarity.RARE, mage.cards.d.DragonsHoard.class));
        cards.add(new SetCardInfo("Druid of Horns", 176, Rarity.UNCOMMON, mage.cards.d.DruidOfHorns.class));
        cards.add(new SetCardInfo("Druid of the Cowl", 177, Rarity.COMMON, mage.cards.d.DruidOfTheCowl.class));
        cards.add(new SetCardInfo("Dryad Greenseeker", 178, Rarity.UNCOMMON, mage.cards.d.DryadGreenseeker.class));
        cards.add(new SetCardInfo("Duress", 94, Rarity.COMMON, mage.cards.d.Duress.class));
        cards.add(new SetCardInfo("Dwarven Priest", 11, Rarity.COMMON, mage.cards.d.DwarvenPriest.class));
        cards.add(new SetCardInfo("Dwindle", 53, Rarity.COMMON, mage.cards.d.Dwindle.class));
        cards.add(new SetCardInfo("Electrify", 139, Rarity.COMMON, mage.cards.e.Electrify.class));
        cards.add(new SetCardInfo("Elvish Clancaller", 179, Rarity.RARE, mage.cards.e.ElvishClancaller.class));
        cards.add(new SetCardInfo("Elvish Rejuvenator", 180, Rarity.COMMON, mage.cards.e.ElvishRejuvenator.class));
        cards.add(new SetCardInfo("Enigma Drake", 216, Rarity.UNCOMMON, mage.cards.e.EnigmaDrake.class));
        cards.add(new SetCardInfo("Epicure of Blood", 95, Rarity.COMMON, mage.cards.e.EpicureOfBlood.class));
        cards.add(new SetCardInfo("Essence Scatter", 54, Rarity.COMMON, mage.cards.e.EssenceScatter.class));
        cards.add(new SetCardInfo("Exclusion Mage", 55, Rarity.UNCOMMON, mage.cards.e.ExclusionMage.class));
        cards.add(new SetCardInfo("Explosive Apparatus", 233, Rarity.COMMON, mage.cards.e.ExplosiveApparatus.class));
        cards.add(new SetCardInfo("Fell Specter", 96, Rarity.UNCOMMON, mage.cards.f.FellSpecter.class));
        cards.add(new SetCardInfo("Field Creeper", 234, Rarity.COMMON, mage.cards.f.FieldCreeper.class));
        cards.add(new SetCardInfo("Fiery Finish", 140, Rarity.UNCOMMON, mage.cards.f.FieryFinish.class));
        cards.add(new SetCardInfo("Fire Elemental", 141, Rarity.COMMON, mage.cards.f.FireElemental.class));
        cards.add(new SetCardInfo("Forest", 277, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 278, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 279, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 280, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forsaken Sanctuary", 250, Rarity.COMMON, mage.cards.f.ForsakenSanctuary.class));
        cards.add(new SetCardInfo("Foul Orchard", 251, Rarity.COMMON, mage.cards.f.FoulOrchard.class));
        cards.add(new SetCardInfo("Fountain of Renewal", 235, Rarity.UNCOMMON, mage.cards.f.FountainOfRenewal.class));
        cards.add(new SetCardInfo("Fraying Omnipotence", 97, Rarity.RARE, mage.cards.f.FrayingOmnipotence.class));
        cards.add(new SetCardInfo("Frilled Sea Serpent", 56, Rarity.COMMON, mage.cards.f.FrilledSeaSerpent.class));
        cards.add(new SetCardInfo("Gallant Cavalry", 12, Rarity.COMMON, mage.cards.g.GallantCavalry.class));
        cards.add(new SetCardInfo("Gargoyle Sentinel", 236, Rarity.UNCOMMON, mage.cards.g.GargoyleSentinel.class));
        cards.add(new SetCardInfo("Gearsmith Guardian", 237, Rarity.COMMON, mage.cards.g.GearsmithGuardian.class));
        cards.add(new SetCardInfo("Gearsmith Prodigy", 57, Rarity.COMMON, mage.cards.g.GearsmithProdigy.class));
        cards.add(new SetCardInfo("Ghastbark Twins", 181, Rarity.UNCOMMON, mage.cards.g.GhastbarkTwins.class));
        cards.add(new SetCardInfo("Ghirapur Guide", 182, Rarity.UNCOMMON, mage.cards.g.GhirapurGuide.class));
        cards.add(new SetCardInfo("Ghostform", 58, Rarity.COMMON, mage.cards.g.Ghostform.class));
        cards.add(new SetCardInfo("Giant Spider", 183, Rarity.COMMON, mage.cards.g.GiantSpider.class));
        cards.add(new SetCardInfo("Gift of Paradise", 184, Rarity.UNCOMMON, mage.cards.g.GiftOfParadise.class));
        cards.add(new SetCardInfo("Gigantosaurus", 185, Rarity.RARE, mage.cards.g.Gigantosaurus.class));
        cards.add(new SetCardInfo("Goblin Instigator", 142, Rarity.COMMON, mage.cards.g.GoblinInstigator.class));
        cards.add(new SetCardInfo("Goblin Motivator", 143, Rarity.COMMON, mage.cards.g.GoblinMotivator.class));
        cards.add(new SetCardInfo("Goblin Trashmaster", 144, Rarity.RARE, mage.cards.g.GoblinTrashmaster.class));
        cards.add(new SetCardInfo("Goreclaw, Terror of Qal Sisma", 186, Rarity.RARE, mage.cards.g.GoreclawTerrorOfQalSisma.class));
        cards.add(new SetCardInfo("Grasping Scoundrel", 312, Rarity.COMMON, mage.cards.g.GraspingScoundrel.class));
        cards.add(new SetCardInfo("Gravedigger", 98, Rarity.UNCOMMON, mage.cards.g.Gravedigger.class));
        cards.add(new SetCardInfo("Gravewaker", 293, Rarity.RARE, mage.cards.g.Gravewaker.class));
        cards.add(new SetCardInfo("Graveyard Marshal", 99, Rarity.RARE, mage.cards.g.GraveyardMarshal.class));
        cards.add(new SetCardInfo("Greenwood Sentinel", 187, Rarity.COMMON, mage.cards.g.GreenwoodSentinel.class));
        cards.add(new SetCardInfo("Guttersnipe", 145, Rarity.UNCOMMON, mage.cards.g.Guttersnipe.class));
        cards.add(new SetCardInfo("Havoc Devils", 146, Rarity.COMMON, mage.cards.h.HavocDevils.class));
        cards.add(new SetCardInfo("Herald of Faith", 13, Rarity.UNCOMMON, mage.cards.h.HeraldOfFaith.class));
        cards.add(new SetCardInfo("Heroic Reinforcements", 217, Rarity.UNCOMMON, mage.cards.h.HeroicReinforcements.class));
        cards.add(new SetCardInfo("Hieromancer's Cage", 14, Rarity.UNCOMMON, mage.cards.h.HieromancersCage.class));
        cards.add(new SetCardInfo("Highland Game", 188, Rarity.COMMON, mage.cards.h.HighlandGame.class));
        cards.add(new SetCardInfo("Highland Lake", 252, Rarity.COMMON, mage.cards.h.HighlandLake.class));
        cards.add(new SetCardInfo("Hired Blade", 100, Rarity.COMMON, mage.cards.h.HiredBlade.class));
        cards.add(new SetCardInfo("Horizon Scholar", 59, Rarity.UNCOMMON, mage.cards.h.HorizonScholar.class));
        cards.add(new SetCardInfo("Hostile Minotaur", 147, Rarity.COMMON, mage.cards.h.HostileMinotaur.class));
        cards.add(new SetCardInfo("Hungering Hydra", 189, Rarity.RARE, mage.cards.h.HungeringHydra.class));
        cards.add(new SetCardInfo("Infectious Horror", 101, Rarity.COMMON, mage.cards.i.InfectiousHorror.class));
        cards.add(new SetCardInfo("Infernal Reckoning", 102, Rarity.RARE, mage.cards.i.InfernalReckoning.class));
        cards.add(new SetCardInfo("Infernal Scarring", 103, Rarity.COMMON, mage.cards.i.InfernalScarring.class));
        cards.add(new SetCardInfo("Inferno Hellion", 148, Rarity.UNCOMMON, mage.cards.i.InfernoHellion.class));
        cards.add(new SetCardInfo("Inspired Charge", 15, Rarity.COMMON, mage.cards.i.InspiredCharge.class));
        cards.add(new SetCardInfo("Invoke the Divine", 16, Rarity.COMMON, mage.cards.i.InvokeTheDivine.class));
        cards.add(new SetCardInfo("Isareth the Awakener", 104, Rarity.RARE, mage.cards.i.IsarethTheAwakener.class));
        cards.add(new SetCardInfo("Island", 265, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 266, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 267, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 268, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Isolate", 17, Rarity.RARE, mage.cards.i.Isolate.class));
        cards.add(new SetCardInfo("Kargan Dragonrider", 297, Rarity.COMMON, mage.cards.k.KarganDragonrider.class));
        cards.add(new SetCardInfo("Knight of the Tusk", 18, Rarity.COMMON, mage.cards.k.KnightOfTheTusk.class));
        cards.add(new SetCardInfo("Knight's Pledge", 19, Rarity.COMMON, mage.cards.k.KnightsPledge.class));
        cards.add(new SetCardInfo("Knightly Valor", 20, Rarity.UNCOMMON, mage.cards.k.KnightlyValor.class));
        cards.add(new SetCardInfo("Lathliss, Dragon Queen", 149, Rarity.RARE, mage.cards.l.LathlissDragonQueen.class));
        cards.add(new SetCardInfo("Lava Axe", 150, Rarity.COMMON, mage.cards.l.LavaAxe.class));
        cards.add(new SetCardInfo("Lena, Selfless Champion", 21, Rarity.RARE, mage.cards.l.LenaSelflessChampion.class));
        cards.add(new SetCardInfo("Leonin Vanguard", 22, Rarity.UNCOMMON, mage.cards.l.LeoninVanguard.class));
        cards.add(new SetCardInfo("Leonin Warleader", 23, Rarity.RARE, mage.cards.l.LeoninWarleader.class));
        cards.add(new SetCardInfo("Lich's Caress", 105, Rarity.COMMON, mage.cards.l.LichsCaress.class));
        cards.add(new SetCardInfo("Lightning Mare", 151, Rarity.UNCOMMON, mage.cards.l.LightningMare.class));
        cards.add(new SetCardInfo("Lightning Strike", 152, Rarity.UNCOMMON, mage.cards.l.LightningStrike.class));
        cards.add(new SetCardInfo("Liliana's Contract", 107, Rarity.RARE, mage.cards.l.LilianasContract.class));
        cards.add(new SetCardInfo("Liliana's Spoils", 294, Rarity.RARE, mage.cards.l.LilianasSpoils.class));
        cards.add(new SetCardInfo("Liliana, Untouched by Death", 106, Rarity.MYTHIC, mage.cards.l.LilianaUntouchedByDeath.class));
        cards.add(new SetCardInfo("Liliana, the Necromancer", 291, Rarity.MYTHIC, mage.cards.l.LilianaTheNecromancer.class));
        cards.add(new SetCardInfo("Llanowar Elves", 314, Rarity.COMMON, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Loxodon Line Breaker", 24, Rarity.COMMON, mage.cards.l.LoxodonLineBreaker.class));
        cards.add(new SetCardInfo("Luminous Bonds", 25, Rarity.COMMON, mage.cards.l.LuminousBonds.class));
        cards.add(new SetCardInfo("Macabre Waltz", 108, Rarity.COMMON, mage.cards.m.MacabreWaltz.class));
        cards.add(new SetCardInfo("Magistrate's Scepter", 238, Rarity.RARE, mage.cards.m.MagistratesScepter.class));
        cards.add(new SetCardInfo("Make a Stand", 26, Rarity.UNCOMMON, mage.cards.m.MakeAStand.class));
        cards.add(new SetCardInfo("Manalith", 239, Rarity.COMMON, mage.cards.m.Manalith.class));
        cards.add(new SetCardInfo("Marauder's Axe", 240, Rarity.COMMON, mage.cards.m.MaraudersAxe.class));
        cards.add(new SetCardInfo("Meandering River", 253, Rarity.COMMON, mage.cards.m.MeanderingRiver.class));
        cards.add(new SetCardInfo("Mentor of the Meek", 27, Rarity.RARE, mage.cards.m.MentorOfTheMeek.class));
        cards.add(new SetCardInfo("Metamorphic Alteration", 60, Rarity.RARE, mage.cards.m.MetamorphicAlteration.class));
        cards.add(new SetCardInfo("Meteor Golem", 241, Rarity.UNCOMMON, mage.cards.m.MeteorGolem.class));
        cards.add(new SetCardInfo("Mighty Leap", 28, Rarity.COMMON, mage.cards.m.MightyLeap.class));
        cards.add(new SetCardInfo("Militia Bugler", 29, Rarity.UNCOMMON, mage.cards.m.MilitiaBugler.class));
        cards.add(new SetCardInfo("Millstone", 242, Rarity.UNCOMMON, mage.cards.m.Millstone.class));
        cards.add(new SetCardInfo("Mind Rot", 109, Rarity.COMMON, mage.cards.m.MindRot.class));
        cards.add(new SetCardInfo("Mirror Image", 61, Rarity.UNCOMMON, mage.cards.m.MirrorImage.class));
        cards.add(new SetCardInfo("Mist-Cloaked Herald", 310, Rarity.COMMON, mage.cards.m.MistCloakedHerald.class));
        cards.add(new SetCardInfo("Mistcaller", 62, Rarity.RARE, mage.cards.m.Mistcaller.class));
        cards.add(new SetCardInfo("Mountain", 273, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 274, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 275, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 276, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Murder", 110, Rarity.UNCOMMON, mage.cards.m.Murder.class));
        cards.add(new SetCardInfo("Mystic Archaeologist", 63, Rarity.RARE, mage.cards.m.MysticArchaeologist.class));
        cards.add(new SetCardInfo("Naturalize", 190, Rarity.COMMON, mage.cards.n.Naturalize.class));
        cards.add(new SetCardInfo("Nexus of Fate", 306, Rarity.MYTHIC, mage.cards.n.NexusOfFate.class));
        cards.add(new SetCardInfo("Nicol Bolas, the Arisen", 218, Rarity.MYTHIC, mage.cards.n.NicolBolasTheArisen.class));
        cards.add(new SetCardInfo("Nicol Bolas, the Ravager", 218, Rarity.MYTHIC, mage.cards.n.NicolBolasTheRavager.class));
        cards.add(new SetCardInfo("Nightmare's Thirst", 111, Rarity.UNCOMMON, mage.cards.n.NightmaresThirst.class));
        cards.add(new SetCardInfo("Novice Knight", 30, Rarity.UNCOMMON, mage.cards.n.NoviceKnight.class));
        cards.add(new SetCardInfo("Oakenform", 191, Rarity.COMMON, mage.cards.o.Oakenform.class));
        cards.add(new SetCardInfo("Omenspeaker", 64, Rarity.COMMON, mage.cards.o.Omenspeaker.class));
        cards.add(new SetCardInfo("Omniscience", 65, Rarity.MYTHIC, mage.cards.o.Omniscience.class));
        cards.add(new SetCardInfo("Onakke Ogre", 153, Rarity.COMMON, mage.cards.o.OnakkeOgre.class));
        cards.add(new SetCardInfo("One with the Machine", 66, Rarity.RARE, mage.cards.o.OneWithTheMachine.class));
        cards.add(new SetCardInfo("Open the Graves", 112, Rarity.RARE, mage.cards.o.OpenTheGraves.class));
        cards.add(new SetCardInfo("Oreskos Swiftclaw", 31, Rarity.COMMON, mage.cards.o.OreskosSwiftclaw.class));
        cards.add(new SetCardInfo("Palladia-Mors, the Ruiner", 219, Rarity.MYTHIC, mage.cards.p.PalladiaMorsTheRuiner.class));
        cards.add(new SetCardInfo("Patient Rebuilding", 67, Rarity.RARE, mage.cards.p.PatientRebuilding.class));
        cards.add(new SetCardInfo("Pegasus Courser", 32, Rarity.COMMON, mage.cards.p.PegasusCourser.class));
        cards.add(new SetCardInfo("Pelakka Wurm", 192, Rarity.RARE, mage.cards.p.PelakkaWurm.class));
        cards.add(new SetCardInfo("Pendulum of Patterns", 288, Rarity.COMMON, mage.cards.p.PendulumOfPatterns.class));
        cards.add(new SetCardInfo("Phylactery Lich", 113, Rarity.RARE, mage.cards.p.PhylacteryLich.class));
        cards.add(new SetCardInfo("Plague Mare", 114, Rarity.UNCOMMON, mage.cards.p.PlagueMare.class));
        cards.add(new SetCardInfo("Plains", 261, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 262, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 263, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 264, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plummet", 193, Rarity.COMMON, mage.cards.p.Plummet.class));
        cards.add(new SetCardInfo("Poison-Tip Archer", 220, Rarity.UNCOMMON, mage.cards.p.PoisonTipArcher.class));
        cards.add(new SetCardInfo("Prodigious Growth", 194, Rarity.RARE, mage.cards.p.ProdigiousGrowth.class));
        cards.add(new SetCardInfo("Psychic Corrosion", 68, Rarity.UNCOMMON, mage.cards.p.PsychicCorrosion.class));
        cards.add(new SetCardInfo("Psychic Symbiont", 221, Rarity.UNCOMMON, mage.cards.p.PsychicSymbiont.class));
        cards.add(new SetCardInfo("Rabid Bite", 195, Rarity.COMMON, mage.cards.r.RabidBite.class));
        cards.add(new SetCardInfo("Radiating Lightning", 313, Rarity.COMMON, mage.cards.r.RadiatingLightning.class));
        cards.add(new SetCardInfo("Ravenous Harpy", 115, Rarity.UNCOMMON, mage.cards.r.RavenousHarpy.class));
        cards.add(new SetCardInfo("Reassembling Skeleton", 116, Rarity.UNCOMMON, mage.cards.r.ReassemblingSkeleton.class));
        cards.add(new SetCardInfo("Reclamation Sage", 196, Rarity.UNCOMMON, mage.cards.r.ReclamationSage.class));
        cards.add(new SetCardInfo("Recollect", 197, Rarity.UNCOMMON, mage.cards.r.Recollect.class));
        cards.add(new SetCardInfo("Regal Bloodlord", 222, Rarity.UNCOMMON, mage.cards.r.RegalBloodlord.class));
        cards.add(new SetCardInfo("Reliquary Tower", 254, Rarity.UNCOMMON, mage.cards.r.ReliquaryTower.class));
        cards.add(new SetCardInfo("Remorseful Cleric", 33, Rarity.RARE, mage.cards.r.RemorsefulCleric.class));
        cards.add(new SetCardInfo("Resplendent Angel", 34, Rarity.MYTHIC, mage.cards.r.ResplendentAngel.class));
        cards.add(new SetCardInfo("Revitalize", 35, Rarity.COMMON, mage.cards.r.Revitalize.class));
        cards.add(new SetCardInfo("Rhox Oracle", 198, Rarity.COMMON, mage.cards.r.RhoxOracle.class));
        cards.add(new SetCardInfo("Riddlemaster Sphinx", 287, Rarity.RARE, mage.cards.r.RiddlemasterSphinx.class));
        cards.add(new SetCardInfo("Rise from the Grave", 117, Rarity.UNCOMMON, mage.cards.r.RiseFromTheGrave.class));
        cards.add(new SetCardInfo("Rogue's Gloves", 243, Rarity.UNCOMMON, mage.cards.r.RoguesGloves.class));
        cards.add(new SetCardInfo("Root Snare", 199, Rarity.COMMON, mage.cards.r.RootSnare.class));
        cards.add(new SetCardInfo("Runic Armasaur", 200, Rarity.RARE, mage.cards.r.RunicArmasaur.class));
        cards.add(new SetCardInfo("Rupture Spire", 255, Rarity.UNCOMMON, mage.cards.r.RuptureSpire.class));
        cards.add(new SetCardInfo("Rustwing Falcon", 36, Rarity.COMMON, mage.cards.r.RustwingFalcon.class));
        cards.add(new SetCardInfo("Sai, Master Thopterist", 69, Rarity.RARE, mage.cards.s.SaiMasterThopterist.class));
        cards.add(new SetCardInfo("Salvager of Secrets", 70, Rarity.COMMON, mage.cards.s.SalvagerOfSecrets.class));
        cards.add(new SetCardInfo("Sarkhan's Dragonfire", 298, Rarity.RARE, mage.cards.s.SarkhansDragonfire.class));
        cards.add(new SetCardInfo("Sarkhan's Unsealing", 155, Rarity.RARE, mage.cards.s.SarkhansUnsealing.class));
        cards.add(new SetCardInfo("Sarkhan's Whelp", 299, Rarity.UNCOMMON, mage.cards.s.SarkhansWhelp.class));
        cards.add(new SetCardInfo("Sarkhan, Dragonsoul", 296, Rarity.MYTHIC, mage.cards.s.SarkhanDragonsoul.class));
        cards.add(new SetCardInfo("Sarkhan, Fireblood", 154, Rarity.MYTHIC, mage.cards.s.SarkhanFireblood.class));
        cards.add(new SetCardInfo("Satyr Enchanter", 223, Rarity.UNCOMMON, mage.cards.s.SatyrEnchanter.class));
        cards.add(new SetCardInfo("Scapeshift", 201, Rarity.MYTHIC, mage.cards.s.Scapeshift.class));
        cards.add(new SetCardInfo("Scholar of Stars", 71, Rarity.COMMON, mage.cards.s.ScholarOfStars.class));
        cards.add(new SetCardInfo("Serra's Guardian", 284, Rarity.RARE, mage.cards.s.SerrasGuardian.class));
        cards.add(new SetCardInfo("Shield Mare", 37, Rarity.UNCOMMON, mage.cards.s.ShieldMare.class));
        cards.add(new SetCardInfo("Shivan Dragon", 300, Rarity.RARE, mage.cards.s.ShivanDragon.class));
        cards.add(new SetCardInfo("Shock", 156, Rarity.COMMON, mage.cards.s.Shock.class));
        cards.add(new SetCardInfo("Siegebreaker Giant", 157, Rarity.UNCOMMON, mage.cards.s.SiegebreakerGiant.class));
        cards.add(new SetCardInfo("Sift", 72, Rarity.UNCOMMON, mage.cards.s.Sift.class));
        cards.add(new SetCardInfo("Sigiled Sword of Valeron", 244, Rarity.RARE, mage.cards.s.SigiledSwordOfValeron.class));
        cards.add(new SetCardInfo("Silverbeak Griffin", 285, Rarity.COMMON, mage.cards.s.SilverbeakGriffin.class));
        cards.add(new SetCardInfo("Skalla Wolf", 303, Rarity.RARE, mage.cards.s.SkallaWolf.class));
        cards.add(new SetCardInfo("Skeleton Archer", 118, Rarity.COMMON, mage.cards.s.SkeletonArcher.class));
        cards.add(new SetCardInfo("Skilled Animator", 73, Rarity.UNCOMMON, mage.cards.s.SkilledAnimator.class));
        cards.add(new SetCardInfo("Skymarch Bloodletter", 119, Rarity.COMMON, mage.cards.s.SkymarchBloodletter.class));
        cards.add(new SetCardInfo("Skyrider Patrol", 224, Rarity.UNCOMMON, mage.cards.s.SkyriderPatrol.class));
        cards.add(new SetCardInfo("Skyscanner", 245, Rarity.COMMON, mage.cards.s.Skyscanner.class));
        cards.add(new SetCardInfo("Sleep", 74, Rarity.UNCOMMON, mage.cards.s.Sleep.class));
        cards.add(new SetCardInfo("Smelt", 158, Rarity.COMMON, mage.cards.s.Smelt.class));
        cards.add(new SetCardInfo("Snapping Drake", 75, Rarity.COMMON, mage.cards.s.SnappingDrake.class));
        cards.add(new SetCardInfo("Sovereign's Bite", 120, Rarity.COMMON, mage.cards.s.SovereignsBite.class));
        cards.add(new SetCardInfo("Sparktongue Dragon", 159, Rarity.COMMON, mage.cards.s.SparktongueDragon.class));
        cards.add(new SetCardInfo("Spit Flame", 160, Rarity.RARE, mage.cards.s.SpitFlame.class));
        cards.add(new SetCardInfo("Star-Crowned Stag", 38, Rarity.COMMON, mage.cards.s.StarCrownedStag.class));
        cards.add(new SetCardInfo("Stitcher's Supplier", 121, Rarity.UNCOMMON, mage.cards.s.StitchersSupplier.class));
        cards.add(new SetCardInfo("Stone Quarry", 256, Rarity.COMMON, mage.cards.s.StoneQuarry.class));
        cards.add(new SetCardInfo("Strangling Spores", 122, Rarity.COMMON, mage.cards.s.StranglingSpores.class));
        cards.add(new SetCardInfo("Submerged Boneyard", 257, Rarity.COMMON, mage.cards.s.SubmergedBoneyard.class));
        cards.add(new SetCardInfo("Sun Sentinel", 307, Rarity.COMMON, mage.cards.s.SunSentinel.class));
        cards.add(new SetCardInfo("Suncleanser", 39, Rarity.RARE, mage.cards.s.Suncleanser.class));
        cards.add(new SetCardInfo("Supreme Phantom", 76, Rarity.RARE, mage.cards.s.SupremePhantom.class));
        cards.add(new SetCardInfo("Sure Strike", 161, Rarity.COMMON, mage.cards.s.SureStrike.class));
        cards.add(new SetCardInfo("Surge Mare", 77, Rarity.UNCOMMON, mage.cards.s.SurgeMare.class));
        cards.add(new SetCardInfo("Suspicious Bookcase", 246, Rarity.UNCOMMON, mage.cards.s.SuspiciousBookcase.class));
        cards.add(new SetCardInfo("Swamp", 269, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 270, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 271, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 272, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Switcheroo", 78, Rarity.UNCOMMON, mage.cards.s.Switcheroo.class));
        cards.add(new SetCardInfo("Take Vengeance", 40, Rarity.COMMON, mage.cards.t.TakeVengeance.class));
        cards.add(new SetCardInfo("Talons of Wildwood", 202, Rarity.COMMON, mage.cards.t.TalonsOfWildwood.class));
        cards.add(new SetCardInfo("Tattered Mummy", 295, Rarity.COMMON, mage.cards.t.TatteredMummy.class));
        cards.add(new SetCardInfo("Tectonic Rift", 162, Rarity.UNCOMMON, mage.cards.t.TectonicRift.class));
        cards.add(new SetCardInfo("Tezzeret's Gatebreaker", 289, Rarity.RARE, mage.cards.t.TezzeretsGatebreaker.class));
        cards.add(new SetCardInfo("Tezzeret's Strider", 290, Rarity.UNCOMMON, mage.cards.t.TezzeretsStrider.class));
        cards.add(new SetCardInfo("Tezzeret, Artifice Master", 79, Rarity.MYTHIC, mage.cards.t.TezzeretArtificeMaster.class));
        cards.add(new SetCardInfo("Tezzeret, Cruel Machinist", 286, Rarity.MYTHIC, mage.cards.t.TezzeretCruelMachinist.class));
        cards.add(new SetCardInfo("Thorn Lieutenant", 203, Rarity.RARE, mage.cards.t.ThornLieutenant.class));
        cards.add(new SetCardInfo("Thornhide Wolves", 204, Rarity.COMMON, mage.cards.t.ThornhideWolves.class));
        cards.add(new SetCardInfo("Thud", 163, Rarity.UNCOMMON, mage.cards.t.Thud.class));
        cards.add(new SetCardInfo("Timber Gorge", 258, Rarity.COMMON, mage.cards.t.TimberGorge.class));
        cards.add(new SetCardInfo("Titanic Growth", 205, Rarity.COMMON, mage.cards.t.TitanicGrowth.class));
        cards.add(new SetCardInfo("Tolarian Scholar", 80, Rarity.COMMON, mage.cards.t.TolarianScholar.class));
        cards.add(new SetCardInfo("Tormenting Voice", 164, Rarity.COMMON, mage.cards.t.TormentingVoice.class));
        cards.add(new SetCardInfo("Totally Lost", 81, Rarity.COMMON, mage.cards.t.TotallyLost.class));
        cards.add(new SetCardInfo("Tranquil Expanse", 259, Rarity.COMMON, mage.cards.t.TranquilExpanse.class));
        cards.add(new SetCardInfo("Transmogrifying Wand", 247, Rarity.RARE, mage.cards.t.TransmogrifyingWand.class));
        cards.add(new SetCardInfo("Trumpet Blast", 165, Rarity.COMMON, mage.cards.t.TrumpetBlast.class));
        cards.add(new SetCardInfo("Trusty Packbeast", 41, Rarity.COMMON, mage.cards.t.TrustyPackbeast.class));
        cards.add(new SetCardInfo("Two-Headed Zombie", 123, Rarity.COMMON, mage.cards.t.TwoHeadedZombie.class));
        cards.add(new SetCardInfo("Uncomfortable Chill", 82, Rarity.COMMON, mage.cards.u.UncomfortableChill.class));
        cards.add(new SetCardInfo("Ursine Champion", 304, Rarity.COMMON, mage.cards.u.UrsineChampion.class));
        cards.add(new SetCardInfo("Vaevictis Asmadi, the Dire", 225, Rarity.MYTHIC, mage.cards.v.VaevictisAsmadiTheDire.class));
        cards.add(new SetCardInfo("Valiant Knight", 42, Rarity.RARE, mage.cards.v.ValiantKnight.class));
        cards.add(new SetCardInfo("Vampire Neonate", 124, Rarity.COMMON, mage.cards.v.VampireNeonate.class));
        cards.add(new SetCardInfo("Vampire Sovereign", 125, Rarity.UNCOMMON, mage.cards.v.VampireSovereign.class));
        cards.add(new SetCardInfo("Viashino Pyromancer", 166, Rarity.COMMON, mage.cards.v.ViashinoPyromancer.class));
        cards.add(new SetCardInfo("Vigilant Baloth", 206, Rarity.UNCOMMON, mage.cards.v.VigilantBaloth.class));
        cards.add(new SetCardInfo("Vine Mare", 207, Rarity.UNCOMMON, mage.cards.v.VineMare.class));
        cards.add(new SetCardInfo("Vivien Reid", 208, Rarity.MYTHIC, mage.cards.v.VivienReid.class));
        cards.add(new SetCardInfo("Vivien of the Arkbow", 301, Rarity.MYTHIC, mage.cards.v.VivienOfTheArkbow.class));
        cards.add(new SetCardInfo("Vivien's Invocation", 209, Rarity.RARE, mage.cards.v.ViviensInvocation.class));
        cards.add(new SetCardInfo("Vivien's Jaguar", 305, Rarity.UNCOMMON, mage.cards.v.ViviensJaguar.class));
        cards.add(new SetCardInfo("Volcanic Dragon", 167, Rarity.UNCOMMON, mage.cards.v.VolcanicDragon.class));
        cards.add(new SetCardInfo("Volley Veteran", 168, Rarity.UNCOMMON, mage.cards.v.VolleyVeteran.class));
        cards.add(new SetCardInfo("Walking Corpse", 126, Rarity.COMMON, mage.cards.w.WalkingCorpse.class));
        cards.add(new SetCardInfo("Wall of Mist", 83, Rarity.COMMON, mage.cards.w.WallOfMist.class));
        cards.add(new SetCardInfo("Wall of Vines", 210, Rarity.COMMON, mage.cards.w.WallOfVines.class));
        cards.add(new SetCardInfo("Waterknot", 311, Rarity.COMMON, mage.cards.w.Waterknot.class));
        cards.add(new SetCardInfo("Windreader Sphinx", 84, Rarity.RARE, mage.cards.w.WindreaderSphinx.class));
        cards.add(new SetCardInfo("Woodland Stream", 260, Rarity.COMMON, mage.cards.w.WoodlandStream.class));
    }

    @Override
    public BoosterCollator createCollator() {
        return new CoreSet2019Collator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/m19.html
// Using USA collation for common/uncommon, rare collation inferred from other sets
class CoreSet2019Collator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "161", "28", "64", "142", "24", "50", "165", "35", "56", "150", "18", "57", "132", "36", "80", "127", "19", "82", "143", "10", "71", "164", "31", "51", "142", "11", "53", "141", "24", "56", "165", "41", "64", "150", "15", "44", "147", "18", "80", "153", "28", "48", "161", "35", "82", "132", "36", "50", "143", "41", "57", "141", "31", "51", "127", "10", "71", "164", "11", "48", "147", "19", "44", "153", "15", "53");
    private final CardRun commonB = new CardRun(true, "170", "100", "188", "87", "193", "123", "202", "108", "180", "103", "183", "93", "174", "94", "191", "95", "205", "101", "188", "123", "198", "108", "180", "89", "170", "87", "187", "100", "202", "94", "193", "109", "174", "103", "191", "93", "198", "101", "183", "95", "170", "89", "205", "100", "188", "87", "187", "123", "180", "108", "202", "103", "193", "109", "174", "94", "183", "95", "205", "101", "191", "93", "198", "89", "187", "109");
    private final CardRun commonC1 = new CardRun(true, "234", "46", "166", "195", "120", "146", "204", "12", "118", "83", "40", "199", "139", "126", "75", "237", "177", "158", "105", "12", "245", "70", "120", "131", "83", "195", "239", "85", "58", "7", "46", "237", "146", "16", "118", "204", "32", "158", "177", "245", "75", "105", "239", "40", "58", "139", "126", "7", "166", "70", "199", "85", "131", "16", "32");
    private final CardRun commonC2 = new CardRun(true, "234", "172", "54", "190", "122", "240", "156", "81", "171", "124", "45", "233", "25", "119", "210", "159", "124", "38", "54", "122", "133", "172", "240", "8", "156", "190", "25", "124", "45", "38", "171", "133", "119", "233", "8", "159", "210", "25", "240", "54", "190", "122", "81", "172", "233", "156", "38", "81", "171", "133", "45", "8", "159", "210", "119");
    private final CardRun uncommonA = new CardRun(true, "47", "6", "169", "246", "168", "117", "68", "243", "181", "115", "231", "2", "163", "73", "227", "207", "86", "254", "137", "37", "242", "184", "255", "121", "148", "176", "26", "111", "138", "235", "196", "43", "117", "140", "6", "197", "1", "246", "47", "162", "169", "30", "168", "181", "243", "254", "2", "182", "151", "86", "68", "22", "184", "137", "73", "197", "163", "231", "138", "26", "176", "115", "148", "242", "37", "111", "168", "6", "196", "1", "227", "255", "121", "43", "207", "140", "30", "117", "254", "182", "137", "235", "26", "162", "47", "169", "151", "86", "176", "68", "243", "138", "181", "2", "73", "148", "196", "246", "37", "115", "163", "231", "207", "151", "22", "121", "184", "227", "255", "1", "111", "162", "197", "43", "242", "30", "182", "140", "235", "22");
    private final CardRun uncommonB = new CardRun(true, "222", "49", "145", "216", "125", "178", "78", "215", "114", "241", "221", "157", "77", "224", "206", "98", "55", "213", "14", "92", "211", "110", "236", "223", "72", "116", "74", "167", "217", "96", "61", "220", "175", "114", "59", "222", "145", "125", "173", "215", "152", "78", "5", "98", "216", "178", "77", "92", "221", "157", "241", "224", "96", "49", "13", "223", "20", "167", "213", "110", "55", "29", "14", "217", "236", "152", "78", "220", "114", "5", "74", "211", "206", "59", "215", "173", "125", "216", "49", "20", "222", "13", "116", "72", "175", "241", "92", "221", "29", "61", "224", "145", "98", "178", "213", "77", "217", "14", "55", "157", "220", "5", "74", "96", "206", "72", "20", "236", "110", "173", "223", "59", "167", "175", "116", "211", "13", "61", "29", "152");
    private final CardRun rare = new CardRun(false, "4", "9", "17", "21", "23", "27", "33", "39", "42", "52", "60", "62", "63", "66", "67", "69", "76", "84", "90", "91", "97", "99", "102", "104", "107", "112", "113", "128", "130", "134", "135", "136", "144", "149", "155", "160", "179", "185", "186", "189", "192", "194", "200", "203", "209", "226", "228", "230", "232", "238", "244", "247", "249", "4", "9", "17", "21", "23", "27", "33", "39", "42", "52", "60", "62", "63", "66", "67", "69", "76", "84", "90", "91", "97", "99", "102", "104", "107", "112", "113", "128", "130", "134", "135", "136", "144", "149", "155", "160", "179", "185", "186", "189", "192", "194", "200", "203", "209", "226", "228", "230", "232", "238", "244", "247", "249", "3", "34", "65", "79", "88", "106", "129", "154", "201", "208", "212", "214", "218", "219", "225", "229");
    private final CardRun landBasic = new CardRun(false, "261", "262", "263", "264", "265", "266", "267", "268", "269", "270", "271", "272", "273", "274", "275", "276", "277", "278", "279", "280");
    private final CardRun landNonBasic = new CardRun(false, "248", "250", "251", "252", "253", "256", "257", "258", "259", "260");

    private final BoosterStructure AABBC1C1C1C1C1C1 = new BoosterStructure(
            commonA, commonA,
            commonB, commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAABBC1C1C1C1C1 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAAABBC2C2C2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB,
            commonC2, commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAAABBBC2C2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB, commonB,
            commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAAABBBBC2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB, commonB, commonB,
            commonC2, commonC2
    );
    private final BoosterStructure AAB = new BoosterStructure(uncommonA, uncommonA, uncommonB);
    private final BoosterStructure ABB = new BoosterStructure(uncommonA, uncommonB, uncommonB);
    private final BoosterStructure R1 = new BoosterStructure(rare);
    private final BoosterStructure LB = new BoosterStructure(landBasic);
    private final BoosterStructure LN = new BoosterStructure(landNonBasic);

    // In order for equal numbers of each common to exist, the average booster must contain:
    // 3.27 A commons (36 / 11)
    // 2.18 B commons (24 / 11)
    // 2.73 C1 commons (30 / 11, or 60 / 11 in each C1 booster)
    // 1.82 C2 commons (20 / 11, or 40 / 11 in each C2 booster)
    // These numbers are the same for all sets with 101 commons in A/B/C1/C2 print runs
    // and with 10 common slots per booster
    private final RarityConfiguration commonRuns = new RarityConfiguration(
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,

            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBBC2C2C2,
            AAAABBBC2C2C2,
            AAAABBBBC2C2
    );
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(AAB, ABB);
    private final RarityConfiguration rareRuns = new RarityConfiguration(R1);
    private final RarityConfiguration landRuns = new RarityConfiguration(
        LB, LB, LB, LB, LB, LB, LB,
        LN, LN, LN, LN, LN
    );

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        booster.addAll(commonRuns.getNext().makeRun());
        booster.addAll(uncommonRuns.getNext().makeRun());
        booster.addAll(rareRuns.getNext().makeRun());
        booster.addAll(landRuns.getNext().makeRun());
        return booster;
    }
}
