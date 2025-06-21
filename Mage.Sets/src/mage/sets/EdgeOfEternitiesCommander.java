package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class EdgeOfEternitiesCommander extends ExpansionSet {

    private static final EdgeOfEternitiesCommander instance = new EdgeOfEternitiesCommander();

    public static EdgeOfEternitiesCommander getInstance() {
        return instance;
    }

    private EdgeOfEternitiesCommander() {
        super("Edge of Eternities Commander", "EOC", ExpansionSet.buildDate(2025, 8, 1), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Kilo, Apogee Mind", 3, Rarity.MYTHIC, mage.cards.k.KiloApogeeMind.class));
    }
}
