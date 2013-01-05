package mage.sets;

import java.util.GregorianCalendar;
import mage.Constants;
import mage.cards.ExpansionSet;

/**
 *
 * @author North
 */
public class LimitedEditionBeta extends ExpansionSet {

    private static final LimitedEditionBeta fINSTANCE = new LimitedEditionBeta();

    public static LimitedEditionBeta getInstance() {
        return fINSTANCE;
    }

    private LimitedEditionBeta() {
        super("Limited Edition Beta", "LEB", "", "mage.sets.limitedbeta", new GregorianCalendar(1993, 9, 1).getTime(), Constants.SetType.CORE);
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
    }
}
