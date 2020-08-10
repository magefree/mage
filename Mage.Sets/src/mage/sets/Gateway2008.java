package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pg08
 */
public class Gateway2008 extends ExpansionSet {

    private static final Gateway2008 instance = new Gateway2008();

    public static Gateway2008 getInstance() {
        return instance;
    }

    private Gateway2008() {
        super("Gateway 2008", "PG08", ExpansionSet.buildDate(2008, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Boggart Ram-Gang", 17, Rarity.RARE, mage.cards.b.BoggartRamGang.class));
        cards.add(new SetCardInfo("Cenn's Tactician", 14, Rarity.RARE, mage.cards.c.CennsTactician.class));
        cards.add(new SetCardInfo("Duergar Hedge-Mage", 19, Rarity.RARE, mage.cards.d.DuergarHedgeMage.class));
        cards.add(new SetCardInfo("Gravedigger", 16, Rarity.RARE, mage.cards.g.Gravedigger.class));
        cards.add(new SetCardInfo("Lava Axe", 13, Rarity.RARE, mage.cards.l.LavaAxe.class));
        cards.add(new SetCardInfo("Oona's Blackguard", 15, Rarity.RARE, mage.cards.o.OonasBlackguard.class));
        cards.add(new SetCardInfo("Selkie Hedge-Mage", 20, Rarity.RARE, mage.cards.s.SelkieHedgeMage.class));
        cards.add(new SetCardInfo("Wilt-Leaf Cavaliers", 18, Rarity.RARE, mage.cards.w.WiltLeafCavaliers.class));
     }
}
