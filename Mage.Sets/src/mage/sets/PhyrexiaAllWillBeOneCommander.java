package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class PhyrexiaAllWillBeOneCommander extends ExpansionSet {

    private static final PhyrexiaAllWillBeOneCommander instance = new PhyrexiaAllWillBeOneCommander();

    public static PhyrexiaAllWillBeOneCommander getInstance() {
        return instance;
    }

    private PhyrexiaAllWillBeOneCommander() {
        super("Phyrexia: All Will Be One Commander", "ONC", ExpansionSet.buildDate(2023, 1, 10), SetType.SUPPLEMENTAL);
    }
}
