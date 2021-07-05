package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class ForgottenRealmsCommander extends ExpansionSet {

    private static final ForgottenRealmsCommander instance = new ForgottenRealmsCommander();

    public static ForgottenRealmsCommander getInstance() {
        return instance;
    }

    private ForgottenRealmsCommander() {
        super("Adventures in the Forgotten Realms Commander", "AFC", ExpansionSet.buildDate(2021, 7, 23), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;
    }
}
