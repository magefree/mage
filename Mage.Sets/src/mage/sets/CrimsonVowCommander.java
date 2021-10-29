package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class CrimsonVowCommander extends ExpansionSet {

    private static final CrimsonVowCommander instance = new CrimsonVowCommander();

    public static CrimsonVowCommander getInstance() {
        return instance;
    }

    private CrimsonVowCommander() {
        super("Crimson Vow Commander", "VOC", ExpansionSet.buildDate(2021, 11, 19), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;
    }
}
