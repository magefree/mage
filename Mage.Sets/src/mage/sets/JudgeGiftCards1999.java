package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/g99
 */
public class JudgeGiftCards1999 extends ExpansionSet {

    private static final JudgeGiftCards1999 instance = new JudgeGiftCards1999();

    public static JudgeGiftCards1999 getInstance() {
        return instance;
    }

    private JudgeGiftCards1999() {
        super("Judge Gift Cards 1999", "G99", ExpansionSet.buildDate(1999, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Memory Lapse", 1, Rarity.RARE, mage.cards.m.MemoryLapse.class));
     }
}
