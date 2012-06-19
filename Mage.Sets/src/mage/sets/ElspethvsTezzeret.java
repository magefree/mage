package mage.sets;

import mage.Constants;
import mage.cards.ExpansionSet;

import java.util.GregorianCalendar;

public class ElspethvsTezzeret extends ExpansionSet {
    private static final ElspethvsTezzeret fINSTANCE =  new ElspethvsTezzeret();

    public static ElspethvsTezzeret getInstance() {
        return fINSTANCE;
    }

    private ElspethvsTezzeret() {
        super("Duel Decks: Elspeth vs. Tezzeret", "DDF", "", "mage.sets.elspethvstezzeret", new GregorianCalendar(2010, 8, 3).getTime(), Constants.SetType.REPRINT);
    }
}
