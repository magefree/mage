
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

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
        cards.add(new SetCardInfo("Allay", 1, Rarity.COMMON, mage.cards.a.Allay.class));
        cards.add(new SetCardInfo("Anarchist", 79, Rarity.COMMON, mage.cards.a.Anarchist.class));
        cards.add(new SetCardInfo("Angelic Blessing", 2, Rarity.COMMON, mage.cards.a.AngelicBlessing.class));
        cards.add(new SetCardInfo("Avenging Druid", 105, Rarity.COMMON, mage.cards.a.AvengingDruid.class));
        cards.add(new SetCardInfo("Bequeathal", 106, Rarity.COMMON, mage.cards.b.Bequeathal.class));
        cards.add(new SetCardInfo("Carnophage", 53, Rarity.COMMON, mage.cards.c.Carnophage.class));
        cards.add(new SetCardInfo("Cartographer", 107, Rarity.UNCOMMON, mage.cards.c.Cartographer.class));
        cards.add(new SetCardInfo("Cataclysm", 3, Rarity.RARE, mage.cards.c.Cataclysm.class));
        cards.add(new SetCardInfo("Cat Burglar", 54, Rarity.COMMON, mage.cards.c.CatBurglar.class));
        cards.add(new SetCardInfo("Charging Paladin", 4, Rarity.COMMON, mage.cards.c.ChargingPaladin.class));
        cards.add(new SetCardInfo("Cinder Crawler", 80, Rarity.COMMON, mage.cards.c.CinderCrawler.class));
        cards.add(new SetCardInfo("City of Traitors", 143, Rarity.RARE, mage.cards.c.CityOfTraitors.class));
        cards.add(new SetCardInfo("Coat of Arms", 131, Rarity.RARE, mage.cards.c.CoatOfArms.class));
        cards.add(new SetCardInfo("Convalescence", 5, Rarity.RARE, mage.cards.c.Convalescence.class));
        cards.add(new SetCardInfo("Crashing Boars", 108, Rarity.UNCOMMON, mage.cards.c.CrashingBoars.class));
        cards.add(new SetCardInfo("Culling the Weak", 55, Rarity.COMMON, mage.cards.c.CullingTheWeak.class));
        cards.add(new SetCardInfo("Curiosity", 29, Rarity.UNCOMMON, mage.cards.c.Curiosity.class));
        cards.add(new SetCardInfo("Cursed Flesh", 56, Rarity.COMMON, mage.cards.c.CursedFlesh.class));
        cards.add(new SetCardInfo("Dauthi Cutthroat", 57, Rarity.UNCOMMON, mage.cards.d.DauthiCutthroat.class));
        cards.add(new SetCardInfo("Dauthi Jackal", 58, Rarity.COMMON, mage.cards.d.DauthiJackal.class));
        cards.add(new SetCardInfo("Dauthi Warlord", 59, Rarity.UNCOMMON, mage.cards.d.DauthiWarlord.class));
        cards.add(new SetCardInfo("Death's Duet", 60, Rarity.COMMON, mage.cards.d.DeathsDuet.class));
        cards.add(new SetCardInfo("Dominating Licid", 30, Rarity.RARE, mage.cards.d.DominatingLicid.class));
        cards.add(new SetCardInfo("Elven Palisade", 109, Rarity.UNCOMMON, mage.cards.e.ElvenPalisade.class));
        cards.add(new SetCardInfo("Elvish Berserker", 110, Rarity.COMMON, mage.cards.e.ElvishBerserker.class));
        cards.add(new SetCardInfo("Entropic Specter", 61, Rarity.RARE, mage.cards.e.EntropicSpecter.class));
        cards.add(new SetCardInfo("Ephemeron", 31, Rarity.RARE, mage.cards.e.Ephemeron.class));
        cards.add(new SetCardInfo("Equilibrium", 32, Rarity.RARE, mage.cards.e.Equilibrium.class));
        cards.add(new SetCardInfo("Erratic Portal", 132, Rarity.RARE, mage.cards.e.ErraticPortal.class));
        cards.add(new SetCardInfo("Ertai, Wizard Adept", 33, Rarity.RARE, mage.cards.e.ErtaiWizardAdept.class));
        cards.add(new SetCardInfo("Exalted Dragon", 6, Rarity.RARE, mage.cards.e.ExaltedDragon.class));
        cards.add(new SetCardInfo("Fade Away", 34, Rarity.COMMON, mage.cards.f.FadeAway.class));
        cards.add(new SetCardInfo("Fighting Chance", 82, Rarity.RARE, mage.cards.f.FightingChance.class));
        cards.add(new SetCardInfo("Flowstone Flood", 83, Rarity.UNCOMMON, mage.cards.f.FlowstoneFlood.class));
        cards.add(new SetCardInfo("Forbid", 35, Rarity.UNCOMMON, mage.cards.f.Forbid.class));
        cards.add(new SetCardInfo("Fugue", 62, Rarity.UNCOMMON, mage.cards.f.Fugue.class));
        cards.add(new SetCardInfo("Furnace Brood", 84, Rarity.COMMON, mage.cards.f.FurnaceBrood.class));
        cards.add(new SetCardInfo("Hatred", 64, Rarity.RARE, mage.cards.h.Hatred.class));
        cards.add(new SetCardInfo("High Ground", 7, Rarity.UNCOMMON, mage.cards.h.HighGround.class));
        cards.add(new SetCardInfo("Jackalope Herd", 111, Rarity.COMMON, mage.cards.j.JackalopeHerd.class));
        cards.add(new SetCardInfo("Keeper of the Beasts", 112, Rarity.UNCOMMON, mage.cards.k.KeeperOfTheBeasts.class));
        cards.add(new SetCardInfo("Keeper of the Dead", 65, Rarity.UNCOMMON, mage.cards.k.KeeperOfTheDead.class));
	cards.add(new SetCardInfo("Keeper of the Light", 8, Rarity.UNCOMMON, mage.cards.k.KeeperOfTheLight.class));
        cards.add(new SetCardInfo("Keeper of the Mind", 36, Rarity.UNCOMMON, mage.cards.k.KeeperOfTheMind.class));
        cards.add(new SetCardInfo("Killer Whale", 37, Rarity.UNCOMMON, mage.cards.k.KillerWhale.class));
        cards.add(new SetCardInfo("Kor Chant", 9, Rarity.COMMON, mage.cards.k.KorChant.class));
        cards.add(new SetCardInfo("Mage il-Vec", 86, Rarity.COMMON, mage.cards.m.MageIlVec.class));
        cards.add(new SetCardInfo("Manabond", 113, Rarity.RARE, mage.cards.m.Manabond.class));
        cards.add(new SetCardInfo("Mana Breach", 38, Rarity.UNCOMMON, mage.cards.m.ManaBreach.class));
        cards.add(new SetCardInfo("Maniacal Rage", 87, Rarity.COMMON, mage.cards.m.ManiacalRage.class));
        cards.add(new SetCardInfo("Medicine Bag", 133, Rarity.UNCOMMON, mage.cards.m.MedicineBag.class));
        cards.add(new SetCardInfo("Memory Crystal", 134, Rarity.RARE, mage.cards.m.MemoryCrystal.class));
        cards.add(new SetCardInfo("Merfolk Looter", 39, Rarity.COMMON, mage.cards.m.MerfolkLooter.class));
        cards.add(new SetCardInfo("Mindless Automaton", 135, Rarity.RARE, mage.cards.m.MindlessAutomaton.class));
        cards.add(new SetCardInfo("Mind Over Matter", 40, Rarity.RARE, mage.cards.m.MindOverMatter.class));
        cards.add(new SetCardInfo("Mirozel", 41, Rarity.UNCOMMON, mage.cards.m.Mirozel.class));
        cards.add(new SetCardInfo("Mirri, Cat Warrior", 114, Rarity.RARE, mage.cards.m.MirriCatWarrior.class));
        cards.add(new SetCardInfo("Mogg Assassin", 88, Rarity.UNCOMMON, mage.cards.m.MoggAssassin.class));
        cards.add(new SetCardInfo("Nausea", 67, Rarity.COMMON, mage.cards.n.Nausea.class));
        cards.add(new SetCardInfo("Necrologia", 68, Rarity.UNCOMMON, mage.cards.n.Necrologia.class));
        cards.add(new SetCardInfo("Null Brooch", 136, Rarity.RARE, mage.cards.n.NullBrooch.class));
        cards.add(new SetCardInfo("Oath of Druids", 115, Rarity.RARE, mage.cards.o.OathOfDruids.class));
        cards.add(new SetCardInfo("Oath of Ghouls", 69, Rarity.RARE, mage.cards.o.OathOfGhouls.class));
        cards.add(new SetCardInfo("Oath of Lieges", 11, Rarity.RARE, mage.cards.o.OathOfLieges.class));
        cards.add(new SetCardInfo("Oath of Mages", 90, Rarity.RARE, mage.cards.o.OathOfMages.class));
        cards.add(new SetCardInfo("Oath of Scholars", 42, Rarity.RARE, mage.cards.o.OathOfScholars.class));
        cards.add(new SetCardInfo("Ogre Shaman", 91, Rarity.RARE, mage.cards.o.OgreShaman.class));
        cards.add(new SetCardInfo("Onslaught", 92, Rarity.COMMON, mage.cards.o.Onslaught.class));
        cards.add(new SetCardInfo("Paladin en-Vec", 12, Rarity.RARE, mage.cards.p.PaladinEnVec.class));
        cards.add(new SetCardInfo("Pandemonium", 93, Rarity.RARE, mage.cards.p.Pandemonium.class));
        cards.add(new SetCardInfo("Peace of Mind", 13, Rarity.UNCOMMON, mage.cards.p.PeaceOfMind.class));
        cards.add(new SetCardInfo("Pegasus Stampede", 14, Rarity.UNCOMMON, mage.cards.p.PegasusStampede.class));
        cards.add(new SetCardInfo("Penance", 15, Rarity.UNCOMMON, mage.cards.p.Penance.class));
        cards.add(new SetCardInfo("Pit Spawn", 70, Rarity.RARE, mage.cards.p.PitSpawn.class));
        cards.add(new SetCardInfo("Plaguebearer", 71, Rarity.RARE, mage.cards.p.Plaguebearer.class));
        cards.add(new SetCardInfo("Plated Rootwalla", 116, Rarity.COMMON, mage.cards.p.PlatedRootwalla.class));
        cards.add(new SetCardInfo("Predatory Hunger", 117, Rarity.COMMON, mage.cards.p.PredatoryHunger.class));
        cards.add(new SetCardInfo("Price of Progress", 95, Rarity.UNCOMMON, mage.cards.p.PriceOfProgress.class));
        cards.add(new SetCardInfo("Pygmy Troll", 118, Rarity.COMMON, mage.cards.p.PygmyTroll.class));
        cards.add(new SetCardInfo("Rabid Wolverines", 119, Rarity.COMMON, mage.cards.r.RabidWolverines.class));
        cards.add(new SetCardInfo("Raging Goblin", 96, Rarity.COMMON, mage.cards.r.RagingGoblin.class));
        cards.add(new SetCardInfo("Ravenous Baboons", 97, Rarity.RARE, mage.cards.r.RavenousBaboons.class));
        cards.add(new SetCardInfo("Reaping the Rewards", 16, Rarity.COMMON, mage.cards.r.ReapingTheRewards.class));
        cards.add(new SetCardInfo("Reckless Ogre", 98, Rarity.COMMON, mage.cards.r.RecklessOgre.class));
        cards.add(new SetCardInfo("Reclaim", 120, Rarity.COMMON, mage.cards.r.Reclaim.class));
        cards.add(new SetCardInfo("Reconnaissance", 17, Rarity.UNCOMMON, mage.cards.r.Reconnaissance.class));
        cards.add(new SetCardInfo("Recurring Nightmare", 72, Rarity.RARE, mage.cards.r.RecurringNightmare.class));
        cards.add(new SetCardInfo("Resuscitate", 121, Rarity.UNCOMMON, mage.cards.r.Resuscitate.class));
        cards.add(new SetCardInfo("Robe of Mirrors", 43, Rarity.COMMON, mage.cards.r.RobeOfMirrors.class));
        cards.add(new SetCardInfo("Rootwater Alligator", 122, Rarity.COMMON, mage.cards.r.RootwaterAlligator.class));
        cards.add(new SetCardInfo("Rootwater Mystic", 44, Rarity.COMMON, mage.cards.r.RootwaterMystic.class));
        cards.add(new SetCardInfo("Sabertooth Wyvern", 99, Rarity.UNCOMMON, mage.cards.s.SabertoothWyvern.class));
        cards.add(new SetCardInfo("Scalding Salamander", 100, Rarity.UNCOMMON, mage.cards.s.ScaldingSalamander.class));
        cards.add(new SetCardInfo("Scare Tactics", 73, Rarity.COMMON, mage.cards.s.ScareTactics.class));
        cards.add(new SetCardInfo("School of Piranha", 45, Rarity.COMMON, mage.cards.s.SchoolOfPiranha.class));
        cards.add(new SetCardInfo("Scrivener", 46, Rarity.UNCOMMON, mage.cards.s.Scrivener.class));
        cards.add(new SetCardInfo("Seismic Assault", 101, Rarity.RARE, mage.cards.s.SeismicAssault.class));
        cards.add(new SetCardInfo("Shackles", 18, Rarity.COMMON, mage.cards.s.Shackles.class));
        cards.add(new SetCardInfo("Shattering Pulse", 102, Rarity.COMMON, mage.cards.s.ShatteringPulse.class));
        cards.add(new SetCardInfo("Shield Mate", 19, Rarity.COMMON, mage.cards.s.ShieldMate.class));
        cards.add(new SetCardInfo("Skyshaper", 137, Rarity.UNCOMMON, mage.cards.s.Skyshaper.class));
        cards.add(new SetCardInfo("Skyshroud Elite", 123, Rarity.UNCOMMON, mage.cards.s.SkyshroudElite.class));
        cards.add(new SetCardInfo("Skyshroud War Beast", 124, Rarity.RARE, mage.cards.s.SkyshroudWarBeast.class));
        cards.add(new SetCardInfo("Slaughter", 74, Rarity.UNCOMMON, mage.cards.s.Slaughter.class));
        cards.add(new SetCardInfo("Soltari Visionary", 20, Rarity.COMMON, mage.cards.s.SoltariVisionary.class));
        cards.add(new SetCardInfo("Song of Serenity", 125, Rarity.UNCOMMON, mage.cards.s.SongOfSerenity.class));
        cards.add(new SetCardInfo("Sonic Burst", 103, Rarity.COMMON, mage.cards.s.SonicBurst.class));
        cards.add(new SetCardInfo("Soul Warden", 21, Rarity.COMMON, mage.cards.s.SoulWarden.class));
        cards.add(new SetCardInfo("Spellbook", 138, Rarity.UNCOMMON, mage.cards.s.Spellbook.class));
        cards.add(new SetCardInfo("Spellshock", 104, Rarity.UNCOMMON, mage.cards.s.Spellshock.class));
        cards.add(new SetCardInfo("Sphere of Resistance", 139, Rarity.RARE, mage.cards.s.SphereOfResistance.class));
        cards.add(new SetCardInfo("Spike Cannibal", 75, Rarity.UNCOMMON, mage.cards.s.SpikeCannibal.class));
        cards.add(new SetCardInfo("Spike Hatcher", 126, Rarity.RARE, mage.cards.s.SpikeHatcher.class));
        cards.add(new SetCardInfo("Spike Rogue", 127, Rarity.UNCOMMON, mage.cards.s.SpikeRogue.class));
        cards.add(new SetCardInfo("Spike Weaver", 128, Rarity.RARE, mage.cards.s.SpikeWeaver.class));
        cards.add(new SetCardInfo("Standing Troops", 22, Rarity.COMMON, mage.cards.s.StandingTroops.class));
        cards.add(new SetCardInfo("Survival of the Fittest", 129, Rarity.RARE, mage.cards.s.SurvivalOfTheFittest.class));
        cards.add(new SetCardInfo("Thalakos Drifters", 47, Rarity.RARE, mage.cards.t.ThalakosDrifters.class));
        cards.add(new SetCardInfo("Thalakos Scout", 48, Rarity.COMMON, mage.cards.t.ThalakosScout.class));
        cards.add(new SetCardInfo("Theft of Dreams", 49, Rarity.COMMON, mage.cards.t.TheftOfDreams.class));
        cards.add(new SetCardInfo("Thopter Squadron", 140, Rarity.RARE, mage.cards.t.ThopterSquadron.class));
        cards.add(new SetCardInfo("Thrull Surgeon", 76, Rarity.COMMON, mage.cards.t.ThrullSurgeon.class));
        cards.add(new SetCardInfo("Transmogrifying Licid", 141, Rarity.UNCOMMON, mage.cards.t.TransmogrifyingLicid.class));
        cards.add(new SetCardInfo("Treasure Hunter", 23, Rarity.UNCOMMON, mage.cards.t.TreasureHunter.class));
        cards.add(new SetCardInfo("Treasure Trove", 50, Rarity.UNCOMMON, mage.cards.t.TreasureTrove.class));
        cards.add(new SetCardInfo("Vampire Hounds", 77, Rarity.COMMON, mage.cards.v.VampireHounds.class));
        cards.add(new SetCardInfo("Wall of Nets", 24, Rarity.RARE, mage.cards.w.WallOfNets.class));
        cards.add(new SetCardInfo("Wayward Soul", 51, Rarity.COMMON, mage.cards.w.WaywardSoul.class));
        cards.add(new SetCardInfo("Welkin Hawk", 25, Rarity.COMMON, mage.cards.w.WelkinHawk.class));
        cards.add(new SetCardInfo("Whiptongue Frog", 52, Rarity.COMMON, mage.cards.w.WhiptongueFrog.class));
        cards.add(new SetCardInfo("Wood Elves", 130, Rarity.COMMON, mage.cards.w.WoodElves.class));
        cards.add(new SetCardInfo("Workhorse", 142, Rarity.RARE, mage.cards.w.Workhorse.class));
        cards.add(new SetCardInfo("Zealots en-Dal", 26, Rarity.UNCOMMON, mage.cards.z.ZealotsEnDal.class));
    }
}
