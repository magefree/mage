package mage.target.common;

import mage.abilities.Ability;
import mage.constants.AbilityType;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.FilterStackObject;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.TargetObject;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Styxo
 */
public class TargetTriggeredAbility extends TargetObject {

    protected final FilterStackObject filter;

    public TargetTriggeredAbility(FilterStackObject filter) {
        this.minNumberOfTargets = 1;
        this.maxNumberOfTargets = 1;
        this.zone = Zone.STACK;
        this.targetName = filter.getMessage();
        this.filter = filter;
    }

    public TargetTriggeredAbility(final TargetTriggeredAbility target) {
        super(target);
        this.filter = target.filter.copy();
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        // 114.4. A spell or ability on the stack is an illegal target for itself.
        if (source != null && source.getSourceId().equals(id)) {
            return false;
        }

        StackObject stackObject = game.getStack().getStackObject(id);
        return isTriggeredAbility(stackObject)
                && source != null
                && filter.match(stackObject, source.getControllerId(), source, game);
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        for (StackObject stackObject : game.getStack()) {
            if (isTriggeredAbility(stackObject)
                    && filter.match(stackObject, sourceControllerId, source, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        return game.getStack()
                .stream()
                .anyMatch(TargetTriggeredAbility::isTriggeredAbility);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        return possibleTargets(sourceControllerId, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        return game.getStack().stream()
                .filter(TargetTriggeredAbility::isTriggeredAbility)
                .map(stackObject -> stackObject.getStackAbility().getId())
                .collect(Collectors.toSet());
    }

    @Override
    public TargetTriggeredAbility copy() {
        return new TargetTriggeredAbility(this);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    static boolean isTriggeredAbility(StackObject stackObject) {
        if (stackObject == null) {
            return false;
        }
        if (stackObject instanceof Ability) {
            Ability ability = (Ability) stackObject;
            return ability.getAbilityType() == AbilityType.TRIGGERED;
        }
        return false;
    }

}
