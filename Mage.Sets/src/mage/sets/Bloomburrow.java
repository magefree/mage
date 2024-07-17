package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

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
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Agate Assault", 122, Rarity.COMMON, mage.cards.a.AgateAssault.class));
        cards.add(new SetCardInfo("Alania's Pathmaker", 123, Rarity.COMMON, mage.cards.a.AlaniasPathmaker.class));
        cards.add(new SetCardInfo("Bakersbane Duo", 163, Rarity.COMMON, mage.cards.b.BakersbaneDuo.class));
        cards.add(new SetCardInfo("Banishing Light", 1, Rarity.COMMON, mage.cards.b.BanishingLight.class));
        cards.add(new SetCardInfo("Bark-Knuckle Boxer", 164, Rarity.UNCOMMON, mage.cards.b.BarkKnuckleBoxer.class));
        cards.add(new SetCardInfo("Barkform Harvester", 243, Rarity.COMMON, mage.cards.b.BarkformHarvester.class));
        cards.add(new SetCardInfo("Baylen, the Haymaker", 205, Rarity.RARE, mage.cards.b.BaylenTheHaymaker.class));
        cards.add(new SetCardInfo("Bellowing Crier", 42, Rarity.COMMON, mage.cards.b.BellowingCrier.class));
        cards.add(new SetCardInfo("Blossoming Sands", 396, Rarity.COMMON, mage.cards.b.BlossomingSands.class));
        cards.add(new SetCardInfo("Bonecache Overseer", 85, Rarity.UNCOMMON, mage.cards.b.BonecacheOverseer.class));
        cards.add(new SetCardInfo("Brambleguard Captain", 127, Rarity.UNCOMMON, mage.cards.b.BrambleguardCaptain.class));
        cards.add(new SetCardInfo("Brambleguard Veteran", 165, Rarity.UNCOMMON, mage.cards.b.BrambleguardVeteran.class));
        cards.add(new SetCardInfo("Brave-Kin Duo", 3, Rarity.COMMON, mage.cards.b.BraveKinDuo.class));
        cards.add(new SetCardInfo("Brazen Collector", 128, Rarity.UNCOMMON, mage.cards.b.BrazenCollector.class));
        cards.add(new SetCardInfo("Bria, Riptide Rogue", 379, Rarity.MYTHIC, mage.cards.b.BriaRiptideRogue.class));
        cards.add(new SetCardInfo("Brightblade Stoat", 4, Rarity.UNCOMMON, mage.cards.b.BrightbladeStoat.class));
        cards.add(new SetCardInfo("Bumbleflower's Sharepot", 244, Rarity.COMMON, mage.cards.b.BumbleflowersSharepot.class));
        cards.add(new SetCardInfo("Burrowguard Mentor", 206, Rarity.UNCOMMON, mage.cards.b.BurrowguardMentor.class));
        cards.add(new SetCardInfo("Bushy Bodyguard", 166, Rarity.UNCOMMON, mage.cards.b.BushyBodyguard.class));
        cards.add(new SetCardInfo("Byrke, Long Ear of the Law", 380, Rarity.MYTHIC, mage.cards.b.ByrkeLongEarOfTheLaw.class));
        cards.add(new SetCardInfo("Byway Barterer", 129, Rarity.RARE, mage.cards.b.BywayBarterer.class));
        cards.add(new SetCardInfo("Cache Grab", 167, Rarity.COMMON, mage.cards.c.CacheGrab.class));
        cards.add(new SetCardInfo("Calamitous Tide", 43, Rarity.UNCOMMON, mage.cards.c.CalamitousTide.class));
        cards.add(new SetCardInfo("Carrot Cake", 7, Rarity.COMMON, mage.cards.c.CarrotCake.class));
        cards.add(new SetCardInfo("Charmed Sleep", 388, Rarity.COMMON, mage.cards.c.CharmedSleep.class));
        cards.add(new SetCardInfo("Cindering Cutthroat", 208, Rarity.COMMON, mage.cards.c.CinderingCutthroat.class));
        cards.add(new SetCardInfo("Clifftop Lookout", 168, Rarity.UNCOMMON, mage.cards.c.ClifftopLookout.class));
        cards.add(new SetCardInfo("Colossification", 392, Rarity.RARE, mage.cards.c.Colossification.class));
        cards.add(new SetCardInfo("Conduct Electricity", 130, Rarity.COMMON, mage.cards.c.ConductElectricity.class));
        cards.add(new SetCardInfo("Corpseberry Cultivator", 210, Rarity.COMMON, mage.cards.c.CorpseberryCultivator.class));
        cards.add(new SetCardInfo("Coruscation Mage", 131, Rarity.UNCOMMON, mage.cards.c.CoruscationMage.class));
        cards.add(new SetCardInfo("Curious Forager", 169, Rarity.UNCOMMON, mage.cards.c.CuriousForager.class));
        cards.add(new SetCardInfo("Darkstar Augur", 90, Rarity.RARE, mage.cards.d.DarkstarAugur.class));
        cards.add(new SetCardInfo("Diresight", 91, Rarity.COMMON, mage.cards.d.Diresight.class));
        cards.add(new SetCardInfo("Downwind Ambusher", 92, Rarity.UNCOMMON, mage.cards.d.DownwindAmbusher.class));
        cards.add(new SetCardInfo("Dreamdew Entrancer", 211, Rarity.RARE, mage.cards.d.DreamdewEntrancer.class));
        cards.add(new SetCardInfo("Druid of the Spade", 170, Rarity.COMMON, mage.cards.d.DruidOfTheSpade.class));
        cards.add(new SetCardInfo("Early Winter", 93, Rarity.COMMON, mage.cards.e.EarlyWinter.class));
        cards.add(new SetCardInfo("Emberheart Challenger", 133, Rarity.RARE, mage.cards.e.EmberheartChallenger.class));
        cards.add(new SetCardInfo("Fabled Passage", 252, Rarity.RARE, mage.cards.f.FabledPassage.class));
        cards.add(new SetCardInfo("Feather of Flight", 13, Rarity.UNCOMMON, mage.cards.f.FeatherOfFlight.class));
        cards.add(new SetCardInfo("Feed the Cycle", 94, Rarity.UNCOMMON, mage.cards.f.FeedTheCycle.class));
        cards.add(new SetCardInfo("Fell", 95, Rarity.UNCOMMON, mage.cards.f.Fell.class));
        cards.add(new SetCardInfo("Finch Formation", 50, Rarity.COMMON, mage.cards.f.FinchFormation.class));
        cards.add(new SetCardInfo("Finneas, Ace Archer", 212, Rarity.RARE, mage.cards.f.FinneasAceArcher.class));
        cards.add(new SetCardInfo("Flame Lash", 391, Rarity.COMMON, mage.cards.f.FlameLash.class));
        cards.add(new SetCardInfo("Flowerfoot Swordmaster", 14, Rarity.UNCOMMON, mage.cards.f.FlowerfootSwordmaster.class));
        cards.add(new SetCardInfo("Forest", 278, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Fountainport Bell", 245, Rarity.COMMON, mage.cards.f.FountainportBell.class));
        cards.add(new SetCardInfo("Fountainport", 253, Rarity.RARE, mage.cards.f.Fountainport.class));
        cards.add(new SetCardInfo("Galewind Moose", 173, Rarity.UNCOMMON, mage.cards.g.GalewindMoose.class));
        cards.add(new SetCardInfo("Giant Growth", 393, Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Glidedive Duo", 96, Rarity.COMMON, mage.cards.g.GlidediveDuo.class));
        cards.add(new SetCardInfo("Harnesser of Storms", 137, Rarity.UNCOMMON, mage.cards.h.HarnesserOfStorms.class));
        cards.add(new SetCardInfo("Harvestrite Host", 15, Rarity.UNCOMMON, mage.cards.h.HarvestriteHost.class));
        cards.add(new SetCardInfo("Hazardroot Herbalist", 174, Rarity.UNCOMMON, mage.cards.h.HazardrootHerbalist.class));
        cards.add(new SetCardInfo("Hazel's Nocturne", 97, Rarity.UNCOMMON, mage.cards.h.HazelsNocturne.class));
        cards.add(new SetCardInfo("Head of the Homestead", 216, Rarity.COMMON, mage.cards.h.HeadOfTheHomestead.class));
        cards.add(new SetCardInfo("Heartfire Hero", 138, Rarity.UNCOMMON, mage.cards.h.HeartfireHero.class));
        cards.add(new SetCardInfo("Hearthborn Battler", 139, Rarity.RARE, mage.cards.h.HearthbornBattler.class));
        cards.add(new SetCardInfo("Hidden Grotto", 254, Rarity.COMMON, mage.cards.h.HiddenGrotto.class));
        cards.add(new SetCardInfo("Hired Claw", 140, Rarity.RARE, mage.cards.h.HiredClaw.class));
        cards.add(new SetCardInfo("Hivespine Wolverine", 177, Rarity.UNCOMMON, mage.cards.h.HivespineWolverine.class));
        cards.add(new SetCardInfo("Hop to It", 16, Rarity.UNCOMMON, mage.cards.h.HopToIt.class));
        cards.add(new SetCardInfo("Hugs, Grisly Guardian", 218, Rarity.MYTHIC, mage.cards.h.HugsGrislyGuardian.class));
        cards.add(new SetCardInfo("Hunter's Talent", 179, Rarity.UNCOMMON, mage.cards.h.HuntersTalent.class));
        cards.add(new SetCardInfo("Innkeeper's Talent", 180, Rarity.RARE, mage.cards.i.InnkeepersTalent.class));
        cards.add(new SetCardInfo("Iridescent Vinelasher", 99, Rarity.RARE, mage.cards.i.IridescentVinelasher.class));
        cards.add(new SetCardInfo("Island", 266, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Junkblade Bruiser", 220, Rarity.COMMON, mage.cards.j.JunkbladeBruiser.class));
        cards.add(new SetCardInfo("Kindlespark Duo", 142, Rarity.COMMON, mage.cards.k.KindlesparkDuo.class));
        cards.add(new SetCardInfo("Kitsa, Otterball Elite", 54, Rarity.MYTHIC, mage.cards.k.KitsaOtterballElite.class));
        cards.add(new SetCardInfo("Knightfisher", 55, Rarity.UNCOMMON, mage.cards.k.Knightfisher.class));
        cards.add(new SetCardInfo("Lifecreed Duo", 20, Rarity.COMMON, mage.cards.l.LifecreedDuo.class));
        cards.add(new SetCardInfo("Lightshell Duo", 56, Rarity.COMMON, mage.cards.l.LightshellDuo.class));
        cards.add(new SetCardInfo("Lilypad Village", 255, Rarity.UNCOMMON, mage.cards.l.LilypadVillage.class));
        cards.add(new SetCardInfo("Lumra, Bellow of the Woods", 183, Rarity.MYTHIC, mage.cards.l.LumraBellowOfTheWoods.class));
        cards.add(new SetCardInfo("Lupinflower Village", 256, Rarity.UNCOMMON, mage.cards.l.LupinflowerVillage.class));
        cards.add(new SetCardInfo("Mabel, Heir to Cragflame", 224, Rarity.RARE, mage.cards.m.MabelHeirToCragflame.class));
        cards.add(new SetCardInfo("Maha, Its Feathers Night", 100, Rarity.MYTHIC, mage.cards.m.MahaItsFeathersNight.class));
        cards.add(new SetCardInfo("Might of the Meek", 144, Rarity.COMMON, mage.cards.m.MightOfTheMeek.class));
        cards.add(new SetCardInfo("Mind Drill Assailant", 225, Rarity.COMMON, mage.cards.m.MindDrillAssailant.class));
        cards.add(new SetCardInfo("Mind Spring", 389, Rarity.RARE, mage.cards.m.MindSpring.class));
        cards.add(new SetCardInfo("Mindwhisker", 60, Rarity.UNCOMMON, mage.cards.m.Mindwhisker.class));
        cards.add(new SetCardInfo("Moonrise Cleric", 226, Rarity.COMMON, mage.cards.m.MoonriseCleric.class));
        cards.add(new SetCardInfo("Mountain", 274, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mouse Trapper", 22, Rarity.UNCOMMON, mage.cards.m.MouseTrapper.class));
        cards.add(new SetCardInfo("Mudflat Village", 257, Rarity.UNCOMMON, mage.cards.m.MudflatVillage.class));
        cards.add(new SetCardInfo("Muerra, Trash Tactician", 227, Rarity.RARE, mage.cards.m.MuerraTrashTactician.class));
        cards.add(new SetCardInfo("Nettle Guard", 23, Rarity.COMMON, mage.cards.n.NettleGuard.class));
        cards.add(new SetCardInfo("Nightwhorl Hermit", 62, Rarity.COMMON, mage.cards.n.NightwhorlHermit.class));
        cards.add(new SetCardInfo("Oakhollow Village", 258, Rarity.UNCOMMON, mage.cards.o.OakhollowVillage.class));
        cards.add(new SetCardInfo("Overprotect", 185, Rarity.UNCOMMON, mage.cards.o.Overprotect.class));
        cards.add(new SetCardInfo("Patchwork Banner", 247, Rarity.UNCOMMON, mage.cards.p.PatchworkBanner.class));
        cards.add(new SetCardInfo("Pawpatch Formation", 186, Rarity.UNCOMMON, mage.cards.p.PawpatchFormation.class));
        cards.add(new SetCardInfo("Pearl of Wisdom", 64, Rarity.COMMON, mage.cards.p.PearlOfWisdom.class));
        cards.add(new SetCardInfo("Pileated Provisioner", 25, Rarity.COMMON, mage.cards.p.PileatedProvisioner.class));
        cards.add(new SetCardInfo("Plains", 262, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Playful Shove", 145, Rarity.UNCOMMON, mage.cards.p.PlayfulShove.class));
        cards.add(new SetCardInfo("Plumecreed Escort", 65, Rarity.UNCOMMON, mage.cards.p.PlumecreedEscort.class));
        cards.add(new SetCardInfo("Plumecreed Mentor", 228, Rarity.UNCOMMON, mage.cards.p.PlumecreedMentor.class));
        cards.add(new SetCardInfo("Pond Prophet", 229, Rarity.COMMON, mage.cards.p.PondProphet.class));
        cards.add(new SetCardInfo("Quaketusk Boar", 146, Rarity.UNCOMMON, mage.cards.q.QuaketuskBoar.class));
        cards.add(new SetCardInfo("Rabbit Response", 26, Rarity.COMMON, mage.cards.r.RabbitResponse.class));
        cards.add(new SetCardInfo("Rabid Bite", 394, Rarity.COMMON, mage.cards.r.RabidBite.class));
        cards.add(new SetCardInfo("Repel Calamity", 27, Rarity.UNCOMMON, mage.cards.r.RepelCalamity.class));
        cards.add(new SetCardInfo("Rockface Village", 259, Rarity.UNCOMMON, mage.cards.r.RockfaceVillage.class));
        cards.add(new SetCardInfo("Run Away Together", 67, Rarity.COMMON, mage.cards.r.RunAwayTogether.class));
        cards.add(new SetCardInfo("Rust-Shield Rampager", 190, Rarity.COMMON, mage.cards.r.RustShieldRampager.class));
        cards.add(new SetCardInfo("Salvation Swan", 28, Rarity.RARE, mage.cards.s.SalvationSwan.class));
        cards.add(new SetCardInfo("Scales of Shale", 110, Rarity.COMMON, mage.cards.s.ScalesOfShale.class));
        cards.add(new SetCardInfo("Seasoned Warrenguard", 30, Rarity.UNCOMMON, mage.cards.s.SeasonedWarrenguard.class));
        cards.add(new SetCardInfo("Seedglaive Mentor", 231, Rarity.UNCOMMON, mage.cards.s.SeedglaiveMentor.class));
        cards.add(new SetCardInfo("Seedpod Squire", 232, Rarity.COMMON, mage.cards.s.SeedpodSquire.class));
        cards.add(new SetCardInfo("Serra Redeemer", 387, Rarity.RARE, mage.cards.s.SerraRedeemer.class));
        cards.add(new SetCardInfo("Shoreline Looter", 70, Rarity.UNCOMMON, mage.cards.s.ShorelineLooter.class));
        cards.add(new SetCardInfo("Shrike Force", 31, Rarity.UNCOMMON, mage.cards.s.ShrikeForce.class));
        cards.add(new SetCardInfo("Sinister Monolith", 113, Rarity.UNCOMMON, mage.cards.s.SinisterMonolith.class));
        cards.add(new SetCardInfo("Splash Lasher", 73, Rarity.UNCOMMON, mage.cards.s.SplashLasher.class));
        cards.add(new SetCardInfo("Stargaze", 114, Rarity.UNCOMMON, mage.cards.s.Stargaze.class));
        cards.add(new SetCardInfo("Starscape Cleric", 116, Rarity.UNCOMMON, mage.cards.s.StarscapeCleric.class));
        cards.add(new SetCardInfo("Starseer Mentor", 233, Rarity.UNCOMMON, mage.cards.s.StarseerMentor.class));
        cards.add(new SetCardInfo("Steampath Charger", 153, Rarity.COMMON, mage.cards.s.SteampathCharger.class));
        cards.add(new SetCardInfo("Stickytongue Sentinel", 193, Rarity.COMMON, mage.cards.s.StickytongueSentinel.class));
        cards.add(new SetCardInfo("Stormcatch Mentor", 234, Rarity.UNCOMMON, mage.cards.s.StormcatchMentor.class));
        cards.add(new SetCardInfo("Stormsplitter", 154, Rarity.MYTHIC, mage.cards.s.Stormsplitter.class));
        cards.add(new SetCardInfo("Sunshower Druid", 195, Rarity.COMMON, mage.cards.s.SunshowerDruid.class));
        cards.add(new SetCardInfo("Sunspine Lynx", 155, Rarity.RARE, mage.cards.s.SunspineLynx.class));
        cards.add(new SetCardInfo("Swamp", 270, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swiftwater Cliffs", 397, Rarity.COMMON, mage.cards.s.SwiftwaterCliffs.class));
        cards.add(new SetCardInfo("Sword of Vengeance", 395, Rarity.RARE, mage.cards.s.SwordOfVengeance.class));
        cards.add(new SetCardInfo("Take Out the Trash", 156, Rarity.COMMON, mage.cards.t.TakeOutTheTrash.class));
        cards.add(new SetCardInfo("Teapot Slinger", 157, Rarity.UNCOMMON, mage.cards.t.TeapotSlinger.class));
        cards.add(new SetCardInfo("Tempest Angler", 235, Rarity.COMMON, mage.cards.t.TempestAngler.class));
        cards.add(new SetCardInfo("Tender Wildguide", 196, Rarity.RARE, mage.cards.t.TenderWildguide.class));
        cards.add(new SetCardInfo("Thieving Otter", 390, Rarity.COMMON, mage.cards.t.ThievingOtter.class));
        cards.add(new SetCardInfo("Thistledown Players", 35, Rarity.COMMON, mage.cards.t.ThistledownPlayers.class));
        cards.add(new SetCardInfo("Thornvault Forager", 197, Rarity.RARE, mage.cards.t.ThornvaultForager.class));
        cards.add(new SetCardInfo("Three Tree Mascot", 251, Rarity.COMMON, mage.cards.t.ThreeTreeMascot.class));
        cards.add(new SetCardInfo("Thundertrap Trainer", 78, Rarity.RARE, mage.cards.t.ThundertrapTrainer.class));
        cards.add(new SetCardInfo("Tidecaller Mentor", 236, Rarity.UNCOMMON, mage.cards.t.TidecallerMentor.class));
        cards.add(new SetCardInfo("Treetop Sentries", 201, Rarity.COMMON, mage.cards.t.TreetopSentries.class));
        cards.add(new SetCardInfo("Uncharted Haven", 261, Rarity.COMMON, mage.cards.u.UnchartedHaven.class));
        cards.add(new SetCardInfo("Valley Mightcaller", 202, Rarity.RARE, mage.cards.v.ValleyMightcaller.class));
        cards.add(new SetCardInfo("Valley Rotcaller", 119, Rarity.RARE, mage.cards.v.ValleyRotcaller.class));
        cards.add(new SetCardInfo("Veteran Guardmouse", 237, Rarity.COMMON, mage.cards.v.VeteranGuardmouse.class));
        cards.add(new SetCardInfo("Vinereap Mentor", 238, Rarity.UNCOMMON, mage.cards.v.VinereapMentor.class));
        cards.add(new SetCardInfo("Wandertale Mentor", 240, Rarity.UNCOMMON, mage.cards.w.WandertaleMentor.class));
        cards.add(new SetCardInfo("War Squeak", 160, Rarity.COMMON, mage.cards.w.WarSqueak.class));
        cards.add(new SetCardInfo("Warren Elder", 37, Rarity.COMMON, mage.cards.w.WarrenElder.class));
        cards.add(new SetCardInfo("Warren Warleader", 38, Rarity.MYTHIC, mage.cards.w.WarrenWarleader.class));
        cards.add(new SetCardInfo("Whiskerquill Scribe", 161, Rarity.COMMON, mage.cards.w.WhiskerquillScribe.class));
        cards.add(new SetCardInfo("Whiskervale Forerunner", 40, Rarity.RARE, mage.cards.w.WhiskervaleForerunner.class));
        cards.add(new SetCardInfo("Zoraline, Cosmos Caller", 242, Rarity.RARE, mage.cards.z.ZoralineCosmosCaller.class));
    }
}
