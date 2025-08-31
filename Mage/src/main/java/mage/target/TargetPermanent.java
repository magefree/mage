package mage.target;

import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TargetPermanent extends TargetObject {

    protected FilterPermanent filter;

    public TargetPermanent() {
        this(StaticFilters.FILTER_PERMANENT);
    }

    public TargetPermanent(FilterPermanent filter) {
        this(1, filter);
    }

    public TargetPermanent(int numTargets, FilterPermanent filter) {
        this(numTargets, numTargets, filter);
    }

    public TargetPermanent(int minNumTargets, int maxNumTargets, FilterPermanent filter) {
        this(minNumTargets, maxNumTargets, filter, false);
    }

    public TargetPermanent(int minNumTargets, int maxNumTargets, FilterPermanent filter, boolean notTarget) {
        this.minNumberOfTargets = minNumTargets;
        this.maxNumberOfTargets = maxNumTargets;
        this.zone = Zone.BATTLEFIELD;
        this.filter = filter;
        this.targetName = filter.getMessage();
        this.notTarget = notTarget;
    }

    protected TargetPermanent(final TargetPermanent target) {
        super(target);
        this.filter = target.filter.copy();
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        return canTarget(source == null ? null : source.getControllerId(), id, source, game);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        Permanent permanent = game.getPermanent(id);
        if (permanent == null) {
            return false;
        }

        if (source != null) {
            //1. TODO: check for replacement effects
            //2. We need to check both source.getId() and source.getSourceId()
            // first for protection from spells or abilities (e.g. protection from colored spells, r1753)
            // second for protection from sources (e.g. protection from artifacts + equip ability)
            if (!isNotTarget()) {
                if (!permanent.canBeTargetedBy(game.getObject(source.getId()), playerId, source, game)
                        || !permanent.canBeTargetedBy(game.getObject(source), playerId, source, game)) {
                    return false;
                }
            }
        }

        return filter.match(permanent, playerId, source, game);
    }

    @Override
    public FilterPermanent getFilter() {
        return this.filter;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        return canChooseFromPossibleTargets(sourceControllerId, source, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        // TODO: check if possible targets works with setTargetController from some cards like Nicol Bolas, Dragon-God
        Set<UUID> possibleTargets = new HashSet<>();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, source, game)) {
            possibleTargets.add(permanent.getId());
        }
        return keepValidPossibleTargets(possibleTargets, sourceControllerId, source, game);
    }

    @Override
    public TargetPermanent copy() {
        return new TargetPermanent(this);
    }

    /**
     * User carefully!
     *
     * @param filter
     */
    public void replaceFilter(FilterPermanent filter) {
        this.filter = filter;
        this.targetName = filter.getMessage();
    }
}
