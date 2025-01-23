package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class AetherdriftCommander extends ExpansionSet {

    private static final AetherdriftCommander instance = new AetherdriftCommander();

    public static AetherdriftCommander getInstance() {
        return instance;
    }

    private AetherdriftCommander() {
        super("Aetherdrift Commander", "DRC", ExpansionSet.buildDate(2025, 1, 21), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Temmet, Naktamun's Will", 4, Rarity.MYTHIC, mage.cards.t.TemmetNaktamunsWill.class));
    }
}
