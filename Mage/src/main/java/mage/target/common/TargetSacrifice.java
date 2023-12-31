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
        this.withChooseHint("to sacrifice");
    }

    protected TargetSacrifice(final TargetSacrifice target) {
        super(target);
    }

    @Override
    public TargetSacrifice copy() {
        return new TargetSacrifice(this);
    }

    /**
     * Creates a new filter with necessary constraints for sacrificing
     * @param filter input generic filter
     * @return new filter with "you control" and CanBeSacrificedPredicate added
     */
    public static FilterPermanent makeFilter(FilterPermanent filter) {
        FilterPermanent newFilter = filter.copy();
        newFilter.add(TargetController.YOU.getControllerPredicate());
        newFilter.add(CanBeSacrificedPredicate.instance);
        if (filter.getMessage().contains(" you control")) {
            newFilter.setMessage(filter.getMessage().replace(" you control", ""));
        }
        return newFilter;
    }
}
