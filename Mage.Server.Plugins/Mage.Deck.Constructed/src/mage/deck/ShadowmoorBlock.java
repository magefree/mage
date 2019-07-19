

package mage.deck;

import mage.cards.decks.Constructed;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ShadowmoorBlock extends Constructed {

    public ShadowmoorBlock() {
        super("Constructed - Shadowmoor Block");
        setCodes.add(mage.sets.Shadowmoor.getInstance().getCode());
        setCodes.add(mage.sets.Eventide.getInstance().getCode());
    }

}
