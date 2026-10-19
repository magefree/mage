package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
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

        // Chittering Illuminator (15)

        // Ethrimik, Imagined Fiend (1)

        cards.add(new SetCardInfo("Fear of Change", 21, Rarity.UNCOMMON, mage.cards.f.FearOfChange.class));
    }
}
