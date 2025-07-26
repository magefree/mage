package mage.filter.common;

import mage.constants.SubType;
import mage.filter.FilterCard;

/**
 * TODO: Collapse this into FilterCard
 *
 * @author LevelX2
 */

public class FilterBySubtypeCard extends FilterCard {

    public FilterBySubtypeCard(SubType subtype) {
        this(subtype, subtype + " card");
    }

    public FilterBySubtypeCard(SubType subtype, String name) {
        super(name);
        this.add(subtype.getPredicate());
    }

    protected FilterBySubtypeCard(final FilterBySubtypeCard filter) {
        super(filter);
    }

    @Override
    public FilterBySubtypeCard copy() {
        return new FilterBySubtypeCard(this);
    }
}
