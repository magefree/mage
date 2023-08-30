package mage.abilities.effects.common.counter;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author North
 */
public class AddCountersAllEffect extends OneShotEffect {

    private final Counter counter;
    private DynamicValue amount;
    private final FilterPermanent filter;

    public AddCountersAllEffect(Counter counter, FilterPermanent filter) {
        this(counter, StaticValue.get(0), filter);
    }

    public AddCountersAllEffect(Counter counter, DynamicValue amount, FilterPermanent filter) {
        super(Outcome.Benefit);
        this.counter = counter;
        this.amount = amount;
        this.filter = filter;
        staticText = "put " + counter.getDescription() + " on each " + filter.getMessage();
    }

    protected AddCountersAllEffect(final AddCountersAllEffect effect) {
        super(effect);
        this.counter = effect.counter.copy();
        this.filter = effect.filter.copy();
        this.amount = effect.amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            if (counter != null) {
                for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                    Counter newCounter = counter.copy();
                    int calculated = amount.calculate(game, source, this); // 0 -- you must use default couner
                    if (calculated < 0) {
                        continue;
                    } else if (calculated == 0) {
                        // use original counter
                    } else {
                        // increase to calculated value
                        newCounter.remove(newCounter.getCount());
                        newCounter.add(calculated);
                    }

                    permanent.addCounters(newCounter, source.getControllerId(), source, game);
                    if (!game.isSimulation()) {
                        game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName() + " puts " + newCounter.getCount() + ' ' + newCounter.getName()
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
