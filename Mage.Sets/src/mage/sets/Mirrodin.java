package mage.sets;

import mage.Constants;
import mage.cards.ExpansionSet;

import java.util.GregorianCalendar;

public class Mirrodin extends ExpansionSet {
    private static final Mirrodin fINSTANCE =  new Mirrodin();

    public static Mirrodin getInstance() {
        return fINSTANCE;
    }

    private Mirrodin() {
        super("Mirrodin", "MRD", "", "mage.sets.mirrodin", new GregorianCalendar(2003, 9, 2).getTime(), Constants.SetType.EXPANSION);
        this.blockName = "Mirrodin";
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
    }
}
