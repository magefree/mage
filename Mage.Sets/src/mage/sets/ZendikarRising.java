package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.Arrays;
import java.util.List;

/**
 * @author TheElk801
 */
public final class ZendikarRising extends ExpansionSet {

    private static final List<String> unfinished = Arrays.asList(
            "Agadeem's Awakening",
            "Agadeem, the Undercrypt",
            "Akoum Warrior",
            "Akoum Teeth",
            "Bala Ged Recovery",
            "Bala Ged Sanctuary",
            "Beyeen Veil",
            "Beyeen Coast",
            "Blackbloom Rogue",
            "Blackbloom Bog",
            "Branchloft Pathway",
            "Boulderloft Pathway",
            "Brightclimb Pathway",
            "Grimclimb Pathway",
            "Clearwater Pathway",
            "Murkwater Pathway",
            "Cragcrown Pathway",
            "Timbercrown Pathway",
            "Emeria's Call",
            "Emeria, Shattered Skyclave",
            "Glasspool Mimic",
            "Glasspool Shore",
            "Hagra Mauling",
            "Hagra Broodpit",
            "Jwari Disruption",
            "Jwari Ruins",
            "Kabira Takedown",
            "Kabira Plateau",
            "Kazandu Mammoth",
            "Kazandu Valley",
            "Kazuul's Fury",
            "Kazuul's Cliffs",
            "Khalni Ambush",
            "Khalni Territory",
            "Makindi Stampede",
            "Makindi Mesas",
            "Malakir Rebirth",
            "Malakir Mire",
            "Needleverge Pathway",
            "Pillarverge Pathway",
            "Ondu Inversion",
            "Ondu Skyruins",
            "Pelakka Predation",
            "Pelakka Caverns",
            "Riverglide Pathway",
            "Lavaglide Pathway",
            "Sea Gate Restoration",
            "Sea Gate, Reborn",
            "Sejiri Shelter",
            "Sejiri Glacier",
            "Shatterskull Smashing",
            "Shatterskull, the Hammer Pass",
            "Silundi Vision",
            "Silundi Isle",
            "Skyclave Cleric",
            "Skyclave Basilica",
            "Song-Mad Treachery",
            "Song-Mad Ruins",
            "Spikefield Hazard",
            "Spikefield Cave",
            "Tangled Florahedron",
            "Tangled Vale",
            "Turntimber Symbiosis",
            "Turntimber, Serpentine Wood",
            "Umara Wizard",
            "Umara Skyfalls",
            "Valakut Awakening",
            "Valakut Stoneforge",
            "Vastwood Fortification",
            "Vastwood Thicket",
            "Zof Consumption",
            "Zof Bloodbog"
    );

    private static final ZendikarRising instance = new ZendikarRising();

    public static ZendikarRising getInstance() {
        return instance;
    }

    private ZendikarRising() {
        super("Zendikar Rising", "ZNR", ExpansionSet.buildDate(2020, 9, 25), SetType.EXPANSION);
        this.blockName = "Zendikar Rising";
        this.hasBasicLands = true;
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 9;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.numBoosterDoubleFaced = 1;
        this.maxCardNumberInBooster = 280;

        cards.add(new SetCardInfo("Acquisitions Expert", 89, Rarity.UNCOMMON, mage.cards.a.AcquisitionsExpert.class));
        cards.add(new SetCardInfo("Adventure Awaits", 177, Rarity.COMMON, mage.cards.a.AdventureAwaits.class));
        cards.add(new SetCardInfo("Agadeem's Awakening", 90, Rarity.MYTHIC, mage.cards.a.AgadeemsAwakening.class));
        cards.add(new SetCardInfo("Agadeem, the Undercrypt", 90, Rarity.MYTHIC, mage.cards.a.AgadeemTheUndercrypt.class));
        cards.add(new SetCardInfo("Akoum Hellhound", 133, Rarity.COMMON, mage.cards.a.AkoumHellhound.class));
        cards.add(new SetCardInfo("Akoum Teeth", 134, Rarity.UNCOMMON, mage.cards.a.AkoumTeeth.class));
        cards.add(new SetCardInfo("Akoum Warrior", 134, Rarity.UNCOMMON, mage.cards.a.AkoumWarrior.class));
        cards.add(new SetCardInfo("Allied Assault", 1, Rarity.UNCOMMON, mage.cards.a.AlliedAssault.class));
        cards.add(new SetCardInfo("Ancient Greenwarden", 178, Rarity.MYTHIC, mage.cards.a.AncientGreenwarden.class));
        cards.add(new SetCardInfo("Angel of Destiny", 2, Rarity.MYTHIC, mage.cards.a.AngelOfDestiny.class));
        cards.add(new SetCardInfo("Angelheart Protector", 3, Rarity.COMMON, mage.cards.a.AngelheartProtector.class));
        cards.add(new SetCardInfo("Anticognition", 45, Rarity.COMMON, mage.cards.a.Anticognition.class));
        cards.add(new SetCardInfo("Archon of Emeria", 4, Rarity.RARE, mage.cards.a.ArchonOfEmeria.class));
        cards.add(new SetCardInfo("Archpriest of Iona", 5, Rarity.RARE, mage.cards.a.ArchpriestOfIona.class));
        cards.add(new SetCardInfo("Ardent Electromancer", 135, Rarity.COMMON, mage.cards.a.ArdentElectromancer.class));
        cards.add(new SetCardInfo("Ashaya, Soul of the Wild", 179, Rarity.MYTHIC, mage.cards.a.AshayaSoulOfTheWild.class));
        cards.add(new SetCardInfo("Attended Healer", 6, Rarity.UNCOMMON, mage.cards.a.AttendedHealer.class));
        cards.add(new SetCardInfo("Bala Ged Recovery", 180, Rarity.UNCOMMON, mage.cards.b.BalaGedRecovery.class));
        cards.add(new SetCardInfo("Bala Ged Sanctuary", 180, Rarity.UNCOMMON, mage.cards.b.BalaGedSanctuary.class));
        cards.add(new SetCardInfo("Base Camp", 257, Rarity.UNCOMMON, mage.cards.b.BaseCamp.class));
        cards.add(new SetCardInfo("Beyeen Coast", 46, Rarity.UNCOMMON, mage.cards.b.BeyeenCoast.class));
        cards.add(new SetCardInfo("Beyeen Veil", 46, Rarity.UNCOMMON, mage.cards.b.BeyeenVeil.class));
        cards.add(new SetCardInfo("Blackbloom Bog", 91, Rarity.UNCOMMON, mage.cards.b.BlackbloomBog.class));
        cards.add(new SetCardInfo("Blackbloom Rogue", 91, Rarity.UNCOMMON, mage.cards.b.BlackbloomRogue.class));
        cards.add(new SetCardInfo("Blood Beckoning", 92, Rarity.COMMON, mage.cards.b.BloodBeckoning.class));
        cards.add(new SetCardInfo("Blood Price", 93, Rarity.COMMON, mage.cards.b.BloodPrice.class));
        cards.add(new SetCardInfo("Bloodchief's Thirst", 94, Rarity.UNCOMMON, mage.cards.b.BloodchiefsThirst.class));
        cards.add(new SetCardInfo("Boulderloft Pathway", 258, Rarity.RARE, mage.cards.b.BoulderloftPathway.class));
        cards.add(new SetCardInfo("Branchloft Pathway", 258, Rarity.RARE, mage.cards.b.BranchloftPathway.class));
        cards.add(new SetCardInfo("Brightclimb Pathway", 259, Rarity.RARE, mage.cards.b.BrightclimbPathway.class));
        cards.add(new SetCardInfo("Broken Wings", 181, Rarity.COMMON, mage.cards.b.BrokenWings.class));
        cards.add(new SetCardInfo("Brushfire Elemental", 221, Rarity.UNCOMMON, mage.cards.b.BrushfireElemental.class));
        cards.add(new SetCardInfo("Bubble Snare", 47, Rarity.COMMON, mage.cards.b.BubbleSnare.class));
        cards.add(new SetCardInfo("Canopy Baloth", 182, Rarity.COMMON, mage.cards.c.CanopyBaloth.class));
        cards.add(new SetCardInfo("Canyon Jerboa", 7, Rarity.UNCOMMON, mage.cards.c.CanyonJerboa.class));
        cards.add(new SetCardInfo("Cascade Seer", 48, Rarity.COMMON, mage.cards.c.CascadeSeer.class));
        cards.add(new SetCardInfo("Charix, the Raging Isle", 49, Rarity.RARE, mage.cards.c.CharixTheRagingIsle.class));
        cards.add(new SetCardInfo("Chilling Trap", 50, Rarity.COMMON, mage.cards.c.ChillingTrap.class));
        cards.add(new SetCardInfo("Cinderclasm", 136, Rarity.UNCOMMON, mage.cards.c.Cinderclasm.class));
        cards.add(new SetCardInfo("Cleansing Wildfire", 137, Rarity.COMMON, mage.cards.c.CleansingWildfire.class));
        cards.add(new SetCardInfo("Clearwater Pathway", 260, Rarity.RARE, mage.cards.c.ClearwaterPathway.class));
        cards.add(new SetCardInfo("Cleric of Chill Depths", 51, Rarity.COMMON, mage.cards.c.ClericOfChillDepths.class));
        cards.add(new SetCardInfo("Cleric of Life's Bond", 222, Rarity.UNCOMMON, mage.cards.c.ClericOfLifesBond.class));
        cards.add(new SetCardInfo("Cliffhaven Kitesail", 243, Rarity.COMMON, mage.cards.c.CliffhavenKitesail.class));
        cards.add(new SetCardInfo("Cliffhaven Sell-Sword", 8, Rarity.COMMON, mage.cards.c.CliffhavenSellSword.class));
        cards.add(new SetCardInfo("Concerted Defense", 52, Rarity.UNCOMMON, mage.cards.c.ConcertedDefense.class));
        cards.add(new SetCardInfo("Confounding Conundrum", 53, Rarity.RARE, mage.cards.c.ConfoundingConundrum.class));
        cards.add(new SetCardInfo("Coralhelm Chronicler", 54, Rarity.RARE, mage.cards.c.CoralhelmChronicler.class));
        cards.add(new SetCardInfo("Coveted Prize", 95, Rarity.RARE, mage.cards.c.CovetedPrize.class));
        cards.add(new SetCardInfo("Cragcrown Pathway", 261, Rarity.RARE, mage.cards.c.CragcrownPathway.class));
        cards.add(new SetCardInfo("Cragplate Baloth", 183, Rarity.RARE, mage.cards.c.CragplateBaloth.class));
        cards.add(new SetCardInfo("Crawling Barrens", 262, Rarity.RARE, mage.cards.c.CrawlingBarrens.class));
        cards.add(new SetCardInfo("Cunning Geysermage", 55, Rarity.COMMON, mage.cards.c.CunningGeysermage.class));
        cards.add(new SetCardInfo("Dauntless Survivor", 184, Rarity.COMMON, mage.cards.d.DauntlessSurvivor.class));
        cards.add(new SetCardInfo("Dauntless Unity", 9, Rarity.COMMON, mage.cards.d.DauntlessUnity.class));
        cards.add(new SetCardInfo("Deadly Alliance", 96, Rarity.COMMON, mage.cards.d.DeadlyAlliance.class));
        cards.add(new SetCardInfo("Deliberate", 56, Rarity.COMMON, mage.cards.d.Deliberate.class));
        cards.add(new SetCardInfo("Demon's Disciple", 97, Rarity.UNCOMMON, mage.cards.d.DemonsDisciple.class));
        cards.add(new SetCardInfo("Disenchant", 10, Rarity.COMMON, mage.cards.d.Disenchant.class));
        cards.add(new SetCardInfo("Drana's Silencer", 99, Rarity.COMMON, mage.cards.d.DranasSilencer.class));
        cards.add(new SetCardInfo("Drana, the Last Bloodchief", 98, Rarity.MYTHIC, mage.cards.d.DranaTheLastBloodchief.class));
        cards.add(new SetCardInfo("Dreadwurm", 100, Rarity.COMMON, mage.cards.d.Dreadwurm.class));
        cards.add(new SetCardInfo("Emeria Captain", 11, Rarity.UNCOMMON, mage.cards.e.EmeriaCaptain.class));
        cards.add(new SetCardInfo("Emeria's Call", 12, Rarity.MYTHIC, mage.cards.e.EmeriasCall.class));
        cards.add(new SetCardInfo("Emeria, Shattered Skyclave", 12, Rarity.MYTHIC, mage.cards.e.EmeriaShatteredSkyclave.class));
        cards.add(new SetCardInfo("Expedition Champion", 138, Rarity.COMMON, mage.cards.e.ExpeditionChampion.class));
        cards.add(new SetCardInfo("Expedition Diviner", 57, Rarity.COMMON, mage.cards.e.ExpeditionDiviner.class));
        cards.add(new SetCardInfo("Expedition Healer", 13, Rarity.COMMON, mage.cards.e.ExpeditionHealer.class));
        cards.add(new SetCardInfo("Expedition Skulker", 101, Rarity.COMMON, mage.cards.e.ExpeditionSkulker.class));
        cards.add(new SetCardInfo("Farsight Adept", 14, Rarity.COMMON, mage.cards.f.FarsightAdept.class));
        cards.add(new SetCardInfo("Fearless Fledgling", 15, Rarity.UNCOMMON, mage.cards.f.FearlessFledgling.class));
        cards.add(new SetCardInfo("Feed the Swarm", 102, Rarity.COMMON, mage.cards.f.FeedTheSwarm.class));
        cards.add(new SetCardInfo("Felidar Retreat", 16, Rarity.RARE, mage.cards.f.FelidarRetreat.class));
        cards.add(new SetCardInfo("Field Research", 58, Rarity.COMMON, mage.cards.f.FieldResearch.class));
        cards.add(new SetCardInfo("Fireblade Charger", 139, Rarity.UNCOMMON, mage.cards.f.FirebladeCharger.class));
        cards.add(new SetCardInfo("Fissure Wizard", 140, Rarity.COMMON, mage.cards.f.FissureWizard.class));
        cards.add(new SetCardInfo("Forest", 278, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Ghastly Gloomhunter", 103, Rarity.COMMON, mage.cards.g.GhastlyGloomhunter.class));
        cards.add(new SetCardInfo("Glacial Grasp", 59, Rarity.COMMON, mage.cards.g.GlacialGrasp.class));
        cards.add(new SetCardInfo("Glasspool Mimic", 60, Rarity.RARE, mage.cards.g.GlasspoolMimic.class));
        cards.add(new SetCardInfo("Glasspool Shore", 60, Rarity.RARE, mage.cards.g.GlasspoolShore.class));
        cards.add(new SetCardInfo("Gnarlid Colony", 185, Rarity.COMMON, mage.cards.g.GnarlidColony.class));
        cards.add(new SetCardInfo("Goma Fada Vanguard", 141, Rarity.UNCOMMON, mage.cards.g.GomaFadaVanguard.class));
        cards.add(new SetCardInfo("Grimclimb Pathway", 259, Rarity.RARE, mage.cards.g.GrimclimbPathway.class));
        cards.add(new SetCardInfo("Grotag Bug-Catcher", 142, Rarity.COMMON, mage.cards.g.GrotagBugCatcher.class));
        cards.add(new SetCardInfo("Grotag Night-Runner", 143, Rarity.UNCOMMON, mage.cards.g.GrotagNightRunner.class));
        cards.add(new SetCardInfo("Guul Draz Mucklord", 104, Rarity.COMMON, mage.cards.g.GuulDrazMucklord.class));
        cards.add(new SetCardInfo("Hagra Broodpit", 106, Rarity.RARE, mage.cards.h.HagraBroodpit.class));
        cards.add(new SetCardInfo("Hagra Constrictor", 105, Rarity.COMMON, mage.cards.h.HagraConstrictor.class));
        cards.add(new SetCardInfo("Hagra Mauling", 106, Rarity.RARE, mage.cards.h.HagraMauling.class));
        cards.add(new SetCardInfo("Highborn Vampire", 107, Rarity.COMMON, mage.cards.h.HighbornVampire.class));
        cards.add(new SetCardInfo("Inordinate Rage", 144, Rarity.COMMON, mage.cards.i.InordinateRage.class));
        cards.add(new SetCardInfo("Inscription of Abundance", 186, Rarity.RARE, mage.cards.i.InscriptionOfAbundance.class));
        cards.add(new SetCardInfo("Inscription of Insight", 61, Rarity.RARE, mage.cards.i.InscriptionOfInsight.class));
        cards.add(new SetCardInfo("Inscription of Ruin", 108, Rarity.RARE, mage.cards.i.InscriptionOfRuin.class));
        cards.add(new SetCardInfo("Into the Roil", 62, Rarity.COMMON, mage.cards.i.IntoTheRoil.class));
        cards.add(new SetCardInfo("Iridescent Hornbeetle", 187, Rarity.UNCOMMON, mage.cards.i.IridescentHornbeetle.class));
        cards.add(new SetCardInfo("Island", 269, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Jace, Mirror Mage", 63, Rarity.MYTHIC, mage.cards.j.JaceMirrorMage.class));
        cards.add(new SetCardInfo("Joraga Visionary", 188, Rarity.COMMON, mage.cards.j.JoragaVisionary.class));
        cards.add(new SetCardInfo("Journey to Oblivion", 17, Rarity.UNCOMMON, mage.cards.j.JourneyToOblivion.class));
        cards.add(new SetCardInfo("Jwari Disruption", 64, Rarity.UNCOMMON, mage.cards.j.JwariDisruption.class));
        cards.add(new SetCardInfo("Jwari Ruins", 64, Rarity.UNCOMMON, mage.cards.j.JwariRuins.class));
        cards.add(new SetCardInfo("Kabira Outrider", 18, Rarity.COMMON, mage.cards.k.KabiraOutrider.class));
        cards.add(new SetCardInfo("Kabira Plateau", 19, Rarity.UNCOMMON, mage.cards.k.KabiraPlateau.class));
        cards.add(new SetCardInfo("Kabira Takedown", 19, Rarity.UNCOMMON, mage.cards.k.KabiraTakedown.class));
        cards.add(new SetCardInfo("Kargan Intimidator", 145, Rarity.RARE, mage.cards.k.KarganIntimidator.class));
        cards.add(new SetCardInfo("Kargan Warleader", 224, Rarity.UNCOMMON, mage.cards.k.KarganWarleader.class));
        cards.add(new SetCardInfo("Kaza, Roil Chaser", 225, Rarity.RARE, mage.cards.k.KazaRoilChaser.class));
        cards.add(new SetCardInfo("Kazandu Mammoth", 189, Rarity.RARE, mage.cards.k.KazanduMammoth.class));
        cards.add(new SetCardInfo("Kazandu Nectarpot", 190, Rarity.COMMON, mage.cards.k.KazanduNectarpot.class));
        cards.add(new SetCardInfo("Kazandu Stomper", 191, Rarity.COMMON, mage.cards.k.KazanduStomper.class));
        cards.add(new SetCardInfo("Kazandu Valley", 189, Rarity.RARE, mage.cards.k.KazanduValley.class));
        cards.add(new SetCardInfo("Kazuul's Cliffs", 146, Rarity.UNCOMMON, mage.cards.k.KazuulsCliffs.class));
        cards.add(new SetCardInfo("Kazuul's Fury", 146, Rarity.UNCOMMON, mage.cards.k.KazuulsFury.class));
        cards.add(new SetCardInfo("Khalni Ambush", 192, Rarity.UNCOMMON, mage.cards.k.KhalniAmbush.class));
        cards.add(new SetCardInfo("Khalni Territory", 192, Rarity.UNCOMMON, mage.cards.k.KhalniTerritory.class));
        cards.add(new SetCardInfo("Kitesail Cleric", 20, Rarity.UNCOMMON, mage.cards.k.KitesailCleric.class));
        cards.add(new SetCardInfo("Kor Blademaster", 21, Rarity.UNCOMMON, mage.cards.k.KorBlademaster.class));
        cards.add(new SetCardInfo("Kor Celebrant", 22, Rarity.COMMON, mage.cards.k.KorCelebrant.class));
        cards.add(new SetCardInfo("Lavaglide Pathway", 264, Rarity.RARE, mage.cards.l.LavaglidePathway.class));
        cards.add(new SetCardInfo("Legion Angel", 23, Rarity.RARE, mage.cards.l.LegionAngel.class));
        cards.add(new SetCardInfo("Linvala, Shield of Sea Gate", 226, Rarity.RARE, mage.cards.l.LinvalaShieldOfSeaGate.class));
        cards.add(new SetCardInfo("Living Tempest", 65, Rarity.COMMON, mage.cards.l.LivingTempest.class));
        cards.add(new SetCardInfo("Lotus Cobra", 193, Rarity.RARE, mage.cards.l.LotusCobra.class));
        cards.add(new SetCardInfo("Lullmage's Domination", 66, Rarity.UNCOMMON, mage.cards.l.LullmagesDomination.class));
        cards.add(new SetCardInfo("Lullmage's Familiar", 227, Rarity.UNCOMMON, mage.cards.l.LullmagesFamiliar.class));
        cards.add(new SetCardInfo("Luminarch Aspirant", 24, Rarity.RARE, mage.cards.l.LuminarchAspirant.class));
        cards.add(new SetCardInfo("Maddening Cacophony", 67, Rarity.RARE, mage.cards.m.MaddeningCacophony.class));
        cards.add(new SetCardInfo("Magmatic Channeler", 148, Rarity.RARE, mage.cards.m.MagmaticChanneler.class));
        cards.add(new SetCardInfo("Makindi Mesas", 26, Rarity.UNCOMMON, mage.cards.m.MakindiMesas.class));
        cards.add(new SetCardInfo("Makindi Ox", 25, Rarity.COMMON, mage.cards.m.MakindiOx.class));
        cards.add(new SetCardInfo("Makindi Stampede", 26, Rarity.UNCOMMON, mage.cards.m.MakindiStampede.class));
        cards.add(new SetCardInfo("Malakir Blood-Priest", 110, Rarity.COMMON, mage.cards.m.MalakirBloodPriest.class));
        cards.add(new SetCardInfo("Malakir Mire", 111, Rarity.UNCOMMON, mage.cards.m.MalakirMire.class));
        cards.add(new SetCardInfo("Malakir Rebirth", 111, Rarity.UNCOMMON, mage.cards.m.MalakirRebirth.class));
        cards.add(new SetCardInfo("Marauding Blight-Priest", 112, Rarity.COMMON, mage.cards.m.MaraudingBlightPriest.class));
        cards.add(new SetCardInfo("Master of Winds", 68, Rarity.RARE, mage.cards.m.MasterOfWinds.class));
        cards.add(new SetCardInfo("Maul of the Skyclaves", 27, Rarity.RARE, mage.cards.m.MaulOfTheSkyclaves.class));
        cards.add(new SetCardInfo("Merfolk Falconer", 69, Rarity.UNCOMMON, mage.cards.m.MerfolkFalconer.class));
        cards.add(new SetCardInfo("Merfolk Windrobber", 70, Rarity.UNCOMMON, mage.cards.m.MerfolkWindrobber.class));
        cards.add(new SetCardInfo("Mesa Lynx", 28, Rarity.COMMON, mage.cards.m.MesaLynx.class));
        cards.add(new SetCardInfo("Might of Murasa", 194, Rarity.COMMON, mage.cards.m.MightOfMurasa.class));
        cards.add(new SetCardInfo("Mind Carver", 113, Rarity.UNCOMMON, mage.cards.m.MindCarver.class));
        cards.add(new SetCardInfo("Mind Drain", 114, Rarity.COMMON, mage.cards.m.MindDrain.class));
        cards.add(new SetCardInfo("Molten Blast", 149, Rarity.COMMON, mage.cards.m.MoltenBlast.class));
        cards.add(new SetCardInfo("Moss-Pit Skeleton", 228, Rarity.UNCOMMON, mage.cards.m.MossPitSkeleton.class));
        cards.add(new SetCardInfo("Mountain", 275, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Murasa Brute", 195, Rarity.COMMON, mage.cards.m.MurasaBrute.class));
        cards.add(new SetCardInfo("Murasa Rootgrazer", 229, Rarity.UNCOMMON, mage.cards.m.MurasaRootgrazer.class));
        cards.add(new SetCardInfo("Murasa Sproutling", 196, Rarity.UNCOMMON, mage.cards.m.MurasaSproutling.class));
        cards.add(new SetCardInfo("Murkwater Pathway", 260, Rarity.RARE, mage.cards.m.MurkwaterPathway.class));
        cards.add(new SetCardInfo("Myriad Construct", 246, Rarity.RARE, mage.cards.m.MyriadConstruct.class));
        cards.add(new SetCardInfo("Nahiri's Binding", 29, Rarity.COMMON, mage.cards.n.NahirisBinding.class));
        cards.add(new SetCardInfo("Nahiri's Lithoforming", 151, Rarity.RARE, mage.cards.n.NahirisLithoforming.class));
        cards.add(new SetCardInfo("Nahiri, Heir of the Ancients", 230, Rarity.MYTHIC, mage.cards.n.NahiriHeirOfTheAncients.class));
        cards.add(new SetCardInfo("Needleverge Pathway", 263, Rarity.RARE, mage.cards.n.NeedlevergePathway.class));
        cards.add(new SetCardInfo("Negate", 71, Rarity.COMMON, mage.cards.n.Negate.class));
        cards.add(new SetCardInfo("Nighthawk Scavenger", 115, Rarity.RARE, mage.cards.n.NighthawkScavenger.class));
        cards.add(new SetCardInfo("Nimana Skitter-Sneak", 116, Rarity.COMMON, mage.cards.n.NimanaSkitterSneak.class));
        cards.add(new SetCardInfo("Nimana Skydancer", 117, Rarity.COMMON, mage.cards.n.NimanaSkydancer.class));
        cards.add(new SetCardInfo("Nimble Trapfinder", 72, Rarity.RARE, mage.cards.n.NimbleTrapfinder.class));
        cards.add(new SetCardInfo("Nissa's Zendikon", 197, Rarity.COMMON, mage.cards.n.NissasZendikon.class));
        cards.add(new SetCardInfo("Nullpriest of Oblivion", 118, Rarity.RARE, mage.cards.n.NullpriestOfOblivion.class));
        cards.add(new SetCardInfo("Oblivion's Hunger", 119, Rarity.COMMON, mage.cards.o.OblivionsHunger.class));
        cards.add(new SetCardInfo("Omnath, Locus of Creation", 232, Rarity.MYTHIC, mage.cards.o.OmnathLocusOfCreation.class));
        cards.add(new SetCardInfo("Ondu Inversion", 30, Rarity.RARE, mage.cards.o.OnduInversion.class));
        cards.add(new SetCardInfo("Ondu Skyruins", 30, Rarity.RARE, mage.cards.o.OnduSkyruins.class));
        cards.add(new SetCardInfo("Orah, Skyclave Hierophant", 233, Rarity.RARE, mage.cards.o.OrahSkyclaveHierophant.class));
        cards.add(new SetCardInfo("Oran-Rief Ooze", 198, Rarity.RARE, mage.cards.o.OranRiefOoze.class));
        cards.add(new SetCardInfo("Paired Tactician", 31, Rarity.UNCOMMON, mage.cards.p.PairedTactician.class));
        cards.add(new SetCardInfo("Pelakka Caverns", 120, Rarity.UNCOMMON, mage.cards.p.PelakkaCaverns.class));
        cards.add(new SetCardInfo("Pelakka Predation", 120, Rarity.UNCOMMON, mage.cards.p.PelakkaPredation.class));
        cards.add(new SetCardInfo("Phylath, World Sculptor", 234, Rarity.RARE, mage.cards.p.PhylathWorldSculptor.class));
        cards.add(new SetCardInfo("Pillarverge Pathway", 263, Rarity.RARE, mage.cards.p.PillarvergePathway.class));
        cards.add(new SetCardInfo("Plains", 266, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Practiced Tactics", 32, Rarity.COMMON, mage.cards.p.PracticedTactics.class));
        cards.add(new SetCardInfo("Pressure Point", 33, Rarity.COMMON, mage.cards.p.PressurePoint.class));
        cards.add(new SetCardInfo("Prowling Felidar", 34, Rarity.COMMON, mage.cards.p.ProwlingFelidar.class));
        cards.add(new SetCardInfo("Pyroclastic Hellion", 152, Rarity.COMMON, mage.cards.p.PyroclasticHellion.class));
        cards.add(new SetCardInfo("Rabid Bite", 199, Rarity.COMMON, mage.cards.r.RabidBite.class));
        cards.add(new SetCardInfo("Ravager's Mace", 235, Rarity.UNCOMMON, mage.cards.r.RavagersMace.class));
        cards.add(new SetCardInfo("Reclaim the Wastes", 200, Rarity.COMMON, mage.cards.r.ReclaimTheWastes.class));
        cards.add(new SetCardInfo("Relic Amulet", 247, Rarity.UNCOMMON, mage.cards.r.RelicAmulet.class));
        cards.add(new SetCardInfo("Relic Axe", 248, Rarity.UNCOMMON, mage.cards.r.RelicAxe.class));
        cards.add(new SetCardInfo("Relic Golem", 249, Rarity.UNCOMMON, mage.cards.r.RelicGolem.class));
        cards.add(new SetCardInfo("Relic Robber", 153, Rarity.RARE, mage.cards.r.RelicRobber.class));
        cards.add(new SetCardInfo("Relic Vial", 250, Rarity.UNCOMMON, mage.cards.r.RelicVial.class));
        cards.add(new SetCardInfo("Resolute Strike", 35, Rarity.COMMON, mage.cards.r.ResoluteStrike.class));
        cards.add(new SetCardInfo("Risen Riptide", 73, Rarity.COMMON, mage.cards.r.RisenRiptide.class));
        cards.add(new SetCardInfo("Riverglide Pathway", 264, Rarity.RARE, mage.cards.r.RiverglidePathway.class));
        cards.add(new SetCardInfo("Rockslide Sorcerer", 154, Rarity.UNCOMMON, mage.cards.r.RockslideSorcerer.class));
        cards.add(new SetCardInfo("Roil Eruption", 155, Rarity.COMMON, mage.cards.r.RoilEruption.class));
        cards.add(new SetCardInfo("Roiling Regrowth", 201, Rarity.UNCOMMON, mage.cards.r.RoilingRegrowth.class));
        cards.add(new SetCardInfo("Roost of Drakes", 74, Rarity.UNCOMMON, mage.cards.r.RoostOfDrakes.class));
        cards.add(new SetCardInfo("Ruin Crab", 75, Rarity.UNCOMMON, mage.cards.r.RuinCrab.class));
        cards.add(new SetCardInfo("Scale the Heights", 202, Rarity.COMMON, mage.cards.s.ScaleTheHeights.class));
        cards.add(new SetCardInfo("Scavenged Blade", 157, Rarity.COMMON, mage.cards.s.ScavengedBlade.class));
        cards.add(new SetCardInfo("Scion of the Swarm", 121, Rarity.UNCOMMON, mage.cards.s.ScionOfTheSwarm.class));
        cards.add(new SetCardInfo("Scorch Rider", 158, Rarity.COMMON, mage.cards.s.ScorchRider.class));
        cards.add(new SetCardInfo("Scourge of the Skyclaves", 122, Rarity.MYTHIC, mage.cards.s.ScourgeOfTheSkyclaves.class));
        cards.add(new SetCardInfo("Scute Swarm", 203, Rarity.RARE, mage.cards.s.ScuteSwarm.class));
        cards.add(new SetCardInfo("Sea Gate Banneret", 36, Rarity.COMMON, mage.cards.s.SeaGateBanneret.class));
        cards.add(new SetCardInfo("Sea Gate Colossus", 251, Rarity.COMMON, mage.cards.s.SeaGateColossus.class));
        cards.add(new SetCardInfo("Sea Gate Restoration", 76, Rarity.MYTHIC, mage.cards.s.SeaGateRestoration.class));
        cards.add(new SetCardInfo("Sea Gate Stormcaller", 77, Rarity.MYTHIC, mage.cards.s.SeaGateStormcaller.class));
        cards.add(new SetCardInfo("Sea Gate, Reborn", 76, Rarity.MYTHIC, mage.cards.s.SeaGateReborn.class));
        cards.add(new SetCardInfo("Seafloor Stalker", 78, Rarity.COMMON, mage.cards.s.SeafloorStalker.class));
        cards.add(new SetCardInfo("Sejiri Glacier", 37, Rarity.UNCOMMON, mage.cards.s.SejiriGlacier.class));
        cards.add(new SetCardInfo("Sejiri Shelter", 37, Rarity.UNCOMMON, mage.cards.s.SejiriShelter.class));
        cards.add(new SetCardInfo("Shadow Stinger", 123, Rarity.UNCOMMON, mage.cards.s.ShadowStinger.class));
        cards.add(new SetCardInfo("Shadows' Verdict", 124, Rarity.RARE, mage.cards.s.ShadowsVerdict.class));
        cards.add(new SetCardInfo("Shatterskull Charger", 159, Rarity.RARE, mage.cards.s.ShatterskullCharger.class));
        cards.add(new SetCardInfo("Shatterskull Minotaur", 160, Rarity.UNCOMMON, mage.cards.s.ShatterskullMinotaur.class));
        cards.add(new SetCardInfo("Shatterskull Smashing", 161, Rarity.MYTHIC, mage.cards.s.ShatterskullSmashing.class));
        cards.add(new SetCardInfo("Shatterskull, the Hammer Pass", 161, Rarity.MYTHIC, mage.cards.s.ShatterskullTheHammerPass.class));
        cards.add(new SetCardInfo("Shell Shield", 79, Rarity.COMMON, mage.cards.s.ShellShield.class));
        cards.add(new SetCardInfo("Shepherd of Heroes", 38, Rarity.COMMON, mage.cards.s.ShepherdOfHeroes.class));
        cards.add(new SetCardInfo("Silundi Isle", 80, Rarity.UNCOMMON, mage.cards.s.SilundiIsle.class));
        cards.add(new SetCardInfo("Silundi Vision", 80, Rarity.UNCOMMON, mage.cards.s.SilundiVision.class));
        cards.add(new SetCardInfo("Sizzling Barrage", 162, Rarity.COMMON, mage.cards.s.SizzlingBarrage.class));
        cards.add(new SetCardInfo("Skyclave Basilica", 40, Rarity.UNCOMMON, mage.cards.s.SkyclaveBasilica.class));
        cards.add(new SetCardInfo("Skyclave Cleric", 40, Rarity.UNCOMMON, mage.cards.s.SkyclaveCleric.class));
        cards.add(new SetCardInfo("Skyclave Geopede", 163, Rarity.UNCOMMON, mage.cards.s.SkyclaveGeopede.class));
        cards.add(new SetCardInfo("Skyclave Pick-Axe", 204, Rarity.UNCOMMON, mage.cards.s.SkyclavePickAxe.class));
        cards.add(new SetCardInfo("Skyclave Plunder", 81, Rarity.UNCOMMON, mage.cards.s.SkyclavePlunder.class));
        cards.add(new SetCardInfo("Skyclave Relic", 252, Rarity.RARE, mage.cards.s.SkyclaveRelic.class));
        cards.add(new SetCardInfo("Skyclave Sentinel", 253, Rarity.COMMON, mage.cards.s.SkyclaveSentinel.class));
        cards.add(new SetCardInfo("Skyclave Shade", 125, Rarity.RARE, mage.cards.s.SkyclaveShade.class));
        cards.add(new SetCardInfo("Skyclave Shadowcat", 126, Rarity.UNCOMMON, mage.cards.s.SkyclaveShadowcat.class));
        cards.add(new SetCardInfo("Skyclave Squid", 82, Rarity.COMMON, mage.cards.s.SkyclaveSquid.class));
        cards.add(new SetCardInfo("Smite the Monstrous", 42, Rarity.COMMON, mage.cards.s.SmiteTheMonstrous.class));
        cards.add(new SetCardInfo("Sneaking Guide", 164, Rarity.COMMON, mage.cards.s.SneakingGuide.class));
        cards.add(new SetCardInfo("Soaring Thought-Thief", 236, Rarity.UNCOMMON, mage.cards.s.SoaringThoughtThief.class));
        cards.add(new SetCardInfo("Song-Mad Ruins", 165, Rarity.UNCOMMON, mage.cards.s.SongMadRuins.class));
        cards.add(new SetCardInfo("Song-Mad Treachery", 165, Rarity.UNCOMMON, mage.cards.s.SongMadTreachery.class));
        cards.add(new SetCardInfo("Soul Shatter", 127, Rarity.RARE, mage.cards.s.SoulShatter.class));
        cards.add(new SetCardInfo("Spare Supplies", 254, Rarity.COMMON, mage.cards.s.SpareSupplies.class));
        cards.add(new SetCardInfo("Spikefield Cave", 166, Rarity.UNCOMMON, mage.cards.s.SpikefieldCave.class));
        cards.add(new SetCardInfo("Spikefield Hazard", 166, Rarity.UNCOMMON, mage.cards.s.SpikefieldHazard.class));
        cards.add(new SetCardInfo("Spitfire Lagac", 167, Rarity.COMMON, mage.cards.s.SpitfireLagac.class));
        cards.add(new SetCardInfo("Spoils of Adventure", 237, Rarity.UNCOMMON, mage.cards.s.SpoilsOfAdventure.class));
        cards.add(new SetCardInfo("Springmantle Cleric", 205, Rarity.UNCOMMON, mage.cards.s.SpringmantleCleric.class));
        cards.add(new SetCardInfo("Squad Commander", 41, Rarity.RARE, mage.cards.s.SquadCommander.class));
        cards.add(new SetCardInfo("Stonework Packbeast", 255, Rarity.COMMON, mage.cards.s.StoneworkPackbeast.class));
        cards.add(new SetCardInfo("Strength of Solidarity", 206, Rarity.COMMON, mage.cards.s.StrengthOfSolidarity.class));
        cards.add(new SetCardInfo("Subtle Strike", 128, Rarity.COMMON, mage.cards.s.SubtleStrike.class));
        cards.add(new SetCardInfo("Sure-Footed Infiltrator", 83, Rarity.UNCOMMON, mage.cards.s.SureFootedInfiltrator.class));
        cards.add(new SetCardInfo("Swamp", 272, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swarm Shambler", 207, Rarity.RARE, mage.cards.s.SwarmShambler.class));
        cards.add(new SetCardInfo("Synchronized Spellcraft", 168, Rarity.COMMON, mage.cards.s.SynchronizedSpellcraft.class));
        cards.add(new SetCardInfo("Tajuru Blightblade", 208, Rarity.COMMON, mage.cards.t.TajuruBlightblade.class));
        cards.add(new SetCardInfo("Tajuru Paragon", 209, Rarity.RARE, mage.cards.t.TajuruParagon.class));
        cards.add(new SetCardInfo("Tajuru Snarecaster", 210, Rarity.COMMON, mage.cards.t.TajuruSnarecaster.class));
        cards.add(new SetCardInfo("Tangled Florahedron", 211, Rarity.UNCOMMON, mage.cards.t.TangledFlorahedron.class));
        cards.add(new SetCardInfo("Tangled Vale", 211, Rarity.UNCOMMON, mage.cards.t.TangledVale.class));
        cards.add(new SetCardInfo("Taunting Arbormage", 212, Rarity.UNCOMMON, mage.cards.t.TauntingArbormage.class));
        cards.add(new SetCardInfo("Tazeem Raptor", 43, Rarity.COMMON, mage.cards.t.TazeemRaptor.class));
        cards.add(new SetCardInfo("Tazeem Roilmage", 84, Rarity.COMMON, mage.cards.t.TazeemRoilmage.class));
        cards.add(new SetCardInfo("Tazri, Beacon of Unity", 44, Rarity.MYTHIC, mage.cards.t.TazriBeaconOfUnity.class));
        cards.add(new SetCardInfo("Teeterpeak Ambusher", 169, Rarity.COMMON, mage.cards.t.TeeterpeakAmbusher.class));
        cards.add(new SetCardInfo("Territorial Scythecat", 213, Rarity.COMMON, mage.cards.t.TerritorialScythecat.class));
        cards.add(new SetCardInfo("Thieving Skydiver", 85, Rarity.RARE, mage.cards.t.ThievingSkydiver.class));
        cards.add(new SetCardInfo("Throne of Makindi", 265, Rarity.RARE, mage.cards.t.ThroneOfMakindi.class));
        cards.add(new SetCardInfo("Thundering Rebuke", 170, Rarity.UNCOMMON, mage.cards.t.ThunderingRebuke.class));
        cards.add(new SetCardInfo("Thundering Sparkmage", 171, Rarity.UNCOMMON, mage.cards.t.ThunderingSparkmage.class));
        cards.add(new SetCardInfo("Thwart the Grave", 130, Rarity.UNCOMMON, mage.cards.t.ThwartTheGrave.class));
        cards.add(new SetCardInfo("Timbercrown Pathway", 261, Rarity.RARE, mage.cards.t.TimbercrownPathway.class));
        cards.add(new SetCardInfo("Tormenting Voice", 172, Rarity.COMMON, mage.cards.t.TormentingVoice.class));
        cards.add(new SetCardInfo("Tuktuk Rubblefort", 173, Rarity.COMMON, mage.cards.t.TuktukRubblefort.class));
        cards.add(new SetCardInfo("Turntimber Ascetic", 214, Rarity.COMMON, mage.cards.t.TurntimberAscetic.class));
        cards.add(new SetCardInfo("Umara Mystic", 238, Rarity.UNCOMMON, mage.cards.u.UmaraMystic.class));
        cards.add(new SetCardInfo("Umara Skyfalls", 86, Rarity.UNCOMMON, mage.cards.u.UmaraSkyfalls.class));
        cards.add(new SetCardInfo("Umara Wizard", 86, Rarity.UNCOMMON, mage.cards.u.UmaraWizard.class));
        cards.add(new SetCardInfo("Utility Knife", 256, Rarity.COMMON, mage.cards.u.UtilityKnife.class));
        cards.add(new SetCardInfo("Valakut Awakening", 174, Rarity.RARE, mage.cards.v.ValakutAwakening.class));
        cards.add(new SetCardInfo("Valakut Stoneforge", 174, Rarity.RARE, mage.cards.v.ValakutStoneforge.class));
        cards.add(new SetCardInfo("Vanquish the Weak", 131, Rarity.COMMON, mage.cards.v.VanquishTheWeak.class));
        cards.add(new SetCardInfo("Vastwood Fortification", 216, Rarity.UNCOMMON, mage.cards.v.VastwoodFortification.class));
        cards.add(new SetCardInfo("Vastwood Surge", 217, Rarity.UNCOMMON, mage.cards.v.VastwoodSurge.class));
        cards.add(new SetCardInfo("Vastwood Thicket", 216, Rarity.UNCOMMON, mage.cards.v.VastwoodThicket.class));
        cards.add(new SetCardInfo("Veteran Adventurer", 218, Rarity.UNCOMMON, mage.cards.v.VeteranAdventurer.class));
        cards.add(new SetCardInfo("Vine Gecko", 219, Rarity.UNCOMMON, mage.cards.v.VineGecko.class));
        cards.add(new SetCardInfo("Wayward Guide-Beast", 176, Rarity.RARE, mage.cards.w.WaywardGuideBeast.class));
        cards.add(new SetCardInfo("Windrider Wizard", 87, Rarity.UNCOMMON, mage.cards.w.WindriderWizard.class));
        cards.add(new SetCardInfo("Yasharn, Implacable Earth", 240, Rarity.RARE, mage.cards.y.YasharnImplacableEarth.class));
        cards.add(new SetCardInfo("Zagras, Thief of Heartbeats", 241, Rarity.RARE, mage.cards.z.ZagrasThiefOfHeartbeats.class));
        cards.add(new SetCardInfo("Zareth San, the Trickster", 242, Rarity.RARE, mage.cards.z.ZarethSanTheTrickster.class));
        cards.add(new SetCardInfo("Zof Bloodbog", 132, Rarity.UNCOMMON, mage.cards.z.ZofBloodbog.class));
        cards.add(new SetCardInfo("Zof Consumption", 132, Rarity.UNCOMMON, mage.cards.z.ZofConsumption.class));
        cards.add(new SetCardInfo("Zulaport Duelist", 88, Rarity.COMMON, mage.cards.z.ZulaportDuelist.class));

        cards.removeIf(setCardInfo -> unfinished.contains(setCardInfo.getName())); // remove when mechanics are fully implemented
    }
}
