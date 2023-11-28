package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author jeffwadsworth
 */

public class SkipCombatStepEffect extends ReplacementEffectImpl {

    public SkipCombatStepEffect(Duration duration) {
        super(duration, Outcome.Detriment);
        staticText = "that player skips their next combat phase";
    }

    protected SkipCombatStepEffect(final SkipCombatStepEffect effect) {
        super(effect);
    }

    @Override
    public SkipCombatStepEffect copy() {
        return new SkipCombatStepEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COMBAT_PHASE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(targetPointer.getFirst(game, source));
    }
}
