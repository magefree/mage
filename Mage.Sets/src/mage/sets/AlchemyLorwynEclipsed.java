package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

public final class AlchemyLorwynEclipsed extends ExpansionSet {

    private static final AlchemyLorwynEclipsed instance = new AlchemyLorwynEclipsed();

    public static AlchemyLorwynEclipsed getInstance() {
        return instance;
    }

    private AlchemyLorwynEclipsed() {
        super("Alchemy: Lorwyn Eclipsed", "YECL", ExpansionSet.buildDate(2026, 2, 3), SetType.MAGIC_ARENA);
        this.blockName = "Alchemy";
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Gilt-Leaf Alchemist", 14, Rarity.RARE, mage.cards.g.GiltLeafAlchemist.class));
    }
}
