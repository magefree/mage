package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/g08
 */
public class JudgeGiftCards2008 extends ExpansionSet {

    private static final JudgeGiftCards2008 instance = new JudgeGiftCards2008();

    public static JudgeGiftCards2008 getInstance() {
        return instance;
    }

    private JudgeGiftCards2008() {
        super("Judge Gift Cards 2008", "G08", ExpansionSet.buildDate(2008, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Demonic Tutor", 3, Rarity.RARE, mage.cards.d.DemonicTutor.class));
        cards.add(new SetCardInfo("Goblin Piledriver", 4, Rarity.RARE, mage.cards.g.GoblinPiledriver.class));
        cards.add(new SetCardInfo("Living Wish", 5, Rarity.RARE, mage.cards.l.LivingWish.class));
        cards.add(new SetCardInfo("Mind's Desire", 2, Rarity.RARE, mage.cards.m.MindsDesire.class));
        cards.add(new SetCardInfo("Orim's Chant", 1, Rarity.RARE, mage.cards.o.OrimsChant.class));
     }
}
