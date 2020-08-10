package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/g00
 */
public class JudgeGiftCards2000 extends ExpansionSet {

    private static final JudgeGiftCards2000 instance = new JudgeGiftCards2000();

    public static JudgeGiftCards2000 getInstance() {
        return instance;
    }

    private JudgeGiftCards2000() {
        super("Judge Gift Cards 2000", "G00", ExpansionSet.buildDate(2000, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Counterspell", 1, Rarity.RARE, mage.cards.c.Counterspell.class));
        cards.add(new SetCardInfo("Vampiric Tutor", 2, Rarity.RARE, mage.cards.v.VampiricTutor.class));
     }
}
