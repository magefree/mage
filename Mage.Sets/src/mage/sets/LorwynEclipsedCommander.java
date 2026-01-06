package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class LorwynEclipsedCommander extends ExpansionSet {

    private static final LorwynEclipsedCommander instance = new LorwynEclipsedCommander();

    public static LorwynEclipsedCommander getInstance() {
        return instance;
    }

    private LorwynEclipsedCommander() {
        super("Lorwyn Eclipsed Commander", "ECC", ExpansionSet.buildDate(2026, 1, 23), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false; // temporary
    }
}
