package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class Commander2020Edition extends ExpansionSet {

    private static final Commander2020Edition instance = new Commander2020Edition();

    public static Commander2020Edition getInstance() {
        return instance;
    }

    private Commander2020Edition() {
        super("Commander 2020 Edition", "C20", ExpansionSet.buildDate(2020, 4, 24), SetType.SUPPLEMENTAL);
        this.blockName = "Command Zone";
    }
}
