
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

/**
 *
 * @author TheElk801
 */
public final class Commander2018 extends ExpansionSet {

    private static final Commander2018 instance = new Commander2018();

    public static Commander2018 getInstance() {
        return instance;
    }

    private Commander2018() {
        super("Commander 2018 Edition", "C18", ExpansionSet.buildDate(2018, 8, 10), SetType.SUPPLEMENTAL);
        this.blockName = "Command Zone";
    }
}
