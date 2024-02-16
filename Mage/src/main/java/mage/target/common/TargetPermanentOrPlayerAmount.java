package mage.target.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.filter.Filter;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetAmount;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public abstract class TargetPermanentOrPlayerAmount extends TargetAmount {

    protected FilterPermanentOrPlayer filter;

    TargetPermanentOrPlayerAmount(DynamicValue amount) {
        this(amount, 0);
    }

    TargetPermanentOrPlayerAmount(DynamicValue amount, int maxNumberOfTargets) {
        super(amount);
        this.maxNumberOfTargets = maxNumberOfTargets;
    }

    TargetPermanentOrPlayerAmount(final TargetPermanentOrPlayerAmount target) {
        super(target);
        this.filter = target.filter.copy();
    }

    @Override
    public Filter getFilter() {
        return this.filter;
    }

    @Override
    public boolean canTarget(UUID objectId, Game game) {

        // max targets limit reached (only selected can be chosen again)
        if (getMaxNumberOfTargets() > 0 && getTargets().size() >= getMaxNumberOfTargets()) {
            return getTargets().contains(objectId);
        }

        Permanent permanent = game.getPermanent(objectId);
        if (permanent != null) {
            return filter.match(permanent, game);
        }
        Player player = game.getPlayer(objectId);
        return filter.match(player, game);
    }

    @Override
    public boolean canTarget(UUID objectId, Ability source, Game game) {

        // max targets limit reached (only selected can be chosen again)
        if (getMaxNumberOfTargets() > 0 && getTargets().size() >= getMaxNumberOfTargets()) {
            return getTargets().contains(objectId);
        }

        Permanent permanent = game.getPermanent(objectId);
        Player player = game.getPlayer(objectId);

        if (source != null) {
            MageObject targetSource = source.getSourceObject(game);
            if (permanent != null) {
                return permanent.canBeTargetedBy(targetSource, source.getControllerId(), game)
                        && filter.match(permanent, source.getControllerId(), source, game);
            }
            if (player != null) {
                return player.canBeTargetedBy(targetSource, source.getControllerId(), game)
                        && filter.match(player, game);
            }
        }

        if (permanent != null) {
            return filter.match(permanent, game);
        }
        return filter.match(player, game);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID objectId, Ability source, Game game) {
        return canTarget(objectId, source, game);
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        // no max targets limit here
        int count = 0;
        MageObject targetSource = game.getObject(source);
        for (UUID playerId : game.getState().getPlayersInRange(sourceControllerId, game)) {
            Player player = game.getPlayer(playerId);
            if (player == null
                    || !player.canBeTargetedBy(targetSource, sourceControllerId, game)
                    || !filter.match(player, game)) {
                continue;
            }
            count++;
            if (count >= this.minNumberOfTargets) {
                return true;
            }
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter.getPermanentFilter(), sourceControllerId, game)) {
            if (!permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                continue;
            }
            count++;
            if (count >= this.minNumberOfTargets) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        // no max targets limit here
        int count = 0;
        for (UUID playerId : game.getState().getPlayersInRange(sourceControllerId, game)) {
            Player player = game.getPlayer(playerId);
            if (player == null || !filter.match(player, game)) {
                continue;
            }
            count++;
            if (count >= this.minNumberOfTargets) {
                return true;
            }
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter.getPermanentFilter(), sourceControllerId, game)) {
            count++;
            if (count >= this.minNumberOfTargets) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();

        // max targets limit reached (only selected can be chosen again)
        if (getMaxNumberOfTargets() > 0 && getTargets().size() >= getMaxNumberOfTargets()) {
            possibleTargets.addAll(getTargets());
            return possibleTargets;
        }

        MageObject targetSource = game.getObject(source);

        game.getState()
                .getPlayersInRange(sourceControllerId, game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .filter(player -> player.canBeTargetedBy(targetSource, sourceControllerId, game)
                        && filter.match(player, game)
                )
                .map(Player::getId)
                .forEach(possibleTargets::add);

        game.getBattlefield()
                .getActivePermanents(filter.getPermanentFilter(), sourceControllerId, game)
                .stream()
                .filter(Objects::nonNull)
                .filter(permanent -> permanent.canBeTargetedBy(targetSource, sourceControllerId, game))
                .map(Permanent::getId)
                .forEach(possibleTargets::add);

        return possibleTargets;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();

        // max targets limit reached (only selected can be chosen again)
        if (getMaxNumberOfTargets() > 0 && getTargets().size() >= getMaxNumberOfTargets()) {
            possibleTargets.addAll(getTargets());
            return possibleTargets;
        }

        game.getState()
                .getPlayersInRange(sourceControllerId, game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .filter(player -> filter.match(player, game))
                .map(Player::getId)
                .forEach(possibleTargets::add);

        game.getBattlefield()
                .getActivePermanents(filter.getPermanentFilter(), sourceControllerId, game)
                .stream()
                .map(Permanent::getId)
                .forEach(possibleTargets::add);

        return possibleTargets;
    }

    @Override
    public String getTargetedName(Game game) {
        StringBuilder sb = new StringBuilder();
        for (UUID targetId : getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                sb.append(permanent.getLogName()).append(" (").append(getTargetAmount(targetId)).append(") ");
            } else {
                Player player = game.getPlayer(targetId);
                if (player != null) {
                    sb.append(player.getLogName()).append(" (").append(getTargetAmount(targetId)).append(") ");
                }
            }
        }
        return sb.toString().trim();
    }
}
