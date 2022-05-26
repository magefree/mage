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

    public TargetPermanent(final TargetPermanent target) {
        super(target);
        this.filter = target.filter.copy();
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        return canTarget(source.getControllerId(), id, source, game);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
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
                if (!permanent.canBeTargetedBy(game.getObject(source.getId()), controllerId, game)
                        || !permanent.canBeTargetedBy(game.getObject(source), controllerId, game)) {
                    return false;
                }
            }
        }

        return filter.match(permanent, controllerId, source, game);
    }

    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game, boolean flag) {
        Permanent permanent = game.getPermanent(id);
        return filter.match(permanent, controllerId, source, game);
    }

    @Override
    public FilterPermanent getFilter() {
        return this.filter;
    }

    /**
     * Checks if there are enough {@link Permanent} that can be chosen.
     * <p>
     * Takes into account notTarget parameter, in case it's true doesn't check
     * for protection, shroud etc.
     *
     * @param sourceControllerId controller of the target event source
     * @param source
     * @param game
     * @return true if enough valid {@link Permanent} exist
     */
    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        int remainingTargets = this.minNumberOfTargets - targets.size();
        if (remainingTargets <= 0) {
            return true;
        }
        int count = 0;
        MageObject targetSource = game.getObject(source);
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, source, game)) {
            if (!targets.containsKey(permanent.getId())) {
                if (notTarget || permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                    count++;
                    if (count >= remainingTargets) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks if there are enough {@link Permanent} that can be selected. Should
     * not be used for Ability targets since this does not check for protection,
     * shroud etc.
     *
     * @param sourceControllerId - controller of the select event
     * @param game
     * @return - true if enough valid {@link Permanent} exist
     */
    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        int remainingTargets = this.minNumberOfTargets - targets.size();
        if (remainingTargets == 0) {
            // if we return true, then AnowonTheRuinSage will hang for AI when no targets in play
            // TODO: retest Anowon the Ruin Sage
            return true;
        }
        int count = 0;
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, game)) {
            if (!targets.containsKey(permanent.getId())) {
                count++;
                if (count >= remainingTargets) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        // TODO: check if possible targets works with setTargetController from some cards like Nicol Bolas, Dragon-God
        Set<UUID> possibleTargets = new HashSet<>();
        MageObject targetSource = game.getObject(source);
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, source, game)) {
            if (!targets.containsKey(permanent.getId())) {
                if (notTarget || permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                    possibleTargets.add(permanent.getId());
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, game)) {
            if (!targets.containsKey(permanent.getId())) {
                possibleTargets.add(permanent.getId());
            }
        }
        return possibleTargets;
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
