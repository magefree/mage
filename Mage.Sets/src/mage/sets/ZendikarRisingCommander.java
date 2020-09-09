package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class ZendikarRisingCommander extends ExpansionSet {

    private static final ZendikarRisingCommander instance = new ZendikarRisingCommander();

    public static ZendikarRisingCommander getInstance() {
        return instance;
    }

    private ZendikarRisingCommander() {
        super("Zendikar Rising Commander", "ZNC", ExpansionSet.buildDate(2020, 9, 25), SetType.SUPPLEMENTAL);

        cards.add(new SetCardInfo("Enigma Thief", 4, Rarity.RARE, mage.cards.e.EnigmaThief.class));
    }
}
