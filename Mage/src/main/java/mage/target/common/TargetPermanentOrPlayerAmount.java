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

    TargetPermanentOrPlayerAmount(DynamicValue amount, int minNumberOfTargets, int maxNumberOfTargets) {
        super(amount, minNumberOfTargets, maxNumberOfTargets);
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
    public boolean canTarget(UUID objectId, Ability source, Game game) {
        Permanent permanent = game.getPermanent(objectId);
        Player player = game.getPlayer(objectId);

        if (source != null) {
            if (permanent != null) {
                return (isNotTarget() || permanent.canBeTargetedBy(game.getObject(source), source.getControllerId(), source, game))
                        && filter.match(permanent, source.getControllerId(), source, game);
            }
            if (player != null) {
                return (isNotTarget() || player.canBeTargetedBy(game.getObject(source), source.getControllerId(), source, game))
                        && filter.match(player, game);
            }
        } else {
            if (permanent != null) {
                return filter.match(permanent, game);
            }
            if (player != null) {
                return filter.match(player, game);
            }
        }

        return false;
    }

    @Override
    public boolean canTarget(UUID playerId, UUID objectId, Ability source, Game game) {
        return canTarget(objectId, source, game);
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        return canChooseFromPossibleTargets(sourceControllerId, source, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();

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
                .filter(Objects::nonNull)
                .map(Permanent::getId)
                .forEach(possibleTargets::add);

        return keepValidPossibleTargets(possibleTargets, sourceControllerId, source, game);
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
