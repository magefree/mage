
package mage.target.common;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import mage.abilities.Ability;
import mage.constants.AbilityType;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.FilterStackObject;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.TargetObject;

public class TargetActivatedOrTriggeredAbility extends TargetObject {

    protected final FilterStackObject filter;

    public TargetActivatedOrTriggeredAbility() {
        this(new FilterStackObject("activated or triggered ability"));
    }

    public TargetActivatedOrTriggeredAbility(FilterStackObject filter) {
        this.minNumberOfTargets = 1;
        this.maxNumberOfTargets = 1;
        this.zone = Zone.STACK;
        this.targetName = filter.getMessage();
        this.filter = filter;
    }

    public TargetActivatedOrTriggeredAbility(final TargetActivatedOrTriggeredAbility target) {
        super(target);
        this.filter = target.filter.copy();
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        // rule 114.4. A spell or ability on the stack is an illegal target for itself.
        if (source != null && source.getId().equals(id)) {
            return false;
        }

        StackObject stackObject = game.getStack().getStackObject(id);
        return isActivatedOrTriggeredAbility(stackObject) && filter.match(stackObject, source.getSourceId(), source.getControllerId(), game);
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        for (StackObject stackObject : game.getStack()) {
            if (isActivatedOrTriggeredAbility(stackObject)
                    && filter.match(stackObject, sourceId, sourceControllerId, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        return game.getStack()
                .stream()
                .anyMatch(TargetActivatedOrTriggeredAbility::isActivatedOrTriggeredAbility);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        return possibleTargets(sourceControllerId, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        return game.getStack().stream()
                .filter(TargetActivatedOrTriggeredAbility::isActivatedOrTriggeredAbility)
                .map(stackObject -> stackObject.getStackAbility().getId())
                .collect(Collectors.toSet());
    }

    @Override
    public TargetActivatedOrTriggeredAbility copy() {
        return new TargetActivatedOrTriggeredAbility(this);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    static boolean isActivatedOrTriggeredAbility(StackObject stackObject) {
        if (stackObject == null) {
            return false;
        }
        if (stackObject instanceof Ability) {
            Ability ability = (Ability) stackObject;
            return ability.getAbilityType() == AbilityType.TRIGGERED
                    || ability.getAbilityType() == AbilityType.ACTIVATED;
        }
        return false;
    }
}
