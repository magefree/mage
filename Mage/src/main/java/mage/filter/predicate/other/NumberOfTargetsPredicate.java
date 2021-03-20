package mage.filter.predicate.other;

import java.util.UUID;
import mage.abilities.Mode;
import mage.filter.predicate.Predicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.Target;

/**
 *
 * @author jeffwadsworth
 */
public class NumberOfTargetsPredicate implements Predicate<Controllable> {

    private final int targets;

    public NumberOfTargetsPredicate(int targets) {
        this.targets = targets;
    }

    @Override
    public boolean apply(Controllable input, Game game) {
        StackObject stackObject = game.getState().getStack().getStackObject(input.getId());
        if (stackObject != null) {
            int numberOfTargets = 0;
            for (UUID modeId : stackObject.getStackAbility().getModes().getSelectedModes()) {
                Mode mode = stackObject.getStackAbility().getModes().get(modeId);
                for (Target target : mode.getTargets()) {
                    numberOfTargets += target.getTargets().size();
                }
            }
            if (numberOfTargets == targets) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Number of targets(" + targets + ')';
    }
}
