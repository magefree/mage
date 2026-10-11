package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author muz
 */
public final class DarkholdDestinyCommander extends ExpansionSet {

    private static final DarkholdDestinyCommander instance = new DarkholdDestinyCommander();

    public static DarkholdDestinyCommander getInstance() {
        return instance;
    }

    private DarkholdDestinyCommander() {
        super("Darkhold Destiny Commander", "MDC", ExpansionSet.buildDate(2027, 04, 9), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Gargantos, the Endbringer", 399, Rarity.RARE, mage.cards.g.GargantosTheEndbringer.class));
    }
}
