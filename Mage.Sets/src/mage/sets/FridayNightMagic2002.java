package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/f02
 */
public class FridayNightMagic2002 extends ExpansionSet {

    private static final FridayNightMagic2002 instance = new FridayNightMagic2002();

    public static FridayNightMagic2002 getInstance() {
        return instance;
    }

    private FridayNightMagic2002() {
        super("Friday Night Magic 2002", "F02", ExpansionSet.buildDate(2002, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Albino Troll", 2, Rarity.RARE, mage.cards.a.AlbinoTroll.class));
        cards.add(new SetCardInfo("Aura of Silence", 8, Rarity.RARE, mage.cards.a.AuraOfSilence.class));
        cards.add(new SetCardInfo("Black Knight", 4, Rarity.RARE, mage.cards.b.BlackKnight.class));
        cards.add(new SetCardInfo("Dissipate", 3, Rarity.RARE, mage.cards.d.Dissipate.class));
        cards.add(new SetCardInfo("Drain Life", 7, Rarity.RARE, mage.cards.d.DrainLife.class));
        cards.add(new SetCardInfo("Fireslinger", 6, Rarity.RARE, mage.cards.f.Fireslinger.class));
        cards.add(new SetCardInfo("Forbid", 9, Rarity.RARE, mage.cards.f.Forbid.class));
        cards.add(new SetCardInfo("Mogg Fanatic", 11, Rarity.RARE, mage.cards.m.MoggFanatic.class));
        cards.add(new SetCardInfo("Soltari Priest", 1, Rarity.RARE, mage.cards.s.SoltariPriest.class));
        cards.add(new SetCardInfo("Spike Feeder", 10, Rarity.RARE, mage.cards.s.SpikeFeeder.class));
        cards.add(new SetCardInfo("Wall of Blossoms", 5, Rarity.RARE, mage.cards.w.WallOfBlossoms.class));
        cards.add(new SetCardInfo("White Knight", 12, Rarity.RARE, mage.cards.w.WhiteKnight.class));
     }
}
