package mage.target.common;

import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TargetAttackingCreature extends TargetPermanent {

    public TargetAttackingCreature() {
        this(1);
    }

    public TargetAttackingCreature(int numTargets) {
        this(numTargets, numTargets);
    }

    public TargetAttackingCreature(int minNumTargets, int maxNumTargets) {
        super(minNumTargets, maxNumTargets, StaticFilters.FILTER_ATTACKING_CREATURE, false);
    }

    public TargetAttackingCreature(final TargetAttackingCreature target) {
        super(target);
    }

    @Override
    public TargetAttackingCreature copy() {
        return new TargetAttackingCreature(this);
    }
}
