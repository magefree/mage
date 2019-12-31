

package mage.deck;

import mage.cards.decks.Constructed;

/**
 *
 * @author fireshoes
 */
public class AmonkhetBlock extends Constructed {

    public AmonkhetBlock() {
        super("Constructed - Amonkhet Block");
        setCodes.add(mage.sets.Amonkhet.getInstance().getCode());
        setCodes.add(mage.sets.HourOfDevastation.getInstance().getCode());
    }
}
