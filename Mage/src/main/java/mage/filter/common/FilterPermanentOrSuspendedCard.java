package mage.filter.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.MultiFilterImpl;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author emerald000
 */
public class FilterPermanentOrSuspendedCard extends MultiFilterImpl<MageObject> {

    public FilterPermanentOrSuspendedCard() {
        this("permanent or suspended card");
    }

    public FilterPermanentOrSuspendedCard(String name) {
        super(name, new FilterPermanent(), new FilterCard());
        this.getCardFilter().add(new AbilityPredicate(SuspendAbility.class));
        this.getCardFilter().add(CounterType.TIME.getPredicate());
    }

    protected FilterPermanentOrSuspendedCard(final FilterPermanentOrSuspendedCard filter) {
        super(filter);
    }

    @Override
    public boolean match(MageObject object, Game game) {
        // We can not rely on super.match, since Permanent extend Card
        // so a Permanent could get filtered by the FilterCard
        if (object instanceof Permanent) {
            return getPermanentFilter().match((Permanent) object, game);
        } else if (object instanceof Card) {
            return getCardFilter().match((Card) object, game);
        } else {
            return false;
        }
    }

    @Override
    public boolean match(MageObject object, UUID sourceControllerId, Ability source, Game game) {
        // We can not rely on super.match, since Permanent extend Card
        // so a Permanent could get filtered by the FilterCard
        if (object instanceof Permanent) {
            return getPermanentFilter().match((Permanent) object, sourceControllerId, source, game);
        } else if (object instanceof Card) {
            return getCardFilter().match((Card) object, sourceControllerId, source, game);
        } else {
            return false;
        }
    }

    @Override
    public FilterPermanentOrSuspendedCard copy() {
        return new FilterPermanentOrSuspendedCard(this);
    }

    public FilterPermanent getPermanentFilter() {
        return (FilterPermanent) this.innerFilters.get(0);
    }

    public FilterCard getCardFilter() {
        return (FilterCard) this.innerFilters.get(1);
    }
}
