package mage.sets;

import mage.cards.ExpansionSet;
import mage.collation.BoosterCollator;
import mage.collation.BoosterStructure;
import mage.collation.CardRun;
import mage.collation.RarityConfiguration;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fireshoes
 */
public final class Kaladesh extends ExpansionSet {

    private static final Kaladesh instance = new Kaladesh();

    public static Kaladesh getInstance() {
        return instance;
    }

    private Kaladesh() {
        super("Kaladesh", "KLD", ExpansionSet.buildDate(2016, 9, 30), SetType.EXPANSION);
        this.blockName = "Kaladesh";
        this.hasBoosters = true;
        this.hasBasicLands = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.ratioBoosterSpecialCommon = 144;
        this.maxCardNumberInBooster = 264;

        cards.add(new SetCardInfo("Accomplished Automaton", 191, Rarity.COMMON, mage.cards.a.AccomplishedAutomaton.class));
        cards.add(new SetCardInfo("Acrobatic Maneuver", 1, Rarity.COMMON, mage.cards.a.AcrobaticManeuver.class));
        cards.add(new SetCardInfo("Aerial Responder", 2, Rarity.UNCOMMON, mage.cards.a.AerialResponder.class));
        cards.add(new SetCardInfo("Aether Hub", 242, Rarity.UNCOMMON, mage.cards.a.AetherHub.class));
        cards.add(new SetCardInfo("Aether Meltdown", 36, Rarity.UNCOMMON, mage.cards.a.AetherMeltdown.class));
        cards.add(new SetCardInfo("Aether Theorist", 37, Rarity.COMMON, mage.cards.a.AetherTheorist.class));
        cards.add(new SetCardInfo("Aether Tradewinds", 38, Rarity.COMMON, mage.cards.a.AetherTradewinds.class));
        cards.add(new SetCardInfo("Aetherborn Marauder", 71, Rarity.UNCOMMON, mage.cards.a.AetherbornMarauder.class));
        cards.add(new SetCardInfo("Aetherflux Reservoir", 192, Rarity.RARE, mage.cards.a.AetherfluxReservoir.class));
        cards.add(new SetCardInfo("Aethersquall Ancient", 39, Rarity.RARE, mage.cards.a.AethersquallAncient.class));
        cards.add(new SetCardInfo("Aetherstorm Roc", 3, Rarity.RARE, mage.cards.a.AetherstormRoc.class));
        cards.add(new SetCardInfo("Aethertorch Renegade", 106, Rarity.UNCOMMON, mage.cards.a.AethertorchRenegade.class));
        cards.add(new SetCardInfo("Aetherworks Marvel", 193, Rarity.MYTHIC, mage.cards.a.AetherworksMarvel.class));
        cards.add(new SetCardInfo("Ambitious Aetherborn", 72, Rarity.COMMON, mage.cards.a.AmbitiousAetherborn.class));
        cards.add(new SetCardInfo("Angel of Invention", 4, Rarity.MYTHIC, mage.cards.a.AngelOfInvention.class));
        cards.add(new SetCardInfo("Animation Module", 194, Rarity.RARE, mage.cards.a.AnimationModule.class));
        cards.add(new SetCardInfo("Appetite for the Unnatural", 141, Rarity.COMMON, mage.cards.a.AppetiteForTheUnnatural.class));
        cards.add(new SetCardInfo("Aradara Express", 195, Rarity.COMMON, mage.cards.a.AradaraExpress.class));
        cards.add(new SetCardInfo("Arborback Stomper", "142+", Rarity.UNCOMMON, mage.cards.a.ArborbackStomper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arborback Stomper", 142, Rarity.UNCOMMON, mage.cards.a.ArborbackStomper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Architect of the Untamed", 143, Rarity.RARE, mage.cards.a.ArchitectOfTheUntamed.class));
        cards.add(new SetCardInfo("Armorcraft Judge", 144, Rarity.UNCOMMON, mage.cards.a.ArmorcraftJudge.class));
        cards.add(new SetCardInfo("Attune with Aether", 145, Rarity.COMMON, mage.cards.a.AttuneWithAether.class));
        cards.add(new SetCardInfo("Authority of the Consuls", 5, Rarity.RARE, mage.cards.a.AuthorityOfTheConsuls.class));
        cards.add(new SetCardInfo("Aviary Mechanic", 6, Rarity.COMMON, mage.cards.a.AviaryMechanic.class));
        cards.add(new SetCardInfo("Ballista Charger", 196, Rarity.UNCOMMON, mage.cards.b.BallistaCharger.class));
        cards.add(new SetCardInfo("Bastion Mastodon", 197, Rarity.COMMON, mage.cards.b.BastionMastodon.class));
        cards.add(new SetCardInfo("Blooming Marsh", 243, Rarity.RARE, mage.cards.b.BloomingMarsh.class));
        cards.add(new SetCardInfo("Blossoming Defense", 146, Rarity.UNCOMMON, mage.cards.b.BlossomingDefense.class));
        cards.add(new SetCardInfo("Bomat Bazaar Barge", 198, Rarity.UNCOMMON, mage.cards.b.BomatBazaarBarge.class));
        cards.add(new SetCardInfo("Bomat Courier", 199, Rarity.RARE, mage.cards.b.BomatCourier.class));
        cards.add(new SetCardInfo("Botanical Sanctum", 244, Rarity.RARE, mage.cards.b.BotanicalSanctum.class));
        cards.add(new SetCardInfo("Brazen Scourge", "107+", Rarity.UNCOMMON, mage.cards.b.BrazenScourge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Brazen Scourge", 107, Rarity.UNCOMMON, mage.cards.b.BrazenScourge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bristling Hydra", 147, Rarity.RARE, mage.cards.b.BristlingHydra.class));
        cards.add(new SetCardInfo("Built to Last", 7, Rarity.COMMON, mage.cards.b.BuiltToLast.class));
        cards.add(new SetCardInfo("Built to Smash", 108, Rarity.COMMON, mage.cards.b.BuiltToSmash.class));
        cards.add(new SetCardInfo("Captured by the Consulate", 8, Rarity.RARE, mage.cards.c.CapturedByTheConsulate.class));
        cards.add(new SetCardInfo("Cataclysmic Gearhulk", 9, Rarity.MYTHIC, mage.cards.c.CataclysmicGearhulk.class));
        cards.add(new SetCardInfo("Cathartic Reunion", 109, Rarity.COMMON, mage.cards.c.CatharticReunion.class));
        cards.add(new SetCardInfo("Ceremonious Rejection", 40, Rarity.UNCOMMON, mage.cards.c.CeremoniousRejection.class));
        cards.add(new SetCardInfo("Chandra's Pyrohelix", 111, Rarity.COMMON, mage.cards.c.ChandrasPyrohelix.class));
        cards.add(new SetCardInfo("Chandra, Pyrogenius", 265, Rarity.MYTHIC, mage.cards.c.ChandraPyrogenius.class));
        cards.add(new SetCardInfo("Chandra, Torch of Defiance", 110, Rarity.MYTHIC, mage.cards.c.ChandraTorchOfDefiance.class));
        cards.add(new SetCardInfo("Chief of the Foundry", 200, Rarity.UNCOMMON, mage.cards.c.ChiefOfTheFoundry.class));
        cards.add(new SetCardInfo("Cloudblazer", 176, Rarity.UNCOMMON, mage.cards.c.Cloudblazer.class));
        cards.add(new SetCardInfo("Cogworker's Puzzleknot", 201, Rarity.COMMON, mage.cards.c.CogworkersPuzzleknot.class));
        cards.add(new SetCardInfo("Combustible Gearhulk", 112, Rarity.MYTHIC, mage.cards.c.CombustibleGearhulk.class));
        cards.add(new SetCardInfo("Commencement of Festivities", 148, Rarity.COMMON, mage.cards.c.CommencementOfFestivities.class));
        cards.add(new SetCardInfo("Concealed Courtyard", 245, Rarity.RARE, mage.cards.c.ConcealedCourtyard.class));
        cards.add(new SetCardInfo("Confiscation Coup", 41, Rarity.RARE, mage.cards.c.ConfiscationCoup.class));
        cards.add(new SetCardInfo("Consul's Shieldguard", 11, Rarity.UNCOMMON, mage.cards.c.ConsulsShieldguard.class));
        cards.add(new SetCardInfo("Consulate Skygate", 202, Rarity.COMMON, mage.cards.c.ConsulateSkygate.class));
        cards.add(new SetCardInfo("Consulate Surveillance", 10, Rarity.UNCOMMON, mage.cards.c.ConsulateSurveillance.class));
        cards.add(new SetCardInfo("Contraband Kingpin", 177, Rarity.UNCOMMON, mage.cards.c.ContrabandKingpin.class));
        cards.add(new SetCardInfo("Cowl Prowler", 149, Rarity.COMMON, mage.cards.c.CowlProwler.class));
        cards.add(new SetCardInfo("Creeping Mold", 150, Rarity.UNCOMMON, mage.cards.c.CreepingMold.class));
        cards.add(new SetCardInfo("Cultivator of Blades", 151, Rarity.RARE, mage.cards.c.CultivatorOfBlades.class));
        cards.add(new SetCardInfo("Cultivator's Caravan", 203, Rarity.RARE, mage.cards.c.CultivatorsCaravan.class));
        cards.add(new SetCardInfo("Curio Vendor", 42, Rarity.COMMON, mage.cards.c.CurioVendor.class));
        cards.add(new SetCardInfo("Deadlock Trap", 204, Rarity.RARE, mage.cards.d.DeadlockTrap.class));
        cards.add(new SetCardInfo("Decoction Module", 205, Rarity.UNCOMMON, mage.cards.d.DecoctionModule.class));
        cards.add(new SetCardInfo("Demolish", 113, Rarity.COMMON, mage.cards.d.Demolish.class));
        cards.add(new SetCardInfo("Demolition Stomper", 206, Rarity.UNCOMMON, mage.cards.d.DemolitionStomper.class));
        cards.add(new SetCardInfo("Demon of Dark Schemes", 73, Rarity.MYTHIC, mage.cards.d.DemonOfDarkSchemes.class));
        cards.add(new SetCardInfo("Depala, Pilot Exemplar", 178, Rarity.RARE, mage.cards.d.DepalaPilotExemplar.class));
        cards.add(new SetCardInfo("Dhund Operative", 74, Rarity.COMMON, mage.cards.d.DhundOperative.class));
        cards.add(new SetCardInfo("Diabolic Tutor", 75, Rarity.UNCOMMON, mage.cards.d.DiabolicTutor.class));
        cards.add(new SetCardInfo("Die Young", 76, Rarity.COMMON, mage.cards.d.DieYoung.class));
        cards.add(new SetCardInfo("Disappearing Act", 43, Rarity.UNCOMMON, mage.cards.d.DisappearingAct.class));
        cards.add(new SetCardInfo("Dovin Baan", 179, Rarity.MYTHIC, mage.cards.d.DovinBaan.class));
        cards.add(new SetCardInfo("Dramatic Reversal", 44, Rarity.COMMON, mage.cards.d.DramaticReversal.class));
        cards.add(new SetCardInfo("Dubious Challenge", 152, Rarity.RARE, mage.cards.d.DubiousChallenge.class));
        cards.add(new SetCardInfo("Dukhara Peafowl", 207, Rarity.COMMON, mage.cards.d.DukharaPeafowl.class));
        cards.add(new SetCardInfo("Dukhara Scavenger", 77, Rarity.COMMON, mage.cards.d.DukharaScavenger.class));
        cards.add(new SetCardInfo("Durable Handicraft", 153, Rarity.UNCOMMON, mage.cards.d.DurableHandicraft.class));
        cards.add(new SetCardInfo("Dynavolt Tower", 208, Rarity.RARE, mage.cards.d.DynavoltTower.class));
        cards.add(new SetCardInfo("Eager Construct", 209, Rarity.COMMON, mage.cards.e.EagerConstruct.class));
        cards.add(new SetCardInfo("Eddytrail Hawk", 12, Rarity.COMMON, mage.cards.e.EddytrailHawk.class));
        cards.add(new SetCardInfo("Electrostatic Pummeler", 210, Rarity.RARE, mage.cards.e.ElectrostaticPummeler.class));
        cards.add(new SetCardInfo("Elegant Edgecrafters", 154, Rarity.UNCOMMON, mage.cards.e.ElegantEdgecrafters.class));
        cards.add(new SetCardInfo("Eliminate the Competition", 78, Rarity.RARE, mage.cards.e.EliminateTheCompetition.class));
        cards.add(new SetCardInfo("Embraal Bruiser", 79, Rarity.UNCOMMON, mage.cards.e.EmbraalBruiser.class));
        cards.add(new SetCardInfo("Empyreal Voyager", 180, Rarity.UNCOMMON, mage.cards.e.EmpyrealVoyager.class));
        cards.add(new SetCardInfo("Engineered Might", 181, Rarity.UNCOMMON, mage.cards.e.EngineeredMight.class));
        cards.add(new SetCardInfo("Era of Innovation", 45, Rarity.UNCOMMON, mage.cards.e.EraOfInnovation.class));
        cards.add(new SetCardInfo("Essence Extraction", 80, Rarity.UNCOMMON, mage.cards.e.EssenceExtraction.class));
        cards.add(new SetCardInfo("Experimental Aviator", 46, Rarity.UNCOMMON, mage.cards.e.ExperimentalAviator.class));
        cards.add(new SetCardInfo("Fabrication Module", 211, Rarity.UNCOMMON, mage.cards.f.FabricationModule.class));
        cards.add(new SetCardInfo("Failed Inspection", 47, Rarity.COMMON, mage.cards.f.FailedInspection.class));
        cards.add(new SetCardInfo("Fairgrounds Trumpeter", 155, Rarity.UNCOMMON, mage.cards.f.FairgroundsTrumpeter.class));
        cards.add(new SetCardInfo("Fairgrounds Warden", 13, Rarity.UNCOMMON, mage.cards.f.FairgroundsWarden.class));
        cards.add(new SetCardInfo("Fateful Showdown", 114, Rarity.RARE, mage.cards.f.FatefulShowdown.class));
        cards.add(new SetCardInfo("Filigree Familiar", 212, Rarity.UNCOMMON, mage.cards.f.FiligreeFamiliar.class));
        cards.add(new SetCardInfo("Fireforger's Puzzleknot", 213, Rarity.COMMON, mage.cards.f.FireforgersPuzzleknot.class));
        cards.add(new SetCardInfo("Flame Lash", 266, Rarity.COMMON, mage.cards.f.FlameLash.class));
        cards.add(new SetCardInfo("Fleetwheel Cruiser", 214, Rarity.RARE, mage.cards.f.FleetwheelCruiser.class));
        cards.add(new SetCardInfo("Forest", 262, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 263, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 264, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fortuitous Find", 81, Rarity.COMMON, mage.cards.f.FortuitousFind.class));
        cards.add(new SetCardInfo("Foundry Inspector", 215, Rarity.UNCOMMON, mage.cards.f.FoundryInspector.class));
        cards.add(new SetCardInfo("Foundry Screecher", 82, Rarity.COMMON, mage.cards.f.FoundryScreecher.class));
        cards.add(new SetCardInfo("Fragmentize", 14, Rarity.COMMON, mage.cards.f.Fragmentize.class));
        cards.add(new SetCardInfo("Fretwork Colony", 83, Rarity.UNCOMMON, mage.cards.f.FretworkColony.class));
        cards.add(new SetCardInfo("Fumigate", 15, Rarity.RARE, mage.cards.f.Fumigate.class));
        cards.add(new SetCardInfo("Furious Reprisal", 115, Rarity.UNCOMMON, mage.cards.f.FuriousReprisal.class));
        cards.add(new SetCardInfo("Gearseeker Serpent", 48, Rarity.COMMON, mage.cards.g.GearseekerSerpent.class));
        cards.add(new SetCardInfo("Gearshift Ace", 16, Rarity.UNCOMMON, mage.cards.g.GearshiftAce.class));
        cards.add(new SetCardInfo("Ghirapur Guide", 156, Rarity.UNCOMMON, mage.cards.g.GhirapurGuide.class));
        cards.add(new SetCardInfo("Ghirapur Orrery", 216, Rarity.RARE, mage.cards.g.GhirapurOrrery.class));
        cards.add(new SetCardInfo("Giant Spectacle", 116, Rarity.COMMON, mage.cards.g.GiantSpectacle.class));
        cards.add(new SetCardInfo("Glassblower's Puzzleknot", 217, Rarity.COMMON, mage.cards.g.GlassblowersPuzzleknot.class));
        cards.add(new SetCardInfo("Glimmer of Genius", 49, Rarity.UNCOMMON, mage.cards.g.GlimmerOfGenius.class));
        cards.add(new SetCardInfo("Glint-Nest Crane", 50, Rarity.UNCOMMON, mage.cards.g.GlintNestCrane.class));
        cards.add(new SetCardInfo("Glint-Sleeve Artisan", 17, Rarity.COMMON, mage.cards.g.GlintSleeveArtisan.class));
        cards.add(new SetCardInfo("Gonti, Lord of Luxury", 84, Rarity.RARE, mage.cards.g.GontiLordOfLuxury.class));
        cards.add(new SetCardInfo("Guardian of the Great Conduit", 271, Rarity.UNCOMMON, mage.cards.g.GuardianOfTheGreatConduit.class));
        cards.add(new SetCardInfo("Harnessed Lightning", 117, Rarity.UNCOMMON, mage.cards.h.HarnessedLightning.class));
        cards.add(new SetCardInfo("Harsh Scrutiny", 85, Rarity.UNCOMMON, mage.cards.h.HarshScrutiny.class));
        cards.add(new SetCardInfo("Hazardous Conditions", 182, Rarity.UNCOMMON, mage.cards.h.HazardousConditions.class));
        cards.add(new SetCardInfo("Herald of the Fair", 18, Rarity.COMMON, mage.cards.h.HeraldOfTheFair.class));
        cards.add(new SetCardInfo("Highspire Artisan", 157, Rarity.COMMON, mage.cards.h.HighspireArtisan.class));
        cards.add(new SetCardInfo("Hightide Hermit", 51, Rarity.COMMON, mage.cards.h.HightideHermit.class));
        cards.add(new SetCardInfo("Hijack", 118, Rarity.COMMON, mage.cards.h.Hijack.class));
        cards.add(new SetCardInfo("Hunt the Weak", 158, Rarity.COMMON, mage.cards.h.HuntTheWeak.class));
        cards.add(new SetCardInfo("Impeccable Timing", 19, Rarity.COMMON, mage.cards.i.ImpeccableTiming.class));
        cards.add(new SetCardInfo("Incendiary Sabotage", 119, Rarity.UNCOMMON, mage.cards.i.IncendiarySabotage.class));
        cards.add(new SetCardInfo("Insidious Will", 52, Rarity.RARE, mage.cards.i.InsidiousWill.class));
        cards.add(new SetCardInfo("Inspired Charge", 20, Rarity.COMMON, mage.cards.i.InspiredCharge.class));
        cards.add(new SetCardInfo("Inspiring Vantage", 246, Rarity.RARE, mage.cards.i.InspiringVantage.class));
        cards.add(new SetCardInfo("Inventor's Apprentice", 120, Rarity.UNCOMMON, mage.cards.i.InventorsApprentice.class));
        cards.add(new SetCardInfo("Inventor's Goggles", 218, Rarity.COMMON, mage.cards.i.InventorsGoggles.class));
        cards.add(new SetCardInfo("Inventors' Fair", 247, Rarity.RARE, mage.cards.i.InventorsFair.class));
        cards.add(new SetCardInfo("Iron League Steed", 219, Rarity.UNCOMMON, mage.cards.i.IronLeagueSteed.class));
        cards.add(new SetCardInfo("Island", 253, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 254, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 255, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Janjeet Sentry", 53, Rarity.UNCOMMON, mage.cards.j.JanjeetSentry.class));
        cards.add(new SetCardInfo("Kambal, Consul of Allocation", 183, Rarity.RARE, mage.cards.k.KambalConsulOfAllocation.class));
        cards.add(new SetCardInfo("Key to the City", 220, Rarity.RARE, mage.cards.k.KeyToTheCity.class));
        cards.add(new SetCardInfo("Kujar Seedsculptor", 159, Rarity.COMMON, mage.cards.k.KujarSeedsculptor.class));
        cards.add(new SetCardInfo("Larger Than Life", 160, Rarity.COMMON, mage.cards.l.LargerThanLife.class));
        cards.add(new SetCardInfo("Lathnu Hellion", 121, Rarity.RARE, mage.cards.l.LathnuHellion.class));
        cards.add(new SetCardInfo("Lawless Broker", 86, Rarity.COMMON, mage.cards.l.LawlessBroker.class));
        cards.add(new SetCardInfo("Liberating Combustion", 267, Rarity.RARE, mage.cards.l.LiberatingCombustion.class));
        cards.add(new SetCardInfo("Live Fast", 87, Rarity.COMMON, mage.cards.l.LiveFast.class));
        cards.add(new SetCardInfo("Long-Finned Skywhale", 54, Rarity.UNCOMMON, mage.cards.l.LongFinnedSkywhale.class));
        cards.add(new SetCardInfo("Longtusk Cub", 161, Rarity.UNCOMMON, mage.cards.l.LongtuskCub.class));
        cards.add(new SetCardInfo("Lost Legacy", 88, Rarity.RARE, mage.cards.l.LostLegacy.class));
        cards.add(new SetCardInfo("Madcap Experiment", 122, Rarity.RARE, mage.cards.m.MadcapExperiment.class));
        cards.add(new SetCardInfo("Make Obsolete", 89, Rarity.UNCOMMON, mage.cards.m.MakeObsolete.class));
        cards.add(new SetCardInfo("Malfunction", 55, Rarity.COMMON, mage.cards.m.Malfunction.class));
        cards.add(new SetCardInfo("Marionette Master", 90, Rarity.RARE, mage.cards.m.MarionetteMaster.class));
        cards.add(new SetCardInfo("Master Trinketeer", 21, Rarity.RARE, mage.cards.m.MasterTrinketeer.class));
        cards.add(new SetCardInfo("Maulfist Doorbuster", 123, Rarity.UNCOMMON, mage.cards.m.MaulfistDoorbuster.class));
        cards.add(new SetCardInfo("Maulfist Squad", 91, Rarity.COMMON, mage.cards.m.MaulfistSquad.class));
        cards.add(new SetCardInfo("Metallurgic Summonings", 56, Rarity.MYTHIC, mage.cards.m.MetallurgicSummonings.class));
        cards.add(new SetCardInfo("Metalspinner's Puzzleknot", 221, Rarity.COMMON, mage.cards.m.MetalspinnersPuzzleknot.class));
        cards.add(new SetCardInfo("Metalwork Colossus", 222, Rarity.RARE, mage.cards.m.MetalworkColossus.class));
        cards.add(new SetCardInfo("Midnight Oil", 92, Rarity.RARE, mage.cards.m.MidnightOil.class));
        cards.add(new SetCardInfo("Mind Rot", 93, Rarity.COMMON, mage.cards.m.MindRot.class));
        cards.add(new SetCardInfo("Minister of Inquiries", 57, Rarity.UNCOMMON, mage.cards.m.MinisterOfInquiries.class));
        cards.add(new SetCardInfo("Morbid Curiosity", 94, Rarity.UNCOMMON, mage.cards.m.MorbidCuriosity.class));
        cards.add(new SetCardInfo("Mountain", 259, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 260, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 261, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Multiform Wonder", 223, Rarity.RARE, mage.cards.m.MultiformWonder.class));
        cards.add(new SetCardInfo("Narnam Cobra", 224, Rarity.COMMON, mage.cards.n.NarnamCobra.class));
        cards.add(new SetCardInfo("Nature's Way", 162, Rarity.UNCOMMON, mage.cards.n.NaturesWay.class));
        cards.add(new SetCardInfo("Night Market Lookout", 95, Rarity.COMMON, mage.cards.n.NightMarketLookout.class));
        cards.add(new SetCardInfo("Nimble Innovator", 58, Rarity.COMMON, mage.cards.n.NimbleInnovator.class));
        cards.add(new SetCardInfo("Ninth Bridge Patrol", 22, Rarity.COMMON, mage.cards.n.NinthBridgePatrol.class));
        cards.add(new SetCardInfo("Nissa, Nature's Artisan", 270, Rarity.MYTHIC, mage.cards.n.NissaNaturesArtisan.class));
        cards.add(new SetCardInfo("Nissa, Vital Force", 163, Rarity.MYTHIC, mage.cards.n.NissaVitalForce.class));
        cards.add(new SetCardInfo("Noxious Gearhulk", 96, Rarity.MYTHIC, mage.cards.n.NoxiousGearhulk.class));
        cards.add(new SetCardInfo("Ornamental Courage", 164, Rarity.COMMON, mage.cards.o.OrnamentalCourage.class));
        cards.add(new SetCardInfo("Ovalchase Daredevil", 97, Rarity.UNCOMMON, mage.cards.o.OvalchaseDaredevil.class));
        cards.add(new SetCardInfo("Ovalchase Dragster", 225, Rarity.UNCOMMON, mage.cards.o.OvalchaseDragster.class));
        cards.add(new SetCardInfo("Oviya Pashiri, Sage Lifecrafter", 165, Rarity.RARE, mage.cards.o.OviyaPashiriSageLifecrafter.class));
        cards.add(new SetCardInfo("Padeem, Consul of Innovation", 59, Rarity.RARE, mage.cards.p.PadeemConsulOfInnovation.class));
        cards.add(new SetCardInfo("Panharmonicon", 226, Rarity.RARE, mage.cards.p.Panharmonicon.class));
        cards.add(new SetCardInfo("Paradoxical Outcome", 60, Rarity.RARE, mage.cards.p.ParadoxicalOutcome.class));
        cards.add(new SetCardInfo("Peema Outrider", 166, Rarity.COMMON, mage.cards.p.PeemaOutrider.class));
        cards.add(new SetCardInfo("Perpetual Timepiece", 227, Rarity.UNCOMMON, mage.cards.p.PerpetualTimepiece.class));
        cards.add(new SetCardInfo("Pia Nalaar", 124, Rarity.RARE, mage.cards.p.PiaNalaar.class));
        cards.add(new SetCardInfo("Plains", 250, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 251, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 252, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prakhata Club Security", 98, Rarity.COMMON, mage.cards.p.PrakhataClubSecurity.class));
        cards.add(new SetCardInfo("Prakhata Pillar-Bug", 228, Rarity.COMMON, mage.cards.p.PrakhataPillarBug.class));
        cards.add(new SetCardInfo("Pressure Point", 23, Rarity.COMMON, mage.cards.p.PressurePoint.class));
        cards.add(new SetCardInfo("Propeller Pioneer", 24, Rarity.COMMON, mage.cards.p.PropellerPioneer.class));
        cards.add(new SetCardInfo("Prophetic Prism", 229, Rarity.COMMON, mage.cards.p.PropheticPrism.class));
        cards.add(new SetCardInfo("Quicksmith Genius", 125, Rarity.UNCOMMON, mage.cards.q.QuicksmithGenius.class));
        cards.add(new SetCardInfo("Rashmi, Eternities Crafter", 184, Rarity.MYTHIC, mage.cards.r.RashmiEternitiesCrafter.class));
        cards.add(new SetCardInfo("Reckless Fireweaver", 126, Rarity.COMMON, mage.cards.r.RecklessFireweaver.class));
        cards.add(new SetCardInfo("Refurbish", 25, Rarity.UNCOMMON, mage.cards.r.Refurbish.class));
        cards.add(new SetCardInfo("Renegade Firebrand", 268, Rarity.UNCOMMON, mage.cards.r.RenegadeFirebrand.class));
        cards.add(new SetCardInfo("Renegade Freighter", 230, Rarity.COMMON, mage.cards.r.RenegadeFreighter.class));
        cards.add(new SetCardInfo("Renegade Tactics", 127, Rarity.COMMON, mage.cards.r.RenegadeTactics.class));
        cards.add(new SetCardInfo("Restoration Gearsmith", 185, Rarity.UNCOMMON, mage.cards.r.RestorationGearsmith.class));
        cards.add(new SetCardInfo("Revoke Privileges", 26, Rarity.COMMON, mage.cards.r.RevokePrivileges.class));
        cards.add(new SetCardInfo("Revolutionary Rebuff", 61, Rarity.COMMON, mage.cards.r.RevolutionaryRebuff.class));
        cards.add(new SetCardInfo("Riparian Tiger", 167, Rarity.COMMON, mage.cards.r.RiparianTiger.class));
        cards.add(new SetCardInfo("Ruinous Gremlin", 128, Rarity.COMMON, mage.cards.r.RuinousGremlin.class));
        cards.add(new SetCardInfo("Rush of Vitality", 99, Rarity.COMMON, mage.cards.r.RushOfVitality.class));
        cards.add(new SetCardInfo("Sage of Shaila's Claim", 168, Rarity.COMMON, mage.cards.s.SageOfShailasClaim.class));
        cards.add(new SetCardInfo("Saheeli Rai", 186, Rarity.MYTHIC, mage.cards.s.SaheeliRai.class));
        cards.add(new SetCardInfo("Saheeli's Artistry", 62, Rarity.RARE, mage.cards.s.SaheelisArtistry.class));
        cards.add(new SetCardInfo("Salivating Gremlins", 129, Rarity.COMMON, mage.cards.s.SalivatingGremlins.class));
        cards.add(new SetCardInfo("Scrapheap Scrounger", 231, Rarity.RARE, mage.cards.s.ScrapheapScrounger.class));
        cards.add(new SetCardInfo("Select for Inspection", 63, Rarity.COMMON, mage.cards.s.SelectForInspection.class));
        cards.add(new SetCardInfo("Self-Assembler", 232, Rarity.COMMON, mage.cards.s.SelfAssembler.class));
        cards.add(new SetCardInfo("Sequestered Stash", 248, Rarity.UNCOMMON, mage.cards.s.SequesteredStash.class));
        cards.add(new SetCardInfo("Servant of the Conduit", 169, Rarity.UNCOMMON, mage.cards.s.ServantOfTheConduit.class));
        cards.add(new SetCardInfo("Servo Exhibition", 27, Rarity.UNCOMMON, mage.cards.s.ServoExhibition.class));
        cards.add(new SetCardInfo("Shrewd Negotiation", 64, Rarity.UNCOMMON, mage.cards.s.ShrewdNegotiation.class));
        cards.add(new SetCardInfo("Sky Skiff", 233, Rarity.COMMON, mage.cards.s.SkySkiff.class));
        cards.add(new SetCardInfo("Skyship Stalker", 130, Rarity.RARE, mage.cards.s.SkyshipStalker.class));
        cards.add(new SetCardInfo("Skysovereign, Consul Flagship", 234, Rarity.MYTHIC, mage.cards.s.SkysovereignConsulFlagship.class));
        cards.add(new SetCardInfo("Skyswirl Harrier", 28, Rarity.COMMON, mage.cards.s.SkyswirlHarrier.class));
        cards.add(new SetCardInfo("Skywhaler's Shot", 29, Rarity.UNCOMMON, mage.cards.s.SkywhalersShot.class));
        cards.add(new SetCardInfo("Smuggler's Copter", 235, Rarity.RARE, mage.cards.s.SmugglersCopter.class));
        cards.add(new SetCardInfo("Snare Thopter", 236, Rarity.UNCOMMON, mage.cards.s.SnareThopter.class));
        cards.add(new SetCardInfo("Spark of Creativity", 131, Rarity.UNCOMMON, mage.cards.s.SparkOfCreativity.class));
        cards.add(new SetCardInfo("Speedway Fanatic", 132, Rarity.UNCOMMON, mage.cards.s.SpeedwayFanatic.class));
        cards.add(new SetCardInfo("Spirebluff Canal", 249, Rarity.RARE, mage.cards.s.SpirebluffCanal.class));
        cards.add(new SetCardInfo("Spireside Infiltrator", 133, Rarity.COMMON, mage.cards.s.SpiresideInfiltrator.class));
        cards.add(new SetCardInfo("Spontaneous Artist", 134, Rarity.COMMON, mage.cards.s.SpontaneousArtist.class));
        cards.add(new SetCardInfo("Start Your Engines", 135, Rarity.UNCOMMON, mage.cards.s.StartYourEngines.class));
        cards.add(new SetCardInfo("Stone Quarry", 269, Rarity.COMMON, mage.cards.s.StoneQuarry.class));
        cards.add(new SetCardInfo("Subtle Strike", 100, Rarity.COMMON, mage.cards.s.SubtleStrike.class));
        cards.add(new SetCardInfo("Swamp", 256, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 257, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 258, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Syndicate Trafficker", 101, Rarity.RARE, mage.cards.s.SyndicateTrafficker.class));
        cards.add(new SetCardInfo("Take Down", 170, Rarity.COMMON, mage.cards.t.TakeDown.class));
        cards.add(new SetCardInfo("Tasseled Dromedary", 30, Rarity.COMMON, mage.cards.t.TasseledDromedary.class));
        cards.add(new SetCardInfo("Terrain Elemental", "272+", Rarity.COMMON, mage.cards.t.TerrainElemental.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Terrain Elemental", 272, Rarity.COMMON, mage.cards.t.TerrainElemental.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Territorial Gorger", 136, Rarity.RARE, mage.cards.t.TerritorialGorger.class));
        cards.add(new SetCardInfo("Terror of the Fairgrounds", 137, Rarity.COMMON, mage.cards.t.TerrorOfTheFairgrounds.class));
        cards.add(new SetCardInfo("Tezzeret's Ambition", 65, Rarity.COMMON, mage.cards.t.TezzeretsAmbition.class));
        cards.add(new SetCardInfo("Thriving Grubs", 138, Rarity.COMMON, mage.cards.t.ThrivingGrubs.class));
        cards.add(new SetCardInfo("Thriving Ibex", 31, Rarity.COMMON, mage.cards.t.ThrivingIbex.class));
        cards.add(new SetCardInfo("Thriving Rats", 102, Rarity.COMMON, mage.cards.t.ThrivingRats.class));
        cards.add(new SetCardInfo("Thriving Rhino", 171, Rarity.COMMON, mage.cards.t.ThrivingRhino.class));
        cards.add(new SetCardInfo("Thriving Turtle", 66, Rarity.COMMON, mage.cards.t.ThrivingTurtle.class));
        cards.add(new SetCardInfo("Tidy Conclusion", 103, Rarity.COMMON, mage.cards.t.TidyConclusion.class));
        cards.add(new SetCardInfo("Toolcraft Exemplar", 32, Rarity.RARE, mage.cards.t.ToolcraftExemplar.class));
        cards.add(new SetCardInfo("Torch Gauntlet", 237, Rarity.COMMON, mage.cards.t.TorchGauntlet.class));
        cards.add(new SetCardInfo("Torrential Gearhulk", 67, Rarity.MYTHIC, mage.cards.t.TorrentialGearhulk.class));
        cards.add(new SetCardInfo("Trusty Companion", 33, Rarity.UNCOMMON, mage.cards.t.TrustyCompanion.class));
        cards.add(new SetCardInfo("Underhanded Designs", 104, Rarity.UNCOMMON, mage.cards.u.UnderhandedDesigns.class));
        cards.add(new SetCardInfo("Unlicensed Disintegration", 187, Rarity.UNCOMMON, mage.cards.u.UnlicensedDisintegration.class));
        cards.add(new SetCardInfo("Vedalken Blademaster", 68, Rarity.COMMON, mage.cards.v.VedalkenBlademaster.class));
        cards.add(new SetCardInfo("Verdant Crescendo", 273, Rarity.RARE, mage.cards.v.VerdantCrescendo.class));
        cards.add(new SetCardInfo("Verdurous Gearhulk", 172, Rarity.MYTHIC, mage.cards.v.VerdurousGearhulk.class));
        cards.add(new SetCardInfo("Veteran Motorist", 188, Rarity.UNCOMMON, mage.cards.v.VeteranMotorist.class));
        cards.add(new SetCardInfo("Visionary Augmenter", 34, Rarity.UNCOMMON, mage.cards.v.VisionaryAugmenter.class));
        cards.add(new SetCardInfo("Voltaic Brawler", 189, Rarity.UNCOMMON, mage.cards.v.VoltaicBrawler.class));
        cards.add(new SetCardInfo("Wayward Giant", 139, Rarity.COMMON, mage.cards.w.WaywardGiant.class));
        cards.add(new SetCardInfo("Weaponcraft Enthusiast", 105, Rarity.UNCOMMON, mage.cards.w.WeaponcraftEnthusiast.class));
        cards.add(new SetCardInfo("Weldfast Monitor", 238, Rarity.COMMON, mage.cards.w.WeldfastMonitor.class));
        cards.add(new SetCardInfo("Weldfast Wingsmith", 69, Rarity.COMMON, mage.cards.w.WeldfastWingsmith.class));
        cards.add(new SetCardInfo("Welding Sparks", 140, Rarity.COMMON, mage.cards.w.WeldingSparks.class));
        cards.add(new SetCardInfo("Whirler Virtuoso", 190, Rarity.UNCOMMON, mage.cards.w.WhirlerVirtuoso.class));
        cards.add(new SetCardInfo("Whirlermaker", 239, Rarity.UNCOMMON, mage.cards.w.Whirlermaker.class));
        cards.add(new SetCardInfo("Wild Wanderer", 173, Rarity.COMMON, mage.cards.w.WildWanderer.class));
        cards.add(new SetCardInfo("Wildest Dreams", 174, Rarity.RARE, mage.cards.w.WildestDreams.class));
        cards.add(new SetCardInfo("Wily Bandar", 175, Rarity.COMMON, mage.cards.w.WilyBandar.class));
        cards.add(new SetCardInfo("Wind Drake", "70+", Rarity.COMMON, mage.cards.w.WindDrake.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wind Drake", 70, Rarity.COMMON, mage.cards.w.WindDrake.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wispweaver Angel", 35, Rarity.UNCOMMON, mage.cards.w.WispweaverAngel.class));
        cards.add(new SetCardInfo("Woodland Stream", 274, Rarity.COMMON, mage.cards.w.WoodlandStream.class));
        cards.add(new SetCardInfo("Woodweaver's Puzzleknot", 240, Rarity.COMMON, mage.cards.w.WoodweaversPuzzleknot.class));
        cards.add(new SetCardInfo("Workshop Assistant", 241, Rarity.COMMON, mage.cards.w.WorkshopAssistant.class));
    }

    @Override
    protected List<CardInfo> findCardsByRarity(Rarity rarity) {
        List<CardInfo> cardInfos = super.findCardsByRarity(rarity);
        if (rarity == Rarity.SPECIAL) {
            cardInfos.addAll(CardRepository.instance.findCards(new CardCriteria().setCodes("MPS").maxCardNumber(30)));
        }
        return cardInfos;
    }
    @Override
    protected void generateBoosterMap() {
        super.generateBoosterMap();
        CardRepository
                .instance
                .findCards(new CardCriteria().setCodes("MPS").maxCardNumber(30))
                .stream()
                .forEach(cardInfo -> inBoosterMap.put("MPS_" + cardInfo.getCardNumber(), cardInfo));
    }

    //@Override
    //public BoosterCollator createCollator() {
    //    return new KaladeshCollator();
    //}
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/kld.html
// Using USA collation for all rarities
// Foil rare sheet used for regular rares as regular rare sheet is not known
class KaladeshCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "14", "47", "113", "22", "51", "108", "1", "58", "118", "28", "44", "139", "7", "61", "133", "30", "37", "126", "20", "38", "137", "31", "66", "138", "12", "68", "128", "18", "51", "108", "14", "69", "113", "6", "47", "109", "1", "58", "134", "7", "63", "118", "22", "37", "133", "28", "44", "139", "30", "61", "138", "20", "38", "126", "18", "66", "128", "12", "69", "134", "6", "68", "137", "31", "63", "109");
    private final CardRun commonB = new CardRun(true, "93", "171", "86", "159", "98", "175", "100", "145", "77", "170", "102", "157", "72", "149", "91", "141", "95", "173", "81", "164", "87", "168", "93", "171", "86", "159", "72", "145", "102", "157", "95", "170", "98", "175", "91", "149", "87", "164", "77", "168", "81", "173", "86", "141", "93", "171", "100", "159", "102", "175", "98", "149", "95", "157", "72", "170", "91", "145", "100", "141", "77", "168", "87", "164", "81", "173");
    private final CardRun commonC1 = new CardRun(true, "229", "48", "202", "148", "19", "213", "42", "111", "201", "230", "99", "238", "140", "160", "197", "26", "127", "232", "103", "218", "116", "19", "202", "42", "167", "195", "209", "55", "217", "148", "111", "229", "76", "232", "48", "213", "127", "230", "23", "197", "140", "201", "103", "218", "160", "26", "217", "99", "55", "195", "116", "76", "238");
    private final CardRun commonC2 = new CardRun(true, "82", "240", "158", "65", "74", "224", "24", "241", "129", "233", "207", "237", "82", "228", "17", "191", "65", "221", "166", "224", "70", "233", "24", "240", "129", "241", "158", "237", "74", "17", "207", "228", "166", "240", "82", "191", "65", "221", "129", "224", "24", "237", "158", "233", "70", "74", "228", "166", "241", "17", "209", "207", "221", "70", "191");
    private final CardRun uncommonA = new CardRun(true, "35", "206", "64", "97", "176", "71", "142", "211", "29", "198", "117", "79", "187", "212", "13", "85", "162", "219", "181", "146", "80", "225", "161", "104", "190", "215", "2", "89", "189", "200", "115", "105", "54", "196", "180", "97", "106", "212", "182", "83", "64", "225", "35", "71", "176", "205", "142", "85", "13", "211", "117", "79", "187", "206", "162", "104", "29", "198", "181", "97", "189", "219", "80", "177", "54", "200", "190", "89", "2", "196", "161", "105", "180", "215", "115", "83", "106", "205", "182", "85", "35", "206", "64", "104", "187", "212", "29", "79", "176", "219", "13", "146", "181", "198", "142", "177", "80", "215", "189", "71", "117", "211", "162", "105", "190", "205", "2", "146", "54", "225", "182", "83", "161", "196", "115", "89", "180", "200", "106", "177");
    private final CardRun uncommonB = new CardRun(true, "185", "153", "227", "34", "36", "119", "25", "156", "40", "242", "10", "132", "155", "45", "16", "131", "236", "53", "169", "94", "33", "46", "107", "154", "239", "125", "11", "43", "150", "49", "27", "75", "120", "57", "144", "34", "248", "36", "119", "153", "227", "188", "50", "16", "135", "155", "107", "25", "46", "169", "131", "185", "40", "150", "10", "45", "156", "125", "43", "33", "236", "123", "154", "94", "53", "132", "242", "144", "120", "239", "49", "11", "153", "248", "57", "135", "188", "123", "75", "27", "50", "169", "131", "185", "45", "16", "227", "132", "154", "120", "25", "242", "236", "36", "119", "94", "34", "156", "40", "188", "43", "107", "155", "46", "10", "144", "53", "33", "239", "125", "150", "49", "11", "75", "135", "57", "27", "248", "123", "50");
    private final CardRun rare = new CardRun(true, "208", "152", "110", "246", "39", "192", "130", "73", "247", "5", "222", "60", "223", "21", "210", "151", "4", "231", "78", "32", "243", "121", "124", "226", "163", "220", "3", "245", "84", "183", "56", "235", "90", "174", "244", "41", "178", "136", "179", "222", "101", "214", "15", "247", "59", "203", "193", "152", "114", "62", "143", "5", "204", "88", "130", "92", "246", "8", "122", "249", "96", "147", "121", "32", "39", "199", "194", "60", "208", "9", "78", "216", "52", "165", "3", "223", "90", "124", "112", "231", "84", "183", "21", "192", "151", "172", "174", "245", "143", "101", "243", "67", "210", "178", "41", "226", "15", "204", "114", "147", "8", "234", "235", "136", "88", "203", "184", "220", "52", "249", "216", "92", "244", "59", "199", "186", "214", "122", "62", "165", "194");
    private final CardRun masterpiece = new CardRun(false, "MPS_1", "MPS_2", "MPS_3", "MPS_4", "MPS_5", "MPS_6", "MPS_7", "MPS_8", "MPS_9", "MPS_10", "MPS_11", "MPS_12", "MPS_13", "MPS_14", "MPS_15", "MPS_16", "MPS_17", "MPS_18", "MPS_19", "MPS_20", "MPS_21", "MPS_22", "MPS_23", "MPS_24", "MPS_25", "MPS_26", "MPS_27", "MPS_28", "MPS_29", "MPS_30");
    private final CardRun land = new CardRun(false, "250", "251", "252", "253", "254", "255", "256", "257", "258", "259", "260", "261", "262", "263", "264");

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
    private final BoosterStructure AABBC1C1C1C1C1M = new BoosterStructure(
            commonA, commonA,
            commonB, commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1,
            masterpiece
    );
    private final BoosterStructure AAAABBC2C2C2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB,
            commonC2, commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAAABBBBC2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB, commonB, commonB,
            commonC2, commonC2
    );
    private final BoosterStructure AAB = new BoosterStructure(uncommonA, uncommonA, uncommonB);
    private final BoosterStructure ABB = new BoosterStructure(uncommonA, uncommonB, uncommonB);
    private final BoosterStructure R1 = new BoosterStructure(rare);
    private final BoosterStructure L1 = new BoosterStructure(land);

    // In order for equal numbers of each common to exist, the average booster must contain:
    // 3.27 A commons (36 / 11)  (rounded to 471/144)
    // 2.18 B commons (24 / 11)  (rounded to 314/144)
    // 2.73 C1 commons (30 / 11) (rounded to 392/144)
    // 1.82 C2 commons (20 / 11) (rounded to 262/144)
    // These numbers are the same for all sets with 101 commons in A/B/C1/C2 print runs
    // and with 10 common slots per booster
    private final RarityConfiguration commonRuns = new RarityConfiguration(
            AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1,
            AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AAABBC1C1C1C1C1, AABBC1C1C1C1C1M,

            AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2,
            AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2,
            AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2,
            AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2,
            AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2,
            AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2,
            AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2,
            AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2,
            AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2,
            AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2,
            AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2,
            AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2, AAAABBC2C2C2C2,

            AAAABBBBC2C2, AAAABBBBC2C2, AAAABBBBC2C2, AAAABBBBC2C2, AAAABBBBC2C2,
            AAAABBBBC2C2, AAAABBBBC2C2, AAAABBBBC2C2, AAAABBBBC2C2, AAAABBBBC2C2,
            AAAABBBBC2C2, AAAABBBBC2C2, AAAABBBBC2C2
    );
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(AAB, ABB);
    private final RarityConfiguration rareRuns = new RarityConfiguration(R1);
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
