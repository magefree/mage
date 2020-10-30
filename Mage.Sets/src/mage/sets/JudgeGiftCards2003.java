package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/g03
 */
public class JudgeGiftCards2003 extends ExpansionSet {

    private static final JudgeGiftCards2003 instance = new JudgeGiftCards2003();

    public static JudgeGiftCards2003 getInstance() {
        return instance;
    }

    private JudgeGiftCards2003() {
        super("Judge Gift Cards 2003", "G03", ExpansionSet.buildDate(2003, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Argothian Enchantress", 2, Rarity.RARE, mage.cards.a.ArgothianEnchantress.class));
        cards.add(new SetCardInfo("Intuition", 1, Rarity.RARE, mage.cards.i.Intuition.class));
        cards.add(new SetCardInfo("Living Death", 3, Rarity.RARE, mage.cards.l.LivingDeath.class));
     }
}
