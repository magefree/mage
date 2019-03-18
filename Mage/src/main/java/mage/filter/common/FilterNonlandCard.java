

package mage.filter.common;

import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FilterNonlandCard extends FilterCard {

    public FilterNonlandCard() {
        this("nonland card");
    }

    public FilterNonlandCard(String name) {
        super(name);
        this.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
    }

    public FilterNonlandCard(final FilterNonlandCard filter) {
        super(filter);
    }

    @Override
    public FilterNonlandCard copy() {
        return new FilterNonlandCard(this);
    }
}
