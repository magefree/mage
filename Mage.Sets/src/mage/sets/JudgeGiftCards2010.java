package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/g10
 */
public class JudgeGiftCards2010 extends ExpansionSet {

    private static final JudgeGiftCards2010 instance = new JudgeGiftCards2010();

    public static JudgeGiftCards2010 getInstance() {
        return instance;
    }

    private JudgeGiftCards2010() {
        super("Judge Gift Cards 2010", "G10", ExpansionSet.buildDate(2010, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Land Tax", 5, Rarity.RARE, mage.cards.l.LandTax.class));
        cards.add(new SetCardInfo("Morphling", 6, Rarity.RARE, mage.cards.m.Morphling.class));
        cards.add(new SetCardInfo("Natural Order", 2, Rarity.RARE, mage.cards.n.NaturalOrder.class));
        cards.add(new SetCardInfo("Phyrexian Dreadnought", 3, Rarity.RARE, mage.cards.p.PhyrexianDreadnought.class));
        cards.add(new SetCardInfo("Sinkhole", 1, Rarity.RARE, mage.cards.s.Sinkhole.class));
        cards.add(new SetCardInfo("Thawing Glaciers", 4, Rarity.RARE, mage.cards.t.ThawingGlaciers.class));
        cards.add(new SetCardInfo("Wasteland", 8, Rarity.RARE, mage.cards.w.Wasteland.class));
        cards.add(new SetCardInfo("Wheel of Fortune", 7, Rarity.RARE, mage.cards.w.WheelOfFortune.class));
     }
}
