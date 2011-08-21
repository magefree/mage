package mage.sets;

import mage.Constants;
import mage.cards.ExpansionSet;

import java.util.GregorianCalendar;

public class Apocalypse extends ExpansionSet {
    private static final Apocalypse fINSTANCE =  new Apocalypse();

	public static Apocalypse getInstance() {
		return fINSTANCE;
	}

    private Apocalypse() {
        super("Apocalypse", "APC", "", "mage.sets.apocalypse", new GregorianCalendar(2009, 5, 1).getTime(), Constants.SetType.EXPANSION);
        this.blockName = "Invasion";
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
    }
}
