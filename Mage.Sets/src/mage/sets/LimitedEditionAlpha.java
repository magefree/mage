package mage.sets;

import java.util.GregorianCalendar;
import mage.Constants;
import mage.cards.ExpansionSet;

/**
 *
 * @author North
 */
public class LimitedEditionAlpha extends ExpansionSet {

    private static final LimitedEditionAlpha fINSTANCE = new LimitedEditionAlpha();

    public static LimitedEditionAlpha getInstance() {
        return fINSTANCE;
    }

    private LimitedEditionAlpha() {
        super("Limited Edition Alpha", "LEA", "", "mage.sets.limitedalpha", new GregorianCalendar(1993, 7, 1).getTime(), Constants.SetType.CORE);
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
    }
}
