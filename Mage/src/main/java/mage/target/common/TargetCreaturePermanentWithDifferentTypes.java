
package mage.target.common;

import mage.abilities.Ability;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class TargetCreaturePermanentWithDifferentTypes extends TargetCreaturePermanent {

    public TargetCreaturePermanentWithDifferentTypes(int minNumTargets, int maxNumTargets, FilterCreaturePermanent filter, boolean notTarget) {
        super(minNumTargets, maxNumTargets, filter, notTarget);
    }

    protected TargetCreaturePermanentWithDifferentTypes(final TargetCreaturePermanentWithDifferentTypes target) {
        super(target);
    }

    @Override
    public TargetCreaturePermanentWithDifferentTypes copy() {
        return new TargetCreaturePermanentWithDifferentTypes(this);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (super.canTarget(controllerId, id, source, game)) {
            Permanent creature = game.getPermanent(id);
            if (creature != null) {
                for (Object object : getTargets()) {
                    UUID targetId = (UUID) object;
                    Permanent selectedCreature = game.getPermanent(targetId);
                    if (selectedCreature != null
                            && !creature.getId().equals(selectedCreature.getId())) {
                        if (creature.shareCreatureTypes(game, selectedCreature)) {
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
}
