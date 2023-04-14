package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pzen
 */
public class ZendikarPromos extends ExpansionSet {

    private static final ZendikarPromos instance = new ZendikarPromos();

    public static ZendikarPromos getInstance() {
        return instance;
    }

    private ZendikarPromos() {
        super("Zendikar Promos", "PZEN", ExpansionSet.buildDate(2009, 10, 2), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Day of Judgment", "9*", Rarity.RARE, mage.cards.d.DayOfJudgment.class));
        cards.add(new SetCardInfo("Emeria Angel", 35, Rarity.RARE, mage.cards.e.EmeriaAngel.class));
        cards.add(new SetCardInfo("Nissa's Chosen", 34, Rarity.RARE, mage.cards.n.NissasChosen.class));
        cards.add(new SetCardInfo("Rampaging Baloths", "178*", Rarity.MYTHIC, mage.cards.r.RampagingBaloths.class));
        cards.add(new SetCardInfo("Valakut, the Molten Pinnacle", "228*", Rarity.RARE, mage.cards.v.ValakutTheMoltenPinnacle.class));
    }
}
