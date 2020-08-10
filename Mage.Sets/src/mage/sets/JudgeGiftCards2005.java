package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/g05
 */
public class JudgeGiftCards2005 extends ExpansionSet {

    private static final JudgeGiftCards2005 instance = new JudgeGiftCards2005();

    public static JudgeGiftCards2005 getInstance() {
        return instance;
    }

    private JudgeGiftCards2005() {
        super("Judge Gift Cards 2005", "G05", ExpansionSet.buildDate(2005, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Gemstone Mine", 1, Rarity.RARE, mage.cards.g.GemstoneMine.class));
        cards.add(new SetCardInfo("Mishra's Factory", 4, Rarity.RARE, mage.cards.m.MishrasFactory.class));
        cards.add(new SetCardInfo("Regrowth", 2, Rarity.RARE, mage.cards.r.Regrowth.class));
        cards.add(new SetCardInfo("Sol Ring", 3, Rarity.RARE, mage.cards.s.SolRing.class));
     }
}
