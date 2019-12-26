

package mage.deck;

import mage.cards.decks.Constructed;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class KhansOfTarkirBlock extends Constructed {

    public KhansOfTarkirBlock() {
        super("Constructed - Khans of Tarkir Block");
        setCodes.add(mage.sets.KhansOfTarkir.getInstance().getCode());
        setCodes.add(mage.sets.FateReforged.getInstance().getCode());
        setCodes.add(mage.sets.DragonsOfTarkir.getInstance().getCode());
    }

}
