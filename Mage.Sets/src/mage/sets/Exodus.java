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

/**
 *
 * @author North
 */
public final class Exodus extends ExpansionSet {

    private static final Exodus instance = new Exodus();

    public static Exodus getInstance() {
        return instance;
    }

    private Exodus() {
        super("Exodus", "EXO", ExpansionSet.buildDate(1998, 6, 15), SetType.EXPANSION);
        this.blockName = "Tempest";
        this.parentSet = Tempest.getInstance();
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;

        cards.add(new SetCardInfo("Aether Tide", 27, Rarity.COMMON, mage.cards.a.AetherTide.class, RETRO_ART));
        cards.add(new SetCardInfo("Allay", 1, Rarity.COMMON, mage.cards.a.Allay.class, RETRO_ART));
        cards.add(new SetCardInfo("Anarchist", 79, Rarity.COMMON, mage.cards.a.Anarchist.class, RETRO_ART));
        cards.add(new SetCardInfo("Angelic Blessing", 2, Rarity.COMMON, mage.cards.a.AngelicBlessing.class, RETRO_ART));
        cards.add(new SetCardInfo("Avenging Druid", 105, Rarity.COMMON, mage.cards.a.AvengingDruid.class, RETRO_ART));
        cards.add(new SetCardInfo("Bequeathal", 106, Rarity.COMMON, mage.cards.b.Bequeathal.class, RETRO_ART));
        cards.add(new SetCardInfo("Carnophage", 53, Rarity.COMMON, mage.cards.c.Carnophage.class, RETRO_ART));
        cards.add(new SetCardInfo("Cartographer", 107, Rarity.UNCOMMON, mage.cards.c.Cartographer.class, RETRO_ART));
        cards.add(new SetCardInfo("Cat Burglar", 54, Rarity.COMMON, mage.cards.c.CatBurglar.class, RETRO_ART));
        cards.add(new SetCardInfo("Cataclysm", 3, Rarity.RARE, mage.cards.c.Cataclysm.class, RETRO_ART));
        cards.add(new SetCardInfo("Charging Paladin", 4, Rarity.COMMON, mage.cards.c.ChargingPaladin.class, RETRO_ART));
        cards.add(new SetCardInfo("Cinder Crawler", 80, Rarity.COMMON, mage.cards.c.CinderCrawler.class, RETRO_ART));
        cards.add(new SetCardInfo("City of Traitors", 143, Rarity.RARE, mage.cards.c.CityOfTraitors.class, RETRO_ART));
        cards.add(new SetCardInfo("Coat of Arms", 131, Rarity.RARE, mage.cards.c.CoatOfArms.class, RETRO_ART));
        cards.add(new SetCardInfo("Convalescence", 5, Rarity.RARE, mage.cards.c.Convalescence.class, RETRO_ART));
        cards.add(new SetCardInfo("Crashing Boars", 108, Rarity.UNCOMMON, mage.cards.c.CrashingBoars.class, RETRO_ART));
        cards.add(new SetCardInfo("Culling the Weak", 55, Rarity.COMMON, mage.cards.c.CullingTheWeak.class, RETRO_ART));
        cards.add(new SetCardInfo("Cunning", 28, Rarity.COMMON, mage.cards.c.Cunning.class, RETRO_ART));
        cards.add(new SetCardInfo("Curiosity", 29, Rarity.UNCOMMON, mage.cards.c.Curiosity.class, RETRO_ART));
        cards.add(new SetCardInfo("Cursed Flesh", 56, Rarity.COMMON, mage.cards.c.CursedFlesh.class, RETRO_ART));
        cards.add(new SetCardInfo("Dauthi Cutthroat", 57, Rarity.UNCOMMON, mage.cards.d.DauthiCutthroat.class, RETRO_ART));
        cards.add(new SetCardInfo("Dauthi Jackal", 58, Rarity.COMMON, mage.cards.d.DauthiJackal.class, RETRO_ART));
        cards.add(new SetCardInfo("Dauthi Warlord", 59, Rarity.UNCOMMON, mage.cards.d.DauthiWarlord.class, RETRO_ART));
        cards.add(new SetCardInfo("Death's Duet", 60, Rarity.COMMON, mage.cards.d.DeathsDuet.class, RETRO_ART));
        cards.add(new SetCardInfo("Dizzying Gaze", 81, Rarity.COMMON, mage.cards.d.DizzyingGaze.class, RETRO_ART));
        cards.add(new SetCardInfo("Dominating Licid", 30, Rarity.RARE, mage.cards.d.DominatingLicid.class, RETRO_ART));
        cards.add(new SetCardInfo("Elven Palisade", 109, Rarity.UNCOMMON, mage.cards.e.ElvenPalisade.class, RETRO_ART));
        cards.add(new SetCardInfo("Elvish Berserker", 110, Rarity.COMMON, mage.cards.e.ElvishBerserker.class, RETRO_ART));
        cards.add(new SetCardInfo("Entropic Specter", 61, Rarity.RARE, mage.cards.e.EntropicSpecter.class, RETRO_ART));
        cards.add(new SetCardInfo("Ephemeron", 31, Rarity.RARE, mage.cards.e.Ephemeron.class, RETRO_ART));
        cards.add(new SetCardInfo("Equilibrium", 32, Rarity.RARE, mage.cards.e.Equilibrium.class, RETRO_ART));
        cards.add(new SetCardInfo("Erratic Portal", 132, Rarity.RARE, mage.cards.e.ErraticPortal.class, RETRO_ART));
        cards.add(new SetCardInfo("Ertai, Wizard Adept", 33, Rarity.RARE, mage.cards.e.ErtaiWizardAdept.class, RETRO_ART));
        cards.add(new SetCardInfo("Exalted Dragon", 6, Rarity.RARE, mage.cards.e.ExaltedDragon.class, RETRO_ART));
        cards.add(new SetCardInfo("Fade Away", 34, Rarity.COMMON, mage.cards.f.FadeAway.class, RETRO_ART));
        cards.add(new SetCardInfo("Fighting Chance", 82, Rarity.RARE, mage.cards.f.FightingChance.class, RETRO_ART));
        cards.add(new SetCardInfo("Flowstone Flood", 83, Rarity.UNCOMMON, mage.cards.f.FlowstoneFlood.class, RETRO_ART));
        cards.add(new SetCardInfo("Forbid", 35, Rarity.UNCOMMON, mage.cards.f.Forbid.class, RETRO_ART));
        cards.add(new SetCardInfo("Fugue", 62, Rarity.UNCOMMON, mage.cards.f.Fugue.class, RETRO_ART));
        cards.add(new SetCardInfo("Furnace Brood", 84, Rarity.COMMON, mage.cards.f.FurnaceBrood.class, RETRO_ART));
        cards.add(new SetCardInfo("Grollub", 63, Rarity.COMMON, mage.cards.g.Grollub.class, RETRO_ART));
        cards.add(new SetCardInfo("Hatred", 64, Rarity.RARE, mage.cards.h.Hatred.class, RETRO_ART));
        cards.add(new SetCardInfo("High Ground", 7, Rarity.UNCOMMON, mage.cards.h.HighGround.class, RETRO_ART));
        cards.add(new SetCardInfo("Jackalope Herd", 111, Rarity.COMMON, mage.cards.j.JackalopeHerd.class, RETRO_ART));
        cards.add(new SetCardInfo("Keeper of the Beasts", 112, Rarity.UNCOMMON, mage.cards.k.KeeperOfTheBeasts.class, RETRO_ART));
        cards.add(new SetCardInfo("Keeper of the Dead", 65, Rarity.UNCOMMON, mage.cards.k.KeeperOfTheDead.class, RETRO_ART));
        cards.add(new SetCardInfo("Keeper of the Flame", 85, Rarity.UNCOMMON, mage.cards.k.KeeperOfTheFlame.class, RETRO_ART));
        cards.add(new SetCardInfo("Keeper of the Light", 8, Rarity.UNCOMMON, mage.cards.k.KeeperOfTheLight.class, RETRO_ART));
        cards.add(new SetCardInfo("Keeper of the Mind", 36, Rarity.UNCOMMON, mage.cards.k.KeeperOfTheMind.class, RETRO_ART));
        cards.add(new SetCardInfo("Killer Whale", 37, Rarity.UNCOMMON, mage.cards.k.KillerWhale.class, RETRO_ART));
        cards.add(new SetCardInfo("Kor Chant", 9, Rarity.COMMON, mage.cards.k.KorChant.class, RETRO_ART));
        cards.add(new SetCardInfo("Limited Resources", 10, Rarity.RARE, mage.cards.l.LimitedResources.class, RETRO_ART));
        cards.add(new SetCardInfo("Mage il-Vec", 86, Rarity.COMMON, mage.cards.m.MageIlVec.class, RETRO_ART));
        cards.add(new SetCardInfo("Mana Breach", 38, Rarity.UNCOMMON, mage.cards.m.ManaBreach.class, RETRO_ART));
        cards.add(new SetCardInfo("Manabond", 113, Rarity.RARE, mage.cards.m.Manabond.class, RETRO_ART));
        cards.add(new SetCardInfo("Maniacal Rage", 87, Rarity.COMMON, mage.cards.m.ManiacalRage.class, RETRO_ART));
        cards.add(new SetCardInfo("Medicine Bag", 133, Rarity.UNCOMMON, mage.cards.m.MedicineBag.class, RETRO_ART));
        cards.add(new SetCardInfo("Memory Crystal", 134, Rarity.RARE, mage.cards.m.MemoryCrystal.class, RETRO_ART));
        cards.add(new SetCardInfo("Merfolk Looter", 39, Rarity.COMMON, mage.cards.m.MerfolkLooter.class, RETRO_ART));
        cards.add(new SetCardInfo("Mind Maggots", 66, Rarity.UNCOMMON, mage.cards.m.MindMaggots.class, RETRO_ART));
        cards.add(new SetCardInfo("Mind Over Matter", 40, Rarity.RARE, mage.cards.m.MindOverMatter.class, RETRO_ART));
        cards.add(new SetCardInfo("Mindless Automaton", 135, Rarity.RARE, mage.cards.m.MindlessAutomaton.class, RETRO_ART));
        cards.add(new SetCardInfo("Mirozel", 41, Rarity.UNCOMMON, mage.cards.m.Mirozel.class, RETRO_ART));
        cards.add(new SetCardInfo("Mirri, Cat Warrior", 114, Rarity.RARE, mage.cards.m.MirriCatWarrior.class, RETRO_ART));
        cards.add(new SetCardInfo("Mogg Assassin", 88, Rarity.UNCOMMON, mage.cards.m.MoggAssassin.class, RETRO_ART));
        cards.add(new SetCardInfo("Monstrous Hound", 89, Rarity.RARE, mage.cards.m.MonstrousHound.class, RETRO_ART));
        cards.add(new SetCardInfo("Nausea", 67, Rarity.COMMON, mage.cards.n.Nausea.class, RETRO_ART));
        cards.add(new SetCardInfo("Necrologia", 68, Rarity.UNCOMMON, mage.cards.n.Necrologia.class, RETRO_ART));
        cards.add(new SetCardInfo("Null Brooch", 136, Rarity.RARE, mage.cards.n.NullBrooch.class, RETRO_ART));
        cards.add(new SetCardInfo("Oath of Druids", 115, Rarity.RARE, mage.cards.o.OathOfDruids.class, RETRO_ART));
        cards.add(new SetCardInfo("Oath of Ghouls", 69, Rarity.RARE, mage.cards.o.OathOfGhouls.class, RETRO_ART));
        cards.add(new SetCardInfo("Oath of Lieges", 11, Rarity.RARE, mage.cards.o.OathOfLieges.class, RETRO_ART));
        cards.add(new SetCardInfo("Oath of Mages", 90, Rarity.RARE, mage.cards.o.OathOfMages.class, RETRO_ART));
        cards.add(new SetCardInfo("Oath of Scholars", 42, Rarity.RARE, mage.cards.o.OathOfScholars.class, RETRO_ART));
        cards.add(new SetCardInfo("Ogre Shaman", 91, Rarity.RARE, mage.cards.o.OgreShaman.class, RETRO_ART));
        cards.add(new SetCardInfo("Onslaught", 92, Rarity.COMMON, mage.cards.o.Onslaught.class, RETRO_ART));
        cards.add(new SetCardInfo("Paladin en-Vec", 12, Rarity.RARE, mage.cards.p.PaladinEnVec.class, RETRO_ART));
        cards.add(new SetCardInfo("Pandemonium", 93, Rarity.RARE, mage.cards.p.Pandemonium.class, RETRO_ART));
        cards.add(new SetCardInfo("Paroxysm", 94, Rarity.UNCOMMON, mage.cards.p.Paroxysm.class, RETRO_ART));
        cards.add(new SetCardInfo("Peace of Mind", 13, Rarity.UNCOMMON, mage.cards.p.PeaceOfMind.class, RETRO_ART));
        cards.add(new SetCardInfo("Pegasus Stampede", 14, Rarity.UNCOMMON, mage.cards.p.PegasusStampede.class, RETRO_ART));
        cards.add(new SetCardInfo("Penance", 15, Rarity.UNCOMMON, mage.cards.p.Penance.class, RETRO_ART));
        cards.add(new SetCardInfo("Pit Spawn", 70, Rarity.RARE, mage.cards.p.PitSpawn.class, RETRO_ART));
        cards.add(new SetCardInfo("Plaguebearer", 71, Rarity.RARE, mage.cards.p.Plaguebearer.class, RETRO_ART));
        cards.add(new SetCardInfo("Plated Rootwalla", 116, Rarity.COMMON, mage.cards.p.PlatedRootwalla.class, RETRO_ART));
        cards.add(new SetCardInfo("Predatory Hunger", 117, Rarity.COMMON, mage.cards.p.PredatoryHunger.class, RETRO_ART));
        cards.add(new SetCardInfo("Price of Progress", 95, Rarity.UNCOMMON, mage.cards.p.PriceOfProgress.class, RETRO_ART));
        cards.add(new SetCardInfo("Pygmy Troll", 118, Rarity.COMMON, mage.cards.p.PygmyTroll.class, RETRO_ART));
        cards.add(new SetCardInfo("Rabid Wolverines", 119, Rarity.COMMON, mage.cards.r.RabidWolverines.class, RETRO_ART));
        cards.add(new SetCardInfo("Raging Goblin", 96, Rarity.COMMON, mage.cards.r.RagingGoblin.class, RETRO_ART));
        cards.add(new SetCardInfo("Ravenous Baboons", 97, Rarity.RARE, mage.cards.r.RavenousBaboons.class, RETRO_ART));
        cards.add(new SetCardInfo("Reaping the Rewards", 16, Rarity.COMMON, mage.cards.r.ReapingTheRewards.class, RETRO_ART));
        cards.add(new SetCardInfo("Reckless Ogre", 98, Rarity.COMMON, mage.cards.r.RecklessOgre.class, RETRO_ART));
        cards.add(new SetCardInfo("Reclaim", 120, Rarity.COMMON, mage.cards.r.Reclaim.class, RETRO_ART));
        cards.add(new SetCardInfo("Reconnaissance", 17, Rarity.UNCOMMON, mage.cards.r.Reconnaissance.class, RETRO_ART));
        cards.add(new SetCardInfo("Recurring Nightmare", 72, Rarity.RARE, mage.cards.r.RecurringNightmare.class, RETRO_ART));
        cards.add(new SetCardInfo("Resuscitate", 121, Rarity.UNCOMMON, mage.cards.r.Resuscitate.class, RETRO_ART));
        cards.add(new SetCardInfo("Robe of Mirrors", 43, Rarity.COMMON, mage.cards.r.RobeOfMirrors.class, RETRO_ART));
        cards.add(new SetCardInfo("Rootwater Alligator", 122, Rarity.COMMON, mage.cards.r.RootwaterAlligator.class, RETRO_ART));
        cards.add(new SetCardInfo("Rootwater Mystic", 44, Rarity.COMMON, mage.cards.r.RootwaterMystic.class, RETRO_ART));
        cards.add(new SetCardInfo("Sabertooth Wyvern", 99, Rarity.UNCOMMON, mage.cards.s.SabertoothWyvern.class, RETRO_ART));
        cards.add(new SetCardInfo("Scalding Salamander", 100, Rarity.UNCOMMON, mage.cards.s.ScaldingSalamander.class, RETRO_ART));
        cards.add(new SetCardInfo("Scare Tactics", 73, Rarity.COMMON, mage.cards.s.ScareTactics.class, RETRO_ART));
        cards.add(new SetCardInfo("School of Piranha", 45, Rarity.COMMON, mage.cards.s.SchoolOfPiranha.class, RETRO_ART));
        cards.add(new SetCardInfo("Scrivener", 46, Rarity.UNCOMMON, mage.cards.s.Scrivener.class, RETRO_ART));
        cards.add(new SetCardInfo("Seismic Assault", 101, Rarity.RARE, mage.cards.s.SeismicAssault.class, RETRO_ART));
        cards.add(new SetCardInfo("Shackles", 18, Rarity.COMMON, mage.cards.s.Shackles.class, RETRO_ART));
        cards.add(new SetCardInfo("Shattering Pulse", 102, Rarity.COMMON, mage.cards.s.ShatteringPulse.class, RETRO_ART));
        cards.add(new SetCardInfo("Shield Mate", 19, Rarity.COMMON, mage.cards.s.ShieldMate.class, RETRO_ART));
        cards.add(new SetCardInfo("Skyshaper", 137, Rarity.UNCOMMON, mage.cards.s.Skyshaper.class, RETRO_ART));
        cards.add(new SetCardInfo("Skyshroud Elite", 123, Rarity.UNCOMMON, mage.cards.s.SkyshroudElite.class, RETRO_ART));
        cards.add(new SetCardInfo("Skyshroud War Beast", 124, Rarity.RARE, mage.cards.s.SkyshroudWarBeast.class, RETRO_ART));
        cards.add(new SetCardInfo("Slaughter", 74, Rarity.UNCOMMON, mage.cards.s.Slaughter.class, RETRO_ART));
        cards.add(new SetCardInfo("Soltari Visionary", 20, Rarity.COMMON, mage.cards.s.SoltariVisionary.class, RETRO_ART));
        cards.add(new SetCardInfo("Song of Serenity", 125, Rarity.UNCOMMON, mage.cards.s.SongOfSerenity.class, RETRO_ART));
        cards.add(new SetCardInfo("Sonic Burst", 103, Rarity.COMMON, mage.cards.s.SonicBurst.class, RETRO_ART));
        cards.add(new SetCardInfo("Soul Warden", 21, Rarity.COMMON, mage.cards.s.SoulWarden.class, RETRO_ART));
        cards.add(new SetCardInfo("Spellbook", 138, Rarity.UNCOMMON, mage.cards.s.Spellbook.class, RETRO_ART));
        cards.add(new SetCardInfo("Spellshock", 104, Rarity.UNCOMMON, mage.cards.s.Spellshock.class, RETRO_ART));
        cards.add(new SetCardInfo("Sphere of Resistance", 139, Rarity.RARE, mage.cards.s.SphereOfResistance.class, RETRO_ART));
        cards.add(new SetCardInfo("Spike Cannibal", 75, Rarity.UNCOMMON, mage.cards.s.SpikeCannibal.class, RETRO_ART));
        cards.add(new SetCardInfo("Spike Hatcher", 126, Rarity.RARE, mage.cards.s.SpikeHatcher.class, RETRO_ART));
        cards.add(new SetCardInfo("Spike Rogue", 127, Rarity.UNCOMMON, mage.cards.s.SpikeRogue.class, RETRO_ART));
        cards.add(new SetCardInfo("Spike Weaver", 128, Rarity.RARE, mage.cards.s.SpikeWeaver.class, RETRO_ART));
        cards.add(new SetCardInfo("Standing Troops", 22, Rarity.COMMON, mage.cards.s.StandingTroops.class, RETRO_ART));
        cards.add(new SetCardInfo("Survival of the Fittest", 129, Rarity.RARE, mage.cards.s.SurvivalOfTheFittest.class, RETRO_ART));
        cards.add(new SetCardInfo("Thalakos Drifters", 47, Rarity.RARE, mage.cards.t.ThalakosDrifters.class, RETRO_ART));
        cards.add(new SetCardInfo("Thalakos Scout", 48, Rarity.COMMON, mage.cards.t.ThalakosScout.class, RETRO_ART));
        cards.add(new SetCardInfo("Theft of Dreams", 49, Rarity.COMMON, mage.cards.t.TheftOfDreams.class, RETRO_ART));
        cards.add(new SetCardInfo("Thopter Squadron", 140, Rarity.RARE, mage.cards.t.ThopterSquadron.class, RETRO_ART));
        cards.add(new SetCardInfo("Thrull Surgeon", 76, Rarity.COMMON, mage.cards.t.ThrullSurgeon.class, RETRO_ART));
        cards.add(new SetCardInfo("Transmogrifying Licid", 141, Rarity.UNCOMMON, mage.cards.t.TransmogrifyingLicid.class, RETRO_ART));
        cards.add(new SetCardInfo("Treasure Hunter", 23, Rarity.UNCOMMON, mage.cards.t.TreasureHunter.class, RETRO_ART));
        cards.add(new SetCardInfo("Treasure Trove", 50, Rarity.UNCOMMON, mage.cards.t.TreasureTrove.class, RETRO_ART));
        cards.add(new SetCardInfo("Vampire Hounds", 77, Rarity.COMMON, mage.cards.v.VampireHounds.class, RETRO_ART));
        cards.add(new SetCardInfo("Volrath's Dungeon", 78, Rarity.RARE, mage.cards.v.VolrathsDungeon.class, RETRO_ART));
        cards.add(new SetCardInfo("Wall of Nets", 24, Rarity.RARE, mage.cards.w.WallOfNets.class, RETRO_ART));
        cards.add(new SetCardInfo("Wayward Soul", 51, Rarity.COMMON, mage.cards.w.WaywardSoul.class, RETRO_ART));
        cards.add(new SetCardInfo("Welkin Hawk", 25, Rarity.COMMON, mage.cards.w.WelkinHawk.class, RETRO_ART));
        cards.add(new SetCardInfo("Whiptongue Frog", 52, Rarity.COMMON, mage.cards.w.WhiptongueFrog.class, RETRO_ART));
        cards.add(new SetCardInfo("Wood Elves", 130, Rarity.COMMON, mage.cards.w.WoodElves.class, RETRO_ART));
        cards.add(new SetCardInfo("Workhorse", 142, Rarity.RARE, mage.cards.w.Workhorse.class, RETRO_ART));
        cards.add(new SetCardInfo("Zealots en-Dal", 26, Rarity.UNCOMMON, mage.cards.z.ZealotsEnDal.class, RETRO_ART));
    }

    @Override
    public BoosterCollator createCollator() {
        return new ExodusCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/exo.html
// Using US collation - commons only
class ExodusCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "67", "130", "52", "86", "9", "58", "105", "34", "98", "19", "77", "122", "39", "87", "20", "76", "119", "34", "79", "18", "54", "118", "48", "92", "22", "58", "116", "52", "103", "19", "67", "122", "51", "79", "9", "63", "119", "28", "98", "21", "77", "130", "39", "92", "20", "54", "116", "28", "86", "18", "76", "105", "48", "87", "22", "63", "118", "51", "103", "21");
    private final CardRun commonB = new CardRun(true, "56", "106", "44", "102", "4", "53", "120", "43", "81", "25", "60", "106", "45", "96", "2", "56", "117", "27", "84", "4", "60", "111", "43", "80", "1", "55", "110", "49", "96", "25", "73", "120", "44", "81", "16", "55", "111", "45", "102", "2", "73", "110", "27", "80", "16", "53", "117", "49", "84", "1");
    private final CardRun uncommon = new CardRun(false, "107", "108", "29", "57", "59", "109", "83", "35", "62", "7", "112", "65", "85", "8", "36", "37", "38", "133", "66", "41", "88", "68", "94", "13", "14", "15", "95", "17", "121", "99", "100", "46", "137", "123", "74", "125", "138", "104", "75", "127", "141", "23", "50", "26");
    private final CardRun rare = new CardRun(false, "3", "143", "131", "5", "30", "61", "31", "32", "132", "33", "6", "82", "64", "10", "113", "134", "135", "40", "114", "89", "136", "115", "69", "11", "90", "42", "91", "12", "93", "70", "71", "97", "72", "101", "124", "139", "126", "128", "129", "47", "140", "78", "24", "142");

    private final BoosterStructure A6 = new BoosterStructure(
            commonA, commonA, commonA, commonA, commonA, commonA
    );
    private final BoosterStructure B5 = new BoosterStructure(
            commonB, commonB, commonB, commonB, commonB
    );
    private final BoosterStructure U3 = new BoosterStructure(uncommon, uncommon, uncommon);
    private final BoosterStructure R1 = new BoosterStructure(rare);

    private final RarityConfiguration commonRunsA = new RarityConfiguration(A6);
    private final RarityConfiguration commonRunsB = new RarityConfiguration(B5);
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(U3);
    private final RarityConfiguration rareRuns = new RarityConfiguration(R1);

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        booster.addAll(commonRunsA.getNext().makeRun());
        booster.addAll(uncommonRuns.getNext().makeRun());
        booster.addAll(rareRuns.getNext().makeRun());
        booster.addAll(commonRunsB.getNext().makeRun());
        return booster;
    }
}
