
package mage.filter.predicate.permanent;

import java.util.UUID;
import mage.filter.Filter;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;

/**
 *
 * @author jeffwadsworth
 */
// // Use this predicate if a aura permanent is to be attached to a permanent without targeting

public class AuraPermanentCanAttachToPermanentId implements Predicate<Permanent> {

    private final UUID toBeCheckedPermanentId;

    public AuraPermanentCanAttachToPermanentId(UUID toBeCheckedPermanentId) {
        this.toBeCheckedPermanentId = toBeCheckedPermanentId;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        final Permanent permanent = game.getPermanent(toBeCheckedPermanentId);
        Filter filter;
        if(input.getSpellAbility() != null && input.getSpellAbility().getTargets() != null) {
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
        return "AuraPermanentCanAttachToPermanentId(" + toBeCheckedPermanentId + ')';
    }
}