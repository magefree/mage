package mage.filter.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterImpl;
import mage.filter.FilterInPlay;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author emerald000
 */
public class FilterSuspendedCard extends FilterImpl<MageObject> implements FilterInPlay<MageObject> {

    protected FilterCard cardFilter;

    public FilterSuspendedCard() {
        this("suspended card");
    }

    public FilterSuspendedCard(String name) {
        super(name);
        cardFilter = new FilterCard();
        cardFilter.add(new AbilityPredicate(SuspendAbility.class));
        cardFilter.add(CounterType.TIME.getPredicate());
    }

    protected FilterSuspendedCard(final FilterSuspendedCard filter) {
        super(filter);
        this.cardFilter = filter.cardFilter.copy();
    }

    @Override
    public boolean checkObjectClass(Object object) {
        return object instanceof MageObject;
    }

    @Override
    public boolean match(MageObject o, Game game) {
        if (o instanceof Card) {
            return cardFilter.match((Card) o, game);
        }
        return false;
    }

    @Override
    public boolean match(MageObject o, UUID playerId, Ability source, Game game) {
        if (o instanceof Card) {
            return cardFilter.match((Card) o, playerId, source, game);
        }
        return false;
    }

    public FilterCard getCardFilter() {
        return this.cardFilter;
    }

    public void setCardFilter(FilterCard cardFilter) {
        this.cardFilter = cardFilter;
    }

    @Override
    public FilterSuspendedCard copy() {
        return new FilterSuspendedCard(this);
    }
}
