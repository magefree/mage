package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pbook
 */
public class MiscellaneousBookPromos extends ExpansionSet {

    private static final MiscellaneousBookPromos instance = new MiscellaneousBookPromos();

    public static MiscellaneousBookPromos getInstance() {
        return instance;
    }

    private MiscellaneousBookPromos() {
        super("Miscellaneous Book Promos", "PBOOK", ExpansionSet.buildDate(2009, 1, 27), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Jace Beleren", 1, Rarity.MYTHIC, mage.cards.j.JaceBeleren.class));
    }
}
