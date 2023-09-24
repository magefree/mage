
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

/**
 * @author Susucr
 */
public final class LostCavernsOfIxalanCommander extends ExpansionSet {

    private static final LostCavernsOfIxalanCommander instance = new LostCavernsOfIxalanCommander();

    public static LostCavernsOfIxalanCommander getInstance() {
        return instance;
    }

    private LostCavernsOfIxalanCommander() {
        super("Lost Caverns of Ixalan Commander", "LCC", ExpansionSet.buildDate(2023, 11, 17), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;
    }
}
