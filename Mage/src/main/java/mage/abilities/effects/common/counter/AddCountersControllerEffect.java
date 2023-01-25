package mage.abilities.effects.common.counter;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author nantuko
 */
public class AddCountersControllerEffect extends OneShotEffect {

    private Counter counter;

    /**
     * @param counter Counter to add. Includes type and amount.
     */
    public AddCountersControllerEffect(Counter counter) {
        super(Outcome.Benefit);
        this.counter = counter.copy();
        staticText = "you get" + counter.getDescription();
    }

    public AddCountersControllerEffect(final AddCountersControllerEffect effect) {
        super(effect);
        if (effect.counter != null) {
            this.counter = effect.counter.copy();
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.addCounters(counter, source.getControllerId(), source, game);
            return true;
        }
        return false;
    }

    @Override
    public AddCountersControllerEffect copy() {
        return new AddCountersControllerEffect(this);
    }
}
