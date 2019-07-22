package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class ThroneOfEldraine extends ExpansionSet {

    private static final ThroneOfEldraine instance = new ThroneOfEldraine();

    public static ThroneOfEldraine getInstance() {
        return instance;
    }

    private ThroneOfEldraine() {
        super("Throne of Eldraine", "ELD", ExpansionSet.buildDate(2019, 10, 4), SetType.EXPANSION);
        this.blockName = "Throne of Eldraine";
        this.hasBoosters = false; // false until basics officially spoiled
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.maxCardNumberInBooster = 269; // unconfirmed for now
    }
}
