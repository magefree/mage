package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class JumpstartHistoricHorizons extends ExpansionSet {

    private static final JumpstartHistoricHorizons instance = new JumpstartHistoricHorizons();

    public static JumpstartHistoricHorizons getInstance() {
        return instance;
    }

    private JumpstartHistoricHorizons() {
        super("Historic Anthology 5", "HA5", ExpansionSet.buildDate(2021, 8, 12), SetType.MAGIC_ARENA);
        this.hasBoosters = false;
        this.hasBasicLands = false;
    }
}
