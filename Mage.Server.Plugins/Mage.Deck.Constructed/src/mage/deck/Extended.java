package mage.deck;

import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Constructed;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class Extended extends Constructed {

    public Extended() {
        super("Constructed - Extended");
        GregorianCalendar current = new GregorianCalendar();
        GregorianCalendar cutoff;
        if (current.get(Calendar.MONTH) > 9) {
            cutoff = new GregorianCalendar(current.get(Calendar.YEAR) - 3, Calendar.SEPTEMBER, 1);
        } else {
            cutoff = new GregorianCalendar(current.get(Calendar.YEAR) - 4, Calendar.SEPTEMBER, 1);
        }
        for (ExpansionSet set : Sets.getInstance().values()) {
            if (set.getSetType().isStandardLegal() && set.getReleaseDate().after(cutoff.getTime())) {
                setCodes.add(set.getCode());
            }
        }

        banned.add("Jace, the Mind Sculptor");
        banned.add("Mental Misstep");
        banned.add("Ponder");
        banned.add("Preordain");
        banned.add("Stoneforge Mystic");

    }
}
