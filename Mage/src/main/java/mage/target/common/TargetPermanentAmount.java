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
public class TargetPermanentAmount extends TargetAmount {

    protected final FilterPermanent filter;

    /**
     * {@code maxNumberOfTargets} defaults to {@code amount}.
     *
     * @see TargetPermanentAmount#TargetPermanentAmount(DynamicValue, int, int, FilterPermanent)
     */
    public TargetPermanentAmount(int amount, int minNumberOfTargets, FilterPermanent filter) {
        this(amount, minNumberOfTargets, amount, filter);
    }

    /**
     * {@code maxNumberOfTargets} defaults to Integer.MAX_VALUE.
     *
     * @see TargetPermanentAmount#TargetPermanentAmount(DynamicValue, int, int, FilterPermanent)
     */
    public TargetPermanentAmount(DynamicValue amount, int minNumberOfTargets, FilterPermanent filter) {
        this(amount, minNumberOfTargets, Integer.MAX_VALUE, filter);
    }

    /**
     * @see TargetPermanentAmount#TargetPermanentAmount(DynamicValue, int, int, FilterPermanent)
     */
    public TargetPermanentAmount(int amount, int minNumberOfTargets, int maxNumberOfTargets, FilterPermanent filter) {
        this(StaticValue.get(amount), minNumberOfTargets, maxNumberOfTargets, filter);
    }

    /**
     * @param amount             Amount of stuff (e.g. damage, counters) to distribute.
     * @param minNumberOfTargets Minimum number of targets.
     * @param maxNumberOfTargets Maximum number of targets. If no lower max is needed:
     *                           set to {@code amount} if amount is static; otherwise, set to Integer.MAX_VALUE.
     *                           (Game will always prevent distributing among more than {@code amount} permanents.)
     * @param filter             Filter for permanents that something will be distributed amongst.
     */
    public TargetPermanentAmount(DynamicValue amount, int minNumberOfTargets, int maxNumberOfTargets, FilterPermanent filter) {
        super(amount, minNumberOfTargets, maxNumberOfTargets);
        this.zone = Zone.ALL;
        this.filter = filter;
        this.targetName = filter.getMessage();
    }

    protected TargetPermanentAmount(final TargetPermanentAmount target) {
        super(target);
        this.filter = target.filter.copy();
    }

    @Override
    public TargetPermanentAmount copy() {
        return new TargetPermanentAmount(this);
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
        return (notTarget || permanent.canBeTargetedBy(targetSource, source.getControllerId(), source, game))
                && filter.match(permanent, source.getControllerId(), source, game);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID objectId, Ability source, Game game) {
        return canTarget(objectId, source, game);
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        return game.getBattlefield().getActivePermanents(filter, sourceControllerId, source, game).size()
                >= this.minNumberOfTargets;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        return game.getBattlefield().getActivePermanents(filter, sourceControllerId, game).size()
                >= this.minNumberOfTargets;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        if (getMaxNumberOfTargets() > 0 && getTargets().size() >= getMaxNumberOfTargets()) {
            return getTargets()
                    .stream()
                    .collect(Collectors.toSet());
        }
        MageObject targetSource = game.getObject(source);
        return game
                .getBattlefield()
                .getActivePermanents(filter, sourceControllerId, source, game)
                .stream()
                .filter(Objects::nonNull)
                .filter(permanent -> notTarget || permanent.canBeTargetedBy(targetSource, sourceControllerId, source, game))
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
}
