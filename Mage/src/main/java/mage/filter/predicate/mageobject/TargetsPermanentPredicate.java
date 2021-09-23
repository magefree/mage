package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.abilities.Mode;
import mage.filter.FilterPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.Target;

import java.util.UUID;

/**
 * @author LoneFox
 */
public class TargetsPermanentPredicate implements ObjectSourcePlayerPredicate<MageObject> {

    private final FilterPermanent targetFilter;

    public TargetsPermanentPredicate(FilterPermanent targetFilter) {
        this.targetFilter = targetFilter;
    }

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        StackObject object = game.getStack().getStackObject(input.getObject().getId());
        if (object != null) {
            for (UUID modeId : object.getStackAbility().getModes().getSelectedModes()) {
                Mode mode = object.getStackAbility().getModes().get(modeId);
                for (Target target : mode.getTargets()) {
                    if (target.isNotTarget()) {
                        continue;
                    }
                    for (UUID targetId : target.getTargets()) {
                        Permanent permanent = game.getPermanentOrLKIBattlefield(targetId);
                        if (targetFilter.match(permanent, input.getSourceId(), input.getPlayerId(), game)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "that targets " + targetFilter.getMessage();
    }
}
