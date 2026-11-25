

package mage.filter.predicate.card;

import java.util.UUID;
import mage.cards.Card;
import mage.filter.Filter;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.util.CardUtil;

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
        // double faced cards: a main card has no own spell ability, must check the side that enters the battlefield
        for (Target target : CardUtil.getDefaultCardSideForBattlefield(game, input).getSpellAbility().getTargets()) {
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