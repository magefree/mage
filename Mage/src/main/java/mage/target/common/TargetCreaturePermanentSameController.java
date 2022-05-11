package mage.target.common;

import mage.abilities.Ability;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class TargetCreaturePermanentSameController extends TargetCreaturePermanent {

    public TargetCreaturePermanentSameController(int numTargets) {
        this(numTargets, StaticFilters.FILTER_PERMANENT_CREATURE);
    }

    public TargetCreaturePermanentSameController(int numTargets, FilterCreaturePermanent filter) {
        super(numTargets, numTargets, filter, false);
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
