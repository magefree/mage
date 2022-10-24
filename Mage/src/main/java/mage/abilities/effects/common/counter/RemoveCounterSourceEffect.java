package mage.abilities.effects.common.counter;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author Loki
 */
public class RemoveCounterSourceEffect extends OneShotEffect {

    private final Counter counter;

    public RemoveCounterSourceEffect(Counter counter) {
        super(Outcome.UnboostCreature);
        this.counter = counter;
        staticText = "remove " + counter.getDescription() + " from {this}";
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
}
