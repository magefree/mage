package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

public final class AlchemyBloomburrow extends ExpansionSet {

    private static final AlchemyBloomburrow instance = new AlchemyBloomburrow();

    public static AlchemyBloomburrow getInstance() {
        return instance;
    }

    private AlchemyBloomburrow() {
        super("Alchemy: Bloomburrow", "YBLB", ExpansionSet.buildDate(2024, 8, 20), SetType.MAGIC_ARENA);
        this.blockName = "Alchemy";
        this.hasBoosters = false;
        this.hasBasicLands = false;

        // Chittering Skullspeaker (9)

        // Fountainport Charmer (17)
    }
}
