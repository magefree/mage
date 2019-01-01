package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public class AdaptEffect extends OneShotEffect {

    private final int adaptNumber;

    public AdaptEffect(int adaptNumber) {
        super(Outcome.BoostCreature);
        this.adaptNumber = adaptNumber;
        staticText = "Adapt " + adaptNumber +
                " <i>(If this creature has no +1/+1 counters on it, put " +
                CardUtil.numberToText(adaptNumber) + " +1/+1 counter" +
                (adaptNumber > 1 ? "s" : "") + " on it.)</i>";
    }

    private AdaptEffect(final AdaptEffect effect) {
        super(effect);
        this.adaptNumber = effect.adaptNumber;
    }

    @Override
    public AdaptEffect copy() {
        return new AdaptEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        if (permanent.getCounters(game).getCount(CounterType.P1P1) == 0) {
            permanent.addCounters(CounterType.P1P1.createInstance(adaptNumber), source, game);
        }
        return true;
    }
}
