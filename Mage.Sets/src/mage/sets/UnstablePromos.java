package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pust
 */
public class UnstablePromos extends ExpansionSet {

    private static final UnstablePromos instance = new UnstablePromos();

    public static UnstablePromos getInstance() {
        return instance;
    }

    private UnstablePromos() {
        super("Unstable Promos", "PUST", ExpansionSet.buildDate(2017, 11, 13), SetType.JOKESET);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Earl of Squirrel", 108, Rarity.RARE, mage.cards.e.EarlOfSquirrel.class));
     }
}
