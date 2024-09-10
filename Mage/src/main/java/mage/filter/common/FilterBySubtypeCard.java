

package mage.filter.common;

import mage.constants.SubType;
import mage.filter.FilterCard;

/**
 * @author LevelX2
 */

public class FilterBySubtypeCard extends FilterCard {

    public FilterBySubtypeCard(SubType subtype) {
        super(subtype + " card");
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
