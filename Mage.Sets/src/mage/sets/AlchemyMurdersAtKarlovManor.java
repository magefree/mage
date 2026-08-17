package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author muz
 */
public final class AlchemyMurdersAtKarlovManor extends ExpansionSet {

    private static final AlchemyMurdersAtKarlovManor instance = new AlchemyMurdersAtKarlovManor();

    public static AlchemyMurdersAtKarlovManor getInstance() {
        return instance;
    }

    private AlchemyMurdersAtKarlovManor() {
        super("Alchemy: Murders at Karlov Manor", "YMKM", ExpansionSet.buildDate(2024, 3, 5), SetType.MAGIC_ARENA);
        this.blockName = "Alchemy";
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Emmara, Voice of the Conclave", 22, Rarity.RARE, mage.cards.e.EmmaraVoiceOfTheConclave.class));
        cards.add(new SetCardInfo("Emporium Thopterist", 5, Rarity.UNCOMMON, mage.cards.e.EmporiumThopterist.class));
    }
}
