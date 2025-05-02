package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/wc98
 */
public class WorldChampionshipDecks1998 extends ExpansionSet {

    private static final WorldChampionshipDecks1998 instance = new WorldChampionshipDecks1998();

    public static WorldChampionshipDecks1998 getInstance() {
        return instance;
    }

    private WorldChampionshipDecks1998() {
        super("World Championship Decks 1998", "WC98", ExpansionSet.buildDate(1998, 8, 12), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Abeyance", "bh1sb", Rarity.RARE, mage.cards.a.Abeyance.class, RETRO_ART));
        cards.add(new SetCardInfo("Armageddon", "bh7asb", Rarity.RARE, mage.cards.a.Armageddon.class, RETRO_ART));
        cards.add(new SetCardInfo("Aura of Silence", "bh7b", Rarity.UNCOMMON, mage.cards.a.AuraOfSilence.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Aura of Silence", "bh7bsb", Rarity.UNCOMMON, mage.cards.a.AuraOfSilence.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Ball Lightning", "br210", Rarity.RARE, mage.cards.b.BallLightning.class, RETRO_ART));
        cards.add(new SetCardInfo("Birds of Paradise", "bs280", Rarity.RARE, mage.cards.b.BirdsOfParadise.class, RETRO_ART));
        cards.add(new SetCardInfo("Boil", "bs165sb", Rarity.UNCOMMON, mage.cards.b.Boil.class, RETRO_ART));
        cards.add(new SetCardInfo("Bottle Gnomes", "br278sb", Rarity.UNCOMMON, mage.cards.b.BottleGnomes.class, RETRO_ART));
        cards.add(new SetCardInfo("Capsize", "rb55sb", Rarity.COMMON, mage.cards.c.Capsize.class, RETRO_ART));
        cards.add(new SetCardInfo("Cataclysm", "bh3", Rarity.RARE, mage.cards.c.Cataclysm.class, RETRO_ART));
        cards.add(new SetCardInfo("City of Brass", "bs112a", Rarity.RARE, mage.cards.c.CityOfBrass.class, RETRO_ART));
        cards.add(new SetCardInfo("Cloudchaser Eagle", "bs15", Rarity.COMMON, mage.cards.c.CloudchaserEagle.class, RETRO_ART));
        cards.add(new SetCardInfo("Counterspell", "rb57", Rarity.COMMON, mage.cards.c.Counterspell.class, RETRO_ART));
        cards.add(new SetCardInfo("Cursed Scroll", "bh281sb", Rarity.RARE, mage.cards.c.CursedScroll.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Cursed Scroll", "br281", Rarity.RARE, mage.cards.c.CursedScroll.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Disenchant", "bh16", Rarity.COMMON, mage.cards.d.Disenchant.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Disenchant", "bh16sb", Rarity.COMMON, mage.cards.d.Disenchant.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Dismiss", "rb58a", Rarity.UNCOMMON, mage.cards.d.Dismiss.class, RETRO_ART));
        cards.add(new SetCardInfo("Dissipate", "rb61", Rarity.UNCOMMON, mage.cards.d.Dissipate.class, RETRO_ART));
        cards.add(new SetCardInfo("Dread of Night", "bs130sb", Rarity.UNCOMMON, mage.cards.d.DreadOfNight.class, RETRO_ART));
        cards.add(new SetCardInfo("Dwarven Miner", "br169sb", Rarity.UNCOMMON, mage.cards.d.DwarvenMiner.class, RETRO_ART));
        cards.add(new SetCardInfo("Dwarven Thaumaturgist", "br98sba", Rarity.RARE, mage.cards.d.DwarvenThaumaturgist.class, RETRO_ART));
        cards.add(new SetCardInfo("Emerald Charm", "bs106sb", Rarity.COMMON, mage.cards.e.EmeraldCharm.class, RETRO_ART));
        cards.add(new SetCardInfo("Empyrial Armor", "bh13", Rarity.COMMON, mage.cards.e.EmpyrialArmor.class, RETRO_ART));
        cards.add(new SetCardInfo("Final Fortune", "br174sb", Rarity.RARE, mage.cards.f.FinalFortune.class, RETRO_ART));
        cards.add(new SetCardInfo("Fireblast", "br79", Rarity.COMMON, mage.cards.f.Fireblast.class, RETRO_ART));
        cards.add(new SetCardInfo("Firestorm", "br101sb", Rarity.RARE, mage.cards.f.Firestorm.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Firestorm", "bs101", Rarity.RARE, mage.cards.f.Firestorm.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Forbid", "rb35", Rarity.UNCOMMON, mage.cards.f.Forbid.class, RETRO_ART));
        cards.add(new SetCardInfo("Force Spike", "rb58b", Rarity.COMMON, mage.cards.f.ForceSpike.class, RETRO_ART));
        cards.add(new SetCardInfo("Forest", "bs347", Rarity.LAND, mage.cards.basiclands.Forest.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", "bs348", Rarity.LAND, mage.cards.basiclands.Forest.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", "bs349", Rarity.LAND, mage.cards.basiclands.Forest.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", "bs350", Rarity.LAND, mage.cards.basiclands.Forest.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Gemstone Mine", "bs164", Rarity.UNCOMMON, mage.cards.g.GemstoneMine.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Vandal", "br105", Rarity.COMMON, mage.cards.g.GoblinVandal.class, RETRO_ART));
        cards.add(new SetCardInfo("Grindstone", "rb290sb", Rarity.RARE, mage.cards.g.Grindstone.class, RETRO_ART));
        cards.add(new SetCardInfo("Hall of Gemstone", "bs221sb", Rarity.RARE, mage.cards.h.HallOfGemstone.class, RETRO_ART));
        cards.add(new SetCardInfo("Hammer of Bogardan", "br181", Rarity.RARE, mage.cards.h.HammerOfBogardan.class, RETRO_ART));
        cards.add(new SetCardInfo("Hydroblast", "rb72sb", Rarity.COMMON, mage.cards.h.Hydroblast.class, RETRO_ART));
        cards.add(new SetCardInfo("Impulse", "rb34", Rarity.COMMON, mage.cards.i.Impulse.class, RETRO_ART));
        cards.add(new SetCardInfo("Incinerate", "br184", Rarity.COMMON, mage.cards.i.Incinerate.class, RETRO_ART));
        cards.add(new SetCardInfo("Ironclaw Orcs", "br245", Rarity.COMMON, mage.cards.i.IronclawOrcs.class, RETRO_ART));
        cards.add(new SetCardInfo("Island", "rb335", Rarity.LAND, mage.cards.basiclands.Island.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "rb336", Rarity.LAND, mage.cards.basiclands.Island.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "rb337", Rarity.LAND, mage.cards.basiclands.Island.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "rb338", Rarity.LAND, mage.cards.basiclands.Island.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Jackal Pup", "br183", Rarity.UNCOMMON, mage.cards.j.JackalPup.class, RETRO_ART));
        cards.add(new SetCardInfo("Karplusan Forest", "bs356", Rarity.RARE, mage.cards.k.KarplusanForest.class, RETRO_ART));
        cards.add(new SetCardInfo("Lobotomy", "bs267", Rarity.UNCOMMON, mage.cards.l.Lobotomy.class, RETRO_ART));
        cards.add(new SetCardInfo("Man-o'-War", "bs37", Rarity.COMMON, mage.cards.m.ManOWar.class, RETRO_ART));
        cards.add(new SetCardInfo("Mana Leak", "rb36", Rarity.COMMON, mage.cards.m.ManaLeak.class, RETRO_ART));
        cards.add(new SetCardInfo("Memory Lapse", "rb32", Rarity.COMMON, mage.cards.m.MemoryLapse.class, RETRO_ART));
        cards.add(new SetCardInfo("Mogg Fanatic", "br190", Rarity.COMMON, mage.cards.m.MoggFanatic.class, RETRO_ART));
        cards.add(new SetCardInfo("Mogg Flunkies", "br92", Rarity.COMMON, mage.cards.m.MoggFlunkies.class, RETRO_ART));
        cards.add(new SetCardInfo("Mountain", "br343", Rarity.LAND, mage.cards.basiclands.Mountain.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", "br344", Rarity.LAND, mage.cards.basiclands.Mountain.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", "br345", Rarity.LAND, mage.cards.basiclands.Mountain.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", "br346", Rarity.LAND, mage.cards.basiclands.Mountain.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Nekrataal", "bs66", Rarity.UNCOMMON, mage.cards.n.Nekrataal.class, RETRO_ART));
        cards.add(new SetCardInfo("Nevinyrral's Disk", "rb391", Rarity.RARE, mage.cards.n.NevinyrralsDisk.class, RETRO_ART));
        cards.add(new SetCardInfo("Nomads en-Kor", "bh9", Rarity.COMMON, mage.cards.n.NomadsEnKor.class, RETRO_ART));
        cards.add(new SetCardInfo("Orcish Settlers", "bs112b", Rarity.UNCOMMON, mage.cards.o.OrcishSettlers.class, RETRO_ART));
        cards.add(new SetCardInfo("Paladin en-Vec", "bh12", Rarity.RARE, mage.cards.p.PaladinEnVec.class, RETRO_ART));
        cards.add(new SetCardInfo("Phyrexian Furnace", "bs155sb", Rarity.UNCOMMON, mage.cards.p.PhyrexianFurnace.class, RETRO_ART));
        cards.add(new SetCardInfo("Plains", "bh331", Rarity.LAND, mage.cards.basiclands.Plains.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", "bh332", Rarity.LAND, mage.cards.basiclands.Plains.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", "bh333", Rarity.LAND, mage.cards.basiclands.Plains.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", "bh334", Rarity.LAND, mage.cards.basiclands.Plains.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Pyroblast", "br213sb", Rarity.COMMON, mage.cards.p.Pyroblast.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Pyroblast", "bs213sb", Rarity.COMMON, mage.cards.p.Pyroblast.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Quicksand", "rb166", Rarity.UNCOMMON, mage.cards.q.Quicksand.class, RETRO_ART));
        cards.add(new SetCardInfo("Rainbow Efreet", "rb41", Rarity.RARE, mage.cards.r.RainbowEfreet.class, RETRO_ART));
        cards.add(new SetCardInfo("Recurring Nightmare", "bs72", Rarity.RARE, mage.cards.r.RecurringNightmare.class, RETRO_ART));
        cards.add(new SetCardInfo("Reflecting Pool", "bs322", Rarity.RARE, mage.cards.r.ReflectingPool.class, RETRO_ART));
        cards.add(new SetCardInfo("Scroll Rack", "bs308", Rarity.RARE, mage.cards.s.ScrollRack.class, RETRO_ART));
        cards.add(new SetCardInfo("Sea Sprite", "rb38sb", Rarity.UNCOMMON, mage.cards.s.SeaSprite.class, RETRO_ART));
        cards.add(new SetCardInfo("Shattering Pulse", "br102sb", Rarity.COMMON, mage.cards.s.ShatteringPulse.class, RETRO_ART));
        cards.add(new SetCardInfo("Shock", "br98b", Rarity.COMMON, mage.cards.s.Shock.class, RETRO_ART));
        cards.add(new SetCardInfo("Soltari Monk", "bh45", Rarity.UNCOMMON, mage.cards.s.SoltariMonk.class, RETRO_ART));
        cards.add(new SetCardInfo("Soltari Priest", "bh46", Rarity.UNCOMMON, mage.cards.s.SoltariPriest.class, RETRO_ART));
        cards.add(new SetCardInfo("Soltari Visionary", "bh20", Rarity.COMMON, mage.cards.s.SoltariVisionary.class, RETRO_ART));
        cards.add(new SetCardInfo("Soul Warden", "bh21", Rarity.COMMON, mage.cards.s.SoulWarden.class, RETRO_ART));
        cards.add(new SetCardInfo("Spike Feeder", "bs118", Rarity.UNCOMMON, mage.cards.s.SpikeFeeder.class, RETRO_ART));
        cards.add(new SetCardInfo("Spike Weaver", "bs128", Rarity.RARE, mage.cards.s.SpikeWeaver.class, RETRO_ART));
        cards.add(new SetCardInfo("Spirit Link", "bh64sb", Rarity.UNCOMMON, mage.cards.s.SpiritLink.class, RETRO_ART));
        cards.add(new SetCardInfo("Spirit of the Night", "bs146", Rarity.RARE, mage.cards.s.SpiritOfTheNight.class, RETRO_ART));
        cards.add(new SetCardInfo("Stalking Stones", "rb327", Rarity.UNCOMMON, mage.cards.s.StalkingStones.class, RETRO_ART));
        cards.add(new SetCardInfo("Staunch Defenders", "bs49sb", Rarity.UNCOMMON, mage.cards.s.StaunchDefenders.class, RETRO_ART));
        cards.add(new SetCardInfo("Survival of the Fittest", "bs129", Rarity.RARE, mage.cards.s.SurvivalOfTheFittest.class, RETRO_ART));
        cards.add(new SetCardInfo("Swamp", "bs340", Rarity.LAND, mage.cards.basiclands.Swamp.class, RETRO_ART));
        cards.add(new SetCardInfo("Thrull Surgeon", "bs76", Rarity.COMMON, mage.cards.t.ThrullSurgeon.class, RETRO_ART));
        cards.add(new SetCardInfo("Tithe", "bh23a", Rarity.RARE, mage.cards.t.Tithe.class, RETRO_ART));
        cards.add(new SetCardInfo("Tradewind Rider", "bs98", Rarity.RARE, mage.cards.t.TradewindRider.class, RETRO_ART));
        cards.add(new SetCardInfo("Uktabi Orangutan", "bs123", Rarity.UNCOMMON, mage.cards.u.UktabiOrangutan.class, RETRO_ART));
        cards.add(new SetCardInfo("Underground River", "bs362", Rarity.RARE, mage.cards.u.UndergroundRiver.class, RETRO_ART));
        cards.add(new SetCardInfo("Undiscovered Paradise", "bs167", Rarity.RARE, mage.cards.u.UndiscoveredParadise.class, RETRO_ART));
        cards.add(new SetCardInfo("Verdant Force", "bs263", Rarity.RARE, mage.cards.v.VerdantForce.class, RETRO_ART));
        cards.add(new SetCardInfo("Viashino Sandstalker", "br100", Rarity.UNCOMMON, mage.cards.v.ViashinoSandstalker.class, RETRO_ART));
        cards.add(new SetCardInfo("Volrath's Stronghold", "bs143", Rarity.RARE, mage.cards.v.VolrathsStronghold.class, RETRO_ART));
        cards.add(new SetCardInfo("Wall of Blossoms", "bs125", Rarity.UNCOMMON, mage.cards.w.WallOfBlossoms.class, RETRO_ART));
        cards.add(new SetCardInfo("Wall of Roots", "bs253", Rarity.COMMON, mage.cards.w.WallOfRoots.class, RETRO_ART));
        cards.add(new SetCardInfo("Warrior en-Kor", "bh23b", Rarity.UNCOMMON, mage.cards.w.WarriorEnKor.class, RETRO_ART));
        cards.add(new SetCardInfo("Wasteland", "br330", Rarity.UNCOMMON, mage.cards.w.Wasteland.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Wasteland", "rb330sb", Rarity.UNCOMMON, mage.cards.w.Wasteland.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Whispers of the Muse", "rb103", Rarity.UNCOMMON, mage.cards.w.WhispersOfTheMuse.class, RETRO_ART));
        cards.add(new SetCardInfo("White Knight", "bh68", Rarity.UNCOMMON, mage.cards.w.WhiteKnight.class, RETRO_ART));
    }
}
