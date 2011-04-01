package mage.sets;

import mage.Constants;
import mage.cards.ExpansionSet;

import java.util.GregorianCalendar;

public class Guru extends ExpansionSet {
   private static final Guru fINSTANCE =  new Guru();

	public static Guru getInstance() {
		return fINSTANCE;
	}

    private Guru() {
        //TODO find correct release date, wiki don't known anything about this expansion
        super("Guru", "GURU", "", "mage.sets.guru", new GregorianCalendar(2000, 1, 1).getTime(), Constants.SetType.REPRINT);
    }
}
