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
public final class Bloomburrow extends ExpansionSet {

    private static final Bloomburrow instance = new Bloomburrow();

    public static Bloomburrow getInstance() {
        return instance;
    }

    private Bloomburrow() {
        super("Bloomburrow", "BLB", ExpansionSet.buildDate(2024, 8, 2), SetType.EXPANSION);
        this.blockName = "Bloomburrow"; // for sorting in GUI
        this.hasBasicLands = true;
        this.rotationSet = true;
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Agate Assault", 122, Rarity.COMMON, mage.cards.a.AgateAssault.class));
        cards.add(new SetCardInfo("Agate-Blade Assassin", 82, Rarity.COMMON, mage.cards.a.AgateBladeAssassin.class));
        cards.add(new SetCardInfo("Alania's Pathmaker", 123, Rarity.COMMON, mage.cards.a.AlaniasPathmaker.class));
        cards.add(new SetCardInfo("Alania, Divergent Storm", 204, Rarity.RARE, mage.cards.a.AlaniaDivergentStorm.class));
        cards.add(new SetCardInfo("Artist's Talent", 124, Rarity.RARE, mage.cards.a.ArtistsTalent.class));
        cards.add(new SetCardInfo("Azure Beastbinder", 41, Rarity.RARE, mage.cards.a.AzureBeastbinder.class));
        cards.add(new SetCardInfo("Bakersbane Duo", 163, Rarity.COMMON, mage.cards.b.BakersbaneDuo.class));
        cards.add(new SetCardInfo("Bandit's Talent", 83, Rarity.UNCOMMON, mage.cards.b.BanditsTalent.class));
        cards.add(new SetCardInfo("Banishing Light", 1, Rarity.COMMON, mage.cards.b.BanishingLight.class));
        cards.add(new SetCardInfo("Bark-Knuckle Boxer", 164, Rarity.UNCOMMON, mage.cards.b.BarkKnuckleBoxer.class));
        cards.add(new SetCardInfo("Barkform Harvester", 243, Rarity.COMMON, mage.cards.b.BarkformHarvester.class));
        cards.add(new SetCardInfo("Baylen, the Haymaker", 205, Rarity.RARE, mage.cards.b.BaylenTheHaymaker.class));
        cards.add(new SetCardInfo("Bellowing Crier", 42, Rarity.COMMON, mage.cards.b.BellowingCrier.class));
        cards.add(new SetCardInfo("Beza, the Bounding Spring", 2, Rarity.MYTHIC, mage.cards.b.BezaTheBoundingSpring.class));
        cards.add(new SetCardInfo("Blacksmith's Talent", 125, Rarity.UNCOMMON, mage.cards.b.BlacksmithsTalent.class));
        cards.add(new SetCardInfo("Blooming Blast", 126, Rarity.UNCOMMON, mage.cards.b.BloomingBlast.class));
        cards.add(new SetCardInfo("Blossoming Sands", 396, Rarity.COMMON, mage.cards.b.BlossomingSands.class));
        cards.add(new SetCardInfo("Bonebind Orator", 84, Rarity.COMMON, mage.cards.b.BonebindOrator.class));
        cards.add(new SetCardInfo("Bonecache Overseer", 85, Rarity.UNCOMMON, mage.cards.b.BonecacheOverseer.class));
        cards.add(new SetCardInfo("Brambleguard Captain", 127, Rarity.UNCOMMON, mage.cards.b.BrambleguardCaptain.class));
        cards.add(new SetCardInfo("Brambleguard Veteran", 165, Rarity.UNCOMMON, mage.cards.b.BrambleguardVeteran.class));
        cards.add(new SetCardInfo("Brave-Kin Duo", 3, Rarity.COMMON, mage.cards.b.BraveKinDuo.class));
        cards.add(new SetCardInfo("Brazen Collector", 128, Rarity.UNCOMMON, mage.cards.b.BrazenCollector.class));
        cards.add(new SetCardInfo("Bria, Riptide Rogue", 379, Rarity.MYTHIC, mage.cards.b.BriaRiptideRogue.class));
        cards.add(new SetCardInfo("Brightblade Stoat", 4, Rarity.UNCOMMON, mage.cards.b.BrightbladeStoat.class));
        cards.add(new SetCardInfo("Builder's Talent", 5, Rarity.UNCOMMON, mage.cards.b.BuildersTalent.class));
        cards.add(new SetCardInfo("Bumbleflower's Sharepot", 244, Rarity.COMMON, mage.cards.b.BumbleflowersSharepot.class));
        cards.add(new SetCardInfo("Burrowguard Mentor", 206, Rarity.UNCOMMON, mage.cards.b.BurrowguardMentor.class));
        cards.add(new SetCardInfo("Bushy Bodyguard", 166, Rarity.UNCOMMON, mage.cards.b.BushyBodyguard.class));
        cards.add(new SetCardInfo("Byrke, Long Ear of the Law", 380, Rarity.MYTHIC, mage.cards.b.ByrkeLongEarOfTheLaw.class));
        cards.add(new SetCardInfo("Byway Barterer", 129, Rarity.RARE, mage.cards.b.BywayBarterer.class));
        cards.add(new SetCardInfo("Cache Grab", 167, Rarity.COMMON, mage.cards.c.CacheGrab.class));
        cards.add(new SetCardInfo("Calamitous Tide", 43, Rarity.UNCOMMON, mage.cards.c.CalamitousTide.class));
        cards.add(new SetCardInfo("Camellia, the Seedmiser", 207, Rarity.RARE, mage.cards.c.CamelliaTheSeedmiser.class));
        cards.add(new SetCardInfo("Caretaker's Talent", 6, Rarity.RARE, mage.cards.c.CaretakersTalent.class));
        cards.add(new SetCardInfo("Carrot Cake", 7, Rarity.COMMON, mage.cards.c.CarrotCake.class));
        cards.add(new SetCardInfo("Charmed Sleep", 388, Rarity.COMMON, mage.cards.c.CharmedSleep.class));
        cards.add(new SetCardInfo("Cindering Cutthroat", 208, Rarity.COMMON, mage.cards.c.CinderingCutthroat.class));
        cards.add(new SetCardInfo("Clifftop Lookout", 168, Rarity.UNCOMMON, mage.cards.c.ClifftopLookout.class));
        cards.add(new SetCardInfo("Coiling Rebirth", 86, Rarity.RARE, mage.cards.c.CoilingRebirth.class));
        cards.add(new SetCardInfo("Colossification", 392, Rarity.RARE, mage.cards.c.Colossification.class));
        cards.add(new SetCardInfo("Conduct Electricity", 130, Rarity.COMMON, mage.cards.c.ConductElectricity.class));
        cards.add(new SetCardInfo("Consumed by Greed", 87, Rarity.UNCOMMON, mage.cards.c.ConsumedByGreed.class));
        cards.add(new SetCardInfo("Corpseberry Cultivator", 210, Rarity.COMMON, mage.cards.c.CorpseberryCultivator.class));
        cards.add(new SetCardInfo("Coruscation Mage", 131, Rarity.UNCOMMON, mage.cards.c.CoruscationMage.class));
        cards.add(new SetCardInfo("Cruelclaw's Heist", 88, Rarity.RARE, mage.cards.c.CruelclawsHeist.class));
        cards.add(new SetCardInfo("Crumb and Get It", 8, Rarity.COMMON, mage.cards.c.CrumbAndGetIt.class));
        cards.add(new SetCardInfo("Curious Forager", 169, Rarity.UNCOMMON, mage.cards.c.CuriousForager.class));
        cards.add(new SetCardInfo("Daggerfang Duo", 89, Rarity.COMMON, mage.cards.d.DaggerfangDuo.class));
        cards.add(new SetCardInfo("Daring Waverider", 44, Rarity.UNCOMMON, mage.cards.d.DaringWaverider.class));
        cards.add(new SetCardInfo("Darkstar Augur", 90, Rarity.RARE, mage.cards.d.DarkstarAugur.class));
        cards.add(new SetCardInfo("Dawn's Truce", 9, Rarity.RARE, mage.cards.d.DawnsTruce.class));
        cards.add(new SetCardInfo("Dazzling Denial", 45, Rarity.COMMON, mage.cards.d.DazzlingDenial.class));
        cards.add(new SetCardInfo("Dewdrop Cure", 10, Rarity.UNCOMMON, mage.cards.d.DewdropCure.class));
        cards.add(new SetCardInfo("Dire Downdraft", 46, Rarity.COMMON, mage.cards.d.DireDowndraft.class));
        cards.add(new SetCardInfo("Diresight", 91, Rarity.COMMON, mage.cards.d.Diresight.class));
        cards.add(new SetCardInfo("Dour Port-Mage", 47, Rarity.RARE, mage.cards.d.DourPortMage.class));
        cards.add(new SetCardInfo("Downwind Ambusher", 92, Rarity.UNCOMMON, mage.cards.d.DownwindAmbusher.class));
        cards.add(new SetCardInfo("Dragonhawk, Fate's Tempest", 132, Rarity.MYTHIC, mage.cards.d.DragonhawkFatesTempest.class));
        cards.add(new SetCardInfo("Dreamdew Entrancer", 211, Rarity.RARE, mage.cards.d.DreamdewEntrancer.class));
        cards.add(new SetCardInfo("Driftgloom Coyote", 11, Rarity.UNCOMMON, mage.cards.d.DriftgloomCoyote.class));
        cards.add(new SetCardInfo("Druid of the Spade", 170, Rarity.COMMON, mage.cards.d.DruidOfTheSpade.class));
        cards.add(new SetCardInfo("Early Winter", 93, Rarity.COMMON, mage.cards.e.EarlyWinter.class));
        cards.add(new SetCardInfo("Eddymurk Crab", 48, Rarity.UNCOMMON, mage.cards.e.EddymurkCrab.class));
        cards.add(new SetCardInfo("Eluge, the Shoreless Sea", 49, Rarity.MYTHIC, mage.cards.e.ElugeTheShorelessSea.class));
        cards.add(new SetCardInfo("Emberheart Challenger", 133, Rarity.RARE, mage.cards.e.EmberheartChallenger.class));
        cards.add(new SetCardInfo("Essence Channeler", 12, Rarity.RARE, mage.cards.e.EssenceChanneler.class));
        cards.add(new SetCardInfo("Fabled Passage", 252, Rarity.RARE, mage.cards.f.FabledPassage.class));
        cards.add(new SetCardInfo("Feather of Flight", 13, Rarity.UNCOMMON, mage.cards.f.FeatherOfFlight.class));
        cards.add(new SetCardInfo("Fecund Greenshell", 171, Rarity.RARE, mage.cards.f.FecundGreenshell.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fecund Greenshell", 362, Rarity.RARE, mage.cards.f.FecundGreenshell.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Feed the Cycle", 94, Rarity.UNCOMMON, mage.cards.f.FeedTheCycle.class));
        cards.add(new SetCardInfo("Fell", 95, Rarity.UNCOMMON, mage.cards.f.Fell.class));
        cards.add(new SetCardInfo("Festival of Embers", 134, Rarity.RARE, mage.cards.f.FestivalOfEmbers.class));
        cards.add(new SetCardInfo("Finch Formation", 50, Rarity.COMMON, mage.cards.f.FinchFormation.class));
        cards.add(new SetCardInfo("Finneas, Ace Archer", 212, Rarity.RARE, mage.cards.f.FinneasAceArcher.class));
        cards.add(new SetCardInfo("Fireglass Mentor", 213, Rarity.UNCOMMON, mage.cards.f.FireglassMentor.class));
        cards.add(new SetCardInfo("Flame Lash", 391, Rarity.COMMON, mage.cards.f.FlameLash.class));
        cards.add(new SetCardInfo("Flamecache Gecko", 135, Rarity.UNCOMMON, mage.cards.f.FlamecacheGecko.class));
        cards.add(new SetCardInfo("Flowerfoot Swordmaster", 14, Rarity.UNCOMMON, mage.cards.f.FlowerfootSwordmaster.class));
        cards.add(new SetCardInfo("For the Common Good", 172, Rarity.RARE, mage.cards.f.ForTheCommonGood.class));
        cards.add(new SetCardInfo("Forest", 278, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Fountainport Bell", 245, Rarity.COMMON, mage.cards.f.FountainportBell.class));
        cards.add(new SetCardInfo("Fountainport", 253, Rarity.RARE, mage.cards.f.Fountainport.class));
        cards.add(new SetCardInfo("Frilled Sparkshooter", 136, Rarity.COMMON, mage.cards.f.FrilledSparkshooter.class));
        cards.add(new SetCardInfo("Galewind Moose", 173, Rarity.UNCOMMON, mage.cards.g.GalewindMoose.class));
        cards.add(new SetCardInfo("Gev, Scaled Scorch", 214, Rarity.RARE, mage.cards.g.GevScaledScorch.class));
        cards.add(new SetCardInfo("Giant Growth", 393, Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Glarb, Calamity's Augur", 215, Rarity.MYTHIC, mage.cards.g.GlarbCalamitysAugur.class));
        cards.add(new SetCardInfo("Glidedive Duo", 96, Rarity.COMMON, mage.cards.g.GlidediveDuo.class));
        cards.add(new SetCardInfo("Gossip's Talent", 51, Rarity.UNCOMMON, mage.cards.g.GossipsTalent.class));
        cards.add(new SetCardInfo("Harnesser of Storms", 137, Rarity.UNCOMMON, mage.cards.h.HarnesserOfStorms.class));
        cards.add(new SetCardInfo("Harvestrite Host", 15, Rarity.UNCOMMON, mage.cards.h.HarvestriteHost.class));
        cards.add(new SetCardInfo("Hazardroot Herbalist", 174, Rarity.UNCOMMON, mage.cards.h.HazardrootHerbalist.class));
        cards.add(new SetCardInfo("Hazel's Nocturne", 97, Rarity.UNCOMMON, mage.cards.h.HazelsNocturne.class));
        cards.add(new SetCardInfo("Head of the Homestead", 216, Rarity.COMMON, mage.cards.h.HeadOfTheHomestead.class));
        cards.add(new SetCardInfo("Heaped Harvest", 175, Rarity.COMMON, mage.cards.h.HeapedHarvest.class));
        cards.add(new SetCardInfo("Heartfire Hero", 138, Rarity.UNCOMMON, mage.cards.h.HeartfireHero.class));
        cards.add(new SetCardInfo("Hearthborn Battler", 139, Rarity.RARE, mage.cards.h.HearthbornBattler.class));
        cards.add(new SetCardInfo("Helga, Skittish Seer", 217, Rarity.MYTHIC, mage.cards.h.HelgaSkittishSeer.class));
        cards.add(new SetCardInfo("Hidden Grotto", 254, Rarity.COMMON, mage.cards.h.HiddenGrotto.class));
        cards.add(new SetCardInfo("High Stride", 176, Rarity.COMMON, mage.cards.h.HighStride.class));
        cards.add(new SetCardInfo("Hired Claw", 140, Rarity.RARE, mage.cards.h.HiredClaw.class));
        cards.add(new SetCardInfo("Hivespine Wolverine", 177, Rarity.UNCOMMON, mage.cards.h.HivespineWolverine.class));
        cards.add(new SetCardInfo("Hoarder's Overflow", 141, Rarity.UNCOMMON, mage.cards.h.HoardersOverflow.class));
        cards.add(new SetCardInfo("Honored Dreyleader", 178, Rarity.UNCOMMON, mage.cards.h.HonoredDreyleader.class));
        cards.add(new SetCardInfo("Hop to It", 16, Rarity.UNCOMMON, mage.cards.h.HopToIt.class));
        cards.add(new SetCardInfo("Hugs, Grisly Guardian", 218, Rarity.MYTHIC, mage.cards.h.HugsGrislyGuardian.class));
        cards.add(new SetCardInfo("Hunter's Talent", 179, Rarity.UNCOMMON, mage.cards.h.HuntersTalent.class));
        cards.add(new SetCardInfo("Huskburster Swarm", 98, Rarity.UNCOMMON, mage.cards.h.HuskbursterSwarm.class));
        cards.add(new SetCardInfo("Innkeeper's Talent", 180, Rarity.RARE, mage.cards.i.InnkeepersTalent.class));
        cards.add(new SetCardInfo("Into the Flood Maw", 52, Rarity.UNCOMMON, mage.cards.i.IntoTheFloodMaw.class));
        cards.add(new SetCardInfo("Intrepid Rabbit", 17, Rarity.COMMON, mage.cards.i.IntrepidRabbit.class));
        cards.add(new SetCardInfo("Iridescent Vinelasher", 99, Rarity.RARE, mage.cards.i.IridescentVinelasher.class));
        cards.add(new SetCardInfo("Island", 266, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Jackdaw Savior", 18, Rarity.RARE, mage.cards.j.JackdawSavior.class));
        cards.add(new SetCardInfo("Jolly Gerbils", 19, Rarity.UNCOMMON, mage.cards.j.JollyGerbils.class));
        cards.add(new SetCardInfo("Junkblade Bruiser", 220, Rarity.COMMON, mage.cards.j.JunkbladeBruiser.class));
        cards.add(new SetCardInfo("Kastral, the Windcrested", 221, Rarity.RARE, mage.cards.k.KastralTheWindcrested.class));
        cards.add(new SetCardInfo("Keen-Eyed Curator", 181, Rarity.RARE, mage.cards.k.KeenEyedCurator.class));
        cards.add(new SetCardInfo("Kindlespark Duo", 142, Rarity.COMMON, mage.cards.k.KindlesparkDuo.class));
        cards.add(new SetCardInfo("Kitnap", 53, Rarity.RARE, mage.cards.k.Kitnap.class));
        cards.add(new SetCardInfo("Kitsa, Otterball Elite", 54, Rarity.MYTHIC, mage.cards.k.KitsaOtterballElite.class));
        cards.add(new SetCardInfo("Knightfisher", 55, Rarity.UNCOMMON, mage.cards.k.Knightfisher.class));
        cards.add(new SetCardInfo("Lifecreed Duo", 20, Rarity.COMMON, mage.cards.l.LifecreedDuo.class));
        cards.add(new SetCardInfo("Lightshell Duo", 56, Rarity.COMMON, mage.cards.l.LightshellDuo.class));
        cards.add(new SetCardInfo("Lilypad Village", 255, Rarity.UNCOMMON, mage.cards.l.LilypadVillage.class));
        cards.add(new SetCardInfo("Lilysplash Mentor", 222, Rarity.UNCOMMON, mage.cards.l.LilysplashMentor.class));
        cards.add(new SetCardInfo("Long River Lurker", 57, Rarity.UNCOMMON, mage.cards.l.LongRiverLurker.class));
        cards.add(new SetCardInfo("Long River's Pull", 58, Rarity.UNCOMMON, mage.cards.l.LongRiversPull.class));
        cards.add(new SetCardInfo("Longstalk Brawl", 182, Rarity.COMMON, mage.cards.l.LongstalkBrawl.class));
        cards.add(new SetCardInfo("Lumra, Bellow of the Woods", 183, Rarity.MYTHIC, mage.cards.l.LumraBellowOfTheWoods.class));
        cards.add(new SetCardInfo("Lunar Convocation", 223, Rarity.RARE, mage.cards.l.LunarConvocation.class));
        cards.add(new SetCardInfo("Lupinflower Village", 256, Rarity.UNCOMMON, mage.cards.l.LupinflowerVillage.class));
        cards.add(new SetCardInfo("Mabel's Mettle", 21, Rarity.UNCOMMON, mage.cards.m.MabelsMettle.class));
        cards.add(new SetCardInfo("Mabel, Heir to Cragflame", 224, Rarity.RARE, mage.cards.m.MabelHeirToCragflame.class));
        cards.add(new SetCardInfo("Maha, Its Feathers Night", 100, Rarity.MYTHIC, mage.cards.m.MahaItsFeathersNight.class));
        cards.add(new SetCardInfo("Manifold Mouse", 143, Rarity.RARE, mage.cards.m.ManifoldMouse.class));
        cards.add(new SetCardInfo("Might of the Meek", 144, Rarity.COMMON, mage.cards.m.MightOfTheMeek.class));
        cards.add(new SetCardInfo("Mind Drill Assailant", 225, Rarity.COMMON, mage.cards.m.MindDrillAssailant.class));
        cards.add(new SetCardInfo("Mind Spiral", 59, Rarity.COMMON, mage.cards.m.MindSpiral.class));
        cards.add(new SetCardInfo("Mind Spring", 389, Rarity.RARE, mage.cards.m.MindSpring.class));
        cards.add(new SetCardInfo("Mindwhisker", 60, Rarity.UNCOMMON, mage.cards.m.Mindwhisker.class));
        cards.add(new SetCardInfo("Mistbreath Elder", 184, Rarity.RARE, mage.cards.m.MistbreathElder.class));
        cards.add(new SetCardInfo("Mockingbird", 61, Rarity.RARE, mage.cards.m.Mockingbird.class));
        cards.add(new SetCardInfo("Moonrise Cleric", 226, Rarity.COMMON, mage.cards.m.MoonriseCleric.class));
        cards.add(new SetCardInfo("Moonstone Harbinger", 101, Rarity.UNCOMMON, mage.cards.m.MoonstoneHarbinger.class));
        cards.add(new SetCardInfo("Mountain", 274, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mouse Trapper", 22, Rarity.UNCOMMON, mage.cards.m.MouseTrapper.class));
        cards.add(new SetCardInfo("Mudflat Village", 257, Rarity.UNCOMMON, mage.cards.m.MudflatVillage.class));
        cards.add(new SetCardInfo("Muerra, Trash Tactician", 227, Rarity.RARE, mage.cards.m.MuerraTrashTactician.class));
        cards.add(new SetCardInfo("Nettle Guard", 23, Rarity.COMMON, mage.cards.n.NettleGuard.class));
        cards.add(new SetCardInfo("Nightwhorl Hermit", 62, Rarity.COMMON, mage.cards.n.NightwhorlHermit.class));
        cards.add(new SetCardInfo("Nocturnal Hunger", 102, Rarity.COMMON, mage.cards.n.NocturnalHunger.class));
        cards.add(new SetCardInfo("Oakhollow Village", 258, Rarity.UNCOMMON, mage.cards.o.OakhollowVillage.class));
        cards.add(new SetCardInfo("Osteomancer Adept", 103, Rarity.RARE, mage.cards.o.OsteomancerAdept.class));
        cards.add(new SetCardInfo("Otterball Antics", 63, Rarity.UNCOMMON, mage.cards.o.OtterballAntics.class));
        cards.add(new SetCardInfo("Overprotect", 185, Rarity.UNCOMMON, mage.cards.o.Overprotect.class));
        cards.add(new SetCardInfo("Parting Gust", 24, Rarity.UNCOMMON, mage.cards.p.PartingGust.class));
        cards.add(new SetCardInfo("Patchwork Banner", 247, Rarity.UNCOMMON, mage.cards.p.PatchworkBanner.class));
        cards.add(new SetCardInfo("Pawpatch Formation", 186, Rarity.UNCOMMON, mage.cards.p.PawpatchFormation.class));
        cards.add(new SetCardInfo("Pawpatch Recruit", 187, Rarity.RARE, mage.cards.p.PawpatchRecruit.class));
        cards.add(new SetCardInfo("Pearl of Wisdom", 64, Rarity.COMMON, mage.cards.p.PearlOfWisdom.class));
        cards.add(new SetCardInfo("Peerless Recycling", 188, Rarity.UNCOMMON, mage.cards.p.PeerlessRecycling.class));
        cards.add(new SetCardInfo("Persistent Marshstalker", 104, Rarity.UNCOMMON, mage.cards.p.PersistentMarshstalker.class));
        cards.add(new SetCardInfo("Pileated Provisioner", 25, Rarity.COMMON, mage.cards.p.PileatedProvisioner.class));
        cards.add(new SetCardInfo("Plains", 262, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Playful Shove", 145, Rarity.UNCOMMON, mage.cards.p.PlayfulShove.class));
        cards.add(new SetCardInfo("Plumecreed Escort", 65, Rarity.UNCOMMON, mage.cards.p.PlumecreedEscort.class));
        cards.add(new SetCardInfo("Plumecreed Mentor", 228, Rarity.UNCOMMON, mage.cards.p.PlumecreedMentor.class));
        cards.add(new SetCardInfo("Polliwallop", 189, Rarity.COMMON, mage.cards.p.Polliwallop.class));
        cards.add(new SetCardInfo("Pond Prophet", 229, Rarity.COMMON, mage.cards.p.PondProphet.class));
        cards.add(new SetCardInfo("Portent of Calamity", 66, Rarity.RARE, mage.cards.p.PortentOfCalamity.class));
        cards.add(new SetCardInfo("Psychic Whorl", 105, Rarity.COMMON, mage.cards.p.PsychicWhorl.class));
        cards.add(new SetCardInfo("Quaketusk Boar", 146, Rarity.UNCOMMON, mage.cards.q.QuaketuskBoar.class));
        cards.add(new SetCardInfo("Rabbit Response", 26, Rarity.COMMON, mage.cards.r.RabbitResponse.class));
        cards.add(new SetCardInfo("Rabid Bite", 394, Rarity.COMMON, mage.cards.r.RabidBite.class));
        cards.add(new SetCardInfo("Rabid Gnaw", 147, Rarity.UNCOMMON, mage.cards.r.RabidGnaw.class));
        cards.add(new SetCardInfo("Raccoon Rallier", 148, Rarity.COMMON, mage.cards.r.RaccoonRallier.class));
        cards.add(new SetCardInfo("Ral, Crackling Wit", 230, Rarity.MYTHIC, mage.cards.r.RalCracklingWit.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ral, Crackling Wit", 341, Rarity.MYTHIC, mage.cards.r.RalCracklingWit.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ral, Crackling Wit", 353, Rarity.MYTHIC, mage.cards.r.RalCracklingWit.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ravine Raider", 106, Rarity.COMMON, mage.cards.r.RavineRaider.class));
        cards.add(new SetCardInfo("Repel Calamity", 27, Rarity.UNCOMMON, mage.cards.r.RepelCalamity.class));
        cards.add(new SetCardInfo("Reptilian Recruiter", 149, Rarity.UNCOMMON, mage.cards.r.ReptilianRecruiter.class));
        cards.add(new SetCardInfo("Rockface Village", 259, Rarity.UNCOMMON, mage.cards.r.RockfaceVillage.class));
        cards.add(new SetCardInfo("Rottenmouth Viper", 107, Rarity.MYTHIC, mage.cards.r.RottenmouthViper.class));
        cards.add(new SetCardInfo("Roughshod Duo", 150, Rarity.COMMON, mage.cards.r.RoughshodDuo.class));
        cards.add(new SetCardInfo("Run Away Together", 67, Rarity.COMMON, mage.cards.r.RunAwayTogether.class));
        cards.add(new SetCardInfo("Rust-Shield Rampager", 190, Rarity.COMMON, mage.cards.r.RustShieldRampager.class));
        cards.add(new SetCardInfo("Ruthless Negotiation", 108, Rarity.UNCOMMON, mage.cards.r.RuthlessNegotiation.class));
        cards.add(new SetCardInfo("Salvation Swan", 28, Rarity.RARE, mage.cards.s.SalvationSwan.class));
        cards.add(new SetCardInfo("Savor", 109, Rarity.COMMON, mage.cards.s.Savor.class));
        cards.add(new SetCardInfo("Sazacap's Brew", 151, Rarity.COMMON, mage.cards.s.SazacapsBrew.class));
        cards.add(new SetCardInfo("Scales of Shale", 110, Rarity.COMMON, mage.cards.s.ScalesOfShale.class));
        cards.add(new SetCardInfo("Scavenger's Talent", 111, Rarity.RARE, mage.cards.s.ScavengersTalent.class));
        cards.add(new SetCardInfo("Scrapshooter", 191, Rarity.RARE, mage.cards.s.Scrapshooter.class));
        cards.add(new SetCardInfo("Season of Gathering", 192, Rarity.MYTHIC, mage.cards.s.SeasonOfGathering.class));
        cards.add(new SetCardInfo("Season of Loss", 112, Rarity.MYTHIC, mage.cards.s.SeasonOfLoss.class));
        cards.add(new SetCardInfo("Season of Weaving", 68, Rarity.MYTHIC, mage.cards.s.SeasonOfWeaving.class));
        cards.add(new SetCardInfo("Season of the Bold", 152, Rarity.MYTHIC, mage.cards.s.SeasonOfTheBold.class));
        cards.add(new SetCardInfo("Season of the Burrow", 29, Rarity.MYTHIC, mage.cards.s.SeasonOfTheBurrow.class));
        cards.add(new SetCardInfo("Seasoned Warrenguard", 30, Rarity.UNCOMMON, mage.cards.s.SeasonedWarrenguard.class));
        cards.add(new SetCardInfo("Seedglaive Mentor", 231, Rarity.UNCOMMON, mage.cards.s.SeedglaiveMentor.class));
        cards.add(new SetCardInfo("Seedpod Squire", 232, Rarity.COMMON, mage.cards.s.SeedpodSquire.class));
        cards.add(new SetCardInfo("Serra Redeemer", 387, Rarity.RARE, mage.cards.s.SerraRedeemer.class));
        cards.add(new SetCardInfo("Shore Up", 69, Rarity.COMMON, mage.cards.s.ShoreUp.class));
        cards.add(new SetCardInfo("Shoreline Looter", 70, Rarity.UNCOMMON, mage.cards.s.ShorelineLooter.class));
        cards.add(new SetCardInfo("Short Bow", 248, Rarity.UNCOMMON, mage.cards.s.ShortBow.class));
        cards.add(new SetCardInfo("Shrike Force", 31, Rarity.UNCOMMON, mage.cards.s.ShrikeForce.class));
        cards.add(new SetCardInfo("Sinister Monolith", 113, Rarity.UNCOMMON, mage.cards.s.SinisterMonolith.class));
        cards.add(new SetCardInfo("Skyskipper Duo", 71, Rarity.COMMON, mage.cards.s.SkyskipperDuo.class));
        cards.add(new SetCardInfo("Sonar Strike", 32, Rarity.COMMON, mage.cards.s.SonarStrike.class));
        cards.add(new SetCardInfo("Spellgyre", 72, Rarity.UNCOMMON, mage.cards.s.Spellgyre.class));
        cards.add(new SetCardInfo("Splash Lasher", 73, Rarity.UNCOMMON, mage.cards.s.SplashLasher.class));
        cards.add(new SetCardInfo("Splash Portal", 74, Rarity.UNCOMMON, mage.cards.s.SplashPortal.class));
        cards.add(new SetCardInfo("Star Charter", 33, Rarity.UNCOMMON, mage.cards.s.StarCharter.class));
        cards.add(new SetCardInfo("Starfall Invocation", 34, Rarity.RARE, mage.cards.s.StarfallInvocation.class));
        cards.add(new SetCardInfo("Starforged Sword", 249, Rarity.UNCOMMON, mage.cards.s.StarforgedSword.class));
        cards.add(new SetCardInfo("Stargaze", 114, Rarity.UNCOMMON, mage.cards.s.Stargaze.class));
        cards.add(new SetCardInfo("Starlit Soothsayer", 115, Rarity.COMMON, mage.cards.s.StarlitSoothsayer.class));
        cards.add(new SetCardInfo("Starscape Cleric", 116, Rarity.UNCOMMON, mage.cards.s.StarscapeCleric.class));
        cards.add(new SetCardInfo("Starseer Mentor", 233, Rarity.UNCOMMON, mage.cards.s.StarseerMentor.class));
        cards.add(new SetCardInfo("Steampath Charger", 153, Rarity.COMMON, mage.cards.s.SteampathCharger.class));
        cards.add(new SetCardInfo("Stickytongue Sentinel", 193, Rarity.COMMON, mage.cards.s.StickytongueSentinel.class));
        cards.add(new SetCardInfo("Stocking the Pantry", 194, Rarity.UNCOMMON, mage.cards.s.StockingThePantry.class));
        cards.add(new SetCardInfo("Stormcatch Mentor", 234, Rarity.UNCOMMON, mage.cards.s.StormcatchMentor.class));
        cards.add(new SetCardInfo("Stormchaser's Talent", 75, Rarity.RARE, mage.cards.s.StormchasersTalent.class));
        cards.add(new SetCardInfo("Stormsplitter", 154, Rarity.MYTHIC, mage.cards.s.Stormsplitter.class));
        cards.add(new SetCardInfo("Sugar Coat", 76, Rarity.UNCOMMON, mage.cards.s.SugarCoat.class));
        cards.add(new SetCardInfo("Sunshower Druid", 195, Rarity.COMMON, mage.cards.s.SunshowerDruid.class));
        cards.add(new SetCardInfo("Sunspine Lynx", 155, Rarity.RARE, mage.cards.s.SunspineLynx.class));
        cards.add(new SetCardInfo("Swamp", 270, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swiftwater Cliffs", 397, Rarity.COMMON, mage.cards.s.SwiftwaterCliffs.class));
        cards.add(new SetCardInfo("Sword of Vengeance", 395, Rarity.RARE, mage.cards.s.SwordOfVengeance.class));
        cards.add(new SetCardInfo("Take Out the Trash", 156, Rarity.COMMON, mage.cards.t.TakeOutTheTrash.class));
        cards.add(new SetCardInfo("Tangle Tumbler", 250, Rarity.UNCOMMON, mage.cards.t.TangleTumbler.class));
        cards.add(new SetCardInfo("Teapot Slinger", 157, Rarity.UNCOMMON, mage.cards.t.TeapotSlinger.class));
        cards.add(new SetCardInfo("Tempest Angler", 235, Rarity.COMMON, mage.cards.t.TempestAngler.class));
        cards.add(new SetCardInfo("Tender Wildguide", 196, Rarity.RARE, mage.cards.t.TenderWildguide.class));
        cards.add(new SetCardInfo("The Infamous Cruelclaw", 219, Rarity.MYTHIC, mage.cards.t.TheInfamousCruelclaw.class));
        cards.add(new SetCardInfo("Thieving Otter", 390, Rarity.COMMON, mage.cards.t.ThievingOtter.class));
        cards.add(new SetCardInfo("Thistledown Players", 35, Rarity.COMMON, mage.cards.t.ThistledownPlayers.class));
        cards.add(new SetCardInfo("Thornplate Intimidator", 117, Rarity.COMMON, mage.cards.t.ThornplateIntimidator.class));
        cards.add(new SetCardInfo("Thornvault Forager", 197, Rarity.RARE, mage.cards.t.ThornvaultForager.class));
        cards.add(new SetCardInfo("Thought Shucker", 77, Rarity.COMMON, mage.cards.t.ThoughtShucker.class));
        cards.add(new SetCardInfo("Thought-Stalker Warlock", 118, Rarity.UNCOMMON, mage.cards.t.ThoughtStalkerWarlock.class));
        cards.add(new SetCardInfo("Three Tree City", 260, Rarity.RARE, mage.cards.t.ThreeTreeCity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Three Tree City", 337, Rarity.RARE, mage.cards.t.ThreeTreeCity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Three Tree City", 338, Rarity.RARE, mage.cards.t.ThreeTreeCity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Three Tree City", 339, Rarity.RARE, mage.cards.t.ThreeTreeCity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Three Tree City", 340, Rarity.RARE, mage.cards.t.ThreeTreeCity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Three Tree Mascot", 251, Rarity.COMMON, mage.cards.t.ThreeTreeMascot.class));
        cards.add(new SetCardInfo("Three Tree Rootweaver", 198, Rarity.COMMON, mage.cards.t.ThreeTreeRootweaver.class));
        cards.add(new SetCardInfo("Three Tree Scribe", 199, Rarity.UNCOMMON, mage.cards.t.ThreeTreeScribe.class));
        cards.add(new SetCardInfo("Thundertrap Trainer", 78, Rarity.RARE, mage.cards.t.ThundertrapTrainer.class));
        cards.add(new SetCardInfo("Tidecaller Mentor", 236, Rarity.UNCOMMON, mage.cards.t.TidecallerMentor.class));
        cards.add(new SetCardInfo("Treeguard Duo", 200, Rarity.COMMON, mage.cards.t.TreeguardDuo.class));
        cards.add(new SetCardInfo("Treetop Sentries", 201, Rarity.COMMON, mage.cards.t.TreetopSentries.class));
        cards.add(new SetCardInfo("Uncharted Haven", 261, Rarity.COMMON, mage.cards.u.UnchartedHaven.class));
        cards.add(new SetCardInfo("Valley Flamecaller", 158, Rarity.RARE, mage.cards.v.ValleyFlamecaller.class));
        cards.add(new SetCardInfo("Valley Floodcaller", 79, Rarity.RARE, mage.cards.v.ValleyFloodcaller.class));
        cards.add(new SetCardInfo("Valley Mightcaller", 202, Rarity.RARE, mage.cards.v.ValleyMightcaller.class));
        cards.add(new SetCardInfo("Valley Questcaller", 36, Rarity.RARE, mage.cards.v.ValleyQuestcaller.class));
        cards.add(new SetCardInfo("Valley Rally", 159, Rarity.UNCOMMON, mage.cards.v.ValleyRally.class));
        cards.add(new SetCardInfo("Valley Rotcaller", 119, Rarity.RARE, mage.cards.v.ValleyRotcaller.class));
        cards.add(new SetCardInfo("Veteran Guardmouse", 237, Rarity.COMMON, mage.cards.v.VeteranGuardmouse.class));
        cards.add(new SetCardInfo("Vinereap Mentor", 238, Rarity.UNCOMMON, mage.cards.v.VinereapMentor.class));
        cards.add(new SetCardInfo("Vren, the Relentless", 239, Rarity.RARE, mage.cards.v.VrenTheRelentless.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vren, the Relentless", 354, Rarity.RARE, mage.cards.v.VrenTheRelentless.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wandertale Mentor", 240, Rarity.UNCOMMON, mage.cards.w.WandertaleMentor.class));
        cards.add(new SetCardInfo("War Squeak", 160, Rarity.COMMON, mage.cards.w.WarSqueak.class));
        cards.add(new SetCardInfo("Warren Elder", 37, Rarity.COMMON, mage.cards.w.WarrenElder.class));
        cards.add(new SetCardInfo("Warren Warleader", 38, Rarity.MYTHIC, mage.cards.w.WarrenWarleader.class));
        cards.add(new SetCardInfo("Waterspout Warden", 80, Rarity.COMMON, mage.cards.w.WaterspoutWarden.class));
        cards.add(new SetCardInfo("Wax-Wane Witness", 39, Rarity.COMMON, mage.cards.w.WaxWaneWitness.class));
        cards.add(new SetCardInfo("Wear Down", 203, Rarity.UNCOMMON, mage.cards.w.WearDown.class));
        cards.add(new SetCardInfo("Whiskerquill Scribe", 161, Rarity.COMMON, mage.cards.w.WhiskerquillScribe.class));
        cards.add(new SetCardInfo("Whiskervale Forerunner", 40, Rarity.RARE, mage.cards.w.WhiskervaleForerunner.class));
        cards.add(new SetCardInfo("Wick's Patrol", 121, Rarity.UNCOMMON, mage.cards.w.WicksPatrol.class));
        cards.add(new SetCardInfo("Wick, the Whorled Mind", 120, Rarity.RARE, mage.cards.w.WickTheWhorledMind.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wick, the Whorled Mind", 314, Rarity.RARE, mage.cards.w.WickTheWhorledMind.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wildfire Howl", 162, Rarity.UNCOMMON, mage.cards.w.WildfireHowl.class));
        cards.add(new SetCardInfo("Wishing Well", 81, Rarity.RARE, mage.cards.w.WishingWell.class));
        cards.add(new SetCardInfo("Ygra, Eater of All", 241, Rarity.MYTHIC, mage.cards.y.YgraEaterOfAll.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ygra, Eater of All", 294, Rarity.MYTHIC, mage.cards.y.YgraEaterOfAll.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zoraline, Cosmos Caller", 242, Rarity.RARE, mage.cards.z.ZoralineCosmosCaller.class));
    }

    @Override
    protected void generateBoosterMap() {
        super.generateBoosterMap();
        
        CardInfo cardInfo;
        for( int cn = 54 ; cn < 64 ; cn++ ){
            cardInfo = CardRepository.instance.findCard("SPG", "" + cn);
            if( cardInfo != null ){
                inBoosterMap.put("SPG_" + cn, cardInfo);
            }else{
                throw new IllegalArgumentException("Card not found: " + "SPG_" + cn);
            }
        }
    }

    @Override
    public BoosterCollator createCollator() {
        return new BloomburrowCollator();
    }
}

// Booster collation info from https://vm1.substation33.com/tiera/t/lethe/blb.html
// Using Japanese collation plus other info inferred from various sources
class BloomburrowCollator implements BoosterCollator {

    private final CardRun commonA = new CardRun(false, "42", "3", "245", "176", "26", "106", "160");
    private final CardRun commonB = new CardRun(true, "201", "156", "229", "198", "144", "210", "193", "130", "243", "163", "136", "225", "189", "153", "237", "182", "229", "151", "235", "167", "161", "226", "193", "150", "220", "189", "136", "216", "200", "144", "235", "167", "130", "226", "195", "122", "251", "175", "148", "208", "170", "156", "225", "200", "123", "210", "201", "122", "243", "190", "148", "251", "175", "232", "151", "237", "163", "142", "208", "198", "150", "210", "189", "153", "244", "182", "161", "220", "163", "142", "229", "193", "150", "237", "198", "144", "244", "182", "243", "161", "232", "195", "153", "216", "201", "142", "235", "170", "136", "226", "200", "151", "232", "190", "130", "216", "195", "123", "251", "167", "156", "220", "175", "122", "244", "170", "148", "225", "190", "123", "208");
    private final CardRun commonC = new CardRun(true, "84", "62", "32", "64", "117", "20", "71", "93", "23", "45", "102", "77", "32", "96", "62", "117", "1", "105", "80", "91", "17", "45", "254", "109", "35", "71", "110", "8", "59", "89", "1", "56", "261", "82", "7", "62", "96", "37", "50", "93", "20", "56", "110", "32", "261", "67", "84", "7", "69", "117", "25", "46", "91", "37", "50", "115", "35", "59", "102", "23", "77", "105", "25", "64", "89", "8", "80", "109", "39", "69", "254", "115", "17", "56", "93", "23", "71", "91", "37", "67", "96", "261", "7", "69", "82", "35", "50", "109", "39", "46", "84", "8", "45", "102", "20", "64", "89", "25", "254", "59", "105", "17", "77", "115", "80", "1", "110", "67", "82", "39", "46");
    private final CardRun uncommonA = new CardRun(true, "250", "194", "248", "159", "51", "83", "30", "186", "5", "203", "246", "188", "108", "178", "250", "51", "83", "194", "248", "159", "30", "188", "246", "203", "249", "186", "5", "108", "250", "178", "10", "248", "83", "194", "30", "159", "249", "203", "5", "186", "51", "108", "246", "10", "188", "250", "194", "249", "159", "30", "203", "83", "5", "246", "188", "10", "108", "186", "248", "178", "51", "203", "30", "194", "250", "5", "159", "186", "108", "249", "51", "188", "248", "178", "246", "10", "83", "186", "159", "203", "108", "188", "249", "194", "30", "248", "178", "10", "250", "83", "249", "159", "30", "203", "83", "5", "246", "188", "10", "178", "51", "250", "194", "249", "5", "246", "10", "108", "186", "248", "178", "51");
    private final CardRun uncommonB = new CardRun(true, "213", "31", "256", "228", "22", "236", "33", "238", "11", "259", "233", "21", "240", "13", "228", "256", "14", "213", "22", "258", "33", "236", "255", "16", "234", "27", "257", "24", "259", "11", "206", "15", "222", "31", "238", "21", "233", "16", "234", "4", "258", "19", "236", "27", "222", "31", "256", "11", "231", "13", "233", "24", "255", "4", "206", "16", "240", "21", "228", "22", "213", "257", "14", "238", "33", "231", "259", "15", "255", "31", "234", "24", "206", "16", "222", "4", "259", "228", "22", "238", "27", "213", "21", "258", "13", "233", "33", "231", "19", "236", "15", "257", "11", "256", "14", "258", "13", "240", "19", "231", "27", "222", "4", "234", "14", "206", "15", "255", "24", "257", "240", "19");
    private final CardRun uncommonC = new CardRun(true, "113", "55", "141", "87", "74", "165", "104", "135", "247", "177", "149", "97", "127", "164", "162", "63", "145", "116", "60", "138", "174", "118", "52", "146", "114", "73", "128", "94", "199", "137", "57", "147", "173", "58", "116", "162", "65", "145", "70", "114", "137", "247", "165", "52", "87", "58", "146", "76", "97", "43", "104", "179", "141", "63", "168", "70", "128", "73", "135", "113", "55", "199", "138", "72", "121", "127", "177", "98", "44", "126", "101", "169", "131", "185", "92", "74", "85", "174", "95", "125", "173", "57", "118", "157", "164", "60", "147", "166", "94", "149", "179", "98", "43", "126", "95", "185", "48", "125", "44", "168", "85", "72", "101", "166", "65", "157", "92", "48", "169", "121", "76", "131");
    private final CardRun rare = new CardRun(false, "204", "204", "124", "124", "41", "41", "205", "205", "129", "129", "207", "207", "6", "6", "209", "209", "86", "86", "88", "88", "90", "90", "9", "9", "47", "47", "211", "211", "133", "133", "12", "12", "252", "252", "171", "171", "134", "134", "212", "212", "172", "172", "253", "253", "214", "214", "139", "139", "140", "140", "180", "180", "99", "99", "18", "18", "221", "221", "181", "181", "53", "53", "223", "223", "224", "224", "143", "143", "184", "184", "61", "61", "227", "227", "103", "103", "187", "187", "66", "66", "28", "28", "111", "111", "191", "191", "34", "34", "75", "75", "155", "155", "196", "196", "197", "197", "260", "260", "78", "78", "158", "158", "79", "79", "202", "202", "36", "36", "119", "119", "239", "239", "40", "40", "120", "120", "81", "81", "242", "242", "2", "132", "49", "215", "217", "218", "54", "183", "100", "230", "107", "192", "112", "152", "29", "68", "154", "219", "38", "241");
    private final CardRun listGuest = new CardRun(false, "SPG_54", "SPG_55", "SPG_56", "SPG_57", "SPG_58", "SPG_59", "SPG_60", "SPG_61", "SPG_62", "SPG_63");
    // boosterfun showcase variants not included
    private final CardRun land = new CardRun(false, "262", "262", "262", "262", "263", "263", "263", "264", "264", "265", "266", "266", "266", "266", "267", "267", "267", "268", "268", "269", "270", "270", "270", "270", "271", "271", "271", "272", "272", "273", "274", "274", "274", "274", "275", "275", "275", "276", "276", "277", "278", "278", "278", "278", "279", "279", "279", "280", "280", "281");

    // because a foil card is independant from sorted runs, repeated as unsorted runs
    private final CardRun foilCommonB = new CardRun(true, "201", "156", "229", "198", "144", "210", "193", "130", "243", "163", "136", "225", "189", "153", "237", "182", "229", "151", "235", "167", "161", "226", "193", "150", "220", "189", "136", "216", "200", "144", "235", "167", "130", "226", "195", "122", "251", "175", "148", "208", "170", "156", "225", "200", "123", "210", "201", "122", "243", "190", "148", "251", "175", "232", "151", "237", "163", "142", "208", "198", "150", "210", "189", "153", "244", "182", "161", "220", "163", "142", "229", "193", "150", "237", "198", "144", "244", "182", "243", "161", "232", "195", "153", "216", "201", "142", "235", "170", "136", "226", "200", "151", "232", "190", "130", "216", "195", "123", "251", "167", "156", "220", "175", "122", "244", "170", "148", "225", "190", "123", "208");
    private final CardRun foilCommonC = new CardRun(true, "84", "62", "32", "64", "117", "20", "71", "93", "23", "45", "102", "77", "32", "96", "62", "117", "1", "105", "80", "91", "17", "45", "254", "109", "35", "71", "110", "8", "59", "89", "1", "56", "261", "82", "7", "62", "96", "37", "50", "93", "20", "56", "110", "32", "261", "67", "84", "7", "69", "117", "25", "46", "91", "37", "50", "115", "35", "59", "102", "23", "77", "105", "25", "64", "89", "8", "80", "109", "39", "69", "254", "115", "17", "56", "93", "23", "71", "91", "37", "67", "96", "261", "7", "69", "82", "35", "50", "109", "39", "46", "84", "8", "45", "102", "20", "64", "89", "25", "254", "59", "105", "17", "77", "115", "80", "1", "110", "67", "82", "39", "46");
    private final CardRun foilUncommonA = new CardRun(true, "250", "194", "248", "159", "51", "83", "30", "186", "5", "203", "246", "188", "108", "178", "250", "51", "83", "194", "248", "159", "30", "188", "246", "203", "249", "186", "5", "108", "250", "178", "10", "248", "83", "194", "30", "159", "249", "203", "5", "186", "51", "108", "246", "10", "188", "250", "194", "249", "159", "30", "203", "83", "5", "246", "188", "10", "108", "186", "248", "178", "51", "203", "30", "194", "250", "5", "159", "186", "108", "249", "51", "188", "248", "178", "246", "10", "83", "186", "159", "203", "108", "188", "249", "194", "30", "248", "178", "10", "250", "83", "249", "159", "30", "203", "83", "5", "246", "188", "10", "178", "51", "250", "194", "249", "5", "246", "10", "108", "186", "248", "178", "51");
    private final CardRun foilUncommonB = new CardRun(true, "213", "31", "256", "228", "22", "236", "33", "238", "11", "259", "233", "21", "240", "13", "228", "256", "14", "213", "22", "258", "33", "236", "255", "16", "234", "27", "257", "24", "259", "11", "206", "15", "222", "31", "238", "21", "233", "16", "234", "4", "258", "19", "236", "27", "222", "31", "256", "11", "231", "13", "233", "24", "255", "4", "206", "16", "240", "21", "228", "22", "213", "257", "14", "238", "33", "231", "259", "15", "255", "31", "234", "24", "206", "16", "222", "4", "259", "228", "22", "238", "27", "213", "21", "258", "13", "233", "33", "231", "19", "236", "15", "257", "11", "256", "14", "258", "13", "240", "19", "231", "27", "222", "4", "234", "14", "206", "15", "255", "24", "257", "240", "19");
    private final CardRun foilUncommonC = new CardRun(true, "113", "55", "141", "87", "74", "165", "104", "135", "247", "177", "149", "97", "127", "164", "162", "63", "145", "116", "60", "138", "174", "118", "52", "146", "114", "73", "128", "94", "199", "137", "57", "147", "173", "58", "116", "162", "65", "145", "70", "114", "137", "247", "165", "52", "87", "58", "146", "76", "97", "43", "104", "179", "141", "63", "168", "70", "128", "73", "135", "113", "55", "199", "138", "72", "121", "127", "177", "98", "44", "126", "101", "169", "131", "185", "92", "74", "85", "174", "95", "125", "173", "57", "118", "157", "164", "60", "147", "166", "94", "149", "179", "98", "43", "126", "95", "185", "48", "125", "44", "168", "85", "72", "101", "166", "65", "157", "92", "48", "169", "121", "76", "131");

    private final BoosterStructure BBBCCC = new BoosterStructure(
            commonB, commonB, commonB,
            commonC, commonC, commonC
    );
    private final BoosterStructure ABBBCCC = new BoosterStructure(
            commonA,
            commonB, commonB, commonB,
            commonC, commonC, commonC
    );
    private final BoosterStructure BBBBCCC = new BoosterStructure(
            commonB, commonB, commonB, commonB,
            commonC, commonC, commonC
    );
    private final BoosterStructure BBBCCCC = new BoosterStructure(
            commonB, commonB, commonB,
            commonC, commonC, commonC, commonC
    );
    private final BoosterStructure ABBBBCCC = new BoosterStructure(
            commonA,
            commonB, commonB, commonB, commonB,
            commonC, commonC, commonC
    );
    private final BoosterStructure ABBBCCCC = new BoosterStructure(
            commonA,
            commonB, commonB, commonB,
            commonC, commonC, commonC, commonC
    );
    private final BoosterStructure BBBBCCCC = new BoosterStructure(
            commonB, commonB, commonB, commonB,
            commonC, commonC, commonC, commonC
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
    private final BoosterStructure BBCC = new BoosterStructure(
            uncommonB, uncommonB,
            uncommonC, uncommonC
    );
    private final BoosterStructure BCCC = new BoosterStructure(
            uncommonB,
            uncommonC, uncommonC, uncommonC
    );

    private final BoosterStructure R1 = new BoosterStructure(rare);
    private final BoosterStructure R2 = new BoosterStructure(rare,rare);
    private final BoosterStructure L1 = new BoosterStructure(land);
    private final BoosterStructure Sg = new BoosterStructure(listGuest);
    private final BoosterStructure fcA = new BoosterStructure(commonA);
    private final BoosterStructure fcB = new BoosterStructure(foilCommonB);
    private final BoosterStructure fcC = new BoosterStructure(foilCommonC);
    private final BoosterStructure fuA = new BoosterStructure(foilUncommonA);
    private final BoosterStructure fuB = new BoosterStructure(foilUncommonB);
    private final BoosterStructure fuC = new BoosterStructure(foilUncommonC);

    // In order for equal numbers of each common to exist, the average booster must contain:
    // 0.6 A commons ( 60074 / 97200)       6 Commons (  1 /  80) or ( 1215 / 97200)
    // 3.3 B commons (317534 / 97200)       7 Commons (247 / 300) or (80028 / 97200)
    // 3.3 C commons (317534 / 97200)       8 Commons (197 / 200) or (15957 / 97200)
    private final RarityConfiguration commonRuns6 = new RarityConfiguration(BBBCCC);
    private final RarityConfiguration commonRuns7A = new RarityConfiguration(ABBBCCC);
    private final RarityConfiguration commonRuns7BC = new RarityConfiguration(BBBBCCC,BBBCCCC);
    private final RarityConfiguration commonRuns8A = new RarityConfiguration(ABBBBCCC, ABBBCCCC);
    private final RarityConfiguration commonRuns8BC = new RarityConfiguration(BBBBCCCC);
    private static final RarityConfiguration commonRuns(int runLength){
        return ( 6< runLength ? 7< runLength ?
            ( RandomUtil.nextInt(15957) <4789 ? commonRuns8BC : commonRuns8A )
          : ( RandomUtil.nextInt(8892) <5434 ? commonRuns7A : commonRuns7BC )
          : commonRuns6 );
    }

    // In order for equal numbers of each uncommon to exist, the average booster must contain:
    // 0.59 A uncommons ( 44 / 75)       3 Uncommons (1 / 3) or (25 / 75)
    // 1.03 B uncommons ( 77 / 75)       4 Uncommons (2 / 3) or (50 / 75)
    // 2.05 C uncommons (154 / 75)
    private final RarityConfiguration uncommonRuns3 = new RarityConfiguration(BCC);
    private final RarityConfiguration uncommonRuns4A = new RarityConfiguration(ABCC);
    private final RarityConfiguration uncommonRuns4BC = new RarityConfiguration(BBCC, BCCC, BCCC);
    private static final RarityConfiguration uncommonRuns(int runLength){
        return ( 3< runLength ? RandomUtil.nextInt(25) <22 ?
            uncommonRuns4A : uncommonRuns4BC : uncommonRuns3 );
    }

    private final RarityConfiguration rareRuns1 = new RarityConfiguration(R1);
    private final RarityConfiguration rareRuns2 = new RarityConfiguration(R2);
    private static final RarityConfiguration rareRuns(int runLength){
        return ( 1< runLength ? rareRuns2 : rareRuns1 );
    }
    // 1.5% of packs contain a special guest card
    private final RarityConfiguration listRuns = new RarityConfiguration(Sg);

    private final RarityConfiguration landRuns = new RarityConfiguration(L1);

    // Foil - 2/3 C , 1/4 U , 1/12 R/M
    private final RarityConfiguration foilRunsC = new RarityConfiguration(
        fcA, fcA, fcA, fcA, fcA, fcA, fcA,

        fcB, fcB, fcB, fcB, fcB, fcB, fcB, fcB, fcB, fcB,
        fcB, fcB, fcB, fcB, fcB, fcB, fcB, fcB, fcB, fcB,
        fcB, fcB, fcB, fcB, fcB, fcB, fcB, fcB, fcB, fcB,
        fcB, fcB, fcB, fcB, fcB, fcB, fcB,

        fcC, fcC, fcC, fcC, fcC, fcC, fcC, fcC, fcC, fcC,
        fcC, fcC, fcC, fcC, fcC, fcC, fcC, fcC, fcC, fcC,
        fcC, fcC, fcC, fcC, fcC, fcC, fcC, fcC, fcC, fcC,
        fcC, fcC, fcC, fcC, fcC, fcC, fcC
    );
    private final RarityConfiguration foilRunsU = new RarityConfiguration(
        fuA, fuA, fuA, fuA,
        fuB, fuB, fuB, fuB, fuB, fuB, fuB,
        fuC, fuC, fuC, fuC, fuC, fuC, fuC, fuC, fuC, fuC,
        fuC, fuC, fuC, fuC
    );
    private final RarityConfiguration foilRunsRM = new RarityConfiguration(R1);
    private static final RarityConfiguration foilRuns(){
        int runRoll = RandomUtil.nextInt(12);
        return ( 11> runRoll ? 8> runRoll ?
            foilRunsC : foilRunsU : foilRunsRM );
    }

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();

        // 1-2 rares, 3-4 uncommons, and 6-8 commons
        // wildcard 1/6 common, 2/3 uncommon, 1/6 rare (incl mythic)
        int wildNum = RandomUtil.nextInt(6);

        int numCommon = 7;
        boolean wildRare = false;
        boolean wildUncommon = false;
        if( wildNum < 1 ){
            numCommon++;
        }else if( wildNum < 5 ){
            wildUncommon = true;
        }else{
            wildRare = true;
        };

        booster.addAll(foilRuns().getNext().makeRun());
        booster.addAll(landRuns.getNext().makeRun());
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
