package mage.deck;

import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Constructed;

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

        banned.add("Abuelo's Awakening");
        banned.add("Cori-Steel Cutter");
        banned.add("Heartfire Hero");
        banned.add("Hopeless Nightmare");
        banned.add("Monstrous Rage");
        banned.add("This Town Ain't Big Enough");
        banned.add("Up the Beanstalk");
    }

    static List<String> makeLegalSets() {
        GregorianCalendar current = new GregorianCalendar();
        // Get the third most recent fall set that's been released.
        Date earliestDate = Sets
                .getInstance()
                .values()
                .stream()
                .filter(set -> !set.getReleaseDate().after(current.getTime()))
                .filter(ExpansionSet::isRotationSet)
                .sorted(ExpansionSet.getComparator())
                .skip(2)
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
