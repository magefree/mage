
package mage.filter.predicate.card;

import java.util.UUID;
import mage.cards.Card;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.target.Target;

/**
 *
 * @author LevelIX
 */
public class CardCanTargetPermanentId implements Predicate<Card> {

    private final UUID toBeCheckedPermanentId;

    public CardCanTargetPermanentId(UUID toBeCheckedPermanentId) {
        this.toBeCheckedPermanentId = toBeCheckedPermanentId;
    }

    @Override
    public boolean apply(Card input, Game game) {
        for (Target target : input.getSpellAbility().getTargets()) {
            if (target.canTarget(toBeCheckedPermanentId, input.getSpellAbility(), game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "CardCanTargetPermanentId(" + toBeCheckedPermanentId + ')';
    }
}