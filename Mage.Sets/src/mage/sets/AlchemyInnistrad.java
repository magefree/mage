package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class AlchemyInnistrad extends ExpansionSet {

    private static final AlchemyInnistrad instance = new AlchemyInnistrad();

    public static AlchemyInnistrad getInstance() {
        return instance;
    }

    private AlchemyInnistrad() {
        super("Alchemy: Innistrad", "Y22", ExpansionSet.buildDate(2021, 12, 9), SetType.MAGIC_ARENA);
        this.blockName = "Alchemy";
        this.hasBoosters = false;

        cards.add(new SetCardInfo("Faithful Disciple", 7, Rarity.UNCOMMON, mage.cards.f.FaithfulDisciple.class));
    }
}
