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

        cards.add(new SetCardInfo("Brokers Ascendancy", 170, Rarity.RARE, mage.cards.b.BrokersAscendancy.class));
        cards.add(new SetCardInfo("Cabaretti Charm", 173, Rarity.UNCOMMON, mage.cards.c.CabarettiCharm.class));
        cards.add(new SetCardInfo("Forest", 280, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 274, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Jetmir's Garden", 250, Rarity.RARE, mage.cards.j.JetmirsGarden.class));
        cards.add(new SetCardInfo("Jetmir, Nexus of Revels", 193, Rarity.MYTHIC, mage.cards.j.JetmirNexusOfRevels.class));
        cards.add(new SetCardInfo("Lord Xander, the Collector", 197, Rarity.MYTHIC, mage.cards.l.LordXanderTheCollector.class));
        cards.add(new SetCardInfo("Maestros Charm", 199, Rarity.UNCOMMON, mage.cards.m.MaestrosCharm.class));
        cards.add(new SetCardInfo("Mountain", 278, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Obscura Charm", 208, Rarity.UNCOMMON, mage.cards.o.ObscuraCharm.class));
        cards.add(new SetCardInfo("Plains", 272, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Raffine's Tower", 254, Rarity.RARE, mage.cards.r.RaffinesTower.class));
        cards.add(new SetCardInfo("Raffine, Scheming Seer", 213, Rarity.MYTHIC, mage.cards.r.RaffineSchemingSeer.class));
        cards.add(new SetCardInfo("Riveteers Charm", 217, Rarity.UNCOMMON, mage.cards.r.RiveteersCharm.class));
        cards.add(new SetCardInfo("Spara's Headquarters", 257, Rarity.RARE, mage.cards.s.SparasHeadquarters.class));
        cards.add(new SetCardInfo("Swamp", 276, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Xander's Lounge", 260, Rarity.RARE, mage.cards.x.XandersLounge.class));
        cards.add(new SetCardInfo("Ziatora's Proving Ground", 261, Rarity.RARE, mage.cards.z.ZiatorasProvingGround.class));
    }
}
