

package mage.filter.common;

import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author LevelX2
 */

public class FilterBySubtypeCard extends FilterCard {

    public FilterBySubtypeCard(SubType subtype) {
        super(subtype + " card");
        this.add(new SubtypePredicate(subtype));
    }

    public FilterBySubtypeCard(final FilterBySubtypeCard filter) {
        super(filter);
    }

    @Override
    public FilterBySubtypeCard copy() {
        return new FilterBySubtypeCard(this);
    }
}
