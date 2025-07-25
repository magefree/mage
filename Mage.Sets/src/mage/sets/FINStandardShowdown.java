package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pss5
 */
public class FINStandardShowdown extends ExpansionSet {

    private static final FINStandardShowdown instance = new FINStandardShowdown();

    public static FINStandardShowdown getInstance() {
        return instance;
    }

    private FINStandardShowdown() {
        super("FIN Standard Showdown", "PSS5", ExpansionSet.buildDate(2025, 6, 13), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Squall, SeeD Mercenary", 2, Rarity.RARE, mage.cards.s.SquallSeeDMercenary.class));
        cards.add(new SetCardInfo("Ultima", 1, Rarity.RARE, mage.cards.u.Ultima.class));
     }
}
