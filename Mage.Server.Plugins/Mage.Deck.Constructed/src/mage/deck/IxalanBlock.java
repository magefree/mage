
package mage.deck;

import mage.cards.decks.Constructed;

/**
 *
 * @author LevelX2
 */
public class IxalanBlock extends Constructed {

    public IxalanBlock() {
        super("Constructed - Ixalan Block");
        setCodes.add(mage.sets.Ixalan.getInstance().getCode());
        setCodes.add(mage.sets.RivalsOfIxalan.getInstance().getCode());
    }
}
