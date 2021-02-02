package mage.abilities.effects.keyword;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
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
        staticText = "Adapt " + adaptNumber
                + ". <i>(If this creature has no +1/+1 counters on it, put "
                + CardUtil.numberToText(adaptNumber) + " +1/+1 counter"
                + (adaptNumber > 1 ? "s" : "") + " on it.)</i>";
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
        // Verify source object did not change zone and is on the battlefield
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        if (sourceObject == null) {
            if (game.getState().getZone(source.getSourceId()).equals(Zone.BATTLEFIELD)
                    && source.getSourceObjectZoneChangeCounter() + 1 == game.getState().getZoneChangeCounter(source.getSourceId())) {
                sourceObject = game.getPermanent(source.getSourceId());
            }
        }
        Permanent permanent = ((Permanent) sourceObject);
        if (permanent == null) {
            return false;
        }
        GameEvent event = new GameEvent(
                GameEvent.EventType.ADAPT, source.getSourceId(), source,
                source.getControllerId(), adaptNumber, false
        );
        if (game.replaceEvent(event)) {
            return false;
        }
        if (permanent.getCounters(game).getCount(CounterType.P1P1) == 0
                || event.getFlag()) {
            permanent.addCounters(CounterType.P1P1.createInstance(event.getAmount()), source.getControllerId(), source, game);
        }
        return true;
    }
}
