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
import mage.filter.common.FilterCreaturePlayerOrPlaneswalker;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetAmount;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TargetAnyTargetAmount extends TargetAmount {

    protected final FilterCreaturePlayerOrPlaneswalker filter;

    public TargetAnyTargetAmount(int amount) {
        // 107.1c If a rule or ability instructs a player to choose “any number,” that player may choose
        // any positive number or zero, unless something (such as damage or counters) is being divided
        // or distributed among “any number” of players and/or objects. In that case, a nonzero number
        // of players and/or objects must be chosen if possible.
        this(new StaticValue(amount));
        this.minNumberOfTargets = 1;
    }

    public TargetAnyTargetAmount(DynamicValue amount) {
        super(amount);
        this.zone = Zone.ALL;
        this.filter = new FilterCreaturePlayerOrPlaneswalker("targets");
        this.targetName = filter.getMessage();
    }

    public TargetAnyTargetAmount(final TargetAnyTargetAmount target) {
        super(target);
        this.filter = target.filter.copy();
    }

    @Override
    public Filter getFilter() {
        return this.filter;
    }

    @Override
    public boolean canTarget(UUID objectId, Game game) {
        Permanent permanent = game.getPermanent(objectId);
        if (permanent != null) {
            return filter.match(permanent, game);
        }
        Player player = game.getPlayer(objectId);
        return player != null && filter.match(player, game);
    }

    @Override
    public boolean canTarget(UUID objectId, Ability source, Game game) {
        Permanent permanent = game.getPermanent(objectId);
        Player player = game.getPlayer(objectId);

        if (source != null) {
            MageObject targetSource = source.getSourceObject(game);
            if (permanent != null) {
                return permanent.canBeTargetedBy(targetSource, source.getControllerId(), game) && filter.match(permanent, source.getSourceId(), source.getControllerId(), game);
            }
            if (player != null) {
                return player.canBeTargetedBy(targetSource, source.getControllerId(), game) && filter.match(player, game);
            }
        }

        if (permanent != null) {
            return filter.match(permanent, game);
        }
        return player != null && filter.match(player, game);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID objectId, Ability source, Game game) {
        return canTarget(objectId, source, game);
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        int count = 0;
        MageObject targetSource = game.getObject(sourceId);
        for (UUID playerId : game.getState().getPlayersInRange(sourceControllerId, game)) {
            Player player = game.getPlayer(playerId);
            if (player != null && player.canBeTargetedBy(targetSource, sourceControllerId, game) && filter.match(player, game)) {
                count++;
                if (count >= this.minNumberOfTargets) {
                    return true;
                }
            }
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT, sourceControllerId, game)) {
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
        for (UUID playerId : game.getState().getPlayersInRange(sourceControllerId, game)) {
            Player player = game.getPlayer(playerId);
            if (player != null && filter.match(player, game)) {
                count++;
                if (count >= this.minNumberOfTargets) {
                    return true;
                }
            }
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT, sourceControllerId, game)) {
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
        for (UUID playerId : game.getState().getPlayersInRange(sourceControllerId, game)) {
            Player player = game.getPlayer(playerId);
            if (player != null && player.canBeTargetedBy(targetSource, sourceControllerId, game) && filter.match(player, game)) {
                possibleTargets.add(playerId);
            }
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT, sourceControllerId, game)) {
            if (permanent.canBeTargetedBy(targetSource, sourceControllerId, game) && filter.match(permanent, sourceId, sourceControllerId, game)) {
                possibleTargets.add(permanent.getId());
            }
        }
        return possibleTargets;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        for (UUID playerId : game.getState().getPlayersInRange(sourceControllerId, game)) {
            Player player = game.getPlayer(playerId);
            if (player != null && filter.match(player, game)) {
                possibleTargets.add(playerId);
            }
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT, sourceControllerId, game)) {
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
            } else {
                Player player = game.getPlayer(targetId);
                if (player != null) {
                    sb.append(player.getLogName()).append('(').append(getTargetAmount(targetId)).append(") ");
                }
            }
        }
        return sb.toString();
    }

    @Override
    public TargetAnyTargetAmount copy() {
        return new TargetAnyTargetAmount(this);
    }

}
