

package mage.deck;

import mage.cards.decks.Constructed;

/**
 *
 * @author fireshoes
 */
public class KaladeshBlock extends Constructed {

    public KaladeshBlock() {
        super("Constructed - Kaladesh Block");
        setCodes.add(mage.sets.Kaladesh.getInstance().getCode());
        setCodes.add(mage.sets.AetherRevolt.getInstance().getCode());
    }
}
