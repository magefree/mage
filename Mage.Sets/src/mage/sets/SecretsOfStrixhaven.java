package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class SecretsOfStrixhaven extends ExpansionSet {

    private static final SecretsOfStrixhaven instance = new SecretsOfStrixhaven();

    public static SecretsOfStrixhaven getInstance() {
        return instance;
    }

    private SecretsOfStrixhaven() {
        super("Secrets of Strixhaven", "SOS", ExpansionSet.buildDate(2026, 4, 24), SetType.EXPANSION);
        this.blockName = "Secrets of Strixhaven"; // for sorting in GUI
        this.hasBasicLands = false; // temporary
    }
}
