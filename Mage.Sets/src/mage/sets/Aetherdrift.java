package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class Aetherdrift extends ExpansionSet {

    private static final Aetherdrift instance = new Aetherdrift();

    public static Aetherdrift getInstance() {
        return instance;
    }

    private Aetherdrift() {
        super("Aetherdrift", "DFT", ExpansionSet.buildDate(2025, 2, 14), SetType.EXPANSION);
        this.blockName = "Aetherdrift"; // for sorting in GUI
        this.hasBasicLands = false; // temporary
        this.hasBoosters = false; // temporary
    }
}
