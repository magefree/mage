
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author Shootbot
 */
public final class DuelsOfThePlaneswalkers extends ExpansionSet {

    private static final DuelsOfThePlaneswalkers instance = new DuelsOfThePlaneswalkers();

    public static DuelsOfThePlaneswalkers getInstance() {
        return instance;
    }

    private DuelsOfThePlaneswalkers() {
        super("Duels of the Planeswalkers", "DPA", ExpansionSet.buildDate(2010, 6, 4), SetType.SUPPLEMENTAL);
        cards.add(new SetCardInfo("The Rack", 95, Rarity.UNCOMMON, mage.cards.t.TheRack.class));
    }
}
