package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/g04
 */
public class JudgeGiftCards2004 extends ExpansionSet {

    private static final JudgeGiftCards2004 instance = new JudgeGiftCards2004();

    public static JudgeGiftCards2004 getInstance() {
        return instance;
    }

    private JudgeGiftCards2004() {
        super("Judge Gift Cards 2004", "G04", ExpansionSet.buildDate(2004, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Armageddon", 1, Rarity.RARE, mage.cards.a.Armageddon.class));
        cards.add(new SetCardInfo("Balance", 2, Rarity.RARE, mage.cards.b.Balance.class));
        cards.add(new SetCardInfo("Deranged Hermit", 5, Rarity.RARE, mage.cards.d.DerangedHermit.class));
        cards.add(new SetCardInfo("Hermit Druid", 6, Rarity.RARE, mage.cards.h.HermitDruid.class));
        cards.add(new SetCardInfo("Phyrexian Negator", 4, Rarity.RARE, mage.cards.p.PhyrexianNegator.class));
        cards.add(new SetCardInfo("Time Warp", 3, Rarity.RARE, mage.cards.t.TimeWarp.class));
     }
}
