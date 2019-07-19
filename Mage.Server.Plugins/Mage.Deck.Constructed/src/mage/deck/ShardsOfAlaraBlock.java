

package mage.deck;

import mage.cards.decks.Constructed;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ShardsOfAlaraBlock extends Constructed {

    public ShardsOfAlaraBlock() {
        super("Constructed - Shards of Alara Block");
        setCodes.add(mage.sets.ShardsOfAlara.getInstance().getCode());
        setCodes.add(mage.sets.Conflux.getInstance().getCode());
        setCodes.add(mage.sets.AlaraReborn.getInstance().getCode());
    }

}
