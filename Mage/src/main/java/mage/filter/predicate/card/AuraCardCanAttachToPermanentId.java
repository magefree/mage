package mage.filter.predicate.card;

import mage.cards.Card;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
// Use this predicate if a aura card comes into play attached to a permanent without targeting
public class AuraCardCanAttachToPermanentId implements Predicate<Card> {

    private final UUID toBeCheckedPermanentId;

    public AuraCardCanAttachToPermanentId(UUID toBeCheckedPermanentId) {
        this.toBeCheckedPermanentId = toBeCheckedPermanentId;
    }

    @Override
    public boolean apply(Card input, Game game) {
         Permanent permanent = game.getPermanent(toBeCheckedPermanentId);
        if (permanent == null || input == null || !input.isEnchantment(game)) {
            return false;
        }
        return input
                .getSpellAbility()
                .getTargets()
                .stream()
                .anyMatch(target -> target.getFilter().match(permanent, game));
    }

    @Override
    public String toString() {
        return "AuraCardCanAttachToPermanentId(" + toBeCheckedPermanentId + ')';
    }
}
