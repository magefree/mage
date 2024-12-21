package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.collation.BoosterCollator;
import mage.collation.BoosterStructure;
import mage.collation.CardRun;
import mage.collation.RarityConfiguration;
import mage.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TheElk801
 */
public final class Foundations extends ExpansionSet {

    private static final Foundations instance = new Foundations();

    public static Foundations getInstance() {
        return instance;
    }

    private Foundations() {
        super("Foundations", "FDN", ExpansionSet.buildDate(2024, 11, 15), SetType.EXPANSION);
        this.blockName = "Foundations"; // for sorting in GUI
        this.hasBasicLands = true;
        this.hasBoosters = true;

        cards.add(new SetCardInfo("Abrade", 188, Rarity.UNCOMMON, mage.cards.a.Abrade.class));
        cards.add(new SetCardInfo("Abyssal Harvester", 54, Rarity.RARE, mage.cards.a.AbyssalHarvester.class));
        cards.add(new SetCardInfo("Adamant Will", 488, Rarity.COMMON, mage.cards.a.AdamantWill.class));
        cards.add(new SetCardInfo("Adaptive Automaton", 723, Rarity.RARE, mage.cards.a.AdaptiveAutomaton.class));
        cards.add(new SetCardInfo("Adventuring Gear", 249, Rarity.UNCOMMON, mage.cards.a.AdventuringGear.class));
        cards.add(new SetCardInfo("Aegis Turtle", 150, Rarity.COMMON, mage.cards.a.AegisTurtle.class));
        cards.add(new SetCardInfo("Aetherize", 151, Rarity.UNCOMMON, mage.cards.a.Aetherize.class));
        cards.add(new SetCardInfo("Affectionate Indrik", 211, Rarity.UNCOMMON, mage.cards.a.AffectionateIndrik.class));
        cards.add(new SetCardInfo("Aggressive Mammoth", 551, Rarity.RARE, mage.cards.a.AggressiveMammoth.class));
        cards.add(new SetCardInfo("Ajani's Pridemate", 135, Rarity.UNCOMMON, mage.cards.a.AjanisPridemate.class));
        cards.add(new SetCardInfo("Ajani, Caller of the Pride", 134, Rarity.MYTHIC, mage.cards.a.AjaniCallerOfThePride.class));
        cards.add(new SetCardInfo("Alesha, Who Laughs at Fate", 115, Rarity.RARE, mage.cards.a.AleshaWhoLaughsAtFate.class));
        cards.add(new SetCardInfo("Ambush Wolf", 98, Rarity.COMMON, mage.cards.a.AmbushWolf.class));
        cards.add(new SetCardInfo("An Offer You Can't Refuse", 160, Rarity.UNCOMMON, mage.cards.a.AnOfferYouCantRefuse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("An Offer You Can't Refuse", 311, Rarity.UNCOMMON, mage.cards.a.AnOfferYouCantRefuse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ancestor Dragon", 489, Rarity.RARE, mage.cards.a.AncestorDragon.class));
        cards.add(new SetCardInfo("Angel of Finality", 136, Rarity.UNCOMMON, mage.cards.a.AngelOfFinality.class));
        cards.add(new SetCardInfo("Angel of Vitality", 706, Rarity.UNCOMMON, mage.cards.a.AngelOfVitality.class));
        cards.add(new SetCardInfo("Angelic Destiny", 565, Rarity.MYTHIC, mage.cards.a.AngelicDestiny.class));
        cards.add(new SetCardInfo("Angelic Edict", 490, Rarity.COMMON, mage.cards.a.AngelicEdict.class));
        cards.add(new SetCardInfo("Anthem of Champions", 116, Rarity.RARE, mage.cards.a.AnthemOfChampions.class));
        cards.add(new SetCardInfo("Apothecary Stomper", 99, Rarity.COMMON, mage.cards.a.ApothecaryStomper.class));
        cards.add(new SetCardInfo("Arahbo, the First Fang", 2, Rarity.RARE, mage.cards.a.ArahboTheFirstFang.class));
        cards.add(new SetCardInfo("Arbiter of Woe", 55, Rarity.UNCOMMON, mage.cards.a.ArbiterOfWoe.class));
        cards.add(new SetCardInfo("Arcane Epiphany", 29, Rarity.UNCOMMON, mage.cards.a.ArcaneEpiphany.class));
        cards.add(new SetCardInfo("Arcanis the Omnipotent", 585, Rarity.RARE, mage.cards.a.ArcanisTheOmnipotent.class));
        cards.add(new SetCardInfo("Archmage of Runes", 30, Rarity.RARE, mage.cards.a.ArchmageOfRunes.class));
        cards.add(new SetCardInfo("Archway Angel", 566, Rarity.UNCOMMON, mage.cards.a.ArchwayAngel.class));
        cards.add(new SetCardInfo("Armasaur Guide", 3, Rarity.COMMON, mage.cards.a.ArmasaurGuide.class));
        cards.add(new SetCardInfo("Ashroot Animist", 117, Rarity.RARE, mage.cards.a.AshrootAnimist.class));
        cards.add(new SetCardInfo("Aurelia, the Warleader", 651, Rarity.MYTHIC, mage.cards.a.AureliaTheWarleader.class));
        cards.add(new SetCardInfo("Authority of the Consuls", 137, Rarity.RARE, mage.cards.a.AuthorityOfTheConsuls.class));
        cards.add(new SetCardInfo("Axgard Cavalry", 189, Rarity.COMMON, mage.cards.a.AxgardCavalry.class));
        cards.add(new SetCardInfo("Ayli, Eternal Pilgrim", 652, Rarity.RARE, mage.cards.a.AyliEternalPilgrim.class));
        cards.add(new SetCardInfo("Azorius Guildgate", 683, Rarity.COMMON, mage.cards.a.AzoriusGuildgate.class));
        cards.add(new SetCardInfo("Bake into a Pie", 169, Rarity.COMMON, mage.cards.b.BakeIntoAPie.class));
        cards.add(new SetCardInfo("Ball Lightning", 618, Rarity.RARE, mage.cards.b.BallLightning.class));
        cards.add(new SetCardInfo("Ballyrush Banneret", 567, Rarity.COMMON, mage.cards.b.BallyrushBanneret.class));
        cards.add(new SetCardInfo("Balmor, Battlemage Captain", 237, Rarity.UNCOMMON, mage.cards.b.BalmorBattlemageCaptain.class));
        cards.add(new SetCardInfo("Banishing Light", 138, Rarity.COMMON, mage.cards.b.BanishingLight.class));
        cards.add(new SetCardInfo("Banner of Kinship", 127, Rarity.RARE, mage.cards.b.BannerOfKinship.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Banner of Kinship", 352, Rarity.RARE, mage.cards.b.BannerOfKinship.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Banner of Kinship", 484, Rarity.RARE, mage.cards.b.BannerOfKinship.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Basilisk Collar", 669, Rarity.RARE, mage.cards.b.BasiliskCollar.class));
        cards.add(new SetCardInfo("Battle-Rattle Shaman", 533, Rarity.UNCOMMON, mage.cards.b.BattleRattleShaman.class));
        cards.add(new SetCardInfo("Battlesong Berserker", 78, Rarity.UNCOMMON, mage.cards.b.BattlesongBerserker.class));
        cards.add(new SetCardInfo("Bear Cub", 552, Rarity.COMMON, mage.cards.b.BearCub.class));
        cards.add(new SetCardInfo("Beast-Kin Ranger", 100, Rarity.COMMON, mage.cards.b.BeastKinRanger.class));
        cards.add(new SetCardInfo("Bigfin Bouncer", 31, Rarity.COMMON, mage.cards.b.BigfinBouncer.class));
        cards.add(new SetCardInfo("Billowing Shriekmass", 56, Rarity.UNCOMMON, mage.cards.b.BillowingShriekmass.class));
        cards.add(new SetCardInfo("Biogenic Upgrade", 553, Rarity.UNCOMMON, mage.cards.b.BiogenicUpgrade.class));
        cards.add(new SetCardInfo("Bishop's Soldier", 491, Rarity.COMMON, mage.cards.b.BishopsSoldier.class));
        cards.add(new SetCardInfo("Bite Down", 212, Rarity.COMMON, mage.cards.b.BiteDown.class));
        cards.add(new SetCardInfo("Blanchwood Armor", 213, Rarity.UNCOMMON, mage.cards.b.BlanchwoodArmor.class));
        cards.add(new SetCardInfo("Blasphemous Edict", 57, Rarity.RARE, mage.cards.b.BlasphemousEdict.class));
        cards.add(new SetCardInfo("Bloodfell Caves", 259, Rarity.COMMON, mage.cards.b.BloodfellCaves.class));
        cards.add(new SetCardInfo("Bloodthirsty Conqueror", 58, Rarity.MYTHIC, mage.cards.b.BloodthirstyConqueror.class));
        cards.add(new SetCardInfo("Bloodtithe Collector", 516, Rarity.UNCOMMON, mage.cards.b.BloodtitheCollector.class));
        cards.add(new SetCardInfo("Blossoming Sands", 260, Rarity.COMMON, mage.cards.b.BlossomingSands.class));
        cards.add(new SetCardInfo("Bolt Bend", 619, Rarity.UNCOMMON, mage.cards.b.BoltBend.class));
        cards.add(new SetCardInfo("Boltwave", 79, Rarity.UNCOMMON, mage.cards.b.Boltwave.class));
        cards.add(new SetCardInfo("Boros Charm", 721, Rarity.UNCOMMON, mage.cards.b.BorosCharm.class));
        cards.add(new SetCardInfo("Boros Guildgate", 684, Rarity.COMMON, mage.cards.b.BorosGuildgate.class));
        cards.add(new SetCardInfo("Brass's Bounty", 190, Rarity.RARE, mage.cards.b.BrasssBounty.class));
        cards.add(new SetCardInfo("Brazen Scourge", 191, Rarity.UNCOMMON, mage.cards.b.BrazenScourge.class));
        cards.add(new SetCardInfo("Brineborn Cutthroat", 152, Rarity.UNCOMMON, mage.cards.b.BrinebornCutthroat.class));
        cards.add(new SetCardInfo("Broken Wings", 214, Rarity.COMMON, mage.cards.b.BrokenWings.class));
        cards.add(new SetCardInfo("Bulk Up", 80, Rarity.UNCOMMON, mage.cards.b.BulkUp.class));
        cards.add(new SetCardInfo("Burglar Rat", 170, Rarity.COMMON, mage.cards.b.BurglarRat.class));
        cards.add(new SetCardInfo("Burnished Hart", 250, Rarity.UNCOMMON, mage.cards.b.BurnishedHart.class));
        cards.add(new SetCardInfo("Burrog Befuddler", 504, Rarity.COMMON, mage.cards.b.BurrogBefuddler.class));
        cards.add(new SetCardInfo("Burst Lightning", 192, Rarity.COMMON, mage.cards.b.BurstLightning.class));
        cards.add(new SetCardInfo("Bushwhack", 215, Rarity.COMMON, mage.cards.b.Bushwhack.class));
        cards.add(new SetCardInfo("Cackling Prowler", 101, Rarity.COMMON, mage.cards.c.CacklingProwler.class));
        cards.add(new SetCardInfo("Campus Guide", 251, Rarity.COMMON, mage.cards.c.CampusGuide.class));
        cards.add(new SetCardInfo("Cancel", 505, Rarity.COMMON, mage.cards.c.Cancel.class));
        cards.add(new SetCardInfo("Carnelian Orb of Dragonkind", 534, Rarity.COMMON, mage.cards.c.CarnelianOrbOfDragonkind.class));
        cards.add(new SetCardInfo("Cat Collector", 4, Rarity.UNCOMMON, mage.cards.c.CatCollector.class));
        cards.add(new SetCardInfo("Cathar Commando", 139, Rarity.COMMON, mage.cards.c.CatharCommando.class));
        cards.add(new SetCardInfo("Celestial Armor", 5, Rarity.RARE, mage.cards.c.CelestialArmor.class));
        cards.add(new SetCardInfo("Cemetery Recruitment", 517, Rarity.COMMON, mage.cards.c.CemeteryRecruitment.class));
        cards.add(new SetCardInfo("Cephalid Inkmage", 32, Rarity.UNCOMMON, mage.cards.c.CephalidInkmage.class));
        cards.add(new SetCardInfo("Chandra, Flameshaper", 81, Rarity.MYTHIC, mage.cards.c.ChandraFlameshaper.class));
        cards.add(new SetCardInfo("Charming Prince", 568, Rarity.RARE, mage.cards.c.CharmingPrince.class));
        cards.add(new SetCardInfo("Chart a Course", 586, Rarity.UNCOMMON, mage.cards.c.ChartACourse.class));
        cards.add(new SetCardInfo("Circuitous Route", 635, Rarity.UNCOMMON, mage.cards.c.CircuitousRoute.class));
        cards.add(new SetCardInfo("Claws Out", 6, Rarity.UNCOMMON, mage.cards.c.ClawsOut.class));
        cards.add(new SetCardInfo("Clinquant Skymage", 33, Rarity.UNCOMMON, mage.cards.c.ClinquantSkymage.class));
        cards.add(new SetCardInfo("Cloudblazer", 653, Rarity.UNCOMMON, mage.cards.c.Cloudblazer.class));
        cards.add(new SetCardInfo("Confiscate", 709, Rarity.UNCOMMON, mage.cards.c.Confiscate.class));
        cards.add(new SetCardInfo("Consuming Aberration", 238, Rarity.RARE, mage.cards.c.ConsumingAberration.class));
        cards.add(new SetCardInfo("Corsair Captain", 506, Rarity.RARE, mage.cards.c.CorsairCaptain.class));
        cards.add(new SetCardInfo("Courageous Goblin", 82, Rarity.COMMON, mage.cards.c.CourageousGoblin.class));
        cards.add(new SetCardInfo("Crackling Cyclops", 83, Rarity.COMMON, mage.cards.c.CracklingCyclops.class));
        cards.add(new SetCardInfo("Crash Through", 620, Rarity.COMMON, mage.cards.c.CrashThrough.class));
        cards.add(new SetCardInfo("Crawling Barrens", 685, Rarity.RARE, mage.cards.c.CrawlingBarrens.class));
        cards.add(new SetCardInfo("Crossway Troublemakers", 518, Rarity.RARE, mage.cards.c.CrosswayTroublemakers.class));
        cards.add(new SetCardInfo("Crow of Dark Tidings", 519, Rarity.COMMON, mage.cards.c.CrowOfDarkTidings.class));
        cards.add(new SetCardInfo("Crusader of Odric", 569, Rarity.COMMON, mage.cards.c.CrusaderOfOdric.class));
        cards.add(new SetCardInfo("Crypt Feaster", 59, Rarity.COMMON, mage.cards.c.CryptFeaster.class));
        cards.add(new SetCardInfo("Cryptic Caves", 686, Rarity.UNCOMMON, mage.cards.c.CrypticCaves.class));
        cards.add(new SetCardInfo("Crystal Barricade", 7, Rarity.RARE, mage.cards.c.CrystalBarricade.class));
        cards.add(new SetCardInfo("Cultivator's Caravan", 670, Rarity.RARE, mage.cards.c.CultivatorsCaravan.class));
        cards.add(new SetCardInfo("Curator of Destinies", 34, Rarity.RARE, mage.cards.c.CuratorOfDestinies.class));
        cards.add(new SetCardInfo("Darksteel Colossus", 671, Rarity.MYTHIC, mage.cards.d.DarksteelColossus.class));
        cards.add(new SetCardInfo("Dauntless Veteran", 8, Rarity.UNCOMMON, mage.cards.d.DauntlessVeteran.class));
        cards.add(new SetCardInfo("Dawnwing Marshal", 570, Rarity.UNCOMMON, mage.cards.d.DawnwingMarshal.class));
        cards.add(new SetCardInfo("Day of Judgment", 140, Rarity.RARE, mage.cards.d.DayOfJudgment.class));
        cards.add(new SetCardInfo("Dazzling Angel", 9, Rarity.COMMON, mage.cards.d.DazzlingAngel.class));
        cards.add(new SetCardInfo("Deadly Brew", 654, Rarity.UNCOMMON, mage.cards.d.DeadlyBrew.class));
        cards.add(new SetCardInfo("Deadly Plot", 520, Rarity.UNCOMMON, mage.cards.d.DeadlyPlot.class));
        cards.add(new SetCardInfo("Deadly Riposte", 492, Rarity.COMMON, mage.cards.d.DeadlyRiposte.class));
        cards.add(new SetCardInfo("Death Baron", 521, Rarity.RARE, mage.cards.d.DeathBaron.class));
        cards.add(new SetCardInfo("Deathmark", 601, Rarity.UNCOMMON, mage.cards.d.Deathmark.class));
        cards.add(new SetCardInfo("Demolition Field", 687, Rarity.UNCOMMON, mage.cards.d.DemolitionField.class));
        cards.add(new SetCardInfo("Demonic Pact", 602, Rarity.MYTHIC, mage.cards.d.DemonicPact.class));
        cards.add(new SetCardInfo("Desecration Demon", 603, Rarity.RARE, mage.cards.d.DesecrationDemon.class));
        cards.add(new SetCardInfo("Devout Decree", 571, Rarity.UNCOMMON, mage.cards.d.DevoutDecree.class));
        cards.add(new SetCardInfo("Diamond Mare", 672, Rarity.UNCOMMON, mage.cards.d.DiamondMare.class));
        cards.add(new SetCardInfo("Dictate of Kruphix", 587, Rarity.RARE, mage.cards.d.DictateOfKruphix.class));
        cards.add(new SetCardInfo("Dimir Guildgate", 688, Rarity.COMMON, mage.cards.d.DimirGuildgate.class));
        cards.add(new SetCardInfo("Diregraf Ghoul", 171, Rarity.UNCOMMON, mage.cards.d.DiregrafGhoul.class));
        cards.add(new SetCardInfo("Disenchant", 572, Rarity.COMMON, mage.cards.d.Disenchant.class));
        cards.add(new SetCardInfo("Dismal Backwater", 261, Rarity.COMMON, mage.cards.d.DismalBackwater.class));
        cards.add(new SetCardInfo("Dive Down", 588, Rarity.COMMON, mage.cards.d.DiveDown.class));
        cards.add(new SetCardInfo("Divine Resilience", 10, Rarity.UNCOMMON, mage.cards.d.DivineResilience.class));
        cards.add(new SetCardInfo("Doubling Season", 216, Rarity.MYTHIC, mage.cards.d.DoublingSeason.class));
        cards.add(new SetCardInfo("Dragon Fodder", 535, Rarity.COMMON, mage.cards.d.DragonFodder.class));
        cards.add(new SetCardInfo("Dragon Mage", 621, Rarity.UNCOMMON, mage.cards.d.DragonMage.class));
        cards.add(new SetCardInfo("Dragon Trainer", 84, Rarity.UNCOMMON, mage.cards.d.DragonTrainer.class));
        cards.add(new SetCardInfo("Dragonlord's Servant", 536, Rarity.UNCOMMON, mage.cards.d.DragonlordsServant.class));
        cards.add(new SetCardInfo("Dragonmaster Outcast", 622, Rarity.MYTHIC, mage.cards.d.DragonmasterOutcast.class));
        cards.add(new SetCardInfo("Drake Hatcher", 35, Rarity.RARE, mage.cards.d.DrakeHatcher.class));
        cards.add(new SetCardInfo("Drakuseth, Maw of Flames", 193, Rarity.RARE, mage.cards.d.DrakusethMawOfFlames.class));
        cards.add(new SetCardInfo("Dread Summons", 604, Rarity.RARE, mage.cards.d.DreadSummons.class));
        cards.add(new SetCardInfo("Dreadwing Scavenger", 118, Rarity.UNCOMMON, mage.cards.d.DreadwingScavenger.class));
        cards.add(new SetCardInfo("Driver of the Dead", 605, Rarity.COMMON, mage.cards.d.DriverOfTheDead.class));
        cards.add(new SetCardInfo("Drogskol Reaver", 655, Rarity.RARE, mage.cards.d.DrogskolReaver.class));
        cards.add(new SetCardInfo("Dropkick Bomber", 537, Rarity.RARE, mage.cards.d.DropkickBomber.class));
        cards.add(new SetCardInfo("Druid of the Cowl", 554, Rarity.COMMON, mage.cards.d.DruidOfTheCowl.class));
        cards.add(new SetCardInfo("Dryad Militant", 656, Rarity.UNCOMMON, mage.cards.d.DryadMilitant.class));
        cards.add(new SetCardInfo("Duress", 606, Rarity.COMMON, mage.cards.d.Duress.class));
        cards.add(new SetCardInfo("Dwynen's Elite", 218, Rarity.COMMON, mage.cards.d.DwynensElite.class));
        cards.add(new SetCardInfo("Dwynen, Gilt-Leaf Daen", 217, Rarity.UNCOMMON, mage.cards.d.DwynenGiltLeafDaen.class));
        cards.add(new SetCardInfo("Eager Trufflesnout", 102, Rarity.UNCOMMON, mage.cards.e.EagerTrufflesnout.class));
        cards.add(new SetCardInfo("Eaten Alive", 172, Rarity.COMMON, mage.cards.e.EatenAlive.class));
        cards.add(new SetCardInfo("Eaten by Piranhas", 507, Rarity.UNCOMMON, mage.cards.e.EatenByPiranhas.class));
        cards.add(new SetCardInfo("Electroduplicate", 85, Rarity.RARE, mage.cards.e.Electroduplicate.class));
        cards.add(new SetCardInfo("Elementalist Adept", 36, Rarity.COMMON, mage.cards.e.ElementalistAdept.class));
        cards.add(new SetCardInfo("Elenda, Saint of Dusk", 119, Rarity.RARE, mage.cards.e.ElendaSaintOfDusk.class));
        cards.add(new SetCardInfo("Elfsworn Giant", 103, Rarity.COMMON, mage.cards.e.ElfswornGiant.class));
        cards.add(new SetCardInfo("Elspeth's Smite", 493, Rarity.UNCOMMON, mage.cards.e.ElspethsSmite.class));
        cards.add(new SetCardInfo("Elvish Archdruid", 219, Rarity.RARE, mage.cards.e.ElvishArchdruid.class));
        cards.add(new SetCardInfo("Elvish Regrower", 104, Rarity.UNCOMMON, mage.cards.e.ElvishRegrower.class));
        cards.add(new SetCardInfo("Empyrean Eagle", 239, Rarity.UNCOMMON, mage.cards.e.EmpyreanEagle.class));
        cards.add(new SetCardInfo("Enigma Drake", 657, Rarity.UNCOMMON, mage.cards.e.EnigmaDrake.class));
        cards.add(new SetCardInfo("Erudite Wizard", 37, Rarity.COMMON, mage.cards.e.EruditeWizard.class));
        cards.add(new SetCardInfo("Essence Scatter", 153, Rarity.UNCOMMON, mage.cards.e.EssenceScatter.class));
        cards.add(new SetCardInfo("Etali, Primal Storm", 194, Rarity.RARE, mage.cards.e.EtaliPrimalStorm.class));
        cards.add(new SetCardInfo("Evolving Wilds", 262, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Exclusion Mage", 508, Rarity.UNCOMMON, mage.cards.e.ExclusionMage.class));
        cards.add(new SetCardInfo("Exemplar of Light", 11, Rarity.RARE, mage.cards.e.ExemplarOfLight.class));
        cards.add(new SetCardInfo("Expedition Map", 724, Rarity.COMMON, mage.cards.e.ExpeditionMap.class));
        cards.add(new SetCardInfo("Exsanguinate", 173, Rarity.UNCOMMON, mage.cards.e.Exsanguinate.class));
        cards.add(new SetCardInfo("Extravagant Replication", 154, Rarity.RARE, mage.cards.e.ExtravagantReplication.class));
        cards.add(new SetCardInfo("Faebloom Trick", 38, Rarity.UNCOMMON, mage.cards.f.FaebloomTrick.class));
        cards.add(new SetCardInfo("Fake Your Own Death", 174, Rarity.COMMON, mage.cards.f.FakeYourOwnDeath.class));
        cards.add(new SetCardInfo("Fanatical Firebrand", 195, Rarity.COMMON, mage.cards.f.FanaticalFirebrand.class));
        cards.add(new SetCardInfo("Feed the Swarm", 712, Rarity.COMMON, mage.cards.f.FeedTheSwarm.class));
        cards.add(new SetCardInfo("Feldon's Cane", 673, Rarity.UNCOMMON, mage.cards.f.FeldonsCane.class));
        cards.add(new SetCardInfo("Felidar Cub", 573, Rarity.COMMON, mage.cards.f.FelidarCub.class));
        cards.add(new SetCardInfo("Felidar Retreat", 574, Rarity.RARE, mage.cards.f.FelidarRetreat.class));
        cards.add(new SetCardInfo("Felidar Savior", 12, Rarity.COMMON, mage.cards.f.FelidarSavior.class));
        cards.add(new SetCardInfo("Felling Blow", 105, Rarity.UNCOMMON, mage.cards.f.FellingBlow.class));
        cards.add(new SetCardInfo("Fiendish Panda", 120, Rarity.UNCOMMON, mage.cards.f.FiendishPanda.class));
        cards.add(new SetCardInfo("Fierce Empath", 636, Rarity.COMMON, mage.cards.f.FierceEmpath.class));
        cards.add(new SetCardInfo("Fiery Annihilation", 86, Rarity.UNCOMMON, mage.cards.f.FieryAnnihilation.class));
        cards.add(new SetCardInfo("Finale of Revelation", 589, Rarity.MYTHIC, mage.cards.f.FinaleOfRevelation.class));
        cards.add(new SetCardInfo("Fire Elemental", 538, Rarity.COMMON, mage.cards.f.FireElemental.class));
        cards.add(new SetCardInfo("Firebrand Archer", 196, Rarity.COMMON, mage.cards.f.FirebrandArcher.class));
        cards.add(new SetCardInfo("Fireshrieker", 674, Rarity.UNCOMMON, mage.cards.f.Fireshrieker.class));
        cards.add(new SetCardInfo("Firespitter Whelp", 197, Rarity.UNCOMMON, mage.cards.f.FirespitterWhelp.class));
        cards.add(new SetCardInfo("Fishing Pole", 128, Rarity.UNCOMMON, mage.cards.f.FishingPole.class));
        cards.add(new SetCardInfo("Flamewake Phoenix", 198, Rarity.RARE, mage.cards.f.FlamewakePhoenix.class));
        cards.add(new SetCardInfo("Flashfreeze", 590, Rarity.UNCOMMON, mage.cards.f.Flashfreeze.class));
        cards.add(new SetCardInfo("Fleeting Distraction", 155, Rarity.COMMON, mage.cards.f.FleetingDistraction.class));
        cards.add(new SetCardInfo("Fleeting Flight", 13, Rarity.COMMON, mage.cards.f.FleetingFlight.class));
        cards.add(new SetCardInfo("Fog Bank", 591, Rarity.UNCOMMON, mage.cards.f.FogBank.class));
        cards.add(new SetCardInfo("Forest", 280, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forest", 290, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 291, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frenzied Goblin", 199, Rarity.UNCOMMON, mage.cards.f.FrenziedGoblin.class));
        cards.add(new SetCardInfo("Fumigate", 575, Rarity.RARE, mage.cards.f.Fumigate.class));
        cards.add(new SetCardInfo("Fynn, the Fangbearer", 637, Rarity.UNCOMMON, mage.cards.f.FynnTheFangbearer.class));
        cards.add(new SetCardInfo("Garna, Bloodfist of Keld", 658, Rarity.UNCOMMON, mage.cards.g.GarnaBloodfistOfKeld.class));
        cards.add(new SetCardInfo("Garruk's Uprising", 220, Rarity.UNCOMMON, mage.cards.g.GarruksUprising.class));
        cards.add(new SetCardInfo("Gate Colossus", 675, Rarity.UNCOMMON, mage.cards.g.GateColossus.class));
        cards.add(new SetCardInfo("Gatekeeper of Malakir", 713, Rarity.UNCOMMON, mage.cards.g.GatekeeperOfMalakir.class));
        cards.add(new SetCardInfo("Gateway Sneak", 592, Rarity.UNCOMMON, mage.cards.g.GatewaySneak.class));
        cards.add(new SetCardInfo("Genesis Wave", 221, Rarity.RARE, mage.cards.g.GenesisWave.class));
        cards.add(new SetCardInfo("Ghalta, Primal Hunger", 222, Rarity.RARE, mage.cards.g.GhaltaPrimalHunger.class));
        cards.add(new SetCardInfo("Ghitu Lavarunner", 623, Rarity.COMMON, mage.cards.g.GhituLavarunner.class));
        cards.add(new SetCardInfo("Giada, Font of Hope", 141, Rarity.RARE, mage.cards.g.GiadaFontOfHope.class));
        cards.add(new SetCardInfo("Giant Cindermaw", 624, Rarity.UNCOMMON, mage.cards.g.GiantCindermaw.class));
        cards.add(new SetCardInfo("Giant Growth", 223, Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Gigantosaurus", 718, Rarity.RARE, mage.cards.g.Gigantosaurus.class));
        cards.add(new SetCardInfo("Gilded Lotus", 725, Rarity.RARE, mage.cards.g.GildedLotus.class));
        cards.add(new SetCardInfo("Gleaming Barrier", 252, Rarity.COMMON, mage.cards.g.GleamingBarrier.class));
        cards.add(new SetCardInfo("Gnarlback Rhino", 638, Rarity.UNCOMMON, mage.cards.g.GnarlbackRhino.class));
        cards.add(new SetCardInfo("Gnarlid Colony", 224, Rarity.COMMON, mage.cards.g.GnarlidColony.class));
        cards.add(new SetCardInfo("Goblin Boarders", 87, Rarity.COMMON, mage.cards.g.GoblinBoarders.class));
        cards.add(new SetCardInfo("Goblin Firebomb", 562, Rarity.COMMON, mage.cards.g.GoblinFirebomb.class));
        cards.add(new SetCardInfo("Goblin Negotiation", 88, Rarity.UNCOMMON, mage.cards.g.GoblinNegotiation.class));
        cards.add(new SetCardInfo("Goblin Oriflamme", 539, Rarity.UNCOMMON, mage.cards.g.GoblinOriflamme.class));
        cards.add(new SetCardInfo("Goblin Smuggler", 540, Rarity.COMMON, mage.cards.g.GoblinSmuggler.class));
        cards.add(new SetCardInfo("Goblin Surprise", 200, Rarity.COMMON, mage.cards.g.GoblinSurprise.class));
        cards.add(new SetCardInfo("Goldvein Pick", 253, Rarity.COMMON, mage.cards.g.GoldveinPick.class));
        cards.add(new SetCardInfo("Golgari Guildgate", 689, Rarity.COMMON, mage.cards.g.GolgariGuildgate.class));
        cards.add(new SetCardInfo("Good-Fortune Unicorn", 240, Rarity.UNCOMMON, mage.cards.g.GoodFortuneUnicorn.class));
        cards.add(new SetCardInfo("Gorehorn Raider", 89, Rarity.COMMON, mage.cards.g.GorehornRaider.class));
        cards.add(new SetCardInfo("Grappling Kraken", 39, Rarity.UNCOMMON, mage.cards.g.GrapplingKraken.class));
        cards.add(new SetCardInfo("Gratuitous Violence", 715, Rarity.RARE, mage.cards.g.GratuitousViolence.class));
        cards.add(new SetCardInfo("Grow from the Ashes", 225, Rarity.COMMON, mage.cards.g.GrowFromTheAshes.class));
        cards.add(new SetCardInfo("Gruul Guildgate", 690, Rarity.COMMON, mage.cards.g.GruulGuildgate.class));
        cards.add(new SetCardInfo("Guarded Heir", 14, Rarity.UNCOMMON, mage.cards.g.GuardedHeir.class));
        cards.add(new SetCardInfo("Gutless Plunderer", 60, Rarity.COMMON, mage.cards.g.GutlessPlunderer.class));
        cards.add(new SetCardInfo("Guttersnipe", 716, Rarity.UNCOMMON, mage.cards.g.Guttersnipe.class));
        cards.add(new SetCardInfo("Halana and Alena, Partners", 659, Rarity.RARE, mage.cards.h.HalanaAndAlenaPartners.class));
        cards.add(new SetCardInfo("Harbinger of the Tides", 593, Rarity.RARE, mage.cards.h.HarbingerOfTheTides.class));
        cards.add(new SetCardInfo("Hare Apparent", 15, Rarity.COMMON, mage.cards.h.HareApparent.class));
        cards.add(new SetCardInfo("Harmless Offering", 625, Rarity.RARE, mage.cards.h.HarmlessOffering.class));
        cards.add(new SetCardInfo("Healer's Hawk", 142, Rarity.COMMON, mage.cards.h.HealersHawk.class));
        cards.add(new SetCardInfo("Heartfire Immolator", 201, Rarity.UNCOMMON, mage.cards.h.HeartfireImmolator.class));
        cards.add(new SetCardInfo("Hedron Archive", 726, Rarity.UNCOMMON, mage.cards.h.HedronArchive.class));
        cards.add(new SetCardInfo("Helpful Hunter", 16, Rarity.COMMON, mage.cards.h.HelpfulHunter.class));
        cards.add(new SetCardInfo("Herald of Eternal Dawn", 17, Rarity.MYTHIC, mage.cards.h.HeraldOfEternalDawn.class));
        cards.add(new SetCardInfo("Herald of Faith", 494, Rarity.UNCOMMON, mage.cards.h.HeraldOfFaith.class));
        cards.add(new SetCardInfo("Heraldic Banner", 254, Rarity.UNCOMMON, mage.cards.h.HeraldicBanner.class));
        cards.add(new SetCardInfo("Hero's Downfall", 175, Rarity.UNCOMMON, mage.cards.h.HerosDownfall.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hero's Downfall", 319, Rarity.UNCOMMON, mage.cards.h.HerosDownfall.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Heroes' Bane", 639, Rarity.RARE, mage.cards.h.HeroesBane.class));
        cards.add(new SetCardInfo("Heroic Reinforcements", 241, Rarity.UNCOMMON, mage.cards.h.HeroicReinforcements.class));
        cards.add(new SetCardInfo("Hidetsugu's Second Rite", 202, Rarity.UNCOMMON, mage.cards.h.HidetsugusSecondRite.class));
        cards.add(new SetCardInfo("High Fae Trickster", 40, Rarity.RARE, mage.cards.h.HighFaeTrickster.class));
        cards.add(new SetCardInfo("High-Society Hunter", 61, Rarity.RARE, mage.cards.h.HighSocietyHunter.class));
        cards.add(new SetCardInfo("Highborn Vampire", 522, Rarity.COMMON, mage.cards.h.HighbornVampire.class));
        cards.add(new SetCardInfo("Hinterland Sanctifier", 730, Rarity.COMMON, mage.cards.h.HinterlandSanctifier.class));
        cards.add(new SetCardInfo("Hoarding Dragon", 626, Rarity.UNCOMMON, mage.cards.h.HoardingDragon.class));
        cards.add(new SetCardInfo("Homunculus Horde", 41, Rarity.RARE, mage.cards.h.HomunculusHorde.class));
        cards.add(new SetCardInfo("Hungry Ghoul", 62, Rarity.COMMON, mage.cards.h.HungryGhoul.class));
        cards.add(new SetCardInfo("Icewind Elemental", 42, Rarity.COMMON, mage.cards.i.IcewindElemental.class));
        cards.add(new SetCardInfo("Immersturm Predator", 660, Rarity.RARE, mage.cards.i.ImmersturmPredator.class));
        cards.add(new SetCardInfo("Impact Tremors", 717, Rarity.COMMON, mage.cards.i.ImpactTremors.class));
        cards.add(new SetCardInfo("Imperious Perfect", 719, Rarity.UNCOMMON, mage.cards.i.ImperiousPerfect.class));
        cards.add(new SetCardInfo("Imprisoned in the Moon", 156, Rarity.UNCOMMON, mage.cards.i.ImprisonedInTheMoon.class));
        cards.add(new SetCardInfo("Incinerating Blast", 90, Rarity.COMMON, mage.cards.i.IncineratingBlast.class));
        cards.add(new SetCardInfo("Infernal Vessel", 63, Rarity.UNCOMMON, mage.cards.i.InfernalVessel.class));
        cards.add(new SetCardInfo("Infestation Sage", 64, Rarity.COMMON, mage.cards.i.InfestationSage.class));
        cards.add(new SetCardInfo("Ingenious Leonin", 495, Rarity.UNCOMMON, mage.cards.i.IngeniousLeonin.class));
        cards.add(new SetCardInfo("Inspiration from Beyond", 43, Rarity.UNCOMMON, mage.cards.i.InspirationFromBeyond.class));
        cards.add(new SetCardInfo("Inspiring Call", 226, Rarity.UNCOMMON, mage.cards.i.InspiringCall.class));
        cards.add(new SetCardInfo("Inspiring Overseer", 496, Rarity.COMMON, mage.cards.i.InspiringOverseer.class));
        cards.add(new SetCardInfo("Inspiring Paladin", 18, Rarity.COMMON, mage.cards.i.InspiringPaladin.class));
        cards.add(new SetCardInfo("Into the Roil", 509, Rarity.COMMON, mage.cards.i.IntoTheRoil.class));
        cards.add(new SetCardInfo("Involuntary Employment", 203, Rarity.COMMON, mage.cards.i.InvoluntaryEmployment.class));
        cards.add(new SetCardInfo("Island", 274, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 284, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 285, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Izzet Guildgate", 691, Rarity.COMMON, mage.cards.i.IzzetGuildgate.class));
        cards.add(new SetCardInfo("Jazal Goldmane", 497, Rarity.RARE, mage.cards.j.JazalGoldmane.class));
        cards.add(new SetCardInfo("Joraga Invocation", 555, Rarity.UNCOMMON, mage.cards.j.JoragaInvocation.class));
        cards.add(new SetCardInfo("Joust Through", 19, Rarity.UNCOMMON, mage.cards.j.JoustThrough.class));
        cards.add(new SetCardInfo("Juggernaut", 255, Rarity.UNCOMMON, mage.cards.j.Juggernaut.class));
        cards.add(new SetCardInfo("Jungle Hollow", 263, Rarity.COMMON, mage.cards.j.JungleHollow.class));
        cards.add(new SetCardInfo("Kaito, Cunning Infiltrator", 44, Rarity.MYTHIC, mage.cards.k.KaitoCunningInfiltrator.class));
        cards.add(new SetCardInfo("Kalastria Highborn", 607, Rarity.RARE, mage.cards.k.KalastriaHighborn.class));
        cards.add(new SetCardInfo("Kargan Dragonrider", 541, Rarity.COMMON, mage.cards.k.KarganDragonrider.class));
        cards.add(new SetCardInfo("Kellan, Planar Trailblazer", 91, Rarity.RARE, mage.cards.k.KellanPlanarTrailblazer.class));
        cards.add(new SetCardInfo("Kindled Fury", 542, Rarity.COMMON, mage.cards.k.KindledFury.class));
        cards.add(new SetCardInfo("Kiora, the Rising Tide", 45, Rarity.RARE, mage.cards.k.KioraTheRisingTide.class));
        cards.add(new SetCardInfo("Kitesail Corsair", 510, Rarity.COMMON, mage.cards.k.KitesailCorsair.class));
        cards.add(new SetCardInfo("Knight of Grace", 576, Rarity.UNCOMMON, mage.cards.k.KnightOfGrace.class));
        cards.add(new SetCardInfo("Knight of Malice", 608, Rarity.UNCOMMON, mage.cards.k.KnightOfMalice.class));
        cards.add(new SetCardInfo("Koma, World-Eater", 121, Rarity.RARE, mage.cards.k.KomaWorldEater.class));
        cards.add(new SetCardInfo("Krenko, Mob Boss", 204, Rarity.RARE, mage.cards.k.KrenkoMobBoss.class));
        cards.add(new SetCardInfo("Kykar, Zephyr Awakener", 122, Rarity.RARE, mage.cards.k.KykarZephyrAwakener.class));
        cards.add(new SetCardInfo("Lathliss, Dragon Queen", 627, Rarity.RARE, mage.cards.l.LathlissDragonQueen.class));
        cards.add(new SetCardInfo("Lathril, Blade of the Elves", 242, Rarity.RARE, mage.cards.l.LathrilBladeOfTheElves.class));
        cards.add(new SetCardInfo("Leonin Skyhunter", 498, Rarity.UNCOMMON, mage.cards.l.LeoninSkyhunter.class));
        cards.add(new SetCardInfo("Leonin Vanguard", 499, Rarity.UNCOMMON, mage.cards.l.LeoninVanguard.class));
        cards.add(new SetCardInfo("Leyline Axe", 129, Rarity.RARE, mage.cards.l.LeylineAxe.class));
        cards.add(new SetCardInfo("Lightshell Duo", 157, Rarity.COMMON, mage.cards.l.LightshellDuo.class));
        cards.add(new SetCardInfo("Liliana, Dreadhorde General", 176, Rarity.MYTHIC, mage.cards.l.LilianaDreadhordeGeneral.class));
        cards.add(new SetCardInfo("Linden, the Steadfast Queen", 577, Rarity.RARE, mage.cards.l.LindenTheSteadfastQueen.class));
        cards.add(new SetCardInfo("Llanowar Elves", 227, Rarity.COMMON, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Loot, Exuberant Explorer", 106, Rarity.RARE, mage.cards.l.LootExuberantExplorer.class));
        cards.add(new SetCardInfo("Luminous Rebuke", 20, Rarity.COMMON, mage.cards.l.LuminousRebuke.class));
        cards.add(new SetCardInfo("Lunar Insight", 46, Rarity.RARE, mage.cards.l.LunarInsight.class));
        cards.add(new SetCardInfo("Lyra Dawnbringer", 707, Rarity.MYTHIC, mage.cards.l.LyraDawnbringer.class));
        cards.add(new SetCardInfo("Maalfeld Twins", 523, Rarity.UNCOMMON, mage.cards.m.MaalfeldTwins.class));
        cards.add(new SetCardInfo("Macabre Waltz", 177, Rarity.COMMON, mage.cards.m.MacabreWaltz.class));
        cards.add(new SetCardInfo("Maelstrom Pulse", 661, Rarity.RARE, mage.cards.m.MaelstromPulse.class));
        cards.add(new SetCardInfo("Magnigoth Sentry", 556, Rarity.COMMON, mage.cards.m.MagnigothSentry.class));
        cards.add(new SetCardInfo("Make Your Move", 143, Rarity.COMMON, mage.cards.m.MakeYourMove.class));
        cards.add(new SetCardInfo("Make a Stand", 708, Rarity.UNCOMMON, mage.cards.m.MakeAStand.class));
        cards.add(new SetCardInfo("Marauding Blight-Priest", 178, Rarity.COMMON, mage.cards.m.MaraudingBlightPriest.class));
        cards.add(new SetCardInfo("Massacre Wurm", 714, Rarity.MYTHIC, mage.cards.m.MassacreWurm.class));
        cards.add(new SetCardInfo("Maze's End", 727, Rarity.MYTHIC, mage.cards.m.MazesEnd.class));
        cards.add(new SetCardInfo("Mazemind Tome", 676, Rarity.RARE, mage.cards.m.MazemindTome.class));
        cards.add(new SetCardInfo("Mentor of the Meek", 578, Rarity.RARE, mage.cards.m.MentorOfTheMeek.class));
        cards.add(new SetCardInfo("Meteor Golem", 256, Rarity.UNCOMMON, mage.cards.m.MeteorGolem.class));
        cards.add(new SetCardInfo("Micromancer", 158, Rarity.UNCOMMON, mage.cards.m.Micromancer.class));
        cards.add(new SetCardInfo("Midnight Reaper", 609, Rarity.RARE, mage.cards.m.MidnightReaper.class));
        cards.add(new SetCardInfo("Midnight Snack", 65, Rarity.UNCOMMON, mage.cards.m.MidnightSnack.class));
        cards.add(new SetCardInfo("Mild-Mannered Librarian", 228, Rarity.UNCOMMON, mage.cards.m.MildManneredLibrarian.class));
        cards.add(new SetCardInfo("Mindsparker", 628, Rarity.UNCOMMON, mage.cards.m.Mindsparker.class));
        cards.add(new SetCardInfo("Mischievous Mystic", 47, Rarity.UNCOMMON, mage.cards.m.MischievousMystic.class));
        cards.add(new SetCardInfo("Mischievous Pup", 144, Rarity.UNCOMMON, mage.cards.m.MischievousPup.class));
        cards.add(new SetCardInfo("Mocking Sprite", 159, Rarity.COMMON, mage.cards.m.MockingSprite.class));
        cards.add(new SetCardInfo("Mold Adder", 640, Rarity.UNCOMMON, mage.cards.m.MoldAdder.class));
        cards.add(new SetCardInfo("Moment of Craving", 524, Rarity.COMMON, mage.cards.m.MomentOfCraving.class));
        cards.add(new SetCardInfo("Moment of Triumph", 500, Rarity.COMMON, mage.cards.m.MomentOfTriumph.class));
        cards.add(new SetCardInfo("Mortify", 662, Rarity.UNCOMMON, mage.cards.m.Mortify.class));
        cards.add(new SetCardInfo("Mossborn Hydra", 107, Rarity.RARE, mage.cards.m.MossbornHydra.class));
        cards.add(new SetCardInfo("Mountain", 278, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 288, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 289, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Muldrotha, the Gravetide", 243, Rarity.MYTHIC, mage.cards.m.MuldrothaTheGravetide.class));
        cards.add(new SetCardInfo("Myojin of Night's Reach", 610, Rarity.RARE, mage.cards.m.MyojinOfNightsReach.class));
        cards.add(new SetCardInfo("Mystic Archaeologist", 511, Rarity.RARE, mage.cards.m.MysticArchaeologist.class));
        cards.add(new SetCardInfo("Mystical Teachings", 594, Rarity.UNCOMMON, mage.cards.m.MysticalTeachings.class));
        cards.add(new SetCardInfo("Needletooth Pack", 108, Rarity.UNCOMMON, mage.cards.n.NeedletoothPack.class));
        cards.add(new SetCardInfo("Negate", 710, Rarity.COMMON, mage.cards.n.Negate.class));
        cards.add(new SetCardInfo("Nessian Hornbeetle", 229, Rarity.UNCOMMON, mage.cards.n.NessianHornbeetle.class));
        cards.add(new SetCardInfo("New Horizons", 557, Rarity.COMMON, mage.cards.n.NewHorizons.class));
        cards.add(new SetCardInfo("Nine-Lives Familiar", 66, Rarity.RARE, mage.cards.n.NineLivesFamiliar.class));
        cards.add(new SetCardInfo("Niv-Mizzet, Visionary", 123, Rarity.MYTHIC, mage.cards.n.NivMizzetVisionary.class));
        cards.add(new SetCardInfo("Nullpriest of Oblivion", 611, Rarity.RARE, mage.cards.n.NullpriestOfOblivion.class));
        cards.add(new SetCardInfo("Obliterating Bolt", 629, Rarity.UNCOMMON, mage.cards.o.ObliteratingBolt.class));
        cards.add(new SetCardInfo("Offer Immortality", 525, Rarity.COMMON, mage.cards.o.OfferImmortality.class));
        cards.add(new SetCardInfo("Omniscience", 161, Rarity.MYTHIC, mage.cards.o.Omniscience.class));
        cards.add(new SetCardInfo("Opt", 512, Rarity.COMMON, mage.cards.o.Opt.class));
        cards.add(new SetCardInfo("Ordeal of Nylea", 641, Rarity.UNCOMMON, mage.cards.o.OrdealOfNylea.class));
        cards.add(new SetCardInfo("Orzhov Guildgate", 692, Rarity.COMMON, mage.cards.o.OrzhovGuildgate.class));
        cards.add(new SetCardInfo("Overrun", 230, Rarity.UNCOMMON, mage.cards.o.Overrun.class));
        cards.add(new SetCardInfo("Ovika, Enigma Goliath", 663, Rarity.RARE, mage.cards.o.OvikaEnigmaGoliath.class));
        cards.add(new SetCardInfo("Pacifism", 501, Rarity.COMMON, mage.cards.p.Pacifism.class));
        cards.add(new SetCardInfo("Painful Quandary", 179, Rarity.RARE, mage.cards.p.PainfulQuandary.class));
        cards.add(new SetCardInfo("Pelakka Wurm", 720, Rarity.UNCOMMON, mage.cards.p.PelakkaWurm.class));
        cards.add(new SetCardInfo("Perforating Artist", 124, Rarity.UNCOMMON, mage.cards.p.PerforatingArtist.class));
        cards.add(new SetCardInfo("Phyrexian Arena", 180, Rarity.RARE, mage.cards.p.PhyrexianArena.class));
        cards.add(new SetCardInfo("Pilfer", 181, Rarity.COMMON, mage.cards.p.Pilfer.class));
        cards.add(new SetCardInfo("Pirate's Cutlass", 563, Rarity.COMMON, mage.cards.p.PiratesCutlass.class));
        cards.add(new SetCardInfo("Plains", 272, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 282, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 283, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prayer of Binding", 502, Rarity.UNCOMMON, mage.cards.p.PrayerOfBinding.class));
        cards.add(new SetCardInfo("Predator Ooze", 642, Rarity.RARE, mage.cards.p.PredatorOoze.class));
        cards.add(new SetCardInfo("Preposterous Proportions", 109, Rarity.RARE, mage.cards.p.PreposterousProportions.class));
        cards.add(new SetCardInfo("Prideful Parent", 21, Rarity.COMMON, mage.cards.p.PridefulParent.class));
        cards.add(new SetCardInfo("Primal Might", 643, Rarity.RARE, mage.cards.p.PrimalMight.class));
        cards.add(new SetCardInfo("Prime Speaker Zegana", 664, Rarity.RARE, mage.cards.p.PrimeSpeakerZegana.class));
        cards.add(new SetCardInfo("Primeval Bounty", 644, Rarity.MYTHIC, mage.cards.p.PrimevalBounty.class));
        cards.add(new SetCardInfo("Progenitus", 244, Rarity.MYTHIC, mage.cards.p.Progenitus.class));
        cards.add(new SetCardInfo("Pulse Tracker", 612, Rarity.COMMON, mage.cards.p.PulseTracker.class));
        cards.add(new SetCardInfo("Pyromancer's Goggles", 677, Rarity.MYTHIC, mage.cards.p.PyromancersGoggles.class));
        cards.add(new SetCardInfo("Quakestrider Ceratops", 110, Rarity.UNCOMMON, mage.cards.q.QuakestriderCeratops.class));
        cards.add(new SetCardInfo("Quick Study", 513, Rarity.COMMON, mage.cards.q.QuickStudy.class));
        cards.add(new SetCardInfo("Quilled Greatwurm", 111, Rarity.MYTHIC, mage.cards.q.QuilledGreatwurm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Quilled Greatwurm", 339, Rarity.MYTHIC, mage.cards.q.QuilledGreatwurm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Quilled Greatwurm", 473, Rarity.MYTHIC, mage.cards.q.QuilledGreatwurm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Quick-Draw Katana", 130, Rarity.COMMON, mage.cards.q.QuickDrawKatana.class));
        cards.add(new SetCardInfo("Raging Redcap", 543, Rarity.COMMON, mage.cards.r.RagingRedcap.class));
        cards.add(new SetCardInfo("Raise the Past", 22, Rarity.RARE, mage.cards.r.RaiseThePast.class));
        cards.add(new SetCardInfo("Rakdos Guildgate", 693, Rarity.COMMON, mage.cards.r.RakdosGuildgate.class));
        cards.add(new SetCardInfo("Ramos, Dragon Engine", 678, Rarity.MYTHIC, mage.cards.r.RamosDragonEngine.class));
        cards.add(new SetCardInfo("Rampaging Baloths", 645, Rarity.RARE, mage.cards.r.RampagingBaloths.class));
        cards.add(new SetCardInfo("Rapacious Dragon", 544, Rarity.COMMON, mage.cards.r.RapaciousDragon.class));
        cards.add(new SetCardInfo("Ravenous Amulet", 131, Rarity.UNCOMMON, mage.cards.r.RavenousAmulet.class));
        cards.add(new SetCardInfo("Ravenous Giant", 630, Rarity.UNCOMMON, mage.cards.r.RavenousGiant.class));
        cards.add(new SetCardInfo("Reassembling Skeleton", 182, Rarity.UNCOMMON, mage.cards.r.ReassemblingSkeleton.class));
        cards.add(new SetCardInfo("Reclamation Sage", 231, Rarity.UNCOMMON, mage.cards.r.ReclamationSage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Reclamation Sage", 340, Rarity.UNCOMMON, mage.cards.r.ReclamationSage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Redcap Gutter-Dweller", 631, Rarity.RARE, mage.cards.r.RedcapGutterDweller.class));
        cards.add(new SetCardInfo("Refute", 48, Rarity.COMMON, mage.cards.r.Refute.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Refute", 313, Rarity.COMMON, mage.cards.r.Refute.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Regal Caracal", 579, Rarity.RARE, mage.cards.r.RegalCaracal.class));
        cards.add(new SetCardInfo("Release the Dogs", 580, Rarity.UNCOMMON, mage.cards.r.ReleaseTheDogs.class));
        cards.add(new SetCardInfo("Resolute Reinforcements", 145, Rarity.UNCOMMON, mage.cards.r.ResoluteReinforcements.class));
        cards.add(new SetCardInfo("Revenge of the Rats", 67, Rarity.UNCOMMON, mage.cards.r.RevengeOfTheRats.class));
        cards.add(new SetCardInfo("Rise of the Dark Realms", 183, Rarity.MYTHIC, mage.cards.r.RiseOfTheDarkRealms.class));
        cards.add(new SetCardInfo("Rite of Replication", 711, Rarity.RARE, mage.cards.r.RiteOfReplication.class));
        cards.add(new SetCardInfo("Rite of the Dragoncaller", 92, Rarity.MYTHIC, mage.cards.r.RiteOfTheDragoncaller.class));
        cards.add(new SetCardInfo("River's Rebuke", 595, Rarity.RARE, mage.cards.r.RiversRebuke.class));
        cards.add(new SetCardInfo("Rogue's Passage", 264, Rarity.UNCOMMON, mage.cards.r.RoguesPassage.class));
        cards.add(new SetCardInfo("Ruby, Daring Tracker", 245, Rarity.UNCOMMON, mage.cards.r.RubyDaringTracker.class));
        cards.add(new SetCardInfo("Rugged Highlands", 265, Rarity.COMMON, mage.cards.r.RuggedHighlands.class));
        cards.add(new SetCardInfo("Run Away Together", 162, Rarity.COMMON, mage.cards.r.RunAwayTogether.class));
        cards.add(new SetCardInfo("Rune-Scarred Demon", 184, Rarity.RARE, mage.cards.r.RuneScarredDemon.class));
        cards.add(new SetCardInfo("Rune-Sealed Wall", 49, Rarity.UNCOMMON, mage.cards.r.RuneSealedWall.class));
        cards.add(new SetCardInfo("Sanguine Indulgence", 613, Rarity.COMMON, mage.cards.s.SanguineIndulgence.class));
        cards.add(new SetCardInfo("Sanguine Syphoner", 68, Rarity.COMMON, mage.cards.s.SanguineSyphoner.class));
        cards.add(new SetCardInfo("Savage Ventmaw", 665, Rarity.UNCOMMON, mage.cards.s.SavageVentmaw.class));
        cards.add(new SetCardInfo("Savannah Lions", 146, Rarity.UNCOMMON, mage.cards.s.SavannahLions.class));
        cards.add(new SetCardInfo("Scavenging Ooze", 232, Rarity.RARE, mage.cards.s.ScavengingOoze.class));
        cards.add(new SetCardInfo("Scorching Dragonfire", 545, Rarity.COMMON, mage.cards.s.ScorchingDragonfire.class));
        cards.add(new SetCardInfo("Scoured Barrens", 266, Rarity.COMMON, mage.cards.s.ScouredBarrens.class));
        cards.add(new SetCardInfo("Scrawling Crawler", 132, Rarity.RARE, mage.cards.s.ScrawlingCrawler.class));
        cards.add(new SetCardInfo("Searslicer Goblin", 93, Rarity.RARE, mage.cards.s.SearslicerGoblin.class));
        cards.add(new SetCardInfo("Secluded Courtyard", 267, Rarity.UNCOMMON, mage.cards.s.SecludedCourtyard.class));
        cards.add(new SetCardInfo("Seeker's Folly", 69, Rarity.UNCOMMON, mage.cards.s.SeekersFolly.class));
        cards.add(new SetCardInfo("Seismic Rupture", 205, Rarity.UNCOMMON, mage.cards.s.SeismicRupture.class));
        cards.add(new SetCardInfo("Seize the Spoils", 546, Rarity.COMMON, mage.cards.s.SeizeTheSpoils.class));
        cards.add(new SetCardInfo("Selesnya Guildgate", 694, Rarity.COMMON, mage.cards.s.SelesnyaGuildgate.class));
        cards.add(new SetCardInfo("Self-Reflection", 163, Rarity.UNCOMMON, mage.cards.s.SelfReflection.class));
        cards.add(new SetCardInfo("Serra Angel", 147, Rarity.UNCOMMON, mage.cards.s.SerraAngel.class));
        cards.add(new SetCardInfo("Shipwreck Dowser", 596, Rarity.UNCOMMON, mage.cards.s.ShipwreckDowser.class));
        cards.add(new SetCardInfo("Shivan Dragon", 206, Rarity.UNCOMMON, mage.cards.s.ShivanDragon.class));
        cards.add(new SetCardInfo("Simic Guildgate", 695, Rarity.COMMON, mage.cards.s.SimicGuildgate.class));
        cards.add(new SetCardInfo("Sire of Seven Deaths", 1, Rarity.MYTHIC, mage.cards.s.SireOfSevenDeaths.class));
        cards.add(new SetCardInfo("Skeleton Archer", 526, Rarity.COMMON, mage.cards.s.SkeletonArcher.class));
        cards.add(new SetCardInfo("Skyknight Squire", 23, Rarity.RARE, mage.cards.s.SkyknightSquire.class));
        cards.add(new SetCardInfo("Skyraker Giant", 547, Rarity.COMMON, mage.cards.s.SkyrakerGiant.class));
        cards.add(new SetCardInfo("Skyship Buccaneer", 50, Rarity.UNCOMMON, mage.cards.s.SkyshipBuccaneer.class));
        cards.add(new SetCardInfo("Slagstorm", 207, Rarity.RARE, mage.cards.s.Slagstorm.class));
        cards.add(new SetCardInfo("Slumbering Cerberus", 94, Rarity.UNCOMMON, mage.cards.s.SlumberingCerberus.class));
        cards.add(new SetCardInfo("Snakeskin Veil", 233, Rarity.UNCOMMON, mage.cards.s.SnakeskinVeil.class));
        cards.add(new SetCardInfo("Solemn Simulacrum", 257, Rarity.RARE, mage.cards.s.SolemnSimulacrum.class));
        cards.add(new SetCardInfo("Sorcerous Spyglass", 679, Rarity.UNCOMMON, mage.cards.s.SorcerousSpyglass.class));
        cards.add(new SetCardInfo("Soul-Guide Lantern", 680, Rarity.UNCOMMON, mage.cards.s.SoulGuideLantern.class));
        cards.add(new SetCardInfo("Soul-Shackled Zombie", 70, Rarity.COMMON, mage.cards.s.SoulShackledZombie.class));
        cards.add(new SetCardInfo("Soulstone Sanctuary", 133, Rarity.RARE, mage.cards.s.SoulstoneSanctuary.class));
        cards.add(new SetCardInfo("Sower of Chaos", 95, Rarity.COMMON, mage.cards.s.SowerOfChaos.class));
        cards.add(new SetCardInfo("Spectral Sailor", 164, Rarity.UNCOMMON, mage.cards.s.SpectralSailor.class));
        cards.add(new SetCardInfo("Sphinx of Forgotten Lore", 51, Rarity.MYTHIC, mage.cards.s.SphinxOfForgottenLore.class));
        cards.add(new SetCardInfo("Sphinx of the Final Word", 597, Rarity.MYTHIC, mage.cards.s.SphinxOfTheFinalWord.class));
        cards.add(new SetCardInfo("Spinner of Souls", 112, Rarity.RARE, mage.cards.s.SpinnerOfSouls.class));
        cards.add(new SetCardInfo("Spitfire Lagac", 208, Rarity.COMMON, mage.cards.s.SpitfireLagac.class));
        cards.add(new SetCardInfo("Springbloom Druid", 646, Rarity.COMMON, mage.cards.s.SpringbloomDruid.class));
        cards.add(new SetCardInfo("Squad Rallier", 24, Rarity.COMMON, mage.cards.s.SquadRallier.class));
        cards.add(new SetCardInfo("Stab", 71, Rarity.COMMON, mage.cards.s.Stab.class));
        cards.add(new SetCardInfo("Starlight Snare", 514, Rarity.COMMON, mage.cards.s.StarlightSnare.class));
        cards.add(new SetCardInfo("Stasis Snare", 581, Rarity.UNCOMMON, mage.cards.s.StasisSnare.class));
        cards.add(new SetCardInfo("Steel Hellkite", 681, Rarity.RARE, mage.cards.s.SteelHellkite.class));
        cards.add(new SetCardInfo("Storm Fleet Spy", 515, Rarity.UNCOMMON, mage.cards.s.StormFleetSpy.class));
        cards.add(new SetCardInfo("Strix Lookout", 52, Rarity.COMMON, mage.cards.s.StrixLookout.class));
        cards.add(new SetCardInfo("Stroke of Midnight", 148, Rarity.UNCOMMON, mage.cards.s.StrokeOfMidnight.class));
        cards.add(new SetCardInfo("Stromkirk Bloodthief", 185, Rarity.UNCOMMON, mage.cards.s.StromkirkBloodthief.class));
        cards.add(new SetCardInfo("Stromkirk Noble", 632, Rarity.RARE, mage.cards.s.StromkirkNoble.class));
        cards.add(new SetCardInfo("Strongbox Raider", 96, Rarity.UNCOMMON, mage.cards.s.StrongboxRaider.class));
        cards.add(new SetCardInfo("Sun-Blessed Healer", 25, Rarity.UNCOMMON, mage.cards.s.SunBlessedHealer.class));
        cards.add(new SetCardInfo("Sure Strike", 209, Rarity.COMMON, mage.cards.s.SureStrike.class));
        cards.add(new SetCardInfo("Surrak, the Hunt Caller", 647, Rarity.RARE, mage.cards.s.SurrakTheHuntCaller.class));
        cards.add(new SetCardInfo("Suspicious Shambler", 527, Rarity.COMMON, mage.cards.s.SuspiciousShambler.class));
        cards.add(new SetCardInfo("Swab Goblin", 548, Rarity.COMMON, mage.cards.s.SwabGoblin.class));
        cards.add(new SetCardInfo("Swamp", 276, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 286, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 287, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swiftblade Vindicator", 246, Rarity.RARE, mage.cards.s.SwiftbladeVindicator.class));
        cards.add(new SetCardInfo("Swiftfoot Boots", 258, Rarity.UNCOMMON, mage.cards.s.SwiftfootBoots.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swiftfoot Boots", 355, Rarity.UNCOMMON, mage.cards.s.SwiftfootBoots.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swiftwater Cliffs", 268, Rarity.COMMON, mage.cards.s.SwiftwaterCliffs.class));
        cards.add(new SetCardInfo("Sylvan Scavenging", 113, Rarity.RARE, mage.cards.s.SylvanScavenging.class));
        cards.add(new SetCardInfo("Syr Alin, the Lion's Claw", 582, Rarity.UNCOMMON, mage.cards.s.SyrAlinTheLionsClaw.class));
        cards.add(new SetCardInfo("Tajuru Pathwarden", 558, Rarity.COMMON, mage.cards.t.TajuruPathwarden.class));
        cards.add(new SetCardInfo("Tatyova, Benthic Druid", 247, Rarity.UNCOMMON, mage.cards.t.TatyovaBenthicDruid.class));
        cards.add(new SetCardInfo("Taurean Mauler", 633, Rarity.RARE, mage.cards.t.TaureanMauler.class));
        cards.add(new SetCardInfo("Teach by Example", 666, Rarity.UNCOMMON, mage.cards.t.TeachByExample.class));
        cards.add(new SetCardInfo("Tempest Djinn", 598, Rarity.RARE, mage.cards.t.TempestDjinn.class));
        cards.add(new SetCardInfo("Temple of Abandon", 696, Rarity.RARE, mage.cards.t.TempleOfAbandon.class));
        cards.add(new SetCardInfo("Temple of Deceit", 697, Rarity.RARE, mage.cards.t.TempleOfDeceit.class));
        cards.add(new SetCardInfo("Temple of Enlightenment", 698, Rarity.RARE, mage.cards.t.TempleOfEnlightenment.class));
        cards.add(new SetCardInfo("Temple of Epiphany", 699, Rarity.RARE, mage.cards.t.TempleOfEpiphany.class));
        cards.add(new SetCardInfo("Temple of Malady", 700, Rarity.RARE, mage.cards.t.TempleOfMalady.class));
        cards.add(new SetCardInfo("Temple of Malice", 701, Rarity.RARE, mage.cards.t.TempleOfMalice.class));
        cards.add(new SetCardInfo("Temple of Mystery", 702, Rarity.RARE, mage.cards.t.TempleOfMystery.class));
        cards.add(new SetCardInfo("Temple of Plenty", 703, Rarity.RARE, mage.cards.t.TempleOfPlenty.class));
        cards.add(new SetCardInfo("Temple of Silence", 704, Rarity.RARE, mage.cards.t.TempleOfSilence.class));
        cards.add(new SetCardInfo("Temple of Triumph", 705, Rarity.RARE, mage.cards.t.TempleOfTriumph.class));
        cards.add(new SetCardInfo("Terror of Mount Velus", 549, Rarity.RARE, mage.cards.t.TerrorOfMountVelus.class));
        cards.add(new SetCardInfo("Think Twice", 165, Rarity.COMMON, mage.cards.t.ThinkTwice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Think Twice", 315, Rarity.COMMON, mage.cards.t.ThinkTwice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thornweald Archer", 559, Rarity.COMMON, mage.cards.t.ThornwealdArcher.class));
        cards.add(new SetCardInfo("Thornwood Falls", 269, Rarity.COMMON, mage.cards.t.ThornwoodFalls.class));
        cards.add(new SetCardInfo("Thousand-Year Storm", 248, Rarity.RARE, mage.cards.t.ThousandYearStorm.class));
        cards.add(new SetCardInfo("Thrashing Brontodon", 560, Rarity.UNCOMMON, mage.cards.t.ThrashingBrontodon.class));
        cards.add(new SetCardInfo("Three Tree Mascot", 682, Rarity.COMMON, mage.cards.t.ThreeTreeMascot.class));
        cards.add(new SetCardInfo("Thrill of Possibility", 210, Rarity.COMMON, mage.cards.t.ThrillOfPossibility.class));
        cards.add(new SetCardInfo("Time Stop", 166, Rarity.RARE, mage.cards.t.TimeStop.class));
        cards.add(new SetCardInfo("Tinybones, Bauble Burglar", 72, Rarity.RARE, mage.cards.t.TinybonesBaubleBurglar.class));
        cards.add(new SetCardInfo("Tolarian Terror", 167, Rarity.COMMON, mage.cards.t.TolarianTerror.class));
        cards.add(new SetCardInfo("Tragic Banshee", 73, Rarity.UNCOMMON, mage.cards.t.TragicBanshee.class));
        cards.add(new SetCardInfo("Tranquil Cove", 270, Rarity.COMMON, mage.cards.t.TranquilCove.class));
        cards.add(new SetCardInfo("Treetop Snarespinner", 114, Rarity.COMMON, mage.cards.t.TreetopSnarespinner.class));
        cards.add(new SetCardInfo("Tribute to Hunger", 614, Rarity.UNCOMMON, mage.cards.t.TributeToHunger.class));
        cards.add(new SetCardInfo("Trygon Predator", 667, Rarity.UNCOMMON, mage.cards.t.TrygonPredator.class));
        cards.add(new SetCardInfo("Twinblade Blessing", 26, Rarity.UNCOMMON, mage.cards.t.TwinbladeBlessing.class));
        cards.add(new SetCardInfo("Twinblade Paladin", 503, Rarity.UNCOMMON, mage.cards.t.TwinbladePaladin.class));
        cards.add(new SetCardInfo("Twinflame Tyrant", 97, Rarity.MYTHIC, mage.cards.t.TwinflameTyrant.class));
        cards.add(new SetCardInfo("Uncharted Haven", 564, Rarity.COMMON, mage.cards.u.UnchartedHaven.class));
        cards.add(new SetCardInfo("Uncharted Voyage", 53, Rarity.COMMON, mage.cards.u.UnchartedVoyage.class));
        cards.add(new SetCardInfo("Undying Malice", 528, Rarity.COMMON, mage.cards.u.UndyingMalice.class));
        cards.add(new SetCardInfo("Unflinching Courage", 722, Rarity.UNCOMMON, mage.cards.u.UnflinchingCourage.class));
        cards.add(new SetCardInfo("Unsummon", 599, Rarity.COMMON, mage.cards.u.Unsummon.class));
        cards.add(new SetCardInfo("Untamed Hunger", 529, Rarity.COMMON, mage.cards.u.UntamedHunger.class));
        cards.add(new SetCardInfo("Valkyrie's Call", 27, Rarity.MYTHIC, mage.cards.v.ValkyriesCall.class));
        cards.add(new SetCardInfo("Valorous Stance", 583, Rarity.UNCOMMON, mage.cards.v.ValorousStance.class));
        cards.add(new SetCardInfo("Vampire Gourmand", 74, Rarity.UNCOMMON, mage.cards.v.VampireGourmand.class));
        cards.add(new SetCardInfo("Vampire Interloper", 530, Rarity.COMMON, mage.cards.v.VampireInterloper.class));
        cards.add(new SetCardInfo("Vampire Neonate", 531, Rarity.COMMON, mage.cards.v.VampireNeonate.class));
        cards.add(new SetCardInfo("Vampire Nighthawk", 186, Rarity.UNCOMMON, mage.cards.v.VampireNighthawk.class));
        cards.add(new SetCardInfo("Vampire Soulcaller", 75, Rarity.COMMON, mage.cards.v.VampireSoulcaller.class));
        cards.add(new SetCardInfo("Vampire Spawn", 532, Rarity.COMMON, mage.cards.v.VampireSpawn.class));
        cards.add(new SetCardInfo("Vampiric Rites", 615, Rarity.UNCOMMON, mage.cards.v.VampiricRites.class));
        cards.add(new SetCardInfo("Vanguard Seraph", 28, Rarity.COMMON, mage.cards.v.VanguardSeraph.class));
        cards.add(new SetCardInfo("Vengeful Bloodwitch", 76, Rarity.UNCOMMON, mage.cards.v.VengefulBloodwitch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vengeful Bloodwitch", 325, Rarity.UNCOMMON, mage.cards.v.VengefulBloodwitch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Venom Connoisseur", 648, Rarity.UNCOMMON, mage.cards.v.VenomConnoisseur.class));
        cards.add(new SetCardInfo("Viashino Pyromancer", 634, Rarity.COMMON, mage.cards.v.ViashinoPyromancer.class));
        cards.add(new SetCardInfo("Vile Entomber", 616, Rarity.UNCOMMON, mage.cards.v.VileEntomber.class));
        cards.add(new SetCardInfo("Vivien Reid", 234, Rarity.MYTHIC, mage.cards.v.VivienReid.class));
        cards.add(new SetCardInfo("Vizier of the Menagerie", 649, Rarity.MYTHIC, mage.cards.v.VizierOfTheMenagerie.class));
        cards.add(new SetCardInfo("Volley Veteran", 550, Rarity.UNCOMMON, mage.cards.v.VolleyVeteran.class));
        cards.add(new SetCardInfo("Voracious Greatshark", 600, Rarity.RARE, mage.cards.v.VoraciousGreatshark.class));
        cards.add(new SetCardInfo("Wardens of the Cycle", 125, Rarity.UNCOMMON, mage.cards.w.WardensOfTheCycle.class));
        cards.add(new SetCardInfo("Wary Thespian", 235, Rarity.COMMON, mage.cards.w.WaryThespian.class));
        cards.add(new SetCardInfo("Wildborn Preserver", 650, Rarity.RARE, mage.cards.w.WildbornPreserver.class));
        cards.add(new SetCardInfo("Wildheart Invoker", 561, Rarity.COMMON, mage.cards.w.WildheartInvoker.class));
        cards.add(new SetCardInfo("Wildwood Scourge", 236, Rarity.UNCOMMON, mage.cards.w.WildwoodScourge.class));
        cards.add(new SetCardInfo("Wilt-Leaf Liege", 668, Rarity.RARE, mage.cards.w.WiltLeafLiege.class));
        cards.add(new SetCardInfo("Wind-Scarred Crag", 271, Rarity.COMMON, mage.cards.w.WindScarredCrag.class));
        cards.add(new SetCardInfo("Wishclaw Talisman", 617, Rarity.RARE, mage.cards.w.WishclawTalisman.class));
        cards.add(new SetCardInfo("Witness Protection", 168, Rarity.COMMON, mage.cards.w.WitnessProtection.class));
        cards.add(new SetCardInfo("Youthful Valkyrie", 149, Rarity.UNCOMMON, mage.cards.y.YouthfulValkyrie.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Youthful Valkyrie", 303, Rarity.UNCOMMON, mage.cards.y.YouthfulValkyrie.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zetalpa, Primal Dawn", 584, Rarity.RARE, mage.cards.z.ZetalpaPrimalDawn.class));
        cards.add(new SetCardInfo("Zimone, Paradox Sculptor", 126, Rarity.MYTHIC, mage.cards.z.ZimoneParadoxSculptor.class));
        cards.add(new SetCardInfo("Zombify", 187, Rarity.UNCOMMON, mage.cards.z.Zombify.class));
        cards.add(new SetCardInfo("Zul Ashur, Lich Lord", 77, Rarity.RARE, mage.cards.z.ZulAshurLichLord.class));
    }

    @Override
    protected void generateBoosterMap() {
        super.generateBoosterMap();
        
        CardInfo cardInfo;
        for( int cn = 19 ; cn < 29 ; cn++ ){
            cardInfo = CardRepository.instance.findCard("SPG", "" + cn);
            if( cardInfo != null ){
                inBoosterMap.put("SPG_" + cn, cardInfo);
            }else{
                throw new IllegalArgumentException("Card not found: " + "SPG_" + cn);
            }
        }
        String[] lstCards = {"SPG_74", "SPG_75", "SPG_76", "SPG_77", "SPG_78", "SPG_79", "SPG_80", "SPG_81", "SPG_82", "SPG_83"};
        for( String itm : lstCards ){
            int i = itm.indexOf("_");
            cardInfo = CardRepository.instance.findCard(itm.substring(0,i), itm.substring(i+1));
            if( cardInfo != null ){
                inBoosterMap.put(itm, cardInfo);
            }else{
                throw new IllegalArgumentException("Card not found: " + itm);
            }
        }
    }

    @Override
    public BoosterCollator createCollator() {
        return new FoundationsCollator();
    }
}

// Booster collation info from https://vm1.substation33.com/tiera/t/lethe/fdn.html
// Using Japanese collation plus other info inferred from various sources
class FoundationsCollator implements BoosterCollator {

    private final CardRun commonA = new CardRun(false, "13", "18", "20", "21", "24", "28");
    private final CardRun commonB = new CardRun(true, "95", "170", "262", "208", "59", "196", "90", "169", "252", "192", "62", "143", "87", "64", "15", "83", "75", "139", "189", "178", "3", "196", "177", "209", "68", "252", "195", "172", "130", "95", "174", "150", "83", "60", "210", "208", "178", "89", "64", "253", "200", "70", "262", "90", "59", "150", "189", "169", "170", "203", "181", "143", "210", "62", "15", "87", "174", "89", "60", "3", "209", "75", "172", "90", "181", "139", "196", "62", "253", "203", "177", "195", "170", "208", "200", "64", "143", "192", "70", "252", "83", "59", "130", "209", "174", "253", "189", "172", "262", "95", "68", "150", "192", "178", "200", "169", "15", "210", "75", "130", "195", "60", "139", "87", "177", "3", "89", "68", "203", "70", "181");
    private final CardRun commonC = new CardRun(true, "142", "53", "224", "12", "162", "225", "71", "42", "100", "16", "52", "235", "36", "103", "214", "167", "218", "251", "31", "225", "9", "155", "212", "82", "36", "114", "71", "52", "99", "227", "48", "101", "12", "157", "223", "159", "98", "138", "37", "212", "251", "155", "227", "218", "162", "215", "168", "167", "224", "165", "225", "82", "31", "100", "142", "37", "99", "71", "53", "215", "16", "168", "218", "9", "157", "235", "138", "48", "212", "98", "36", "214", "52", "223", "142", "155", "100", "165", "42", "103", "9", "162", "114", "16", "159", "224", "31", "214", "251", "167", "103", "12", "37", "101", "82", "165", "98", "227", "157", "114", "138", "53", "223", "168", "42", "101", "235", "159", "215", "48", "99");
    private final CardRun uncommonA = new CardRun(false, "135", "136", "237", "250", "4", "6", "8", "10", "118", "239", "120", "128", "240", "14", "19", "255", "256", "144", "124", "131", "145", "146", "147", "148", "25", "125", "149");
    private final CardRun uncommonB = new CardRun(true, "26", "80", "171", "202", "69", "43", "79", "185", "213", "65", "267", "187", "33", "173", "32", "249", "151", "205", "74", "49", "182", "264", "231", "160", "197", "152", "254", "158", "102", "163", "79", "156", "233", "171", "226", "185", "236", "26", "69", "267", "80", "258", "220", "202", "187", "199", "43", "182", "160", "231", "254", "74", "205", "33", "173", "197", "49", "220", "249", "236", "65", "151", "213", "158", "102", "156", "264", "32", "171", "202", "43", "185", "79", "152", "26", "233", "258", "187", "160", "69", "205", "163", "226", "74", "249", "80", "267", "33", "199", "231", "254", "158", "182", "213", "65", "236", "264", "197", "49", "102", "151", "173", "156", "226", "32", "258", "152", "233", "163", "199", "220");
    private final CardRun uncommonC = new CardRun(true, "63", "241", "217", "73", "164", "78", "175", "86", "56", "105", "188", "39", "110", "94", "108", "201", "55", "206", "229", "38", "88", "47", "104", "245", "228", "76", "230", "96", "67", "50", "84", "186", "211", "29", "247", "63", "153", "217", "175", "241", "164", "191", "39", "94", "73", "86", "55", "188", "56", "110", "206", "104", "201", "245", "105", "78", "108", "47", "229", "76", "38", "228", "50", "96", "29", "63", "88", "230", "153", "211", "73", "247", "217", "67", "84", "164", "175", "94", "241", "186", "108", "245", "191", "55", "105", "86", "39", "78", "110", "188", "228", "88", "104", "96", "56", "201", "47", "206", "67", "153", "191", "50", "229", "29", "230", "38", "186", "247", "84", "76", "211");
    // ToDo: Implement borderless printings for rares and mythics
    private final CardRun rare = new CardRun(false, "134", "58", "81", "216", "17", "44", "176", "243", "123", "161", "244", "111", "183", "92", "1", "51", "97", "27", "234", "126", "54", "54", "115", "115", "116", "116", "2", "2", "30", "30", "117", "117", "137", "137", "127", "127", "57", "57", "190", "190", "5", "5", "238", "238", "7", "7", "34", "34", "140", "140", "35", "35", "193", "193", "85", "85", "119", "119", "219", "219", "194", "194", "11", "11", "154", "154", "198", "198", "221", "221", "222", "222", "141", "141", "40", "40", "61", "61", "41", "41", "91", "91", "45", "45", "121", "121", "204", "204", "122", "122", "242", "242", "129", "129", "106", "106", "46", "46", "107", "107", "66", "66", "179", "179", "180", "180", "322", "322", "109", "109", "22", "22", "184", "184", "232", "232", "132", "132", "93", "93", "23", "23", "207", "207", "257", "257", "729", "729", "133", "133", "112", "112", "246", "246", "113", "113", "248", "248", "166", "166", "72", "72", "77", "77");
    private final CardRun borderlessCU = new CardRun(false, "327", "293", "311", "319", "340", "355", "325", "303", "313", "313", "313", "315", "315", "315");
    private final CardRun landBasic = new CardRun(false, "272", "273", "274", "275", "276", "277", "278", "279", "280", "281", "282", "283", "284", "285", "286", "287", "288", "289", "290", "291");
    private final CardRun landDual = new CardRun(false, "259", "260", "261", "263", "265", "266", "268", "269", "270", "271");
    private final CardRun listGuest = new CardRun(false, "SPG_74", "SPG_75", "SPG_76", "SPG_77", "SPG_78", "SPG_79", "SPG_80", "SPG_81", "SPG_82", "SPG_83");

    // because a foil card is independant from sorted runs, repeated as unsorted runs
    private final CardRun commonFoilB = new CardRun(false, "95", "170", "262", "208", "59", "196", "90", "169", "252", "192", "62", "143", "87", "64", "15", "83", "75", "139", "189", "178", "3", "196", "177", "209", "68", "252", "195", "172", "130", "95", "174", "150", "83", "60", "210", "208", "178", "89", "64", "253", "200", "70", "262", "90", "59", "150", "189", "169", "170", "203", "181", "143", "210", "62", "15", "87", "174", "89", "60", "3", "209", "75", "172", "90", "181", "139", "196", "62", "253", "203", "177", "195", "170", "208", "200", "64", "143", "192", "70", "252", "83", "59", "130", "209", "174", "253", "189", "172", "262", "95", "68", "150", "192", "178", "200", "169", "15", "210", "75", "130", "195", "60", "139", "87", "177", "3", "89", "68", "203", "70", "181");
    private final CardRun commonFoilC = new CardRun(false, "142", "53", "224", "12", "162", "225", "71", "42", "100", "16", "52", "235", "36", "103", "214", "167", "218", "251", "31", "225", "9", "155", "212", "82", "36", "114", "71", "52", "99", "227", "48", "101", "12", "157", "223", "159", "98", "138", "37", "212", "251", "155", "227", "218", "162", "215", "168", "167", "224", "165", "225", "82", "31", "100", "142", "37", "99", "71", "53", "215", "16", "168", "218", "9", "157", "235", "138", "48", "212", "98", "36", "214", "52", "223", "142", "155", "100", "165", "42", "103", "9", "162", "114", "16", "159", "224", "31", "214", "251", "167", "103", "12", "37", "101", "82", "165", "98", "227", "157", "114", "138", "53", "223", "168", "42", "101", "235", "159", "215", "48", "99");
    private final CardRun uncommonFoilB = new CardRun(false, "26", "80", "171", "202", "69", "43", "79", "185", "213", "65", "267", "187", "33", "173", "32", "249", "151", "205", "74", "49", "182", "264", "231", "160", "197", "152", "254", "158", "102", "163", "79", "156", "233", "171", "226", "185", "236", "26", "69", "267", "80", "258", "220", "202", "187", "199", "43", "182", "160", "231", "254", "74", "205", "33", "173", "197", "49", "220", "249", "236", "65", "151", "213", "158", "102", "156", "264", "32", "171", "202", "43", "185", "79", "152", "26", "233", "258", "187", "160", "69", "205", "163", "226", "74", "249", "80", "267", "33", "199", "231", "254", "158", "182", "213", "65", "236", "264", "197", "49", "102", "151", "173", "156", "226", "32", "258", "152", "233", "163", "199", "220");
    private final CardRun uncommonFoilC = new CardRun(false, "63", "241", "217", "73", "164", "78", "175", "86", "56", "105", "188", "39", "110", "94", "108", "201", "55", "206", "229", "38", "88", "47", "104", "245", "228", "76", "230", "96", "67", "50", "84", "186", "211", "29", "247", "63", "153", "217", "175", "241", "164", "191", "39", "94", "73", "86", "55", "188", "56", "110", "206", "104", "201", "245", "105", "78", "108", "47", "229", "76", "38", "228", "50", "96", "29", "63", "88", "230", "153", "211", "73", "247", "217", "67", "84", "164", "175", "94", "241", "186", "108", "245", "191", "55", "105", "86", "39", "78", "110", "188", "228", "88", "104", "96", "56", "201", "47", "206", "67", "153", "191", "50", "229", "29", "230", "38", "186", "247", "84", "76", "211");

    private final BoosterStructure BBBCCC = new BoosterStructure(
            commonB, commonB, commonB,
            commonC, commonC, commonC
    );
    private final BoosterStructure BBBCCCC = new BoosterStructure(
            commonB, commonB, commonB,
            commonC, commonC, commonC, commonC
    );
    private final BoosterStructure BBBBCCC = new BoosterStructure(
            commonB, commonB, commonB, commonB,
            commonC, commonC, commonC
    );
    private final BoosterStructure ABBBCCC = new BoosterStructure(
            commonA,
            commonB, commonB, commonB,
            commonC, commonC, commonC
    );
    private final BoosterStructure ABBBCCCC = new BoosterStructure(
            commonA,
            commonB, commonB, commonB,
            commonC, commonC, commonC, commonC
    );
    private final BoosterStructure ABBBBCCC = new BoosterStructure(
            commonA,
            commonB, commonB, commonB, commonB,
            commonC, commonC, commonC
    );
    private final BoosterStructure BBBBCCCC = new BoosterStructure(
            commonB, commonB, commonB, commonB,
            commonC, commonC, commonC, commonC
    );

    private final BoosterStructure ABC = new BoosterStructure(
            uncommonA,
            uncommonB,
            uncommonC
    );
    private final BoosterStructure BBC = new BoosterStructure(
            uncommonB, uncommonB,
            uncommonC
    );
    private final BoosterStructure BCC = new BoosterStructure(
            uncommonB,
            uncommonC, uncommonC
    );
    private final BoosterStructure ABCC = new BoosterStructure(
            uncommonA,
            uncommonB,
            uncommonC, uncommonC
    );
    private final BoosterStructure ABBC = new BoosterStructure(
            uncommonA,
            uncommonB, uncommonB,
            uncommonC
    );

    private final BoosterStructure R1 = new BoosterStructure(rare);
    private final BoosterStructure R2 = new BoosterStructure(rare,rare);
    private final BoosterStructure Rb = new BoosterStructure(rare,borderlessCU);
    private final BoosterStructure Lb = new BoosterStructure(landBasic);
    private final BoosterStructure Ld = new BoosterStructure(landDual);
    private final BoosterStructure Sg = new BoosterStructure(listGuest);
    private final BoosterStructure fcA = new BoosterStructure(commonA);
    private final BoosterStructure fcB = new BoosterStructure(commonFoilB);
    private final BoosterStructure fcC = new BoosterStructure(commonFoilC);
    private final BoosterStructure fuA = new BoosterStructure(uncommonA);
    private final BoosterStructure fuB = new BoosterStructure(uncommonFoilB);
    private final BoosterStructure fuC = new BoosterStructure(uncommonFoilC);
    private final BoosterStructure fr = R1;
    private final BoosterStructure fb = new BoosterStructure(borderlessCU);

    // 1/6 packs contain an 8th common, 1.5% of packs have a common replaced with a special guest card
    // In order for equal numbers of each common to exist, the average booster must contain:
    // 0.54 A commons ( 25746 / 48000)       6 Commons ( 15 / 1200) or (  600 / 48000)
    // 3.31 B commons (158767 / 48000)       7 Commons (988 / 1200) or (39520 / 48000)
    // 3.31 C commons (158767 / 48000)       8 Commons (197 / 1200) or ( 7880 / 48000)
    private final RarityConfiguration commonRuns6 = new RarityConfiguration(BBBCCC);
    private final RarityConfiguration commonRuns7 = new RarityConfiguration(
        BBBBCCC, BBBBCCC,
        BBBCCCC, BBBCCCC,
        ABBBCCC, ABBBCCC, ABBBCCC, ABBBCCC, ABBBCCC
    );
    // If commonRuns8 were combined would need 17057x ABBBBCCC, 17057x ABBBCCCC, 36806x BBBBCCCC
    private final RarityConfiguration commonRuns8A = new RarityConfiguration(ABBBBCCC, ABBBCCCC);
    private final RarityConfiguration commonRuns8BC = new RarityConfiguration(BBBBCCCC);
    private static final RarityConfiguration commonRuns(int runLength){
        return ( 6< runLength ? 7< runLength ? RandomUtil.nextInt(35460) <17057 ?
            commonRuns8A : commonRuns8BC : commonRuns7 : commonRuns6 );
    }

    // 7/12 packs contain an extra uncommon
    // In order for equal numbers of each uncommon to exist, the average booster must contain:
    // 0.96 A uncommons (1161 / 1212)       3 Uncommons (5 / 12) or (505 / 1212)
    // 1.31 B uncommons (1591 / 1212)       4 Uncommons (7 / 12) or (707 / 1212)
    // 1.31 C uncommons (1591 / 1212)       
    // If commonRuns3 were combined would need 908 ABC, 51x BBC, 51x BCC
    private final RarityConfiguration uncommonRuns3A = new RarityConfiguration(ABC);
    private final RarityConfiguration uncommonRuns3BC = new RarityConfiguration(BBC, BCC);
    private final RarityConfiguration uncommonRuns4 = new RarityConfiguration(ABBC, ABCC);
    private static final RarityConfiguration uncommonRuns(int runLength){
        return ( 4> runLength ? RandomUtil.nextInt(505) <454 ?
            uncommonRuns3A : commonRuns3BC : uncommonRuns4 );
    }

    // 5/24 packs contain a second rare, 1/24 packs contain a borderless common/uncommon
    private final RarityConfiguration rareRuns1 = new RarityConfiguration(R1);
    private final RarityConfiguration rareRuns2 = new RarityConfiguration(R2, R2, R2, R2, R2, Rb);
    private static final RarityConfiguration rareRuns(int runLength){
        return ( 1< runLength ? rareRuns2 : rareRuns1 );
    }

    // 50% of packs contain a common dual land
    private final RarityConfiguration landRuns = new RarityConfiguration(Lb, Ld);

    // 1.5% of packs contain a special guest card
    private final RarityConfiguration listRuns = new RarityConfiguration(Sg);

    private final RarityConfiguration foilCommonRuns = new RarityConfiguration(
        fcA, fcA, fcA, fcA, fcA, fcA,
        fcB, fcB, fcB, fcB, fcB, fcB, fcB, fcB, fcB, fcB,
        fcB, fcB, fcB, fcB, fcB, fcB, fcB, fcB, fcB, fcB,
        fcB, fcB, fcB, fcB, fcB, fcB, fcB, fcB, fcB, fcB,
        fcB, fcB, fcB, fcB, fcB, fcB, fcB,
        fcC, fcC, fcC, fcC, fcC, fcC, fcC, fcC, fcC, fcC,
        fcC, fcC, fcC, fcC, fcC, fcC, fcC, fcC, fcC, fcC,
        fcC, fcC, fcC, fcC, fcC, fcC, fcC, fcC, fcC, fcC,
        fcC, fcC, fcC, fcC, fcC, fcC, fcC
    );

    private final RarityConfiguration foilUncommonRuns = new RarityConfiguration(
        fuA, fuA, fuA, fuA, fuA, fuA, fuA, fuA, fuA, fuA,
        fuA, fuA, fuA, fuA, fuA, fuA, fuA, fuA, fuA, fuA,
        fuA, fuA, fuA, fuA, fuA, fuA, fuA,
        fuB, fuB, fuB, fuB, fuB, fuB, fuB, fuB, fuB, fuB,
        fuB, fuB, fuB, fuB, fuB, fuB, fuB, fuB, fuB, fuB,
        fuB, fuB, fuB, fuB, fuB, fuB, fuB, fuB, fuB, fuB,
        fuB, fuB, fuB, fuB, fuB, fuB, fuB,
        fuC, fuC, fuC, fuC, fuC, fuC, fuC, fuC, fuC, fuC,
        fuC, fuC, fuC, fuC, fuC, fuC, fuC, fuC, fuC, fuC,
        fuC, fuC, fuC, fuC, fuC, fuC, fuC, fuC, fuC, fuC,
        fuC, fuC, fuC, fuC, fuC, fuC, fuC
    );

    private final RarityConfiguration foilRareRuns = new RarityConfiguration(fr);
    private final RarityConfiguration foilBorderlessRuns = new RarityConfiguration(fb);

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        // 1-2 rares, 3-4 uncommons, and 6-8 commons
        // wildcard 1/6 common, 7/12 uncommon, 1/4 rare (incl borderless c/u)
        int wildNum = RandomUtil.nextInt(12);

        int numCommon = 7;
        boolean wildRare = false;
        boolean wildUncommon = false;
        if( wildNum < 2 ){
            numCommon++;
        }else if( wildNum < 9 ){
            wildUncommon = true;
        }else{
            wildRare = true;
        };

        booster.addAll(landRuns.getNext().makeRun());

        // foil wildcard rarity distribution not specified, so derived from observed pack openings
        // 180 packs, 101 common, 60 uncommon, 15 rare/mythic, 4 borderless common/uncommon
        wildNum = RandomUtil.nextInt(180);
        if( wildNum < 101 ){
            booster.addAll(foilCommonRuns.getNext().makeRun());
        }else if( wildNum < 161 ){
            booster.addAll(foilUncommonRuns.getNext().makeRun());
        }else if( wildNum < 176 ){
            booster.addAll(foilRareRuns.getNext().makeRun());
        }else{
            booster.addAll(foilBorderlessRuns.getNext().makeRun());
        };

        booster.addAll(rareRuns( wildRare ? 2 : 1 ).getNext().makeRun());

        // 1.5% of  Play Boosters features a Special Guests card displacing a common card.
        if (RandomUtil.nextInt(200) <3) {
            booster.addAll(listRuns.getNext().makeRun());
            --numCommon;
        }

        booster.addAll(uncommonRuns( wildUncommon ? 4 : 3 ).getNext().makeRun());
        booster.addAll(commonRuns( numCommon ).getNext().makeRun());

        return booster;
    }
}
