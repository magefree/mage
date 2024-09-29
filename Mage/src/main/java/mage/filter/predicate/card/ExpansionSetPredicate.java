
package mage.filter.predicate.card;

import mage.cards.Card;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 *
 * @author North
 */
public class ExpansionSetPredicate implements Predicate<Card> {

    private final String setCode;

    /**
     * Per CR 206.1, the expansion symbol has no effect on game play.
     * Use only for utility or silver-bordered / acorn mechanics.
     */
    public ExpansionSetPredicate(String setCode) {
        this.setCode = setCode;
    }

    @Override
    public boolean apply(Card input, Game game) {
        return input.getExpansionSetCode().equals(setCode);
    }

    @Override
    public String toString() {
        return "ExpansionSet(" + setCode + ')';
    }
}
