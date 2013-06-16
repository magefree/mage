package mage.sets;

import java.util.GregorianCalendar;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

/**
 *
 * @author North
 */
public class RevisedEdition extends ExpansionSet {

    private static final RevisedEdition fINSTANCE = new RevisedEdition();

    public static RevisedEdition getInstance() {
        return fINSTANCE;
    }

    private RevisedEdition() {
        super("Revised Edition", "3ED", "mage.sets.revisededition", new GregorianCalendar(1994, 3, 1).getTime(), SetType.CORE);
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
    }
}
