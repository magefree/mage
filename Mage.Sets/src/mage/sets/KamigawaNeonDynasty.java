package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class KamigawaNeonDynasty extends ExpansionSet {

    private static final KamigawaNeonDynasty instance = new KamigawaNeonDynasty();

    public static KamigawaNeonDynasty getInstance() {
        return instance;
    }

    private KamigawaNeonDynasty() {
        super("Kamigawa: Neon Dynasty", "NEO", ExpansionSet.buildDate(2022, 2, 18), SetType.EXPANSION);
        this.blockName = "Kamigawa: Neon Dynasty";
        this.hasBoosters = true;
        this.hasBasicLands = true;
    }
}
