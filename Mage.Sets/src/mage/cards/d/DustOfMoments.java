
package mage.cards.d;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Gal Lerman
 *
 */
public final class DustOfMoments extends CardImpl {

    public DustOfMoments(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Choose one - Remove two time counters from each permanent and each suspended card
        this.getSpellAbility().addEffect(new RemoveCountersEffect());

        // Or put two time counters on each permanent with a time counter on it and each suspended card
        Mode mode = new Mode(new AddCountersEffect());
        this.getSpellAbility().addMode(mode);
    }

    private DustOfMoments(final DustOfMoments card) {
        super(card);
    }

    @Override
    public DustOfMoments copy() {
        return new DustOfMoments(this);
    }

    //TODO: PermanentImpl.getCounters() and CardImpl.getCounters(game) don't return the same value for the same Card
    //TODO: This means I can't use a Card generic for Permanents and Exiled cards and use Card.getCounters(game)
    //TODO: This is the reason i've copy pasted some logic in DustOfMomentsEffect
    //TODO: After this issue is fixed/explained i'll refactor the code
    public abstract static class DustOfMomentsEffect extends OneShotEffect {

        private final Counter counter;
        private final Filter<Permanent> permFilter;
        private final Filter<Card> exiledFilter;

        public DustOfMomentsEffect() {
            super(Outcome.Benefit);
            this.counter = new Counter(CounterType.TIME.getName(), 2);
            this.permFilter = new FilterPermanent("permanent and each suspended card");
            permFilter.add(CounterType.TIME.getPredicate());

            this.exiledFilter = new FilterCard("permanent and each suspended card");
            exiledFilter.add(CounterType.TIME.getPredicate());
            setText();
        }

        public DustOfMomentsEffect(final DustOfMomentsEffect effect) {
            super(effect);
            this.counter = effect.counter.copy();
            this.permFilter = effect.permFilter.copy();
            this.exiledFilter = effect.exiledFilter.copy();
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player controller = game.getPlayer(source.getControllerId());
            MageObject sourceObject = game.getObject(source.getSourceId());
            if (controller != null && sourceObject != null) {
                updatePermanents(source, game, controller, sourceObject);
                updateSuspended(source, game, controller, sourceObject);
                return true;
            }
            return false;
        }

        private void updateSuspended(final Ability source, final Game game, final Player controller, final MageObject sourceObject) {
            final List<Card> exiledCards = game.getExile().getAllCards(game);
            execute(source, game, controller, sourceObject, exiledCards);
        }

        private void updatePermanents(final Ability source, final Game game, final Player controller, final MageObject sourceObject) {
            List<Permanent> permanents = game.getBattlefield().getAllActivePermanents();
            executeP(source, game, controller, sourceObject, permanents);
        }

        private void executeP(final Ability source, final Game game, final Player controller, final MageObject sourceObject, final List<Permanent> cards) {
            if (cards == null || cards.isEmpty()) {
                return;
            }
            for (Permanent card : cards) {
                if (permFilter.match(card, game)) {
                    final String counterName = counter.getName();
                    if (shouldRemoveCounters()) {
                        final Counter existingCounterOfSameType = card.getCounters(game).get(counterName);
                        final int countersToRemove = Math.min(existingCounterOfSameType.getCount(), counter.getCount());
                        final Counter modifiedCounter = new Counter(counterName, countersToRemove);
                        card.removeCounters(modifiedCounter, source, game);
                    } else {
                        card.addCounters(counter, source.getControllerId(), source, game);
                    }
                    if (!game.isSimulation()) {
                        game.informPlayers(sourceObject.getName() + ": " +
                                controller.getLogName() + getActionStr() + 's' +
                                counter.getCount() + ' ' + counterName.toLowerCase(Locale.ENGLISH) +
                                " counter on " + card.getName());
                    }
                }
            }
        }

        private void execute(final Ability source, final Game game, final Player controller, final MageObject sourceObject, final List<Card> cards) {
            if (cards == null || cards.isEmpty()) {
                return;
            }
            for (Card card : cards) {
                if (exiledFilter.match(card, game)) {
                    final String counterName = counter.getName();
                    if (shouldRemoveCounters()) {
                        final Counter existingCounterOfSameType = card.getCounters(game).get(counterName);
                        final int countersToRemove = Math.min(existingCounterOfSameType.getCount(), counter.getCount());
                        final Counter modifiedCounter = new Counter(counterName, countersToRemove);
                        card.removeCounters(modifiedCounter, source, game);
                    } else {
                        card.addCounters(counter, source.getControllerId(), source, game);
                    }
                    if (!game.isSimulation()) {
                        game.informPlayers(sourceObject.getName() + ": " +
                                controller.getLogName() + getActionStr() + "s " +
                                counter.getCount() + ' ' + counterName.toLowerCase(Locale.ENGLISH) +
                                " counter on " + card.getName());
                    }
                }
            }
        }

        protected abstract boolean shouldRemoveCounters();

        protected abstract String getActionStr();

        private void setText() {
            StringBuilder sb = new StringBuilder();
            sb.append(getActionStr());
            if (counter.getCount() > 1) {
                sb.append(Integer.toString(counter.getCount())).append(' ').append(counter.getName().toLowerCase(Locale.ENGLISH)).append(" counters on each ");
            } else {
                sb.append("a ").append(counter.getName().toLowerCase(Locale.ENGLISH)).append(" counter on each ");
            }
            sb.append(permFilter.getMessage());
            staticText = sb.toString();
        }
    }

    public static class AddCountersEffect extends DustOfMomentsEffect {

        public AddCountersEffect() {
            super();
        }

        public AddCountersEffect(final DustOfMomentsEffect effect) {
            super(effect);
        }

        @Override
        protected boolean shouldRemoveCounters() {
            return false;
        }

        @Override
        protected String getActionStr() {
            return "add";
        }

        @Override
        public Effect copy() {
            return new AddCountersEffect(this);
        }
    }

    public static class RemoveCountersEffect extends DustOfMomentsEffect {

        public RemoveCountersEffect() {
            super();
        }

        public RemoveCountersEffect(final DustOfMomentsEffect effect) {
            super(effect);
        }

        @Override
        protected boolean shouldRemoveCounters() {
            return true;
        }

        @Override
        protected String getActionStr() {
            return "remove";
        }

        @Override
        public Effect copy() {
            return new RemoveCountersEffect(this);
        }
    }
}
