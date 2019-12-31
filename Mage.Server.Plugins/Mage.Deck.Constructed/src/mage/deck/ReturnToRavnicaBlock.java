

package mage.deck;

import mage.cards.decks.Constructed;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ReturnToRavnicaBlock extends Constructed {

    public ReturnToRavnicaBlock() {
        super("Constructed - Return to Ravnica Block");
        setCodes.add(mage.sets.ReturnToRavnica.getInstance().getCode());
        setCodes.add(mage.sets.Gatecrash.getInstance().getCode());
        setCodes.add(mage.sets.DragonsMaze.getInstance().getCode());
    }

}
