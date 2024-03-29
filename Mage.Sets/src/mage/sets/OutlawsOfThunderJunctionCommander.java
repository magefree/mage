package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class OutlawsOfThunderJunctionCommander extends ExpansionSet {

    private static final OutlawsOfThunderJunctionCommander instance = new OutlawsOfThunderJunctionCommander();

    public static OutlawsOfThunderJunctionCommander getInstance() {
        return instance;
    }

    private OutlawsOfThunderJunctionCommander() {
        super("Outlaws of Thunder Junction Commander", "OTC", ExpansionSet.buildDate(2024, 4, 19), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Stella Lee, Wild Card", 3, Rarity.MYTHIC, mage.cards.s.StellaLeeWildCard.class));
    }
}
