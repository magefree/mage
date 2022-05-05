package mage.abilities.effects.common.counter;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 * @author Loki
 */
public class RemoveCounterSourceEffect extends OneShotEffect {

    private final Counter counter;

    public RemoveCounterSourceEffect(Counter counter) {
        super(Outcome.UnboostCreature);
        this.counter = counter;
        setText();
    }

    public RemoveCounterSourceEffect(RemoveCounterSourceEffect effect) {
        super(effect);
        this.counter = effect.counter.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null) {
            int toRemove = Math.min(counter.getCount(), permanent.getCounters(game).getCount(counter.getName()));
            if (toRemove > 0) {
                permanent.removeCounters(counter.getName(), toRemove, source, game);
                if (!game.isSimulation()) {
                    game.informPlayers("Removed " + toRemove + ' ' + counter.getName() + " counter from " + permanent.getLogName());
                }
            }
            return true;
        }
        if (!(source.getSourceObject(game) instanceof Permanent)) {
            Card card = game.getCard(source.getSourceId());
            if (card != null) {
                int toRemove = Math.min(counter.getCount(), card.getCounters(game).getCount(counter.getName()));
                if (toRemove > 0) {
                    card.removeCounters(counter.getName(), toRemove, source, game);
                    if (!game.isSimulation()) {
                        game.informPlayers("Removed " + toRemove + ' ' + counter.getName()
                                + " counter from " + card.getLogName()
                                + " (" + card.getCounters(game).getCount(counter.getName()) + " left)");
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public RemoveCounterSourceEffect copy() {
        return new RemoveCounterSourceEffect(this);
    }

    private void setText() {
        if (counter.getCount() > 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("remove ").append(CardUtil.numberToText(counter.getCount())).append(' ').append(counter.getName()).append(" counters from {this}");
            staticText = sb.toString();
        } else {
            staticText = "remove " + CounterType.findArticle(counter.getName()) + " " + counter.getName() + " counter from {this}";
        }
    }
}
