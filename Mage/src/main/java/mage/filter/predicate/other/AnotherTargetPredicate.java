
package mage.filter.predicate.other;

import java.util.UUID;
import mage.MageItem;
import mage.abilities.Mode;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.Target;

/**
 * All targets that are already selected in other target definitions of the
 * source are omitted To use this predicate you have to set the targetTag of all
 * targets involved in the card constructor to a unique value (e.g. using 1,2,3
 * for three targets)
 *
 * @author LevelX2
 */
public class AnotherTargetPredicate implements ObjectSourcePlayerPredicate<MageItem> {

    private final int targetTag;
    private final boolean crossModalCheck;

    /**
     *
     * @param targetTag tag of the target the filter belongs to
     */
    public AnotherTargetPredicate(int targetTag) {
        this(targetTag, false);
    }

    public AnotherTargetPredicate(int targetTag, boolean crossModalCheck) {
        this.targetTag = targetTag;
        this.crossModalCheck = crossModalCheck;
    }

    @Override
    public boolean apply(ObjectSourcePlayer<MageItem> input, Game game) {
        StackObject source = game.getStack().getStackObject(input.getSourceId());
        if (source != null && source.getStackAbility().getTargets() != null) {
            if (crossModalCheck) {
                for (UUID modeId : source.getStackAbility().getModes().getSelectedModes()) {
                    Mode mode = source.getStackAbility().getModes().get(modeId);
                    for (Target target : mode.getTargets()) {
                        if (target.getTargetTag() > 0 // target is included in the target group to check
                                && target.getTargetTag() != targetTag // it's not the target of this predicate
                                && target.getTargets().contains(input.getObject().getId())) { // if the uuid already is used for another target in the group it's not allowed here
                            return false;
                        }
                    }
                }
            } else {
                for (Target target : source.getStackAbility().getTargets()) {
                    if (target.getTargetTag() > 0 // target is included in the target group to check
                            && target.getTargetTag() != targetTag // it's not the target of this predicate
                            && target.getTargets().contains(input.getObject().getId())) { // if the uuid already is used for another target in the group it's not allowed here
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Another target";
    }
}
