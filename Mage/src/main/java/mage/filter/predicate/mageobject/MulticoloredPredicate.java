
package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.cards.SplitCardHalf;
import mage.constants.Zone;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 *
 * @author jeffwadsworth
 */
public class MulticoloredPredicate implements Predicate<MageObject> {

    @Override
    public boolean apply(MageObject input, Game game) {
        // 708.3. Each split card that consists of two halves with different colored mana symbols in their mana costs
        // is a multicolored card while it's not a spell on the stack. While it's a spell on the stack, it's only the
        // color or colors of the half or halves being cast. #
        if (input instanceof SplitCardHalf && game.getState().getZone(input.getId()) != Zone.STACK) {
            return 1 < ((SplitCardHalf) input).getMainCard().getColor(game).getColorCount();
        } else {
            return 1 < input.getColor(game).getColorCount();
        }
    }

    @Override
    public String toString() {
        return "Multicolored";
    }
}
