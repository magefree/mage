package mage.target.common;

import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 * @author LevelX2
 */
public class TargetEnchantmentPermanent extends TargetPermanent {

    public TargetEnchantmentPermanent() {
        this(1);
    }

    public TargetEnchantmentPermanent(int numTargets) {
        this(numTargets, numTargets);
    }

    public TargetEnchantmentPermanent(int minNumTargets, int maxNumTargets) {
        super(minNumTargets, maxNumTargets, StaticFilters.FILTER_PERMANENT_ENCHANTMENT, false);
    }

    protected TargetEnchantmentPermanent(final TargetEnchantmentPermanent target) {
        super(target);
    }

    @Override
    public TargetEnchantmentPermanent copy() {
        return new TargetEnchantmentPermanent(this);
    }
}
