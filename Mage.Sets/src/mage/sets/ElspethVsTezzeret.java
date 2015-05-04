package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

import java.util.GregorianCalendar;

public class ElspethVsTezzeret extends ExpansionSet {
    private static final ElspethVsTezzeret fINSTANCE =  new ElspethVsTezzeret();

    public static ElspethVsTezzeret getInstance() {
        return fINSTANCE;
    }

    private ElspethVsTezzeret() {
        super("Duel Decks: Elspeth vs. Tezzeret", "DDF", "mage.sets.elspethvstezzeret", new GregorianCalendar(2010, 8, 3).getTime(), SetType.SUPPLEMENTAL);
        this.blockName = "Duel Decks";
        this.hasBasicLands = false;
    }
}
