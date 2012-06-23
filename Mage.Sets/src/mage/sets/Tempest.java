package mage.sets;

import mage.Constants;
import mage.cards.ExpansionSet;

import java.util.GregorianCalendar;

public class Tempest extends ExpansionSet {
    private static final Tempest fINSTANCE =  new Tempest();

    public static Tempest getInstance() {
        return fINSTANCE;
    }

    private Tempest() {
        super("Tempest", "TMP", "", "mage.sets.tempest", new GregorianCalendar(1997, 9, 1).getTime(), Constants.SetType.EXPANSION);
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
    }
}
