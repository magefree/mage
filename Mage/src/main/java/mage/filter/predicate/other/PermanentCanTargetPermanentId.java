
package mage.filter.predicate.other;

import java.util.UUID;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;

/**
 *
 * @author LevelIX
 */
public class PermanentCanTargetPermanentId implements Predicate<Permanent> {

    private final UUID toBeCheckedPermanentId;

    public PermanentCanTargetPermanentId(UUID toBeCheckedPermanentId) {
        this.toBeCheckedPermanentId = toBeCheckedPermanentId;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        for (Target target : input.getSpellAbility().getTargets()) {
            if (target.canTarget(toBeCheckedPermanentId, input.getSpellAbility(), game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "PermanentCanTargetPermanentId(" + toBeCheckedPermanentId + ')';
    }
}