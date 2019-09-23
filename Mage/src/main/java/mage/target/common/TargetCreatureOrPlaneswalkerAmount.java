package mage.target.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetAmount;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TargetCreatureOrPlaneswalkerAmount extends TargetAmount {

    protected final FilterCreatureOrPlaneswalkerPermanent filter;
    private static final FilterCreatureOrPlaneswalkerPermanent defaultFilter
            = new FilterCreatureOrPlaneswalkerPermanent();

    public TargetCreatureOrPlaneswalkerAmount(int amount) {
        // 107.1c If a rule or ability instructs a player to choose “any number,” that player may choose
        // any positive number or zero, unless something (such as damage or counters) is being divided
        // or distributed among “any number” of players and/or objects. In that case, a nonzero number
        // of players and/or objects must be chosen if possible.
        this(amount, defaultFilter);
    }

    public TargetCreatureOrPlaneswalkerAmount(DynamicValue amount) {
        this(amount, defaultFilter);
    }

    public TargetCreatureOrPlaneswalkerAmount(int amount, FilterCreatureOrPlaneswalkerPermanent filter) {
        this(new StaticValue(amount), filter);
    }

    public TargetCreatureOrPlaneswalkerAmount(DynamicValue amount, FilterCreatureOrPlaneswalkerPermanent filter) {
        super(amount);
        this.zone = Zone.ALL;
        this.filter = filter;
        this.targetName = filter.getMessage();
    }

    private TargetCreatureOrPlaneswalkerAmount(final TargetCreatureOrPlaneswalkerAmount target) {
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
        return permanent != null && filter.match(permanent, game);
    }

    @Override
    public boolean canTarget(UUID objectId, Ability source, Game game) {
        Permanent permanent = game.getPermanent(objectId);
        if (permanent != null) {
            if (source != null) {
                MageObject targetSource = source.getSourceObject(game);
                return permanent.canBeTargetedBy(targetSource, source.getControllerId(), game)
                        && filter.match(permanent, source.getSourceId(), source.getControllerId(), game);
            } else {
                return filter.match(permanent, game);
            }
        }
        return false;
    }

    @Override
    public boolean canTarget(UUID playerId, UUID objectId, Ability source, Game game) {
        return canTarget(objectId, source, game);
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        int count = 0;
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, sourceId, game)) {
            count++;
            if (count >= this.minNumberOfTargets) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        int count = 0;
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, game)) {
            count++;
            if (count >= this.minNumberOfTargets) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        MageObject targetSource = game.getObject(sourceId);
        return game
                .getBattlefield()
                .getActivePermanents(filter, sourceId, sourceControllerId, game)
                .stream()
                .filter(Objects::nonNull)
                .filter(permanent -> permanent.canBeTargetedBy(targetSource, sourceControllerId, game))
                .map(Permanent::getId)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
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
        for (UUID targetId : getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                sb.append(permanent.getLogName()).append('(').append(getTargetAmount(targetId)).append(") ");
            }
        }
        return sb.toString();
    }

    @Override
    public TargetCreatureOrPlaneswalkerAmount copy() {
        return new TargetCreatureOrPlaneswalkerAmount(this);
    }

}
