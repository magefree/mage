package mage.sets;

import java.util.GregorianCalendar;
import mage.Constants.SetType;
import mage.cards.ExpansionSet;

public class FifthEdition extends ExpansionSet {

    private static final FifthEdition fINSTANCE =  new FifthEdition();

    public static FifthEdition getInstance() {
        return fINSTANCE;
    }

    private FifthEdition() {
        super("Fifth Edition", "5ED", "", "mage.sets.fifthedition", new GregorianCalendar(1997, 3, 1).getTime(), SetType.CORE);
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
    this.ratioBoosterMythic = 0;
    }

}