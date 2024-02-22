package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author LevelX2
 */

public class SkipDrawStepEffect extends ReplacementEffectImpl {

    public SkipDrawStepEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "Skip your draw step";
    }

    protected SkipDrawStepEffect(final SkipDrawStepEffect effect) {
        super(effect);
    }

    @Override
    public SkipDrawStepEffect copy() {
        return new SkipDrawStepEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_STEP;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }
}
