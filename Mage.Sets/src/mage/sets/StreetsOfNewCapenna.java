package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class StreetsOfNewCapenna extends ExpansionSet {

    private static final StreetsOfNewCapenna instance = new StreetsOfNewCapenna();

    public static StreetsOfNewCapenna getInstance() {
        return instance;
    }

    private StreetsOfNewCapenna() {
        super("Streets of New Capenna", "SNC", ExpansionSet.buildDate(2022, 4, 29), SetType.EXPANSION);
        this.blockName = "Streets of New Capenna";
        this.hasBoosters = true;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Botanical Plaza", 350, Rarity.COMMON, mage.cards.b.BotanicalPlaza.class));
        cards.add(new SetCardInfo("Brokers Ascendancy", 170, Rarity.RARE, mage.cards.b.BrokersAscendancy.class));
        cards.add(new SetCardInfo("Brokers Charm", 171, Rarity.UNCOMMON, mage.cards.b.BrokersCharm.class));
        cards.add(new SetCardInfo("Cabaretti Charm", 173, Rarity.UNCOMMON, mage.cards.c.CabarettiCharm.class));
        cards.add(new SetCardInfo("Chrome Cat", 236, Rarity.COMMON, mage.cards.c.ChromeCat.class));
        cards.add(new SetCardInfo("Cut of the Profits", 72, Rarity.RARE, mage.cards.c.CutOfTheProfits.class));
        cards.add(new SetCardInfo("Devilish Valet", 105, Rarity.RARE, mage.cards.d.DevilishValet.class));
        cards.add(new SetCardInfo("Forest", 270, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 264, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jetmir's Garden", 250, Rarity.RARE, mage.cards.j.JetmirsGarden.class));
        cards.add(new SetCardInfo("Jetmir, Nexus of Revels", 193, Rarity.MYTHIC, mage.cards.j.JetmirNexusOfRevels.class));
        cards.add(new SetCardInfo("Ledger Shredder", 46, Rarity.RARE, mage.cards.l.LedgerShredder.class));
        cards.add(new SetCardInfo("Light 'Em Up", 113, Rarity.COMMON, mage.cards.l.LightEmUp.class));
        cards.add(new SetCardInfo("Lord Xander, the Collector", 197, Rarity.MYTHIC, mage.cards.l.LordXanderTheCollector.class));
        cards.add(new SetCardInfo("Maestros Charm", 199, Rarity.UNCOMMON, mage.cards.m.MaestrosCharm.class));
        cards.add(new SetCardInfo("Mountain", 268, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Murder", 88, Rarity.COMMON, mage.cards.m.Murder.class));
        cards.add(new SetCardInfo("Obscura Charm", 208, Rarity.UNCOMMON, mage.cards.o.ObscuraCharm.class));
        cards.add(new SetCardInfo("Plains", 262, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Racers' Ring", 253, Rarity.COMMON, mage.cards.r.RacersRing.class));
        cards.add(new SetCardInfo("Raffine's Tower", 254, Rarity.RARE, mage.cards.r.RaffinesTower.class));
        cards.add(new SetCardInfo("Raffine, Scheming Seer", 213, Rarity.MYTHIC, mage.cards.r.RaffineSchemingSeer.class));
        cards.add(new SetCardInfo("Riveteers Charm", 217, Rarity.UNCOMMON, mage.cards.r.RiveteersCharm.class));
        cards.add(new SetCardInfo("Rumor Gatherer", 29, Rarity.UNCOMMON, mage.cards.r.RumorGatherer.class));
        cards.add(new SetCardInfo("Skybridge Towers", 256, Rarity.COMMON, mage.cards.s.SkybridgeTowers.class));
        cards.add(new SetCardInfo("Spara's Headquarters", 257, Rarity.RARE, mage.cards.s.SparasHeadquarters.class));
        cards.add(new SetCardInfo("Strangle", 125, Rarity.COMMON, mage.cards.s.Strangle.class));
        cards.add(new SetCardInfo("Swamp", 266, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tramway Station", 258, Rarity.COMMON, mage.cards.t.TramwayStation.class));
        cards.add(new SetCardInfo("Waterfront District", 259, Rarity.COMMON, mage.cards.w.WaterfrontDistrict.class));
        cards.add(new SetCardInfo("Xander's Lounge", 260, Rarity.RARE, mage.cards.x.XandersLounge.class));
        cards.add(new SetCardInfo("Ziatora's Proving Ground", 261, Rarity.RARE, mage.cards.z.ZiatorasProvingGround.class));
    }
}
