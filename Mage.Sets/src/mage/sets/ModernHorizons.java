package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class ModernHorizons extends ExpansionSet {

    private static final ModernHorizons instance = new ModernHorizons();

    public static ModernHorizons getInstance() {
        return instance;
    }

    private ModernHorizons() {
        // TODO: update the set type closer to release (no point right now, the cards won't be legal for a while)
        super("Modern Horizons", "MH1", ExpansionSet.buildDate(2019, 6, 14), SetType.SUPPLEMENTAL);
        this.blockName = "Modern Horizons";
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;

    }

}
