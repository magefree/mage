package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

public final class AlchemyOutlawsOfThunderJunction extends ExpansionSet {

    private static final AlchemyOutlawsOfThunderJunction instance = new AlchemyOutlawsOfThunderJunction();

    public static AlchemyOutlawsOfThunderJunction getInstance() {
        return instance;
    }

    private AlchemyOutlawsOfThunderJunction() {
        super("Alchemy: Outlaws of Thunder Junction", "YOTJ", ExpansionSet.buildDate(2024, 5, 7), SetType.MAGIC_ARENA);
        this.blockName = "Alchemy";
        this.hasBoosters = false;
        this.hasBasicLands = false;

        // Impetuous Lootmonger (12)

        cards.add(new SetCardInfo("Jet Collector", 18, Rarity.RARE, mage.cards.j.JetCollector.class));

        // Saint Elenda (4)

        // Vona de Iedo, the Antifex (28)
    }
}
