
package mage.target.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class TargetCreaturePermanentSameController extends TargetCreaturePermanent {

    public TargetCreaturePermanentSameController(int minNumTargets, int maxNumTargets, FilterCreaturePermanent filter, boolean notTarget) {
        super(minNumTargets, maxNumTargets, filter, notTarget);
    }

    public TargetCreaturePermanentSameController(final TargetCreaturePermanentSameController target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (super.canTarget(controllerId, id, source, game)) {
            Permanent firstTargetPermanent = game.getPermanent(id);
            if (firstTargetPermanent != null) {
                for (Object object : getTargets()) {
                    UUID targetId = (UUID) object;
                    Permanent targetPermanent = game.getPermanent(targetId);
                    if (targetPermanent != null) {
                        if (!firstTargetPermanent.getId().equals(targetPermanent.getId())) {
                            if (!firstTargetPermanent.isControlledBy(targetPermanent.getOwnerId())) {
                                return false;
                            }
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public TargetCreaturePermanentSameController copy() {
        return new TargetCreaturePermanentSameController(this);
    }
}
