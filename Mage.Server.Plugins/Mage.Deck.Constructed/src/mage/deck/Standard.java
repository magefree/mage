package mage.deck;

import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Constructed;
import mage.constants.SetType;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class Standard extends Constructed {

    public Standard() {
        super("Constructed - Standard");

        setCodes.addAll(makeLegalSets());

        banned.add("The Meathook Massacre");
        banned.add("Fable of the Mirror-Breaker");
        banned.add("Reckoner Bankbuster");
        banned.add("Invoke Despair");
    }

    private static boolean isFallSet(ExpansionSet set) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(set.getReleaseDate());
        // Fall sets are normally released during or after September and before November
        return set.getSetType() == SetType.EXPANSION
                && Calendar.SEPTEMBER <= cal.get(Calendar.MONTH)
                && cal.get(Calendar.MONTH) < Calendar.NOVEMBER;
    }

    static List<String> makeLegalSets() {
        GregorianCalendar current = new GregorianCalendar();
        // Get the second most recent fall set that's been released.
        Date earliestDate = Sets
                .getInstance()
                .values()
                .stream()
                .filter(set -> !set.getReleaseDate().after(current.getTime()))
                .filter(Standard::isFallSet)
                .sorted(ExpansionSet.getComparator())
                .skip(1)
                .findFirst()
                .get()
                .getReleaseDate();
        return Sets.getInstance()
                .values()
                .stream()
                .filter(set -> set.getSetType().isStandardLegal())
                .filter(set -> !set.getReleaseDate().before(earliestDate))
//                .filter(set -> !set.getReleaseDate().after(current.getTime())) // no after date restrict for early tests and beta
                .map(ExpansionSet::getCode)
                .collect(Collectors.toList());
    }
}
