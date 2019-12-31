package mage.deck;

import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Constructed;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author LevelX2
 */
public class ModernNoBannedList extends Constructed {

    public ModernNoBannedList() {
        super("Constructed - Modern - No Banned List");

        Date cutoff = new GregorianCalendar(2003, Calendar.JULY, 28).getTime(); // Eight edition release date
        for (ExpansionSet set : Sets.getInstance().values()) {
            if (set.getSetType().isModernLegal() && (set.getReleaseDate().after(cutoff) || set.getReleaseDate().equals(cutoff))) {
                setCodes.add(set.getCode());
            }
        }
    }
}
