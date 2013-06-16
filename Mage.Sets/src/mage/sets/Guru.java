package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

import java.util.GregorianCalendar;

public class Guru extends ExpansionSet {
   private static final Guru fINSTANCE =  new Guru();

    public static Guru getInstance() {
        return fINSTANCE;
    }

    private Guru() {
        //TODO find correct release date, wiki don't known anything about this expansion
        super("Guru", "GUR", "mage.sets.guru", new GregorianCalendar(1990, 1, 2).getTime(), SetType.REPRINT);
    }
}
