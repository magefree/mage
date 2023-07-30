

package mage.filter.common;


import mage.constants.CardType;

/**
 * @author Susucr
 */
public class FilterOwnedCreatureCard extends FilterOwnedCard {

    public FilterOwnedCreatureCard() {
        this("creature card");
    }

    public FilterOwnedCreatureCard(String name) {
        super(name);
        this.add(CardType.CREATURE.getPredicate());
    }

    public FilterOwnedCreatureCard(final FilterOwnedCreatureCard filter) {
        super(filter);
    }

    @Override
    public FilterOwnedCreatureCard copy() {
        return new FilterOwnedCreatureCard(this);
    }
}
