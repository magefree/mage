package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/j15
 */
public class JudgeGiftCards2015 extends ExpansionSet {

    private static final JudgeGiftCards2015 instance = new JudgeGiftCards2015();

    public static JudgeGiftCards2015 getInstance() {
        return instance;
    }

    private JudgeGiftCards2015() {
        super("Judge Gift Cards 2015", "J15", ExpansionSet.buildDate(2015, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Damnation", 5, Rarity.RARE, mage.cards.d.Damnation.class));
        cards.add(new SetCardInfo("Dualcaster Mage", 6, Rarity.RARE, mage.cards.d.DualcasterMage.class));
        cards.add(new SetCardInfo("Feldon of the Third Path", 7, Rarity.MYTHIC, mage.cards.f.FeldonOfTheThirdPath.class));
        cards.add(new SetCardInfo("Ravages of War", 4, Rarity.RARE, mage.cards.r.RavagesOfWar.class));
        cards.add(new SetCardInfo("Rishadan Port", 3, Rarity.RARE, mage.cards.r.RishadanPort.class));
        cards.add(new SetCardInfo("Shardless Agent", 2, Rarity.RARE, mage.cards.s.ShardlessAgent.class));
        cards.add(new SetCardInfo("Temporal Manipulation", 1, Rarity.RARE, mage.cards.t.TemporalManipulation.class));
        cards.add(new SetCardInfo("Wasteland", 8, Rarity.RARE, mage.cards.w.Wasteland.class));
     }
}
