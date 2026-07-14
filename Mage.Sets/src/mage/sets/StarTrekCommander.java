package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author muz
 */
public final class StarTrekCommander extends ExpansionSet {

    private static final StarTrekCommander instance = new StarTrekCommander();

    public static StarTrekCommander getInstance() {
        return instance;
    }

    private StarTrekCommander() {
        super("Star Trek Commander", "TRC", ExpansionSet.buildDate(2026, 11, 20), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Benjamin Sisko, Besieged", 200, Rarity.MYTHIC, mage.cards.b.BenjaminSiskoBesieged.class));
        cards.add(new SetCardInfo("Spock, Logical Choice", 194, Rarity.MYTHIC, mage.cards.s.SpockLogicalChoice.class));
    }
}
