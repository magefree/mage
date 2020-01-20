package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/f03
 */
public class FridayNightMagic2003 extends ExpansionSet {

    private static final FridayNightMagic2003 instance = new FridayNightMagic2003();

    public static FridayNightMagic2003 getInstance() {
        return instance;
    }

    private FridayNightMagic2003() {
        super("Friday Night Magic 2003", "F03", ExpansionSet.buildDate(2003, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Bottle Gnomes", 1, Rarity.RARE, mage.cards.b.BottleGnomes.class));
        cards.add(new SetCardInfo("Capsize", 4, Rarity.RARE, mage.cards.c.Capsize.class));
        cards.add(new SetCardInfo("Crystalline Sliver", 3, Rarity.RARE, mage.cards.c.CrystallineSliver.class));
        cards.add(new SetCardInfo("Disenchant", 13, Rarity.RARE, mage.cards.d.Disenchant.class));
        cards.add(new SetCardInfo("Goblin Bombardment", 6, Rarity.RARE, mage.cards.g.GoblinBombardment.class));
        cards.add(new SetCardInfo("Krosan Tusker", 11, Rarity.RARE, mage.cards.k.KrosanTusker.class));
        cards.add(new SetCardInfo("Muscle Sliver", 2, Rarity.RARE, mage.cards.m.MuscleSliver.class));
        cards.add(new SetCardInfo("Priest of Titania", 5, Rarity.RARE, mage.cards.p.PriestOfTitania.class));
        cards.add(new SetCardInfo("Scragnoth", 7, Rarity.RARE, mage.cards.s.Scragnoth.class));
        cards.add(new SetCardInfo("Smother", 8, Rarity.RARE, mage.cards.s.Smother.class));
        cards.add(new SetCardInfo("Sparksmith", 10, Rarity.RARE, mage.cards.s.Sparksmith.class));
        cards.add(new SetCardInfo("Whipcorder", 9, Rarity.RARE, mage.cards.w.Whipcorder.class));
        cards.add(new SetCardInfo("Withered Wretch", 12, Rarity.RARE, mage.cards.w.WitheredWretch.class));
     }
}
