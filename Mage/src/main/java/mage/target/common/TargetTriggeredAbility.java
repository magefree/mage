package mage.target.common;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.FilterAbility;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.TargetObject;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Styxo
 */
public class TargetTriggeredAbility extends TargetObject {

    public TargetTriggeredAbility() {
        this.minNumberOfTargets = 1;
        this.maxNumberOfTargets = 1;
        this.zone = Zone.STACK;
        this.targetName = "target triggered ability you control";
    }

    public TargetTriggeredAbility(final TargetTriggeredAbility target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        // 114.4. A spell or ability on the stack is an illegal target for itself.
        if (source != null && source.getSourceId().equals(id)) {
            return false;
        }

        StackObject stackObject = game.getStack().getStackObject(id);
        return stackObject != null
                && stackObject.getStackAbility() instanceof TriggeredAbility
                && source != null
                && stackObject.getStackAbility().isControlledBy(source.getControllerId());
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        return canChoose(sourceControllerId, game);
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        for (StackObject stackObject : game.getStack()) {
            if (stackObject.getStackAbility() instanceof TriggeredAbility
                    && stackObject.getStackAbility().isControlledBy(sourceControllerId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        return possibleTargets(sourceControllerId, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        for (StackObject stackObject : game.getStack()) {
            if (stackObject.getStackAbility() instanceof TriggeredAbility
                    && stackObject.getStackAbility().isControlledBy(sourceControllerId)) {
                possibleTargets.add(stackObject.getStackAbility().getId());
            }
        }
        return possibleTargets;
    }

    @Override
    public TargetTriggeredAbility copy() {
        return new TargetTriggeredAbility(this);
    }

    @Override
    public Filter getFilter() {
        return new FilterAbility();
    }

}
