package mage.target.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPlaneswalkerOrPlayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetImpl;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TargetDefender extends TargetImpl {

    protected final FilterPlaneswalkerOrPlayer filter;
    protected final UUID attackerId;

    public TargetDefender(Set<UUID> defenders, UUID attackerId) {
        this(1, 1, defenders, attackerId);
    }

    public TargetDefender(int numTargets, Set<UUID> defenders, UUID attackerId) {
        this(numTargets, numTargets, defenders, attackerId);
    }

    public TargetDefender(int minNumTargets, int maxNumTargets, Set<UUID> defenders, UUID attackerId) {
        this.minNumberOfTargets = minNumTargets;
        this.maxNumberOfTargets = maxNumTargets;
        this.zone = Zone.ALL;
        this.filter = new FilterPlaneswalkerOrPlayer(defenders);
        this.targetName = filter.getMessage();
        this.attackerId = attackerId;
        this.notTarget = true;
    }

    public TargetDefender(final TargetDefender target) {
        super(target);
        this.filter = target.filter.copy();
        this.attackerId = target.attackerId;
    }

    @Override
    public Filter getFilter() {
        return this.filter;
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        int count = 0;
        MageObject targetSource = game.getObject(sourceId);
        for (UUID playerId : game.getState().getPlayersInRange(sourceControllerId, game)) {
            Player player = game.getPlayer(playerId);
            if (player != null
                    && (notTarget || player.canBeTargetedBy(targetSource, sourceControllerId, game))
                    && filter.match(player, game)) {
                count++;
                if (count >= this.minNumberOfTargets) {
                    return true;
                }
            }
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_PLANESWALKER, sourceControllerId, game)) {
            if ((notTarget
                    || permanent.canBeTargetedBy(targetSource, sourceControllerId, game))
                    && filter.match(permanent, game)) {
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
            if (player != null
                    && filter.match(player, game)) {
                count++;
                if (count >= this.minNumberOfTargets) {
                    return true;
                }
            }
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_PLANESWALKER, sourceControllerId, game)) {
            if (filter.match(permanent, game)) {
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
            if (player != null
                    && (notTarget
                    || player.canBeTargetedBy(targetSource, sourceControllerId, game))
                    && filter.match(player, game)) {
                possibleTargets.add(playerId);
            }
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_PLANESWALKER, sourceControllerId, game)) {
            if ((notTarget
                    || permanent.canBeTargetedBy(targetSource, sourceControllerId, game))
                    && filter.match(permanent, game)) {
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
            if (player != null
                    && filter.match(player, game)) {
                possibleTargets.add(playerId);
            }
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_PLANESWALKER, sourceControllerId, game)) {
            if (filter.match(permanent, game)) {
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
                sb.append(permanent.getName()).append(' ');
            } else {
                Player player = game.getPlayer(targetId);
                sb.append(player.getLogName()).append(' ');
            }
        }
        return sb.toString().trim();
    }

    @Override
    public boolean canTarget(UUID id, Game game) {
        Player player = game.getPlayer(id);
        if (player != null) {
            return filter.match(player, game);
        }
        Permanent permanent = game.getPermanent(id);
        return permanent != null
                && filter.match(permanent, game);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Player player = game.getPlayer(id);
        MageObject targetSource = game.getObject(attackerId);
        if (player != null) {
            return (notTarget
                    || player.canBeTargetedBy(targetSource, (source == null ? null : source.getControllerId()), game))
                    && filter.match(player, game);
        }
        Permanent permanent = game.getPermanent(id); // planeswalker
        if (permanent != null) {
            //Could be targeting due to combat decision to attack a player or planeswalker.
            UUID controllerId = null;
            if (source != null) {
                controllerId = source.getControllerId();
            }
            return (notTarget
                    || permanent.canBeTargetedBy(targetSource, controllerId, game))
                    && filter.match(permanent, game);
        }
        return false;
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        return canTarget(id, source, game);
    }

    @Override
    public TargetDefender copy() {
        return new TargetDefender(this);
    }

}
