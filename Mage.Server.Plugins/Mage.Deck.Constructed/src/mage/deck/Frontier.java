package mage.deck;

import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Constructed;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author fireshoes
 */
public class Frontier extends Constructed {

    public Frontier() {
        super("Constructed - Frontier");

        Date cutoff = new GregorianCalendar(2014, Calendar.JULY, 18).getTime(); // M15 release date
        for (ExpansionSet set : Sets.getInstance().values()) {
            if (set.getSetType().isStandardLegal() && (set.getReleaseDate().after(cutoff) || set.getReleaseDate().equals(cutoff))) {
                setCodes.add(set.getCode());
            }
        }

        // Future banlist
        // banned.add("");
    }
}
