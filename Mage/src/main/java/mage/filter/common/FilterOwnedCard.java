

package mage.filter.common;

import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.other.OwnerPredicate;

/**
 *
 * @author LevelX2
 */
public class FilterOwnedCard extends FilterCard {

    public FilterOwnedCard() {
        this("card");
    }

    public FilterOwnedCard(String name) {
        super(name);
        this.add(new OwnerPredicate(TargetController.YOU));
    }

    public FilterOwnedCard(final FilterOwnedCard filter) {
        super(filter);
    }

    @Override
    public FilterOwnedCard copy() {
        return new FilterOwnedCard(this);
    }
}
