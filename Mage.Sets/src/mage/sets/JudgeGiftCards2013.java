package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/j13
 */
public class JudgeGiftCards2013 extends ExpansionSet {

    private static final JudgeGiftCards2013 instance = new JudgeGiftCards2013();

    public static JudgeGiftCards2013 getInstance() {
        return instance;
    }

    private JudgeGiftCards2013() {
        super("Judge Gift Cards 2013", "J13", ExpansionSet.buildDate(2013, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Bribery", 2, Rarity.RARE, mage.cards.b.Bribery.class));
        cards.add(new SetCardInfo("Crucible of Worlds", 4, Rarity.RARE, mage.cards.c.CrucibleOfWorlds.class));
        cards.add(new SetCardInfo("Genesis", 5, Rarity.RARE, mage.cards.g.Genesis.class));
        cards.add(new SetCardInfo("Imperial Recruiter", 3, Rarity.RARE, mage.cards.i.ImperialRecruiter.class));
        cards.add(new SetCardInfo("Overwhelming Forces", 6, Rarity.RARE, mage.cards.o.OverwhelmingForces.class));
        cards.add(new SetCardInfo("Show and Tell", 8, Rarity.RARE, mage.cards.s.ShowAndTell.class));
        cards.add(new SetCardInfo("Swords to Plowshares", 1, Rarity.RARE, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("Vindicate", 7, Rarity.RARE, mage.cards.v.Vindicate.class));
     }
}
