package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public class StunCounterEffect extends ReplacementEffectImpl {

    public StunCounterEffect() {
        super(Duration.Custom, Outcome.Tap);
        this.staticText = "If a permanent with a stun counter would become untapped, remove one from it instead.";
    }

    private StunCounterEffect(final StunCounterEffect effect) {
        super(effect);
    }

    @Override
    public StunCounterEffect copy() {
        return new StunCounterEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null || permanent.getCounters(game).getCount(CounterType.STUN) < 1) {
            return false;
        }
        permanent.removeCounters(CounterType.STUN.getName(), 1, source, game);
        game.informPlayers("Removed a stun counter from " + permanent.getLogName());
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAP;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null && permanent.getCounters(game).getCount(CounterType.STUN) > 0;
    }
}
