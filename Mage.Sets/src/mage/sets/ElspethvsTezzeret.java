package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

import java.util.GregorianCalendar;

public class ElspethvsTezzeret extends ExpansionSet {
    private static final ElspethvsTezzeret fINSTANCE =  new ElspethvsTezzeret();

    public static ElspethvsTezzeret getInstance() {
        return fINSTANCE;
    }

    private ElspethvsTezzeret() {
        super("Duel Decks: Elspeth vs. Tezzeret", "DDF", "mage.sets.elspethvstezzeret", new GregorianCalendar(2010, 8, 3).getTime(), SetType.REPRINT);
        this.hasBasicLands = false;
    }
}
