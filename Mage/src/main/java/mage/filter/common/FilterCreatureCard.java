

package mage.filter.common;

import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FilterCreatureCard extends FilterCard {

    public FilterCreatureCard() {
        this("creature card");
    }

    public FilterCreatureCard(String name) {
        super(name);
        this.add(new CardTypePredicate(CardType.CREATURE));
    }

    public FilterCreatureCard(final FilterCreatureCard filter) {
        super(filter);
    }

    @Override
    public FilterCreatureCard copy() {
        return new FilterCreatureCard(this);
    }
}
