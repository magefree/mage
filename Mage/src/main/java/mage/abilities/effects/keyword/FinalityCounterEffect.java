package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public class FinalityCounterEffect extends ReplacementEffectImpl {

    public FinalityCounterEffect() {
        super(Duration.Custom, Outcome.Tap);
        this.staticText = "If a creature with a finality counter on it would die, exile it instead.";
    }

    private FinalityCounterEffect(final FinalityCounterEffect effect) {
        super(effect);
    }

    @Override
    public FinalityCounterEffect copy() {
        return new FinalityCounterEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!((ZoneChangeEvent) event).isDiesEvent()) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null && permanent.getCounters(game).getCount(CounterType.FINALITY) > 0;
    }
}
