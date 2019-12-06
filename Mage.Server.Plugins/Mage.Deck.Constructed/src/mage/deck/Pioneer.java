package mage.deck;

import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Constructed;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author TheElk801
 */
public class Pioneer extends Constructed {

    public Pioneer() {
        super("Constructed - Pioneer");

        Date cutoff = new GregorianCalendar(2012, 10, 5).getTime(); // RTR release date
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
        banned.add("Felidar Guardian");
        banned.add("Field of the Dead");
        banned.add("Leyline of Abundance");
        banned.add("Oath of Nissa");
        banned.add("Once Upon a Time");
        banned.add("Smuggler's Copter");
        banned.add("Veil of Summer");
    }
}
