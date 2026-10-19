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

        cards.add(new SetCardInfo("Motivated Muralist", 15, Rarity.UNCOMMON, mage.cards.m.MotivatedMuralist.class));

        // Ornate Imitations (23)

        // Trackhand Trainer (6)
    }
}
