package mage.filter.predicate.other;

import java.util.UUID;
import mage.cards.Card;
import mage.filter.Filter;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;

/**
 *
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
        final Permanent permanent = game.getPermanent(toBeCheckedPermanentId);
        Filter filter;
        if (permanent != null
                && input != null
                && input.isEnchantment()) {
            for (Target target : input.getSpellAbility().getTargets()) {
                filter = target.getFilter();
                if (filter.match(permanent, game)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "AuraCardCanAttachToPermanentId(" + toBeCheckedPermanentId + ')';
    }
}
