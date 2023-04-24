package mage.target.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetImpl;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author nantuko
 */
public class TargetPermanentOrPlayer extends TargetImpl {

    protected FilterPermanentOrPlayer filter;

    public TargetPermanentOrPlayer() {
        this(1);
    }

    public TargetPermanentOrPlayer(int numTargets) {
        this(numTargets, numTargets);
    }

    public TargetPermanentOrPlayer(FilterPermanentOrPlayer filter) {
        this(1, 1, filter, false);
    }

    public TargetPermanentOrPlayer(int minNumTargets, int maxNumTargets) {
        this(minNumTargets, maxNumTargets, false);
    }

    public TargetPermanentOrPlayer(int minNumTargets, int maxNumTargets, boolean notTarget) {
        this(minNumTargets, maxNumTargets, new FilterPermanentOrPlayer(), notTarget);
    }

    public TargetPermanentOrPlayer(int minNumTargets, int maxNumTargets, FilterPermanentOrPlayer filter, boolean notTarget) {
        this.minNumberOfTargets = minNumTargets;
        this.maxNumberOfTargets = maxNumTargets;
        this.zone = Zone.ALL;
        this.filter = filter;
        this.targetName = filter.getMessage();
        this.notTarget = notTarget;
    }

    public TargetPermanentOrPlayer(final TargetPermanentOrPlayer target) {
        super(target);
        this.filter = target.filter.copy();
    }

    @Override
    public Filter getFilter() {
        return filter;
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
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        return canTarget(id, source, game);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Permanent permanent = game.getPermanent(id);
        Player player = game.getPlayer(id);

        if (source != null) {
            MageObject targetSource = game.getObject(source);
            if (permanent != null) {
                if (!isNotTarget()) {
                    if (!permanent.canBeTargetedBy(game.getObject(source.getId()), source.getControllerId(), game)
                            || !permanent.canBeTargetedBy(game.getObject(source), source.getControllerId(), game)) {
                        return false;
                    }
                }
                return filter.match(permanent, source.getControllerId(), source, game);
            }
            if (player != null) {
                if (!isNotTarget()) {
                    if (!player.canBeTargetedBy(targetSource, source.getControllerId(), game)
                            || !filter.match(player, source.getControllerId(), source, game)) {
                        return false;
                    }
                }
                return filter.match(player, game);
            }
        }

        if (permanent != null) {
            return filter.match(permanent, game);
        }
        return filter.match(player, game);
    }

    /**
     * Checks if there are enough {@link mage.game.permanent.Permanent} or
     * {@link mage.players.Player} that can be chosen. Should only be used for
     * Ability targets since this checks for protection, shroud etc.
     *
     * @param sourceControllerId - controller of the target event source
     * @param source
     * @param game
     * @return - true if enough valid {@link mage.game.permanent.Permanent} or
     * {@link mage.players.Player} exist
     */
    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        int count = 0;
        MageObject targetSource = game.getObject(source);
        for (UUID playerId : game.getState().getPlayersInRange(sourceControllerId, game)) {
            Player player = game.getPlayer(playerId);
            if (player != null && player.canBeTargetedBy(targetSource, sourceControllerId, game) && filter.match(player, sourceControllerId, source, game)) {
                count++;
                if (count >= this.minNumberOfTargets) {
                    return true;
                }
            }
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter.getPermanentFilter(), sourceControllerId, game)) {
            if (permanent.canBeTargetedBy(targetSource, sourceControllerId, game) && filter.match(permanent, sourceControllerId, source, game)) {
                count++;
                if (count >= this.minNumberOfTargets) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if there are enough {@link mage.game.permanent.Permanent} or
     * {@link mage.players.Player} that can be selected. Should not be used for
     * Ability targets since this does not check for protection, shroud etc.
     *
     * @param sourceControllerId - controller of the select event
     * @param game
     * @return - true if enough valid {@link mage.game.permanent.Permanent} or
     * {@link mage.players.Player} exist
     */
    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        int count = 0;
        for (UUID playerId : game.getState().getPlayersInRange(sourceControllerId, game)) {
            Player player = game.getPlayer(playerId);
            if (player != null && filter.getPlayerFilter().match(player, game)) {
                count++;
                if (count >= this.minNumberOfTargets) {
                    return true;
                }
            }
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter.getPermanentFilter(), sourceControllerId, game)) {
            if (filter.match(permanent, sourceControllerId, null, game) && filter.match(permanent, game)) {
                count++;
                if (count >= this.minNumberOfTargets) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        MageObject targetSource = game.getObject(source);
        for (UUID playerId : game.getState().getPlayersInRange(sourceControllerId, game)) {
            Player player = game.getPlayer(playerId);
            if (player != null && (notTarget || player.canBeTargetedBy(targetSource, sourceControllerId, game)) && filter.match(player, sourceControllerId, source, game)) {
                possibleTargets.add(playerId);
            }
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter.getPermanentFilter(), sourceControllerId, game)) {
            if ((notTarget || permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) && filter.match(permanent, sourceControllerId, source, game)) {
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
            if (filter.match(permanent, sourceControllerId, null, game)) {
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
                continue;
            }

            Player player = game.getPlayer(targetId);
            if (player != null) {
                sb.append(player.getLogName()).append(' ');
                continue;
            }

            MageObject object = game.getObject(targetId);
            if (object != null) {
                sb.append(object.getLogName()).append(' ');
                continue;
            }

            sb.append("ERROR");
        }
        return sb.toString().trim();
    }

    @Override
    public TargetPermanentOrPlayer copy() {
        return new TargetPermanentOrPlayer(this);
    }

    public FilterPermanent getFilterPermanent() {
        return filter.getPermanentFilter().copy();
    }
}
