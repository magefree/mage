package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public class DoubleP1P1CountersSourceEffect extends OneShotEffect {

    public DoubleP1P1CountersSourceEffect() {
        super(Outcome.Benefit);
        staticText = "double the number of +1/+1 counters on {this}";
    }

    private DoubleP1P1CountersSourceEffect(final DoubleP1P1CountersSourceEffect effect) {
        super(effect);
    }

    @Override
    public DoubleP1P1CountersSourceEffect copy() {
        return new DoubleP1P1CountersSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        return permanent.addCounters(CounterType.P1P1.createInstance(
                permanent.getCounters(game).getCount(CounterType.P1P1)
        ), source.getControllerId(), source, game);
    }
}
