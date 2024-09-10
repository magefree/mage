package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author TheElk801
 */
public class CantBeCopiedSourceEffect extends ReplacementEffectImpl {

    public CantBeCopiedSourceEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit);
        staticText = "this spell can't be copied";
    }

    private CantBeCopiedSourceEffect(final CantBeCopiedSourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COPY_STACKOBJECT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public CantBeCopiedSourceEffect copy() {
        return new CantBeCopiedSourceEffect(this);
    }
}
