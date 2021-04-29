package mage.target.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.common.FilterCreaturePlayerOrPlaneswalker;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetImpl;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author JRHerlehy Created on 4/8/18.
 */
public class TargetAnyTarget extends TargetImpl {

    protected FilterCreaturePlayerOrPlaneswalker filter;

    public TargetAnyTarget() {
        this(1, 1, new FilterCreaturePlayerOrPlaneswalker());
    }

    public TargetAnyTarget(int numTargets) {
        this(numTargets, numTargets, new FilterCreaturePlayerOrPlaneswalker());
    }

    public TargetAnyTarget(FilterCreaturePlayerOrPlaneswalker filter) {
        this(1, 1, filter);
    }

    public TargetAnyTarget(int numTargets, int maxNumTargets) {
        this(numTargets, maxNumTargets, new FilterCreaturePlayerOrPlaneswalker());
    }

    public TargetAnyTarget(int minNumTargets, int maxNumTargets, FilterCreaturePlayerOrPlaneswalker filter) {
        this.minNumberOfTargets = minNumTargets;
        this.maxNumberOfTargets = maxNumTargets;
        this.zone = Zone.ALL;
        this.filter = filter;
        this.targetName = filter.getMessage();
    }

    public TargetAnyTarget(final TargetAnyTarget target) {
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
        if (permanent != null) {
            return filter.match(permanent, game);
        }
        Player player = game.getPlayer(id);
        return filter.match(player, game);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        return canTarget(source.getControllerId(), id, source, game);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        Permanent permanent = game.getPermanent(id);
        Player player = game.getPlayer(id);

        if (source != null) {
            MageObject targetSource = game.getObject(source.getSourceId());
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
        return filter.match(player, game);
    }

    /**
     * Checks if there are enough {@link Permanent} or {@link Player} that can
     * be chosen. Should only be used for Ability targets since this checks for
     * protection, shroud etc.
     *
     * @param sourceId           - the target event source
     * @param sourceControllerId - controller of the target event source
     * @param game
     * @return - true if enough valid {@link Permanent} or {@link Player} exist
     */
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

        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter.getPermanentFilter(), sourceControllerId, game)) {
            if (permanent.canBeTargetedBy(targetSource, sourceControllerId, game) && filter.match(permanent, sourceId, sourceControllerId, game)) {
                count++;
                if (count >= this.minNumberOfTargets) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks if there are enough {@link Permanent} or {@link Player} that can
     * be selected. Should not be used for Ability targets since this does not
     * check for protection, shroud etc.
     *
     * @param sourceControllerId - controller of the select event
     * @param game
     * @return - true if enough valid {@link Permanent} or {@link Player} exist
     */
    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        int count = 0;

        for (UUID playerId : game.getState().getPlayersInRange(sourceControllerId, game)) {
            Player player = game.getPlayer(playerId);
            if (filter.match(player, game)) {
                count++;
                if (count >= this.minNumberOfTargets) {
                    return true;
                }
            }
        }

        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter.getPermanentFilter(), sourceControllerId, game)) {
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
            if (player != null
                    && player.canBeTargetedBy(targetSource, sourceControllerId, game)
                    && filter.match(player, sourceId, sourceControllerId, game)) {
                possibleTargets.add(playerId);
            }
        }

        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter.getPermanentFilter(), sourceControllerId, game)) {
            if (permanent.canBeTargetedBy(targetSource, sourceControllerId, game)
                    && filter.match(permanent, sourceId, sourceControllerId, game)) {
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
            if (filter.match(player, game)) {
                possibleTargets.add(playerId);
            }
        }

        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter.getPermanentFilter(), sourceControllerId, game)) {
            if (filter.getPermanentFilter().match(permanent, null, sourceControllerId, game)) {
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
                sb.append(permanent.getLogName()).append(' ');
            } else {
                Player player = game.getPlayer(targetId);
                if (player != null) {
                    sb.append(player.getLogName()).append(' ');
                }
            }
        }
        return sb.toString().trim();
    }

    @Override
    public TargetAnyTarget copy() {
        return new TargetAnyTarget(this);
    }

}
