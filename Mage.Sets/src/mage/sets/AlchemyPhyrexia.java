package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

public final class AlchemyPhyrexia extends ExpansionSet {

    private static final AlchemyPhyrexia instance = new AlchemyPhyrexia();

    public static AlchemyPhyrexia getInstance() {
        return instance;
    }

    private AlchemyPhyrexia() {
        super("Alchemy: Phyrexia", "YONE", ExpansionSet.buildDate(2023, 2, 28), SetType.MAGIC_ARENA);
        this.blockName = "Alchemy";
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Ghalma the Shaper", 1, Rarity.MYTHIC, mage.cards.g.GhalmaTheShaper.class));

        // Innovative Metatect (24)

        // Quicksilver Lapidary (26)
    }
}
