package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/g07
 */
public class JudgeGiftCards2007 extends ExpansionSet {

    private static final JudgeGiftCards2007 instance = new JudgeGiftCards2007();

    public static JudgeGiftCards2007 getInstance() {
        return instance;
    }

    private JudgeGiftCards2007() {
        super("Judge Gift Cards 2007", "G07", ExpansionSet.buildDate(2007, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Cunning Wish", 2, Rarity.RARE, mage.cards.c.CunningWish.class));
        cards.add(new SetCardInfo("Decree of Justice", 5, Rarity.RARE, mage.cards.d.DecreeOfJustice.class));
        cards.add(new SetCardInfo("Ravenous Baloth", 1, Rarity.RARE, mage.cards.r.RavenousBaloth.class));
        cards.add(new SetCardInfo("Vindicate", 4, Rarity.RARE, mage.cards.v.Vindicate.class));
        cards.add(new SetCardInfo("Yawgmoth's Will", 3, Rarity.RARE, mage.cards.y.YawgmothsWill.class));
     }
}
