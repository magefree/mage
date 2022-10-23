package mage.abilities.effects.common.counter;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author North
 */
public class AddCountersAllEffect extends OneShotEffect {

    private final Counter counter;
    private final FilterPermanent filter;

    public AddCountersAllEffect(Counter counter, FilterPermanent filter) {
        super(Outcome.Benefit);
        this.counter = counter;
        this.filter = filter;
        staticText = "put " + counter.getDescription() + " on each " + filter.getMessage();
    }

    public AddCountersAllEffect(final AddCountersAllEffect effect) {
        super(effect);
        this.counter = effect.counter.copy();
        this.filter = effect.filter.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            if (counter != null) {
                for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                    permanent.addCounters(counter.copy(), source.getControllerId(), source, game);
                    if (!game.isSimulation()) {
                        game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName() + " puts " + counter.getCount() + ' ' + counter.getName()
                                + " counter on " + permanent.getLogName());
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public AddCountersAllEffect copy() {
        return new AddCountersAllEffect(this);
    }
}
