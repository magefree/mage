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
        super("Teenage Mutant Ninja Turtles Eternal", "TMC", ExpansionSet.buildDate(2026, 3, 6), SetType.EXPANSION);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Dark Ritual", 131, Rarity.MYTHIC, mage.cards.d.DarkRitual.class));
        cards.add(new SetCardInfo("Donatello, Rad Scientist", 109, Rarity.MYTHIC, mage.cards.d.DonatelloRadScientist.class));
        cards.add(new SetCardInfo("Leonardo, the Balance", 1, Rarity.MYTHIC, mage.cards.l.LeonardoTheBalance.class));
    }
}
