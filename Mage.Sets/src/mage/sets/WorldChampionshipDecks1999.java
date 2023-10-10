package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/wc99
 */
public class WorldChampionshipDecks1999 extends ExpansionSet {

    private static final WorldChampionshipDecks1999 instance = new WorldChampionshipDecks1999();

    public static WorldChampionshipDecks1999 getInstance() {
        return instance;
    }

    private WorldChampionshipDecks1999() {
        super("World Championship Decks 1999", "WC99", ExpansionSet.buildDate(1999, 8, 4), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Albino Troll", "ml231", Rarity.UNCOMMON, mage.cards.a.AlbinoTroll.class));
        cards.add(new SetCardInfo("Ancient Tomb", "mlp315", Rarity.UNCOMMON, mage.cards.a.AncientTomb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ancient Tomb", "kb315", Rarity.UNCOMMON, mage.cards.a.AncientTomb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arc Lightning", "mlp174sb", Rarity.COMMON, mage.cards.a.ArcLightning.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arc Lightning", "mlp174", Rarity.COMMON, mage.cards.a.ArcLightning.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Avalanche Riders", "mlp74", Rarity.UNCOMMON, mage.cards.a.AvalancheRiders.class));
        cards.add(new SetCardInfo("Boil", "kb165sb", Rarity.UNCOMMON, mage.cards.b.Boil.class));
        cards.add(new SetCardInfo("Bottle Gnomes", "js278sb", Rarity.UNCOMMON, mage.cards.b.BottleGnomes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bottle Gnomes", "js278", Rarity.UNCOMMON, mage.cards.b.BottleGnomes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Carrion Beetles", "js122sb", Rarity.COMMON, mage.cards.c.CarrionBeetles.class));
        cards.add(new SetCardInfo("Choke", "ml219sb", Rarity.UNCOMMON, mage.cards.c.Choke.class));
        cards.add(new SetCardInfo("City of Traitors", "kb143a", Rarity.RARE, mage.cards.c.CityOfTraitors.class));
        cards.add(new SetCardInfo("Constant Mists", "ml104sb", Rarity.UNCOMMON, mage.cards.c.ConstantMists.class));
        cards.add(new SetCardInfo("Corpse Dance", "js116", Rarity.RARE, mage.cards.c.CorpseDance.class));
        cards.add(new SetCardInfo("Covetous Dragon", "kb80", Rarity.RARE, mage.cards.c.CovetousDragon.class));
        cards.add(new SetCardInfo("Cursed Scroll", "mlp281", Rarity.RARE, mage.cards.c.CursedScroll.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cursed Scroll", "ml281", Rarity.RARE, mage.cards.c.CursedScroll.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cursed Scroll", "kb281", Rarity.RARE, mage.cards.c.CursedScroll.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cursed Scroll", "js281", Rarity.RARE, mage.cards.c.CursedScroll.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dark Ritual", "js127", Rarity.COMMON, mage.cards.d.DarkRitual.class));
        cards.add(new SetCardInfo("Diabolic Edict", "js128", Rarity.COMMON, mage.cards.d.DiabolicEdict.class));
        cards.add(new SetCardInfo("Duress", "js132", Rarity.COMMON, mage.cards.d.Duress.class));
        cards.add(new SetCardInfo("Earthquake", "kb173sb", Rarity.RARE, mage.cards.e.Earthquake.class));
        cards.add(new SetCardInfo("Elvish Lyrist", "ml248", Rarity.COMMON, mage.cards.e.ElvishLyrist.class));
        cards.add(new SetCardInfo("Evincar's Justice", "js134sb", Rarity.COMMON, mage.cards.e.EvincarsJustice.class));
        cards.add(new SetCardInfo("Fire Diamond", "kb284", Rarity.UNCOMMON, mage.cards.f.FireDiamond.class));
        cards.add(new SetCardInfo("Fireslinger", "mlp173sb", Rarity.COMMON, mage.cards.f.Fireslinger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fireslinger", "mlp173", Rarity.COMMON, mage.cards.f.Fireslinger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Flowstone Flood", "mlp83sb", Rarity.UNCOMMON, mage.cards.f.FlowstoneFlood.class));
        cards.add(new SetCardInfo("Forest", "ml349", Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", "ml347b", Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", "ml347a", Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gaea's Cradle", "ml321", Rarity.RARE, mage.cards.g.GaeasCradle.class));
        cards.add(new SetCardInfo("Ghitu Encampment", "mlp141", Rarity.UNCOMMON, mage.cards.g.GhituEncampment.class));
        cards.add(new SetCardInfo("Giant Growth", "ml233", Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Grim Monolith", "kb126", Rarity.RARE, mage.cards.g.GrimMonolith.class));
        cards.add(new SetCardInfo("Hammer of Bogardan", "mlp188", Rarity.RARE, mage.cards.h.HammerOfBogardan.class));
        cards.add(new SetCardInfo("Hatred", "js64sb", Rarity.RARE, mage.cards.h.Hatred.class));
        cards.add(new SetCardInfo("Hurricane", "ml237sb", Rarity.RARE, mage.cards.h.Hurricane.class));
        cards.add(new SetCardInfo("Jackal Pup", "mlp183", Rarity.UNCOMMON, mage.cards.j.JackalPup.class));
        cards.add(new SetCardInfo("Karn, Silver Golem", "kb298", Rarity.RARE, mage.cards.k.KarnSilverGolem.class));
        cards.add(new SetCardInfo("Llanowar Elves", "ml239", Rarity.COMMON, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Masticore", "kb143b", Rarity.RARE, mage.cards.m.Masticore.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Masticore", "mlp134sb", Rarity.RARE, mage.cards.m.Masticore.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mishra's Helix", "kb302sb", Rarity.RARE, mage.cards.m.MishrasHelix.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mishra's Helix", "kb302", Rarity.RARE, mage.cards.m.MishrasHelix.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mogg Fanatic", "mlp190", Rarity.COMMON, mage.cards.m.MoggFanatic.class));
        cards.add(new SetCardInfo("Mountain", "kb343", Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", "kb344", Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", "kb346", Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", "mlp343", Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", "mlp344", Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", "mlp346", Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Overrun", "ml243sb", Rarity.UNCOMMON, mage.cards.o.Overrun.class));
        cards.add(new SetCardInfo("Perish", "js147sb", Rarity.UNCOMMON, mage.cards.p.Perish.class));
        cards.add(new SetCardInfo("Persecute", "js146sb", Rarity.RARE, mage.cards.p.Persecute.class));
        cards.add(new SetCardInfo("Phyrexian Negator", "js65sb", Rarity.RARE, mage.cards.p.PhyrexianNegator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Phyrexian Negator", "js65", Rarity.RARE, mage.cards.p.PhyrexianNegator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Phyrexian Plaguelord", "js62", Rarity.RARE, mage.cards.p.PhyrexianPlaguelord.class));
        cards.add(new SetCardInfo("Phyrexian Processor", "kb306sb", Rarity.RARE, mage.cards.p.PhyrexianProcessor.class));
        cards.add(new SetCardInfo("Pillage", "mlp198", Rarity.UNCOMMON, mage.cards.p.Pillage.class));
        cards.add(new SetCardInfo("Pouncing Jaguar", "ml269", Rarity.COMMON, mage.cards.p.PouncingJaguar.class));
        cards.add(new SetCardInfo("Powder Keg", "js136a", Rarity.RARE, mage.cards.p.PowderKeg.class));
        cards.add(new SetCardInfo("Rack and Ruin", "kb89sb", Rarity.UNCOMMON, mage.cards.r.RackAndRuin.class));
        cards.add(new SetCardInfo("Rancor", "ml110", Rarity.COMMON, mage.cards.r.Rancor.class));
        cards.add(new SetCardInfo("Rapid Decay", "js67sb", Rarity.RARE, mage.cards.r.RapidDecay.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rapid Decay", "js67", Rarity.RARE, mage.cards.r.RapidDecay.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ravenous Rats", "js68", Rarity.COMMON, mage.cards.r.RavenousRats.class));
        cards.add(new SetCardInfo("River Boa", "ml249", Rarity.UNCOMMON, mage.cards.r.RiverBoa.class));
        cards.add(new SetCardInfo("Scald", "mlp211sb", Rarity.UNCOMMON, mage.cards.s.Scald.class));
        cards.add(new SetCardInfo("Shattering Pulse", "mlp102sb", Rarity.COMMON, mage.cards.s.ShatteringPulse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shattering Pulse", "kb102sb", Rarity.COMMON, mage.cards.s.ShatteringPulse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shock", "mlp98", Rarity.COMMON, mage.cards.s.Shock.class));
        cards.add(new SetCardInfo("Spawning Pool", "js142", Rarity.UNCOMMON, mage.cards.s.SpawningPool.class));
        cards.add(new SetCardInfo("Spellshock", "kb104sb", Rarity.UNCOMMON, mage.cards.s.Spellshock.class));
        cards.add(new SetCardInfo("Sphere of Resistance", "js139sb", Rarity.RARE, mage.cards.s.SphereOfResistance.class));
        cards.add(new SetCardInfo("Stone Rain", "mlp209", Rarity.COMMON, mage.cards.s.StoneRain.class));
        cards.add(new SetCardInfo("Stromgald Cabal", "js157sb", Rarity.RARE, mage.cards.s.StromgaldCabal.class));
        cards.add(new SetCardInfo("Stupor", "js158", Rarity.UNCOMMON, mage.cards.s.Stupor.class));
        cards.add(new SetCardInfo("Swamp", "js340b", Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", "js340a", Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", "js339", Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temporal Aperture", "kb310", Rarity.RARE, mage.cards.t.TemporalAperture.class));
        cards.add(new SetCardInfo("Thran Dynamo", "kb139", Rarity.UNCOMMON, mage.cards.t.ThranDynamo.class));
        cards.add(new SetCardInfo("Thran Foundry", "mlp140sb", Rarity.UNCOMMON, mage.cards.t.ThranFoundry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thran Foundry", "ml140sb", Rarity.UNCOMMON, mage.cards.t.ThranFoundry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ticking Gnomes", "js136b", Rarity.UNCOMMON, mage.cards.t.TickingGnomes.class));
        cards.add(new SetCardInfo("Treetop Village", "ml143", Rarity.UNCOMMON, mage.cards.t.TreetopVillage.class));
        cards.add(new SetCardInfo("Uktabi Orangutan", "ml260sb", Rarity.UNCOMMON, mage.cards.u.UktabiOrangutan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Uktabi Orangutan", "ml260", Rarity.UNCOMMON, mage.cards.u.UktabiOrangutan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vampiric Tutor", "js161", Rarity.RARE, mage.cards.v.VampiricTutor.class));
        cards.add(new SetCardInfo("Volrath's Stronghold", "js143", Rarity.RARE, mage.cards.v.VolrathsStronghold.class));
        cards.add(new SetCardInfo("Voltaic Key", "kb314", Rarity.UNCOMMON, mage.cards.v.VoltaicKey.class));
        cards.add(new SetCardInfo("Wasteland", "mlp330", Rarity.UNCOMMON, mage.cards.w.Wasteland.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wasteland", "js330", Rarity.UNCOMMON, mage.cards.w.Wasteland.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Weatherseed Treefolk", "ml116sb", Rarity.RARE, mage.cards.w.WeatherseedTreefolk.class));
        cards.add(new SetCardInfo("Wild Dogs", "ml284", Rarity.COMMON, mage.cards.w.WildDogs.class));
        cards.add(new SetCardInfo("Wildfire", "kb228", Rarity.RARE, mage.cards.w.Wildfire.class));
        cards.add(new SetCardInfo("Worn Powerstone", "kb318", Rarity.UNCOMMON, mage.cards.w.WornPowerstone.class));
        cards.add(new SetCardInfo("Yawgmoth's Will", "js171", Rarity.RARE, mage.cards.y.YawgmothsWill.class));
     }
}
