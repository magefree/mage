package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

public final class AlchemyNewCapenna extends ExpansionSet {

    private static final AlchemyNewCapenna instance = new AlchemyNewCapenna();

    public static AlchemyNewCapenna getInstance() {
        return instance;
    }

    private AlchemyNewCapenna() {
        super("Alchemy: New Capenna", "YSNC", ExpansionSet.buildDate(2022, 6, 2), SetType.MAGIC_ARENA);
        this.blockName = "Alchemy";
        this.hasBoosters = false;
        this.hasBasicLands = false;

        // Diviner of Fates (22)
    }
}
