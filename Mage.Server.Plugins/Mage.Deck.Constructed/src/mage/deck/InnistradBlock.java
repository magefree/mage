

package mage.deck;

import mage.cards.decks.Constructed;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class InnistradBlock extends Constructed {

    public InnistradBlock() {
        super("Constructed - Innistrad Block");
        setCodes.add(mage.sets.Innistrad.getInstance().getCode());
        setCodes.add(mage.sets.DarkAscension.getInstance().getCode());
        setCodes.add(mage.sets.AvacynRestored.getInstance().getCode());
    }

}
