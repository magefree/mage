package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class DominariaUnitedCommander extends ExpansionSet {

    private static final DominariaUnitedCommander instance = new DominariaUnitedCommander();

    public static DominariaUnitedCommander getInstance() {
        return instance;
    }

    private DominariaUnitedCommander() {
        super("Dominaria United Commander", "DMC", ExpansionSet.buildDate(2022, 11, 9), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Bad River", 197, Rarity.UNCOMMON, mage.cards.b.BadRiver.class));
        cards.add(new SetCardInfo("Baleful Strix", 143, Rarity.RARE, mage.cards.b.BalefulStrix.class));
        cards.add(new SetCardInfo("Cascading Cataracts", 202, Rarity.RARE, mage.cards.c.CascadingCataracts.class));
        cards.add(new SetCardInfo("Crystal Quarry", 206, Rarity.RARE, mage.cards.c.CrystalQuarry.class));
        cards.add(new SetCardInfo("Faeburrow Elder", 149, Rarity.RARE, mage.cards.f.FaeburrowElder.class));
        cards.add(new SetCardInfo("Iridian Maelstrom", 12, Rarity.RARE, mage.cards.i.IridianMaelstrom.class));
        cards.add(new SetCardInfo("Maelstrom Nexus", 159, Rarity.MYTHIC, mage.cards.m.MaelstromNexus.class));
        cards.add(new SetCardInfo("Murmuring Bosk", 220, Rarity.RARE, mage.cards.m.MurmuringBosk.class));
        cards.add(new SetCardInfo("Nethroi, Apex of Death", 163, Rarity.MYTHIC, mage.cards.n.NethroiApexOfDeath.class));
        cards.add(new SetCardInfo("O-Kagachi, Vengeful Kami", 164, Rarity.MYTHIC, mage.cards.o.OKagachiVengefulKami.class));
        cards.add(new SetCardInfo("Path to Exile", 104, Rarity.UNCOMMON, mage.cards.p.PathToExile.class));
        cards.add(new SetCardInfo("Shanid, Sleepers' Scourge", 4, Rarity.MYTHIC, mage.cards.s.ShanidSleepersScourge.class));
        cards.add(new SetCardInfo("Surrak Dragonclaw", 169, Rarity.MYTHIC, mage.cards.s.SurrakDragonclaw.class));
        cards.add(new SetCardInfo("Thrill of Possibility", 127, Rarity.COMMON, mage.cards.t.ThrillOfPossibility.class));
        cards.add(new SetCardInfo("Two-Headed Hellkite", 14, Rarity.RARE, mage.cards.t.TwoHeadedHellkite.class));
        cards.add(new SetCardInfo("Unite the Coalition", 15, Rarity.RARE, mage.cards.u.UniteTheCoalition.class));
        cards.add(new SetCardInfo("Zeriam, Golden Wind", 5, Rarity.RARE, mage.cards.z.ZeriamGoldenWind.class));
    }
}
