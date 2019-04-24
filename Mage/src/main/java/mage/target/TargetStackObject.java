

package mage.target;

import mage.constants.Zone;
import mage.abilities.Ability;
import mage.filter.FilterStackObject;
import mage.game.Game;
import mage.game.stack.StackObject;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TargetStackObject extends TargetObject {

    protected final FilterStackObject filter;

    public TargetStackObject() {
        this(1, 1, new FilterStackObject());
    }

    public TargetStackObject(FilterStackObject filter) {
        this(1, 1, filter);
    }

    public TargetStackObject(int numTargets, FilterStackObject filter) {
        this(numTargets, numTargets, filter);
    }

    public TargetStackObject(int minNumTargets, int maxNumTargets, FilterStackObject filter) {
        this.minNumberOfTargets = minNumTargets;
        this.maxNumberOfTargets = maxNumTargets;
        this.zone = Zone.STACK;
        this.filter = filter;
        this.targetName = filter.getMessage();
    }

    public TargetStackObject(final TargetStackObject target) {
        super(target);
        this.filter = target.filter.copy();
    }

    @Override
    public FilterStackObject getFilter() {
        return filter;
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        StackObject stackObject = game.getStack().getStackObject(id);
        return stackObject != null && filter.match(stackObject, source.getSourceId(), source.getControllerId(), game);
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        int count = 0;
        for (StackObject stackObject: game.getStack()) {
            if (game.getState().getPlayersInRange(sourceControllerId, game).contains(stackObject.getControllerId()) && filter.match(stackObject, sourceId, sourceControllerId, game)) {
                count++;
                if (count >= this.minNumberOfTargets) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        return canChoose(null, sourceControllerId, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        for (StackObject stackObject: game.getStack()) {
            if (game.getState().getPlayersInRange(sourceControllerId, game).contains(stackObject.getControllerId()) && filter.match(stackObject, sourceId, sourceControllerId, game)) {
                possibleTargets.add(stackObject.getId());
            }
        }
        return possibleTargets;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        return this.possibleTargets(null, sourceControllerId, game);
    }

    @Override
    public TargetStackObject copy() {
        return new TargetStackObject(this);
    }

}
