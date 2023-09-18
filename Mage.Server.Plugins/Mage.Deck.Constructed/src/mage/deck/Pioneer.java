package mage.deck;

import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Constructed;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author TheElk801
 */
public class Pioneer extends Constructed {

    public Pioneer() {
        super("Constructed - Pioneer");

        Date cutoff = new GregorianCalendar(2012, Calendar.OCTOBER, 5).getTime(); // RTR release date
        for (ExpansionSet set : Sets.getInstance().values()) {
            if (set.getSetType().isStandardLegal() && (set.getReleaseDate().after(cutoff) || set.getReleaseDate().equals(cutoff))) {
                setCodes.add(set.getCode());
            }
        }

        banned.add("Bloodstained Mire");
        banned.add("Flooded Strand");
        banned.add("Polluted Delta");
        banned.add("Windswept Heath");
        banned.add("Wooded Foothills");
        banned.add("Balustrade Spy");
        banned.add("Felidar Guardian");
        banned.add("Field of the Dead");
        banned.add("Inverter of Truth");
        banned.add("Kethis, the Hidden Hand");
        banned.add("Leyline of Abundance");
        banned.add("Lurrus of the Dream-Den");
        banned.add("Nexus of Fate");
        banned.add("Oko, Thief of Crowns");
        banned.add("Once Upon a Time");
        banned.add("Smuggler's Copter");
        banned.add("Teferi, Time Raveler");
        banned.add("Undercity Informer");
        banned.add("Underworld Breach");
        banned.add("Uro, Titan of Nature's Wrath");
        banned.add("Veil of Summer");
        banned.add("Walking Ballista");
        banned.add("Wilderness Reclamation");
    }
}
