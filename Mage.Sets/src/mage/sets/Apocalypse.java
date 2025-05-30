package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.collation.BoosterCollator;
import mage.collation.BoosterStructure;
import mage.collation.CardRun;
import mage.collation.RarityConfiguration;

import java.util.ArrayList;
import java.util.List;

public final class Apocalypse extends ExpansionSet {

    private static final Apocalypse instance = new Apocalypse();

    public static Apocalypse getInstance() {
        return instance;
    }

    private Apocalypse() {
        super("Apocalypse", "APC", ExpansionSet.buildDate(2001, 5, 1), SetType.EXPANSION);
        this.blockName = "Invasion";
        this.parentSet = Invasion.getInstance();
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
        cards.add(new SetCardInfo("Aether Mutation", 91, Rarity.UNCOMMON, mage.cards.a.AetherMutation.class, RETRO_ART));
        cards.add(new SetCardInfo("Ana Disciple", 73, Rarity.COMMON, mage.cards.a.AnaDisciple.class, RETRO_ART));
        cards.add(new SetCardInfo("Ana Sanctuary", 74, Rarity.UNCOMMON, mage.cards.a.AnaSanctuary.class, RETRO_ART));
        cards.add(new SetCardInfo("Anavolver", 75, Rarity.RARE, mage.cards.a.Anavolver.class, RETRO_ART));
        cards.add(new SetCardInfo("Angelfire Crusader", 1, Rarity.COMMON, mage.cards.a.AngelfireCrusader.class, RETRO_ART));
        cards.add(new SetCardInfo("Battlefield Forge", 139, Rarity.RARE, mage.cards.b.BattlefieldForge.class, RETRO_ART));
        cards.add(new SetCardInfo("Bloodfire Colossus", 55, Rarity.RARE, mage.cards.b.BloodfireColossus.class, RETRO_ART));
        cards.add(new SetCardInfo("Bloodfire Dwarf", 56, Rarity.COMMON, mage.cards.b.BloodfireDwarf.class, RETRO_ART));
        cards.add(new SetCardInfo("Bloodfire Infusion", 57, Rarity.COMMON, mage.cards.b.BloodfireInfusion.class, RETRO_ART));
        cards.add(new SetCardInfo("Bloodfire Kavu", 58, Rarity.UNCOMMON, mage.cards.b.BloodfireKavu.class, RETRO_ART));
        cards.add(new SetCardInfo("Bog Gnarr", 76, Rarity.COMMON, mage.cards.b.BogGnarr.class, RETRO_ART));
        cards.add(new SetCardInfo("Brass Herald", 133, Rarity.UNCOMMON, mage.cards.b.BrassHerald.class, RETRO_ART));
        cards.add(new SetCardInfo("Captain's Maneuver", 92, Rarity.UNCOMMON, mage.cards.c.CaptainsManeuver.class, RETRO_ART));
        cards.add(new SetCardInfo("Caves of Koilos", 140, Rarity.RARE, mage.cards.c.CavesOfKoilos.class, RETRO_ART));
        cards.add(new SetCardInfo("Ceta Disciple", 19, Rarity.COMMON, mage.cards.c.CetaDisciple.class, RETRO_ART));
        cards.add(new SetCardInfo("Ceta Sanctuary", 20, Rarity.UNCOMMON, mage.cards.c.CetaSanctuary.class, RETRO_ART));
        cards.add(new SetCardInfo("Cetavolver", 21, Rarity.RARE, mage.cards.c.Cetavolver.class, RETRO_ART));
        cards.add(new SetCardInfo("Coalition Flag", 2, Rarity.UNCOMMON, mage.cards.c.CoalitionFlag.class, RETRO_ART));
        cards.add(new SetCardInfo("Coalition Honor Guard", 3, Rarity.COMMON, mage.cards.c.CoalitionHonorGuard.class, RETRO_ART));
        cards.add(new SetCardInfo("Coastal Drake", 22, Rarity.COMMON, mage.cards.c.CoastalDrake.class, RETRO_ART));
        cards.add(new SetCardInfo("Consume Strength", 93, Rarity.COMMON, mage.cards.c.ConsumeStrength.class, RETRO_ART));
        cards.add(new SetCardInfo("Cromat", 94, Rarity.RARE, mage.cards.c.Cromat.class, RETRO_ART));
        cards.add(new SetCardInfo("Dead Ringers", 37, Rarity.COMMON, mage.cards.d.DeadRingers.class, RETRO_ART));
        cards.add(new SetCardInfo("Death Grasp", 95, Rarity.RARE, mage.cards.d.DeathGrasp.class, RETRO_ART));
        cards.add(new SetCardInfo("Death Mutation", 96, Rarity.UNCOMMON, mage.cards.d.DeathMutation.class, RETRO_ART));
        cards.add(new SetCardInfo("Dega Disciple", 4, Rarity.COMMON, mage.cards.d.DegaDisciple.class, RETRO_ART));
        cards.add(new SetCardInfo("Dega Sanctuary", 5, Rarity.UNCOMMON, mage.cards.d.DegaSanctuary.class, RETRO_ART));
        cards.add(new SetCardInfo("Degavolver", 6, Rarity.RARE, mage.cards.d.Degavolver.class, RETRO_ART));
        cards.add(new SetCardInfo("Desolation Angel", 38, Rarity.RARE, mage.cards.d.DesolationAngel.class, RETRO_ART));
        cards.add(new SetCardInfo("Desolation Giant", 59, Rarity.RARE, mage.cards.d.DesolationGiant.class, RETRO_ART));
        cards.add(new SetCardInfo("Diversionary Tactics", 7, Rarity.UNCOMMON, mage.cards.d.DiversionaryTactics.class, RETRO_ART));
        cards.add(new SetCardInfo("Divine Light", 8, Rarity.COMMON, mage.cards.d.DivineLight.class, RETRO_ART));
        cards.add(new SetCardInfo("Dodecapod", 134, Rarity.UNCOMMON, mage.cards.d.Dodecapod.class, RETRO_ART));
        cards.add(new SetCardInfo("Dragon Arch", 135, Rarity.UNCOMMON, mage.cards.d.DragonArch.class, RETRO_ART));
        cards.add(new SetCardInfo("Dwarven Landslide", 60, Rarity.COMMON, mage.cards.d.DwarvenLandslide.class, RETRO_ART));
        cards.add(new SetCardInfo("Dwarven Patrol", 61, Rarity.UNCOMMON, mage.cards.d.DwarvenPatrol.class, RETRO_ART));
        cards.add(new SetCardInfo("Ebony Treefolk", 97, Rarity.UNCOMMON, mage.cards.e.EbonyTreefolk.class, RETRO_ART));
        cards.add(new SetCardInfo("Emblazoned Golem", 136, Rarity.UNCOMMON, mage.cards.e.EmblazonedGolem.class, RETRO_ART));
        cards.add(new SetCardInfo("Enlistment Officer", 9, Rarity.UNCOMMON, mage.cards.e.EnlistmentOfficer.class, RETRO_ART));
        cards.add(new SetCardInfo("Evasive Action", 23, Rarity.UNCOMMON, mage.cards.e.EvasiveAction.class, RETRO_ART));
        cards.add(new SetCardInfo("False Dawn", 10, Rarity.RARE, mage.cards.f.FalseDawn.class, RETRO_ART));
        cards.add(new SetCardInfo("Fervent Charge", 98, Rarity.RARE, mage.cards.f.FerventCharge.class, RETRO_ART));
        cards.add(new SetCardInfo("Fire // Ice", 128, Rarity.UNCOMMON, mage.cards.f.FireIce.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Flowstone Charger", 99, Rarity.UNCOMMON, mage.cards.f.FlowstoneCharger.class, RETRO_ART));
        cards.add(new SetCardInfo("Foul Presence", 39, Rarity.UNCOMMON, mage.cards.f.FoulPresence.class, RETRO_ART));
        cards.add(new SetCardInfo("Fungal Shambler", 100, Rarity.RARE, mage.cards.f.FungalShambler.class, RETRO_ART));
        cards.add(new SetCardInfo("Gaea's Balance", 77, Rarity.UNCOMMON, mage.cards.g.GaeasBalance.class, RETRO_ART));
        cards.add(new SetCardInfo("Gaea's Skyfolk", 101, Rarity.COMMON, mage.cards.g.GaeasSkyfolk.class, RETRO_ART));
        cards.add(new SetCardInfo("Gerrard Capashen", 11, Rarity.RARE, mage.cards.g.GerrardCapashen.class, RETRO_ART));
        cards.add(new SetCardInfo("Gerrard's Verdict", 102, Rarity.UNCOMMON, mage.cards.g.GerrardsVerdict.class, RETRO_ART));
        cards.add(new SetCardInfo("Glade Gnarr", 78, Rarity.COMMON, mage.cards.g.GladeGnarr.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Legionnaire", 103, Rarity.COMMON, mage.cards.g.GoblinLegionnaire.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Ringleader", 62, Rarity.UNCOMMON, mage.cards.g.GoblinRingleader.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Trenches", 104, Rarity.RARE, mage.cards.g.GoblinTrenches.class, RETRO_ART));
        cards.add(new SetCardInfo("Grave Defiler", 40, Rarity.UNCOMMON, mage.cards.g.GraveDefiler.class, RETRO_ART));
        cards.add(new SetCardInfo("Guided Passage", 105, Rarity.RARE, mage.cards.g.GuidedPassage.class, RETRO_ART));
        cards.add(new SetCardInfo("Haunted Angel", 12, Rarity.UNCOMMON, mage.cards.h.HauntedAngel.class, RETRO_ART));
        cards.add(new SetCardInfo("Helionaut", 13, Rarity.COMMON, mage.cards.h.Helionaut.class, RETRO_ART));
        cards.add(new SetCardInfo("Ice Cave", 24, Rarity.RARE, mage.cards.i.IceCave.class, RETRO_ART));
        cards.add(new SetCardInfo("Illuminate", 63, Rarity.UNCOMMON, mage.cards.i.Illuminate.class, RETRO_ART));
        cards.add(new SetCardInfo("Illusion // Reality", 129, Rarity.UNCOMMON, mage.cards.i.IllusionReality.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Index", 25, Rarity.COMMON, mage.cards.i.Index.class, RETRO_ART));
        cards.add(new SetCardInfo("Jaded Response", 26, Rarity.COMMON, mage.cards.j.JadedResponse.class, RETRO_ART));
        cards.add(new SetCardInfo("Jilt", 27, Rarity.COMMON, mage.cards.j.Jilt.class, RETRO_ART));
        cards.add(new SetCardInfo("Jungle Barrier", 106, Rarity.UNCOMMON, mage.cards.j.JungleBarrier.class, RETRO_ART));
        cards.add(new SetCardInfo("Kavu Glider", 64, Rarity.COMMON, mage.cards.k.KavuGlider.class, RETRO_ART));
        cards.add(new SetCardInfo("Kavu Howler", 79, Rarity.UNCOMMON, mage.cards.k.KavuHowler.class, RETRO_ART));
        cards.add(new SetCardInfo("Kavu Mauler", 80, Rarity.RARE, mage.cards.k.KavuMauler.class, RETRO_ART));
        cards.add(new SetCardInfo("Last Caress", 41, Rarity.COMMON, mage.cards.l.LastCaress.class, RETRO_ART));
        cards.add(new SetCardInfo("Last Stand", 107, Rarity.RARE, mage.cards.l.LastStand.class, RETRO_ART));
        cards.add(new SetCardInfo("Lay of the Land", 81, Rarity.COMMON, mage.cards.l.LayOfTheLand.class, RETRO_ART));
        cards.add(new SetCardInfo("Legacy Weapon", 137, Rarity.RARE, mage.cards.l.LegacyWeapon.class, RETRO_ART));
        cards.add(new SetCardInfo("Life // Death", 130, Rarity.UNCOMMON, mage.cards.l.LifeDeath.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Lightning Angel", 108, Rarity.RARE, mage.cards.l.LightningAngel.class, RETRO_ART));
        cards.add(new SetCardInfo("Living Airship", 28, Rarity.COMMON, mage.cards.l.LivingAirship.class, RETRO_ART));
        cards.add(new SetCardInfo("Llanowar Dead", 109, Rarity.COMMON, mage.cards.l.LlanowarDead.class, RETRO_ART));
        cards.add(new SetCardInfo("Llanowar Wastes", 141, Rarity.RARE, mage.cards.l.LlanowarWastes.class, RETRO_ART));
        cards.add(new SetCardInfo("Manacles of Decay", 14, Rarity.COMMON, mage.cards.m.ManaclesOfDecay.class, RETRO_ART));
        cards.add(new SetCardInfo("Martyrs' Tomb", 110, Rarity.UNCOMMON, mage.cards.m.MartyrsTomb.class, RETRO_ART));
        cards.add(new SetCardInfo("Mask of Intolerance", 138, Rarity.RARE, mage.cards.m.MaskOfIntolerance.class, RETRO_ART));
        cards.add(new SetCardInfo("Mind Extraction", 42, Rarity.COMMON, mage.cards.m.MindExtraction.class, RETRO_ART));
        cards.add(new SetCardInfo("Minotaur Illusionist", 111, Rarity.UNCOMMON, mage.cards.m.MinotaurIllusionist.class, RETRO_ART));
        cards.add(new SetCardInfo("Minotaur Tactician", 65, Rarity.COMMON, mage.cards.m.MinotaurTactician.class, RETRO_ART));
        cards.add(new SetCardInfo("Mournful Zombie", 43, Rarity.COMMON, mage.cards.m.MournfulZombie.class, RETRO_ART));
        cards.add(new SetCardInfo("Mystic Snake", 112, Rarity.RARE, mage.cards.m.MysticSnake.class, RETRO_ART));
        cards.add(new SetCardInfo("Necra Disciple", 44, Rarity.COMMON, mage.cards.n.NecraDisciple.class, RETRO_ART));
        cards.add(new SetCardInfo("Necra Sanctuary", 45, Rarity.UNCOMMON, mage.cards.n.NecraSanctuary.class, RETRO_ART));
        cards.add(new SetCardInfo("Necravolver", 46, Rarity.RARE, mage.cards.n.Necravolver.class, RETRO_ART));
        cards.add(new SetCardInfo("Night // Day", 131, Rarity.UNCOMMON, mage.cards.n.NightDay.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Order // Chaos", 132, Rarity.UNCOMMON, mage.cards.o.OrderChaos.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Orim's Thunder", 15, Rarity.COMMON, mage.cards.o.OrimsThunder.class, RETRO_ART));
        cards.add(new SetCardInfo("Overgrown Estate", 113, Rarity.RARE, mage.cards.o.OvergrownEstate.class, RETRO_ART));
        cards.add(new SetCardInfo("Penumbra Bobcat", 82, Rarity.COMMON, mage.cards.p.PenumbraBobcat.class, RETRO_ART));
        cards.add(new SetCardInfo("Penumbra Kavu", 83, Rarity.UNCOMMON, mage.cards.p.PenumbraKavu.class, RETRO_ART));
        cards.add(new SetCardInfo("Penumbra Wurm", 84, Rarity.RARE, mage.cards.p.PenumbraWurm.class, RETRO_ART));
        cards.add(new SetCardInfo("Pernicious Deed", 114, Rarity.RARE, mage.cards.p.PerniciousDeed.class, RETRO_ART));
        cards.add(new SetCardInfo("Phyrexian Arena", 47, Rarity.RARE, mage.cards.p.PhyrexianArena.class, RETRO_ART));
        cards.add(new SetCardInfo("Phyrexian Gargantua", 48, Rarity.UNCOMMON, mage.cards.p.PhyrexianGargantua.class, RETRO_ART));
        cards.add(new SetCardInfo("Phyrexian Rager", 49, Rarity.COMMON, mage.cards.p.PhyrexianRager.class, RETRO_ART));
        cards.add(new SetCardInfo("Planar Despair", 50, Rarity.RARE, mage.cards.p.PlanarDespair.class, RETRO_ART));
        cards.add(new SetCardInfo("Powerstone Minefield", 115, Rarity.RARE, mage.cards.p.PowerstoneMinefield.class, RETRO_ART));
        cards.add(new SetCardInfo("Prophetic Bolt", 116, Rarity.RARE, mage.cards.p.PropheticBolt.class, RETRO_ART));
        cards.add(new SetCardInfo("Putrid Warrior", 117, Rarity.COMMON, mage.cards.p.PutridWarrior.class, RETRO_ART));
        cards.add(new SetCardInfo("Quagmire Druid", 51, Rarity.COMMON, mage.cards.q.QuagmireDruid.class, RETRO_ART));
        cards.add(new SetCardInfo("Quicksilver Dagger", 118, Rarity.COMMON, mage.cards.q.QuicksilverDagger.class, RETRO_ART));
        cards.add(new SetCardInfo("Raka Disciple", 66, Rarity.COMMON, mage.cards.r.RakaDisciple.class, RETRO_ART));
        cards.add(new SetCardInfo("Raka Sanctuary", 67, Rarity.UNCOMMON, mage.cards.r.RakaSanctuary.class, RETRO_ART));
        cards.add(new SetCardInfo("Rakavolver", 68, Rarity.RARE, mage.cards.r.Rakavolver.class, RETRO_ART));
        cards.add(new SetCardInfo("Razorfin Hunter", 119, Rarity.COMMON, mage.cards.r.RazorfinHunter.class, RETRO_ART));
        cards.add(new SetCardInfo("Reef Shaman", 29, Rarity.COMMON, mage.cards.r.ReefShaman.class, RETRO_ART));
        cards.add(new SetCardInfo("Savage Gorilla", 85, Rarity.COMMON, mage.cards.s.SavageGorilla.class, RETRO_ART));
        cards.add(new SetCardInfo("Shield of Duty and Reason", 16, Rarity.COMMON, mage.cards.s.ShieldOfDutyAndReason.class, RETRO_ART));
        cards.add(new SetCardInfo("Shimmering Mirage", 30, Rarity.COMMON, mage.cards.s.ShimmeringMirage.class, RETRO_ART));
        cards.add(new SetCardInfo("Shivan Reef", 142, Rarity.RARE, mage.cards.s.ShivanReef.class, RETRO_ART));
        cards.add(new SetCardInfo("Smash", 69, Rarity.COMMON, mage.cards.s.Smash.class, RETRO_ART));
        cards.add(new SetCardInfo("Soul Link", 120, Rarity.COMMON, mage.cards.s.SoulLink.class, RETRO_ART));
        cards.add(new SetCardInfo("Spectral Lynx", 17, Rarity.RARE, mage.cards.s.SpectralLynx.class, RETRO_ART));
        cards.add(new SetCardInfo("Spiritmonger", 121, Rarity.RARE, mage.cards.s.Spiritmonger.class, RETRO_ART));
        cards.add(new SetCardInfo("Squee's Embrace", 122, Rarity.COMMON, mage.cards.s.SqueesEmbrace.class, RETRO_ART));
        cards.add(new SetCardInfo("Squee's Revenge", 123, Rarity.UNCOMMON, mage.cards.s.SqueesRevenge.class, RETRO_ART));
        cards.add(new SetCardInfo("Standard Bearer", 18, Rarity.COMMON, mage.cards.s.StandardBearer.class, RETRO_ART));
        cards.add(new SetCardInfo("Strength of Night", 86, Rarity.COMMON, mage.cards.s.StrengthOfNight.class, RETRO_ART));
        cards.add(new SetCardInfo("Suffocating Blast", 124, Rarity.RARE, mage.cards.s.SuffocatingBlast.class, RETRO_ART));
        cards.add(new SetCardInfo("Suppress", 52, Rarity.UNCOMMON, mage.cards.s.Suppress.class, RETRO_ART));
        cards.add(new SetCardInfo("Sylvan Messenger", 87, Rarity.UNCOMMON, mage.cards.s.SylvanMessenger.class, RETRO_ART));
        cards.add(new SetCardInfo("Symbiotic Deployment", 88, Rarity.RARE, mage.cards.s.SymbioticDeployment.class, RETRO_ART));
        cards.add(new SetCardInfo("Tahngarth's Glare", 70, Rarity.COMMON, mage.cards.t.TahngarthsGlare.class, RETRO_ART));
        cards.add(new SetCardInfo("Temporal Spring", 125, Rarity.COMMON, mage.cards.t.TemporalSpring.class, RETRO_ART));
        cards.add(new SetCardInfo("Tidal Courier", 31, Rarity.UNCOMMON, mage.cards.t.TidalCourier.class, RETRO_ART));
        cards.add(new SetCardInfo("Tranquil Path", 89, Rarity.COMMON, mage.cards.t.TranquilPath.class, RETRO_ART));
        cards.add(new SetCardInfo("Tundra Kavu", 71, Rarity.COMMON, mage.cards.t.TundraKavu.class, RETRO_ART));
        cards.add(new SetCardInfo("Unnatural Selection", 32, Rarity.RARE, mage.cards.u.UnnaturalSelection.class, RETRO_ART));
        cards.add(new SetCardInfo("Urborg Elf", 90, Rarity.COMMON, mage.cards.u.UrborgElf.class, RETRO_ART));
        cards.add(new SetCardInfo("Urborg Uprising", 53, Rarity.COMMON, mage.cards.u.UrborgUprising.class, RETRO_ART));
        cards.add(new SetCardInfo("Vindicate", 126, Rarity.RARE, mage.cards.v.Vindicate.class, RETRO_ART));
        cards.add(new SetCardInfo("Vodalian Mystic", 33, Rarity.UNCOMMON, mage.cards.v.VodalianMystic.class, RETRO_ART));
        cards.add(new SetCardInfo("Whirlpool Drake", 34, Rarity.UNCOMMON, mage.cards.w.WhirlpoolDrake.class, RETRO_ART));
        cards.add(new SetCardInfo("Whirlpool Rider", 35, Rarity.COMMON, mage.cards.w.WhirlpoolRider.class, RETRO_ART));
        cards.add(new SetCardInfo("Whirlpool Warrior", 36, Rarity.RARE, mage.cards.w.WhirlpoolWarrior.class, RETRO_ART));
        cards.add(new SetCardInfo("Wild Research", 72, Rarity.RARE, mage.cards.w.WildResearch.class, RETRO_ART));
        cards.add(new SetCardInfo("Yavimaya Coast", 143, Rarity.RARE, mage.cards.y.YavimayaCoast.class, RETRO_ART));
        cards.add(new SetCardInfo("Yavimaya's Embrace", 127, Rarity.RARE, mage.cards.y.YavimayasEmbrace.class, RETRO_ART));
        cards.add(new SetCardInfo("Zombie Boa", 54, Rarity.COMMON, mage.cards.z.ZombieBoa.class, RETRO_ART));
    }

    @Override
    public BoosterCollator createCollator() {
        return new ApocalypseCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/jud.html
// Using US collation - commons only
class ApocalypseCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "49", "81", "103", "19", "66", "118", "4", "37", "125", "90", "28", "101", "57", "14", "117", "37", "78", "119", "19", "57", "109", "13", "53", "101", "78", "27", "109", "66", "14", "119", "54", "81", "117", "27", "71", "103", "4", "53", "120", "90", "22", "93", "64", "3", "125", "49", "85", "93", "28", "71", "122", "3", "54", "120", "85", "22", "118", "64", "13", "122");
    private final CardRun commonB = new CardRun(true, "42", "76", "30", "56", "8", "43", "89", "25", "56", "16", "51", "76", "26", "60", "18", "44", "86", "25", "60", "1", "51", "86", "26", "65", "1", "41", "89", "29", "69", "18", "42", "82", "35", "69", "8", "44", "73", "30", "70", "16", "43", "82", "29", "70", "15", "41", "73", "35", "65", "15");
    private final CardRun uncommon = new CardRun(false, "91", "74", "58", "133", "92", "20", "2", "96", "5", "7", "134", "135", "61", "97", "136", "9", "23", "128", "99", "39", "77", "102", "62", "40", "12", "63", "129", "106", "79", "130", "110", "111", "45", "131", "132", "83", "48", "67", "123", "52", "87", "31", "33", "34");
    private final CardRun rare = new CardRun(false, "75", "139", "55", "140", "21", "94", "95", "6", "38", "59", "10", "98", "100", "11", "104", "105", "24", "80", "107", "137", "108", "141", "138", "112", "46", "113", "84", "114", "47", "50", "115", "116", "68", "142", "17", "121", "124", "88", "32", "126", "36", "72", "143", "127");

    private final BoosterStructure AAAAAABBBBB = new BoosterStructure(
            commonA, commonA, commonA, commonA, commonA, commonA,
            commonB, commonB, commonB, commonB, commonB
    );
    private final BoosterStructure U3 = new BoosterStructure(uncommon, uncommon, uncommon);
    private final BoosterStructure R1 = new BoosterStructure(rare);

    private final RarityConfiguration commonRuns = new RarityConfiguration(AAAAAABBBBB);
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(U3);
    private final RarityConfiguration rareRuns = new RarityConfiguration(R1);

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        booster.addAll(commonRuns.getNext().makeRun());
        booster.addAll(uncommonRuns.getNext().makeRun());
        booster.addAll(rareRuns.getNext().makeRun());
        return booster;
    }
}
