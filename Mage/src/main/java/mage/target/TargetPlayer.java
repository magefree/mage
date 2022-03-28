package mage.target;

import mage.MageObject;
import mage.abilities.Ability;
import mage.filter.FilterPlayer;
import mage.game.Game;
import mage.players.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TargetPlayer extends TargetImpl {

    protected final FilterPlayer filter;

    public TargetPlayer() {
        this(1, 1, false);
    }

    public TargetPlayer(FilterPlayer filter) {
        this(1, 1, false, filter);
    }

    public TargetPlayer(int numTargets) {
        this(numTargets, numTargets, false);
    }

    public TargetPlayer(int minNumTargets, int maxNumTargets, boolean notTarget) {
        this(minNumTargets, maxNumTargets, notTarget, new FilterPlayer());
    }

    public TargetPlayer(int minNumTargets, int maxNumTargets, boolean notTarget, FilterPlayer filter) {
        this.minNumberOfTargets = minNumTargets;
        this.maxNumberOfTargets = maxNumTargets;
        this.filter = filter;
        this.targetName = filter.getMessage();
        this.notTarget = notTarget;
    }

    public TargetPlayer(final TargetPlayer target) {
        super(target);
        this.filter = target.filter.copy();
    }

    @Override
    public FilterPlayer getFilter() {
        return filter;
    }

    /**
     * Checks if there are enough {@link Player} that can be chosen. Should only
     * be used for Ability targets since this checks for protection, shroud etc.
     *
     * @param sourceControllerId - controller of the target event source
     * @param source
     * @param game
     * @return - true if enough valid {@link Player} exist
     */
    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        int count = 0;
        MageObject targetSource = game.getObject(source);
        for (UUID playerId : game.getState().getPlayersInRange(sourceControllerId, game)) {
            Player player = game.getPlayer(playerId);
            if (player != null && !player.hasLeft() && filter.match(player, sourceControllerId, source, game)) {
                if (player.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                    count++;
                    if (count >= this.minNumberOfTargets) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks if there are enough {@link Player} that can be selected. Should
     * not be used for Ability targets since this does not check for protection,
     * shroud etc.
     *
     * @param sourceControllerId - controller of the select event
     * @param game
     * @return - true if enough valid {@link Player} exist
     */
    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        int count = 0;
        for (UUID playerId : game.getState().getPlayersInRange(sourceControllerId, game)) {
            Player player = game.getPlayer(playerId);
            if (player != null && !player.hasLeft() && filter.match(player, game)) {
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
            if (player != null && !player.hasLeft() && filter.match(player, sourceControllerId, source, game)) {
                if (isNotTarget() || player.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                    possibleTargets.add(playerId);
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        for (UUID playerId : game.getState().getPlayersInRange(sourceControllerId, game)) {
            Player player = game.getPlayer(playerId);
            if (player != null && !player.hasLeft() && filter.match(player, game)) {
                possibleTargets.add(playerId);
            }
        }
        return possibleTargets;
    }

    @Override
    public boolean isLegal(Ability source, Game game) {
        //20101001 - 608.2b
        if (getNumberOfTargets() == 0 && targets.isEmpty()) {
            return true; // 0 targets selected is valid
        }
        return targets.keySet().stream().anyMatch(playerId -> canTarget(playerId, source, game));
    }

    @Override
    public boolean canTarget(UUID id, Game game) {
        Player player = game.getPlayer(id);
        return filter.match(player, game);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Player player = game.getPlayer(id);
        if (player != null) {
            if (source != null) {
                return (isNotTarget() || player.canBeTargetedBy(game.getObject(source), source.getControllerId(), game))
                        && filter.match(player, source.getControllerId(), source, game);
            } else {
                return filter.match(player, game);
            }
        }
        return false;
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        return canTarget(id, source, game);
    }

    @Override
    public String getTargetedName(Game game) {
        StringBuilder sb = new StringBuilder();
        for (UUID targetId : getTargets()) {
            Player player = game.getPlayer(targetId);
            if (player != null) {
                sb.append(player.getLogName()).append(' ');
            } else {
                sb.append("[target missing]");
            }
        }
        return sb.toString().trim();
    }

    @Override
    public TargetPlayer copy() {
        return new TargetPlayer(this);
    }

}
