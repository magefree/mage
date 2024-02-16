

package mage.filter.common;

import mage.constants.TargetController;
import mage.filter.FilterCard;

/**
 * @author LevelX2
 */
public class FilterOwnedCard extends FilterCard {

    public FilterOwnedCard() {
        this("card");
    }

    public FilterOwnedCard(String name) {
        super(name);
        this.add(TargetController.YOU.getOwnerPredicate());
    }

    protected FilterOwnedCard(final FilterOwnedCard filter) {
        super(filter);
    }

    @Override
    public FilterOwnedCard copy() {
        return new FilterOwnedCard(this);
    }
}
