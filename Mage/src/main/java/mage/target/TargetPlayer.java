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

    protected TargetPlayer(final TargetPlayer target) {
        super(target);
        this.filter = target.filter.copy();
    }

    @Override
    public FilterPlayer getFilter() {
        return filter;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        return canChooseFromPossibleTargets(sourceControllerId, source, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        for (UUID playerId : game.getState().getPlayersInRange(sourceControllerId, game)) {
            Player player = game.getPlayer(playerId);
            if (player != null && filter.match(player, sourceControllerId, source, game)) {
                possibleTargets.add(playerId);
            }
        }
        return keepValidPossibleTargets(possibleTargets, sourceControllerId, source, game);
    }

    @Override
    public boolean isLegal(Ability source, Game game) {
        //20101001 - 608.2b
        if (getMinNumberOfTargets() == 0 && targets.isEmpty()) {
            return true; // 0 targets selected is valid
        }
        return targets.keySet().stream().anyMatch(playerId -> canTarget(playerId, source, game));
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Player player = game.getPlayer(id);
        if (player != null) {
            if (source != null) {
                return (isNotTarget() || player.canBeTargetedBy(game.getObject(source), source.getControllerId(), source, game))
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
