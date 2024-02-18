package mage.sets;

import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.util.RandomUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * https://mtg.gamepedia.com/Mystery_Booster
 * https://magic.wizards.com/en/articles/archive/feature/unraveling-mystery-booster-2019-11-14
 * https://scryfall.com/sets/mb1
 * Print sheets used for booster construction sourced from http://www.lethe.xyz/mtg/collation/mb1.html
 * <p>
 * This set has a very special booster layout: Each slot draws from it’s own distinct print sheet and
 * all cards have an equal probability. Therefore, this class implements booster construction by using 14 card lists.
 *
 * @author TheElk801
 */
public class MysteryBooster extends ExpansionSet {

    private static final MysteryBooster instance = new MysteryBooster();

    public static MysteryBooster getInstance() {
        return instance;
    }

    /**
     * This map defines which cards can go into which booster slot.
     * Will be populated when the first booster is requested.
     * <p>
     * Of note, this is booster collation for MB1, the 2019 Mystery Booster.
     * In the 2021 versions (not collated here), there are 1692 cards in common, and two changes:
     * - Goblin Trenches added instead of Selesnya Guildmage (See Scryfall note on https://scryfall.com/card/mb1/1695/goblin-trenches )
     * - Prophetic Bolt added instead of Nucklavee (See Scryfall note on https://scryfall.com/card/mb1/1696/prophetic-bolt )
     */
    protected final Map<Integer, List<CardInfo>> possibleCardsPerBoosterSlot = new HashMap<>();


    private MysteryBooster() {
        super("Mystery Booster", "MB1", ExpansionSet.buildDate(2019, 11, 7), SetType.SUPPLEMENTAL);
        this.hasBoosters = true;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Absorb Vis", "CN2-126", Rarity.COMMON, mage.cards.a.AbsorbVis.class));
        cards.add(new SetCardInfo("Abundant Growth", "EMA-156", Rarity.COMMON, mage.cards.a.AbundantGrowth.class));
        cards.add(new SetCardInfo("Abzan Charm", "C16-177", Rarity.UNCOMMON, mage.cards.a.AbzanCharm.class));
        cards.add(new SetCardInfo("Abzan Falconer", "KTK-2", Rarity.UNCOMMON, mage.cards.a.AbzanFalconer.class));
        cards.add(new SetCardInfo("Abzan Guide", "KTK-162", Rarity.COMMON, mage.cards.a.AbzanGuide.class));
        cards.add(new SetCardInfo("Abzan Runemark", "FRF-3", Rarity.COMMON, mage.cards.a.AbzanRunemark.class));
        cards.add(new SetCardInfo("Academy Journeymage", "DOM-41", Rarity.COMMON, mage.cards.a.AcademyJourneymage.class));
        cards.add(new SetCardInfo("Accursed Spirit", "M15-85", Rarity.COMMON, mage.cards.a.AccursedSpirit.class));
        cards.add(new SetCardInfo("Acidic Slime", "C18-127", Rarity.UNCOMMON, mage.cards.a.AcidicSlime.class));
        cards.add(new SetCardInfo("Acrobatic Maneuver", "KLD-1", Rarity.COMMON, mage.cards.a.AcrobaticManeuver.class));
        cards.add(new SetCardInfo("Act of Treason", "RNA-91", Rarity.COMMON, mage.cards.a.ActOfTreason.class));
        cards.add(new SetCardInfo("Act on Impulse", "M15-126", Rarity.UNCOMMON, mage.cards.a.ActOnImpulse.class));
        cards.add(new SetCardInfo("Adanto Vanguard", "XLN-1", Rarity.UNCOMMON, mage.cards.a.AdantoVanguard.class));
        cards.add(new SetCardInfo("Adorned Pouncer", "HOU-2", Rarity.RARE, mage.cards.a.AdornedPouncer.class));
        cards.add(new SetCardInfo("Adventurous Impulse", "DOM-153", Rarity.COMMON, mage.cards.a.AdventurousImpulse.class));
        cards.add(new SetCardInfo("Aerie Bowmasters", "DTK-170", Rarity.COMMON, mage.cards.a.AerieBowmasters.class));
        cards.add(new SetCardInfo("Aether Hub", "KLD-242", Rarity.UNCOMMON, mage.cards.a.AetherHub.class));
        cards.add(new SetCardInfo("Aether Spellbomb", "MMA-196", Rarity.COMMON, mage.cards.a.AetherSpellbomb.class));
        cards.add(new SetCardInfo("Aether Tradewinds", "KLD-38", Rarity.COMMON, mage.cards.a.AetherTradewinds.class));
        cards.add(new SetCardInfo("Aetherflux Reservoir", "KLD-192", Rarity.RARE, mage.cards.a.AetherfluxReservoir.class));
        cards.add(new SetCardInfo("Aethersnipe", "MM2-39", Rarity.COMMON, mage.cards.a.Aethersnipe.class));
        cards.add(new SetCardInfo("Affa Protector", "OGW-14", Rarity.COMMON, mage.cards.a.AffaProtector.class));
        cards.add(new SetCardInfo("Affectionate Indrik", "GRN-121", Rarity.UNCOMMON, mage.cards.a.AffectionateIndrik.class));
        cards.add(new SetCardInfo("Aggressive Instinct", "GS1-34", Rarity.COMMON, mage.cards.a.AggressiveInstinct.class));
        cards.add(new SetCardInfo("Aggressive Urge", "RIX-122", Rarity.COMMON, mage.cards.a.AggressiveUrge.class));
        cards.add(new SetCardInfo("Agony Warp", "MM3-150", Rarity.COMMON, mage.cards.a.AgonyWarp.class));
        cards.add(new SetCardInfo("Ahn-Crop Crasher", "AKH-117", Rarity.UNCOMMON, mage.cards.a.AhnCropCrasher.class));
        cards.add(new SetCardInfo("Aid the Fallen", "WAR-76", Rarity.COMMON, mage.cards.a.AidTheFallen.class));
        cards.add(new SetCardInfo("Ainok Bond-Kin", "KTK-3", Rarity.COMMON, mage.cards.a.AinokBondKin.class));
        cards.add(new SetCardInfo("Ainok Survivalist", "DTK-172", Rarity.UNCOMMON, mage.cards.a.AinokSurvivalist.class));
        cards.add(new SetCardInfo("Ainok Tracker", "KTK-96", Rarity.COMMON, mage.cards.a.AinokTracker.class));
        cards.add(new SetCardInfo("Ajani's Pridemate", "WAR-4", Rarity.UNCOMMON, mage.cards.a.AjanisPridemate.class));
        cards.add(new SetCardInfo("Akoum Refuge", "CMA-238", Rarity.UNCOMMON, mage.cards.a.AkoumRefuge.class));
        cards.add(new SetCardInfo("Akroan Hoplite", "CN2-197", Rarity.UNCOMMON, mage.cards.a.AkroanHoplite.class));
        cards.add(new SetCardInfo("Akroan Horse", "C16-241", Rarity.RARE, mage.cards.a.AkroanHorse.class));
        cards.add(new SetCardInfo("Akroan Sergeant", "ORI-130", Rarity.COMMON, mage.cards.a.AkroanSergeant.class));
        cards.add(new SetCardInfo("Alchemist's Greeting", "EMN-116", Rarity.COMMON, mage.cards.a.AlchemistsGreeting.class));
        cards.add(new SetCardInfo("Alchemist's Vial", "ORI-220", Rarity.COMMON, mage.cards.a.AlchemistsVial.class));
        cards.add(new SetCardInfo("Alesha's Vanguard", "FRF-60", Rarity.COMMON, mage.cards.a.AleshasVanguard.class));
        cards.add(new SetCardInfo("Alesha, Who Smiles at Death", "FRF-90", Rarity.RARE, mage.cards.a.AleshaWhoSmilesAtDeath.class));
        cards.add(new SetCardInfo("Alhammarret's Archive", "ORI-221", Rarity.MYTHIC, mage.cards.a.AlhammarretsArchive.class));
        cards.add(new SetCardInfo("All Is Dust", "MM2-1", Rarity.RARE, mage.cards.a.AllIsDust.class));
        cards.add(new SetCardInfo("Alley Evasion", "AER-6", Rarity.COMMON, mage.cards.a.AlleyEvasion.class));
        cards.add(new SetCardInfo("Alley Strangler", "AER-52", Rarity.COMMON, mage.cards.a.AlleyStrangler.class));
        cards.add(new SetCardInfo("Alloy Myr", "MM2-201", Rarity.COMMON, mage.cards.a.AlloyMyr.class));
        cards.add(new SetCardInfo("Alpine Grizzly", "KTK-127", Rarity.COMMON, mage.cards.a.AlpineGrizzly.class));
        cards.add(new SetCardInfo("Altar's Reap", "DDR-37", Rarity.COMMON, mage.cards.a.AltarsReap.class));
        cards.add(new SetCardInfo("Amass the Components", "IMA-41", Rarity.COMMON, mage.cards.a.AmassTheComponents.class));
        cards.add(new SetCardInfo("Ambassador Oak", "A25-158", Rarity.COMMON, mage.cards.a.AmbassadorOak.class));
        cards.add(new SetCardInfo("Ambitious Aetherborn", "KLD-72", Rarity.COMMON, mage.cards.a.AmbitiousAetherborn.class));
        cards.add(new SetCardInfo("Aminatou's Augury", "C18-6", Rarity.RARE, mage.cards.a.AminatousAugury.class));
        cards.add(new SetCardInfo("Amphin Pathmage", "M15-45", Rarity.COMMON, mage.cards.a.AmphinPathmage.class));
        cards.add(new SetCardInfo("Ana Sanctuary", "APC-74", Rarity.UNCOMMON, mage.cards.a.AnaSanctuary.class));
        cards.add(new SetCardInfo("Ancestral Mask", "EMA-157", Rarity.UNCOMMON, mage.cards.a.AncestralMask.class));
        cards.add(new SetCardInfo("Ancestral Vengeance", "FRF-61", Rarity.COMMON, mage.cards.a.AncestralVengeance.class));
        cards.add(new SetCardInfo("Ancient Brontodon", "XLN-175", Rarity.COMMON, mage.cards.a.AncientBrontodon.class));
        cards.add(new SetCardInfo("Ancient Den", "MRD-278", Rarity.COMMON, mage.cards.a.AncientDen.class));
        cards.add(new SetCardInfo("Ancient Grudge", "MM3-88", Rarity.UNCOMMON, mage.cards.a.AncientGrudge.class));
        cards.add(new SetCardInfo("Ancient Stirrings", "A25-159", Rarity.UNCOMMON, mage.cards.a.AncientStirrings.class));
        cards.add(new SetCardInfo("Ancient Ziggurat", "CON-141", Rarity.UNCOMMON, mage.cards.a.AncientZiggurat.class));
        cards.add(new SetCardInfo("Angel of Mercy", "IMA-6", Rarity.COMMON, mage.cards.a.AngelOfMercy.class));
        cards.add(new SetCardInfo("Angel of Renewal", "BFZ-18", Rarity.UNCOMMON, mage.cards.a.AngelOfRenewal.class));
        cards.add(new SetCardInfo("Angel of the Dire Hour", "C14-1", Rarity.RARE, mage.cards.a.AngelOfTheDireHour.class));
        cards.add(new SetCardInfo("Angelic Destiny", "M12-3", Rarity.MYTHIC, mage.cards.a.AngelicDestiny.class));
        cards.add(new SetCardInfo("Angelic Gift", "BBD-88", Rarity.COMMON, mage.cards.a.AngelicGift.class));
        cards.add(new SetCardInfo("Angelic Purge", "SOI-3", Rarity.COMMON, mage.cards.a.AngelicPurge.class));
        cards.add(new SetCardInfo("Angelsong", "DDC-15", Rarity.COMMON, mage.cards.a.Angelsong.class));
        cards.add(new SetCardInfo("Anger", "UMA-122", Rarity.UNCOMMON, mage.cards.a.Anger.class));
        cards.add(new SetCardInfo("Anger of the Gods", "IMA-116", Rarity.RARE, mage.cards.a.AngerOfTheGods.class));
        cards.add(new SetCardInfo("Animar, Soul of Elements", "A25-196", Rarity.MYTHIC, mage.cards.a.AnimarSoulOfElements.class));
        cards.add(new SetCardInfo("Animate Dead", "EMA-78", Rarity.UNCOMMON, mage.cards.a.AnimateDead.class));
        cards.add(new SetCardInfo("Annihilate", "EMA-79", Rarity.UNCOMMON, mage.cards.a.Annihilate.class));
        cards.add(new SetCardInfo("Anticipate", "M19-44", Rarity.COMMON, mage.cards.a.Anticipate.class));
        cards.add(new SetCardInfo("Apostle's Blessing", "MM2-8", Rarity.COMMON, mage.cards.a.ApostlesBlessing.class));
        cards.add(new SetCardInfo("Approach of the Second Sun", "AKH-4", Rarity.RARE, mage.cards.a.ApproachOfTheSecondSun.class));
        cards.add(new SetCardInfo("Arachnus Web", "MM3-118", Rarity.COMMON, mage.cards.a.ArachnusWeb.class));
        cards.add(new SetCardInfo("Arbor Armament", "DOM-155", Rarity.COMMON, mage.cards.a.ArborArmament.class));
        cards.add(new SetCardInfo("Arbor Elf", "A25-160", Rarity.COMMON, mage.cards.a.ArborElf.class));
        cards.add(new SetCardInfo("Arc Trail", "PCA-39", Rarity.UNCOMMON, mage.cards.a.ArcTrail.class));
        cards.add(new SetCardInfo("Arcane Denial", "CMA-30", Rarity.COMMON, mage.cards.a.ArcaneDenial.class));
        cards.add(new SetCardInfo("Arcane Sanctum", "C18-232", Rarity.UNCOMMON, mage.cards.a.ArcaneSanctum.class));
        cards.add(new SetCardInfo("Arch of Orazca", "RIX-185", Rarity.RARE, mage.cards.a.ArchOfOrazca.class));
        cards.add(new SetCardInfo("Archaeomancer", "C17-81", Rarity.COMMON, mage.cards.a.Archaeomancer.class));
        cards.add(new SetCardInfo("Archangel", "C13-5", Rarity.UNCOMMON, mage.cards.a.Archangel.class));
        cards.add(new SetCardInfo("Archetype of Imagination", "C18-81", Rarity.UNCOMMON, mage.cards.a.ArchetypeOfImagination.class));
        cards.add(new SetCardInfo("Armadillo Cloak", "EMA-195", Rarity.UNCOMMON, mage.cards.a.ArmadilloCloak.class));
        cards.add(new SetCardInfo("Armament Corps", "KTK-165", Rarity.UNCOMMON, mage.cards.a.ArmamentCorps.class));
        cards.add(new SetCardInfo("Armillary Sphere", "C17-203", Rarity.COMMON, mage.cards.a.ArmillarySphere.class));
        cards.add(new SetCardInfo("Arrest", "MM2-9", Rarity.COMMON, mage.cards.a.Arrest.class));
        cards.add(new SetCardInfo("Arrester's Zeal", "RNA-4", Rarity.COMMON, mage.cards.a.ArrestersZeal.class));
        cards.add(new SetCardInfo("Arrow Storm", "KTK-98", Rarity.COMMON, mage.cards.a.ArrowStorm.class));
        cards.add(new SetCardInfo("Artful Maneuver", "DTK-4", Rarity.COMMON, mage.cards.a.ArtfulManeuver.class));
        cards.add(new SetCardInfo("Artificer's Assistant", "DOM-44", Rarity.COMMON, mage.cards.a.ArtificersAssistant.class));
        cards.add(new SetCardInfo("Artisan of Kozilek", "CM2-14", Rarity.UNCOMMON, mage.cards.a.ArtisanOfKozilek.class));
        cards.add(new SetCardInfo("Asceticism", "SOM-110", Rarity.RARE, mage.cards.a.Asceticism.class));
        cards.add(new SetCardInfo("Ash Barrens", "CM2-235", Rarity.COMMON, mage.cards.a.AshBarrens.class));
        cards.add(new SetCardInfo("Ashnod's Altar", "EMA-218", Rarity.UNCOMMON, mage.cards.a.AshnodsAltar.class));
        cards.add(new SetCardInfo("Assemble the Legion", "GTC-142", Rarity.RARE, mage.cards.a.AssembleTheLegion.class));
        cards.add(new SetCardInfo("Atarka Efreet", "DTK-128", Rarity.COMMON, mage.cards.a.AtarkaEfreet.class));
        cards.add(new SetCardInfo("Athreos, God of Passage", "JOU-146", Rarity.MYTHIC, mage.cards.a.AthreosGodOfPassage.class));
        cards.add(new SetCardInfo("Augur of Bolas", "MM3-30", Rarity.COMMON, mage.cards.a.AugurOfBolas.class));
        cards.add(new SetCardInfo("Augury Owl", "PCA-14", Rarity.COMMON, mage.cards.a.AuguryOwl.class));
        cards.add(new SetCardInfo("Aura Gnarlid", "C18-128", Rarity.COMMON, mage.cards.a.AuraGnarlid.class));
        cards.add(new SetCardInfo("Aura Shards", "INV-233", Rarity.UNCOMMON, mage.cards.a.AuraShards.class));
        cards.add(new SetCardInfo("Aura of Silence", "C15-60", Rarity.UNCOMMON, mage.cards.a.AuraOfSilence.class));
        cards.add(new SetCardInfo("Avacyn's Pilgrim", "MM3-119", Rarity.COMMON, mage.cards.a.AvacynsPilgrim.class));
        cards.add(new SetCardInfo("Avalanche Riders", "ULG-74", Rarity.UNCOMMON, mage.cards.a.AvalancheRiders.class));
        cards.add(new SetCardInfo("Avarax", "EMA-117", Rarity.COMMON, mage.cards.a.Avarax.class));
        cards.add(new SetCardInfo("Aven Battle Priest", "ORI-6", Rarity.COMMON, mage.cards.a.AvenBattlePriest.class));
        cards.add(new SetCardInfo("Aven Sentry", "DOM-3", Rarity.COMMON, mage.cards.a.AvenSentry.class));
        cards.add(new SetCardInfo("Azorius Charm", "IMA-192", Rarity.UNCOMMON, mage.cards.a.AzoriusCharm.class));
        cards.add(new SetCardInfo("Azra Bladeseeker", "BBD-55", Rarity.COMMON, mage.cards.a.AzraBladeseeker.class));
        cards.add(new SetCardInfo("Azra Oddsmaker", "BBD-75", Rarity.UNCOMMON, mage.cards.a.AzraOddsmaker.class));
        cards.add(new SetCardInfo("Backwoods Survivalists", "EMN-150", Rarity.COMMON, mage.cards.b.BackwoodsSurvivalists.class));
        cards.add(new SetCardInfo("Bala Ged Scorpion", "IMA-79", Rarity.COMMON, mage.cards.b.BalaGedScorpion.class));
        cards.add(new SetCardInfo("Balduvian Horde", "A25-120", Rarity.COMMON, mage.cards.b.BalduvianHorde.class));
        cards.add(new SetCardInfo("Baleful Ammit", "AKH-79", Rarity.UNCOMMON, mage.cards.b.BalefulAmmit.class));
        cards.add(new SetCardInfo("Baleful Strix", "PCA-82", Rarity.UNCOMMON, mage.cards.b.BalefulStrix.class));
        cards.add(new SetCardInfo("Ballynock Cohort", "EMA-3", Rarity.COMMON, mage.cards.b.BallynockCohort.class));
        cards.add(new SetCardInfo("Baloth Gorger", "DOM-156", Rarity.COMMON, mage.cards.b.BalothGorger.class));
        cards.add(new SetCardInfo("Baloth Null", "A25-197", Rarity.UNCOMMON, mage.cards.b.BalothNull.class));
        cards.add(new SetCardInfo("Balustrade Spy", "IMA-80", Rarity.COMMON, mage.cards.b.BalustradeSpy.class));
        cards.add(new SetCardInfo("Barging Sergeant", "GRN-92", Rarity.COMMON, mage.cards.b.BargingSergeant.class));
        cards.add(new SetCardInfo("Barrage of Boulders", "KTK-100", Rarity.COMMON, mage.cards.b.BarrageOfBoulders.class));
        cards.add(new SetCardInfo("Bartered Cow", "ELD-6", Rarity.COMMON, mage.cards.b.BarteredCow.class));
        cards.add(new SetCardInfo("Bartizan Bats", "GRN-62", Rarity.COMMON, mage.cards.b.BartizanBats.class));
        cards.add(new SetCardInfo("Basilisk Collar", "2XM-233", Rarity.RARE, mage.cards.b.BasiliskCollar.class));
        cards.add(new SetCardInfo("Basking Rootwalla", "DDD-2", Rarity.COMMON, mage.cards.b.BaskingRootwalla.class));
        cards.add(new SetCardInfo("Bastion Inventor", "AER-30", Rarity.COMMON, mage.cards.b.BastionInventor.class));
        cards.add(new SetCardInfo("Battle Mastery", "BBD-89", Rarity.UNCOMMON, mage.cards.b.BattleMastery.class));
        cards.add(new SetCardInfo("Battle Rampart", "BBD-165", Rarity.COMMON, mage.cards.b.BattleRampart.class));
        cards.add(new SetCardInfo("Battle-Rattle Shaman", "E01-40", Rarity.COMMON, mage.cards.b.BattleRattleShaman.class));
        cards.add(new SetCardInfo("Beacon of Immortality", "E02-1", Rarity.RARE, mage.cards.b.BeaconOfImmortality.class));
        cards.add(new SetCardInfo("Bear Cub", "P02-123", Rarity.COMMON, mage.cards.b.BearCub.class));
        cards.add(new SetCardInfo("Bear's Companion", "KTK-167", Rarity.UNCOMMON, mage.cards.b.BearsCompanion.class));
        cards.add(new SetCardInfo("Beast Within", "NPH-103", Rarity.UNCOMMON, mage.cards.b.BeastWithin.class));
        cards.add(new SetCardInfo("Beastbreaker of Bala Ged", "DDP-10", Rarity.UNCOMMON, mage.cards.b.BeastbreakerOfBalaGed.class));
        cards.add(new SetCardInfo("Beastmaster Ascension", "ZEN-159", Rarity.RARE, mage.cards.b.BeastmasterAscension.class));
        cards.add(new SetCardInfo("Become Immense", "KTK-130", Rarity.UNCOMMON, mage.cards.b.BecomeImmense.class));
        cards.add(new SetCardInfo("Beetleback Chief", "PCA-40", Rarity.UNCOMMON, mage.cards.b.BeetlebackChief.class));
        cards.add(new SetCardInfo("Befuddle", "M19-309", Rarity.COMMON, mage.cards.b.Befuddle.class));
        cards.add(new SetCardInfo("Belbe's Portal", "NEM-127", Rarity.RARE, mage.cards.b.BelbesPortal.class));
        cards.add(new SetCardInfo("Belligerent Brontodon", "XLN-218", Rarity.UNCOMMON, mage.cards.b.BelligerentBrontodon.class));
        cards.add(new SetCardInfo("Bellows Lizard", "ORI-132", Rarity.COMMON, mage.cards.b.BellowsLizard.class));
        cards.add(new SetCardInfo("Beneath the Sands", "HOU-111", Rarity.COMMON, mage.cards.b.BeneathTheSands.class));
        cards.add(new SetCardInfo("Benevolent Ancestor", "IMA-12", Rarity.COMMON, mage.cards.b.BenevolentAncestor.class));
        cards.add(new SetCardInfo("Benthic Giant", "BBD-113", Rarity.COMMON, mage.cards.b.BenthicGiant.class));
        cards.add(new SetCardInfo("Benthic Infiltrator", "BFZ-55", Rarity.COMMON, mage.cards.b.BenthicInfiltrator.class));
        cards.add(new SetCardInfo("Bestial Menace", "MM2-141", Rarity.UNCOMMON, mage.cards.b.BestialMenace.class));
        cards.add(new SetCardInfo("Bewilder", "IMA-43", Rarity.COMMON, mage.cards.b.Bewilder.class));
        cards.add(new SetCardInfo("Birds of Paradise", "CN2-176", Rarity.RARE, mage.cards.b.BirdsOfParadise.class));
        cards.add(new SetCardInfo("Bitter Revelation", "KTK-65", Rarity.COMMON, mage.cards.b.BitterRevelation.class));
        cards.add(new SetCardInfo("Bitterblade Warrior", "AKH-157", Rarity.COMMON, mage.cards.b.BitterbladeWarrior.class));
        cards.add(new SetCardInfo("Bitterbow Sharpshooters", "HOU-112", Rarity.COMMON, mage.cards.b.BitterbowSharpshooters.class));
        cards.add(new SetCardInfo("Bituminous Blast", "PCA-83", Rarity.UNCOMMON, mage.cards.b.BituminousBlast.class));
        cards.add(new SetCardInfo("Black Cat", "M15-86", Rarity.COMMON, mage.cards.b.BlackCat.class));
        cards.add(new SetCardInfo("Black Knight", "M10-85", Rarity.UNCOMMON, mage.cards.b.BlackKnight.class));
        cards.add(new SetCardInfo("Black Market", "C17-98", Rarity.RARE, mage.cards.b.BlackMarket.class));
        cards.add(new SetCardInfo("Blade Instructor", "GRN-1", Rarity.COMMON, mage.cards.b.BladeInstructor.class));
        cards.add(new SetCardInfo("Bladebrand", "M20-86", Rarity.COMMON, mage.cards.b.Bladebrand.class));
        cards.add(new SetCardInfo("Blades of Velis Vel", "MM2-105", Rarity.COMMON, mage.cards.b.BladesOfVelisVel.class));
        cards.add(new SetCardInfo("Bladewing the Risen", "IMA-193", Rarity.UNCOMMON, mage.cards.b.BladewingTheRisen.class));
        cards.add(new SetCardInfo("Blanchwood Armor", "M19-169", Rarity.UNCOMMON, mage.cards.b.BlanchwoodArmor.class));
        cards.add(new SetCardInfo("Blasted Landscape", "CM2-238", Rarity.UNCOMMON, mage.cards.b.BlastedLandscape.class));
        cards.add(new SetCardInfo("Blastfire Bolt", "M15-130", Rarity.COMMON, mage.cards.b.BlastfireBolt.class));
        cards.add(new SetCardInfo("Blastoderm", "DDD-7", Rarity.COMMON, mage.cards.b.Blastoderm.class));
        cards.add(new SetCardInfo("Blazing Volley", "AKH-119", Rarity.COMMON, mage.cards.b.BlazingVolley.class));
        cards.add(new SetCardInfo("Blessed Spirits", "ORI-7", Rarity.UNCOMMON, mage.cards.b.BlessedSpirits.class));
        cards.add(new SetCardInfo("Blessing of Belzenlok", "DOM-77", Rarity.COMMON, mage.cards.b.BlessingOfBelzenlok.class));
        cards.add(new SetCardInfo("Blighted Bat", "AKH-80", Rarity.COMMON, mage.cards.b.BlightedBat.class));
        cards.add(new SetCardInfo("Blighted Fen", "BFZ-230", Rarity.UNCOMMON, mage.cards.b.BlightedFen.class));
        cards.add(new SetCardInfo("Blightning", "A25-198", Rarity.UNCOMMON, mage.cards.b.Blightning.class));
        cards.add(new SetCardInfo("Blightsoil Druid", "EMA-80", Rarity.COMMON, mage.cards.b.BlightsoilDruid.class));
        cards.add(new SetCardInfo("Blindblast", "WAR-114", Rarity.COMMON, mage.cards.b.Blindblast.class));
        cards.add(new SetCardInfo("Blinding Souleater", "MM2-202", Rarity.COMMON, mage.cards.b.BlindingSouleater.class));
        cards.add(new SetCardInfo("Blistergrub", "DDR-40", Rarity.COMMON, mage.cards.b.Blistergrub.class));
        cards.add(new SetCardInfo("Blood Artist", "C17-99", Rarity.UNCOMMON, mage.cards.b.BloodArtist.class));
        cards.add(new SetCardInfo("Blood Ogre", "E01-41", Rarity.COMMON, mage.cards.b.BloodOgre.class));
        cards.add(new SetCardInfo("Bloodbraid Elf", "PCA-84", Rarity.UNCOMMON, mage.cards.b.BloodbraidElf.class));
        cards.add(new SetCardInfo("Bloodfire Expert", "KTK-101", Rarity.COMMON, mage.cards.b.BloodfireExpert.class));
        cards.add(new SetCardInfo("Bloodlust Inciter", "AKH-120", Rarity.COMMON, mage.cards.b.BloodlustInciter.class));
        cards.add(new SetCardInfo("Bloodmad Vampire", "SOI-146", Rarity.COMMON, mage.cards.b.BloodmadVampire.class));
        cards.add(new SetCardInfo("Bloodrite Invoker", "DDP-45", Rarity.COMMON, mage.cards.b.BloodriteInvoker.class));
        cards.add(new SetCardInfo("Bloodstone Goblin", "DOM-115", Rarity.COMMON, mage.cards.b.BloodstoneGoblin.class));
        cards.add(new SetCardInfo("Bloom Tender", "EVE-66", Rarity.RARE, mage.cards.b.BloomTender.class));
        cards.add(new SetCardInfo("Blossom Dryad", "XLN-178", Rarity.COMMON, mage.cards.b.BlossomDryad.class));
        cards.add(new SetCardInfo("Blossoming Sands", "C18-237", Rarity.COMMON, mage.cards.b.BlossomingSands.class));
        cards.add(new SetCardInfo("Blow Your House Down", "ELD-114", Rarity.COMMON, mage.cards.b.BlowYourHouseDown.class));
        cards.add(new SetCardInfo("Blue Elemental Blast", "A25-43", Rarity.UNCOMMON, mage.cards.b.BlueElementalBlast.class));
        cards.add(new SetCardInfo("Blur of Blades", "HOU-84", Rarity.COMMON, mage.cards.b.BlurOfBlades.class));
        cards.add(new SetCardInfo("Boggart Brute", "ORI-133", Rarity.COMMON, mage.cards.b.BoggartBrute.class));
        cards.add(new SetCardInfo("Boiling Earth", "BFZ-142", Rarity.COMMON, mage.cards.b.BoilingEarth.class));
        cards.add(new SetCardInfo("Bojuka Bog", "WWK-132", Rarity.COMMON, mage.cards.b.BojukaBog.class));
        cards.add(new SetCardInfo("Bomat Bazaar Barge", "KLD-198", Rarity.UNCOMMON, mage.cards.b.BomatBazaarBarge.class));
        cards.add(new SetCardInfo("Bombard", "GNT-37", Rarity.COMMON, mage.cards.b.Bombard.class));
        cards.add(new SetCardInfo("Bomber Corps", "GK1-80", Rarity.COMMON, mage.cards.b.BomberCorps.class));
        cards.add(new SetCardInfo("Bonds of Faith", "DDQ-2", Rarity.COMMON, mage.cards.b.BondsOfFaith.class));
        cards.add(new SetCardInfo("Bone Saw", "OGW-161", Rarity.COMMON, mage.cards.b.BoneSaw.class));
        cards.add(new SetCardInfo("Bone Splinters", "M20-92", Rarity.COMMON, mage.cards.b.BoneSplinters.class));
        cards.add(new SetCardInfo("Bonesplitter", "MRD-146", Rarity.COMMON, mage.cards.b.Bonesplitter.class));
        cards.add(new SetCardInfo("Boompile", "C16-52", Rarity.RARE, mage.cards.b.Boompile.class));
        cards.add(new SetCardInfo("Boon of Emrakul", "EMN-81", Rarity.COMMON, mage.cards.b.BoonOfEmrakul.class));
        cards.add(new SetCardInfo("Borderland Explorer", "C18-133", Rarity.COMMON, mage.cards.b.BorderlandExplorer.class));
        cards.add(new SetCardInfo("Borderland Ranger", "E02-31", Rarity.COMMON, mage.cards.b.BorderlandRanger.class));
        cards.add(new SetCardInfo("Boros Challenger", "GRN-156", Rarity.UNCOMMON, mage.cards.b.BorosChallenger.class));
        cards.add(new SetCardInfo("Boros Reckoner", "GK1-85", Rarity.RARE, mage.cards.b.BorosReckoner.class));
        cards.add(new SetCardInfo("Borrowed Grace", "EMN-14", Rarity.COMMON, mage.cards.b.BorrowedGrace.class));
        cards.add(new SetCardInfo("Borrowed Hostility", "EMN-121", Rarity.COMMON, mage.cards.b.BorrowedHostility.class));
        cards.add(new SetCardInfo("Borrowing 100,000 Arrows", "A25-45", Rarity.COMMON, mage.cards.b.Borrowing100000Arrows.class));
        cards.add(new SetCardInfo("Bottle Gnomes", "TMP-278", Rarity.UNCOMMON, mage.cards.b.BottleGnomes.class));
        cards.add(new SetCardInfo("Boulder Salvo", "OGW-102", Rarity.COMMON, mage.cards.b.BoulderSalvo.class));
        cards.add(new SetCardInfo("Bounding Krasis", "ORI-212", Rarity.UNCOMMON, mage.cards.b.BoundingKrasis.class));
        cards.add(new SetCardInfo("Bow of Nylea", "THS-153", Rarity.RARE, mage.cards.b.BowOfNylea.class));
        cards.add(new SetCardInfo("Brainstorm", "SS1-3", Rarity.COMMON, mage.cards.b.Brainstorm.class));
        cards.add(new SetCardInfo("Brazen Buccaneers", "XLN-134", Rarity.COMMON, mage.cards.b.BrazenBuccaneers.class));
        cards.add(new SetCardInfo("Brazen Wolves", "EMN-122", Rarity.COMMON, mage.cards.b.BrazenWolves.class));
        cards.add(new SetCardInfo("Breaker of Armies", "BFZ-3", Rarity.UNCOMMON, mage.cards.b.BreakerOfArmies.class));
        cards.add(new SetCardInfo("Breeding Pit", "DDC-53", Rarity.UNCOMMON, mage.cards.b.BreedingPit.class));
        cards.add(new SetCardInfo("Briarhorn", "DDR-3", Rarity.UNCOMMON, mage.cards.b.Briarhorn.class));
        cards.add(new SetCardInfo("Brilliant Spectrum", "BFZ-70", Rarity.COMMON, mage.cards.b.BrilliantSpectrum.class));
        cards.add(new SetCardInfo("Brimstone Dragon", "P02-92", Rarity.RARE, mage.cards.b.BrimstoneDragon.class));
        cards.add(new SetCardInfo("Brimstone Mage", "ROE-137", Rarity.UNCOMMON, mage.cards.b.BrimstoneMage.class));
        cards.add(new SetCardInfo("Brine Elemental", "A25-47", Rarity.UNCOMMON, mage.cards.b.BrineElemental.class));
        cards.add(new SetCardInfo("Bring Low", "KTK-103", Rarity.COMMON, mage.cards.b.BringLow.class));
        cards.add(new SetCardInfo("Bristling Boar", "M19-170", Rarity.COMMON, mage.cards.b.BristlingBoar.class));
        cards.add(new SetCardInfo("Broken Bond", "DOM-157", Rarity.COMMON, mage.cards.b.BrokenBond.class));
        cards.add(new SetCardInfo("Broodhunter Wurm", "BFZ-171", Rarity.COMMON, mage.cards.b.BroodhunterWurm.class));
        cards.add(new SetCardInfo("Browbeat", "A25-123", Rarity.UNCOMMON, mage.cards.b.Browbeat.class));
        cards.add(new SetCardInfo("Brute Strength", "DDT-35", Rarity.COMMON, mage.cards.b.BruteStrength.class));
        cards.add(new SetCardInfo("Built to Last", "KLD-7", Rarity.COMMON, mage.cards.b.BuiltToLast.class));
        cards.add(new SetCardInfo("Built to Smash", "KLD-108", Rarity.COMMON, mage.cards.b.BuiltToSmash.class));
        cards.add(new SetCardInfo("Bulwark Giant", "WAR-7", Rarity.COMMON, mage.cards.b.BulwarkGiant.class));
        cards.add(new SetCardInfo("Burnished Hart", "C15-248", Rarity.UNCOMMON, mage.cards.b.BurnishedHart.class));
        cards.add(new SetCardInfo("Burst Lightning", "P10-8", Rarity.COMMON, mage.cards.b.BurstLightning.class));
        cards.add(new SetCardInfo("Butcher's Glee", "IMA-84", Rarity.COMMON, mage.cards.b.ButchersGlee.class));
        cards.add(new SetCardInfo("Byway Courier", "SOI-196", Rarity.COMMON, mage.cards.b.BywayCourier.class));
        cards.add(new SetCardInfo("Cabal Therapy", "EMA-83", Rarity.UNCOMMON, mage.cards.c.CabalTherapy.class));
        cards.add(new SetCardInfo("Cackling Imp", "DDC-41", Rarity.COMMON, mage.cards.c.CacklingImp.class));
        cards.add(new SetCardInfo("Cadaver Imp", "DDR-41", Rarity.COMMON, mage.cards.c.CadaverImp.class));
        cards.add(new SetCardInfo("Caged Sun", "CM2-178", Rarity.RARE, mage.cards.c.CagedSun.class));
        cards.add(new SetCardInfo("Cairn Wanderer", "LRW-105", Rarity.RARE, mage.cards.c.CairnWanderer.class));
        cards.add(new SetCardInfo("Calculated Dismissal", "ORI-48", Rarity.COMMON, mage.cards.c.CalculatedDismissal.class));
        cards.add(new SetCardInfo("Caligo Skin-Witch", "DOM-80", Rarity.COMMON, mage.cards.c.CaligoSkinWitch.class));
        cards.add(new SetCardInfo("Call of the Nightwing", "GTC-149", Rarity.UNCOMMON, mage.cards.c.CallOfTheNightwing.class));
        cards.add(new SetCardInfo("Call the Scions", "BFZ-165", Rarity.COMMON, mage.cards.c.CallTheScions.class));
        cards.add(new SetCardInfo("Call to Heel", "BBD-114", Rarity.COMMON, mage.cards.c.CallToHeel.class));
        cards.add(new SetCardInfo("Caller of Gales", "CN2-103", Rarity.COMMON, mage.cards.c.CallerOfGales.class));
        cards.add(new SetCardInfo("Campaign of Vengeance", "EMN-182", Rarity.UNCOMMON, mage.cards.c.CampaignOfVengeance.class));
        cards.add(new SetCardInfo("Cancel", "KTK-33", Rarity.COMMON, mage.cards.c.Cancel.class));
        cards.add(new SetCardInfo("Candlelight Vigil", "GRN-3", Rarity.COMMON, mage.cards.c.CandlelightVigil.class));
        cards.add(new SetCardInfo("Canopy Spider", "BBD-191", Rarity.COMMON, mage.cards.c.CanopySpider.class));
        cards.add(new SetCardInfo("Canyon Lurkers", "KTK-105", Rarity.COMMON, mage.cards.c.CanyonLurkers.class));
        cards.add(new SetCardInfo("Capture Sphere", "GRN-31", Rarity.COMMON, mage.cards.c.CaptureSphere.class));
        cards.add(new SetCardInfo("Caravan Escort", "DDP-3", Rarity.COMMON, mage.cards.c.CaravanEscort.class));
        cards.add(new SetCardInfo("Carnivorous Moss-Beast", "M15-170", Rarity.COMMON, mage.cards.c.CarnivorousMossBeast.class));
        cards.add(new SetCardInfo("Carpet of Flowers", "USG-240", Rarity.UNCOMMON, mage.cards.c.CarpetOfFlowers.class));
        cards.add(new SetCardInfo("Carrion Feeder", "MH1-81", Rarity.UNCOMMON, mage.cards.c.CarrionFeeder.class));
        cards.add(new SetCardInfo("Carrion Imp", "RNA-66", Rarity.COMMON, mage.cards.c.CarrionImp.class));
        cards.add(new SetCardInfo("Cartouche of Knowledge", "AKH-45", Rarity.COMMON, mage.cards.c.CartoucheOfKnowledge.class));
        cards.add(new SetCardInfo("Cartouche of Solidarity", "AKH-7", Rarity.COMMON, mage.cards.c.CartoucheOfSolidarity.class));
        cards.add(new SetCardInfo("Cartouche of Zeal", "AKH-124", Rarity.COMMON, mage.cards.c.CartoucheOfZeal.class));
        cards.add(new SetCardInfo("Cast Out", "AKH-8", Rarity.UNCOMMON, mage.cards.c.CastOut.class));
        cards.add(new SetCardInfo("Castaway's Despair", "XLN-281", Rarity.COMMON, mage.cards.c.CastawaysDespair.class));
        cards.add(new SetCardInfo("Catacomb Crocodile", "RNA-67", Rarity.COMMON, mage.cards.c.CatacombCrocodile.class));
        cards.add(new SetCardInfo("Catacomb Slug", "ORI-86", Rarity.COMMON, mage.cards.c.CatacombSlug.class));
        cards.add(new SetCardInfo("Catalog", "SOI-51", Rarity.COMMON, mage.cards.c.Catalog.class));
        cards.add(new SetCardInfo("Cathar's Companion", "SOI-9", Rarity.COMMON, mage.cards.c.CatharsCompanion.class));
        cards.add(new SetCardInfo("Cathartic Reunion", "KLD-109", Rarity.COMMON, mage.cards.c.CatharticReunion.class));
        cards.add(new SetCardInfo("Cathodion", "CM2-179", Rarity.UNCOMMON, mage.cards.c.Cathodion.class));
        cards.add(new SetCardInfo("Caught in the Brights", "AER-10", Rarity.COMMON, mage.cards.c.CaughtInTheBrights.class));
        cards.add(new SetCardInfo("Cauldron Dance", "C17-166", Rarity.UNCOMMON, mage.cards.c.CauldronDance.class));
        cards.add(new SetCardInfo("Cauldron of Souls", "CM2-180", Rarity.RARE, mage.cards.c.CauldronOfSouls.class));
        cards.add(new SetCardInfo("Caustic Caterpillar", "ORI-170", Rarity.COMMON, mage.cards.c.CausticCaterpillar.class));
        cards.add(new SetCardInfo("Caustic Tar", "A25-81", Rarity.UNCOMMON, mage.cards.c.CausticTar.class));
        cards.add(new SetCardInfo("Celestial Crusader", "C14-68", Rarity.UNCOMMON, mage.cards.c.CelestialCrusader.class));
        cards.add(new SetCardInfo("Celestial Flare", "ORI-8", Rarity.COMMON, mage.cards.c.CelestialFlare.class));
        cards.add(new SetCardInfo("Centaur Courser", "M19-171", Rarity.COMMON, mage.cards.c.CentaurCourser.class));
        cards.add(new SetCardInfo("Centaur Glade", "ONS-251", Rarity.UNCOMMON, mage.cards.c.CentaurGlade.class));
        cards.add(new SetCardInfo("Center Soul", "DTK-8", Rarity.COMMON, mage.cards.c.CenterSoul.class));
        cards.add(new SetCardInfo("Certain Death", "EMN-84", Rarity.COMMON, mage.cards.c.CertainDeath.class));
        cards.add(new SetCardInfo("Champion of Arashin", "DTK-9", Rarity.COMMON, mage.cards.c.ChampionOfArashin.class));
        cards.add(new SetCardInfo("Champion of the Parish", "DDQ-4", Rarity.RARE, mage.cards.c.ChampionOfTheParish.class));
        cards.add(new SetCardInfo("Chancellor of the Annex", "NPH-6", Rarity.RARE, mage.cards.c.ChancellorOfTheAnnex.class));
        cards.add(new SetCardInfo("Chandra's Pyrohelix", "WAR-120", Rarity.COMMON, mage.cards.c.ChandrasPyrohelix.class));
        cards.add(new SetCardInfo("Chandra's Revolution", "AER-77", Rarity.COMMON, mage.cards.c.ChandrasRevolution.class));
        cards.add(new SetCardInfo("Chaos Warp", "CMD-114", Rarity.RARE, mage.cards.c.ChaosWarp.class));
        cards.add(new SetCardInfo("Charge", "DOM-10", Rarity.COMMON, mage.cards.c.Charge.class));
        cards.add(new SetCardInfo("Charging Monstrosaur", "XLN-138", Rarity.UNCOMMON, mage.cards.c.ChargingMonstrosaur.class));
        cards.add(new SetCardInfo("Charging Rhino", "BBD-192", Rarity.COMMON, mage.cards.c.ChargingRhino.class));
        cards.add(new SetCardInfo("Chart a Course", "XLN-48", Rarity.UNCOMMON, mage.cards.c.ChartACourse.class));
        cards.add(new SetCardInfo("Chartooth Cougar", "A25-125", Rarity.COMMON, mage.cards.c.ChartoothCougar.class));
        cards.add(new SetCardInfo("Chasm Skulker", "M15-46", Rarity.RARE, mage.cards.c.ChasmSkulker.class));
        cards.add(new SetCardInfo("Chatter of the Squirrel", "ODY-233", Rarity.COMMON, mage.cards.c.ChatterOfTheSquirrel.class));
        cards.add(new SetCardInfo("Child of Night", "IMA-85", Rarity.COMMON, mage.cards.c.ChildOfNight.class));
        cards.add(new SetCardInfo("Chillbringer", "RNA-33", Rarity.COMMON, mage.cards.c.Chillbringer.class));
        cards.add(new SetCardInfo("Choking Tethers", "A25-48", Rarity.COMMON, mage.cards.c.ChokingTethers.class));
        cards.add(new SetCardInfo("Chromatic Lantern", "RTR-226", Rarity.RARE, mage.cards.c.ChromaticLantern.class));
        cards.add(new SetCardInfo("Chromatic Star", "TSP-251", Rarity.COMMON, mage.cards.c.ChromaticStar.class));
        cards.add(new SetCardInfo("Chronostutter", "M15-48", Rarity.COMMON, mage.cards.c.Chronostutter.class));
        cards.add(new SetCardInfo("Cinder Hellion", "OGW-105", Rarity.COMMON, mage.cards.c.CinderHellion.class));
        cards.add(new SetCardInfo("Circular Logic", "UMA-47", Rarity.UNCOMMON, mage.cards.c.CircularLogic.class));
        cards.add(new SetCardInfo("Citadel Castellan", "ORI-213", Rarity.UNCOMMON, mage.cards.c.CitadelCastellan.class));
        cards.add(new SetCardInfo("Citanul Woodreaders", "DDR-4", Rarity.COMMON, mage.cards.c.CitanulWoodreaders.class));
        cards.add(new SetCardInfo("Citywatch Sphinx", "GRN-33", Rarity.UNCOMMON, mage.cards.c.CitywatchSphinx.class));
        cards.add(new SetCardInfo("Claim // Fame", "HOU-150", Rarity.UNCOMMON, mage.cards.c.ClaimFame.class));
        cards.add(new SetCardInfo("Claustrophobia", "DDT-3", Rarity.COMMON, mage.cards.c.Claustrophobia.class));
        cards.add(new SetCardInfo("Cleansing Screech", "GS1-37", Rarity.COMMON, mage.cards.c.CleansingScreech.class));
        cards.add(new SetCardInfo("Clear the Mind", "RNA-34", Rarity.COMMON, mage.cards.c.ClearTheMind.class));
        cards.add(new SetCardInfo("Cliffside Lookout", "BFZ-20", Rarity.COMMON, mage.cards.c.CliffsideLookout.class));
        cards.add(new SetCardInfo("Clip Wings", "SOI-197", Rarity.COMMON, mage.cards.c.ClipWings.class));
        cards.add(new SetCardInfo("Cloak of Mists", "GS1-13", Rarity.COMMON, mage.cards.c.CloakOfMists.class));
        cards.add(new SetCardInfo("Cloud Elemental", "MM2-42", Rarity.COMMON, mage.cards.c.CloudElemental.class));
        cards.add(new SetCardInfo("Cloudkin Seer", "M20-54", Rarity.COMMON, mage.cards.c.CloudkinSeer.class));
        cards.add(new SetCardInfo("Cloudreader Sphinx", "DOM-47", Rarity.COMMON, mage.cards.c.CloudreaderSphinx.class));
        cards.add(new SetCardInfo("Cloudshift", "A25-7", Rarity.COMMON, mage.cards.c.Cloudshift.class));
        cards.add(new SetCardInfo("Clutch of Currents", "BFZ-72", Rarity.COMMON, mage.cards.c.ClutchOfCurrents.class));
        cards.add(new SetCardInfo("Coalition Honor Guard", "EMA-6", Rarity.COMMON, mage.cards.c.CoalitionHonorGuard.class));
        cards.add(new SetCardInfo("Coat of Arms", "DDS-58", Rarity.RARE, mage.cards.c.CoatOfArms.class));
        cards.add(new SetCardInfo("Coat with Venom", "DTK-91", Rarity.COMMON, mage.cards.c.CoatWithVenom.class));
        cards.add(new SetCardInfo("Cobblebrute", "ORI-138", Rarity.COMMON, mage.cards.c.Cobblebrute.class));
        cards.add(new SetCardInfo("Coiling Oracle", "MM3-157", Rarity.COMMON, mage.cards.c.CoilingOracle.class));
        cards.add(new SetCardInfo("Coldsteel Heart", "CM2-181", Rarity.UNCOMMON, mage.cards.c.ColdsteelHeart.class));
        cards.add(new SetCardInfo("Collar the Culprit", "GRN-5", Rarity.COMMON, mage.cards.c.CollarTheCulprit.class));
        cards.add(new SetCardInfo("Collective Brutality", "EMN-85", Rarity.RARE, mage.cards.c.CollectiveBrutality.class));
        cards.add(new SetCardInfo("Colossal Dreadmaw", "XLN-180", Rarity.COMMON, mage.cards.c.ColossalDreadmaw.class));
        cards.add(new SetCardInfo("Combo Attack", "BBD-67", Rarity.COMMON, mage.cards.c.ComboAttack.class));
        cards.add(new SetCardInfo("Commit // Memory", "AKH-211", Rarity.RARE, mage.cards.c.CommitMemory.class));
        cards.add(new SetCardInfo("Commune with Nature", "MM2-142", Rarity.COMMON, mage.cards.c.CommuneWithNature.class));
        cards.add(new SetCardInfo("Commune with the Gods", "EMA-162", Rarity.COMMON, mage.cards.c.CommuneWithTheGods.class));
        cards.add(new SetCardInfo("Compelling Argument", "AKH-47", Rarity.COMMON, mage.cards.c.CompellingArgument.class));
        cards.add(new SetCardInfo("Concentrate", "E02-9", Rarity.UNCOMMON, mage.cards.c.Concentrate.class));
        cards.add(new SetCardInfo("Condescend", "IMA-46", Rarity.UNCOMMON, mage.cards.c.Condescend.class));
        cards.add(new SetCardInfo("Congregate", "M15-6", Rarity.UNCOMMON, mage.cards.c.Congregate.class));
        cards.add(new SetCardInfo("Conifer Strider", "DTK-179", Rarity.COMMON, mage.cards.c.ConiferStrider.class));
        cards.add(new SetCardInfo("Consulate Dreadnought", "AER-146", Rarity.UNCOMMON, mage.cards.c.ConsulateDreadnought.class));
        cards.add(new SetCardInfo("Contagion Clasp", "SOM-144", Rarity.UNCOMMON, mage.cards.c.ContagionClasp.class));
        cards.add(new SetCardInfo("Containment Membrane", "OGW-52", Rarity.COMMON, mage.cards.c.ContainmentMembrane.class));
        cards.add(new SetCardInfo("Contingency Plan", "EMN-52", Rarity.COMMON, mage.cards.c.ContingencyPlan.class));
        cards.add(new SetCardInfo("Contraband Kingpin", "KLD-177", Rarity.UNCOMMON, mage.cards.c.ContrabandKingpin.class));
        cards.add(new SetCardInfo("Contradict", "DTK-49", Rarity.COMMON, mage.cards.c.Contradict.class));
        cards.add(new SetCardInfo("Conviction", "AER-12", Rarity.COMMON, mage.cards.c.Conviction.class));
        cards.add(new SetCardInfo("Convolute", "EMN-53", Rarity.COMMON, mage.cards.c.Convolute.class));
        cards.add(new SetCardInfo("Copper Carapace", "MM2-205", Rarity.COMMON, mage.cards.c.CopperCarapace.class));
        cards.add(new SetCardInfo("Coral Trickster", "DDN-44", Rarity.COMMON, mage.cards.c.CoralTrickster.class));
        cards.add(new SetCardInfo("Coralhelm Guide", "BBD-116", Rarity.COMMON, mage.cards.c.CoralhelmGuide.class));
        cards.add(new SetCardInfo("Corpsehatch", "DDP-50", Rarity.UNCOMMON, mage.cards.c.Corpsehatch.class));
        cards.add(new SetCardInfo("Corpsejack Menace", "IMA-197", Rarity.UNCOMMON, mage.cards.c.CorpsejackMenace.class));
        cards.add(new SetCardInfo("Corrupted Conscience", "MBS-22", Rarity.UNCOMMON, mage.cards.c.CorruptedConscience.class));
        cards.add(new SetCardInfo("Cosmotronic Wave", "GRN-95", Rarity.COMMON, mage.cards.c.CosmotronicWave.class));
        cards.add(new SetCardInfo("Costly Plunder", "XLN-96", Rarity.COMMON, mage.cards.c.CostlyPlunder.class));
        cards.add(new SetCardInfo("Counterspell", "CMR-395", Rarity.COMMON, mage.cards.c.Counterspell.class));
        cards.add(new SetCardInfo("Countless Gears Renegade", "AER-13", Rarity.COMMON, mage.cards.c.CountlessGearsRenegade.class));
        cards.add(new SetCardInfo("Courser of Kruphix", "A25-164", Rarity.RARE, mage.cards.c.CourserOfKruphix.class));
        cards.add(new SetCardInfo("Court Homunculus", "MM2-13", Rarity.COMMON, mage.cards.c.CourtHomunculus.class));
        cards.add(new SetCardInfo("Court Hussar", "A25-51", Rarity.COMMON, mage.cards.c.CourtHussar.class));
        cards.add(new SetCardInfo("Court Street Denizen", "DDO-5", Rarity.COMMON, mage.cards.c.CourtStreetDenizen.class));
        cards.add(new SetCardInfo("Covenant of Blood", "M15-91", Rarity.COMMON, mage.cards.c.CovenantOfBlood.class));
        cards.add(new SetCardInfo("Coveted Jewel", "C18-54", Rarity.RARE, mage.cards.c.CovetedJewel.class));
        cards.add(new SetCardInfo("Cower in Fear", "MM3-62", Rarity.COMMON, mage.cards.c.CowerInFear.class));
        cards.add(new SetCardInfo("Cragganwick Cremator", "SHM-87", Rarity.RARE, mage.cards.c.CragganwickCremator.class));
        cards.add(new SetCardInfo("Crash Through", "M19-133", Rarity.COMMON, mage.cards.c.CrashThrough.class));
        cards.add(new SetCardInfo("Crashing Tide", "RIX-34", Rarity.COMMON, mage.cards.c.CrashingTide.class));
        cards.add(new SetCardInfo("Creeping Mold", "KLD-150", Rarity.UNCOMMON, mage.cards.c.CreepingMold.class));
        cards.add(new SetCardInfo("Crenellated Wall", "MMQ-290", Rarity.UNCOMMON, mage.cards.c.CrenellatedWall.class));
        cards.add(new SetCardInfo("Crib Swap", "LRW-11", Rarity.UNCOMMON, mage.cards.c.CribSwap.class));
        cards.add(new SetCardInfo("Crippling Blight", "M15-92", Rarity.COMMON, mage.cards.c.CripplingBlight.class));
        cards.add(new SetCardInfo("Crop Rotation", "DDR-7", Rarity.COMMON, mage.cards.c.CropRotation.class));
        cards.add(new SetCardInfo("Crosis's Charm", "C17-169", Rarity.UNCOMMON, mage.cards.c.CrosissCharm.class));
        cards.add(new SetCardInfo("Crossroads Consecrator", "EMN-154", Rarity.COMMON, mage.cards.c.CrossroadsConsecrator.class));
        cards.add(new SetCardInfo("Crow of Dark Tidings", "SOI-105", Rarity.COMMON, mage.cards.c.CrowOfDarkTidings.class));
        cards.add(new SetCardInfo("Crowd's Favor", "M15-138", Rarity.COMMON, mage.cards.c.CrowdsFavor.class));
        cards.add(new SetCardInfo("Crown-Hunter Hireling", "CN2-50", Rarity.COMMON, mage.cards.c.CrownHunterHireling.class));
        cards.add(new SetCardInfo("Crowned Ceratok", "IMA-158", Rarity.COMMON, mage.cards.c.CrownedCeratok.class));
        cards.add(new SetCardInfo("Crumbling Necropolis", "C17-244", Rarity.UNCOMMON, mage.cards.c.CrumblingNecropolis.class));
        cards.add(new SetCardInfo("Crush Dissent", "WAR-47", Rarity.COMMON, mage.cards.c.CrushDissent.class));
        cards.add(new SetCardInfo("Crushing Canopy", "GRN-126", Rarity.COMMON, mage.cards.c.CrushingCanopy.class));
        cards.add(new SetCardInfo("Crystal Ball", "C18-201", Rarity.UNCOMMON, mage.cards.c.CrystalBall.class));
        cards.add(new SetCardInfo("Crystal Chimes", "C15-250", Rarity.UNCOMMON, mage.cards.c.CrystalChimes.class));
        cards.add(new SetCardInfo("Crystal Shard", "MRD-159", Rarity.UNCOMMON, mage.cards.c.CrystalShard.class));
        cards.add(new SetCardInfo("Cultivate", "NCC-285", Rarity.COMMON, mage.cards.c.Cultivate.class));
        cards.add(new SetCardInfo("Cunning Breezedancer", "DTK-215", Rarity.UNCOMMON, mage.cards.c.CunningBreezedancer.class));
        cards.add(new SetCardInfo("Curio Vendor", "KLD-42", Rarity.COMMON, mage.cards.c.CurioVendor.class));
        cards.add(new SetCardInfo("Curiosity", "A25-52", Rarity.UNCOMMON, mage.cards.c.Curiosity.class));
        cards.add(new SetCardInfo("Curse of Opulence", "C17-24", Rarity.UNCOMMON, mage.cards.c.CurseOfOpulence.class));
        cards.add(new SetCardInfo("Curse of the Nightly Hunt", "CM2-90", Rarity.UNCOMMON, mage.cards.c.CurseOfTheNightlyHunt.class));
        cards.add(new SetCardInfo("Cursed Minotaur", "AKH-85", Rarity.COMMON, mage.cards.c.CursedMinotaur.class));
        cards.add(new SetCardInfo("Daggerback Basilisk", "M19-174", Rarity.COMMON, mage.cards.d.DaggerbackBasilisk.class));
        cards.add(new SetCardInfo("Danitha Capashen, Paragon", "DOM-12", Rarity.UNCOMMON, mage.cards.d.DanithaCapashenParagon.class));
        cards.add(new SetCardInfo("Daretti, Scrap Savant", "CM2-4", Rarity.MYTHIC, mage.cards.d.DarettiScrapSavant.class));
        cards.add(new SetCardInfo("Daring Demolition", "AER-55", Rarity.COMMON, mage.cards.d.DaringDemolition.class));
        cards.add(new SetCardInfo("Daring Skyjek", "GK1-79", Rarity.COMMON, mage.cards.d.DaringSkyjek.class));
        cards.add(new SetCardInfo("Dark Dabbling", "ORI-89", Rarity.COMMON, mage.cards.d.DarkDabbling.class));
        cards.add(new SetCardInfo("Dark Ritual", "DDE-18", Rarity.COMMON, mage.cards.d.DarkRitual.class));
        cards.add(new SetCardInfo("Dark Withering", "C19-110", Rarity.COMMON, mage.cards.d.DarkWithering.class));
        cards.add(new SetCardInfo("Darkblast", "GK1-51", Rarity.UNCOMMON, mage.cards.d.Darkblast.class));
        cards.add(new SetCardInfo("Darksteel Citadel", "MM2-238", Rarity.COMMON, mage.cards.d.DarksteelCitadel.class));
        cards.add(new SetCardInfo("Darksteel Garrison", "FUT-167", Rarity.RARE, mage.cards.d.DarksteelGarrison.class));
        cards.add(new SetCardInfo("Darksteel Mutation", "CMA-9", Rarity.UNCOMMON, mage.cards.d.DarksteelMutation.class));
        cards.add(new SetCardInfo("Dauntless Cathar", "SOI-11", Rarity.COMMON, mage.cards.d.DauntlessCathar.class));
        cards.add(new SetCardInfo("Dauthi Mindripper", "TMP-125", Rarity.UNCOMMON, mage.cards.d.DauthiMindripper.class));
        cards.add(new SetCardInfo("Dawn's Reflection", "C18-139", Rarity.COMMON, mage.cards.d.DawnsReflection.class));
        cards.add(new SetCardInfo("Dawnglare Invoker", "C15-67", Rarity.COMMON, mage.cards.d.DawnglareInvoker.class));
        cards.add(new SetCardInfo("Daze", "DD2-23", Rarity.COMMON, mage.cards.d.Daze.class));
        cards.add(new SetCardInfo("Dazzling Lights", "GRN-34", Rarity.COMMON, mage.cards.d.DazzlingLights.class));
        cards.add(new SetCardInfo("Dead Reveler", "IMA-86", Rarity.COMMON, mage.cards.d.DeadReveler.class));
        cards.add(new SetCardInfo("Deadbridge Shaman", "EMA-85", Rarity.COMMON, mage.cards.d.DeadbridgeShaman.class));
        cards.add(new SetCardInfo("Deadeye Tormentor", "XLN-98", Rarity.COMMON, mage.cards.d.DeadeyeTormentor.class));
        cards.add(new SetCardInfo("Deadly Tempest", "C15-19", Rarity.RARE, mage.cards.d.DeadlyTempest.class));
        cards.add(new SetCardInfo("Death Denied", "MM2-76", Rarity.COMMON, mage.cards.d.DeathDenied.class));
        cards.add(new SetCardInfo("Death by Dragons", "CMA-80", Rarity.UNCOMMON, mage.cards.d.DeathByDragons.class));
        cards.add(new SetCardInfo("Death-Hood Cobra", "MM3-123", Rarity.COMMON, mage.cards.d.DeathHoodCobra.class));
        cards.add(new SetCardInfo("Deathreap Ritual", "C18-174", Rarity.UNCOMMON, mage.cards.d.DeathreapRitual.class));
        cards.add(new SetCardInfo("Debtors' Knell", "GK2-39", Rarity.RARE, mage.cards.d.DebtorsKnell.class));
        cards.add(new SetCardInfo("Decision Paralysis", "AKH-50", Rarity.COMMON, mage.cards.d.DecisionParalysis.class));
        cards.add(new SetCardInfo("Decommission", "AER-16", Rarity.COMMON, mage.cards.d.Decommission.class));
        cards.add(new SetCardInfo("Decree of Justice", "DDO-7", Rarity.RARE, mage.cards.d.DecreeOfJustice.class));
        cards.add(new SetCardInfo("Deep Analysis", "EMA-45", Rarity.COMMON, mage.cards.d.DeepAnalysis.class));
        cards.add(new SetCardInfo("Deep Freeze", "DOM-50", Rarity.COMMON, mage.cards.d.DeepFreeze.class));
        cards.add(new SetCardInfo("Deepglow Skate", "CM2-39", Rarity.RARE, mage.cards.d.DeepglowSkate.class));
        cards.add(new SetCardInfo("Defeat", "DTK-97", Rarity.COMMON, mage.cards.d.Defeat.class));
        cards.add(new SetCardInfo("Defense of the Heart", "ULG-100", Rarity.RARE, mage.cards.d.DefenseOfTheHeart.class));
        cards.add(new SetCardInfo("Defiant Ogre", "FRF-96", Rarity.COMMON, mage.cards.d.DefiantOgre.class));
        cards.add(new SetCardInfo("Defiant Strike", "WAR-9", Rarity.COMMON, mage.cards.d.DefiantStrike.class));
        cards.add(new SetCardInfo("Demolish", "XLN-139", Rarity.COMMON, mage.cards.d.Demolish.class));
        cards.add(new SetCardInfo("Demon's Grasp", "BFZ-108", Rarity.COMMON, mage.cards.d.DemonsGrasp.class));
        cards.add(new SetCardInfo("Demonic Tutor", "UMA-93", Rarity.UNCOMMON, mage.cards.d.DemonicTutor.class));
        cards.add(new SetCardInfo("Demonic Vigor", "DOM-85", Rarity.COMMON, mage.cards.d.DemonicVigor.class));
        cards.add(new SetCardInfo("Deny Reality", "ARB-19", Rarity.COMMON, mage.cards.d.DenyReality.class));
        cards.add(new SetCardInfo("Desert Cerodon", "AKH-128", Rarity.COMMON, mage.cards.d.DesertCerodon.class));
        cards.add(new SetCardInfo("Desert Twister", "CMA-100", Rarity.UNCOMMON, mage.cards.d.DesertTwister.class));
        cards.add(new SetCardInfo("Desolation Twin", "BFZ-6", Rarity.RARE, mage.cards.d.DesolationTwin.class));
        cards.add(new SetCardInfo("Desperate Castaways", "XLN-101", Rarity.COMMON, mage.cards.d.DesperateCastaways.class));
        cards.add(new SetCardInfo("Desperate Ravings", "C15-149", Rarity.UNCOMMON, mage.cards.d.DesperateRavings.class));
        cards.add(new SetCardInfo("Desperate Sentry", "EMN-21", Rarity.COMMON, mage.cards.d.DesperateSentry.class));
        cards.add(new SetCardInfo("Destructive Tampering", "AER-78", Rarity.COMMON, mage.cards.d.DestructiveTampering.class));
        cards.add(new SetCardInfo("Destructor Dragon", "FRF-127", Rarity.UNCOMMON, mage.cards.d.DestructorDragon.class));
        cards.add(new SetCardInfo("Devilthorn Fox", "SOI-14", Rarity.COMMON, mage.cards.d.DevilthornFox.class));
        cards.add(new SetCardInfo("Diabolic Edict", "A25-85", Rarity.COMMON, mage.cards.d.DiabolicEdict.class));
        cards.add(new SetCardInfo("Diamond Mare", "M19-231", Rarity.UNCOMMON, mage.cards.d.DiamondMare.class));
        cards.add(new SetCardInfo("Dictate of Erebos", "JOU-65", Rarity.RARE, mage.cards.d.DictateOfErebos.class));
        cards.add(new SetCardInfo("Dictate of Heliod", "DDO-8", Rarity.RARE, mage.cards.d.DictateOfHeliod.class));
        cards.add(new SetCardInfo("Die Young", "KLD-76", Rarity.COMMON, mage.cards.d.DieYoung.class));
        cards.add(new SetCardInfo("Diminish", "IMA-50", Rarity.COMMON, mage.cards.d.Diminish.class));
        cards.add(new SetCardInfo("Dinosaur Hunter", "RIX-67", Rarity.COMMON, mage.cards.d.DinosaurHunter.class));
        cards.add(new SetCardInfo("Direct Current", "GRN-96", Rarity.COMMON, mage.cards.d.DirectCurrent.class));
        cards.add(new SetCardInfo("Dirge of Dread", "A25-86", Rarity.COMMON, mage.cards.d.DirgeOfDread.class));
        cards.add(new SetCardInfo("Dirgur Nemesis", "DTK-51", Rarity.COMMON, mage.cards.d.DirgurNemesis.class));
        cards.add(new SetCardInfo("Disenchant", "P07-6", Rarity.COMMON, mage.cards.d.Disenchant.class));
        cards.add(new SetCardInfo("Dismal Backwater", "KTK-232", Rarity.COMMON, mage.cards.d.DismalBackwater.class));
        cards.add(new SetCardInfo("Dismantling Blow", "MH1-5", Rarity.UNCOMMON, mage.cards.d.DismantlingBlow.class));
        cards.add(new SetCardInfo("Dismember", "MM2-79", Rarity.UNCOMMON, mage.cards.d.Dismember.class));
        cards.add(new SetCardInfo("Disowned Ancestor", "KTK-70", Rarity.COMMON, mage.cards.d.DisownedAncestor.class));
        cards.add(new SetCardInfo("Dispel", "BFZ-76", Rarity.COMMON, mage.cards.d.Dispel.class));
        cards.add(new SetCardInfo("Displace", "EMN-55", Rarity.COMMON, mage.cards.d.Displace.class));
        cards.add(new SetCardInfo("Disposal Mummy", "HOU-9", Rarity.COMMON, mage.cards.d.DisposalMummy.class));
        cards.add(new SetCardInfo("Dissenter's Deliverance", "AKH-164", Rarity.COMMON, mage.cards.d.DissentersDeliverance.class));
        cards.add(new SetCardInfo("Distemper of the Blood", "EMN-126", Rarity.COMMON, mage.cards.d.DistemperOfTheBlood.class));
        cards.add(new SetCardInfo("Distortion Strike", "IMA-52", Rarity.UNCOMMON, mage.cards.d.DistortionStrike.class));
        cards.add(new SetCardInfo("Divination", "M19-51", Rarity.COMMON, mage.cards.d.Divination.class));
        cards.add(new SetCardInfo("Divine Favor", "M15-10", Rarity.COMMON, mage.cards.d.DivineFavor.class));
        cards.add(new SetCardInfo("Djeru's Renunciation", "HOU-11", Rarity.COMMON, mage.cards.d.DjerusRenunciation.class));
        cards.add(new SetCardInfo("Djeru's Resolve", "AKH-11", Rarity.COMMON, mage.cards.d.DjerusResolve.class));
        cards.add(new SetCardInfo("Djinn of Wishes", "C18-87", Rarity.RARE, mage.cards.d.DjinnOfWishes.class));
        cards.add(new SetCardInfo("Dolmen Gate", "LRW-256", Rarity.RARE, mage.cards.d.DolmenGate.class));
        cards.add(new SetCardInfo("Domesticated Hydra", "CN2-63", Rarity.UNCOMMON, mage.cards.d.DomesticatedHydra.class));
        cards.add(new SetCardInfo("Dominus of Fealty", "CMD-194", Rarity.RARE, mage.cards.d.DominusOfFealty.class));
        cards.add(new SetCardInfo("Doomed Dissenter", "AKH-87", Rarity.COMMON, mage.cards.d.DoomedDissenter.class));
        cards.add(new SetCardInfo("Doomed Traveler", "2X2-9", Rarity.COMMON, mage.cards.d.DoomedTraveler.class));
        cards.add(new SetCardInfo("Doomgape", "DDJ-65", Rarity.RARE, mage.cards.d.Doomgape.class));
        cards.add(new SetCardInfo("Doorkeeper", "IMA-53", Rarity.COMMON, mage.cards.d.Doorkeeper.class));
        cards.add(new SetCardInfo("Douse in Gloom", "FRF-68", Rarity.COMMON, mage.cards.d.DouseInGloom.class));
        cards.add(new SetCardInfo("Draco", "PLS-131", Rarity.RARE, mage.cards.d.Draco.class));
        cards.add(new SetCardInfo("Draconic Disciple", "M19-215", Rarity.UNCOMMON, mage.cards.d.DraconicDisciple.class));
        cards.add(new SetCardInfo("Drag Under", "W17-9", Rarity.COMMON, mage.cards.d.DragUnder.class));
        cards.add(new SetCardInfo("Dragon Bell Monk", "IMA-17", Rarity.COMMON, mage.cards.d.DragonBellMonk.class));
        cards.add(new SetCardInfo("Dragon Breath", "BBD-172", Rarity.UNCOMMON, mage.cards.d.DragonBreath.class));
        cards.add(new SetCardInfo("Dragon Broodmother", "ARB-53", Rarity.MYTHIC, mage.cards.d.DragonBroodmother.class));
        cards.add(new SetCardInfo("Dragon Egg", "IMA-124", Rarity.COMMON, mage.cards.d.DragonEgg.class));
        cards.add(new SetCardInfo("Dragon Fodder", "GNT-39", Rarity.COMMON, mage.cards.d.DragonFodder.class));
        cards.add(new SetCardInfo("Dragon Mask", "VIS-144", Rarity.UNCOMMON, mage.cards.d.DragonMask.class));
        cards.add(new SetCardInfo("Dragon Whelp", "CMA-81", Rarity.UNCOMMON, mage.cards.d.DragonWhelp.class));
        cards.add(new SetCardInfo("Dragon's Eye Savants", "KTK-38", Rarity.UNCOMMON, mage.cards.d.DragonsEyeSavants.class));
        cards.add(new SetCardInfo("Dragon's Eye Sentry", "DTK-11", Rarity.COMMON, mage.cards.d.DragonsEyeSentry.class));
        cards.add(new SetCardInfo("Dragon's Presence", "GS1-16", Rarity.COMMON, mage.cards.d.DragonsPresence.class));
        cards.add(new SetCardInfo("Dragon-Scarred Bear", "DTK-183", Rarity.COMMON, mage.cards.d.DragonScarredBear.class));
        cards.add(new SetCardInfo("Dragonlord Ojutai", "DTK-219", Rarity.MYTHIC, mage.cards.d.DragonlordOjutai.class));
        cards.add(new SetCardInfo("Dragonscale Boon", "KTK-131", Rarity.COMMON, mage.cards.d.DragonscaleBoon.class));
        cards.add(new SetCardInfo("Dragonsoul Knight", "MM2-112", Rarity.COMMON, mage.cards.d.DragonsoulKnight.class));
        cards.add(new SetCardInfo("Drana's Emissary", "BFZ-210", Rarity.UNCOMMON, mage.cards.d.DranasEmissary.class));
        cards.add(new SetCardInfo("Drana, Kalastria Bloodchief", "C17-112", Rarity.RARE, mage.cards.d.DranaKalastriaBloodchief.class));
        cards.add(new SetCardInfo("Dread Drone", "MM2-80", Rarity.COMMON, mage.cards.d.DreadDrone.class));
        cards.add(new SetCardInfo("Dread Return", "DDQ-55", Rarity.UNCOMMON, mage.cards.d.DreadReturn.class));
        cards.add(new SetCardInfo("Dreadbringer Lampads", "C15-122", Rarity.COMMON, mage.cards.d.DreadbringerLampads.class));
        cards.add(new SetCardInfo("Dreadship Reef", "CM2-247", Rarity.UNCOMMON, mage.cards.d.DreadshipReef.class));
        cards.add(new SetCardInfo("Dreadwaters", "ORI-56", Rarity.COMMON, mage.cards.d.Dreadwaters.class));
        cards.add(new SetCardInfo("Dream Cache", "C18-88", Rarity.COMMON, mage.cards.d.DreamCache.class));
        cards.add(new SetCardInfo("Dream Twist", "EMA-47", Rarity.COMMON, mage.cards.d.DreamTwist.class));
        cards.add(new SetCardInfo("Dregscape Zombie", "DDN-5", Rarity.COMMON, mage.cards.d.DregscapeZombie.class));
        cards.add(new SetCardInfo("Driver of the Dead", "CN2-133", Rarity.COMMON, mage.cards.d.DriverOfTheDead.class));
        cards.add(new SetCardInfo("Drudge Sentinel", "DOM-89", Rarity.COMMON, mage.cards.d.DrudgeSentinel.class));
        cards.add(new SetCardInfo("Dual Shot", "SOI-153", Rarity.COMMON, mage.cards.d.DualShot.class));
        cards.add(new SetCardInfo("Dukhara Scavenger", "KLD-77", Rarity.COMMON, mage.cards.d.DukharaScavenger.class));
        cards.add(new SetCardInfo("Dune Beetle", "AKH-89", Rarity.COMMON, mage.cards.d.DuneBeetle.class));
        cards.add(new SetCardInfo("Dungrove Elder", "M12-171", Rarity.RARE, mage.cards.d.DungroveElder.class));
        cards.add(new SetCardInfo("Duress", "EMA-86", Rarity.COMMON, mage.cards.d.Duress.class));
        cards.add(new SetCardInfo("Durkwood Baloth", "IMA-160", Rarity.COMMON, mage.cards.d.DurkwoodBaloth.class));
        cards.add(new SetCardInfo("Dusk Charger", "RIX-69", Rarity.COMMON, mage.cards.d.DuskCharger.class));
        cards.add(new SetCardInfo("Dusk Legion Zealot", "RIX-70", Rarity.COMMON, mage.cards.d.DuskLegionZealot.class));
        cards.add(new SetCardInfo("Dynacharge", "MM3-94", Rarity.COMMON, mage.cards.d.Dynacharge.class));
        cards.add(new SetCardInfo("Earth Elemental", "BBD-174", Rarity.COMMON, mage.cards.e.EarthElemental.class));
        cards.add(new SetCardInfo("Earthen Arms", "BFZ-172", Rarity.COMMON, mage.cards.e.EarthenArms.class));
        cards.add(new SetCardInfo("Eater of Days", "DST-120", Rarity.RARE, mage.cards.e.EaterOfDays.class));
        cards.add(new SetCardInfo("Eddytrail Hawk", "KLD-12", Rarity.COMMON, mage.cards.e.EddytrailHawk.class));
        cards.add(new SetCardInfo("Eel Umbra", "C18-89", Rarity.COMMON, mage.cards.e.EelUmbra.class));
        cards.add(new SetCardInfo("Eldrazi Devastator", "BFZ-7", Rarity.COMMON, mage.cards.e.EldraziDevastator.class));
        cards.add(new SetCardInfo("Eldrazi Monument", "CMA-216", Rarity.MYTHIC, mage.cards.e.EldraziMonument.class));
        cards.add(new SetCardInfo("Eldritch Evolution", "EMN-155", Rarity.RARE, mage.cards.e.EldritchEvolution.class));
        cards.add(new SetCardInfo("Elemental Uprising", "OGW-130", Rarity.COMMON, mage.cards.e.ElementalUprising.class));
        cards.add(new SetCardInfo("Elephant Guide", "EMA-163", Rarity.COMMON, mage.cards.e.ElephantGuide.class));
        cards.add(new SetCardInfo("Elesh Norn, Grand Cenobite", "IMA-18", Rarity.MYTHIC, mage.cards.e.EleshNornGrandCenobite.class));
        cards.add(new SetCardInfo("Elixir of Immortality", "M11-206", Rarity.UNCOMMON, mage.cards.e.ElixirOfImmortality.class));
        cards.add(new SetCardInfo("Elves of Deep Shadow", "GK1-56", Rarity.COMMON, mage.cards.e.ElvesOfDeepShadow.class));
        cards.add(new SetCardInfo("Elvish Fury", "MH1-162", Rarity.COMMON, mage.cards.e.ElvishFury.class));
        cards.add(new SetCardInfo("Elvish Visionary", "BBD-196", Rarity.COMMON, mage.cards.e.ElvishVisionary.class));
        cards.add(new SetCardInfo("Elvish Warrior", "DD1-5", Rarity.COMMON, mage.cards.e.ElvishWarrior.class));
        cards.add(new SetCardInfo("Ember Weaver", "A25-169", Rarity.COMMON, mage.cards.e.EmberWeaver.class));
        cards.add(new SetCardInfo("Embodiment of Spring", "KTK-39", Rarity.COMMON, mage.cards.e.EmbodimentOfSpring.class));
        cards.add(new SetCardInfo("Emerge Unscathed", "IMA-19", Rarity.COMMON, mage.cards.e.EmergeUnscathed.class));
        cards.add(new SetCardInfo("Emmessi Tome", "EMA-221", Rarity.UNCOMMON, mage.cards.e.EmmessiTome.class));
        cards.add(new SetCardInfo("Empyrial Armor", "WTH-13", Rarity.COMMON, mage.cards.e.EmpyrialArmor.class));
        cards.add(new SetCardInfo("Emrakul's Hatcher", "DDP-59", Rarity.COMMON, mage.cards.e.EmrakulsHatcher.class));
        cards.add(new SetCardInfo("Encampment Keeper", "XLN-11", Rarity.COMMON, mage.cards.e.EncampmentKeeper.class));
        cards.add(new SetCardInfo("Enchanted Evening", "SHM-140", Rarity.RARE, mage.cards.e.EnchantedEvening.class));
        cards.add(new SetCardInfo("Encircling Fissure", "BFZ-23", Rarity.UNCOMMON, mage.cards.e.EncirclingFissure.class));
        cards.add(new SetCardInfo("Enduring Victory", "DTK-16", Rarity.COMMON, mage.cards.e.EnduringVictory.class));
        cards.add(new SetCardInfo("Energy Field", "USG-73", Rarity.RARE, mage.cards.e.EnergyField.class));
        cards.add(new SetCardInfo("Engineered Might", "KLD-181", Rarity.UNCOMMON, mage.cards.e.EngineeredMight.class));
        cards.add(new SetCardInfo("Enlightened Ascetic", "ORI-12", Rarity.COMMON, mage.cards.e.EnlightenedAscetic.class));
        cards.add(new SetCardInfo("Enlightened Maniac", "EMN-58", Rarity.COMMON, mage.cards.e.EnlightenedManiac.class));
        cards.add(new SetCardInfo("Ensoul Artifact", "M15-54", Rarity.UNCOMMON, mage.cards.e.EnsoulArtifact.class));
        cards.add(new SetCardInfo("Enthralling Victor", "BBD-176", Rarity.UNCOMMON, mage.cards.e.EnthrallingVictor.class));
        cards.add(new SetCardInfo("Ephemeral Shields", "M15-11", Rarity.COMMON, mage.cards.e.EphemeralShields.class));
        cards.add(new SetCardInfo("Ephemerate", "MH1-7", Rarity.COMMON, mage.cards.e.Ephemerate.class));
        cards.add(new SetCardInfo("Epic Confrontation", "DTK-185", Rarity.COMMON, mage.cards.e.EpicConfrontation.class));
        cards.add(new SetCardInfo("Epicure of Blood", "M19-95", Rarity.COMMON, mage.cards.e.EpicureOfBlood.class));
        cards.add(new SetCardInfo("Erg Raiders", "A25-90", Rarity.COMMON, mage.cards.e.ErgRaiders.class));
        cards.add(new SetCardInfo("Errant Ephemeron", "DD2-20", Rarity.COMMON, mage.cards.e.ErrantEphemeron.class));
        cards.add(new SetCardInfo("Erratic Explosion", "PCA-41", Rarity.COMMON, mage.cards.e.ErraticExplosion.class));
        cards.add(new SetCardInfo("Esper Charm", "C18-179", Rarity.UNCOMMON, mage.cards.e.EsperCharm.class));
        cards.add(new SetCardInfo("Essence Scatter", "M19-54", Rarity.COMMON, mage.cards.e.EssenceScatter.class));
        cards.add(new SetCardInfo("Essence Warden", "CMA-106", Rarity.COMMON, mage.cards.e.EssenceWarden.class));
        cards.add(new SetCardInfo("Etched Oracle", "C16-252", Rarity.UNCOMMON, mage.cards.e.EtchedOracle.class));
        cards.add(new SetCardInfo("Eternal Thirst", "IMA-89", Rarity.COMMON, mage.cards.e.EternalThirst.class));
        cards.add(new SetCardInfo("Eternal Witness", "5DN-86", Rarity.UNCOMMON, mage.cards.e.EternalWitness.class));
        cards.add(new SetCardInfo("Ethercaste Knight", "MM2-175", Rarity.UNCOMMON, mage.cards.e.EthercasteKnight.class));
        cards.add(new SetCardInfo("Ethereal Ambush", "FRF-152", Rarity.COMMON, mage.cards.e.EtherealAmbush.class));
        cards.add(new SetCardInfo("Everdream", "MH1-47", Rarity.UNCOMMON, mage.cards.e.Everdream.class));
        cards.add(new SetCardInfo("Evincar's Justice", "CMA-58", Rarity.COMMON, mage.cards.e.EvincarsJustice.class));
        cards.add(new SetCardInfo("Evolving Wilds", "RIX-186", Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Evra, Halcyon Witness", "DOM-16", Rarity.RARE, mage.cards.e.EvraHalcyonWitness.class));
        cards.add(new SetCardInfo("Excavation Elephant", "DOM-17", Rarity.COMMON, mage.cards.e.ExcavationElephant.class));
        cards.add(new SetCardInfo("Exclude", "MH1-48", Rarity.UNCOMMON, mage.cards.e.Exclude.class));
        cards.add(new SetCardInfo("Excoriate", "E01-5", Rarity.COMMON, mage.cards.e.Excoriate.class));
        cards.add(new SetCardInfo("Executioner's Capsule", "C16-109", Rarity.COMMON, mage.cards.e.ExecutionersCapsule.class));
        cards.add(new SetCardInfo("Expedite", "BBD-177", Rarity.COMMON, mage.cards.e.Expedite.class));
        cards.add(new SetCardInfo("Expedition Raptor", "BBD-92", Rarity.COMMON, mage.cards.e.ExpeditionRaptor.class));
        cards.add(new SetCardInfo("Experiment One", "C15-184", Rarity.UNCOMMON, mage.cards.e.ExperimentOne.class));
        cards.add(new SetCardInfo("Explore", "C18-133", Rarity.COMMON, mage.cards.e.Explore.class));
        cards.add(new SetCardInfo("Explosive Vegetation", "C18-144", Rarity.UNCOMMON, mage.cards.e.ExplosiveVegetation.class));
        cards.add(new SetCardInfo("Expose Evil", "SOI-19", Rarity.COMMON, mage.cards.e.ExposeEvil.class));
        cards.add(new SetCardInfo("Expropriate", "CN2-30", Rarity.MYTHIC, mage.cards.e.Expropriate.class));
        cards.add(new SetCardInfo("Exsanguinate", "SOM-61", Rarity.UNCOMMON, mage.cards.e.Exsanguinate.class));
        cards.add(new SetCardInfo("Extract from Darkness", "E01-84", Rarity.UNCOMMON, mage.cards.e.ExtractFromDarkness.class));
        cards.add(new SetCardInfo("Exultant Skymarcher", "RIX-7", Rarity.COMMON, mage.cards.e.ExultantSkymarcher.class));
        cards.add(new SetCardInfo("Eyeblight's Ending", "EMA-88", Rarity.COMMON, mage.cards.e.EyeblightsEnding.class));
        cards.add(new SetCardInfo("Eyes in the Skies", "MM3-5", Rarity.COMMON, mage.cards.e.EyesInTheSkies.class));
        cards.add(new SetCardInfo("Ezuri's Archers", "DDU-9", Rarity.COMMON, mage.cards.e.EzurisArchers.class));
        cards.add(new SetCardInfo("Fact or Fiction", "MH2-219", Rarity.UNCOMMON, mage.cards.f.FactOrFiction.class));
        cards.add(new SetCardInfo("Fade into Antiquity", "CN2-181", Rarity.COMMON, mage.cards.f.FadeIntoAntiquity.class));
        cards.add(new SetCardInfo("Faerie Conclave", "CMA-248", Rarity.UNCOMMON, mage.cards.f.FaerieConclave.class));
        cards.add(new SetCardInfo("Faerie Invaders", "DDN-57", Rarity.COMMON, mage.cards.f.FaerieInvaders.class));
        cards.add(new SetCardInfo("Faerie Mechanist", "DDU-38", Rarity.COMMON, mage.cards.f.FaerieMechanist.class));
        cards.add(new SetCardInfo("Failed Inspection", "KLD-47", Rarity.COMMON, mage.cards.f.FailedInspection.class));
        cards.add(new SetCardInfo("Faith's Fetters", "UMA-16", Rarity.COMMON, mage.cards.f.FaithsFetters.class));
        cards.add(new SetCardInfo("Faithbearer Paladin", "EMN-25", Rarity.COMMON, mage.cards.f.FaithbearerPaladin.class));
        cards.add(new SetCardInfo("Faithless Looting", "DDK-59", Rarity.COMMON, mage.cards.f.FaithlessLooting.class));
        cards.add(new SetCardInfo("Falkenrath Reaver", "W17-21", Rarity.COMMON, mage.cards.f.FalkenrathReaver.class));
        cards.add(new SetCardInfo("Fall of the Hammer", "CM2-97", Rarity.COMMON, mage.cards.f.FallOfTheHammer.class));
        cards.add(new SetCardInfo("Fallen Angel", "A25-91", Rarity.UNCOMMON, mage.cards.f.FallenAngel.class));
        cards.add(new SetCardInfo("Farbog Revenant", "SOI-110", Rarity.COMMON, mage.cards.f.FarbogRevenant.class));
        cards.add(new SetCardInfo("Farmstead Gleaner", "MH1-222", Rarity.UNCOMMON, mage.cards.f.FarmsteadGleaner.class));
        cards.add(new SetCardInfo("Farseek", "NCC-290", Rarity.COMMON, mage.cards.f.Farseek.class));
        cards.add(new SetCardInfo("Fascination", "FRF-34", Rarity.UNCOMMON, mage.cards.f.Fascination.class));
        cards.add(new SetCardInfo("Fatal Push", "AER-57", Rarity.UNCOMMON, mage.cards.f.FatalPush.class));
        cards.add(new SetCardInfo("Fathom Seer", "C14-109", Rarity.COMMON, mage.cards.f.FathomSeer.class));
        cards.add(new SetCardInfo("Fblthp, the Lost", "WAR-50", Rarity.RARE, mage.cards.f.FblthpTheLost.class));
        cards.add(new SetCardInfo("Feat of Resistance", "KTK-10", Rarity.COMMON, mage.cards.f.FeatOfResistance.class));
        cards.add(new SetCardInfo("Feed the Clan", "KTK-132", Rarity.COMMON, mage.cards.f.FeedTheClan.class));
        cards.add(new SetCardInfo("Felidar Guardian", "AER-19", Rarity.UNCOMMON, mage.cards.f.FelidarGuardian.class));
        cards.add(new SetCardInfo("Felidar Sovereign", "BFZ-26", Rarity.RARE, mage.cards.f.FelidarSovereign.class));
        cards.add(new SetCardInfo("Felidar Umbra", "PCA-6", Rarity.UNCOMMON, mage.cards.f.FelidarUmbra.class));
        cards.add(new SetCardInfo("Fen Hauler", "AER-58", Rarity.COMMON, mage.cards.f.FenHauler.class));
        cards.add(new SetCardInfo("Fencing Ace", "A25-13", Rarity.COMMON, mage.cards.f.FencingAce.class));
        cards.add(new SetCardInfo("Feral Abomination", "DOM-92", Rarity.COMMON, mage.cards.f.FeralAbomination.class));
        cards.add(new SetCardInfo("Feral Krushok", "FRF-128", Rarity.COMMON, mage.cards.f.FeralKrushok.class));
        cards.add(new SetCardInfo("Feral Prowler", "HOU-115", Rarity.COMMON, mage.cards.f.FeralProwler.class));
        cards.add(new SetCardInfo("Ferocious Zheng", "GS1-28", Rarity.COMMON, mage.cards.f.FerociousZheng.class));
        cards.add(new SetCardInfo("Fertile Ground", "C18-147", Rarity.COMMON, mage.cards.f.FertileGround.class));
        cards.add(new SetCardInfo("Fervent Strike", "DOM-117", Rarity.COMMON, mage.cards.f.FerventStrike.class));
        cards.add(new SetCardInfo("Festercreep", "CM2-63", Rarity.COMMON, mage.cards.f.Festercreep.class));
        cards.add(new SetCardInfo("Festering Newt", "IMA-90", Rarity.COMMON, mage.cards.f.FesteringNewt.class));
        cards.add(new SetCardInfo("Fetid Imp", "ORI-97", Rarity.COMMON, mage.cards.f.FetidImp.class));
        cards.add(new SetCardInfo("Field of Ruin", "XLN-254", Rarity.UNCOMMON, mage.cards.f.FieldOfRuin.class));
        cards.add(new SetCardInfo("Fiend Hunter", "CMA-10", Rarity.UNCOMMON, mage.cards.f.FiendHunter.class));
        cards.add(new SetCardInfo("Fierce Empath", "DDU-10", Rarity.COMMON, mage.cards.f.FierceEmpath.class));
        cards.add(new SetCardInfo("Fierce Invocation", "FRF-98", Rarity.COMMON, mage.cards.f.FierceInvocation.class));
        cards.add(new SetCardInfo("Fiery Hellhound", "ORI-284", Rarity.COMMON, mage.cards.f.FieryHellhound.class));
        cards.add(new SetCardInfo("Fiery Temper", "SOI-156", Rarity.COMMON, mage.cards.f.FieryTemper.class));
        cards.add(new SetCardInfo("Filigree Familiar", "GNT-52", Rarity.UNCOMMON, mage.cards.f.FiligreeFamiliar.class));
        cards.add(new SetCardInfo("Fill with Fright", "BBD-144", Rarity.COMMON, mage.cards.f.FillWithFright.class));
        cards.add(new SetCardInfo("Fire // Ice", "UMA-225", Rarity.COMMON, mage.cards.f.FireIce.class));
        cards.add(new SetCardInfo("Fire Elemental", "M19-141", Rarity.COMMON, mage.cards.f.FireElemental.class));
        cards.add(new SetCardInfo("Fireball", "DD2-56", Rarity.UNCOMMON, mage.cards.f.Fireball.class));
        cards.add(new SetCardInfo("Firebolt", "M15-130", Rarity.COMMON, mage.cards.f.Firebolt.class));
        cards.add(new SetCardInfo("Firebrand Archer", "HOU-92", Rarity.COMMON, mage.cards.f.FirebrandArcher.class));
        cards.add(new SetCardInfo("Firehoof Cavalry", "KTK-11", Rarity.COMMON, mage.cards.f.FirehoofCavalry.class));
        cards.add(new SetCardInfo("Fires of Yavimaya", "PCA-92", Rarity.UNCOMMON, mage.cards.f.FiresOfYavimaya.class));
        cards.add(new SetCardInfo("First-Sphere Gargantua", "MH1-91", Rarity.COMMON, mage.cards.f.FirstSphereGargantua.class));
        cards.add(new SetCardInfo("Flame Jab", "EMA-131", Rarity.UNCOMMON, mage.cards.f.FlameJab.class));
        cards.add(new SetCardInfo("Flame-Kin Zealot", "EMA-201", Rarity.UNCOMMON, mage.cards.f.FlameKinZealot.class));
        cards.add(new SetCardInfo("Flameshot", "PCY-90", Rarity.UNCOMMON, mage.cards.f.Flameshot.class));
        cards.add(new SetCardInfo("Flametongue Kavu", "E01-48", Rarity.UNCOMMON, mage.cards.f.FlametongueKavu.class));
        cards.add(new SetCardInfo("Flamewave Invoker", "BBD-178", Rarity.UNCOMMON, mage.cards.f.FlamewaveInvoker.class));
        cards.add(new SetCardInfo("Flashfreeze", "MM2-45", Rarity.UNCOMMON, mage.cards.f.Flashfreeze.class));
        cards.add(new SetCardInfo("Flayer Husk", "PCA-110", Rarity.COMMON, mage.cards.f.FlayerHusk.class));
        cards.add(new SetCardInfo("Fledgling Mawcor", "DD2-10", Rarity.UNCOMMON, mage.cards.f.FledglingMawcor.class));
        cards.add(new SetCardInfo("Fleeting Distraction", "CN2-110", Rarity.COMMON, mage.cards.f.FleetingDistraction.class));
        cards.add(new SetCardInfo("Flesh to Dust", "ORI-280", Rarity.COMMON, mage.cards.f.FleshToDust.class));
        cards.add(new SetCardInfo("Fling", "EVG-33", Rarity.COMMON, mage.cards.f.Fling.class));
        cards.add(new SetCardInfo("Floodgate", "MIR-67", Rarity.UNCOMMON, mage.cards.f.Floodgate.class));
        cards.add(new SetCardInfo("Fog", "EMA-167", Rarity.COMMON, mage.cards.f.Fog.class));
        cards.add(new SetCardInfo("Fog Bank", "BBD-117", Rarity.UNCOMMON, mage.cards.f.FogBank.class));
        cards.add(new SetCardInfo("Fogwalker", "EMN-60", Rarity.COMMON, mage.cards.f.Fogwalker.class));
        cards.add(new SetCardInfo("Foil", "UMA-55", Rarity.COMMON, mage.cards.f.Foil.class));
        cards.add(new SetCardInfo("Font of Mythos", "CON-136", Rarity.RARE, mage.cards.f.FontOfMythos.class));
        cards.add(new SetCardInfo("Forbidden Alchemy", "MM3-38", Rarity.COMMON, mage.cards.f.ForbiddenAlchemy.class));
        cards.add(new SetCardInfo("Forge Devil", "M15-140", Rarity.COMMON, mage.cards.f.ForgeDevil.class));
        cards.add(new SetCardInfo("Forgotten Cave", "C18-246", Rarity.COMMON, mage.cards.f.ForgottenCave.class));
        cards.add(new SetCardInfo("Formless Nurturing", "FRF-129", Rarity.COMMON, mage.cards.f.FormlessNurturing.class));
        cards.add(new SetCardInfo("Forsake the Worldly", "AKH-13", Rarity.COMMON, mage.cards.f.ForsakeTheWorldly.class));
        cards.add(new SetCardInfo("Fortify", "MM2-17", Rarity.COMMON, mage.cards.f.Fortify.class));
        cards.add(new SetCardInfo("Foundry Inspector", "KLD-215", Rarity.UNCOMMON, mage.cards.f.FoundryInspector.class));
        cards.add(new SetCardInfo("Foundry Street Denizen", "M15-141", Rarity.COMMON, mage.cards.f.FoundryStreetDenizen.class));
        cards.add(new SetCardInfo("Fountain of Renewal", "M19-235", Rarity.UNCOMMON, mage.cards.f.FountainOfRenewal.class));
        cards.add(new SetCardInfo("Fragmentize", "KLD-14", Rarity.COMMON, mage.cards.f.Fragmentize.class));
        cards.add(new SetCardInfo("Frantic Search", "UMA-57", Rarity.COMMON, mage.cards.f.FranticSearch.class));
        cards.add(new SetCardInfo("Frenzied Raptor", "XLN-146", Rarity.COMMON, mage.cards.f.FrenziedRaptor.class));
        cards.add(new SetCardInfo("Fretwork Colony", "KLD-83", Rarity.UNCOMMON, mage.cards.f.FretworkColony.class));
        cards.add(new SetCardInfo("Frilled Deathspitter", "RIX-104", Rarity.COMMON, mage.cards.f.FrilledDeathspitter.class));
        cards.add(new SetCardInfo("Frilled Sea Serpent", "M19-56", Rarity.COMMON, mage.cards.f.FrilledSeaSerpent.class));
        cards.add(new SetCardInfo("Frogmite", "MM2-215", Rarity.COMMON, mage.cards.f.Frogmite.class));
        cards.add(new SetCardInfo("Frontier Bivouac", "KTK-234", Rarity.UNCOMMON, mage.cards.f.FrontierBivouac.class));
        cards.add(new SetCardInfo("Frontier Mastodon", "FRF-130", Rarity.COMMON, mage.cards.f.FrontierMastodon.class));
        cards.add(new SetCardInfo("Frontline Devastator", "HOU-93", Rarity.COMMON, mage.cards.f.FrontlineDevastator.class));
        cards.add(new SetCardInfo("Frontline Rebel", "AER-82", Rarity.COMMON, mage.cards.f.FrontlineRebel.class));
        cards.add(new SetCardInfo("Frost Lynx", "BBD-118", Rarity.COMMON, mage.cards.f.FrostLynx.class));
        cards.add(new SetCardInfo("Fungal Infection", "DOM-94", Rarity.COMMON, mage.cards.f.FungalInfection.class));
        cards.add(new SetCardInfo("Furnace Whelp", "IMA-129", Rarity.COMMON, mage.cards.f.FurnaceWhelp.class));
        cards.add(new SetCardInfo("Fury Charm", "IMA-130", Rarity.COMMON, mage.cards.f.FuryCharm.class));
        cards.add(new SetCardInfo("Fusion Elemental", "PCA-93", Rarity.UNCOMMON, mage.cards.f.FusionElemental.class));
        cards.add(new SetCardInfo("Gaea's Blessing", "DOM-161", Rarity.UNCOMMON, mage.cards.g.GaeasBlessing.class));
        cards.add(new SetCardInfo("Gaea's Protector", "DOM-162", Rarity.COMMON, mage.cards.g.GaeasProtector.class));
        cards.add(new SetCardInfo("Galvanic Blast", "DDU-45", Rarity.COMMON, mage.cards.g.GalvanicBlast.class));
        cards.add(new SetCardInfo("Gaseous Form", "EMA-51", Rarity.COMMON, mage.cards.g.GaseousForm.class));
        cards.add(new SetCardInfo("Gateway Plaza", "GRN-247", Rarity.COMMON, mage.cards.g.GatewayPlaza.class));
        cards.add(new SetCardInfo("Geist of the Moors", "A25-15", Rarity.COMMON, mage.cards.g.GeistOfTheMoors.class));
        cards.add(new SetCardInfo("Gelectrode", "GK1-38", Rarity.UNCOMMON, mage.cards.g.Gelectrode.class));
        cards.add(new SetCardInfo("Generator Servant", "M15-143", Rarity.COMMON, mage.cards.g.GeneratorServant.class));
        cards.add(new SetCardInfo("Genju of the Fens", "DDD-47", Rarity.UNCOMMON, mage.cards.g.GenjuOfTheFens.class));
        cards.add(new SetCardInfo("Genju of the Spires", "A25-132", Rarity.UNCOMMON, mage.cards.g.GenjuOfTheSpires.class));
        cards.add(new SetCardInfo("Geomancer's Gambit", "MH1-125", Rarity.COMMON, mage.cards.g.GeomancersGambit.class));
        cards.add(new SetCardInfo("Ghitu Lavarunner", "DOM-127", Rarity.COMMON, mage.cards.g.GhituLavarunner.class));
        cards.add(new SetCardInfo("Ghitu War Cry", "ULG-78", Rarity.UNCOMMON, mage.cards.g.GhituWarCry.class));
        cards.add(new SetCardInfo("Ghor-Clan Rampager", "MM3-165", Rarity.UNCOMMON, mage.cards.g.GhorClanRampager.class));
        cards.add(new SetCardInfo("Ghost Quarter", "DIS-173", Rarity.UNCOMMON, mage.cards.g.GhostQuarter.class));
        cards.add(new SetCardInfo("Ghost Ship", "A25-60", Rarity.COMMON, mage.cards.g.GhostShip.class));
        cards.add(new SetCardInfo("Ghostblade Eidolon", "C15-70", Rarity.UNCOMMON, mage.cards.g.GhostbladeEidolon.class));
        cards.add(new SetCardInfo("Ghostly Changeling", "MM2-83", Rarity.COMMON, mage.cards.g.GhostlyChangeling.class));
        cards.add(new SetCardInfo("Ghoulcaller's Accomplice", "SOI-112", Rarity.COMMON, mage.cards.g.GhoulcallersAccomplice.class));
        cards.add(new SetCardInfo("Giant Growth", "BBD-200", Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Giant Spectacle", "KLD-116", Rarity.COMMON, mage.cards.g.GiantSpectacle.class));
        cards.add(new SetCardInfo("Giant Spider", "AKH-166", Rarity.COMMON, mage.cards.g.GiantSpider.class));
        cards.add(new SetCardInfo("Giantbaiting", "MM3-208", Rarity.COMMON, mage.cards.g.Giantbaiting.class));
        cards.add(new SetCardInfo("Gideon Jura", "E01-10", Rarity.MYTHIC, mage.cards.g.GideonJura.class));
        cards.add(new SetCardInfo("Gideon's Lawkeeper", "MM3-7", Rarity.COMMON, mage.cards.g.GideonsLawkeeper.class));
        cards.add(new SetCardInfo("Gift of Estates", "C14-73", Rarity.UNCOMMON, mage.cards.g.GiftOfEstates.class));
        cards.add(new SetCardInfo("Gift of Growth", "DOM-163", Rarity.COMMON, mage.cards.g.GiftOfGrowth.class));
        cards.add(new SetCardInfo("Gift of Orzhova", "MM3-209", Rarity.COMMON, mage.cards.g.GiftOfOrzhova.class));
        cards.add(new SetCardInfo("Gift of Paradise", "AKH-167", Rarity.COMMON, mage.cards.g.GiftOfParadise.class));
        cards.add(new SetCardInfo("Gifted Aetherborn", "AER-61", Rarity.UNCOMMON, mage.cards.g.GiftedAetherborn.class));
        cards.add(new SetCardInfo("Gilt-Leaf Palace", "LRW-268", Rarity.RARE, mage.cards.g.GiltLeafPalace.class));
        cards.add(new SetCardInfo("Glacial Crasher", "M15-57", Rarity.COMMON, mage.cards.g.GlacialCrasher.class));
        cards.add(new SetCardInfo("Glade Watcher", "DTK-188", Rarity.COMMON, mage.cards.g.GladeWatcher.class));
        cards.add(new SetCardInfo("Glaring Aegis", "DTK-18", Rarity.COMMON, mage.cards.g.GlaringAegis.class));
        cards.add(new SetCardInfo("Gleam of Resistance", "CN2-87", Rarity.COMMON, mage.cards.g.GleamOfResistance.class));
        cards.add(new SetCardInfo("Glint", "DTK-55", Rarity.COMMON, mage.cards.g.Glint.class));
        cards.add(new SetCardInfo("Glint-Sleeve Artisan", "KLD-17", Rarity.COMMON, mage.cards.g.GlintSleeveArtisan.class));
        cards.add(new SetCardInfo("Gnarlid Pack", "MM2-144", Rarity.COMMON, mage.cards.g.GnarlidPack.class));
        cards.add(new SetCardInfo("Go for the Throat", "C17-114", Rarity.UNCOMMON, mage.cards.g.GoForTheThroat.class));
        cards.add(new SetCardInfo("Goblin Assault", "MM3-95", Rarity.UNCOMMON, mage.cards.g.GoblinAssault.class));
        cards.add(new SetCardInfo("Goblin Balloon Brigade", "CN2-159", Rarity.COMMON, mage.cards.g.GoblinBalloonBrigade.class));
        cards.add(new SetCardInfo("Goblin Bombardment", "DDN-24", Rarity.UNCOMMON, mage.cards.g.GoblinBombardment.class));
        cards.add(new SetCardInfo("Goblin Burrows", "DD1-58", Rarity.UNCOMMON, mage.cards.g.GoblinBurrows.class));
        cards.add(new SetCardInfo("Goblin Charbelcher", "DDT-57", Rarity.RARE, mage.cards.g.GoblinCharbelcher.class));
        cards.add(new SetCardInfo("Goblin Deathraiders", "DDN-6", Rarity.COMMON, mage.cards.g.GoblinDeathraiders.class));
        cards.add(new SetCardInfo("Goblin Fireslinger", "MM2-114", Rarity.COMMON, mage.cards.g.GoblinFireslinger.class));
        cards.add(new SetCardInfo("Goblin Game", "PLS-61", Rarity.RARE, mage.cards.g.GoblinGame.class));
        cards.add(new SetCardInfo("Goblin Locksmith", "GRN-104", Rarity.COMMON, mage.cards.g.GoblinLocksmith.class));
        cards.add(new SetCardInfo("Goblin Matron", "MH1-129", Rarity.UNCOMMON, mage.cards.g.GoblinMatron.class));
        cards.add(new SetCardInfo("Goblin Motivator", "M19-143", Rarity.COMMON, mage.cards.g.GoblinMotivator.class));
        cards.add(new SetCardInfo("Goblin Oriflamme", "MH1-130", Rarity.UNCOMMON, mage.cards.g.GoblinOriflamme.class));
        cards.add(new SetCardInfo("Goblin Piledriver", "ORI-151", Rarity.RARE, mage.cards.g.GoblinPiledriver.class));
        cards.add(new SetCardInfo("Goblin Roughrider", "M15-146", Rarity.COMMON, mage.cards.g.GoblinRoughrider.class));
        // Goblin Trenches is not in the collation booster, as it was only added for the second Mystery Booster.
        cards.add(new SetCardInfo("Goblin Trenches", "EMA-203", Rarity.RARE, mage.cards.g.GoblinTrenches.class));
        cards.add(new SetCardInfo("Goblin War Paint", "BFZ-146", Rarity.COMMON, mage.cards.g.GoblinWarPaint.class));
        cards.add(new SetCardInfo("Goblin Warchief", "DOM-130", Rarity.UNCOMMON, mage.cards.g.GoblinWarchief.class));
        cards.add(new SetCardInfo("God-Pharaoh's Faithful", "HOU-14", Rarity.COMMON, mage.cards.g.GodPharaohsFaithful.class));
        cards.add(new SetCardInfo("Gods Willing", "UMA-18", Rarity.COMMON, mage.cards.g.GodsWilling.class));
        cards.add(new SetCardInfo("Gone Missing", "SOI-67", Rarity.COMMON, mage.cards.g.GoneMissing.class));
        cards.add(new SetCardInfo("Gonti, Lord of Luxury", "KLD-84", Rarity.RARE, mage.cards.g.GontiLordOfLuxury.class));
        cards.add(new SetCardInfo("Gore Swine", "FRF-103", Rarity.COMMON, mage.cards.g.GoreSwine.class));
        cards.add(new SetCardInfo("Gorehorn Minotaurs", "E01-49", Rarity.COMMON, mage.cards.g.GorehornMinotaurs.class));
        cards.add(new SetCardInfo("Granitic Titan", "HOU-95", Rarity.COMMON, mage.cards.g.GraniticTitan.class));
        cards.add(new SetCardInfo("Grapeshot", "DDS-16", Rarity.COMMON, mage.cards.g.Grapeshot.class));
        cards.add(new SetCardInfo("Grapple with the Past", "C18-148", Rarity.COMMON, mage.cards.g.GrappleWithThePast.class));
        cards.add(new SetCardInfo("Grasp of Fate", "C15-3", Rarity.RARE, mage.cards.g.GraspOfFate.class));
        cards.add(new SetCardInfo("Grasp of Phantoms", "MM3-41", Rarity.COMMON, mage.cards.g.GraspOfPhantoms.class));
        cards.add(new SetCardInfo("Grasp of the Hieromancer", "E01-13", Rarity.COMMON, mage.cards.g.GraspOfTheHieromancer.class));
        cards.add(new SetCardInfo("Grasping Scoundrel", "RIX-74", Rarity.COMMON, mage.cards.g.GraspingScoundrel.class));
        cards.add(new SetCardInfo("Grave Titan", "C14-145", Rarity.MYTHIC, mage.cards.g.GraveTitan.class));
        cards.add(new SetCardInfo("Gravecrawler", "DDQ-59", Rarity.RARE, mage.cards.g.Gravecrawler.class));
        cards.add(new SetCardInfo("Gravedigger", "CM2-66", Rarity.COMMON, mage.cards.g.Gravedigger.class));
        cards.add(new SetCardInfo("Gravepurge", "DTK-104", Rarity.COMMON, mage.cards.g.Gravepurge.class));
        cards.add(new SetCardInfo("Gravitic Punch", "GRN-105", Rarity.COMMON, mage.cards.g.GraviticPunch.class));
        cards.add(new SetCardInfo("Gray Merchant of Asphodel", "C14-146", Rarity.COMMON, mage.cards.g.GrayMerchantOfAsphodel.class));
        cards.add(new SetCardInfo("Graypelt Refuge", "C17-253", Rarity.UNCOMMON, mage.cards.g.GraypeltRefuge.class));
        cards.add(new SetCardInfo("Grazing Gladehart", "DDP-14", Rarity.COMMON, mage.cards.g.GrazingGladehart.class));
        cards.add(new SetCardInfo("Great Furnace", "MRD-282", Rarity.COMMON, mage.cards.g.GreatFurnace.class));
        cards.add(new SetCardInfo("Great-Horn Krushok", "FRF-13", Rarity.COMMON, mage.cards.g.GreatHornKrushok.class));
        cards.add(new SetCardInfo("Greater Basilisk", "IMA-165", Rarity.COMMON, mage.cards.g.GreaterBasilisk.class));
        cards.add(new SetCardInfo("Greater Gargadon", "MMA-117", Rarity.RARE, mage.cards.g.GreaterGargadon.class));
        cards.add(new SetCardInfo("Greater Sandwurm", "AKH-168", Rarity.COMMON, mage.cards.g.GreaterSandwurm.class));
        cards.add(new SetCardInfo("Greenbelt Rampager", "AER-107", Rarity.RARE, mage.cards.g.GreenbeltRampager.class));
        cards.add(new SetCardInfo("Greenwood Sentinel", "M19-187", Rarity.COMMON, mage.cards.g.GreenwoodSentinel.class));
        cards.add(new SetCardInfo("Grim Affliction", "MM2-84", Rarity.COMMON, mage.cards.g.GrimAffliction.class));
        cards.add(new SetCardInfo("Grim Contest", "FRF-153", Rarity.COMMON, mage.cards.g.GrimContest.class));
        cards.add(new SetCardInfo("Grim Discovery", "DDR-51", Rarity.COMMON, mage.cards.g.GrimDiscovery.class));
        cards.add(new SetCardInfo("Grixis Slavedriver", "MM3-74", Rarity.COMMON, mage.cards.g.GrixisSlavedriver.class));
        cards.add(new SetCardInfo("Grotesque Mutation", "BBD-145", Rarity.COMMON, mage.cards.g.GrotesqueMutation.class));
        cards.add(new SetCardInfo("Groundswell", "DDP-15", Rarity.COMMON, mage.cards.g.Groundswell.class));
        cards.add(new SetCardInfo("Gruesome Fate", "RIX-75", Rarity.COMMON, mage.cards.g.GruesomeFate.class));
        cards.add(new SetCardInfo("Gruul Signet", "C16-256", Rarity.COMMON, mage.cards.g.GruulSignet.class));
        cards.add(new SetCardInfo("Guard Gomazoa", "PCA-17", Rarity.UNCOMMON, mage.cards.g.GuardGomazoa.class));
        cards.add(new SetCardInfo("Guardian Shield-Bearer", "DTK-189", Rarity.COMMON, mage.cards.g.GuardianShieldBearer.class));
        cards.add(new SetCardInfo("Guardians of Meletis", "ORI-228", Rarity.COMMON, mage.cards.g.GuardiansOfMeletis.class));
        cards.add(new SetCardInfo("Guided Passage", "APC-105", Rarity.RARE, mage.cards.g.GuidedPassage.class));
        cards.add(new SetCardInfo("Guided Strike", "IMA-23", Rarity.COMMON, mage.cards.g.GuidedStrike.class));
        cards.add(new SetCardInfo("Gurmag Angler", "UMA-102", Rarity.COMMON, mage.cards.g.GurmagAngler.class));
        cards.add(new SetCardInfo("Gurmag Drowner", "DTK-57", Rarity.COMMON, mage.cards.g.GurmagDrowner.class));
        cards.add(new SetCardInfo("Gush", "DD2-27", Rarity.COMMON, mage.cards.g.Gush.class));
        cards.add(new SetCardInfo("Gust Walker", "AKH-17", Rarity.COMMON, mage.cards.g.GustWalker.class));
        cards.add(new SetCardInfo("Gustcloak Skirmisher", "DDO-13", Rarity.UNCOMMON, mage.cards.g.GustcloakSkirmisher.class));
        cards.add(new SetCardInfo("Gut Shot", "MM2-117", Rarity.COMMON, mage.cards.g.GutShot.class));
        cards.add(new SetCardInfo("Guttersnipe", "E01-51", Rarity.UNCOMMON, mage.cards.g.Guttersnipe.class));
        cards.add(new SetCardInfo("Gwyllion Hedge-Mage", "CMA-201", Rarity.UNCOMMON, mage.cards.g.GwyllionHedgeMage.class));
        cards.add(new SetCardInfo("Haakon, Stromgald Scourge", "CSP-61", Rarity.RARE, mage.cards.h.HaakonStromgaldScourge.class));
        cards.add(new SetCardInfo("Hamlet Captain", "EMN-161", Rarity.UNCOMMON, mage.cards.h.HamletCaptain.class));
        cards.add(new SetCardInfo("Hammer Dropper", "GRN-176", Rarity.COMMON, mage.cards.h.HammerDropper.class));
        cards.add(new SetCardInfo("Hammerhand", "IMA-132", Rarity.COMMON, mage.cards.h.Hammerhand.class));
        cards.add(new SetCardInfo("Hanweir Lancer", "MM3-97", Rarity.COMMON, mage.cards.h.HanweirLancer.class));
        cards.add(new SetCardInfo("Hardened Berserker", "DTK-139", Rarity.COMMON, mage.cards.h.HardenedBerserker.class));
        cards.add(new SetCardInfo("Hardy Veteran", "RIX-132", Rarity.COMMON, mage.cards.h.HardyVeteran.class));
        cards.add(new SetCardInfo("Harmonize", "C20-173", Rarity.UNCOMMON, mage.cards.h.Harmonize.class));
        cards.add(new SetCardInfo("Harrow", "C18-150", Rarity.COMMON, mage.cards.h.Harrow.class));
        cards.add(new SetCardInfo("Healer's Hawk", "GRN-14", Rarity.COMMON, mage.cards.h.HealersHawk.class));
        cards.add(new SetCardInfo("Healing Grace", "DOM-20", Rarity.COMMON, mage.cards.h.HealingGrace.class));
        cards.add(new SetCardInfo("Healing Hands", "ORI-17", Rarity.COMMON, mage.cards.h.HealingHands.class));
        cards.add(new SetCardInfo("Heavy Arbalest", "A25-225", Rarity.UNCOMMON, mage.cards.h.HeavyArbalest.class));
        cards.add(new SetCardInfo("Heavy Infantry", "ORI-18", Rarity.COMMON, mage.cards.h.HeavyInfantry.class));
        cards.add(new SetCardInfo("Hedron Crab", "ZEN-47", Rarity.UNCOMMON, mage.cards.h.HedronCrab.class));
        cards.add(new SetCardInfo("Helm of Awakening", "VIS-145", Rarity.UNCOMMON, mage.cards.h.HelmOfAwakening.class));
        cards.add(new SetCardInfo("Herald's Horn", "C17-53", Rarity.UNCOMMON, mage.cards.h.HeraldsHorn.class));
        cards.add(new SetCardInfo("Hexplate Golem", "BBD-237", Rarity.COMMON, mage.cards.h.HexplateGolem.class));
        cards.add(new SetCardInfo("Hidden Stockpile", "AER-129", Rarity.UNCOMMON, mage.cards.h.HiddenStockpile.class));
        cards.add(new SetCardInfo("Hideous End", "DDR-52", Rarity.COMMON, mage.cards.h.HideousEnd.class));
        cards.add(new SetCardInfo("Hieroglyphic Illumination", "AKH-57", Rarity.COMMON, mage.cards.h.HieroglyphicIllumination.class));
        cards.add(new SetCardInfo("Highspire Mantis", "KTK-177", Rarity.UNCOMMON, mage.cards.h.HighspireMantis.class));
        cards.add(new SetCardInfo("Hightide Hermit", "KLD-51", Rarity.COMMON, mage.cards.h.HightideHermit.class));
        cards.add(new SetCardInfo("Hijack", "XLN-148", Rarity.COMMON, mage.cards.h.Hijack.class));
        cards.add(new SetCardInfo("Hinterland Drake", "AER-34", Rarity.COMMON, mage.cards.h.HinterlandDrake.class));
        cards.add(new SetCardInfo("Hired Blade", "M19-100", Rarity.COMMON, mage.cards.h.HiredBlade.class));
        cards.add(new SetCardInfo("Hooded Brawler", "AKH-173", Rarity.COMMON, mage.cards.h.HoodedBrawler.class));
        cards.add(new SetCardInfo("Hooting Mandrills", "KTK-137", Rarity.COMMON, mage.cards.h.HootingMandrills.class));
        cards.add(new SetCardInfo("Hornet Nest", "M15-177", Rarity.RARE, mage.cards.h.HornetNest.class));
        cards.add(new SetCardInfo("Horseshoe Crab", "A25-61", Rarity.COMMON, mage.cards.h.HorseshoeCrab.class));
        cards.add(new SetCardInfo("Hot Soup", "M15-219", Rarity.UNCOMMON, mage.cards.h.HotSoup.class));
        cards.add(new SetCardInfo("Hound of the Farbogs", "SOI-117", Rarity.COMMON, mage.cards.h.HoundOfTheFarbogs.class));
        cards.add(new SetCardInfo("Hulking Devil", "SOI-165", Rarity.COMMON, mage.cards.h.HulkingDevil.class));
        cards.add(new SetCardInfo("Humble", "EMA-14", Rarity.COMMON, mage.cards.h.Humble.class));
        cards.add(new SetCardInfo("Humongulus", "RNA-41", Rarity.COMMON, mage.cards.h.Humongulus.class));
        cards.add(new SetCardInfo("Hunt the Weak", "RIX-133", Rarity.COMMON, mage.cards.h.HuntTheWeak.class));
        cards.add(new SetCardInfo("Hunter of Eyeblights", "LRW-119", Rarity.UNCOMMON, mage.cards.h.HunterOfEyeblights.class));
        cards.add(new SetCardInfo("Hunter's Ambush", "M15-180", Rarity.COMMON, mage.cards.h.HuntersAmbush.class));
        cards.add(new SetCardInfo("Hurricane", "10E-270", Rarity.RARE, mage.cards.h.Hurricane.class));
        cards.add(new SetCardInfo("Hyena Pack", "AKH-139", Rarity.COMMON, mage.cards.h.HyenaPack.class));
        cards.add(new SetCardInfo("Hyena Umbra", "UMA-21", Rarity.COMMON, mage.cards.h.HyenaUmbra.class));
        cards.add(new SetCardInfo("Hypnotic Specter", "M10-100", Rarity.RARE, mage.cards.h.HypnoticSpecter.class));
        cards.add(new SetCardInfo("Hypothesizzle", "GRN-178", Rarity.COMMON, mage.cards.h.Hypothesizzle.class));
        cards.add(new SetCardInfo("Icy Manipulator", "DOM-219", Rarity.UNCOMMON, mage.cards.i.IcyManipulator.class));
        cards.add(new SetCardInfo("Ill-Tempered Cyclops", "CN2-166", Rarity.COMMON, mage.cards.i.IllTemperedCyclops.class));
        cards.add(new SetCardInfo("Impact Tremors", "DTK-140", Rarity.COMMON, mage.cards.i.ImpactTremors.class));
        cards.add(new SetCardInfo("Impending Disaster", "ULG-82", Rarity.RARE, mage.cards.i.ImpendingDisaster.class));
        cards.add(new SetCardInfo("Imperious Perfect", "PCMP-9", Rarity.UNCOMMON, mage.cards.i.ImperiousPerfect.class));
        cards.add(new SetCardInfo("Implement of Malice", "AER-159", Rarity.COMMON, mage.cards.i.ImplementOfMalice.class));
        cards.add(new SetCardInfo("Impulse", "M15-126", Rarity.COMMON, mage.cards.i.Impulse.class));
        cards.add(new SetCardInfo("Incorrigible Youths", "SOI-166", Rarity.UNCOMMON, mage.cards.i.IncorrigibleYouths.class));
        cards.add(new SetCardInfo("Induce Despair", "DDP-53", Rarity.COMMON, mage.cards.i.InduceDespair.class));
        cards.add(new SetCardInfo("Infantry Veteran", "DDN-3", Rarity.COMMON, mage.cards.i.InfantryVeteran.class));
        cards.add(new SetCardInfo("Infernal Scarring", "ORI-102", Rarity.COMMON, mage.cards.i.InfernalScarring.class));
        cards.add(new SetCardInfo("Inferno Fist", "M15-150", Rarity.COMMON, mage.cards.i.InfernoFist.class));
        cards.add(new SetCardInfo("Inferno Jet", "HOU-99", Rarity.UNCOMMON, mage.cards.i.InfernoJet.class));
        cards.add(new SetCardInfo("Infest", "CN2-139", Rarity.UNCOMMON, mage.cards.i.Infest.class));
        cards.add(new SetCardInfo("Ingot Chewer", "CM2-110", Rarity.COMMON, mage.cards.i.IngotChewer.class));
        cards.add(new SetCardInfo("Inkfathom Divers", "DDT-8", Rarity.COMMON, mage.cards.i.InkfathomDivers.class));
        cards.add(new SetCardInfo("Innocent Blood", "EMA-94", Rarity.COMMON, mage.cards.i.InnocentBlood.class));
        cards.add(new SetCardInfo("Inquisition of Kozilek", "MM3-75", Rarity.UNCOMMON, mage.cards.i.InquisitionOfKozilek.class));
        cards.add(new SetCardInfo("Inquisitor's Ox", "SOI-24", Rarity.COMMON, mage.cards.i.InquisitorsOx.class));
        cards.add(new SetCardInfo("Insolent Neonate", "SOI-168", Rarity.COMMON, mage.cards.i.InsolentNeonate.class));
        cards.add(new SetCardInfo("Inspired Charge", "M19-15", Rarity.COMMON, mage.cards.i.InspiredCharge.class));
        cards.add(new SetCardInfo("Instill Infection", "MM2-85", Rarity.COMMON, mage.cards.i.InstillInfection.class));
        cards.add(new SetCardInfo("Intrusive Packbeast", "GRN-17", Rarity.COMMON, mage.cards.i.IntrusivePackbeast.class));
        cards.add(new SetCardInfo("Invigorate", "A25-173", Rarity.UNCOMMON, mage.cards.i.Invigorate.class));
        cards.add(new SetCardInfo("Invisibility", "M15-61", Rarity.COMMON, mage.cards.i.Invisibility.class));
        cards.add(new SetCardInfo("Iona's Judgment", "IMA-25", Rarity.COMMON, mage.cards.i.IonasJudgment.class));
        cards.add(new SetCardInfo("Ior Ruin Expedition", "E01-25", Rarity.COMMON, mage.cards.i.IorRuinExpedition.class));
        cards.add(new SetCardInfo("Iroas's Champion", "ORI-214", Rarity.UNCOMMON, mage.cards.i.IroassChampion.class));
        cards.add(new SetCardInfo("Irontread Crusher", "AER-161", Rarity.COMMON, mage.cards.i.IrontreadCrusher.class));
        cards.add(new SetCardInfo("Isolation Zone", "OGW-22", Rarity.COMMON, mage.cards.i.IsolationZone.class));
        cards.add(new SetCardInfo("Ivy Lane Denizen", "DDU-12", Rarity.COMMON, mage.cards.i.IvyLaneDenizen.class));
        cards.add(new SetCardInfo("Jace's Phantasm", "IMA-60", Rarity.COMMON, mage.cards.j.JacesPhantasm.class));
        cards.add(new SetCardInfo("Jackal Pup", "A25-139", Rarity.COMMON, mage.cards.j.JackalPup.class));
        cards.add(new SetCardInfo("Jeering Homunculus", "CN2-33", Rarity.COMMON, mage.cards.j.JeeringHomunculus.class));
        cards.add(new SetCardInfo("Jeskai Sage", "FRF-38", Rarity.COMMON, mage.cards.j.JeskaiSage.class));
        cards.add(new SetCardInfo("Join Shields", "GRN-181", Rarity.UNCOMMON, mage.cards.j.JoinShields.class));
        cards.add(new SetCardInfo("Jubilant Mascot", "BBD-28", Rarity.UNCOMMON, mage.cards.j.JubilantMascot.class));
        cards.add(new SetCardInfo("Juggernaut", "BBD-238", Rarity.UNCOMMON, mage.cards.j.Juggernaut.class));
        cards.add(new SetCardInfo("Jungle Barrier", "E02-38", Rarity.UNCOMMON, mage.cards.j.JungleBarrier.class));
        cards.add(new SetCardInfo("Jungle Delver", "XLN-195", Rarity.COMMON, mage.cards.j.JungleDelver.class));
        cards.add(new SetCardInfo("Jungle Hollow", "KTK-235", Rarity.COMMON, mage.cards.j.JungleHollow.class));
        cards.add(new SetCardInfo("Jungle Shrine", "E02-46", Rarity.UNCOMMON, mage.cards.j.JungleShrine.class));
        cards.add(new SetCardInfo("Jungle Wayfinder", "BBD-72", Rarity.COMMON, mage.cards.j.JungleWayfinder.class));
        cards.add(new SetCardInfo("Jushi Apprentice", "CHK-70", Rarity.RARE, mage.cards.j.JushiApprentice.class));
        cards.add(new SetCardInfo("Jwar Isle Avenger", "OGW-58", Rarity.COMMON, mage.cards.j.JwarIsleAvenger.class));
        cards.add(new SetCardInfo("Kaervek's Torch", "MIR-185", Rarity.COMMON, mage.cards.k.KaerveksTorch.class));
        cards.add(new SetCardInfo("Kalastria Nightwatch", "BFZ-115", Rarity.COMMON, mage.cards.k.KalastriaNightwatch.class));
        cards.add(new SetCardInfo("Kargan Dragonlord", "ROE-152", Rarity.MYTHIC, mage.cards.k.KarganDragonlord.class));
        cards.add(new SetCardInfo("Kathari Remnant", "PCA-98", Rarity.UNCOMMON, mage.cards.k.KathariRemnant.class));
        cards.add(new SetCardInfo("Kavu Climber", "A25-175", Rarity.COMMON, mage.cards.k.KavuClimber.class));
        cards.add(new SetCardInfo("Kavu Primarch", "MM2-146", Rarity.COMMON, mage.cards.k.KavuPrimarch.class));
        cards.add(new SetCardInfo("Kazandu Refuge", "C18-261", Rarity.UNCOMMON, mage.cards.k.KazanduRefuge.class));
        cards.add(new SetCardInfo("Keldon Halberdier", "IMA-135", Rarity.COMMON, mage.cards.k.KeldonHalberdier.class));
        cards.add(new SetCardInfo("Keldon Overseer", "DOM-134", Rarity.COMMON, mage.cards.k.KeldonOverseer.class));
        cards.add(new SetCardInfo("Khalni Heart Expedition", "C18-154", Rarity.COMMON, mage.cards.k.KhalniHeartExpedition.class));
        cards.add(new SetCardInfo("Khenra Scrapper", "HOU-100", Rarity.COMMON, mage.cards.k.KhenraScrapper.class));
        cards.add(new SetCardInfo("Kiki-Jiki, Mirror Breaker", "CHK-175", Rarity.MYTHIC, mage.cards.k.KikiJikiMirrorBreaker.class));
        cards.add(new SetCardInfo("Kiln Fiend", "IMA-137", Rarity.COMMON, mage.cards.k.KilnFiend.class));
        cards.add(new SetCardInfo("Kin-Tree Invocation", "KTK-183", Rarity.UNCOMMON, mage.cards.k.KinTreeInvocation.class));
        cards.add(new SetCardInfo("Kin-Tree Warden", "KTK-139", Rarity.COMMON, mage.cards.k.KinTreeWarden.class));
        cards.add(new SetCardInfo("Kiora's Dambreaker", "WAR-58", Rarity.COMMON, mage.cards.k.KiorasDambreaker.class));
        cards.add(new SetCardInfo("Kiora's Follower", "DDO-52", Rarity.UNCOMMON, mage.cards.k.KiorasFollower.class));
        cards.add(new SetCardInfo("Kird Ape", "EMA-137", Rarity.COMMON, mage.cards.k.KirdApe.class));
        cards.add(new SetCardInfo("Kiss of the Amesha", "BBD-225", Rarity.UNCOMMON, mage.cards.k.KissOfTheAmesha.class));
        cards.add(new SetCardInfo("Knight of Cliffhaven", "DDP-5", Rarity.COMMON, mage.cards.k.KnightOfCliffhaven.class));
        cards.add(new SetCardInfo("Knight of Dawn", "TMP-26", Rarity.UNCOMMON, mage.cards.k.KnightOfDawn.class));
        cards.add(new SetCardInfo("Knight of Old Benalia", "MH1-17", Rarity.COMMON, mage.cards.k.KnightOfOldBenalia.class));
        cards.add(new SetCardInfo("Knight of Sorrows", "RNA-14", Rarity.COMMON, mage.cards.k.KnightOfSorrows.class));
        cards.add(new SetCardInfo("Knight of the Skyward Eye", "A25-19", Rarity.COMMON, mage.cards.k.KnightOfTheSkywardEye.class));
        cards.add(new SetCardInfo("Knight of the Tusk", "M19-18", Rarity.COMMON, mage.cards.k.KnightOfTheTusk.class));
        cards.add(new SetCardInfo("Knollspine Dragon", "SHM-98", Rarity.RARE, mage.cards.k.KnollspineDragon.class));
        cards.add(new SetCardInfo("Kolaghan Stormsinger", "DTK-145", Rarity.COMMON, mage.cards.k.KolaghanStormsinger.class));
        cards.add(new SetCardInfo("Kolaghan's Command", "DTK-224", Rarity.RARE, mage.cards.k.KolaghansCommand.class));
        cards.add(new SetCardInfo("Kor Bladewhirl", "BFZ-34", Rarity.UNCOMMON, mage.cards.k.KorBladewhirl.class));
        cards.add(new SetCardInfo("Kor Chant", "CNS-73", Rarity.COMMON, mage.cards.k.KorChant.class));
        cards.add(new SetCardInfo("Kor Firewalker", "A25-21", Rarity.UNCOMMON, mage.cards.k.KorFirewalker.class));
        cards.add(new SetCardInfo("Kor Hookmaster", "EMA-18", Rarity.COMMON, mage.cards.k.KorHookmaster.class));
        cards.add(new SetCardInfo("Kor Sky Climber", "OGW-24", Rarity.COMMON, mage.cards.k.KorSkyClimber.class));
        cards.add(new SetCardInfo("Kor Skyfisher", "DDO-16", Rarity.COMMON, mage.cards.k.KorSkyfisher.class));
        cards.add(new SetCardInfo("Kozilek's Predator", "MM2-147", Rarity.COMMON, mage.cards.k.KozileksPredator.class));
        cards.add(new SetCardInfo("Kraul Foragers", "GRN-135", Rarity.COMMON, mage.cards.k.KraulForagers.class));
        cards.add(new SetCardInfo("Kraul Warrior", "BBD-204", Rarity.COMMON, mage.cards.k.KraulWarrior.class));
        cards.add(new SetCardInfo("Krenko's Command", "DDT-53", Rarity.COMMON, mage.cards.k.KrenkosCommand.class));
        cards.add(new SetCardInfo("Krenko's Enforcer", "M15-152", Rarity.COMMON, mage.cards.k.KrenkosEnforcer.class));
        cards.add(new SetCardInfo("Krenko, Mob Boss", "DDT-52", Rarity.RARE, mage.cards.k.KrenkoMobBoss.class));
        cards.add(new SetCardInfo("Krosan Druid", "DOM-167", Rarity.COMMON, mage.cards.k.KrosanDruid.class));
        cards.add(new SetCardInfo("Krosan Tusker", "ONS-272", Rarity.COMMON, mage.cards.k.KrosanTusker.class));
        cards.add(new SetCardInfo("Krosan Verge", "ZNC-134", Rarity.UNCOMMON, mage.cards.k.KrosanVerge.class));
        cards.add(new SetCardInfo("Krumar Bond-Kin", "KTK-77", Rarity.COMMON, mage.cards.k.KrumarBondKin.class));
        cards.add(new SetCardInfo("Kruphix, God of Horizons", "JOU-152", Rarity.MYTHIC, mage.cards.k.KruphixGodOfHorizons.class));
        cards.add(new SetCardInfo("Laboratory Brute", "EMN-67", Rarity.COMMON, mage.cards.l.LaboratoryBrute.class));
        cards.add(new SetCardInfo("Laboratory Maniac", "ISD-61", Rarity.UNCOMMON, mage.cards.l.LaboratoryManiac.class));
        cards.add(new SetCardInfo("Labyrinth Guardian", "AKH-60", Rarity.UNCOMMON, mage.cards.l.LabyrinthGuardian.class));
        cards.add(new SetCardInfo("Larger Than Life", "KLD-160", Rarity.COMMON, mage.cards.l.LargerThanLife.class));
        cards.add(new SetCardInfo("Lashknife Barrier", "PLS-9", Rarity.UNCOMMON, mage.cards.l.LashknifeBarrier.class));
        cards.add(new SetCardInfo("Lawless Broker", "KLD-86", Rarity.COMMON, mage.cards.l.LawlessBroker.class));
        cards.add(new SetCardInfo("Lawmage's Binding", "RNA-190", Rarity.COMMON, mage.cards.l.LawmagesBinding.class));
        cards.add(new SetCardInfo("Lay Claim", "AKH-61", Rarity.UNCOMMON, mage.cards.l.LayClaim.class));
        cards.add(new SetCardInfo("Lay of the Land", "CN2-185", Rarity.COMMON, mage.cards.l.LayOfTheLand.class));
        cards.add(new SetCardInfo("Lazotep Behemoth", "WAR-95", Rarity.COMMON, mage.cards.l.LazotepBehemoth.class));
        cards.add(new SetCardInfo("Lead by Example", "BBD-205", Rarity.COMMON, mage.cards.l.LeadByExample.class));
        cards.add(new SetCardInfo("Lead the Stampede", "DDU-16", Rarity.COMMON, mage.cards.l.LeadTheStampede.class));
        cards.add(new SetCardInfo("Leapfrog", "GRN-42", Rarity.COMMON, mage.cards.l.Leapfrog.class));
        cards.add(new SetCardInfo("Leaping Master", "KTK-114", Rarity.COMMON, mage.cards.l.LeapingMaster.class));
        cards.add(new SetCardInfo("Leonin Relic-Warder", "MBS-10", Rarity.UNCOMMON, mage.cards.l.LeoninRelicWarder.class));
        cards.add(new SetCardInfo("Leopard-Spotted Jiao", "GS1-23", Rarity.COMMON, mage.cards.l.LeopardSpottedJiao.class));
        cards.add(new SetCardInfo("Lethal Sting", "HOU-67", Rarity.COMMON, mage.cards.l.LethalSting.class));
        cards.add(new SetCardInfo("Lieutenants of the Guard", "CN2-16", Rarity.COMMON, mage.cards.l.LieutenantsOfTheGuard.class));
        cards.add(new SetCardInfo("Lifespring Druid", "BFZ-177", Rarity.COMMON, mage.cards.l.LifespringDruid.class));
        cards.add(new SetCardInfo("Lightform", "C18-68", Rarity.UNCOMMON, mage.cards.l.Lightform.class));
        cards.add(new SetCardInfo("Lightning Bolt", "A25-141", Rarity.UNCOMMON, mage.cards.l.LightningBolt.class));
        cards.add(new SetCardInfo("Lightning Greaves", "2XM-267", Rarity.UNCOMMON, mage.cards.l.LightningGreaves.class));
        cards.add(new SetCardInfo("Lightning Helix", "GK1-90", Rarity.UNCOMMON, mage.cards.l.LightningHelix.class));
        cards.add(new SetCardInfo("Lightning Javelin", "ORI-153", Rarity.COMMON, mage.cards.l.LightningJavelin.class));
        cards.add(new SetCardInfo("Lightning Shrieker", "FRF-106", Rarity.COMMON, mage.cards.l.LightningShrieker.class));
        cards.add(new SetCardInfo("Lightning Talons", "BBD-180", Rarity.COMMON, mage.cards.l.LightningTalons.class));
        cards.add(new SetCardInfo("Lightwalker", "BBD-95", Rarity.COMMON, mage.cards.l.Lightwalker.class));
        cards.add(new SetCardInfo("Lignify", "DDD-16", Rarity.COMMON, mage.cards.l.Lignify.class));
        cards.add(new SetCardInfo("Liliana, Death's Majesty", "AKH-97", Rarity.MYTHIC, mage.cards.l.LilianaDeathsMajesty.class));
        cards.add(new SetCardInfo("Lingering Souls", "MM3-12", Rarity.UNCOMMON, mage.cards.l.LingeringSouls.class));
        cards.add(new SetCardInfo("Living Death", "A25-96", Rarity.RARE, mage.cards.l.LivingDeath.class));
        cards.add(new SetCardInfo("Llanowar Elves", "M19-314", Rarity.COMMON, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Llanowar Empath", "DDU-18", Rarity.COMMON, mage.cards.l.LlanowarEmpath.class));
        cards.add(new SetCardInfo("Lone Missionary", "DDN-49", Rarity.COMMON, mage.cards.l.LoneMissionary.class));
        cards.add(new SetCardInfo("Lonesome Unicorn", "ELD-21", Rarity.COMMON, mage.cards.l.LonesomeUnicorn.class));
        cards.add(new SetCardInfo("Longshot Squad", "KTK-140", Rarity.COMMON, mage.cards.l.LongshotSquad.class));
        cards.add(new SetCardInfo("Looming Altisaur", "XLN-23", Rarity.COMMON, mage.cards.l.LoomingAltisaur.class));
        cards.add(new SetCardInfo("Lord of the Accursed", "AKH-99", Rarity.UNCOMMON, mage.cards.l.LordOfTheAccursed.class));
        cards.add(new SetCardInfo("Lotus Petal", "TMP-294", Rarity.COMMON, mage.cards.l.LotusPetal.class));
        cards.add(new SetCardInfo("Lotus-Eye Mystics", "UMA-23", Rarity.COMMON, mage.cards.l.LotusEyeMystics.class));
        cards.add(new SetCardInfo("Loxodon Partisan", "DDO-17", Rarity.COMMON, mage.cards.l.LoxodonPartisan.class));
        cards.add(new SetCardInfo("Loxodon Warhammer", "C17-216", Rarity.UNCOMMON, mage.cards.l.LoxodonWarhammer.class));
        cards.add(new SetCardInfo("Loyal Sentry", "A25-22", Rarity.COMMON, mage.cards.l.LoyalSentry.class));
        cards.add(new SetCardInfo("Lunarch Mantle", "A25-24", Rarity.COMMON, mage.cards.l.LunarchMantle.class));
        cards.add(new SetCardInfo("Lure", "IMA-175", Rarity.UNCOMMON, mage.cards.l.Lure.class));
        cards.add(new SetCardInfo("Macabre Waltz", "SOI-121", Rarity.COMMON, mage.cards.m.MacabreWaltz.class));
        cards.add(new SetCardInfo("Madcap Skills", "MM3-99", Rarity.COMMON, mage.cards.m.MadcapSkills.class));
        cards.add(new SetCardInfo("Maelstrom Archangel", "CON-115", Rarity.MYTHIC, mage.cards.m.MaelstromArchangel.class));
        cards.add(new SetCardInfo("Magma Spray", "AKH-141", Rarity.COMMON, mage.cards.m.MagmaSpray.class));
        cards.add(new SetCardInfo("Magus of the Moat", "FUT-12", Rarity.RARE, mage.cards.m.MagusOfTheMoat.class));
        cards.add(new SetCardInfo("Mahamoti Djinn", "IMA-64", Rarity.UNCOMMON, mage.cards.m.MahamotiDjinn.class));
        cards.add(new SetCardInfo("Makindi Sliderunner", "BFZ-148", Rarity.COMMON, mage.cards.m.MakindiSliderunner.class));
        cards.add(new SetCardInfo("Man-o'-War", "VIS-37", Rarity.COMMON, mage.cards.m.ManOWar.class));
        cards.add(new SetCardInfo("Mana Crypt", "EMA-225", Rarity.MYTHIC, mage.cards.m.ManaCrypt.class));
        cards.add(new SetCardInfo("Mana Leak", "DDN-64", Rarity.COMMON, mage.cards.m.ManaLeak.class));
        cards.add(new SetCardInfo("Mana Tithe", "PLC-25", Rarity.COMMON, mage.cards.m.ManaTithe.class));
        cards.add(new SetCardInfo("Manamorphose", "SHM-211", Rarity.COMMON, mage.cards.m.Manamorphose.class));
        cards.add(new SetCardInfo("Manglehorn", "AKH-175", Rarity.UNCOMMON, mage.cards.m.Manglehorn.class));
        cards.add(new SetCardInfo("Mantle of Webs", "ORI-187", Rarity.COMMON, mage.cards.m.MantleOfWebs.class));
        cards.add(new SetCardInfo("Map the Wastes", "FRF-134", Rarity.COMMON, mage.cards.m.MapTheWastes.class));
        cards.add(new SetCardInfo("Marauding Boneslasher", "HOU-70", Rarity.COMMON, mage.cards.m.MaraudingBoneslasher.class));
        cards.add(new SetCardInfo("March of the Drowned", "XLN-112", Rarity.COMMON, mage.cards.m.MarchOfTheDrowned.class));
        cards.add(new SetCardInfo("Mardu Hordechief", "KTK-17", Rarity.COMMON, mage.cards.m.MarduHordechief.class));
        cards.add(new SetCardInfo("Mardu Roughrider", "KTK-187", Rarity.UNCOMMON, mage.cards.m.MarduRoughrider.class));
        cards.add(new SetCardInfo("Mardu Warshrieker", "KTK-117", Rarity.COMMON, mage.cards.m.MarduWarshrieker.class));
        cards.add(new SetCardInfo("Mark of Mutiny", "PCA-47", Rarity.UNCOMMON, mage.cards.m.MarkOfMutiny.class));
        cards.add(new SetCardInfo("Mark of the Vampire", "UMA-105", Rarity.COMMON, mage.cards.m.MarkOfTheVampire.class));
        cards.add(new SetCardInfo("Marked by Honor", "M15-17", Rarity.COMMON, mage.cards.m.MarkedByHonor.class));
        cards.add(new SetCardInfo("Marsh Hulk", "DTK-109", Rarity.COMMON, mage.cards.m.MarshHulk.class));
        cards.add(new SetCardInfo("Martial Glory", "GK1-91", Rarity.COMMON, mage.cards.m.MartialGlory.class));
        cards.add(new SetCardInfo("Martyr's Bond", "CMD-19", Rarity.RARE, mage.cards.m.MartyrsBond.class));
        cards.add(new SetCardInfo("Martyr's Cause", "ULG-13", Rarity.UNCOMMON, mage.cards.m.MartyrsCause.class));
        cards.add(new SetCardInfo("Mask of Memory", "C14-249", Rarity.UNCOMMON, mage.cards.m.MaskOfMemory.class));
        cards.add(new SetCardInfo("Master Transmuter", "CON-31", Rarity.RARE, mage.cards.m.MasterTransmuter.class));
        cards.add(new SetCardInfo("Maverick Thopterist", "DDU-50", Rarity.UNCOMMON, mage.cards.m.MaverickThopterist.class));
        cards.add(new SetCardInfo("Maximize Altitude", "GRN-43", Rarity.COMMON, mage.cards.m.MaximizeAltitude.class));
        cards.add(new SetCardInfo("Maximize Velocity", "GRN-111", Rarity.COMMON, mage.cards.m.MaximizeVelocity.class));
        cards.add(new SetCardInfo("Meandering Towershell", "KTK-141", Rarity.RARE, mage.cards.m.MeanderingTowershell.class));
        cards.add(new SetCardInfo("Meddling Mage", "ARB-8", Rarity.RARE, mage.cards.m.MeddlingMage.class));
        cards.add(new SetCardInfo("Meditation Puzzle", "M15-19", Rarity.COMMON, mage.cards.m.MeditationPuzzle.class));
        cards.add(new SetCardInfo("Memory Erosion", "CM2-45", Rarity.RARE, mage.cards.m.MemoryErosion.class));
        cards.add(new SetCardInfo("Memory Lapse", "EMA-60", Rarity.COMMON, mage.cards.m.MemoryLapse.class));
        cards.add(new SetCardInfo("Mephitic Vapors", "GRN-76", Rarity.COMMON, mage.cards.m.MephiticVapors.class));
        cards.add(new SetCardInfo("Merciless Resolve", "SOI-123", Rarity.COMMON, mage.cards.m.MercilessResolve.class));
        cards.add(new SetCardInfo("Mercurial Geists", "EMN-186", Rarity.UNCOMMON, mage.cards.m.MercurialGeists.class));
        cards.add(new SetCardInfo("Meren of Clan Nel Toth", "C15-49", Rarity.MYTHIC, mage.cards.m.MerenOfClanNelToth.class));
        cards.add(new SetCardInfo("Merfolk Looter", "DDT-10", Rarity.UNCOMMON, mage.cards.m.MerfolkLooter.class));
        cards.add(new SetCardInfo("Messenger Jays", "CN2-35", Rarity.COMMON, mage.cards.m.MessengerJays.class));
        cards.add(new SetCardInfo("Metallic Rebuke", "AER-39", Rarity.COMMON, mage.cards.m.MetallicRebuke.class));
        cards.add(new SetCardInfo("Meteorite", "ORI-233", Rarity.UNCOMMON, mage.cards.m.Meteorite.class));
        cards.add(new SetCardInfo("Miasmic Mummy", "AKH-100", Rarity.COMMON, mage.cards.m.MiasmicMummy.class));
        cards.add(new SetCardInfo("Midnight Guard", "BBD-99", Rarity.COMMON, mage.cards.m.MidnightGuard.class));
        cards.add(new SetCardInfo("Might of the Masses", "ORI-188", Rarity.COMMON, mage.cards.m.MightOfTheMasses.class));
        cards.add(new SetCardInfo("Migratory Route", "CM2-161", Rarity.UNCOMMON, mage.cards.m.MigratoryRoute.class));
        cards.add(new SetCardInfo("Millikin", "EMA-226", Rarity.UNCOMMON, mage.cards.m.Millikin.class));
        cards.add(new SetCardInfo("Millstone", "M14-213", Rarity.UNCOMMON, mage.cards.m.Millstone.class));
        cards.add(new SetCardInfo("Mimic Vat", "C19-219", Rarity.RARE, mage.cards.m.MimicVat.class));
        cards.add(new SetCardInfo("Mind Rake", "MH1-96", Rarity.COMMON, mage.cards.m.MindRake.class));
        cards.add(new SetCardInfo("Mind Rot", "W16-7", Rarity.COMMON, mage.cards.m.MindRot.class));
        cards.add(new SetCardInfo("Mind Sculpt", "M15-70", Rarity.COMMON, mage.cards.m.MindSculpt.class));
        cards.add(new SetCardInfo("Mind Shatter", "MM3-77", Rarity.RARE, mage.cards.m.MindShatter.class));
        cards.add(new SetCardInfo("Mind Spring", "DDT-14", Rarity.RARE, mage.cards.m.MindSpring.class));
        cards.add(new SetCardInfo("Mind Stone", "WTH-153", Rarity.COMMON, mage.cards.m.MindStone.class));
        cards.add(new SetCardInfo("Miner's Bane", "M15-157", Rarity.COMMON, mage.cards.m.MinersBane.class));
        cards.add(new SetCardInfo("Mire's Malice", "BFZ-117", Rarity.COMMON, mage.cards.m.MiresMalice.class));
        cards.add(new SetCardInfo("Mirran Crusader", "MM2-25", Rarity.RARE, mage.cards.m.MirranCrusader.class));
        cards.add(new SetCardInfo("Mirror Entity", "LRW-31", Rarity.RARE, mage.cards.m.MirrorEntity.class));
        cards.add(new SetCardInfo("Misdirection", "DDT-15", Rarity.RARE, mage.cards.m.Misdirection.class));
        cards.add(new SetCardInfo("Mishra's Bauble", "IMA-221", Rarity.UNCOMMON, mage.cards.m.MishrasBauble.class));
        cards.add(new SetCardInfo("Mishra's Factory", "MH2-302", Rarity.UNCOMMON, mage.cards.m.MishrasFactory.class));
        cards.add(new SetCardInfo("Mist Raven", "DDQ-26", Rarity.COMMON, mage.cards.m.MistRaven.class));
        cards.add(new SetCardInfo("Mistform Shrieker", "ONS-96", Rarity.UNCOMMON, mage.cards.m.MistformShrieker.class));
        cards.add(new SetCardInfo("Mistmeadow Witch", "SHM-144", Rarity.UNCOMMON, mage.cards.m.MistmeadowWitch.class));
        cards.add(new SetCardInfo("Mizzix's Mastery", "C15-29", Rarity.RARE, mage.cards.m.MizzixsMastery.class));
        cards.add(new SetCardInfo("Mnemonic Wall", "IMA-67", Rarity.COMMON, mage.cards.m.MnemonicWall.class));
        cards.add(new SetCardInfo("Mogg Fanatic", "DD1-44", Rarity.UNCOMMON, mage.cards.m.MoggFanatic.class));
        cards.add(new SetCardInfo("Mogg Flunkies", "MM3-102", Rarity.COMMON, mage.cards.m.MoggFlunkies.class));
        cards.add(new SetCardInfo("Mogg War Marshal", "EMA-139", Rarity.COMMON, mage.cards.m.MoggWarMarshal.class));
        cards.add(new SetCardInfo("Molten Rain", "MM3-103", Rarity.UNCOMMON, mage.cards.m.MoltenRain.class));
        cards.add(new SetCardInfo("Moment of Craving", "RIX-79", Rarity.COMMON, mage.cards.m.MomentOfCraving.class));
        cards.add(new SetCardInfo("Momentary Blink", "MM3-16", Rarity.COMMON, mage.cards.m.MomentaryBlink.class));
        cards.add(new SetCardInfo("Monastery Loremaster", "DTK-63", Rarity.COMMON, mage.cards.m.MonasteryLoremaster.class));
        cards.add(new SetCardInfo("Monastery Swiftspear", "IMA-140", Rarity.UNCOMMON, mage.cards.m.MonasterySwiftspear.class));
        cards.add(new SetCardInfo("Moonglove Extract", "IMA-222", Rarity.COMMON, mage.cards.m.MoongloveExtract.class));
        cards.add(new SetCardInfo("Moonlit Strider", "MM2-27", Rarity.COMMON, mage.cards.m.MoonlitStrider.class));
        cards.add(new SetCardInfo("Mortal's Ardor", "DDO-19", Rarity.COMMON, mage.cards.m.MortalsArdor.class));
        cards.add(new SetCardInfo("Mortarpod", "MM2-222", Rarity.UNCOMMON, mage.cards.m.Mortarpod.class));
        cards.add(new SetCardInfo("Mortify", "P07-3", Rarity.UNCOMMON, mage.cards.m.Mortify.class));
        cards.add(new SetCardInfo("Mother of Runes", "DDO-20", Rarity.UNCOMMON, mage.cards.m.MotherOfRunes.class));
        cards.add(new SetCardInfo("Mulch", "CMA-128", Rarity.COMMON, mage.cards.m.Mulch.class));
        cards.add(new SetCardInfo("Mulldrifter", "CM2-47", Rarity.COMMON, mage.cards.m.Mulldrifter.class));
        cards.add(new SetCardInfo("Murder", "M20-109", Rarity.COMMON, mage.cards.m.Murder.class));
        cards.add(new SetCardInfo("Murder of Crows", "A25-66", Rarity.UNCOMMON, mage.cards.m.MurderOfCrows.class));
        cards.add(new SetCardInfo("Murderous Compulsion", "SOI-126", Rarity.COMMON, mage.cards.m.MurderousCompulsion.class));
        cards.add(new SetCardInfo("Mutiny", "PCA-47", Rarity.COMMON, mage.cards.m.Mutiny.class));
        cards.add(new SetCardInfo("Mycoloth", "CMA-129", Rarity.RARE, mage.cards.m.Mycoloth.class));
        cards.add(new SetCardInfo("Myr Retriever", "CM2-203", Rarity.UNCOMMON, mage.cards.m.MyrRetriever.class));
        cards.add(new SetCardInfo("Myr Sire", "CM2-204", Rarity.COMMON, mage.cards.m.MyrSire.class));
        cards.add(new SetCardInfo("Mystic Confluence", "BBD-122", Rarity.RARE, mage.cards.m.MysticConfluence.class));
        cards.add(new SetCardInfo("Mystic of the Hidden Way", "A25-67", Rarity.COMMON, mage.cards.m.MysticOfTheHiddenWay.class));
        cards.add(new SetCardInfo("Mystical Teachings", "MM3-44", Rarity.COMMON, mage.cards.m.MysticalTeachings.class));
        cards.add(new SetCardInfo("Nagging Thoughts", "SOI-74", Rarity.COMMON, mage.cards.n.NaggingThoughts.class));
        cards.add(new SetCardInfo("Nameless Inversion", "MM2-87", Rarity.COMMON, mage.cards.n.NamelessInversion.class));
        cards.add(new SetCardInfo("Nantuko Husk", "ORI-109", Rarity.COMMON, mage.cards.n.NantukoHusk.class));
        cards.add(new SetCardInfo("Natural Connection", "DDR-13", Rarity.COMMON, mage.cards.n.NaturalConnection.class));
        cards.add(new SetCardInfo("Naturalize", "M19-190", Rarity.COMMON, mage.cards.n.Naturalize.class));
        cards.add(new SetCardInfo("Nature's Claim", "IMA-177", Rarity.COMMON, mage.cards.n.NaturesClaim.class));
        cards.add(new SetCardInfo("Nature's Lore", "DMR-170", Rarity.COMMON, mage.cards.n.NaturesLore.class));
        cards.add(new SetCardInfo("Naya Charm", "C16-214", Rarity.UNCOMMON, mage.cards.n.NayaCharm.class));
        cards.add(new SetCardInfo("Negate", "ZNR-71", Rarity.COMMON, mage.cards.n.Negate.class));
        cards.add(new SetCardInfo("Nemesis of Reason", "ARB-28", Rarity.RARE, mage.cards.n.NemesisOfReason.class));
        cards.add(new SetCardInfo("Nest Invader", "PCA-69", Rarity.COMMON, mage.cards.n.NestInvader.class));
        cards.add(new SetCardInfo("Nettle Sentinel", "A25-182", Rarity.COMMON, mage.cards.n.NettleSentinel.class));
        cards.add(new SetCardInfo("Never Happened", "GRN-80", Rarity.COMMON, mage.cards.n.NeverHappened.class));
        cards.add(new SetCardInfo("New Benalia", "C18-270", Rarity.UNCOMMON, mage.cards.n.NewBenalia.class));
        cards.add(new SetCardInfo("New Horizons", "XLN-198", Rarity.COMMON, mage.cards.n.NewHorizons.class));
        cards.add(new SetCardInfo("Niblis of Dusk", "SOI-76", Rarity.COMMON, mage.cards.n.NiblisOfDusk.class));
        cards.add(new SetCardInfo("Night's Whisper", "EMA-100", Rarity.COMMON, mage.cards.n.NightsWhisper.class));
        cards.add(new SetCardInfo("Nighthowler", "C15-129", Rarity.RARE, mage.cards.n.Nighthowler.class));
        cards.add(new SetCardInfo("Nimble Mongoose", "EMA-179", Rarity.COMMON, mage.cards.n.NimbleMongoose.class));
        cards.add(new SetCardInfo("Nimble-Blade Khenra", "AKH-145", Rarity.COMMON, mage.cards.n.NimbleBladeKhenra.class));
        cards.add(new SetCardInfo("Nin, the Pain Artist", "C17-183", Rarity.RARE, mage.cards.n.NinThePainArtist.class));
        cards.add(new SetCardInfo("Nine-Tail White Fox", "GS1-8", Rarity.COMMON, mage.cards.n.NineTailWhiteFox.class));
        cards.add(new SetCardInfo("Ninja of the Deep Hours", "C18-95", Rarity.COMMON, mage.cards.n.NinjaOfTheDeepHours.class));
        cards.add(new SetCardInfo("Ninth Bridge Patrol", "KLD-22", Rarity.COMMON, mage.cards.n.NinthBridgePatrol.class));
        cards.add(new SetCardInfo("Nirkana Assassin", "BFZ-118", Rarity.COMMON, mage.cards.n.NirkanaAssassin.class));
        cards.add(new SetCardInfo("Nissa, Voice of Zendikar", "OGW-138", Rarity.MYTHIC, mage.cards.n.NissaVoiceOfZendikar.class));
        cards.add(new SetCardInfo("Noxious Dragon", "FRF-77", Rarity.UNCOMMON, mage.cards.n.NoxiousDragon.class));
        cards.add(new SetCardInfo("Nucklavee", "DDS-26", Rarity.UNCOMMON, mage.cards.n.Nucklavee.class));
        cards.add(new SetCardInfo("Nyx-Fleece Ram", "A25-26", Rarity.UNCOMMON, mage.cards.n.NyxFleeceRam.class));
        cards.add(new SetCardInfo("Oakgnarl Warrior", "DDR-15", Rarity.COMMON, mage.cards.o.OakgnarlWarrior.class));
        cards.add(new SetCardInfo("Obelisk Spider", "HOU-141", Rarity.UNCOMMON, mage.cards.o.ObeliskSpider.class));
        cards.add(new SetCardInfo("Ochran Assassin", "GRN-194", Rarity.UNCOMMON, mage.cards.o.OchranAssassin.class));
        cards.add(new SetCardInfo("Odric, Lunarch Marshal", "SOI-31", Rarity.RARE, mage.cards.o.OdricLunarchMarshal.class));
        cards.add(new SetCardInfo("Ojutai Interceptor", "DTK-66", Rarity.COMMON, mage.cards.o.OjutaiInterceptor.class));
        cards.add(new SetCardInfo("Ojutai's Breath", "DTK-67", Rarity.COMMON, mage.cards.o.OjutaisBreath.class));
        cards.add(new SetCardInfo("Okiba-Gang Shinobi", "PCA-35", Rarity.COMMON, mage.cards.o.OkibaGangShinobi.class));
        cards.add(new SetCardInfo("Omenspeaker", "BBD-125", Rarity.COMMON, mage.cards.o.Omenspeaker.class));
        cards.add(new SetCardInfo("Ondu Champion", "BFZ-149", Rarity.COMMON, mage.cards.o.OnduChampion.class));
        cards.add(new SetCardInfo("Ondu Giant", "PCA-71", Rarity.COMMON, mage.cards.o.OnduGiant.class));
        cards.add(new SetCardInfo("Ondu Greathorn", "BFZ-40", Rarity.COMMON, mage.cards.o.OnduGreathorn.class));
        cards.add(new SetCardInfo("Ondu War Cleric", "OGW-31", Rarity.COMMON, mage.cards.o.OnduWarCleric.class));
        cards.add(new SetCardInfo("Opportunity", "ULG-37", Rarity.UNCOMMON, mage.cards.o.Opportunity.class));
        cards.add(new SetCardInfo("Opt", "DDU-50", Rarity.COMMON, mage.cards.o.Opt.class));
        cards.add(new SetCardInfo("Oracle of Nectars", "SHM-233", Rarity.RARE, mage.cards.o.OracleOfNectars.class));
        cards.add(new SetCardInfo("Oran-Rief Invoker", "DDR-17", Rarity.COMMON, mage.cards.o.OranRiefInvoker.class));
        cards.add(new SetCardInfo("Orcish Cannonade", "DDN-28", Rarity.COMMON, mage.cards.o.OrcishCannonade.class));
        cards.add(new SetCardInfo("Orcish Oriflamme", "EMA-140", Rarity.COMMON, mage.cards.o.OrcishOriflamme.class));
        cards.add(new SetCardInfo("Oreskos Swiftclaw", "M15-22", Rarity.COMMON, mage.cards.o.OreskosSwiftclaw.class));
        cards.add(new SetCardInfo("Ornithopter", "M15-223", Rarity.COMMON, mage.cards.o.Ornithopter.class));
        cards.add(new SetCardInfo("Orzhov Basilica", "C17-268", Rarity.COMMON, mage.cards.o.OrzhovBasilica.class));
        cards.add(new SetCardInfo("Oust", "C21-218", Rarity.UNCOMMON, mage.cards.o.Oust.class));
        cards.add(new SetCardInfo("Outnumber", "BFZ-150", Rarity.COMMON, mage.cards.o.Outnumber.class));
        cards.add(new SetCardInfo("Overgrown Armasaur", "RIX-141", Rarity.COMMON, mage.cards.o.OvergrownArmasaur.class));
        cards.add(new SetCardInfo("Overgrown Battlement", "IMA-180", Rarity.UNCOMMON, mage.cards.o.OvergrownBattlement.class));
        cards.add(new SetCardInfo("Overrun", "CMA-130", Rarity.UNCOMMON, mage.cards.o.Overrun.class));
        cards.add(new SetCardInfo("Pacifism", "M20-32", Rarity.COMMON, mage.cards.p.Pacifism.class));
        cards.add(new SetCardInfo("Pack's Favor", "GRN-139", Rarity.COMMON, mage.cards.p.PacksFavor.class));
        cards.add(new SetCardInfo("Painful Lesson", "BBD-154", Rarity.COMMON, mage.cards.p.PainfulLesson.class));
        cards.add(new SetCardInfo("Palace Jailer", "CN2-18", Rarity.UNCOMMON, mage.cards.p.PalaceJailer.class));
        cards.add(new SetCardInfo("Palace Sentinels", "CN2-19", Rarity.COMMON, mage.cards.p.PalaceSentinels.class));
        cards.add(new SetCardInfo("Paladin of the Bloodstained", "XLN-25", Rarity.COMMON, mage.cards.p.PaladinOfTheBloodstained.class));
        cards.add(new SetCardInfo("Palladium Myr", "CM2-207", Rarity.UNCOMMON, mage.cards.p.PalladiumMyr.class));
        cards.add(new SetCardInfo("Path of Peace", "A25-29", Rarity.COMMON, mage.cards.p.PathOfPeace.class));
        cards.add(new SetCardInfo("Path to Exile", "CON-15", Rarity.UNCOMMON, mage.cards.p.PathToExile.class));
        cards.add(new SetCardInfo("Pathrazer of Ulamog", "ROE-9", Rarity.UNCOMMON, mage.cards.p.PathrazerOfUlamog.class));
        cards.add(new SetCardInfo("Peace Strider", "BBD-243", Rarity.COMMON, mage.cards.p.PeaceStrider.class));
        cards.add(new SetCardInfo("Peace of Mind", "EMN-36", Rarity.UNCOMMON, mage.cards.p.PeaceOfMind.class));
        cards.add(new SetCardInfo("Peel from Reality", "DDO-40", Rarity.COMMON, mage.cards.p.PeelFromReality.class));
        cards.add(new SetCardInfo("Peema Outrider", "KLD-166", Rarity.COMMON, mage.cards.p.PeemaOutrider.class));
        cards.add(new SetCardInfo("Pegasus Courser", "M19-32", Rarity.COMMON, mage.cards.p.PegasusCourser.class));
        cards.add(new SetCardInfo("Pelakka Wurm", "MM2-154", Rarity.UNCOMMON, mage.cards.p.PelakkaWurm.class));
        cards.add(new SetCardInfo("Pentarch Ward", "IMA-27", Rarity.COMMON, mage.cards.p.PentarchWard.class));
        cards.add(new SetCardInfo("Penumbra Spider", "MM3-131", Rarity.COMMON, mage.cards.p.PenumbraSpider.class));
        cards.add(new SetCardInfo("Perilous Myr", "A25-227", Rarity.UNCOMMON, mage.cards.p.PerilousMyr.class));
        cards.add(new SetCardInfo("Perish", "TMP-147", Rarity.UNCOMMON, mage.cards.p.Perish.class));
        cards.add(new SetCardInfo("Pestilence", "USG-147", Rarity.COMMON, mage.cards.p.Pestilence.class));
        cards.add(new SetCardInfo("Phantasmal Bear", "A25-69", Rarity.COMMON, mage.cards.p.PhantasmalBear.class));
        cards.add(new SetCardInfo("Phantasmal Dragon", "DDM-14", Rarity.UNCOMMON, mage.cards.p.PhantasmalDragon.class));
        cards.add(new SetCardInfo("Phantom Centaur", "JUD-127", Rarity.UNCOMMON, mage.cards.p.PhantomCentaur.class));
        cards.add(new SetCardInfo("Phyrexian Arena", "CN2-144", Rarity.RARE, mage.cards.p.PhyrexianArena.class));
        cards.add(new SetCardInfo("Phyrexian Ingester", "EMA-66", Rarity.UNCOMMON, mage.cards.p.PhyrexianIngester.class));
        cards.add(new SetCardInfo("Phyrexian Metamorph", "NPH-42", Rarity.RARE, mage.cards.p.PhyrexianMetamorph.class));
        cards.add(new SetCardInfo("Phyrexian Plaguelord", "CMA-61", Rarity.RARE, mage.cards.p.PhyrexianPlaguelord.class));
        cards.add(new SetCardInfo("Phyrexian Rager", "CMA-62", Rarity.COMMON, mage.cards.p.PhyrexianRager.class));
        cards.add(new SetCardInfo("Phyrexian Reclamation", "C15-133", Rarity.UNCOMMON, mage.cards.p.PhyrexianReclamation.class));
        cards.add(new SetCardInfo("Phyrexian Soulgorger", "CSP-141", Rarity.RARE, mage.cards.p.PhyrexianSoulgorger.class));
        cards.add(new SetCardInfo("Pierce the Sky", "DOM-176", Rarity.COMMON, mage.cards.p.PierceTheSky.class));
        cards.add(new SetCardInfo("Pilgrim's Eye", "GNT-55", Rarity.UNCOMMON, mage.cards.p.PilgrimsEye.class));
        cards.add(new SetCardInfo("Pillage", "A25-144", Rarity.COMMON, mage.cards.p.Pillage.class));
        cards.add(new SetCardInfo("Pillory of the Sleepless", "A25-213", Rarity.UNCOMMON, mage.cards.p.PilloryOfTheSleepless.class));
        cards.add(new SetCardInfo("Pinion Feast", "DTK-195", Rarity.COMMON, mage.cards.p.PinionFeast.class));
        cards.add(new SetCardInfo("Pit Keeper", "MM3-81", Rarity.COMMON, mage.cards.p.PitKeeper.class));
        cards.add(new SetCardInfo("Pitfall Trap", "MM3-18", Rarity.COMMON, mage.cards.p.PitfallTrap.class));
        cards.add(new SetCardInfo("Plague Wight", "RNA-82", Rarity.COMMON, mage.cards.p.PlagueWight.class));
        cards.add(new SetCardInfo("Plaguecrafter", "GRN-82", Rarity.UNCOMMON, mage.cards.p.Plaguecrafter.class));
        cards.add(new SetCardInfo("Plagued Rusalka", "MM2-89", Rarity.COMMON, mage.cards.p.PlaguedRusalka.class));
        cards.add(new SetCardInfo("Plaxcaster Frogling", "MM2-184", Rarity.UNCOMMON, mage.cards.p.PlaxcasterFrogling.class));
        cards.add(new SetCardInfo("Plummet", "RIX-143", Rarity.COMMON, mage.cards.p.Plummet.class));
        cards.add(new SetCardInfo("Pollenbright Wings", "GK1-115", Rarity.UNCOMMON, mage.cards.p.PollenbrightWings.class));
        cards.add(new SetCardInfo("Pondering Mage", "MH1-63", Rarity.COMMON, mage.cards.p.PonderingMage.class));
        cards.add(new SetCardInfo("Portent", "C18-97", Rarity.COMMON, mage.cards.p.Portent.class));
        cards.add(new SetCardInfo("Pouncing Cheetah", "AKH-179", Rarity.COMMON, mage.cards.p.PouncingCheetah.class));
        cards.add(new SetCardInfo("Prakhata Club Security", "KLD-98", Rarity.COMMON, mage.cards.p.PrakhataClubSecurity.class));
        cards.add(new SetCardInfo("Precursor Golem", "MM2-225", Rarity.RARE, mage.cards.p.PrecursorGolem.class));
        cards.add(new SetCardInfo("Predict", "C18-98", Rarity.UNCOMMON, mage.cards.p.Predict.class));
        cards.add(new SetCardInfo("Preordain", "CMR-84", Rarity.COMMON, mage.cards.p.Preordain.class));
        cards.add(new SetCardInfo("Pressure Point", "FRF-21", Rarity.COMMON, mage.cards.p.PressurePoint.class));
        cards.add(new SetCardInfo("Prey Upon", "GRN-143", Rarity.COMMON, mage.cards.p.PreyUpon.class));
        cards.add(new SetCardInfo("Prey's Vengeance", "IMA-182", Rarity.COMMON, mage.cards.p.PreysVengeance.class));
        cards.add(new SetCardInfo("Preyseizer Dragon", "PCA-50", Rarity.RARE, mage.cards.p.PreyseizerDragon.class));
        cards.add(new SetCardInfo("Price of Progress", "EMA-141", Rarity.UNCOMMON, mage.cards.p.PriceOfProgress.class));
        cards.add(new SetCardInfo("Prickleboar", "ORI-158", Rarity.COMMON, mage.cards.p.Prickleboar.class));
        cards.add(new SetCardInfo("Priest of Titania", "C14-210", Rarity.COMMON, mage.cards.p.PriestOfTitania.class));
        cards.add(new SetCardInfo("Prodigal Sorcerer", "EMA-67", Rarity.UNCOMMON, mage.cards.p.ProdigalSorcerer.class));
        cards.add(new SetCardInfo("Promise of Bunrei", "A25-30", Rarity.UNCOMMON, mage.cards.p.PromiseOfBunrei.class));
        cards.add(new SetCardInfo("Propaganda", "C16-94", Rarity.UNCOMMON, mage.cards.p.Propaganda.class));
        // Prophetic Bolt is not in the collation booster, as it was only added for the second Mystery Booster.
        cards.add(new SetCardInfo("Prophetic Bolt", "C15-231", Rarity.RARE, mage.cards.p.PropheticBolt.class));
        cards.add(new SetCardInfo("Prophetic Prism", "A25-229", Rarity.COMMON, mage.cards.p.PropheticPrism.class));
        cards.add(new SetCardInfo("Prophetic Ravings", "EMN-139", Rarity.COMMON, mage.cards.p.PropheticRavings.class));
        cards.add(new SetCardInfo("Prosperous Pirates", "XLN-69", Rarity.COMMON, mage.cards.p.ProsperousPirates.class));
        cards.add(new SetCardInfo("Prowling Caracal", "RNA-17", Rarity.COMMON, mage.cards.p.ProwlingCaracal.class));
        cards.add(new SetCardInfo("Prowling Pangolin", "EMA-104", Rarity.COMMON, mage.cards.p.ProwlingPangolin.class));
        cards.add(new SetCardInfo("Pulse of Murasa", "OGW-141", Rarity.COMMON, mage.cards.p.PulseOfMurasa.class));
        cards.add(new SetCardInfo("Purphoros, God of the Forge", "THS-135", Rarity.MYTHIC, mage.cards.p.PurphorosGodOfTheForge.class));
        cards.add(new SetCardInfo("Purple-Crystal Crab", "GS1-3", Rarity.COMMON, mage.cards.p.PurpleCrystalCrab.class));
        cards.add(new SetCardInfo("Putrefy", "KHC-91", Rarity.UNCOMMON, mage.cards.p.Putrefy.class));
        cards.add(new SetCardInfo("Pyrotechnics", "FRF-111", Rarity.UNCOMMON, mage.cards.p.Pyrotechnics.class));
        cards.add(new SetCardInfo("Qasali Pridemage", "C17-189", Rarity.COMMON, mage.cards.q.QasaliPridemage.class));
        cards.add(new SetCardInfo("Quakefoot Cyclops", "MH1-142", Rarity.COMMON, mage.cards.q.QuakefootCyclops.class));
        cards.add(new SetCardInfo("Queen Marchesa", "CN2-78", Rarity.MYTHIC, mage.cards.q.QueenMarchesa.class));
        cards.add(new SetCardInfo("Queen's Agent", "XLN-114", Rarity.COMMON, mage.cards.q.QueensAgent.class));
        cards.add(new SetCardInfo("Quest for the Gravelord", "BBD-156", Rarity.UNCOMMON, mage.cards.q.QuestForTheGravelord.class));
        cards.add(new SetCardInfo("Questing Phelddagrif", "PLS-119", Rarity.RARE, mage.cards.q.QuestingPhelddagrif.class));
        cards.add(new SetCardInfo("Quiet Disrepair", "PCA-75", Rarity.COMMON, mage.cards.q.QuietDisrepair.class));
        cards.add(new SetCardInfo("Rabid Bloodsucker", "ORI-113", Rarity.COMMON, mage.cards.r.RabidBloodsucker.class));
        cards.add(new SetCardInfo("Raff Capashen, Ship's Mage", "DOM-202", Rarity.UNCOMMON, mage.cards.r.RaffCapashenShipsMage.class));
        cards.add(new SetCardInfo("Rage Reflection", "SHM-104", Rarity.RARE, mage.cards.r.RageReflection.class));
        cards.add(new SetCardInfo("Raging Swordtooth", "XLN-226", Rarity.UNCOMMON, mage.cards.r.RagingSwordtooth.class));
        cards.add(new SetCardInfo("Rain of Thorns", "C17-156", Rarity.UNCOMMON, mage.cards.r.RainOfThorns.class));
        cards.add(new SetCardInfo("Rakdos Drake", "IMA-103", Rarity.COMMON, mage.cards.r.RakdosDrake.class));
        cards.add(new SetCardInfo("Rakshasa's Secret", "KTK-84", Rarity.COMMON, mage.cards.r.RakshasasSecret.class));
        cards.add(new SetCardInfo("Rally the Peasants", "EMA-25", Rarity.COMMON, mage.cards.r.RallyThePeasants.class));
        cards.add(new SetCardInfo("Rampaging Cyclops", "DOM-139", Rarity.COMMON, mage.cards.r.RampagingCyclops.class));
        cards.add(new SetCardInfo("Rampant Growth", "DDS-48", Rarity.COMMON, mage.cards.r.RampantGrowth.class));
        cards.add(new SetCardInfo("Rancor", "A25-186", Rarity.UNCOMMON, mage.cards.r.Rancor.class));
        cards.add(new SetCardInfo("Ranger's Guile", "M15-193", Rarity.COMMON, mage.cards.r.RangersGuile.class));
        cards.add(new SetCardInfo("Raptor Companion", "RIX-19", Rarity.COMMON, mage.cards.r.RaptorCompanion.class));
        cards.add(new SetCardInfo("Ravenous Chupacabra", "RIX-82", Rarity.UNCOMMON, mage.cards.r.RavenousChupacabra.class));
        cards.add(new SetCardInfo("Ravenous Leucrocota", "CN2-192", Rarity.COMMON, mage.cards.r.RavenousLeucrocota.class));
        cards.add(new SetCardInfo("Read the Bones", "DDP-56", Rarity.COMMON, mage.cards.r.ReadTheBones.class));
        cards.add(new SetCardInfo("Reality Scramble", "C18-25", Rarity.RARE, mage.cards.r.RealityScramble.class));
        cards.add(new SetCardInfo("Reaper of Night", "ELD-102", Rarity.COMMON, mage.cards.r.ReaperOfNight.class));
        cards.add(new SetCardInfo("Reassembling Skeleton", "M19-116", Rarity.UNCOMMON, mage.cards.r.ReassemblingSkeleton.class));
        cards.add(new SetCardInfo("Reckless Fireweaver", "KLD-126", Rarity.COMMON, mage.cards.r.RecklessFireweaver.class));
        cards.add(new SetCardInfo("Reckless Imp", "DTK-115", Rarity.COMMON, mage.cards.r.RecklessImp.class));
        cards.add(new SetCardInfo("Reckless Spite", "E01-37", Rarity.UNCOMMON, mage.cards.r.RecklessSpite.class));
        cards.add(new SetCardInfo("Reckless Wurm", "UMA-144", Rarity.COMMON, mage.cards.r.RecklessWurm.class));
        cards.add(new SetCardInfo("Reclaim", "ORI-195", Rarity.COMMON, mage.cards.r.Reclaim.class));
        cards.add(new SetCardInfo("Reclaiming Vines", "BFZ-185", Rarity.COMMON, mage.cards.r.ReclaimingVines.class));
        cards.add(new SetCardInfo("Reclusive Artificer", "DDU-51", Rarity.UNCOMMON, mage.cards.r.ReclusiveArtificer.class));
        cards.add(new SetCardInfo("Recoup", "DDK-63", Rarity.UNCOMMON, mage.cards.r.Recoup.class));
        cards.add(new SetCardInfo("Recover", "ZNR-180", Rarity.COMMON, mage.cards.r.Recover.class));
        cards.add(new SetCardInfo("Recruiter of the Guard", "CN2-22", Rarity.RARE, mage.cards.r.RecruiterOfTheGuard.class));
        cards.add(new SetCardInfo("Reflector Mage", "OGW-157", Rarity.UNCOMMON, mage.cards.r.ReflectorMage.class));
        cards.add(new SetCardInfo("Refocus", "FRF-47", Rarity.COMMON, mage.cards.r.Refocus.class));
        cards.add(new SetCardInfo("Refurbish", "KLD-25", Rarity.UNCOMMON, mage.cards.r.Refurbish.class));
        cards.add(new SetCardInfo("Regrowth", "A25-187", Rarity.UNCOMMON, mage.cards.r.Regrowth.class));
        cards.add(new SetCardInfo("Release the Ants", "MOR-98", Rarity.UNCOMMON, mage.cards.r.ReleaseTheAnts.class));
        cards.add(new SetCardInfo("Release the Gremlins", "AER-96", Rarity.RARE, mage.cards.r.ReleaseTheGremlins.class));
        cards.add(new SetCardInfo("Relic Crush", "CM2-142", Rarity.COMMON, mage.cards.r.RelicCrush.class));
        cards.add(new SetCardInfo("Reliquary Tower", "C21-311", Rarity.UNCOMMON, mage.cards.r.ReliquaryTower.class));
        cards.add(new SetCardInfo("Renegade Demon", "DDR-59", Rarity.COMMON, mage.cards.r.RenegadeDemon.class));
        cards.add(new SetCardInfo("Renegade Map", "AER-173", Rarity.COMMON, mage.cards.r.RenegadeMap.class));
        cards.add(new SetCardInfo("Renegade Tactics", "KLD-127", Rarity.COMMON, mage.cards.r.RenegadeTactics.class));
        cards.add(new SetCardInfo("Renegade's Getaway", "AER-69", Rarity.COMMON, mage.cards.r.RenegadesGetaway.class));
        cards.add(new SetCardInfo("Renewed Faith", "A25-31", Rarity.COMMON, mage.cards.r.RenewedFaith.class));
        cards.add(new SetCardInfo("Repulse", "CN2-119", Rarity.COMMON, mage.cards.r.Repulse.class));
        cards.add(new SetCardInfo("Resurrection", "UMA-30", Rarity.COMMON, mage.cards.r.Resurrection.class));
        cards.add(new SetCardInfo("Retraction Helix", "A25-71", Rarity.COMMON, mage.cards.r.RetractionHelix.class));
        cards.add(new SetCardInfo("Retreat to Emeria", "BFZ-44", Rarity.UNCOMMON, mage.cards.r.RetreatToEmeria.class));
        cards.add(new SetCardInfo("Return to the Earth", "BBD-210", Rarity.COMMON, mage.cards.r.ReturnToTheEarth.class));
        cards.add(new SetCardInfo("Returned Centaur", "ORI-116", Rarity.COMMON, mage.cards.r.ReturnedCentaur.class));
        cards.add(new SetCardInfo("Revel in Riches", "XLN-117", Rarity.RARE, mage.cards.r.RevelInRiches.class));
        cards.add(new SetCardInfo("Revenant", "SOI-110", Rarity.UNCOMMON, mage.cards.r.Revenant.class));
        cards.add(new SetCardInfo("Revive", "MM3-133", Rarity.COMMON, mage.cards.r.Revive.class));
        cards.add(new SetCardInfo("Reviving Dose", "CN2-97", Rarity.COMMON, mage.cards.r.RevivingDose.class));
        cards.add(new SetCardInfo("Rhet-Crop Spearmaster", "AKH-26", Rarity.COMMON, mage.cards.r.RhetCropSpearmaster.class));
        cards.add(new SetCardInfo("Rhonas's Monument", "AKH-236", Rarity.UNCOMMON, mage.cards.r.RhonassMonument.class));
        cards.add(new SetCardInfo("Rhox Maulers", "ORI-196", Rarity.COMMON, mage.cards.r.RhoxMaulers.class));
        cards.add(new SetCardInfo("Rhox War Monk", "MM3-180", Rarity.UNCOMMON, mage.cards.r.RhoxWarMonk.class));
        cards.add(new SetCardInfo("Rhys the Redeemed", "SHM-237", Rarity.RARE, mage.cards.r.RhysTheRedeemed.class));
        cards.add(new SetCardInfo("Rhystic Study", "PCY-45", Rarity.COMMON, mage.cards.r.RhysticStudy.class));
        cards.add(new SetCardInfo("Riftwing Cloudskate", "DD2-15", Rarity.UNCOMMON, mage.cards.r.RiftwingCloudskate.class));
        cards.add(new SetCardInfo("Righteous Cause", "CMA-21", Rarity.UNCOMMON, mage.cards.r.RighteousCause.class));
        cards.add(new SetCardInfo("Ringwarden Owl", "ORI-68", Rarity.COMMON, mage.cards.r.RingwardenOwl.class));
        cards.add(new SetCardInfo("Riparian Tiger", "KLD-167", Rarity.COMMON, mage.cards.r.RiparianTiger.class));
        cards.add(new SetCardInfo("Riptide Crab", "BBD-228", Rarity.COMMON, mage.cards.r.RiptideCrab.class));
        cards.add(new SetCardInfo("Rishadan Footpad", "MMQ-94", Rarity.UNCOMMON, mage.cards.r.RishadanFootpad.class));
        cards.add(new SetCardInfo("Rite of the Serpent", "KTK-86", Rarity.COMMON, mage.cards.r.RiteOfTheSerpent.class));
        cards.add(new SetCardInfo("Rith, the Awakener", "DDE-48", Rarity.RARE, mage.cards.r.RithTheAwakener.class));
        cards.add(new SetCardInfo("Rivals' Duel", "PCA-51", Rarity.UNCOMMON, mage.cards.r.RivalsDuel.class));
        cards.add(new SetCardInfo("River Boa", "ZEN-180", Rarity.UNCOMMON, mage.cards.r.RiverBoa.class));
        cards.add(new SetCardInfo("River Darter", "RIX-47", Rarity.COMMON, mage.cards.r.RiverDarter.class));
        cards.add(new SetCardInfo("River Hoopoe", "HOU-143", Rarity.UNCOMMON, mage.cards.r.RiverHoopoe.class));
        cards.add(new SetCardInfo("River Serpent", "AKH-66", Rarity.COMMON, mage.cards.r.RiverSerpent.class));
        cards.add(new SetCardInfo("Riverwheel Aerialists", "IMA-71", Rarity.COMMON, mage.cards.r.RiverwheelAerialists.class));
        cards.add(new SetCardInfo("Roar of the Wurm", "DDS-49", Rarity.UNCOMMON, mage.cards.r.RoarOfTheWurm.class));
        cards.add(new SetCardInfo("Roast", "DTK-151", Rarity.UNCOMMON, mage.cards.r.Roast.class));
        cards.add(new SetCardInfo("Rogue's Passage", "UMA-250", Rarity.UNCOMMON, mage.cards.r.RoguesPassage.class));
        cards.add(new SetCardInfo("Rolling Thunder", "BFZ-154", Rarity.UNCOMMON, mage.cards.r.RollingThunder.class));
        cards.add(new SetCardInfo("Root Out", "SOI-224", Rarity.COMMON, mage.cards.r.RootOut.class));
        cards.add(new SetCardInfo("Rootborn Defenses", "MM3-21", Rarity.COMMON, mage.cards.r.RootbornDefenses.class));
        cards.add(new SetCardInfo("Roots", "EMA-183", Rarity.COMMON, mage.cards.r.Roots.class));
        cards.add(new SetCardInfo("Rosemane Centaur", "GRN-197", Rarity.COMMON, mage.cards.r.RosemaneCentaur.class));
        cards.add(new SetCardInfo("Rosethorn Halberd", "ELD-175", Rarity.COMMON, mage.cards.r.RosethornHalberd.class));
        cards.add(new SetCardInfo("Rosheen Meanderer", "IMA-206", Rarity.UNCOMMON, mage.cards.r.RosheenMeanderer.class));
        cards.add(new SetCardInfo("Rotfeaster Maggot", "M15-112", Rarity.COMMON, mage.cards.r.RotfeasterMaggot.class));
        cards.add(new SetCardInfo("Rubblebelt Maaka", "MM3-109", Rarity.COMMON, mage.cards.r.RubblebeltMaaka.class));
        cards.add(new SetCardInfo("Ruin Rat", "HOU-75", Rarity.COMMON, mage.cards.r.RuinRat.class));
        cards.add(new SetCardInfo("Ruinous Gremlin", "KLD-128", Rarity.COMMON, mage.cards.r.RuinousGremlin.class));
        cards.add(new SetCardInfo("Rummaging Goblin", "XLN-160", Rarity.COMMON, mage.cards.r.RummagingGoblin.class));
        cards.add(new SetCardInfo("Run Amok", "DOM-140", Rarity.COMMON, mage.cards.r.RunAmok.class));
        cards.add(new SetCardInfo("Rune-Scarred Demon", "IMA-106", Rarity.RARE, mage.cards.r.RuneScarredDemon.class));
        cards.add(new SetCardInfo("Runeclaw Bear", "M15-197", Rarity.COMMON, mage.cards.r.RuneclawBear.class));
        cards.add(new SetCardInfo("Rush of Adrenaline", "SOI-177", Rarity.COMMON, mage.cards.r.RushOfAdrenaline.class));
        cards.add(new SetCardInfo("Sacred Cat", "AKH-27", Rarity.COMMON, mage.cards.s.SacredCat.class));
        cards.add(new SetCardInfo("Sadistic Hypnotist", "ODY-159", Rarity.UNCOMMON, mage.cards.s.SadisticHypnotist.class));
        cards.add(new SetCardInfo("Sage of Lat-Nam", "DOM-64", Rarity.UNCOMMON, mage.cards.s.SageOfLatNam.class));
        cards.add(new SetCardInfo("Sagu Archer", "KTK-146", Rarity.COMMON, mage.cards.s.SaguArcher.class));
        cards.add(new SetCardInfo("Sailor of Means", "RIX-49", Rarity.COMMON, mage.cards.s.SailorOfMeans.class));
        cards.add(new SetCardInfo("Sakashima the Impostor", "SOK-53", Rarity.RARE, mage.cards.s.SakashimaTheImpostor.class));
        cards.add(new SetCardInfo("Sakura-Tribe Elder", "C18-160", Rarity.COMMON, mage.cards.s.SakuraTribeElder.class));
        cards.add(new SetCardInfo("Salivating Gremlins", "KLD-129", Rarity.COMMON, mage.cards.s.SalivatingGremlins.class));
        cards.add(new SetCardInfo("Samut's Sprint", "WAR-142", Rarity.COMMON, mage.cards.s.SamutsSprint.class));
        cards.add(new SetCardInfo("Sanctum Gargoyle", "C16-76", Rarity.COMMON, mage.cards.s.SanctumGargoyle.class));
        cards.add(new SetCardInfo("Sandsteppe Citadel", "KTK-241", Rarity.UNCOMMON, mage.cards.s.SandsteppeCitadel.class));
        cards.add(new SetCardInfo("Sandstone Oracle", "C15-52", Rarity.UNCOMMON, mage.cards.s.SandstoneOracle.class));
        cards.add(new SetCardInfo("Sandstorm Charger", "DTK-34", Rarity.COMMON, mage.cards.s.SandstormCharger.class));
        cards.add(new SetCardInfo("Sapphire Charm", "MIR-89", Rarity.COMMON, mage.cards.s.SapphireCharm.class));
        cards.add(new SetCardInfo("Saproling Migration", "DOM-178", Rarity.COMMON, mage.cards.s.SaprolingMigration.class));
        cards.add(new SetCardInfo("Sarkhan's Rage", "DTK-153", Rarity.COMMON, mage.cards.s.SarkhansRage.class));
        cards.add(new SetCardInfo("Satyr Enchanter", "M19-223", Rarity.UNCOMMON, mage.cards.s.SatyrEnchanter.class));
        cards.add(new SetCardInfo("Savage Knuckleblade", "KTK-197", Rarity.RARE, mage.cards.s.SavageKnuckleblade.class));
        cards.add(new SetCardInfo("Savage Punch", "KTK-147", Rarity.COMMON, mage.cards.s.SavagePunch.class));
        cards.add(new SetCardInfo("Savage Twister", "C18-190", Rarity.UNCOMMON, mage.cards.s.SavageTwister.class));
        cards.add(new SetCardInfo("Savannah Lions", "A25-33", Rarity.COMMON, mage.cards.s.SavannahLions.class));
        cards.add(new SetCardInfo("Scarab Feast", "AKH-106", Rarity.COMMON, mage.cards.s.ScarabFeast.class));
        cards.add(new SetCardInfo("Scatter the Seeds", "GK1-106", Rarity.COMMON, mage.cards.s.ScatterTheSeeds.class));
        cards.add(new SetCardInfo("Scoured Barrens", "C18-276", Rarity.COMMON, mage.cards.s.ScouredBarrens.class));
        cards.add(new SetCardInfo("Screamreach Brawler", "DTK-155", Rarity.COMMON, mage.cards.s.ScreamreachBrawler.class));
        cards.add(new SetCardInfo("Scroll Thief", "DDT-17", Rarity.COMMON, mage.cards.s.ScrollThief.class));
        cards.add(new SetCardInfo("Scrounger of Souls", "HOU-76", Rarity.COMMON, mage.cards.s.ScroungerOfSouls.class));
        cards.add(new SetCardInfo("Scuttling Death", "MM2-94", Rarity.COMMON, mage.cards.s.ScuttlingDeath.class));
        cards.add(new SetCardInfo("Sea Gate Oracle", "KHC-43", Rarity.COMMON, mage.cards.s.SeaGateOracle.class));
        cards.add(new SetCardInfo("Seal of Cleansing", "EMA-26", Rarity.COMMON, mage.cards.s.SealOfCleansing.class));
        cards.add(new SetCardInfo("Seal of Doom", "C15-135", Rarity.COMMON, mage.cards.s.SealOfDoom.class));
        cards.add(new SetCardInfo("Seal of Strength", "EMA-184", Rarity.COMMON, mage.cards.s.SealOfStrength.class));
        cards.add(new SetCardInfo("Sealock Monster", "DDO-42", Rarity.UNCOMMON, mage.cards.s.SealockMonster.class));
        cards.add(new SetCardInfo("Search for Tomorrow", "IMA-185", Rarity.COMMON, mage.cards.s.SearchForTomorrow.class));
        cards.add(new SetCardInfo("Searing Light", "OGW-33", Rarity.COMMON, mage.cards.s.SearingLight.class));
        cards.add(new SetCardInfo("Secrets of the Golden City", "RIX-52", Rarity.COMMON, mage.cards.s.SecretsOfTheGoldenCity.class));
        cards.add(new SetCardInfo("Sedraxis Specter", "MM3-181", Rarity.UNCOMMON, mage.cards.s.SedraxisSpecter.class));
        cards.add(new SetCardInfo("Seek the Horizon", "DDR-20", Rarity.UNCOMMON, mage.cards.s.SeekTheHorizon.class));
        cards.add(new SetCardInfo("Seek the Wilds", "BFZ-189", Rarity.COMMON, mage.cards.s.SeekTheWilds.class));
        cards.add(new SetCardInfo("Seeker of the Way", "KTK-22", Rarity.UNCOMMON, mage.cards.s.SeekerOfTheWay.class));
        cards.add(new SetCardInfo("Seismic Shift", "DOM-141", Rarity.COMMON, mage.cards.s.SeismicShift.class));
        cards.add(new SetCardInfo("Seismic Stomp", "EMA-146", Rarity.COMMON, mage.cards.s.SeismicStomp.class));
        cards.add(new SetCardInfo("Sejiri Refuge", "C18-280", Rarity.UNCOMMON, mage.cards.s.SejiriRefuge.class));
        cards.add(new SetCardInfo("Selesnya Guildmage", "GK1-119", Rarity.UNCOMMON, mage.cards.s.SelesnyaGuildmage.class));
        cards.add(new SetCardInfo("Selvala, Heart of the Wilds", "CN2-70", Rarity.MYTHIC, mage.cards.s.SelvalaHeartOfTheWilds.class));
        cards.add(new SetCardInfo("Send to Sleep", "ORI-71", Rarity.COMMON, mage.cards.s.SendToSleep.class));
        cards.add(new SetCardInfo("Sengir Vampire", "W17-19", Rarity.UNCOMMON, mage.cards.s.SengirVampire.class));
        cards.add(new SetCardInfo("Sensor Splicer", "MM3-23", Rarity.COMMON, mage.cards.s.SensorSplicer.class));
        cards.add(new SetCardInfo("Seraph of the Suns", "AKH-28", Rarity.UNCOMMON, mage.cards.s.SeraphOfTheSuns.class));
        cards.add(new SetCardInfo("Serendib Efreet", "EMA-70", Rarity.RARE, mage.cards.s.SerendibEfreet.class));
        cards.add(new SetCardInfo("Serra Disciple", "DOM-34", Rarity.COMMON, mage.cards.s.SerraDisciple.class));
        cards.add(new SetCardInfo("Serra's Embrace", "DDC-21", Rarity.UNCOMMON, mage.cards.s.SerrasEmbrace.class));
        cards.add(new SetCardInfo("Serrated Arrows", "DDD-20", Rarity.COMMON, mage.cards.s.SerratedArrows.class));
        cards.add(new SetCardInfo("Sewer Nemesis", "CM2-75", Rarity.RARE, mage.cards.s.SewerNemesis.class));
        cards.add(new SetCardInfo("Shadowcloak Vampire", "M15-113", Rarity.COMMON, mage.cards.s.ShadowcloakVampire.class));
        cards.add(new SetCardInfo("Shamanic Revelation", "FRF-138", Rarity.RARE, mage.cards.s.ShamanicRevelation.class));
        cards.add(new SetCardInfo("Shambling Attendants", "KTK-89", Rarity.COMMON, mage.cards.s.ShamblingAttendants.class));
        cards.add(new SetCardInfo("Shambling Goblin", "DTK-118", Rarity.COMMON, mage.cards.s.ShamblingGoblin.class));
        cards.add(new SetCardInfo("Shambling Remains", "DDN-12", Rarity.UNCOMMON, mage.cards.s.ShamblingRemains.class));
        cards.add(new SetCardInfo("Shape the Sands", "DTK-205", Rarity.COMMON, mage.cards.s.ShapeTheSands.class));
        cards.add(new SetCardInfo("Shaper Parasite", "C14-125", Rarity.COMMON, mage.cards.s.ShaperParasite.class));
        cards.add(new SetCardInfo("Shardless Agent", "PCA-104", Rarity.UNCOMMON, mage.cards.s.ShardlessAgent.class));
        cards.add(new SetCardInfo("Shatter", "MM3-77", Rarity.COMMON, mage.cards.s.Shatter.class));
        cards.add(new SetCardInfo("Shattering Spree", "GK1-34", Rarity.UNCOMMON, mage.cards.s.ShatteringSpree.class));
        cards.add(new SetCardInfo("Sheer Drop", "BFZ-48", Rarity.COMMON, mage.cards.s.SheerDrop.class));
        cards.add(new SetCardInfo("Shenanigans", "MH1-146", Rarity.COMMON, mage.cards.s.Shenanigans.class));
        cards.add(new SetCardInfo("Shimmerscale Drake", "AKH-70", Rarity.COMMON, mage.cards.s.ShimmerscaleDrake.class));
        cards.add(new SetCardInfo("Shining Aerosaur", "XLN-36", Rarity.COMMON, mage.cards.s.ShiningAerosaur.class));
        cards.add(new SetCardInfo("Shining Armor", "ELD-29", Rarity.COMMON, mage.cards.s.ShiningArmor.class));
        cards.add(new SetCardInfo("Shipwreck Looter", "XLN-76", Rarity.COMMON, mage.cards.s.ShipwreckLooter.class));
        cards.add(new SetCardInfo("Shipwreck Singer", "CN2-206", Rarity.UNCOMMON, mage.cards.s.ShipwreckSinger.class));
        cards.add(new SetCardInfo("Shock", "M19-156", Rarity.COMMON, mage.cards.s.Shock.class));
        cards.add(new SetCardInfo("Short Sword", "DOM-229", Rarity.COMMON, mage.cards.s.ShortSword.class));
        cards.add(new SetCardInfo("Shoulder to Shoulder", "BBD-105", Rarity.COMMON, mage.cards.s.ShoulderToShoulder.class));
        cards.add(new SetCardInfo("Shrewd Hatchling", "MM2-198", Rarity.UNCOMMON, mage.cards.s.ShrewdHatchling.class));
        cards.add(new SetCardInfo("Shriekmaw", "CMA-68", Rarity.UNCOMMON, mage.cards.s.Shriekmaw.class));
        cards.add(new SetCardInfo("Shrouded Lore", "PLC-91", Rarity.UNCOMMON, mage.cards.s.ShroudedLore.class));
        cards.add(new SetCardInfo("Siege Wurm", "GRN-144", Rarity.COMMON, mage.cards.s.SiegeWurm.class));
        cards.add(new SetCardInfo("Siegecraft", "KTK-23", Rarity.COMMON, mage.cards.s.Siegecraft.class));
        cards.add(new SetCardInfo("Sigil of Valor", "ORI-239", Rarity.UNCOMMON, mage.cards.s.SigilOfValor.class));
        cards.add(new SetCardInfo("Sigiled Starfish", "ORI-73", Rarity.UNCOMMON, mage.cards.s.SigiledStarfish.class));
        cards.add(new SetCardInfo("Silent Observer", "SOI-86", Rarity.COMMON, mage.cards.s.SilentObserver.class));
        cards.add(new SetCardInfo("Silhana Ledgewalker", "PCA-77", Rarity.COMMON, mage.cards.s.SilhanaLedgewalker.class));
        cards.add(new SetCardInfo("Silkweaver Elite", "AER-125", Rarity.COMMON, mage.cards.s.SilkweaverElite.class));
        cards.add(new SetCardInfo("Silumgar Butcher", "DTK-122", Rarity.COMMON, mage.cards.s.SilumgarButcher.class));
        cards.add(new SetCardInfo("Silverchase Fox", "BBD-106", Rarity.COMMON, mage.cards.s.SilverchaseFox.class));
        cards.add(new SetCardInfo("Silvergill Adept", "RIX-53", Rarity.UNCOMMON, mage.cards.s.SilvergillAdept.class));
        cards.add(new SetCardInfo("Simic Locket", "RNA-240", Rarity.COMMON, mage.cards.s.SimicLocket.class));
        cards.add(new SetCardInfo("Singing Bell Strike", "KTK-55", Rarity.COMMON, mage.cards.s.SingingBellStrike.class));
        cards.add(new SetCardInfo("Skaab Goliath", "ORI-74", Rarity.UNCOMMON, mage.cards.s.SkaabGoliath.class));
        cards.add(new SetCardInfo("Skarrg, the Rage Pits", "PCA-127", Rarity.UNCOMMON, mage.cards.s.SkarrgTheRagePits.class));
        cards.add(new SetCardInfo("Skeletal Scrying", "C14-162", Rarity.UNCOMMON, mage.cards.s.SkeletalScrying.class));
        cards.add(new SetCardInfo("Skeleton Archer", "M19-118", Rarity.COMMON, mage.cards.s.SkeletonArcher.class));
        cards.add(new SetCardInfo("Skirk Commando", "A25-150", Rarity.COMMON, mage.cards.s.SkirkCommando.class));
        cards.add(new SetCardInfo("Skirk Prospector", "DOM-144", Rarity.COMMON, mage.cards.s.SkirkProspector.class));
        cards.add(new SetCardInfo("Skitter Eel", "RNA-53", Rarity.COMMON, mage.cards.s.SkitterEel.class));
        cards.add(new SetCardInfo("Skittering Crustacean", "CN2-36", Rarity.COMMON, mage.cards.s.SkitteringCrustacean.class));
        cards.add(new SetCardInfo("Skulking Ghost", "EMA-107", Rarity.COMMON, mage.cards.s.SkulkingGhost.class));
        cards.add(new SetCardInfo("Skullclamp", "C20-251", Rarity.UNCOMMON, mage.cards.s.Skullclamp.class));
        cards.add(new SetCardInfo("Skyhunter Skirmisher", "MM2-32", Rarity.COMMON, mage.cards.s.SkyhunterSkirmisher.class));
        cards.add(new SetCardInfo("Skymarcher Aspirant", "RIX-21", Rarity.UNCOMMON, mage.cards.s.SkymarcherAspirant.class));
        cards.add(new SetCardInfo("Skyscanner", "M19-245", Rarity.COMMON, mage.cards.s.Skyscanner.class));
        cards.add(new SetCardInfo("Skyspear Cavalry", "UMA-36", Rarity.COMMON, mage.cards.s.SkyspearCavalry.class));
        cards.add(new SetCardInfo("Skyward Eye Prophets", "CMA-193", Rarity.UNCOMMON, mage.cards.s.SkywardEyeProphets.class));
        cards.add(new SetCardInfo("Slash of Talons", "XLN-38", Rarity.COMMON, mage.cards.s.SlashOfTalons.class));
        cards.add(new SetCardInfo("Slave of Bolas", "E01-86", Rarity.UNCOMMON, mage.cards.s.SlaveOfBolas.class));
        cards.add(new SetCardInfo("Sleep", "A25-213", Rarity.UNCOMMON, mage.cards.s.Sleep.class));
        cards.add(new SetCardInfo("Slipstream Eel", "CM2-49", Rarity.COMMON, mage.cards.s.SlipstreamEel.class));
        cards.add(new SetCardInfo("Slither Blade", "AKH-71", Rarity.COMMON, mage.cards.s.SlitherBlade.class));
        cards.add(new SetCardInfo("Sliver Hivelord", "M15-211", Rarity.MYTHIC, mage.cards.s.SliverHivelord.class));
        cards.add(new SetCardInfo("Smash to Smithereens", "MM2-124", Rarity.COMMON, mage.cards.s.SmashToSmithereens.class));
        cards.add(new SetCardInfo("Smelt", "M19-158", Rarity.COMMON, mage.cards.s.Smelt.class));
        cards.add(new SetCardInfo("Smiting Helix", "MH1-109", Rarity.UNCOMMON, mage.cards.s.SmitingHelix.class));
        cards.add(new SetCardInfo("Snake Umbra", "C18-162", Rarity.COMMON, mage.cards.s.SnakeUmbra.class));
        cards.add(new SetCardInfo("Snap", "DST-37", Rarity.COMMON, mage.cards.s.Snap.class));
        cards.add(new SetCardInfo("Snapping Drake", "M19-75", Rarity.COMMON, mage.cards.s.SnappingDrake.class));
        cards.add(new SetCardInfo("Snapping Sailback", "XLN-208", Rarity.UNCOMMON, mage.cards.s.SnappingSailback.class));
        cards.add(new SetCardInfo("Snubhorn Sentry", "RIX-23", Rarity.COMMON, mage.cards.s.SnubhornSentry.class));
        cards.add(new SetCardInfo("Sol Ring", "CMD-261", Rarity.UNCOMMON, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Solemn Simulacrum", "DDU-62", Rarity.RARE, mage.cards.s.SolemnSimulacrum.class));
        cards.add(new SetCardInfo("Somber Hoverguard", "MM2-57", Rarity.COMMON, mage.cards.s.SomberHoverguard.class));
        cards.add(new SetCardInfo("Soothsaying", "MMQ-104", Rarity.UNCOMMON, mage.cards.s.Soothsaying.class));
        cards.add(new SetCardInfo("Sorcerer's Broom", "ELD-232", Rarity.UNCOMMON, mage.cards.s.SorcerersBroom.class));
        cards.add(new SetCardInfo("Sorin Markov", "M12-109", Rarity.MYTHIC, mage.cards.s.SorinMarkov.class));
        cards.add(new SetCardInfo("Soul Manipulation", "MM3-185", Rarity.UNCOMMON, mage.cards.s.SoulManipulation.class));
        cards.add(new SetCardInfo("Soul Parry", "DDO-24", Rarity.COMMON, mage.cards.s.SoulParry.class));
        cards.add(new SetCardInfo("Soul Summons", "FRF-26", Rarity.COMMON, mage.cards.s.SoulSummons.class));
        cards.add(new SetCardInfo("Soul Warden", "MM3-24", Rarity.COMMON, mage.cards.s.SoulWarden.class));
        cards.add(new SetCardInfo("Soul-Strike Technique", "MH1-30", Rarity.COMMON, mage.cards.s.SoulStrikeTechnique.class));
        cards.add(new SetCardInfo("Soulmender", "M15-35", Rarity.COMMON, mage.cards.s.Soulmender.class));
        cards.add(new SetCardInfo("Sparkmage Apprentice", "DDN-48", Rarity.COMMON, mage.cards.s.SparkmageApprentice.class));
        cards.add(new SetCardInfo("Sparkspitter", "UMA-149", Rarity.COMMON, mage.cards.s.Sparkspitter.class));
        cards.add(new SetCardInfo("Sparktongue Dragon", "M19-159", Rarity.COMMON, mage.cards.s.SparktongueDragon.class));
        cards.add(new SetCardInfo("Sparring Mummy", "AKH-29", Rarity.COMMON, mage.cards.s.SparringMummy.class));
        cards.add(new SetCardInfo("Spawning Grounds", "C18-163", Rarity.RARE, mage.cards.s.SpawningGrounds.class));
        cards.add(new SetCardInfo("Spectral Gateguards", "DDQ-19", Rarity.COMMON, mage.cards.s.SpectralGateguards.class));
        cards.add(new SetCardInfo("Sphinx's Tutelage", "ORI-76", Rarity.UNCOMMON, mage.cards.s.SphinxsTutelage.class));
        cards.add(new SetCardInfo("Spider Spawning", "CMA-149", Rarity.UNCOMMON, mage.cards.s.SpiderSpawning.class));
        cards.add(new SetCardInfo("Spikeshot Goblin", "A25-152", Rarity.UNCOMMON, mage.cards.s.SpikeshotGoblin.class));
        cards.add(new SetCardInfo("Spire Monitor", "MM3-52", Rarity.COMMON, mage.cards.s.SpireMonitor.class));
        cards.add(new SetCardInfo("Spreading Rot", "XLN-125", Rarity.COMMON, mage.cards.s.SpreadingRot.class));
        cards.add(new SetCardInfo("Sprouting Thrinax", "MM3-189", Rarity.UNCOMMON, mage.cards.s.SproutingThrinax.class));
        // Card not implemented. BEWARE: When enabling this entry, add it to the appropriate booster slot!
        // It belongs into the Artifact/Land booster. Just uncomment the relevant entry down there.
        //cards.add(new SetCardInfo("Spy Kit", "CN2-79", Rarity.UNCOMMON, mage.cards.s.SpyKit.class));
        cards.add(new SetCardInfo("Squirrel Wrangler", "PCY-127", Rarity.RARE, mage.cards.s.SquirrelWrangler.class));
        cards.add(new SetCardInfo("Stab Wound", "M15-116", Rarity.UNCOMMON, mage.cards.s.StabWound.class));
        cards.add(new SetCardInfo("Staggershock", "IMA-147", Rarity.UNCOMMON, mage.cards.s.Staggershock.class));
        cards.add(new SetCardInfo("Stalking Tiger", "W17-28", Rarity.COMMON, mage.cards.s.StalkingTiger.class));
        cards.add(new SetCardInfo("Stallion of Ashmouth", "SOI-136", Rarity.COMMON, mage.cards.s.StallionOfAshmouth.class));
        cards.add(new SetCardInfo("Stalwart Aven", "IMA-32", Rarity.COMMON, mage.cards.s.StalwartAven.class));
        cards.add(new SetCardInfo("Star of Extinction", "XLN-161", Rarity.MYTHIC, mage.cards.s.StarOfExtinction.class));
        cards.add(new SetCardInfo("Star-Crowned Stag", "M19-38", Rarity.COMMON, mage.cards.s.StarCrownedStag.class));
        cards.add(new SetCardInfo("Stave Off", "DDN-61", Rarity.COMMON, mage.cards.s.StaveOff.class));
        cards.add(new SetCardInfo("Steadfast Sentinel", "HOU-24", Rarity.COMMON, mage.cards.s.SteadfastSentinel.class));
        cards.add(new SetCardInfo("Steady Progress", "MM2-58", Rarity.COMMON, mage.cards.s.SteadyProgress.class));
        cards.add(new SetCardInfo("Steamflogger Boss", "UST-93", Rarity.RARE, mage.cards.s.SteamfloggerBoss.class));
        cards.add(new SetCardInfo("Stinkweed Imp", "GK1-53", Rarity.COMMON, mage.cards.s.StinkweedImp.class));
        cards.add(new SetCardInfo("Stitched Drake", "DDQ-49", Rarity.COMMON, mage.cards.s.StitchedDrake.class));
        cards.add(new SetCardInfo("Stoic Builder", "SOI-231", Rarity.COMMON, mage.cards.s.StoicBuilder.class));
        cards.add(new SetCardInfo("Stone Haven Medic", "BFZ-51", Rarity.COMMON, mage.cards.s.StoneHavenMedic.class));
        cards.add(new SetCardInfo("Storm Sculptor", "XLN-85", Rarity.COMMON, mage.cards.s.StormSculptor.class));
        cards.add(new SetCardInfo("Stormblood Berserker", "E01-58", Rarity.UNCOMMON, mage.cards.s.StormbloodBerserker.class));
        cards.add(new SetCardInfo("Stormchaser Chimera", "CN2-207", Rarity.UNCOMMON, mage.cards.s.StormchaserChimera.class));
        cards.add(new SetCardInfo("Strategic Planning", "HOU-47", Rarity.COMMON, mage.cards.s.StrategicPlanning.class));
        cards.add(new SetCardInfo("Stream of Thought", "MH1-71", Rarity.COMMON, mage.cards.s.StreamOfThought.class));
        cards.add(new SetCardInfo("Street Wraith", "A25-108", Rarity.UNCOMMON, mage.cards.s.StreetWraith.class));
        cards.add(new SetCardInfo("Strength in Numbers", "MM3-138", Rarity.COMMON, mage.cards.s.StrengthInNumbers.class));
        cards.add(new SetCardInfo("Stromkirk Patrol", "CN2-149", Rarity.COMMON, mage.cards.s.StromkirkPatrol.class));
        cards.add(new SetCardInfo("Stunt Double", "CN2-38", Rarity.RARE, mage.cards.s.StuntDouble.class));
        cards.add(new SetCardInfo("Subtle Strike", "KLD-100", Rarity.COMMON, mage.cards.s.SubtleStrike.class));
        cards.add(new SetCardInfo("Sudden Demise", "E01-59", Rarity.RARE, mage.cards.s.SuddenDemise.class));
        cards.add(new SetCardInfo("Sulfurous Blast", "CMA-88", Rarity.UNCOMMON, mage.cards.s.SulfurousBlast.class));
        cards.add(new SetCardInfo("Sultai Charm", "KTK-204", Rarity.UNCOMMON, mage.cards.s.SultaiCharm.class));
        cards.add(new SetCardInfo("Sultai Runemark", "FRF-86", Rarity.COMMON, mage.cards.s.SultaiRunemark.class));
        cards.add(new SetCardInfo("Sultai Soothsayer", "KTK-205", Rarity.UNCOMMON, mage.cards.s.SultaiSoothsayer.class));
        cards.add(new SetCardInfo("Summit Prowler", "DTK-160", Rarity.COMMON, mage.cards.s.SummitProwler.class));
        cards.add(new SetCardInfo("Sun-Crowned Hunters", "XLN-164", Rarity.COMMON, mage.cards.s.SunCrownedHunters.class));
        cards.add(new SetCardInfo("Sunlance", "MM2-34", Rarity.COMMON, mage.cards.s.Sunlance.class));
        cards.add(new SetCardInfo("Sunrise Seeker", "XLN-40", Rarity.COMMON, mage.cards.s.SunriseSeeker.class));
        cards.add(new SetCardInfo("Sunset Pyramid", "HOU-166", Rarity.UNCOMMON, mage.cards.s.SunsetPyramid.class));
        cards.add(new SetCardInfo("Suppression Bonds", "ORI-34", Rarity.COMMON, mage.cards.s.SuppressionBonds.class));
        cards.add(new SetCardInfo("Supreme Verdict", "IMA-210", Rarity.RARE, mage.cards.s.SupremeVerdict.class));
        cards.add(new SetCardInfo("Surrakar Banisher", "DDO-43", Rarity.COMMON, mage.cards.s.SurrakarBanisher.class));
        cards.add(new SetCardInfo("Survive the Night", "SOI-41", Rarity.COMMON, mage.cards.s.SurviveTheNight.class));
        cards.add(new SetCardInfo("Suspicious Bookcase", "M19-246", Rarity.UNCOMMON, mage.cards.s.SuspiciousBookcase.class));
        cards.add(new SetCardInfo("Swashbuckling", "XLN-167", Rarity.COMMON, mage.cards.s.Swashbuckling.class));
        cards.add(new SetCardInfo("Sweatworks Brawler", "AER-100", Rarity.COMMON, mage.cards.s.SweatworksBrawler.class));
        cards.add(new SetCardInfo("Swift Kick", "KTK-122", Rarity.COMMON, mage.cards.s.SwiftKick.class));
        cards.add(new SetCardInfo("Swiftwater Cliffs", "C18-284", Rarity.COMMON, mage.cards.s.SwiftwaterCliffs.class));
        cards.add(new SetCardInfo("Sword of the Animist", "ORI-240", Rarity.RARE, mage.cards.s.SwordOfTheAnimist.class));
        cards.add(new SetCardInfo("Swords to Plowshares", "VOC-99", Rarity.UNCOMMON, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("Sylvan Bounty", "E01-74", Rarity.COMMON, mage.cards.s.SylvanBounty.class));
        cards.add(new SetCardInfo("Sylvan Scrying", "BFZ-192", Rarity.UNCOMMON, mage.cards.s.SylvanScrying.class));
        cards.add(new SetCardInfo("Syncopate", "DOM-67", Rarity.COMMON, mage.cards.s.Syncopate.class));
        cards.add(new SetCardInfo("Syr Elenora, the Discerning", "ELD-67", Rarity.UNCOMMON, mage.cards.s.SyrElenoraTheDiscerning.class));
        cards.add(new SetCardInfo("Tajuru Pathwarden", "OGW-145", Rarity.COMMON, mage.cards.t.TajuruPathwarden.class));
        cards.add(new SetCardInfo("Tajuru Warcaller", "BFZ-195", Rarity.UNCOMMON, mage.cards.t.TajuruWarcaller.class));
        cards.add(new SetCardInfo("Take Down", "KLD-170", Rarity.COMMON, mage.cards.t.TakeDown.class));
        cards.add(new SetCardInfo("Take Vengeance", "M19-40", Rarity.COMMON, mage.cards.t.TakeVengeance.class));
        cards.add(new SetCardInfo("Talons of Wildwood", "M19-202", Rarity.COMMON, mage.cards.t.TalonsOfWildwood.class));
        cards.add(new SetCardInfo("Talrand, Sky Summoner", "DDS-11", Rarity.RARE, mage.cards.t.TalrandSkySummoner.class));
        cards.add(new SetCardInfo("Tandem Lookout", "MM3-53", Rarity.COMMON, mage.cards.t.TandemLookout.class));
        cards.add(new SetCardInfo("Tandem Tactics", "BBD-112", Rarity.COMMON, mage.cards.t.TandemTactics.class));
        cards.add(new SetCardInfo("Tar Snare", "OGW-90", Rarity.COMMON, mage.cards.t.TarSnare.class));
        cards.add(new SetCardInfo("Tarfire", "DDT-55", Rarity.COMMON, mage.cards.t.Tarfire.class));
        cards.add(new SetCardInfo("Tatyova, Benthic Druid", "DOM-206", Rarity.UNCOMMON, mage.cards.t.TatyovaBenthicDruid.class));
        cards.add(new SetCardInfo("Taurean Mauler", "MOR-109", Rarity.RARE, mage.cards.t.TaureanMauler.class));
        cards.add(new SetCardInfo("Tavern Swindler", "BBD-162", Rarity.UNCOMMON, mage.cards.t.TavernSwindler.class));
        cards.add(new SetCardInfo("Tectonic Edge", "C14-313", Rarity.UNCOMMON, mage.cards.t.TectonicEdge.class));
        cards.add(new SetCardInfo("Tectonic Rift", "M19-162", Rarity.UNCOMMON, mage.cards.t.TectonicRift.class));
        cards.add(new SetCardInfo("Teferi's Protection", "C17-8", Rarity.RARE, mage.cards.t.TeferisProtection.class));
        cards.add(new SetCardInfo("Teferi, Temporal Archmage", "C14-19", Rarity.MYTHIC, mage.cards.t.TeferiTemporalArchmage.class));
        cards.add(new SetCardInfo("Temple of the False God", "C21-326", Rarity.UNCOMMON, mage.cards.t.TempleOfTheFalseGod.class));
        cards.add(new SetCardInfo("Temporal Fissure", "DDS-12", Rarity.COMMON, mage.cards.t.TemporalFissure.class));
        cards.add(new SetCardInfo("Temporal Mastery", "MM3-54", Rarity.MYTHIC, mage.cards.t.TemporalMastery.class));
        cards.add(new SetCardInfo("Tempt with Discovery", "C16-170", Rarity.RARE, mage.cards.t.TemptWithDiscovery.class));
        cards.add(new SetCardInfo("Temur Battle Rage", "CMR-417", Rarity.COMMON, mage.cards.t.TemurBattleRage.class));
        cards.add(new SetCardInfo("Tendrils of Corruption", "C14-166", Rarity.COMMON, mage.cards.t.TendrilsOfCorruption.class));
        cards.add(new SetCardInfo("Terashi's Grasp", "MM2-37", Rarity.COMMON, mage.cards.t.TerashisGrasp.class));
        cards.add(new SetCardInfo("Terminate", "P09-9", Rarity.COMMON, mage.cards.t.Terminate.class));
        cards.add(new SetCardInfo("Terrain Elemental", "KLD-272", Rarity.COMMON, mage.cards.t.TerrainElemental.class));
        cards.add(new SetCardInfo("Territorial Baloth", "DDP-24", Rarity.COMMON, mage.cards.t.TerritorialBaloth.class));
        cards.add(new SetCardInfo("Territorial Hammerskull", "XLN-41", Rarity.COMMON, mage.cards.t.TerritorialHammerskull.class));
        cards.add(new SetCardInfo("Thalia's Lancers", "EMN-47", Rarity.RARE, mage.cards.t.ThaliasLancers.class));
        cards.add(new SetCardInfo("Thallid Omnivore", "DOM-106", Rarity.COMMON, mage.cards.t.ThallidOmnivore.class));
        cards.add(new SetCardInfo("The Crowd Goes Wild", "BBD-68", Rarity.UNCOMMON, mage.cards.t.TheCrowdGoesWild.class));
        cards.add(new SetCardInfo("The Eldest Reborn", "DOM-90", Rarity.UNCOMMON, mage.cards.t.TheEldestReborn.class));
        cards.add(new SetCardInfo("The Gitrog Monster", "SOI-245", Rarity.MYTHIC, mage.cards.t.TheGitrogMonster.class));
        cards.add(new SetCardInfo("The Mirari Conjecture", "DOM-57", Rarity.RARE, mage.cards.t.TheMirariConjecture.class));
        cards.add(new SetCardInfo("Thieving Magpie", "UDS-49", Rarity.UNCOMMON, mage.cards.t.ThievingMagpie.class));
        cards.add(new SetCardInfo("Thopter Foundry", "ARB-133", Rarity.UNCOMMON, mage.cards.t.ThopterFoundry.class));
        cards.add(new SetCardInfo("Thorn of the Black Rose", "CN2-48", Rarity.COMMON, mage.cards.t.ThornOfTheBlackRose.class));
        cards.add(new SetCardInfo("Thornbow Archer", "ORI-121", Rarity.COMMON, mage.cards.t.ThornbowArcher.class));
        cards.add(new SetCardInfo("Thornhide Wolves", "M19-204", Rarity.COMMON, mage.cards.t.ThornhideWolves.class));
        cards.add(new SetCardInfo("Thornscape Battlemage", "MM3-142", Rarity.UNCOMMON, mage.cards.t.ThornscapeBattlemage.class));
        cards.add(new SetCardInfo("Thornweald Archer", "CMA-154", Rarity.COMMON, mage.cards.t.ThornwealdArcher.class));
        cards.add(new SetCardInfo("Thornwind Faeries", "CMA-42", Rarity.COMMON, mage.cards.t.ThornwindFaeries.class));
        cards.add(new SetCardInfo("Thornwood Falls", "C18-287", Rarity.COMMON, mage.cards.t.ThornwoodFalls.class));
        cards.add(new SetCardInfo("Thought Collapse", "RNA-57", Rarity.COMMON, mage.cards.t.ThoughtCollapse.class));
        cards.add(new SetCardInfo("Thought Erasure", "GRN-206", Rarity.UNCOMMON, mage.cards.t.ThoughtErasure.class));
        cards.add(new SetCardInfo("Thought Scour", "IMA-76", Rarity.COMMON, mage.cards.t.ThoughtScour.class));
        cards.add(new SetCardInfo("Thought Vessel", "CMR-346", Rarity.COMMON, mage.cards.t.ThoughtVessel.class));
        cards.add(new SetCardInfo("Thoughtcast", "MM2-64", Rarity.COMMON, mage.cards.t.Thoughtcast.class));
        cards.add(new SetCardInfo("Thraben Foulbloods", "EMN-108", Rarity.COMMON, mage.cards.t.ThrabenFoulbloods.class));
        cards.add(new SetCardInfo("Thraben Inspector", "SOI-44", Rarity.COMMON, mage.cards.t.ThrabenInspector.class));
        cards.add(new SetCardInfo("Thraben Standard Bearer", "EMN-48", Rarity.COMMON, mage.cards.t.ThrabenStandardBearer.class));
        cards.add(new SetCardInfo("Thran Dynamo", "IMA-230", Rarity.UNCOMMON, mage.cards.t.ThranDynamo.class));
        cards.add(new SetCardInfo("Thran Golem", "PCA-114", Rarity.UNCOMMON, mage.cards.t.ThranGolem.class));
        cards.add(new SetCardInfo("Thrashing Brontodon", "RIX-148", Rarity.UNCOMMON, mage.cards.t.ThrashingBrontodon.class));
        cards.add(new SetCardInfo("Thresher Lizard", "AKH-150", Rarity.COMMON, mage.cards.t.ThresherLizard.class));
        cards.add(new SetCardInfo("Thrill of Possibility", "ELD-146", Rarity.COMMON, mage.cards.t.ThrillOfPossibility.class));
        cards.add(new SetCardInfo("Thrive", "MM2-166", Rarity.COMMON, mage.cards.t.Thrive.class));
        cards.add(new SetCardInfo("Thrummingbird", "CM2-52", Rarity.UNCOMMON, mage.cards.t.Thrummingbird.class));
        cards.add(new SetCardInfo("Thrun, the Last Troll", "MBS-92", Rarity.MYTHIC, mage.cards.t.ThrunTheLastTroll.class));
        cards.add(new SetCardInfo("Thunder Drake", "WAR-73", Rarity.COMMON, mage.cards.t.ThunderDrake.class));
        cards.add(new SetCardInfo("Tibalt's Rager", "WAR-147", Rarity.UNCOMMON, mage.cards.t.TibaltsRager.class));
        cards.add(new SetCardInfo("Tidal Warrior", "DDT-20", Rarity.COMMON, mage.cards.t.TidalWarrior.class));
        cards.add(new SetCardInfo("Tidal Wave", "EMA-75", Rarity.COMMON, mage.cards.t.TidalWave.class));
        cards.add(new SetCardInfo("Tidy Conclusion", "KLD-103", Rarity.COMMON, mage.cards.t.TidyConclusion.class));
        cards.add(new SetCardInfo("Timberwatch Elf", "EMA-190", Rarity.UNCOMMON, mage.cards.t.TimberwatchElf.class));
        cards.add(new SetCardInfo("Time Sieve", "ARB-31", Rarity.RARE, mage.cards.t.TimeSieve.class));
        cards.add(new SetCardInfo("Time to Feed", "DDO-50", Rarity.COMMON, mage.cards.t.TimeToFeed.class));
        cards.add(new SetCardInfo("Timely Reinforcements", "M12-40", Rarity.UNCOMMON, mage.cards.t.TimelyReinforcements.class));
        cards.add(new SetCardInfo("Tinker", "C16-133", Rarity.UNCOMMON, mage.cards.t.Tinker.class));
        cards.add(new SetCardInfo("Tireless Tracker", "SOI-233", Rarity.RARE, mage.cards.t.TirelessTracker.class));
        cards.add(new SetCardInfo("Titanic Growth", "M19-205", Rarity.COMMON, mage.cards.t.TitanicGrowth.class));
        cards.add(new SetCardInfo("Tithe Drinker", "C17-200", Rarity.COMMON, mage.cards.t.TitheDrinker.class));
        cards.add(new SetCardInfo("Topan Freeblade", "ORI-36", Rarity.COMMON, mage.cards.t.TopanFreeblade.class));
        cards.add(new SetCardInfo("Torch Courier", "GRN-119", Rarity.COMMON, mage.cards.t.TorchCourier.class));
        cards.add(new SetCardInfo("Torment of Hailfire", "HOU-77", Rarity.RARE, mage.cards.t.TormentOfHailfire.class));
        cards.add(new SetCardInfo("Torment of Venom", "HOU-79", Rarity.COMMON, mage.cards.t.TormentOfVenom.class));
        cards.add(new SetCardInfo("Tormod's Crypt", "C14-278", Rarity.UNCOMMON, mage.cards.t.TormodsCrypt.class));
        cards.add(new SetCardInfo("Totally Lost", "BBD-135", Rarity.COMMON, mage.cards.t.TotallyLost.class));
        cards.add(new SetCardInfo("Touch of Moonglove", "ORI-123", Rarity.COMMON, mage.cards.t.TouchOfMoonglove.class));
        cards.add(new SetCardInfo("Tower Gargoyle", "MM3-196", Rarity.UNCOMMON, mage.cards.t.TowerGargoyle.class));
        cards.add(new SetCardInfo("Tower of Eons", "MRD-266", Rarity.RARE, mage.cards.t.TowerOfEons.class));
        cards.add(new SetCardInfo("Toxin Sliver", "LGN-84", Rarity.RARE, mage.cards.t.ToxinSliver.class));
        cards.add(new SetCardInfo("Trading Post", "CM2-225", Rarity.RARE, mage.cards.t.TradingPost.class));
        cards.add(new SetCardInfo("Tragic Slip", "C14-167", Rarity.COMMON, mage.cards.t.TragicSlip.class));
        cards.add(new SetCardInfo("Trail of Evidence", "SOI-93", Rarity.UNCOMMON, mage.cards.t.TrailOfEvidence.class));
        cards.add(new SetCardInfo("Treacherous Terrain", "C16-47", Rarity.UNCOMMON, mage.cards.t.TreacherousTerrain.class));
        cards.add(new SetCardInfo("Treasure Cruise", "KTK-59", Rarity.COMMON, mage.cards.t.TreasureCruise.class));
        cards.add(new SetCardInfo("Treasure Hunt", "C18-109", Rarity.COMMON, mage.cards.t.TreasureHunt.class));
        cards.add(new SetCardInfo("Treasure Mage", "DDU-40", Rarity.UNCOMMON, mage.cards.t.TreasureMage.class));
        cards.add(new SetCardInfo("Trepanation Blade", "IMA-231", Rarity.UNCOMMON, mage.cards.t.TrepanationBlade.class));
        cards.add(new SetCardInfo("Trespasser's Curse", "AKH-112", Rarity.COMMON, mage.cards.t.TrespassersCurse.class));
        cards.add(new SetCardInfo("Trial of Ambition", "AKH-113", Rarity.UNCOMMON, mage.cards.t.TrialOfAmbition.class));
        cards.add(new SetCardInfo("Trinket Mage", "DDU-41", Rarity.COMMON, mage.cards.t.TrinketMage.class));
        cards.add(new SetCardInfo("Triton Tactics", "DDT-23", Rarity.UNCOMMON, mage.cards.t.TritonTactics.class));
        cards.add(new SetCardInfo("Triumph of the Hordes", "NPH-123", Rarity.UNCOMMON, mage.cards.t.TriumphOfTheHordes.class));
        cards.add(new SetCardInfo("Tukatongue Thallid", "PCA-79", Rarity.COMMON, mage.cards.t.TukatongueThallid.class));
        cards.add(new SetCardInfo("Turn Aside", "EMN-78", Rarity.COMMON, mage.cards.t.TurnAside.class));
        cards.add(new SetCardInfo("Turntimber Basilisk", "E01-76", Rarity.UNCOMMON, mage.cards.t.TurntimberBasilisk.class));
        cards.add(new SetCardInfo("Twins of Maurer Estate", "SOI-142", Rarity.COMMON, mage.cards.t.TwinsOfMaurerEstate.class));
        cards.add(new SetCardInfo("Two-Headed Giant", "DOM-147", Rarity.RARE, mage.cards.t.TwoHeadedGiant.class));
        cards.add(new SetCardInfo("Typhoid Rats", "M15-118", Rarity.COMMON, mage.cards.t.TyphoidRats.class));
        cards.add(new SetCardInfo("Umbral Mantle", "SHM-267", Rarity.UNCOMMON, mage.cards.u.UmbralMantle.class));
        cards.add(new SetCardInfo("Unburden", "AKH-114", Rarity.COMMON, mage.cards.u.Unburden.class));
        cards.add(new SetCardInfo("Uncaged Fury", "A25-155", Rarity.COMMON, mage.cards.u.UncagedFury.class));
        cards.add(new SetCardInfo("Unclaimed Territory", "XLN-258", Rarity.UNCOMMON, mage.cards.u.UnclaimedTerritory.class));
        cards.add(new SetCardInfo("Uncomfortable Chill", "M19-82", Rarity.COMMON, mage.cards.u.UncomfortableChill.class));
        cards.add(new SetCardInfo("Undercity's Embrace", "RNA-89", Rarity.COMMON, mage.cards.u.UndercitysEmbrace.class));
        cards.add(new SetCardInfo("Underworld Coinsmith", "C15-237", Rarity.UNCOMMON, mage.cards.u.UnderworldCoinsmith.class));
        cards.add(new SetCardInfo("Undying Rage", "EMA-152", Rarity.COMMON, mage.cards.u.UndyingRage.class));
        cards.add(new SetCardInfo("Unflinching Courage", "C18-192", Rarity.UNCOMMON, mage.cards.u.UnflinchingCourage.class));
        cards.add(new SetCardInfo("Universal Automaton", "MH1-235", Rarity.COMMON, mage.cards.u.UniversalAutomaton.class));
        cards.add(new SetCardInfo("Universal Solvent", "AER-178", Rarity.COMMON, mage.cards.u.UniversalSolvent.class));
        cards.add(new SetCardInfo("Unlicensed Disintegration", "KLD-187", Rarity.UNCOMMON, mage.cards.u.UnlicensedDisintegration.class));
        cards.add(new SetCardInfo("Untamed Hunger", "OGW-91", Rarity.COMMON, mage.cards.u.UntamedHunger.class));
        cards.add(new SetCardInfo("Unwavering Initiate", "AKH-36", Rarity.COMMON, mage.cards.u.UnwaveringInitiate.class));
        cards.add(new SetCardInfo("Unyielding Krumar", "KTK-94", Rarity.COMMON, mage.cards.u.UnyieldingKrumar.class));
        cards.add(new SetCardInfo("Urban Evolution", "MM3-198", Rarity.UNCOMMON, mage.cards.u.UrbanEvolution.class));
        cards.add(new SetCardInfo("Urborg Uprising", "EMA-111", Rarity.COMMON, mage.cards.u.UrborgUprising.class));
        cards.add(new SetCardInfo("Urza's Rage", "C15-169", Rarity.RARE, mage.cards.u.UrzasRage.class));
        cards.add(new SetCardInfo("Valakut Invoker", "BFZ-159", Rarity.COMMON, mage.cards.v.ValakutInvoker.class));
        cards.add(new SetCardInfo("Valakut Predator", "BFZ-160", Rarity.COMMON, mage.cards.v.ValakutPredator.class));
        cards.add(new SetCardInfo("Valley Dasher", "KTK-125", Rarity.COMMON, mage.cards.v.ValleyDasher.class));
        cards.add(new SetCardInfo("Vampire Champion", "RIX-198", Rarity.COMMON, mage.cards.v.VampireChampion.class));
        cards.add(new SetCardInfo("Vampire Envoy", "OGW-92", Rarity.COMMON, mage.cards.v.VampireEnvoy.class));
        cards.add(new SetCardInfo("Vampire Hexmage", "C14-168", Rarity.UNCOMMON, mage.cards.v.VampireHexmage.class));
        cards.add(new SetCardInfo("Vampire Lacerator", "A25-114", Rarity.COMMON, mage.cards.v.VampireLacerator.class));
        cards.add(new SetCardInfo("Vampire Nighthawk", "ZEN-116", Rarity.UNCOMMON, mage.cards.v.VampireNighthawk.class));
        cards.add(new SetCardInfo("Vandalize", "DTK-165", Rarity.COMMON, mage.cards.v.Vandalize.class));
        cards.add(new SetCardInfo("Vapor Snag", "MM2-66", Rarity.COMMON, mage.cards.v.VaporSnag.class));
        cards.add(new SetCardInfo("Vastwood Gorger", "ORI-204", Rarity.COMMON, mage.cards.v.VastwoodGorger.class));
        cards.add(new SetCardInfo("Vengeful Rebirth", "MM2-188", Rarity.UNCOMMON, mage.cards.v.VengefulRebirth.class));
        cards.add(new SetCardInfo("Venom Sliver", "M15-205", Rarity.UNCOMMON, mage.cards.v.VenomSliver.class));
        cards.add(new SetCardInfo("Vent Sentinel", "IMA-153", Rarity.COMMON, mage.cards.v.VentSentinel.class));
        cards.add(new SetCardInfo("Vessel of Malignity", "SOI-144", Rarity.COMMON, mage.cards.v.VesselOfMalignity.class));
        cards.add(new SetCardInfo("Vessel of Volatility", "SOI-189", Rarity.COMMON, mage.cards.v.VesselOfVolatility.class));
        cards.add(new SetCardInfo("Veteran Swordsmith", "DDO-28", Rarity.COMMON, mage.cards.v.VeteranSwordsmith.class));
        cards.add(new SetCardInfo("Viashino Sandstalker", "VIS-100", Rarity.UNCOMMON, mage.cards.v.ViashinoSandstalker.class));
        cards.add(new SetCardInfo("Vigean Graftmage", "MM2-68", Rarity.COMMON, mage.cards.v.VigeanGraftmage.class));
        cards.add(new SetCardInfo("Vigor", "WWK-121", Rarity.RARE, mage.cards.v.Vigor.class));
        cards.add(new SetCardInfo("Village Bell-Ringer", "DDQ-22", Rarity.COMMON, mage.cards.v.VillageBellRinger.class));
        cards.add(new SetCardInfo("Violent Ultimatum", "ALA-206", Rarity.RARE, mage.cards.v.ViolentUltimatum.class));
        cards.add(new SetCardInfo("Virulent Swipe", "IMA-113", Rarity.COMMON, mage.cards.v.VirulentSwipe.class));
        cards.add(new SetCardInfo("Voice of the Provinces", "DDQ-23", Rarity.COMMON, mage.cards.v.VoiceOfTheProvinces.class));
        cards.add(new SetCardInfo("Volcanic Dragon", "M19-167", Rarity.UNCOMMON, mage.cards.v.VolcanicDragon.class));
        cards.add(new SetCardInfo("Volcanic Rush", "DTK-166", Rarity.COMMON, mage.cards.v.VolcanicRush.class));
        cards.add(new SetCardInfo("Voldaren Duelist", "SOI-191", Rarity.COMMON, mage.cards.v.VoldarenDuelist.class));
        cards.add(new SetCardInfo("Volunteer Reserves", "WTH-29", Rarity.UNCOMMON, mage.cards.v.VolunteerReserves.class));
        cards.add(new SetCardInfo("Voracious Null", "BFZ-125", Rarity.COMMON, mage.cards.v.VoraciousNull.class));
        cards.add(new SetCardInfo("Vraska's Finisher", "WAR-112", Rarity.COMMON, mage.cards.v.VraskasFinisher.class));
        cards.add(new SetCardInfo("Wake of Vultures", "EMA-115", Rarity.COMMON, mage.cards.w.WakeOfVultures.class));
        cards.add(new SetCardInfo("Wake the Reflections", "MM3-28", Rarity.COMMON, mage.cards.w.WakeTheReflections.class));
        cards.add(new SetCardInfo("Walk the Plank", "XLN-130", Rarity.UNCOMMON, mage.cards.w.WalkThePlank.class));
        cards.add(new SetCardInfo("Walking Corpse", "M19-126", Rarity.COMMON, mage.cards.w.WalkingCorpse.class));
        cards.add(new SetCardInfo("Wall of Fire", "M15-167", Rarity.COMMON, mage.cards.w.WallOfFire.class));
        cards.add(new SetCardInfo("Wall of Frost", "MM3-56", Rarity.UNCOMMON, mage.cards.w.WallOfFrost.class));
        cards.add(new SetCardInfo("Wall of Omens", "KHC-35", Rarity.UNCOMMON, mage.cards.w.WallOfOmens.class));
        cards.add(new SetCardInfo("Wall of One Thousand Cuts", "MH1-36", Rarity.COMMON, mage.cards.w.WallOfOneThousandCuts.class));
        cards.add(new SetCardInfo("Wander in Death", "AKH-115", Rarity.COMMON, mage.cards.w.WanderInDeath.class));
        cards.add(new SetCardInfo("Wandering Champion", "UMA-42", Rarity.COMMON, mage.cards.w.WanderingChampion.class));
        cards.add(new SetCardInfo("War Behemoth", "KTK-29", Rarity.COMMON, mage.cards.w.WarBehemoth.class));
        cards.add(new SetCardInfo("Warden of Evos Isle", "EMA-76", Rarity.COMMON, mage.cards.w.WardenOfEvosIsle.class));
        cards.add(new SetCardInfo("Warden of the Eye", "KTK-212", Rarity.UNCOMMON, mage.cards.w.WardenOfTheEye.class));
        cards.add(new SetCardInfo("Wargate", "ARB-129", Rarity.RARE, mage.cards.w.Wargate.class));
        cards.add(new SetCardInfo("Warteye Witch", "MH1-115", Rarity.COMMON, mage.cards.w.WarteyeWitch.class));
        cards.add(new SetCardInfo("Watcher in the Web", "SOI-239", Rarity.COMMON, mage.cards.w.WatcherInTheWeb.class));
        cards.add(new SetCardInfo("Watercourser", "BBD-137", Rarity.COMMON, mage.cards.w.Watercourser.class));
        cards.add(new SetCardInfo("Wave-Wing Elemental", "BFZ-88", Rarity.COMMON, mage.cards.w.WaveWingElemental.class));
        cards.add(new SetCardInfo("Wayfaring Temple", "MM3-202", Rarity.UNCOMMON, mage.cards.w.WayfaringTemple.class));
        cards.add(new SetCardInfo("Wayward Giant", "KLD-139", Rarity.COMMON, mage.cards.w.WaywardGiant.class));
        cards.add(new SetCardInfo("Weapons Trainer", "OGW-160", Rarity.UNCOMMON, mage.cards.w.WeaponsTrainer.class));
        cards.add(new SetCardInfo("Weathered Wayfarer", "ONS-59", Rarity.RARE, mage.cards.w.WeatheredWayfarer.class));
        cards.add(new SetCardInfo("Wee Dragonauts", "GRN-214", Rarity.UNCOMMON, mage.cards.w.WeeDragonauts.class));
        cards.add(new SetCardInfo("Weight of the Underworld", "ORI-126", Rarity.COMMON, mage.cards.w.WeightOfTheUnderworld.class));
        cards.add(new SetCardInfo("Weirded Vampire", "EMN-113", Rarity.COMMON, mage.cards.w.WeirdedVampire.class));
        cards.add(new SetCardInfo("Weldfast Wingsmith", "KLD-69", Rarity.COMMON, mage.cards.w.WeldfastWingsmith.class));
        cards.add(new SetCardInfo("Welkin Tern", "GS1-5", Rarity.COMMON, mage.cards.w.WelkinTern.class));
        cards.add(new SetCardInfo("Wellwisher", "CMA-166", Rarity.COMMON, mage.cards.w.Wellwisher.class));
        cards.add(new SetCardInfo("Wheel of Fate", "C16-138", Rarity.RARE, mage.cards.w.WheelOfFate.class));
        cards.add(new SetCardInfo("Whelming Wave", "DDO-44", Rarity.RARE, mage.cards.w.WhelmingWave.class));
        cards.add(new SetCardInfo("Whiplash Trap", "DDN-70", Rarity.COMMON, mage.cards.w.WhiplashTrap.class));
        cards.add(new SetCardInfo("Whir of Invention", "AER-49", Rarity.RARE, mage.cards.w.WhirOfInvention.class));
        cards.add(new SetCardInfo("Whispersilk Cloak", "M11-221", Rarity.UNCOMMON, mage.cards.w.WhispersilkCloak.class));
        cards.add(new SetCardInfo("Wight of Precinct Six", "C16-118", Rarity.UNCOMMON, mage.cards.w.WightOfPrecinctSix.class));
        cards.add(new SetCardInfo("Wild Griffin", "CN2-99", Rarity.COMMON, mage.cards.w.WildGriffin.class));
        cards.add(new SetCardInfo("Wild Growth", "C18-165", Rarity.COMMON, mage.cards.w.WildGrowth.class));
        cards.add(new SetCardInfo("Wild Mongrel", "DDD-5", Rarity.COMMON, mage.cards.w.WildMongrel.class));
        cards.add(new SetCardInfo("Wild Nacatl", "ALA-152", Rarity.COMMON, mage.cards.w.WildNacatl.class));
        cards.add(new SetCardInfo("Wildfire Emissary", "EMA-153", Rarity.COMMON, mage.cards.w.WildfireEmissary.class));
        cards.add(new SetCardInfo("Wildsize", "IMA-191", Rarity.COMMON, mage.cards.w.Wildsize.class));
        cards.add(new SetCardInfo("Will-o'-the-Wisp", "A25-115", Rarity.UNCOMMON, mage.cards.w.WillOTheWisp.class));
        cards.add(new SetCardInfo("Wind Drake", "KLD-70", Rarity.COMMON, mage.cards.w.WindDrake.class));
        cards.add(new SetCardInfo("Wind Strider", "XLN-88", Rarity.COMMON, mage.cards.w.WindStrider.class));
        cards.add(new SetCardInfo("Wind-Kin Raiders", "AER-50", Rarity.UNCOMMON, mage.cards.w.WindKinRaiders.class));
        cards.add(new SetCardInfo("Windborne Charge", "CN2-100", Rarity.UNCOMMON, mage.cards.w.WindborneCharge.class));
        cards.add(new SetCardInfo("Windcaller Aven", "MH1-77", Rarity.COMMON, mage.cards.w.WindcallerAven.class));
        cards.add(new SetCardInfo("Windgrace Acolyte", "DOM-112", Rarity.COMMON, mage.cards.w.WindgraceAcolyte.class));
        cards.add(new SetCardInfo("Winding Constrictor", "AER-140", Rarity.UNCOMMON, mage.cards.w.WindingConstrictor.class));
        cards.add(new SetCardInfo("Windrider Eel", "E01-30", Rarity.COMMON, mage.cards.w.WindriderEel.class));
        cards.add(new SetCardInfo("Wing Shards", "IMA-38", Rarity.UNCOMMON, mage.cards.w.WingShards.class));
        cards.add(new SetCardInfo("Winged Shepherd", "AKH-39", Rarity.COMMON, mage.cards.w.WingedShepherd.class));
        cards.add(new SetCardInfo("Wirewood Lodge", "DD1-26", Rarity.UNCOMMON, mage.cards.w.WirewoodLodge.class));
        cards.add(new SetCardInfo("Wishcoin Crab", "GRN-60", Rarity.COMMON, mage.cards.w.WishcoinCrab.class));
        cards.add(new SetCardInfo("Wishful Merfolk", "ELD-73", Rarity.COMMON, mage.cards.w.WishfulMerfolk.class));
        cards.add(new SetCardInfo("Wojek Bodyguard", "GRN-120", Rarity.COMMON, mage.cards.w.WojekBodyguard.class));
        cards.add(new SetCardInfo("Wolfkin Bond", "EMN-178", Rarity.COMMON, mage.cards.w.WolfkinBond.class));
        cards.add(new SetCardInfo("Woodborn Behemoth", "E01-79", Rarity.UNCOMMON, mage.cards.w.WoodbornBehemoth.class));
        cards.add(new SetCardInfo("Woolly Loxodon", "KTK-158", Rarity.COMMON, mage.cards.w.WoollyLoxodon.class));
        cards.add(new SetCardInfo("Woolly Thoctar", "MM3-203", Rarity.UNCOMMON, mage.cards.w.WoollyThoctar.class));
        cards.add(new SetCardInfo("Wren's Run Vanquisher", "DD1-19", Rarity.UNCOMMON, mage.cards.w.WrensRunVanquisher.class));
        cards.add(new SetCardInfo("Wrench Mind", "IMA-115", Rarity.COMMON, mage.cards.w.WrenchMind.class));
        cards.add(new SetCardInfo("Wretched Gryff", "EMN-12", Rarity.COMMON, mage.cards.w.WretchedGryff.class));
        cards.add(new SetCardInfo("Write into Being", "FRF-59", Rarity.COMMON, mage.cards.w.WriteIntoBeing.class));
        cards.add(new SetCardInfo("Yargle, Glutton of Urborg", "DOM-113", Rarity.UNCOMMON, mage.cards.y.YargleGluttonOfUrborg.class));
        cards.add(new SetCardInfo("Yavimaya Elder", "C18-166", Rarity.COMMON, mage.cards.y.YavimayaElder.class));
        cards.add(new SetCardInfo("Yavimaya Sapherd", "DOM-189", Rarity.COMMON, mage.cards.y.YavimayaSapherd.class));
        cards.add(new SetCardInfo("Yavimaya's Embrace", "APC-127", Rarity.RARE, mage.cards.y.YavimayasEmbrace.class));
        cards.add(new SetCardInfo("Yeva's Forcemage", "ORI-208", Rarity.COMMON, mage.cards.y.YevasForcemage.class));
        cards.add(new SetCardInfo("Young Pyromancer", "M14-163", Rarity.UNCOMMON, mage.cards.y.YoungPyromancer.class));
        cards.add(new SetCardInfo("Youthful Knight", "MM3-29", Rarity.COMMON, mage.cards.y.YouthfulKnight.class));
        cards.add(new SetCardInfo("Youthful Scholar", "DTK-84", Rarity.UNCOMMON, mage.cards.y.YouthfulScholar.class));
        cards.add(new SetCardInfo("Yuriko, the Tiger's Shadow", "C18-52", Rarity.RARE, mage.cards.y.YurikoTheTigersShadow.class));
        cards.add(new SetCardInfo("Zada's Commando", "OGW-120", Rarity.COMMON, mage.cards.z.ZadasCommando.class));
        cards.add(new SetCardInfo("Zealot of the God-Pharaoh", "HOU-207", Rarity.COMMON, mage.cards.z.ZealotOfTheGodPharaoh.class));
        cards.add(new SetCardInfo("Zealous Persecution", "E02-41", Rarity.UNCOMMON, mage.cards.z.ZealousPersecution.class));
        cards.add(new SetCardInfo("Zealous Strike", "CN2-101", Rarity.COMMON, mage.cards.z.ZealousStrike.class));
        cards.add(new SetCardInfo("Zendikar's Roil", "ORI-209", Rarity.UNCOMMON, mage.cards.z.ZendikarsRoil.class));
        cards.add(new SetCardInfo("Zhur-Taa Druid", "C16-232", Rarity.COMMON, mage.cards.z.ZhurTaaDruid.class));
        cards.add(new SetCardInfo("Zulaport Chainmage", "OGW-93", Rarity.COMMON, mage.cards.z.ZulaportChainmage.class));
    }

    private synchronized void populateBoosterSlotMap() {
        for (int i = 1; i < 16; ++i)
            this.possibleCardsPerBoosterSlot.put(i, new ArrayList<>());
        this.populateSlot(1, asList(  // White A
                "Abzan Falconer",
                "Abzan Runemark",
                "Acrobatic Maneuver",
                "Affa Protector",
                "Ainok Bond-Kin",
                "Alley Evasion",
                "Angelic Purge",
                "Angelsong",
                "Apostle's Blessing",
                "Arrester's Zeal",
                "Artful Maneuver",
                "Aura of Silence",
                "Bartered Cow",
                "Bonds of Faith",
                "Borrowed Grace",
                "Bulwark Giant",
                "Caravan Escort",
                "Caught in the Brights",
                "Celestial Crusader",
                "Celestial Flare",
                "Center Soul",
                "Cliffside Lookout",
                "Conviction",
                "Countless Gears Renegade",
                "Court Street Denizen",
                "Crib Swap",
                "Danitha Capashen, Paragon",
                "Daring Skyjek",
                "Decommission",
                "Defiant Strike",
                "Desperate Sentry",
                "Devilthorn Fox",
                "Disposal Mummy",
                "Divine Favor",
                "Dragon's Eye Sentry",
                "Dragon's Presence",
                "Eddytrail Hawk",
                "Enduring Victory",
                "Enlightened Ascetic",
                "Ephemeral Shields",
                "Ephemerate",
                "Excoriate",
                "Expose Evil",
                "Eyes in the Skies",
                "Faith's Fetters",
                "Feat of Resistance",
                "Felidar Umbra",
                "Firehoof Cavalry",
                "Ghostblade Eidolon",
                "Gift of Estates",
                "Glaring Aegis",
                "Glint-Sleeve Artisan",
                "God-Pharaoh's Faithful",
                "Grasp of the Hieromancer",
                "Gust Walker",
                "Gustcloak Skirmisher",
                "Healing Hands",
                "Hyena Umbra",
                "Infantry Veteran",
                "Inquisitor's Ox",
                "Isolation Zone",
                "Knight of Old Benalia",
                "Knight of Sorrows",
                "Kor Skyfisher",
                "Leonin Relic-Warder",
                "Lightform",
                "Lone Missionary",
                "Lonesome Unicorn",
                "Lotus-Eye Mystics",
                "Loxodon Partisan",
                "Mardu Hordechief",
                "Marked by Honor",
                "Meditation Puzzle",
                "Mortal's Ardor",
                "Mother of Runes",
                "Ninth Bridge Patrol",
                "Ondu Greathorn",
                "Ondu War Cleric",
                "Oreskos Swiftclaw",
                "Oust",
                "Palace Jailer",
                "Path to Exile",
                "Peace of Mind",
                "Prowling Caracal",
                "Resurrection",
                "Rhet-Crop Spearmaster",
                "Righteous Cause",
                "Savannah Lions",
                "Searing Light",
                "Serra's Embrace",
                "Sheer Drop",
                "Shining Aerosaur",
                "Shining Armor",
                "Siegecraft",
                "Skymarcher Aspirant",
                "Skyspear Cavalry",
                "Snubhorn Sentry",
                "Soul Parry",
                "Soul Summons",
                "Soul-Strike Technique",
                "Soulmender",
                "Sparring Mummy",
                "Spectral Gateguards",
                "Stave Off",
                "Steadfast Sentinel",
                "Stone Haven Medic",
                "Suppression Bonds",
                "Survive the Night",
                "Territorial Hammerskull",
                "Thraben Inspector",
                "Thraben Standard Bearer",
                "Topan Freeblade",
                "Veteran Swordsmith",
                "Village Bell-Ringer",
                "Voice of the Provinces",
                "Wall of One Thousand Cuts",
                "Wandering Champion",
                "War Behemoth",
                "Windborne Charge",
                "Wing Shards",
                "Winged Shepherd"
        ));
        this.populateSlot(2, asList(  // White B
                "Adanto Vanguard",
                "Ajani's Pridemate",
                "Angel of Mercy",
                "Angel of Renewal",
                "Angelic Gift",
                "Arrest",
                "Aven Battle Priest",
                "Aven Sentry",
                "Ballynock Cohort",
                "Battle Mastery",
                "Benevolent Ancestor",
                "Blade Instructor",
                "Blessed Spirits",
                "Built to Last",
                "Candlelight Vigil",
                "Cartouche of Solidarity",
                "Cast Out",
                "Cathar's Companion",
                "Champion of Arashin",
                "Charge",
                "Cloudshift",
                "Coalition Honor Guard",
                "Collar the Culprit",
                "Congregate",
                "Court Homunculus",
                "Darksteel Mutation",
                "Dauntless Cathar",
                "Dawnglare Invoker",
                "Disenchant",
                "Dismantling Blow",
                "Djeru's Renunciation",
                "Djeru's Resolve",
                "Doomed Traveler",
                "Dragon Bell Monk",
                "Emerge Unscathed",
                "Encampment Keeper",
                "Encircling Fissure",
                "Excavation Elephant",
                "Expedition Raptor",
                "Exultant Skymarcher",
                "Faithbearer Paladin",
                "Felidar Guardian",
                "Fencing Ace",
                "Fiend Hunter",
                "Forsake the Worldly",
                "Fortify",
                "Fragmentize",
                "Geist of the Moors",
                "Gideon's Lawkeeper",
                "Gleam of Resistance",
                "Gods Willing",
                "Great-Horn Krushok",
                "Guided Strike",
                "Healer's Hawk",
                "Healing Grace",
                "Heavy Infantry",
                "Humble",
                "Inspired Charge",
                "Intrusive Packbeast",
                "Iona's Judgment",
                "Jubilant Mascot",
                "Knight of Cliffhaven",
                "Knight of the Skyward Eye",
                "Knight of the Tusk",
                "Kor Bladewhirl",
                "Kor Firewalker",
                "Kor Hookmaster",
                "Kor Sky Climber",
                "Lieutenants of the Guard",
                "Lightwalker",
                "Lingering Souls",
                "Looming Altisaur",
                "Loyal Sentry",
                "Lunarch Mantle",
                "Midnight Guard",
                "Momentary Blink",
                "Moonlit Strider",
                "Nyx-Fleece Ram",
                "Pacifism",
                "Palace Sentinels",
                "Paladin of the Bloodstained",
                "Path of Peace",
                "Pegasus Courser",
                "Pentarch Ward",
                "Pitfall Trap",
                "Pressure Point",
                "Promise of Bunrei",
                "Rally the Peasants",
                "Raptor Companion",
                "Refurbish",
                "Renewed Faith",
                "Retreat to Emeria",
                "Reviving Dose",
                "Rootborn Defenses",
                "Sacred Cat",
                "Sanctum Gargoyle",
                "Sandstorm Charger",
                "Seal of Cleansing",
                "Seeker of the Way",
                "Sensor Splicer",
                "Seraph of the Suns",
                "Serra Disciple",
                "Shoulder to Shoulder",
                "Silverchase Fox",
                "Skyhunter Skirmisher",
                "Slash of Talons",
                "Soul Warden",
                "Stalwart Aven",
                "Star-Crowned Stag",
                "Sunlance",
                "Sunrise Seeker",
                "Swords to Plowshares",
                "Take Vengeance",
                "Tandem Tactics",
                "Terashi's Grasp",
                "Unwavering Initiate",
                "Wake the Reflections",
                "Wall of Omens",
                "Wild Griffin",
                "Youthful Knight",
                "Zealous Strike"
        ));
        this.populateSlot(3, asList(  // Blue A
                "Amass the Components",
                "Anticipate",
                "Artificer's Assistant",
                "Augury Owl",
                "Befuddle",
                "Benthic Giant",
                "Calculated Dismissal",
                "Call to Heel",
                "Caller of Gales",
                "Cancel",
                "Capture Sphere",
                "Catalog",
                "Chart a Course",
                "Chillbringer",
                "Chronostutter",
                "Circular Logic",
                "Clear the Mind",
                "Cloak of Mists",
                "Cloudkin Seer",
                "Clutch of Currents",
                "Compelling Argument",
                "Condescend",
                "Containment Membrane",
                "Contingency Plan",
                "Contradict",
                "Crashing Tide",
                "Crush Dissent",
                "Curio Vendor",
                "Daze",
                "Decision Paralysis",
                "Deep Freeze",
                "Dispel",
                "Displace",
                "Drag Under",
                "Dragon's Eye Savants",
                "Dreadwaters",
                "Embodiment of Spring",
                "Ensoul Artifact",
                "Everdream",
                "Failed Inspection",
                "Flashfreeze",
                "Fledgling Mawcor",
                "Fleeting Distraction",
                "Fogwalker",
                "Foil",
                "Frantic Search",
                "Frilled Sea Serpent",
                "Gaseous Form",
                "Glint",
                "Gone Missing",
                "Grasp of Phantoms",
                "Guard Gomazoa",
                "Gurmag Drowner",
                "Gush",
                "Hightide Hermit",
                "Hinterland Drake",
                "Humongulus",
                "Inkfathom Divers",
                "Invisibility",
                "Jeering Homunculus",
                "Jeskai Sage",
                "Kiora's Dambreaker",
                "Laboratory Brute",
                "Laboratory Maniac",
                "Labyrinth Guardian",
                "Messenger Jays",
                "Mind Sculpt",
                "Mist Raven",
                "Mnemonic Wall",
                "Monastery Loremaster",
                "Murder of Crows",
                "Nagging Thoughts",
                "Niblis of Dusk",
                "Nine-Tail White Fox",
                "Ojutai's Breath",
                "Phyrexian Ingester",
                "Pondering Mage",
                "Predict",
                "Purple-Crystal Crab",
                "Refocus",
                "Riftwing Cloudskate",
                "River Darter",
                "Sailor of Means",
                "Scroll Thief",
                "Send to Sleep",
                "Shipwreck Looter",
                "Silent Observer",
                "Silvergill Adept",
                "Singing Bell Strike",
                "Skaab Goliath",
                "Skitter Eel",
                "Sleep",
                "Slipstream Eel",
                "Slither Blade",
                "Sphinx's Tutelage",
                "Stream of Thought",
                "Surrakar Banisher",
                "Syr Elenora, the Discerning",
                "Thought Collapse",
                "Thunder Drake",
                "Tidal Warrior",
                "Trail of Evidence",
                "Treasure Cruise",
                "Treasure Mage",
                "Trinket Mage",
                "Turn Aside",
                "Uncomfortable Chill",
                "Wall of Frost",
                "Warden of Evos Isle",
                "Watercourser",
                "Weldfast Wingsmith",
                "Welkin Tern",
                "Wind Drake",
                "Wind Strider",
                "Wind-Kin Raiders",
                "Windcaller Aven",
                "Wishcoin Crab",
                "Wishful Merfolk",
                "Wretched Gryff",
                "Write into Being",
                "Youthful Scholar"
        ));
        this.populateSlot(4, asList(  // Blue B
                "Academy Journeymage",
                "Aether Tradewinds",
                "Aethersnipe",
                "Amphin Pathmage",
                "Arcane Denial",
                "Archaeomancer",
                "Archetype of Imagination",
                "Augur of Bolas",
                "Bastion Inventor",
                "Bewilder",
                "Blue Elemental Blast",
                "Borrowing 100,000 Arrows",
                "Brainstorm",
                "Brilliant Spectrum",
                "Brine Elemental",
                "Cartouche of Knowledge",
                "Castaway's Despair",
                "Choking Tethers",
                "Citywatch Sphinx",
                "Claustrophobia",
                "Cloud Elemental",
                "Cloudreader Sphinx",
                "Concentrate",
                "Convolute",
                "Coral Trickster",
                "Coralhelm Guide",
                "Counterspell",
                "Court Hussar",
                "Curiosity",
                "Dazzling Lights",
                "Deep Analysis",
                "Diminish",
                "Dirgur Nemesis",
                "Distortion Strike",
                "Divination",
                "Doorkeeper",
                "Dream Cache",
                "Dream Twist",
                "Eel Umbra",
                "Enlightened Maniac",
                "Errant Ephemeron",
                "Essence Scatter",
                "Exclude",
                "Fact or Fiction",
                "Faerie Invaders",
                "Faerie Mechanist",
                "Fascination",
                "Fathom Seer",
                "Fog Bank",
                "Forbidden Alchemy",
                "Frost Lynx",
                "Ghost Ship",
                "Glacial Crasher",
                "Hieroglyphic Illumination",
                "Horseshoe Crab",
                "Impulse",
                "Ior Ruin Expedition",
                "Jace's Phantasm",
                "Jwar Isle Avenger",
                "Lay Claim",
                "Leapfrog",
                "Mahamoti Djinn",
                "Man-o'-War",
                "Mana Leak",
                "Maximize Altitude",
                "Memory Lapse",
                "Merfolk Looter",
                "Metallic Rebuke",
                "Mulldrifter",
                "Mystic of the Hidden Way",
                "Mystical Teachings",
                "Negate",
                "Ninja of the Deep Hours",
                "Ojutai Interceptor",
                "Omenspeaker",
                "Opportunity",
                "Opt",
                "Peel from Reality",
                "Phantasmal Bear",
                "Portent",
                "Preordain",
                "Prodigal Sorcerer",
                "Propaganda",
                "Prosperous Pirates",
                "Repulse",
                "Retraction Helix",
                "Ringwarden Owl",
                "River Serpent",
                "Riverwheel Aerialists",
                "Sage of Lat-Nam",
                "Sea Gate Oracle",
                "Sealock Monster",
                "Secrets of the Golden City",
                "Shaper Parasite",
                "Shimmerscale Drake",
                "Sigiled Starfish",
                "Skittering Crustacean",
                "Snap",
                "Snapping Drake",
                "Somber Hoverguard",
                "Spire Monitor",
                "Steady Progress",
                "Stitched Drake",
                "Storm Sculptor",
                "Strategic Planning",
                "Syncopate",
                "Tandem Lookout",
                "Temporal Fissure",
                "Thornwind Faeries",
                "Thought Scour",
                "Thoughtcast",
                "Thrummingbird",
                "Tidal Wave",
                "Totally Lost",
                "Treasure Hunt",
                "Triton Tactics",
                "Vapor Snag",
                "Vigean Graftmage",
                "Wave-Wing Elemental",
                "Whiplash Trap",
                "Windrider Eel"
        ));
        this.populateSlot(5, asList(  // Black A
                "Aid the Fallen",
                "Alesha's Vanguard",
                "Alley Strangler",
                "Ambitious Aetherborn",
                "Ancestral Vengeance",
                "Annihilate",
                "Bala Ged Scorpion",
                "Bitter Revelation",
                "Bladebrand",
                "Blighted Bat",
                "Blistergrub",
                "Bone Splinters",
                "Boon of Emrakul",
                "Breeding Pit",
                "Butcher's Glee",
                "Cabal Therapy",
                "Cackling Imp",
                "Cadaver Imp",
                "Catacomb Slug",
                "Certain Death",
                "Coat with Venom",
                "Corpsehatch",
                "Covenant of Blood",
                "Crow of Dark Tidings",
                "Dark Dabbling",
                "Dark Withering",
                "Darkblast",
                "Dead Reveler",
                "Deadeye Tormentor",
                "Defeat",
                "Demon's Grasp",
                "Demonic Tutor",
                "Demonic Vigor",
                "Dismember",
                "Disowned Ancestor",
                "Doomed Dissenter",
                "Douse in Gloom",
                "Dread Return",
                "Dregscape Zombie",
                "Dukhara Scavenger",
                "Dune Beetle",
                "Duress",
                "Farbog Revenant",
                "Fetid Imp",
                "First-Sphere Gargantua",
                "Flesh to Dust",
                "Fretwork Colony",
                "Genju of the Fens",
                "Ghoulcaller's Accomplice",
                "Grasping Scoundrel",
                "Gravepurge",
                "Grim Discovery",
                "Hideous End",
                "Induce Despair",
                "Infernal Scarring",
                "Infest",
                "Instill Infection",
                "Kalastria Nightwatch",
                "Krumar Bond-Kin",
                "Lazotep Behemoth",
                "Macabre Waltz",
                "Marauding Boneslasher",
                "Mark of the Vampire",
                "Marsh Hulk",
                "Merciless Resolve",
                "Miasmic Mummy",
                "Mind Rake",
                "Mire's Malice",
                "Murder",
                "Murderous Compulsion",
                "Nantuko Husk",
                "Never Happened",
                "Nirkana Assassin",
                "Plaguecrafter",
                "Prowling Pangolin",
                "Rakshasa's Secret",
                "Read the Bones",
                "Reaper of Night",
                "Reassembling Skeleton",
                "Reckless Imp",
                "Reckless Spite",
                "Returned Centaur",
                "Revenant",
                "Rite of the Serpent",
                "Ruin Rat",
                "Scrounger of Souls",
                "Sengir Vampire",
                "Shambling Attendants",
                "Shambling Goblin",
                "Shriekmaw",
                "Silumgar Butcher",
                "Skeleton Archer",
                "Stab Wound",
                "Stallion of Ashmouth",
                "Stinkweed Imp",
                "Stromkirk Patrol",
                "Subtle Strike",
                "Sultai Runemark",
                "Tar Snare",
                "Thallid Omnivore",
                "The Eldest Reborn",
                "Thornbow Archer",
                "Thraben Foulbloods",
                "Torment of Venom",
                "Touch of Moonglove",
                "Twins of Maurer Estate",
                "Undercity's Embrace",
                "Untamed Hunger",
                "Unyielding Krumar",
                "Vampire Champion",
                "Vampire Envoy",
                "Vampire Nighthawk",
                "Vessel of Malignity",
                "Voracious Null",
                "Vraska's Finisher",
                "Walk the Plank",
                "Warteye Witch",
                "Weight of the Underworld",
                "Weirded Vampire",
                "Yargle, Glutton of Urborg",
                "Zulaport Chainmage"
        ));
        this.populateSlot(6, asList(  // Black B
                "Absorb Vis",
                "Accursed Spirit",
                "Altar's Reap",
                "Animate Dead",
                "Baleful Ammit",
                "Balustrade Spy",
                "Bartizan Bats",
                "Black Cat",
                "Blessing of Belzenlok",
                "Blightsoil Druid",
                "Blood Artist",
                "Bloodrite Invoker",
                "Caligo Skin-Witch",
                "Carrion Feeder",
                "Carrion Imp",
                "Catacomb Crocodile",
                "Caustic Tar",
                "Child of Night",
                "Costly Plunder",
                "Cower in Fear",
                "Crippling Blight",
                "Cursed Minotaur",
                "Daring Demolition",
                "Dark Ritual",
                "Deadbridge Shaman",
                "Death Denied",
                "Desperate Castaways",
                "Diabolic Edict",
                "Die Young",
                "Dinosaur Hunter",
                "Dirge of Dread",
                "Dread Drone",
                "Dreadbringer Lampads",
                "Driver of the Dead",
                "Drudge Sentinel",
                "Dusk Charger",
                "Dusk Legion Zealot",
                "Epicure of Blood",
                "Erg Raiders",
                "Eternal Thirst",
                "Evincar's Justice",
                "Executioner's Capsule",
                "Eyeblight's Ending",
                "Fallen Angel",
                "Fatal Push",
                "Fen Hauler",
                "Feral Abomination",
                "Festercreep",
                "Festering Newt",
                "Fill with Fright",
                "Fungal Infection",
                "Ghostly Changeling",
                "Gifted Aetherborn",
                "Go for the Throat",
                "Gravedigger",
                "Gray Merchant of Asphodel",
                "Grim Affliction",
                "Grixis Slavedriver",
                "Grotesque Mutation",
                "Gruesome Fate",
                "Gurmag Angler",
                "Hired Blade",
                "Hound of the Farbogs",
                "Innocent Blood",
                "Inquisition of Kozilek",
                "Lawless Broker",
                "Lethal Sting",
                "Lord of the Accursed",
                "March of the Drowned",
                "Mephitic Vapors",
                "Mind Rot",
                "Moment of Craving",
                "Nameless Inversion",
                "Night's Whisper",
                "Noxious Dragon",
                "Okiba-Gang Shinobi",
                "Painful Lesson",
                "Phyrexian Rager",
                "Phyrexian Reclamation",
                "Pit Keeper",
                "Plague Wight",
                "Plagued Rusalka",
                "Prakhata Club Security",
                "Queen's Agent",
                "Quest for the Gravelord",
                "Rabid Bloodsucker",
                "Rakdos Drake",
                "Ravenous Chupacabra",
                "Recover",
                "Renegade Demon",
                "Renegade's Getaway",
                "Rotfeaster Maggot",
                "Scarab Feast",
                "Scuttling Death",
                "Seal of Doom",
                "Shadowcloak Vampire",
                "Skeletal Scrying",
                "Skulking Ghost",
                "Smiting Helix",
                "Spreading Rot",
                "Street Wraith",
                "Tavern Swindler",
                "Tendrils of Corruption",
                "Thorn of the Black Rose",
                "Tidy Conclusion",
                "Tragic Slip",
                "Trespasser's Curse",
                "Trial of Ambition",
                "Typhoid Rats",
                "Unburden",
                "Urborg Uprising",
                "Vampire Hexmage",
                "Vampire Lacerator",
                "Virulent Swipe",
                "Wake of Vultures",
                "Walking Corpse",
                "Wander in Death",
                "Wight of Precinct Six",
                "Will-o'-the-Wisp",
                "Windgrace Acolyte",
                "Wrench Mind"
        ));
        this.populateSlot(7, asList(  // Red A
                "Act on Impulse",
                "Ainok Tracker",
                "Alchemist's Greeting",
                "Ancient Grudge",
                "Arc Trail",
                "Arrow Storm",
                "Azra Bladeseeker",
                "Balduvian Horde",
                "Barrage of Boulders",
                "Beetleback Chief",
                "Bellows Lizard",
                "Blastfire Bolt",
                "Blazing Volley",
                "Blindblast",
                "Blood Ogre",
                "Bloodfire Expert",
                "Bloodlust Inciter",
                "Bloodstone Goblin",
                "Blow Your House Down",
                "Bombard",
                "Bomber Corps",
                "Borrowed Hostility",
                "Brazen Buccaneers",
                "Brazen Wolves",
                "Bring Low",
                "Brute Strength",
                "Built to Smash",
                "Burst Lightning",
                "Canyon Lurkers",
                "Chandra's Pyrohelix",
                "Charging Monstrosaur",
                "Cobblebrute",
                "Crowd's Favor",
                "Crown-Hunter Hireling",
                "Curse of Opulence",
                "Destructive Tampering",
                "Direct Current",
                "Dragon Fodder",
                "Dynacharge",
                "Erratic Explosion",
                "Expedite",
                "Falkenrath Reaver",
                "Fireball",
                "Flame Jab",
                "Forge Devil",
                "Foundry Street Denizen",
                "Frontline Rebel",
                "Furnace Whelp",
                "Galvanic Blast",
                "Generator Servant",
                "Geomancer's Gambit",
                "Ghitu Lavarunner",
                "Giant Spectacle",
                "Goblin Assault",
                "Goblin Bombardment",
                "Goblin Fireslinger",
                "Goblin Matron",
                "Goblin Roughrider",
                "Goblin War Paint",
                "Gore Swine",
                "Gorehorn Minotaurs",
                "Granitic Titan",
                "Grapeshot",
                "Gravitic Punch",
                "Guttersnipe",
                "Hammerhand",
                "Hardened Berserker",
                "Hyena Pack",
                "Ill-Tempered Cyclops",
                "Impact Tremors",
                "Incorrigible Youths",
                "Inferno Fist",
                "Inferno Jet",
                "Ingot Chewer",
                "Keldon Halberdier",
                "Kiln Fiend",
                "Krenko's Enforcer",
                "Leaping Master",
                "Leopard-Spotted Jiao",
                "Madcap Skills",
                "Mardu Warshrieker",
                "Maximize Velocity",
                "Miner's Bane",
                "Mogg Flunkies",
                "Molten Rain",
                "Monastery Swiftspear",
                "Ondu Champion",
                "Outnumber",
                "Price of Progress",
                "Pyrotechnics",
                "Quakefoot Cyclops",
                "Reckless Fireweaver",
                "Reckless Wurm",
                "Rivals' Duel",
                "Ruinous Gremlin",
                "Samut's Sprint",
                "Sarkhan's Rage",
                "Screamreach Brawler",
                "Seismic Shift",
                "Shattering Spree",
                "Shenanigans",
                "Smelt",
                "Sparkmage Apprentice",
                "Sparkspitter",
                "Staggershock",
                "Stormblood Berserker",
                "Swift Kick",
                "Tectonic Rift",
                "Temur Battle Rage",
                "Thrill of Possibility",
                "Tibalt's Rager",
                "Torch Courier",
                "Valakut Invoker",
                "Valakut Predator",
                "Valley Dasher",
                "Vandalize",
                "Volcanic Dragon",
                "Volcanic Rush",
                "Wall of Fire",
                "Wayward Giant",
                "Wojek Bodyguard"
        ));
        this.populateSlot(8, asList(  // Red B
                "Act of Treason",
                "Ahn-Crop Crasher",
                "Akroan Sergeant",
                "Anger",
                "Atarka Efreet",
                "Avarax",
                "Barging Sergeant",
                "Battle Rampart",
                "Battle-Rattle Shaman",
                "Blades of Velis Vel",
                "Bloodmad Vampire",
                "Blur of Blades",
                "Boggart Brute",
                "Boiling Earth",
                "Boulder Salvo",
                "Browbeat",
                "Cartouche of Zeal",
                "Cathartic Reunion",
                "Chandra's Revolution",
                "Chartooth Cougar",
                "Cinder Hellion",
                "Cleansing Screech",
                "Cosmotronic Wave",
                "Crash Through",
                "Curse of the Nightly Hunt",
                "Death by Dragons",
                "Defiant Ogre",
                "Demolish",
                "Desert Cerodon",
                "Desperate Ravings",
                "Distemper of the Blood",
                "Dragon Breath",
                "Dragon Egg",
                "Dragon Whelp",
                "Dragonsoul Knight",
                "Dual Shot",
                "Earth Elemental",
                "Emrakul's Hatcher",
                "Enthralling Victor",
                "Faithless Looting",
                "Fall of the Hammer",
                "Fervent Strike",
                "Fierce Invocation",
                "Fiery Hellhound",
                "Fiery Temper",
                "Fire Elemental",
                "Firebolt",
                "Firebrand Archer",
                "Flametongue Kavu",
                "Flamewave Invoker",
                "Fling",
                "Frenzied Raptor",
                "Frilled Deathspitter",
                "Frontline Devastator",
                "Fury Charm",
                "Genju of the Spires",
                "Goblin Balloon Brigade",
                "Goblin Locksmith",
                "Goblin Motivator",
                "Goblin Oriflamme",
                "Goblin Warchief",
                "Gut Shot",
                "Hanweir Lancer",
                "Hijack",
                "Hulking Devil",
                "Insolent Neonate",
                "Jackal Pup",
                "Keldon Overseer",
                "Khenra Scrapper",
                "Kird Ape",
                "Kolaghan Stormsinger",
                "Krenko's Command",
                "Lightning Bolt",
                "Lightning Javelin",
                "Lightning Shrieker",
                "Lightning Talons",
                "Magma Spray",
                "Makindi Sliderunner",
                "Mark of Mutiny",
                "Mogg Fanatic",
                "Mogg War Marshal",
                "Mutiny",
                "Nimble-Blade Khenra",
                "Orcish Cannonade",
                "Orcish Oriflamme",
                "Pillage",
                "Prickleboar",
                "Prophetic Ravings",
                "Rampaging Cyclops",
                "Renegade Tactics",
                "Roast",
                "Rolling Thunder",
                "Rubblebelt Maaka",
                "Rummaging Goblin",
                "Run Amok",
                "Rush of Adrenaline",
                "Salivating Gremlins",
                "Seismic Stomp",
                "Shatter",
                "Shock",
                "Skirk Commando",
                "Skirk Prospector",
                "Smash to Smithereens",
                "Sparktongue Dragon",
                "Spikeshot Goblin",
                "Sulfurous Blast",
                "Summit Prowler",
                "Sun-Crowned Hunters",
                "Swashbuckling",
                "Sweatworks Brawler",
                "Tarfire",
                "Thresher Lizard",
                "Uncaged Fury",
                "Undying Rage",
                "Vent Sentinel",
                "Vessel of Volatility",
                "Voldaren Duelist",
                "Wildfire Emissary",
                "Young Pyromancer",
                "Zada's Commando",
                "Zealot of the God-Pharaoh"
        ));
        this.populateSlot(9, asList(  // Green A
                "Affectionate Indrik",
                "Ancestral Mask",
                "Ancient Brontodon",
                "Arbor Armament",
                "Beastbreaker of Bala Ged",
                "Become Immense",
                "Blanchwood Armor",
                "Blastoderm",
                "Borderland Explorer",
                "Briarhorn",
                "Broodhunter Wurm",
                "Byway Courier",
                "Centaur Courser",
                "Creeping Mold",
                "Destructor Dragon",
                "Domesticated Hydra",
                "Dragon-Scarred Bear",
                "Elemental Uprising",
                "Elvish Fury",
                "Eternal Witness",
                "Feral Prowler",
                "Fierce Empath",
                "Frontier Mastodon",
                "Gaea's Blessing",
                "Gaea's Protector",
                "Gift of Growth",
                "Glade Watcher",
                "Grapple with the Past",
                "Greater Basilisk",
                "Greater Sandwurm",
                "Hamlet Captain",
                "Hooded Brawler",
                "Hooting Mandrills",
                "Jungle Delver",
                "Jungle Wayfinder",
                "Kin-Tree Warden",
                "Kraul Foragers",
                "Krosan Druid",
                "Lead by Example",
                "Lead the Stampede",
                "Lifespring Druid",
                "Lignify",
                "Llanowar Elves",
                "Llanowar Empath",
                "Lure",
                "Mantle of Webs",
                "Map the Wastes",
                "Mulch",
                "Natural Connection",
                "Naturalize",
                "Nature's Lore",
                "Nest Invader",
                "Nettle Sentinel",
                "New Horizons",
                "Nimble Mongoose",
                "Ondu Giant",
                "Oran-Rief Invoker",
                "Overgrown Armasaur",
                "Pack's Favor",
                "Penumbra Spider",
                "Pierce the Sky",
                "Plummet",
                "Prey Upon",
                "Prey's Vengeance",
                "Pulse of Murasa",
                "Quiet Disrepair",
                "Rampant Growth",
                "Ranger's Guile",
                "Ravenous Leucrocota",
                "Reclaim",
                "Revive",
                "Rhox Maulers",
                "Riparian Tiger",
                "Roar of the Wurm",
                "Root Out",
                "Rosethorn Halberd",
                "Runeclaw Bear",
                "Sagu Archer",
                "Sakura-Tribe Elder",
                "Saproling Migration",
                "Savage Punch",
                "Seal of Strength",
                "Search for Tomorrow",
                "Seek the Horizon",
                "Seek the Wilds",
                "Shape the Sands",
                "Siege Wurm",
                "Silhana Ledgewalker",
                "Silkweaver Elite",
                "Snake Umbra",
                "Snapping Sailback",
                "Spider Spawning",
                "Stoic Builder",
                "Strength in Numbers",
                "Sylvan Bounty",
                "Tajuru Pathwarden",
                "Take Down",
                "Talons of Wildwood",
                "Territorial Baloth",
                "Thornhide Wolves",
                "Thornweald Archer",
                "Thrive",
                "Timberwatch Elf",
                "Time to Feed",
                "Titanic Growth",
                "Tukatongue Thallid",
                "Turntimber Basilisk",
                "Vastwood Gorger",
                "Watcher in the Web",
                "Wellwisher",
                "Wild Growth",
                "Wild Mongrel",
                "Wildsize",
                "Wolfkin Bond",
                "Woodborn Behemoth",
                "Woolly Loxodon",
                "Wren's Run Vanquisher",
                "Yavimaya Elder",
                "Yavimaya Sapherd",
                "Yeva's Forcemage",
                "Zendikar's Roil"
        ));
        this.populateSlot(10, asList(  // Green B
                "Abundant Growth",
                "Acidic Slime",
                "Adventurous Impulse",
                "Aerie Bowmasters",
                "Aggressive Instinct",
                "Aggressive Urge",
                "Ainok Survivalist",
                "Alpine Grizzly",
                "Ambassador Oak",
                "Ancient Stirrings",
                "Arachnus Web",
                "Arbor Elf",
                "Aura Gnarlid",
                "Avacyn's Pilgrim",
                "Backwoods Survivalists",
                "Baloth Gorger",
                "Basking Rootwalla",
                "Beast Within",
                "Beneath the Sands",
                "Bestial Menace",
                "Bitterblade Warrior",
                "Bitterbow Sharpshooters",
                "Blossom Dryad",
                "Borderland Ranger",
                "Bristling Boar",
                "Broken Bond",
                "Canopy Spider",
                "Carnivorous Moss-Beast",
                "Caustic Caterpillar",
                "Charging Rhino",
                "Citanul Woodreaders",
                "Clip Wings",
                "Colossal Dreadmaw",
                "Combo Attack",
                "Commune with Nature",
                "Commune with the Gods",
                "Conifer Strider",
                "Crop Rotation",
                "Crossroads Consecrator",
                "Crowned Ceratok",
                "Crushing Canopy",
                "Cultivate",
                "Daggerback Basilisk",
                "Dawn's Reflection",
                "Death-Hood Cobra",
                "Desert Twister",
                "Dissenter's Deliverance",
                "Dragonscale Boon",
                "Durkwood Baloth",
                "Earthen Arms",
                "Elephant Guide",
                "Elves of Deep Shadow",
                "Elvish Visionary",
                "Elvish Warrior",
                "Ember Weaver",
                "Epic Confrontation",
                "Essence Warden",
                "Experiment One",
                "Explore",
                "Explosive Vegetation",
                "Ezuri's Archers",
                "Fade into Antiquity",
                "Farseek",
                "Feed the Clan",
                "Feral Krushok",
                "Ferocious Zheng",
                "Fertile Ground",
                "Fog",
                "Formless Nurturing",
                "Giant Growth",
                "Giant Spider",
                "Gift of Paradise",
                "Gnarlid Pack",
                "Grazing Gladehart",
                "Greenwood Sentinel",
                "Groundswell",
                "Guardian Shield-Bearer",
                "Hardy Veteran",
                "Harmonize",
                "Harrow",
                "Hunt the Weak",
                "Hunter's Ambush",
                "Imperious Perfect",
                "Invigorate",
                "Ivy Lane Denizen",
                "Kavu Climber",
                "Kavu Primarch",
                "Khalni Heart Expedition",
                "Kozilek's Predator",
                "Kraul Warrior",
                "Krosan Tusker",
                "Larger Than Life",
                "Lay of the Land",
                "Longshot Squad",
                "Manglehorn",
                "Might of the Masses",
                "Nature's Claim",
                "Oakgnarl Warrior",
                "Overgrown Battlement",
                "Overrun",
                "Peema Outrider",
                "Pelakka Wurm",
                "Pinion Feast",
                "Pouncing Cheetah",
                "Priest of Titania",
                "Rain of Thorns",
                "Rancor",
                "Reclaiming Vines",
                "Regrowth",
                "Relic Crush",
                "Return to the Earth",
                "Roots",
                "Scatter the Seeds",
                "Stalking Tiger",
                "Sylvan Scrying",
                "Tajuru Warcaller",
                "Terrain Elemental",
                "The Crowd Goes Wild",
                "Thornscape Battlemage",
                "Thrashing Brontodon",
                "Venom Sliver"
        ));
        this.populateSlot(11, asList(  // Multicolored
                "Abzan Charm",
                "Abzan Guide",
                "Agony Warp",
                "Akroan Hoplite",
                "Armadillo Cloak",
                "Armament Corps",
                "Azorius Charm",
                "Azra Oddsmaker",
                "Baleful Strix",
                "Baloth Null",
                "Bear's Companion",
                "Belligerent Brontodon",
                "Bituminous Blast",
                "Bladewing the Risen",
                "Blightning",
                "Bloodbraid Elf",
                "Boros Challenger",
                "Bounding Krasis",
                "Call of the Nightwing",
                "Campaign of Vengeance",
                "Cauldron Dance",
                "Citadel Castellan",
                "Claim // Fame",
                "Coiling Oracle",
                "Contraband Kingpin",
                "Corpsejack Menace",
                "Crosis's Charm",
                "Cunning Breezedancer",
                "Deathreap Ritual",
                "Deny Reality",
                "Draconic Disciple",
                "Drana's Emissary",
                "Engineered Might",
                "Esper Charm",
                "Ethercaste Knight",
                "Ethereal Ambush",
                "Extract from Darkness",
                "Fire // Ice",
                "Fires of Yavimaya",
                "Flame-Kin Zealot",
                "Fusion Elemental",
                "Gelectrode",
                "Ghor-Clan Rampager",
                "Giantbaiting",
                "Gift of Orzhova",
                "Goblin Deathraiders",
                "Grim Contest",
                "Gwyllion Hedge-Mage",
                "Hammer Dropper",
                "Hidden Stockpile",
                "Highspire Mantis",
                "Hypothesizzle",
                "Iroas's Champion",
                "Join Shields",
                "Jungle Barrier",
                "Kathari Remnant",
                "Kin-Tree Invocation",
                "Kiora's Follower",
                "Kiss of the Amesha",
                "Lawmage's Binding",
                "Lightning Helix",
                "Mardu Roughrider",
                "Martial Glory",
                "Maverick Thopterist",
                "Mercurial Geists",
                "Migratory Route",
                "Mistmeadow Witch",
                "Mortify",
                "Naya Charm",
                "Nucklavee",
                "Obelisk Spider",
                "Ochran Assassin",
                "Pillory of the Sleepless",
                "Plaxcaster Frogling",
                "Pollenbright Wings",
                "Putrefy",
                "Qasali Pridemage",
                "Raff Capashen, Ship's Mage",
                "Raging Swordtooth",
                "Reclusive Artificer",
                "Reflector Mage",
                "Rhox War Monk",
                "Riptide Crab",
                "River Hoopoe",
                "Rosemane Centaur",
                "Rosheen Meanderer",
                "Satyr Enchanter",
                "Savage Twister",
                "Sedraxis Specter",
                "Selesnya Guildmage",
                "Shambling Remains",
                "Shardless Agent",
                "Shipwreck Singer",
                "Shrewd Hatchling",
                "Skyward Eye Prophets",
                "Slave of Bolas",
                "Soul Manipulation",
                "Sprouting Thrinax",
                "Stormchaser Chimera",
                "Sultai Charm",
                "Sultai Soothsayer",
                "Tatyova, Benthic Druid",
                "Terminate",
                "Thopter Foundry",
                "Thought Erasure",
                "Tithe Drinker",
                "Tower Gargoyle",
                "Treacherous Terrain",
                "Underworld Coinsmith",
                "Unflinching Courage",
                "Unlicensed Disintegration",
                "Urban Evolution",
                "Vengeful Rebirth",
                "Warden of the Eye",
                "Wayfaring Temple",
                "Weapons Trainer",
                "Wee Dragonauts",
                "Winding Constrictor",
                "Woolly Thoctar",
                "Zealous Persecution",
                "Zhur-Taa Druid"
        ));
        this.populateSlot(12, asList(  // Artifact/Land
                "Aether Hub",
                "Aether Spellbomb",
                "Akoum Refuge",
                "Alchemist's Vial",
                "Alloy Myr",
                "Arcane Sanctum",
                "Armillary Sphere",
                "Artisan of Kozilek",
                "Ash Barrens",
                "Ashnod's Altar",
                "Benthic Infiltrator",
                "Blasted Landscape",
                "Blighted Fen",
                "Blinding Souleater",
                "Blossoming Sands",
                "Bojuka Bog",
                "Bomat Bazaar Barge",
                "Bone Saw",
                "Bottle Gnomes",
                "Breaker of Armies",
                "Burnished Hart",
                "Call the Scions",
                "Cathodion",
                "Coldsteel Heart",
                "Consulate Dreadnought",
                "Copper Carapace",
                "Crumbling Necropolis",
                "Crystal Ball",
                "Crystal Chimes",
                "Darksteel Citadel",
                "Diamond Mare",
                "Dismal Backwater",
                "Dreadship Reef",
                "Eldrazi Devastator",
                "Emmessi Tome",
                "Etched Oracle",
                "Evolving Wilds",
                "Faerie Conclave",
                "Farmstead Gleaner",
                "Field of Ruin",
                "Filigree Familiar",
                "Flayer Husk",
                "Forgotten Cave",
                "Foundry Inspector",
                "Fountain of Renewal",
                "Frogmite",
                "Frontier Bivouac",
                "Gateway Plaza",
                "Ghost Quarter",
                "Goblin Burrows",
                "Graypelt Refuge",
                "Great Furnace",
                "Gruul Signet",
                "Guardians of Meletis",
                "Heavy Arbalest",
                "Herald's Horn",
                "Hexplate Golem",
                "Hot Soup",
                "Icy Manipulator",
                "Implement of Malice",
                "Irontread Crusher",
                "Juggernaut",
                "Jungle Hollow",
                "Jungle Shrine",
                "Kazandu Refuge",
                "Krosan Verge",
                "Lightning Greaves",
                "Loxodon Warhammer",
                "Mask of Memory",
                "Meteorite",
                "Millikin",
                "Millstone",
                "Mind Stone",
                "Mishra's Bauble",
                "Mishra's Factory",
                "Moonglove Extract",
                "Mortarpod",
                "Myr Retriever",
                "Myr Sire",
                "New Benalia",
                "Ornithopter",
                "Orzhov Basilica",
                "Palladium Myr",
                "Peace Strider",
                "Perilous Myr",
                "Pilgrim's Eye",
                "Prophetic Prism",
                "Reliquary Tower",
                "Renegade Map",
                "Rhonas's Monument",
                "Rogue's Passage",
                "Sandsteppe Citadel",
                "Sandstone Oracle",
                "Scoured Barrens",
                "Sejiri Refuge",
                "Serrated Arrows",
                "Short Sword",
                "Sigil of Valor",
                "Simic Locket",
                "Skarrg, the Rage Pits",
                "Skullclamp",
                "Skyscanner",
                "Sol Ring",
                "Sorcerer's Broom",
                //"Spy Kit",
                "Sunset Pyramid",
                "Suspicious Bookcase",
                "Swiftwater Cliffs",
                "Tectonic Edge",
                "Temple of the False God",
                "Thornwood Falls",
                "Thought Vessel",
                "Thran Dynamo",
                "Thran Golem",
                "Tormod's Crypt",
                "Trepanation Blade",
                "Unclaimed Territory",
                "Universal Automaton",
                "Universal Solvent",
                "Whispersilk Cloak",
                "Wirewood Lodge"
        ));
        this.populateSlot(13, asList(  // Pre-M15, sourced from magic.wizards.com
                "Avalanche Riders",
                "Belbe's Portal",
                "Black Knight",
                "Dauthi Mindripper",
                "Knight of Dawn",
                "Maelstrom Archangel",
                "Mana Tithe",
                "Oracle of Nectars",
                "Perish",
                "Pestilence",
                "Tower of Eons",
                "Ana Sanctuary",
                "Ancient Den",
                "Ancient Ziggurat",
                "Angelic Destiny",
                "Archangel",
                "Asceticism",
                "Assemble the Legion",
                "Athreos, God of Passage",
                "Aura Shards",
                "Bear Cub",
                "Bloom Tender",
                "Bonesplitter",
                "Bow of Nylea",
                "Brimstone Dragon",
                "Brimstone Mage",
                "Cairn Wanderer",
                "Carpet of Flowers",
                "Centaur Glade",
                "Chancellor of the Annex",
                "Chatter of the Squirrel",
                "Chromatic Star",
                "Contagion Clasp",
                "Corrupted Conscience",
                "Cragganwick Cremator",
                "Crenellated Wall",
                "Crystal Shard",
                "Darksteel Garrison",
                "Defense of the Heart",
                "Dictate of Erebos",
                "Dolmen Gate",
                "Dominus of Fealty",
                "Doomgape",
                "Draco",
                "Dragon Broodmother",
                "Dragon Mask",
                "Dungrove Elder",
                "Eater of Days",
                "Elixir of Immortality",
                "Empyrial Armor",
                "Enchanted Evening",
                "Energy Field",
                "Exsanguinate",
                "Flameshot",
                "Floodgate",
                "Font of Mythos",
                "Ghitu War Cry",
                "Gilt-Leaf Palace",
                "Goblin Game",
                "Greater Gargadon",
                "Guided Passage",
                "Haakon, Stromgald Scourge",
                "Hedron Crab",
                "Helm of Awakening",
                "Hunter of Eyeblights",
                "Hurricane",
                "Hypnotic Specter",
                "Impending Disaster",
                "Jushi Apprentice",
                "Kaervek's Torch",
                "Kargan Dragonlord",
                "Knollspine Dragon",
                "Kor Chant",
                "Kruphix, God of Horizons",
                "Lashknife Barrier",
                "Lotus Petal",
                "Magus of the Moat",
                "Manamorphose",
                "Martyr's Bond",
                "Martyr's Cause",
                "Master Transmuter",
                "Meddling Mage",
                "Mistform Shrieker",
                "Nemesis of Reason",
                "Pathrazer of Ulamog",
                "Phantasmal Dragon",
                "Phantom Centaur",
                "Phyrexian Metamorph",
                "Phyrexian Soulgorger",
                "Purphoros, God of the Forge",
                "Questing Phelddagrif",
                "Rage Reflection",
                "Recoup",
                "Release the Ants",
                "Rhys the Redeemed",
                "Rhystic Study",
                "Rishadan Footpad",
                "Rith, the Awakener",
                "River Boa",
                "Sadistic Hypnotist",
                "Sakashima the Impostor",
                "Sapphire Charm",
                "Shrouded Lore",
                "Soothsaying",
                "Sorin Markov",
                "Squirrel Wrangler",
                "Thieving Magpie",
                "Thrun, the Last Troll",
                "Time Sieve",
                "Timely Reinforcements",
                "Tinker",
                "Toxin Sliver",
                "Triumph of the Hordes",
                "Umbral Mantle",
                "Viashino Sandstalker",
                "Violent Ultimatum",
                "Volunteer Reserves",
                "Wargate",
                "Weathered Wayfarer",
                "Wild Nacatl",
                "Yavimaya's Embrace"
        ));
        this.populateSlot(14, asList(  // Post-M15 rare / mythic, sourced from magic.wizards.com
                "Adorned Pouncer",
                "Aetherflux Reservoir",
                "Akroan Horse",
                "Alesha, Who Smiles at Death",
                "Alhammarret's Archive",
                "All Is Dust",
                "Aminatou's Augury",
                "Angel of the Dire Hour",
                "Anger of the Gods",
                "Animar, Soul of Elements",
                "Approach of the Second Sun",
                "Arch of Orazca",
                "Basilisk Collar",
                "Beacon of Immortality",
                "Beastmaster Ascension",
                "Birds of Paradise",
                "Black Market",
                "Boompile",
                "Boros Reckoner",
                "Caged Sun",
                "Cauldron of Souls",
                "Champion of the Parish",
                "Chaos Warp",
                "Chasm Skulker",
                "Chromatic Lantern",
                "Coat of Arms",
                "Collective Brutality",
                "Commit // Memory",
                "Courser of Kruphix",
                "Coveted Jewel",
                "Daretti, Scrap Savant",
                "Deadly Tempest",
                "Debtors' Knell",
                "Decree of Justice",
                "Deepglow Skate",
                "Desolation Twin",
                "Dictate of Heliod",
                "Djinn of Wishes",
                "Dragonlord Ojutai",
                "Drana, Kalastria Bloodchief",
                "Eldrazi Monument",
                "Eldritch Evolution",
                "Elesh Norn, Grand Cenobite",
                "Evra, Halcyon Witness",
                "Expropriate",
                "Fblthp, the Lost",
                "Felidar Sovereign",
                "Gideon Jura",
                "Goblin Charbelcher",
                "Goblin Piledriver",
                "Gonti, Lord of Luxury",
                "Grasp of Fate",
                "Grave Titan",
                "Gravecrawler",
                "Greenbelt Rampager",
                "Hornet Nest",
                "Kiki-Jiki, Mirror Breaker",
                "Kolaghan's Command",
                "Krenko, Mob Boss",
                "Liliana, Death's Majesty",
                "Living Death",
                "Mana Crypt",
                "Meandering Towershell",
                "Memory Erosion",
                "Meren of Clan Nel Toth",
                "Mimic Vat",
                "Mind Shatter",
                "Mind Spring",
                "Mirran Crusader",
                "Mirror Entity",
                "Misdirection",
                "Mizzix's Mastery",
                "Mycoloth",
                "Mystic Confluence",
                "Nighthowler",
                "Nin, the Pain Artist",
                "Nissa, Voice of Zendikar",
                "Odric, Lunarch Marshal",
                "Phyrexian Arena",
                "Phyrexian Plaguelord",
                "Precursor Golem",
                "Preyseizer Dragon",
                "Queen Marchesa",
                "Reality Scramble",
                "Recruiter of the Guard",
                "Release the Gremlins",
                "Revel in Riches",
                "Rune-Scarred Demon",
                "Savage Knuckleblade",
                "Selvala, Heart of the Wilds",
                "Serendib Efreet",
                "Sewer Nemesis",
                "Shamanic Revelation",
                "Sliver Hivelord",
                "Solemn Simulacrum",
                "Spawning Grounds",
                "Star of Extinction",
                "Steamflogger Boss",
                "Stunt Double",
                "Sudden Demise",
                "Supreme Verdict",
                "Sword of the Animist",
                "Talrand, Sky Summoner",
                "Taurean Mauler",
                "Teferi, Temporal Archmage",
                "Teferi's Protection",
                "Temporal Mastery",
                "Tempt with Discovery",
                "Thalia's Lancers",
                "The Gitrog Monster",
                "The Mirari Conjecture",
                "Tireless Tracker",
                "Torment of Hailfire",
                "Trading Post",
                "Two-Headed Giant",
                "Urza's Rage",
                "Vigor",
                "Wheel of Fate",
                "Whelming Wave",
                "Whir of Invention",
                "Yuriko, the Tiger's Shadow"
        ));
        this.populateBoosterSpecialSlot();
    }

    /**
     * Populate the given booster slot.
     *
     * @param slotNumber booster slot number. 1-indexed, valid range is 1-14, as 15 is the special slot
     * @param cardNames  List of English card names found on the given slot
     */
    private void populateSlot(int slotNumber, List<String> cardNames) {
        final List<CardInfo> cardInfoList = this.possibleCardsPerBoosterSlot.get(slotNumber);
        for (String name : cardNames) {
            final CardInfo cardWithGivenName = CardRepository.instance.findCardWithPreferredSetAndNumber(name, this.code, null);
            cardInfoList.add(cardWithGivenName);
        }
    }

    /**
     * Populate the special slot. Defined as protected to allow overwriting this if the playtest cards
     * from the convention edition are ever implemented.
     */
    protected void populateBoosterSpecialSlot() {
        CardCriteria criteria = new CardCriteria();
        criteria.setCodes("FMB1");
        this.possibleCardsPerBoosterSlot.get(15).addAll(CardRepository.instance.findCards(criteria));
    }

    @Override
    public List<Card> createBooster() {
        if (this.possibleCardsPerBoosterSlot.isEmpty()) {
            // Generate the map only once
            this.populateBoosterSlotMap();
        }
        final List<Card> booster = new ArrayList<>(15);
        for (int slot = 1; slot < 16; ++slot) {
            final List<CardInfo> availableCards = this.possibleCardsPerBoosterSlot.get(slot);
            final int printSheetCardNumber = RandomUtil.nextInt(availableCards.size());
            final Card chosenCard = availableCards.get(printSheetCardNumber).getCard();
            booster.add(chosenCard);
        }
        return booster;
    }

    @Override
    public List<Card> create15CardBooster() {
        // ignore special partner generation for 15 booster
        return this.createBooster();
    }
}
