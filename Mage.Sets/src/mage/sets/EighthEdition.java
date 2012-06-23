package mage.sets;

import java.util.GregorianCalendar;
import mage.Constants.SetType;
import mage.cards.ExpansionSet;

public class EighthEdition extends ExpansionSet {

    private static final EighthEdition fINSTANCE =  new EighthEdition();

    public static EighthEdition getInstance() {
        return fINSTANCE;
    }

    private EighthEdition() {
        super("Eighth Edition", "8ED", "", "mage.sets.eighthedition", new GregorianCalendar(2003, 7, 28).getTime(), SetType.CORE);
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
    }

}
