package mage.sets;

import mage.Constants;
import mage.cards.ExpansionSet;

import java.util.GregorianCalendar;

public class Darksteel extends ExpansionSet {

    private static final Darksteel fINSTANCE =  new Darksteel();

	public static Darksteel getInstance() {
		return fINSTANCE;
	}

    public Darksteel() {
        super("Darksteel", "DST", "", "mage.sets.darksteel", new GregorianCalendar(2004, 1, 6).getTime(), Constants.SetType.EXPANSION);
        this.blockName = "Mirrodin";
        this.parentSet = Mirrodin.getInstance();
		this.hasBoosters = true;
		this.numBoosterLands = 1;
		this.numBoosterCommon = 10;
		this.numBoosterUncommon = 3;
		this.numBoosterRare = 1;
		this.ratioBoosterMythic = 0;
    }
}
