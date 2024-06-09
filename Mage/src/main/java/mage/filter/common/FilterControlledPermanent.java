package mage.filter.common;

import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class FilterControlledPermanent extends FilterPermanent {

    public FilterControlledPermanent() {
        this("permanent you control");
    }

    public FilterControlledPermanent(String name) {
        this(null, name);
    }

    public FilterControlledPermanent(SubType subtype) {
        this(subtype, subtype.toString() + " you control");
    }

    public FilterControlledPermanent(SubType subtype, String name) {
        super(name);
        this.add(TargetController.YOU.getControllerPredicate());
        if (subtype != null) {
            this.add(subtype.getPredicate());
        }
    }

    protected FilterControlledPermanent(final FilterControlledPermanent filter) {
        super(filter);
    }

    @Override
    public FilterControlledPermanent copy() {
        return new FilterControlledPermanent(this);
    }

}
