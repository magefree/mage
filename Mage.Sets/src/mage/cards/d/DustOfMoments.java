package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPermanentOrSuspendedCard;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DustOfMoments extends CardImpl {

    public DustOfMoments(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Choose one - Remove two time counters from each permanent and each suspended card
        this.getSpellAbility().addEffect(new DustOfMomentsEffect(true));

        // Or put two time counters on each permanent with a time counter on it and each suspended card
        this.getSpellAbility().addMode(new Mode(new DustOfMomentsEffect(false)));
    }

    private DustOfMoments(final DustOfMoments card) {
        super(card);
    }

    @Override
    public DustOfMoments copy() {
        return new DustOfMoments(this);
    }
}

class DustOfMomentsEffect extends OneShotEffect {

    private static final FilterPermanentOrSuspendedCard filter = new FilterPermanentOrSuspendedCard();
    private final boolean remove;

    static {
        filter.getPermanentFilter().add(CounterType.TIME.getPredicate());
    }

    DustOfMomentsEffect(boolean remove) {
        super(Outcome.Benefit);
        this.remove = remove;
        this.staticText = (remove ? "remove" : "put") +
                " two time counters " +
                (remove ? "from" : "on") +
                " each permanent" +
                (remove ? "" : " with a time counter on it") +
                " and each suspended card";
    }

    private DustOfMomentsEffect(final DustOfMomentsEffect effect) {
        super(effect);
        this.remove = effect.remove;
    }

    @Override
    public DustOfMomentsEffect copy() {
        return new DustOfMomentsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                remove ? StaticFilters.FILTER_PERMANENT : filter.getPermanentFilter(),
                source.getControllerId(), source, game
        )) {
            if (remove) {
                permanent.removeCounters(CounterType.TIME.createInstance(2), source, game);
            } else {
                permanent.addCounters(CounterType.TIME.createInstance(2), source, game);
            }
        }
        for (Card card : game.getExile().getCards(filter.getCardFilter(), game)) {
            if (remove) {
                card.removeCounters(CounterType.TIME.createInstance(2), source, game);
            } else {
                card.addCounters(CounterType.TIME.createInstance(2), source, game);
            }
        }
        return true;
    }
}
