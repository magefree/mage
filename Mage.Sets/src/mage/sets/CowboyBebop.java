package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pcbb
 */
public class CowboyBebop extends ExpansionSet {

    private static final CowboyBebop instance = new CowboyBebop();

    public static CowboyBebop getInstance() {
        return instance;
    }

    private CowboyBebop() {
        super("Cowboy Bebop", "PCBB", ExpansionSet.buildDate(2024, 8, 2), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Disdainful Stroke", 2, Rarity.RARE, mage.cards.d.DisdainfulStroke.class));
        cards.add(new SetCardInfo("Go for the Throat", 3, Rarity.RARE, mage.cards.g.GoForTheThroat.class));
        cards.add(new SetCardInfo("Lightning Strike", 4, Rarity.RARE, mage.cards.l.LightningStrike.class));
        cards.add(new SetCardInfo("Ossification", 1, Rarity.RARE, mage.cards.o.Ossification.class));
        cards.add(new SetCardInfo("Snakeskin Veil", 5, Rarity.RARE, mage.cards.s.SnakeskinVeil.class));
     }
}
