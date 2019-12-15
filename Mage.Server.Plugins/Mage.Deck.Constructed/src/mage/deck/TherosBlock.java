

package mage.deck;

import mage.cards.decks.Constructed;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TherosBlock extends Constructed {

    public TherosBlock() {
        super("Constructed - Theros Block");
        setCodes.add(mage.sets.Theros.getInstance().getCode());
        setCodes.add(mage.sets.BornOfTheGods.getInstance().getCode());
        setCodes.add(mage.sets.JourneyIntoNyx.getInstance().getCode());
    }

}
