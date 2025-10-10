package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class TeenageMutantNinjaTurtlesEternal extends ExpansionSet {

    private static final TeenageMutantNinjaTurtlesEternal instance = new TeenageMutantNinjaTurtlesEternal();

    public static TeenageMutantNinjaTurtlesEternal getInstance() {
        return instance;
    }

    private TeenageMutantNinjaTurtlesEternal() {
        super("Teenage Mutant Ninja Turtles Commander", "TMC", ExpansionSet.buildDate(2026, 3, 6), SetType.EXPANSION);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Dark Ritual", 131, Rarity.MYTHIC, mage.cards.d.DarkRitual.class));
    }
}
