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
     */
    protected final Map<Integer, List<CardInfo>> possibleCardsPerBoosterSlot = new HashMap<>();


    private MysteryBooster() {
        super("Mystery Booster", "MB1", ExpansionSet.buildDate(2019, 11, 7), SetType.SUPPLEMENTAL);
        this.hasBoosters = true;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Absorb Vis", 558, Rarity.COMMON, mage.cards.a.AbsorbVis.class));
        cards.add(new SetCardInfo("Abundant Growth", 1108, Rarity.COMMON, mage.cards.a.AbundantGrowth.class));
        cards.add(new SetCardInfo("Abzan Charm", 1383, Rarity.UNCOMMON, mage.cards.a.AbzanCharm.class));
        cards.add(new SetCardInfo("Abzan Falconer", 7, Rarity.UNCOMMON, mage.cards.a.AbzanFalconer.class));
        cards.add(new SetCardInfo("Abzan Guide", 1384, Rarity.COMMON, mage.cards.a.AbzanGuide.class));
        cards.add(new SetCardInfo("Abzan Runemark", 8, Rarity.COMMON, mage.cards.a.AbzanRunemark.class));
        cards.add(new SetCardInfo("Academy Journeymage", 281, Rarity.COMMON, mage.cards.a.AcademyJourneymage.class));
        cards.add(new SetCardInfo("Accursed Spirit", 559, Rarity.COMMON, mage.cards.a.AccursedSpirit.class));
        cards.add(new SetCardInfo("Acidic Slime", 1109, Rarity.UNCOMMON, mage.cards.a.AcidicSlime.class));
        cards.add(new SetCardInfo("Acrobatic Maneuver", 9, Rarity.COMMON, mage.cards.a.AcrobaticManeuver.class));
        cards.add(new SetCardInfo("Act of Treason", 831, Rarity.COMMON, mage.cards.a.ActOfTreason.class));
        cards.add(new SetCardInfo("Act on Impulse", 832, Rarity.UNCOMMON, mage.cards.a.ActOnImpulse.class));
        cards.add(new SetCardInfo("Adanto Vanguard", 10, Rarity.UNCOMMON, mage.cards.a.AdantoVanguard.class));
        cards.add(new SetCardInfo("Adorned Pouncer", 11, Rarity.RARE, mage.cards.a.AdornedPouncer.class));
        cards.add(new SetCardInfo("Adventurous Impulse", 1110, Rarity.COMMON, mage.cards.a.AdventurousImpulse.class));
        cards.add(new SetCardInfo("Aerie Bowmasters", 1111, Rarity.COMMON, mage.cards.a.AerieBowmasters.class));
        cards.add(new SetCardInfo("Aether Hub", 1650, Rarity.UNCOMMON, mage.cards.a.AetherHub.class));
        cards.add(new SetCardInfo("Aether Spellbomb", 1540, Rarity.COMMON, mage.cards.a.AetherSpellbomb.class));
        cards.add(new SetCardInfo("Aether Tradewinds", 283, Rarity.COMMON, mage.cards.a.AetherTradewinds.class));
        cards.add(new SetCardInfo("Aetherflux Reservoir", 1539, Rarity.RARE, mage.cards.a.AetherfluxReservoir.class));
        cards.add(new SetCardInfo("Aethersnipe", 282, Rarity.COMMON, mage.cards.a.Aethersnipe.class));
        cards.add(new SetCardInfo("Affa Protector", 12, Rarity.COMMON, mage.cards.a.AffaProtector.class));
        cards.add(new SetCardInfo("Affectionate Indrik", 1112, Rarity.UNCOMMON, mage.cards.a.AffectionateIndrik.class));
        cards.add(new SetCardInfo("Aggressive Instinct", 1113, Rarity.COMMON, mage.cards.a.AggressiveInstinct.class));
        cards.add(new SetCardInfo("Aggressive Urge", 1114, Rarity.COMMON, mage.cards.a.AggressiveUrge.class));
        cards.add(new SetCardInfo("Agony Warp", 1385, Rarity.COMMON, mage.cards.a.AgonyWarp.class));
        cards.add(new SetCardInfo("Ahn-Crop Crasher", 833, Rarity.UNCOMMON, mage.cards.a.AhnCropCrasher.class));
        cards.add(new SetCardInfo("Aid the Fallen", 560, Rarity.COMMON, mage.cards.a.AidTheFallen.class));
        cards.add(new SetCardInfo("Ainok Bond-Kin", 13, Rarity.COMMON, mage.cards.a.AinokBondKin.class));
        cards.add(new SetCardInfo("Ainok Survivalist", 1115, Rarity.UNCOMMON, mage.cards.a.AinokSurvivalist.class));
        cards.add(new SetCardInfo("Ainok Tracker", 834, Rarity.COMMON, mage.cards.a.AinokTracker.class));
        cards.add(new SetCardInfo("Ajani's Pridemate", 14, Rarity.UNCOMMON, mage.cards.a.AjanisPridemate.class));
        cards.add(new SetCardInfo("Akoum Refuge", 1651, Rarity.UNCOMMON, mage.cards.a.AkoumRefuge.class));
        cards.add(new SetCardInfo("Akroan Hoplite", 1386, Rarity.UNCOMMON, mage.cards.a.AkroanHoplite.class));
        cards.add(new SetCardInfo("Akroan Horse", 1541, Rarity.RARE, mage.cards.a.AkroanHorse.class));
        cards.add(new SetCardInfo("Akroan Sergeant", 835, Rarity.COMMON, mage.cards.a.AkroanSergeant.class));
        cards.add(new SetCardInfo("Alchemist's Greeting", 836, Rarity.COMMON, mage.cards.a.AlchemistsGreeting.class));
        cards.add(new SetCardInfo("Alchemist's Vial", 1542, Rarity.COMMON, mage.cards.a.AlchemistsVial.class));
        cards.add(new SetCardInfo("Alesha's Vanguard", 561, Rarity.COMMON, mage.cards.a.AleshasVanguard.class));
        cards.add(new SetCardInfo("Alesha, Who Smiles at Death", 837, Rarity.RARE, mage.cards.a.AleshaWhoSmilesAtDeath.class));
        cards.add(new SetCardInfo("Alhammarret's Archive", 1543, Rarity.MYTHIC, mage.cards.a.AlhammarretsArchive.class));
        cards.add(new SetCardInfo("All Is Dust", 1, Rarity.RARE, mage.cards.a.AllIsDust.class));
        cards.add(new SetCardInfo("Alley Evasion", 15, Rarity.COMMON, mage.cards.a.AlleyEvasion.class));
        cards.add(new SetCardInfo("Alley Strangler", 562, Rarity.COMMON, mage.cards.a.AlleyStrangler.class));
        cards.add(new SetCardInfo("Alloy Myr", 1544, Rarity.COMMON, mage.cards.a.AlloyMyr.class));
        cards.add(new SetCardInfo("Alpine Grizzly", 1116, Rarity.COMMON, mage.cards.a.AlpineGrizzly.class));
        cards.add(new SetCardInfo("Altar's Reap", 563, Rarity.COMMON, mage.cards.a.AltarsReap.class));
        cards.add(new SetCardInfo("Amass the Components", 284, Rarity.COMMON, mage.cards.a.AmassTheComponents.class));
        cards.add(new SetCardInfo("Ambassador Oak", 1117, Rarity.COMMON, mage.cards.a.AmbassadorOak.class));
        cards.add(new SetCardInfo("Ambitious Aetherborn", 564, Rarity.COMMON, mage.cards.a.AmbitiousAetherborn.class));
        cards.add(new SetCardInfo("Aminatou's Augury", 285, Rarity.RARE, mage.cards.a.AminatousAugury.class));
        cards.add(new SetCardInfo("Amphin Pathmage", 286, Rarity.COMMON, mage.cards.a.AmphinPathmage.class));
        cards.add(new SetCardInfo("Ana Sanctuary", 1118, Rarity.UNCOMMON, mage.cards.a.AnaSanctuary.class));
        cards.add(new SetCardInfo("Ancestral Mask", 1119, Rarity.UNCOMMON, mage.cards.a.AncestralMask.class));
        cards.add(new SetCardInfo("Ancestral Vengeance", 565, Rarity.COMMON, mage.cards.a.AncestralVengeance.class));
        cards.add(new SetCardInfo("Ancient Brontodon", 1120, Rarity.COMMON, mage.cards.a.AncientBrontodon.class));
        cards.add(new SetCardInfo("Ancient Den", 1652, Rarity.COMMON, mage.cards.a.AncientDen.class));
        cards.add(new SetCardInfo("Ancient Grudge", 838, Rarity.UNCOMMON, mage.cards.a.AncientGrudge.class));
        cards.add(new SetCardInfo("Ancient Stirrings", 1121, Rarity.UNCOMMON, mage.cards.a.AncientStirrings.class));
        cards.add(new SetCardInfo("Ancient Ziggurat", 1653, Rarity.UNCOMMON, mage.cards.a.AncientZiggurat.class));
        cards.add(new SetCardInfo("Angel of Mercy", 19, Rarity.COMMON, mage.cards.a.AngelOfMercy.class));
        cards.add(new SetCardInfo("Angel of Renewal", 20, Rarity.UNCOMMON, mage.cards.a.AngelOfRenewal.class));
        cards.add(new SetCardInfo("Angel of the Dire Hour", 21, Rarity.RARE, mage.cards.a.AngelOfTheDireHour.class));
        cards.add(new SetCardInfo("Angelic Destiny", 16, Rarity.MYTHIC, mage.cards.a.AngelicDestiny.class));
        cards.add(new SetCardInfo("Angelic Gift", 17, Rarity.COMMON, mage.cards.a.AngelicGift.class));
        cards.add(new SetCardInfo("Angelic Purge", 18, Rarity.COMMON, mage.cards.a.AngelicPurge.class));
        cards.add(new SetCardInfo("Angelsong", 22, Rarity.COMMON, mage.cards.a.Angelsong.class));
        cards.add(new SetCardInfo("Anger", 839, Rarity.UNCOMMON, mage.cards.a.Anger.class));
        cards.add(new SetCardInfo("Anger of the Gods", 840, Rarity.RARE, mage.cards.a.AngerOfTheGods.class));
        cards.add(new SetCardInfo("Animar, Soul of Elements", 1387, Rarity.MYTHIC, mage.cards.a.AnimarSoulOfElements.class));
        cards.add(new SetCardInfo("Animate Dead", 566, Rarity.UNCOMMON, mage.cards.a.AnimateDead.class));
        cards.add(new SetCardInfo("Annihilate", 567, Rarity.UNCOMMON, mage.cards.a.Annihilate.class));
        cards.add(new SetCardInfo("Anticipate", 287, Rarity.COMMON, mage.cards.a.Anticipate.class));
        cards.add(new SetCardInfo("Apostle's Blessing", 23, Rarity.COMMON, mage.cards.a.ApostlesBlessing.class));
        cards.add(new SetCardInfo("Approach of the Second Sun", 24, Rarity.RARE, mage.cards.a.ApproachOfTheSecondSun.class));
        cards.add(new SetCardInfo("Arachnus Web", 1122, Rarity.COMMON, mage.cards.a.ArachnusWeb.class));
        cards.add(new SetCardInfo("Arbor Armament", 1123, Rarity.COMMON, mage.cards.a.ArborArmament.class));
        cards.add(new SetCardInfo("Arbor Elf", 1124, Rarity.COMMON, mage.cards.a.ArborElf.class));
        cards.add(new SetCardInfo("Arc Trail", 841, Rarity.UNCOMMON, mage.cards.a.ArcTrail.class));
        cards.add(new SetCardInfo("Arcane Denial", 288, Rarity.COMMON, mage.cards.a.ArcaneDenial.class));
        cards.add(new SetCardInfo("Arcane Sanctum", 1654, Rarity.UNCOMMON, mage.cards.a.ArcaneSanctum.class));
        cards.add(new SetCardInfo("Arch of Orazca", 1655, Rarity.RARE, mage.cards.a.ArchOfOrazca.class));
        cards.add(new SetCardInfo("Archaeomancer", 289, Rarity.COMMON, mage.cards.a.Archaeomancer.class));
        cards.add(new SetCardInfo("Archangel", 25, Rarity.UNCOMMON, mage.cards.a.Archangel.class));
        cards.add(new SetCardInfo("Archetype of Imagination", 290, Rarity.UNCOMMON, mage.cards.a.ArchetypeOfImagination.class));
        cards.add(new SetCardInfo("Armadillo Cloak", 1388, Rarity.UNCOMMON, mage.cards.a.ArmadilloCloak.class));
        cards.add(new SetCardInfo("Armament Corps", 1389, Rarity.UNCOMMON, mage.cards.a.ArmamentCorps.class));
        cards.add(new SetCardInfo("Armillary Sphere", 1545, Rarity.COMMON, mage.cards.a.ArmillarySphere.class));
        cards.add(new SetCardInfo("Arrest", 26, Rarity.COMMON, mage.cards.a.Arrest.class));
        cards.add(new SetCardInfo("Arrester's Zeal", 27, Rarity.COMMON, mage.cards.a.ArrestersZeal.class));
        cards.add(new SetCardInfo("Arrow Storm", 842, Rarity.COMMON, mage.cards.a.ArrowStorm.class));
        cards.add(new SetCardInfo("Artful Maneuver", 28, Rarity.COMMON, mage.cards.a.ArtfulManeuver.class));
        cards.add(new SetCardInfo("Artificer's Assistant", 291, Rarity.COMMON, mage.cards.a.ArtificersAssistant.class));
        cards.add(new SetCardInfo("Artisan of Kozilek", 2, Rarity.UNCOMMON, mage.cards.a.ArtisanOfKozilek.class));
        cards.add(new SetCardInfo("Asceticism", 1125, Rarity.RARE, mage.cards.a.Asceticism.class));
        cards.add(new SetCardInfo("Ash Barrens", 1656, Rarity.COMMON, mage.cards.a.AshBarrens.class));
        cards.add(new SetCardInfo("Ashnod's Altar", 1546, Rarity.UNCOMMON, mage.cards.a.AshnodsAltar.class));
        cards.add(new SetCardInfo("Assemble the Legion", 1390, Rarity.RARE, mage.cards.a.AssembleTheLegion.class));
        cards.add(new SetCardInfo("Atarka Efreet", 843, Rarity.COMMON, mage.cards.a.AtarkaEfreet.class));
        cards.add(new SetCardInfo("Athreos, God of Passage", 1391, Rarity.MYTHIC, mage.cards.a.AthreosGodOfPassage.class));
        cards.add(new SetCardInfo("Augur of Bolas", 292, Rarity.COMMON, mage.cards.a.AugurOfBolas.class));
        cards.add(new SetCardInfo("Augury Owl", 293, Rarity.COMMON, mage.cards.a.AuguryOwl.class));
        cards.add(new SetCardInfo("Aura Gnarlid", 1126, Rarity.COMMON, mage.cards.a.AuraGnarlid.class));
        cards.add(new SetCardInfo("Aura Shards", 1392, Rarity.UNCOMMON, mage.cards.a.AuraShards.class));
        cards.add(new SetCardInfo("Aura of Silence", 29, Rarity.UNCOMMON, mage.cards.a.AuraOfSilence.class));
        cards.add(new SetCardInfo("Avacyn's Pilgrim", 1127, Rarity.COMMON, mage.cards.a.AvacynsPilgrim.class));
        cards.add(new SetCardInfo("Avalanche Riders", 844, Rarity.UNCOMMON, mage.cards.a.AvalancheRiders.class));
        cards.add(new SetCardInfo("Avarax", 845, Rarity.COMMON, mage.cards.a.Avarax.class));
        cards.add(new SetCardInfo("Aven Battle Priest", 30, Rarity.COMMON, mage.cards.a.AvenBattlePriest.class));
        cards.add(new SetCardInfo("Aven Sentry", 31, Rarity.COMMON, mage.cards.a.AvenSentry.class));
        cards.add(new SetCardInfo("Azorius Charm", 1393, Rarity.UNCOMMON, mage.cards.a.AzoriusCharm.class));
        cards.add(new SetCardInfo("Azra Bladeseeker", 846, Rarity.COMMON, mage.cards.a.AzraBladeseeker.class));
        cards.add(new SetCardInfo("Azra Oddsmaker", 1394, Rarity.UNCOMMON, mage.cards.a.AzraOddsmaker.class));
        cards.add(new SetCardInfo("Backwoods Survivalists", 1128, Rarity.COMMON, mage.cards.b.BackwoodsSurvivalists.class));
        cards.add(new SetCardInfo("Bala Ged Scorpion", 568, Rarity.COMMON, mage.cards.b.BalaGedScorpion.class));
        cards.add(new SetCardInfo("Balduvian Horde", 847, Rarity.COMMON, mage.cards.b.BalduvianHorde.class));
        cards.add(new SetCardInfo("Baleful Ammit", 569, Rarity.UNCOMMON, mage.cards.b.BalefulAmmit.class));
        cards.add(new SetCardInfo("Baleful Strix", 1395, Rarity.UNCOMMON, mage.cards.b.BalefulStrix.class));
        cards.add(new SetCardInfo("Ballynock Cohort", 32, Rarity.COMMON, mage.cards.b.BallynockCohort.class));
        cards.add(new SetCardInfo("Baloth Gorger", 1129, Rarity.COMMON, mage.cards.b.BalothGorger.class));
        cards.add(new SetCardInfo("Baloth Null", 1396, Rarity.UNCOMMON, mage.cards.b.BalothNull.class));
        cards.add(new SetCardInfo("Balustrade Spy", 570, Rarity.COMMON, mage.cards.b.BalustradeSpy.class));
        cards.add(new SetCardInfo("Barging Sergeant", 848, Rarity.COMMON, mage.cards.b.BargingSergeant.class));
        cards.add(new SetCardInfo("Barrage of Boulders", 849, Rarity.COMMON, mage.cards.b.BarrageOfBoulders.class));
        cards.add(new SetCardInfo("Bartered Cow", 33, Rarity.COMMON, mage.cards.b.BarteredCow.class));
        cards.add(new SetCardInfo("Bartizan Bats", 571, Rarity.COMMON, mage.cards.b.BartizanBats.class));
        cards.add(new SetCardInfo("Basilisk Collar", 1547, Rarity.RARE, mage.cards.b.BasiliskCollar.class));
        cards.add(new SetCardInfo("Basking Rootwalla", 1130, Rarity.COMMON, mage.cards.b.BaskingRootwalla.class));
        cards.add(new SetCardInfo("Bastion Inventor", 294, Rarity.COMMON, mage.cards.b.BastionInventor.class));
        cards.add(new SetCardInfo("Battle Mastery", 34, Rarity.UNCOMMON, mage.cards.b.BattleMastery.class));
        cards.add(new SetCardInfo("Battle Rampart", 850, Rarity.COMMON, mage.cards.b.BattleRampart.class));
        cards.add(new SetCardInfo("Battle-Rattle Shaman", 851, Rarity.COMMON, mage.cards.b.BattleRattleShaman.class));
        cards.add(new SetCardInfo("Beacon of Immortality", 35, Rarity.RARE, mage.cards.b.BeaconOfImmortality.class));
        cards.add(new SetCardInfo("Bear Cub", 1131, Rarity.COMMON, mage.cards.b.BearCub.class));
        cards.add(new SetCardInfo("Bear's Companion", 1397, Rarity.UNCOMMON, mage.cards.b.BearsCompanion.class));
        cards.add(new SetCardInfo("Beast Within", 1134, Rarity.UNCOMMON, mage.cards.b.BeastWithin.class));
        cards.add(new SetCardInfo("Beastbreaker of Bala Ged", 1132, Rarity.UNCOMMON, mage.cards.b.BeastbreakerOfBalaGed.class));
        cards.add(new SetCardInfo("Beastmaster Ascension", 1133, Rarity.RARE, mage.cards.b.BeastmasterAscension.class));
        cards.add(new SetCardInfo("Become Immense", 1135, Rarity.UNCOMMON, mage.cards.b.BecomeImmense.class));
        cards.add(new SetCardInfo("Beetleback Chief", 852, Rarity.UNCOMMON, mage.cards.b.BeetlebackChief.class));
        cards.add(new SetCardInfo("Befuddle", 295, Rarity.COMMON, mage.cards.b.Befuddle.class));
        cards.add(new SetCardInfo("Belbe's Portal", 1548, Rarity.RARE, mage.cards.b.BelbesPortal.class));
        cards.add(new SetCardInfo("Belligerent Brontodon", 1398, Rarity.UNCOMMON, mage.cards.b.BelligerentBrontodon.class));
        cards.add(new SetCardInfo("Bellows Lizard", 853, Rarity.COMMON, mage.cards.b.BellowsLizard.class));
        cards.add(new SetCardInfo("Beneath the Sands", 1136, Rarity.COMMON, mage.cards.b.BeneathTheSands.class));
        cards.add(new SetCardInfo("Benevolent Ancestor", 36, Rarity.COMMON, mage.cards.b.BenevolentAncestor.class));
        cards.add(new SetCardInfo("Benthic Giant", 296, Rarity.COMMON, mage.cards.b.BenthicGiant.class));
        cards.add(new SetCardInfo("Benthic Infiltrator", 297, Rarity.COMMON, mage.cards.b.BenthicInfiltrator.class));
        cards.add(new SetCardInfo("Bestial Menace", 1137, Rarity.UNCOMMON, mage.cards.b.BestialMenace.class));
        cards.add(new SetCardInfo("Bewilder", 298, Rarity.COMMON, mage.cards.b.Bewilder.class));
        cards.add(new SetCardInfo("Birds of Paradise", 1138, Rarity.RARE, mage.cards.b.BirdsOfParadise.class));
        cards.add(new SetCardInfo("Bitter Revelation", 572, Rarity.COMMON, mage.cards.b.BitterRevelation.class));
        cards.add(new SetCardInfo("Bitterblade Warrior", 1139, Rarity.COMMON, mage.cards.b.BitterbladeWarrior.class));
        cards.add(new SetCardInfo("Bitterbow Sharpshooters", 1140, Rarity.COMMON, mage.cards.b.BitterbowSharpshooters.class));
        cards.add(new SetCardInfo("Bituminous Blast", 1399, Rarity.UNCOMMON, mage.cards.b.BituminousBlast.class));
        cards.add(new SetCardInfo("Black Cat", 573, Rarity.COMMON, mage.cards.b.BlackCat.class));
        cards.add(new SetCardInfo("Black Knight", 574, Rarity.UNCOMMON, mage.cards.b.BlackKnight.class));
        cards.add(new SetCardInfo("Black Market", 575, Rarity.RARE, mage.cards.b.BlackMarket.class));
        cards.add(new SetCardInfo("Blade Instructor", 37, Rarity.COMMON, mage.cards.b.BladeInstructor.class));
        cards.add(new SetCardInfo("Bladebrand", 576, Rarity.COMMON, mage.cards.b.Bladebrand.class));
        cards.add(new SetCardInfo("Blades of Velis Vel", 854, Rarity.COMMON, mage.cards.b.BladesOfVelisVel.class));
        cards.add(new SetCardInfo("Bladewing the Risen", 1400, Rarity.UNCOMMON, mage.cards.b.BladewingTheRisen.class));
        cards.add(new SetCardInfo("Blanchwood Armor", 1141, Rarity.UNCOMMON, mage.cards.b.BlanchwoodArmor.class));
        cards.add(new SetCardInfo("Blasted Landscape", 1657, Rarity.UNCOMMON, mage.cards.b.BlastedLandscape.class));
        cards.add(new SetCardInfo("Blastfire Bolt", 855, Rarity.COMMON, mage.cards.b.BlastfireBolt.class));
        cards.add(new SetCardInfo("Blastoderm", 1142, Rarity.COMMON, mage.cards.b.Blastoderm.class));
        cards.add(new SetCardInfo("Blazing Volley", 856, Rarity.COMMON, mage.cards.b.BlazingVolley.class));
        cards.add(new SetCardInfo("Blessed Spirits", 38, Rarity.UNCOMMON, mage.cards.b.BlessedSpirits.class));
        cards.add(new SetCardInfo("Blessing of Belzenlok", 577, Rarity.COMMON, mage.cards.b.BlessingOfBelzenlok.class));
        cards.add(new SetCardInfo("Blighted Bat", 578, Rarity.COMMON, mage.cards.b.BlightedBat.class));
        cards.add(new SetCardInfo("Blighted Fen", 1658, Rarity.UNCOMMON, mage.cards.b.BlightedFen.class));
        cards.add(new SetCardInfo("Blightning", 1401, Rarity.UNCOMMON, mage.cards.b.Blightning.class));
        cards.add(new SetCardInfo("Blightsoil Druid", 579, Rarity.COMMON, mage.cards.b.BlightsoilDruid.class));
        cards.add(new SetCardInfo("Blindblast", 857, Rarity.COMMON, mage.cards.b.Blindblast.class));
        cards.add(new SetCardInfo("Blinding Souleater", 1549, Rarity.COMMON, mage.cards.b.BlindingSouleater.class));
        cards.add(new SetCardInfo("Blistergrub", 580, Rarity.COMMON, mage.cards.b.Blistergrub.class));
        cards.add(new SetCardInfo("Blood Artist", 581, Rarity.UNCOMMON, mage.cards.b.BloodArtist.class));
        cards.add(new SetCardInfo("Blood Ogre", 861, Rarity.COMMON, mage.cards.b.BloodOgre.class));
        cards.add(new SetCardInfo("Bloodbraid Elf", 1402, Rarity.UNCOMMON, mage.cards.b.BloodbraidElf.class));
        cards.add(new SetCardInfo("Bloodfire Expert", 858, Rarity.COMMON, mage.cards.b.BloodfireExpert.class));
        cards.add(new SetCardInfo("Bloodlust Inciter", 859, Rarity.COMMON, mage.cards.b.BloodlustInciter.class));
        cards.add(new SetCardInfo("Bloodmad Vampire", 860, Rarity.COMMON, mage.cards.b.BloodmadVampire.class));
        cards.add(new SetCardInfo("Bloodrite Invoker", 582, Rarity.COMMON, mage.cards.b.BloodriteInvoker.class));
        cards.add(new SetCardInfo("Bloodstone Goblin", 862, Rarity.COMMON, mage.cards.b.BloodstoneGoblin.class));
        cards.add(new SetCardInfo("Bloom Tender", 1143, Rarity.RARE, mage.cards.b.BloomTender.class));
        cards.add(new SetCardInfo("Blossom Dryad", 1144, Rarity.COMMON, mage.cards.b.BlossomDryad.class));
        cards.add(new SetCardInfo("Blossoming Sands", 1659, Rarity.COMMON, mage.cards.b.BlossomingSands.class));
        cards.add(new SetCardInfo("Blow Your House Down", 863, Rarity.COMMON, mage.cards.b.BlowYourHouseDown.class));
        cards.add(new SetCardInfo("Blue Elemental Blast", 299, Rarity.UNCOMMON, mage.cards.b.BlueElementalBlast.class));
        cards.add(new SetCardInfo("Blur of Blades", 864, Rarity.COMMON, mage.cards.b.BlurOfBlades.class));
        cards.add(new SetCardInfo("Boggart Brute", 865, Rarity.COMMON, mage.cards.b.BoggartBrute.class));
        cards.add(new SetCardInfo("Boiling Earth", 866, Rarity.COMMON, mage.cards.b.BoilingEarth.class));
        cards.add(new SetCardInfo("Bojuka Bog", 1660, Rarity.COMMON, mage.cards.b.BojukaBog.class));
        cards.add(new SetCardInfo("Bomat Bazaar Barge", 1550, Rarity.UNCOMMON, mage.cards.b.BomatBazaarBarge.class));
        cards.add(new SetCardInfo("Bombard", 867, Rarity.COMMON, mage.cards.b.Bombard.class));
        cards.add(new SetCardInfo("Bomber Corps", 868, Rarity.COMMON, mage.cards.b.BomberCorps.class));
        cards.add(new SetCardInfo("Bonds of Faith", 39, Rarity.COMMON, mage.cards.b.BondsOfFaith.class));
        cards.add(new SetCardInfo("Bone Saw", 1551, Rarity.COMMON, mage.cards.b.BoneSaw.class));
        cards.add(new SetCardInfo("Bone Splinters", 583, Rarity.COMMON, mage.cards.b.BoneSplinters.class));
        cards.add(new SetCardInfo("Bonesplitter", 1552, Rarity.COMMON, mage.cards.b.Bonesplitter.class));
        cards.add(new SetCardInfo("Boompile", 1553, Rarity.RARE, mage.cards.b.Boompile.class));
        cards.add(new SetCardInfo("Boon of Emrakul", 584, Rarity.COMMON, mage.cards.b.BoonOfEmrakul.class));
        cards.add(new SetCardInfo("Borderland Explorer", 1145, Rarity.COMMON, mage.cards.b.BorderlandExplorer.class));
        cards.add(new SetCardInfo("Borderland Ranger", 1146, Rarity.COMMON, mage.cards.b.BorderlandRanger.class));
        cards.add(new SetCardInfo("Boros Challenger", 1403, Rarity.UNCOMMON, mage.cards.b.BorosChallenger.class));
        cards.add(new SetCardInfo("Boros Reckoner", 1518, Rarity.RARE, mage.cards.b.BorosReckoner.class));
        cards.add(new SetCardInfo("Borrowed Grace", 40, Rarity.COMMON, mage.cards.b.BorrowedGrace.class));
        cards.add(new SetCardInfo("Borrowed Hostility", 869, Rarity.COMMON, mage.cards.b.BorrowedHostility.class));
        cards.add(new SetCardInfo("Borrowing 100,000 Arrows", 300, Rarity.COMMON, mage.cards.b.Borrowing100000Arrows.class));
        cards.add(new SetCardInfo("Bottle Gnomes", 1554, Rarity.UNCOMMON, mage.cards.b.BottleGnomes.class));
        cards.add(new SetCardInfo("Boulder Salvo", 870, Rarity.COMMON, mage.cards.b.BoulderSalvo.class));
        cards.add(new SetCardInfo("Bounding Krasis", 1404, Rarity.UNCOMMON, mage.cards.b.BoundingKrasis.class));
        cards.add(new SetCardInfo("Bow of Nylea", 1147, Rarity.RARE, mage.cards.b.BowOfNylea.class));
        cards.add(new SetCardInfo("Brainstorm", 301, Rarity.COMMON, mage.cards.b.Brainstorm.class));
        cards.add(new SetCardInfo("Brazen Buccaneers", 871, Rarity.COMMON, mage.cards.b.BrazenBuccaneers.class));
        cards.add(new SetCardInfo("Brazen Wolves", 872, Rarity.COMMON, mage.cards.b.BrazenWolves.class));
        cards.add(new SetCardInfo("Breaker of Armies", 3, Rarity.UNCOMMON, mage.cards.b.BreakerOfArmies.class));
        cards.add(new SetCardInfo("Breeding Pit", 585, Rarity.UNCOMMON, mage.cards.b.BreedingPit.class));
        cards.add(new SetCardInfo("Briarhorn", 1148, Rarity.UNCOMMON, mage.cards.b.Briarhorn.class));
        cards.add(new SetCardInfo("Brilliant Spectrum", 302, Rarity.COMMON, mage.cards.b.BrilliantSpectrum.class));
        cards.add(new SetCardInfo("Brimstone Dragon", 873, Rarity.RARE, mage.cards.b.BrimstoneDragon.class));
        cards.add(new SetCardInfo("Brimstone Mage", 874, Rarity.UNCOMMON, mage.cards.b.BrimstoneMage.class));
        cards.add(new SetCardInfo("Brine Elemental", 303, Rarity.UNCOMMON, mage.cards.b.BrineElemental.class));
        cards.add(new SetCardInfo("Bring Low", 875, Rarity.COMMON, mage.cards.b.BringLow.class));
        cards.add(new SetCardInfo("Bristling Boar", 1149, Rarity.COMMON, mage.cards.b.BristlingBoar.class));
        cards.add(new SetCardInfo("Broken Bond", 1150, Rarity.COMMON, mage.cards.b.BrokenBond.class));
        cards.add(new SetCardInfo("Broodhunter Wurm", 1151, Rarity.COMMON, mage.cards.b.BroodhunterWurm.class));
        cards.add(new SetCardInfo("Browbeat", 876, Rarity.UNCOMMON, mage.cards.b.Browbeat.class));
        cards.add(new SetCardInfo("Brute Strength", 877, Rarity.COMMON, mage.cards.b.BruteStrength.class));
        cards.add(new SetCardInfo("Built to Last", 41, Rarity.COMMON, mage.cards.b.BuiltToLast.class));
        cards.add(new SetCardInfo("Built to Smash", 878, Rarity.COMMON, mage.cards.b.BuiltToSmash.class));
        cards.add(new SetCardInfo("Bulwark Giant", 42, Rarity.COMMON, mage.cards.b.BulwarkGiant.class));
        cards.add(new SetCardInfo("Burnished Hart", 1555, Rarity.UNCOMMON, mage.cards.b.BurnishedHart.class));
        cards.add(new SetCardInfo("Burst Lightning", 879, Rarity.COMMON, mage.cards.b.BurstLightning.class));
        cards.add(new SetCardInfo("Butcher's Glee", 586, Rarity.COMMON, mage.cards.b.ButchersGlee.class));
        cards.add(new SetCardInfo("Byway Courier", 1152, Rarity.COMMON, mage.cards.b.BywayCourier.class));
        cards.add(new SetCardInfo("Cabal Therapy", 587, Rarity.UNCOMMON, mage.cards.c.CabalTherapy.class));
        cards.add(new SetCardInfo("Cackling Imp", 588, Rarity.COMMON, mage.cards.c.CacklingImp.class));
        cards.add(new SetCardInfo("Cadaver Imp", 589, Rarity.COMMON, mage.cards.c.CadaverImp.class));
        cards.add(new SetCardInfo("Caged Sun", 1556, Rarity.RARE, mage.cards.c.CagedSun.class));
        cards.add(new SetCardInfo("Cairn Wanderer", 590, Rarity.RARE, mage.cards.c.CairnWanderer.class));
        cards.add(new SetCardInfo("Calculated Dismissal", 304, Rarity.COMMON, mage.cards.c.CalculatedDismissal.class));
        cards.add(new SetCardInfo("Caligo Skin-Witch", 591, Rarity.COMMON, mage.cards.c.CaligoSkinWitch.class));
        cards.add(new SetCardInfo("Call of the Nightwing", 1405, Rarity.UNCOMMON, mage.cards.c.CallOfTheNightwing.class));
        cards.add(new SetCardInfo("Call the Scions", 1153, Rarity.COMMON, mage.cards.c.CallTheScions.class));
        cards.add(new SetCardInfo("Call to Heel", 306, Rarity.COMMON, mage.cards.c.CallToHeel.class));
        cards.add(new SetCardInfo("Caller of Gales", 305, Rarity.COMMON, mage.cards.c.CallerOfGales.class));
        cards.add(new SetCardInfo("Campaign of Vengeance", 1406, Rarity.UNCOMMON, mage.cards.c.CampaignOfVengeance.class));
        cards.add(new SetCardInfo("Cancel", 307, Rarity.COMMON, mage.cards.c.Cancel.class));
        cards.add(new SetCardInfo("Candlelight Vigil", 43, Rarity.COMMON, mage.cards.c.CandlelightVigil.class));
        cards.add(new SetCardInfo("Canopy Spider", 1154, Rarity.COMMON, mage.cards.c.CanopySpider.class));
        cards.add(new SetCardInfo("Canyon Lurkers", 880, Rarity.COMMON, mage.cards.c.CanyonLurkers.class));
        cards.add(new SetCardInfo("Capture Sphere", 308, Rarity.COMMON, mage.cards.c.CaptureSphere.class));
        cards.add(new SetCardInfo("Caravan Escort", 44, Rarity.COMMON, mage.cards.c.CaravanEscort.class));
        cards.add(new SetCardInfo("Carnivorous Moss-Beast", 1155, Rarity.COMMON, mage.cards.c.CarnivorousMossBeast.class));
        cards.add(new SetCardInfo("Carpet of Flowers", 1156, Rarity.UNCOMMON, mage.cards.c.CarpetOfFlowers.class));
        cards.add(new SetCardInfo("Carrion Feeder", 592, Rarity.UNCOMMON, mage.cards.c.CarrionFeeder.class));
        cards.add(new SetCardInfo("Carrion Imp", 593, Rarity.COMMON, mage.cards.c.CarrionImp.class));
        cards.add(new SetCardInfo("Cartouche of Knowledge", 309, Rarity.COMMON, mage.cards.c.CartoucheOfKnowledge.class));
        cards.add(new SetCardInfo("Cartouche of Solidarity", 45, Rarity.COMMON, mage.cards.c.CartoucheOfSolidarity.class));
        cards.add(new SetCardInfo("Cartouche of Zeal", 881, Rarity.COMMON, mage.cards.c.CartoucheOfZeal.class));
        cards.add(new SetCardInfo("Cast Out", 46, Rarity.UNCOMMON, mage.cards.c.CastOut.class));
        cards.add(new SetCardInfo("Castaway's Despair", 310, Rarity.COMMON, mage.cards.c.CastawaysDespair.class));
        cards.add(new SetCardInfo("Catacomb Crocodile", 594, Rarity.COMMON, mage.cards.c.CatacombCrocodile.class));
        cards.add(new SetCardInfo("Catacomb Slug", 595, Rarity.COMMON, mage.cards.c.CatacombSlug.class));
        cards.add(new SetCardInfo("Catalog", 311, Rarity.COMMON, mage.cards.c.Catalog.class));
        cards.add(new SetCardInfo("Cathar's Companion", 47, Rarity.COMMON, mage.cards.c.CatharsCompanion.class));
        cards.add(new SetCardInfo("Cathartic Reunion", 882, Rarity.COMMON, mage.cards.c.CatharticReunion.class));
        cards.add(new SetCardInfo("Cathodion", 1557, Rarity.UNCOMMON, mage.cards.c.Cathodion.class));
        cards.add(new SetCardInfo("Caught in the Brights", 48, Rarity.COMMON, mage.cards.c.CaughtInTheBrights.class));
        cards.add(new SetCardInfo("Cauldron Dance", 1407, Rarity.UNCOMMON, mage.cards.c.CauldronDance.class));
        cards.add(new SetCardInfo("Cauldron of Souls", 1558, Rarity.RARE, mage.cards.c.CauldronOfSouls.class));
        cards.add(new SetCardInfo("Caustic Caterpillar", 1157, Rarity.COMMON, mage.cards.c.CausticCaterpillar.class));
        cards.add(new SetCardInfo("Caustic Tar", 596, Rarity.UNCOMMON, mage.cards.c.CausticTar.class));
        cards.add(new SetCardInfo("Celestial Crusader", 49, Rarity.UNCOMMON, mage.cards.c.CelestialCrusader.class));
        cards.add(new SetCardInfo("Celestial Flare", 50, Rarity.COMMON, mage.cards.c.CelestialFlare.class));
        cards.add(new SetCardInfo("Centaur Courser", 1158, Rarity.COMMON, mage.cards.c.CentaurCourser.class));
        cards.add(new SetCardInfo("Centaur Glade", 1159, Rarity.UNCOMMON, mage.cards.c.CentaurGlade.class));
        cards.add(new SetCardInfo("Center Soul", 51, Rarity.COMMON, mage.cards.c.CenterSoul.class));
        cards.add(new SetCardInfo("Certain Death", 597, Rarity.COMMON, mage.cards.c.CertainDeath.class));
        cards.add(new SetCardInfo("Champion of Arashin", 52, Rarity.COMMON, mage.cards.c.ChampionOfArashin.class));
        cards.add(new SetCardInfo("Champion of the Parish", 53, Rarity.RARE, mage.cards.c.ChampionOfTheParish.class));
        cards.add(new SetCardInfo("Chancellor of the Annex", 54, Rarity.RARE, mage.cards.c.ChancellorOfTheAnnex.class));
        cards.add(new SetCardInfo("Chandra's Pyrohelix", 883, Rarity.COMMON, mage.cards.c.ChandrasPyrohelix.class));
        cards.add(new SetCardInfo("Chandra's Revolution", 884, Rarity.COMMON, mage.cards.c.ChandrasRevolution.class));
        cards.add(new SetCardInfo("Chaos Warp", 885, Rarity.RARE, mage.cards.c.ChaosWarp.class));
        cards.add(new SetCardInfo("Charge", 55, Rarity.COMMON, mage.cards.c.Charge.class));
        cards.add(new SetCardInfo("Charging Monstrosaur", 886, Rarity.UNCOMMON, mage.cards.c.ChargingMonstrosaur.class));
        cards.add(new SetCardInfo("Charging Rhino", 1160, Rarity.COMMON, mage.cards.c.ChargingRhino.class));
        cards.add(new SetCardInfo("Chart a Course", 312, Rarity.UNCOMMON, mage.cards.c.ChartACourse.class));
        cards.add(new SetCardInfo("Chartooth Cougar", 887, Rarity.COMMON, mage.cards.c.ChartoothCougar.class));
        cards.add(new SetCardInfo("Chasm Skulker", 313, Rarity.RARE, mage.cards.c.ChasmSkulker.class));
        cards.add(new SetCardInfo("Chatter of the Squirrel", 1161, Rarity.COMMON, mage.cards.c.ChatterOfTheSquirrel.class));
        cards.add(new SetCardInfo("Child of Night", 598, Rarity.COMMON, mage.cards.c.ChildOfNight.class));
        cards.add(new SetCardInfo("Chillbringer", 314, Rarity.COMMON, mage.cards.c.Chillbringer.class));
        cards.add(new SetCardInfo("Choking Tethers", 315, Rarity.COMMON, mage.cards.c.ChokingTethers.class));
        cards.add(new SetCardInfo("Chromatic Lantern", 1559, Rarity.RARE, mage.cards.c.ChromaticLantern.class));
        cards.add(new SetCardInfo("Chromatic Star", 1560, Rarity.COMMON, mage.cards.c.ChromaticStar.class));
        cards.add(new SetCardInfo("Chronostutter", 316, Rarity.COMMON, mage.cards.c.Chronostutter.class));
        cards.add(new SetCardInfo("Cinder Hellion", 888, Rarity.COMMON, mage.cards.c.CinderHellion.class));
        cards.add(new SetCardInfo("Circular Logic", 317, Rarity.UNCOMMON, mage.cards.c.CircularLogic.class));
        cards.add(new SetCardInfo("Citadel Castellan", 1408, Rarity.UNCOMMON, mage.cards.c.CitadelCastellan.class));
        cards.add(new SetCardInfo("Citanul Woodreaders", 1162, Rarity.COMMON, mage.cards.c.CitanulWoodreaders.class));
        cards.add(new SetCardInfo("Citywatch Sphinx", 318, Rarity.UNCOMMON, mage.cards.c.CitywatchSphinx.class));
        cards.add(new SetCardInfo("Claim // Fame", 1536, Rarity.UNCOMMON, mage.cards.c.ClaimFame.class));
        cards.add(new SetCardInfo("Claustrophobia", 319, Rarity.COMMON, mage.cards.c.Claustrophobia.class));
        cards.add(new SetCardInfo("Cleansing Screech", 889, Rarity.COMMON, mage.cards.c.CleansingScreech.class));
        cards.add(new SetCardInfo("Clear the Mind", 320, Rarity.COMMON, mage.cards.c.ClearTheMind.class));
        cards.add(new SetCardInfo("Cliffside Lookout", 56, Rarity.COMMON, mage.cards.c.CliffsideLookout.class));
        cards.add(new SetCardInfo("Clip Wings", 1163, Rarity.COMMON, mage.cards.c.ClipWings.class));
        cards.add(new SetCardInfo("Cloak of Mists", 321, Rarity.COMMON, mage.cards.c.CloakOfMists.class));
        cards.add(new SetCardInfo("Cloud Elemental", 322, Rarity.COMMON, mage.cards.c.CloudElemental.class));
        cards.add(new SetCardInfo("Cloudkin Seer", 323, Rarity.COMMON, mage.cards.c.CloudkinSeer.class));
        cards.add(new SetCardInfo("Cloudreader Sphinx", 324, Rarity.COMMON, mage.cards.c.CloudreaderSphinx.class));
        cards.add(new SetCardInfo("Cloudshift", 57, Rarity.COMMON, mage.cards.c.Cloudshift.class));
        cards.add(new SetCardInfo("Clutch of Currents", 325, Rarity.COMMON, mage.cards.c.ClutchOfCurrents.class));
        cards.add(new SetCardInfo("Coalition Honor Guard", 58, Rarity.COMMON, mage.cards.c.CoalitionHonorGuard.class));
        cards.add(new SetCardInfo("Coat of Arms", 1561, Rarity.RARE, mage.cards.c.CoatOfArms.class));
        cards.add(new SetCardInfo("Coat with Venom", 599, Rarity.COMMON, mage.cards.c.CoatWithVenom.class));
        cards.add(new SetCardInfo("Cobblebrute", 890, Rarity.COMMON, mage.cards.c.Cobblebrute.class));
        cards.add(new SetCardInfo("Coiling Oracle", 1409, Rarity.COMMON, mage.cards.c.CoilingOracle.class));
        cards.add(new SetCardInfo("Coldsteel Heart", 1562, Rarity.UNCOMMON, mage.cards.c.ColdsteelHeart.class));
        cards.add(new SetCardInfo("Collar the Culprit", 59, Rarity.COMMON, mage.cards.c.CollarTheCulprit.class));
        cards.add(new SetCardInfo("Collective Brutality", 600, Rarity.RARE, mage.cards.c.CollectiveBrutality.class));
        cards.add(new SetCardInfo("Colossal Dreadmaw", 1164, Rarity.COMMON, mage.cards.c.ColossalDreadmaw.class));
        cards.add(new SetCardInfo("Combo Attack", 1165, Rarity.COMMON, mage.cards.c.ComboAttack.class));
        cards.add(new SetCardInfo("Commit // Memory", 1537, Rarity.RARE, mage.cards.c.CommitMemory.class));
        cards.add(new SetCardInfo("Commune with Nature", 1166, Rarity.COMMON, mage.cards.c.CommuneWithNature.class));
        cards.add(new SetCardInfo("Commune with the Gods", 1167, Rarity.COMMON, mage.cards.c.CommuneWithTheGods.class));
        cards.add(new SetCardInfo("Compelling Argument", 326, Rarity.COMMON, mage.cards.c.CompellingArgument.class));
        cards.add(new SetCardInfo("Concentrate", 327, Rarity.UNCOMMON, mage.cards.c.Concentrate.class));
        cards.add(new SetCardInfo("Condescend", 328, Rarity.UNCOMMON, mage.cards.c.Condescend.class));
        cards.add(new SetCardInfo("Congregate", 60, Rarity.UNCOMMON, mage.cards.c.Congregate.class));
        cards.add(new SetCardInfo("Conifer Strider", 1168, Rarity.COMMON, mage.cards.c.ConiferStrider.class));
        cards.add(new SetCardInfo("Consulate Dreadnought", 1563, Rarity.UNCOMMON, mage.cards.c.ConsulateDreadnought.class));
        cards.add(new SetCardInfo("Contagion Clasp", 1564, Rarity.UNCOMMON, mage.cards.c.ContagionClasp.class));
        cards.add(new SetCardInfo("Containment Membrane", 329, Rarity.COMMON, mage.cards.c.ContainmentMembrane.class));
        cards.add(new SetCardInfo("Contingency Plan", 330, Rarity.COMMON, mage.cards.c.ContingencyPlan.class));
        cards.add(new SetCardInfo("Contraband Kingpin", 1410, Rarity.UNCOMMON, mage.cards.c.ContrabandKingpin.class));
        cards.add(new SetCardInfo("Contradict", 331, Rarity.COMMON, mage.cards.c.Contradict.class));
        cards.add(new SetCardInfo("Conviction", 61, Rarity.COMMON, mage.cards.c.Conviction.class));
        cards.add(new SetCardInfo("Convolute", 332, Rarity.COMMON, mage.cards.c.Convolute.class));
        cards.add(new SetCardInfo("Copper Carapace", 1565, Rarity.COMMON, mage.cards.c.CopperCarapace.class));
        cards.add(new SetCardInfo("Coral Trickster", 334, Rarity.COMMON, mage.cards.c.CoralTrickster.class));
        cards.add(new SetCardInfo("Coralhelm Guide", 333, Rarity.COMMON, mage.cards.c.CoralhelmGuide.class));
        cards.add(new SetCardInfo("Corpsehatch", 601, Rarity.UNCOMMON, mage.cards.c.Corpsehatch.class));
        cards.add(new SetCardInfo("Corpsejack Menace", 1411, Rarity.UNCOMMON, mage.cards.c.CorpsejackMenace.class));
        cards.add(new SetCardInfo("Corrupted Conscience", 335, Rarity.UNCOMMON, mage.cards.c.CorruptedConscience.class));
        cards.add(new SetCardInfo("Cosmotronic Wave", 891, Rarity.COMMON, mage.cards.c.CosmotronicWave.class));
        cards.add(new SetCardInfo("Costly Plunder", 602, Rarity.COMMON, mage.cards.c.CostlyPlunder.class));
        cards.add(new SetCardInfo("Counterspell", 336, Rarity.COMMON, mage.cards.c.Counterspell.class));
        cards.add(new SetCardInfo("Countless Gears Renegade", 62, Rarity.COMMON, mage.cards.c.CountlessGearsRenegade.class));
        cards.add(new SetCardInfo("Courser of Kruphix", 1169, Rarity.RARE, mage.cards.c.CourserOfKruphix.class));
        cards.add(new SetCardInfo("Court Homunculus", 63, Rarity.COMMON, mage.cards.c.CourtHomunculus.class));
        cards.add(new SetCardInfo("Court Hussar", 337, Rarity.COMMON, mage.cards.c.CourtHussar.class));
        cards.add(new SetCardInfo("Court Street Denizen", 64, Rarity.COMMON, mage.cards.c.CourtStreetDenizen.class));
        cards.add(new SetCardInfo("Covenant of Blood", 603, Rarity.COMMON, mage.cards.c.CovenantOfBlood.class));
        cards.add(new SetCardInfo("Coveted Jewel", 1566, Rarity.RARE, mage.cards.c.CovetedJewel.class));
        cards.add(new SetCardInfo("Cower in Fear", 604, Rarity.COMMON, mage.cards.c.CowerInFear.class));
        cards.add(new SetCardInfo("Cragganwick Cremator", 892, Rarity.RARE, mage.cards.c.CragganwickCremator.class));
        cards.add(new SetCardInfo("Crash Through", 893, Rarity.COMMON, mage.cards.c.CrashThrough.class));
        cards.add(new SetCardInfo("Crashing Tide", 338, Rarity.COMMON, mage.cards.c.CrashingTide.class));
        cards.add(new SetCardInfo("Creeping Mold", 1170, Rarity.UNCOMMON, mage.cards.c.CreepingMold.class));
        cards.add(new SetCardInfo("Crenellated Wall", 1567, Rarity.UNCOMMON, mage.cards.c.CrenellatedWall.class));
        cards.add(new SetCardInfo("Crib Swap", 65, Rarity.UNCOMMON, mage.cards.c.CribSwap.class));
        cards.add(new SetCardInfo("Crippling Blight", 605, Rarity.COMMON, mage.cards.c.CripplingBlight.class));
        cards.add(new SetCardInfo("Crop Rotation", 1171, Rarity.COMMON, mage.cards.c.CropRotation.class));
        cards.add(new SetCardInfo("Crosis's Charm", 1412, Rarity.UNCOMMON, mage.cards.c.CrosissCharm.class));
        cards.add(new SetCardInfo("Crossroads Consecrator", 1172, Rarity.COMMON, mage.cards.c.CrossroadsConsecrator.class));
        cards.add(new SetCardInfo("Crow of Dark Tidings", 606, Rarity.COMMON, mage.cards.c.CrowOfDarkTidings.class));
        cards.add(new SetCardInfo("Crowd's Favor", 894, Rarity.COMMON, mage.cards.c.CrowdsFavor.class));
        cards.add(new SetCardInfo("Crown-Hunter Hireling", 895, Rarity.COMMON, mage.cards.c.CrownHunterHireling.class));
        cards.add(new SetCardInfo("Crowned Ceratok", 1174, Rarity.COMMON, mage.cards.c.CrownedCeratok.class));
        cards.add(new SetCardInfo("Crumbling Necropolis", 1661, Rarity.UNCOMMON, mage.cards.c.CrumblingNecropolis.class));
        cards.add(new SetCardInfo("Crush Dissent", 339, Rarity.COMMON, mage.cards.c.CrushDissent.class));
        cards.add(new SetCardInfo("Crushing Canopy", 1175, Rarity.COMMON, mage.cards.c.CrushingCanopy.class));
        cards.add(new SetCardInfo("Crystal Ball", 1568, Rarity.UNCOMMON, mage.cards.c.CrystalBall.class));
        cards.add(new SetCardInfo("Crystal Chimes", 1569, Rarity.UNCOMMON, mage.cards.c.CrystalChimes.class));
        cards.add(new SetCardInfo("Crystal Shard", 1570, Rarity.UNCOMMON, mage.cards.c.CrystalShard.class));
        cards.add(new SetCardInfo("Cultivate", 1176, Rarity.COMMON, mage.cards.c.Cultivate.class));
        cards.add(new SetCardInfo("Cunning Breezedancer", 1413, Rarity.UNCOMMON, mage.cards.c.CunningBreezedancer.class));
        cards.add(new SetCardInfo("Curio Vendor", 341, Rarity.COMMON, mage.cards.c.CurioVendor.class));
        cards.add(new SetCardInfo("Curiosity", 340, Rarity.UNCOMMON, mage.cards.c.Curiosity.class));
        cards.add(new SetCardInfo("Curse of Opulence", 896, Rarity.UNCOMMON, mage.cards.c.CurseOfOpulence.class));
        cards.add(new SetCardInfo("Curse of the Nightly Hunt", 897, Rarity.UNCOMMON, mage.cards.c.CurseOfTheNightlyHunt.class));
        cards.add(new SetCardInfo("Cursed Minotaur", 607, Rarity.COMMON, mage.cards.c.CursedMinotaur.class));
        cards.add(new SetCardInfo("Daggerback Basilisk", 1177, Rarity.COMMON, mage.cards.d.DaggerbackBasilisk.class));
        cards.add(new SetCardInfo("Danitha Capashen, Paragon", 66, Rarity.UNCOMMON, mage.cards.d.DanithaCapashenParagon.class));
        cards.add(new SetCardInfo("Daretti, Scrap Savant", 898, Rarity.MYTHIC, mage.cards.d.DarettiScrapSavant.class));
        cards.add(new SetCardInfo("Daring Demolition", 608, Rarity.COMMON, mage.cards.d.DaringDemolition.class));
        cards.add(new SetCardInfo("Daring Skyjek", 67, Rarity.COMMON, mage.cards.d.DaringSkyjek.class));
        cards.add(new SetCardInfo("Dark Dabbling", 610, Rarity.COMMON, mage.cards.d.DarkDabbling.class));
        cards.add(new SetCardInfo("Dark Ritual", 611, Rarity.COMMON, mage.cards.d.DarkRitual.class));
        cards.add(new SetCardInfo("Dark Withering", 612, Rarity.COMMON, mage.cards.d.DarkWithering.class));
        cards.add(new SetCardInfo("Darkblast", 609, Rarity.UNCOMMON, mage.cards.d.Darkblast.class));
        cards.add(new SetCardInfo("Darksteel Citadel", 1662, Rarity.COMMON, mage.cards.d.DarksteelCitadel.class));
        cards.add(new SetCardInfo("Darksteel Garrison", 1571, Rarity.RARE, mage.cards.d.DarksteelGarrison.class));
        cards.add(new SetCardInfo("Darksteel Mutation", 68, Rarity.UNCOMMON, mage.cards.d.DarksteelMutation.class));
        cards.add(new SetCardInfo("Dauntless Cathar", 69, Rarity.COMMON, mage.cards.d.DauntlessCathar.class));
        cards.add(new SetCardInfo("Dauthi Mindripper", 613, Rarity.UNCOMMON, mage.cards.d.DauthiMindripper.class));
        cards.add(new SetCardInfo("Dawn's Reflection", 1178, Rarity.COMMON, mage.cards.d.DawnsReflection.class));
        cards.add(new SetCardInfo("Dawnglare Invoker", 70, Rarity.COMMON, mage.cards.d.DawnglareInvoker.class));
        cards.add(new SetCardInfo("Daze", 342, Rarity.COMMON, mage.cards.d.Daze.class));
        cards.add(new SetCardInfo("Dazzling Lights", 343, Rarity.COMMON, mage.cards.d.DazzlingLights.class));
        cards.add(new SetCardInfo("Dead Reveler", 617, Rarity.COMMON, mage.cards.d.DeadReveler.class));
        cards.add(new SetCardInfo("Deadbridge Shaman", 614, Rarity.COMMON, mage.cards.d.DeadbridgeShaman.class));
        cards.add(new SetCardInfo("Deadeye Tormentor", 615, Rarity.COMMON, mage.cards.d.DeadeyeTormentor.class));
        cards.add(new SetCardInfo("Deadly Tempest", 616, Rarity.RARE, mage.cards.d.DeadlyTempest.class));
        cards.add(new SetCardInfo("Death Denied", 618, Rarity.COMMON, mage.cards.d.DeathDenied.class));
        cards.add(new SetCardInfo("Death by Dragons", 899, Rarity.UNCOMMON, mage.cards.d.DeathByDragons.class));
        cards.add(new SetCardInfo("Death-Hood Cobra", 1179, Rarity.COMMON, mage.cards.d.DeathHoodCobra.class));
        cards.add(new SetCardInfo("Deathreap Ritual", 1414, Rarity.UNCOMMON, mage.cards.d.DeathreapRitual.class));
        cards.add(new SetCardInfo("Debtors' Knell", 1519, Rarity.RARE, mage.cards.d.DebtorsKnell.class));
        cards.add(new SetCardInfo("Decision Paralysis", 344, Rarity.COMMON, mage.cards.d.DecisionParalysis.class));
        cards.add(new SetCardInfo("Decommission", 71, Rarity.COMMON, mage.cards.d.Decommission.class));
        cards.add(new SetCardInfo("Decree of Justice", 72, Rarity.RARE, mage.cards.d.DecreeOfJustice.class));
        cards.add(new SetCardInfo("Deep Analysis", 345, Rarity.COMMON, mage.cards.d.DeepAnalysis.class));
        cards.add(new SetCardInfo("Deep Freeze", 346, Rarity.COMMON, mage.cards.d.DeepFreeze.class));
        cards.add(new SetCardInfo("Deepglow Skate", 347, Rarity.RARE, mage.cards.d.DeepglowSkate.class));
        cards.add(new SetCardInfo("Defeat", 619, Rarity.COMMON, mage.cards.d.Defeat.class));
        cards.add(new SetCardInfo("Defense of the Heart", 1180, Rarity.RARE, mage.cards.d.DefenseOfTheHeart.class));
        cards.add(new SetCardInfo("Defiant Ogre", 900, Rarity.COMMON, mage.cards.d.DefiantOgre.class));
        cards.add(new SetCardInfo("Defiant Strike", 73, Rarity.COMMON, mage.cards.d.DefiantStrike.class));
        cards.add(new SetCardInfo("Demolish", 901, Rarity.COMMON, mage.cards.d.Demolish.class));
        cards.add(new SetCardInfo("Demon's Grasp", 622, Rarity.COMMON, mage.cards.d.DemonsGrasp.class));
        cards.add(new SetCardInfo("Demonic Tutor", 620, Rarity.UNCOMMON, mage.cards.d.DemonicTutor.class));
        cards.add(new SetCardInfo("Demonic Vigor", 621, Rarity.COMMON, mage.cards.d.DemonicVigor.class));
        cards.add(new SetCardInfo("Deny Reality", 1415, Rarity.COMMON, mage.cards.d.DenyReality.class));
        cards.add(new SetCardInfo("Desert Cerodon", 902, Rarity.COMMON, mage.cards.d.DesertCerodon.class));
        cards.add(new SetCardInfo("Desert Twister", 1181, Rarity.UNCOMMON, mage.cards.d.DesertTwister.class));
        cards.add(new SetCardInfo("Desolation Twin", 4, Rarity.RARE, mage.cards.d.DesolationTwin.class));
        cards.add(new SetCardInfo("Desperate Castaways", 623, Rarity.COMMON, mage.cards.d.DesperateCastaways.class));
        cards.add(new SetCardInfo("Desperate Ravings", 903, Rarity.UNCOMMON, mage.cards.d.DesperateRavings.class));
        cards.add(new SetCardInfo("Desperate Sentry", 74, Rarity.COMMON, mage.cards.d.DesperateSentry.class));
        cards.add(new SetCardInfo("Destructive Tampering", 904, Rarity.COMMON, mage.cards.d.DestructiveTampering.class));
        cards.add(new SetCardInfo("Destructor Dragon", 1182, Rarity.UNCOMMON, mage.cards.d.DestructorDragon.class));
        cards.add(new SetCardInfo("Devilthorn Fox", 75, Rarity.COMMON, mage.cards.d.DevilthornFox.class));
        cards.add(new SetCardInfo("Diabolic Edict", 624, Rarity.COMMON, mage.cards.d.DiabolicEdict.class));
        cards.add(new SetCardInfo("Diamond Mare", 1572, Rarity.UNCOMMON, mage.cards.d.DiamondMare.class));
        cards.add(new SetCardInfo("Dictate of Erebos", 625, Rarity.RARE, mage.cards.d.DictateOfErebos.class));
        cards.add(new SetCardInfo("Dictate of Heliod", 76, Rarity.RARE, mage.cards.d.DictateOfHeliod.class));
        cards.add(new SetCardInfo("Die Young", 626, Rarity.COMMON, mage.cards.d.DieYoung.class));
        cards.add(new SetCardInfo("Diminish", 348, Rarity.COMMON, mage.cards.d.Diminish.class));
        cards.add(new SetCardInfo("Dinosaur Hunter", 627, Rarity.COMMON, mage.cards.d.DinosaurHunter.class));
        cards.add(new SetCardInfo("Direct Current", 905, Rarity.COMMON, mage.cards.d.DirectCurrent.class));
        cards.add(new SetCardInfo("Dirge of Dread", 628, Rarity.COMMON, mage.cards.d.DirgeOfDread.class));
        cards.add(new SetCardInfo("Dirgur Nemesis", 349, Rarity.COMMON, mage.cards.d.DirgurNemesis.class));
        cards.add(new SetCardInfo("Disenchant", 77, Rarity.COMMON, mage.cards.d.Disenchant.class));
        cards.add(new SetCardInfo("Dismal Backwater", 1663, Rarity.COMMON, mage.cards.d.DismalBackwater.class));
        cards.add(new SetCardInfo("Dismantling Blow", 78, Rarity.UNCOMMON, mage.cards.d.DismantlingBlow.class));
        cards.add(new SetCardInfo("Dismember", 629, Rarity.UNCOMMON, mage.cards.d.Dismember.class));
        cards.add(new SetCardInfo("Disowned Ancestor", 630, Rarity.COMMON, mage.cards.d.DisownedAncestor.class));
        cards.add(new SetCardInfo("Dispel", 350, Rarity.COMMON, mage.cards.d.Dispel.class));
        cards.add(new SetCardInfo("Displace", 351, Rarity.COMMON, mage.cards.d.Displace.class));
        cards.add(new SetCardInfo("Disposal Mummy", 79, Rarity.COMMON, mage.cards.d.DisposalMummy.class));
        cards.add(new SetCardInfo("Dissenter's Deliverance", 1183, Rarity.COMMON, mage.cards.d.DissentersDeliverance.class));
        cards.add(new SetCardInfo("Distemper of the Blood", 906, Rarity.COMMON, mage.cards.d.DistemperOfTheBlood.class));
        cards.add(new SetCardInfo("Distortion Strike", 352, Rarity.UNCOMMON, mage.cards.d.DistortionStrike.class));
        cards.add(new SetCardInfo("Divination", 353, Rarity.COMMON, mage.cards.d.Divination.class));
        cards.add(new SetCardInfo("Divine Favor", 80, Rarity.COMMON, mage.cards.d.DivineFavor.class));
        cards.add(new SetCardInfo("Djeru's Renunciation", 81, Rarity.COMMON, mage.cards.d.DjerusRenunciation.class));
        cards.add(new SetCardInfo("Djeru's Resolve", 82, Rarity.COMMON, mage.cards.d.DjerusResolve.class));
        cards.add(new SetCardInfo("Djinn of Wishes", 354, Rarity.RARE, mage.cards.d.DjinnOfWishes.class));
        cards.add(new SetCardInfo("Dolmen Gate", 1573, Rarity.RARE, mage.cards.d.DolmenGate.class));
        cards.add(new SetCardInfo("Domesticated Hydra", 1184, Rarity.UNCOMMON, mage.cards.d.DomesticatedHydra.class));
        cards.add(new SetCardInfo("Dominus of Fealty", 1520, Rarity.RARE, mage.cards.d.DominusOfFealty.class));
        cards.add(new SetCardInfo("Doomed Dissenter", 631, Rarity.COMMON, mage.cards.d.DoomedDissenter.class));
        cards.add(new SetCardInfo("Doomed Traveler", 83, Rarity.COMMON, mage.cards.d.DoomedTraveler.class));
        cards.add(new SetCardInfo("Doomgape", 1521, Rarity.RARE, mage.cards.d.Doomgape.class));
        cards.add(new SetCardInfo("Doorkeeper", 355, Rarity.COMMON, mage.cards.d.Doorkeeper.class));
        cards.add(new SetCardInfo("Douse in Gloom", 632, Rarity.COMMON, mage.cards.d.DouseInGloom.class));
        cards.add(new SetCardInfo("Draco", 1574, Rarity.RARE, mage.cards.d.Draco.class));
        cards.add(new SetCardInfo("Draconic Disciple", 1416, Rarity.UNCOMMON, mage.cards.d.DraconicDisciple.class));
        cards.add(new SetCardInfo("Drag Under", 357, Rarity.COMMON, mage.cards.d.DragUnder.class));
        cards.add(new SetCardInfo("Dragon Bell Monk", 84, Rarity.COMMON, mage.cards.d.DragonBellMonk.class));
        cards.add(new SetCardInfo("Dragon Breath", 907, Rarity.UNCOMMON, mage.cards.d.DragonBreath.class));
        cards.add(new SetCardInfo("Dragon Broodmother", 1417, Rarity.MYTHIC, mage.cards.d.DragonBroodmother.class));
        cards.add(new SetCardInfo("Dragon Egg", 908, Rarity.COMMON, mage.cards.d.DragonEgg.class));
        cards.add(new SetCardInfo("Dragon Fodder", 909, Rarity.COMMON, mage.cards.d.DragonFodder.class));
        cards.add(new SetCardInfo("Dragon Mask", 1575, Rarity.UNCOMMON, mage.cards.d.DragonMask.class));
        cards.add(new SetCardInfo("Dragon Whelp", 911, Rarity.UNCOMMON, mage.cards.d.DragonWhelp.class));
        cards.add(new SetCardInfo("Dragon's Eye Savants", 356, Rarity.UNCOMMON, mage.cards.d.DragonsEyeSavants.class));
        cards.add(new SetCardInfo("Dragon's Eye Sentry", 85, Rarity.COMMON, mage.cards.d.DragonsEyeSentry.class));
        cards.add(new SetCardInfo("Dragon's Presence", 86, Rarity.COMMON, mage.cards.d.DragonsPresence.class));
        cards.add(new SetCardInfo("Dragon-Scarred Bear", 1186, Rarity.COMMON, mage.cards.d.DragonScarredBear.class));
        cards.add(new SetCardInfo("Dragonlord Ojutai", 1418, Rarity.MYTHIC, mage.cards.d.DragonlordOjutai.class));
        cards.add(new SetCardInfo("Dragonscale Boon", 1185, Rarity.COMMON, mage.cards.d.DragonscaleBoon.class));
        cards.add(new SetCardInfo("Dragonsoul Knight", 910, Rarity.COMMON, mage.cards.d.DragonsoulKnight.class));
        cards.add(new SetCardInfo("Drana's Emissary", 1419, Rarity.UNCOMMON, mage.cards.d.DranasEmissary.class));
        cards.add(new SetCardInfo("Drana, Kalastria Bloodchief", 633, Rarity.RARE, mage.cards.d.DranaKalastriaBloodchief.class));
        cards.add(new SetCardInfo("Dread Drone", 635, Rarity.COMMON, mage.cards.d.DreadDrone.class));
        cards.add(new SetCardInfo("Dread Return", 636, Rarity.UNCOMMON, mage.cards.d.DreadReturn.class));
        cards.add(new SetCardInfo("Dreadbringer Lampads", 634, Rarity.COMMON, mage.cards.d.DreadbringerLampads.class));
        cards.add(new SetCardInfo("Dreadship Reef", 1664, Rarity.UNCOMMON, mage.cards.d.DreadshipReef.class));
        cards.add(new SetCardInfo("Dreadwaters", 358, Rarity.COMMON, mage.cards.d.Dreadwaters.class));
        cards.add(new SetCardInfo("Dream Cache", 359, Rarity.COMMON, mage.cards.d.DreamCache.class));
        cards.add(new SetCardInfo("Dream Twist", 360, Rarity.COMMON, mage.cards.d.DreamTwist.class));
        cards.add(new SetCardInfo("Dregscape Zombie", 637, Rarity.COMMON, mage.cards.d.DregscapeZombie.class));
        cards.add(new SetCardInfo("Driver of the Dead", 638, Rarity.COMMON, mage.cards.d.DriverOfTheDead.class));
        cards.add(new SetCardInfo("Drudge Sentinel", 639, Rarity.COMMON, mage.cards.d.DrudgeSentinel.class));
        cards.add(new SetCardInfo("Dual Shot", 912, Rarity.COMMON, mage.cards.d.DualShot.class));
        cards.add(new SetCardInfo("Dukhara Scavenger", 640, Rarity.COMMON, mage.cards.d.DukharaScavenger.class));
        cards.add(new SetCardInfo("Dune Beetle", 641, Rarity.COMMON, mage.cards.d.DuneBeetle.class));
        cards.add(new SetCardInfo("Dungrove Elder", 1187, Rarity.RARE, mage.cards.d.DungroveElder.class));
        cards.add(new SetCardInfo("Duress", 642, Rarity.COMMON, mage.cards.d.Duress.class));
        cards.add(new SetCardInfo("Durkwood Baloth", 1188, Rarity.COMMON, mage.cards.d.DurkwoodBaloth.class));
        cards.add(new SetCardInfo("Dusk Charger", 643, Rarity.COMMON, mage.cards.d.DuskCharger.class));
        cards.add(new SetCardInfo("Dusk Legion Zealot", 644, Rarity.COMMON, mage.cards.d.DuskLegionZealot.class));
        cards.add(new SetCardInfo("Dynacharge", 913, Rarity.COMMON, mage.cards.d.Dynacharge.class));
        cards.add(new SetCardInfo("Earth Elemental", 914, Rarity.COMMON, mage.cards.e.EarthElemental.class));
        cards.add(new SetCardInfo("Earthen Arms", 1189, Rarity.COMMON, mage.cards.e.EarthenArms.class));
        cards.add(new SetCardInfo("Eater of Days", 1576, Rarity.RARE, mage.cards.e.EaterOfDays.class));
        cards.add(new SetCardInfo("Eddytrail Hawk", 87, Rarity.COMMON, mage.cards.e.EddytrailHawk.class));
        cards.add(new SetCardInfo("Eel Umbra", 361, Rarity.COMMON, mage.cards.e.EelUmbra.class));
        cards.add(new SetCardInfo("Eldrazi Devastator", 5, Rarity.COMMON, mage.cards.e.EldraziDevastator.class));
        cards.add(new SetCardInfo("Eldrazi Monument", 1577, Rarity.MYTHIC, mage.cards.e.EldraziMonument.class));
        cards.add(new SetCardInfo("Eldritch Evolution", 1190, Rarity.RARE, mage.cards.e.EldritchEvolution.class));
        cards.add(new SetCardInfo("Elemental Uprising", 1191, Rarity.COMMON, mage.cards.e.ElementalUprising.class));
        cards.add(new SetCardInfo("Elephant Guide", 1192, Rarity.COMMON, mage.cards.e.ElephantGuide.class));
        cards.add(new SetCardInfo("Elesh Norn, Grand Cenobite", 88, Rarity.MYTHIC, mage.cards.e.EleshNornGrandCenobite.class));
        cards.add(new SetCardInfo("Elixir of Immortality", 1578, Rarity.UNCOMMON, mage.cards.e.ElixirOfImmortality.class));
        cards.add(new SetCardInfo("Elves of Deep Shadow", 1193, Rarity.COMMON, mage.cards.e.ElvesOfDeepShadow.class));
        cards.add(new SetCardInfo("Elvish Fury", 1194, Rarity.COMMON, mage.cards.e.ElvishFury.class));
        cards.add(new SetCardInfo("Elvish Visionary", 1195, Rarity.COMMON, mage.cards.e.ElvishVisionary.class));
        cards.add(new SetCardInfo("Elvish Warrior", 1196, Rarity.COMMON, mage.cards.e.ElvishWarrior.class));
        cards.add(new SetCardInfo("Ember Weaver", 1197, Rarity.COMMON, mage.cards.e.EmberWeaver.class));
        cards.add(new SetCardInfo("Embodiment of Spring", 362, Rarity.COMMON, mage.cards.e.EmbodimentOfSpring.class));
        cards.add(new SetCardInfo("Emerge Unscathed", 89, Rarity.COMMON, mage.cards.e.EmergeUnscathed.class));
        cards.add(new SetCardInfo("Emmessi Tome", 1579, Rarity.UNCOMMON, mage.cards.e.EmmessiTome.class));
        cards.add(new SetCardInfo("Empyrial Armor", 90, Rarity.COMMON, mage.cards.e.EmpyrialArmor.class));
        cards.add(new SetCardInfo("Emrakul's Hatcher", 915, Rarity.COMMON, mage.cards.e.EmrakulsHatcher.class));
        cards.add(new SetCardInfo("Encampment Keeper", 91, Rarity.COMMON, mage.cards.e.EncampmentKeeper.class));
        cards.add(new SetCardInfo("Enchanted Evening", 1522, Rarity.RARE, mage.cards.e.EnchantedEvening.class));
        cards.add(new SetCardInfo("Encircling Fissure", 92, Rarity.UNCOMMON, mage.cards.e.EncirclingFissure.class));
        cards.add(new SetCardInfo("Enduring Victory", 93, Rarity.COMMON, mage.cards.e.EnduringVictory.class));
        cards.add(new SetCardInfo("Energy Field", 363, Rarity.RARE, mage.cards.e.EnergyField.class));
        cards.add(new SetCardInfo("Engineered Might", 1420, Rarity.UNCOMMON, mage.cards.e.EngineeredMight.class));
        cards.add(new SetCardInfo("Enlightened Ascetic", 94, Rarity.COMMON, mage.cards.e.EnlightenedAscetic.class));
        cards.add(new SetCardInfo("Enlightened Maniac", 364, Rarity.COMMON, mage.cards.e.EnlightenedManiac.class));
        cards.add(new SetCardInfo("Ensoul Artifact", 365, Rarity.UNCOMMON, mage.cards.e.EnsoulArtifact.class));
        cards.add(new SetCardInfo("Enthralling Victor", 916, Rarity.UNCOMMON, mage.cards.e.EnthrallingVictor.class));
        cards.add(new SetCardInfo("Ephemeral Shields", 95, Rarity.COMMON, mage.cards.e.EphemeralShields.class));
        cards.add(new SetCardInfo("Ephemerate", 96, Rarity.COMMON, mage.cards.e.Ephemerate.class));
        cards.add(new SetCardInfo("Epic Confrontation", 1198, Rarity.COMMON, mage.cards.e.EpicConfrontation.class));
        cards.add(new SetCardInfo("Epicure of Blood", 646, Rarity.COMMON, mage.cards.e.EpicureOfBlood.class));
        cards.add(new SetCardInfo("Erg Raiders", 647, Rarity.COMMON, mage.cards.e.ErgRaiders.class));
        cards.add(new SetCardInfo("Errant Ephemeron", 366, Rarity.COMMON, mage.cards.e.ErrantEphemeron.class));
        cards.add(new SetCardInfo("Erratic Explosion", 917, Rarity.COMMON, mage.cards.e.ErraticExplosion.class));
        cards.add(new SetCardInfo("Esper Charm", 1421, Rarity.UNCOMMON, mage.cards.e.EsperCharm.class));
        cards.add(new SetCardInfo("Essence Scatter", 367, Rarity.COMMON, mage.cards.e.EssenceScatter.class));
        cards.add(new SetCardInfo("Essence Warden", 1199, Rarity.COMMON, mage.cards.e.EssenceWarden.class));
        cards.add(new SetCardInfo("Etched Oracle", 1580, Rarity.UNCOMMON, mage.cards.e.EtchedOracle.class));
        cards.add(new SetCardInfo("Eternal Thirst", 648, Rarity.COMMON, mage.cards.e.EternalThirst.class));
        cards.add(new SetCardInfo("Eternal Witness", 1200, Rarity.UNCOMMON, mage.cards.e.EternalWitness.class));
        cards.add(new SetCardInfo("Ethercaste Knight", 1422, Rarity.UNCOMMON, mage.cards.e.EthercasteKnight.class));
        cards.add(new SetCardInfo("Ethereal Ambush", 1423, Rarity.COMMON, mage.cards.e.EtherealAmbush.class));
        cards.add(new SetCardInfo("Everdream", 368, Rarity.UNCOMMON, mage.cards.e.Everdream.class));
        cards.add(new SetCardInfo("Evincar's Justice", 649, Rarity.COMMON, mage.cards.e.EvincarsJustice.class));
        cards.add(new SetCardInfo("Evolving Wilds", 1665, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Evra, Halcyon Witness", 97, Rarity.RARE, mage.cards.e.EvraHalcyonWitness.class));
        cards.add(new SetCardInfo("Excavation Elephant", 98, Rarity.COMMON, mage.cards.e.ExcavationElephant.class));
        cards.add(new SetCardInfo("Exclude", 369, Rarity.UNCOMMON, mage.cards.e.Exclude.class));
        cards.add(new SetCardInfo("Excoriate", 99, Rarity.COMMON, mage.cards.e.Excoriate.class));
        cards.add(new SetCardInfo("Executioner's Capsule", 650, Rarity.COMMON, mage.cards.e.ExecutionersCapsule.class));
        cards.add(new SetCardInfo("Expedite", 918, Rarity.COMMON, mage.cards.e.Expedite.class));
        cards.add(new SetCardInfo("Expedition Raptor", 100, Rarity.COMMON, mage.cards.e.ExpeditionRaptor.class));
        cards.add(new SetCardInfo("Experiment One", 1201, Rarity.UNCOMMON, mage.cards.e.ExperimentOne.class));
        cards.add(new SetCardInfo("Explore", 1202, Rarity.COMMON, mage.cards.e.Explore.class));
        cards.add(new SetCardInfo("Explosive Vegetation", 1203, Rarity.UNCOMMON, mage.cards.e.ExplosiveVegetation.class));
        cards.add(new SetCardInfo("Expose Evil", 101, Rarity.COMMON, mage.cards.e.ExposeEvil.class));
        cards.add(new SetCardInfo("Expropriate", 370, Rarity.MYTHIC, mage.cards.e.Expropriate.class));
        cards.add(new SetCardInfo("Exsanguinate", 651, Rarity.UNCOMMON, mage.cards.e.Exsanguinate.class));
        cards.add(new SetCardInfo("Extract from Darkness", 1424, Rarity.UNCOMMON, mage.cards.e.ExtractFromDarkness.class));
        cards.add(new SetCardInfo("Exultant Skymarcher", 102, Rarity.COMMON, mage.cards.e.ExultantSkymarcher.class));
        cards.add(new SetCardInfo("Eyeblight's Ending", 652, Rarity.COMMON, mage.cards.e.EyeblightsEnding.class));
        cards.add(new SetCardInfo("Eyes in the Skies", 103, Rarity.COMMON, mage.cards.e.EyesInTheSkies.class));
        cards.add(new SetCardInfo("Ezuri's Archers", 1204, Rarity.COMMON, mage.cards.e.EzurisArchers.class));
        cards.add(new SetCardInfo("Fact or Fiction", 371, Rarity.UNCOMMON, mage.cards.f.FactOrFiction.class));
        cards.add(new SetCardInfo("Fade into Antiquity", 1205, Rarity.COMMON, mage.cards.f.FadeIntoAntiquity.class));
        cards.add(new SetCardInfo("Faerie Conclave", 1666, Rarity.UNCOMMON, mage.cards.f.FaerieConclave.class));
        cards.add(new SetCardInfo("Faerie Invaders", 372, Rarity.COMMON, mage.cards.f.FaerieInvaders.class));
        cards.add(new SetCardInfo("Faerie Mechanist", 373, Rarity.COMMON, mage.cards.f.FaerieMechanist.class));
        cards.add(new SetCardInfo("Failed Inspection", 374, Rarity.COMMON, mage.cards.f.FailedInspection.class));
        cards.add(new SetCardInfo("Faith's Fetters", 105, Rarity.COMMON, mage.cards.f.FaithsFetters.class));
        cards.add(new SetCardInfo("Faithbearer Paladin", 104, Rarity.COMMON, mage.cards.f.FaithbearerPaladin.class));
        cards.add(new SetCardInfo("Faithless Looting", 919, Rarity.COMMON, mage.cards.f.FaithlessLooting.class));
        cards.add(new SetCardInfo("Falkenrath Reaver", 920, Rarity.COMMON, mage.cards.f.FalkenrathReaver.class));
        cards.add(new SetCardInfo("Fall of the Hammer", 921, Rarity.COMMON, mage.cards.f.FallOfTheHammer.class));
        cards.add(new SetCardInfo("Fallen Angel", 653, Rarity.UNCOMMON, mage.cards.f.FallenAngel.class));
        cards.add(new SetCardInfo("Farbog Revenant", 654, Rarity.COMMON, mage.cards.f.FarbogRevenant.class));
        cards.add(new SetCardInfo("Farmstead Gleaner", 1581, Rarity.UNCOMMON, mage.cards.f.FarmsteadGleaner.class));
        cards.add(new SetCardInfo("Farseek", 1206, Rarity.COMMON, mage.cards.f.Farseek.class));
        cards.add(new SetCardInfo("Fascination", 375, Rarity.UNCOMMON, mage.cards.f.Fascination.class));
        cards.add(new SetCardInfo("Fatal Push", 655, Rarity.UNCOMMON, mage.cards.f.FatalPush.class));
        cards.add(new SetCardInfo("Fathom Seer", 376, Rarity.COMMON, mage.cards.f.FathomSeer.class));
        cards.add(new SetCardInfo("Fblthp, the Lost", 377, Rarity.RARE, mage.cards.f.FblthpTheLost.class));
        cards.add(new SetCardInfo("Feat of Resistance", 106, Rarity.COMMON, mage.cards.f.FeatOfResistance.class));
        cards.add(new SetCardInfo("Feed the Clan", 1207, Rarity.COMMON, mage.cards.f.FeedTheClan.class));
        cards.add(new SetCardInfo("Felidar Guardian", 107, Rarity.UNCOMMON, mage.cards.f.FelidarGuardian.class));
        cards.add(new SetCardInfo("Felidar Sovereign", 108, Rarity.RARE, mage.cards.f.FelidarSovereign.class));
        cards.add(new SetCardInfo("Felidar Umbra", 109, Rarity.UNCOMMON, mage.cards.f.FelidarUmbra.class));
        cards.add(new SetCardInfo("Fen Hauler", 656, Rarity.COMMON, mage.cards.f.FenHauler.class));
        cards.add(new SetCardInfo("Fencing Ace", 110, Rarity.COMMON, mage.cards.f.FencingAce.class));
        cards.add(new SetCardInfo("Feral Abomination", 657, Rarity.COMMON, mage.cards.f.FeralAbomination.class));
        cards.add(new SetCardInfo("Feral Krushok", 1208, Rarity.COMMON, mage.cards.f.FeralKrushok.class));
        cards.add(new SetCardInfo("Feral Prowler", 1209, Rarity.COMMON, mage.cards.f.FeralProwler.class));
        cards.add(new SetCardInfo("Ferocious Zheng", 1210, Rarity.COMMON, mage.cards.f.FerociousZheng.class));
        cards.add(new SetCardInfo("Fertile Ground", 1211, Rarity.COMMON, mage.cards.f.FertileGround.class));
        cards.add(new SetCardInfo("Fervent Strike", 922, Rarity.COMMON, mage.cards.f.FerventStrike.class));
        cards.add(new SetCardInfo("Festercreep", 658, Rarity.COMMON, mage.cards.f.Festercreep.class));
        cards.add(new SetCardInfo("Festering Newt", 659, Rarity.COMMON, mage.cards.f.FesteringNewt.class));
        cards.add(new SetCardInfo("Fetid Imp", 660, Rarity.COMMON, mage.cards.f.FetidImp.class));
        cards.add(new SetCardInfo("Field of Ruin", 1667, Rarity.UNCOMMON, mage.cards.f.FieldOfRuin.class));
        cards.add(new SetCardInfo("Fiend Hunter", 111, Rarity.UNCOMMON, mage.cards.f.FiendHunter.class));
        cards.add(new SetCardInfo("Fierce Empath", 1212, Rarity.COMMON, mage.cards.f.FierceEmpath.class));
        cards.add(new SetCardInfo("Fierce Invocation", 923, Rarity.COMMON, mage.cards.f.FierceInvocation.class));
        cards.add(new SetCardInfo("Fiery Hellhound", 924, Rarity.COMMON, mage.cards.f.FieryHellhound.class));
        cards.add(new SetCardInfo("Fiery Temper", 925, Rarity.COMMON, mage.cards.f.FieryTemper.class));
        cards.add(new SetCardInfo("Filigree Familiar", 1582, Rarity.UNCOMMON, mage.cards.f.FiligreeFamiliar.class));
        cards.add(new SetCardInfo("Fill with Fright", 661, Rarity.COMMON, mage.cards.f.FillWithFright.class));
        cards.add(new SetCardInfo("Fire // Ice", 1538, Rarity.COMMON, mage.cards.f.FireIce.class));
        cards.add(new SetCardInfo("Fire Elemental", 929, Rarity.COMMON, mage.cards.f.FireElemental.class));
        cards.add(new SetCardInfo("Fireball", 926, Rarity.UNCOMMON, mage.cards.f.Fireball.class));
        cards.add(new SetCardInfo("Firebolt", 927, Rarity.COMMON, mage.cards.f.Firebolt.class));
        cards.add(new SetCardInfo("Firebrand Archer", 928, Rarity.COMMON, mage.cards.f.FirebrandArcher.class));
        cards.add(new SetCardInfo("Firehoof Cavalry", 112, Rarity.COMMON, mage.cards.f.FirehoofCavalry.class));
        cards.add(new SetCardInfo("Fires of Yavimaya", 1425, Rarity.UNCOMMON, mage.cards.f.FiresOfYavimaya.class));
        cards.add(new SetCardInfo("First-Sphere Gargantua", 662, Rarity.COMMON, mage.cards.f.FirstSphereGargantua.class));
        cards.add(new SetCardInfo("Flame Jab", 930, Rarity.UNCOMMON, mage.cards.f.FlameJab.class));
        cards.add(new SetCardInfo("Flame-Kin Zealot", 1426, Rarity.UNCOMMON, mage.cards.f.FlameKinZealot.class));
        cards.add(new SetCardInfo("Flameshot", 931, Rarity.UNCOMMON, mage.cards.f.Flameshot.class));
        cards.add(new SetCardInfo("Flametongue Kavu", 932, Rarity.UNCOMMON, mage.cards.f.FlametongueKavu.class));
        cards.add(new SetCardInfo("Flamewave Invoker", 933, Rarity.UNCOMMON, mage.cards.f.FlamewaveInvoker.class));
        cards.add(new SetCardInfo("Flashfreeze", 378, Rarity.UNCOMMON, mage.cards.f.Flashfreeze.class));
        cards.add(new SetCardInfo("Flayer Husk", 1583, Rarity.COMMON, mage.cards.f.FlayerHusk.class));
        cards.add(new SetCardInfo("Fledgling Mawcor", 379, Rarity.UNCOMMON, mage.cards.f.FledglingMawcor.class));
        cards.add(new SetCardInfo("Fleeting Distraction", 380, Rarity.COMMON, mage.cards.f.FleetingDistraction.class));
        cards.add(new SetCardInfo("Flesh to Dust", 663, Rarity.COMMON, mage.cards.f.FleshToDust.class));
        cards.add(new SetCardInfo("Fling", 934, Rarity.COMMON, mage.cards.f.Fling.class));
        cards.add(new SetCardInfo("Floodgate", 381, Rarity.UNCOMMON, mage.cards.f.Floodgate.class));
        cards.add(new SetCardInfo("Fog", 1213, Rarity.COMMON, mage.cards.f.Fog.class));
        cards.add(new SetCardInfo("Fog Bank", 382, Rarity.UNCOMMON, mage.cards.f.FogBank.class));
        cards.add(new SetCardInfo("Fogwalker", 383, Rarity.COMMON, mage.cards.f.Fogwalker.class));
        cards.add(new SetCardInfo("Foil", 384, Rarity.COMMON, mage.cards.f.Foil.class));
        cards.add(new SetCardInfo("Font of Mythos", 1584, Rarity.RARE, mage.cards.f.FontOfMythos.class));
        cards.add(new SetCardInfo("Forbidden Alchemy", 385, Rarity.COMMON, mage.cards.f.ForbiddenAlchemy.class));
        cards.add(new SetCardInfo("Forge Devil", 935, Rarity.COMMON, mage.cards.f.ForgeDevil.class));
        cards.add(new SetCardInfo("Forgotten Cave", 1668, Rarity.COMMON, mage.cards.f.ForgottenCave.class));
        cards.add(new SetCardInfo("Formless Nurturing", 1214, Rarity.COMMON, mage.cards.f.FormlessNurturing.class));
        cards.add(new SetCardInfo("Forsake the Worldly", 113, Rarity.COMMON, mage.cards.f.ForsakeTheWorldly.class));
        cards.add(new SetCardInfo("Fortify", 114, Rarity.COMMON, mage.cards.f.Fortify.class));
        cards.add(new SetCardInfo("Foundry Inspector", 1585, Rarity.UNCOMMON, mage.cards.f.FoundryInspector.class));
        cards.add(new SetCardInfo("Foundry Street Denizen", 936, Rarity.COMMON, mage.cards.f.FoundryStreetDenizen.class));
        cards.add(new SetCardInfo("Fountain of Renewal", 1586, Rarity.UNCOMMON, mage.cards.f.FountainOfRenewal.class));
        cards.add(new SetCardInfo("Fragmentize", 115, Rarity.COMMON, mage.cards.f.Fragmentize.class));
        cards.add(new SetCardInfo("Frantic Search", 386, Rarity.COMMON, mage.cards.f.FranticSearch.class));
        cards.add(new SetCardInfo("Frenzied Raptor", 937, Rarity.COMMON, mage.cards.f.FrenziedRaptor.class));
        cards.add(new SetCardInfo("Fretwork Colony", 664, Rarity.UNCOMMON, mage.cards.f.FretworkColony.class));
        cards.add(new SetCardInfo("Frilled Deathspitter", 938, Rarity.COMMON, mage.cards.f.FrilledDeathspitter.class));
        cards.add(new SetCardInfo("Frilled Sea Serpent", 387, Rarity.COMMON, mage.cards.f.FrilledSeaSerpent.class));
        cards.add(new SetCardInfo("Frogmite", 1587, Rarity.COMMON, mage.cards.f.Frogmite.class));
        cards.add(new SetCardInfo("Frontier Bivouac", 1669, Rarity.UNCOMMON, mage.cards.f.FrontierBivouac.class));
        cards.add(new SetCardInfo("Frontier Mastodon", 1215, Rarity.COMMON, mage.cards.f.FrontierMastodon.class));
        cards.add(new SetCardInfo("Frontline Devastator", 939, Rarity.COMMON, mage.cards.f.FrontlineDevastator.class));
        cards.add(new SetCardInfo("Frontline Rebel", 940, Rarity.COMMON, mage.cards.f.FrontlineRebel.class));
        cards.add(new SetCardInfo("Frost Lynx", 388, Rarity.COMMON, mage.cards.f.FrostLynx.class));
        cards.add(new SetCardInfo("Fungal Infection", 665, Rarity.COMMON, mage.cards.f.FungalInfection.class));
        cards.add(new SetCardInfo("Furnace Whelp", 941, Rarity.COMMON, mage.cards.f.FurnaceWhelp.class));
        cards.add(new SetCardInfo("Fury Charm", 942, Rarity.COMMON, mage.cards.f.FuryCharm.class));
        cards.add(new SetCardInfo("Fusion Elemental", 1427, Rarity.UNCOMMON, mage.cards.f.FusionElemental.class));
        cards.add(new SetCardInfo("Gaea's Blessing", 1216, Rarity.UNCOMMON, mage.cards.g.GaeasBlessing.class));
        cards.add(new SetCardInfo("Gaea's Protector", 1217, Rarity.COMMON, mage.cards.g.GaeasProtector.class));
        cards.add(new SetCardInfo("Galvanic Blast", 943, Rarity.COMMON, mage.cards.g.GalvanicBlast.class));
        cards.add(new SetCardInfo("Gaseous Form", 389, Rarity.COMMON, mage.cards.g.GaseousForm.class));
        cards.add(new SetCardInfo("Gateway Plaza", 1670, Rarity.COMMON, mage.cards.g.GatewayPlaza.class));
        cards.add(new SetCardInfo("Geist of the Moors", 116, Rarity.COMMON, mage.cards.g.GeistOfTheMoors.class));
        cards.add(new SetCardInfo("Gelectrode", 1428, Rarity.UNCOMMON, mage.cards.g.Gelectrode.class));
        cards.add(new SetCardInfo("Generator Servant", 944, Rarity.COMMON, mage.cards.g.GeneratorServant.class));
        cards.add(new SetCardInfo("Genju of the Fens", 666, Rarity.UNCOMMON, mage.cards.g.GenjuOfTheFens.class));
        cards.add(new SetCardInfo("Genju of the Spires", 945, Rarity.UNCOMMON, mage.cards.g.GenjuOfTheSpires.class));
        cards.add(new SetCardInfo("Geomancer's Gambit", 946, Rarity.COMMON, mage.cards.g.GeomancersGambit.class));
        cards.add(new SetCardInfo("Ghitu Lavarunner", 947, Rarity.COMMON, mage.cards.g.GhituLavarunner.class));
        cards.add(new SetCardInfo("Ghitu War Cry", 948, Rarity.UNCOMMON, mage.cards.g.GhituWarCry.class));
        cards.add(new SetCardInfo("Ghor-Clan Rampager", 1429, Rarity.UNCOMMON, mage.cards.g.GhorClanRampager.class));
        cards.add(new SetCardInfo("Ghost Quarter", 1671, Rarity.UNCOMMON, mage.cards.g.GhostQuarter.class));
        cards.add(new SetCardInfo("Ghost Ship", 390, Rarity.COMMON, mage.cards.g.GhostShip.class));
        cards.add(new SetCardInfo("Ghostblade Eidolon", 117, Rarity.UNCOMMON, mage.cards.g.GhostbladeEidolon.class));
        cards.add(new SetCardInfo("Ghostly Changeling", 667, Rarity.COMMON, mage.cards.g.GhostlyChangeling.class));
        cards.add(new SetCardInfo("Ghoulcaller's Accomplice", 668, Rarity.COMMON, mage.cards.g.GhoulcallersAccomplice.class));
        cards.add(new SetCardInfo("Giant Growth", 1218, Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Giant Spectacle", 949, Rarity.COMMON, mage.cards.g.GiantSpectacle.class));
        cards.add(new SetCardInfo("Giant Spider", 1219, Rarity.COMMON, mage.cards.g.GiantSpider.class));
        cards.add(new SetCardInfo("Giantbaiting", 1523, Rarity.COMMON, mage.cards.g.Giantbaiting.class));
        cards.add(new SetCardInfo("Gideon Jura", 118, Rarity.MYTHIC, mage.cards.g.GideonJura.class));
        cards.add(new SetCardInfo("Gideon's Lawkeeper", 119, Rarity.COMMON, mage.cards.g.GideonsLawkeeper.class));
        cards.add(new SetCardInfo("Gift of Estates", 120, Rarity.UNCOMMON, mage.cards.g.GiftOfEstates.class));
        cards.add(new SetCardInfo("Gift of Growth", 1220, Rarity.COMMON, mage.cards.g.GiftOfGrowth.class));
        cards.add(new SetCardInfo("Gift of Orzhova", 1524, Rarity.COMMON, mage.cards.g.GiftOfOrzhova.class));
        cards.add(new SetCardInfo("Gift of Paradise", 1221, Rarity.COMMON, mage.cards.g.GiftOfParadise.class));
        cards.add(new SetCardInfo("Gifted Aetherborn", 669, Rarity.UNCOMMON, mage.cards.g.GiftedAetherborn.class));
        cards.add(new SetCardInfo("Gilt-Leaf Palace", 1672, Rarity.RARE, mage.cards.g.GiltLeafPalace.class));
        cards.add(new SetCardInfo("Glacial Crasher", 391, Rarity.COMMON, mage.cards.g.GlacialCrasher.class));
        cards.add(new SetCardInfo("Glade Watcher", 1222, Rarity.COMMON, mage.cards.g.GladeWatcher.class));
        cards.add(new SetCardInfo("Glaring Aegis", 121, Rarity.COMMON, mage.cards.g.GlaringAegis.class));
        cards.add(new SetCardInfo("Gleam of Resistance", 122, Rarity.COMMON, mage.cards.g.GleamOfResistance.class));
        cards.add(new SetCardInfo("Glint", 392, Rarity.COMMON, mage.cards.g.Glint.class));
        cards.add(new SetCardInfo("Glint-Sleeve Artisan", 123, Rarity.COMMON, mage.cards.g.GlintSleeveArtisan.class));
        cards.add(new SetCardInfo("Gnarlid Pack", 1223, Rarity.COMMON, mage.cards.g.GnarlidPack.class));
        cards.add(new SetCardInfo("Go for the Throat", 670, Rarity.UNCOMMON, mage.cards.g.GoForTheThroat.class));
        cards.add(new SetCardInfo("Goblin Assault", 950, Rarity.UNCOMMON, mage.cards.g.GoblinAssault.class));
        cards.add(new SetCardInfo("Goblin Balloon Brigade", 951, Rarity.COMMON, mage.cards.g.GoblinBalloonBrigade.class));
        cards.add(new SetCardInfo("Goblin Bombardment", 952, Rarity.UNCOMMON, mage.cards.g.GoblinBombardment.class));
        cards.add(new SetCardInfo("Goblin Burrows", 1673, Rarity.UNCOMMON, mage.cards.g.GoblinBurrows.class));
        cards.add(new SetCardInfo("Goblin Charbelcher", 1588, Rarity.RARE, mage.cards.g.GoblinCharbelcher.class));
        cards.add(new SetCardInfo("Goblin Deathraiders", 1431, Rarity.COMMON, mage.cards.g.GoblinDeathraiders.class));
        cards.add(new SetCardInfo("Goblin Fireslinger", 953, Rarity.COMMON, mage.cards.g.GoblinFireslinger.class));
        cards.add(new SetCardInfo("Goblin Game", 954, Rarity.RARE, mage.cards.g.GoblinGame.class));
        cards.add(new SetCardInfo("Goblin Locksmith", 955, Rarity.COMMON, mage.cards.g.GoblinLocksmith.class));
        cards.add(new SetCardInfo("Goblin Matron", 956, Rarity.UNCOMMON, mage.cards.g.GoblinMatron.class));
        cards.add(new SetCardInfo("Goblin Motivator", 957, Rarity.COMMON, mage.cards.g.GoblinMotivator.class));
        cards.add(new SetCardInfo("Goblin Oriflamme", 958, Rarity.UNCOMMON, mage.cards.g.GoblinOriflamme.class));
        cards.add(new SetCardInfo("Goblin Piledriver", 959, Rarity.RARE, mage.cards.g.GoblinPiledriver.class));
        cards.add(new SetCardInfo("Goblin Roughrider", 960, Rarity.COMMON, mage.cards.g.GoblinRoughrider.class));
        cards.add(new SetCardInfo("Goblin War Paint", 962, Rarity.COMMON, mage.cards.g.GoblinWarPaint.class));
        cards.add(new SetCardInfo("Goblin Warchief", 961, Rarity.UNCOMMON, mage.cards.g.GoblinWarchief.class));
        cards.add(new SetCardInfo("God-Pharaoh's Faithful", 124, Rarity.COMMON, mage.cards.g.GodPharaohsFaithful.class));
        cards.add(new SetCardInfo("Gods Willing", 125, Rarity.COMMON, mage.cards.g.GodsWilling.class));
        cards.add(new SetCardInfo("Gone Missing", 393, Rarity.COMMON, mage.cards.g.GoneMissing.class));
        cards.add(new SetCardInfo("Gonti, Lord of Luxury", 671, Rarity.RARE, mage.cards.g.GontiLordOfLuxury.class));
        cards.add(new SetCardInfo("Gore Swine", 964, Rarity.COMMON, mage.cards.g.GoreSwine.class));
        cards.add(new SetCardInfo("Gorehorn Minotaurs", 963, Rarity.COMMON, mage.cards.g.GorehornMinotaurs.class));
        cards.add(new SetCardInfo("Granitic Titan", 965, Rarity.COMMON, mage.cards.g.GraniticTitan.class));
        cards.add(new SetCardInfo("Grapeshot", 966, Rarity.COMMON, mage.cards.g.Grapeshot.class));
        cards.add(new SetCardInfo("Grapple with the Past", 1224, Rarity.COMMON, mage.cards.g.GrappleWithThePast.class));
        cards.add(new SetCardInfo("Grasp of Fate", 126, Rarity.RARE, mage.cards.g.GraspOfFate.class));
        cards.add(new SetCardInfo("Grasp of Phantoms", 394, Rarity.COMMON, mage.cards.g.GraspOfPhantoms.class));
        cards.add(new SetCardInfo("Grasp of the Hieromancer", 127, Rarity.COMMON, mage.cards.g.GraspOfTheHieromancer.class));
        cards.add(new SetCardInfo("Grasping Scoundrel", 672, Rarity.COMMON, mage.cards.g.GraspingScoundrel.class));
        cards.add(new SetCardInfo("Grave Titan", 676, Rarity.MYTHIC, mage.cards.g.GraveTitan.class));
        cards.add(new SetCardInfo("Gravecrawler", 673, Rarity.RARE, mage.cards.g.Gravecrawler.class));
        cards.add(new SetCardInfo("Gravedigger", 674, Rarity.COMMON, mage.cards.g.Gravedigger.class));
        cards.add(new SetCardInfo("Gravepurge", 675, Rarity.COMMON, mage.cards.g.Gravepurge.class));
        cards.add(new SetCardInfo("Gravitic Punch", 967, Rarity.COMMON, mage.cards.g.GraviticPunch.class));
        cards.add(new SetCardInfo("Gray Merchant of Asphodel", 677, Rarity.COMMON, mage.cards.g.GrayMerchantOfAsphodel.class));
        cards.add(new SetCardInfo("Graypelt Refuge", 1674, Rarity.UNCOMMON, mage.cards.g.GraypeltRefuge.class));
        cards.add(new SetCardInfo("Grazing Gladehart", 1225, Rarity.COMMON, mage.cards.g.GrazingGladehart.class));
        cards.add(new SetCardInfo("Great Furnace", 1675, Rarity.COMMON, mage.cards.g.GreatFurnace.class));
        cards.add(new SetCardInfo("Great-Horn Krushok", 128, Rarity.COMMON, mage.cards.g.GreatHornKrushok.class));
        cards.add(new SetCardInfo("Greater Basilisk", 1226, Rarity.COMMON, mage.cards.g.GreaterBasilisk.class));
        cards.add(new SetCardInfo("Greater Gargadon", 968, Rarity.RARE, mage.cards.g.GreaterGargadon.class));
        cards.add(new SetCardInfo("Greater Sandwurm", 1227, Rarity.COMMON, mage.cards.g.GreaterSandwurm.class));
        cards.add(new SetCardInfo("Greenbelt Rampager", 1228, Rarity.RARE, mage.cards.g.GreenbeltRampager.class));
        cards.add(new SetCardInfo("Greenwood Sentinel", 1229, Rarity.COMMON, mage.cards.g.GreenwoodSentinel.class));
        cards.add(new SetCardInfo("Grim Affliction", 678, Rarity.COMMON, mage.cards.g.GrimAffliction.class));
        cards.add(new SetCardInfo("Grim Contest", 1432, Rarity.COMMON, mage.cards.g.GrimContest.class));
        cards.add(new SetCardInfo("Grim Discovery", 679, Rarity.COMMON, mage.cards.g.GrimDiscovery.class));
        cards.add(new SetCardInfo("Grixis Slavedriver", 680, Rarity.COMMON, mage.cards.g.GrixisSlavedriver.class));
        cards.add(new SetCardInfo("Grotesque Mutation", 681, Rarity.COMMON, mage.cards.g.GrotesqueMutation.class));
        cards.add(new SetCardInfo("Groundswell", 1230, Rarity.COMMON, mage.cards.g.Groundswell.class));
        cards.add(new SetCardInfo("Gruesome Fate", 682, Rarity.COMMON, mage.cards.g.GruesomeFate.class));
        cards.add(new SetCardInfo("Gruul Signet", 1589, Rarity.COMMON, mage.cards.g.GruulSignet.class));
        cards.add(new SetCardInfo("Guard Gomazoa", 395, Rarity.UNCOMMON, mage.cards.g.GuardGomazoa.class));
        cards.add(new SetCardInfo("Guardian Shield-Bearer", 1231, Rarity.COMMON, mage.cards.g.GuardianShieldBearer.class));
        cards.add(new SetCardInfo("Guardians of Meletis", 1590, Rarity.COMMON, mage.cards.g.GuardiansOfMeletis.class));
        cards.add(new SetCardInfo("Guided Passage", 1433, Rarity.RARE, mage.cards.g.GuidedPassage.class));
        cards.add(new SetCardInfo("Guided Strike", 129, Rarity.COMMON, mage.cards.g.GuidedStrike.class));
        cards.add(new SetCardInfo("Gurmag Angler", 683, Rarity.COMMON, mage.cards.g.GurmagAngler.class));
        cards.add(new SetCardInfo("Gurmag Drowner", 396, Rarity.COMMON, mage.cards.g.GurmagDrowner.class));
        cards.add(new SetCardInfo("Gush", 397, Rarity.COMMON, mage.cards.g.Gush.class));
        cards.add(new SetCardInfo("Gust Walker", 131, Rarity.COMMON, mage.cards.g.GustWalker.class));
        cards.add(new SetCardInfo("Gustcloak Skirmisher", 130, Rarity.UNCOMMON, mage.cards.g.GustcloakSkirmisher.class));
        cards.add(new SetCardInfo("Gut Shot", 969, Rarity.COMMON, mage.cards.g.GutShot.class));
        cards.add(new SetCardInfo("Guttersnipe", 970, Rarity.UNCOMMON, mage.cards.g.Guttersnipe.class));
        cards.add(new SetCardInfo("Gwyllion Hedge-Mage", 1525, Rarity.UNCOMMON, mage.cards.g.GwyllionHedgeMage.class));
        cards.add(new SetCardInfo("Haakon, Stromgald Scourge", 684, Rarity.RARE, mage.cards.h.HaakonStromgaldScourge.class));
        cards.add(new SetCardInfo("Hamlet Captain", 1232, Rarity.UNCOMMON, mage.cards.h.HamletCaptain.class));
        cards.add(new SetCardInfo("Hammer Dropper", 1434, Rarity.COMMON, mage.cards.h.HammerDropper.class));
        cards.add(new SetCardInfo("Hammerhand", 971, Rarity.COMMON, mage.cards.h.Hammerhand.class));
        cards.add(new SetCardInfo("Hanweir Lancer", 972, Rarity.COMMON, mage.cards.h.HanweirLancer.class));
        cards.add(new SetCardInfo("Hardened Berserker", 973, Rarity.COMMON, mage.cards.h.HardenedBerserker.class));
        cards.add(new SetCardInfo("Hardy Veteran", 1233, Rarity.COMMON, mage.cards.h.HardyVeteran.class));
        cards.add(new SetCardInfo("Harmonize", 1234, Rarity.UNCOMMON, mage.cards.h.Harmonize.class));
        cards.add(new SetCardInfo("Harrow", 1235, Rarity.COMMON, mage.cards.h.Harrow.class));
        cards.add(new SetCardInfo("Healer's Hawk", 132, Rarity.COMMON, mage.cards.h.HealersHawk.class));
        cards.add(new SetCardInfo("Healing Grace", 133, Rarity.COMMON, mage.cards.h.HealingGrace.class));
        cards.add(new SetCardInfo("Healing Hands", 134, Rarity.COMMON, mage.cards.h.HealingHands.class));
        cards.add(new SetCardInfo("Heavy Arbalest", 1591, Rarity.UNCOMMON, mage.cards.h.HeavyArbalest.class));
        cards.add(new SetCardInfo("Heavy Infantry", 135, Rarity.COMMON, mage.cards.h.HeavyInfantry.class));
        cards.add(new SetCardInfo("Hedron Crab", 398, Rarity.UNCOMMON, mage.cards.h.HedronCrab.class));
        cards.add(new SetCardInfo("Helm of Awakening", 1592, Rarity.UNCOMMON, mage.cards.h.HelmOfAwakening.class));
        cards.add(new SetCardInfo("Herald's Horn", 1593, Rarity.UNCOMMON, mage.cards.h.HeraldsHorn.class));
        cards.add(new SetCardInfo("Hexplate Golem", 1594, Rarity.COMMON, mage.cards.h.HexplateGolem.class));
        cards.add(new SetCardInfo("Hidden Stockpile", 1435, Rarity.UNCOMMON, mage.cards.h.HiddenStockpile.class));
        cards.add(new SetCardInfo("Hideous End", 685, Rarity.COMMON, mage.cards.h.HideousEnd.class));
        cards.add(new SetCardInfo("Hieroglyphic Illumination", 399, Rarity.COMMON, mage.cards.h.HieroglyphicIllumination.class));
        cards.add(new SetCardInfo("Highspire Mantis", 1436, Rarity.UNCOMMON, mage.cards.h.HighspireMantis.class));
        cards.add(new SetCardInfo("Hightide Hermit", 400, Rarity.COMMON, mage.cards.h.HightideHermit.class));
        cards.add(new SetCardInfo("Hijack", 974, Rarity.COMMON, mage.cards.h.Hijack.class));
        cards.add(new SetCardInfo("Hinterland Drake", 401, Rarity.COMMON, mage.cards.h.HinterlandDrake.class));
        cards.add(new SetCardInfo("Hired Blade", 686, Rarity.COMMON, mage.cards.h.HiredBlade.class));
        cards.add(new SetCardInfo("Hooded Brawler", 1236, Rarity.COMMON, mage.cards.h.HoodedBrawler.class));
        cards.add(new SetCardInfo("Hooting Mandrills", 1237, Rarity.COMMON, mage.cards.h.HootingMandrills.class));
        cards.add(new SetCardInfo("Hornet Nest", 1238, Rarity.RARE, mage.cards.h.HornetNest.class));
        cards.add(new SetCardInfo("Horseshoe Crab", 402, Rarity.COMMON, mage.cards.h.HorseshoeCrab.class));
        cards.add(new SetCardInfo("Hot Soup", 1595, Rarity.UNCOMMON, mage.cards.h.HotSoup.class));
        cards.add(new SetCardInfo("Hound of the Farbogs", 687, Rarity.COMMON, mage.cards.h.HoundOfTheFarbogs.class));
        cards.add(new SetCardInfo("Hulking Devil", 975, Rarity.COMMON, mage.cards.h.HulkingDevil.class));
        cards.add(new SetCardInfo("Humble", 136, Rarity.COMMON, mage.cards.h.Humble.class));
        cards.add(new SetCardInfo("Humongulus", 403, Rarity.COMMON, mage.cards.h.Humongulus.class));
        cards.add(new SetCardInfo("Hunt the Weak", 1240, Rarity.COMMON, mage.cards.h.HuntTheWeak.class));
        cards.add(new SetCardInfo("Hunter of Eyeblights", 688, Rarity.UNCOMMON, mage.cards.h.HunterOfEyeblights.class));
        cards.add(new SetCardInfo("Hunter's Ambush", 1239, Rarity.COMMON, mage.cards.h.HuntersAmbush.class));
        cards.add(new SetCardInfo("Hurricane", 1241, Rarity.RARE, mage.cards.h.Hurricane.class));
        cards.add(new SetCardInfo("Hyena Pack", 976, Rarity.COMMON, mage.cards.h.HyenaPack.class));
        cards.add(new SetCardInfo("Hyena Umbra", 137, Rarity.COMMON, mage.cards.h.HyenaUmbra.class));
        cards.add(new SetCardInfo("Hypnotic Specter", 689, Rarity.RARE, mage.cards.h.HypnoticSpecter.class));
        cards.add(new SetCardInfo("Hypothesizzle", 1437, Rarity.COMMON, mage.cards.h.Hypothesizzle.class));
        cards.add(new SetCardInfo("Icy Manipulator", 1596, Rarity.UNCOMMON, mage.cards.i.IcyManipulator.class));
        cards.add(new SetCardInfo("Ill-Tempered Cyclops", 977, Rarity.COMMON, mage.cards.i.IllTemperedCyclops.class));
        cards.add(new SetCardInfo("Impact Tremors", 978, Rarity.COMMON, mage.cards.i.ImpactTremors.class));
        cards.add(new SetCardInfo("Impending Disaster", 979, Rarity.RARE, mage.cards.i.ImpendingDisaster.class));
        cards.add(new SetCardInfo("Imperious Perfect", 1242, Rarity.UNCOMMON, mage.cards.i.ImperiousPerfect.class));
        cards.add(new SetCardInfo("Implement of Malice", 1597, Rarity.COMMON, mage.cards.i.ImplementOfMalice.class));
        cards.add(new SetCardInfo("Impulse", 404, Rarity.COMMON, mage.cards.i.Impulse.class));
        cards.add(new SetCardInfo("Incorrigible Youths", 980, Rarity.UNCOMMON, mage.cards.i.IncorrigibleYouths.class));
        cards.add(new SetCardInfo("Induce Despair", 690, Rarity.COMMON, mage.cards.i.InduceDespair.class));
        cards.add(new SetCardInfo("Infantry Veteran", 138, Rarity.COMMON, mage.cards.i.InfantryVeteran.class));
        cards.add(new SetCardInfo("Infernal Scarring", 691, Rarity.COMMON, mage.cards.i.InfernalScarring.class));
        cards.add(new SetCardInfo("Inferno Fist", 981, Rarity.COMMON, mage.cards.i.InfernoFist.class));
        cards.add(new SetCardInfo("Inferno Jet", 982, Rarity.UNCOMMON, mage.cards.i.InfernoJet.class));
        cards.add(new SetCardInfo("Infest", 692, Rarity.UNCOMMON, mage.cards.i.Infest.class));
        cards.add(new SetCardInfo("Ingot Chewer", 983, Rarity.COMMON, mage.cards.i.IngotChewer.class));
        cards.add(new SetCardInfo("Inkfathom Divers", 405, Rarity.COMMON, mage.cards.i.InkfathomDivers.class));
        cards.add(new SetCardInfo("Innocent Blood", 693, Rarity.COMMON, mage.cards.i.InnocentBlood.class));
        cards.add(new SetCardInfo("Inquisition of Kozilek", 694, Rarity.UNCOMMON, mage.cards.i.InquisitionOfKozilek.class));
        cards.add(new SetCardInfo("Inquisitor's Ox", 139, Rarity.COMMON, mage.cards.i.InquisitorsOx.class));
        cards.add(new SetCardInfo("Insolent Neonate", 984, Rarity.COMMON, mage.cards.i.InsolentNeonate.class));
        cards.add(new SetCardInfo("Inspired Charge", 140, Rarity.COMMON, mage.cards.i.InspiredCharge.class));
        cards.add(new SetCardInfo("Instill Infection", 695, Rarity.COMMON, mage.cards.i.InstillInfection.class));
        cards.add(new SetCardInfo("Intrusive Packbeast", 141, Rarity.COMMON, mage.cards.i.IntrusivePackbeast.class));
        cards.add(new SetCardInfo("Invigorate", 1243, Rarity.UNCOMMON, mage.cards.i.Invigorate.class));
        cards.add(new SetCardInfo("Invisibility", 406, Rarity.COMMON, mage.cards.i.Invisibility.class));
        cards.add(new SetCardInfo("Iona's Judgment", 142, Rarity.COMMON, mage.cards.i.IonasJudgment.class));
        cards.add(new SetCardInfo("Ior Ruin Expedition", 407, Rarity.COMMON, mage.cards.i.IorRuinExpedition.class));
        cards.add(new SetCardInfo("Iroas's Champion", 1438, Rarity.UNCOMMON, mage.cards.i.IroassChampion.class));
        cards.add(new SetCardInfo("Irontread Crusher", 1598, Rarity.COMMON, mage.cards.i.IrontreadCrusher.class));
        cards.add(new SetCardInfo("Isolation Zone", 143, Rarity.COMMON, mage.cards.i.IsolationZone.class));
        cards.add(new SetCardInfo("Ivy Lane Denizen", 1244, Rarity.COMMON, mage.cards.i.IvyLaneDenizen.class));
        cards.add(new SetCardInfo("Jace's Phantasm", 408, Rarity.COMMON, mage.cards.j.JacesPhantasm.class));
        cards.add(new SetCardInfo("Jackal Pup", 985, Rarity.COMMON, mage.cards.j.JackalPup.class));
        cards.add(new SetCardInfo("Jeering Homunculus", 409, Rarity.COMMON, mage.cards.j.JeeringHomunculus.class));
        cards.add(new SetCardInfo("Jeskai Sage", 410, Rarity.COMMON, mage.cards.j.JeskaiSage.class));
        cards.add(new SetCardInfo("Join Shields", 1439, Rarity.UNCOMMON, mage.cards.j.JoinShields.class));
        cards.add(new SetCardInfo("Jubilant Mascot", 144, Rarity.UNCOMMON, mage.cards.j.JubilantMascot.class));
        cards.add(new SetCardInfo("Juggernaut", 1599, Rarity.UNCOMMON, mage.cards.j.Juggernaut.class));
        cards.add(new SetCardInfo("Jungle Barrier", 1440, Rarity.UNCOMMON, mage.cards.j.JungleBarrier.class));
        cards.add(new SetCardInfo("Jungle Delver", 1245, Rarity.COMMON, mage.cards.j.JungleDelver.class));
        cards.add(new SetCardInfo("Jungle Hollow", 1676, Rarity.COMMON, mage.cards.j.JungleHollow.class));
        cards.add(new SetCardInfo("Jungle Shrine", 1677, Rarity.UNCOMMON, mage.cards.j.JungleShrine.class));
        cards.add(new SetCardInfo("Jungle Wayfinder", 1246, Rarity.COMMON, mage.cards.j.JungleWayfinder.class));
        cards.add(new SetCardInfo("Jushi Apprentice", 411, Rarity.RARE, mage.cards.j.JushiApprentice.class));
        cards.add(new SetCardInfo("Jwar Isle Avenger", 412, Rarity.COMMON, mage.cards.j.JwarIsleAvenger.class));
        cards.add(new SetCardInfo("Kaervek's Torch", 986, Rarity.COMMON, mage.cards.k.KaerveksTorch.class));
        cards.add(new SetCardInfo("Kalastria Nightwatch", 696, Rarity.COMMON, mage.cards.k.KalastriaNightwatch.class));
        cards.add(new SetCardInfo("Kargan Dragonlord", 987, Rarity.MYTHIC, mage.cards.k.KarganDragonlord.class));
        cards.add(new SetCardInfo("Kathari Remnant", 1441, Rarity.UNCOMMON, mage.cards.k.KathariRemnant.class));
        cards.add(new SetCardInfo("Kavu Climber", 1247, Rarity.COMMON, mage.cards.k.KavuClimber.class));
        cards.add(new SetCardInfo("Kavu Primarch", 1248, Rarity.COMMON, mage.cards.k.KavuPrimarch.class));
        cards.add(new SetCardInfo("Kazandu Refuge", 1678, Rarity.UNCOMMON, mage.cards.k.KazanduRefuge.class));
        cards.add(new SetCardInfo("Keldon Halberdier", 988, Rarity.COMMON, mage.cards.k.KeldonHalberdier.class));
        cards.add(new SetCardInfo("Keldon Overseer", 989, Rarity.COMMON, mage.cards.k.KeldonOverseer.class));
        cards.add(new SetCardInfo("Khalni Heart Expedition", 1249, Rarity.COMMON, mage.cards.k.KhalniHeartExpedition.class));
        cards.add(new SetCardInfo("Khenra Scrapper", 990, Rarity.COMMON, mage.cards.k.KhenraScrapper.class));
        cards.add(new SetCardInfo("Kiki-Jiki, Mirror Breaker", 991, Rarity.MYTHIC, mage.cards.k.KikiJikiMirrorBreaker.class));
        cards.add(new SetCardInfo("Kiln Fiend", 992, Rarity.COMMON, mage.cards.k.KilnFiend.class));
        cards.add(new SetCardInfo("Kin-Tree Invocation", 1442, Rarity.UNCOMMON, mage.cards.k.KinTreeInvocation.class));
        cards.add(new SetCardInfo("Kin-Tree Warden", 1250, Rarity.COMMON, mage.cards.k.KinTreeWarden.class));
        cards.add(new SetCardInfo("Kiora's Dambreaker", 413, Rarity.COMMON, mage.cards.k.KiorasDambreaker.class));
        cards.add(new SetCardInfo("Kiora's Follower", 1443, Rarity.UNCOMMON, mage.cards.k.KiorasFollower.class));
        cards.add(new SetCardInfo("Kird Ape", 993, Rarity.COMMON, mage.cards.k.KirdApe.class));
        cards.add(new SetCardInfo("Kiss of the Amesha", 1444, Rarity.UNCOMMON, mage.cards.k.KissOfTheAmesha.class));
        cards.add(new SetCardInfo("Knight of Cliffhaven", 145, Rarity.COMMON, mage.cards.k.KnightOfCliffhaven.class));
        cards.add(new SetCardInfo("Knight of Dawn", 146, Rarity.UNCOMMON, mage.cards.k.KnightOfDawn.class));
        cards.add(new SetCardInfo("Knight of Old Benalia", 147, Rarity.COMMON, mage.cards.k.KnightOfOldBenalia.class));
        cards.add(new SetCardInfo("Knight of Sorrows", 148, Rarity.COMMON, mage.cards.k.KnightOfSorrows.class));
        cards.add(new SetCardInfo("Knight of the Skyward Eye", 149, Rarity.COMMON, mage.cards.k.KnightOfTheSkywardEye.class));
        cards.add(new SetCardInfo("Knight of the Tusk", 150, Rarity.COMMON, mage.cards.k.KnightOfTheTusk.class));
        cards.add(new SetCardInfo("Knollspine Dragon", 994, Rarity.RARE, mage.cards.k.KnollspineDragon.class));
        cards.add(new SetCardInfo("Kolaghan Stormsinger", 995, Rarity.COMMON, mage.cards.k.KolaghanStormsinger.class));
        cards.add(new SetCardInfo("Kolaghan's Command", 1445, Rarity.RARE, mage.cards.k.KolaghansCommand.class));
        cards.add(new SetCardInfo("Kor Bladewhirl", 151, Rarity.UNCOMMON, mage.cards.k.KorBladewhirl.class));
        cards.add(new SetCardInfo("Kor Chant", 152, Rarity.COMMON, mage.cards.k.KorChant.class));
        cards.add(new SetCardInfo("Kor Firewalker", 153, Rarity.UNCOMMON, mage.cards.k.KorFirewalker.class));
        cards.add(new SetCardInfo("Kor Hookmaster", 154, Rarity.COMMON, mage.cards.k.KorHookmaster.class));
        cards.add(new SetCardInfo("Kor Sky Climber", 155, Rarity.COMMON, mage.cards.k.KorSkyClimber.class));
        cards.add(new SetCardInfo("Kor Skyfisher", 156, Rarity.COMMON, mage.cards.k.KorSkyfisher.class));
        cards.add(new SetCardInfo("Kozilek's Predator", 1251, Rarity.COMMON, mage.cards.k.KozileksPredator.class));
        cards.add(new SetCardInfo("Kraul Foragers", 1252, Rarity.COMMON, mage.cards.k.KraulForagers.class));
        cards.add(new SetCardInfo("Kraul Warrior", 1253, Rarity.COMMON, mage.cards.k.KraulWarrior.class));
        cards.add(new SetCardInfo("Krenko's Command", 997, Rarity.COMMON, mage.cards.k.KrenkosCommand.class));
        cards.add(new SetCardInfo("Krenko's Enforcer", 998, Rarity.COMMON, mage.cards.k.KrenkosEnforcer.class));
        cards.add(new SetCardInfo("Krenko, Mob Boss", 996, Rarity.RARE, mage.cards.k.KrenkoMobBoss.class));
        cards.add(new SetCardInfo("Krosan Druid", 1254, Rarity.COMMON, mage.cards.k.KrosanDruid.class));
        cards.add(new SetCardInfo("Krosan Tusker", 1255, Rarity.COMMON, mage.cards.k.KrosanTusker.class));
        cards.add(new SetCardInfo("Krosan Verge", 1679, Rarity.UNCOMMON, mage.cards.k.KrosanVerge.class));
        cards.add(new SetCardInfo("Krumar Bond-Kin", 697, Rarity.COMMON, mage.cards.k.KrumarBondKin.class));
        cards.add(new SetCardInfo("Kruphix, God of Horizons", 1446, Rarity.MYTHIC, mage.cards.k.KruphixGodOfHorizons.class));
        cards.add(new SetCardInfo("Laboratory Brute", 414, Rarity.COMMON, mage.cards.l.LaboratoryBrute.class));
        cards.add(new SetCardInfo("Laboratory Maniac", 415, Rarity.UNCOMMON, mage.cards.l.LaboratoryManiac.class));
        cards.add(new SetCardInfo("Labyrinth Guardian", 416, Rarity.UNCOMMON, mage.cards.l.LabyrinthGuardian.class));
        cards.add(new SetCardInfo("Larger Than Life", 1256, Rarity.COMMON, mage.cards.l.LargerThanLife.class));
        cards.add(new SetCardInfo("Lashknife Barrier", 157, Rarity.UNCOMMON, mage.cards.l.LashknifeBarrier.class));
        cards.add(new SetCardInfo("Lawless Broker", 698, Rarity.COMMON, mage.cards.l.LawlessBroker.class));
        cards.add(new SetCardInfo("Lawmage's Binding", 1447, Rarity.COMMON, mage.cards.l.LawmagesBinding.class));
        cards.add(new SetCardInfo("Lay Claim", 417, Rarity.UNCOMMON, mage.cards.l.LayClaim.class));
        cards.add(new SetCardInfo("Lay of the Land", 1257, Rarity.COMMON, mage.cards.l.LayOfTheLand.class));
        cards.add(new SetCardInfo("Lazotep Behemoth", 699, Rarity.COMMON, mage.cards.l.LazotepBehemoth.class));
        cards.add(new SetCardInfo("Lead by Example", 1258, Rarity.COMMON, mage.cards.l.LeadByExample.class));
        cards.add(new SetCardInfo("Lead the Stampede", 1259, Rarity.COMMON, mage.cards.l.LeadTheStampede.class));
        cards.add(new SetCardInfo("Leapfrog", 418, Rarity.COMMON, mage.cards.l.Leapfrog.class));
        cards.add(new SetCardInfo("Leaping Master", 999, Rarity.COMMON, mage.cards.l.LeapingMaster.class));
        cards.add(new SetCardInfo("Leonin Relic-Warder", 158, Rarity.UNCOMMON, mage.cards.l.LeoninRelicWarder.class));
        cards.add(new SetCardInfo("Leopard-Spotted Jiao", 1000, Rarity.COMMON, mage.cards.l.LeopardSpottedJiao.class));
        cards.add(new SetCardInfo("Lethal Sting", 700, Rarity.COMMON, mage.cards.l.LethalSting.class));
        cards.add(new SetCardInfo("Lieutenants of the Guard", 159, Rarity.COMMON, mage.cards.l.LieutenantsOfTheGuard.class));
        cards.add(new SetCardInfo("Lifespring Druid", 1260, Rarity.COMMON, mage.cards.l.LifespringDruid.class));
        cards.add(new SetCardInfo("Lightform", 160, Rarity.UNCOMMON, mage.cards.l.Lightform.class));
        cards.add(new SetCardInfo("Lightning Bolt", 1001, Rarity.UNCOMMON, mage.cards.l.LightningBolt.class));
        cards.add(new SetCardInfo("Lightning Greaves", 1600, Rarity.UNCOMMON, mage.cards.l.LightningGreaves.class));
        cards.add(new SetCardInfo("Lightning Helix", 1448, Rarity.UNCOMMON, mage.cards.l.LightningHelix.class));
        cards.add(new SetCardInfo("Lightning Javelin", 1002, Rarity.COMMON, mage.cards.l.LightningJavelin.class));
        cards.add(new SetCardInfo("Lightning Shrieker", 1003, Rarity.COMMON, mage.cards.l.LightningShrieker.class));
        cards.add(new SetCardInfo("Lightning Talons", 1004, Rarity.COMMON, mage.cards.l.LightningTalons.class));
        cards.add(new SetCardInfo("Lightwalker", 161, Rarity.COMMON, mage.cards.l.Lightwalker.class));
        cards.add(new SetCardInfo("Lignify", 1261, Rarity.COMMON, mage.cards.l.Lignify.class));
        cards.add(new SetCardInfo("Liliana, Death's Majesty", 701, Rarity.MYTHIC, mage.cards.l.LilianaDeathsMajesty.class));
        cards.add(new SetCardInfo("Lingering Souls", 162, Rarity.UNCOMMON, mage.cards.l.LingeringSouls.class));
        cards.add(new SetCardInfo("Living Death", 702, Rarity.RARE, mage.cards.l.LivingDeath.class));
        cards.add(new SetCardInfo("Llanowar Elves", 1262, Rarity.COMMON, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Llanowar Empath", 1263, Rarity.COMMON, mage.cards.l.LlanowarEmpath.class));
        cards.add(new SetCardInfo("Lone Missionary", 163, Rarity.COMMON, mage.cards.l.LoneMissionary.class));
        cards.add(new SetCardInfo("Lonesome Unicorn", 164, Rarity.COMMON, mage.cards.l.LonesomeUnicorn.class));
        cards.add(new SetCardInfo("Longshot Squad", 1264, Rarity.COMMON, mage.cards.l.LongshotSquad.class));
        cards.add(new SetCardInfo("Looming Altisaur", 165, Rarity.COMMON, mage.cards.l.LoomingAltisaur.class));
        cards.add(new SetCardInfo("Lord of the Accursed", 703, Rarity.UNCOMMON, mage.cards.l.LordOfTheAccursed.class));
        cards.add(new SetCardInfo("Lotus Petal", 1601, Rarity.COMMON, mage.cards.l.LotusPetal.class));
        cards.add(new SetCardInfo("Lotus-Eye Mystics", 166, Rarity.COMMON, mage.cards.l.LotusEyeMystics.class));
        cards.add(new SetCardInfo("Loxodon Partisan", 167, Rarity.COMMON, mage.cards.l.LoxodonPartisan.class));
        cards.add(new SetCardInfo("Loxodon Warhammer", 1602, Rarity.UNCOMMON, mage.cards.l.LoxodonWarhammer.class));
        cards.add(new SetCardInfo("Loyal Sentry", 168, Rarity.COMMON, mage.cards.l.LoyalSentry.class));
        cards.add(new SetCardInfo("Lunarch Mantle", 169, Rarity.COMMON, mage.cards.l.LunarchMantle.class));
        cards.add(new SetCardInfo("Lure", 1265, Rarity.UNCOMMON, mage.cards.l.Lure.class));
        cards.add(new SetCardInfo("Macabre Waltz", 704, Rarity.COMMON, mage.cards.m.MacabreWaltz.class));
        cards.add(new SetCardInfo("Madcap Skills", 1005, Rarity.COMMON, mage.cards.m.MadcapSkills.class));
        cards.add(new SetCardInfo("Maelstrom Archangel", 1449, Rarity.MYTHIC, mage.cards.m.MaelstromArchangel.class));
        cards.add(new SetCardInfo("Magma Spray", 1006, Rarity.COMMON, mage.cards.m.MagmaSpray.class));
        cards.add(new SetCardInfo("Magus of the Moat", 170, Rarity.RARE, mage.cards.m.MagusOfTheMoat.class));
        cards.add(new SetCardInfo("Mahamoti Djinn", 419, Rarity.UNCOMMON, mage.cards.m.MahamotiDjinn.class));
        cards.add(new SetCardInfo("Makindi Sliderunner", 1007, Rarity.COMMON, mage.cards.m.MakindiSliderunner.class));
        cards.add(new SetCardInfo("Man-o'-War", 421, Rarity.COMMON, mage.cards.m.ManOWar.class));
        cards.add(new SetCardInfo("Mana Crypt", 1603, Rarity.MYTHIC, mage.cards.m.ManaCrypt.class));
        cards.add(new SetCardInfo("Mana Leak", 420, Rarity.COMMON, mage.cards.m.ManaLeak.class));
        cards.add(new SetCardInfo("Mana Tithe", 171, Rarity.COMMON, mage.cards.m.ManaTithe.class));
        cards.add(new SetCardInfo("Manamorphose", 1526, Rarity.COMMON, mage.cards.m.Manamorphose.class));
        cards.add(new SetCardInfo("Manglehorn", 1266, Rarity.UNCOMMON, mage.cards.m.Manglehorn.class));
        cards.add(new SetCardInfo("Mantle of Webs", 1267, Rarity.COMMON, mage.cards.m.MantleOfWebs.class));
        cards.add(new SetCardInfo("Map the Wastes", 1268, Rarity.COMMON, mage.cards.m.MapTheWastes.class));
        cards.add(new SetCardInfo("Marauding Boneslasher", 705, Rarity.COMMON, mage.cards.m.MaraudingBoneslasher.class));
        cards.add(new SetCardInfo("March of the Drowned", 706, Rarity.COMMON, mage.cards.m.MarchOfTheDrowned.class));
        cards.add(new SetCardInfo("Mardu Hordechief", 172, Rarity.COMMON, mage.cards.m.MarduHordechief.class));
        cards.add(new SetCardInfo("Mardu Roughrider", 1450, Rarity.UNCOMMON, mage.cards.m.MarduRoughrider.class));
        cards.add(new SetCardInfo("Mardu Warshrieker", 1008, Rarity.COMMON, mage.cards.m.MarduWarshrieker.class));
        cards.add(new SetCardInfo("Mark of Mutiny", 1009, Rarity.UNCOMMON, mage.cards.m.MarkOfMutiny.class));
        cards.add(new SetCardInfo("Mark of the Vampire", 707, Rarity.COMMON, mage.cards.m.MarkOfTheVampire.class));
        cards.add(new SetCardInfo("Marked by Honor", 173, Rarity.COMMON, mage.cards.m.MarkedByHonor.class));
        cards.add(new SetCardInfo("Marsh Hulk", 708, Rarity.COMMON, mage.cards.m.MarshHulk.class));
        cards.add(new SetCardInfo("Martial Glory", 1451, Rarity.COMMON, mage.cards.m.MartialGlory.class));
        cards.add(new SetCardInfo("Martyr's Bond", 174, Rarity.RARE, mage.cards.m.MartyrsBond.class));
        cards.add(new SetCardInfo("Martyr's Cause", 175, Rarity.UNCOMMON, mage.cards.m.MartyrsCause.class));
        cards.add(new SetCardInfo("Mask of Memory", 1604, Rarity.UNCOMMON, mage.cards.m.MaskOfMemory.class));
        cards.add(new SetCardInfo("Master Transmuter", 422, Rarity.RARE, mage.cards.m.MasterTransmuter.class));
        cards.add(new SetCardInfo("Maverick Thopterist", 1452, Rarity.UNCOMMON, mage.cards.m.MaverickThopterist.class));
        cards.add(new SetCardInfo("Maximize Altitude", 423, Rarity.COMMON, mage.cards.m.MaximizeAltitude.class));
        cards.add(new SetCardInfo("Maximize Velocity", 1010, Rarity.COMMON, mage.cards.m.MaximizeVelocity.class));
        cards.add(new SetCardInfo("Meandering Towershell", 1269, Rarity.RARE, mage.cards.m.MeanderingTowershell.class));
        cards.add(new SetCardInfo("Meddling Mage", 1453, Rarity.RARE, mage.cards.m.MeddlingMage.class));
        cards.add(new SetCardInfo("Meditation Puzzle", 176, Rarity.COMMON, mage.cards.m.MeditationPuzzle.class));
        cards.add(new SetCardInfo("Memory Erosion", 424, Rarity.RARE, mage.cards.m.MemoryErosion.class));
        cards.add(new SetCardInfo("Memory Lapse", 425, Rarity.COMMON, mage.cards.m.MemoryLapse.class));
        cards.add(new SetCardInfo("Mephitic Vapors", 709, Rarity.COMMON, mage.cards.m.MephiticVapors.class));
        cards.add(new SetCardInfo("Merciless Resolve", 710, Rarity.COMMON, mage.cards.m.MercilessResolve.class));
        cards.add(new SetCardInfo("Mercurial Geists", 1454, Rarity.UNCOMMON, mage.cards.m.MercurialGeists.class));
        cards.add(new SetCardInfo("Meren of Clan Nel Toth", 1455, Rarity.MYTHIC, mage.cards.m.MerenOfClanNelToth.class));
        cards.add(new SetCardInfo("Merfolk Looter", 426, Rarity.UNCOMMON, mage.cards.m.MerfolkLooter.class));
        cards.add(new SetCardInfo("Messenger Jays", 427, Rarity.COMMON, mage.cards.m.MessengerJays.class));
        cards.add(new SetCardInfo("Metallic Rebuke", 428, Rarity.COMMON, mage.cards.m.MetallicRebuke.class));
        cards.add(new SetCardInfo("Meteorite", 1605, Rarity.UNCOMMON, mage.cards.m.Meteorite.class));
        cards.add(new SetCardInfo("Miasmic Mummy", 711, Rarity.COMMON, mage.cards.m.MiasmicMummy.class));
        cards.add(new SetCardInfo("Midnight Guard", 177, Rarity.COMMON, mage.cards.m.MidnightGuard.class));
        cards.add(new SetCardInfo("Might of the Masses", 1270, Rarity.COMMON, mage.cards.m.MightOfTheMasses.class));
        cards.add(new SetCardInfo("Migratory Route", 1456, Rarity.UNCOMMON, mage.cards.m.MigratoryRoute.class));
        cards.add(new SetCardInfo("Millikin", 1606, Rarity.UNCOMMON, mage.cards.m.Millikin.class));
        cards.add(new SetCardInfo("Millstone", 1607, Rarity.UNCOMMON, mage.cards.m.Millstone.class));
        cards.add(new SetCardInfo("Mimic Vat", 1608, Rarity.RARE, mage.cards.m.MimicVat.class));
        cards.add(new SetCardInfo("Mind Rake", 712, Rarity.COMMON, mage.cards.m.MindRake.class));
        cards.add(new SetCardInfo("Mind Rot", 713, Rarity.COMMON, mage.cards.m.MindRot.class));
        cards.add(new SetCardInfo("Mind Sculpt", 429, Rarity.COMMON, mage.cards.m.MindSculpt.class));
        cards.add(new SetCardInfo("Mind Shatter", 714, Rarity.RARE, mage.cards.m.MindShatter.class));
        cards.add(new SetCardInfo("Mind Spring", 430, Rarity.RARE, mage.cards.m.MindSpring.class));
        cards.add(new SetCardInfo("Mind Stone", 1609, Rarity.COMMON, mage.cards.m.MindStone.class));
        cards.add(new SetCardInfo("Miner's Bane", 1011, Rarity.COMMON, mage.cards.m.MinersBane.class));
        cards.add(new SetCardInfo("Mire's Malice", 715, Rarity.COMMON, mage.cards.m.MiresMalice.class));
        cards.add(new SetCardInfo("Mirran Crusader", 178, Rarity.RARE, mage.cards.m.MirranCrusader.class));
        cards.add(new SetCardInfo("Mirror Entity", 179, Rarity.RARE, mage.cards.m.MirrorEntity.class));
        cards.add(new SetCardInfo("Misdirection", 432, Rarity.RARE, mage.cards.m.Misdirection.class));
        cards.add(new SetCardInfo("Mishra's Bauble", 1610, Rarity.UNCOMMON, mage.cards.m.MishrasBauble.class));
        cards.add(new SetCardInfo("Mishra's Factory", 1680, Rarity.UNCOMMON, mage.cards.m.MishrasFactory.class));
        cards.add(new SetCardInfo("Mist Raven", 434, Rarity.COMMON, mage.cards.m.MistRaven.class));
        cards.add(new SetCardInfo("Mistform Shrieker", 433, Rarity.UNCOMMON, mage.cards.m.MistformShrieker.class));
        cards.add(new SetCardInfo("Mistmeadow Witch", 1527, Rarity.UNCOMMON, mage.cards.m.MistmeadowWitch.class));
        cards.add(new SetCardInfo("Mizzix's Mastery", 1012, Rarity.RARE, mage.cards.m.MizzixsMastery.class));
        cards.add(new SetCardInfo("Mnemonic Wall", 435, Rarity.COMMON, mage.cards.m.MnemonicWall.class));
        cards.add(new SetCardInfo("Mogg Fanatic", 1013, Rarity.UNCOMMON, mage.cards.m.MoggFanatic.class));
        cards.add(new SetCardInfo("Mogg Flunkies", 1014, Rarity.COMMON, mage.cards.m.MoggFlunkies.class));
        cards.add(new SetCardInfo("Mogg War Marshal", 1015, Rarity.COMMON, mage.cards.m.MoggWarMarshal.class));
        cards.add(new SetCardInfo("Molten Rain", 1016, Rarity.UNCOMMON, mage.cards.m.MoltenRain.class));
        cards.add(new SetCardInfo("Moment of Craving", 716, Rarity.COMMON, mage.cards.m.MomentOfCraving.class));
        cards.add(new SetCardInfo("Momentary Blink", 180, Rarity.COMMON, mage.cards.m.MomentaryBlink.class));
        cards.add(new SetCardInfo("Monastery Loremaster", 436, Rarity.COMMON, mage.cards.m.MonasteryLoremaster.class));
        cards.add(new SetCardInfo("Monastery Swiftspear", 1017, Rarity.UNCOMMON, mage.cards.m.MonasterySwiftspear.class));
        cards.add(new SetCardInfo("Moonglove Extract", 1611, Rarity.COMMON, mage.cards.m.MoongloveExtract.class));
        cards.add(new SetCardInfo("Moonlit Strider", 181, Rarity.COMMON, mage.cards.m.MoonlitStrider.class));
        cards.add(new SetCardInfo("Mortal's Ardor", 182, Rarity.COMMON, mage.cards.m.MortalsArdor.class));
        cards.add(new SetCardInfo("Mortarpod", 1612, Rarity.UNCOMMON, mage.cards.m.Mortarpod.class));
        cards.add(new SetCardInfo("Mortify", 1457, Rarity.UNCOMMON, mage.cards.m.Mortify.class));
        cards.add(new SetCardInfo("Mother of Runes", 183, Rarity.UNCOMMON, mage.cards.m.MotherOfRunes.class));
        cards.add(new SetCardInfo("Mulch", 1271, Rarity.COMMON, mage.cards.m.Mulch.class));
        cards.add(new SetCardInfo("Mulldrifter", 437, Rarity.COMMON, mage.cards.m.Mulldrifter.class));
        cards.add(new SetCardInfo("Murder", 717, Rarity.COMMON, mage.cards.m.Murder.class));
        cards.add(new SetCardInfo("Murder of Crows", 438, Rarity.UNCOMMON, mage.cards.m.MurderOfCrows.class));
        cards.add(new SetCardInfo("Murderous Compulsion", 718, Rarity.COMMON, mage.cards.m.MurderousCompulsion.class));
        cards.add(new SetCardInfo("Mutiny", 1018, Rarity.COMMON, mage.cards.m.Mutiny.class));
        cards.add(new SetCardInfo("Mycoloth", 1272, Rarity.RARE, mage.cards.m.Mycoloth.class));
        cards.add(new SetCardInfo("Myr Retriever", 1613, Rarity.UNCOMMON, mage.cards.m.MyrRetriever.class));
        cards.add(new SetCardInfo("Myr Sire", 1614, Rarity.COMMON, mage.cards.m.MyrSire.class));
        cards.add(new SetCardInfo("Mystic Confluence", 440, Rarity.RARE, mage.cards.m.MysticConfluence.class));
        cards.add(new SetCardInfo("Mystic of the Hidden Way", 441, Rarity.COMMON, mage.cards.m.MysticOfTheHiddenWay.class));
        cards.add(new SetCardInfo("Mystical Teachings", 439, Rarity.COMMON, mage.cards.m.MysticalTeachings.class));
        cards.add(new SetCardInfo("Nagging Thoughts", 442, Rarity.COMMON, mage.cards.n.NaggingThoughts.class));
        cards.add(new SetCardInfo("Nameless Inversion", 719, Rarity.COMMON, mage.cards.n.NamelessInversion.class));
        cards.add(new SetCardInfo("Nantuko Husk", 720, Rarity.COMMON, mage.cards.n.NantukoHusk.class));
        cards.add(new SetCardInfo("Natural Connection", 1273, Rarity.COMMON, mage.cards.n.NaturalConnection.class));
        cards.add(new SetCardInfo("Naturalize", 1274, Rarity.COMMON, mage.cards.n.Naturalize.class));
        cards.add(new SetCardInfo("Nature's Claim", 1275, Rarity.COMMON, mage.cards.n.NaturesClaim.class));
        cards.add(new SetCardInfo("Nature's Lore", 1276, Rarity.COMMON, mage.cards.n.NaturesLore.class));
        cards.add(new SetCardInfo("Naya Charm", 1458, Rarity.UNCOMMON, mage.cards.n.NayaCharm.class));
        cards.add(new SetCardInfo("Negate", 443, Rarity.COMMON, mage.cards.n.Negate.class));
        cards.add(new SetCardInfo("Nemesis of Reason", 1459, Rarity.RARE, mage.cards.n.NemesisOfReason.class));
        cards.add(new SetCardInfo("Nest Invader", 1277, Rarity.COMMON, mage.cards.n.NestInvader.class));
        cards.add(new SetCardInfo("Nettle Sentinel", 1278, Rarity.COMMON, mage.cards.n.NettleSentinel.class));
        cards.add(new SetCardInfo("Never Happened", 721, Rarity.COMMON, mage.cards.n.NeverHappened.class));
        cards.add(new SetCardInfo("New Benalia", 1681, Rarity.UNCOMMON, mage.cards.n.NewBenalia.class));
        cards.add(new SetCardInfo("New Horizons", 1279, Rarity.COMMON, mage.cards.n.NewHorizons.class));
        cards.add(new SetCardInfo("Niblis of Dusk", 444, Rarity.COMMON, mage.cards.n.NiblisOfDusk.class));
        cards.add(new SetCardInfo("Night's Whisper", 723, Rarity.COMMON, mage.cards.n.NightsWhisper.class));
        cards.add(new SetCardInfo("Nighthowler", 722, Rarity.RARE, mage.cards.n.Nighthowler.class));
        cards.add(new SetCardInfo("Nimble Mongoose", 1280, Rarity.COMMON, mage.cards.n.NimbleMongoose.class));
        cards.add(new SetCardInfo("Nimble-Blade Khenra", 1019, Rarity.COMMON, mage.cards.n.NimbleBladeKhenra.class));
        cards.add(new SetCardInfo("Nin, the Pain Artist", 1460, Rarity.RARE, mage.cards.n.NinThePainArtist.class));
        cards.add(new SetCardInfo("Nine-Tail White Fox", 445, Rarity.COMMON, mage.cards.n.NineTailWhiteFox.class));
        cards.add(new SetCardInfo("Ninja of the Deep Hours", 446, Rarity.COMMON, mage.cards.n.NinjaOfTheDeepHours.class));
        cards.add(new SetCardInfo("Ninth Bridge Patrol", 184, Rarity.COMMON, mage.cards.n.NinthBridgePatrol.class));
        cards.add(new SetCardInfo("Nirkana Assassin", 724, Rarity.COMMON, mage.cards.n.NirkanaAssassin.class));
        cards.add(new SetCardInfo("Nissa, Voice of Zendikar", 1281, Rarity.MYTHIC, mage.cards.n.NissaVoiceOfZendikar.class));
        cards.add(new SetCardInfo("Noxious Dragon", 725, Rarity.UNCOMMON, mage.cards.n.NoxiousDragon.class));
        cards.add(new SetCardInfo("Nucklavee", 1528, Rarity.UNCOMMON, mage.cards.n.Nucklavee.class));
        cards.add(new SetCardInfo("Nyx-Fleece Ram", 185, Rarity.UNCOMMON, mage.cards.n.NyxFleeceRam.class));
        cards.add(new SetCardInfo("Oakgnarl Warrior", 1282, Rarity.COMMON, mage.cards.o.OakgnarlWarrior.class));
        cards.add(new SetCardInfo("Obelisk Spider", 1461, Rarity.UNCOMMON, mage.cards.o.ObeliskSpider.class));
        cards.add(new SetCardInfo("Ochran Assassin", 1462, Rarity.UNCOMMON, mage.cards.o.OchranAssassin.class));
        cards.add(new SetCardInfo("Odric, Lunarch Marshal", 186, Rarity.RARE, mage.cards.o.OdricLunarchMarshal.class));
        cards.add(new SetCardInfo("Ojutai Interceptor", 447, Rarity.COMMON, mage.cards.o.OjutaiInterceptor.class));
        cards.add(new SetCardInfo("Ojutai's Breath", 448, Rarity.COMMON, mage.cards.o.OjutaisBreath.class));
        cards.add(new SetCardInfo("Okiba-Gang Shinobi", 726, Rarity.COMMON, mage.cards.o.OkibaGangShinobi.class));
        cards.add(new SetCardInfo("Omenspeaker", 449, Rarity.COMMON, mage.cards.o.Omenspeaker.class));
        cards.add(new SetCardInfo("Ondu Champion", 1020, Rarity.COMMON, mage.cards.o.OnduChampion.class));
        cards.add(new SetCardInfo("Ondu Giant", 1283, Rarity.COMMON, mage.cards.o.OnduGiant.class));
        cards.add(new SetCardInfo("Ondu Greathorn", 187, Rarity.COMMON, mage.cards.o.OnduGreathorn.class));
        cards.add(new SetCardInfo("Ondu War Cleric", 188, Rarity.COMMON, mage.cards.o.OnduWarCleric.class));
        cards.add(new SetCardInfo("Opportunity", 450, Rarity.UNCOMMON, mage.cards.o.Opportunity.class));
        cards.add(new SetCardInfo("Opt", 451, Rarity.COMMON, mage.cards.o.Opt.class));
        cards.add(new SetCardInfo("Oracle of Nectars", 1529, Rarity.RARE, mage.cards.o.OracleOfNectars.class));
        cards.add(new SetCardInfo("Oran-Rief Invoker", 1284, Rarity.COMMON, mage.cards.o.OranRiefInvoker.class));
        cards.add(new SetCardInfo("Orcish Cannonade", 1021, Rarity.COMMON, mage.cards.o.OrcishCannonade.class));
        cards.add(new SetCardInfo("Orcish Oriflamme", 1022, Rarity.COMMON, mage.cards.o.OrcishOriflamme.class));
        cards.add(new SetCardInfo("Oreskos Swiftclaw", 189, Rarity.COMMON, mage.cards.o.OreskosSwiftclaw.class));
        cards.add(new SetCardInfo("Ornithopter", 1615, Rarity.COMMON, mage.cards.o.Ornithopter.class));
        cards.add(new SetCardInfo("Orzhov Basilica", 1682, Rarity.COMMON, mage.cards.o.OrzhovBasilica.class));
        cards.add(new SetCardInfo("Oust", 190, Rarity.UNCOMMON, mage.cards.o.Oust.class));
        cards.add(new SetCardInfo("Outnumber", 1023, Rarity.COMMON, mage.cards.o.Outnumber.class));
        cards.add(new SetCardInfo("Overgrown Armasaur", 1285, Rarity.COMMON, mage.cards.o.OvergrownArmasaur.class));
        cards.add(new SetCardInfo("Overgrown Battlement", 1286, Rarity.UNCOMMON, mage.cards.o.OvergrownBattlement.class));
        cards.add(new SetCardInfo("Overrun", 1287, Rarity.UNCOMMON, mage.cards.o.Overrun.class));
        cards.add(new SetCardInfo("Pacifism", 191, Rarity.COMMON, mage.cards.p.Pacifism.class));
        cards.add(new SetCardInfo("Pack's Favor", 1288, Rarity.COMMON, mage.cards.p.PacksFavor.class));
        cards.add(new SetCardInfo("Painful Lesson", 727, Rarity.COMMON, mage.cards.p.PainfulLesson.class));
        cards.add(new SetCardInfo("Palace Jailer", 192, Rarity.UNCOMMON, mage.cards.p.PalaceJailer.class));
        cards.add(new SetCardInfo("Palace Sentinels", 193, Rarity.COMMON, mage.cards.p.PalaceSentinels.class));
        cards.add(new SetCardInfo("Paladin of the Bloodstained", 194, Rarity.COMMON, mage.cards.p.PaladinOfTheBloodstained.class));
        cards.add(new SetCardInfo("Palladium Myr", 1616, Rarity.UNCOMMON, mage.cards.p.PalladiumMyr.class));
        cards.add(new SetCardInfo("Path of Peace", 195, Rarity.COMMON, mage.cards.p.PathOfPeace.class));
        cards.add(new SetCardInfo("Path to Exile", 196, Rarity.UNCOMMON, mage.cards.p.PathToExile.class));
        cards.add(new SetCardInfo("Pathrazer of Ulamog", 6, Rarity.UNCOMMON, mage.cards.p.PathrazerOfUlamog.class));
        cards.add(new SetCardInfo("Peace Strider", 1617, Rarity.COMMON, mage.cards.p.PeaceStrider.class));
        cards.add(new SetCardInfo("Peace of Mind", 197, Rarity.UNCOMMON, mage.cards.p.PeaceOfMind.class));
        cards.add(new SetCardInfo("Peel from Reality", 452, Rarity.COMMON, mage.cards.p.PeelFromReality.class));
        cards.add(new SetCardInfo("Peema Outrider", 1289, Rarity.COMMON, mage.cards.p.PeemaOutrider.class));
        cards.add(new SetCardInfo("Pegasus Courser", 198, Rarity.COMMON, mage.cards.p.PegasusCourser.class));
        cards.add(new SetCardInfo("Pelakka Wurm", 1290, Rarity.UNCOMMON, mage.cards.p.PelakkaWurm.class));
        cards.add(new SetCardInfo("Pentarch Ward", 199, Rarity.COMMON, mage.cards.p.PentarchWard.class));
        cards.add(new SetCardInfo("Penumbra Spider", 1291, Rarity.COMMON, mage.cards.p.PenumbraSpider.class));
        cards.add(new SetCardInfo("Perilous Myr", 1618, Rarity.UNCOMMON, mage.cards.p.PerilousMyr.class));
        cards.add(new SetCardInfo("Perish", 728, Rarity.UNCOMMON, mage.cards.p.Perish.class));
        cards.add(new SetCardInfo("Pestilence", 729, Rarity.COMMON, mage.cards.p.Pestilence.class));
        cards.add(new SetCardInfo("Phantasmal Bear", 453, Rarity.COMMON, mage.cards.p.PhantasmalBear.class));
        cards.add(new SetCardInfo("Phantasmal Dragon", 454, Rarity.UNCOMMON, mage.cards.p.PhantasmalDragon.class));
        cards.add(new SetCardInfo("Phantom Centaur", 1292, Rarity.UNCOMMON, mage.cards.p.PhantomCentaur.class));
        cards.add(new SetCardInfo("Phyrexian Arena", 730, Rarity.RARE, mage.cards.p.PhyrexianArena.class));
        cards.add(new SetCardInfo("Phyrexian Ingester", 455, Rarity.UNCOMMON, mage.cards.p.PhyrexianIngester.class));
        cards.add(new SetCardInfo("Phyrexian Metamorph", 456, Rarity.RARE, mage.cards.p.PhyrexianMetamorph.class));
        cards.add(new SetCardInfo("Phyrexian Plaguelord", 731, Rarity.RARE, mage.cards.p.PhyrexianPlaguelord.class));
        cards.add(new SetCardInfo("Phyrexian Rager", 732, Rarity.COMMON, mage.cards.p.PhyrexianRager.class));
        cards.add(new SetCardInfo("Phyrexian Reclamation", 733, Rarity.UNCOMMON, mage.cards.p.PhyrexianReclamation.class));
        cards.add(new SetCardInfo("Phyrexian Soulgorger", 1619, Rarity.RARE, mage.cards.p.PhyrexianSoulgorger.class));
        cards.add(new SetCardInfo("Pierce the Sky", 1293, Rarity.COMMON, mage.cards.p.PierceTheSky.class));
        cards.add(new SetCardInfo("Pilgrim's Eye", 1620, Rarity.UNCOMMON, mage.cards.p.PilgrimsEye.class));
        cards.add(new SetCardInfo("Pillage", 1024, Rarity.COMMON, mage.cards.p.Pillage.class));
        cards.add(new SetCardInfo("Pillory of the Sleepless", 1463, Rarity.UNCOMMON, mage.cards.p.PilloryOfTheSleepless.class));
        cards.add(new SetCardInfo("Pinion Feast", 1294, Rarity.COMMON, mage.cards.p.PinionFeast.class));
        cards.add(new SetCardInfo("Pit Keeper", 734, Rarity.COMMON, mage.cards.p.PitKeeper.class));
        cards.add(new SetCardInfo("Pitfall Trap", 200, Rarity.COMMON, mage.cards.p.PitfallTrap.class));
        cards.add(new SetCardInfo("Plague Wight", 737, Rarity.COMMON, mage.cards.p.PlagueWight.class));
        cards.add(new SetCardInfo("Plaguecrafter", 735, Rarity.UNCOMMON, mage.cards.p.Plaguecrafter.class));
        cards.add(new SetCardInfo("Plagued Rusalka", 736, Rarity.COMMON, mage.cards.p.PlaguedRusalka.class));
        cards.add(new SetCardInfo("Plaxcaster Frogling", 1464, Rarity.UNCOMMON, mage.cards.p.PlaxcasterFrogling.class));
        cards.add(new SetCardInfo("Plummet", 1295, Rarity.COMMON, mage.cards.p.Plummet.class));
        cards.add(new SetCardInfo("Pollenbright Wings", 1465, Rarity.UNCOMMON, mage.cards.p.PollenbrightWings.class));
        cards.add(new SetCardInfo("Pondering Mage", 457, Rarity.COMMON, mage.cards.p.PonderingMage.class));
        cards.add(new SetCardInfo("Portent", 458, Rarity.COMMON, mage.cards.p.Portent.class));
        cards.add(new SetCardInfo("Pouncing Cheetah", 1296, Rarity.COMMON, mage.cards.p.PouncingCheetah.class));
        cards.add(new SetCardInfo("Prakhata Club Security", 738, Rarity.COMMON, mage.cards.p.PrakhataClubSecurity.class));
        cards.add(new SetCardInfo("Precursor Golem", 1621, Rarity.RARE, mage.cards.p.PrecursorGolem.class));
        cards.add(new SetCardInfo("Predict", 459, Rarity.UNCOMMON, mage.cards.p.Predict.class));
        cards.add(new SetCardInfo("Preordain", 460, Rarity.COMMON, mage.cards.p.Preordain.class));
        cards.add(new SetCardInfo("Pressure Point", 201, Rarity.COMMON, mage.cards.p.PressurePoint.class));
        cards.add(new SetCardInfo("Prey Upon", 1298, Rarity.COMMON, mage.cards.p.PreyUpon.class));
        cards.add(new SetCardInfo("Prey's Vengeance", 1297, Rarity.COMMON, mage.cards.p.PreysVengeance.class));
        cards.add(new SetCardInfo("Preyseizer Dragon", 1025, Rarity.RARE, mage.cards.p.PreyseizerDragon.class));
        cards.add(new SetCardInfo("Price of Progress", 1026, Rarity.UNCOMMON, mage.cards.p.PriceOfProgress.class));
        cards.add(new SetCardInfo("Prickleboar", 1027, Rarity.COMMON, mage.cards.p.Prickleboar.class));
        cards.add(new SetCardInfo("Priest of Titania", 1299, Rarity.COMMON, mage.cards.p.PriestOfTitania.class));
        cards.add(new SetCardInfo("Prodigal Sorcerer", 461, Rarity.UNCOMMON, mage.cards.p.ProdigalSorcerer.class));
        cards.add(new SetCardInfo("Promise of Bunrei", 202, Rarity.UNCOMMON, mage.cards.p.PromiseOfBunrei.class));
        cards.add(new SetCardInfo("Propaganda", 462, Rarity.UNCOMMON, mage.cards.p.Propaganda.class));
        cards.add(new SetCardInfo("Prophetic Prism", 1622, Rarity.COMMON, mage.cards.p.PropheticPrism.class));
        cards.add(new SetCardInfo("Prophetic Ravings", 1028, Rarity.COMMON, mage.cards.p.PropheticRavings.class));
        cards.add(new SetCardInfo("Prosperous Pirates", 463, Rarity.COMMON, mage.cards.p.ProsperousPirates.class));
        cards.add(new SetCardInfo("Prowling Caracal", 203, Rarity.COMMON, mage.cards.p.ProwlingCaracal.class));
        cards.add(new SetCardInfo("Prowling Pangolin", 739, Rarity.COMMON, mage.cards.p.ProwlingPangolin.class));
        cards.add(new SetCardInfo("Pulse of Murasa", 1300, Rarity.COMMON, mage.cards.p.PulseOfMurasa.class));
        cards.add(new SetCardInfo("Purphoros, God of the Forge", 1029, Rarity.MYTHIC, mage.cards.p.PurphorosGodOfTheForge.class));
        cards.add(new SetCardInfo("Purple-Crystal Crab", 464, Rarity.COMMON, mage.cards.p.PurpleCrystalCrab.class));
        cards.add(new SetCardInfo("Putrefy", 1466, Rarity.UNCOMMON, mage.cards.p.Putrefy.class));
        cards.add(new SetCardInfo("Pyrotechnics", 1030, Rarity.UNCOMMON, mage.cards.p.Pyrotechnics.class));
        cards.add(new SetCardInfo("Qasali Pridemage", 1467, Rarity.COMMON, mage.cards.q.QasaliPridemage.class));
        cards.add(new SetCardInfo("Quakefoot Cyclops", 1031, Rarity.COMMON, mage.cards.q.QuakefootCyclops.class));
        cards.add(new SetCardInfo("Queen Marchesa", 1468, Rarity.MYTHIC, mage.cards.q.QueenMarchesa.class));
        cards.add(new SetCardInfo("Queen's Agent", 740, Rarity.COMMON, mage.cards.q.QueensAgent.class));
        cards.add(new SetCardInfo("Quest for the Gravelord", 741, Rarity.UNCOMMON, mage.cards.q.QuestForTheGravelord.class));
        cards.add(new SetCardInfo("Questing Phelddagrif", 1469, Rarity.RARE, mage.cards.q.QuestingPhelddagrif.class));
        cards.add(new SetCardInfo("Quiet Disrepair", 1301, Rarity.COMMON, mage.cards.q.QuietDisrepair.class));
        cards.add(new SetCardInfo("Rabid Bloodsucker", 742, Rarity.COMMON, mage.cards.r.RabidBloodsucker.class));
        cards.add(new SetCardInfo("Raff Capashen, Ship's Mage", 1470, Rarity.UNCOMMON, mage.cards.r.RaffCapashenShipsMage.class));
        cards.add(new SetCardInfo("Rage Reflection", 1032, Rarity.RARE, mage.cards.r.RageReflection.class));
        cards.add(new SetCardInfo("Raging Swordtooth", 1471, Rarity.UNCOMMON, mage.cards.r.RagingSwordtooth.class));
        cards.add(new SetCardInfo("Rain of Thorns", 1302, Rarity.UNCOMMON, mage.cards.r.RainOfThorns.class));
        cards.add(new SetCardInfo("Rakdos Drake", 743, Rarity.COMMON, mage.cards.r.RakdosDrake.class));
        cards.add(new SetCardInfo("Rakshasa's Secret", 744, Rarity.COMMON, mage.cards.r.RakshasasSecret.class));
        cards.add(new SetCardInfo("Rally the Peasants", 204, Rarity.COMMON, mage.cards.r.RallyThePeasants.class));
        cards.add(new SetCardInfo("Rampaging Cyclops", 1033, Rarity.COMMON, mage.cards.r.RampagingCyclops.class));
        cards.add(new SetCardInfo("Rampant Growth", 1303, Rarity.COMMON, mage.cards.r.RampantGrowth.class));
        cards.add(new SetCardInfo("Rancor", 1304, Rarity.UNCOMMON, mage.cards.r.Rancor.class));
        cards.add(new SetCardInfo("Ranger's Guile", 1305, Rarity.COMMON, mage.cards.r.RangersGuile.class));
        cards.add(new SetCardInfo("Raptor Companion", 205, Rarity.COMMON, mage.cards.r.RaptorCompanion.class));
        cards.add(new SetCardInfo("Ravenous Chupacabra", 745, Rarity.UNCOMMON, mage.cards.r.RavenousChupacabra.class));
        cards.add(new SetCardInfo("Ravenous Leucrocota", 1306, Rarity.COMMON, mage.cards.r.RavenousLeucrocota.class));
        cards.add(new SetCardInfo("Read the Bones", 746, Rarity.COMMON, mage.cards.r.ReadTheBones.class));
        cards.add(new SetCardInfo("Reality Scramble", 1034, Rarity.RARE, mage.cards.r.RealityScramble.class));
        cards.add(new SetCardInfo("Reaper of Night", 747, Rarity.COMMON, mage.cards.r.ReaperOfNight.class));
        cards.add(new SetCardInfo("Reassembling Skeleton", 748, Rarity.UNCOMMON, mage.cards.r.ReassemblingSkeleton.class));
        cards.add(new SetCardInfo("Reckless Fireweaver", 1035, Rarity.COMMON, mage.cards.r.RecklessFireweaver.class));
        cards.add(new SetCardInfo("Reckless Imp", 749, Rarity.COMMON, mage.cards.r.RecklessImp.class));
        cards.add(new SetCardInfo("Reckless Spite", 750, Rarity.UNCOMMON, mage.cards.r.RecklessSpite.class));
        cards.add(new SetCardInfo("Reckless Wurm", 1036, Rarity.COMMON, mage.cards.r.RecklessWurm.class));
        cards.add(new SetCardInfo("Reclaim", 1307, Rarity.COMMON, mage.cards.r.Reclaim.class));
        cards.add(new SetCardInfo("Reclaiming Vines", 1308, Rarity.COMMON, mage.cards.r.ReclaimingVines.class));
        cards.add(new SetCardInfo("Reclusive Artificer", 1472, Rarity.UNCOMMON, mage.cards.r.ReclusiveArtificer.class));
        cards.add(new SetCardInfo("Recoup", 1037, Rarity.UNCOMMON, mage.cards.r.Recoup.class));
        cards.add(new SetCardInfo("Recover", 751, Rarity.COMMON, mage.cards.r.Recover.class));
        cards.add(new SetCardInfo("Recruiter of the Guard", 206, Rarity.RARE, mage.cards.r.RecruiterOfTheGuard.class));
        cards.add(new SetCardInfo("Reflector Mage", 1473, Rarity.UNCOMMON, mage.cards.r.ReflectorMage.class));
        cards.add(new SetCardInfo("Refocus", 465, Rarity.COMMON, mage.cards.r.Refocus.class));
        cards.add(new SetCardInfo("Refurbish", 207, Rarity.UNCOMMON, mage.cards.r.Refurbish.class));
        cards.add(new SetCardInfo("Regrowth", 1309, Rarity.UNCOMMON, mage.cards.r.Regrowth.class));
        cards.add(new SetCardInfo("Release the Ants", 1038, Rarity.UNCOMMON, mage.cards.r.ReleaseTheAnts.class));
        cards.add(new SetCardInfo("Release the Gremlins", 1039, Rarity.RARE, mage.cards.r.ReleaseTheGremlins.class));
        cards.add(new SetCardInfo("Relic Crush", 1310, Rarity.COMMON, mage.cards.r.RelicCrush.class));
        cards.add(new SetCardInfo("Reliquary Tower", 1683, Rarity.UNCOMMON, mage.cards.r.ReliquaryTower.class));
        cards.add(new SetCardInfo("Renegade Demon", 752, Rarity.COMMON, mage.cards.r.RenegadeDemon.class));
        cards.add(new SetCardInfo("Renegade Map", 1623, Rarity.COMMON, mage.cards.r.RenegadeMap.class));
        cards.add(new SetCardInfo("Renegade Tactics", 1040, Rarity.COMMON, mage.cards.r.RenegadeTactics.class));
        cards.add(new SetCardInfo("Renegade's Getaway", 753, Rarity.COMMON, mage.cards.r.RenegadesGetaway.class));
        cards.add(new SetCardInfo("Renewed Faith", 208, Rarity.COMMON, mage.cards.r.RenewedFaith.class));
        cards.add(new SetCardInfo("Repulse", 466, Rarity.COMMON, mage.cards.r.Repulse.class));
        cards.add(new SetCardInfo("Resurrection", 209, Rarity.COMMON, mage.cards.r.Resurrection.class));
        cards.add(new SetCardInfo("Retraction Helix", 467, Rarity.COMMON, mage.cards.r.RetractionHelix.class));
        cards.add(new SetCardInfo("Retreat to Emeria", 210, Rarity.UNCOMMON, mage.cards.r.RetreatToEmeria.class));
        cards.add(new SetCardInfo("Return to the Earth", 1311, Rarity.COMMON, mage.cards.r.ReturnToTheEarth.class));
        cards.add(new SetCardInfo("Returned Centaur", 754, Rarity.COMMON, mage.cards.r.ReturnedCentaur.class));
        cards.add(new SetCardInfo("Revel in Riches", 755, Rarity.RARE, mage.cards.r.RevelInRiches.class));
        cards.add(new SetCardInfo("Revenant", 756, Rarity.UNCOMMON, mage.cards.r.Revenant.class));
        cards.add(new SetCardInfo("Revive", 1312, Rarity.COMMON, mage.cards.r.Revive.class));
        cards.add(new SetCardInfo("Reviving Dose", 211, Rarity.COMMON, mage.cards.r.RevivingDose.class));
        cards.add(new SetCardInfo("Rhet-Crop Spearmaster", 212, Rarity.COMMON, mage.cards.r.RhetCropSpearmaster.class));
        cards.add(new SetCardInfo("Rhonas's Monument", 1624, Rarity.UNCOMMON, mage.cards.r.RhonassMonument.class));
        cards.add(new SetCardInfo("Rhox Maulers", 1313, Rarity.COMMON, mage.cards.r.RhoxMaulers.class));
        cards.add(new SetCardInfo("Rhox War Monk", 1474, Rarity.UNCOMMON, mage.cards.r.RhoxWarMonk.class));
        cards.add(new SetCardInfo("Rhys the Redeemed", 1530, Rarity.RARE, mage.cards.r.RhysTheRedeemed.class));
        cards.add(new SetCardInfo("Rhystic Study", 468, Rarity.COMMON, mage.cards.r.RhysticStudy.class));
        cards.add(new SetCardInfo("Riftwing Cloudskate", 469, Rarity.UNCOMMON, mage.cards.r.RiftwingCloudskate.class));
        cards.add(new SetCardInfo("Righteous Cause", 213, Rarity.UNCOMMON, mage.cards.r.RighteousCause.class));
        cards.add(new SetCardInfo("Ringwarden Owl", 470, Rarity.COMMON, mage.cards.r.RingwardenOwl.class));
        cards.add(new SetCardInfo("Riparian Tiger", 1314, Rarity.COMMON, mage.cards.r.RiparianTiger.class));
        cards.add(new SetCardInfo("Riptide Crab", 1475, Rarity.COMMON, mage.cards.r.RiptideCrab.class));
        cards.add(new SetCardInfo("Rishadan Footpad", 471, Rarity.UNCOMMON, mage.cards.r.RishadanFootpad.class));
        cards.add(new SetCardInfo("Rite of the Serpent", 757, Rarity.COMMON, mage.cards.r.RiteOfTheSerpent.class));
        cards.add(new SetCardInfo("Rith, the Awakener", 1476, Rarity.RARE, mage.cards.r.RithTheAwakener.class));
        cards.add(new SetCardInfo("Rivals' Duel", 1041, Rarity.UNCOMMON, mage.cards.r.RivalsDuel.class));
        cards.add(new SetCardInfo("River Boa", 1315, Rarity.UNCOMMON, mage.cards.r.RiverBoa.class));
        cards.add(new SetCardInfo("River Darter", 472, Rarity.COMMON, mage.cards.r.RiverDarter.class));
        cards.add(new SetCardInfo("River Hoopoe", 1477, Rarity.UNCOMMON, mage.cards.r.RiverHoopoe.class));
        cards.add(new SetCardInfo("River Serpent", 473, Rarity.COMMON, mage.cards.r.RiverSerpent.class));
        cards.add(new SetCardInfo("Riverwheel Aerialists", 474, Rarity.COMMON, mage.cards.r.RiverwheelAerialists.class));
        cards.add(new SetCardInfo("Roar of the Wurm", 1316, Rarity.UNCOMMON, mage.cards.r.RoarOfTheWurm.class));
        cards.add(new SetCardInfo("Roast", 1042, Rarity.UNCOMMON, mage.cards.r.Roast.class));
        cards.add(new SetCardInfo("Rogue's Passage", 1684, Rarity.UNCOMMON, mage.cards.r.RoguesPassage.class));
        cards.add(new SetCardInfo("Rolling Thunder", 1043, Rarity.UNCOMMON, mage.cards.r.RollingThunder.class));
        cards.add(new SetCardInfo("Root Out", 1317, Rarity.COMMON, mage.cards.r.RootOut.class));
        cards.add(new SetCardInfo("Rootborn Defenses", 214, Rarity.COMMON, mage.cards.r.RootbornDefenses.class));
        cards.add(new SetCardInfo("Roots", 1318, Rarity.COMMON, mage.cards.r.Roots.class));
        cards.add(new SetCardInfo("Rosemane Centaur", 1478, Rarity.COMMON, mage.cards.r.RosemaneCentaur.class));
        cards.add(new SetCardInfo("Rosethorn Halberd", 1319, Rarity.COMMON, mage.cards.r.RosethornHalberd.class));
        cards.add(new SetCardInfo("Rosheen Meanderer", 1531, Rarity.UNCOMMON, mage.cards.r.RosheenMeanderer.class));
        cards.add(new SetCardInfo("Rotfeaster Maggot", 758, Rarity.COMMON, mage.cards.r.RotfeasterMaggot.class));
        cards.add(new SetCardInfo("Rubblebelt Maaka", 1044, Rarity.COMMON, mage.cards.r.RubblebeltMaaka.class));
        cards.add(new SetCardInfo("Ruin Rat", 759, Rarity.COMMON, mage.cards.r.RuinRat.class));
        cards.add(new SetCardInfo("Ruinous Gremlin", 1045, Rarity.COMMON, mage.cards.r.RuinousGremlin.class));
        cards.add(new SetCardInfo("Rummaging Goblin", 1046, Rarity.COMMON, mage.cards.r.RummagingGoblin.class));
        cards.add(new SetCardInfo("Run Amok", 1047, Rarity.COMMON, mage.cards.r.RunAmok.class));
        cards.add(new SetCardInfo("Rune-Scarred Demon", 760, Rarity.RARE, mage.cards.r.RuneScarredDemon.class));
        cards.add(new SetCardInfo("Runeclaw Bear", 1320, Rarity.COMMON, mage.cards.r.RuneclawBear.class));
        cards.add(new SetCardInfo("Rush of Adrenaline", 1048, Rarity.COMMON, mage.cards.r.RushOfAdrenaline.class));
        cards.add(new SetCardInfo("Sacred Cat", 215, Rarity.COMMON, mage.cards.s.SacredCat.class));
        cards.add(new SetCardInfo("Sadistic Hypnotist", 761, Rarity.UNCOMMON, mage.cards.s.SadisticHypnotist.class));
        cards.add(new SetCardInfo("Sage of Lat-Nam", 475, Rarity.UNCOMMON, mage.cards.s.SageOfLatNam.class));
        cards.add(new SetCardInfo("Sagu Archer", 1321, Rarity.COMMON, mage.cards.s.SaguArcher.class));
        cards.add(new SetCardInfo("Sailor of Means", 476, Rarity.COMMON, mage.cards.s.SailorOfMeans.class));
        cards.add(new SetCardInfo("Sakashima the Impostor", 477, Rarity.RARE, mage.cards.s.SakashimaTheImpostor.class));
        cards.add(new SetCardInfo("Sakura-Tribe Elder", 1322, Rarity.COMMON, mage.cards.s.SakuraTribeElder.class));
        cards.add(new SetCardInfo("Salivating Gremlins", 1049, Rarity.COMMON, mage.cards.s.SalivatingGremlins.class));
        cards.add(new SetCardInfo("Samut's Sprint", 1050, Rarity.COMMON, mage.cards.s.SamutsSprint.class));
        cards.add(new SetCardInfo("Sanctum Gargoyle", 216, Rarity.COMMON, mage.cards.s.SanctumGargoyle.class));
        cards.add(new SetCardInfo("Sandsteppe Citadel", 1685, Rarity.UNCOMMON, mage.cards.s.SandsteppeCitadel.class));
        cards.add(new SetCardInfo("Sandstone Oracle", 1625, Rarity.UNCOMMON, mage.cards.s.SandstoneOracle.class));
        cards.add(new SetCardInfo("Sandstorm Charger", 217, Rarity.COMMON, mage.cards.s.SandstormCharger.class));
        cards.add(new SetCardInfo("Sapphire Charm", 478, Rarity.COMMON, mage.cards.s.SapphireCharm.class));
        cards.add(new SetCardInfo("Saproling Migration", 1323, Rarity.COMMON, mage.cards.s.SaprolingMigration.class));
        cards.add(new SetCardInfo("Sarkhan's Rage", 1051, Rarity.COMMON, mage.cards.s.SarkhansRage.class));
        cards.add(new SetCardInfo("Satyr Enchanter", 1479, Rarity.UNCOMMON, mage.cards.s.SatyrEnchanter.class));
        cards.add(new SetCardInfo("Savage Knuckleblade", 1480, Rarity.RARE, mage.cards.s.SavageKnuckleblade.class));
        cards.add(new SetCardInfo("Savage Punch", 1324, Rarity.COMMON, mage.cards.s.SavagePunch.class));
        cards.add(new SetCardInfo("Savage Twister", 1481, Rarity.UNCOMMON, mage.cards.s.SavageTwister.class));
        cards.add(new SetCardInfo("Savannah Lions", 218, Rarity.COMMON, mage.cards.s.SavannahLions.class));
        cards.add(new SetCardInfo("Scarab Feast", 762, Rarity.COMMON, mage.cards.s.ScarabFeast.class));
        cards.add(new SetCardInfo("Scatter the Seeds", 1325, Rarity.COMMON, mage.cards.s.ScatterTheSeeds.class));
        cards.add(new SetCardInfo("Scoured Barrens", 1686, Rarity.COMMON, mage.cards.s.ScouredBarrens.class));
        cards.add(new SetCardInfo("Screamreach Brawler", 1052, Rarity.COMMON, mage.cards.s.ScreamreachBrawler.class));
        cards.add(new SetCardInfo("Scroll Thief", 479, Rarity.COMMON, mage.cards.s.ScrollThief.class));
        cards.add(new SetCardInfo("Scrounger of Souls", 763, Rarity.COMMON, mage.cards.s.ScroungerOfSouls.class));
        cards.add(new SetCardInfo("Scuttling Death", 764, Rarity.COMMON, mage.cards.s.ScuttlingDeath.class));
        cards.add(new SetCardInfo("Sea Gate Oracle", 480, Rarity.COMMON, mage.cards.s.SeaGateOracle.class));
        cards.add(new SetCardInfo("Seal of Cleansing", 219, Rarity.COMMON, mage.cards.s.SealOfCleansing.class));
        cards.add(new SetCardInfo("Seal of Doom", 765, Rarity.COMMON, mage.cards.s.SealOfDoom.class));
        cards.add(new SetCardInfo("Seal of Strength", 1326, Rarity.COMMON, mage.cards.s.SealOfStrength.class));
        cards.add(new SetCardInfo("Sealock Monster", 481, Rarity.UNCOMMON, mage.cards.s.SealockMonster.class));
        cards.add(new SetCardInfo("Search for Tomorrow", 1327, Rarity.COMMON, mage.cards.s.SearchForTomorrow.class));
        cards.add(new SetCardInfo("Searing Light", 220, Rarity.COMMON, mage.cards.s.SearingLight.class));
        cards.add(new SetCardInfo("Secrets of the Golden City", 482, Rarity.COMMON, mage.cards.s.SecretsOfTheGoldenCity.class));
        cards.add(new SetCardInfo("Sedraxis Specter", 1482, Rarity.UNCOMMON, mage.cards.s.SedraxisSpecter.class));
        cards.add(new SetCardInfo("Seek the Horizon", 1328, Rarity.UNCOMMON, mage.cards.s.SeekTheHorizon.class));
        cards.add(new SetCardInfo("Seek the Wilds", 1329, Rarity.COMMON, mage.cards.s.SeekTheWilds.class));
        cards.add(new SetCardInfo("Seeker of the Way", 221, Rarity.UNCOMMON, mage.cards.s.SeekerOfTheWay.class));
        cards.add(new SetCardInfo("Seismic Shift", 1053, Rarity.COMMON, mage.cards.s.SeismicShift.class));
        cards.add(new SetCardInfo("Seismic Stomp", 1054, Rarity.COMMON, mage.cards.s.SeismicStomp.class));
        cards.add(new SetCardInfo("Sejiri Refuge", 1687, Rarity.UNCOMMON, mage.cards.s.SejiriRefuge.class));
        cards.add(new SetCardInfo("Selesnya Guildmage", 1532, Rarity.UNCOMMON, mage.cards.s.SelesnyaGuildmage.class));
        cards.add(new SetCardInfo("Selvala, Heart of the Wilds", 1330, Rarity.MYTHIC, mage.cards.s.SelvalaHeartOfTheWilds.class));
        cards.add(new SetCardInfo("Send to Sleep", 483, Rarity.COMMON, mage.cards.s.SendToSleep.class));
        cards.add(new SetCardInfo("Sengir Vampire", 766, Rarity.UNCOMMON, mage.cards.s.SengirVampire.class));
        cards.add(new SetCardInfo("Sensor Splicer", 222, Rarity.COMMON, mage.cards.s.SensorSplicer.class));
        cards.add(new SetCardInfo("Seraph of the Suns", 223, Rarity.UNCOMMON, mage.cards.s.SeraphOfTheSuns.class));
        cards.add(new SetCardInfo("Serendib Efreet", 484, Rarity.RARE, mage.cards.s.SerendibEfreet.class));
        cards.add(new SetCardInfo("Serra Disciple", 224, Rarity.COMMON, mage.cards.s.SerraDisciple.class));
        cards.add(new SetCardInfo("Serra's Embrace", 225, Rarity.UNCOMMON, mage.cards.s.SerrasEmbrace.class));
        cards.add(new SetCardInfo("Serrated Arrows", 1626, Rarity.COMMON, mage.cards.s.SerratedArrows.class));
        cards.add(new SetCardInfo("Sewer Nemesis", 767, Rarity.RARE, mage.cards.s.SewerNemesis.class));
        cards.add(new SetCardInfo("Shadowcloak Vampire", 768, Rarity.COMMON, mage.cards.s.ShadowcloakVampire.class));
        cards.add(new SetCardInfo("Shamanic Revelation", 1331, Rarity.RARE, mage.cards.s.ShamanicRevelation.class));
        cards.add(new SetCardInfo("Shambling Attendants", 769, Rarity.COMMON, mage.cards.s.ShamblingAttendants.class));
        cards.add(new SetCardInfo("Shambling Goblin", 770, Rarity.COMMON, mage.cards.s.ShamblingGoblin.class));
        cards.add(new SetCardInfo("Shambling Remains", 1483, Rarity.UNCOMMON, mage.cards.s.ShamblingRemains.class));
        cards.add(new SetCardInfo("Shape the Sands", 1332, Rarity.COMMON, mage.cards.s.ShapeTheSands.class));
        cards.add(new SetCardInfo("Shaper Parasite", 485, Rarity.COMMON, mage.cards.s.ShaperParasite.class));
        cards.add(new SetCardInfo("Shardless Agent", 1484, Rarity.UNCOMMON, mage.cards.s.ShardlessAgent.class));
        cards.add(new SetCardInfo("Shatter", 1055, Rarity.COMMON, mage.cards.s.Shatter.class));
        cards.add(new SetCardInfo("Shattering Spree", 1056, Rarity.UNCOMMON, mage.cards.s.ShatteringSpree.class));
        cards.add(new SetCardInfo("Sheer Drop", 226, Rarity.COMMON, mage.cards.s.SheerDrop.class));
        cards.add(new SetCardInfo("Shenanigans", 1057, Rarity.COMMON, mage.cards.s.Shenanigans.class));
        cards.add(new SetCardInfo("Shimmerscale Drake", 486, Rarity.COMMON, mage.cards.s.ShimmerscaleDrake.class));
        cards.add(new SetCardInfo("Shining Aerosaur", 227, Rarity.COMMON, mage.cards.s.ShiningAerosaur.class));
        cards.add(new SetCardInfo("Shining Armor", 228, Rarity.COMMON, mage.cards.s.ShiningArmor.class));
        cards.add(new SetCardInfo("Shipwreck Looter", 487, Rarity.COMMON, mage.cards.s.ShipwreckLooter.class));
        cards.add(new SetCardInfo("Shipwreck Singer", 1485, Rarity.UNCOMMON, mage.cards.s.ShipwreckSinger.class));
        cards.add(new SetCardInfo("Shock", 1058, Rarity.COMMON, mage.cards.s.Shock.class));
        cards.add(new SetCardInfo("Short Sword", 1627, Rarity.COMMON, mage.cards.s.ShortSword.class));
        cards.add(new SetCardInfo("Shoulder to Shoulder", 229, Rarity.COMMON, mage.cards.s.ShoulderToShoulder.class));
        cards.add(new SetCardInfo("Shrewd Hatchling", 1533, Rarity.UNCOMMON, mage.cards.s.ShrewdHatchling.class));
        cards.add(new SetCardInfo("Shriekmaw", 771, Rarity.UNCOMMON, mage.cards.s.Shriekmaw.class));
        cards.add(new SetCardInfo("Shrouded Lore", 772, Rarity.UNCOMMON, mage.cards.s.ShroudedLore.class));
        cards.add(new SetCardInfo("Siege Wurm", 1333, Rarity.COMMON, mage.cards.s.SiegeWurm.class));
        cards.add(new SetCardInfo("Siegecraft", 230, Rarity.COMMON, mage.cards.s.Siegecraft.class));
        cards.add(new SetCardInfo("Sigil of Valor", 1628, Rarity.UNCOMMON, mage.cards.s.SigilOfValor.class));
        cards.add(new SetCardInfo("Sigiled Starfish", 488, Rarity.UNCOMMON, mage.cards.s.SigiledStarfish.class));
        cards.add(new SetCardInfo("Silent Observer", 489, Rarity.COMMON, mage.cards.s.SilentObserver.class));
        cards.add(new SetCardInfo("Silhana Ledgewalker", 1334, Rarity.COMMON, mage.cards.s.SilhanaLedgewalker.class));
        cards.add(new SetCardInfo("Silkweaver Elite", 1335, Rarity.COMMON, mage.cards.s.SilkweaverElite.class));
        cards.add(new SetCardInfo("Silumgar Butcher", 773, Rarity.COMMON, mage.cards.s.SilumgarButcher.class));
        cards.add(new SetCardInfo("Silverchase Fox", 231, Rarity.COMMON, mage.cards.s.SilverchaseFox.class));
        cards.add(new SetCardInfo("Silvergill Adept", 490, Rarity.UNCOMMON, mage.cards.s.SilvergillAdept.class));
        cards.add(new SetCardInfo("Simic Locket", 1629, Rarity.COMMON, mage.cards.s.SimicLocket.class));
        cards.add(new SetCardInfo("Singing Bell Strike", 491, Rarity.COMMON, mage.cards.s.SingingBellStrike.class));
        cards.add(new SetCardInfo("Skaab Goliath", 492, Rarity.UNCOMMON, mage.cards.s.SkaabGoliath.class));
        cards.add(new SetCardInfo("Skarrg, the Rage Pits", 1688, Rarity.UNCOMMON, mage.cards.s.SkarrgTheRagePits.class));
        cards.add(new SetCardInfo("Skeletal Scrying", 774, Rarity.UNCOMMON, mage.cards.s.SkeletalScrying.class));
        cards.add(new SetCardInfo("Skeleton Archer", 775, Rarity.COMMON, mage.cards.s.SkeletonArcher.class));
        cards.add(new SetCardInfo("Skirk Commando", 1059, Rarity.COMMON, mage.cards.s.SkirkCommando.class));
        cards.add(new SetCardInfo("Skirk Prospector", 1060, Rarity.COMMON, mage.cards.s.SkirkProspector.class));
        cards.add(new SetCardInfo("Skitter Eel", 493, Rarity.COMMON, mage.cards.s.SkitterEel.class));
        cards.add(new SetCardInfo("Skittering Crustacean", 494, Rarity.COMMON, mage.cards.s.SkitteringCrustacean.class));
        cards.add(new SetCardInfo("Skulking Ghost", 776, Rarity.COMMON, mage.cards.s.SkulkingGhost.class));
        cards.add(new SetCardInfo("Skullclamp", 1630, Rarity.UNCOMMON, mage.cards.s.Skullclamp.class));
        cards.add(new SetCardInfo("Skyhunter Skirmisher", 232, Rarity.COMMON, mage.cards.s.SkyhunterSkirmisher.class));
        cards.add(new SetCardInfo("Skymarcher Aspirant", 233, Rarity.UNCOMMON, mage.cards.s.SkymarcherAspirant.class));
        cards.add(new SetCardInfo("Skyscanner", 1631, Rarity.COMMON, mage.cards.s.Skyscanner.class));
        cards.add(new SetCardInfo("Skyspear Cavalry", 234, Rarity.COMMON, mage.cards.s.SkyspearCavalry.class));
        cards.add(new SetCardInfo("Skyward Eye Prophets", 1486, Rarity.UNCOMMON, mage.cards.s.SkywardEyeProphets.class));
        cards.add(new SetCardInfo("Slash of Talons", 235, Rarity.COMMON, mage.cards.s.SlashOfTalons.class));
        cards.add(new SetCardInfo("Slave of Bolas", 1534, Rarity.UNCOMMON, mage.cards.s.SlaveOfBolas.class));
        cards.add(new SetCardInfo("Sleep", 495, Rarity.UNCOMMON, mage.cards.s.Sleep.class));
        cards.add(new SetCardInfo("Slipstream Eel", 496, Rarity.COMMON, mage.cards.s.SlipstreamEel.class));
        cards.add(new SetCardInfo("Slither Blade", 497, Rarity.COMMON, mage.cards.s.SlitherBlade.class));
        cards.add(new SetCardInfo("Sliver Hivelord", 1487, Rarity.MYTHIC, mage.cards.s.SliverHivelord.class));
        cards.add(new SetCardInfo("Smash to Smithereens", 1061, Rarity.COMMON, mage.cards.s.SmashToSmithereens.class));
        cards.add(new SetCardInfo("Smelt", 1062, Rarity.COMMON, mage.cards.s.Smelt.class));
        cards.add(new SetCardInfo("Smiting Helix", 777, Rarity.UNCOMMON, mage.cards.s.SmitingHelix.class));
        cards.add(new SetCardInfo("Snake Umbra", 1336, Rarity.COMMON, mage.cards.s.SnakeUmbra.class));
        cards.add(new SetCardInfo("Snap", 498, Rarity.COMMON, mage.cards.s.Snap.class));
        cards.add(new SetCardInfo("Snapping Drake", 499, Rarity.COMMON, mage.cards.s.SnappingDrake.class));
        cards.add(new SetCardInfo("Snapping Sailback", 1337, Rarity.UNCOMMON, mage.cards.s.SnappingSailback.class));
        cards.add(new SetCardInfo("Snubhorn Sentry", 236, Rarity.COMMON, mage.cards.s.SnubhornSentry.class));
        cards.add(new SetCardInfo("Sol Ring", 1633, Rarity.UNCOMMON, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Solemn Simulacrum", 1632, Rarity.RARE, mage.cards.s.SolemnSimulacrum.class));
        cards.add(new SetCardInfo("Somber Hoverguard", 500, Rarity.COMMON, mage.cards.s.SomberHoverguard.class));
        cards.add(new SetCardInfo("Soothsaying", 501, Rarity.UNCOMMON, mage.cards.s.Soothsaying.class));
        cards.add(new SetCardInfo("Sorcerer's Broom", 1634, Rarity.UNCOMMON, mage.cards.s.SorcerersBroom.class));
        cards.add(new SetCardInfo("Sorin Markov", 778, Rarity.MYTHIC, mage.cards.s.SorinMarkov.class));
        cards.add(new SetCardInfo("Soul Manipulation", 1488, Rarity.UNCOMMON, mage.cards.s.SoulManipulation.class));
        cards.add(new SetCardInfo("Soul Parry", 238, Rarity.COMMON, mage.cards.s.SoulParry.class));
        cards.add(new SetCardInfo("Soul Summons", 240, Rarity.COMMON, mage.cards.s.SoulSummons.class));
        cards.add(new SetCardInfo("Soul Warden", 241, Rarity.COMMON, mage.cards.s.SoulWarden.class));
        cards.add(new SetCardInfo("Soul-Strike Technique", 239, Rarity.COMMON, mage.cards.s.SoulStrikeTechnique.class));
        cards.add(new SetCardInfo("Soulmender", 237, Rarity.COMMON, mage.cards.s.Soulmender.class));
        cards.add(new SetCardInfo("Sparkmage Apprentice", 1063, Rarity.COMMON, mage.cards.s.SparkmageApprentice.class));
        cards.add(new SetCardInfo("Sparkspitter", 1064, Rarity.COMMON, mage.cards.s.Sparkspitter.class));
        cards.add(new SetCardInfo("Sparktongue Dragon", 1065, Rarity.COMMON, mage.cards.s.SparktongueDragon.class));
        cards.add(new SetCardInfo("Sparring Mummy", 242, Rarity.COMMON, mage.cards.s.SparringMummy.class));
        cards.add(new SetCardInfo("Spawning Grounds", 1338, Rarity.RARE, mage.cards.s.SpawningGrounds.class));
        cards.add(new SetCardInfo("Spectral Gateguards", 243, Rarity.COMMON, mage.cards.s.SpectralGateguards.class));
        cards.add(new SetCardInfo("Sphinx's Tutelage", 502, Rarity.UNCOMMON, mage.cards.s.SphinxsTutelage.class));
        cards.add(new SetCardInfo("Spider Spawning", 1339, Rarity.UNCOMMON, mage.cards.s.SpiderSpawning.class));
        cards.add(new SetCardInfo("Spikeshot Goblin", 1066, Rarity.UNCOMMON, mage.cards.s.SpikeshotGoblin.class));
        cards.add(new SetCardInfo("Spire Monitor", 503, Rarity.COMMON, mage.cards.s.SpireMonitor.class));
        cards.add(new SetCardInfo("Spreading Rot", 779, Rarity.COMMON, mage.cards.s.SpreadingRot.class));
        cards.add(new SetCardInfo("Sprouting Thrinax", 1489, Rarity.UNCOMMON, mage.cards.s.SproutingThrinax.class));
        // Card not implemented. BEWARE: When enabling this entry, add it to the appropriate booster slot!
        // It belongs into the Artifact/Land booster. Just uncomment the relevant entry down there.
        //cards.add(new SetCardInfo("Spy Kit", 1635, Rarity.UNCOMMON, mage.cards.s.SpyKit.class));
        cards.add(new SetCardInfo("Squirrel Wrangler", 1340, Rarity.RARE, mage.cards.s.SquirrelWrangler.class));
        cards.add(new SetCardInfo("Stab Wound", 780, Rarity.UNCOMMON, mage.cards.s.StabWound.class));
        cards.add(new SetCardInfo("Staggershock", 1067, Rarity.UNCOMMON, mage.cards.s.Staggershock.class));
        cards.add(new SetCardInfo("Stalking Tiger", 1341, Rarity.COMMON, mage.cards.s.StalkingTiger.class));
        cards.add(new SetCardInfo("Stallion of Ashmouth", 781, Rarity.COMMON, mage.cards.s.StallionOfAshmouth.class));
        cards.add(new SetCardInfo("Stalwart Aven", 244, Rarity.COMMON, mage.cards.s.StalwartAven.class));
        cards.add(new SetCardInfo("Star of Extinction", 1068, Rarity.MYTHIC, mage.cards.s.StarOfExtinction.class));
        cards.add(new SetCardInfo("Star-Crowned Stag", 245, Rarity.COMMON, mage.cards.s.StarCrownedStag.class));
        cards.add(new SetCardInfo("Stave Off", 246, Rarity.COMMON, mage.cards.s.StaveOff.class));
        cards.add(new SetCardInfo("Steadfast Sentinel", 247, Rarity.COMMON, mage.cards.s.SteadfastSentinel.class));
        cards.add(new SetCardInfo("Steady Progress", 504, Rarity.COMMON, mage.cards.s.SteadyProgress.class));
        cards.add(new SetCardInfo("Steamflogger Boss", 1069, Rarity.RARE, mage.cards.s.SteamfloggerBoss.class));
        cards.add(new SetCardInfo("Stinkweed Imp", 782, Rarity.COMMON, mage.cards.s.StinkweedImp.class));
        cards.add(new SetCardInfo("Stitched Drake", 505, Rarity.COMMON, mage.cards.s.StitchedDrake.class));
        cards.add(new SetCardInfo("Stoic Builder", 1342, Rarity.COMMON, mage.cards.s.StoicBuilder.class));
        cards.add(new SetCardInfo("Stone Haven Medic", 248, Rarity.COMMON, mage.cards.s.StoneHavenMedic.class));
        cards.add(new SetCardInfo("Storm Sculptor", 506, Rarity.COMMON, mage.cards.s.StormSculptor.class));
        cards.add(new SetCardInfo("Stormblood Berserker", 1070, Rarity.UNCOMMON, mage.cards.s.StormbloodBerserker.class));
        cards.add(new SetCardInfo("Stormchaser Chimera", 1490, Rarity.UNCOMMON, mage.cards.s.StormchaserChimera.class));
        cards.add(new SetCardInfo("Strategic Planning", 507, Rarity.COMMON, mage.cards.s.StrategicPlanning.class));
        cards.add(new SetCardInfo("Stream of Thought", 508, Rarity.COMMON, mage.cards.s.StreamOfThought.class));
        cards.add(new SetCardInfo("Street Wraith", 783, Rarity.UNCOMMON, mage.cards.s.StreetWraith.class));
        cards.add(new SetCardInfo("Strength in Numbers", 1343, Rarity.COMMON, mage.cards.s.StrengthInNumbers.class));
        cards.add(new SetCardInfo("Stromkirk Patrol", 784, Rarity.COMMON, mage.cards.s.StromkirkPatrol.class));
        cards.add(new SetCardInfo("Stunt Double", 509, Rarity.RARE, mage.cards.s.StuntDouble.class));
        cards.add(new SetCardInfo("Subtle Strike", 785, Rarity.COMMON, mage.cards.s.SubtleStrike.class));
        cards.add(new SetCardInfo("Sudden Demise", 1071, Rarity.RARE, mage.cards.s.SuddenDemise.class));
        cards.add(new SetCardInfo("Sulfurous Blast", 1072, Rarity.UNCOMMON, mage.cards.s.SulfurousBlast.class));
        cards.add(new SetCardInfo("Sultai Charm", 1491, Rarity.UNCOMMON, mage.cards.s.SultaiCharm.class));
        cards.add(new SetCardInfo("Sultai Runemark", 786, Rarity.COMMON, mage.cards.s.SultaiRunemark.class));
        cards.add(new SetCardInfo("Sultai Soothsayer", 1492, Rarity.UNCOMMON, mage.cards.s.SultaiSoothsayer.class));
        cards.add(new SetCardInfo("Summit Prowler", 1073, Rarity.COMMON, mage.cards.s.SummitProwler.class));
        cards.add(new SetCardInfo("Sun-Crowned Hunters", 1074, Rarity.COMMON, mage.cards.s.SunCrownedHunters.class));
        cards.add(new SetCardInfo("Sunlance", 249, Rarity.COMMON, mage.cards.s.Sunlance.class));
        cards.add(new SetCardInfo("Sunrise Seeker", 250, Rarity.COMMON, mage.cards.s.SunriseSeeker.class));
        cards.add(new SetCardInfo("Sunset Pyramid", 1636, Rarity.UNCOMMON, mage.cards.s.SunsetPyramid.class));
        cards.add(new SetCardInfo("Suppression Bonds", 251, Rarity.COMMON, mage.cards.s.SuppressionBonds.class));
        cards.add(new SetCardInfo("Supreme Verdict", 1493, Rarity.RARE, mage.cards.s.SupremeVerdict.class));
        cards.add(new SetCardInfo("Surrakar Banisher", 510, Rarity.COMMON, mage.cards.s.SurrakarBanisher.class));
        cards.add(new SetCardInfo("Survive the Night", 252, Rarity.COMMON, mage.cards.s.SurviveTheNight.class));
        cards.add(new SetCardInfo("Suspicious Bookcase", 1637, Rarity.UNCOMMON, mage.cards.s.SuspiciousBookcase.class));
        cards.add(new SetCardInfo("Swashbuckling", 1075, Rarity.COMMON, mage.cards.s.Swashbuckling.class));
        cards.add(new SetCardInfo("Sweatworks Brawler", 1076, Rarity.COMMON, mage.cards.s.SweatworksBrawler.class));
        cards.add(new SetCardInfo("Swift Kick", 1077, Rarity.COMMON, mage.cards.s.SwiftKick.class));
        cards.add(new SetCardInfo("Swiftwater Cliffs", 1689, Rarity.COMMON, mage.cards.s.SwiftwaterCliffs.class));
        cards.add(new SetCardInfo("Sword of the Animist", 1638, Rarity.RARE, mage.cards.s.SwordOfTheAnimist.class));
        cards.add(new SetCardInfo("Swords to Plowshares", 253, Rarity.UNCOMMON, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("Sylvan Bounty", 1344, Rarity.COMMON, mage.cards.s.SylvanBounty.class));
        cards.add(new SetCardInfo("Sylvan Scrying", 1345, Rarity.UNCOMMON, mage.cards.s.SylvanScrying.class));
        cards.add(new SetCardInfo("Syncopate", 511, Rarity.COMMON, mage.cards.s.Syncopate.class));
        cards.add(new SetCardInfo("Syr Elenora, the Discerning", 512, Rarity.UNCOMMON, mage.cards.s.SyrElenoraTheDiscerning.class));
        cards.add(new SetCardInfo("Tajuru Pathwarden", 1346, Rarity.COMMON, mage.cards.t.TajuruPathwarden.class));
        cards.add(new SetCardInfo("Tajuru Warcaller", 1347, Rarity.UNCOMMON, mage.cards.t.TajuruWarcaller.class));
        cards.add(new SetCardInfo("Take Down", 1348, Rarity.COMMON, mage.cards.t.TakeDown.class));
        cards.add(new SetCardInfo("Take Vengeance", 254, Rarity.COMMON, mage.cards.t.TakeVengeance.class));
        cards.add(new SetCardInfo("Talons of Wildwood", 1349, Rarity.COMMON, mage.cards.t.TalonsOfWildwood.class));
        cards.add(new SetCardInfo("Talrand, Sky Summoner", 513, Rarity.RARE, mage.cards.t.TalrandSkySummoner.class));
        cards.add(new SetCardInfo("Tandem Lookout", 514, Rarity.COMMON, mage.cards.t.TandemLookout.class));
        cards.add(new SetCardInfo("Tandem Tactics", 255, Rarity.COMMON, mage.cards.t.TandemTactics.class));
        cards.add(new SetCardInfo("Tar Snare", 787, Rarity.COMMON, mage.cards.t.TarSnare.class));
        cards.add(new SetCardInfo("Tarfire", 1078, Rarity.COMMON, mage.cards.t.Tarfire.class));
        cards.add(new SetCardInfo("Tatyova, Benthic Druid", 1494, Rarity.UNCOMMON, mage.cards.t.TatyovaBenthicDruid.class));
        cards.add(new SetCardInfo("Taurean Mauler", 1079, Rarity.RARE, mage.cards.t.TaureanMauler.class));
        cards.add(new SetCardInfo("Tavern Swindler", 788, Rarity.UNCOMMON, mage.cards.t.TavernSwindler.class));
        cards.add(new SetCardInfo("Tectonic Edge", 1690, Rarity.UNCOMMON, mage.cards.t.TectonicEdge.class));
        cards.add(new SetCardInfo("Tectonic Rift", 1080, Rarity.UNCOMMON, mage.cards.t.TectonicRift.class));
        cards.add(new SetCardInfo("Teferi's Protection", 256, Rarity.RARE, mage.cards.t.TeferisProtection.class));
        cards.add(new SetCardInfo("Teferi, Temporal Archmage", 515, Rarity.MYTHIC, mage.cards.t.TeferiTemporalArchmage.class));
        cards.add(new SetCardInfo("Temple of the False God", 1691, Rarity.UNCOMMON, mage.cards.t.TempleOfTheFalseGod.class));
        cards.add(new SetCardInfo("Temporal Fissure", 516, Rarity.COMMON, mage.cards.t.TemporalFissure.class));
        cards.add(new SetCardInfo("Temporal Mastery", 517, Rarity.MYTHIC, mage.cards.t.TemporalMastery.class));
        cards.add(new SetCardInfo("Tempt with Discovery", 1350, Rarity.RARE, mage.cards.t.TemptWithDiscovery.class));
        cards.add(new SetCardInfo("Temur Battle Rage", 1081, Rarity.COMMON, mage.cards.t.TemurBattleRage.class));
        cards.add(new SetCardInfo("Tendrils of Corruption", 789, Rarity.COMMON, mage.cards.t.TendrilsOfCorruption.class));
        cards.add(new SetCardInfo("Terashi's Grasp", 257, Rarity.COMMON, mage.cards.t.TerashisGrasp.class));
        cards.add(new SetCardInfo("Terminate", 1495, Rarity.COMMON, mage.cards.t.Terminate.class));
        cards.add(new SetCardInfo("Terrain Elemental", 1351, Rarity.COMMON, mage.cards.t.TerrainElemental.class));
        cards.add(new SetCardInfo("Territorial Baloth", 1352, Rarity.COMMON, mage.cards.t.TerritorialBaloth.class));
        cards.add(new SetCardInfo("Territorial Hammerskull", 258, Rarity.COMMON, mage.cards.t.TerritorialHammerskull.class));
        cards.add(new SetCardInfo("Thalia's Lancers", 259, Rarity.RARE, mage.cards.t.ThaliasLancers.class));
        cards.add(new SetCardInfo("Thallid Omnivore", 790, Rarity.COMMON, mage.cards.t.ThallidOmnivore.class));
        cards.add(new SetCardInfo("The Crowd Goes Wild", 1173, Rarity.UNCOMMON, mage.cards.t.TheCrowdGoesWild.class));
        cards.add(new SetCardInfo("The Eldest Reborn", 645, Rarity.UNCOMMON, mage.cards.t.TheEldestReborn.class));
        cards.add(new SetCardInfo("The Gitrog Monster", 1430, Rarity.MYTHIC, mage.cards.t.TheGitrogMonster.class));
        cards.add(new SetCardInfo("The Mirari Conjecture", 431, Rarity.RARE, mage.cards.t.TheMirariConjecture.class));
        cards.add(new SetCardInfo("Thieving Magpie", 518, Rarity.UNCOMMON, mage.cards.t.ThievingMagpie.class));
        cards.add(new SetCardInfo("Thopter Foundry", 1535, Rarity.UNCOMMON, mage.cards.t.ThopterFoundry.class));
        cards.add(new SetCardInfo("Thorn of the Black Rose", 792, Rarity.COMMON, mage.cards.t.ThornOfTheBlackRose.class));
        cards.add(new SetCardInfo("Thornbow Archer", 791, Rarity.COMMON, mage.cards.t.ThornbowArcher.class));
        cards.add(new SetCardInfo("Thornhide Wolves", 1353, Rarity.COMMON, mage.cards.t.ThornhideWolves.class));
        cards.add(new SetCardInfo("Thornscape Battlemage", 1354, Rarity.UNCOMMON, mage.cards.t.ThornscapeBattlemage.class));
        cards.add(new SetCardInfo("Thornweald Archer", 1355, Rarity.COMMON, mage.cards.t.ThornwealdArcher.class));
        cards.add(new SetCardInfo("Thornwind Faeries", 519, Rarity.COMMON, mage.cards.t.ThornwindFaeries.class));
        cards.add(new SetCardInfo("Thornwood Falls", 1692, Rarity.COMMON, mage.cards.t.ThornwoodFalls.class));
        cards.add(new SetCardInfo("Thought Collapse", 521, Rarity.COMMON, mage.cards.t.ThoughtCollapse.class));
        cards.add(new SetCardInfo("Thought Erasure", 1496, Rarity.UNCOMMON, mage.cards.t.ThoughtErasure.class));
        cards.add(new SetCardInfo("Thought Scour", 522, Rarity.COMMON, mage.cards.t.ThoughtScour.class));
        cards.add(new SetCardInfo("Thought Vessel", 1639, Rarity.COMMON, mage.cards.t.ThoughtVessel.class));
        cards.add(new SetCardInfo("Thoughtcast", 520, Rarity.COMMON, mage.cards.t.Thoughtcast.class));
        cards.add(new SetCardInfo("Thraben Foulbloods", 793, Rarity.COMMON, mage.cards.t.ThrabenFoulbloods.class));
        cards.add(new SetCardInfo("Thraben Inspector", 260, Rarity.COMMON, mage.cards.t.ThrabenInspector.class));
        cards.add(new SetCardInfo("Thraben Standard Bearer", 261, Rarity.COMMON, mage.cards.t.ThrabenStandardBearer.class));
        cards.add(new SetCardInfo("Thran Dynamo", 1640, Rarity.UNCOMMON, mage.cards.t.ThranDynamo.class));
        cards.add(new SetCardInfo("Thran Golem", 1641, Rarity.UNCOMMON, mage.cards.t.ThranGolem.class));
        cards.add(new SetCardInfo("Thrashing Brontodon", 1356, Rarity.UNCOMMON, mage.cards.t.ThrashingBrontodon.class));
        cards.add(new SetCardInfo("Thresher Lizard", 1082, Rarity.COMMON, mage.cards.t.ThresherLizard.class));
        cards.add(new SetCardInfo("Thrill of Possibility", 1083, Rarity.COMMON, mage.cards.t.ThrillOfPossibility.class));
        cards.add(new SetCardInfo("Thrive", 1357, Rarity.COMMON, mage.cards.t.Thrive.class));
        cards.add(new SetCardInfo("Thrummingbird", 523, Rarity.UNCOMMON, mage.cards.t.Thrummingbird.class));
        cards.add(new SetCardInfo("Thrun, the Last Troll", 1358, Rarity.MYTHIC, mage.cards.t.ThrunTheLastTroll.class));
        cards.add(new SetCardInfo("Thunder Drake", 524, Rarity.COMMON, mage.cards.t.ThunderDrake.class));
        cards.add(new SetCardInfo("Tibalt's Rager", 1084, Rarity.UNCOMMON, mage.cards.t.TibaltsRager.class));
        cards.add(new SetCardInfo("Tidal Warrior", 525, Rarity.COMMON, mage.cards.t.TidalWarrior.class));
        cards.add(new SetCardInfo("Tidal Wave", 526, Rarity.COMMON, mage.cards.t.TidalWave.class));
        cards.add(new SetCardInfo("Tidy Conclusion", 794, Rarity.COMMON, mage.cards.t.TidyConclusion.class));
        cards.add(new SetCardInfo("Timberwatch Elf", 1359, Rarity.UNCOMMON, mage.cards.t.TimberwatchElf.class));
        cards.add(new SetCardInfo("Time Sieve", 1497, Rarity.RARE, mage.cards.t.TimeSieve.class));
        cards.add(new SetCardInfo("Time to Feed", 1360, Rarity.COMMON, mage.cards.t.TimeToFeed.class));
        cards.add(new SetCardInfo("Timely Reinforcements", 262, Rarity.UNCOMMON, mage.cards.t.TimelyReinforcements.class));
        cards.add(new SetCardInfo("Tinker", 527, Rarity.UNCOMMON, mage.cards.t.Tinker.class));
        cards.add(new SetCardInfo("Tireless Tracker", 1361, Rarity.RARE, mage.cards.t.TirelessTracker.class));
        cards.add(new SetCardInfo("Titanic Growth", 1362, Rarity.COMMON, mage.cards.t.TitanicGrowth.class));
        cards.add(new SetCardInfo("Tithe Drinker", 1498, Rarity.COMMON, mage.cards.t.TitheDrinker.class));
        cards.add(new SetCardInfo("Topan Freeblade", 263, Rarity.COMMON, mage.cards.t.TopanFreeblade.class));
        cards.add(new SetCardInfo("Torch Courier", 1085, Rarity.COMMON, mage.cards.t.TorchCourier.class));
        cards.add(new SetCardInfo("Torment of Hailfire", 795, Rarity.RARE, mage.cards.t.TormentOfHailfire.class));
        cards.add(new SetCardInfo("Torment of Venom", 796, Rarity.COMMON, mage.cards.t.TormentOfVenom.class));
        cards.add(new SetCardInfo("Tormod's Crypt", 1642, Rarity.UNCOMMON, mage.cards.t.TormodsCrypt.class));
        cards.add(new SetCardInfo("Totally Lost", 528, Rarity.COMMON, mage.cards.t.TotallyLost.class));
        cards.add(new SetCardInfo("Touch of Moonglove", 797, Rarity.COMMON, mage.cards.t.TouchOfMoonglove.class));
        cards.add(new SetCardInfo("Tower Gargoyle", 1499, Rarity.UNCOMMON, mage.cards.t.TowerGargoyle.class));
        cards.add(new SetCardInfo("Tower of Eons", 1643, Rarity.RARE, mage.cards.t.TowerOfEons.class));
        cards.add(new SetCardInfo("Toxin Sliver", 798, Rarity.RARE, mage.cards.t.ToxinSliver.class));
        cards.add(new SetCardInfo("Trading Post", 1644, Rarity.RARE, mage.cards.t.TradingPost.class));
        cards.add(new SetCardInfo("Tragic Slip", 799, Rarity.COMMON, mage.cards.t.TragicSlip.class));
        cards.add(new SetCardInfo("Trail of Evidence", 529, Rarity.UNCOMMON, mage.cards.t.TrailOfEvidence.class));
        cards.add(new SetCardInfo("Treacherous Terrain", 1500, Rarity.UNCOMMON, mage.cards.t.TreacherousTerrain.class));
        cards.add(new SetCardInfo("Treasure Cruise", 530, Rarity.COMMON, mage.cards.t.TreasureCruise.class));
        cards.add(new SetCardInfo("Treasure Hunt", 531, Rarity.COMMON, mage.cards.t.TreasureHunt.class));
        cards.add(new SetCardInfo("Treasure Mage", 532, Rarity.UNCOMMON, mage.cards.t.TreasureMage.class));
        cards.add(new SetCardInfo("Trepanation Blade", 1645, Rarity.UNCOMMON, mage.cards.t.TrepanationBlade.class));
        cards.add(new SetCardInfo("Trespasser's Curse", 800, Rarity.COMMON, mage.cards.t.TrespassersCurse.class));
        cards.add(new SetCardInfo("Trial of Ambition", 801, Rarity.UNCOMMON, mage.cards.t.TrialOfAmbition.class));
        cards.add(new SetCardInfo("Trinket Mage", 533, Rarity.COMMON, mage.cards.t.TrinketMage.class));
        cards.add(new SetCardInfo("Triton Tactics", 534, Rarity.UNCOMMON, mage.cards.t.TritonTactics.class));
        cards.add(new SetCardInfo("Triumph of the Hordes", 1363, Rarity.UNCOMMON, mage.cards.t.TriumphOfTheHordes.class));
        cards.add(new SetCardInfo("Tukatongue Thallid", 1364, Rarity.COMMON, mage.cards.t.TukatongueThallid.class));
        cards.add(new SetCardInfo("Turn Aside", 535, Rarity.COMMON, mage.cards.t.TurnAside.class));
        cards.add(new SetCardInfo("Turntimber Basilisk", 1365, Rarity.UNCOMMON, mage.cards.t.TurntimberBasilisk.class));
        cards.add(new SetCardInfo("Twins of Maurer Estate", 802, Rarity.COMMON, mage.cards.t.TwinsOfMaurerEstate.class));
        cards.add(new SetCardInfo("Two-Headed Giant", 1086, Rarity.RARE, mage.cards.t.TwoHeadedGiant.class));
        cards.add(new SetCardInfo("Typhoid Rats", 803, Rarity.COMMON, mage.cards.t.TyphoidRats.class));
        cards.add(new SetCardInfo("Umbral Mantle", 1646, Rarity.UNCOMMON, mage.cards.u.UmbralMantle.class));
        cards.add(new SetCardInfo("Unburden", 804, Rarity.COMMON, mage.cards.u.Unburden.class));
        cards.add(new SetCardInfo("Uncaged Fury", 1087, Rarity.COMMON, mage.cards.u.UncagedFury.class));
        cards.add(new SetCardInfo("Unclaimed Territory", 1693, Rarity.UNCOMMON, mage.cards.u.UnclaimedTerritory.class));
        cards.add(new SetCardInfo("Uncomfortable Chill", 536, Rarity.COMMON, mage.cards.u.UncomfortableChill.class));
        cards.add(new SetCardInfo("Undercity's Embrace", 805, Rarity.COMMON, mage.cards.u.UndercitysEmbrace.class));
        cards.add(new SetCardInfo("Underworld Coinsmith", 1501, Rarity.UNCOMMON, mage.cards.u.UnderworldCoinsmith.class));
        cards.add(new SetCardInfo("Undying Rage", 1088, Rarity.COMMON, mage.cards.u.UndyingRage.class));
        cards.add(new SetCardInfo("Unflinching Courage", 1502, Rarity.UNCOMMON, mage.cards.u.UnflinchingCourage.class));
        cards.add(new SetCardInfo("Universal Automaton", 1647, Rarity.COMMON, mage.cards.u.UniversalAutomaton.class));
        cards.add(new SetCardInfo("Universal Solvent", 1648, Rarity.COMMON, mage.cards.u.UniversalSolvent.class));
        cards.add(new SetCardInfo("Unlicensed Disintegration", 1503, Rarity.UNCOMMON, mage.cards.u.UnlicensedDisintegration.class));
        cards.add(new SetCardInfo("Untamed Hunger", 806, Rarity.COMMON, mage.cards.u.UntamedHunger.class));
        cards.add(new SetCardInfo("Unwavering Initiate", 264, Rarity.COMMON, mage.cards.u.UnwaveringInitiate.class));
        cards.add(new SetCardInfo("Unyielding Krumar", 807, Rarity.COMMON, mage.cards.u.UnyieldingKrumar.class));
        cards.add(new SetCardInfo("Urban Evolution", 1504, Rarity.UNCOMMON, mage.cards.u.UrbanEvolution.class));
        cards.add(new SetCardInfo("Urborg Uprising", 808, Rarity.COMMON, mage.cards.u.UrborgUprising.class));
        cards.add(new SetCardInfo("Urza's Rage", 1089, Rarity.RARE, mage.cards.u.UrzasRage.class));
        cards.add(new SetCardInfo("Valakut Invoker", 1090, Rarity.COMMON, mage.cards.v.ValakutInvoker.class));
        cards.add(new SetCardInfo("Valakut Predator", 1091, Rarity.COMMON, mage.cards.v.ValakutPredator.class));
        cards.add(new SetCardInfo("Valley Dasher", 1092, Rarity.COMMON, mage.cards.v.ValleyDasher.class));
        cards.add(new SetCardInfo("Vampire Champion", 809, Rarity.COMMON, mage.cards.v.VampireChampion.class));
        cards.add(new SetCardInfo("Vampire Envoy", 810, Rarity.COMMON, mage.cards.v.VampireEnvoy.class));
        cards.add(new SetCardInfo("Vampire Hexmage", 811, Rarity.UNCOMMON, mage.cards.v.VampireHexmage.class));
        cards.add(new SetCardInfo("Vampire Lacerator", 812, Rarity.COMMON, mage.cards.v.VampireLacerator.class));
        cards.add(new SetCardInfo("Vampire Nighthawk", 813, Rarity.UNCOMMON, mage.cards.v.VampireNighthawk.class));
        cards.add(new SetCardInfo("Vandalize", 1093, Rarity.COMMON, mage.cards.v.Vandalize.class));
        cards.add(new SetCardInfo("Vapor Snag", 537, Rarity.COMMON, mage.cards.v.VaporSnag.class));
        cards.add(new SetCardInfo("Vastwood Gorger", 1366, Rarity.COMMON, mage.cards.v.VastwoodGorger.class));
        cards.add(new SetCardInfo("Vengeful Rebirth", 1505, Rarity.UNCOMMON, mage.cards.v.VengefulRebirth.class));
        cards.add(new SetCardInfo("Venom Sliver", 1367, Rarity.UNCOMMON, mage.cards.v.VenomSliver.class));
        cards.add(new SetCardInfo("Vent Sentinel", 1094, Rarity.COMMON, mage.cards.v.VentSentinel.class));
        cards.add(new SetCardInfo("Vessel of Malignity", 814, Rarity.COMMON, mage.cards.v.VesselOfMalignity.class));
        cards.add(new SetCardInfo("Vessel of Volatility", 1095, Rarity.COMMON, mage.cards.v.VesselOfVolatility.class));
        cards.add(new SetCardInfo("Veteran Swordsmith", 265, Rarity.COMMON, mage.cards.v.VeteranSwordsmith.class));
        cards.add(new SetCardInfo("Viashino Sandstalker", 1096, Rarity.UNCOMMON, mage.cards.v.ViashinoSandstalker.class));
        cards.add(new SetCardInfo("Vigean Graftmage", 538, Rarity.COMMON, mage.cards.v.VigeanGraftmage.class));
        cards.add(new SetCardInfo("Vigor", 1368, Rarity.RARE, mage.cards.v.Vigor.class));
        cards.add(new SetCardInfo("Village Bell-Ringer", 266, Rarity.COMMON, mage.cards.v.VillageBellRinger.class));
        cards.add(new SetCardInfo("Violent Ultimatum", 1506, Rarity.RARE, mage.cards.v.ViolentUltimatum.class));
        cards.add(new SetCardInfo("Virulent Swipe", 815, Rarity.COMMON, mage.cards.v.VirulentSwipe.class));
        cards.add(new SetCardInfo("Voice of the Provinces", 267, Rarity.COMMON, mage.cards.v.VoiceOfTheProvinces.class));
        cards.add(new SetCardInfo("Volcanic Dragon", 1097, Rarity.UNCOMMON, mage.cards.v.VolcanicDragon.class));
        cards.add(new SetCardInfo("Volcanic Rush", 1098, Rarity.COMMON, mage.cards.v.VolcanicRush.class));
        cards.add(new SetCardInfo("Voldaren Duelist", 1099, Rarity.COMMON, mage.cards.v.VoldarenDuelist.class));
        cards.add(new SetCardInfo("Volunteer Reserves", 268, Rarity.UNCOMMON, mage.cards.v.VolunteerReserves.class));
        cards.add(new SetCardInfo("Voracious Null", 816, Rarity.COMMON, mage.cards.v.VoraciousNull.class));
        cards.add(new SetCardInfo("Vraska's Finisher", 817, Rarity.COMMON, mage.cards.v.VraskasFinisher.class));
        cards.add(new SetCardInfo("Wake of Vultures", 818, Rarity.COMMON, mage.cards.w.WakeOfVultures.class));
        cards.add(new SetCardInfo("Wake the Reflections", 269, Rarity.COMMON, mage.cards.w.WakeTheReflections.class));
        cards.add(new SetCardInfo("Walk the Plank", 820, Rarity.UNCOMMON, mage.cards.w.WalkThePlank.class));
        cards.add(new SetCardInfo("Walking Corpse", 819, Rarity.COMMON, mage.cards.w.WalkingCorpse.class));
        cards.add(new SetCardInfo("Wall of Fire", 1100, Rarity.COMMON, mage.cards.w.WallOfFire.class));
        cards.add(new SetCardInfo("Wall of Frost", 539, Rarity.UNCOMMON, mage.cards.w.WallOfFrost.class));
        cards.add(new SetCardInfo("Wall of Omens", 270, Rarity.UNCOMMON, mage.cards.w.WallOfOmens.class));
        cards.add(new SetCardInfo("Wall of One Thousand Cuts", 271, Rarity.COMMON, mage.cards.w.WallOfOneThousandCuts.class));
        cards.add(new SetCardInfo("Wander in Death", 821, Rarity.COMMON, mage.cards.w.WanderInDeath.class));
        cards.add(new SetCardInfo("Wandering Champion", 272, Rarity.COMMON, mage.cards.w.WanderingChampion.class));
        cards.add(new SetCardInfo("War Behemoth", 273, Rarity.COMMON, mage.cards.w.WarBehemoth.class));
        cards.add(new SetCardInfo("Warden of Evos Isle", 540, Rarity.COMMON, mage.cards.w.WardenOfEvosIsle.class));
        cards.add(new SetCardInfo("Warden of the Eye", 1507, Rarity.UNCOMMON, mage.cards.w.WardenOfTheEye.class));
        cards.add(new SetCardInfo("Wargate", 1508, Rarity.RARE, mage.cards.w.Wargate.class));
        cards.add(new SetCardInfo("Warteye Witch", 822, Rarity.COMMON, mage.cards.w.WarteyeWitch.class));
        cards.add(new SetCardInfo("Watcher in the Web", 1369, Rarity.COMMON, mage.cards.w.WatcherInTheWeb.class));
        cards.add(new SetCardInfo("Watercourser", 541, Rarity.COMMON, mage.cards.w.Watercourser.class));
        cards.add(new SetCardInfo("Wave-Wing Elemental", 542, Rarity.COMMON, mage.cards.w.WaveWingElemental.class));
        cards.add(new SetCardInfo("Wayfaring Temple", 1509, Rarity.UNCOMMON, mage.cards.w.WayfaringTemple.class));
        cards.add(new SetCardInfo("Wayward Giant", 1101, Rarity.COMMON, mage.cards.w.WaywardGiant.class));
        cards.add(new SetCardInfo("Weapons Trainer", 1510, Rarity.UNCOMMON, mage.cards.w.WeaponsTrainer.class));
        cards.add(new SetCardInfo("Weathered Wayfarer", 274, Rarity.RARE, mage.cards.w.WeatheredWayfarer.class));
        cards.add(new SetCardInfo("Wee Dragonauts", 1511, Rarity.UNCOMMON, mage.cards.w.WeeDragonauts.class));
        cards.add(new SetCardInfo("Weight of the Underworld", 823, Rarity.COMMON, mage.cards.w.WeightOfTheUnderworld.class));
        cards.add(new SetCardInfo("Weirded Vampire", 824, Rarity.COMMON, mage.cards.w.WeirdedVampire.class));
        cards.add(new SetCardInfo("Weldfast Wingsmith", 543, Rarity.COMMON, mage.cards.w.WeldfastWingsmith.class));
        cards.add(new SetCardInfo("Welkin Tern", 544, Rarity.COMMON, mage.cards.w.WelkinTern.class));
        cards.add(new SetCardInfo("Wellwisher", 1370, Rarity.COMMON, mage.cards.w.Wellwisher.class));
        cards.add(new SetCardInfo("Wheel of Fate", 1102, Rarity.RARE, mage.cards.w.WheelOfFate.class));
        cards.add(new SetCardInfo("Whelming Wave", 545, Rarity.RARE, mage.cards.w.WhelmingWave.class));
        cards.add(new SetCardInfo("Whiplash Trap", 546, Rarity.COMMON, mage.cards.w.WhiplashTrap.class));
        cards.add(new SetCardInfo("Whir of Invention", 547, Rarity.RARE, mage.cards.w.WhirOfInvention.class));
        cards.add(new SetCardInfo("Whispersilk Cloak", 1649, Rarity.UNCOMMON, mage.cards.w.WhispersilkCloak.class));
        cards.add(new SetCardInfo("Wight of Precinct Six", 825, Rarity.UNCOMMON, mage.cards.w.WightOfPrecinctSix.class));
        cards.add(new SetCardInfo("Wild Griffin", 275, Rarity.COMMON, mage.cards.w.WildGriffin.class));
        cards.add(new SetCardInfo("Wild Growth", 1371, Rarity.COMMON, mage.cards.w.WildGrowth.class));
        cards.add(new SetCardInfo("Wild Mongrel", 1372, Rarity.COMMON, mage.cards.w.WildMongrel.class));
        cards.add(new SetCardInfo("Wild Nacatl", 1373, Rarity.COMMON, mage.cards.w.WildNacatl.class));
        cards.add(new SetCardInfo("Wildfire Emissary", 1103, Rarity.COMMON, mage.cards.w.WildfireEmissary.class));
        cards.add(new SetCardInfo("Wildsize", 1374, Rarity.COMMON, mage.cards.w.Wildsize.class));
        cards.add(new SetCardInfo("Will-o'-the-Wisp", 826, Rarity.UNCOMMON, mage.cards.w.WillOTheWisp.class));
        cards.add(new SetCardInfo("Wind Drake", 549, Rarity.COMMON, mage.cards.w.WindDrake.class));
        cards.add(new SetCardInfo("Wind Strider", 552, Rarity.COMMON, mage.cards.w.WindStrider.class));
        cards.add(new SetCardInfo("Wind-Kin Raiders", 550, Rarity.UNCOMMON, mage.cards.w.WindKinRaiders.class));
        cards.add(new SetCardInfo("Windborne Charge", 276, Rarity.UNCOMMON, mage.cards.w.WindborneCharge.class));
        cards.add(new SetCardInfo("Windcaller Aven", 548, Rarity.COMMON, mage.cards.w.WindcallerAven.class));
        cards.add(new SetCardInfo("Windgrace Acolyte", 827, Rarity.COMMON, mage.cards.w.WindgraceAcolyte.class));
        cards.add(new SetCardInfo("Winding Constrictor", 1512, Rarity.UNCOMMON, mage.cards.w.WindingConstrictor.class));
        cards.add(new SetCardInfo("Windrider Eel", 551, Rarity.COMMON, mage.cards.w.WindriderEel.class));
        cards.add(new SetCardInfo("Wing Shards", 278, Rarity.UNCOMMON, mage.cards.w.WingShards.class));
        cards.add(new SetCardInfo("Winged Shepherd", 277, Rarity.COMMON, mage.cards.w.WingedShepherd.class));
        cards.add(new SetCardInfo("Wirewood Lodge", 1694, Rarity.UNCOMMON, mage.cards.w.WirewoodLodge.class));
        cards.add(new SetCardInfo("Wishcoin Crab", 553, Rarity.COMMON, mage.cards.w.WishcoinCrab.class));
        cards.add(new SetCardInfo("Wishful Merfolk", 554, Rarity.COMMON, mage.cards.w.WishfulMerfolk.class));
        cards.add(new SetCardInfo("Wojek Bodyguard", 1104, Rarity.COMMON, mage.cards.w.WojekBodyguard.class));
        cards.add(new SetCardInfo("Wolfkin Bond", 1375, Rarity.COMMON, mage.cards.w.WolfkinBond.class));
        cards.add(new SetCardInfo("Woodborn Behemoth", 1376, Rarity.UNCOMMON, mage.cards.w.WoodbornBehemoth.class));
        cards.add(new SetCardInfo("Woolly Loxodon", 1377, Rarity.COMMON, mage.cards.w.WoollyLoxodon.class));
        cards.add(new SetCardInfo("Woolly Thoctar", 1513, Rarity.UNCOMMON, mage.cards.w.WoollyThoctar.class));
        cards.add(new SetCardInfo("Wren's Run Vanquisher", 1378, Rarity.UNCOMMON, mage.cards.w.WrensRunVanquisher.class));
        cards.add(new SetCardInfo("Wrench Mind", 828, Rarity.COMMON, mage.cards.w.WrenchMind.class));
        cards.add(new SetCardInfo("Wretched Gryff", 555, Rarity.COMMON, mage.cards.w.WretchedGryff.class));
        cards.add(new SetCardInfo("Write into Being", 556, Rarity.COMMON, mage.cards.w.WriteIntoBeing.class));
        cards.add(new SetCardInfo("Yargle, Glutton of Urborg", 829, Rarity.UNCOMMON, mage.cards.y.YargleGluttonOfUrborg.class));
        cards.add(new SetCardInfo("Yavimaya Elder", 1379, Rarity.COMMON, mage.cards.y.YavimayaElder.class));
        cards.add(new SetCardInfo("Yavimaya Sapherd", 1380, Rarity.COMMON, mage.cards.y.YavimayaSapherd.class));
        cards.add(new SetCardInfo("Yavimaya's Embrace", 1514, Rarity.RARE, mage.cards.y.YavimayasEmbrace.class));
        cards.add(new SetCardInfo("Yeva's Forcemage", 1381, Rarity.COMMON, mage.cards.y.YevasForcemage.class));
        cards.add(new SetCardInfo("Young Pyromancer", 1105, Rarity.UNCOMMON, mage.cards.y.YoungPyromancer.class));
        cards.add(new SetCardInfo("Youthful Knight", 279, Rarity.COMMON, mage.cards.y.YouthfulKnight.class));
        cards.add(new SetCardInfo("Youthful Scholar", 557, Rarity.UNCOMMON, mage.cards.y.YouthfulScholar.class));
        cards.add(new SetCardInfo("Yuriko, the Tiger's Shadow", 1515, Rarity.RARE, mage.cards.y.YurikoTheTigersShadow.class));
        cards.add(new SetCardInfo("Zada's Commando", 1106, Rarity.COMMON, mage.cards.z.ZadasCommando.class));
        cards.add(new SetCardInfo("Zealot of the God-Pharaoh", 1107, Rarity.COMMON, mage.cards.z.ZealotOfTheGodPharaoh.class));
        cards.add(new SetCardInfo("Zealous Persecution", 1516, Rarity.UNCOMMON, mage.cards.z.ZealousPersecution.class));
        cards.add(new SetCardInfo("Zealous Strike", 280, Rarity.COMMON, mage.cards.z.ZealousStrike.class));
        cards.add(new SetCardInfo("Zendikar's Roil", 1382, Rarity.UNCOMMON, mage.cards.z.ZendikarsRoil.class));
        cards.add(new SetCardInfo("Zhur-Taa Druid", 1517, Rarity.COMMON, mage.cards.z.ZhurTaaDruid.class));
        cards.add(new SetCardInfo("Zulaport Chainmage", 830, Rarity.COMMON, mage.cards.z.ZulaportChainmage.class));
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
        return this.createBooster();
    }
}
