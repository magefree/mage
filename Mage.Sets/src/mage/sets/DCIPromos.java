package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pg07
 */
public class DCIPromos extends ExpansionSet {

    private static final DCIPromos instance = new DCIPromos();

    public static DCIPromos getInstance() {
        return instance;
    }

    private DCIPromos() {
        super("DCI Promos", "PDCI", ExpansionSet.buildDate(2006, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Wood Elves", 1, Rarity.RARE, mage.cards.w.WoodElves.class));
        cards.add(new SetCardInfo("Icatian Javelineers", 2, Rarity.RARE, mage.cards.i.IcatianJavelineers.class));
        cards.add(new SetCardInfo("Fiery Temper", 3, Rarity.RARE, mage.cards.f.FieryTemper.class));
        cards.add(new SetCardInfo("Boomerang", 4, Rarity.RARE, mage.cards.b.Boomerang.class));
        cards.add(new SetCardInfo("Calciderm", 5, Rarity.RARE, mage.cards.c.Calciderm.class));
        cards.add(new SetCardInfo("Reckless Wurm", 6, Rarity.RARE, mage.cards.r.RecklessWurm.class));
        cards.add(new SetCardInfo("Yixlid Jailer", 7, Rarity.RARE, mage.cards.y.YixlidJailer.class));
        cards.add(new SetCardInfo("Zoetic Cavern", 8, Rarity.RARE, mage.cards.z.ZoeticCavern.class));
        cards.add(new SetCardInfo("Llanowar Elves", 9, Rarity.RARE, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Mogg Fanatic", 10, Rarity.RARE, mage.cards.m.MoggFanatic.class));
        cards.add(new SetCardInfo("Mind Stone", 11, Rarity.RARE, mage.cards.m.MindStone.class));
        cards.add(new SetCardInfo("Dauntless Dourbark", 12, Rarity.RARE, mage.cards.d.DauntlessDourbark.class));
        cards.add(new SetCardInfo("Lava Axe", 13, Rarity.RARE, mage.cards.l.LavaAxe.class));
        cards.add(new SetCardInfo("Cenn's Tactician", 14, Rarity.RARE, mage.cards.c.CennsTactician.class));
        cards.add(new SetCardInfo("Oona's Blackguard", 15, Rarity.RARE, mage.cards.o.OonasBlackguard.class));
        cards.add(new SetCardInfo("Gravedigger", 16, Rarity.RARE, mage.cards.g.Gravedigger.class));
        cards.add(new SetCardInfo("Boggart Ram-Gang", 17, Rarity.RARE, mage.cards.b.BoggartRamGang.class));
        cards.add(new SetCardInfo("Wilt-Leaf Cavaliers", 18, Rarity.RARE, mage.cards.w.WiltLeafCavaliers.class));
        cards.add(new SetCardInfo("Duergar Hedge-Mage", 19, Rarity.RARE, mage.cards.d.DuergarHedgeMage.class));
        cards.add(new SetCardInfo("Selkie Hedge-Mage", 20, Rarity.RARE, mage.cards.s.SelkieHedgeMage.class));

     }
}
