package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class IkoriaLairOfBehemoths extends ExpansionSet {

    private static final IkoriaLairOfBehemoths instance = new IkoriaLairOfBehemoths();

    public static IkoriaLairOfBehemoths getInstance() {
        return instance;
    }

    private IkoriaLairOfBehemoths() {
        super("Ikoria: Lair of Behemoths", "IKO", ExpansionSet.buildDate(2020, 4, 24), SetType.EXPANSION);
        this.blockName = "Ikoria: Lair of Behemoths";
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.maxCardNumberInBooster = 274;
    }
}
