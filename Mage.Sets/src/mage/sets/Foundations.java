package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class Foundations extends ExpansionSet {

    private static final Foundations instance = new Foundations();

    public static Foundations getInstance() {
        return instance;
    }

    private Foundations() {
        super("Foundations", "FDN", ExpansionSet.buildDate(2024, 11,15), SetType.EXPANSION);
        this.blockName = "Foundations"; // for sorting in GUI
        this.hasBasicLands = false;
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Anthem of Champions", 116, Rarity.RARE, mage.cards.a.AnthemOfChampions.class));
        cards.add(new SetCardInfo("Day of Judgment", 140, Rarity.RARE, mage.cards.d.DayOfJudgment.class));
        cards.add(new SetCardInfo("Llanowar Elves", 227, Rarity.COMMON, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Omniscience", 161, Rarity.MYTHIC, mage.cards.o.Omniscience.class));
    }
}
