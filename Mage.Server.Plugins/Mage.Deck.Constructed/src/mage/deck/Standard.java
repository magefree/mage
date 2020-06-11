package mage.deck;

import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Constructed;
import mage.constants.SetType;

import java.util.*;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class Standard extends Constructed {

    public Standard() {
        super("Constructed - Standard");

        setCodes.addAll(makeLegalSets());

        banned.add("Agent of Treachery");
        banned.add("Field of the Dead");
        banned.add("Fires of Invention");
        banned.add("Oko, Thief of Crowns");
        banned.add("Once Upon a Time");
        banned.add("Veil of Summer");
    }

    private static boolean isFallSet(ExpansionSet set) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(set.getReleaseDate());
        // Fall sets are normally released during or after September
        return set.getSetType() == SetType.EXPANSION && (cal.get(Calendar.MONTH) > 7);
    }

    static List<String> makeLegalSets() {
        List<String> codes = new ArrayList<>();
        GregorianCalendar current = new GregorianCalendar();
        List<ExpansionSet> sets = new ArrayList(Sets.getInstance().values());
        Collections.sort(sets, new Comparator<ExpansionSet>() {
            @Override
            public int compare(final ExpansionSet lhs, ExpansionSet rhs) {
                return lhs.getReleaseDate().after(rhs.getReleaseDate()) ? -1 : 1;
            }
        });
        int fallSetsAdded = 0;
        Date earliestDate = null;
        // Get the second most recent fall set that's been released.
        for (ExpansionSet set : sets) {
            if (set.getReleaseDate().after(current.getTime())) {
                continue;
            }
            if (isFallSet(set)) {
                fallSetsAdded++;
                if (fallSetsAdded == 2) {
                    earliestDate = set.getReleaseDate();
                    break;
                }
            }
        }

        for (ExpansionSet set : sets) {
            boolean isDateCompatible = earliestDate != null && !set.getReleaseDate().before(earliestDate) /*!set.getReleaseDate().after(current.getTime())*/; // no after date restrict for early tests and beta
            if (set.getSetType().isStandardLegal() && isDateCompatible) {
                codes.add(set.getCode());
            }
        }
        return codes;
    }
}
