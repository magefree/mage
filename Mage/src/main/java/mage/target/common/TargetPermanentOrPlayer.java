
package mage.target.common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetImpl;

/**
 *
 * @author nantuko
 */
public class TargetPermanentOrPlayer extends TargetImpl {

    protected FilterPermanentOrPlayer filter;
    protected FilterPermanent filterPermanent;

    public TargetPermanentOrPlayer() {
        this(1, 1);
    }

    public TargetPermanentOrPlayer(int numTargets) {
        this(numTargets, numTargets);
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
        this.filterPermanent = this.filter.getPermanentFilter();
        this.notTarget = notTarget;
    }

    public TargetPermanentOrPlayer(final TargetPermanentOrPlayer target) {
        super(target);
        this.filter = target.filter.copy();
        this.filterPermanent = target.filterPermanent.copy();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    public void setFilter(FilterPermanentOrPlayer filter) {
        this.filter = filter;
    }

    @Override
    public boolean canTarget(UUID id, Game game) {
        Permanent permanent = game.getPermanent(id);
        if (permanent != null) {
            return filter.match(permanent, game);
        }
        Player player = game.getPlayer(id);
        return player != null && filter.match(player, game);
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
            MageObject targetSource = game.getObject(source.getSourceId());
            if (permanent != null) {
                if (!isNotTarget()) {
                    if (!permanent.canBeTargetedBy(game.getObject(source.getId()), source.getControllerId(), game)
                            || !permanent.canBeTargetedBy(game.getObject(source.getSourceId()), source.getControllerId(), game)) {
                        return false;
                    }
                }
                return filter.match(permanent, source.getSourceId(), source.getControllerId(), game);
            }
            if (player != null) {
                if (!isNotTarget()) {
                    if (!player.canBeTargetedBy(targetSource, source.getControllerId(), game)) {
                        return false;
                    }
                }
                return filter.match(player, game);
            }
        }

        if (permanent != null) {
            return filter.match(permanent, game);
        }
        return player != null && filter.match(player, game);
    }

    /**
     * Checks if there are enough {@link mage.game.permanent.Permanent} or
     * {@link mage.players.Player} that can be chosen. Should only be used for
     * Ability targets since this checks for protection, shroud etc.
     *
     * @param sourceId - the target event source
     * @param sourceControllerId - controller of the target event source
     * @param game
     * @return - true if enough valid {@link mage.game.permanent.Permanent} or
     * {@link mage.players.Player} exist
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
            if (player != null && filter.match(player, game)) {
                count++;
                if (count >= this.minNumberOfTargets) {
                    return true;
                }
            }
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filterPermanent, sourceControllerId, game)) {
            if (filter.match(permanent, null, sourceControllerId, game) && filter.match(permanent, game)) {
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
            if (player != null && (notTarget || player.canBeTargetedBy(targetSource, sourceControllerId, game)) && filter.match(player, game)) {
                possibleTargets.add(playerId);
            }
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterPermanent(), sourceControllerId, game)) {
            if ((notTarget || permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) && filter.match(permanent, sourceId, sourceControllerId, game)) {
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
                sb.append(permanent.getLogName()).append(' ');
            } else {
                Player player = game.getPlayer(targetId);
                sb.append(player.getLogName()).append(' ');
            }
        }
        return sb.toString();
    }

    @Override
    public TargetPermanentOrPlayer copy() {
        return new TargetPermanentOrPlayer(this);
    }

    public FilterPermanent getFilterPermanent() {
        return filterPermanent.copy();
    }
}
