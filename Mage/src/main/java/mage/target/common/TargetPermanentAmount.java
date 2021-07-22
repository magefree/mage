package mage.target.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetAmount;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public abstract class TargetPermanentAmount extends TargetAmount {

    protected final FilterPermanent filter;

    TargetPermanentAmount(int amount, FilterPermanent filter) {
        // 107.1c If a rule or ability instructs a player to choose “any number,” that player may choose
        // any positive number or zero, unless something (such as damage or counters) is being divided
        // or distributed among “any number” of players and/or objects. In that case, a nonzero number
        // of players and/or objects must be chosen if possible.
        this(StaticValue.get(amount), filter);
    }

    TargetPermanentAmount(DynamicValue amount, FilterPermanent filter) {
        super(amount);
        this.zone = Zone.ALL;
        this.filter = filter;
        this.targetName = filter.getMessage();
    }

    TargetPermanentAmount(final TargetPermanentAmount target) {
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
        return filter.match(permanent, game);
    }

    @Override
    public boolean canTarget(UUID objectId, Ability source, Game game) {
        if (getMaxNumberOfTargets() > 0 && getTargets().size() >= getMaxNumberOfTargets()) {
            return getTargets().contains(objectId);
        }
        Permanent permanent = game.getPermanent(objectId);
        if (permanent == null) {
            return false;
        }
        if (source == null) {
            return filter.match(permanent, game);
        }
        MageObject targetSource = source.getSourceObject(game);
        return permanent.canBeTargetedBy(targetSource, source.getControllerId(), game)
                && filter.match(permanent, source.getSourceId(), source.getControllerId(), game);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID objectId, Ability source, Game game) {
        return canTarget(objectId, source, game);
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        return game.getBattlefield().getActivePermanents(filter, sourceControllerId, sourceId, game).size()
                >= this.minNumberOfTargets;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        return game.getBattlefield().getActivePermanents(filter, sourceControllerId, game).size()
                >= this.minNumberOfTargets;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        if (getMaxNumberOfTargets() > 0 && getTargets().size() >= getMaxNumberOfTargets()) {
            return getTargets()
                    .stream()
                    .collect(Collectors.toSet());
        }
        MageObject targetSource = game.getObject(sourceId);
        return game
                .getBattlefield()
                .getActivePermanents(filter, sourceControllerId, sourceId, game)
                .stream()
                .filter(Objects::nonNull)
                .filter(permanent -> permanent.canBeTargetedBy(targetSource, sourceControllerId, game))
                .map(Permanent::getId)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        if (getMaxNumberOfTargets() > 0 && getTargets().size() >= getMaxNumberOfTargets()) {
            return getTargets()
                    .stream()
                    .collect(Collectors.toSet());
        }
        return game
                .getBattlefield()
                .getActivePermanents(filter, sourceControllerId, game)
                .stream()
                .map(Permanent::getId)
                .collect(Collectors.toSet());
    }

    @Override
    public String getTargetedName(Game game) {
        StringBuilder sb = new StringBuilder();
        getTargets().forEach((targetId) -> {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                sb.append(permanent.getLogName()).append(" (").append(getTargetAmount(targetId)).append(") ");
            }
        });
        return sb.toString().trim();
    }

    @Override
    public abstract TargetPermanentAmount copy();
}
