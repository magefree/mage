package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/g02
 */
public class JudgeGiftCards2002 extends ExpansionSet {

    private static final JudgeGiftCards2002 instance = new JudgeGiftCards2002();

    public static JudgeGiftCards2002 getInstance() {
        return instance;
    }

    private JudgeGiftCards2002() {
        super("Judge Gift Cards 2002", "G02", ExpansionSet.buildDate(2002, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Hammer of Bogardan", 1, Rarity.RARE, mage.cards.h.HammerOfBogardan.class));
        cards.add(new SetCardInfo("Tradewind Rider", 2, Rarity.RARE, mage.cards.t.TradewindRider.class));
     }
}
