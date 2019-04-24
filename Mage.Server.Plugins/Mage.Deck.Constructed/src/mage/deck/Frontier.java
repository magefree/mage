
package mage.deck;

import java.util.Date;
import java.util.GregorianCalendar;
import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Constructed;
import mage.constants.SetType;

/**
 *
 * @author fireshoes
 */
public class Frontier extends Constructed {

    public Frontier() {
        super("Constructed - Frontier");

        Date cutoff = new GregorianCalendar(2014, 6, 18).getTime(); // M15 release date
        for (ExpansionSet set : Sets.getInstance().values()) {
            if ((set.getReleaseDate().after(cutoff) || set.getReleaseDate().equals(cutoff))
                    && (set.getSetType() == SetType.CORE || set.getSetType() == SetType.EXPANSION)) {
                setCodes.add(set.getCode());
            }
        }

        // Future banlist
        // banned.add("");
    }
}
