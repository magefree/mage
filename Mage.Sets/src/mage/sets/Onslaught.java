package mage.sets;

import mage.Constants;
import mage.cards.ExpansionSet;

import java.util.GregorianCalendar;

public class Onslaught extends ExpansionSet {
    private static final Onslaught fINSTANCE =  new Onslaught();

    public static Onslaught getInstance() {
        return fINSTANCE;
    }

    private Onslaught() {
        super("Onslaught", "ONS", "", "mage.sets.onslaught", new GregorianCalendar(2002, 10, 7).getTime(), Constants.SetType.EXPANSION);
        this.blockName = "Onslaught";
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
    }
}