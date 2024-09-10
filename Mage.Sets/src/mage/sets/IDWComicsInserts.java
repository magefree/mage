package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pidw
 */
public class IDWComicsInserts extends ExpansionSet {

    private static final IDWComicsInserts instance = new IDWComicsInserts();

    public static IDWComicsInserts getInstance() {
        return instance;
    }

    private IDWComicsInserts() {
        super("IDW Comics Inserts", "PIDW", ExpansionSet.buildDate(2012, 1, 1), SetType.PROMOTIONAL);
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
        cards.add(new SetCardInfo("Corrupt", 12, Rarity.RARE, mage.cards.c.Corrupt.class));
        cards.add(new SetCardInfo("Gaze of Granite", 14, Rarity.RARE, mage.cards.g.GazeOfGranite.class));
        cards.add(new SetCardInfo("High Tide", 13, Rarity.RARE, mage.cards.h.HighTide.class));
        cards.add(new SetCardInfo("Ogre Arsonist", 11, Rarity.RARE, mage.cards.o.OgreArsonist.class));
        cards.add(new SetCardInfo("Voidmage Husher", 10, Rarity.RARE, mage.cards.v.VoidmageHusher.class));
        cards.add(new SetCardInfo("Acquire", 16, Rarity.RARE, mage.cards.a.Acquire.class));
        cards.add(new SetCardInfo("Duress", 17, Rarity.RARE, mage.cards.d.Duress.class));
        cards.add(new SetCardInfo("Wash Out", 15, Rarity.RARE, mage.cards.w.WashOut.class));
     }
}
