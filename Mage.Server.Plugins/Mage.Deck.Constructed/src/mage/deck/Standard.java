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

        banned.add("Rampaging Ferocidon"); // since 2018-01-15
    }

    private static boolean isFallSet(ExpansionSet set) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(set.getReleaseDate());
        // Fall sets are normally released during or after September
        return set.getSetType() == SetType.EXPANSION
                && (cal.get(Calendar.MONTH) > 7);
    }

    public static List<String> makeLegalSets() {
        List<String> codes = new ArrayList();
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
        // Get all sets released on or after the second most recent fall set's release
        for (ExpansionSet set : sets) {
            if ((set.getSetType() == SetType.CORE
                    || set.getSetType() == SetType.EXPANSION
                    || set.getSetType() == SetType.SUPPLEMENTAL_STANDARD_LEGAL)
                    && !set.getReleaseDate().before(earliestDate)) {
//                    && !set.getReleaseDate().after(current.getTime()))) {
                codes.add(set.getCode());
            }
        }
        return codes;
    }
}
