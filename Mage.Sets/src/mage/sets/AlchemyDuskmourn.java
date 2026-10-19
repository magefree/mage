package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

public final class AlchemyDuskmourn extends ExpansionSet {

    private static final AlchemyDuskmourn instance = new AlchemyDuskmourn();

    public static AlchemyDuskmourn getInstance() {
        return instance;
    }

    private AlchemyDuskmourn() {
        super("Alchemy: Duskmourn", "YDSK", ExpansionSet.buildDate(2024, 10, 15), SetType.MAGIC_ARENA);
        this.blockName = "Alchemy";
        this.hasBoosters = false;
        this.hasBasicLands = false;

        // Ethrimik, Imagined Fiend (1)

        // Fear of Change (21)
    }
}
