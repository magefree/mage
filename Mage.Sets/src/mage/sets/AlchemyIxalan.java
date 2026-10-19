package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

public final class AlchemyIxalan extends ExpansionSet {

    private static final AlchemyIxalan instance = new AlchemyIxalan();

    public static AlchemyIxalan getInstance() {
        return instance;
    }

    private AlchemyIxalan() {
        super("Alchemy: Ixalan", "YLCI", ExpansionSet.buildDate(2023, 12, 5), SetType.MAGIC_ARENA);
        this.blockName = "Alchemy";
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Landlore Navigator", 7, Rarity.UNCOMMON, mage.cards.l.LandloreNavigator.class));

        // Wingbane Vantasaur (22)
    }
}
