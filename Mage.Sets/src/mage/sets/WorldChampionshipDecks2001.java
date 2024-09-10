package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/wc01
 */
public class WorldChampionshipDecks2001 extends ExpansionSet {

    private static final WorldChampionshipDecks2001 instance = new WorldChampionshipDecks2001();

    public static WorldChampionshipDecks2001 getInstance() {
        return instance;
    }

    private WorldChampionshipDecks2001() {
        super("World Championship Decks 2001", "WC01", ExpansionSet.buildDate(2001, 8, 8), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Accumulated Knowledge", "ar26", Rarity.COMMON, mage.cards.a.AccumulatedKnowledge.class));
        cards.add(new SetCardInfo("Addle", "tvdl91sb", Rarity.UNCOMMON, mage.cards.a.Addle.class));
        cards.add(new SetCardInfo("Birds of Paradise", "jt231", Rarity.RARE, mage.cards.b.BirdsOfParadise.class));
        cards.add(new SetCardInfo("Blastoderm", "jt102", Rarity.COMMON, mage.cards.b.Blastoderm.class));
        cards.add(new SetCardInfo("Blazing Specter", "tvdl236", Rarity.RARE, mage.cards.b.BlazingSpecter.class));
        cards.add(new SetCardInfo("Blood Oath", "jt177sb", Rarity.RARE, mage.cards.b.BloodOath.class));
        cards.add(new SetCardInfo("Boil", "jt177sba", Rarity.UNCOMMON, mage.cards.b.Boil.class));
        cards.add(new SetCardInfo("City of Brass", "jt327", Rarity.RARE, mage.cards.c.CityOfBrass.class));
        cards.add(new SetCardInfo("Counterspell", "ar69", Rarity.COMMON, mage.cards.c.Counterspell.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Counterspell", "ab69", Rarity.COMMON, mage.cards.c.Counterspell.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Counterspell", "ar67", Rarity.COMMON, mage.cards.c.Counterspell.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Counterspell", "ab67", Rarity.COMMON, mage.cards.c.Counterspell.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crosis's Charm", "ar99", Rarity.UNCOMMON, mage.cards.c.CrosissCharm.class));
        cards.add(new SetCardInfo("Crypt Angel", "tvdl97sb", Rarity.RARE, mage.cards.c.CryptAngel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crypt Angel", "tvdl97", Rarity.RARE, mage.cards.c.CryptAngel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dark Ritual", "tvdl129", Rarity.COMMON, mage.cards.d.DarkRitual.class));
        cards.add(new SetCardInfo("Darting Merfolk", "ab72", Rarity.COMMON, mage.cards.d.DartingMerfolk.class));
        cards.add(new SetCardInfo("Duress", "tvdl131", Rarity.COMMON, mage.cards.d.Duress.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Duress", "ar131sb", Rarity.COMMON, mage.cards.d.Duress.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Duress", "ar131", Rarity.COMMON, mage.cards.d.Duress.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Engineered Plague", "ar133sb", Rarity.UNCOMMON, mage.cards.e.EngineeredPlague.class));
        cards.add(new SetCardInfo("Fact or Fiction", "ar57", Rarity.UNCOMMON, mage.cards.f.FactOrFiction.class));
        cards.add(new SetCardInfo("Fire // Ice", "jt128", Rarity.UNCOMMON, mage.cards.f.FireIce.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fire // Ice", "ar128", Rarity.UNCOMMON, mage.cards.f.FireIce.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fires of Yavimaya", "jt246", Rarity.UNCOMMON, mage.cards.f.FiresOfYavimaya.class));
        cards.add(new SetCardInfo("Flametongue Kavu", "tvdl60sb", Rarity.UNCOMMON, mage.cards.f.FlametongueKavu.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Flametongue Kavu", "tvdl60", Rarity.UNCOMMON, mage.cards.f.FlametongueKavu.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Flametongue Kavu", "jt60", Rarity.UNCOMMON, mage.cards.f.FlametongueKavu.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", "jt349a", Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", "jt349", Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", "jt348a", Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", "jt348", Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", "jt347a", Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", "jt347", Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", "jt329", Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", "jt328", Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gush", "ab82", Rarity.COMMON, mage.cards.g.Gush.class));
        cards.add(new SetCardInfo("Hibernation", "ab79sb", Rarity.UNCOMMON, mage.cards.h.Hibernation.class));
        cards.add(new SetCardInfo("Island", "ar338", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "ab338a", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "ab338", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "ar337", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "ab337a", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "ab337", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "ar336a", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "ar336", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "ab336a", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "ab336", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "ar335b", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "ar335a", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "ar335", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "ab335b", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "ab335a", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "ab335", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "ar334", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "ab334", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "ab333", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "ab332", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Karplusan Forest", "jt336", Rarity.RARE, mage.cards.k.KarplusanForest.class));
        cards.add(new SetCardInfo("Kavu Chameleon", "jt191sb", Rarity.UNCOMMON, mage.cards.k.KavuChameleon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kavu Chameleon", "jt191", Rarity.UNCOMMON, mage.cards.k.KavuChameleon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Llanowar Elves", "jt253", Rarity.COMMON, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Llanowar Wastes", "jt141", Rarity.RARE, mage.cards.l.LlanowarWastes.class));
        cards.add(new SetCardInfo("Lobotomy", "ar255sb", Rarity.UNCOMMON, mage.cards.l.Lobotomy.class));
        cards.add(new SetCardInfo("Lord of Atlantis", "ab83", Rarity.RARE, mage.cards.l.LordOfAtlantis.class));
        cards.add(new SetCardInfo("Mana Maze", "ab59sb", Rarity.RARE, mage.cards.m.ManaMaze.class));
        cards.add(new SetCardInfo("Meekstone", "ar307sb", Rarity.RARE, mage.cards.m.Meekstone.class));
        cards.add(new SetCardInfo("Merfolk Looter", "ab89", Rarity.UNCOMMON, mage.cards.m.MerfolkLooter.class));
        cards.add(new SetCardInfo("Merfolk of the Pearl Trident", "ab90", Rarity.COMMON, mage.cards.m.MerfolkOfThePearlTrident.class));
        cards.add(new SetCardInfo("Misdirection", "ab87sb", Rarity.RARE, mage.cards.m.Misdirection.class));
        cards.add(new SetCardInfo("Mountain", "tvdl343b", Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", "tvdl343", Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", "jt343a", Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", "jt343", Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", "tvdl337", Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", "jt337", Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nether Spirit", "ar149", Rarity.RARE, mage.cards.n.NetherSpirit.class));
        cards.add(new SetCardInfo("Obliterate", "jt156sb", Rarity.RARE, mage.cards.o.Obliterate.class));
        cards.add(new SetCardInfo("Opposition", "ab92", Rarity.RARE, mage.cards.o.Opposition.class));
        cards.add(new SetCardInfo("Opt", "ar64", Rarity.COMMON, mage.cards.o.Opt.class));
        cards.add(new SetCardInfo("Persecute", "tvdl154sb", Rarity.RARE, mage.cards.p.Persecute.class));
        cards.add(new SetCardInfo("Phyrexian Arena", "tvdl47sb", Rarity.RARE, mage.cards.p.PhyrexianArena.class));
        cards.add(new SetCardInfo("Phyrexian Scuta", "tvdl51", Rarity.RARE, mage.cards.p.PhyrexianScuta.class));
        cards.add(new SetCardInfo("Plague Spitter", "tvdl119", Rarity.UNCOMMON, mage.cards.p.PlagueSpitter.class));
        cards.add(new SetCardInfo("Prodigal Sorcerer", "ab94sb", Rarity.COMMON, mage.cards.p.ProdigalSorcerer.class));
        cards.add(new SetCardInfo("Pyroclasm", "tvdl209sb", Rarity.UNCOMMON, mage.cards.p.Pyroclasm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pyroclasm", "ar209sb", Rarity.UNCOMMON, mage.cards.p.Pyroclasm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rishadan Port", "tvdl324", Rarity.RARE, mage.cards.r.RishadanPort.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rishadan Port", "jt324", Rarity.RARE, mage.cards.r.RishadanPort.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rushing River", "ab30sb", Rarity.COMMON, mage.cards.r.RushingRiver.class));
        cards.add(new SetCardInfo("Salt Marsh", "ar326", Rarity.UNCOMMON, mage.cards.s.SaltMarsh.class));
        cards.add(new SetCardInfo("Saproling Burst", "jt113", Rarity.RARE, mage.cards.s.SaprolingBurst.class));
        cards.add(new SetCardInfo("Scoria Cat", "tvdl101sb", Rarity.UNCOMMON, mage.cards.s.ScoriaCat.class));
        cards.add(new SetCardInfo("Shivan Reef", "ar142", Rarity.RARE, mage.cards.s.ShivanReef.class));
        cards.add(new SetCardInfo("Skizzik", "tvdl169", Rarity.RARE, mage.cards.s.Skizzik.class));
        cards.add(new SetCardInfo("Spiritmonger", "jt121", Rarity.RARE, mage.cards.s.Spiritmonger.class));
        cards.add(new SetCardInfo("Spite // Malice", "ar293", Rarity.UNCOMMON, mage.cards.s.SpiteMalice.class));
        cards.add(new SetCardInfo("Static Orb", "ab319", Rarity.RARE, mage.cards.s.StaticOrb.class));
        cards.add(new SetCardInfo("Sulfurous Springs", "tvdl345", Rarity.RARE, mage.cards.s.SulfurousSprings.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sulfurous Springs", "jt345", Rarity.RARE, mage.cards.s.SulfurousSprings.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sulfurous Springs", "ar345", Rarity.RARE, mage.cards.s.SulfurousSprings.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", "tvdl348", Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", "tvdl347", Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", "tvdl342", Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", "tvdl341", Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", "tvdl339a", Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", "tvdl339", Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tangle", "jt213sb", Rarity.UNCOMMON, mage.cards.t.Tangle.class));
        cards.add(new SetCardInfo("Teferi's Response", "ar78sb", Rarity.RARE, mage.cards.t.TeferisResponse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teferi's Response", "ab78sb", Rarity.RARE, mage.cards.t.TeferisResponse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Terminate", "tvdl128", Rarity.COMMON, mage.cards.t.Terminate.class));
        cards.add(new SetCardInfo("Thornscape Battlemage", "jt94", Rarity.UNCOMMON, mage.cards.t.ThornscapeBattlemage.class));
        cards.add(new SetCardInfo("Thunderscape Battlemage", "jt75sb", Rarity.UNCOMMON, mage.cards.t.ThunderscapeBattlemage.class));
        cards.add(new SetCardInfo("Thwart", "ab108", Rarity.UNCOMMON, mage.cards.t.Thwart.class));
        cards.add(new SetCardInfo("Tsabo's Assassin", "ar128sb", Rarity.RARE, mage.cards.t.TsabosAssassin.class));
        cards.add(new SetCardInfo("Tsabo's Decree", "ar129", Rarity.RARE, mage.cards.t.TsabosDecree.class));
        cards.add(new SetCardInfo("Tsabo's Web", "ar317", Rarity.RARE, mage.cards.t.TsabosWeb.class));
        cards.add(new SetCardInfo("Underground River", "ar350", Rarity.RARE, mage.cards.u.UndergroundRiver.class));
        cards.add(new SetCardInfo("Undermine", "ar282", Rarity.RARE, mage.cards.u.Undermine.class));
        cards.add(new SetCardInfo("Urborg Volcano", "tvdl330", Rarity.UNCOMMON, mage.cards.u.UrborgVolcano.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urborg Volcano", "ar330", Rarity.UNCOMMON, mage.cards.u.UrborgVolcano.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Rage", "tvdl178", Rarity.RARE, mage.cards.u.UrzasRage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Rage", "jt178", Rarity.RARE, mage.cards.u.UrzasRage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Rage", "ar178", Rarity.RARE, mage.cards.u.UrzasRage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vendetta", "tvdl170", Rarity.COMMON, mage.cards.v.Vendetta.class));
        cards.add(new SetCardInfo("Vodalian Merchant", "ab85", Rarity.COMMON, mage.cards.v.VodalianMerchant.class));
        cards.add(new SetCardInfo("Wash Out", "ab87bsb", Rarity.UNCOMMON, mage.cards.w.WashOut.class));
        cards.add(new SetCardInfo("Waterfront Bouncer", "ab114", Rarity.COMMON, mage.cards.w.WaterfrontBouncer.class));
     }
}
