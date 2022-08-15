package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class DominariaUnitedCommander extends ExpansionSet {

    private static final DominariaUnitedCommander instance = new DominariaUnitedCommander();

    public static DominariaUnitedCommander getInstance() {
        return instance;
    }

    private DominariaUnitedCommander() {
        super("Dominaria United Commander", "DMC", ExpansionSet.buildDate(2022, 11, 9), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;
    }
}
