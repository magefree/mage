package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pidw
 */
public class IDWComics2012 extends ExpansionSet {

    private static final IDWComics2012 instance = new IDWComics2012();

    public static IDWComics2012 getInstance() {
        return instance;
    }

    private IDWComics2012() {
        super("IDW Comics 2012", "PIDW", ExpansionSet.buildDate(2012, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Arrest", 5, Rarity.RARE, mage.cards.a.Arrest.class));
        cards.add(new SetCardInfo("Breath of Malfegor", 8, Rarity.RARE, mage.cards.b.BreathOfMalfegor.class));
        cards.add(new SetCardInfo("Consume Spirit", 6, Rarity.RARE, mage.cards.c.ConsumeSpirit.class));
        cards.add(new SetCardInfo("Electrolyze", 3, Rarity.RARE, mage.cards.e.Electrolyze.class));
        cards.add(new SetCardInfo("Faithless Looting", 2, Rarity.RARE, mage.cards.f.FaithlessLooting.class));
        cards.add(new SetCardInfo("Feast of Blood", 4, Rarity.RARE, mage.cards.f.FeastOfBlood.class));
        cards.add(new SetCardInfo("Standstill", 7, Rarity.RARE, mage.cards.s.Standstill.class));
        cards.add(new SetCardInfo("Treasure Hunt", 1, Rarity.RARE, mage.cards.t.TreasureHunt.class));
        cards.add(new SetCardInfo("Turnabout", 9, Rarity.RARE, mage.cards.t.Turnabout.class));
     }
}
