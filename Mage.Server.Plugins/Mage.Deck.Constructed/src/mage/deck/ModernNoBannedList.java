

package mage.deck;

import java.util.Date;
import java.util.GregorianCalendar;

import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Constructed;
import mage.constants.SetType;

/**
 *
 * @author LevelX2
 */
public class ModernNoBannedList extends Constructed {

    public ModernNoBannedList() {
        super("Constructed - Modern - No Banned List");

        Date cutoff = new GregorianCalendar(2003, 6, 28).getTime(); // Eight edition release date
        for (ExpansionSet set : Sets.getInstance().values()) {
            if ((set.getReleaseDate().after(cutoff) || set.getReleaseDate().equals(cutoff))
                    && (set.getSetType() == SetType.CORE || set.getSetType() == SetType.EXPANSION)) {
                setCodes.add(set.getCode());
            }
        }
    }
}
