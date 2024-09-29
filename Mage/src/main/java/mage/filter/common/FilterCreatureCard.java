

package mage.filter.common;

import mage.constants.CardType;
import mage.filter.FilterCard;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class FilterCreatureCard extends FilterCard {

    public FilterCreatureCard() {
        this("creature card");
    }

    public FilterCreatureCard(String name) {
        super(name);
        this.add(CardType.CREATURE.getPredicate());
    }

    protected FilterCreatureCard(final FilterCreatureCard filter) {
        super(filter);
    }

    @Override
    public FilterCreatureCard copy() {
        return new FilterCreatureCard(this);
    }
}
