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
 *
 * @author fireshoes
 */
public final class Amonkhet extends ExpansionSet {

    private static final Amonkhet instance = new Amonkhet();

    public static Amonkhet getInstance() {
        return instance;
    }

    private Amonkhet() {
        super("Amonkhet", "AKH", ExpansionSet.buildDate(2017, 4, 28), SetType.EXPANSION);
        this.blockName = "Amonkhet";
        this.hasBoosters = true;
        this.hasBasicLands = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.ratioBoosterSpecialCommon = 129;
        this.maxCardNumberInBooster = 269;

        cards.add(new SetCardInfo("Ahn-Crop Champion", 194, Rarity.UNCOMMON, mage.cards.a.AhnCropChampion.class));
        cards.add(new SetCardInfo("Ahn-Crop Crasher", 117, Rarity.UNCOMMON, mage.cards.a.AhnCropCrasher.class));
        cards.add(new SetCardInfo("Ancient Crab", 40, Rarity.COMMON, mage.cards.a.AncientCrab.class));
        cards.add(new SetCardInfo("Angel of Sanctions", 1, Rarity.MYTHIC, mage.cards.a.AngelOfSanctions.class));
        cards.add(new SetCardInfo("Angler Drake", 41, Rarity.UNCOMMON, mage.cards.a.AnglerDrake.class));
        cards.add(new SetCardInfo("Anointed Procession", 2, Rarity.RARE, mage.cards.a.AnointedProcession.class));
        cards.add(new SetCardInfo("Anointer Priest", 3, Rarity.COMMON, mage.cards.a.AnointerPriest.class));
        cards.add(new SetCardInfo("Approach of the Second Sun", 4, Rarity.RARE, mage.cards.a.ApproachOfTheSecondSun.class));
        cards.add(new SetCardInfo("Archfiend of Ifnir", 78, Rarity.RARE, mage.cards.a.ArchfiendOfIfnir.class));
        cards.add(new SetCardInfo("As Foretold", 42, Rarity.MYTHIC, mage.cards.a.AsForetold.class));
        cards.add(new SetCardInfo("Aven Initiate", 43, Rarity.COMMON, mage.cards.a.AvenInitiate.class));
        cards.add(new SetCardInfo("Aven Mindcensor", 5, Rarity.RARE, mage.cards.a.AvenMindcensor.class));
        cards.add(new SetCardInfo("Aven Wind Guide", 195, Rarity.UNCOMMON, mage.cards.a.AvenWindGuide.class));
        cards.add(new SetCardInfo("Baleful Ammit", 79, Rarity.UNCOMMON, mage.cards.b.BalefulAmmit.class));
        cards.add(new SetCardInfo("Battlefield Scavenger", 118, Rarity.UNCOMMON, mage.cards.b.BattlefieldScavenger.class));
        cards.add(new SetCardInfo("Benefaction of Rhonas", 156, Rarity.COMMON, mage.cards.b.BenefactionOfRhonas.class));
        cards.add(new SetCardInfo("Binding Mummy", 6, Rarity.COMMON, mage.cards.b.BindingMummy.class));
        cards.add(new SetCardInfo("Bitterblade Warrior", 157, Rarity.COMMON, mage.cards.b.BitterbladeWarrior.class));
        cards.add(new SetCardInfo("Blazing Volley", 119, Rarity.COMMON, mage.cards.b.BlazingVolley.class));
        cards.add(new SetCardInfo("Blighted Bat", 80, Rarity.COMMON, mage.cards.b.BlightedBat.class));
        cards.add(new SetCardInfo("Bloodlust Inciter", 120, Rarity.COMMON, mage.cards.b.BloodlustInciter.class));
        cards.add(new SetCardInfo("Bloodrage Brawler", 121, Rarity.UNCOMMON, mage.cards.b.BloodrageBrawler.class));
        cards.add(new SetCardInfo("Bone Picker", 81, Rarity.UNCOMMON, mage.cards.b.BonePicker.class));
        cards.add(new SetCardInfo("Bounty of the Luxa", 196, Rarity.RARE, mage.cards.b.BountyOfTheLuxa.class));
        cards.add(new SetCardInfo("Bontu's Monument", 225, Rarity.UNCOMMON, mage.cards.b.BontusMonument.class));
        cards.add(new SetCardInfo("Bontu the Glorified", 82, Rarity.MYTHIC, mage.cards.b.BontuTheGlorified.class));
        cards.add(new SetCardInfo("Brute Strength", 122, Rarity.COMMON, mage.cards.b.BruteStrength.class));
        cards.add(new SetCardInfo("By Force", 123, Rarity.UNCOMMON, mage.cards.b.ByForce.class));
        cards.add(new SetCardInfo("Cancel", 44, Rarity.COMMON, mage.cards.c.Cancel.class));
        cards.add(new SetCardInfo("Canyon Slough", 239, Rarity.RARE, mage.cards.c.CanyonSlough.class));
        cards.add(new SetCardInfo("Cartouche of Ambition", 83, Rarity.COMMON, mage.cards.c.CartoucheOfAmbition.class));
        cards.add(new SetCardInfo("Cartouche of Knowledge", 45, Rarity.COMMON, mage.cards.c.CartoucheOfKnowledge.class));
        cards.add(new SetCardInfo("Cartouche of Solidarity", 7, Rarity.COMMON, mage.cards.c.CartoucheOfSolidarity.class));
        cards.add(new SetCardInfo("Cartouche of Strength", 158, Rarity.COMMON, mage.cards.c.CartoucheOfStrength.class));
        cards.add(new SetCardInfo("Cartouche of Zeal", 124, Rarity.COMMON, mage.cards.c.CartoucheOfZeal.class));
        cards.add(new SetCardInfo("Cascading Cataracts", 240, Rarity.RARE, mage.cards.c.CascadingCataracts.class));
        cards.add(new SetCardInfo("Cast Out", 8, Rarity.UNCOMMON, mage.cards.c.CastOut.class));
        cards.add(new SetCardInfo("Censor", 46, Rarity.UNCOMMON, mage.cards.c.Censor.class));
        cards.add(new SetCardInfo("Champion of Rhonas", 159, Rarity.RARE, mage.cards.c.ChampionOfRhonas.class));
        cards.add(new SetCardInfo("Channeler Initiate", 160, Rarity.RARE, mage.cards.c.ChannelerInitiate.class));
        cards.add(new SetCardInfo("Cinder Barrens", 280, Rarity.COMMON, mage.cards.c.CinderBarrens.class));
        cards.add(new SetCardInfo("Colossapede", 161, Rarity.COMMON, mage.cards.c.Colossapede.class));
        cards.add(new SetCardInfo("Combat Celebrant", 125, Rarity.MYTHIC, mage.cards.c.CombatCelebrant.class));
        cards.add(new SetCardInfo("Commit // Memory", 211, Rarity.RARE, mage.cards.c.CommitMemory.class));
        cards.add(new SetCardInfo("Companion of the Trials", 271, Rarity.UNCOMMON, mage.cards.c.CompanionOfTheTrials.class));
        cards.add(new SetCardInfo("Compelling Argument", 47, Rarity.COMMON, mage.cards.c.CompellingArgument.class));
        cards.add(new SetCardInfo("Compulsory Rest", 9, Rarity.COMMON, mage.cards.c.CompulsoryRest.class));
        cards.add(new SetCardInfo("Consuming Fervor", 126, Rarity.UNCOMMON, mage.cards.c.ConsumingFervor.class));
        cards.add(new SetCardInfo("Cradle of the Accursed", 241, Rarity.COMMON, mage.cards.c.CradleOfTheAccursed.class));
        cards.add(new SetCardInfo("Crocodile of the Crossing", 162, Rarity.UNCOMMON, mage.cards.c.CrocodileOfTheCrossing.class));
        cards.add(new SetCardInfo("Cryptic Serpent", 48, Rarity.UNCOMMON, mage.cards.c.CrypticSerpent.class));
        cards.add(new SetCardInfo("Cruel Reality", 84, Rarity.MYTHIC, mage.cards.c.CruelReality.class));
        cards.add(new SetCardInfo("Curator of Mysteries", 49, Rarity.RARE, mage.cards.c.CuratorOfMysteries.class));
        cards.add(new SetCardInfo("Cursed Minotaur", 85, Rarity.COMMON, mage.cards.c.CursedMinotaur.class));
        cards.add(new SetCardInfo("Cut // Ribbons", 223, Rarity.RARE, mage.cards.c.CutRibbons.class));
        cards.add(new SetCardInfo("Decimator Beetle", 197, Rarity.UNCOMMON, mage.cards.d.DecimatorBeetle.class));
        cards.add(new SetCardInfo("Decision Paralysis", 50, Rarity.COMMON, mage.cards.d.DecisionParalysis.class));
        cards.add(new SetCardInfo("Deem Worthy", 127, Rarity.UNCOMMON, mage.cards.d.DeemWorthy.class));
        cards.add(new SetCardInfo("Defiant Greatmaw", 163, Rarity.UNCOMMON, mage.cards.d.DefiantGreatmaw.class));
        cards.add(new SetCardInfo("Desert Cerodon", 128, Rarity.COMMON, mage.cards.d.DesertCerodon.class));
        cards.add(new SetCardInfo("Desiccated Naga", 276, Rarity.UNCOMMON, mage.cards.d.DesiccatedNaga.class));
        cards.add(new SetCardInfo("Destined // Lead", 217, Rarity.UNCOMMON, mage.cards.d.DestinedLead.class));
        cards.add(new SetCardInfo("Devoted Crop-Mate", 10, Rarity.UNCOMMON, mage.cards.d.DevotedCropMate.class));
        cards.add(new SetCardInfo("Dispossess", 86, Rarity.RARE, mage.cards.d.Dispossess.class));
        cards.add(new SetCardInfo("Dissenter's Deliverance", 164, Rarity.COMMON, mage.cards.d.DissentersDeliverance.class));
        cards.add(new SetCardInfo("Djeru's Resolve", 11, Rarity.COMMON, mage.cards.d.DjerusResolve.class));
        cards.add(new SetCardInfo("Doomed Dissenter", 87, Rarity.COMMON, mage.cards.d.DoomedDissenter.class));
        cards.add(new SetCardInfo("Drake Haven", 51, Rarity.RARE, mage.cards.d.DrakeHaven.class));
        cards.add(new SetCardInfo("Dread Wanderer", 88, Rarity.RARE, mage.cards.d.DreadWanderer.class));
        cards.add(new SetCardInfo("Dune Beetle", 89, Rarity.COMMON, mage.cards.d.DuneBeetle.class));
        cards.add(new SetCardInfo("Dusk // Dawn", 210, Rarity.RARE, mage.cards.d.DuskDawn.class));
        cards.add(new SetCardInfo("Edifice of Authority", 226, Rarity.UNCOMMON, mage.cards.e.EdificeOfAuthority.class));
        cards.add(new SetCardInfo("Electrify", 129, Rarity.COMMON, mage.cards.e.Electrify.class));
        cards.add(new SetCardInfo("Embalmer's Tools", 227, Rarity.UNCOMMON, mage.cards.e.EmbalmersTools.class));
        cards.add(new SetCardInfo("Emberhorn Minotaur", 130, Rarity.COMMON, mage.cards.e.EmberhornMinotaur.class));
        cards.add(new SetCardInfo("Enigma Drake", 198, Rarity.UNCOMMON, mage.cards.e.EnigmaDrake.class));
        cards.add(new SetCardInfo("Essence Scatter", 52, Rarity.COMMON, mage.cards.e.EssenceScatter.class));
        cards.add(new SetCardInfo("Evolving Wilds", 242, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Exemplar of Strength", 165, Rarity.UNCOMMON, mage.cards.e.ExemplarOfStrength.class));
        cards.add(new SetCardInfo("Failure // Comply", 221, Rarity.RARE, mage.cards.f.FailureComply.class));
        cards.add(new SetCardInfo("Faith of the Devoted", 90, Rarity.UNCOMMON, mage.cards.f.FaithOfTheDevoted.class));
        cards.add(new SetCardInfo("Fan Bearer", 12, Rarity.COMMON, mage.cards.f.FanBearer.class));
        cards.add(new SetCardInfo("Festering Mummy", 91, Rarity.COMMON, mage.cards.f.FesteringMummy.class));
        cards.add(new SetCardInfo("Fetid Pools", 243, Rarity.RARE, mage.cards.f.FetidPools.class));
        cards.add(new SetCardInfo("Final Reward", 92, Rarity.COMMON, mage.cards.f.FinalReward.class));
        cards.add(new SetCardInfo("Flameblade Adept", 131, Rarity.UNCOMMON, mage.cards.f.FlamebladeAdept.class));
        cards.add(new SetCardInfo("Fling", 132, Rarity.COMMON, mage.cards.f.Fling.class));
        cards.add(new SetCardInfo("Floodwaters", 53, Rarity.COMMON, mage.cards.f.Floodwaters.class));
        cards.add(new SetCardInfo("Forest", 254, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forest", 267, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 268, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 269, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forsake the Worldly", 13, Rarity.COMMON, mage.cards.f.ForsakeTheWorldly.class));
        cards.add(new SetCardInfo("Forsaken Sanctuary", 281, Rarity.COMMON, mage.cards.f.ForsakenSanctuary.class));
        cards.add(new SetCardInfo("Foul Orchard", 279, Rarity.COMMON, mage.cards.f.FoulOrchard.class));
        cards.add(new SetCardInfo("Galestrike", 54, Rarity.UNCOMMON, mage.cards.g.Galestrike.class));
        cards.add(new SetCardInfo("Gate to the Afterlife", 228, Rarity.UNCOMMON, mage.cards.g.GateToTheAfterlife.class));
        cards.add(new SetCardInfo("Giant Spider", 166, Rarity.COMMON, mage.cards.g.GiantSpider.class));
        cards.add(new SetCardInfo("Gideon of the Trials", 14, Rarity.MYTHIC, mage.cards.g.GideonOfTheTrials.class));
        cards.add(new SetCardInfo("Gideon's Intervention", 15, Rarity.RARE, mage.cards.g.GideonsIntervention.class));
        cards.add(new SetCardInfo("Gideon's Resolve", 272, Rarity.RARE, mage.cards.g.GideonsResolve.class));
        cards.add(new SetCardInfo("Gideon, Martial Paragon", 270, Rarity.MYTHIC, mage.cards.g.GideonMartialParagon.class));
        cards.add(new SetCardInfo("Gift of Paradise", 167, Rarity.COMMON, mage.cards.g.GiftOfParadise.class));
        cards.add(new SetCardInfo("Glorious End", 133, Rarity.MYTHIC, mage.cards.g.GloriousEnd.class));
        cards.add(new SetCardInfo("Glory-Bound Initiate", 16, Rarity.RARE, mage.cards.g.GloryBoundInitiate.class));
        cards.add(new SetCardInfo("Glorybringer", 134, Rarity.RARE, mage.cards.g.Glorybringer.class));
        cards.add(new SetCardInfo("Glyph Keeper", 55, Rarity.RARE, mage.cards.g.GlyphKeeper.class));
        cards.add(new SetCardInfo("Graceful Cat", 273, Rarity.COMMON, mage.cards.g.GracefulCat.class));
        cards.add(new SetCardInfo("Grasping Dunes", 244, Rarity.UNCOMMON, mage.cards.g.GraspingDunes.class));
        cards.add(new SetCardInfo("Gravedigger", 93, Rarity.UNCOMMON, mage.cards.g.Gravedigger.class));
        cards.add(new SetCardInfo("Greater Sandwurm", 168, Rarity.COMMON, mage.cards.g.GreaterSandwurm.class));
        cards.add(new SetCardInfo("Grim Strider", 94, Rarity.UNCOMMON, mage.cards.g.GrimStrider.class));
        cards.add(new SetCardInfo("Gust Walker", 17, Rarity.COMMON, mage.cards.g.GustWalker.class));
        cards.add(new SetCardInfo("Hapatra's Mark", 169, Rarity.UNCOMMON, mage.cards.h.HapatrasMark.class));
        cards.add(new SetCardInfo("Hapatra, Vizier of Poisons", 199, Rarity.RARE, mage.cards.h.HapatraVizierOfPoisons.class));
        cards.add(new SetCardInfo("Harsh Mentor", 135, Rarity.RARE, mage.cards.h.HarshMentor.class));
        cards.add(new SetCardInfo("Harvest Season", 170, Rarity.RARE, mage.cards.h.HarvestSeason.class));
        cards.add(new SetCardInfo("Haze of Pollen", 171, Rarity.COMMON, mage.cards.h.HazeOfPollen.class));
        cards.add(new SetCardInfo("Hazoret the Fervent", 136, Rarity.MYTHIC, mage.cards.h.HazoretTheFervent.class));
        cards.add(new SetCardInfo("Hazoret's Favor", 137, Rarity.RARE, mage.cards.h.HazoretsFavor.class));
        cards.add(new SetCardInfo("Hazoret's Monument", 229, Rarity.UNCOMMON, mage.cards.h.HazoretsMonument.class));
        cards.add(new SetCardInfo("Heart-Piercer Manticore", 138, Rarity.RARE, mage.cards.h.HeartPiercerManticore.class));
        cards.add(new SetCardInfo("Heaven // Earth", 224, Rarity.RARE, mage.cards.h.HeavenEarth.class));
        cards.add(new SetCardInfo("Hekma Sentinels", 56, Rarity.COMMON, mage.cards.h.HekmaSentinels.class));
        cards.add(new SetCardInfo("Hieroglyphic Illumination", 57, Rarity.COMMON, mage.cards.h.HieroglyphicIllumination.class));
        cards.add(new SetCardInfo("Highland Lake", 282, Rarity.COMMON, mage.cards.h.HighlandLake.class));
        cards.add(new SetCardInfo("Honed Khopesh", 230, Rarity.COMMON, mage.cards.h.HonedKhopesh.class));
        cards.add(new SetCardInfo("Honored Crop-Captain", 200, Rarity.UNCOMMON, mage.cards.h.HonoredCropCaptain.class));
        cards.add(new SetCardInfo("Honored Hydra", 172, Rarity.RARE, mage.cards.h.HonoredHydra.class));
        cards.add(new SetCardInfo("Hooded Brawler", 173, Rarity.COMMON, mage.cards.h.HoodedBrawler.class));
        cards.add(new SetCardInfo("Horror of the Broken Lands", 95, Rarity.COMMON, mage.cards.h.HorrorOfTheBrokenLands.class));
        cards.add(new SetCardInfo("Hyena Pack", 139, Rarity.COMMON, mage.cards.h.HyenaPack.class));
        cards.add(new SetCardInfo("Illusory Wrappings", 58, Rarity.COMMON, mage.cards.i.IllusoryWrappings.class));
        cards.add(new SetCardInfo("Impeccable Timing", 18, Rarity.COMMON, mage.cards.i.ImpeccableTiming.class));
        cards.add(new SetCardInfo("In Oketra's Name", 19, Rarity.COMMON, mage.cards.i.InOketrasName.class));
        cards.add(new SetCardInfo("Initiate's Companion", 174, Rarity.COMMON, mage.cards.i.InitiatesCompanion.class));
        cards.add(new SetCardInfo("Insult // Injury", 213, Rarity.RARE, mage.cards.i.InsultInjury.class));
        cards.add(new SetCardInfo("Irrigated Farmland", 245, Rarity.RARE, mage.cards.i.IrrigatedFarmland.class));
        cards.add(new SetCardInfo("Island", 251, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 258, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 259, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 260, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kefnet the Mindful", 59, Rarity.MYTHIC, mage.cards.k.KefnetTheMindful.class));
        cards.add(new SetCardInfo("Kefnet's Monument", 231, Rarity.UNCOMMON, mage.cards.k.KefnetsMonument.class));
        cards.add(new SetCardInfo("Khenra Charioteer", 201, Rarity.UNCOMMON, mage.cards.k.KhenraCharioteer.class));
        cards.add(new SetCardInfo("Labyrinth Guardian", 60, Rarity.UNCOMMON, mage.cards.l.LabyrinthGuardian.class));
        cards.add(new SetCardInfo("Lay Bare the Heart", 96, Rarity.UNCOMMON, mage.cards.l.LayBareTheHeart.class));
        cards.add(new SetCardInfo("Lay Claim", 61, Rarity.UNCOMMON, mage.cards.l.LayClaim.class));
        cards.add(new SetCardInfo("Liliana's Influence", 277, Rarity.RARE, mage.cards.l.LilianasInfluence.class));
        cards.add(new SetCardInfo("Liliana's Mastery", 98, Rarity.RARE, mage.cards.l.LilianasMastery.class));
        cards.add(new SetCardInfo("Liliana, Death Wielder", 275, Rarity.MYTHIC, mage.cards.l.LilianaDeathWielder.class));
        cards.add(new SetCardInfo("Liliana, Death's Majesty", 97, Rarity.MYTHIC, mage.cards.l.LilianaDeathsMajesty.class));
        cards.add(new SetCardInfo("Limits of Solidarity", 140, Rarity.UNCOMMON, mage.cards.l.LimitsOfSolidarity.class));
        cards.add(new SetCardInfo("Lord of the Accursed", 99, Rarity.UNCOMMON, mage.cards.l.LordOfTheAccursed.class));
        cards.add(new SetCardInfo("Luxa River Shrine", 232, Rarity.COMMON, mage.cards.l.LuxaRiverShrine.class));
        cards.add(new SetCardInfo("Magma Spray", 141, Rarity.COMMON, mage.cards.m.MagmaSpray.class));
        cards.add(new SetCardInfo("Manglehorn", 175, Rarity.UNCOMMON, mage.cards.m.Manglehorn.class));
        cards.add(new SetCardInfo("Manticore of the Gauntlet", 142, Rarity.COMMON, mage.cards.m.ManticoreOfTheGauntlet.class));
        cards.add(new SetCardInfo("Meandering River", 283, Rarity.COMMON, mage.cards.m.MeanderingRiver.class));
        cards.add(new SetCardInfo("Merciless Javelineer", 202, Rarity.UNCOMMON, mage.cards.m.MercilessJavelineer.class));
        cards.add(new SetCardInfo("Miasmic Mummy", 100, Rarity.COMMON, mage.cards.m.MiasmicMummy.class));
        cards.add(new SetCardInfo("Mighty Leap", 20, Rarity.COMMON, mage.cards.m.MightyLeap.class));
        cards.add(new SetCardInfo("Minotaur Sureshot", 143, Rarity.COMMON, mage.cards.m.MinotaurSureshot.class));
        cards.add(new SetCardInfo("Mountain", 253, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 264, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 265, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 266, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mouth // Feed", 214, Rarity.RARE, mage.cards.m.MouthFeed.class));
        cards.add(new SetCardInfo("Naga Oracle", 62, Rarity.COMMON, mage.cards.n.NagaOracle.class));
        cards.add(new SetCardInfo("Naga Vitalist", 176, Rarity.COMMON, mage.cards.n.NagaVitalist.class));
        cards.add(new SetCardInfo("Nef-Crop Entangler", 144, Rarity.COMMON, mage.cards.n.NefCropEntangler.class));
        cards.add(new SetCardInfo("Neheb, the Worthy", 203, Rarity.RARE, mage.cards.n.NehebTheWorthy.class));
        cards.add(new SetCardInfo("Nest of Scarabs", 101, Rarity.UNCOMMON, mage.cards.n.NestOfScarabs.class));
        cards.add(new SetCardInfo("Never // Return", 212, Rarity.RARE, mage.cards.n.NeverReturn.class));
        cards.add(new SetCardInfo("New Perspectives", 63, Rarity.RARE, mage.cards.n.NewPerspectives.class));
        cards.add(new SetCardInfo("Nimble-Blade Khenra", 145, Rarity.COMMON, mage.cards.n.NimbleBladeKhenra.class));
        cards.add(new SetCardInfo("Nissa, Steward of Elements", 204, Rarity.MYTHIC, mage.cards.n.NissaStewardOfElements.class));
        cards.add(new SetCardInfo("Oashra Cultivator", 177, Rarity.COMMON, mage.cards.o.OashraCultivator.class));
        cards.add(new SetCardInfo("Oketra the True", 21, Rarity.MYTHIC, mage.cards.o.OketraTheTrue.class));
        cards.add(new SetCardInfo("Oketra's Attendant", 22, Rarity.UNCOMMON, mage.cards.o.OketrasAttendant.class));
        cards.add(new SetCardInfo("Oketra's Monument", 233, Rarity.UNCOMMON, mage.cards.o.OketrasMonument.class));
        cards.add(new SetCardInfo("Onward // Victory", 218, Rarity.UNCOMMON, mage.cards.o.OnwardVictory.class));
        cards.add(new SetCardInfo("Open into Wonder", 64, Rarity.UNCOMMON, mage.cards.o.OpenIntoWonder.class));
        cards.add(new SetCardInfo("Oracle's Vault", 234, Rarity.RARE, mage.cards.o.OraclesVault.class));
        cards.add(new SetCardInfo("Ornery Kudu", 178, Rarity.COMMON, mage.cards.o.OrneryKudu.class));
        cards.add(new SetCardInfo("Painful Lesson", 102, Rarity.COMMON, mage.cards.p.PainfulLesson.class));
        cards.add(new SetCardInfo("Painted Bluffs", 246, Rarity.COMMON, mage.cards.p.PaintedBluffs.class));
        cards.add(new SetCardInfo("Pathmaker Initiate", 146, Rarity.COMMON, mage.cards.p.PathmakerInitiate.class));
        cards.add(new SetCardInfo("Pitiless Vizier", 103, Rarity.COMMON, mage.cards.p.PitilessVizier.class));
        cards.add(new SetCardInfo("Plague Belcher", 104, Rarity.RARE, mage.cards.p.PlagueBelcher.class));
        cards.add(new SetCardInfo("Pyramid of the Pantheon", 235, Rarity.RARE, mage.cards.p.PyramidOfThePantheon.class));
        cards.add(new SetCardInfo("Plains", 250, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 255, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 256, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 257, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pouncing Cheetah", 179, Rarity.COMMON, mage.cards.p.PouncingCheetah.class));
        cards.add(new SetCardInfo("Prepare // Fight", 220, Rarity.RARE, mage.cards.p.PrepareFight.class));
        cards.add(new SetCardInfo("Protection of the Hekma", 23, Rarity.UNCOMMON, mage.cards.p.ProtectionOfTheHekma.class));
        cards.add(new SetCardInfo("Prowling Serpopard", 180, Rarity.RARE, mage.cards.p.ProwlingSerpopard.class));
        cards.add(new SetCardInfo("Pull from Tomorrow", 65, Rarity.RARE, mage.cards.p.PullFromTomorrow.class));
        cards.add(new SetCardInfo("Pursue Glory", 147, Rarity.COMMON, mage.cards.p.PursueGlory.class));
        cards.add(new SetCardInfo("Quarry Hauler", 181, Rarity.COMMON, mage.cards.q.QuarryHauler.class));
        cards.add(new SetCardInfo("Rags // Riches", 222, Rarity.RARE, mage.cards.r.RagsRiches.class));
        cards.add(new SetCardInfo("Reduce // Rubble", 216, Rarity.UNCOMMON, mage.cards.r.ReduceRubble.class));
        cards.add(new SetCardInfo("Regal Caracal", 24, Rarity.RARE, mage.cards.r.RegalCaracal.class));
        cards.add(new SetCardInfo("Renewed Faith", 25, Rarity.UNCOMMON, mage.cards.r.RenewedFaith.class));
        cards.add(new SetCardInfo("Rhet-Crop Spearmaster", 26, Rarity.COMMON, mage.cards.r.RhetCropSpearmaster.class));
        cards.add(new SetCardInfo("Rhonas's Monument", 236, Rarity.UNCOMMON, mage.cards.r.RhonassMonument.class));
        cards.add(new SetCardInfo("Rhonas the Indomitable", 182, Rarity.MYTHIC, mage.cards.r.RhonasTheIndomitable.class));
        cards.add(new SetCardInfo("River Serpent", 66, Rarity.COMMON, mage.cards.r.RiverSerpent.class));
        cards.add(new SetCardInfo("Ruthless Sniper", 105, Rarity.UNCOMMON, mage.cards.r.RuthlessSniper.class));
        cards.add(new SetCardInfo("Sacred Cat", 27, Rarity.COMMON, mage.cards.s.SacredCat.class));
        cards.add(new SetCardInfo("Sacred Excavation", 67, Rarity.UNCOMMON, mage.cards.s.SacredExcavation.class));
        cards.add(new SetCardInfo("Samut, Voice of Dissent", 205, Rarity.MYTHIC, mage.cards.s.SamutVoiceOfDissent.class));
        cards.add(new SetCardInfo("Sandwurm Convergence", 183, Rarity.RARE, mage.cards.s.SandwurmConvergence.class));
        cards.add(new SetCardInfo("Scaled Behemoth", 184, Rarity.UNCOMMON, mage.cards.s.ScaledBehemoth.class));
        cards.add(new SetCardInfo("Scarab Feast", 106, Rarity.COMMON, mage.cards.s.ScarabFeast.class));
        cards.add(new SetCardInfo("Scattered Groves", 247, Rarity.RARE, mage.cards.s.ScatteredGroves.class));
        cards.add(new SetCardInfo("Scribe of the Mindful", 68, Rarity.COMMON, mage.cards.s.ScribeOfTheMindful.class));
        cards.add(new SetCardInfo("Seeker of Insight", 69, Rarity.COMMON, mage.cards.s.SeekerOfInsight.class));
        cards.add(new SetCardInfo("Seraph of the Suns", 28, Rarity.UNCOMMON, mage.cards.s.SeraphOfTheSuns.class));
        cards.add(new SetCardInfo("Shadow of the Grave", 107, Rarity.RARE, mage.cards.s.ShadowOfTheGrave.class));
        cards.add(new SetCardInfo("Shadowstorm Vizier", 206, Rarity.UNCOMMON, mage.cards.s.ShadowstormVizier.class));
        cards.add(new SetCardInfo("Shed Weakness", 185, Rarity.COMMON, mage.cards.s.ShedWeakness.class));
        cards.add(new SetCardInfo("Shefet Monitor", 186, Rarity.UNCOMMON, mage.cards.s.ShefetMonitor.class));
        cards.add(new SetCardInfo("Sheltered Thicket", 248, Rarity.RARE, mage.cards.s.ShelteredThicket.class));
        cards.add(new SetCardInfo("Shimmerscale Drake", 70, Rarity.COMMON, mage.cards.s.ShimmerscaleDrake.class));
        cards.add(new SetCardInfo("Sixth Sense", 187, Rarity.UNCOMMON, mage.cards.s.SixthSense.class));
        cards.add(new SetCardInfo("Slither Blade", 71, Rarity.COMMON, mage.cards.s.SlitherBlade.class));
        cards.add(new SetCardInfo("Soul-Scar Mage", 148, Rarity.RARE, mage.cards.s.SoulScarMage.class));
        cards.add(new SetCardInfo("Soulstinger", 108, Rarity.COMMON, mage.cards.s.Soulstinger.class));
        cards.add(new SetCardInfo("Sparring Mummy", 29, Rarity.COMMON, mage.cards.s.SparringMummy.class));
        cards.add(new SetCardInfo("Spidery Grasp", 188, Rarity.COMMON, mage.cards.s.SpideryGrasp.class));
        cards.add(new SetCardInfo("Splendid Agony", 109, Rarity.COMMON, mage.cards.s.SplendidAgony.class));
        cards.add(new SetCardInfo("Spring // Mind", 219, Rarity.UNCOMMON, mage.cards.s.SpringMind.class));
        cards.add(new SetCardInfo("Start // Finish", 215, Rarity.UNCOMMON, mage.cards.s.StartFinish.class));
        cards.add(new SetCardInfo("Stinging Shot", 189, Rarity.COMMON, mage.cards.s.StingingShot.class));
        cards.add(new SetCardInfo("Stir the Sands", 110, Rarity.UNCOMMON, mage.cards.s.StirTheSands.class));
        cards.add(new SetCardInfo("Stone Quarry", 274, Rarity.COMMON, mage.cards.s.StoneQuarry.class));
        cards.add(new SetCardInfo("Submerged Boneyard", 284, Rarity.COMMON, mage.cards.s.SubmergedBoneyard.class));
        cards.add(new SetCardInfo("Sunscorched Desert", 249, Rarity.COMMON, mage.cards.s.SunscorchedDesert.class));
        cards.add(new SetCardInfo("Supernatural Stamina", 111, Rarity.COMMON, mage.cards.s.SupernaturalStamina.class));
        cards.add(new SetCardInfo("Supply Caravan", 30, Rarity.COMMON, mage.cards.s.SupplyCaravan.class));
        cards.add(new SetCardInfo("Swamp", 252, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 261, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 262, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 263, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sweltering Suns", 149, Rarity.RARE, mage.cards.s.SwelteringSuns.class));
        cards.add(new SetCardInfo("Synchronized Strike", 190, Rarity.UNCOMMON, mage.cards.s.SynchronizedStrike.class));
        cards.add(new SetCardInfo("Tah-Crop Elite", 31, Rarity.COMMON, mage.cards.t.TahCropElite.class));
        cards.add(new SetCardInfo("Tah-Crop Skirmisher", 72, Rarity.COMMON, mage.cards.t.TahCropSkirmisher.class));
        cards.add(new SetCardInfo("Tattered Mummy", 278, Rarity.COMMON, mage.cards.t.TatteredMummy.class));
        cards.add(new SetCardInfo("Temmet, Vizier of Naktamun", 207, Rarity.RARE, mage.cards.t.TemmetVizierOfNaktamun.class));
        cards.add(new SetCardInfo("Those Who Serve", 32, Rarity.COMMON, mage.cards.t.ThoseWhoServe.class));
        cards.add(new SetCardInfo("Thresher Lizard", 150, Rarity.COMMON, mage.cards.t.ThresherLizard.class));
        cards.add(new SetCardInfo("Throne of the God-Pharaoh", 237, Rarity.RARE, mage.cards.t.ThroneOfTheGodPharaoh.class));
        cards.add(new SetCardInfo("Timber Gorge", 285, Rarity.COMMON, mage.cards.t.TimberGorge.class));
        cards.add(new SetCardInfo("Time to Reflect", 33, Rarity.UNCOMMON, mage.cards.t.TimeToReflect.class));
        cards.add(new SetCardInfo("Tormenting Voice", 151, Rarity.COMMON, mage.cards.t.TormentingVoice.class));
        cards.add(new SetCardInfo("Tranquil Expanse", 286, Rarity.COMMON, mage.cards.t.TranquilExpanse.class));
        cards.add(new SetCardInfo("Trespasser's Curse", 112, Rarity.COMMON, mage.cards.t.TrespassersCurse.class));
        cards.add(new SetCardInfo("Trial of Ambition", 113, Rarity.UNCOMMON, mage.cards.t.TrialOfAmbition.class));
        cards.add(new SetCardInfo("Trial of Knowledge", 73, Rarity.UNCOMMON, mage.cards.t.TrialOfKnowledge.class));
        cards.add(new SetCardInfo("Trial of Solidarity", 34, Rarity.UNCOMMON, mage.cards.t.TrialOfSolidarity.class));
        cards.add(new SetCardInfo("Trial of Strength", 191, Rarity.UNCOMMON, mage.cards.t.TrialOfStrength.class));
        cards.add(new SetCardInfo("Trial of Zeal", 152, Rarity.UNCOMMON, mage.cards.t.TrialOfZeal.class));
        cards.add(new SetCardInfo("Trueheart Duelist", 35, Rarity.UNCOMMON, mage.cards.t.TrueheartDuelist.class));
        cards.add(new SetCardInfo("Trueheart Twins", 153, Rarity.UNCOMMON, mage.cards.t.TrueheartTwins.class));
        cards.add(new SetCardInfo("Unburden", 114, Rarity.COMMON, mage.cards.u.Unburden.class));
        cards.add(new SetCardInfo("Unwavering Initiate", 36, Rarity.COMMON, mage.cards.u.UnwaveringInitiate.class));
        cards.add(new SetCardInfo("Violent Impact", 154, Rarity.COMMON, mage.cards.v.ViolentImpact.class));
        cards.add(new SetCardInfo("Vizier of Deferment", 37, Rarity.UNCOMMON, mage.cards.v.VizierOfDeferment.class));
        cards.add(new SetCardInfo("Vizier of Many Faces", 74, Rarity.RARE, mage.cards.v.VizierOfManyFaces.class));
        cards.add(new SetCardInfo("Vizier of Remedies", 38, Rarity.UNCOMMON, mage.cards.v.VizierOfRemedies.class));
        cards.add(new SetCardInfo("Vizier of Tumbling Sands", 75, Rarity.UNCOMMON, mage.cards.v.VizierOfTumblingSands.class));
        cards.add(new SetCardInfo("Vizier of the Menagerie", 192, Rarity.MYTHIC, mage.cards.v.VizierOfTheMenagerie.class));
        cards.add(new SetCardInfo("Wander in Death", 115, Rarity.COMMON, mage.cards.w.WanderInDeath.class));
        cards.add(new SetCardInfo("Warfire Javelineer", 155, Rarity.UNCOMMON, mage.cards.w.WarfireJavelineer.class));
        cards.add(new SetCardInfo("Wasteland Scorpion", 116, Rarity.COMMON, mage.cards.w.WastelandScorpion.class));
        cards.add(new SetCardInfo("Watchers of the Dead", 238, Rarity.UNCOMMON, mage.cards.w.WatchersOfTheDead.class));
        cards.add(new SetCardInfo("Watchful Naga", 193, Rarity.UNCOMMON, mage.cards.w.WatchfulNaga.class));
        cards.add(new SetCardInfo("Wayward Servant", 208, Rarity.UNCOMMON, mage.cards.w.WaywardServant.class));
        cards.add(new SetCardInfo("Weaver of Currents", 209, Rarity.UNCOMMON, mage.cards.w.WeaverOfCurrents.class));
        cards.add(new SetCardInfo("Winds of Rebuke", 76, Rarity.COMMON, mage.cards.w.WindsOfRebuke.class));
        cards.add(new SetCardInfo("Winged Shepherd", 39, Rarity.COMMON, mage.cards.w.WingedShepherd.class));
        cards.add(new SetCardInfo("Woodland Stream", 287, Rarity.COMMON, mage.cards.w.WoodlandStream.class));
        cards.add(new SetCardInfo("Zenith Seeker", 77, Rarity.UNCOMMON, mage.cards.z.ZenithSeeker.class));
    }

    @Override
    protected List<CardInfo> findCardsByRarity(Rarity rarity) {
        List<CardInfo> cardInfos = super.findCardsByRarity(rarity);
        if (rarity == Rarity.SPECIAL) {
            cardInfos.addAll(CardRepository.instance.findCards(new CardCriteria().setCodes("MP2").maxCardNumber(30)));
        }
        return cardInfos;
    }

    @Override
    protected void generateBoosterMap() {
        super.generateBoosterMap();
        CardRepository
                .instance
                .findCards(new CardCriteria().setCodes("MP2").maxCardNumber(30))
                .stream()
                .forEach(cardInfo -> inBoosterMap.put("MP2_" + cardInfo.getCardNumber(), cardInfo));
    }

    @Override
    public BoosterCollator createCollator() {
        return new AmonkhetCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/akh.html
// Using USA collation for all rarities
// Foil rare sheet used for regular rares as regular rare sheet is not known
class AmonkhetCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "145", "29", "62", "151", "30", "58", "142", "39", "52", "128", "13", "69", "146", "26", "57", "132", "20", "40", "143", "3", "72", "120", "27", "47", "122", "7", "45", "145", "12", "44", "150", "11", "62", "124", "29", "56", "142", "30", "52", "151", "39", "58", "146", "20", "40", "128", "13", "47", "150", "26", "69", "132", "3", "57", "124", "7", "45", "122", "27", "44", "120", "12", "72", "143", "11", "56");
    private final CardRun commonB = new CardRun(true, "166", "85", "185", "87", "178", "100", "161", "106", "156", "103", "174", "89", "168", "115", "188", "80", "171", "91", "181", "114", "185", "108", "179", "85", "161", "106", "178", "87", "156", "100", "166", "89", "174", "103", "188", "80", "171", "91", "185", "114", "181", "106", "179", "115", "168", "100", "166", "89", "178", "85", "156", "108", "161", "87", "174", "103", "188", "80", "171", "91", "181", "115", "168", "114", "179", "108");
    private final CardRun commonC1 = new CardRun(true, "230", "157", "144", "83", "70", "241", "177", "129", "102", "71", "19", "17", "119", "31", "53", "167", "116", "130", "32", "68", "111", "189", "141", "19", "43", "246", "157", "154", "6", "50", "230", "83", "144", "177", "70", "119", "102", "130", "241", "53", "189", "116", "129", "32", "71", "167", "31", "154", "246", "43", "17", "111", "141", "50", "68");
    private final CardRun commonC2 = new CardRun(true, "164", "109", "139", "173", "232", "9", "66", "95", "249", "176", "147", "36", "112", "92", "76", "6", "164", "158", "242", "18", "139", "95", "232", "173", "66", "36", "249", "109", "147", "176", "112", "9", "76", "92", "164", "158", "139", "18", "232", "95", "242", "173", "66", "9", "249", "109", "147", "176", "76", "36", "112", "92", "242", "158", "18");
    private final CardRun uncommonA = new CardRun(true, "33", "48", "191", "105", "231", "208", "126", "113", "67", "201", "123", "10", "228", "187", "101", "225", "198", "35", "193", "105", "218", "131", "23", "64", "227", "175", "99", "215", "38", "190", "90", "208", "126", "10", "60", "236", "191", "81", "201", "35", "229", "113", "198", "155", "33", "46", "231", "175", "90", "218", "38", "193", "67", "118", "23", "77", "190", "101", "227", "215", "123", "48", "191", "225", "153", "35", "60", "169", "99", "229", "208", "131", "67", "228", "187", "155", "33", "46", "193", "81", "236", "201", "126", "64", "175", "231", "118", "10", "77", "169", "101", "227", "198", "153", "48", "90", "228", "131", "23", "60", "190", "105", "225", "218", "123", "46", "99", "229", "155", "38", "77", "187", "81", "236", "215", "118", "64", "113", "169", "153");
    private final CardRun uncommonB = new CardRun(true, "25", "140", "202", "163", "238", "206", "28", "54", "152", "197", "165", "110", "244", "217", "93", "233", "194", "75", "8", "121", "200", "162", "37", "41", "96", "186", "226", "219", "34", "61", "127", "216", "163", "94", "117", "209", "79", "238", "202", "22", "73", "140", "195", "184", "25", "93", "206", "110", "194", "28", "54", "152", "197", "162", "37", "41", "200", "165", "233", "217", "8", "75", "117", "209", "186", "22", "121", "219", "96", "226", "195", "34", "61", "127", "216", "184", "94", "73", "244", "79", "238", "202", "25", "54", "140", "194", "163", "8", "121", "206", "165", "110", "200", "22", "75", "152", "197", "162", "28", "93", "209", "96", "233", "217", "37", "41", "117", "195", "186", "94", "61", "244", "79", "226", "219", "34", "73", "127", "216", "184");
    private final CardRun rare = new CardRun(true, "65", "222", "148", "243", "235", "5", "196", "82", "98", "224", "16", "49", "199", "134", "239", "14", "15", "203", "213", "78", "234", "2", "211", "59", "135", "240", "207", "55", "221", "137", "104", "1", "24", "51", "159", "138", "245", "107", "63", "125", "149", "86", "243", "4", "74", "172", "213", "182", "88", "211", "180", "148", "212", "196", "210", "84", "183", "137", "247", "98", "65", "170", "134", "204", "220", "5", "78", "160", "135", "239", "104", "42", "214", "138", "2", "235", "15", "107", "180", "133", "248", "203", "49", "159", "149", "16", "237", "192", "86", "170", "223", "240", "222", "55", "172", "21", "4", "245", "224", "212", "183", "199", "247", "97", "51", "214", "220", "24", "234", "63", "88", "205", "207", "248", "221", "74", "160", "237", "210", "136", "223");
    private final CardRun masterpiece = new CardRun(false, "MP2_1", "MP2_2", "MP2_3", "MP2_4", "MP2_5", "MP2_6", "MP2_7", "MP2_8", "MP2_9", "MP2_10", "MP2_11", "MP2_12", "MP2_13", "MP2_14", "MP2_15", "MP2_16", "MP2_17", "MP2_18", "MP2_19", "MP2_20", "MP2_21", "MP2_22", "MP2_23", "MP2_24", "MP2_25", "MP2_26", "MP2_27", "MP2_28", "MP2_29", "MP2_30");
    private final CardRun land = new CardRun(false, "250", "251", "252", "253", "254", "255", "256", "257", "258", "259", "260", "261", "262", "263", "264", "265", "266", "267", "268", "269");

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
    // 3.27 A commons (36 / 11)  (rounded to 418/128)
    // 2.18 B commons (24 / 11)  (rounded to 280/128)
    // 2.73 C1 commons (30 / 11) (rounded to 349/128)
    // 1.82 C2 commons (20 / 11) (rounded to 232/128)
    // These numbers are the same for all sets with 101 commons in A/B/C1/C2 print runs
    // and with 10 common slots per booster
    private final RarityConfiguration commonRuns = new RarityConfiguration(
            AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1, AABBC1C1C1C1C1C1,

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
            AAAABBC2C2C2C2, AAAABBC2C2C2C2,

            AAAABBBBC2C2, AAAABBBBC2C2, AAAABBBBC2C2, AAAABBBBC2C2, AAAABBBBC2C2, AAAABBBBC2C2,
            AAAABBBBC2C2, AAAABBBBC2C2, AAAABBBBC2C2, AAAABBBBC2C2, AAAABBBBC2C2, AAAABBBBC2C2
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
