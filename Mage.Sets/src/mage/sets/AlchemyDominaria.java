package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author karapuzz14
 */
public final class AlchemyDominaria extends ExpansionSet {

    private static final AlchemyDominaria instance = new AlchemyDominaria();

    public static AlchemyDominaria getInstance() {
        return instance;
    }

    private AlchemyDominaria() {
        super("Alchemy: Dominaria", "YDMU", ExpansionSet.buildDate(2022, 10, 5), SetType.MAGIC_ARENA);
        this.blockName = "Alchemy";
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Vinesoul Spider", 18, Rarity.UNCOMMON, mage.cards.v.VinesoulSpider.class));

    }
}
