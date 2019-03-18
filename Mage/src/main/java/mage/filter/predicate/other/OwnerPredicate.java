
package mage.filter.predicate.other;

import java.util.UUID;
import mage.cards.Card;
import mage.constants.TargetController;
import mage.filter.predicate.ObjectPlayer;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.game.Game;

/**
 *
 * @author North
 */
public class OwnerPredicate implements ObjectPlayerPredicate<ObjectPlayer<Card>> {

    private final TargetController targetOwner;

    public OwnerPredicate(TargetController targetOwner) {
        this.targetOwner = targetOwner;
    }

    @Override
    public boolean apply(ObjectPlayer<Card> input, Game game) {
        Card card = input.getObject();
        UUID playerId = input.getPlayerId();
        if (card == null || playerId == null) {
            return false;
        }

        switch (targetOwner) {
            case YOU:
                if (card.isOwnedBy(playerId)) {
                    return true;
                }
                break;
            case OPPONENT:
                if (!card.isOwnedBy(playerId)
                        && game.getPlayer(playerId).hasOpponent(card.getOwnerId(), game)) {
                    return true;
                }
                break;
            case NOT_YOU:
                if (!card.isOwnedBy(playerId)) {
                    return true;
                }
                break;
            case ANY:
                return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "Owner(" + targetOwner + ')';
    }
}
