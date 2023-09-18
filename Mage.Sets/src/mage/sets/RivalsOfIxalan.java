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
 * @author fireshoes
 */
public final class RivalsOfIxalan extends ExpansionSet {

    private static final RivalsOfIxalan instance = new RivalsOfIxalan();

    public static RivalsOfIxalan getInstance() {
        return instance;
    }

    private RivalsOfIxalan() {
        super("Rivals of Ixalan", "RIX", ExpansionSet.buildDate(2018, 1, 19), SetType.EXPANSION);
        this.blockName = "Ixalan";
        this.parentSet = Ixalan.getInstance();
        this.hasBoosters = true;
        this.hasBasicLands = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.numBoosterDoubleFaced = -1;
        this.maxCardNumberInBooster = 196;

        cards.add(new SetCardInfo("Admiral's Order", 31, Rarity.RARE, mage.cards.a.AdmiralsOrder.class));
        cards.add(new SetCardInfo("Aggressive Urge", 122, Rarity.COMMON, mage.cards.a.AggressiveUrge.class));
        cards.add(new SetCardInfo("Angrath, Minotaur Pirate", 201, Rarity.MYTHIC, mage.cards.a.AngrathMinotaurPirate.class));
        cards.add(new SetCardInfo("Angrath's Ambusher", 202, Rarity.UNCOMMON, mage.cards.a.AngrathsAmbusher.class));
        cards.add(new SetCardInfo("Angrath's Fury", 204, Rarity.RARE, mage.cards.a.AngrathsFury.class));
        cards.add(new SetCardInfo("Angrath, the Flame-Chained", 152, Rarity.MYTHIC, mage.cards.a.AngrathTheFlameChained.class));
        cards.add(new SetCardInfo("Aquatic Incursion", 32, Rarity.UNCOMMON, mage.cards.a.AquaticIncursion.class));
        cards.add(new SetCardInfo("Arch of Orazca", 185, Rarity.RARE, mage.cards.a.ArchOfOrazca.class));
        cards.add(new SetCardInfo("Arterial Flow", 62, Rarity.UNCOMMON, mage.cards.a.ArterialFlow.class));
        cards.add(new SetCardInfo("Atzal, Cave of Eternity", 160, Rarity.RARE, mage.cards.a.AtzalCaveOfEternity.class));
        cards.add(new SetCardInfo("Atzocan Seer", 153, Rarity.UNCOMMON, mage.cards.a.AtzocanSeer.class));
        cards.add(new SetCardInfo("Awakened Amalgam", 175, Rarity.RARE, mage.cards.a.AwakenedAmalgam.class));
        cards.add(new SetCardInfo("Azor's Gateway", 176, Rarity.MYTHIC, mage.cards.a.AzorsGateway.class));
        cards.add(new SetCardInfo("Azor, the Lawbringer", 154, Rarity.MYTHIC, mage.cards.a.AzorTheLawbringer.class));
        cards.add(new SetCardInfo("Baffling End", 1, Rarity.UNCOMMON, mage.cards.b.BafflingEnd.class));
        cards.add(new SetCardInfo("Bishop of Binding", 2, Rarity.RARE, mage.cards.b.BishopOfBinding.class));
        cards.add(new SetCardInfo("Blazing Hope", 3, Rarity.UNCOMMON, mage.cards.b.BlazingHope.class));
        cards.add(new SetCardInfo("Blood Sun", 92, Rarity.RARE, mage.cards.b.BloodSun.class));
        cards.add(new SetCardInfo("Bombard", 93, Rarity.COMMON, mage.cards.b.Bombard.class));
        cards.add(new SetCardInfo("Brass's Bounty", 94, Rarity.RARE, mage.cards.b.BrasssBounty.class));
        cards.add(new SetCardInfo("Brazen Freebooter", 95, Rarity.COMMON, mage.cards.b.BrazenFreebooter.class));
        cards.add(new SetCardInfo("Buccaneer's Bravado", 96, Rarity.COMMON, mage.cards.b.BuccaneersBravado.class));
        cards.add(new SetCardInfo("Cacophodon", 123, Rarity.UNCOMMON, mage.cards.c.Cacophodon.class));
        cards.add(new SetCardInfo("Canal Monitor", 63, Rarity.COMMON, mage.cards.c.CanalMonitor.class));
        cards.add(new SetCardInfo("Captain's Hook", 177, Rarity.RARE, mage.cards.c.CaptainsHook.class));
        cards.add(new SetCardInfo("Champion of Dusk", 64, Rarity.RARE, mage.cards.c.ChampionOfDusk.class));
        cards.add(new SetCardInfo("Charging Tuskodon", 97, Rarity.UNCOMMON, mage.cards.c.ChargingTuskodon.class));
        cards.add(new SetCardInfo("Cherished Hatchling", 124, Rarity.UNCOMMON, mage.cards.c.CherishedHatchling.class));
        cards.add(new SetCardInfo("Cinder Barrens", 205, Rarity.COMMON, mage.cards.c.CinderBarrens.class));
        cards.add(new SetCardInfo("Cleansing Ray", 4, Rarity.COMMON, mage.cards.c.CleansingRay.class));
        cards.add(new SetCardInfo("Colossal Dreadmaw", 125, Rarity.COMMON, mage.cards.c.ColossalDreadmaw.class));
        cards.add(new SetCardInfo("Crafty Cutpurse", 33, Rarity.RARE, mage.cards.c.CraftyCutpurse.class));
        cards.add(new SetCardInfo("Crashing Tide", 34, Rarity.COMMON, mage.cards.c.CrashingTide.class));
        cards.add(new SetCardInfo("Crested Herdcaller", 126, Rarity.UNCOMMON, mage.cards.c.CrestedHerdcaller.class));
        cards.add(new SetCardInfo("Curious Obsession", 35, Rarity.UNCOMMON, mage.cards.c.CuriousObsession.class));
        cards.add(new SetCardInfo("Daring Buccaneer", 98, Rarity.UNCOMMON, mage.cards.d.DaringBuccaneer.class));
        cards.add(new SetCardInfo("Dark Inquiry", 65, Rarity.COMMON, mage.cards.d.DarkInquiry.class));
        cards.add(new SetCardInfo("Dead Man's Chest", 66, Rarity.RARE, mage.cards.d.DeadMansChest.class));
        cards.add(new SetCardInfo("Deadeye Brawler", 155, Rarity.UNCOMMON, mage.cards.d.DeadeyeBrawler.class));
        cards.add(new SetCardInfo("Deadeye Rig-Hauler", 36, Rarity.COMMON, mage.cards.d.DeadeyeRigHauler.class));
        cards.add(new SetCardInfo("Deeproot Elite", 127, Rarity.RARE, mage.cards.d.DeeprootElite.class));
        cards.add(new SetCardInfo("Dinosaur Hunter", 67, Rarity.COMMON, mage.cards.d.DinosaurHunter.class));
        cards.add(new SetCardInfo("Dire Fleet Daredevil", 99, Rarity.RARE, mage.cards.d.DireFleetDaredevil.class));
        cards.add(new SetCardInfo("Dire Fleet Neckbreaker", 156, Rarity.UNCOMMON, mage.cards.d.DireFleetNeckbreaker.class));
        cards.add(new SetCardInfo("Dire Fleet Poisoner", 68, Rarity.RARE, mage.cards.d.DireFleetPoisoner.class));
        cards.add(new SetCardInfo("Divine Verdict", 5, Rarity.COMMON, mage.cards.d.DivineVerdict.class));
        cards.add(new SetCardInfo("Dusk Charger", 69, Rarity.COMMON, mage.cards.d.DuskCharger.class));
        cards.add(new SetCardInfo("Dusk Legion Zealot", 70, Rarity.COMMON, mage.cards.d.DuskLegionZealot.class));
        cards.add(new SetCardInfo("Elenda, the Dusk Rose", 157, Rarity.MYTHIC, mage.cards.e.ElendaTheDuskRose.class));
        cards.add(new SetCardInfo("Enter the Unknown", 128, Rarity.UNCOMMON, mage.cards.e.EnterTheUnknown.class));
        cards.add(new SetCardInfo("Etali, Primal Storm", 100, Rarity.RARE, mage.cards.e.EtaliPrimalStorm.class));
        cards.add(new SetCardInfo("Everdawn Champion", 6, Rarity.UNCOMMON, mage.cards.e.EverdawnChampion.class));
        cards.add(new SetCardInfo("Evolving Wilds", 186, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Expel from Orazca", 37, Rarity.UNCOMMON, mage.cards.e.ExpelFromOrazca.class));
        cards.add(new SetCardInfo("Exultant Skymarcher", 7, Rarity.COMMON, mage.cards.e.ExultantSkymarcher.class));
        cards.add(new SetCardInfo("Famished Paladin", 8, Rarity.UNCOMMON, mage.cards.f.FamishedPaladin.class));
        cards.add(new SetCardInfo("Fanatical Firebrand", 101, Rarity.COMMON, mage.cards.f.FanaticalFirebrand.class));
        cards.add(new SetCardInfo("Fathom Fleet Boarder", 71, Rarity.COMMON, mage.cards.f.FathomFleetBoarder.class));
        cards.add(new SetCardInfo("Flood of Recollection", 38, Rarity.UNCOMMON, mage.cards.f.FloodOfRecollection.class));
        cards.add(new SetCardInfo("Forerunner of the Coalition", 72, Rarity.UNCOMMON, mage.cards.f.ForerunnerOfTheCoalition.class));
        cards.add(new SetCardInfo("Forerunner of the Empire", 102, Rarity.UNCOMMON, mage.cards.f.ForerunnerOfTheEmpire.class));
        cards.add(new SetCardInfo("Forerunner of the Heralds", 129, Rarity.UNCOMMON, mage.cards.f.ForerunnerOfTheHeralds.class));
        cards.add(new SetCardInfo("Forerunner of the Legion", 9, Rarity.UNCOMMON, mage.cards.f.ForerunnerOfTheLegion.class));
        cards.add(new SetCardInfo("Forest", 196, Rarity.LAND, mage.cards.basiclands.Forest.class));
        cards.add(new SetCardInfo("Form of the Dinosaur", 103, Rarity.RARE, mage.cards.f.FormOfTheDinosaur.class));
        cards.add(new SetCardInfo("Forsaken Sanctuary", 187, Rarity.UNCOMMON, mage.cards.f.ForsakenSanctuary.class));
        cards.add(new SetCardInfo("Foul Orchard", 188, Rarity.UNCOMMON, mage.cards.f.FoulOrchard.class));
        cards.add(new SetCardInfo("Frilled Deathspitter", 104, Rarity.COMMON, mage.cards.f.FrilledDeathspitter.class));
        cards.add(new SetCardInfo("Ghalta, Primal Hunger", 130, Rarity.RARE, mage.cards.g.GhaltaPrimalHunger.class));
        cards.add(new SetCardInfo("Giltgrove Stalker", 131, Rarity.COMMON, mage.cards.g.GiltgroveStalker.class));
        cards.add(new SetCardInfo("Gleaming Barrier", 178, Rarity.COMMON, mage.cards.g.GleamingBarrier.class));
        cards.add(new SetCardInfo("Goblin Trailblazer", 105, Rarity.COMMON, mage.cards.g.GoblinTrailblazer.class));
        cards.add(new SetCardInfo("Golden Demise", 73, Rarity.UNCOMMON, mage.cards.g.GoldenDemise.class));
        cards.add(new SetCardInfo("Golden Guardian", 179, Rarity.RARE, mage.cards.g.GoldenGuardian.class));
        cards.add(new SetCardInfo("Gold-Forge Garrison", 179, Rarity.RARE, mage.cards.g.GoldForgeGarrison.class));
        cards.add(new SetCardInfo("Grasping Scoundrel", 74, Rarity.COMMON, mage.cards.g.GraspingScoundrel.class));
        cards.add(new SetCardInfo("Gruesome Fate", 75, Rarity.COMMON, mage.cards.g.GruesomeFate.class));
        cards.add(new SetCardInfo("Hadana's Climb", 158, Rarity.RARE, mage.cards.h.HadanasClimb.class));
        cards.add(new SetCardInfo("Hardy Veteran", 132, Rarity.COMMON, mage.cards.h.HardyVeteran.class));
        cards.add(new SetCardInfo("Highland Lake", 189, Rarity.UNCOMMON, mage.cards.h.HighlandLake.class));
        cards.add(new SetCardInfo("Hornswoggle", 39, Rarity.UNCOMMON, mage.cards.h.Hornswoggle.class));
        cards.add(new SetCardInfo("Huatli, Radiant Champion", 159, Rarity.MYTHIC, mage.cards.h.HuatliRadiantChampion.class));
        cards.add(new SetCardInfo("Hunt the Weak", 133, Rarity.COMMON, mage.cards.h.HuntTheWeak.class));
        cards.add(new SetCardInfo("Impale", 76, Rarity.COMMON, mage.cards.i.Impale.class));
        cards.add(new SetCardInfo("Imperial Ceratops", 10, Rarity.UNCOMMON, mage.cards.i.ImperialCeratops.class));
        cards.add(new SetCardInfo("Induced Amnesia", 40, Rarity.RARE, mage.cards.i.InducedAmnesia.class));
        cards.add(new SetCardInfo("Island", 193, Rarity.LAND, mage.cards.basiclands.Island.class));
        cards.add(new SetCardInfo("Jade Bearer", 134, Rarity.COMMON, mage.cards.j.JadeBearer.class));
        cards.add(new SetCardInfo("Jadecraft Artisan", 135, Rarity.COMMON, mage.cards.j.JadecraftArtisan.class));
        cards.add(new SetCardInfo("Jadelight Ranger", 136, Rarity.RARE, mage.cards.j.JadelightRanger.class));
        cards.add(new SetCardInfo("Journey to Eternity", 160, Rarity.RARE, mage.cards.j.JourneyToEternity.class));
        cards.add(new SetCardInfo("Jungle Creeper", 161, Rarity.UNCOMMON, mage.cards.j.JungleCreeper.class));
        cards.add(new SetCardInfo("Jungleborn Pioneer", 137, Rarity.COMMON, mage.cards.j.JunglebornPioneer.class));
        cards.add(new SetCardInfo("Kitesail Corsair", 41, Rarity.COMMON, mage.cards.k.KitesailCorsair.class));
        cards.add(new SetCardInfo("Knight of the Stampede", 138, Rarity.COMMON, mage.cards.k.KnightOfTheStampede.class));
        cards.add(new SetCardInfo("Kumena, Tyrant of Orazca", 162, Rarity.MYTHIC, mage.cards.k.KumenaTyrantOfOrazca.class));
        cards.add(new SetCardInfo("Kumena's Awakening", 42, Rarity.RARE, mage.cards.k.KumenasAwakening.class));
        cards.add(new SetCardInfo("Legion Conquistador", 11, Rarity.COMMON, mage.cards.l.LegionConquistador.class));
        cards.add(new SetCardInfo("Legion Lieutenant", 163, Rarity.UNCOMMON, mage.cards.l.LegionLieutenant.class));
        cards.add(new SetCardInfo("Luminous Bonds", 12, Rarity.COMMON, mage.cards.l.LuminousBonds.class));
        cards.add(new SetCardInfo("Majestic Heliopterus", 13, Rarity.UNCOMMON, mage.cards.m.MajesticHeliopterus.class));
        cards.add(new SetCardInfo("Martyr of Dusk", 14, Rarity.COMMON, mage.cards.m.MartyrOfDusk.class));
        cards.add(new SetCardInfo("Mastermind's Acquisition", 77, Rarity.RARE, mage.cards.m.MastermindsAcquisition.class));
        cards.add(new SetCardInfo("Mausoleum Harpy", 78, Rarity.UNCOMMON, mage.cards.m.MausoleumHarpy.class));
        cards.add(new SetCardInfo("Merfolk Mistbinder", 164, Rarity.UNCOMMON, mage.cards.m.MerfolkMistbinder.class));
        cards.add(new SetCardInfo("Metzali, Tower of Triumph", 165, Rarity.RARE, mage.cards.m.MetzaliTowerOfTriumph.class));
        cards.add(new SetCardInfo("Mist-Cloaked Herald", 43, Rarity.COMMON, mage.cards.m.MistCloakedHerald.class));
        cards.add(new SetCardInfo("Moment of Craving", 79, Rarity.COMMON, mage.cards.m.MomentOfCraving.class));
        cards.add(new SetCardInfo("Moment of Triumph", 15, Rarity.COMMON, mage.cards.m.MomentOfTriumph.class));
        cards.add(new SetCardInfo("Mountain", 195, Rarity.LAND, mage.cards.basiclands.Mountain.class));
        cards.add(new SetCardInfo("Mutiny", 106, Rarity.COMMON, mage.cards.m.Mutiny.class));
        cards.add(new SetCardInfo("Naturalize", 139, Rarity.COMMON, mage.cards.n.Naturalize.class));
        cards.add(new SetCardInfo("Needletooth Raptor", 107, Rarity.UNCOMMON, mage.cards.n.NeedletoothRaptor.class));
        cards.add(new SetCardInfo("Negate", 44, Rarity.COMMON, mage.cards.n.Negate.class));
        cards.add(new SetCardInfo("Nezahal, Primal Tide", 45, Rarity.RARE, mage.cards.n.NezahalPrimalTide.class));
        cards.add(new SetCardInfo("Oathsworn Vampire", 80, Rarity.UNCOMMON, mage.cards.o.OathswornVampire.class));
        cards.add(new SetCardInfo("Orazca Frillback", 140, Rarity.COMMON, mage.cards.o.OrazcaFrillback.class));
        cards.add(new SetCardInfo("Orazca Raptor", 108, Rarity.COMMON, mage.cards.o.OrazcaRaptor.class));
        cards.add(new SetCardInfo("Orazca Relic", 181, Rarity.COMMON, mage.cards.o.OrazcaRelic.class));
        cards.add(new SetCardInfo("Overgrown Armasaur", 141, Rarity.COMMON, mage.cards.o.OvergrownArmasaur.class));
        cards.add(new SetCardInfo("Paladin of Atonement", 16, Rarity.RARE, mage.cards.p.PaladinOfAtonement.class));
        cards.add(new SetCardInfo("Path of Discovery", 142, Rarity.RARE, mage.cards.p.PathOfDiscovery.class));
        cards.add(new SetCardInfo("Path of Mettle", 165, Rarity.RARE, mage.cards.p.PathOfMettle.class));
        cards.add(new SetCardInfo("Pirate's Pillage", 109, Rarity.UNCOMMON, mage.cards.p.PiratesPillage.class));
        cards.add(new SetCardInfo("Pitiless Plunderer", 81, Rarity.UNCOMMON, mage.cards.p.PitilessPlunderer.class));
        cards.add(new SetCardInfo("Plains", 192, Rarity.LAND, mage.cards.basiclands.Plains.class));
        cards.add(new SetCardInfo("Plummet", 143, Rarity.COMMON, mage.cards.p.Plummet.class));
        cards.add(new SetCardInfo("Polyraptor", 144, Rarity.MYTHIC, mage.cards.p.Polyraptor.class));
        cards.add(new SetCardInfo("Pride of Conquerors", 17, Rarity.UNCOMMON, mage.cards.p.PrideOfConquerors.class));
        cards.add(new SetCardInfo("Profane Procession", 166, Rarity.RARE, mage.cards.p.ProfaneProcession.class));
        cards.add(new SetCardInfo("Protean Raider", 167, Rarity.RARE, mage.cards.p.ProteanRaider.class));
        cards.add(new SetCardInfo("Radiant Destiny", 18, Rarity.RARE, mage.cards.r.RadiantDestiny.class));
        cards.add(new SetCardInfo("Raging Regisaur", 168, Rarity.UNCOMMON, mage.cards.r.RagingRegisaur.class));
        cards.add(new SetCardInfo("Raptor Companion", 19, Rarity.COMMON, mage.cards.r.RaptorCompanion.class));
        cards.add(new SetCardInfo("Ravenous Chupacabra", 82, Rarity.UNCOMMON, mage.cards.r.RavenousChupacabra.class));
        cards.add(new SetCardInfo("Reaver Ambush", 83, Rarity.UNCOMMON, mage.cards.r.ReaverAmbush.class));
        cards.add(new SetCardInfo("Reckless Rage", 110, Rarity.UNCOMMON, mage.cards.r.RecklessRage.class));
        cards.add(new SetCardInfo("Recover", 84, Rarity.COMMON, mage.cards.r.Recover.class));
        cards.add(new SetCardInfo("Rekindling Phoenix", 111, Rarity.MYTHIC, mage.cards.r.RekindlingPhoenix.class));
        cards.add(new SetCardInfo("Relentless Raptor", 169, Rarity.UNCOMMON, mage.cards.r.RelentlessRaptor.class));
        cards.add(new SetCardInfo("Resplendent Griffin", 170, Rarity.UNCOMMON, mage.cards.r.ResplendentGriffin.class));
        cards.add(new SetCardInfo("Release to the Wind", 46, Rarity.RARE, mage.cards.r.ReleaseToTheWind.class));
        cards.add(new SetCardInfo("River Darter", 47, Rarity.COMMON, mage.cards.r.RiverDarter.class));
        cards.add(new SetCardInfo("Riverwise Augur", 48, Rarity.UNCOMMON, mage.cards.r.RiverwiseAugur.class));
        cards.add(new SetCardInfo("Sadistic Skymarcher", 85, Rarity.UNCOMMON, mage.cards.s.SadisticSkymarcher.class));
        cards.add(new SetCardInfo("Sailor of Means", 49, Rarity.COMMON, mage.cards.s.SailorOfMeans.class));
        cards.add(new SetCardInfo("Sanctum of the Sun", 176, Rarity.MYTHIC, mage.cards.s.SanctumOfTheSun.class));
        cards.add(new SetCardInfo("Sanguine Glorifier", 20, Rarity.COMMON, mage.cards.s.SanguineGlorifier.class));
        cards.add(new SetCardInfo("Sea Legs", 50, Rarity.COMMON, mage.cards.s.SeaLegs.class));
        cards.add(new SetCardInfo("Seafloor Oracle", 51, Rarity.RARE, mage.cards.s.SeafloorOracle.class));
        cards.add(new SetCardInfo("Secrets of the Golden City", 52, Rarity.COMMON, mage.cards.s.SecretsOfTheGoldenCity.class));
        cards.add(new SetCardInfo("See Red", 112, Rarity.UNCOMMON, mage.cards.s.SeeRed.class));
        cards.add(new SetCardInfo("Shake the Foundations", 113, Rarity.UNCOMMON, mage.cards.s.ShakeTheFoundations.class));
        cards.add(new SetCardInfo("Shatter", 114, Rarity.COMMON, mage.cards.s.Shatter.class));
        cards.add(new SetCardInfo("Siegehorn Ceratops", 171, Rarity.RARE, mage.cards.s.SiegehornCeratops.class));
        cards.add(new SetCardInfo("Silent Gravestone", 182, Rarity.RARE, mage.cards.s.SilentGravestone.class));
        cards.add(new SetCardInfo("Silverclad Ferocidons", 115, Rarity.RARE, mage.cards.s.SilvercladFerocidons.class));
        cards.add(new SetCardInfo("Silvergill Adept", 53, Rarity.UNCOMMON, mage.cards.s.SilvergillAdept.class));
        cards.add(new SetCardInfo("Siren Reaver", 54, Rarity.UNCOMMON, mage.cards.s.SirenReaver.class));
        cards.add(new SetCardInfo("Skymarcher Aspirant", 21, Rarity.UNCOMMON, mage.cards.s.SkymarcherAspirant.class));
        cards.add(new SetCardInfo("Slaughter the Strong", 22, Rarity.RARE, mage.cards.s.SlaughterTheStrong.class));
        cards.add(new SetCardInfo("Slippery Scoundrel", 55, Rarity.UNCOMMON, mage.cards.s.SlipperyScoundrel.class));
        cards.add(new SetCardInfo("Snubhorn Sentry", 23, Rarity.COMMON, mage.cards.s.SnubhornSentry.class));
        cards.add(new SetCardInfo("Soul of the Rapids", 56, Rarity.COMMON, mage.cards.s.SoulOfTheRapids.class));
        cards.add(new SetCardInfo("Sphinx's Decree", 24, Rarity.RARE, mage.cards.s.SphinxsDecree.class));
        cards.add(new SetCardInfo("Spire Winder", 57, Rarity.COMMON, mage.cards.s.SpireWinder.class));
        cards.add(new SetCardInfo("Squire's Devotion", 25, Rarity.COMMON, mage.cards.s.SquiresDevotion.class));
        cards.add(new SetCardInfo("Stampeding Horncrest", 116, Rarity.COMMON, mage.cards.s.StampedingHorncrest.class));
        cards.add(new SetCardInfo("Stone Quarry", 190, Rarity.UNCOMMON, mage.cards.s.StoneQuarry.class));
        cards.add(new SetCardInfo("Storm Fleet Sprinter", 172, Rarity.UNCOMMON, mage.cards.s.StormFleetSprinter.class));
        cards.add(new SetCardInfo("Storm Fleet Swashbuckler", 117, Rarity.UNCOMMON, mage.cards.s.StormFleetSwashbuckler.class));
        cards.add(new SetCardInfo("Storm the Vault", 173, Rarity.RARE, mage.cards.s.StormTheVault.class));
        cards.add(new SetCardInfo("Strength of the Pack", 145, Rarity.UNCOMMON, mage.cards.s.StrengthOfThePack.class));
        cards.add(new SetCardInfo("Strider Harness", 183, Rarity.COMMON, mage.cards.s.StriderHarness.class));
        cards.add(new SetCardInfo("Sun Sentinel", 26, Rarity.COMMON, mage.cards.s.SunSentinel.class));
        cards.add(new SetCardInfo("Sun-Collared Raptor", 118, Rarity.COMMON, mage.cards.s.SunCollaredRaptor.class));
        cards.add(new SetCardInfo("Sun-Crested Pterodon", 27, Rarity.COMMON, mage.cards.s.SunCrestedPterodon.class));
        cards.add(new SetCardInfo("Swab Goblin", 203, Rarity.COMMON, mage.cards.s.SwabGoblin.class));
        cards.add(new SetCardInfo("Swaggering Corsair", 119, Rarity.COMMON, mage.cards.s.SwaggeringCorsair.class));
        cards.add(new SetCardInfo("Swamp", 194, Rarity.LAND, mage.cards.basiclands.Swamp.class));
        cards.add(new SetCardInfo("Swift Warden", 146, Rarity.UNCOMMON, mage.cards.s.SwiftWarden.class));
        cards.add(new SetCardInfo("Sworn Guardian", 58, Rarity.COMMON, mage.cards.s.SwornGuardian.class));
        cards.add(new SetCardInfo("Temple Altisaur", 28, Rarity.RARE, mage.cards.t.TempleAltisaur.class));
        cards.add(new SetCardInfo("Tendershoot Dryad", 147, Rarity.RARE, mage.cards.t.TendershootDryad.class));
        cards.add(new SetCardInfo("Tetzimoc, Primal Death", 86, Rarity.RARE, mage.cards.t.TetzimocPrimalDeath.class));
        cards.add(new SetCardInfo("The Immortal Sun", 180, Rarity.MYTHIC, mage.cards.t.TheImmortalSun.class));
        cards.add(new SetCardInfo("Thrashing Brontodon", 148, Rarity.UNCOMMON, mage.cards.t.ThrashingBrontodon.class));
        cards.add(new SetCardInfo("Thunderherd Migration", 149, Rarity.UNCOMMON, mage.cards.t.ThunderherdMigration.class));
        cards.add(new SetCardInfo("Tilonalli's Crown", 120, Rarity.COMMON, mage.cards.t.TilonallisCrown.class));
        cards.add(new SetCardInfo("Tilonalli's Summoner", 121, Rarity.RARE, mage.cards.t.TilonallisSummoner.class));
        cards.add(new SetCardInfo("Timestream Navigator", 59, Rarity.MYTHIC, mage.cards.t.TimestreamNavigator.class));
        cards.add(new SetCardInfo("Tomb of the Dusk Rose", 166, Rarity.RARE, mage.cards.t.TombOfTheDuskRose.class));
        cards.add(new SetCardInfo("Tomb Robber", 87, Rarity.RARE, mage.cards.t.TombRobber.class));
        cards.add(new SetCardInfo("Trapjaw Tyrant", 29, Rarity.MYTHIC, mage.cards.t.TrapjawTyrant.class));
        cards.add(new SetCardInfo("Traveler's Amulet", 184, Rarity.COMMON, mage.cards.t.TravelersAmulet.class));
        cards.add(new SetCardInfo("Twilight Prophet", 88, Rarity.MYTHIC, mage.cards.t.TwilightProphet.class));
        cards.add(new SetCardInfo("Vampire Champion", 198, Rarity.COMMON, mage.cards.v.VampireChampion.class));
        cards.add(new SetCardInfo("Vampire Revenant", 89, Rarity.COMMON, mage.cards.v.VampireRevenant.class));
        cards.add(new SetCardInfo("Vault of Catlacan", 173, Rarity.RARE, mage.cards.v.VaultOfCatlacan.class));
        cards.add(new SetCardInfo("Vona's Hunger", 90, Rarity.RARE, mage.cards.v.VonasHunger.class));
        cards.add(new SetCardInfo("Voracious Vampire", 91, Rarity.COMMON, mage.cards.v.VoraciousVampire.class));
        cards.add(new SetCardInfo("Vraska, Scheming Gorgon", 197, Rarity.MYTHIC, mage.cards.v.VraskaSchemingGorgon.class));
        cards.add(new SetCardInfo("Vraska's Conquistador", 199, Rarity.UNCOMMON, mage.cards.v.VraskasConquistador.class));
        cards.add(new SetCardInfo("Vraska's Scorn", 200, Rarity.RARE, mage.cards.v.VraskasScorn.class));
        cards.add(new SetCardInfo("Warkite Marauder", 60, Rarity.RARE, mage.cards.w.WarkiteMarauder.class));
        cards.add(new SetCardInfo("Waterknot", 61, Rarity.COMMON, mage.cards.w.Waterknot.class));
        cards.add(new SetCardInfo("Wayward Swordtooth", 150, Rarity.RARE, mage.cards.w.WaywardSwordtooth.class));
        cards.add(new SetCardInfo("Winged Temple of Orazca", 158, Rarity.RARE, mage.cards.w.WingedTempleOfOrazca.class));
        cards.add(new SetCardInfo("Woodland Stream", 191, Rarity.UNCOMMON, mage.cards.w.WoodlandStream.class));
        cards.add(new SetCardInfo("World Shaper", 151, Rarity.RARE, mage.cards.w.WorldShaper.class));
        cards.add(new SetCardInfo("Zacama, Primal Calamity", 174, Rarity.MYTHIC, mage.cards.z.ZacamaPrimalCalamity.class));
        cards.add(new SetCardInfo("Zetalpa, Primal Dawn", 30, Rarity.RARE, mage.cards.z.ZetalpaPrimalDawn.class));
    }

    @Override
    public BoosterCollator createCollator() {
        return new RivalsOfIxalanCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/rix.html
// Using USA collation for all rarities
// Foil rare sheet used for regular rares as regular rare sheet is not known
class RivalsOfIxalanCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "105", "11", "50", "104", "101", "43", "119", "20", "34", "116", "26", "49", "118", "15", "44", "108", "19", "57", "95", "5", "47", "104", "27", "43", "105", "26", "101", "119", "20", "50", "118", "11", "34", "116", "19", "44", "95", "5", "57", "108", "15", "49", "104", "5", "47", "101", "27", "49", "119", "11", "50", "116", "20", "57", "95", "26", "43", "105", "15", "34", "108", "19", "44", "118", "27", "47");
    private final CardRun commonB = new CardRun(true, "133", "89", "131", "91", "143", "84", "135", "70", "138", "71", "132", "91", "139", "79", "122", "63", "131", "67", "133", "89", "138", "84", "143", "69", "135", "71", "140", "79", "139", "70", "133", "67", "122", "63", "132", "89", "138", "84", "143", "69", "131", "91", "135", "79", "140", "71", "139", "70", "132", "67", "122", "63", "140", "69");
    private final CardRun commonC1 = new CardRun(true, "74", "120", "7", "96", "114", "61", "178", "183", "137", "75", "36", "106", "4", "61", "181", "25", "52", "141", "75", "7", "114", "120", "36", "183", "137", "181", "74", "141", "61", "25", "106", "114", "178", "7", "4", "137", "96", "74", "52", "120", "183", "36", "75", "106", "178", "4", "52", "181", "74", "141", "120", "25", "36", "114", "178", "7", "183", "61", "181", "75", "52", "137", "4", "106", "25", "141");
    private final CardRun commonC2 = new CardRun(true, "41", "184", "186", "125", "134", "96", "14", "58", "125", "12", "65", "56", "76", "23", "184", "186", "41", "93", "134", "12", "125", "65", "56", "14", "58", "76", "186", "96", "93", "23", "125", "41", "134", "184", "14", "65", "56", "186", "93", "12", "58", "184", "41", "23", "14", "76", "134", "56", "65", "12", "58", "76", "93", "23");
    private final CardRun uncommonA = new CardRun(true, "149", "55", "117", "153", "189", "112", "128", "21", "35", "164", "191", "129", "48", "10", "169", "190", "78", "145", "3", "39", "81", "188", "124", "38", "9", "163", "187", "102", "149", "21", "48", "153", "189", "129", "55", "9", "161", "191", "110", "128", "21", "35", "112", "190", "117", "39", "10", "164", "78", "102", "145", "3", "110", "187", "9", "38", "117", "169", "81", "3", "124", "78", "102", "161", "191", "129", "35", "112", "153", "189", "10", "128", "81", "48", "163", "188", "149", "55", "110", "164", "187", "145", "3", "78", "38", "169", "189", "124", "35", "112", "163", "190", "21", "128", "81", "39", "153", "188", "129", "55", "102", "161", "191", "10", "149", "117", "48", "164", "187", "145", "39", "110", "163", "190", "9", "124", "169", "38", "161", "188");
    private final CardRun uncommonB = new CardRun(true, "156", "53", "17", "73", "54", "107", "83", "6", "146", "72", "168", "37", "13", "73", "32", "113", "82", "123", "54", "17", "62", "172", "109", "126", "8", "53", "97", "80", "148", "155", "1", "85", "156", "98", "6", "83", "37", "107", "72", "146", "170", "53", "82", "172", "113", "123", "1", "32", "97", "62", "148", "168", "13", "85", "156", "109", "17", "80", "54", "98", "73", "126", "155", "8", "83", "170", "97", "123", "6", "37", "107", "72", "146", "172", "1", "62", "168", "98", "8", "82", "53", "113", "80", "148", "156", "32", "73", "155", "109", "126", "13", "54", "107", "85", "146", "170", "17", "83", "168", "113", "6", "72", "37", "97", "62", "123", "172", "13", "85", "155", "98", "126", "8", "32", "109", "82", "148", "170", "1", "80");
    private final CardRun rare = new CardRun(true, "175", "22", "167", "29", "2", "86", "16", "147", "162", "103", "45", "177", "24", "171", "59", "45", "87", "22", "150", "182", "115", "46", "86", "28", "103", "88", "46", "90", "24", "151", "185", "121", "51", "87", "30", "115", "111", "51", "92", "28", "167", "174", "127", "60", "90", "31", "121", "144", "60", "94", "18", "171", "175", "130", "64", "92", "33", "127", "152", "64", "99", "31", "147", "177", "136", "66", "94", "40", "130", "154", "66", "100", "33", "150", "180", "142", "68", "99", "42", "136", "157", "68", "182", "40", "151", "2", "30", "77", "100", "16", "142", "159", "77", "185", "42", "18");
    private final CardRun rareDFC = new CardRun(false, "158", "160", "165", "166", "173", "179", "158", "160", "165", "166", "173", "179", "176");
    private final CardRun land = new CardRun(false, "192", "193", "194", "195", "196");

    private final BoosterStructure AAABBC1C1C1C1C1 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAABBBC1C1C1C1 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB, commonB,
            commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAABBBC2C2C2C2 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB, commonB,
            commonC2, commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAAABBC2C2C2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB,
            commonC2, commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAB = new BoosterStructure(uncommonA, uncommonA, uncommonB);
    private final BoosterStructure ABB = new BoosterStructure(uncommonA, uncommonB, uncommonB);
    private final BoosterStructure R1 = new BoosterStructure(rare);
    private final BoosterStructure R2 = new BoosterStructure(rareDFC);
    private final BoosterStructure L1 = new BoosterStructure(land);

    // In order for equal numbers of each common to exist, the average booster must contain:
    // 3.14 A commons (44 / 14)
    // 2.57 B commons (36 / 14)
    // 2.36 C1 commons (33 / 14)
    // 1.93 C2 commons (27 / 14)
    // These numbers are the same for all sets with 70 commons in A/B/C1/C2 print runs
    // and with 10 common slots per booster
    private final RarityConfiguration commonRuns = new RarityConfiguration(
            AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1,

            AAABBBC1C1C1C1, AAABBBC1C1C1C1, AAABBBC1C1C1C1, AAABBBC1C1C1C1,
            AAABBBC1C1C1C1, AAABBBC1C1C1C1, AAABBBC1C1C1C1, AAABBBC1C1C1C1,
            AAABBBC1C1C1C1, AAABBBC1C1C1C1, AAABBBC1C1C1C1, AAABBBC1C1C1C1,
            AAABBBC1C1C1C1,

            AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2,
            AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2,
            AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2,
            AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2,
            AAABBBC2C2C2C2, AAABBBC2C2C2C2, AAABBBC2C2C2C2,

            AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2,
            AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2
    );
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(AAB, ABB);
    private final RarityConfiguration rareRuns = new RarityConfiguration(R1, R1, R1, R1, R1, R1, R1, R2);
    private final RarityConfiguration landRuns = new RarityConfiguration(L1);

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
