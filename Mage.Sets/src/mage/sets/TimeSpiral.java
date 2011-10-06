package mage.sets;

import mage.Constants;
import mage.cards.ExpansionSet;

import java.util.GregorianCalendar;

public class TimeSpiral extends ExpansionSet {

	private static final TimeSpiral fINSTANCE =  new TimeSpiral();

	public static TimeSpiral getInstance() {
		return fINSTANCE;
	}

	private TimeSpiral() {
		super("Time Spiral", "TSP", "", "mage.sets.timespiral", new GregorianCalendar(2006, 9, 9).getTime(), Constants.SetType.EXPANSION);
		this.blockName = "Time Spiral";
		this.hasBoosters = true;
		this.numBoosterLands = 1;
		this.numBoosterCommon = 11;
		this.numBoosterUncommon = 3;
		this.numBoosterRare = 1;
		this.ratioBoosterMythic = 0;
	}
}
