package mage.abilities.effects.common.counter;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
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
    private final DynamicValue amount;
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
        if (controller != null && sourceObject != null && counter != null) {
            Counter newCounter = counter.copy();
            int calculated = amount.calculate(game, source, this);
            if (!(amount instanceof StaticValue) || calculated > 0) {
                // If dynamic, or static and set to a > 0 value, we use that instead of the counter's internal amount.
                newCounter.remove(newCounter.getCount());
                newCounter.add(calculated);
            } else {
                // StaticValue 0 -- the default counter has the amount, so no adjustment.
            }

            if (newCounter.getCount() <= 0) {
                return false; // no need to iterate on targets, no counters will be put on them
            }

            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                Counter newCounterForPermanent = counter.copy();

                permanent.addCounters(newCounterForPermanent, source.getControllerId(), source, game);
                if (!game.isSimulation() && newCounterForPermanent.getCount() > 0) {
                    game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName() + " puts " + newCounterForPermanent.getCount() + ' ' + newCounterForPermanent.getName()
                            + (newCounterForPermanent.getCount() == 1 ? " counter" : " counters") + " on " + permanent.getLogName());
                }
            }
        }
        return false;
    }

    @Override
    public AddCountersAllEffect copy() {
        return new AddCountersAllEffect(this);
    }
}
