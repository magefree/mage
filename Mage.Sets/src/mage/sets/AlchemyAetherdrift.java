package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

public final class AlchemyAetherdrift extends ExpansionSet {

    private static final AlchemyAetherdrift instance = new AlchemyAetherdrift();

    public static AlchemyAetherdrift getInstance() {
        return instance;
    }

    private AlchemyAetherdrift() {
        super("Alchemy: Aetherdrift", "YDFT", ExpansionSet.buildDate(2025, 3, 4), SetType.MAGIC_ARENA);
        this.blockName = "Alchemy";
        this.hasBoosters = false;
        this.hasBasicLands = false;

        // Motivated Muralist (15)

        cards.add(new SetCardInfo("Ornate Imitations", 23, Rarity.MYTHIC, mage.cards.o.OrnateImitations.class));

        // Trackhand Trainer (6)
    }
}
