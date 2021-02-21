

package mage.filter.predicate.card;

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
public class AuraCardCanAttachToLKIPermanentId implements Predicate<Card> {

    private final UUID toBeCheckedLKIPermanentId;

    public AuraCardCanAttachToLKIPermanentId(UUID toBeCheckedLKIPermanentId) {
        this.toBeCheckedLKIPermanentId = toBeCheckedLKIPermanentId;
    }

    @Override
    public boolean apply(Card input, Game game) {
        final Permanent permanent = game.getPermanentOrLKIBattlefield(toBeCheckedLKIPermanentId);
        Filter filter;
        for (Target target : input.getSpellAbility().getTargets()) {
            filter = target.getFilter();
            if (filter.match(permanent, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "AuraCardCanAttachToLKIPermanentId(" + toBeCheckedLKIPermanentId + ')';
    }
}