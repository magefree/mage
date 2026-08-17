package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author muz
 */
public final class AlchemyWildsOfEldraine extends ExpansionSet {

    private static final AlchemyWildsOfEldraine instance = new AlchemyWildsOfEldraine();

    public static AlchemyWildsOfEldraine getInstance() {
        return instance;
    }

    private AlchemyWildsOfEldraine() {
        super("Alchemy: Wilds of Eldraine", "YWOE", ExpansionSet.buildDate(2023, 10, 10), SetType.MAGIC_ARENA);
        this.blockName = "Alchemy";
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("First Little Pig", 18, Rarity.UNCOMMON, mage.cards.f.FirstLittlePig.class));
        cards.add(new SetCardInfo("Overcooked", 11, Rarity.MYTHIC, mage.cards.o.Overcooked.class));
        cards.add(new SetCardInfo("Victory of the Pyrohammer", 12, Rarity.RARE, mage.cards.v.VictoryOfThePyrohammer.class));
    }
}
