
package mage.target.common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.constants.AbilityType;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.FilterAbility;
import mage.filter.FilterStackObject;
import mage.game.Game;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.target.TargetObject;

/**
 *
 * @author emerald000
 */
public class TargetActivatedAbility extends TargetObject {

    protected final FilterStackObject filter;

    public TargetActivatedAbility() {
        this(new FilterStackObject("activated ability"));
    }

    public TargetActivatedAbility(FilterStackObject filter) {
        this.minNumberOfTargets = 1;
        this.maxNumberOfTargets = 1;
        this.zone = Zone.STACK;
        this.targetName = filter.getMessage();
        this.filter = filter;
    }

    public TargetActivatedAbility(final TargetActivatedAbility target) {
        super(target);
        this.filter = target.filter.copy();
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        if (source != null && source.getSourceId().equals(id)) {
            return false;
        }
        StackObject stackObject = game.getStack().getStackObject(id);
        return stackObject != null
                && stackObject.getStackAbility() != null
                && stackObject.getStackAbility().getAbilityType() == AbilityType.ACTIVATED
                && filter.match(stackObject, source.getSourceId(), source.getControllerId(), game);
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        return canChoose(sourceControllerId, game);
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        for (StackObject stackObject : game.getStack()) {
            if (stackObject.getStackAbility() != null
                    && stackObject.getStackAbility().getAbilityType() == AbilityType.ACTIVATED
                    && game.getState().getPlayersInRange(sourceControllerId, game).contains(stackObject.getStackAbility().getControllerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        return possibleTargets(sourceControllerId, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        for (StackObject stackObject : game.getStack()) {
            if (stackObject.getStackAbility().getAbilityType() == AbilityType.ACTIVATED
                    && game.getState().getPlayersInRange(sourceControllerId, game).contains(stackObject.getStackAbility().getControllerId())
                    && filter.match(((StackAbility) stackObject), game)) {
                possibleTargets.add(stackObject.getStackAbility().getId());
            }
        }
        return possibleTargets;
    }

    @Override
    public TargetActivatedAbility copy() {
        return new TargetActivatedAbility(this);
    }

    @Override
    public Filter<Ability> getFilter() {
        return new FilterAbility();
    }

    @Override
    public String getTargetedName(Game game) {
        StringBuilder sb = new StringBuilder("activated ability (");
        for (UUID targetId : getTargets()) {
            StackAbility object = (StackAbility) game.getObject(targetId);
            if (object != null) {
                sb.append(object.getRule()).append(' ');
            }
        }
        sb.append(')');
        return sb.toString();
    }
}
