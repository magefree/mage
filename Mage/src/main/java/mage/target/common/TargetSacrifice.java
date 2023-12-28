package mage.target.common;

import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.CanBeSacrificedPredicate;
import mage.target.TargetPermanent;

/**
 * @author TheElk801
 */
public class TargetSacrifice extends TargetPermanent {

    public TargetSacrifice(FilterPermanent filter) {
        this(1, filter);
    }

    public TargetSacrifice(int numTargets, FilterPermanent filter) {
        this(numTargets, numTargets, filter);
    }

    public TargetSacrifice(int minNumTargets, int maxNumTargets, FilterPermanent filter) {
        super(minNumTargets, maxNumTargets, makeFilter(filter), true);
    }

    private TargetSacrifice(final TargetSacrifice target) {
        super(target);
    }

    private static FilterPermanent makeFilter(FilterPermanent filter) {
        FilterPermanent newFilter = filter.copy();
        newFilter.add(TargetController.YOU.getControllerPredicate());
        newFilter.add(CanBeSacrificedPredicate.instance);
        return newFilter;
    }
}
