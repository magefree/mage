package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pg07
 */
public class Gateway2007 extends ExpansionSet {

    private static final Gateway2007 instance = new Gateway2007();

    public static Gateway2007 getInstance() {
        return instance;
    }

    private Gateway2007() {
        super("Gateway 2007", "PG07", ExpansionSet.buildDate(2007, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Boomerang", 4, Rarity.RARE, mage.cards.b.Boomerang.class));
        cards.add(new SetCardInfo("Calciderm", 5, Rarity.RARE, mage.cards.c.Calciderm.class));
        cards.add(new SetCardInfo("Dauntless Dourbark", 12, Rarity.RARE, mage.cards.d.DauntlessDourbark.class));
        cards.add(new SetCardInfo("Llanowar Elves", 9, Rarity.RARE, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Mind Stone", 11, Rarity.RARE, mage.cards.m.MindStone.class));
        cards.add(new SetCardInfo("Mogg Fanatic", 10, Rarity.RARE, mage.cards.m.MoggFanatic.class));
        cards.add(new SetCardInfo("Reckless Wurm", 6, Rarity.RARE, mage.cards.r.RecklessWurm.class));
        cards.add(new SetCardInfo("Yixlid Jailer", 7, Rarity.RARE, mage.cards.y.YixlidJailer.class));
        cards.add(new SetCardInfo("Zoetic Cavern", 8, Rarity.RARE, mage.cards.z.ZoeticCavern.class));
     }
}
