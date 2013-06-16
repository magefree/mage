package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

import java.util.GregorianCalendar;

public class Darksteel extends ExpansionSet {

    private static final Darksteel fINSTANCE =  new Darksteel();

    public static Darksteel getInstance() {
        return fINSTANCE;
    }

    public Darksteel() {
        super("Darksteel", "DST", "mage.sets.darksteel", new GregorianCalendar(2004, 1, 6).getTime(), SetType.EXPANSION);
        this.blockName = "Mirrodin";
        this.parentSet = Mirrodin.getInstance();
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
    }
}
