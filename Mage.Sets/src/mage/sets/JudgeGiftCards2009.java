package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/g09
 */
public class JudgeGiftCards2009 extends ExpansionSet {

    private static final JudgeGiftCards2009 instance = new JudgeGiftCards2009();

    public static JudgeGiftCards2009 getInstance() {
        return instance;
    }

    private JudgeGiftCards2009() {
        super("Judge Gift Cards 2009", "G09", ExpansionSet.buildDate(2009, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Bloodstained Mire", 6, Rarity.RARE, mage.cards.b.BloodstainedMire.class));
        cards.add(new SetCardInfo("Burning Wish", 5, Rarity.RARE, mage.cards.b.BurningWish.class));
        cards.add(new SetCardInfo("Dark Ritual", 1, Rarity.RARE, mage.cards.d.DarkRitual.class));
        cards.add(new SetCardInfo("Flooded Strand", 7, Rarity.RARE, mage.cards.f.FloodedStrand.class));
        cards.add(new SetCardInfo("Maze of Ith", 2, Rarity.RARE, mage.cards.m.MazeOfIth.class));
        cards.add(new SetCardInfo("Polluted Delta", 8, Rarity.RARE, mage.cards.p.PollutedDelta.class));
        cards.add(new SetCardInfo("Stifle", 3, Rarity.RARE, mage.cards.s.Stifle.class));
        cards.add(new SetCardInfo("Survival of the Fittest", 4, Rarity.RARE, mage.cards.s.SurvivalOfTheFittest.class));
        cards.add(new SetCardInfo("Windswept Heath", 9, Rarity.RARE, mage.cards.w.WindsweptHeath.class));
        cards.add(new SetCardInfo("Wooded Foothills", 10, Rarity.RARE, mage.cards.w.WoodedFoothills.class));
     }
}
