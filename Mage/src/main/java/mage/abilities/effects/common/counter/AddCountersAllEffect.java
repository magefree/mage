package mage.abilities.effects.common.counter;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.Locale;

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
        setText();
    }

    public AddCountersAllEffect(final AddCountersAllEffect effect) {
        super(effect);
        this.counter = effect.counter.copy();
        this.filter = effect.filter.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (counter == null) {
            return false;
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            permanent.addCounters(counter.copy(), source.getControllerId(), source, game);
        }
        return true;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append("put ");
        if (counter.getCount() > 1) {
            sb.append(CardUtil.numberToText(counter.getCount(), "a")).append(' ').append(counter.getName().toLowerCase(Locale.ENGLISH)).append(" counters on each ");
        } else {
            sb.append(CounterType.findArticle(counter.getName())).append(' ').append(counter.getName().toLowerCase(Locale.ENGLISH)).append(" counter on each ");
        }
        sb.append(filter.getMessage());
        staticText = sb.toString();
    }

    @Override
    public AddCountersAllEffect copy() {
        return new AddCountersAllEffect(this);
    }
}
