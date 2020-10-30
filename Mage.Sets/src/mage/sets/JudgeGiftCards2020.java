package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/j20
 */
public class JudgeGiftCards2020 extends ExpansionSet {

    private static final JudgeGiftCards2020 instance = new JudgeGiftCards2020();

    public static JudgeGiftCards2020 getInstance() {
        return instance;
    }

    private JudgeGiftCards2020() {
        super("Judge Gift Cards 2020", "J20", ExpansionSet.buildDate(2020, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Arena Rector", 1, Rarity.RARE, mage.cards.a.ArenaRector.class));
        cards.add(new SetCardInfo("Demonic Tutor", 4, Rarity.RARE, mage.cards.d.DemonicTutor.class));
        cards.add(new SetCardInfo("Enlightened Tutor", 2, Rarity.RARE, mage.cards.e.EnlightenedTutor.class));
        cards.add(new SetCardInfo("Gamble", 6, Rarity.RARE, mage.cards.g.Gamble.class));
        cards.add(new SetCardInfo("Spellseeker", 3, Rarity.RARE, mage.cards.s.Spellseeker.class));
        cards.add(new SetCardInfo("Sylvan Tutor", 8, Rarity.RARE, mage.cards.s.SylvanTutor.class));
     }
}
