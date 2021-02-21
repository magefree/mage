
package mage.filter.predicate.card;

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
    public boolean apply(Card card, Game game) {
        return card.isOwnedBy(ownerId);
    }

    @Override
    public String toString() {
        return "OwnerId(" + ownerId + ')';
    }
}
