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
 *
 * @author weirddan455
 */
public class ShieldCounterEffect extends ReplacementEffectImpl {

    public ShieldCounterEffect() {
        super(Duration.Custom, Outcome.PreventDamage);
        this.staticText = "If it would be dealt damage or destroyed, remove a shield counter from it instead";
    }

    private ShieldCounterEffect(final ShieldCounterEffect effect) {
        super(effect);
    }

    @Override
    public ShieldCounterEffect copy() {
        return new ShieldCounterEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null || permanent.getCounters(game).getCount(CounterType.SHIELD) < 1) {
            return false;
        }
        permanent.removeCounters(CounterType.SHIELD.getName(), 1, source, game);
        if (!game.isSimulation()) {
            game.informPlayers("Removed a shield counter from " + permanent.getLogName());
        }
        // Damage should be prevented rather than replacing the event.
        // Effects that say "damage can't be prevented" will have the creature both take the damage and remove a shield counter.
        if (event.getType() == GameEvent.EventType.DAMAGE_PERMANENT) {
            game.preventDamage(event, source, game, Integer.MAX_VALUE);
            return false;
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PERMANENT:
            case DESTROY_PERMANENT:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null && permanent.getCounters(game).getCount(CounterType.SHIELD) > 0;
    }
}
