
package mage.target.common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetAmount;

/**
 *
 * @author North
 */
public class TargetCreaturePermanentAmount extends TargetAmount {

    protected final FilterCreaturePermanent filter;

    public TargetCreaturePermanentAmount(int amount) {
        this(amount, new FilterCreaturePermanent());
    }

    public TargetCreaturePermanentAmount(DynamicValue amount) {
        this(amount, new FilterCreaturePermanent());
    }

    public TargetCreaturePermanentAmount(int amount, FilterCreaturePermanent filter) {
        this(new StaticValue(amount), filter);
    }

    public TargetCreaturePermanentAmount(DynamicValue amount, FilterCreaturePermanent filter) {
        super(amount);
        this.zone = Zone.ALL;
        this.filter = filter;
        this.targetName = filter.getMessage();
    }

    public TargetCreaturePermanentAmount(final TargetCreaturePermanentAmount target) {
        super(target);
        this.filter = target.filter.copy();
    }

    @Override
    public Filter getFilter() {
        return this.filter;
    }

    @Override
    public boolean canTarget(UUID id, Game game) {
        Permanent permanent = game.getPermanent(id);
        return permanent != null && filter.match(permanent, game);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Permanent permanent = game.getPermanent(id);
        if (permanent != null) {
            if (source != null) {
                MageObject targetSource = game.getObject(source.getSourceId());
                return permanent.canBeTargetedBy(targetSource, source.getControllerId(), game) && filter.match(permanent, source.getSourceId(), source.getControllerId(), game);
            } else {
                return filter.match(permanent, game);
            }
        }
        return false;
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        return canTarget(id, source, game);
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        int count = 0;
        MageObject targetSource = game.getObject(sourceId);
        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, sourceControllerId, game)) {
            if (permanent.canBeTargetedBy(targetSource, sourceControllerId, game) && filter.match(permanent, sourceId, sourceControllerId, game)) {
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
        int count = 0;
        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, sourceControllerId, game)) {
            if (filter.match(permanent, null, sourceControllerId, game)) {
                count++;
                if (count >= this.minNumberOfTargets) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        MageObject targetSource = game.getObject(sourceId);
        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, sourceControllerId, game)) {
            if (permanent.canBeTargetedBy(targetSource, sourceControllerId, game) && filter.match(permanent, sourceId, sourceControllerId, game)) {
                possibleTargets.add(permanent.getId());
            }
        }
        return possibleTargets;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, sourceControllerId, game)) {
            if (filter.match(permanent, null, sourceControllerId, game)) {
                possibleTargets.add(permanent.getId());
            }
        }
        return possibleTargets;
    }

    @Override
    public String getTargetedName(Game game) {
        StringBuilder sb = new StringBuilder();
        for (UUID targetId : getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                sb.append(permanent.getLogName()).append('(').append(getTargetAmount(targetId)).append(") ");
            }
        }
        return sb.toString();
    }

    @Override
    public TargetCreaturePermanentAmount copy() {
        return new TargetCreaturePermanentAmount(this);
    }
}
