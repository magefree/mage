package mage.deck;

import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Constructed;
import mage.sets.HistoricAnthology;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author mikalinn777
 *
 * Historic is a Magic The Gathering Arena format. https://mtg.gamepedia.com/Historic_(format)
 */
public class Historic extends Constructed {

    public Historic() {
        super("Constructed - Historic");

        Date cutoff = new GregorianCalendar(2017, Calendar.SEPTEMBER, 29).getTime(); // XLN release date
        for (ExpansionSet set : Sets.getInstance().values()) {
            if (set.getSetType().isStandardLegal() && (set.getReleaseDate().after(cutoff) || set.getReleaseDate().equals(cutoff))) {
                setCodes.add(set.getCode());
                setCodes.add(mage.sets.HistoricAnthology.getInstance().getCode());
            }
        }


        banned.add("Oko, Thief of Crowns");
        banned.add("Once Upon a Time");
        banned.add("Veil of Summer");
        banned.add("Nexus of Fate");
        banned.add("Winota, Joiner of Forces");
        banned.add("Fires of Invention");
        banned.add("Agent of Treachery");
    }
}
