package mage.sets;

import java.util.GregorianCalendar;
import mage.Constants.SetType;
import mage.cards.ExpansionSet;

public class NinthEdition extends ExpansionSet {

	private static final NinthEdition fINSTANCE =  new NinthEdition();

	public static NinthEdition getInstance() {
		return fINSTANCE;
	}

	private NinthEdition() {
		super("Ninth Edition", "9ED", "", "mage.sets.ninthedition", new GregorianCalendar(2005, 7, 29).getTime(), SetType.CORE);
		this.hasBoosters = true;
		this.numBoosterLands = 1;
		this.numBoosterCommon = 10;
		this.numBoosterUncommon = 3;
		this.numBoosterRare = 1;
		this.ratioBoosterMythic = 0;
	}

}