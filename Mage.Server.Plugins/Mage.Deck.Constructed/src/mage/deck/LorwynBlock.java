
package mage.deck;

import mage.cards.decks.Constructed;

/**
 *
 * @author LevelX2
 */
public class LorwynBlock extends Constructed {

    public LorwynBlock() {
        super("Constructed - Lorwyn Block");
        setCodes.add(mage.sets.Lorwyn.getInstance().getCode());
        setCodes.add(mage.sets.Morningtide.getInstance().getCode());
    }
}
