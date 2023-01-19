package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author chesse20
 */
public final class AlchemyStreetsOfNewCappenaRebalanced extends ExpansionSet {

    private static final AlchemyStreetsOfNewCappenaRebalanced instance = new AlchemyStreetsOfNewCappenaRebalanced();

    public static AlchemyStreetsOfNewCappenaRebalanced getInstance() {
        return instance;
    }

    private AlchemyStreetsOfNewCappenaRebalanced() {
        super("Alchemy Streets of New Cappena Rebalanced", "ASNC", ExpansionSet.buildDate(2022, 8, 5), SetType.MAGIC_ARENA);
        this.blockName = "Streets of New Capenna";
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("A-Buy your Silence", 6, Rarity.COMMON, mage.cards.b.BuyYourSilenceAlchemy.class));
    }
}
