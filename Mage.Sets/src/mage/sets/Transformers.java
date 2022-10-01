package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class Transformers extends ExpansionSet {

    private static final Transformers instance = new Transformers();

    public static Transformers getInstance() {
        return instance;
    }

    private Transformers() {
        super("Transformers", "BOT", ExpansionSet.buildDate(2022, 11, 18), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;
    }
}
