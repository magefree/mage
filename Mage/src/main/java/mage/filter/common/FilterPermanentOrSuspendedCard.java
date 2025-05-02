package mage.filter.common;

import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterImpl;
import mage.filter.FilterInPlay;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author emerald000
 */
public class FilterPermanentOrSuspendedCard extends FilterImpl<MageObject> implements FilterInPlay<MageObject> {

    protected FilterCard cardFilter;
    protected FilterPermanent permanentFilter;

    public FilterPermanentOrSuspendedCard() {
        this("permanent or suspended card");
    }

    public FilterPermanentOrSuspendedCard(String name) {
        super(name);
        permanentFilter = new FilterPermanent();
        cardFilter = new FilterCard();
        cardFilter.add(new AbilityPredicate(SuspendAbility.class));
        cardFilter.add(CounterType.TIME.getPredicate());
    }

    protected FilterPermanentOrSuspendedCard(final FilterPermanentOrSuspendedCard filter) {
        super(filter);
        this.permanentFilter = filter.permanentFilter.copy();
        this.cardFilter = filter.cardFilter.copy();
    }

    @Override
    public boolean checkObjectClass(Object object) {
        return object instanceof MageObject;
    }

    @Override
    public boolean match(MageObject o, Game game) {
        if (o instanceof Permanent) {
            return permanentFilter.match((Permanent) o, game);
        } else if (o instanceof Card) {
            return cardFilter.match((Card) o, game);
        }
        return false;
    }

    @Override
    public boolean match(MageObject o, UUID playerId, Ability source, Game game) {
        if (o instanceof Permanent) {
            return permanentFilter.match((Permanent) o, playerId, source, game);
        } else if (o instanceof Card) {
            return cardFilter.match((Card) o, playerId, source, game);
        }
        return false;
    }

    public FilterPermanent getPermanentFilter() {
        return this.permanentFilter;
    }

    public FilterCard getCardFilter() {
        return this.cardFilter;
    }

    public void setPermanentFilter(FilterPermanent permanentFilter) {
        this.permanentFilter = permanentFilter;
    }

    public void setCardFilter(FilterCard cardFilter) {
        this.cardFilter = cardFilter;
    }

    @Override
    public FilterPermanentOrSuspendedCard copy() {
        return new FilterPermanentOrSuspendedCard(this);
    }
}
