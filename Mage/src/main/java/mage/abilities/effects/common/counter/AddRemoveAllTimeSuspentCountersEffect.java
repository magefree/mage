
package mage.abilities.effects.common.counter;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.Filter;
import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Gal Lerman
 */
public class AddRemoveAllTimeSuspentCountersEffect extends OneShotEffect {

    private final Counter counter;
    private final Filter<Card> filter;
    private final boolean removeCounter;
    private final String actionStr;

    public AddRemoveAllTimeSuspentCountersEffect(Counter counter, Filter<Card> filter, boolean removeCounter) {
        super(Outcome.Benefit);
        this.counter = counter;
        this.filter = filter;
        this.removeCounter = removeCounter;
        actionStr = removeCounter ? " removes " : " puts ";
        setText();
    }

    public AddRemoveAllTimeSuspentCountersEffect(final AddRemoveAllTimeSuspentCountersEffect effect) {
        super(effect);
        this.counter = effect.counter.copy();
        this.filter = effect.filter.copy();
        this.removeCounter = effect.removeCounter;
        this.actionStr = effect.actionStr;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            if (counter != null) {
                List<Card> permanents = new ArrayList<>(game.getBattlefield().getAllActivePermanents());
                execute(game, controller, sourceObject, source, permanents, removeCounter);
                final List<Card> exiledCards = game.getExile().getAllCards(game);
                execute(game, controller, sourceObject, source, exiledCards, removeCounter);
            }
            return true;
        }
        return false;
    }

    private void execute(final Game game, final Player controller, final MageObject sourceObject, Ability source, final List<Card> cards, final boolean removeCounter) {
        for (Card card : cards) {
            if (filter.match(card, game)) {
                final String counterName = counter.getName();
                if (removeCounter) {
                    final Counter existingCounterOfSameType = card.getCounters(game).get(counterName);
                    final int countersToRemove = Math.min(existingCounterOfSameType.getCount(), counter.getCount());
                    final Counter modifiedCounter = new Counter(counterName, countersToRemove);
                    card.removeCounters(modifiedCounter, source, game);
                } else {
                    card.addCounters(counter, source.getControllerId(), source, game);
                }
                if (!game.isSimulation()) {
                    game.informPlayers(new StringBuilder(sourceObject.getName()).append(": ")
                            .append(controller.getLogName()).append(actionStr)
                            .append(counter.getCount()).append(' ').append(counterName.toLowerCase(Locale.ENGLISH))
                            .append(" counter on ").append(card.getName()).toString());
                }
            }
        }
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        final String actionsStr2 = removeCounter ? "remove " : " put ";
        sb.append(actionsStr2);
        if (counter.getCount() > 1) {
            sb.append(Integer.toString(counter.getCount())).append(' ').append(counter.getName().toLowerCase(Locale.ENGLISH)).append(" counters on each ");
        } else {
            sb.append(CounterType.findArticle(counter.getName())).append(' ').append(counter.getName().toLowerCase(Locale.ENGLISH)).append(" counter on each ");
        }
        sb.append(filter.getMessage());
        staticText = sb.toString();
    }

    @Override
    public AddRemoveAllTimeSuspentCountersEffect copy() {
        return new AddRemoveAllTimeSuspentCountersEffect(this);
    }
}
