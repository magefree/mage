
package mage.filter.predicate.other;

import java.util.UUID;
import mage.cards.Card;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 *
 * @author North
 */
public class OwnerIdPredicate implements Predicate<Card> {

    private final UUID ownerId;

    public OwnerIdPredicate(UUID ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public boolean apply(Card input, Game game) {
        return ownerId.equals(input.getOwnerId());
    }

    @Override
    public String toString() {
        return "OwnerId(" + ownerId + ')';
    }
}
