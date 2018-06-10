
package mage.deck;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Constructed;
import mage.constants.SetType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Standard extends Constructed {

    public Standard() {
        super("Constructed - Standard");
        GregorianCalendar current = new GregorianCalendar();
        List<ExpansionSet> sets = new ArrayList(Sets.getInstance().values());
        Collections.sort(sets, new Comparator<ExpansionSet>() {
            @Override
            public int compare(final ExpansionSet lhs, ExpansionSet rhs) {
                return lhs.getReleaseDate().after(rhs.getReleaseDate()) ? -1 : 1;
            }
        });
        int blocksAdded = 0;
        int blocksToAdd = 3;
        for (Iterator<ExpansionSet> iter = sets.iterator(); iter.hasNext() && blocksAdded < blocksToAdd;) {
            ExpansionSet set = iter.next();
            if (set.getSetType() == SetType.CORE || set.getSetType() == SetType.EXPANSION || set.getSetType() == SetType.SUPPLEMENTAL_STANDARD_LEGAL) {    // Still adding core sets because of Magic Origins

                setCodes.add(set.getCode());
                if (set.getReleaseDate().before(current.getTime()) // This stops spoiled sets from counting as "new" blocks
                        && set.getParentSet() == null
                        && set.getSetType() == SetType.EXPANSION) {
                    if (blocksAdded == 0 && !isFallBlock(set)) { // if the most current block is no fall block, 4 blocks are added
                        blocksToAdd++;
                    }
                    blocksAdded++;
                }
            }
        }
        banned.add("Attune with Aether"); // since 2018-01-15
        banned.add("Aetherworks Marvel");
        banned.add("Felidar Guardian");
        banned.add("Rampaging Ferocidon"); // since 2018-01-15
        banned.add("Ramunap Ruins"); // since 2018-01-15
        banned.add("Rogue Refiner"); // since 2018-01-15
        banned.add("Smuggler's Copter");
    }

    private static boolean isFallBlock(ExpansionSet set) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(set.getReleaseDate());
        // Sets from fall block are normally released in September and January
        return cal.get(Calendar.MONTH) > 7 || cal.get(Calendar.MONTH) < 2;
    }
}
