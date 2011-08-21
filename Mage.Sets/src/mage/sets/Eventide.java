package mage.sets;

import mage.Constants;
import mage.cards.ExpansionSet;

import java.util.GregorianCalendar;

public class Eventide extends ExpansionSet {
    private static final Eventide fINSTANCE =  new Eventide();

	public static Eventide getInstance() {
		return fINSTANCE;
	}

    private Eventide() {
        super("Eventide", "EVE", "", "mage.sets.eventide", new GregorianCalendar(2008, 6, 25).getTime(), Constants.SetType.EXPANSION);
        this.blockName = "Shadowmoor";
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
    }
}
