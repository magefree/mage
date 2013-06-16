package mage.sets;

import java.util.GregorianCalendar;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

/**
 *
 * @author North
 */
public class UnlimitedEdition extends ExpansionSet {

    private static final UnlimitedEdition fINSTANCE = new UnlimitedEdition();

    public static UnlimitedEdition getInstance() {
        return fINSTANCE;
    }

    private UnlimitedEdition() {
        super("Unlimited Edition", "2ED", "mage.sets.unlimitededition", new GregorianCalendar(1993, 11, 1).getTime(), SetType.CORE);
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
    }
}
