
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.collation.BoosterCollator;
import mage.collation.BoosterStructure;
import mage.collation.CardRun;
import mage.collation.RarityConfiguration;
import mage.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author North
 */
public final class Prophecy extends ExpansionSet {

    private static final Prophecy instance = new Prophecy();

    public static Prophecy getInstance() {
        return instance;
    }

    private Prophecy() {
        super("Prophecy", "PCY", ExpansionSet.buildDate(2000, 4, 27), SetType.EXPANSION);
        this.blockName = "Masques";
        this.parentSet = MercadianMasques.getInstance();
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
        cards.add(new SetCardInfo("Abolish", 1, Rarity.UNCOMMON, mage.cards.a.Abolish.class));
        cards.add(new SetCardInfo("Agent of Shauku", 55, Rarity.COMMON, mage.cards.a.AgentOfShauku.class));
        cards.add(new SetCardInfo("Alexi's Cloak", 29, Rarity.COMMON, mage.cards.a.AlexisCloak.class));
        cards.add(new SetCardInfo("Alexi, Zephyr Mage", 28, Rarity.RARE, mage.cards.a.AlexiZephyrMage.class));
        cards.add(new SetCardInfo("Aura Fracture", 2, Rarity.COMMON, mage.cards.a.AuraFracture.class));
        cards.add(new SetCardInfo("Avatar of Fury", 82, Rarity.RARE, mage.cards.a.AvatarOfFury.class));
        cards.add(new SetCardInfo("Avatar of Hope", 3, Rarity.RARE, mage.cards.a.AvatarOfHope.class));
        cards.add(new SetCardInfo("Avatar of Might", 109, Rarity.RARE, mage.cards.a.AvatarOfMight.class));
        cards.add(new SetCardInfo("Avatar of Will", 30, Rarity.RARE, mage.cards.a.AvatarOfWill.class));
        cards.add(new SetCardInfo("Avatar of Woe", 56, Rarity.RARE, mage.cards.a.AvatarOfWoe.class));
        cards.add(new SetCardInfo("Barbed Field", 83, Rarity.UNCOMMON, mage.cards.b.BarbedField.class));
        cards.add(new SetCardInfo("Blessed Wind", 4, Rarity.RARE, mage.cards.b.BlessedWind.class));
        cards.add(new SetCardInfo("Bog Elemental", 57, Rarity.RARE, mage.cards.b.BogElemental.class));
        cards.add(new SetCardInfo("Bog Glider", 58, Rarity.COMMON, mage.cards.b.BogGlider.class));
        cards.add(new SetCardInfo("Branded Brawlers", 84, Rarity.COMMON, mage.cards.b.BrandedBrawlers.class));
        cards.add(new SetCardInfo("Brutal Suppression", 85, Rarity.UNCOMMON, mage.cards.b.BrutalSuppression.class));
        cards.add(new SetCardInfo("Calming Verse", 110, Rarity.COMMON, mage.cards.c.CalmingVerse.class));
        cards.add(new SetCardInfo("Celestial Convergence", 5, Rarity.RARE, mage.cards.c.CelestialConvergence.class));
        cards.add(new SetCardInfo("Chilling Apparition", 59, Rarity.UNCOMMON, mage.cards.c.ChillingApparition.class));
        cards.add(new SetCardInfo("Chimeric Idol", 136, Rarity.UNCOMMON, mage.cards.c.ChimericIdol.class));
        cards.add(new SetCardInfo("Citadel of Pain", 86, Rarity.UNCOMMON, mage.cards.c.CitadelOfPain.class));
        cards.add(new SetCardInfo("Coastal Hornclaw", 31, Rarity.COMMON, mage.cards.c.CoastalHornclaw.class));
        cards.add(new SetCardInfo("Coffin Puppets", 60, Rarity.RARE, mage.cards.c.CoffinPuppets.class));
        cards.add(new SetCardInfo("Copper-Leaf Angel", 137, Rarity.RARE, mage.cards.c.CopperLeafAngel.class));
        cards.add(new SetCardInfo("Darba", 111, Rarity.UNCOMMON, mage.cards.d.Darba.class));
        cards.add(new SetCardInfo("Death Charmer", 61, Rarity.COMMON, mage.cards.d.DeathCharmer.class));
        cards.add(new SetCardInfo("Denying Wind", 32, Rarity.RARE, mage.cards.d.DenyingWind.class));
        cards.add(new SetCardInfo("Despoil", 62, Rarity.COMMON, mage.cards.d.Despoil.class));
        cards.add(new SetCardInfo("Devastate", 87, Rarity.COMMON, mage.cards.d.Devastate.class));
        cards.add(new SetCardInfo("Diving Griffin", 6, Rarity.COMMON, mage.cards.d.DivingGriffin.class));
        cards.add(new SetCardInfo("Dual Nature", 112, Rarity.RARE, mage.cards.d.DualNature.class));
        cards.add(new SetCardInfo("Elephant Resurgence", 113, Rarity.RARE, mage.cards.e.ElephantResurgence.class));
        cards.add(new SetCardInfo("Endbringer's Revel", 63, Rarity.UNCOMMON, mage.cards.e.EndbringersRevel.class));
        cards.add(new SetCardInfo("Entangler", 7, Rarity.UNCOMMON, mage.cards.e.Entangler.class));
        cards.add(new SetCardInfo("Excavation", 33, Rarity.UNCOMMON, mage.cards.e.Excavation.class));
        cards.add(new SetCardInfo("Excise", 8, Rarity.COMMON, mage.cards.e.Excise.class));
        cards.add(new SetCardInfo("Fault Riders", 88, Rarity.COMMON, mage.cards.f.FaultRiders.class));
        cards.add(new SetCardInfo("Fen Stalker", 64, Rarity.COMMON, mage.cards.f.FenStalker.class));
        cards.add(new SetCardInfo("Fickle Efreet", 89, Rarity.RARE, mage.cards.f.FickleEfreet.class));
        cards.add(new SetCardInfo("Flameshot", 90, Rarity.UNCOMMON, mage.cards.f.Flameshot.class));
        cards.add(new SetCardInfo("Flay", 65, Rarity.COMMON, mage.cards.f.Flay.class));
        cards.add(new SetCardInfo("Flowering Field", 9, Rarity.UNCOMMON, mage.cards.f.FloweringField.class));
        cards.add(new SetCardInfo("Foil", 34, Rarity.UNCOMMON, mage.cards.f.Foil.class));
        cards.add(new SetCardInfo("Forgotten Harvest", 114, Rarity.RARE, mage.cards.f.ForgottenHarvest.class));
        cards.add(new SetCardInfo("Glittering Lion", 10, Rarity.UNCOMMON, mage.cards.g.GlitteringLion.class));
        cards.add(new SetCardInfo("Glittering Lynx", 11, Rarity.COMMON, mage.cards.g.GlitteringLynx.class));
        cards.add(new SetCardInfo("Greel's Caress", 67, Rarity.COMMON, mage.cards.g.GreelsCaress.class));
        cards.add(new SetCardInfo("Greel, Mind Raker", 66, Rarity.RARE, mage.cards.g.GreelMindRaker.class));
        cards.add(new SetCardInfo("Gulf Squid", 35, Rarity.COMMON, mage.cards.g.GulfSquid.class));
        cards.add(new SetCardInfo("Hazy Homunculus", 36, Rarity.COMMON, mage.cards.h.HazyHomunculus.class));
        cards.add(new SetCardInfo("Heightened Awareness", 37, Rarity.RARE, mage.cards.h.HeightenedAwareness.class));
        cards.add(new SetCardInfo("Hollow Warrior", 138, Rarity.UNCOMMON, mage.cards.h.HollowWarrior.class));
        cards.add(new SetCardInfo("Infernal Genesis", 68, Rarity.RARE, mage.cards.i.InfernalGenesis.class));
        cards.add(new SetCardInfo("Inflame", 91, Rarity.COMMON, mage.cards.i.Inflame.class));
        cards.add(new SetCardInfo("Jeweled Spirit", 12, Rarity.RARE, mage.cards.j.JeweledSpirit.class));
        cards.add(new SetCardInfo("Jolrael, Empress of Beasts", 115, Rarity.RARE, mage.cards.j.JolraelEmpressOfBeasts.class));
        cards.add(new SetCardInfo("Jolrael's Favor", 116, Rarity.COMMON, mage.cards.j.JolraelsFavor.class));
        cards.add(new SetCardInfo("Keldon Arsonist", 92, Rarity.UNCOMMON, mage.cards.k.KeldonArsonist.class));
        cards.add(new SetCardInfo("Keldon Battlewagon", 139, Rarity.RARE, mage.cards.k.KeldonBattlewagon.class));
        cards.add(new SetCardInfo("Keldon Berserker", 93, Rarity.COMMON, mage.cards.k.KeldonBerserker.class));
        cards.add(new SetCardInfo("Keldon Firebombers", 94, Rarity.RARE, mage.cards.k.KeldonFirebombers.class));
        cards.add(new SetCardInfo("Latulla, Keldon Overseer", 95, Rarity.RARE, mage.cards.l.LatullaKeldonOverseer.class));
        cards.add(new SetCardInfo("Latulla's Orders", 96, Rarity.COMMON, mage.cards.l.LatullasOrders.class));
        cards.add(new SetCardInfo("Lesser Gargadon", 97, Rarity.UNCOMMON, mage.cards.l.LesserGargadon.class));
        cards.add(new SetCardInfo("Living Terrain", 117, Rarity.UNCOMMON, mage.cards.l.LivingTerrain.class));
        cards.add(new SetCardInfo("Mageta's Boon", 14, Rarity.COMMON, mage.cards.m.MagetasBoon.class));
        cards.add(new SetCardInfo("Mageta the Lion", 13, Rarity.RARE, mage.cards.m.MagetaTheLion.class));
        cards.add(new SetCardInfo("Mana Vapors", 38, Rarity.UNCOMMON, mage.cards.m.ManaVapors.class));
        cards.add(new SetCardInfo("Marsh Boa", 118, Rarity.COMMON, mage.cards.m.MarshBoa.class));
        cards.add(new SetCardInfo("Mercenary Informer", 15, Rarity.RARE, mage.cards.m.MercenaryInformer.class));
        cards.add(new SetCardInfo("Mine Bearer", 16, Rarity.COMMON, mage.cards.m.MineBearer.class));
        cards.add(new SetCardInfo("Mirror Strike", 17, Rarity.UNCOMMON, mage.cards.m.MirrorStrike.class));
        cards.add(new SetCardInfo("Mungha Wurm", 119, Rarity.RARE, mage.cards.m.MunghaWurm.class));
        cards.add(new SetCardInfo("Nakaya Shade", 69, Rarity.UNCOMMON, mage.cards.n.NakayaShade.class));
        cards.add(new SetCardInfo("Noxious Field", 70, Rarity.UNCOMMON, mage.cards.n.NoxiousField.class));
        cards.add(new SetCardInfo("Outbreak", 71, Rarity.UNCOMMON, mage.cards.o.Outbreak.class));
        cards.add(new SetCardInfo("Overburden", 39, Rarity.RARE, mage.cards.o.Overburden.class));
        cards.add(new SetCardInfo("Panic Attack", 98, Rarity.COMMON, mage.cards.p.PanicAttack.class));
        cards.add(new SetCardInfo("Pit Raptor", 72, Rarity.UNCOMMON, mage.cards.p.PitRaptor.class));
        cards.add(new SetCardInfo("Plague Fiend", 73, Rarity.COMMON, mage.cards.p.PlagueFiend.class));
        cards.add(new SetCardInfo("Plague Wind", 74, Rarity.RARE, mage.cards.p.PlagueWind.class));
        cards.add(new SetCardInfo("Psychic Theft", 40, Rarity.RARE, mage.cards.p.PsychicTheft.class));
        cards.add(new SetCardInfo("Pygmy Razorback", 120, Rarity.COMMON, mage.cards.p.PygmyRazorback.class));
        cards.add(new SetCardInfo("Quicksilver Wall", 41, Rarity.UNCOMMON, mage.cards.q.QuicksilverWall.class));
        cards.add(new SetCardInfo("Rebel Informer", 75, Rarity.RARE, mage.cards.r.RebelInformer.class));
        cards.add(new SetCardInfo("Rethink", 42, Rarity.COMMON, mage.cards.r.Rethink.class));
        cards.add(new SetCardInfo("Reveille Squad", 18, Rarity.UNCOMMON, mage.cards.r.ReveilleSquad.class));
        cards.add(new SetCardInfo("Rhystic Cave", 142, Rarity.UNCOMMON, mage.cards.r.RhysticCave.class));
        cards.add(new SetCardInfo("Rhystic Circle", 19, Rarity.COMMON, mage.cards.r.RhysticCircle.class));
        cards.add(new SetCardInfo("Rhystic Deluge", 43, Rarity.COMMON, mage.cards.r.RhysticDeluge.class));
        cards.add(new SetCardInfo("Rhystic Lightning", 99, Rarity.COMMON, mage.cards.r.RhysticLightning.class));
        cards.add(new SetCardInfo("Rhystic Scrying", 44, Rarity.UNCOMMON, mage.cards.r.RhysticScrying.class));
        cards.add(new SetCardInfo("Rhystic Shield", 20, Rarity.COMMON, mage.cards.r.RhysticShield.class));
        cards.add(new SetCardInfo("Rhystic Study", 45, Rarity.COMMON, mage.cards.r.RhysticStudy.class));
        cards.add(new SetCardInfo("Rhystic Syphon", 76, Rarity.UNCOMMON, mage.cards.r.RhysticSyphon.class));
        cards.add(new SetCardInfo("Rhystic Tutor", 77, Rarity.RARE, mage.cards.r.RhysticTutor.class));
        cards.add(new SetCardInfo("Ribbon Snake", 46, Rarity.COMMON, mage.cards.r.RibbonSnake.class));
        cards.add(new SetCardInfo("Rib Cage Spider", 121, Rarity.COMMON, mage.cards.r.RibCageSpider.class));
        cards.add(new SetCardInfo("Ridgeline Rager", 100, Rarity.COMMON, mage.cards.r.RidgelineRager.class));
        cards.add(new SetCardInfo("Root Cage", 122, Rarity.UNCOMMON, mage.cards.r.RootCage.class));
        cards.add(new SetCardInfo("Samite Sanctuary", 21, Rarity.RARE, mage.cards.s.SamiteSanctuary.class));
        cards.add(new SetCardInfo("Scoria Cat", 101, Rarity.UNCOMMON, mage.cards.s.ScoriaCat.class));
        cards.add(new SetCardInfo("Search for Survivors", 102, Rarity.RARE, mage.cards.s.SearchForSurvivors.class));
        cards.add(new SetCardInfo("Searing Wind", 103, Rarity.RARE, mage.cards.s.SearingWind.class));
        cards.add(new SetCardInfo("Sheltering Prayers", 22, Rarity.RARE, mage.cards.s.ShelteringPrayers.class));
        cards.add(new SetCardInfo("Shield Dancer", 23, Rarity.UNCOMMON, mage.cards.s.ShieldDancer.class));
        cards.add(new SetCardInfo("Shrouded Serpent", 47, Rarity.RARE, mage.cards.s.ShroudedSerpent.class));
        cards.add(new SetCardInfo("Silt Crawler", 123, Rarity.COMMON, mage.cards.s.SiltCrawler.class));
        cards.add(new SetCardInfo("Snag", 124, Rarity.UNCOMMON, mage.cards.s.Snag.class));
        cards.add(new SetCardInfo("Soul Charmer", 24, Rarity.COMMON, mage.cards.s.SoulCharmer.class));
        cards.add(new SetCardInfo("Soul Strings", 78, Rarity.COMMON, mage.cards.s.SoulStrings.class));
        cards.add(new SetCardInfo("Spiketail Drake", 48, Rarity.UNCOMMON, mage.cards.s.SpiketailDrake.class));
        cards.add(new SetCardInfo("Spiketail Hatchling", 49, Rarity.COMMON, mage.cards.s.SpiketailHatchling.class));
        cards.add(new SetCardInfo("Spitting Spider", 125, Rarity.UNCOMMON, mage.cards.s.SpittingSpider.class));
        cards.add(new SetCardInfo("Spore Frog", 126, Rarity.COMMON, mage.cards.s.SporeFrog.class));
        cards.add(new SetCardInfo("Spur Grappler", 104, Rarity.COMMON, mage.cards.s.SpurGrappler.class));
        cards.add(new SetCardInfo("Squirrel Wrangler", 127, Rarity.RARE, mage.cards.s.SquirrelWrangler.class));
        cards.add(new SetCardInfo("Steal Strength", 79, Rarity.COMMON, mage.cards.s.StealStrength.class));
        cards.add(new SetCardInfo("Stormwatch Eagle", 50, Rarity.COMMON, mage.cards.s.StormwatchEagle.class));
        cards.add(new SetCardInfo("Sunken Field", 51, Rarity.UNCOMMON, mage.cards.s.SunkenField.class));
        cards.add(new SetCardInfo("Sword Dancer", 25, Rarity.UNCOMMON, mage.cards.s.SwordDancer.class));
        cards.add(new SetCardInfo("Task Mage Assembly", 105, Rarity.RARE, mage.cards.t.TaskMageAssembly.class));
        cards.add(new SetCardInfo("Thresher Beast", 128, Rarity.COMMON, mage.cards.t.ThresherBeast.class));
        cards.add(new SetCardInfo("Thrive", 129, Rarity.COMMON, mage.cards.t.Thrive.class));
        cards.add(new SetCardInfo("Trenching Steed", 26, Rarity.COMMON, mage.cards.t.TrenchingSteed.class));
        cards.add(new SetCardInfo("Troubled Healer", 27, Rarity.COMMON, mage.cards.t.TroubledHealer.class));
        cards.add(new SetCardInfo("Troublesome Spirit", 52, Rarity.RARE, mage.cards.t.TroublesomeSpirit.class));
        cards.add(new SetCardInfo("Verdant Field", 130, Rarity.UNCOMMON, mage.cards.v.VerdantField.class));
        cards.add(new SetCardInfo("Veteran Brawlers", 106, Rarity.RARE, mage.cards.v.VeteranBrawlers.class));
        cards.add(new SetCardInfo("Vintara Elephant", 131, Rarity.COMMON, mage.cards.v.VintaraElephant.class));
        cards.add(new SetCardInfo("Vintara Snapper", 132, Rarity.UNCOMMON, mage.cards.v.VintaraSnapper.class));
        cards.add(new SetCardInfo("Vitalizing Wind", 133, Rarity.RARE, mage.cards.v.VitalizingWind.class));
        cards.add(new SetCardInfo("Wall of Vipers", 80, Rarity.UNCOMMON, mage.cards.w.WallOfVipers.class));
        cards.add(new SetCardInfo("Well of Discovery", 140, Rarity.RARE, mage.cards.w.WellOfDiscovery.class));
        cards.add(new SetCardInfo("Well of Life", 141, Rarity.UNCOMMON, mage.cards.w.WellOfLife.class));
        cards.add(new SetCardInfo("Whip Sergeant", 107, Rarity.UNCOMMON, mage.cards.w.WhipSergeant.class));
        cards.add(new SetCardInfo("Whipstitched Zombie", 81, Rarity.COMMON, mage.cards.w.WhipstitchedZombie.class));
        cards.add(new SetCardInfo("Wild Might", 134, Rarity.COMMON, mage.cards.w.WildMight.class));
        cards.add(new SetCardInfo("Windscouter", 53, Rarity.UNCOMMON, mage.cards.w.Windscouter.class));
        cards.add(new SetCardInfo("Wing Storm", 135, Rarity.UNCOMMON, mage.cards.w.WingStorm.class));
        cards.add(new SetCardInfo("Wintermoon Mesa", 143, Rarity.RARE, mage.cards.w.WintermoonMesa.class));
        cards.add(new SetCardInfo("Withdraw", 54, Rarity.COMMON, mage.cards.w.Withdraw.class));
        cards.add(new SetCardInfo("Zerapa Minotaur", 108, Rarity.COMMON, mage.cards.z.ZerapaMinotaur.class));
    }

    @Override
    public BoosterCollator createCollator() {
        return new ProphecyCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/pls.html
// Using Striped collation
class ProphecyCollator implements BoosterCollator {
    private final CardRun common = new CardRun(10,
         "65","118", "99", "54", "16", "78","128", "91", "36", "24",
        "100", "35", "26", "58","120", "96", "49", "14", "55","126",
         "27", "79","116", "93", "31",  "8", "65","121", "87", "50",
        "129", "88", "29", "20", "61","118", "99", "46",  "2", "79",
         "43", "16", "64","134", "84", "45", "11", "81","123", "91",
          "6", "73", "98", "42","128", "19", "62", "88", "35","121",
         "67", "36","131", "24", "96", "58", "50","129",  "8", "84",
        "120", "14","108", "81", "31","134", "27", "98", "55", "49",
        "104", "78", "43","123", "20","100", "67", "29","131", "19",
         "54","110",  "6", "87", "64", "46","116", "26","104", "62",
         "11", "93", "73", "45", "126", "2","108", "61", "42","110");
    private final CardRun uncommon = new CardRun(12,
         "53","138",  "7", "85","130", "25", "70", "34", "10","107","141", "53",
        "124", "51", "97",  "9", "53","132","101", "69", "83","111", "92","117",
         "76", "90", "48","125", "80", "85","124",  "9","122", "63", "44", "85",
         "23", "41","122",  "7", "34","136", "38","138", "23", "90", "59", "10",
        "135", "71", "83", "70","142",  "1", "72", "41", "71", "25","142","111",
         "72", "44", "18", "41", "59","107", "18", "76", "51","132",  "1", "76",
         "38","138","135","141", "44","122", "86","136","101", "38", "80", "86",
        "101", "17", "72", "25", "92", "48","130", "33", "70", "83", "33", "18",
        "132", "80",  "9","117", "34", "10", "69","125",  "7", "69", "23", "71",
         "63", "97","130", "86","136", "51","107","141", "97","124","142","125",
         "90", "33", "59","111", "63","117", "17", "92","135",  "1", "48", "17");
    private final CardRun rare = new CardRun(false, "28", "82", "3", "109", "30", "56", "4", "57", "5", "60", "137", "32", "112", "113", "89", "114", "66", "37", "68", "12", "115", "139", "94", "95", "13", "15", "119", "39", "74", "40", "75", "77", "21", "102", "103", "22", "47", "127", "105", "52", "106", "133", "140", "143");

    private final BoosterStructure C11 = new BoosterStructure(
            common, common, common, common, common, common,
            common, common, common, common, common
    );
    private final BoosterStructure U3 = new BoosterStructure(uncommon, uncommon, uncommon);
    private final BoosterStructure R1 = new BoosterStructure(rare);

    private final RarityConfiguration commonRuns = new RarityConfiguration(C11);
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(U3);
    private final RarityConfiguration rareRuns = new RarityConfiguration(R1);

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        booster.addAll(uncommonRuns.getNext().makeRun());
        booster.addAll(rareRuns.getNext().makeRun());
        booster.addAll(commonRuns.getNext().makeRun());
        return booster;
    }
}
