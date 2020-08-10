package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/g01
 */
public class JudgeGiftCards2001 extends ExpansionSet {

    private static final JudgeGiftCards2001 instance = new JudgeGiftCards2001();

    public static JudgeGiftCards2001 getInstance() {
        return instance;
    }

    private JudgeGiftCards2001() {
        super("Judge Gift Cards 2001", "G01", ExpansionSet.buildDate(2001, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Ball Lightning", 1, Rarity.RARE, mage.cards.b.BallLightning.class));
        cards.add(new SetCardInfo("Oath of Druids", 2, Rarity.RARE, mage.cards.o.OathOfDruids.class));
     }
}
